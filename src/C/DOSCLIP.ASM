 	       page    66,132
;============================================================================
; DOSCLIP.COM impliments a DOS clipboard
;
;       Usage: DOSCLIP [/B][/U]
;
;              B = Use Video BIOS calls
;              U = Uninstall
;
; Revision History: 
;
;       Version 1.0     Initial Release
;
;============================================================================
POPTIME         equ     9                       ;Allow 1/2 second to popup
COPYTIME        equ     180                     ;Allow 10 seconds to copy

STACKSIZE       equ     512                     ;TSR stack
INSTDATASIZE	equ	offset DataBlockEnd - offset DataBlock
MSGBOX_BUFFSIZE equ     1 * 132 * 2             ;Screen save buff for title
HELPBOX_BUFFSIZE equ    5 * 132 * 2             ;Screen save buff for help

RES_STACK       equ     offset end_of_resident + STACKSIZE
MSGBOX_BUFFER   equ     offset end_of_resident + STACKSIZE
ATTR_BUFFER     equ     MSGBOX_BUFFER + MSGBOX_BUFFSIZE

;----------------------------------------------------------------------------
; BIOS Data segment
;----------------------------------------------------------------------------
bios_data       segment at 40h
	        org     17h
shift_state     db      ?                       ;State of keyboard shift keys
		org	1Ah
keybuff_head	dw	?			;Start ptr for keyboard buff
keybuff_tail	dw	?	 		;End ptr for keyboard buff

	        org     4Eh
video_buffoff   dw      ?                       ;Offset of video buffer
	        org     63h
video_ioregs    dw      ?                       ;I/O addr of video controller
		org	80h
keybuff_start	dw	?			;Start ptr for keyboard buff
keybuff_end	dw	?	 		;End ptr for keyboard buff
video_rows      db      ?                       ;Number of rows on the screen
bios_data       ends

;----------------------------------------------------------------------------
; DOSCLIP Code and Data segments
;----------------------------------------------------------------------------
	        code    segment
	        assume  cs:code

	        org     2ch
env_segment     dw      ?                       ;Word containing the segment
	                                        ;  of the program's env. block.
	        org     80h
command_tail    db      ?                       ;Offset of the command tail.

	        org     100h
prog:           jmp     initialize
;---------------------------------------------------------------------------            pushf
; Resident Data (Global to all sessions)
;---------------------------------------------------------------------------            pushf
program         db      13,10
program1	db	"DOSCLIP 1.0 "
copyright       db      "(c) 1992 Douglas Boling"
program2	db	10,13
	        db      "First published in PC Magazine, April 14, 1992"
	        db      13,10,"$",1Ah

def_marked_bw   db      70h                     ;Default marked attr B/W
def_text_bw     db      70h                     ;Default text attr B/W
bw_segment	dw	0B000h			;Segment of B/W video memory

def_marked_clr  db      70h                     ;Default marked attr Color
def_text_clr    db      71h                     ;Default text attr Color
clr_segment	dw	0B800h			;Segment of color video memory

attr_buffsize	dw	1024			;Size of attribute buffer
helpbox_buff  	dw	0   			;Pointer to helpbox buffer

copykey		db	2eh			;Hotkey for copy command 'C'
copyshift	db	08h 			;Shift for copy hotkey. 'Alt'
pastekey	db	2Fh			;Hotkey for paste command 'V'
pasteshift	db	08h 			;Shift for paste hotkey. 'Alt'

clipboard_buff	dd	0			;Pointer to clipboard buffer
clipboard_size	dw	4096			;Max size of clipboard
clipboard_dptr	dd	0			;Ptr data in clipboard
clipboard_dsize	dw	0			;Size of data in clipboard

win_enhanced	db	0			;Enhanced mode Windows active

indos_ptr       dd      -1                      ;Pointer to INDOS flag
criterr_ptr     dd      -1                      ;Pointer to DOS crit err flag

int08h          dd      -1                      ;Int 08 vector (Timer)
int09h          dd      -1                      ;Int 09 vector (Keyboard HW)
int10h          dd      -1                      ;Int 10 vector (Video BIOS)
int13h          dd      -1                      ;Int 13 vector (Disk BIOS)
int16h          dd      -1                      ;Int 16 vector (Keyboard)
int28h          dd      -1                      ;Int 28 vector (DOS Idle)
int2Fh          dd      -1                      ;Int 2F vector (DOS Multiplex)

keycodes        db      3bh,51h,49h,4fh,47h,48h,50h,74h,73h,4bh,4dh
keycodes_end    =       $

keyjmp_table    dw      offset cursor_rt        ;Index into key routines are
		dw      offset cursor_lt        ;  inverse of order of
	        dw      offset cursor_clt       ;  keycode array.
	        dw      offset cursor_crt
	        dw      offset cursor_dn 
	        dw      offset cursor_up
	        dw      offset cursor_home	;Home
	        dw      offset cursor_end	;End
	        dw      offset cursor_top	;Page Up
	        dw      offset cursor_bottom	;Page Down
	        dw      offset showhelp		;F1

helptag         db      "F1 for Help "
helptag_end	=	$
		db	0			;Terminating 0 for helptag

no_help		db	0			;Blocks help msg
;                        1234567891123456789212345678931234567894
helpmsg1        db      " <Cursor> keys delete box/move cursor   "
	        db      " <Shift><Cursor> keys size box",0
helpmsg2        db      " <Home>   Move to start of line         "
	        db      " <End>    Move to end of line",0
helpmsg3        db      " <Pg Up>  Move to top of screen         "
	        db      " <Pg Dn>  Move to bot of screen",0
helpmsg4        db      " <C>      Copy box into clipboard       "
	        db      " <V>      Paste clipboard data",0
helpmsg5        db      " <Esc>    Exit the program",0
;
;Screen variables
;
BIOSFlag	db	0			;1 = Use Vid BIOS calls
video_ptr	label	dword
video_offset	dw	0			;Offset of video memory
video_seg	dw	0			;Segment of video memory
;
;Switcher global data structures
;
StartupInfo     =       $
 sisVersion     dw      3                       ;Switcher structure ID
 sisNextDev     dd      0                       ;Ptr to prev startup structure
 sisVirtDevFile dd      0                       ;Ptr to name of opt dev drvr
 sisReferenceData dd    0                       ;Data for Win dev drivr
 sisInstData    dd      0                       ;Ptr to instance mem list

DataBlockPtr    dd      0                       ;Ptr to instance data
DataBlockSize   dw      0                       ;Size of instance data
StackBlockPtr   dd      0                       ;Ptr to instance stack
StackBlockSize  dw      0                       ;Size of instance stack
	        dd      0                       ;Ptr to next block = 0 to
	        dw      0                       ;  terminate list
;============================================================================
;Instance data
;============================================================================
DataBlock	=	$
;
; Program Instance data
;
hotshift        db      0ah                     ;Shift condition for popup
popflag         db      0                       ;Go active counter

int08_active    db      0                       ;Interrupt active flag
int09_active    db      0                       ;Interrupt active flag
int10_active    db      0                       ;Interrupt active flag
int13_active    db      0                       ;Interrupt active flag
int28_active    db      0                       ;Interrupt active flag
int2F_active    db      0                       ;Interrupt active flag
main_active     db      0                       ;TSR active flag

saved_ss        dw      0			;Saved forground stack ptr
saved_sp        dw      0

copy_flag	db	0			;Flag indicating copy to clip
paste_flag	db	0			;Flag indicating paste 

marked_attr     db      0                       ;Screen attr for marked area
text_attr       db      0                       ;Attribute for msg box text

cursor_pos      dw      0                       ;Cursor position at popup
cursor_type     dw      0                       ;Cursor shape at popup

screen_page     db      0                       ;Active video page
screen_size     label   word
screen_cx       db      0                       ;Screen columns
screen_cy       db      0                       ;Screen rows

marked_pos1     label   word
marked_x1       db      0                       ;Marked area starting column
marked_y1       db      0                       ;marked area starting row
marked_pos2     label   word
marked_x2       db      0                       ;Marked area ending column
marked_y2       db      0                       ;marked area ending row

