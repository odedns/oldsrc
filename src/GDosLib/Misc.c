
/*-------------------------------------------------------------------------*\
 *  Miscallenous functions.                    Date : 26/11/1993.          *
 *                                                                         *
 *  String handling functions.                                             *
 *                                                                         *
 *  Conversion functions.                                                  *
 *                                                                         *
 *  Date and time functions.                                               *
 *                                                                         *
 *                                                                         *
 *                                                                         *
 *                                                                         *
 *                                                                         *
\*-------------------------------------------------------------------------*/

/* Include Files *\
\* ------------- */

#include <stdio.h>
#include <string.h>
//#include <mem.h>
#include <dos.h>
#include <stdlib.h>
#include <ctype.h>
#include <stdarg.h>
#include <process.h>
#include "misc.h"

/* Global Variables *\
\* ---------------- */


/* Code *\
\* ---- */



/*------------------------------------------------------------------------*\
 *  Inverse a string.                                                     *
 *                                                                        *
\*------------------------------------------------------------------------*/
char *str_inv(char *s)      /* invers a string */
{
 char c , *s1 = s + strlen(s) - 1 , *t  = s;

    while( s1 > s) {
        c = *s;
        *s++ = *s1;
        *s1-- = c;
   }

 return(t);
 }


/*------------------------------------------------------------------------*\
 * Left justify a string.                                                 *
 *                                                                        *
\*------------------------------------------------------------------------*/
 char *str_left(char *s , int width)     /* left justfy string */
 {
 char *new;
 int len = strlen(s);

    if(width <= len)
	 return(s);

    if(NULL ==  (new = (char *) malloc(width +1)))
	 return(NULL);

    strcpy(new , s);
    memset((new + len ) , ' ',width - len);
    *(new + width) = '\0';

    return(new);

 }



/*------------------------------------------------------------------------*\
 *  Right justify a string.                                               *
 *                                                                        *
\*------------------------------------------------------------------------*/
 char *str_right( char *s , int width)  /* right justify string */
 {
 char *new;
 int len = strlen(s);

    if(width <= len)
	 return(s);

    if(NULL ==  (new = (char *) malloc(width +1)))
	 return(NULL);


    memset(new , ' ' , width - len);
    strcpy((new+width - len) , s);

    return(new);

 }


/*------------------------------------------------------------------------*\
 *  Centers a string.                                                     *
 *                                                                        *
\*------------------------------------------------------------------------*/

 char *str_center(char *s , int width)   /* center a string ! */
 {
 char *new;
 int len = strlen(s) , left , right;

    if(width <= len)
	 return(s);

    if(NULL ==  (new = (char *) malloc(width +1)))
	 return(NULL);

   left = (width - len) /2;
   right = width - len - left;

   memset(new , ' ',left);
   strcpy((new + left) , s);
   memset((new + left + len) , ' ' , right);
   *(new + width) =  '\0';

   return(new);
 }


/*------------------------------------------------------------------------*\
 *  Sets n chars in string s to char c.                                   *
 *                                                                        *
\*------------------------------------------------------------------------*/

/* set n chars in string to c */

 char *strn_set(char *s , char c , int count)
 {

    memset(s , c , count);
    *(s + count) = '\0';

    return(s);
 }




/*------------------------------------------------------------------------*\
 *                                                                        *
 * Inserts a string into another string                                   *
\*------------------------------------------------------------------------*/

 char *str_insert(char *s , char *insert , unsigned int pos)
 {
  char *new;
  int slen = strlen(s) , ilen = strlen(insert);

    if( NULL == (new = (char *) malloc(slen + ilen+1))  || pos > slen)
	 return(NULL);


    memcpy(new , s , pos);
    memcpy((new + pos) , insert , ilen);
    strcpy((new + pos + ilen) , (s + pos ));

    return(new);
 }


/*------------------------------------------------------------------------*\
 * Deletes count characters in string s starting at position pos          *
\*------------------------------------------------------------------------*/
char *str_delete(char *s , unsigned int pos , unsigned int count)
{
unsigned newpos = pos + count;

    if(pos > strlen(s))
	return(s);

    if(newpos > strlen(s))
       newpos = strlen(s);


    strcpy((s + pos) , (s + newpos));

    return(s);
 }

/*------------------------------------------------------------------------*\
 *  Convert  a character to hexadecimal string.                           *
 *                                                                        *
\*------------------------------------------------------------------------*/

char *char2hex  (char c, char *s)
{
char lut[] = "0123456789ABCDEF" ;      /* …‚˜‰š šŒˆ */
char *sav = s ;                        /* š†…˜‡Œ ’‰– ˜…‹† */
int offset ;

    offset = (c & 0xF0) >> 4 ;  /* š‰Œ€™„ „˜”‰‘„ Œ™ „Œˆ„ Š…šŒ ˆ‘‰„„ …™‰‡ */
    *s++ = lut[offset] ;        /* š‰Œ€™ „˜”‰‘ •™ */
    *s++ = lut[c & 0x0F] ;      /* š‰‰ „˜”‰‘ •™ */
    *s = 0 ;

    return (sav) ;
}


/*------------------------------------------------------------------------*\
 *    Convers a char to decimal string.                                   *
 *                                                                        *
\*------------------------------------------------------------------------*/


/* ‚…–‰‰ š‰…š-šŒš š†…˜‡Œ ASCII ‚…–‰‰ char š‚˜š char2dec () „‰–—…”„
**                                                          .‰Œ€‰–ƒ
*/

