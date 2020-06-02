@echo off
rem ############################################################################
rem # File  :  wls_redeploy.cmd
rem # Date  :  29-10-2001
rem # Version:	1.0
rem # Written By: Oded Nissan
rem # Purpose:	ReDeploy a bean using WLS Hot Deploy utility.
rem ########################################################################### 

if "%1" == "" (
	echo "usage: wls_redeploy <bean_name> <jarname>
	goto end
)

set BEAN_NAME=%1
set JAR_NAME=%2

rem echo BEAN_NAME = %BEAN_NAME%
rem echo JAR_NAME = %JAR_NAME%

set JAVA=java

%JAVA% weblogic.deploy undeploy weblogic %BEAN_NAME% 
%JAVA% weblogic.deploy update weblogic %BEAN_NAME% %JAR_NAME%

:end

