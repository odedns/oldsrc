#!/bin/sh 
#############################################################################
# File  :   	prizeconversion_report.sh
# Date  :	
# Version:	
# Written By:	
# Purpose:	
############################################################################# 



# print usage help.
help() {

	echo "Usage prizeconversion_report.sh [options]"
	echo " "
	echo "	-help | -h  	print this help text."
	echo "	-fromdate <DD-MM-YYYY> begindate"
	echo "	-todate   <DD-MM-YYYY>       enddate."
}

# parse command line args.
parse_args() {
FROMDATE=""
TODATE=""


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
			-fromdate)
				shift
				FROMDATE=$1
				;;
			-todate)
				shift
				TODATE=$1
				;;
			*) 	echo "invalid arg: $1"
				;;
		esac
		shift
	done
	check_vars
}


# check_vars
# check if all required input arguments have been
# given on the command line.
check_vars() {


	if [ -z "$FROMDATE" -o -z "$TODATE" ]; then 
		help
		exit 1
	fi
}


#-------------------------
# main 
#-------------------------

	parse_args $@
	echo "fromdate = $FROMDATE"
	echo "todate = $TODATE"

