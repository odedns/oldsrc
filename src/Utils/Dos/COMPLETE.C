/*------------------------------------------------------------------------*/
/*   Module       :  complete.exe                                         */
/*   File         :  complete.c                                           */
/*   Date         :  01/08/1996                                           */
/*   Description  :  A UNIX tcsh style command line completion program.   */
/*   Author       :  Oded Nissan                                          */
/*                   Copyright (c)  1996/7 Oded Nissan.                   */
/*   History      :                                                       */
/*------------------------------------------------------------------------*/
/*   Date         |   Description                                         */
/*------------------------------------------------------------------------*/
/*   01/08/1996   |   Initial Release.                                    */
/*                |                                                       */
/*                |                                                       */
/*------------------------------------------------------------------------*/


#include <stdio.h>
#include <dir.h>
#include <dos.h>


#define CTRL_D (0x20)
#define TAB  (0x0F)
#define CMD_LEN  (128)
#define IS_DELIMITER(c)  (c == ' ' || c == '>' || c == '|' || c == '<')
#define IS_DIR(a)        (FA_DIREC & a)
#define TOLOWER(ch)  ( (ch < 'a' ? ch + 32 : ch))
#define  CTRL         (0x04)

#define HOTKEY TAB
#define IS_HOTKEY(x)  ( x == TAB || (x == CTRL_D && *key_flags & CTRL))

/*--------- function prototypes --------*/
void interrupt (*old_64)(void);
void interrupt new_64(void);
void interrupt (*old_28)(void);
void interrupt new_28(void);
void interrupt (*old_9)(void);
void interrupt new_9(void);
void activate_tsr();
int _unget(int key);

void get_cursor(int *x , int *y);
char *fname_split(char *path);
char *read_at_cursor();
int complete(char *s , char *filename);
int complete_cmd(char *s);
int listfiles();
unsigned get_psp();
/*---------- tsr vars -----------------*/

extern unsigned _heaplen = 1;
extern unsigned _stklen = 256;
unsigned int save_ss , save_sp , ss , sp;
unsigned intpsp;
int tsr_active = 0;
int hotkey = 0;
int key_func = 0;
char far *pVideo = MK_FP(0xb800 , 0); 
char far *key_flags = (char far *) MK_FP(0x00,0x0417);
char *prompt;

/* Code *\
\* ---- */

unsigned prog_size()
{
 return(*((unsigned far *) (MK_FP(_psp -1,3))));
}

/*------------------------------------------------------------------------*
 |                       Keyboard interrupt handler                       |
 *------------------------------------------------------------------------*/

void kbd_reset(void){    /* Reset Keyboard and programable interrupt      */
                         /* controller (PIC)                              */
     register char x;
     x = inportb(0x61);
     outportb(0x61, (x | 0x80));
     outportb(0x61, x);
     disable();
     outp(0x20, 0x20);
     enable();
}/* kbd_reset */

 /*-------- new int 28 ISR -----------*/
void interrupt new_28()
{

    (*old_28)();   /* call old interrupt */

	/* if hotkey was pressed and tsr isn't active - activate */
	if(hotkey  && !tsr_active) {
	   activate_tsr();
	}

}


/* ------------------------------------*/
void interrupt new_9()
{  
/* interrupt 9 handler (whenever a key is    */
/* pressed control arrives hear)             */
register char x;

	
	intpsp = get_psp();
	disable();
	x = inportb(0x60);      /* read keyboard data port          */
	/* check if interrupted psp is equal to that of command.com */
	/* so that tsr only gets activated in command prompt */
	/* if hotkey was pressed set hotkey flag */
	if (intpsp == peek(intpsp,0x16) &&
	    !tsr_active &&  IS_HOTKEY(x) ) {
		hotkey = 1;
		key_func = x;
		kbd_reset();   
	} else {
		hotkey = 0;
		/* call the old keyboard isr */
		(* old_9)();
	}

} /* intr_0x09 */


/*-------- get the current psp --------------*/
unsigned get_psp()
{
	_AH = 0x51;
	geninterrupt(0x21);
	return(_BX);
}

/*-------- tsr activate function -------*/
void activate_tsr()
{
char cmd[CMD_LEN] , *p;

	/* save old stack */
	disable();
	save_ss = _SS;
	save_sp = _SP;

	_SS = ss;
	_SP = sp;
	enable();

	/* tsr goes here */
	/* if tsr isn't active - activate it */
	tsr_active = 1;

	if(key_func == TAB) {
		p = cmd;
		if(!complete_cmd(p)) {
			while(*p) {
				_unget(*p++);
			}
		}
	} else {
	/* listfiles */

		if(!listfiles()) {
			printf("%s",prompt);
		}
	}

	/*  restore old stack */

	disable();
	_SP = save_sp;
	_SS = save_ss;
	enable();

	/* reset tsr and hotkey flags */
	tsr_active = 0;
	hotkey = 0;

}    /*--------- end activate_tsr  ---------*/


