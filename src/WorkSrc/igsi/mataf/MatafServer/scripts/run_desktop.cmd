@echo off
@rem  Run the desktop client.
set JAVAHOME=d:\j2sdk1.4.2\bin
set JARDIR=jars
set LIBDIR=lib
set APP_JARS=%JARDIR%\DesktopProject.jar;%JARDIR%\MatafClient.jar;%JARDIR%\Mataf_GUI.jar;%JARDIR%\MatafServer.jar;%JARDIR%\Bridge2Java.jar;%JARDIR%\Ldpjava.jar;
set DSE_JARS=%JARDIR%\com.ibm.mq.jar;%JARDIR%\com.ibm.mqbind.jar %JARDIR%\comm.jar;%JARDIR%\connector.jar;%JARDIR%\dseb.jar;%JARDIR%\dsecsm.jar;%JARDIR%\dsecss.jar;%JARDIR%\dsed.jar;%JARDIR%\dsed4.jar;%JARDIR%\dseflp.jar;%JARDIR%\dsefmpsi.jar;%JARDIR%\dsegb.jar;%JARDIR%\dsejcon.jar;%JARDIR%\dsemql.jar;%JARDIR%\dsemql4.jar;%JARDIR%\dsemqsvc.jar;%JARDIR%\dsesncsi.jar;%JARDIR%\dsesym.jar;%JARDIR%\jhall.jar;%JARDIR%\sslight.jar;%JARDIR%\xerces_v5.jar;%JARDIR%\xerces.jar;%JARDIR%\log4j-1.2.4.jar;%JARDIR%\jhall.jar;%JARDIR%\junit.jar%JARDIR%\dsefmpsi.jar;%JARDIR%\kunststoff.jar;%JARDIR%\OceanLNF.jar

set PATH=%LIBDIR%;%PATH%
set CP="%APP_JARS%;%DSE_JARS%"
%JAVAHOME%\java -classpath %CP% mataf.desktop.RunDesktop %1 %2 %3 %4 %5 %6 %7 %8 %9
