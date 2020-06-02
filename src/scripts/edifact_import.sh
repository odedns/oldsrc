#!/bin/sh
#############################################################################
# File  :   	edifact_import
# Date  :	27/01/2000
# Version:	v01
# Written By:	Oded Nissan
# Purpose:
# 		
############################################################################# 

USER=""
PASS=""
INDIR=""
OUTDIR=""
URL=""



# print usage help.
help() {

	echo "Usage: $0 -user <uid> -password <pass> -url <url> -input <dir> -output <dir>"
	echo ""
	echo "	-help | -h  	print this help text."
	echo "	-user <uid> 	The userid to login to the application server"
	echo "	-password <pass> the password to login to the application server."
	echo "	-url <url> the url to the application server."
	echo "	-input <dir> the name of the input directory."
	echo "	-output <dir> the name of the output directory."
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
			-input)
				shift
				INDIR=$1
				;;
			-output)
				shift
				OUTDIR=$1
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
	echo "INDIR= $INDIR"
	echo "OUTDIR= $OUTDIR"
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
	if [ -z "$INDIR" -o -z "$OUTDIR" ]; then 
		echo "input directory or output directory not given."
		help
		exit 1
	fi

}



process() {

FILE=""
SIZE=""

	cd $INDIR
	for line in `cat $INDIR.lst`; do 
		if [ -z "$FILE" ]; then
			FILE=$line
		else 
			SIZE=$line
			NSIZE=`ls -l $FILE | awk '{ printf "%010d", $5}'`
			FILE=""
			if [ $NSIZE != $SIZE ]; then
				echo "size for file $FILE is wrong !"
			fi

		fi
	done
	cp *.gz $OUTDIR
	cd $OUTDIR
	gzip -fd *.gz
	BILL_RUN=`basename $INDIR`

}

#-------------------------
# main 
#-------------------------

	. $HOME/inline/v01/scripts/inline_env.sh
	parse_args $@
	process
	echo java -Dappserver.user=$USER -Dappserver.password=$PASS -Dappserver.url=$URL -Dauthentication.service.url=$URL -Doutput.dir=$DIR -Dbillrun.num=$BILL_RUN amdocs.tmwww.batch.PasswordDispatch *.dat 

