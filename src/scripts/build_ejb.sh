#!/bin/sh 
#############################################################################
# File  :   	build_ginshoo
# Date  :	
# Version:	
# Written By:	
# Purpose:	Build ginshoo project
############################################################################# 

GINSHOO_HOME=/home/ginshoo2/projects/ginshoo/1.00
EJB_DEPLOY=$GINSHOO_HOME/scripts/ejbdeploy.sh
EJBS="gamelet randlet createsession basesession creategame"

help() {
	echo "Usage $0 [beanname] "
	exit 1

}


# parse command line args.
parse_args() {


	if [ $# -ge 1 ] ;  then 
		if [ "$1" = "-help" -o "$1" = "-h" ]; then 
			help
			exit 1
		else 
			EJBS=$1
		fi
	fi
}


process() {


	for i in $EJBS 
		do
		echo "deploying bean: $i"
		echo exec $EJB_DEPLOY -bean $i -sources $GINSHOO_HOME/sources/com/cellularmagic/ginshoo/gamesarena/$i -package com/cellularmagic/ginshoo/gamesarena/$i-outdir $GINSHOO_HOME/classes
	done
}


parse_args $@
process
#exec $EJB_DEPLOY -bean gamelet -sources $HOME/projects/ginshoo/1.00/sources/com/cellularmagic/ginshoo/gamesarena/entity -package com/cellularmagic/ginshoo/gamesarena/entity -outdir $HOME/projects/ginshoo/1.00/classes
