title Set Environment Variable Program.
page 60,120			; page length = 60
				; page width  = 120
;
;	This program places a variable in the environment.
;
;	SETENV : Copyright (c) 1988 to 1995 by John Wolchak.  
;	  I give permission to alter the code, but not to copy 
;	  or redistribute the altered code without my explicit 
;	  permission.  If you alter the code, please document 
;	  changes and send me a copy, so all can have it.  The 
;	  file Setenv.Doc must always accompany the SETENV 
;	  program when a copy is made for another machine.  
;	  This code, to the best of my knowledge works well.  
;	  I disclaim any responsibility for the codes actions 
;	  (use at your own risk).  
;
;	John Wolchak.
;	56 Physics Building
;	University of Saskatchewan.
;	Saskatoon, Saskatchewan
;	Canada     S7K 0W0
;	Phone: (306) 966-4852
;	Inter_Network: Wolchak@Admin.Usask.Ca
;
;	To assemble:
;	masm setenv,,,
;	link setenv,,,
;	exe2bin setenv.exe setenv.com
;	delete setenv.obj
;	delete setenv.exe
;
code segment
assume cs:code,ds:code
org 100h

start:		jmp beginning
;
;		Data Area
;
exit_code	db	03h		; Environment variable created.
					; PSP is Program Segment Prefix.
cpsp		dw	00h		; The psp of command.com.
env_off		dw	00h		; Environment offset address.
env_siz		dw	00h		; Environment size in bytes.
dos_maj		db	00h		; DOS major version.
dos_min		db	00h		; DOS minor version.
set		db	"set "
s_rce		dw	03h		; Size of Root Comm Env.
l_rce		db	"rce"
u_rce		db	"RCE"
v_rce		dw	00h		; RCE value.
a_rce		dw	00h		; Segment address.
;d_rce		db	0ffh dup(0)	; Data string of Set command.
s_data		dw	00h		; Size of environment data.
a_env_var	dw	00h		; Address of environment variable.
s_env_var	dw	00h		; Environment variable size.
;d_env_var	db	0ffh dup(0)	; Environment data.
;null		db	00h,00h		; Environment tail.
y_prompt	dw	08h		; Size of '%prompt '.
x_prompt	dw	06h		; Size.
l_prompt	db	"prompt"
u_prompt	db	"PROMPT"
o_prompt	dw	00h		; Offset of prompt.
s_def		dw	03h		; Size of default.
l_def		db	"def"
u_def		db	"DEF"
x_def		dw	00h		; Default value size.
;v_def		dw	100 dup(0)	; Default value.
s_echo		dw	06h		; Size of echo.
l_echo		db	"noecho"
u_echo		db	"NOECHO"
v_echo		dw	00h		; Echo on.
s_upper		dw	05h		; Size of upper.
l_upper		db	"upper"
u_upper		db	"UPPER"
v_upper		dw	00h		; Upper case on.
s_lower		dw	05h		; Size of lower.
l_lower		db	"lower"
u_lower		db	"LOWER"
v_lower		dw	00h		; Lower case on.
s_trim		dw	04h		; Size of trim.
l_trim		db	"trim"
u_trim		db	"TRIM"
v_trim		dw	00h		; Trim leading spaces.
w_trim		dw	00h		; Trim trailing spaces.
o_tmp		dw	00h		; Save temporary address.
s_timo		dw	05h		; Size of timo.
l_timo		db	"timo="
u_timo		db	"TIMO="
v_timo		dw	00h		; Time out infinit.
s_readkey	dw	07h		; Size of readkey.
l_readkey	db	"readkey"
u_readkey	db	"READKEY"
v_readkey	dw	00h		; Readkey leading spaces.
c_brk		db	00h		; Control break status.
d_ent		db	00h		; Entry days.
h_ent		db	00h		; Entry hours.
m_ent		db	00h		; Entry minutes.
s_ent		db	00h		; Entry seconds.
d_stt		db	00h		; Stop time day.
h_stt		db	00h		; Stop time hours.
m_stt		db	00h		; Stop time minutes.
s_stt		db	00h		; Stop time seconds.
f_stt		db	00h		; Stop flag exit type.
s_rep		dw	03h		; Size of replace.
l_rep		db	"rep"
u_rep		db	"REP"
v_rep		dw	00h
s_arg1		dw	00h		; Replace old value size.
;d_arg1		dw	100 dup(0)	; Replace old value.
s_arg2		dw	00h		; Replace new value size.
;d_arg2		dw	100 dup(0)	; Replace new value.
s_chr		dw	03h		; Size of chr.
l_chr		db	"chr"
u_chr		db	"CHR"
n_chr		dw	00h		; Decimal value character.
s_elem		dw	04h		; Size of element.
l_elem		db	"elem"
u_elem		db	"ELEM"
c_elem		db	00h		; Element delimiter
e_elem		dw	00h		; Element number.
s_mid		dw	03h		; Size of mid.
l_mid		db	"mid"
u_mid		db	"MID"
y_mid		dw	00h		; Substr starting position.
z_mid		dw	00h		; Substr length.
b_env		dw	00h		; Program env address.
o_env		dw	00h		; Variable offset.
f_env		db	00h		; Variable search.
s_env		dw	00h		; Variable name size.
n_env		dw	20 dup(0)	; Variable name.
w_mid		dw	00h		; Variable name size.
x_mid		dw	20 dup(0)	; Variable name.
s_loc		dw	03h		; Size of loc.
l_loc		db	"loc"
u_loc		db	"LOC"
e_loc		dw	00h		; Locate starting position.
s_len		dw	03h		; Size of len.
l_len		db	"len"
u_len		db	"LEN"
s_sum		dw	03h		; Size of sum.
l_sum		db	"sum"
u_sum		db	"SUM"
v_sum		dw	00h
f_sum		db	"+"		; Add or subtract flag.
nmb_sgn		db	"+"		; Sign for number.
nmb_tst		db	00h		; Test for sign.
dec_zfl		db	00h		; Decimal zero flag.
l_eq		dw	"eq"
u_eq		dw	"EQ"
l_lt		dw	"lt"
u_lt		dw	"LT"
l_le		dw	"le"
u_le		dw	"LE"
l_gt		dw	"gt"
u_gt		dw	"GT"
l_ge		dw	"ge"
u_ge		dw	"GE"
e_oper		db	00h		; Test for equal.
l_oper		db	00h		; Test for less than.
g_oper		db	00h		; Test for greater than
s_chgup		dw	05h		; Size of Change upper.
l_chgup		db	"chgup"
u_chgup		db	"CHGUP"
v_chgup		dw	00h		; Change upper flag.
s_chglo		dw	05h		; Size of Change lower.
l_chglo		db	"chglo"
u_chglo		db	"CHGLO"
v_chglo		dw	00h		; Change lower flag.
s_dosv		dw	04h		; Size of Dos Major.
l_dosv		db	"dosv"
u_dosv		db	"DOSV"
s_dosm		dw	04h		; Size of Dos Minor.
l_dosm		db	"dosm"
u_dosm		db	"DOSM"
s_cwd		dw	03h		; Size of Current directory.
l_cwd		db	"cwd"
u_cwd		db	"CWD"
;d_cwd		db	0ffh dup(0)	; Data area (temporary).
s_cfd		dw	03h		; Size of Current directory.
l_cfd		db	"cfd"
u_cfd		db	"CFD"
s_time		dw	04h		; Current time.
l_time		db	"time"
u_time		db	"TIME"
s_date		dw	04h		; Current date.
l_date		db	"date"
u_date		db	"DATE"
z_date		db	"1"		; Date format.
s_drive		dw	05h		; Size of drive.
l_drive		db	"drive"
u_drive		db	"DRIVE"
s_path		dw	05h		; Size of Path.
d_path		db	"PATH="
s_comspec	dw	08h		; Size of Comspec=.
d_comspec	db	"COMSPEC="
s_prompt	dw	07h		; Size of Prompt=.
d_prompt	db	"PROMPT="
s_setenv	dw	07h		; Size of Setenv=.
d_setenv	db	"SETENV="
s_config	dw	07h		; Size of Config=.
d_config	db	"CONFIG="
s_temp		dw	05h		; Size of Temp=.
d_temp		db	"TEMP="
s_tmp		dw	04h		; Size of Tmp=.
d_tmp		db	"TMP="
s_winbootdir	dw	10h		; Size of Winbootdir=.
d_winbootdir	db	"winbootdir="
author		db	"Set Environment Variable Program V2.5 (c) 18-Aug-1995 "
		db	"by John Wolchak.",13,10
d_cwd		db	"Usage is:  SETENV <variable> <value>  "
		db	"or  SETENV %rce <variable> <value>",13,10,9
		db	"<variable> by it's self to erase variable.",13,10,9
		db	"<value> can be: '%rep<del><txt1><del><txt2><del>' "
		db	"text replace.",13,10,9
		db	"  '%chgup' or 'chglo' upper/lower case environment "
		db	"variable data.",13,10,9
		db	"  '%prompt %noecho %upper "
d_rce		db	"%def<del><text><del> <message>'.",13,10,9
		db	"     '%noecho' for no echo, '%upper' '%lower' "
		db	"upper/lower case only.",13,10,9
		db	"     '%def<del><text><del>' supply a default,"
		db	" '%trim' trim spaces.",13,10,9
		db	"     '%timo=hh:mm:ss' prompt time out, time length."
		db	13,10,9
		db	"<value> can include keys such as:   "
		db	"ie  setenv drivedir %drive:%cwd",13,10,9
d_arg1		db	"  %dosv / %dosm for DOS major / minor version."
		db	13,10,9
		db	"  %cwd or %cfd for current directory, "
		db	"  %drive for drive letter.",13,10,9
		db	"  %time / %daten for current time / date format 'n'."
		db	13,10,9
v_def		db	"  %+n / %-n for default drive plus / minus 'n'."
		db	13,10,9
		db	"  %loc/<n>/<var>/<str>/  %len/<var>/"
		db	"  %sum/<var>/<n>/",13,10,9
		db	"  %mid/<var>/<n1>/<n2>/  %elem/<n>/<char>/<var>/"
		db	"  %chr/<n>/",13,10
d_arg2		db	"Note:     Use %% to represent a single % in BAT files."
		db	13,10
		db	"       SETENV return codes:",9,9
		db	"no command, help issued 	00h",13,10
d_env_var	db	"root environment set		01h",9
		db	"environment variable erased	02h",13,10
		db	"environment variable created	03h",9
		db	"don't know about MS-DOS V1.x	04h",13,10
		db	"environment variable updated	05h",9
		db	"unmatched delimiter		06h",13,10
		db	"not a valid argument		07h",9
		db	"environment variable not found	08h",13,10
		db	"offset drive will be invalid	09h",9
		db	"environment space is full	10h"
		db	"$"