oldmarked_pos1  label   word
oldmarked_x1    db      0                       ;Marked area starting row
oldmarked_y1    db      0                       ;marked area starting column
oldmarked_pos2  label   word
oldmarked_x2    db      0                       ;Marked area columns
oldmarked_y2    db      0                       ;marked area rows
	
boxactive       db      0                       ;Flag for message box

DataBlockEnd	=	$

;============================================================================
; VIDEOINT processes BIOS video services interrupt (Int 10h)
;============================================================================
videoint        proc    far
	        assume  cs:code,ds:nothing,es:nothing
	        inc     cs:int10_active
	        pushf
	        call    cs:[int10h]             ;Call old int
	        dec     cs:int10_active
	        iret                            ;Return
videoint        endp

;============================================================================
; DISKINT processes BIOS disk services interrupt (Int 13h)
;============================================================================
diskint         proc    far
	        assume  cs:code,ds:nothing,es:nothing
	        inc     cs:int13_active
	        pushf
	        call    cs:[int13h]             ;Call old int
	        pushf
	        dec     cs:int13_active
	        popf
	        ret     2                       ;Return preserving flags
diskint         endp

;============================================================================
; TIMERINT processes timer interrupt (Int 08h)
;============================================================================
timerint        proc    far
	        assume  cs:code,ds:nothing,es:nothing
	        pushf
	        call    cs:[int08h]             ;Call old int 8

		push	ax
		push	ds
		mov	ax,cs
		mov	ds,ax
		assume	ds:code
	
	        cmp     int08_active,0          ;See if we are in this
	        jne     timerint_exit1          ;  routine already
	        inc     int08_active            ;Set int active flag

	        cmp     popflag,0               ;See if we need to try to
	        jne     timer_check             ;  pop up

	        cmp     paste_flag,1            ;See if there is data to
		je      timer_paste_2           ;  paste from clipboard
		ja      timer_paste
timerint_exit:
	        dec     int08_active            ;Clear int active flag
timerint_exit1:
		pop	ds
		pop	ax
	        iret                            ;Return
timer_check:
	        call    check_system            ;See if system OK to pop up
	        or      ax,ax
	        jne     timerint_dec
	        call    main                    ;Call the TSR
	        mov     popflag,1
timerint_dec:
	        dec     popflag
		jmp     short timerint_exit
timer_paste_2:
	        cmp     paste_flag,1            ;See if there is data to
		jne	timerint_exit		;  paste
	        call    push_key
		jnc	timer_paste_2
	        jmp     short timerint_exit
timer_paste:
		cmp	win_enhanced,0		;See if Win/386 active
		je	timer_paste_1

		call	getwinclip		;Get data from the clipboard
		cmp	ax,-1			;If -1, can't open clipboard,
		je	timerint_exit		;  try again later.
		mov	paste_flag,0 		;If 0, no data available.
		or 	ax,ax
		je	timerint_exit
		mov	clipboard_dsize,ax		;Else, AX = bytes
		mov	ax,word ptr clipboard_buff	;  read from CB.
		mov	word ptr clipboard_dptr,ax
		mov	paste_flag,1
		jmp	short timer_paste_2
timer_paste_1:
		mov	paste_flag,0		;If no Win/386, see if any
		cmp	clipboard_dsize,0	;  data in the clipboard.
		je	timerint_exit		;  If not, cancel paste.
		mov	paste_flag,1
		jmp	short timer_paste_2
timerint        endp

;============================================================================
; KEYINT processes keyboard interrupts (Int 09h)
;============================================================================
keyint          proc    far
	        assume  cs:code,ds:nothing,es:nothing
	        push    ax
	        push    ds
	        mov     ax,40h
	        mov     ds,ax                   ;Set ES to bios data segment
	        assume  ds:bios_data
	        mov     ah,ds:[shift_state]
	        and     ah,0fh                  ;Mask lock bits
	        cmp     ah,cs:[copyshift]
	        je      keyint_copy
keyint_1:
	        cmp     ah,cs:[pasteshift]
	        je      keyint_paste
keyint_jmp:
	        pop     ds
	        pop     ax
	        jmp	cs:[int09h]             ;Call old int 9
keyint_exit:
                cli                             ;Disable interrupts
                in      al,61h                  ;Reset the keyboard
                mov     ah,al                   ;  controller
                or      al,80h
                out     61h,al
                mov     al,ah
                out     61h,al
 
                mov     al,20h                  ;Signal end-of-interrupt to
                out     20h,al                  ;  the interrupt controller
                sti                             ;Enable interrupts
	        pop     ds
	        pop     ax
		iret
keyint_copy:
		in	al,60h			;Get scan code from keyboard
		cmp	al,cs:copykey
		jne	keyint_1
		mov     cs:popflag,POPTIME      ;Set timer to pop up
	        jmp     short keyint_exit
keyint_paste:
		in	al,60h			;Get scan code from keyboard
		cmp	al,cs:pastekey		;Compare to paste hotkey
		jne	keyint_jmp
		mov	cs:paste_flag,2		;Set Paste flag
	        jmp     short keyint_exit
keyint          endp

;============================================================================
; IDLEINT processes DOS Idle interrupt (Int 28h)
;============================================================================
idleint         proc    far
	        assume  cs:code,ds:nothing,es:nothing
	        pushf
	        call    cs:[int28h]             ;Call old int

		push	ds
		push	cs
		pop	ds
		assume	ds:code
	        cmp     int28_active,0          ;See if we are in this
	        jne     idleint_exit1           ;  routine already
	        inc     int28_active            ;Set int active flag
	        cmp     popflag,0               ;See if we need to try to
	        jne     idle_check              ;  pop up
idleint_exit:
	        dec     int28_active            ;Clear int active flag
idleint_exit1:
		pop	ds
	        iret                            ;Return
idle_check:
	        push    ax
	        call    check_system            ;See if OK to pop up.  Ignore
	        or      al,al                   ;  INDOS since in idle.
	        jne     idleint_1
	        mov     popflag,0               ;Clear popup flag
	        call    main                    ;Call the TSR
idleint_1:
	        pop     ax
	        jmp     short idleint_exit
idleint         endp

;============================================================================
; MUXINT processes DOS multiplex interrupt (Int 2Fh)
;============================================================================
muxint          proc    far
	        assume  cs:code,ds:nothing,es:nothing

		cmp	ax,1605h		;See if Windows launch
		je	mux_winstart
		cmp	ax,1606h		;See if Windows terminate
		je	mux_winend
		cmp	ax,1608h		;See if Windows launch
		je	mux_wingo 		;  complete

		cmp     ax,4b05h                ;See if switcher get instance
	        je      init_instance           ;  data.
muxint_jmp:
	        jmp     cs:[int2Fh]             ;Jump to old int
mux_winend:
	        test    dx,01h                  ;See if enhanced mode Windows
	        jne     muxint_jmp
		dec	cs:win_enhanced		;Clear Enhanced mode win flag
	        jmp     short muxint_jmp
mux_wingo:
		cmp	cs:clipboard_dsize,0	;If Windows starts and we
		je	muxint_jmp		;  have data in our 
		cmp	cs:win_enhanced,0	;  clipboard, copy our
		je	muxint_jmp		;  data to the Win clipboard
		mov	cs:copy_flag,COPYTIME
		jmp	short muxint_jmp
mux_winstart:
	        test    dx,01h                  ;See if enhanced mode Windows
	        jne     init_instance
	        inc     cs:win_enhanced
init_instance:
	        pushf
	        call    cs:[int2fh]             ;Call old int
	        mov     word ptr cs:[sisNextDev],bx
	        mov     word ptr cs:[sisNextDev+2],es
	        push    cs                      ;ES:BX point to switcher struc
	        pop     es
	        mov     bx,offset StartupInfo
	        jmp     short muxint_exit
muxint_exit:
	        iret
muxint		endp

