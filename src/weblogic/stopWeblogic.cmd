
set JAVA_HOME=c:\jbuilder4\jdk1.3
set JAVA=%JAVA_HOME%\bin\java
set WL_HOME=c:\weblogic

%JAVA% -cp %WL_HOME%\classes;%WL_HOME%\lib\weblogicaux.jar weblogic.Admin t3://localhost:8050 SHUTDOWN system system01