get4d_err	db	"Error - can't find SETENV at top of environment space.$"
;
;	If the help is changed then the variables d_cwd, d_rce
;		v_def, d_arg1, d_arg2, and d_env_var need to be
;		changed to  the size of the comments above.
;		v_def, d_arg1, and d_arg2 are approximately 100
;		d_cwd, d_rce, and d_env_var are approximately 255
;		remember the DOS command line is 123 and you can %rep///
;
;		Code Area
;
Beginning:
		mov sp, offset stack	; Set up local stack.
		mov si, 80h		; Offset of command line.
		lodsb			; Get a byte.
		cmp al, 00h		; Is there any data.
		jnz get_ver		; Yes. Jump.
dis_help:
		mov dx, offset author	; Help message.
		mov ah, 09h		; Display it.
		int 21h
		mov exit_code, 00h	; No command, help issued.
		jmp nor_term		; Exit.

get_ver:	
		mov ah, 30h		; Get DOS version number.
		int 21h

		mov dos_maj, al
		mov dos_min, ah

		cmp ah, 01h
		jnz cont
		mov exit_code, 04h	; No MS-DOS version 1.x.
		jmp nor_term		; Exit.

;		Get rid of leading spaces and tabs.
cont:
		lodsb			; Get a byte.
		cmp al, " "		; Is it a leading blank.
		jz cont			; Yes.  Loop.
		cmp al, 09h		; Is it a leading tab.
		jz cont			; Yes.  Loop.
		cmp al, "%"		; Is it a special one.
		jz rce			; Yes.  Use it.
		cmp al, 0dh		; Is it a carriage return.
		jz dis_help		; Yes. dis_help.
cont_on:
		dec si
		jmp var

rce:
;		Handle %rce.
		push si
		mov di, si		; Set destination.
		mov si, offset l_rce	; Set source.
		mov cx, s_rce
		rep cmpsb		; Is it %rce.
		jz set_rce		; Yes, Jump out.

		pop si
		push si
		mov di, si		; Set destination.
		mov si, offset u_rce	; Set source.
		mov cx, s_rce
		rep cmpsb		; Is it %RCE.
		jz set_rce		; Yes, Jump out.

		pop si
		jmp cont_on
set_rce:
		pop si
		inc si
		inc si
		inc si
		mov v_rce, 01h		; Root Command Environment.

;		Get rid of leading spaces and tabs.
var:
		lodsb			; Get a byte.
		cmp al, " "		; Is it a leading blank.
		jz var			; Yes.  Loop.
		cmp al, 09h		; Is it a leading tab.
		jz var			; Yes.  Loop.

		dec si			; Backup for the character.
		mov a_env_var, si	; Offset of the variable.
		mov di, offset d_env_var ; Offset of env. variable.

;		Get the variable name,
;		and convert to upper case.
get_var:
		lodsb			; Get a byte.
		cmp al, " "		; Is it the end of the variable?
		jz var_end		; Yes.  Jump out.
		cmp al, 09h		; Is it the end of the variable?
		jz var_end		; Yes.  Jump out.
		cmp al, "="		; Is it the end of the variable?
		jz var_end		; Yes. Jump out.
		cmp al, 0dh		; Is it the end.
		jz no_data		; Yes. We got it.
		cmp al, "z"		; Is
		jg no_lower		;   it
		cmp al, "a"		;     lower
		jb no_lower		;       case?
		sub al, 20h		; Make it upper case.
no_lower:
		stosb			; Save the byte.
		jmp get_var

no_data:
		mov al, 0dh
		mov [si], al
var_end:
		mov al, "="		; Set the end of variable.
		stosb			; Save it.
		mov -1[si], al		; And the command line.

		mov ax, si		; Get the offset.
		mov bx, a_env_var	; Beginning of the variable.
		sub ax, bx		; Get the variable length.
		mov s_env_var, ax	; And save it.

var_end1:
		lodsb			; Get a byte.
		cmp al, " "		; Is it a blank.
		jz var_end1		; Loop.
		cmp al, 09h		; Is it a tab.
		jz var_end1		; Loop.
		dec si

		mov al, [si]		; Get a byte.
		cmp al, "%"		; Is it a special one.
		jz prompt		; Yes. Jump out.

;		Get the data part.
get_data:
		mov al, [si]		; Get a byte.
		cmp al, "%"		; Is it a special one.
		jz percent_near		; Yes. Jump out.
get_next:
		lodsb			; Get a byte.
		cmp al, 0dh		; Is it the end.
		jz data_end_near	; Yes. We got it.
		stosb			; Save the byte.
		jmp get_data

data_end_near:
		jmp data_end
percent_near:
		jmp percent

;		Handle %prompt.
prompt:
		mov o_prompt, si
		inc si
		push di
		push si

		mov di, si		; Set destination.
		mov si, offset l_prompt	; Set Source.
		mov cx, x_prompt
		rep cmpsb		; Is it %prompt.
		jz sub_fun_near		; Yes.

		pop si
		push si
		mov di, si		; Set destination.
		mov si, offset u_prompt	; Set source.
		mov cx, x_prompt
		rep cmpsb		; Is it %PROMPT.
		jz sub_fun_near		; Yes.

;		Then check for replace
		jmp replace

sub_fun_near:
		jmp sub_fun

get_prompt:
		pop si
		push si
		dec si

		mov ax, si		; Jump
		mov cx, y_prompt	;   the
		add ax, cx		;     %prompt
		mov si, ax		;       string.

;		Display the prompt.
prompt1:
		lodsb			; Scan for
		cmp al, 0dh		;   a return.
		jz prompt2

		call make_char		; Generate character.
		mov dl, al		; Output Character.
		mov ah, 02h
		int 21h
		jmp prompt1
prompt2:
		pop si
		push si
		mov di, si
		mov bx, di

;		Check do we time out.
		cmp v_timo, 00h		; Do we timeout?
		jnz check0		; Yes. Jumpout.
		jmp prompt3

check0:
;		Get the current time, and set the stop time.
		mov ah, 2ch		; Get the
		int 21h			;   current time.
		mov dl, d_ent		; No day.

		add dh, s_ent		; Add the seconds
		cmp dh, 3ch
		jb get0
		sub dh, 3ch		; and correct.
		inc cl
get0:
		add cl, m_ent		; Add the minutes
		cmp cl, 3ch
		jb get1
		sub cl, 3ch		; and correct.
		inc ch
get1:
		add ch, h_ent		; Add the hours
		cmp ch, 18h
		jb get2
		sub ch, 18h		; and correct.
		inc dl			; New day.
get2:
		mov s_stt, dh		; Save
		mov m_stt, cl		;   the
		mov h_stt, ch		;     stop
		mov d_stt, dl		;       time.
;		end of get and set time.

;		Get and check time.
		mov al, 00h		; Set for
		mov f_stt, al		;   read input.

		mov ah, 33h		; Check Ctrl-Break.
		mov al, 00h
		int 21h
		mov c_brk, dl		; Save current setting.
		mov ah, 33h		; Set Ctrl-Break.
		mov al, 01h
		mov dl, 01h
		int 21h
check1:
		mov ah, 0bh		; Check
		int 21h			;   input
		cmp al, 00h		;     waiting.
		jnz check9		; Yes. Jump out.

		mov ah, 2ch		; Get the
		int 21h			;   current time.

		cmp d_stt, 00h		; Over a midnight?
		jz check3
		cmp ch, 00h		; New hour?
		jnz check1
		cmp cl, 00h		; New minute?
		jnz check1
		cmp dh, 00h		; New second?
		jnz check1
		cmp dl, 00h		; New hundredth second?
		jz check1
		mov al, d_stt		; Decrement
		dec al			;   a day
		mov d_stt, al		;     and save.
check2:
		mov ah, 2ch		; Get the
		int 21h			;   current time.

		cmp dh, 00h		; Loop until
		jz check2		;   the second changes.

		jmp check1

check3:
		cmp ch, h_stt		; Correct hour?
		jb check1		; No. Loop.
		cmp cl, m_stt		; Correct minute?
		jb check1		; No. Loop.
		cmp dh, s_stt		; Correct second?
		jb check1		; No. Loop.
		mov al, 01h		; Set for
		mov f_stt, al		;   timeout.

check9:
		mov ah, 33h		; Restore Ctrl-Break.
		mov al, 01h
		mov dl, c_brk
		int 21h

		cmp f_stt, 00h		; Is someone typing?
		jz prompt3		; Yes. Jump out.
		mov al, 0dh		; Fake carriage return read
		jmp prompt7

;		end of check time.

prompt3:
;		Read the response.
		mov ah, 08h		; Read keyboard.
		int 21h

		cmp v_upper, 01h	; Upper case on.
		jnz not_upper
		call uppercase		; Convert to uppercase.
not_upper:

		cmp v_lower, 01h	; Lower case on.
		jnz not_lower
		call lowercase		; Convert to lowercase.
not_lower:

		cmp v_readkey, 01h	; Readkey on.
		jnz not_readkey		; No.  Jump readkey part.

		cmp v_echo, 00h		; Do we echo the character.
		jnz noecho_readkey

		mov dl, al		; Output Character.
		mov ah, 02h
		int 21h

noecho_readkey:
		stosb			; Save the byte.
		mov al, 0dh		; Put in a carriage return.

not_readkey:
		cmp al, 08h		; Got a backspace character.
		jz prompt4
		cmp al, 7fh		; Got a delete character.
		jnz prompt6

prompt4:
		mov dx, di		; Current pointer.
		cmp bx, dx		; Have we moved?
		jz prompt3		; No. Jump.

		cmp v_echo, 00h		; Do we echo the character.
		jnz prompt5

		mov dl, 08h		; Print a backspace.
		mov ah, 02h
		int 21h

		mov dl, " "		; Print a space.
		mov ah, 02h
		int 21h

		mov dl, 08h		; Print a backspace.
		mov ah, 02h
		int 21h

prompt5:
		dec di
		jmp prompt3		; Backup on the last byte.

prompt6:
		cmp v_echo, 00h		; Do we echo the character.
		jnz prompt7

		mov dl, al		; Output Character.
		mov ah, 02h
		int 21h

prompt7:
		cmp al, " "		; Is it a space.
		jnz not_space

		cmp v_trim, 01h		; Trim leading space.
		jz prompt3

not_space:
		mov v_trim, 00h		; No Trim.

		stosb			; Save the byte.
		cmp al, 0dh		; Got a return.
		jnz prompt3

		mov dl, 0dh		; Print a return.
		mov ah, 02h
		int 21h
		mov dl, 0ah		; Print a linefeed.
		mov ah, 02h
		int 21h

		cmp w_trim, 01h		; Trim trailing space.
		jnz trim_nosp
		dec di