;---------------------------------------------------------------------------            pushf
; Check System Determines if the system is in a state compatible with TSRs
; Exit: AL - ORed flags for DOS Idle OK
;       AH - State of INDOS flag
;---------------------------------------------------------------------------            pushf
check_system    proc    near
	        assume  cs:code,ds:code,es:nothing
	        push    bx
	        push    ds
	        xor     ax,ax
	        or      al,int10_active         ;Check BIOS video int
	        or      al,int13_active         ;Check BIOS disk int
	        lds     bx,criterr_ptr          ;Check DOS critical error
	        or      al,byte ptr ds:[bx]     ;  flag.
	        lds     bx,cs:indos_ptr         ;Check INDOS flag
	        mov     ah,byte ptr ds:[bx]
check_sys_exit:
	        pop     ds
	        pop     bx
	        ret
check_system    endp

;-----------------------------------------------------------------------------
; GETWINCLIP  Gets any data from the Windows Clipboard
; Exit AX - FF Can't open clipboard
;           0  Error in clipboard or clipboard data not available
;           Else, number of bytes read from clipboard
;-----------------------------------------------------------------------------
getwinclip	proc	near
		assume	ds:code
		mov	ax,1701h		;Open Clipboard
		int	2fh
		or	ax,ax			;Error opening, exit
		mov	ax,-1
		je	getwinclip_exit1
		push	bx
		push	dx
		push	es
		mov	ax,1704h		;Get clipboard data size
		mov	dx,7			;Request OEM Text
		int	2fh
		or	dx,dx			;See if too much data
		jne	getwinclip_exit
		cmp	ax,clipboard_size
		jbe	getwinclip_1
		xor	ax,ax			;Indicate error
		jmp	short getwinclip_exit
getwinclip_1:
		cmp	dx,ax	     		;If 0 size, no data in 
		je	getwinclip_exit		;  correct format.
		push	ax
		mov	ax,1705h		;Get clipboard data
		les	bx,clipboard_buff
		mov	dx,7			;Get OEM text
		int	2fh
		or	ax,ax
		pop	ax
		je	getwinclip_exit		
		dec	ax
getwinclip_exit:
		push	ax
		mov	ax,1708h		;Close Clipboard
		int	2fh
		pop	ax
		pop	es
		pop	dx
		pop	bx
getwinclip_exit1:
		ret
getwinclip	endp

;-----------------------------------------------------------------------------
; GETCLIPSIZE  Gets the size of the data in the Windows clipboard
;-----------------------------------------------------------------------------
getclipsize	proc	near
		assume	ds:code
		mov	ax,1701h		;Open Clipboard
		int	2fh
		or	ax,ax			;Error opening, exit
		je	getclipsize_exit
		mov	ax,1704h		;Get clipboard data size
		mov	dx,7			;Request OEM Text
		int	2fh
		mov	ax,1708h		;Close Clipboard
		int	2fh
		pop	es
		pop	dx
		pop	bx
getclipsize_exit:
		ret
getclipsize	endp

;-----------------------------------------------------------------------------
; SETWINCLIP  Sets the Windows clipboard
; Exit:  ZF - Set if error
;-----------------------------------------------------------------------------
setwinclip	proc	near
		assume	ds:code
		push	bx
		push	cx
		push	dx
		push	si
		push	es
		mov	ax,1700h		;Check API
		int	2fh
		or	ax,ax			;no API
		je	setwinclip_exit1
		mov	ax,1701h		;Open Clipboard
		int	2fh
		or	ax,ax			;Error opening, exit
		je	setwinclip_exit1
		mov	ax,1702h		;Clear Clipboard
		int	2fh
		or	ax,ax			;Error, exit
		je	setwinclip_exit1
		mov	ax,1703h		;Set clipboard data
		les	bx,clipboard_buff	;ES:BX ptr to data
		xor	si,si			;SI,CX = data size
		mov	cx,clipboard_dsize
		mov	dx,7			;Data format is OEM text
		int	2fh
setwinclip_exit:
		mov	ax,1708h		;Close Clipboard
		int	2fh
		or	ax,ax			;Set ZF if error
setwinclip_exit1:
		pop	es
		pop	si
		pop	dx
		pop	cx
		pop	bx
		ret
setwinclip	endp

;-----------------------------------------------------------------------------
; INCKEYPTR Incriments the keyboard buffer pointer
; Entry: DI - Current Keyboard tail pointer
;-----------------------------------------------------------------------------
inckeyptr	proc	near
		assume	ds:bios_data
		inc	di			;Make room in buffer
		inc	di
		cmp	di,ds:[keybuff_end]	;Get ptr to end of buffer
		jne	inckeyptr_1
		mov	di,ds:[keybuff_start]	;Get ptr to buffer offset
inckeyptr_1:
		ret
inckeyptr	endp

;-----------------------------------------------------------------------------
; PUSH KEY  Fills the keyboard buffer with data from the clipboard.
; Exit: CF - Set if keyboard buffer full
;       AX - modified
;-----------------------------------------------------------------------------
push_key	proc    near
		assume	ds:nothing,es:nothing
		push	di
		push	si
		push	ds
		push	es
		cli
		mov	ax,bios_data
		mov	ds,ax
		assume	ds:bios_data
		cli				;No interrupts
		mov	di,ds:[keybuff_tail]
		push	di
		call 	inckeyptr
		cmp	di,ds:[keybuff_head]	;See if buffer full
		pop	di
		stc
		je	push_key_exit
		
		les	si,cs:[clipboard_dptr]	;Get ptr to clip data
		mov	ax,word ptr cs:[clipboard_buff]	
		add	ax,cs:[clipboard_dsize] ;Add size of data
		cmp	ax,si			;See if at end of buffer
		mov	al,es:[si]		;Get clipboard byte
		jne	push_key_2		;No, continue
		mov	cs:[paste_flag],0		;Clear paste flag
		mov	si,word ptr cs:[clipboard_buff]	;Reset data ptr
		dec	si
push_key_2:
		or	al,al			;Don't stuff null char
		je	push_key_3
		cmp	al,0ah			;Don't stuff line feeds
		je	push_key_3
	  	xor	ah,ah			;Clear scan code
		cmp	al,0dh			;If CR character, add proper
		jne	push_key_21		;  scan code for Word Perfect
		mov	ah,1ch
push_key_21:		
		mov	ds:[di],ax		;Push data into keyboard buff
		call	inckeyptr
		mov	ds:[keybuff_tail],di	;Save keybuff ptr
push_key_3:
		inc	si			;Update clip data ptr
		mov	word ptr cs:[clipboard_dptr],si	;Save clipdata ptr
		clc
push_key_exit:
		sti				;Allow interrupts
		pop	es
		pop	ds
		pop	si
		pop	di
		ret
push_key	endp

;============================================================================
; MAIN  This routine does most of the work for the TSR
;============================================================================
main            proc    near
	        assume  cs:code,ds:code,es:nothing
	        cmp     main_active,0           ;See if already active
	        jne     main_exit
	        cli
	        inc     main_active
	        mov     [saved_sp],sp           ;Save old stack pointer
	        mov     [saved_ss],ss

	        mov     ax,cs                   ;Set ss:sp to internal stack
	        mov     ss,ax
	        mov     sp,RES_STACK
	        sti				;Enable interrupts
	        cld				;Set string direction UP

	        push    bx
	        push    cx
	        push    dx
	        push    di
	        push    si
	        push    bp
	        push    es
	
	        mov     ah,0fh                  ;Get display mode
	        int     10h
	        cmp     al,7
	        je      main_1                  ;Pop up only if in text mode.
	        cmp     al,4
	        jb      main_1
main_exit1:
	        pop     es
	        pop     bp
	        pop     si
	        pop     di
	        pop     dx
	        pop     cx
	        pop     bx
	
	        cli
	        mov     ss,[saved_ss]           ;Restore old stack
	        mov     sp,[saved_sp]
	        dec     main_active
main_exit:
	        ret
main_1:
		xor	dl,dl
		mov	no_help,dl		;Enable help message
		mov	copy_flag,dl		;Clear copy to clip flag
	        cmp     ah,80
	        jb      main_exit1
	        mov     screen_page,bh          ;Save active video page
	        mov     screen_cx,ah            ;Save screen columns

	        mov     ah,3                    ;Get cursor position
	        int     10h
	        mov     cursor_pos,dx           ;Save cursor information
	        mov     cursor_type,cx
		cmp	dh,0			;If on top line, move down 1
		jne	main_11			;  to miss banner.
		inc	dh
