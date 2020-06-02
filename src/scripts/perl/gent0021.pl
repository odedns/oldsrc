#!/usr/bin/perl
################################################################
# generate sql insert statements for tables.
# The program reads the tab delimited data files and 
# generates the appropriate sql statement.
# If no arguments are given the program processes all tables.
# If a table name arg is given only that table is processed.
# ##############################################################
use warnings;
use strict;

	my $tbname = "t0021";
	#print "generating table name : $tbname \n";
	my $fname = "$tbname" . "_data.txt";
	open(FD1,"< $fname")  or die "Couldn't read input file $!\n";  
	my $line;                      
	while( <FD1>) {
		$line = $_;
		#print "line = $line\n";
		my @list = split /\t/, $line;
		print "update $tbname set kod_kvutzat_sikun = $list[0] , kod_chipus_taarif=$list[1] where kod_taarif= $list[2];\n";
	}
		
