#!/bin/sh
###############################################################################
# File  :   	serverStatus.sh
# Date  :	12-06-1999
# Version:	v01
# Written By:	Oded Nissan
# Purpose:	Display server status.
############################################################################### 

#-------------------------
# main 
#-------------------------

	if [ $# -lt 1 ] ;  
	then 
		SERVER_NAME="startInline.sh"
	else 
		SERVER_NAME=$1
	fi

	LINE=`ps -fe | grep -v grep | grep -v $0 | grep $SERVER_NAME | grep $USER`
	PPID=`echo $LINE | cut -f2 -d" "`
	if [ -z "$PPID" ]; then
		exit 1
	else 
		exit 0
	fi