main_11:
		mov	marked_pos1,dx		;Save initial cursor position
	        mov     ax,40h
	        mov     es,ax                   ;Set ES to bios data segment
	        assume  es:bios_data
	        mov     ah,12h                  ;Determine if CGA by checking
	        mov     bl,10h                  ;  for EGA compatibility
	        int     10h
	        mov     al,24                   ;CGA, only 25 rows on screen
	        cmp     bl,10h
	        je      main_2

	        mov     al,es:video_rows        ;Get number of rows on screen
main_2:
	        mov     screen_cy,al		;Save screen rows
		mov	ax,es:video_buffoff	;Copy ptr to video memory
		mov	video_offset,ax

	        mov     ax,marked_pos1		;Copy starting pos to ending
	        mov     marked_pos2,ax		;  pos to zero block.
	        mov     oldmarked_pos1,ax
	        mov     oldmarked_pos2,ax
	
	        test    es:video_ioregs,40h     ;See if color or monochrome
	        jne     main_colorvid

		mov	si,offset def_marked_bw	;Point to B/W parameters
	        jmp     short main_3
main_colorvid:
		mov	si,offset def_marked_clr ;Point to color parameters	
main_3:
	        push    ds
	        pop     es
	        assume  es:code

		mov	di,offset marked_attr	;Copy default attributes 
		movsw
		lodsw				;Get Video buffer segment
		mov	video_seg,ax

	        mov     ah,1			;Hide the cursor by changing
	        mov     cx,200h                 ;  the size of the cursor
	        int     10h

		call	init_attrlist		;Initialize attr save list
		jc	main_end1

	        mov     dx,marked_pos1          ;Mark initial cursor where
	        call    getchar                 ;  the old cursor was
	        mov     ah,marked_attr		;Mark cursor position on
	        call    putchar			;  screen.
main_loop:
	        cmp     boxactive,1             ;If box active, don't reprint
	        je      main_4                  ;  the box.

		mov	al,marked_y2		;If cursor on top line, don't
		or	al,marked_y2		;  print the title box.
		je	main_4
	        call    msgbox                  ;Draw title box
main_4:
		call	getkey			;Wait for a key
	        cmp     al,27                   ;If ESC, end.
	        je      main_end
	        cmp     ah,pastekey             ;If 'v' paste and exit
	        je      main_paste
	        cmp     ah,28                   ;If Enter, copy and exit
	        je      main_copy
	        cmp     ah,copykey              ;If 'c' copy and exit
	        je      main_copy 
	        mov     al,ah			;Copy scan code
main_6:
	        mov     di,offset keycodes
	        mov     cx,offset keycodes_end - offset keycodes
	        repne   scasb
	        jne     main_loop
	        shl     cx,1			;Convert key num into jump
	        mov     bx,cx			;  table index.
		mov	ax,marked_pos2		;Preload parameters into regs
		mov	cx,screen_size		;  to save code
	        call    [keyjmp_table+bx]	;Call key routine
	        jmp     short main_loop
main_copy:
		mov	copy_flag,1
		jmp	short main_end
main_paste:
		cmp	win_enhanced,0		;If enhanced mode Windows 
		je	main_paste_1		;  active, get clipboard
		call	getwinclip		;  data from the Windows
		jmp	short main_end		;  Clipboard.
main_paste_1:
		cmp	clipboard_dsize,0	;If clipboard empty, don't
		je	main_end		;  paste.
		mov	paste_flag,1
main_end:
	        call    clearmarked             ;Restore attributes
	        call    clearbox                ;Delete title box
main_end1:
	        mov     ah,1                    ;Set cursor type
	        mov     cx,cursor_type
	        mov     bh,screen_page
	        int     10h
	        mov     ah,2                    ;Set cursor position
	        mov     dx,cursor_pos
	        int     10h
	        jmp     main_exit1
main            endp

;-----------------------------------------------------------------------------
; CURSOR KEYS Handles the actions of the cursor keys as the user uses them
;             to mark the selected area.
; Entry: AL - marked_x2
;        AH - marked_y2
;        CL - screen_cx
;        CH - screen_cy
;-----------------------------------------------------------------------------
cursor_keys     proc    near
; CURSOR HOME  Moves the cursor to the left most column
cursor_home:
	        xor     al,al			;Zero current column
	        jmp     short cursor_1
; CURSOR END  Moves the cursor to the right most column
cursor_end:
	        mov     al,cl			;Set Cur column to screen cols
	        dec     al
	        jmp     short cursor_1
; CURSOR TOP  Moves the cursor to the top row
cursor_top:
	        mov     ah,1                    ;Move to line 1 to avoid
	        jmp     short cursor_1          ;  title erase
; CURSOR END  Moves the cursor to the bottom row
cursor_bottom:
	        mov     ah,ch			;Set Cur Row to screen rows
	        jmp     short cursor_1
; CURSOR UP  Moves the marked orgin up one row
cursor_up:
	        or      ah,ah
	        je      cursor_1
	        dec     ah
	        jmp     short cursor_1
; CURSOR DN  Moves the marked orgin down one row
cursor_dn:
	        cmp     ah,ch
	        je      cursor_1
	        inc     ah
	        jmp     short cursor_1
; CURSOR CLT  Moves the marked orgin left 8 columns
cursor_clt:
		sub	al,8
		jae	cursor_1
	        xor     al,al
	        jmp     short cursor_1
; CURSOR CRT  Moves the marked orgin right 8 columns
cursor_crt:
		add	al,8
		dec	cl
		cmp	al,cl
		jb	cursor_1
	        mov     al,cl
	        jmp     short cursor_1
; CURSOR LT  Moves the marked orgin left one col
cursor_lt:
	        or      al,al
	        je      cursor_1
	        dec     al
	        jmp     short cursor_1
; CURSOR RT  Moves the marked orgin right one col
cursor_rt:
		dec	cl
	        cmp     al,cl
	        jae     cursor_1
	        inc     al
cursor_1:
		mov	marked_pos2,ax		;Save new position
		push	ax
	        mov     ah,2                    ;Get shift state
	        int     16h
	        and     al,3
		pop	ax
	        jne     cursor_2
	        mov     marked_pos1,ax          ;Not shifted, set points equal
cursor_2:
	        or      ah,ah                   ;If cursor on top line, clear
	        jne     cursor_3                ;  title box so user can see
	        call    clearbox                ;  what is on top line.
cursor_3:
	        call    drawmarked
	        ret
cursor_keys     endp

;-----------------------------------------------------------------------------
; DRAWMARKED  Displays the marked area by changing the screen attributes
;             inside the marked area.
;-----------------------------------------------------------------------------
drawmarked      proc    near
	        push    es

	        mov     ax,marked_pos1
	        mov     dx,marked_pos2
	        cmp     dx,oldmarked_pos2
	        jne     drawmarked_1

	        cmp     ax,oldmarked_pos1
	        je      drawmarked_exit
drawmarked_1:
	        mov     bx,offset marked_pos1
	        mov     cx,4
drawmarked_2:
	        call    find_min                ;AX = upper left corner
	        call    find_max                ;DX = lower right corner
	        add     bx,2
	        loop    drawmarked_2
drawmarked_7:
	        sub     dx,ax                   ;Compute size of area

	        xor     cx,cx
	        mov     cl,dl                   ;Copy column count
	        mov     si,cx
	        inc     si
	        mov     cl,dh                   ;Copy row count
	        inc     cx
	        mov     dx,ax                   ;Copy starting row, column
drawmarked_8:
	        push    cx                      ;Save row count
	        push    dx                      ;Save cursor position
	        mov     cx,si                   ;Get column count
drawmarked_9:
	        mov     bx,offset marked_pos1
	        call    chk_inwin
	        mov     ah,al                   ;Save in marked area flag
	
	        mov     bx,offset oldmarked_pos1
	        call    chk_inwin

	        cmp     ah,al                   ;See if in or out of both
	        je      drawmarked_11           ;  areas. If so, no change.

	        push    ax                      ;Save in-window flags
	        call    getchar
	        pop     bx
	        or      bh,bh                   ;See if in marked area
	        je      drawmarked_10

	        mov     ah,marked_attr		;Mark character
	        call    putchar

	        jmp     short drawmarked_11
