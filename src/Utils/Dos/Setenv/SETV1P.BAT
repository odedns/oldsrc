rem	settest.bat
rem	for testing Setenv.Com
rem	setenv prompt 1.p
rem	environment space
set
rem	missing delimiter
rem	enter carriage return
setenv name %%prompt %%def#test
if errorlevel 6 goto a0
goto error
:a0
rem	answer	name=%name%=
rem	prompt message
rem	enter 'p'
setenv name %%prompt Data 
if errorlevel 5 goto b1
if errorlevel 2 goto b1
goto error
:b1
rem	answer	p	name=%name%=
rem	setenv version 1.1 and greater
rem	prompt and  noecho
rem	enter 'e'
setenv name %%prompt %%noecho Data 
if errorlevel 5 goto b2
if errorlevel 2 goto b2
goto error
:b2
rem	answer	e	name=%name%
rem	setenv version 1.3 and greater
rem	prompt and upper case
rem	enter 'u'
setenv name %%prompt %%upper Data 
if errorlevel 5 goto b3
if errorlevel 2 goto b3
goto error
:b3
rem	answer	U	name=%name%
rem	setenv version 1.9 and greater
rem	prompt and lower case
rem	enter 'L'
setenv name %%prompt %%lower Data 
if errorlevel 5 goto b4
if errorlevel 2 goto b4
goto error
:b4
rem	answer	l	name=%name%
rem	prompt and default value
rem	enter carriage return
setenv name %%prompt %%def#test# Data 
if errorlevel 5 goto b5
if errorlevel 3 goto b5
goto error
:b5
rem	answer	test	name=%name%
rem	setenv version 1.5 and greater
rem	prompt and time out no default
rem	enter "let time out"
setenv name %%prompt %%timo=04 Data 
if errorlevel 2 goto b6
goto error
:b6
rem	answer	name=%name%
rem	setenv version 1.5 and greater
rem	prompt and time out with default
rem	enter "let time out"
setenv name %%prompt %%def#test# %%timo=04 Data 
if errorlevel 3 goto b7
goto error
:b7
rem	answer	test	name=%name%
rem	setenv version 1.5 and greater
rem	time out with default with defaut
rem	enter "let time out"
setenv name %%prompt %%def#test# %%timo=04 Data 
if errorlevel 5 goto b8
goto error
:b8
rem	answer	test	name=%name%
rem	setenv version 1.9 and greater
rem	trim leading and trailling spaces
rem	enter '  xx  xx  '
setenv name %%prompt %%trim Data 
if errorlevel 5 goto b9
goto error
:b9
rem	answer	'xx  xx'	name=%name%
rem	order test 1
rem	enter xx
setenv name %%prompt %%noecho %%upper %%def#test# Data 
if errorlevel 5 goto d0
goto error
:d0
rem	answer	XX	name=%name%
rem	order test 2
rem	enter xx
setenv name %%prompt %%upper %%noecho %%def#test# Data 
if errorlevel 5 goto d1
goto error
:d1
rem	answer	XX	name=%name%
rem	order test 3
rem	enter xx
setenv name %%prompt %%def#test# %%noecho %%upper Data 
if errorlevel 5 goto d2
goto error
:d2
rem	answer	XX	name=%name%
rem	order test 4
rem	enter xx
setenv name %%prompt %%def#test# %%noecho %%upper %%timo=4 Data 
if errorlevel 5 goto d3
goto error
:d3
rem	answer	XX	name=%name%
rem	order test 5
rem	enter xx
setenv name %%prompt %%timo=4 %%def#test# %%noecho %%upper Data 
if errorlevel 5 goto d4
goto error
:d4
rem	answer	XX	name=%name%
rem	readkey
rem	enter "let time out"
setenv name %%prompt %%timo=4 %%readkey def#y# Answer (y/n)  
if errorlevel 5 goto d5
goto error
:d5
rem	answer	y	name=%name%
:endtest
rem	cleanup
setenv name
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
