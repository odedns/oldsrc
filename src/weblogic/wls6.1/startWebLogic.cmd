@echo off

@rem This script can be used to start WebLogic Server. This script ensures that
@rem the server is started using the config.xml file found in this directory and
@rem that the CLASSPATH is set correctly. This script contains the following variables:
@rem
@rem JAVA_HOME      - Determines the version of Java used to start
@rem                  WebLogic Server. This variable must point to the
@rem                  root directory of a JDK installation and will be set
@rem                  for you by the WebLogic Server installer. Note that
@rem                  this script uses the hotspot VM to run WebLogic Server.
@rem                  If you choose to use a JDK other than the one
@rem                  included in the disribution, make sure that the JDK
@rem                  includes the hotspot VM. See the WebLogic platform support
@rem                  page (http://e-docs.bea.com/wls/platforms/index.html)
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
@rem Server (http://e-docs.bea.com/wls/docs61/install/index.html).

SETLOCAL

cd ..\..

@rem Set user-defined variables.
set JAVA_HOME=C:\bea\jdk131

@rem Check that script is being run from the appropriate directory
if not exist lib\weblogic.jar goto wrongplace
goto checkJDK


:wrongplace
echo startWebLogic.cmd must be run from the config\mydomain directory. 1>&2
goto finish

:checkJDK
if exist "%JAVA_HOME%/bin/javac.exe" goto runWebLogic
echo.
echo Javac wasn't found in directory %JAVA_HOME%/bin.
echo Please edit the startWebLogic.cmd script so that the JAVA_HOME
echo variable points to the root directory of your JDK installation.
goto finish

:runWebLogic
echo on
set PATH=.\bin;%PATH%

set JCON_HOME="c:\Program Files\Netdirect\Jdataconnect\jars"
set JCON_JARS=%JCON_HOME%\Jdata2_0.jar;%JCON_HOME\Jdata2_0OptionalAPI.jar
set CLASSPATH=.;.\lib\weblogic_sp.jar;.\lib\weblogic.jar;.\lib\ejb20.jar;%JCON_JARS%;c:\dev\classes
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
"%JAVA_HOME%\bin\java" -hotspot -ms64m -mx64m -classpath %CLASSPATH% -Dweblogic.Domain=mydomain -Dweblogic.Name=myserver "-Dbea.home=C:\bea" -Dweblogic.management.password=%WLS_PW% -Dweblogic.ProductionModeEnabled=%STARTMODE% "-Djava.security.policy==C:\bea\wlserver6.1/lib/weblogic.policy" -Dweblogic.management.discover=false weblogic.Server
goto finish


:finish
cd config\mydomain
ENDLOCAL
