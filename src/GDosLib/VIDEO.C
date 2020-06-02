/*---------------------------------------------------------------------------*\
 *  Video IO functions.                        Date : 26/11/1993.            *
 *                                                                           *
 *                                                                           *
 *                                                                           *
 *                                                                           *
 *                                                                           *
 *                                                                           *
 *                                                                           *
 *                                                                           *
 *                                                                           *
 *                                                                           *
 *                                                                           *
\*---------------------------------------------------------------------------*/

/* Include Files *\
\* ------------- */

#include <stdio.h>
#include <dos.h>
#include <stdarg.h>
#include <conio.h>
#include <stdlib.h>
#include "clib.h"


/* Global Variables *\
\* ---------------- */

unsigned ACTIVE_PAGE = 0;

#define VID_OFFS    (4096 * ACTIVE_PAGE)
/* Code *\
\* ---- */

/*-------------------------------------------------------------------------*\
 *   sets the cursor shape.                                                *
 *                                                                         *
\*-------------------------------------------------------------------------*/

void set_cursor(unsigned int shape)
/* Sets the shape of the cursor */
{
 union REGS reg;

 reg.h.ah = 1;
 reg.x.cx = shape;
 int86(0X10, &reg, &reg);
} /* setcursor */


/*-------------------------------------------------------------------------*\
 *    Gets current cursor position into x & y                              *
 *    From the ACTIVE PAGE                                                 *
\*-------------------------------------------------------------------------*/

void get_cursor(int *x , int *y)
{
union REGS reg;

    reg.h.ah = 3;
    reg.h.bh = ACTIVE_PAGE;
    int86( 0x10 , &reg , &reg);
    *x = reg.h.dl;
    *y = reg.h.dh;
}


/*-------------------------------------------------------------------------*\
 *  Positions the cursor at column x row y  in the active video page.      *
 *  screen starts at 0 , 0.                                                *
\*-------------------------------------------------------------------------*/
void pos_cursor(int x , int y)   /* position the cursor */
{
union REGS reg;

    reg.h.ah = 2;
    reg.h.bh = ACTIVE_PAGE;
    reg.h.dl = x;
    reg.h.dh = y;
    int86(0x10 , &reg , &reg );
 }


/*-------------------------------------------------------------------------*\
 *  Activates a video page.                                                *
 *                                                                         *
\*-------------------------------------------------------------------------*/
int get_active_page(unsigned int page )
{
union REGS reg;

    reg.h.ah = 5;
    reg.h.al = page;
    int86(0x10 , &reg , &reg );
    return(ACTIVE_PAGE = page);
}

int set_active_page(unsigned page)
{
    ACTIVE_PAGE = page;
    return(page);
 }
/*-------------------------------------------------------------------------*\
 *  Read the keyboard buffer returns ascii code of character               *
 *  If key is a function key scan code is returned.                        *
\*-------------------------------------------------------------------------*/
int getkey()
{
union REGS reg;  /* get a key from keyboard return ascii  & scan code */

    reg.h.ah = 0;
    int86(0x16 , &reg , &reg);
    return((reg.h.al ? reg.h.al : reg.h.ah));
}

/*-------------------------------------------------------------------------*/

/*-------------------------------------------------------------------------*\
 *  Clears the screen and homes the cursor in the active page.             *
 *                                                                         *
\*-------------------------------------------------------------------------*/
void cls()   /* clear the screen */
{
union REGS reg;

    pos_cursor(0 , 0 );
    reg.h.al = 0x20;
    reg.h.ah = 9;
    reg.h.bh = ACTIVE_PAGE;
    reg.h.bl = 7;
    reg.x.cx = 2000;
    int86(0x10 , &reg , &reg);
    pos_cursor(0 , 0);

 }
  /*-------------------------------------------------------------------------*/

    /* draw a Box using color attribute at specific locations */

