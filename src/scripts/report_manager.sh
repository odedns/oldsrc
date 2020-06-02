#!/bin/sh


OUTPUT_DIR="."
FROM_DATE="011900"
TO_DATE="129999"


# print usage help.
help() {

	echo "Usage: reportManager.sh [-help|-h] [-dir <output directory> ] [-from <from date >] [ -to <to date >]"
	echo " "
	echo "Where: "
	echo "	-help | -h  print this help text."
	echo "	-dir 		output directory."
	echo "	-from 		The report period start date ( format MMYYYY)."
	echo "	-to 		The report period end date ( format MMYYYY)."
}

# debug print variables.
print_vars() {
	echo "OUTPUT_DIR	= " $OUTPUT_DIR
	echo "FROM_DATE     = " $FROM_DATE
	echo "TO_DATE		= " $TO_DATE
}


# check_vars
# check if all required input arguments have been
# given on the command line.
check_vars() {

	print_vars
	if [ -z "$OUTPUT_DIR" -o -z "$FROM_DATE" -o -z "$TO_DATE" ]; then 
		help
		exit 1
	fi

}

parse_args() {

	while [ $# != 0 ] 
	do
		case "$1" in 
			-help) 
				help;
				exit;;
			-h)
				help;
				exit;;
			-dir)
				shift
				OUTPUT_DIR=$1
				;;
			-from)
				shift
				FROM_DATE=$1
				;;
			-to)
				shift
				TO_DATE=$1
				;;
			*) 	echo "invalid arg: $1"
				;;
		esac
		shift
	done
	check_vars
}
#-------------------------
# main 
#-------------------------

. $HOME/inline/v01/scripts/inline_env.sh
parse_args $@

java -Damdocs.system.home=/tmbchome/ccwww/cctm/inline/v01/properties/server amdocs.tmwww.gn.ReportManager -out $OUTPUT_DIR -from $FROM_DATE -to $TO_DATE
