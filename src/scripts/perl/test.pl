#!/usr/bin/perl
#
use warnings;
use strict;
#
#
print "What is your username?  ";  # print out the question
my $username;                      # "declare" the variable
$username = <STDIN>;               # ask for the username
chomp($username);                  # remove "new line"
print "Hello, $username.\n";       # print out the greeting

my $owner  = 'Elizabeth';
my $dog    = 'Rex';
my $amount = 12.5;
my $what   = 'dog food';

print "${owner}'s dog, $dog, ate $amount pounds of $what.\n";

my @queue;
unshift (@queue, "Customer 1"); # @queue is now ("Customer 1")
unshift (@queue, "Customer 2"); # @queue is now ("Customer 2" "Customer 1")
unshift (@queue, "Customer 3");
          # @queue is now ("Customer 3" "Customer 2" "Customer 1")
my $item = pop(@queue);         # @queue is now ("Customer 3" "Customer 2")
print "Servicing $item\n";       # prints:  Servicing Customer 1\n
$item = pop(@queue);            # @queue is now ("Customer 3")
print "Servicing $item\n";       # prints:  Servicing Customer 2\n


my @stack;
push(@stack, 7, 6, "go");   # @stack is now qw/7 6 go/
my $action = pop @stack;    # $action is "go", @stack is (7, 6)
my $value = pop(@stack) +
            pop(@stack);    # value is 6 + 7 = 13, @stack is empty


print "action is: $action value is: $value \n";

print "loop test: \n";
my $i =0;

while($i++ < 10 ) {
	print "i = $i\n";
}

for ($i=0; $i < 5; $i++ ) {

	print "i = $i\n";

}


my @collection = qw/hat shoes shirts shorts/;
foreach my $item (@collection) {
    print "$item\n";
}

sub HowdyEveryone {
   my($name1, $name2) = @_;
   return "Hello $name1 and $name2.\n" .
          "Where do you want to go with Perl today?\n";
}

sub foo {
	my $first = pop(@_);
	my $second = pop(@_);
	print "first: $first  second: $second\n";
}
		
print &HowdyEveryone("bart", "lisa");
&foo("oded", "nissan");

open(FD,"> plfile")  or die "Couldn't write to /etc/cantwrite: $!\n";  
print FD "Some bullshit 1\n";
print FD "Some bullshit 2\n";
print FD "Some bullshit 3\n";
print FD "Some bullshit 4\n";
close(FD);


open(FD2,"< plfile")  or die "Couldn't write to /etc/cantwrite: $!\n";  
my $line;                      # "declare" the variable
$line = <FD2>;      
print "line= $line\n";

while( <FD2>) {
		$line = $_;
		print $line;
}

#$line = $ENV(USERNAME);
#print "user $line \n";

print "enter num:";
my $num;
$num= <STDIN>;               # ask for the username

SWITCH: {
	if($num==1)  { print "one"; last SWITCH; };
	if($num==2)  { print "two"; last SWITCH; };
	if($num==3)  { print "three"; last SWITCH; };
	print "num = $num";
}



print "printing argv\n";
my $arg;
while($arg = shift(@ARGV)) {
	print "arg: $arg \n";
}


my @mylist = ('first', 'second', 'third');

foreach $arg (0.. $#mylist) {
		print "arg= $mylist[$arg]\n";
}


print "OS= $ENV{'OS'}\n";
print "mylist = @mylist\n";
push(@mylist,'four');
unshift(@mylist,'zero');
while(@mylist) {
	$arg = shift(@mylist);
	print "$arg\n";
}

my $name = gethostent();
print "my = $name\n";

