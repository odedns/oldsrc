#!/usr/bin/perl
#
use warnings;
use strict;



sub readString {
	my $prompt = @_;
	print $prompt;
	my $val = <STDIN>;
	return $val;
}

sub readInt {
	my $prompt = @_;
	print $prompt;
	my $val = <STDIN>;
	if ( $val=~ /[0-9]*/ ) {
		print "valid number $val\n";
	} else {
		print "invalid number $val\n";
	}
}


my $val = &readString("enter String ");

print "readString: $val\n";

&readInt("Enter int:");
