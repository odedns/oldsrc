#!/bin/sh
#############################################################################
# File  :   	pwdispatch.sh
# Date  :	19/01/2000
# Version:	v01
# Written By:	Oded Nissan
# Purpose:
# 		
############################################################################# 


# print usage help.
help() {

	echo "Usage: $0 -user <uid> -password <pass> -url <url> -output <file>"
	echo ""
	echo "	-help | -h  	print this help text."
	echo "	-user <uid> 	The userid to login to the application server"
	echo "	-password <pass> the password to login to the application server."
	echo "	-url <url> the url to the application server."
	echo "	-output <dir> the name of the output directory."
}

# parse command line args.
parse_args() {

USER=""
PASS=""
DIR="./"
URL=""

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
			-output)
				shift
				DIR=$1
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
	echo "USER 	= $USER"
	echo "PASS      = $PASS"
	echo "URL       = $URL"
	echo "DIR= $DIR"
}


# check_vars
# check if all required input arguments have been
# given on the command line.
check_vars() {

	print_vars
	# if action is not file import
	# then we need a user id.
	if [ -z "$USER" -o -z "$PASS" -o -z "$URL" ]; then 
		echo "userid password or url not given."
		help
		exit 1
	fi

}


#-------------------------
# main 
#-------------------------

	. $HOME/inline/v01/scripts/inline_env.sh
	parse_args $@
	java -Damdocs.system.home=$HOME/inline/v01/properties -Dappserver.user=$USER -Dappserver.password=$PASS -Dappserver.url=$URL -Dauthentication.service.url=$URL -Doutput.dir=$DIR amdocs.tmwww.batch.PasswordDispatch 

