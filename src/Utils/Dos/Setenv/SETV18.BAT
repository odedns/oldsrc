rem	settest.bat
rem	for testing Setenv.Com
rem	setenv version 1.8
rem	environment space
set
rem	variable not found
setenv log %%eq a 'a'
if errorlevel 8 goto a0
goto error
:a0
rem	answer	error	log=%log%
setenv log
rem	variable not found
setenv log %%eq 'a' a
if errorlevel 8 goto a1
goto error
:a1
rem	answer	error	log=%log%
setenv log
rem	not a valid argument
setenv log %%eq  
if errorlevel 7 goto a2
goto error
:a2
rem	answer	error	log=%log%
setenv log
rem	not a valid argument
setenv log %%eq "a"
if errorlevel 7 goto a3
goto error
:a3
rem	answer	error	log=%log%
setenv log
rem	not a valid argument
setenv log %%eq t2
if errorlevel 7 goto a4
goto error
:a4
rem	answer	error	log=%log%
setenv log
rem	unmatched delimiter
setenv log %%eq 'a' 'b
if errorlevel 6 goto a5
goto error
:a5
rem	answer	error	log=%log%
setenv log
rem	unmatched delimiter
setenv log %%eq 'a' "b
if errorlevel 6 goto a6
goto error
:a6
rem	answer	error	log=%log%
setenv log
rem	equal
setenv log %%eq 'a' "a"
if errorlevel 5 goto b0
if errorlevel 3 goto b0
goto error
:b0
rem	answer	true	log=%log%
setenv log
setenv t2 ab
rem	equal
setenv log %%eq 'ab' t2
if errorlevel 5 goto b1
if errorlevel 3 goto b1
goto error
:b1
rem	answer	true	log=%log%
setenv log
rem	equal
setenv log %%eq 'ac' t2
if errorlevel 5 goto b2
if errorlevel 3 goto b2
goto error
:b2
rem	answer	false	log=%log%
setenv log
rem	equal
setenv log %%eq ' ab' t2
if errorlevel 5 goto b3
if errorlevel 3 goto b3
goto error
:b3
rem	answer	true	log=%log%
setenv log
rem	equal
setenv log %%eq 'ab ' t2
if errorlevel 5 goto b4
if errorlevel 3 goto b4
goto error
:b4
rem	answer	false	log=%log%
setenv log
rem	equal
setenv log %%eq t2 'ab '
if errorlevel 5 goto b5
if errorlevel 3 goto b5
goto error
:b5
rem	answer	false	log=%log%
setenv log
rem	equal
setenv log %%eq ' ab ' "ab "
if errorlevel 5 goto b6
if errorlevel 3 goto b6
goto error
:b6
rem	answer	true	log=%log%
setenv log
rem	less than
setenv log %%lt 'ab' 'ac'
if errorlevel 5 goto c0
if errorlevel 3 goto c0
goto error
:c0
rem	answer	true	log=%log%
rem	less than
setenv log %%lt 'ac' 'ab'
if errorlevel 5 goto c1
if errorlevel 3 goto c1
goto error
:c1
rem	answer	false	log=%log%
rem	less than
setenv log %%lt 'ab' 'ab'
if errorlevel 5 goto c2
if errorlevel 3 goto c2
goto error
:c2
rem	answer	false	log=%log%
rem	less than equal
setenv log %%le 'ab' 'ac'
if errorlevel 5 goto c3
if errorlevel 3 goto c3
goto error
:c3
rem	answer	true	log=%log%
rem	less than equal
setenv log %%le 'ac' 'ab'
if errorlevel 5 goto c4
if errorlevel 3 goto c4
goto error
:c4
rem	answer	false	log=%log%
rem	less than equal
setenv log %%le 'ab' 'ab'
if errorlevel 5 goto c5
if errorlevel 3 goto c5
goto error
:c5
rem	answer	true	log=%log%
rem	greater than
setenv log %%gt 'ac' 'ab'
if errorlevel 5 goto d0
if errorlevel 3 goto d0
goto error
:d0
rem	answer	true	log=%log%
rem	greater than
setenv log %%gt 'ab' 'ac'
if errorlevel 5 goto d1
if errorlevel 3 goto d1
goto error
:d1
rem	answer	false	log=%log%
rem	greater than
setenv log %%gt 'ab' 'ab'
if errorlevel 5 goto d2
if errorlevel 3 goto d2
goto error
:d2
rem	answer	false	log=%log%
rem	greater than equal
setenv log %%ge 'ac' 'ab'
if errorlevel 5 goto d3
if errorlevel 3 goto d3
goto error
:d3
rem	answer	true	log=%log%
rem	greater than equal
setenv log %%ge 'ab' 'ac'
if errorlevel 5 goto d4
if errorlevel 3 goto d4
goto error
:d4
rem	answer	false	log=%log%
rem	greater than equal
setenv log %%ge 'ab' 'ab'
if errorlevel 5 goto d5
if errorlevel 3 goto d5
goto error
:d5
rem	answer	true	log=%log%
:endtest
rem	cleanup
setenv log
setenv t2
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