char *char2dec  (unsigned char c, char *s)
{
int i ;
char *t = s+3 ;      /* „€–…š„ š†…˜‡ “…‘Œ ’‰– Œ…‡š‰€ */

    for (*t-- = 0, i=0 ; i<3 ; ++i) {
	*t-- = (c % 10) + 0x30 ;         /* ASCII ‚…–‰‰Œ šƒƒ… „˜”‰‘ š‹‰”„ */
	c /= 10 ;                        /* ˜š…‰ š‰‰ „˜”‰‘ —…˜† */
    }

   return (s) ;
}


/*------------------------------------------------------------------------*\
 * Gets the printer status.  Returns 1 if ready 0 otherwise.              *
 *                                                                        *
\*------------------------------------------------------------------------*/

int printer_stat()      /* return printer status */
{
union REGS reg;
int status;

    reg.h.ah = 2;
    reg.x.dx = 0;
    int86(0x17 , &reg ,&reg);

    status = reg.h.ah;

    if((status & 0x80) == 0x80)
	return(1);
    return(0);
}


/*------------------------------------------------------------------------*\
 * Gets dos time into a formatted string.                                 *
 *                                                                        *
\*------------------------------------------------------------------------*/
char *getDate()       /* gets date from DOS ! */
{
static char date[9];
union REGS regs;

    regs.h.ah= 0x2a;
    intdos(&regs, &regs);
    sprintf(date, "%02.2d/%02.2d/%02.2d", regs.h.dl,
    regs.h.dh, regs.x.cx-1900);

    return(date);
}

/*------------------------------------------------------------------------*\
 *  YesNo prompting function.                                             *
 *                                                                        *
\*------------------------------------------------------------------------*/

int yorn(char *prompt, int dfault)
{
char s[128], c;
int len;

    printf("%s (%c) ", prompt, dfault ? 'y' : 'n');
    fgets(s, 128, stdin);
    len = strspn(s, " \t\n");    /* skip whitespace */

    if(len < strlen(s))
    {
	c = *(s + len);
	if(c == 'Y' || c == 'y') return YES;
	else if(c == 'N' || c == 'n') return NO;
    }

    return dfault;
}

/*------------------------------------------------------------------------*\
 * Gets bios time into formatted string.                                  *                                       *
 *                                                                        *
\*------------------------------------------------------------------------*/
char *get_time(char *TimeStr)
{
long far *Time;
int hour , min , sec;

Time = MK_FP(0x40 , 0x6c);

hour =(int) (*Time / 65520L);
min = (int) (*Time % 65520L / 1092);
sec = (int) (*Time % 65520L  % 1092);
sec = (int)(sec / 18.2) % 60;

sprintf(TimeStr , "%02d:%02d:%02d" , hour , min , sec);
return(TimeStr);
}

char *getword(char *i_str , char *o_word)
{
static char TempStr[255];
char *word_p , *i_str_p;

    if(i_str != NULL)         /*  get word from i_str   */
       i_str_p = i_str;
    else
       i_str_p = TempStr;    /* get word from TempStr */

    while(isspace(*i_str_p))  /* skip white spaces */
	  ++i_str_p;
    word_p = o_word;

    while(*i_str_p != '\0' && !isspace(*i_str_p))
	*o_word++ = *i_str_p++;            /* get first word into o_word */

    strcpy(TempStr , i_str_p);   /* copy rest of string into TempStr */
    *o_word = '\0';

    return(word_p);
}

/*---------- print system error message ------------*/
int sys_error()
{
	fprintf(stderr,sys_errlist[errno]);
	return(errno);
}


/*---------- execute a child process --------------*/
int exec_process(char *pCommand,char **pArgs)
{
	if(-1 == spawnvp(P_WAIT,pCommand,pArgs))
		return(sys_error());

	return(0);
}

int program_msg(char *format, ...)
{
char buf[255];
va_list argptr;
int stat;

    va_start(argptr ,format);
    stat = vsprintf(buf ,format , argptr);
    puts(buf);
    va_end(argptr);
    return(stat);
}
/*------------------------------------------------------------------------*\
 * Gets day of week for a given date.                                     *                                       *
 *                                                                        *
\*------------------------------------------------------------------------*/
char *getDayOfWeek(int day , int month, int year)
{
int index;
	char *dayStr[] = { "Monday",
			  "Tuesday",
			  "Wednsday",
			  "Thursday",
			  "Friday",
			  "Saturday",
			  "Sunday"
			  };
	if(month == 1 || month == 2 ) {
		month +=12;
		year--;
	}
	index = (day + 2 * month + 3 * (month+1)/5 + year +
		 year/4 - year/100 + year/400) % 7;
	return(strdup(dayStr[index]));
}
int StrCountWords(char *pStr)
{
int charFlag = 0;
int wordCount = 0;

	while(*pStr) {
		if(isspace(*pStr++) && charFlag) {
			++wordCount;
            /* eat white space */
			while(isspace(*pStr) && *pStr)  {
				++pStr;
			}
		} else {
			charFlag = 1;
		}
		if(*pStr == '\0') ++wordCount;
	}
	return(wordCount);
}

char **StrConvert(char *pStr)
{
char **argv, *pBuff;
int Offset = 0, count;


	count = StrCountWords(pStr);
	argv = (char **)calloc((count +1),sizeof(char*));

	pBuff = pStr;
	while(*pBuff) {
		while(isspace(*pBuff)) {
			*pBuff = '\0';
			++pBuff;
		}
		argv[Offset++] = pBuff;
		/* skip rest of word  */
		while(*pBuff && !isspace(*pBuff)) {
			++pBuff;
		}
	}
	argv[Offset] = NULL;
	return(argv);
}
