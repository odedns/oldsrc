
rem set JAVA_HOME=c:\jbuilder4\jdk1.3
rem set JAVA=%JAVA_HOME%\bin\java
set JAVA=java

%JAVA% weblogic.Admin -url t3://localhost:7001 -username system -password weblogic SHUTDOWN

