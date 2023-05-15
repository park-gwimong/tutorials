#!/usr/bin/perl
use Net::SSH::Expect;
 
#
# You can do SSH authentication with user-password or without it.
#
 
# Making an ssh connection with user-password authentication
# 1) construct the object
my $ssh = Net::SSH::Expect->new (
    host => "localhost", 
    password=> 'test123', 
    user => 'test', 
    raw_pty => 1
);
 
# 2) logon to the SSH server using those credentials.
# test the login output to make sure we had success
my $login_output = $ssh->login();

print ($login_output);

if ($login_output !~ /Welcome/) {
    die "Login has failed. Login output was $login_output";
}

# - now you know you're logged in - #
 
# Starting ssh without password
# 1) run the constructor
#my $ssh = Net::SSH::Expect->new (
#    host => "myserver.com", 
#    user => 'bnegrao', 
#    raw_pty => 1
#);

# 2) now start the ssh process
#$ssh->run_ssh() or die "SSH process couldn't start: $!";
 
# 3) you should be logged on now. Test if you received the remote prompt:
#($ssh->read_all(2) =~ />\s*\z/) or die "where's the remote prompt?"
 
# - now you know you're logged in - #
 
# disable terminal translations and echo on the SSH server
# executing on the server the stty command:
#$ssh->exec("stty raw -echo");

# runs arbitrary commands and print their outputs 
# (including the remote prompt comming at the end)
my $ls = $ssh->exec("ls -l");
print($ls);

my $who = $ssh->exec("who");
print ($who);
 
# When running a command that causes a huge output,
# lets get the output line by line:
#$ssh->send("find /");   # using send() instead of exec()

my $line;
# returns the next line, removing it from the input stream:
while ( defined ($line = $ssh->read_line()) ) {
    print $line . "\n";  
}
 
# take a look in what is immediately available on the input stream
print $ssh->peek(0);    # you'll probably see the remote prompt
 
# the last read_line() on the previous loop will not include the
# remote prompt that appears at the end of the output, because the prompt
# doesn't end with a '\n' character. So let's remove the remainder
# prompt from the input stream:
$ssh->eat($ssh->peek(0));  # removes whatever is on the input stream now
 
# We can also iterate over the output in chunks,
# printing everything that's available at each 1 second:
#$ssh->send ("find /home");

my $chunk;
while ($chunk = $ssh->peek(1)) { # grabs chunks of output each 1 second
    print $ssh->eat($chunk);
}
 
# Now let's run an interactive command, like passwd.
# This is done combining send() and waitfor() methods together:
#$ssh->send("passwd");
#$ssh->waitfor('password:\s*\z', 1) or die "prompt 'password' not found after 1 second";
#$ssh->send("curren_password");
#$ssh->waitfor(':\s*\z', 1) or die "prompt 'New password:' not found";
#$ssh->send("new_password");
#$ssh->waitfor(':\s*\z', 1) or die "prompt 'Confirm new password:' not found";
#$ssh->send("new_password");

# check that we have the system prompt again.
#my ($before_match, $match) = $ssh->waitfor('>\s*\z', 1);  # waitfor() in a list context
#die "passwd failed. passwd said '$before_match'." unless ($match);
 
# closes the ssh connection
$ssh->close();
