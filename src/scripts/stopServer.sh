#!/bin/sh
###############################################################################
# File  :   	stopServer.sh
# Date  :	12-06-1999
# Version:	v01
# Written By:	Oded Nissan
# Purpose:	Shut down a process by sending it a signal.
############################################################################### 

#-------------------------
# main 
#-------------------------

	if [ $# -lt 1 ] ;  
	then 
		echo "Usage: stopServer  <servername> "
		exit 1
	fi
	SERVER_NAME=$1

	LINE=`ps -fe | grep -v grep | grep -v $0 | grep $SERVER_NAME | grep $USER`
	PPID=`echo $LINE | cut -f2 -d" "`
	if [ -z "$PPID" ]; then
		echo "Server Process is already down !"
		exit 1
	fi

	LINE=`ps -fe | grep java | grep $PPID`
	PID=`echo $LINE | cut -f2 -d" "`
	echo "shutting down server: " $SERVER_NAME "pid : " $PID " PPID = " $PPID
	kill -9 $PID
	kill -9 $PPID