drawmarked_10:
		call	get_attr		;Get saved screen attribute
	        call    putchar			;Restore attribute
drawmarked_11:
	        inc     dl
	        loop    drawmarked_9

	        pop     dx
	        pop     cx
	        inc     dh
	        loop    drawmarked_8

	        mov     ax,marked_pos1          ;Update old pointers
	        mov     oldmarked_pos1,ax
	        mov     ax,marked_pos2
	        mov     oldmarked_pos2,ax
drawmarked_exit:
	        pop     es
	        ret
drawmarked      endp

;-----------------------------------------------------------------------------
; CLEARMARKED  Restores the marked area to its original screen attributes
;-----------------------------------------------------------------------------
clearmarked     proc    near
	        assume  ds:code
		push	es
	        mov     bx,offset marked_pos1
	        call    compute_box
	        sub     ax,dx
	        xor     cx,cx
	        mov     cl,al
	        mov     si,cx                   ;Save column count
	        inc     si
	        mov     cl,ah
	        inc     cx
		xor	bx,bx
		cmp	copy_flag,0
		je	clearmarked_3
		mov	bx,clipboard_size
		les	di,clipboard_buff
		mov	word ptr [clipboard_dptr],di
clearmarked_3:
	        push    cx
	        push    dx
	        mov     cx,si			;Get row count
clearmarked_4:
	        call    getchar
		or	bx,bx			;If copying data to 
		je	clearmarked_5		;  clipboard, save char.
		stosb
		dec	bx
clearmarked_5:
		call	get_attr		;Get saved screen attribute
	        call    putchar
	        inc     dl
	        loop    clearmarked_4
		or	bx,bx
		je	clearmarked_6
		mov	ax,0a0dh		;Append CR-LF to line
		stosw
		dec	bx
		dec	bx
clearmarked_6:
	        pop     dx
	        pop     cx
	        inc     dh
	        loop    clearmarked_3
		cmp	copy_flag,0
		je	clearmarked_exit
		xor	al,al
		stosb
		dec	bx
		sub	bx,clipboard_size
		not	bx
		mov	clipboard_dsize,bx
		mov	ax,word ptr cs:[clipboard_buff]
		mov	word ptr cs:[clipboard_dptr],ax
		cmp	win_enhanced,0		;If enhanced mode Windows
		je	clearmarked_exit	;  active, save data to 
		call	setwinclip		;  Windows clipboard
clearmarked_exit:
		pop	es
	        ret
clearmarked     endp

;-----------------------------------------------------------------------------
; GET ATTR  Gets a saved attribute in the attribute buffer for a given 
;           cursor location.
; Entry:  DX - Row, Column of character
; Exit:   AH - Attribute
;-----------------------------------------------------------------------------
get_attr	proc	near
		push	cx
		push	dx
		push	si
		push	ax
		call	compute_offset		;Get count into buffer
		mov	si,ATTR_BUFFER
		xor	ax,ax
		mov	dx,ax
get_attr_1:
		lodsb				;Get count byte
		inc	si
		or	al,al
		je	get_attr_exit
		sub	cx,ax
		jae	get_attr_1
		mov	dh,[si-1]		;Get attribute
get_attr_exit:
		pop	ax
		mov	ah,dh			;Copy attribute
		pop	si
		pop	dx
		pop	cx
		ret
get_attr	endp

;-----------------------------------------------------------------------------
; INIT ATTRLIST  Initializes the attribute list
;-----------------------------------------------------------------------------
init_attrlist	proc	near
	        assume  ds:code
		mov	bx,screen_size		;Get size of screen
		inc	bh
		inc	bh
		xor	ax,ax
		mov	dx,ax			;Start at top left corner
		mov	cx,ax
		xchg	bh,cl			;CX-Col count, BX-Row cnt
		mov	si,bx
		mov	di,ATTR_BUFFER		;Get pointer to buffer
		call	getchar
		mov	bh,ah
		mov	bl,0
init_attrlist_1:
		push	cx
		push	dx
		mov	cx,si			;Get number of columns
init_attrlist_2:
		call	getchar 		;Read attribute
		cmp	bh,ah			;Compare attributes
		jne	init_attrlist_newblk	;If different write block
		inc	bl			;Incriment count
		cmp	bl,-1			;If count full, write block
		je	init_attrlist_newblk
		mov	bh,ah			;Copy attribute value
init_attrlist_3:
		inc	dl			;Next column
		loop	init_attrlist_2
		pop	dx
		pop	cx
		inc	dh			;Next row
		loop	init_attrlist_1
		clc
init_attrlist_exit:
	        ret
init_attrlist_newblk:
		mov	ds:[di],bx		;Write old block to list
		inc	di			;Update ptr
		inc	di
		mov	bl,1			;New count
		mov	bh,ah			;Copy new attribute 
		cmp	di,helpbox_buff
		jb	init_attrlist_3		;If list runs into the help
		inc	no_help    		;  buffer, disable help
		push	ax
		mov	ax,helpbox_buff		;If past helpbox buffer, exit
		add	ax,HELPBOX_BUFFSIZE
		cmp	di,ax
		pop	ax
		jb	init_attrlist_3		;If list past help box
		stc                		;  buffer, exit prog.
		jmp	short init_attrlist_exit
init_attrlist	endp

;-----------------------------------------------------------------------------
; COMPUTE OFFSET  Computes the offset into the attribute buffer for a
;                 given cursor location.
; Entry:  DX - Row, Column of character
; Exit:   CX - Offset in buffer.
;-----------------------------------------------------------------------------
compute_offset  proc    near
	        push    ax
		push	dx
	        mov     al,dh                   ;Copy column
	        mul     screen_cx               ;Mul by width of screen
	        xor     dh,dh
	        add     ax,dx                   ;Add row
		xchg	cx,ax
		pop	dx
	        pop     ax
	        ret
compute_offset  endp

;-----------------------------------------------------------------------------
; CHK INWIN  Determines if a character is inside the bounds of an area
; Entry:  BX - Pointer to bounding rectangle
;           x1  db  point 1 column
;           y1  db  point 1 row
;           x2  db  point 2 column
;           y2  db  point 2 row
;         DX - Row, Column of character
; Exit:   AL - <> 0 if inside area
;-----------------------------------------------------------------------------
chk_inwin       proc    near
	        push    bx
	        push    cx

	        push    ax                      ;Save AX
	        push    dx                      ;Save current cursor pos
	        call    compute_box             ;DX = UL corner, AX = LR corner
	        pop     bx
	        xchg    bx,dx                   ;DX = cur cursor, BX=UL corner
	        mov     cx,ax                   ;CX = LR corner
	        pop     ax                      ;Restore AX

	        mov     al,0                    ;Clear inbox flag

	        cmp     dl,bl                   ;See if above starting row
	        jb      chk_inwin_exit
	        cmp     dh,bh                   ;See if left of starting col
	        jb      chk_inwin_exit

	        cmp     dl,cl                   ;See if below ending row
	        ja      chk_inwin_exit
	        cmp     dh,ch                   ;See if right of ending col
	        ja      chk_inwin_exit
	        inc     al
chk_inwin_exit:
	        pop     cx
	        pop     bx
	        ret
chk_inwin       endp

;-----------------------------------------------------------------------------
; FIND MIN  Computes the smaller of two screen coordinates.
; Entry:  AX - Row, Column of 1st coordinate
;         BX - Pointer to 2nd coordinate
; Exit:   AX - Result coordinate.
;-----------------------------------------------------------------------------
find_min        proc    near
	        cmp     al,[bx]
	        jbe     find_min_1
	        mov     al,[bx]
find_min_1:
	        cmp     ah,[bx+1]
	        jbe     find_min_2
	        mov     ah,[bx+1]
find_min_2:
	        ret
find_min        endp