trim_sp:
		dec di
		mov al, [di]		; Get a byte.

		cmp al, " "		; Is is a space.
		jz trim_sp		; Yes. Loop.

		inc di
		mov al, 0dh		; Put in a carriage return.
		stosb

trim_nosp:
		pop si
		push si
		mov al, [si]
		cmp al ,0dh		; Is it a return.
		jnz prompt9		; No.  Jump out.

		mov di, si		; Move
		mov si, offset v_def	;   the default
		mov cx, x_def		;     in place.
		rep movsb

		mov al, 0dh
		stosb
prompt9:
		pop si
		pop di

		cmp v_readkey, 01h	; Readkey on.
		jz cont_readkey		; Yes finish up readkey.
		jmp get_data

cont_readkey:
		lodsb			; Get a byte.
		stosb			; Save the byte.
		jmp data_end

;		white space in front of function
sub_fun:
		mov si, di
sub_fun0:
		lodsb
		mov o_tmp, si
		cmp al, " "		; Is it space?
		jz sub_fun0		; Yes. Ignore.
		cmp al, 09h		; Is it a tab?
		jz sub_fun0		; Yes. Ignore.
		cmp al, "%"		; Is it a special one.
		jz echo 		; Yes.  Use it.
sub_fun1:
		dec si
		mov ax, si		; Get current pointer.
		mov bx, o_prompt	; Get prompt value start.
		sub ax, bx		; To get prompt noise length.
		mov y_prompt, ax	; Save it.

		jmp get_prompt


;		Handle %noecho.
echo:
		mov si, o_tmp		; Set destination.
		mov di, offset l_echo	; Set Source.
		mov cx, s_echo
		rep cmpsb		; Is it %noecho.
		jz get_echo		; Yes.

		mov si, o_tmp		; Set destination.
		mov di, offset u_echo	; Set source.
		mov cx, s_echo
		rep cmpsb		; Is it %NOECHO.
		jz get_echo		; Yes.

		jmp upper

get_echo:
		mov v_echo, 01h		; Set no echo.
		jmp sub_fun0

;		Handle %upper.
upper:
		mov si, o_tmp		; Set destination.
		mov di, offset l_upper	; Set Source.
		mov cx, s_upper
		rep cmpsb		; Is it %upper.
		jz get_upper		; Yes.

		mov si, o_tmp		; Set destination.
		mov di, offset u_upper	; Set source.
		mov cx, s_upper
		rep cmpsb		; Is it %UPPER.
		jz get_upper		; Yes.

		jmp lower

get_upper:
		mov v_upper, 01h	; Set upper case.
		jmp sub_fun0

;		Handle %lower.
lower:
		mov si, o_tmp		; Set destination.
		mov di, offset l_lower	; Set Source.
		mov cx, s_lower
		rep cmpsb		; Is it %lower.
		jz get_lower		; Yes.

		mov si, o_tmp		; Set destination.
		mov di, offset u_lower	; Set source.
		mov cx, s_lower
		rep cmpsb		; Is it %LOWER.
		jz get_lower		; Yes.

		jmp trim

get_lower:
		mov v_lower, 01h	; Set lower case.
		jmp sub_fun0

;		Handle %trim.
trim:
		mov si, o_tmp		; Set destination.
		mov di, offset l_trim	; Set Source.
		mov cx, s_trim
		rep cmpsb		; Is it %trim.
		jz get_trim		; Yes.

		mov si, o_tmp		; Set destination.
		mov di, offset u_trim	; Set source.
		mov cx, s_trim
		rep cmpsb		; Is it %TRIM.
		jz get_trim		; Yes.

		jmp readkey

get_trim:
		mov v_trim, 01h		; Trim leading spaces.
		mov w_trim, 01h		; Trim trailing spaces.
		jmp sub_fun0


;		Handle %readkey.
readkey:
		mov si, o_tmp		; Set destination.
		mov di, offset l_readkey ; Set Source.
		mov cx, s_readkey
		rep cmpsb		; Is it %readkey.
		jz get_readkey		; Yes.

		mov si, o_tmp		; Set destination.
		mov di, offset u_readkey ; Set source.
		mov cx, s_readkey
		rep cmpsb		; Is it %READKEY.
		jz get_readkey		; Yes.

		jmp timo

get_readkey:
		mov v_readkey, 01h	; Set readkey.
		jmp sub_fun0

;		Handle %timo.
timo:
		mov si, o_tmp		; Set destination.
		mov di, offset l_timo	; Set Source.
		mov cx, s_timo
		rep cmpsb		; Is it %timo.
		jz get_timo		; Yes.

		mov si, o_tmp		; Set destination.
		mov di, offset u_timo	; Set source.
		mov cx, s_timo
		rep cmpsb		; Is it %TIMO.
		jz get_timo		; Yes.

		jmp default

get_timo:
;		mov si, di
timo1:
		lodsb
		cmp al, ":"		; Time
		jz timo2		;   dividers
		cmp al, "."		;     semi-colon
		jz timo2		;       and period.
		cmp al, "-"
		jz timo2

		cmp al, "0"		; Is
		jb timo3		;   it
		cmp al, "9"		;     a 
		jg timo3		;       digit?

		sub al, 30h		; Make it binary.
		mov bh, al		; Temp. save it.
		mov al, s_ent		; Get the seconds.
		mov bl, 0ah		; Multiply 
		mul bl			;   by ten.
		add al, bh		; Add next digit.
		mov s_ent, al		; Save the seconds.
		jmp timo1

timo2:
		mov al, h_ent
		mov d_ent, al
		mov al, m_ent		; Roll
		mov h_ent, al		;   over
		mov al, s_ent		;     the time
		mov m_ent, al		;       components.
		mov al, 00h
		mov s_ent, al
		jmp timo1

timo3:
		mov v_timo, 01h		; Set time out.
		dec si
		jmp sub_fun0

;		Handle %def.
default:
		mov si, o_tmp		; Set destination.
		mov di, offset l_def	; Set Source.
		mov cx, s_def
		rep cmpsb		; Is it %def.
		jz get_default		; Yes.

		mov si, o_tmp		; Set destination.
		mov di, offset u_def	; Set source.
		mov cx, s_def
		rep cmpsb		; Is it %DEF.
		jz get_default		; Yes.

		mov si, o_tmp
		jmp sub_fun1

get_default:
		lodsb			; Get the delimiter.
		mov dl, al		; Save it.
		mov di, offset v_def	; Get address for save of value.
		mov x_def, si		; Save the start of the value.
default1:
		lodsb			; Get a byte.
		cmp al, 0dh		; Is it a return?
		jz default2		; Yes. Jump out.

		cmp al, dl		; Is it the delimiter?
		jz default3		; Yes. Jump out.
		stosb			; Save it.
		jmp default1
default2:
		mov -1[si], dl		; No
		mov ah, " "		;   delimiter
		mov [si], ah		;     so
		mov ah, ">"		;       create
		mov 1[si], ah		;         the
		mov 2[si], al		;           info.
		dec si
		mov cs:[exit_code], 06h	; Unmatched delimter.
		jmp default1

default3:
		mov ax, si		; Get current pointer.
		mov bx, x_def		; Get default value start.
		sub ax, bx		; To get length of default value.
		dec ax
		mov x_def, ax		; Save it.

		jmp sub_fun0

;		Handle %rep.
replace:
		pop si
		push si
		mov di, si		; Set destination.
		mov si, offset l_rep	; Set Source.
		mov cx, s_rep
		rep cmpsb		; Is it %rep.
		jz get_replace		; Yes.

		pop si
		push si
		mov di, si		; Set destination.
		mov si, offset u_rep	; Set Source.
		mov cx, s_rep
		rep cmpsb		; Is it %REP.
		jz get_replace		; Yes.

		jmp chgup		; No. Jump out.

get_replace:
		mov si, di
		lodsb			; Get the delimiter.
		mov dl, al		; Save it.
		mov di, offset d_arg1	; Get address for save of value.
		mov cx, 00h
replace1:
		lodsb			; Get a byte.
		cmp al, 0dh		; Is it a return?
		jz replace2		; Yes. Jump out.

		cmp al, dl		; Is it the delimiter?
		jz replace3		; Yes. Jump out.
		stosb			; Save it.
		inc cx			; Increment the count
		jmp replace1
replace2:
		dec si
		mov cs:[exit_code], 06h	; Unmatched delimter.
;		You could take out the next line.
		jmp nor_term		; Exit.

replace3:
		mov s_arg1, cx		; Save size.
		mov di, offset d_arg2	; Get address for save of value.
		mov cx, 00h
replace4:
		lodsb			; Get a byte.
		cmp al, 0dh		; Is it a return?
		jz replace5		; Yes. Jump out.

		cmp al, dl		; Is it the delimiter?
		jz replace6		; Yes. Jump out.
		stosb			; Save it.
		inc cx			; Increment the count
		jmp replace4

replace5:
		dec si
		mov cs:[exit_code], 06h	; Unmatched delimter.
replace6:
		mov s_arg2, cx		; Save size.
		mov v_rep, 0ffh		; Flag the replace.

		pop si
		pop di
		inc si
		jmp data_end

;		Handle %chgup.
chgup:
		pop si
		push si
		mov di, si		; Set destination.
		mov si, offset l_chgup	; Set Source.
		mov cx, s_chgup
		rep cmpsb		; Is it %chgup.
		jz get_chgup		; Yes.

		pop si
		push si
		mov di, si		; Set destination.
		mov si, offset u_chgup	; Set Source.
		mov cx, s_chgup
		rep cmpsb		; Is it %CHGUP.
		jz get_chgup		; Yes.

		jmp chglo		; No. Jump out.

get_chgup:
		mov v_chgup, 0ffh
		pop si
		pop di
		inc si
		jmp data_end

;		Handle %chglo.
chglo:
		pop si
		push si
		mov di, si		; Set destination.
		mov si, offset l_chglo	; Set Source.
		mov cx, s_chglo
		rep cmpsb		; Is it %chglo.
		jz get_chglo		; Yes.

		pop si
		push si
		mov di, si		; Set destination.
		mov si, offset u_chglo	; Set Source.
		mov cx, s_chglo
		rep cmpsb		; Is it %CHGLO.
		jz get_chglo		; Yes.

		jmp logical		; No. Jump out.

get_chglo:
		mov v_chglo, 0ffh
		pop si
		pop di
		inc si
		jmp data_end

