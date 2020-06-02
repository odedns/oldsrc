@echo off

@rem This script can be used to start Managed WebLogic Server. This script ensures that the CLASSPATH is set correctly. This script contains the following variables:
@rem
@rem JAVA_HOME      - Determines the version of Java used to start
@rem                  WebLogic Server. This variable must point to the
@rem                  root directory of a JDK or JRE installation. See
@rem                  the WebLogic platform support page
@rem                  (http://e-docs.bea.com/wls/platforms/index.html)
@rem                  for an up-to-date list of supported JVMs on Windows NT.
@rem
@rem When setting these variables below, please use short file names (8.3).
@rem To display short (MS-DOS) filenames, use "dir /x". File names with
@rem spaces will break this script.
@rem
@rem jDriver for Oracle users: This script assumes that native libraries
@rem required for jDriver for Oracle have been installed in the proper
@rem location and that your system PATH variable has been set appropriately.
@rem For additional information, refer to Installing and Setting up WebLogic
@rem Server (documentation/install/index.html in your local documentation set
@rem or on the Internet at http://e-docs.bea.com/wls/docs61/install/index.html).

SETLOCAL

cd ..\..

@rem Set user-defined variables.
set JAVA_HOME=C:\bea\jdk131

@rem Check that script is being run from the appropriate directory
if not exist lib\weblogic.jar goto wrongplace
goto checkJDK

:wrongplace
echo startManagedWebLogic.cmd must be run from the config\mydomain directory. 1>&2
goto finish

:checkJDK
if exist "%JAVA_HOME%/bin/javac.exe" goto runWebLogic
echo.
echo Javac wasn't found in directory %JAVA_HOME%/bin.
echo Please edit the startManagedWebLogic.cmd script so that the JAVA_HOME
echo variable points to the root directory of your JDK installation.
goto finish

@rem Set SERVER_NAME and ADMIN_URL, they must by specified before starting
@rem a managed server, the detail information can be found on the Internet at
@rem http://e-docs.bea.com/wls/docs61/install/index.html.
:runWebLogic
if "%1" == ""  goto checkEnvVars
if "%2" == "" goto usage
set SERVER_NAME="%1"
set ADMIN_URL="%2"
goto startWebLogic

:checkEnvVars
if "%SERVER_NAME%" == "" goto usage
if "%ADMIN_URL%" == ""  goto usage
set SERVER_NAME="%SERVER_NAME%"
set ADMIN_URL="%ADMIN_URL%"
goto startWebLogic

:usage
echo Need to set SERVER_NAME and ADMIN_URL environment variables or specify
echo them in command line:
echo Usage: startManagedWebLogic [SERVER_NAME] [ADMIN_URL]
echo for example:
echo startManagedWebLogic managedserver1 http://localhost:7001
goto finish

:startWebLogic
echo on
set PATH=.\bin;%PATH%

set JCON_HOME="c:\Program Files\Netdirect\Jdataconnect\jars"
set JCON_JARS=%JCON_HOME%\Jdata2_0.jar;%JCON_HOME\Jdata2_0OptionalAPI.jar
set CLASSPATH=.;.\lib\weblogic_sp.jar;.\lib\weblogic.jar;.\lib\ejb20.jar;%JCON_JARS%;c:\dev\classes
echo off
echo off

echo.
echo ***************************************************
echo *  To start WebLogic Server, use the password     *
echo *  assigned to the system user.  The system       *
echo *  username and password must also be used to     *
echo *  access the WebLogic Server console from a web  *
echo *  browser.                                       *
echo ***************************************************

@rem Set WLS_PW equal to your system password for no password prompt server startup.
set WLS_PW=weblogic

@rem Set Production Mode.  When set to true, the server starts up in production mode.  When
@rem set to false, the server starts up in development mode.  The default is false.
set STARTMODE=true

echo on
"%JAVA_HOME%\bin\java" -hotspot -ms64m -mx64m -classpath %CLASSPATH% -Dweblogic.Domain=mydomain -Dbea.home="C:\bea" -Dweblogic.management.password=%WLS_PW% -Dweblogic.ProductionModeEnabled=%STARTMODE% -Dweblogic.Name=%SERVER_NAME% -Dweblogic.management.server=%ADMIN_URL% "-Djava.security.policy==C:\bea\wlserver6.1/lib/weblogic.policy" weblogic.Server
goto finish


:finish
cd config\mydomain
ENDLOCAL
