#!/bin/sh
#############################################################################
# File  :   	userview.sh
# Date  :	12/12/1999
# Version:	v01
# Written By:	Oded Nissan
# Purpose:
# 		
############################################################################# 



# print usage help.
help() {

	echo "Usage: userview.sh -userid [userid][options]"
	echo " "
	echo "Where: "
	echo "	-help | -h  		print this help text."
	echo "	-user <uid> 		The userid to login to the application server"
	echo "	-password <pass> 	the password to login to the application server."
	echo "	-url <url> 			the url to the application server."
	echo "	-userid <userid> 	userid to view current user's state"
}

# parse command line args.
parse_args() {

USER=""
PASS=""
URL=""
USERID=""

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
			-userid)
				shift
				USERID=$1
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
			*) 	echo "invalid arg: $1"
				;;
		esac
		shift
	done
	check_vars
}


# debug print variables.
print_vars() {
	echo "USERID    = $USERID"
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

}


#-------------------------
# main 
#-------------------------

	. $HOME/inline/v01/scripts/inline_env.sh
	parse_args $@
java -Damdocs.system.home=$HOME/inline/v01/properties -Duserview.user=$USER -Duserview.password=$PASS -Duserview.url=$URL -Dauthentication.service.url=$URL -Duserview.userid=$USERID amdocs.tmwww.batch.UserView