;		handle %{operator}
logical:
		pop si
		push si
		lodsb			; Get a byte.
		mov ah, al		; First byte.				
		lodsb			; Second byte.

		mov dx, l_eq		; Is
		cmp ax, dx		;   it
		jz equal		;     eq.
		mov dx, u_eq		; Is
		cmp ax, dx		;   it
		jz equal		;     EQ.

		mov dx, l_lt		; Is
		cmp ax, dx		;   it
		jz less_than		;     lt.
		mov dx, u_lt		; Is
		cmp ax, dx		;   it
		jz less_than		;     LT.

		mov dx, l_le		; Is
		cmp ax, dx		;   it
		jz less_equal		;     le.
		mov dx, u_le		; Is
		cmp ax, dx		;   it
		jz less_equal		;     LE.

		mov dx, l_gt		; Is
		cmp ax, dx		;   it
		jz greater_than		;     gt.
		mov dx, u_gt		; Is
		cmp ax, dx		;   it
		jz greater_than		;     GT.

		mov dx, l_ge		; Is
		cmp ax, dx		;   it
		jz greater_equal	;     ge.
		mov dx, u_ge		; Is
		cmp ax, dx		;   it
		jz greater_equal	;     GE.

		pop si
		pop di
		dec si
		jmp get_data		; No. Jump out.


equal:
		mov e_oper, 0ffh	; Set for equal.
		jmp arg_one

less_equal:
		mov e_oper, 0ffh	; Set for equal.
less_than:
		mov l_oper, 0ffh	; Set for less than.
		jmp arg_one

greater_equal:
		mov e_oper, 0ffh	; Set for equal.
greater_than:
		mov g_oper, 0ffh	; Set for greater than.
		jmp arg_one

;		Get argument one.
arg_one:
		lodsb			; Get a byte.
		cmp al, " "		; Is it a space.
		jz test_arg1
		cmp al, 09h		; Is it a tab.
		jz test_arg1
		cmp al, "'"		; Is it a quote.
		jz quote1
		cmp al, '"'		; Is it a quote.
		jz quote1

		jmp log_exit

quote1:
		mov di, offset d_arg1	; Address of argument one.
		call get_quote		; Get the quoted string.

		cmp exit_code, 06h	; Unmatched quote?
		jz test_end

		mov s_arg1, cx		; Save the size.
		jmp arg_two

test_arg1:
		lodsb			; Get a byte.
		cmp al, " "		; Is it a space.
		jz test_arg1
		cmp al, 09h		; Is it a tab.
		jz test_arg1
		cmp al, "'"		; Is it a quote.
		jz quote1
		cmp al, '"'		; Is it a quote.
		jz quote1
		cmp al, 0dh		; Is it a carriage return.
		jz no_argum

		dec si
		call get_var0		; Get the variable.

		cmp exit_code, 08h	; Variable not found?
		jz test_end

		dec si
		mov di, offset d_arg1	; Set argument one.
		call copy_env		; Copy the data out.
		mov s_arg1, cx		; Save the size.

;		Get argument two
arg_two:
		lodsb			; Get a byte.
		cmp al, " "		; Is it a space.
		jz arg_two
		cmp al, 09h		; Is it a tab.
		jz arg_two
		cmp al, "'"		; Is it a quote.
		jz quote2
		cmp al, '"'		; Is it a quote.
		jz quote2

		cmp al, 0dh		; Is it a carriage return.
		jnz test_arg2
no_argum:
		mov cs:[exit_code], 07h	; Not a valid argument.
		jmp test_end		; Exit.		

test_end:
		mov al, "E"		; Error status.
		jmp log_end

quote2:
		mov di, offset d_arg2	; Address of argument two.
		call get_quote		; Get the quoted string.

		cmp exit_code, 06h	; Unmatched quote?
		jz test_end

		mov s_arg2, cx		; Save the size.
		jmp log_test

test_arg2:
		dec si
		call get_var0		; Get the variable.

		cmp exit_code, 08h	; Variable not found?
		jz test_end

		mov di, offset d_arg2	; Set argument two.
		call copy_env		; Copy the data out.
		mov s_arg2, cx		; Save the size.

log_test:
		mov ax, offset d_arg1	; Argument one.
		mov cx, s_arg1		; Argument size.
		add ax, cx		; At end of argument.
		mov si, ax		; Set source.

		mov ax, offset d_arg2	; Argument two.
		mov dx, s_arg2		; Argument size.
		add ax, dx		; At end of argument.
		mov di, ax		; Set destination.

log_loop:
		cmp cx, 00h		; Arg1 ran out of bytes.
		jz log_arg1

		cmp dx, 00h		; Arg2 ran out of bytes.
		jz log_arg2

		dec si
		mov al, [si]		; Get an arg1 byte.
		dec cx

		dec di
		mov bl, [di]		; Get an arg2 byte.
		dec dx

		cmp al, bl		; Are the equal?
		jz log_loop		; Yes. Loop.

		jg log_great		; Go to test for greater than.

		cmp l_oper, 0ffh	; Less than operator?
		jz log_true		; Yes. Then true.

		jmp log_false		; No. Than false.

log_arg1:
		cmp dx, 00h		; Arg2 ran out of bytes.
		jz log_equal

		cmp g_oper, 0ffh	; Greater than operator?
		jz log_true		; Yes. Then true.
		jmp log_false

log_arg2:
		cmp l_oper, 0ffh	; Less than operator?
		jz log_true		; Yes. Then true.
		jmp log_false

log_equal:
		cmp e_oper, 0ffh	; Equal operator?
		jz log_true		; Yes. Then true.
		jmp log_false

log_great:
		cmp g_oper, 0ffh	; Greater than operator?
		jz log_true		; Yes. Then true.
log_false:
		mov al, "F"		; Test is false.
		jmp log_end

log_true:
		mov al, "T"		; Test is true.

log_end:
		pop si
		push si
		dec si
		mov di, si		; Move
		stosb			;   in
		mov al, 0dh		;     the
		stosb			;        status.

log_exit:
		pop si
		pop di
		dec si
		jmp get_data		; No. Jump out.


;		Get the quoted string
get_quote:
		mov cx, 00h		; Zero the counter.
		mov dl, al		; Save the single quote.

get_quote1:
		lodsb			; Get a byte.
		cmp al, " "		; Is it a space.
		jz get_quote1
		cmp al, 09h		; Is it a tab.
		jz get_quote1
		dec si

get_quote2:
		lodsb			; Get a byte.
		cmp al, dl		; Is it the end.
		jz get_quote4

		cmp al, 0dh		; Is it the end of the command.
		jz get_quote3

		stosb			; Save the byte.
		inc cx			; Count the byte.
		jmp get_quote2

get_quote3:
		mov cs:[exit_code], 06h	; Unmatched delimter.

get_quote4:
		ret


;		Get variable data
get_var0:
		mov cx, 00h		; Zero the counter.
		mov s_env, cx		; Zero for use.
		mov di, offset n_env	; Address of variable.

get_var1:
		lodsb			; Get a byte.
		cmp al, " "		; Is it the end of the variable?
		jz get_var2		; Yes.  Jump out.
		cmp al, 09h		; Is it the end of the variable?
		jz get_var2		; Yes.  Jump out.
		cmp al, 0dh		; Is it a carriage return.
		jz get_var2

		call uppercase		; Convert to uppercase.
		stosb			; Save the byte.
		inc cx			; Count the byte.
		jmp get_var1

get_var2:
		cmp cx, 00h		; Is there a variable name?
		jz get_var3

		mov s_env, cx		; Variable name length.
		call loc_env		; Get env variable base/offset.
		cmp f_env, 00h		; Did we find the variable?
		jz get_var4

get_var3:
		mov cs:[exit_code], 08h	; Variable not found.

get_var4:
		ret


percent:
		mov al, 1[si]
		cmp al, "+"		; Is it a plus.
		jz off_drive

		cmp al, "-"		; Is it a minus.
		jnz cwd

;		Offset from a given drive.
off_drive:
		mov dh, al		; Save the sign.
		mov dl, 2[si]
		cmp dl, "0"		; Is
		jb off_drive0		;   it
		cmp dl, "9"		;     a
		jg off_drive0		;       number.
		sub dl, 30h		; Make it binary.

		mov ah, 19h		; Get the disk drive.
		int 21h

		add al, "a"		; Make it a letter.
		cmp dh, "+"
		jz off_drive1
		sub al, dl		; Subtract from current drive.
		cmp al, "a"		; If less then A
		jge off_drive2		;   we have an error.
let_error:
		mov exit_code, 09h	; Offset not valid.
		mov al, "%"		; Put percent back.
off_drive0:
		jmp get_next

off_drive1:
		add al, dl		; Add from current drive.
		cmp al, "z"		; If greater then Z
		jg let_error		;   we have an error.

off_drive2:
		stosb			; Save the letter.
		inc si			; Skip
		inc si			;   over
		inc si			;     %+n, or %-n.

		jmp get_data


;		Handle %cwd.
cwd:
		push di
		push si

		mov di, si		; Set destination.
		inc di
		mov si, offset l_cwd	; Set Source.
		mov cx, s_cwd
		rep cmpsb		; Is it %cwd.
		jz get_cwd		; Yes.

		pop si
		push si
		mov di, si		; Set destination.
		inc di
		mov si, offset u_cwd	; Set source.
		mov cx, s_cwd
		rep cmpsb		; Is it %CWD.
		jz get_cwd		; Yes.

		pop si
		pop di
		jmp cfd			; No. Jump out.

get_cwd:
		mov bx, di
		pop si
		pop di
		mov si, bx

		mov al, "\"		; Put in the
		stosb			;   root backslash.
		push si
		mov si, offset d_cwd	; Place to put the directory name.

		mov ah, 47h		; Get current directory.
		mov dl, 00h		; Current disk drive.
		int 21h

cwd1:
		lodsb			; Find the
		cmp al, 00h		;   string
		jz cwd2			;     terminator (zero).

		stosb			; Store it.
		jmp cwd1

cwd2:
		pop si
		jmp get_data


;		Handle %cfd.
cfd:
		push di
		push si

		mov di, si		; Set destination.
		inc di
		mov si, offset l_cfd	; Set Source.
		mov cx, s_cfd
		rep cmpsb		; Is it %cfd.
		jz get_cfd		; Yes.

		pop si
		push si
		mov di, si		; Set destination.
		inc di
		mov si, offset u_cfd	; Set source.
		mov cx, s_cfd
		rep cmpsb		; Is it %CFD.
		jz get_cfd		; Yes.

		pop si
		pop di
		jmp dosv		; No. Jump out.

get_cfd:
		mov bx, di
		pop si
		pop di
		mov si, bx

		push si
		mov si, offset d_cwd	; Place to put the directory name.

		mov ah, 47h		; Get current directory.
		mov dl, 00h		; Current disk drive.
		int 21h

		mov al, [si]		; Is it the
		cmp al, 00h		;   string
		jz cfd2			;     terminator (zero).

		mov al, "\"		; Put in the
		stosb			;   root backslash.
cfd1:
		lodsb			; Find the
		cmp al, 00h		;   string
		jz cfd2			;     terminator (zero).

		stosb			; Store it.
		jmp cfd1

cfd2:
		mov al, "\"		; Put in the
		stosb			;   end backslash.
		pop si
		jmp get_data


