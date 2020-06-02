#!/bin/sh
#############################################################################
# File  :   	create_proj.sh
# Date  :	12/12/1999
# Version:	v01
# Written By:	Oded Nissan
# Purpose:
# 		
############################################################################# 
# print usage help.


PROJECT=""
VERSION=""

help() {

	echo "Usage: $0 -project <name>"
	echo ""
	echo "	-help | -h  	print this help text."
	echo "	-project <name> project name."
	echo "	-version <name> version name."
}

# parse command line args.
parse_args() {


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
			-project)
				shift
				PROJECT=$1
				;;
			-version)
				shift
				VERSION=$1
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
	echo "PROJECT 	= $PROJECT"
}


# check_vars
# check if all required input arguments have been
# given on the command line.
check_vars() {

	print_vars
	# if action is not file import
	# then we need a user id.
	if [ -z "$PROJECT" ]; then 
		echo "project name not given."
		help
		exit 1
	fi

}



process() {

	echo mkdir $PROJECT
	mkdir $PROJECT
	echo mkdir $PROJECT/$VERSION
	mkdir $PROJECT/$VERSION
	echo mkdir $PROJECT/$VERSION/docs
	mkdir $PROJECT/$VERSION/docs
	echo mkdir $PROJECT/$VERSION/scripts
	mkdir $PROJECT/$VERSION/scripts
	echo mkdir $PROJECT/$VERSION/sources
	mkdir $PROJECT/$VERSION/sources
	echo mkdir $PROJECT/$VERSION/classes
	mkdir $PROJECT/$VERSION/classes
	echo mkdir $PROJECT/$VERSION/properties
	mkdir $PROJECT/$VERSION/properties
	echo mkdir $PROJECT/$VERSION/servlets
	mkdir $PROJECT/$VERSION/servlets
	echo "done ...."

}

#-------------------------
# main 
#-------------------------

	parse_args $@
	process
