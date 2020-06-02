
@echo off

Echo ***************************
echo "Backing up data...."

if exist q:\backup\mydocs.zip move q:\backup\mydocs.zip q:\backup\mydocs_old.zip

if exist q:\backup\workspace.zip move q:\backup\wks.zip q:\backup\wks.zip

c:\bin\pkzipc -add -excl=*.mp3 -dir=relative q:\backup\mydocs.zip "c:\Documents and Settings\a73552\My Documents\*.*"
c:\bin\pkzipc -add -dir=relative -exclude=.metadata q:\backup\wks.zip c:\dev\workspace\*.*
