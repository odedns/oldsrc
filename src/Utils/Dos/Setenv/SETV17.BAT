rem	settest.bat
rem	for testing Setenv.Com
rem	setenv version 1.7
rem	environment space
setenv date
set
rem	variable not found
setenv day %%mid/date/1/2/
if errorlevel 8 goto a0
goto error
:a0
setenv date 31-01-92
rem	not a valid argument
setenv day %%mid/date/1a/2/
if errorlevel 7 goto a1
goto error
:a1
rem	unmatched delimiter
setenv day %%mid/date/1/2
if errorlevel 6 goto a2
goto error
:a2
rem	length of variable data
setenv length %%len/date/
if errorlevel 5 goto a3
if errorlevel 3 goto a3
goto error
:a3
rem	answer	8	length=%length%
setenv length
rem	substring of a variable
setenv day %%mid/date/1/2/
if errorlevel 5 goto a4
if errorlevel 3 goto a4
goto error
:a4
rem	answer	31	day=%day%
rem	element of a string of elements
setenv month %%elem/1/-/date/
if errorlevel 5 goto a5
if errorlevel 3 goto a5
goto error
:a5
setenv day
rem	answer	01	month=%month%
rem	locate a string
setenv at %%loc/0/date/-9/
if errorlevel 5 goto a6
if errorlevel 3 goto a6
goto error
:a6
setenv month
rem	answer	6	at=%at%
rem	add number to variable
setenv at %%sum/at/2/
if errorlevel 5 goto a7
goto error
:a7
rem	answer	8	at=%at%
setenv at
rem	reformat date
setenv date2 %%mid/date/4/2/ %%mid/date/1/2/ %%mid/date/7/2/
setenv date3 %%elem/2/-/date//%%elem/1/-/date//%%elem/0/-/date/
rem	answer	'01 31 92'	date2=%date2%
rem	answer	'92/01/31'	date3=%date3%
setenv date2
setenv date3
rem	extract at string
setenv t1 %%loc/0/date/-/
setenv t2 %%sum/t1/1/
setenv t3 %%loc/%t2%/date/-/
setenv t4 %%sum/t3/-%t2%/
setenv t5 %%mid/date/%t2%/%t4%/
rem	answer	01	t5=%t5%
rem	test sum sums
set t1=-11
set t2=11
setenv t3 %%sum/t1/2/
setenv t4 %%sum/t2/2/
setenv t5 %%sum/t1/-2/
setenv t6 %%sum/t2/-2/
rem	answer	-9	t3=%t3%
rem	answer	13	t4=%t4%
rem	answer	-13	t5=%t5%
rem	answer	9	t6=%t6%
:endtest
rem	cleanup
setenv t1
setenv t2
setenv t3
setenv t4
setenv t5
setenv t6
setenv date
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
