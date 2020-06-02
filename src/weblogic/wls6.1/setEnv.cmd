@echo on
@rem This script can be used to set up your environment for your
@rem own development work with WebLogic Server. This script ensures 
@rem that the CLASSPATH variable is set to include the WebLogic 
@rem Server classes and required JDK classes. It also ensures that 
@rem the bin directory of the WebLogic Server and JDK distributions 
@rem are added to the PATH variables. This script contains the 
@rem following variables: 
@rem 
@rem WL_HOME   - This must point to the root directory of your WebLogic 
@rem             installation. 
@rem JAVA_HOME - Determines the version of Java used to compile  
@rem             and run examples. This variable must point to the 
@rem             root directory of a complete JDK installation. See 
@rem             the WebLogic platform support page 
@rem             (http://e-docs.bea.com/wls/platforms/index.html)
@rem             for an up-to-date list of supported JVMs on Windows NT. 
@rem 
@rem When setting these variables below, please use short file names(8.3). 
@rem To display short (MS-DOS) filenames, use "dir /x". File names with 
@rem spaces will break this script.
@rem 
@rem jDriver for Oracle users: This script assumes that native libraries 
@rem required for jDriver for Oracle have been installed in the proper 
@rem location and that your system PATH variable has been set appropriately. 
@rem For additional information, refer to Installing and Setting up WebLogic 
@rem Server (/install/index.html in your local documentation set or on the 
@rem Internet at http://e-docs.bea.com/wls/docs61/install/index.html). 

@rem Set user-defined variables.
set WL_HOME=C:\bea\wlserver6.1
set JAVA_HOME=C:\bea\jdk131

@if exist %WL_HOME%\lib\weblogic.jar goto checkJava
@echo.
@echo The WebLogic Server wasn't found in directory %WL_HOME%.
@echo Please edit the setExamplesEnv.cmd script so that the WL_HOME
@echo variable points to the WebLogic Server installation directory.
@echo Your environment has not been set.
@goto finish

:checkJava
@if exist %JAVA_HOME%\bin\java.exe goto setEnv
@echo.
@echo The JDK wasn't found in directory %JAVA_HOME%.
@echo Please edit the setEnv.cmd script so that the JAVA_HOME
@echo variable points to the location of your JDK.
@echo Your environment has not been set.
@goto finish

:setEnv
set CLASSPATH=%JAVA_HOME%\lib\tools.jar;%WL_HOME%\lib\weblogic_sp.jar;%WL_HOME%\lib\weblogic.jar;%CLASSPATH%

set PATH=%WL_HOME%\bin;%JAVA_HOME%\bin;%PATH%
@echo.
@echo Your environment has been set.

:finish
