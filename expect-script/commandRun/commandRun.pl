#!/usr/bin/perl

use strict;
use Switch;
use Expect;

#$Expect::Multiline_Matching = 0;

use constant {
	# connection type
	CONN_UNKNOWN => 0,
	CONN_TELNET => 1,
	CONN_SSH => 2,

	# command control
	EC_SUCCESS => 0,
	EC_ERROR => 1,
	EC_SVR_WRNG_ACCOUNT => 2,
	EC_CONN_FAILED => 3,
	EC_SCRIPT_LOAD_FAILED => 4,
	EC_SCRIPT_DUPLICATED => 5,
	EC_TIMEOUT => 6,

	# command type
	RUN_COMMAND => 1,
	RUN_FILE => 2,

	# connect count
	MAX_CONNECT_COUNT => 3
};

my $timeout = 30;
my $root_prompt = '[#] $';

my $host = "localhost";
my $port = 22;
my $conn_mode = 2;
my $account = "";
my $passwd = "";
my $root_passwd = "";

my $su_change_str = "su";
my $command_type = RUN_COMMAND;
my $connect_type = CONN_SSH;
my $conn;
my $command_str;
my $command_file;

my ($sec, $min, $hour, $mday, $mon, $year, $wday, $yday, $isdst) = localtime;
my $log_postfix = sprintf("%04d%02d%02d_%02d%02d%02d", $year + 1900, $mon + 1, $mday, $hour, $min, $sec).".log";


&main();

sub main {
	my $exp = new Expect;
	my $is_error = "0";

	$exp->raw_pty(0);	 			# pty raw mode
	$exp->log_stdout(1);		# Defaults to 1 for spawned commands. 0 for file handles attached with exp_init()
	$exp->exp_internal(1);	# or 2, 3 for more verbose output

	&init();
	&connect_server($exp);
	&su_change($exp);

	$exp->log_file($log_postfix);
	
	switch ( $command_type ) {

		case RUN_COMMAND	{ $is_error = &command_run($exp); }
		case RUN_FILE		{ $is_error = &file_run($exp); }
		else			{ &exit_proc("Unsupported command_type mode", EC_ERROR, 0, undef); }

	}

	if($is_error) {
		&exit_proc("An exit code is not euqal to 0 when executing the command", EC_ERROR, 1, $exp);
	}

	&exit_proc("Completed all tasks successfully", EC_SUCCESS, 1, $exp);    
}

sub init {

	if ( $#ARGV < 1 ) {
		&exit_proc("Not enough arguments. Please check it again", EC_ERROR, 0, undef);
	}
	
	for(my $i = 0; $i < $#ARGV; $i++) {
		switch($ARGV[$i]) {
			case "-T"	{ $conn_mode = CONN_TELNET; }			# Connect type is Telnet
			case "-S"	{ $conn_mode = CONN_SSH; }				# Connect type is SSH
			case "-C"	{ 
						$command_type = RUN_COMMAND;					# Command Type : command line
						$command_str = $ARGV[++$i];						# Command string
			}
			case "-F"	{ 
						$command_type = RUN_FILE; 						# Command Type : file
						$command_file = $ARGV[++$i];					# File path
					}
			case "-h"	{ $host = $ARGV[++$i]; } 					# Target address
			case "-rp"	{ $root_passwd = $ARGV[++$i]; }	# Target server"s root password
			case "-u"	{ $account = $ARGV[++$i]; }				# Connection account
			case "-p"	{ $passwd = $ARGV[++$i]; }				# Connection accoucnt password
			case "-P" { $port = $ARGV[++$i]; }					# Connect port number
		}

	}

	print "HOST : $host\n";
	print "PORT : $port\n";
	print "Connction mode(SSH(1), Telnet(2)) : $conn_mode\n";
	print "Connection account : $account\n";
	print "Connection account password : $passwd\n";
	print "Target's root password : $root_passwd\n";
	print "Command type(Commamd line(1), Input File(2))  : $command_type\n";	

	if( $command_type == RUN_COMMAND) {		
		print "Command : \n$command_str\n";
	} else {
		print "Command file path : $command_file\n";
	}

	return;

}

sub exit_proc {

	my ($msg, $code, $do_release, $sub) = @_;

	# if do_release is not 0 and defined succeed, exit program with message
	if($do_release && defined($sub)) {
		$sub->send("exit\n");
		$sub->soft_close();     
	}

	printf "Exit Message = $msg\nExit Code = $code\nBye.\n";

	exit $code;
}

