#!/usr/bin/env perl

use strict;
use warnings;
use Expect;

my $host = $ARGV[0];
my $userid   = $ARGV[1];
my $userpass = $ARGV[2];

my $exp = Expect->new;
$exp->spawn("ssh", $userid . "@" . $host);
$exp->expect(
    10,
    [ qr/(yes\/no)/   => sub { my $exp = shift; $exp->send("yes\n"); exp_continue; } ],
    [ qr/password:/   => sub { my $exp = shift; $exp->send($userpass."\n"); exp_continue; } ],
    [ qr/$userid.*\$/ => sub { my $exp = shift; $exp->send("ls > ls.txt\n");} ],
    [ qr/Password:/   => sub { my $exp = shift; $exp->send($userpass."\n"); exp_continue; } ],
    [ qr/root.*#/     => sub { my $exp = shift; $exp->interact(); } ],
);

print "$0 exit\n";
