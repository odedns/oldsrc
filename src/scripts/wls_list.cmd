@echo off
rem ############################################################################
rem # File  :  wls_list.cmd
rem # Date  :  29-10-2001
rem # Version:	1.0
rem # Written By: Oded Nissan
rem # Purpose:	List WLS deployments
rem ########################################################################### 

set JAVA=java

%JAVA% weblogic.deploy list weblogic 

