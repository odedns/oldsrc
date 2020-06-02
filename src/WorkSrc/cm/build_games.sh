#!/bin/sh 
#############################################################################
# File  :   	build_ginshoo
# Date  :	
# Version:	
# Written By:	
# Purpose:	Build ginshoo project
############################################################################# 



# print usage help.
help() {

	echo "Usage $0  [options]"
	echo " "
	echo "	-help | -h  	print this help text."
	echo "	-outdir <dir>		output directory."
	echo "	-sources <dir>	directory to take sources from."
}

# parse command line args.
parse_args() {

OUTDIR="/."
SOURCES=""

	if [ $# -lt 2 ] ;  then 
		help
		exit 1
	fi
	while [ $# != 0 ] 
	do
		case "$1" in 
			-help) 
				help;
				exit;;
			-h)
				help;
				exit;;
			-outdir)
				shift
				OUTDIR=$1
				;;
			-sources)
				shift
				SOURCES=$1
				;;
			*) 	echo "invalid arg: $1"
				;;
		esac
		shift
	done
	check_vars
}


# debug print variables.
print_vars() {
	echo "OUTDIR 	= $OUTDIR"
	echo "SOURCES   = $SOURCES"
}


# check_vars
# check if all required input arguments have been
# given on the command line.
check_vars() {


	if [ -z "$OUTDIR" ]; then 
		help
		exit 1
	fi
}


build() {

	rm -rf $OUTDIR/*
	echo ""
	echo "===================================="
	echo " Starting ginshoo games build 	"
	echo "===================================="
	cd $SOURCES
	PACKAGES="net/ginshoo/games/gameslib net/ginshoo/games/sqlib net/ginshoo/games/beads"

	for i in $PACKAGES
		do
		echo "compiling package: $i"
		javac -d ../$OUTDIR $i/*.java
	done

	echo ""
	echo "===================================="
	echo " Finished ginshoo games build 	"
	echo "===================================="


}



#-------------------------
# main 
#-------------------------

	parse_args $@
	print_vars
	build