;		Handle %dosv.
dosv:
		push di
		push si

		mov di, si		; Set destination.
		inc di
		mov si, offset l_dosv	; Set Source.
		mov cx, s_dosv
		rep cmpsb		; Is it %dosv.
		jz get_dosv		; Yes.

		pop si
		push si
		mov di, si		; Set destination.
		inc di
		mov si, offset u_dosv	; Set source.
		mov cx, s_dosv
		rep cmpsb		; Is it %DOSV.
		jz get_dosv		; Yes.

		pop si
		pop di
		jmp dosm		; No. Jump out.

get_dosv:
		mov bx, di
		pop si
		pop di
		mov si, bx

		mov ah, 30h		; Get DOS version number.
		int 21h

		add al, "0"		; Make it a number.
		stosb			; Store it.

		jmp get_data


;		Handle %dosm.
dosm:
		push di
		push si

		mov di, si		; Set destination.
		inc di
		mov si, offset l_dosm	; Set Source.
		mov cx, s_dosm
		rep cmpsb		; Is it %dosm.
		jz get_dosm		; Yes.

		pop si
		push si
		mov di, si		; Set destination.
		inc di
		mov si, offset u_dosm	; Set source.
		mov cx, s_dosm
		rep cmpsb		; Is it %DOSM.
		jz get_dosm		; Yes.

		pop si
		pop di
		jmp drive		; No. Jump out.

get_dosm:
		mov bx, di
		pop si
		pop di
		mov si, bx

		mov ah, 30h		; Get DOS version number.
		int 21h

		mov al, ah		; Get minor version.
		cmp al, 0ah		; Is it less then ten.
		jb dosm0		; Yes. Jump out.

		call ascii		; Do the minor version
		jmp dosm1
dosm0:
		add al, "0"		; Make it a number.
		stosb			; Store it.

dosm1:
		jmp get_data

;		Handle %drive.
drive:
		push di
		push si

		mov di, si		; Set destination.
		inc di
		mov si, offset l_drive	; Set Source.
		mov cx, s_drive
		rep cmpsb		; Is it %drive.
		jz get_drive		; Yes.

		pop si
		push si
		mov di, si		; Set destination.
		inc di
		mov si, offset u_drive	; Set source.
		mov cx, s_drive
		rep cmpsb		; Is it %DRIVE.
		jz get_drive		; Yes.

		pop si
		pop di
		jmp time		; No. Jump out.

get_drive:
		mov bx, di
		pop si
		pop di
		mov si, bx

		mov ah, 19h		; Get the disk drive.
		int 21h

		add al, "a"		; Make it a letter.
		stosb			; Store it.

		jmp get_data


;		Handle %time.
time:
		push di
		push si

		mov di, si		; Set destination.
		inc di
		mov si, offset l_time	; Set Source.
		mov cx, s_time
		rep cmpsb		; Is it %time.
		jz get_time		; Yes.

		pop si
		push si
		mov di, si		; Set destination.
		inc di
		mov si, offset u_time	; Set source.
		mov cx, s_time
		rep cmpsb		; Is it %TIME.
		jz get_time		; Yes.

		pop si
		pop di
		jmp date		; No. Jump out.

get_time:
		mov bx, di
		pop si
		pop di
		mov si, bx

		mov ah, 2ch		; Get the
		int 21h			;   current time.

		mov bh, ":"		; Separator.
		mov al, ch		; Do the
		call ascii		;   hours.
		mov al, bh		; Do the
		stosb			;   separator.
		mov al, cl		; Do the
		call ascii		;   minutes.
		mov al, bh		; Do the
		stosb			;   separator.
		mov al, dh		; Do the
		call ascii		;   seconds.
		
		jmp get_data


;		Handle %date.
date:
		push di
		push si

		mov di, si		; Set destination.
		inc di
		mov si, offset l_date	; Set Source.
		mov cx, s_date
		rep cmpsb		; Is it %date.
		jz get_date		; Yes.

		pop si
		push si
		mov di, si		; Set destination.
		inc di
		mov si, offset u_date	; Set source.
		mov cx, s_date
		rep cmpsb		; Is it %DATE.
		jz get_date		; Yes.

		pop si
		pop di
		jmp character		; No. Jump out.

get_date:
		mov bx, di
		pop si
		pop di
		mov si, bx

		lodsb			; Get a byte.
		cmp al, "1"		; Format one.
		jz date1		; Yes. Jumpout.
		cmp al, "2"		; Format two.
		jz date1		; Yes. Jumpout.
		cmp al, "3"		; Format three.
		jz date1		; Yes. Jumpout.
		dec si
		mov al, z_date		; Get the default.
date1:
		mov z_date, al		; Save format.
		mov bh, "-"		; Separator.
		
		mov ah, 2ah		; Get the
		int 21h			;   current date.

		cmp z_date, "3"
		jnz date2
		sub cx, 1900		; Remove the century.
		mov al, cl		; Do the
		call ascii		;   year.
		mov al, bh		; Do the
		stosb			;   separator.
date2:
		cmp z_date, "1"
		jz date3
		mov al, dh		; Do the
		call ascii		;   month.
		mov al, bh		; Do the
		stosb			;   separator.
date3:
		mov al, dl		; Do the
		call ascii		;   Day.

		cmp z_date, "3"
		jz date9
		mov al, bh		; Do the
		stosb			;   separator.

		cmp z_date, "2"
		jz date4

		mov al, dh		; Do the
		call ascii		;   month.
		mov al, bh		; Do the
		stosb			;   separator.
date4:
		mov al, dh		; Do the
		sub cx, 1900		; Remove the century.
		mov al, cl		; Do the
		call ascii		;   year.

date9:		
		jmp get_data


;		convert a binary number to two digit decimal.
ascii:
		mov bl, 0ah		; Divide
		cbw			;   by
		div bl			;     ten.
		add al, "0"		; Make it a number.
		stosb			; Store tens.
		add ah, "0"		; Make it a number.
		mov al, ah
		stosb			; Store ones.
		ret


;		Handle %chr.
character:
		push di
		push si

		mov di, si		; Set destination.
		inc di
		mov si, offset l_chr	; Set Source.
		mov cx, s_chr
		rep cmpsb		; Is it %chr.
		jz get_character	; Yes.

		pop si
		push si
		mov di, si		; Set destination.
		inc di
		mov si, offset u_chr	; Set Source.
		mov cx, s_chr
		rep cmpsb		; Is it %CHR.
		jz get_character	; Yes.

		pop si
		pop di
		jmp element		; No. Jump out.

get_character:
		mov bx, di
		pop si
		pop di
		mov si, bx

		mov ax, 00h
		mov s_env, ax		; Zero for use.

		lodsb			; Get the delimiter.
		mov dl, al		; Save it.
		push di

		call p_numb		; Read number.
		mov n_chr, cx		; Save element number.

		pop di
		cmp cx, 00h		; Is it going to be a null.
		jz character1		; No. Error.

		cmp cx, 0ffh		; Is it valid?
		jbe character2		; Yes. Jump out.

character1:
		mov cs:[exit_code], 09h	; Range error.
		jmp nor_term		; Exit.

character2:
		mov al, cl		; Character is okay.
		stosb			; Save the byte.
		jmp get_data


;		Handle %elem.
element:
		push di
		push si

		mov di, si		; Set destination.
		inc di
		mov si, offset l_elem	; Set Source.
		mov cx, s_elem
		rep cmpsb		; Is it %elem.
		jz get_element		; Yes.

		pop si
		push si
		mov di, si		; Set destination.
		inc di
		mov si, offset u_elem	; Set Source.
		mov cx, s_elem
		rep cmpsb		; Is it %ELEM.
		jz get_element		; Yes.

		pop si
		pop di
		jmp substr		; No. Jump out.

get_element:
		mov bx, di
		pop si
		pop di
		mov si, bx

		mov ax, 00h
		mov e_elem, ax		; Zero
		mov c_elem, al		;   values
		mov s_env, ax		;     for use.

		lodsb			; Get the delimiter.
		mov dl, al		; Save it.
		push di

		call p_numb		; Read number.
		mov e_elem, cx		; Save element number.

element1:
		lodsb			; Get a byte.
		cmp al, 0dh		; Is it a return?
		jz element2		; Yes Jump out.

		cmp al, dl		; Is it a delimiter?
		jz element3		; Yes. Jump out.
		mov c_elem, al		; Save element delimiter.
		jmp element1

element2:
		pop di
		dec si
		mov cs:[exit_code], 06h	; Unmatched delimter.
		jmp nor_term		; Exit.

element3:
		mov di, offset n_env	; Store variable name here.
		call p_var		; Read a string.
		mov s_env, cx		; Save the size.
		pop di

		call loc_env		; Get variable offset
		cmp f_env, 00h		; Did we find the variable?
		jz element9

		dec si
		mov cs:[exit_code], 08h	; Variable not found.
		jmp nor_term		; Exit.		

element9:
		mov dx, e_elem		; Element number.
		mov cx, 00h

		mov ax, o_env		; Environment offset.
		mov bx, b_env		; Environment base.
		push si
		push ds
		mov si, ax		; Set for the
		mov ds, bx		;   environment.

		cmp cx, dx		; Is it the number 0.
		jz element11		; Yes. Get it.

element10:
		lodsb			; Get a byte.
		cmp al, 00h		; At end of variable string?
		jz element12		; Yes. Jump out.

		cmp al, cs:[c_elem]	; Is it a delimiter?
		jnz element10		; No. Loop.

		inc cx
		cmp cx, dx		; Is it the right one.
		jnz element10		; No. Loop.

element11:
		lodsb			; Get a byte.
		cmp al, 00h		; At end of variable string?
		jz element13		; Yes. Jump out.

		cmp al, cs:[c_elem]	; Is it a delimiter?
		jz element13		; No. Loop.

		stosb			; Save the byte.
		jmp element11

element12:
		pop ds
		pop si
		mov cs:[exit_code], 09h	; Offset not valid.
		jmp nor_term		; Exit.		

element13:
		pop ds
		pop si

		jmp get_data


;		Handle %mid.
substr:
		push di
		push si

		mov di, si		; Set destination.
		inc di
		mov si, offset l_mid	; Set Source.
		mov cx, s_mid
		rep cmpsb		; Is it %mid.
		jz get_substr		; Yes.

		pop si
		push si
		mov di, si		; Set destination.
		inc di
		mov si, offset u_mid	; Set Source.
		mov cx, s_mid
		rep cmpsb		; Is it %MID.
		jz get_substr		; Yes.

		pop si
		pop di
		jmp locate		; No. Jump out.

