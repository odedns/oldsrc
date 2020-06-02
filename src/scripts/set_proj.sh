#!/bin/sh
#############################################################################
# File  :   	set_proj.sh
# Date  :	
# Version:	v01
# Written By:	Oded Nissan
# Purpose:
# 		
############################################################################# 
# print usage help.


PROJECT=""
VERSION=""
GINSHOO_HOME=/home/ginshoo2

help() {

	echo "Usage: $0 -project <name> -version <name>"
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
	echo "VERSION   = $VERSION"
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

	GINSHOO_CLASSES=$HOME/projects/$PROJECT/$VERSION/classes:$GINSHOO_HOME/projects/$PROJECT/$VERSION/classes
	CLASSPATH=$GINSHOO_CLASSES:$CLASSPATH
	export CLASSPATH
	echo "CLASSPATH = $CLASSPATH"
}

#-------------------------
# main 
#-------------------------

	parse_args $@
	process
	