;-----------------------------------------------------------------------------
; FIND MAX  Computes the larger of two screen coordinates.
; Entry:  DX - Row, Column of 1st coordinate
;         BX - Pointer to 2nd coordinate
; Exit:   AX - Result coordinate.
;-----------------------------------------------------------------------------
find_max        proc    near
	        cmp     dl,[bx]
	        jae     find_max_1
	        mov     dl,[bx]
find_max_1:
	        cmp     dh,[bx+1]
	        jae     find_max_2
	        mov     dh,[bx+1]
find_max_2:
	        ret
find_max        endp

;-----------------------------------------------------------------------------
; COMPUTE BOX  Puts the starting row/column in DX and the SI and CX
; Entry:  BX - Pointer to coordinates
; Exit:   AX - Ending row/column
;         DX - Starting row/column
;-----------------------------------------------------------------------------
compute_box     proc    near
	        mov     ax,[bx]
	        mov     dx,[bx+2]

	        cmp     al,dl
	        ja      compute_box_1
	        xchg    al,dl
compute_box_1:
	        cmp     ah,dh
	        ja      compute_box_2
	        xchg    ah,dh
compute_box_2:
	        ret
compute_box     endp

;-----------------------------------------------------------------------------
; SHOWHELP  Displays a help screen on the first three lines of the display
;-----------------------------------------------------------------------------
showhelp        proc    near
		cmp	byte ptr no_help,0	;See if help msg disabled.
		jne	showhelp_exit
	        mov     dh,1                    ;Box at top of screen
	        mov     cx,5
	        mov     di,helpbox_buff
showhelp_1:
	        push    cx
	        call    saveline
	        inc     dh
	        pop     cx
	        loop    showhelp_1

	        mov     dh,1
	        mov     si,offset helpmsg1      ;Write help text line
	        call    writeline
	        inc     dh                      ;Move to next line
	        mov     si,offset helpmsg2
	        call    writeline
	        inc     dh                      ;Move to next line
	        mov     si,offset helpmsg3
	        call    writeline
	        inc     dh                      ;Move to next line
	        mov     si,offset helpmsg4
	        call    writeline
	        inc     dh                      ;Move to next line
	        mov     si,offset helpmsg5
	        call    writeline

		call	getkey			;Wait for a key
	        mov     dh,1                    ;Box at top of screen
	        mov     cx,5
	        mov     si,helpbox_buff
showhelp_2:
	        push    cx
	        call    restoreline
	        inc     dh
	        pop     cx
	        loop    showhelp_2
showhelp_exit:
	        ret
showhelp        endp

;-----------------------------------------------------------------------------
; MSGBOX  displays a message in a text window on the top line of the
;         screen.  The current data on the screen is saved.
;-----------------------------------------------------------------------------
msgbox          proc    near
	        mov     dh,0                    ;Box at top of screen
		mov	al,boxactive
	        cmp     al,2             	;Display sum only
	        je      msgbox_1
	        or      al,al              	;If box already displayed,
	        jne     msgbox_1                ;  don't save screen data.
	        mov     di,MSGBOX_BUFFER
	        call    saveline
msgbox_1:
		mov	si,offset program1
		mov	cx,offset program2 - offset program1
		xor	dx,dx
msgbox_11:
		lodsb
		call	writechar
		loop	msgbox_11
		xor	cx,cx
		mov	cl,screen_cx
		sub	cl,offset helptag_end - offset helptag
		ja	msgbox_2
		mov	cl,screen_cx
		inc	ch
msgbox_2:
		push	dx
msgbox_3:
	        mov     al,' '                  ;Pad line with spaces
	        call    writechar
	        cmp     dl,cl
	        jb      msgbox_3
		or	ch,ch
		jne	msgbox_5
msgbox_4:
		mov	si,offset helptag
		call	writestr
msgbox_5:
		pop	dx
	        mov     boxactive,1             ;Set box active flag
	        ret
msgbox          endp

;-----------------------------------------------------------------------------
; CLEARBOX  removes the title box from the screen.
;-----------------------------------------------------------------------------
clearbox        proc    near
	        cmp     boxactive,0             ;If box already displayed,
	        je      clearbox_2              ;  don't save screen data.
	        mov     si,MSGBOX_BUFFER
	        mov     dh,0                    ;Box at top of screen
	        call    restoreline
	        mov     boxactive,0             ;Clear box displayed flag
clearbox_2:
	        ret
clearbox        endp

;-----------------------------------------------------------------------------
; WRITELINE  Writes a line of help text to the screen.  The line is padded
;            with spaces to the right.
; Entry: DH - Line on screen to write
;        SI - Pointer to ASCIIZ text
;-----------------------------------------------------------------------------
writeline       proc    near
	        push    dx
	        xor     dl,dl                   ;Start at left side of screen
	        call    writestr                ;Write string
writeline_2:
	        mov     al,' '                  ;Pad line with spaces
	        call    writechar
	        cmp     dl,screen_cx
	        jb      writeline_2
	        pop     dx
	        ret
writeline       endp

;-----------------------------------------------------------------------------
; SAVELINE  Saves the contents of a screen line ot a buffer
; Entry: DH - Line on screen to save
;        DI - Pointer to save buffer
;-----------------------------------------------------------------------------
saveline        proc    near
	        push    dx
	        xor     dl,dl                   ;Start at left side of screen
	        xor     cx,cx
	        mov     cl,screen_cx
saveline_1:
	        call    getchar                 ;Read character from screen
	        stosw
	        inc     dl                      ;Point to next character
	        loop    saveline_1
	        pop     dx
	        ret
saveline        endp

;-----------------------------------------------------------------------------
; RESTORELINE  Restores the screen that was covered by a line of program
;              helptext.
; Entry: DH - Line on screen to restore
;        SI - Pointer to buffer that contains original screen contents
;-----------------------------------------------------------------------------
restoreline     proc    near
	        push    dx
	        xor     dl,dl                   ;Start at left side of screen
	        xor     cx,cx
	        mov     cl,screen_cx
restoreline_1:
	        lodsw
	        call    writechar1              ;Read character from screen
	        loop    restoreline_1
	        pop     dx
	        ret
restoreline     endp

;-----------------------------------------------------------------------------
; WRITESTR  Writes a string to the screen.
; Entry: DH,DL - Row/Column to write the string
;        SI - Pointer to ASCIIZ text
;-----------------------------------------------------------------------------
writestr        proc    near
	        mov     ah,text_attr            
writestr1:
	        lodsb                           ;Read text from string, then
	        or      al,al                   ;  call putchar to write to
	        je      writestr_1		;  the screen.
	        call    writechar1
	        jmp     short writestr1
writestr_1:
	        ret
writestr        endp

;-----------------------------------------------------------------------------
; WRITECHAR  Writes a character to the screen
; Entry: AL - Character
;        DX - Row, Column 
;-----------------------------------------------------------------------------
writechar       proc    near
	        mov     ah,text_attr
writechar1:
	        call    putchar
	        inc     dl
	        ret
writechar       endp

;-----------------------------------------------------------------------------
; GETCHAR  Reads a character and its attribute from the screen
; Entry:  DH - Row of character to read
;         DL - Column of character to read
; Exit:   AL - Character
;         AH - Attribute
;-----------------------------------------------------------------------------
getchar         proc    near
	        push    bx
		cmp	BIOSFlag,0
		jne	getchar_bios
		push	cx
		push	si
		push	ds
		call	compute_offset		;Get offset into buffer
		shl	cx,1			;Double for char and attr
		lds	si,video_ptr		;Get ptr to video memory
		add	si,cx			;Double since char and attr
		lodsw				;Read char/attribute
		pop	ds
		pop	si
		pop	cx
		jmp	short getchar_exit
getchar_bios:
	        mov     ah,2                    ;Set cursor
	        mov     bh,screen_page
	        int     10h
	        mov     ah,8                    ;Read character/attr
	        int     10h
getchar_exit:
	        pop     bx
	        ret
getchar         endp

;-----------------------------------------------------------------------------
; PUTCHAR  Writes a character and its attribute to the screen
; Entry:  AL - Character to write
;         AH - Attribute to write
;         DH - Row of character to write
;         DL - Column of character to write
;-----------------------------------------------------------------------------
putchar         proc    near
	        push    bx
	        push    cx
	        push    ax
		cmp	BIOSFlag,0
		jne	putchar_bios
		push	di
		push	es
		call	compute_offset		;Get offset into buffer
		shl	cx,1 			;Double since char and attr
		les	di,video_ptr
		add	di,cx          		;Add to start of buffer
		stosw				;Read char/attribute
		pop	es
		pop	di
		jmp	short putchar_exit
