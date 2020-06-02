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

	echo "Usage build_ginshoo.sh [options]"
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
	echo " Starting ginshoo build 	"
	echo "===================================="
	cd $SOURCES
	PACKAGES="com/cellularmagic/ginshoo/constants com/cellularmagic/ginshoo/util com/cellularmagic/ginshoo/database com/cellularmagic/ginshoo/wrappers com/cellularmagic/ginshoo/datalayer /com/cellularmagic/ginshoo/ldap com/cellularmagic/ginshoo/struct com/cellularmagic/games/struct com/cellularmagic/games com/cellularmagic/ginshoo/moishcon com/cellularmagic/ginshoo/pushsystem com/cellularmagic/ginshoo/scheduler com/cellularmagic/ginshoo/filter com/cellularmagic/ginshoo/accessmanager com/cellularmagic/ginshoo/comInterface com/cellularmagic/ginshoo/menus com/cellularmagic/ginshoo/help com/cellularmagic/ginshoo/systemmenus com/cellularmagic/ginshoo/pointsconversion  com/cellularmagic/ginshoo/userdetails com/cellularmagic/ginshoo/usermanager/app com/cellularmagic/ginshoo/usermanager/app/logoff com/cellularmagic/ginshoo/topten com/cellularmagic/ginshoo/buddylist com/cellularmagic/ginshoo/gamesarena com/cellularmagic/ginshoo/gamesarena/app com/cellularmagic/ginshoo/gamesarena/entity com/cellularmagic/ginshoo/gamesarena/session"

	for i in $PACKAGES
		do
		echo "compiling package: $i"
		javac -d ../$OUTDIR $i/*.java
	done

	echo ""
	echo "===================================="
	echo " Finished ginshoo build 	"
	echo "===================================="


}



#-------------------------
# main 
#-------------------------

	parse_args $@
	print_vars
	build