get_substr:
		mov bx, di
		pop si
		pop di
		mov si, bx

		mov ax, 00h
		mov y_mid, ax		; Zero
		mov z_mid, ax		;   values
		mov s_env, ax		;     for use.

		lodsb			; Get the delimiter.
		mov dl, al		; Save it.
		push di

		mov di, offset n_env	; Store variable name here.
		call p_var		; Read a string.
		mov s_env, cx		; Variable length.

		call p_numb		; Read number.
		mov y_mid, cx		; Save start postion.

		call p_numb		; Read number.
		mov z_mid, cx		; Save length to get.
		pop di

		call loc_env		; Get variable offset
		cmp f_env, 00h		; Did we find the variable?
		jz substr9

		mov cs:[exit_code], 08h	; Variable not found.
		jmp nor_term		; Exit.		

substr9:
		mov cx, z_mid		; Length of substr.
		cmp cx, 00h		; If not zero.
		jnz substr10

		mov cx, 0ffh		; Make it large.
substr10:
		mov dx, y_mid		; Starting position.
		cmp dx, 00h		; If not zero.
		jnz substr11

		add dx, 01h		; Make it one.
substr11:
		mov ax, o_env		; Get env. variable offset.
		mov bx, ax		; Add starting
		add bx, dx		;   position.

		mov dx, b_env		; Get env. base address.
		push si	
		push ds
		mov si, ax
		mov ds, dx

substr12:
		lodsb			; Get a byte.
		cmp al, 00h		; At end of variable data?
		jz substr13

		cmp si, bx		; At starting position?
		jz substr14
		jmp substr12

substr13:
		pop ds
		pop si
		mov cs:[exit_code], 09h	; Offset not valid.
		jmp nor_term		; Exit.		

substr14:
		dec si

substr15:
		lodsb			; Get a byte.
		cmp al, 00h		; At end of variable data?
		jz substr16

		cmp cx, 00h		; At end of length?
		jz substr16

		stosb			; Move the byte.
		dec cx			; One less byte to check.
		jmp substr15

substr16:
		pop ds
		pop si

		jmp get_data


;		Handle %loc.
locate:
		push di
		push si

		mov di, si		; Set destination.
		inc di
		mov si, offset l_loc	; Set Source.
		mov cx, s_loc
		rep cmpsb		; Is it %loc.
		jz get_locate		; Yes.

		pop si
		push si
		mov di, si		; Set destination.
		inc di
		mov si, offset u_loc	; Set Source.
		mov cx, s_loc
		rep cmpsb		; Is it %LOC.
		jz get_locate		; Yes.

		pop si
		pop di
		jmp length0		; No. Jump out.

get_locate:
		mov bx, di
		pop si
		pop di
		mov si, bx

		mov ax, 00h
		mov e_loc, ax		; Zero
		mov s_env, ax		;   values
		mov w_mid, ax		;     for use.

		lodsb			; Get the delimiter.
		mov dl, al		; Save it.
		push di

		call p_numb		; Read number.
		mov e_loc, cx		; Save starting position.

		mov di, offset n_env	; Store variable name here.
		call p_var		; Read a string.
		mov s_env, cx		; Variable length.

		mov di, offset x_mid	; Store search string.
		call p_str		; Read a string.
		mov w_mid, cx		; Variable length.
		pop di

		call loc_env		; Get variable offset
		cmp f_env, 00h		; Did we find the variable?
		jz locate9

		mov cs:[exit_code], 08h	; Variable not found.
		jmp nor_term		; Exit.		

locate9:
		mov dx, e_loc		; Starting position.
		cmp dx, 00h		; If not zero.
		jnz locate10

		add dx, 01h		; Make it one.
locate10:
		mov ax, o_env		; Get env. variable offset.
		mov bx, ax		; Add starting
		add bx, dx		;   position.

		mov dx, b_env		; Get env. base address.
		push si	
		push ds
		mov si, ax
		mov ds, dx

locate11:
		lodsb			; Get a byte.
		cmp al, 00h		; At end of variable data?
		jz locate12

		cmp si, bx		; At starting position?
		jz locate13
		jmp locate11

locate12:
		pop ds
		pop si
		mov ax, 00h		; Set for not found.
		jmp locate17

locate13:
		dec si
		push di

locate14:
		mov al, [si]		; Get a test byte.
		cmp al, 00h		; At end of variable data?
		jz locate15

		push si

		mov di, offset cs:[x_mid] ; Set destination.
		mov cx, cs:[w_mid]	; Set Length.
		rep cmpsb		; Is it the string.
		jz locate16		; Yes. Jump out.

		pop si			; Get offset back.
		inc si			; Move on a byte.
		jmp locate14

locate15:
		pop di
		jmp locate12


locate16:
		mov ax, si		; Save the offset
		pop si
		pop di

		pop ds
		pop si

		mov bx, o_env		; Subtract env data
		sub ax, bx		;   start from current location.
		mov bx, w_mid		; Subtract
		sub ax, bx		;   search length.
		inc ax

locate17:
		call dec_str

		jmp get_data


;		Handle %len.
length0:
		push di
		push si

		mov di, si		; Set destination.
		inc di
		mov si, offset l_len	; Set Source.
		mov cx, s_len
		rep cmpsb		; Is it %len.
		jz get_length		; Yes.

		pop si
		push si
		mov di, si		; Set destination.
		inc di
		mov si, offset u_len	; Set Source.
		mov cx, s_len
		rep cmpsb		; Is it %LEN.
		jz get_length		; Yes.

		pop si
		pop di
		jmp sum_var		; No. Jump out.

get_length:
		mov bx, di
		pop si
		pop di
		mov si, bx

		mov ax, 00h
		mov s_env, ax		; Zero for use.

		lodsb			; Get the delimiter.
		mov dl, al		; Save it.
		push di

		mov di, offset n_env	; Store variable name here.
		call p_var		; Read a string.
		mov s_env, cx		; Variable length.
		pop di

		call loc_env		; Get variable offset
		cmp f_env, 00h		; Did we find the variable?
		jz length9

		mov cs:[exit_code], 08h	; Variable not found.
		jmp nor_term		; Exit.		

length9:
		mov ax, o_env		; Get env. variable offset.
		mov dx, b_env		; Get env. base address.
		mov cx, 00h

		push si	
		push ds
		mov si, ax
		mov ds, dx

length10:
		lodsb			; Get a byte.
		cmp al, 00h		; At end of variable data?
		jz length11

		inc cx			; Count the bytes.
		jmp length10

length11:
		pop ds
		pop si

		mov ax, cx		; Save length.
		call dec_str

		jmp get_data


;		Handle %sum.
sum_var:
		push di
		push si

		mov di, si		; Set destination.
		inc di
		mov si, offset l_sum	; Set Source.
		mov cx, s_sum
		rep cmpsb		; Is it %sum.
		jz get_sum_var		; Yes.

		pop si
		push si
		mov di, si		; Set destination.
		inc di
		mov si, offset u_sum	; Set Source.
		mov cx, s_sum
		rep cmpsb		; Is it %SUM.
		jz get_sum_var		; Yes.

		pop si
		pop di
		jmp get_next		; No. Jump out.

get_sum_var:
		mov bx, di
		pop si
		pop di
		mov si, bx

		mov ax, 00h
		mov s_env, ax		; Zero for use.

		lodsb			; Get the delimiter.
		mov dl, al		; Save it.
		push di

		mov di, offset n_env	; Store variable name here.
		call p_var		; Read a string.
		mov s_env, cx		; Variable length.

		mov nmb_tst, 0ffh	; Set to test sign.
		call p_numb		; Read number.
		mov v_sum, cx		; Save sum number.
		mov nmb_tst, 00h	; Remove test for sign.
		pop di

		call loc_env		; Get variable offset
		cmp f_env, 00h		; Did we find the variable?
		jz sum_var9

		mov cs:[exit_code], 08h	; Variable not found.
		jmp nor_term		; Exit.		

sum_var9:
		mov ax, o_env		; Get env. variable offset.
		mov dx, b_env		; Get env. base address.
		mov cx, 00h
		mov f_sum, "+"		; Set for add.

		push si	
		push ds
		mov si, ax
		mov ds, dx

sum_var10:
		lodsb			; Get a byte.
		cmp al, " "		; Leading space.
		jz sum_var10		; Then remove it.

		cmp al, 09h		; Leading tab.
		jz sum_var10		; Then remove it.

		cmp al, "+"		; Is it an add.
		jz sum_var12

		cmp al, "-"		; Is it an minus.
		jnz sum_var11
		mov cs:[f_sum], "-"
		jmp sum_var12

sum_var11:
		dec si
sum_var12:
		lodsb			; Get a byte.
		cmp al, "9"		; Is
		jg sum_var14		;   it
		cmp al, "0"		;     numeric?
		jb sum_var14		; No. Then error.

		mov ah, 00h
		sub al, 30h		; Make it binary.
		mov bx, ax		; Save next digit.
		mov ax, cx		; Get the number.
		push dx
		push bx
		mov bx, 0ah		; Multiply 
		mul bx			;   by ten.
		pop bx
		pop dx
		add ax, bx		; Add next digit.
		mov cx, ax		; Save number.
		jmp sum_var12

sum_var13:
		lodsb			; Get a byte.
sum_var14:
		cmp al, 00h		; At end of variable data?
		jz sum_var15

		cmp al, " "		; Leading space.
		jz sum_var13		; Then remove it.

		cmp al, 09h		; Leading tab.
		jz sum_var13		; Then remove it.

		pop di
		dec si
		mov cs:[exit_code], 07h	; Argument not a number.
		jmp nor_term		; Exit.

sum_var15:		
		pop ds
		pop si

		mov ax, cx		; Value of the variable.
		mov cx, v_sum		; Value to add.

		cmp nmb_sgn, "+"	; Is the number positive?
		jz sum_var16

		cmp f_sum, "+"		; Is the variable positive?
		jz sum_var17

		push ax
		mov al, "-"		; Write out
		stosb			;   the sign.
		pop ax
		jmp sum_var21

sum_var16:
		cmp f_sum, "+"		; Is the variable positive?
		jz sum_var21

sum_var17:
		cmp ax, cx		; Is the number gt the variable?
		jge sum_var20

		mov dx, cx		; Then
		mov cx, ax		;   switch
		mov ax, dx		;     them arround.

		cmp nmb_sgn, "+"	; Is the number positive?
		jz sum_var19
sum_var18:
		push ax
		mov al, "-"		; Write out
		stosb			;   the sign.
		pop ax				

sum_var19:
		sub ax, cx		; Subtract the difference.
		jmp sum_var22

sum_var20:
		cmp f_sum, "+"		; Is the variable positive?
		jz sum_var19		

		cmp ax, cx		; No sign if equal.
		jz sum_var19
		jmp sum_var18

sum_var21:
		add ax, cx		; Add the number to variable.

sum_var22:
		call dec_str		; Convert to decimal string.

		jmp get_data