/*-------------------------------------------------------------------------*\
 *  Draw a box at specified coordinates with border attribute and box type *
 *  box is written using bios functions to the active page.                *
\*-------------------------------------------------------------------------*/
void Box( int left , int right , int top , int bottom , int att , int type)
{
int j;
char rchar , lchar , urchar , ulchar , tchar , bchar ,
	      brchar , blchar;


int Bios_putc(char , int );
int Bios_Dup_putc(char , int ,int);

    if(type == 1)       /* single line Box */
    {
	 tchar = 196;
	 bchar = 196;
	 rchar = 179;
	 lchar = 179;
	 ulchar = 218;
	 urchar = 191;
	 blchar = 192;
	 brchar = 217;

    } else  if (type == 2) {   /* double line Box */

	 tchar =  205;
	 bchar =  205;
	 rchar =  186;
	 lchar =  186;
	 ulchar = 201;
	 urchar = 187;
	 blchar =  200;
	 brchar =  188;
      }

      else {                 /* thick line Box */
	 tchar =  219;
	 bchar =  219;
	 rchar =  219;
	 lchar =  219;
	 ulchar = 219;
	 urchar = 219;
	 blchar =  219;
	 brchar =  219;
     }


    pos_cursor(left , top);      /* right top left char */
    Bios_putc(ulchar , att) ;

    pos_cursor(left + 1 , top);
    j = right - left -1;
    Bios_Dup_putc(tchar , att , j);  /* write top line */

    pos_cursor(right , top);       /* write top right char */
    Bios_putc(urchar , att);

    for( j = top + 1; j < bottom; j++)
    {
         pos_cursor(left ,j);            /* write left & right chars */
	 Bios_putc(lchar , att);
         pos_cursor(right ,j);
	 Bios_putc(rchar , att);
    }

    pos_cursor(left , bottom);       /* write bottom left char */
    Bios_putc(blchar , att);

    pos_cursor(left + 1 , bottom);
    j = right - left - 1;

    Bios_Dup_putc(bchar , att , j);  /* write bottom line */


    pos_cursor(right , bottom);       /* write bottom right char */
    Bios_putc(brchar ,att);

}    /* end Box */

/*-------------------------------------------------------------------------*/
/*   change screen attribute at position x y (starting at 0 , 0)           */
/*-------------------------------------------------------------------------*/

 /*-------------------------------------------------------------------------*\
  *  Change screen attribute at coordinates x , y  for len characters.      *
  *                                                                         *
 \*-------------------------------------------------------------------------*/
int change_att(int x , int y , int len , int att)
{
int i , pos;

    pos = 2*(y * 80 + x ) + VID_OFFS;
    for(i = pos ;  len > 0; i+=2 , --len)
	 pokeb(Video , i +1     , att);

    return(att);
}


/*-------------------------------------------------------------------------*\
 *  Writes a character with attribute at curren cursor position to the     *
 *  ACTIVE PAGE. Cursor is advanced.  (using BIOS function).               *
\*-------------------------------------------------------------------------*/

int Bios_putc(char c , int att)
{
 union REGS reg;
 int x , y;

    get_cursor(&x , &y);

    switch(c) {
        case '\n' :  pos_cursor(0 ,++y);
		     break;
        case '\r' :  pos_cursor(0 , y);
		     break;

	default   :
		     reg.h.ah = 9;
		     reg.h.al = c;
		     reg.h.bl = att;
		     reg.h.bh = ACTIVE_PAGE;
		     reg.x.cx = 1;
		     int86( 0x10 ,&reg , &reg);
                     pos_cursor(x+1 , y);
		     break;
   }

    return((int)c);
}

/*-------------------------------------------------------------------------*\
 * Writes count times the character with attribute at current cursor       *
 * position to the active page. (uses BIOS) cursor is not moved.           *
\*-------------------------------------------------------------------------*/

