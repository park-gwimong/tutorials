#!/usr/bin/perl

use strict;
use Switch;
use Expect;
use Class::Struct;


$Expect::Multiline_Matching = 0;

struct( 
	Command => {
	running_mode => '$',
	cmd_text => '$',
	is_local_script => '$',
	local_script_path => '$',
	remote_script_path => '$',
	pattern_set => '@',
});

struct( Pattern => { 
	pattern_str => '$',
	action_str => '$',
});

use constant {
	# connection type
	CONN_UNKNOWN => 0,
	CONN_TELNET => 1,
	CONN_SSH =>2,

	# os type
	OS_BYPASS => -1,
	OS_LINUX => 2,
	OS_AIX => 3,
	OS_HP_UX => 4,
	OS_SUN_OS => 5,
	OS_NETWORK => 7,

	# command control
	EC_SUCCESS => 0,
	EC_ERROR => 1,
	EC_SVR_WRNG_ACCOUNT => 2,
	EC_CONN_FAILED => 3,
	EC_SCRIPT_LOAD_FAILED =>4,
	EC_SCRIPT_DUPLICATED =>5,
	EC_TIMEOUT=>6,

	# command type
	RUN_COMMAND => 1,
	RUN_FILE => 2,

	# connect mode
	CONNECT_ROOT => 1,
	CONNECT_MANAGEMENT => 2,

	# connect count
	MAX_CONNECT_COUNT => 3,

};

my $timeout = 30;
y $shell_prompt = '[#] $';
my $editor_prompt = '[>] $';
my $root_prompt = "";

my $hostname = "";
my $hostname = "";
my $port = 0;
my $os_type = 0;
my $conn_mode = 0;
my $account = "";
my $passwd = "";
my $root_passwd = "";
my $log_postfix = "";

my $su_change_str = "";
my $command_type = "";
my $connect_type = "";
my $conn;
my $command_str;
my $command_file;

&main();

sub main {
	my $exp = new Expect;
	my $is_error = "0";

	$exp->raw_pty(0);
	$exp->log_stdout(1);

	&init();
	&connect_server($exp);

	if( $connect_type == CONNECT_MANAGEMENT ) {
		&su_change($exp);
	}

	$exp->log_file($log_postfix);

	switch ( $command_type ) {

		case RUN_COMMAND	{ $is_error = &command_run($exp); }
		case RUN_FILE		{ $is_error = &file_run($exp); }
		else				{ &exit_proc("Unsupported command_type mode", EC_ERROR, 0, undef); }

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

	$hostname = $ARGV[0];           # target  adress
	$port = $ARGV[1];               # target service port
	$conn_mode = $ARGV[2];          # connection mode : SSH, Telnet, ETC
	$os_type = $ARGV[3];            # target os type : linux, Sun
	$account = $ARGV[4];            # connection account
	$passwd = $ARGV[5];             # connection account password

	$root_passwd = $ARGV[6];		# root password
	$root_prompt = $ARGV[7];		# root prompt
	$log_postfix = $ARGV[8];        # log file name
	$connect_type = $ARGV[9];		# connect type : root(1), management(2);
	$command_type = $ARGV[10];		# command type : Run command(1), Run file(2)

	if( $connect_type == CONNECT_MANAGEMENT ) {
		my @splitted_cmd = split(/;/, $ARGV[11]);
		$su_change_str = @splitted_cmd[0];
	}

	$command_str = $ARGV[11]; 		# command list
	$command_file = $ARGV[12];		# comand file name

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

		case CONN_TELNET	{ $conn = "telnet ".$hostname." ".$port; }
		case CONN_SSH		{ $conn = "ssh ".$account."@".$hostname." -p ".$port; }
		else				{ &exit_porc("Unsupported connection mode", EC_ERROR, 0, undef); }
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

	[qr 'yes/no' => sub{ my $sub = shift; $sub->send("yes\n"); exp_continue; }],
	[qr '[y]:' => sub{ my $sub = shift; printf "???\n"; $sub->send("y\n"); exp_continue; }],
	[qr 'ermission denied|onnection refused' => sub{ 
		&exit_proc("Failed to login", EC_SVR_WRNG_ACCOUNT, 0, undef); 
	}],
	[qr 'ogin incorrect|onnection closed' => sub{&exit_proc("Failed to login", EC_SVR_WRNG_ACCOUNT, 0, undef);}],
	[qr 'o route to host' => sub{&exit_proc("No route to host", EC_CONN_FAILED, 0, undef);}],
	[timeout => \&timeout_err],
	'-re', qr'[#>\$\]]\s?(\e\[\d*;?\d*m){0,1}\s?$');

	return;
}


sub su_change {

	my $exp = shift;
	if( $root_passwd ne "" ) {

		my $changeenv = 0;
		$exp->send("$su_change_str\n");
		$exp->expect($timeout,
			[qr 'ssword: $|ssword:$' => sub{ my $sub = shift;
				$sub->send("$root_passwd\n"); 
				exp_continue; 
			}],     
			[qr '\S\S: $|\S\S:$' => sub{ my $sub = shift;
				$sub->send("$root_passwd\n"); 
				exp_continue;
			}],
			[qr 'incorrect password' => sub{ $changeenv = 1;  return;}],
			[qr 'su: Sorry' => sub{ $changeenv = 1;  return;}],
			[timeout => \&timeout_err],
			'-re', qr "$root_prompt");

		# if changing succeed check a su_changed
		if($changeenv == 1) {
			&exit_proc("Failed to su change.", EC_SVR_WRNG_ACCOUNT, 1, $exp);
		}
	}

	return EC_SUCCESS;
}

sub command_run {

	#my ($exp) = shift;
	my ($exp) = @_;

	my @splitted_cmd = split(/;/, $command_str);

	$exp->log_file($log_postfix, "w");
	my $idx=1;
	if( $connect_type == CONNECT_MANAGEMENT ) {
		$idx = 1;

	} else {
		$idx = 0;
	}

	for(my $i = $idx; $i <= @splitted_cmd; $i++ ) {
		my $struct_cmd = $splitted_cmd[$i];
		$exp->send("$struct_cmd\n");

		sleep(1);

		$exp->log_file();
		$exp->expect(2, [qr '--More--'  => sub{
							$exp->send(" "); 
							exp_continue;
						}],
					'-re', qr "$root_prompt");
	}

	sleep(1);
	return EC_SUCCESS;

}

sub file_run {
	my $exp = shift;
	my $is_error = &command_run($exp); 

	if($is_error) {
		&exit_proc("Run command fail", EC_ERROR, 1, $exp);
	}

	open FILE, $command_file or &exit_proc("Unable to load given $command_file", EC_SCRIPT_LOAD_FAILED, 1, $exp);

	# read script file and run command
	while(my $line = <FILE>) {
		chomp($line);           # remove line eof('\0')
		$line =~s/\t/\cV\t/g;
		$exp->send("$line\n");
	}

	close(FILE);

	return EC_SUCCESS;
}


