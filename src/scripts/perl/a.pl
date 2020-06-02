#!/usr/bin/perl
#
use warnings;
use strict;


my $a = "foo";

print "$a" . "bar\n";


my $i;
my @v = ( 10, 20,30);

foreach $i (@v) {
	print "v = $i\n";
}


for($i=0; $i < @v; $i++ ) {
	print "v[$i] = $v[$i]\n";

}


$i = 0;
while($i < @v) {
	print "v[$i] = $v[$i]\n";
	++$i;

}


my $s = "This is line with break\n";

print "$s\n";
chomp($s);
print "$s\n";
