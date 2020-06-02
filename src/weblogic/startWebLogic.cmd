@echo off
@rem $Id: //depot/dev/src510p/startWebLogic.cmd#4 $

@rem This script can be used to start WebLogic Server. It contains 
@rem following variables: 
@rem 
@rem JAVA_HOME      - Determines the version of Java used to start  
@rem                  WebLogic Server. This variable must point to the 
@rem                  root directory of a JDK or JRE installation. See 
@rem                  the WebLogic platform support page 
@rem                  (http://www.weblogic.com/docs51/platforms/index.html)
@rem                  for an up-to-date list of supported JVMs on Windows NT. 
@rem                  Because of packaging differences between versions of Java, 
@rem                  this script will not work with a JRE 1.1.7 installation.
@rem PRE_CLASSPATH  - Use this variable to prepend jar files or directories to 
@rem                  the WEBLOGIC_CLASSPATH.
@rem POST_CLASSPATH - Use this variable to append jar files or directories to 
@rem                  the end of the WEBLOGIC_CLASSPATH.
@rem 
@rem When setting these variables below, please use short file names (8.3).  
@rem To display short (MS-DOS) filenames, use "dir /x". File names with 
@rem spaces will break this script. 
@rem 
@rem jDriver for Oracle users: This script assumes that native libraries 
@rem required for jDriver for Oracle have been installed in the proper 
@rem location and that your system PATH variable has been set appropriately. 
@rem For additional information, refer to Installing and Setting up WebLogic 
@rem Server (/install/index.html in your local documentation set or on the 
@rem Internet at http://www.weblogic.com/docs51/install/index.html). 

SETLOCAL

@rem Set user-defined variables. Note that JAVA_HOME will be taken 
@rem from the environment, if it is already defined.
set JAVA_HOME=c:\jbuilder4\jdk1.3
set WL_HOME=c:\weblogic
set ORACLE_HOME=c:\oracle\ora81
set PATH=%WL_HOME%\bin\oci816_8;%ORACLE_HOME%\bin;%PATH%

if "%JAVA_HOME%" == "" set JAVA_HOME=.\jre1_2\jre

set PRE_CLASSPATH=c:\dev\classes
set POST_CLASSPATH=

@rem Check that script is being run from WebLogic root directory
#if not exist license\WebLogicLicense.xml goto wrongplace
#if not exist weblogic.policy goto wrongplace
goto checkJRE

:wrongplace
echo startWebLogic.cmd: must be run from the WebLogic installation directory. 1>&2
goto finish

:checkJRE
if exist %JAVA_HOME%\lib\nul goto whichJRE
if exist %JAVA_HOME%\classes\nul goto whichJRE
echo.
echo The JRE wasn't found in directory %JAVA_HOME%.
echo Please edit the startWebLogic.cmd script so that the JAVA_HOME
echo variable points to the root directory of your Java installation.
goto finish

:whichJRE
echo on
set PATH=.\bin;%PATH%
@if exist %JAVA_HOME%\Bin\JView.exe goto runWebLogicJview
@if exist %JAVA_HOME%\..\JView.exe goto runWebLogicJview
@if exist %JAVA_HOME%\lib\classes.zip goto setJava117
set JAVA_CLASSPATH=%WL_HOME%\lib\weblogic510sp9boot.jar;%WL_HOME%\classes\boot;%WL_HOME%\eval\cloudscape\lib\cloudscape.jar
goto runWebLogicJava

:setJava117
set JAVA_CLASSPATH=%JAVA_HOME%\lib\classes.zip;%WL_HOME%\lib\weblogic510sp9boot.jar;%WL_HOME%\classes\boot;%WL_HOME%\eval\cloudscape\lib\cloudscape.jar
goto runWebLogicJava

:runWebLogicJava
set WEBLOGIC_CLASSPATH=%WL_HOME%\license;%WL_HOME%\lib\weblogic510sp9.jar;%WL_HOME%\classes;%WL_HOME%\lib\weblogicaux.jar

if "%PRE_CLASSPATH%" NEQ "" set WEBLOGIC_CLASSPATH=%PRE_CLASSPATH%;%WEBLOGIC_CLASSPATH%

if "%POST_CLASSPATH%" NEQ "" set WEBLOGIC_CLASSPATH=%WEBLOGIC_CLASSPATH%;%POST_CLASSPATH%
%JAVA_HOME%\bin\java -ms64m -mx64m -classpath %JAVA_CLASSPATH% -Dweblogic.system.name=..\oded -Dweblogic.class.path=%WEBLOGIC_CLASSPATH% -Dweblogic.home=%WL_HOME% -Djava.security.manager -Djava.security.policy==.\weblogic.policy weblogic.Server
goto finish

:runWebLogicJview
set CLASSPATH=%windir%\Java\Classes\classes.zip

if "%PRE_CLASSPATH%" NEQ "" set CLASSPATH=%CLASSPATH%;%PRE_CLASSPATH%

set CLASSPATH=%CLASSPATH%;.\license;.\lib\weblogic510sp9boot.jar;.\classes\boot;.\lib\weblogic510sp9.jar;.\classes;.\lib\weblogicaux.jar;.\myserver\serverclasses;.\eval\cloudscape\lib\cloudscape.jar;.\lib\rmiForMs.zip

if "%POST_CLASSPATH%" NEQ "" set CLASSPATH=%CLASSPATH%;%POST_CLASSPATH%

if not exist %JAVA_HOME%\bin\jview.exe goto winntJview
%JAVA_HOME%\bin\jview /d:weblogic.system.disableWeblogicClassPath=true weblogic.Server
goto finish

:winntJview
%JAVA_HOME%\..\..\winnt\jview /d:weblogic.system.disableWeblogicClassPath=true weblogic.Server

:finish
ENDLOCAL