/*------- dummie int0x64 isr ---------*/

void interrupt new_64()
{
}

/*------- unget a key into the keyboard buffer -------*/
 int _unget(int key)  /* push value onto keyboard stack ! */
 {
     _AH = 5;
     _CX = key;
     geninterrupt(0x16);
     return((_AL != 1) ? 1 : 0);
 }



/*-------- get cursor position --------*/
void get_cursor(int *x , int *y)
{

	_AH = 0x03;
	_BH = 0x00;
	geninterrupt(0x10);
	*x = _DL;
	*y = _DH;

}

/*-------- remove a path from a file name ------*/
char *fname_split(char *path)
{
char *file = path + strlen(path) -1;

	while(file >  path) {
	  if(*file == ':' ||  *file == '\\') {
			++file;
			break;
	  }
		--file;
	}
	if(file == path && *file == '\\')
		++file;

	return(file);
}

/*--------read the word at the cursor from video ram --------*/
char *read_at_cursor()
{
int x,y , pos;
char far *p;
static char scr_word[CMD_LEN+1];
char *word = &scr_word[CMD_LEN-1];

	scr_word[0] = '\0';
	get_cursor(&x,&y);
	pos = 2 * ( y * 80 + x);
	p = pVideo + pos;
	p-=2;
	*word = '\0';
	while(!isspace(*p)) {
		if(IS_DELIMITER(*p)) 
			break;

		--word;
		*word = *p;
		p-=2;
	}
	scr_word[CMD_LEN] = '\0';

	return(word);
}

/* read to eol */

char *read_to_eol()
{
int x,y , pos;
char far *p;
static char scr_word[CMD_LEN+1];
char *word = &scr_word[CMD_LEN-1];


	get_cursor(&x,&y);
	pos = 2 * ( y * 80 + x);
	p = pVideo + pos;
	p-=2;
	*word = '\0';
	while(x) {
		--x;
		--word;
		*word = *p;
		p-=2;
	}
	scr_word[CMD_LEN] = '\0';

	return(word);
}



/*------ complete a mask int s into filename -----*/
int complete(char *s , char *filename)
{
int stat;
struct ffblk fs;

	if(s) {
		if(strchr(s,'.'))
			strcat(s,"*");
		else
			strcat(s,"*.*");
	}
	stat = findfirst(s,&fs,FA_DIREC | FA_ARCH | FA_RDONLY);
	if(!stat)  { 
		strcpy(filename , strlwr(fs.ff_name));
	}

	 return(stat);
}

/*---------complete a filename or dir -----------*/
int complete_cmd(char *s)
{
char *mask , cmd[MAXPATH] , *pcmd;
int stat;

	mask = read_at_cursor();
	stat = complete(mask,cmd);
	if(stat) {
		return(stat);
	}
	pcmd = cmd;
	mask = fname_split(mask);
	while(TOLOWER(*mask) == TOLOWER(*pcmd) && *mask && *pcmd) {
		++mask;
		++pcmd;
	}
	while(*pcmd) {
		*s++ = *pcmd++;
	}
	*s = '\0';
	return(stat);
}
/****** list all files that can be completed ******/

int listfiles()
{

int stat,ret_stat = 0;
struct ffblk fs;
char *s;

	prompt = read_to_eol();
	s = read_at_cursor();

	if(s) {
		if(strchr(s,'.'))
			strcat(s,"*");
		else
			strcat(s,"*.*");
	}
	stat = findfirst(s,&fs,FA_DIREC | FA_ARCH | FA_RDONLY);
	if(!stat) {
		putchar('\n');
		while(!stat)  { 
			printf("%-20s",strlwr(fs.ff_name));
			stat = findnext(&fs);
		}
		putchar('\n');
	} else {
		ret_stat = 1;
	}

	 return(ret_stat);
}

/****   main program ****/

int main(void)
{

    printf("%s",
           "*** Complete - Unix Style Command Completion For DOS ***\n"
            "    By Oded Nissan  Version 1.0 15/09/96\n"
	    "    Press <TAB> to complete filenames\n"
	    "    Press <CTRL-D> to list files\n");

    /* check if TSR already loaded */
    old_64 = getvect(0x64);
    if(!old_64)
	/* set TSR loaded indication */
		setvect(0x64,new_64);
	else {
		puts("Complete Is Already Resident!\n");
		return(1);
    }

	/* get program stack */
    ss = _SS;
    sp = _SP;

    /* get old ISR's */
    old_28 = getvect(0x28);
    old_9 = getvect(0x09);

    /* set new ISR's  */
    setvect(0x28 , new_28);
    setvect(0x09 , new_9);


    /* terminate stay resident */
	keep(0, prog_size());

	return(0);

}  /*** end main    ***/
