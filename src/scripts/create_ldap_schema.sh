#!/bin/sh
#############################################################################
# File  :   	userview.sh
# Date  :	12/12/1999
# Version:	v01
# Written By:	Oded Nissan
# Purpose:
# 		
############################################################################# 

CLASSPATH=$HOME/inline/v01/classes/:$CLASSPATH


java -Damdocs.system.home=$HOME/inline/v01/properties
amdocs.uamsimpl.server.storage.ldap.utils.CreateJavaSchema -sn "-ncn=Directory
Manage" -p admin117