sub timeout_err {
	&exit_proc("Given execution time exceeded", EC_TIMEOUT, 0, undef);
	return;
}

sub connect_server {

	my $exp = shift;
	my $connect_count = 0;

	switch ( $conn_mode ) {

		case CONN_TELNET	{ $conn = "telnet ".$host." ".$port; }
		case CONN_SSH		{ $conn = "ssh ".$account."@".$host." -p ".$port; }
		else			{ &exit_porc("Unsupported connection mode", EC_ERROR, 0, undef); }
	}

	$exp->spawn($conn);
	$exp->expect($timeout,
		[qr "sername: " => sub { my $sub = shift; $sub->send("$account\n"); exp_continue; } ],
		[qr "ssword: " => sub { 
			my $sub = shift; 
			$sub->send("$passwd\n"); 

			$connect_count = $connect_count +1; 
			if( $connect_count > MAX_CONNECT_COUNT ) {
				&exit_proc("Failed to login", EC_SVR_WRNG_ACCOUNT, 0, undef);
			}
			exp_continue; 
		}],
		[qr "yes/no" => sub{ my $sub = shift; $sub->send("yes\n"); exp_continue; }],
		[qr "[y]:" => sub{ my $sub = shift; printf "???\n"; $sub->send("y\n"); exp_continue; }],
		[qr "ermission denied|onnection refused" => sub{ 
			&exit_proc("Failed to login", EC_SVR_WRNG_ACCOUNT, 0, undef); 
		}],
		[qr "ogin incorrect|onnection closed" => sub{&exit_proc("Failed to login", EC_SVR_WRNG_ACCOUNT, 0, undef);}],
		[qr "o route to host" => sub{&exit_proc("No route to host", EC_CONN_FAILED, 0, undef);}],
		[timeout => \&timeout_err],
		"-re", qr"[#>\$\]]\s?(\e\[\d*;?\d*m){0,1}\s?$");

	return;
}


sub su_change {

	my $exp = shift;
	if( $root_passwd ne "" ) {

		my $changeenv = 0;
		$exp->send("$su_change_str\n");
		$exp->expect($timeout,
			[qr "ssword: $|ssword:$" => sub{
		       		my $sub = shift;
				$sub->send("$root_passwd\n"); 
				exp_continue; 
			}],     
			[qr "\S\S: $|\S\S:$" => sub{ 
				my $sub = shift;
				$sub->send("$root_passwd\n"); 
				exp_continue;
			}],
			[qr "incorrect password" => sub{ $changeenv = 1;  return;}],
			[qr "su: Sorry" => sub{ $changeenv = 1;  return;}],
			[timeout => \&timeout_err],
			"-re", qr "$root_prompt");

		# if changing succeed check a su_changed
		if($changeenv == 1) {
			&exit_proc("Failed to su change.", EC_SVR_WRNG_ACCOUNT, 1, $exp);
		}
	}

	return EC_SUCCESS;
}

sub command_run {

	my ($exp) = @_;

	my @splitted_cmd = split(/;/, $command_str);

	$exp->log_file($log_postfix, "w");

	for(my $i = 0; $i <= @splitted_cmd; $i++ ) {
		my $struct_cmd = $splitted_cmd[$i];
		$exp->send("$struct_cmd\n");

		sleep(1);

		$exp->log_file();
		$exp->expect(2, [qr "--More--"  => sub{
					$exp->send(" "); 
					exp_continue;
				}],
				"-re", qr "$root_prompt");
	}

	sleep(1);
	return EC_SUCCESS;

}

sub file_run {
	my $exp = shift;

	open FILE, $command_file or &exit_proc("Unable to load given $command_file", EC_SCRIPT_LOAD_FAILED, 1, $exp);

	# read script file and run command
	while(my $line = <FILE>) {
		chomp($line);           # remove line eof("\0")
		$line =~s/\t/\cV\t/g;
		$exp->send("$line\n");

		sleep(1);

		$exp->log_file();
		$exp->expect(2, [qr "--More--"  => sub{
					$exp->send(" "); 
					exp_continue;
				}],
				"-re", qr "$root_prompt");

	}

	close(FILE);

	return EC_SUCCESS;
}