putchar_bios:
	        mov     ah,2                    ;Set cursor
	        mov     bh,screen_page
	        int     10h
	        pop     ax
		push	ax
	        mov     bl,ah                   ;Copy attribute
	        mov     ah,9                    ;Read character/attr
	        mov     cx,1
	        int     10h
putchar_exit:
		pop	ax
	        pop     cx
	        pop     bx
	        ret
putchar         endp

;-----------------------------------------------------------------------------
; GETKEY  Waits for a key from the keyboard.
; Exit:   AX - Scan code, ASCII char from keyboard.
;-----------------------------------------------------------------------------
getkey          proc    near
	        mov     ah,1                    ;Check for key
	        int     16h
	        jnz     getkey_exit
	        int     28h                     ;Call DOS Idle
		mov	ax,1680h		;Release Timeslice
		int	2fh
	        jmp     short getkey
getkey_exit:
	        xor     ax,ax                   ;Get key
	        int     16h
		ret
getkey          endp
		align	16
end_of_resident =       $

;----------------------------------------------------------------------------
; Non-resident data.
;----------------------------------------------------------------------------
alrdy_installed db      0                       ;Installed flag
installed_seg   dw      0                       ;Segment of installed code
dos_version     dw      0                       ;DOS version

infomsg2        db      "DOSCLIP uninstalled$"

errmsg0         db      "Need DOS 3.0 or greater$"
errmsg1         db      "DOSCLIP not installed$"
errmsg2         db      "Usage: DOSCLIP [/B][/U]",13,10
	        db      "/B = Use Video BIOS",13,10
	        db      "/U = Uninstall",13,10,"$"
errmsg3         db      "Can",39,"t uninstall$"
errmsg4         db      "DOSCLIP already installed$"
errmsg5         db      "Can not find Critical error flag$"
endmsg          db      13,10,"$"

infomsg1        db      "DOSCLIP installed",13,10,10
	        db      "Copy key is "
		db	"Alt  C",13,10
	        db      "Paste key is "
		db	"Alt  V","$"

;----------------------------------------------------------------------------
; Initialization routine.
;----------------------------------------------------------------------------
initialize      proc    near
	        assume  cs:code,ds:code,es:code
	        cld
	        mov     dx,offset program       ;Print copyright message
	        call    printmsgcr

	        mov     ah,30h                  ;Get DOS version
	        int     21h
	        xchg    al,ah                   ;Swap major, minor numbers
	        mov     dx,offset errmsg0       ;Bad DOS version
	        cmp     ah,3                    ;Run if DOS 3.0 or greater.
	        jb      disp_error
	        mov     dos_version,ax          ;Save version number

	        mov     ax,offset end_of_code+512       ;Set stack ptr
	        mov     sp,ax
	        add     ax,15
	        mov     cl,4                    ;Convert offset to segment size
	        shr     ax,cl
	        mov     ah,4ah                  ;Reduce memory allocation
	        int     21h

	        call    find_installed		;See if already installed
	        jc      init_1
	        inc     alrdy_installed		;Yes, set flag
	        mov     installed_seg,es        ;Save seg of installed code
init_1:
	        push    ds
	        pop     es
	        mov     dx,offset errmsg4       ;Default message
	        mov     di,80h			;Parse command line
	        xor     cx,cx
	        or      cl,[di]                 ;Get length of cmd line
	        je      init_exit
init_2:
	        mov     al,'/'
	        repne   scasb                   ;Find command line switches
	        jne     init_exit
	        mov     al,[di]                 ;Get comamnd line switch
	        or      al,20h
	        cmp     al,'b'                  ;See if Video BIOS parameter
	        jne     init_3
	        inc	BIOSFlag
	        jmp     short init_2
init_3:
	        cmp     al,'u'                  ;See if uninstall
	        je	init_4
	        mov     dx,offset errmsg2       ;Print useage statement
;
;Display error message.
;
	        assume  ds:nothing
disp_error:
	        push    cs
	        pop     ds
	        assume  ds:code
	        call    printmsgcr              ;print string

	        mov     ax,4c01h                ;Terminate with RC = 1
	        int     21h
init_4:
	        call    remove                  ;Remove installed copy
	        jc	disp_error
		jmp	short exit
init_exit:
	        cmp     alrdy_installed,0
	        jne     disp_error
	        call    install
	        jc      disp_error
exit:
	        mov     ax,4C00h                ;Terminate with RC = 0
	        int     21h
initialize      endp

;-----------------------------------------------------------------------------
; INSTALL Installs the program
;-----------------------------------------------------------------------------
	        assume  cs:code,ds:code,es:code
install         proc    near
	        mov     ah,34h                  ;Get ptr to INDOS flag
	        int     21h
	        mov     word ptr indos_ptr,bx
	        mov     word ptr indos_ptr[2],es
	        call    findCEF                 ;Get ptr to crit error flag
	        jnc     install_1
	        jmp     install_error
install_1:
;
;Set interrupt vectors
;
	        mov     word ptr criterr_ptr,bx
	        mov     word ptr criterr_ptr[2],es
	        mov     al,08h                  ;Get/set the timer interrupt
	        mov     dx,offset timerint
	        mov     di,offset int08h
	        call    set_interrupt
	        mov     al,09h                  ;Get/set the keyboard int
	        mov     dx,offset keyint
	        mov     di,offset int09h
	        call    set_interrupt
	        mov     al,10h                  ;Get/set the video interrupt
	        mov     dx,offset videoint
	        mov     di,offset int10h
	        call    set_interrupt
	        mov     al,13h                  ;Get/set the disk interrupt
	        mov     dx,offset diskint
	        mov     di,offset int13h
	        call    set_interrupt
	        mov     al,28h                  ;Get/set the DOS idle int
	        mov     dx,offset idleint
	        mov     di,offset int28h
	        call    set_interrupt
	        mov     al,2Fh                  ;Get/set the DOS Multiplex
	        mov     dx,offset muxint
	        mov     di,offset int2Fh
	        call    set_interrupt

		push	cs
		push	cs
		pop	ds
		assume	ds:code
		pop	es
		assume	es:code
	        mov     dx,offset infomsg1      ;Print program installed msg
	        call    printmsgcr
;
;Init buffer pointers.
;
		mov	dx,ATTR_BUFFER		;Set pointers for resident
		add	dx,attr_buffsize	;  code.
		mov	helpbox_buff,dx
		add	dx,HELPBOX_BUFFSIZE
;
;Init clipboard pointers.
;
		mov	word ptr [clipboard_buff],dx 
		mov	bx,dx			;Save size of save data area
		add	dx,clipboard_size	;Add size of clipboard
		mov	ax,cs
 		mov	word ptr [clipboard_buff+2],ax
 		mov	word ptr [clipboard_dptr+2],ax
 		mov	word ptr [clipboard_dsize],0
 		mov	paste_flag,0
;
;Init switcher structures
;
	        mov     word ptr [DataBlockPtr+2],ax
	        mov     word ptr [DataBlockPtr],offset DataBlock
	        mov     word ptr [DataBlockSize],INSTDATASIZE

	        mov     word ptr [StackBlockPtr+2],ax
	        mov     word ptr [StackBlockPtr],offset end_of_resident
		sub	bx,offset end_of_resident	;Compute size of
	        mov     word ptr [StackBlockSize],bx	;  Data save area
 
	        mov     word ptr [sisInstData+2],ax
	        mov     ax,offset DataBlockPtr     
	        mov     word ptr [sisInstData],ax
		push	dx			;Save Ptr to end of resident
		
	        mov     ax,1600h                ;See if Enhanced mode windows
	        int     2fh
	        or      al,al
	        je      nowin
	        inc     win_enhanced            ;Set enhanced mode flag
