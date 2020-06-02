#!/usr/bin/perl
#
use warnings;
use strict;
use Getopt::Std;
#
#
#require "getopts.pl";


#	&Getopts('abi:');
	my %opts;
	&getopts('abi:',\%opts);
	#print "opt_a: $opt_a \n";
	#print "opt_b: $opt_b \n";
	#print "opt_i: $opt_i \n";
	print "opt_a: $opts{a} \n";
	print "opt_b: $opts{b} \n";
	print "opt_i: $opts{i} \n";
