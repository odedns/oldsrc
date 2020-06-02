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

# list of tables;
my @tables = ( 'degem' , 'dpt1075', 'degem_mutzarim', 'dpt0220',
			 'dpt1076', 'dpt1046');
my $arg;

if($arg = shift(@ARGV)) {
	&generate($arg);
} else {

	foreach $arg (@tables) {
			#print "arg= $arg\n";
			&generate($arg);
	}
}


# generate the sql insert statements per table.
# the function is called with a table name argument.
sub generate {
	my $tbname = pop(@_);
	#print "generating table name : $tbname \n";
	my $fname = "$tbname" . "_data.txt";
	open(FD1,"< $fname")  or die "Couldn't read input file $!\n";  
	my $line;                      
	while( <FD1>) {
		$line = $_;
		$line =~s/\t/,/g;
		print "insert into $tbname values ( $line );\n";
	}
}
		