;		parse a number from the command line
p_numb:
		lodsb			; Get a byte.
		cmp al, " "		; Leading space.
		jz p_numb		; Then remove it.

		cmp al, 09h		; Leading tab.
		jz p_numb		; Then remove it.

		mov cx, 00h
		cmp nmb_tst, 00h	; Do we test for sign.
		jz p_numb1

		mov nmb_sgn, "+"	; Set initial sign.
		cmp al, "+"		; Is it an add.
		jz p_numb2

		cmp al, "-"		; Is it an minus.
		jnz p_numb1
		mov nmb_sgn, "-"	; Save the sign.
		jmp p_numb2

p_numb1:
		dec si

;		The maximum element is 255.
p_numb2:
		lodsb			; Get a byte.
		cmp al, "9"		; Is
		jg p_numb4		;   it
		cmp al, "0"		;     numeric?
		jb p_numb4		; No. Then error.

		mov ah, 00h
		sub al, 30h		; Make it binary.
		mov bx, ax		; Save next digit.
		mov ax, cx		; Get the number.
		push dx
		push bx
		mov bx, 0ah		; Multiply 
		mul bx			;   by ten.
		pop bx
		pop dx
		add ax, bx		; Add next digit.
		mov cx, ax		; Save number.
		jmp p_numb2

p_numb3:
		lodsb			; Get a byte.
p_numb4:
		cmp al, 0dh		; Is it the end?
		jz p_numb5		; Yes Jump out.

		cmp al, dl		; Digits over?.
		jz p_numb6		; Yes Jump out.

		cmp al, " "		; Leading space.
		jz p_numb3		; Then remove it.

		cmp al, 09h		; Leading tab.
		jz p_numb3		; Then remove it.

		pop di
		dec si
		mov cs:[exit_code], 07h	; Argument not a number.
		jmp nor_term		; Exit.

p_numb5:
		pop di
		dec si
		mov cs:[exit_code], 06h	; Unmatched delimter.
		jmp nor_term		; Exit.

p_numb6:
		ret


;		parse the variable from the command line
p_str:
		mov cx, 00h

p_str1:
		lodsb			; Get a byte.
		cmp al, 0dh		; Is it the end?
		jz p_numb5		; Yes Jump out.

		cmp al, dl		; Is it a delimiter?
		jz p_str3		; Yes. Jump out.

p_str2:
		stosb			; Save it.
		inc cx			; Increment the count.
		jmp p_str1

p_str3:
		ret


;		parse a string from the command line
;		and convert lower to upper
p_var:
		lodsb			; Get a byte.
		cmp al, " "		; Leading space.
		jz p_var		; Then remove it.

		cmp al, 09h		; Leading tab.
		jz p_var		; Then remove it.

		dec si
		mov cx, 00h

p_var1:
		lodsb			; Get a byte.
		cmp al, 0dh		; Is it the end?
		jz p_numb5		; Yes Jump out.

		cmp al, dl		; Is it a delimiter?
		jz p_var2		; Yes. Jump out.

		cmp al, " "		; Leading space.
		jz p_var1		; Then remove it.

		cmp al, 09h		; Leading tab.
		jz p_var1		; Then remove it.

		call uppercase		; Convert to uppercase.
		stosb			; Save it.
		inc cx			; Increment the count.
		jmp p_var1

p_var2:
		ret


;		End of data now finish it.
data_end:
		mov al, 00h		; Save
		stosb			;   two
		stosb			;     nulls.
		mov ax, di		; Get the offset.
		mov bx, offset d_env_var ; The beginng of the variable.
		sub ax, bx		; Get the data length.
		mov s_data, ax		; And save it.

		cmp v_rep, 0ffh		; Are we going to replace.
		jz get_psp		; Yes. Jump.
		cmp v_rce, 00h		; Are we going to do root.
		jz get_psp		; No.  Jump over.
		jmp set_root

get_psp:
		push ds			; Save the segment base.

;		PSP of program.
		mov si, 16h
		mov ax, [si]

;		Parent PSP Segment of program.
		mov ds, ax		; Set the data segment.
		mov si, 16h		; Parent PSP Segment.
		mov ax, [si]

;		Parent PSP Segment of command.com.
		mov ds, ax		; Set the data segment.
		mov si, 16h		; Parent PSP Segment.
		mov ax, [si]
		mov cs:[cpsp], ax

;		Check if the Environment Address is usable.
		mov si, 2ch		; Environment space
		mov bx, [si]		;   of command.com.
		cmp bx, 00h		; If it's zero we
		jz zero_add		;   do a long search.

		sub bx, 01h		; We will have to back
		mov ax, bx		;   up to env. definition.

zero_add:
;		Segment where the environment space is.
		mov ds, ax
		mov si, 00h

get4d:
;		Look for 4dh on segment boundary.
		mov al, [si]
		cmp al, 4dh
		jz got4d

inc_seg:
		mov ax, si		; Increment
		add ax, 10h		;   to the next
		mov si, ax		;      segment boundary.

;		Start of Stop the program from searching for ever.
		cmp ax, 0f000h		; Have we gone way passed 64k?
		jb get4d		; No. Continue.

		pop ds			; Restore the data segment.
		push ds			; Save the segment base.
		mov dx, offset get4d_err
		mov ah, 09h		; display the error.
		int 21h
		mov exit_code, 00h	; No exit.
		jmp nor_term		; Exit.
;		End of Stop the program from searching for ever.

got4d:
		mov ax, 1[si]		; Get the psp.
		cmp cs:[cpsp], ax	; Is it the same as cpsp?
		jnz inc_seg		; No.  Jump out.

		mov ah, 00h
		mov al, 3[si]		; Get the number of segments.
		mov bx, 10h
		mul bx			; Get the number of bytes.
		mov cs:[env_siz], ax	; Save it.

		mov ax, si		; Increment
		add ax, 10h		;   to the environment
		mov si, ax		;      segment boundary.
		mov cs:[env_off], ax	; Save it.

;		Just a double check for environment space.
;		Check for PATH= or PROMPT= or COMPEC= or SETENV=.
		mov ax, ds		; Get the data segment
		mov es, ax		; and put it in extra segment.
		mov di, si		; This is our destination.

		pop ds			; Restore the data segment.
		mov si, offset d_path	; Check
		mov cx, s_path		;   for
		rep cmpsb		;     PATH=.
		jz got_env

		mov di, env_off		; Restore the destination.
		mov si, offset d_prompt	; Check
		mov cx, s_prompt	;   for
		rep cmpsb		;     PROMPT=.
		jz got_env

		mov di, env_off		; Restore the destination.
		mov si, offset d_comspec ;Check
		mov cx, s_comspec	;    for
		rep cmpsb		;      COMSPEC=.
		jz got_env

		mov di, env_off		; Restore the destination.
		mov si, offset d_setenv	; Check
		mov cx, s_setenv	;   for
		rep cmpsb		;     SETENV=.
		jz got_env

		mov di, env_off		; Restore the destination.
		mov si, offset d_config	; Check
		mov cx, s_config	;   for
		rep cmpsb		;     CONFIG=.
		jz got_env

;		Windows NT check for TMP= or TEMP= winbootdir=.
		mov di, env_off		; Restore the destination.
		mov si, offset d_temp	; Check
		mov cx, s_temp		;   for
		rep cmpsb		;     TEMP=.
		jz got_env

		mov di, env_off		; Restore the destination.
		mov si, offset d_tmp	; Check
		mov cx, s_tmp		;   for
		rep cmpsb		;     TMP=.
		jz got_env

		mov di, env_off		; Restore the destination.
		mov si, offset d_winbootdir ; Check
		mov cx, s_winbootdir	;   for
		rep cmpsb		;     winbootdir=.
		jz got_env

		push ds			; Save the data segment.
		mov ax, es		; Get the
		mov ds, ax		;   data segment
		mov si, cs:[env_off]	;     and source
		jmp get4d		;       to scan some more.

got_env:
		push ds			; Save the data segment.
		mov ax, es		; Get the
		mov ds, ax		;   data segment
		mov si, cs:[env_off]	;     and source.

		mov di, offset cs:[d_env_var]	; First character
		mov bl, cs:[di]		;   of environment variable.
		jmp zero1
;		Scan through environment for the end,
;		we need two null bytes.
loop:
		lodsb			; Get a byte.
		cmp al, 00h		; Is it null?
		jz zero1		; Yes. Got first null.
		jmp loop		; Keep looking.

;		Is the variable there?
zero1:
		lodsb			; Get a byte.
		cmp al, 00h		; Is it zero?
		jz write_near		; Yes.  Jump out.
		cmp al, bl		; Is it the env. variable?
		jnz loop		; No.  Jump out.

		dec si			; We are N+1.
		mov ax, ds		; Get the data segment
		mov es, ax		; and put it in extra segment.
		mov di, si		; This is our destination.

		mov dx, si		; Save the address of env. var.
		pop ds			; Restore the data segment.
		mov si, offset d_env_var ; Set the source.
		mov cx, s_env_var	; Length of variable.
		rep cmpsb		; Does it exist?
		jz update

		push ds			; Save the data segment.
		mov ax, es		; Get the extra segment.
		mov ds, ax		; and put it in data segment.
		mov si, di		; Setup the source.
		jmp loop

write_near:
		jmp write
subst_near:
		jmp substitute
change_near:
		jmp change


;		The environment variable is there.
;		Now update the environment variable.
update:
		cmp cs:[v_rep], 0ffh	; Do we replace?
		jz subst_near		; Yes. Go there.

		cmp cs:[v_chgup], 0ffh	; Do we upper case string?
		jz change_near		; Yes. Go there.

		cmp cs:[v_chglo], 0ffh	; Do we lower case string?
		jz change_near		; Yes. Go there.

update0:
		push ds			; Save the data segment.
		mov ax, es		; Get the extra segment.
		mov ds, ax		; and put it in data segment.
		mov di, dx		; This is our destination.
		mov si, di		; Setup the source.
		
update1:
		lodsb			; Skip
		cmp al, 00h		;   over
		jnz update1		;     the variable.

		lodsb			; Look ahead to see
		dec si			;   if the next byte
		cmp al, 00h		;     is zero if not
		jz update3		;       compress the data.

update2:
		lodsb			; Compress
		stosb			;   the data
		cmp al, 00h		;     for a
		jnz update2		;       variable.

		lodsb			; Get a byte.
		dec si
		cmp al, 00h		; Is it zero?
		jnz update2		; No.  Get next.

update3:
		mov al, cs:[exit_code]
		cmp al, 05h
		jg update4
		mov cs:[exit_code], 05h	; Environment variable updated.
update4:
		mov cx, cs:[s_data]	; Length of data.
		mov dx, cs:[s_env_var]	; Length of name.
		sub cx, dx
		sub cx, 02h		; Minus 2 zeroes.
		cmp cx, 00h		; Is it just the name.
		jnz write1		; No. Jump out.

		mov cx, si		; End of old environment.
		mov dx, di		; Current position.
		sub cx, dx
		mov al, 00h		; Zero
		rep stosb		;  the rest.
		mov cs:[exit_code], 02h	; Environment variable erased.
		jmp nor_term

