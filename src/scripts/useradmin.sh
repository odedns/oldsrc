#!/bin/sh
#############################################################################
# File  :   	useradmin.sh
# Date  :	12/12/1999
# Version:	v01
# Written By:	Oded Nissan
# Purpose:
# 		
############################################################################# 



# print usage help.
help() {

	echo "Usage: useradmin.sh [options]"
	echo "       useradmin.sh -add | -update -file [filename] [options]"
	echo "       useradmin.sh -delete   -file [filename] [options]"
	echo " "
	echo "Where: "
	echo "	-help | -h  	print this help text."
	echo "	-user <uid> 	The userid to login to the application server"
	echo "	-password <pass> the password to login to the application server."
	echo "	-url <url> the url to the application server."
	echo "	-add		add users with user data from the input file."
	echo "	-file <filename> the file to read user data to be added from."
	echo "	-update 	update users with the data from the input file."
	echo "	-delete		delete the user specified by the -user option."
}

# parse command line args.
parse_args() {

USER="root"
PASS="root"
URL="t3://localhost:12347"
ACTION=""
INPUTFILE=""

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
			-delete)
				ACTION="DELETE"
				;;
			-update)
				ACTION="UPDATE"
				;;
			-user)
				shift
				USER=$1
				;;
			-password)
				shift
				PASS=$1
				;;
			-url)
				shift
				URL=$1
				;;
			-file)
				shift
				INPUTFILE=$1
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
	echo "ACTION 	= $ACTION"
	echo "USER      = $USER"
	echo "INPUTFILE	= $INPUTFILE"
}


# check_vars
# check if all required input arguments have been
# given on the command line.
check_vars() {

	print_vars
	if [ -z "$URL" -o -z "$USER" -o -z "$PASS" ]; then 
		help
		exit 1
	fi
	# if action is not file import
	# then we need a user id.
	if [ "$ACTION" = "UPDATE" -a -z "$INPUTFILE" ]; then 
		help
		exit 1
	fi

}


#-------------------------
# main 
#-------------------------

	. $HOME/inline/v01/scripts/inline_env.sh
	parse_args $@
java -Damdocs.system.home=$HOME/inline/v01/properties -Duseradmin.user=$USER -Duseradmin.password=$PASS -Duseradmin.url=$URL -Dauthentication.service.url=$URL -Duseradmin.action=$ACTION -Duseradmin.inputfile=$INPUTFILE amdocs.tmwww.batch.UserAdmin

