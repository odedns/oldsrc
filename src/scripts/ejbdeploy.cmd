@echo off
rem ############################################################################
rem # File  :   	ejbdeploy
rem # Date  :	11-28-1999
rem #               05/01/2001
rem #               06/08/2001
rem # Version:	1.2
rem # Written By:	Oded Nissan
rem # Purpose:	Deploy an ejb bean using weblogic ejb server utilities.
rem ########################################################################### 

if "%1" == "" (
	echo "usage: ejbdeploy <bean_name> <outdir> <sourcedir> 
	goto end
)

set BEAN_NAME=%1
set OUTDIR=%2
set SOURCES=%3


echo SOURCES   = %SOURCES% 
echo OUTDIR = %OUTDIR% 
echo BEAN_NAME = %BEAN_NAME% 


rem set WEBLOGICHOME=c:\weblogic
rem set WLCLASSES=%WEBLOGICHOME%\lib\weblogicaux.jar;%WEBLOGICHOME%\lib;%WEBLOGICHOME%\classes\;%WEBLOGICHOME%
rem set JAVAC=javac -classpath %WLCLASSES%
rem set JAVA=java -classpath %WLCLASSES%
set JAVAC=javac
set JAVA=java 

echo building bean: %BEAN_NAME% ... 
rem # removing older classes.
mkdir %OUTDIR%\%BEAN_NAME%
pushd %OUTDIR%\%BEAN_NAME%
echo removing old jar file ...
del/s/q/f *.*

rem # compile classes
echo compiling classes ...
echo %JAVAC% -d . %SOURCES%\*.java
%JAVAC% -d . %SOURCES%\*.java

rem # copy XML files
echo creating deployment descriptor ...
mkdir META-INF
copy %SOURCES%\*.xml META-INF

echo creating jar file %BEAN_NAME%_tmp.jar ....
jar cv0f %BEAN_NAME%_tmp.jar *
rem del/s/q/f %BEAN_NAME%

rem # generate wrapper classes.
echo generating wrapper classes ....
%JAVA% weblogic.ejbc20  %BEAN_NAME%_tmp.jar ..\%BEAN_NAME%.jar
echo created jar file %BEAN_NAME%.jar ....
rem del %BEAN_NAME%_tmp.jar 

echo ""
echo Finished building bean: %BEAN_NAME%
echo ""
:end
popd
