rem	settest.bat
rem	for testing Setenv.Com
rem	setenv prompt 1.p
rem	environment space
set name=
set
rem	variable not found
setenv name
if errorlevel 8 goto a0
goto error
:a0
rem	answer	name=%name%
rem	variable created
setenv name john
if errorlevel 3 goto a1
goto error
:a1
rem	answer	name=%name%
rem	variable updated
setenv name test
if errorlevel 5 goto a2
goto error
:a2
rem	answer	name=%name%
rem	variable erased
setenv name
if errorlevel 2 goto a3
goto error
:a3
rem	answer	name=%name%
rem	drive invalid
setenv name %%-9
if errorlevel 9 goto b0
goto error
:b0
rem	answer	name=%name%
rem	drive offset
setenv name %%+1:
if errorlevel 5 goto b1
goto error
:b1
rem	answer	name=%name%
rem	drive offset / drive invalid
setenv name %%-1:
if errorlevel 9 goto b2
if errorlevel 5 goto b2
goto error
:b2
rem	answer	name=%name%
rem	drive offset for current drive
setenv name %%+0:
if errorlevel 5 goto b3
goto error
:b3
rem	answer	name=%name%
rem	DOS version Major and Minor
setenv name MS-DOS V%%dosv.%%dosm
if errorlevel 5 goto b4
goto error
:b4
rem	answer	name=%name%
rem	current working directory
setenv name %%cwd
if errorlevel 5 goto b5
goto error
:b5
rem	answer	name=%name%
rem	current full directory
setenv name %%cfd
if errorlevel 5 goto b6
goto error
:b6
rem	answer	name=%name%
rem	current drive
setenv name %%drive:
if errorlevel 5 goto b7
goto error
:b7
rem	answer	name=%name%
rem	current time
setenv name %%time
if errorlevel 5 goto b8
goto error
:b8
rem	answer	name=%name%
rem	current date
setenv name %%date
if errorlevel 5 goto b9
goto error
:b9
rem	answer	name=%name%
rem	current date
setenv name 1 is %%date1 2 is %%date2 3 is %%date3
if errorlevel 5 goto c0
goto error
:c0
rem	answer	name=%name%
rem	prompt message
setenv name %%prompt Your name 
if errorlevel 5 goto c1
if errorlevel 2 goto c1
goto error
:c1
rem	answer	name=%name%
rem	setenv version 1.5 and greater
rem	change upper case
setenv name
setenv name %%chgup
if errorlevel 8 goto f0
goto error
:f0
rem	answer	name=%name%
rem	setenv version 1.5 and greater
rem	change upper case
setenv name test this is a test message
setenv name %%chgup
if errorlevel 5 goto f1
goto error
:f1
rem	answer	name=%name%
rem	setenv version 1.9 and greater
rem	change lower case
setenv name TEST THIS IS A TEST MESSAGE
setenv name %%chglo
if errorlevel 5 goto f2
goto error
:f2
rem	answer	name=%name%
rem	setenv version 1.2 and greater
rem	replace variable
setenv name
setenv name %%rep#t#h#
if errorlevel 8 goto f3
goto error
:f3
rem	answer	name=%name%
rem	setenv version 1.2 and greater
rem	replace variable
setenv name test
setenv name %%rep#t#h#
if errorlevel 5 goto f4
goto error
:f4
rem	answer	name=%name%
rem	setenv version 1.2 and greater
rem	missing delimiter
setenv name %%rep#t
if errorlevel 6 goto f5
goto error
:f5
rem	answer	name=%name%
rem	setenv version 1.2 and greater
rem	missing delimiter
setenv name %%rep#t#
if errorlevel 6 goto f6
goto error
:f6
rem	answer	name=%name%
rem	setenv version 1.4 test
rem	test Setenv variable data size
setenv name
setenv ans %%prompt %%def#NO# %%upper 170 bytes free in envir. space (Y/N) 
setenv ans %%rep#E##
setenv ans %%rep#S##
setenv ans %%rep# ##
if not "%ans%" == "Y" goto h1
set lett=abcdefghij
set numb=0123456789
set unde=__________
set brac=()()()()
setenv a %lett%%brac%*%lett%*%lett%*%unde%*%lett%*%unde%*
rem	answer	name=%a%
setenv brac
setenv unde
setenv a %%rep#*#%numb%#
set 
setenv a %%rep#%numb%%lett%%numb%%lett%%numb%#-out-#
rem	answer	name=%a%
setenv lett
setenv numb
setenv a
:h1
setenv ans
rem	setenv version 1.3 and greater
rem	root test
setenv c %%prompt %%def#e# Drive letter for Command.Com 
setenv c %%rep# ##
if not "%c%" == "" goto z0
echo root command environment not run missing drive.
goto endtest
:z0
setenv d %%drive:
setenv e %%cwd
%c%:\command  /c setenv %%rce name fred
if errorlevel 1 goto z1
%d%
cd %e%
goto error
:z1
rem	answer	name=%name%
%d%
cd %e%
:endtest
rem	cleanup
setenv c
setenv d
setenv e
setenv name
setenv
rem	End of Test.
goto fini
:error
echo An error occured.
if errorlevel 10 goto full
if errorlevel 9 goto offset
if errorlevel 8 goto not
if errorlevel 7 goto numb
if errorlevel 6 goto del
if errorlevel 5 goto update
if errorlevel 4 goto dos
if errorlevel 3 goto create
if errorlevel 2 goto erase
if errorlevel 1 goto root
if errorlevel 0 goto help
echo no errorlevel status
goto fini
:full
echo environment space is full.
goto fini
:offset
echo offset not valid.
goto fini
:not
echo environment variable not found.
goto fini
:numb
echo not a valid argument.
goto fini
:del
echo unmatched delimiter.
goto fini
:update
echo environment variable updated.
goto fini
:dos
echo don't know about MS-DOS V1.x
goto fini
:create
echo environment variable created.
goto fini
:erase
echo environment variable erased.
goto fini
:root
echo root environment set.
goto fini
:help
echo no command, help issued.
goto fini
:fini
