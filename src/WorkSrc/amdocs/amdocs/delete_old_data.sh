#!/bin/sh
###############################################################################
# File  :   	delete_old_data.sh
# Date  :	12-06-1999
# Version:	v01
# Written By:	Nirit Ben-Yoseph
# Purpose:	A shell script that runs the java delete_old_data.java process
############################################################################### 


# print usage help.
help() {

	echo "Usage: delete_old_data  bills | calls  -period <number> "
	echo " "
	echo "-help | -h print this help text."	
}


# parse command line args.
parse_args() {

DELETE_DATA=""
PERIOD=""

	if [ $# -lt 2 ] ;  
	then 
		help
		exit 1
	fi
	
	while [ $# != 0 ]
	do 
		case "$1" in 
		 	
		 	-help)
		 		help
		 		exit
		 		;;		 	
		 	-h)     
		 	        help
		 		exit
		 		;;		 	
		 	bills)
		 		DELETE_DATA=$1
		 		shift
		 		;;		 	
		 	calls) 
		 		DELETE_DATA=$1
		 		shift
		 		;;		 				 	
		 	-period)
		 		shift	 		
		 		PERIOD=$1
		 		shift
		 		;;
		 	
		 	*) 	help
		 		exit
		 		;;
		 	
		esac
	done	 		
	check_vars	 			
		
}


#check_vars
# check if all required input arguments have been
# given on the command line.
check_vars() {


	if [ -z "$DELETE_DATA" -o  -z "$PERIOD" ]; then 
		help
		exit 1
	fi
}


# debug print variables.
print_vars() {
	echo "DELETE_DATA   = $DELETE_DATA"
	echo "PERIOD        = $PERIOD"	
}

#-------------------------
# main 
#-------------------------

	. $HOME/inline/v01/scripts/inline_env.sh
	parse_args $@
	print_vars
	java -Damdocs.system.home=$HOME/inline/v01/properties -Ddelete.delete_data=$DELETE_DATA -Ddelete.period=$PERIOD amdocs.tmwww.batch.DeleteOldData
