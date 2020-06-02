
@echo off
set WL_HOME=c:\bea\wlserver6.1
rem set JAVA="c:\Program Files\javasoft\jre\1.2\bin\java"
set JAVA=java
%JAVA% -cp %WL_HOME%\samples\eval\cloudscape\lib\tools.jar;%WL_HOME%\samples\eval\cloudscape\lib\cloudscape.jar COM.cloudscape.tools.cview 