;		The environment variable isn't there.
;		Now move the data into place.
write:
		mov ax, ds		; Get the data segment
		mov es, ax		; and put it in extra segment.
		dec si			; We are N+1.
		mov di, si		; This is our destination.

write1:
		mov cx, cs:[s_data]	; Environment variable & data.
		dec cx			; No
		dec cx			;   nulls.
		mov ax, cs:[s_env_var]	; Environment variable size.
		cmp ax, cx		; Same size.
		jne write2		; No. Jump out.
		mov cs:[exit_code], 08h	; Environment variable not found.
		jmp nor_term

write2:
		pop ds			; Restore the data segment.
		mov si, offset d_env_var ; Set the source.
		mov cx, s_data		; Set the length.
write3:
		mov ah, es:[di]		; Get destination byte.
write4:
		cmp ah, 00h		; Is it a zero?
		jnz not_zero		; No.  Jump out.
		lodsb			; Get byte.
		stosb			; Save byte.
		loop write3		; Loop.

;		Exit the program.
nor_term:
		mov al, cs:[exit_code]	; Set termination code.
		mov ah, 4ch		; Termination.
		int 21h

not_zero:
		mov ax, di		; Get the current offset.
		mov bx, env_off		; Get environment start.
		sub ax, bx		; Subtract the start of env.
		mov bx, env_siz		; Get environment size.
		cmp ax, bx		; Greater the environment size.
		jge reset		; Yes.  Jump.
		mov ah, 00h		; Make it zero.
		mov al, exit_code	; Get exit code.
		cmp al, 05h		; If update then do not
		jmp write4		;   reduce the error code.
;		jge write4		;   reduce the error code.
;		mov exit_code, 04h	; Environment space over written.
;		jmp write4		;   and redo.

reset:
		dec di
		dec di
		mov al, 00h		; Put zero back.
		stosb
		stosb
		mov exit_code, 10h	; Set code for out of env.,
		jmp nor_term		;   and terminate.

;		Replace old text with new text.
substitute:
		push dx
		push ds
		push es
		push si
		push di

		mov ax, es		; Switch
		mov bx, ds		;   the
		mov es, bx		;     segments
		mov ds, ax		;       around.

		mov ax, di		; Switch
		mov di, si		;   the
		mov si, ax		;     registrars.

		mov bx, offset d_arg1	; Get first byte
		mov dl, cs:[bx]		;   of old data.
subst1:
		lodsb			; Get byte.
		cmp al, 00h		; End of variable space.
		jz subst5		; Yes. Jump out.

		cmp al, dl		; First byte of data?
		jz subst3		; Yes. Jump out
subst2:
		stosb			; Save byte.
		jmp subst1

;		Test for old text.
subst3:
		push ds
		push es
		push di
		push si

		mov ax, es		; Switch
		mov bx, ds		;   the
		mov es, bx		;     segments
		mov ds, ax		;       around.

		mov di, si
		dec di			; Data N+1.
		mov si, offset d_arg1	; Old data value.
		mov cx, cs:[s_arg1]	; Old data size.
		rep cmpsb
		jz subst4

		mov al, dl		; Get byte back.
		pop si
		pop di
		pop es
		pop ds
		jmp subst2

;		Put in new text.
subst4:
		pop si
		pop di
		mov ax, ds
		mov es, ax

		mov bx, si
		dec si			; Data N+1.
		push si

		mov si, offset d_arg2	; New data value.
		mov cx, s_arg2		; New data size.
		rep movsb

		pop si
		mov cx, si
		mov ax, s_arg1		; Add size of
		add cx, ax		;   new value.
		mov si, cx		; Advance the size.

		pop es
		pop ds
		jmp subst1

subst5:
		stosb			; Save two
		stosb			;   nulls.
		pop di
		pop si
		pop es
		pop ds

		mov v_rep, 00h		; Turn off replace.
		push si
		push di

		mov si, offset d_env_var
		mov cx, 00h
subst6:
		lodsb			; Get
		inc cx			;   the
		cmp al, 00h		;     size.
		jnz subst6

		inc cx
		mov s_data, cx		; Save the size.

		pop di
		pop si
		pop dx
		jmp update0

;		Replace old text with new text, for case.
change:
		mov ax, es		; Switch
		mov ds, ax		;   the
		mov si, di		;     registers.

change1:
		lodsb			; Get a byte.
		cmp al, 00h		; Is it null?
		jz change9		; Yes. End of variable.

		cmp cs:[v_chgup], 0ffh	; Do we upper case string?
		jnz change2
		call uppercase		; Convert to uppercase.
		jmp change3

change2:
		cmp cs:[v_chglo], 0ffh	; Do we lower case string?
		jnz change3
		call lowercase		; Convert to lowercase.

change3:
		stosb			; Replace it.
		jmp change1		; Next byte.

change9:
		mov cs:[exit_code], 05h	; Environment variable updated.
		jmp nor_term


;		Root environment.
set_root:
		mov si, offset set	; Move in the 'set='
		mov di, offset d_rce	;   into the data area.
		inc di			; Leave room for size.
		mov cx, 04h
		rep movsb		; Move string.

		mov si, offset d_env_var ; Environment data.
		mov cx, s_data
		dec cx			; Ignore the
		dec cx			;   two nulls.
		rep movsb		; Move string.

		mov al, 0dh		; Put in the CR to terminate.
		stosb
		mov ax, s_data		; Set the size and
		inc al			;   add the
		inc al			;     size for 'set='.
		mov byte ptr d_rce, al	; Save the count.

		mov bx, offset lastloc+15 ; bx := program size
		mov cx, 04h		;   in paragraphs.
		shr bx, cl
		mov ax, 4a00h		; Deallocate unused memory.
		int 21h

		mov si, offset d_rce	; Point at command.
		int 02eh		; Execute the DOS command.

		mov ax, cs
		mov ss, ax
		mov ds, ax
		mov es, ax
		mov cs:[exit_code], 01h	; Set code for environment
		jmp nor_term		;   root set and terminate.

;		convert a binary number to decimal string.
dec_str:
		mov	byte ptr dec_zfl, 0ffh	; leading zero flag
		mov	bx, 10000
		call	dec_dig			; get the 10000's digit
		mov	bx, 1000
		call	dec_dig			; get the 1000's digit
		mov	bx, 100
		call	dec_dig			; get the 100's digit
		mov	bx, 10
		call	dec_dig			; get the 10's digit
		add	al, '0'			; get the 1's digit
		jmp	dec_mov

dec_dig:
		mov	dx, 00h			; clear remainder
		div	bx			; divide ax by digit value
		add	al, '0'			; convert to ascii digit

		cmp	byte ptr dec_zfl, 0ffh	; suppress zero
		jne	dec_mov			; no.  jump out

		cmp	al, '0'			; if it a zero
		je	dec_nxt			; yes.  jump out
		mov	byte ptr dec_zfl, 0h	; set print zero

dec_mov:
		stosb				; store the digit
dec_nxt:
		mov	ax, dx			; setup for next digit
		ret


;		Convert lowercase to uppercase.
uppercase:
		cmp al, "z"		; Is
		jg not_lower1		;   it
		cmp al, "a"		;     lower
		jb not_lower1		;       case?
		sub al, 20h		; Make it upper case.
not_lower1:
		ret


;		Convert uppercase to lowercase.
lowercase:
		cmp al, "Z"		; Is
		jg not_upper1		;   it
		cmp al, "A"		;     upper
		jb not_upper1		;       case?
		add al, 20h		; Make it lower case.
not_upper1:
		ret


;		copy environment variable data out.
copy_env:
		push ds
		push es
		push di
		push si

		mov ax, o_env		; Get env. variable offset.
		mov si, ax
		mov ax, b_env		; Get env. bas address.

		mov ds, ax		; Set source.
		mov cx, 00h		; Zero counter.

copy_env1:
		lodsb			; Get a byte.
		cmp al, 00h		; At end of variable data?
		jz copy_env2

		inc cx			; Count the bytes.
		stosb			; Save the byte.
		jmp copy_env1

copy_env2:
		pop si
		pop di
		pop es
		pop ds
		ret


;	subroutine to find a variable in the local environment
loc_env:
		push ax
		push bx
		push cx
		push dx
		push si
		push di
		push es
		push ds

		mov al, 0ffh
		mov f_env, al		; Initialize flag.
		mov ax, b_env		; Get environment base address.
		cmp ax, 00h		; Is it zero?
		jnz loc_env1		; Yes Jump out.

		mov si, 2ch		; Environment offset.
		mov ax, [si]		; Get the address.
		mov b_env, ax		; Save it.

loc_env1:
		mov bx, 00h		; Offset of source.
		mov dx, offset n_env	; Offset of destination.

		mov ax, b_env		; Get environment base.
		mov ds, ax		; Set environment base.
loc_env2:
		mov si, bx		; Set source offset.
		mov di, dx		; Point at the variable.
		mov cx, cs:[s_env]	; Length of variable.
		rep cmpsb		; Is it here.
		jz loc_env5

		mov si, bx		; Backup over this variable.
loc_env3:
		lodsb			; Search
		cmp al, 00h		;  for end
		jnz loc_env3		;    of varible.

		mov al, [si]		; Are we at
		cmp al, 00h		;   the end of
		jz loc_env9		;     the environment?

		mov bx, si		; Set for new variable.
		jmp loc_env2

loc_env5:
		mov al, [si]		; We have the variable.
		cmp al, "="		; Is it only a first part?
		jnz loc_env3		; No. Look again.

		pop ds
		inc si			; Skip equal sign.
		mov o_env, si		; Set offset.
		mov al, 00h		; Search success.
		mov f_env, al		; Set it.
		push ds

loc_env9:
		pop ds
		pop es
		pop di
		pop si
		pop dx
		pop cx
		pop bx
		pop ax
		ret


;		Generate a character if % is there.
make_char:
		cmp al, "%"		; Is it percent.
		jnz make_char3

		lodsb			; Get the delimiter.
		cmp al, "/"		; Is it a /.
		jnz make_char2

		mov dl, al		; Save it.
		push di

		call p_numb		; Read number.
		mov n_chr, cx		; Save element number.

		pop di
		cmp cx, 00h		; Is it going to be a null.
		jz make_char2		; No. Error.

		cmp cx, 0ffh		; Is it valid?
		jg make_char3		; Yes. Jump out.

		mov al, cl		; Character is okay.
		jmp make_char3
make_char2:
		dec si
		mov al, "%"		; Put the percent back.
make_char3:
		ret


		db	128 dup (?)	; Stack.
stack		label	byte

lastloc		label	byte		; End of program.
code ends
     end start