int Bios_Dup_putc(char c , int att , int count)
{
union REGS reg;

    reg.h.ah = 9;
    reg.h.al = c;
    reg.h.bl = att;
    reg.h.bh = ACTIVE_PAGE;
    reg.x.cx = count;
    int86( 0x10 , &reg , &reg);
    return(count);
}


 /*-------------------------------------------------------------------------*\
  *  Prints a string with attribute using BIOS to the ACTIVE PAGE.          *
  *                                                                         *
 \*-------------------------------------------------------------------------*/

int Bios_puts(char *s , int att)
{  int cnt = 0;
    while(*s)  {
       ++cnt;
       Bios_putc(*s++ , att );
    }
    return(cnt);
}

int Bios_printf(int att ,char *format, ...)
{
char buf[255];
va_list argptr;
int stat;

    va_start(argptr ,format);
    stat = vsprintf(buf ,format , argptr);
    textattr(att);
    Bios_puts(buf ,att);
    va_end(argptr);
    return(stat);
}

/*-------------------------------------------------------------------------*/
int Fast_puts(int att , char *s  )
{
int len =0 ;

    while(*s) {
      Fast_putc(att , *s);
      ++len;
      ++s;
    }
    return(len);

}



/*-------------------------------------------------------------------------*\
 *  Writes a character with attribute directly to video ram.               *
 *                                                                         *
\*-------------------------------------------------------------------------*/

int Fast_putc(int att , char c)
{
char far *Screen;
int pos ,x ,y;

    get_cursor(&x , &y);
    
    switch(c) {
        case '\n'  : pos_cursor(0 , ++y);
		     break;
        case '\r'  : pos_cursor(0 , y);
		     break;
        case '\t'  : pos_cursor(x + 8 , y);
		     break;
	default    :
		     Screen = MK_FP(Video , VID_OFFS);
		     pos = 160 *(y) + 2 *x;
		     Screen[pos++] = c;
		     Screen[pos] = att;
                     pos_cursor(x + 1 ,y);
		     break;
    }

    return((int)c);

}


  /* enhanced prints function with cursor position and attribute ! */

/*-------------------------------------------------------------------------*\
 *                                                                         *
 * enhanced prints function with cursor position and attribute !           *
\*-------------------------------------------------------------------------*/

int eprintf(int att ,char *format, ...)
{
char buf[255];
va_list argptr;
int stat;

    va_start(argptr ,format);
    stat = vsprintf(buf ,format , argptr);
    textattr(att);
    aputs(buf);
    va_end(argptr);
    return(stat);
}

int Fast_printf(int att ,char *format, ...)
{
char buf[255];
va_list argptr;
int stat;

    va_start(argptr ,format);
    stat = vsprintf(buf ,format , argptr);
    Fast_puts(att , buf);
    va_end(argptr);
    return(stat);
}


/*-------------------------------------------------------------------------*\
 *   color puts function supports '\n' character                           *
 *                                                                         *
\*-------------------------------------------------------------------------*/
 int aputs( char *s)
{
    while(*s)  {
       if(*s == '\n') {
	   putch('\n');
	   putch('\r');
       } else
	 putch(*s);
       ++s;
    }
   return(*s);
}


/*-------------------------------------------------------------------------*\
 *   Read a character and echo to the screen using attribute.              *
 *                                                                         *
\*-------------------------------------------------------------------------*/

unsigned int Bios_getche()
{
char c;
union REGS reg;

     reg.h.ah = 0x00;
     int86(0x16 , &reg , &reg);

      if(reg.h.al ) {
	  c = reg.h.al;
      Bios_putc(c , 7);
      }

      return(c);
}


/*-------------------------------------------------------------------------*\
 *  scrolls the screen using the BIOS.                                     *
 *                                                                         *
\*-------------------------------------------------------------------------*/
void scroll(int dir ,int lines , int att,int lcol , int trow ,
            int rcol , int brow)      /* scrolls the screen ! */
{
union REGS reg;

    if(dir == UP)
	 reg.h.ah = 6;
    else
	 reg.h.ah = 7;

    reg.h.al = lines;
    reg.h.cl = lcol;
    reg.h.ch = trow;
    reg.h.dl = rcol;
    reg.h.dh = brow;
    reg.h.bh = att;
    int86(0x10 , &reg , &reg);

}