nowin:
		pop 	dx			;Get Ptr to end of resident
	        add     dx,15
	        mov     cl,4
	        shr     dx,cl
	        mov     ax,3100h                ;Terminate and stay resident
	        int     21h
install_error:
	        ret
install         endp

;-----------------------------------------------------------------------------
; REMOVE uninstalls the installed program from memory.
;-----------------------------------------------------------------------------
remove          proc    near
	        assume  ds:code,es:code
	        push    ds
	        push    es

	        mov     ds,installed_seg        ;Point DS to installed code
	        assume  ds:nothing

	        mov     dx,offset errmsg1       ;Not installed message
	        cmp     alrdy_installed,0
	        je      remove_error1

	        mov     al,8                    ;Restore int 8 (Timer)
	        mov     dx,offset timerint
	        mov     di,offset int08h
	        call    restore_int
	        jc      remove_error
	        mov     al,9                    ;Restore int 9 (keyboard)
	        mov     dx,offset keyint
	        mov     di,offset int09h
	        call    restore_int
	        jc      remove_error
	        mov     al,10h                  ;Restore int 10h (BIOS Video)
	        mov     dx,offset videoint
	        mov     di,offset int10h
	        call    restore_int
	        jc      remove_error
	        mov     al,13h                  ;Restore int 13h (BIOS disk)
	        mov     dx,offset diskint
	        mov     di,offset int13h
	        call    restore_int
	        jc      remove_error
	        mov     al,28h                  ;Restore int 28h (DOS Idle)
	        mov     dx,offset idleint
	        mov     di,offset int28h
	        call    restore_int
	        jc      remove_error
	        mov     al,2Fh                  ;Restore int 2Fh (DOS Mux)
	        mov     dx,offset muxint
	        mov     di,offset int2Fh
	        call    restore_int
	        jc      remove_error
	        mov     es,ds:[2ch]             ;Get installed env seg
	        mov     ah,49h
	        int     21h                     ;Free installed env segment
	        push    ds
	        pop     es
	        mov     ah,49h                  ;Free installed program segment
	        int     21h
	        mov     ax,cs
	        mov     ds,ax
	        mov     es,ax
	        mov     dx,offset infomsg2      ;Print program removed msg
	        call    printmsgcr
	        clc
remove_exit:
	        pop     ds
	        pop     es
	        ret
remove_error:
	        mov     dx,offset errmsg3       ;Can't uninstall msg
remove_error1:
	        stc
	        jmp     short remove_exit
remove          endp

;-----------------------------------------------------------------------------
; SETINTERRUPT Get and sets an interrupt
; Entry: AL - Interrupt number
;        DX - Pointer to new interrupt routine
;        DI - Pointer to storage location for old interrupt vector
;-----------------------------------------------------------------------------
	        assume  cs:code,ds:code,es:nothing
set_interrupt   proc    near
	        push    es
	        push    ax
	        mov     ah,35h                  ;DOS get interrupt
	        int     21h
	        pop     ax
	        mov     word ptr [di],bx        ;Save old vector
	        mov     word ptr [di+2],es
	        mov     ah,25h                  ;DOS set interrupt
	        int     21h
	        pop     es
	        ret
set_interrupt   endp

;-----------------------------------------------------------------------------
; RESTOREINT Checks to see if an interrupt vector has been changed, if not
;            the interrupt vector is restored with its original value
; Entry:    AL - Interrupt number
;        DS:DX - Pointer to current interrupt routine
;        DS:DI - Pointer to old interrupt vector
;-----------------------------------------------------------------------------
	        assume  cs:code,ds:nothing
restore_int     proc    near
	        push    es
	        push    ax
	        mov     ah,35h                  ;DOS get interrupt
	        int     21h
	        pop     ax                      ;Get back interrupt number
	        cmp     dx,bx                   ;Compare routine offset
	        jne     restoreint_error
	        mov     bx,es                   ;Get current vector segment
	        mov     cx,ds                   ;Get installed segment
	        cmp     cx,bx                   ;Compare routine segment
	        jne     restoreint_error
	        push    ds
	        lds     dx,ds:[di]              ;Get old vector
	        mov     ah,25h                  ;DOS set interrupt
	        int     21h
	        clc
	        pop     ds
restoreint_exit:
	        pop     es
	        ret
restoreint_error:
	        stc
	        jmp     short restoreint_exit
restore_int     endp

;-----------------------------------------------------------------------------
; FINDCEF  Finds the DOS ErrorMode (Critical error) flag
; Exit:   ES:BX - Segment,offset of ErrorMode flag
;         CF - Clear if flag found
;-----------------------------------------------------------------------------
findCEF         proc    near
		mov	ah,34h			;Get InDOS address
		int	21h
		dec	bx		
		cmp	dos_version,30ah	;If DOS 3.1 or later, ErrorMode
		jnc	findCEF_exit		;  sits before InDOS.

	        mov     ax,3e80h                ;CMP opcode
		mov     si,028cdh               ;Int 28 Opcode
		mov     dl,75h                  ;JNE Opcode
	        mov     cx,-1                   ;max search length
	        mov     di,bx                   ;start at INDOS address
findCEF_1:
		repne   scasb                   ;do the search
	        jcxz    findCEF_notfound        ;branch if search failed
		cmp     es:[di],ah              ;Check other half of CMP opcode
		jne     findCEF_1
		cmp     byte ptr es:[di+4],dl   ;Check for JNE
		jne     findCEF_1
		cmp     word ptr es:[di+6],si   ; Check for Int 28h call
		jne     findCEF_1               ;Resume loop if not found
		inc     di
		mov     bx,es:[di]              ;Get offset of ErrorMode flag
		clc
findCEF_exit:
		ret
findCEF_notfound:
		stc
		mov	dx,offset errmsg5	;Can't find Critical Error flg
		jmp     short findCEF_exit
findCEF		endp

;-----------------------------------------------------------------------------
; FIND INSTALLED Find the installed code by scanning the memory control blocks.
; Exit:   AX - Segment of installed code if found.
;         CF - Clear if installed code found
;-----------------------------------------------------------------------------
find_installed  proc    near
	        assume  ds:code,es:code
	        mov     word ptr prog,0
	        mov     bx,0A000h               ;Start at upper mem blk start
	        mov     ax,cs                   ;keep CS value in AX
find_installed_1:
	        inc     bx                      ;increment search segment value
	        mov     es,bx
	        assume  es:nothing
	        cmp     ax,bx                   ;not installed if current
	        je      find_installed_2        ;  segment is found.
	        call    cmpheader
	        jne     find_installed_1        ;loop back if not found

	        clc
find_installed_exit:
	        ret
find_installed_2:
	        stc
	        jmp     short find_installed_exit
find_installed  endp

;-----------------------------------------------------------------------------
; CMPHEADER compares the first 16 bytes of this file with the segment
;           pointed to by ES.
; Entry:  DS - code segment
;         ES - pointer to segment to compare
; Exit:   ZF - 0 = segments match.
;-----------------------------------------------------------------------------
cmpheader       proc    near
	        assume  ds:code,es:nothing
	        mov     si,offset prog          ;Search this segment for ASCII
	        mov     di,si                   ;  fingerprint.
	        mov     cx,16
	        repe    cmpsb
	        ret
cmpheader       endp

;-----------------------------------------------------------------------------
; PRINTMSG prints the message pointed to by DX to the screen.
; Entry:  DX - pointer to ASCII message terminated by $
;-----------------------------------------------------------------------------
printmsg        proc    near
	        assume  ds:nothing,es:nothing
	        push    ds
	        push    cs
	        pop     ds
	        assume  ds:code
	        mov     ah,9                    ;Print message
	        int     21h
	        pop     ds
	        ret
printmsg        endp

;-----------------------------------------------------------------------------
; PRINTMSGCR calls PRINTMSG, then appends a carriage return to the message.
; Entry:  DX - pointer to ASCII message terminated by $
;-----------------------------------------------------------------------------
printmsgcr      proc    near
	        assume  ds:nothing,es:nothing
	        push    dx
	        call    printmsg
	        mov     dx,offset endmsg
	        call    printmsg
	        pop     dx
	        ret
printmsgcr      endp
	        even
end_of_code     =       $
code            ends

end             prog

