rem ***echo off
rem ***cls
rem	*** set my path and restores the old path ***
if "%mypath%" == "mypath%%" goto cont
if NOT "%1" == "" goto more
rem	*** put the old path back ***
setenv mypath %%chgup
setenv path %%rep/%mypath%//
rem	*** remove the save path ***
setenv mypath=
goto fini
rem	*** path additions ***
:cont
if "%1" == "" goto def
rem	*** save new info ***
setenv mypath=;%1;
rem	*** add to the existing path ***
:add
path %path%%mypath%
goto fini
rem	*** add my path defaults ***
:def
rem	*** save default info ***
setenv mypath=;c:\dos;
goto add
rem	*** add more to path ***
:more
rem	*** add to save ***
setenv mypath=%mypath%%1;
rem	*** save new info ***
path %path%%1;
:fini