/*-------------------------------------------------------------------------*\
 *  Returns the current cursor shape.                                      *
 *                                                                         *
\*-------------------------------------------------------------------------*/
int get_cursorShape()    /* gets cursor shape */
{
union REGS reg;

    reg.h.ah = 3;
    int86(0x10 , &reg , &reg);
    return(reg.x.dx);

}


/*-------------------------------------------------------------------------*\
 *  Saves specified coordinates of the screen into buffer.                 *
 *                                                                         *
\*-------------------------------------------------------------------------*/
 char *save_scr(int x , int y , int x1 , int y1 , char *buf)
 {
 int len ;
 int pos;
 char *scr = buf;

      for(; y <= y1; ++y)
      {
	   pos = 2*( y * 80 + x) + VID_OFFS;
	   for(len = 2*(x1 -x+1) ; len > 0; --len)
	   {
		*buf++ = peekb(Video , pos);
		++pos;
	   }
       }

       *buf = '\0';
       return(scr);
 }    /* end save_scr */


/*-------------------------------------------------------------------------*\
 * Restores specified coordinates of the screen from buffer.               *
 *                                                                         *
\*-------------------------------------------------------------------------*/
 void rest_scr(int x , int y , int x1 , int y1 , char *buf)
 {
 int len = y1 - y;
 int pos;
 char c;

    for(; y <= y1; ++y)
    {
	pos = 2*( y * 80 + x) +VID_OFFS;

	for(len = 2*(x1 -x+1); len > 0; --len)
	{
	     c = *buf++;

pokeb( Video , pos , c);
	     ++pos;
	 }      /* end column loop */

     }    /* end row loop */

 } /* end rest_scr */


/*-------------------------------------------------------------------------*\
 *   Moves a screen block to new position on the screen.                   *
 *                                                                         *
\*-------------------------------------------------------------------------*/
int move_block(int x , int  y , int x1 ,int y1 , int new_x , int new_y ,int att)
{

char *buf;

    if((buf = (char *) malloc(25*80*2)) == NULL)
	 return(-1);

    if ((buf = save_scr(x , y , x1 ,y1 ,buf)) == NULL)
	 return(-1);

    scroll(DOWN , y1 - y +1 , att , x , y , x1 ,y1);
    rest_scr(new_x , new_y , new_x + (x1 -x) , new_y +(y1 -y) ,buf);
    free(buf);

    return(0);
}


/*-------------------------------------------------------------------------*\
 *  Deletes a line from the screen. The screen is scrolled.                *
 *                                                                         *
\*-------------------------------------------------------------------------*/
int del_line(int y ,int att)
{
    if ((move_block(0 , y , 79 ,24 , 0 , y -1 ,att)) == -1)
	 return(-1);

     return(0);
}


/*-------------------------------------------------------------------------*\
 *  Fills a screen area with character and attribute.                      *
 *                                                                         *
\*-------------------------------------------------------------------------*/
 void fill_area(int x , int y , int x1 , int y1 , int att , char c)
 {
 int len = y1 - y;
 int pos;

     for(; y <= y1; ++y)
     {
	pos = 2*( y * 80 + x) +VID_OFFS;

	for(len = (x1 -x+1); len > 0; --len)
	{
	    pokeb( Video , pos , c);
	    pokeb(Video , ++pos , att);
	    ++pos;
	}      /* end column loop */

     }    /* end row loop */

 } /* end fill_area  */


/*------- write string to video ram ---------*/

void write_direct(char *s,int pos , int att)
{
int i;
char far *Screen = MK_FP(Video , VID_OFFS);

    for(i = 2 * pos; *s != '\0'; i++) {
       Screen[i++] = *s++;
       Screen[i] = att;
    }
}

