/*------------------------------------------------------------------------*/
/*   Module       :                                                       */
/*   File         :                                                       */
/*   Date         :                                                       */
/*   Description  :                                                       */
/*   Author       :  Oded Nissan                                          */
/*                   Copyright (c) 1994,1997 Oded Nissan.                 */
/*   History      :                                                       */
/*------------------------------------------------------------------------*/
/*   Date         |   Description                                         */
/*------------------------------------------------------------------------*/
/*   01/01/1997   |   Initial Release.                                    */
/*                |                                                       */
/*                |                                                       */
/*------------------------------------------------------------------------*/

#include <stdio.h>
#include <dos.h>
#include <stdarg.h>


#define __VIDEO_INTR 		(0x10)
#define __VIDEO_PAGE_SZ  	(4096)
#define __SCROLL_UP    		(6)
#define __SCROLL_DOWN		(7)

typedef struct  {
		unsigned addr;
		int 	attrib;
		int 	active_page;
		int		cols;
		int		rows;
		} SCREEN_INFO;

static SCREEN_INFO __curr_scr;

/* get current screen mode */
int get_scr_mode()
{
	_AH = 0x0f;
	geninterrupt(__VIDEO_INTR);
	return(_AL);
}

/* set the current screen mode */
int set_scr_mode(int mode)
{
	_AH = 0x00;
	_AL = mode;
	geninterrupt(__VIDEO_INTR);
	return(mode);
}

/* initialize the screen
 * get the screen mode
 * get screen address
 * get number of columns
 * returns current screen mode
 */
int init_scr()
{
	_AH = 0x0f;
	geninterrupt(__VIDEO_INTR);
	__curr_scr.active_page = _BH;
	__curr_scr.cols = 80;
	__curr_scr.rows = 25;
	__curr_scr.attrib = 0x07;

	if(_AL == 0x07) {
			__curr_scr.addr = 0xB000;
	} else {
			__curr_scr.addr = 0xB800;
	}
	return(_AL);
}

/* set the cursor size */
void set_cursor(int size)
{
	_AH = 0x01;
	_CX = size;
	geninterrupt(__VIDEO_INTR);
}

/*  position the cursor 
 *  at col x row y
 */
void pos_cursor(int x, int y)
{
	_AH = 0x02;
	_BH = __curr_scr.active_page;
	_DL = x;
	_DH = y;
	geninterrupt(__VIDEO_INTR);
}

/* get current cursor position
 * column returned in x
 * row returned in y
 * returns cursor size
 */
int get_cursor(int *x, int *y)
{
	_AH = 0x03;
	_BH = __curr_scr.active_page;
	geninterrupt(__VIDEO_INTR);
	*x = _DL;
	*y = _DH;
	return(_CX);
}

/* sets the active video page */
void set_video_page(int page)
{
	_AH = 0x05;
	_AL = page;
	geninterrupt(__VIDEO_INTR);
}

int get_video_page()
{
	return(__curr_scr.active_page);
}

/* scrolls the screen
 * cmd - scroll up or down
 * lines - number of lines to scroll
 * left,right,top, bottom - rectangle to scroll
 * att - the attribute 
 */
void scr_scroll(int cmd, int lines, int left , int right,
			   int top, int bottom, int att)
{
	_AH = cmd;
	_AL = lines;
	_CL = left;
	_CH = top;
	_DL = right;
	_DH = bottom;
	_BH = att;
	geninterrupt(__VIDEO_INTR);
}

/* gets a char and its attribute
 * from current cursor position
 */
int get_scr_char()
{
	_AH = 0x08;
	_BH = __curr_scr.active_page;
	geninterrupt(__VIDEO_INTR);
	return(_AX);
}

void scr_clear()
{
	scr_scroll(__SCROLL_UP,0,0,__curr_scr.cols,
			   0,__curr_scr.rows,__curr_scr.attrib);
	pos_cursor(0,0);
}

void scr_clear_rec(int left, int right , int top , int bottom)
{
	scr_scroll(__SCROLL_UP,bottom-top,left,right,
			   top,bottom,__curr_scr.attrib);
}


int scr_dup_putch(int c, int count)
{
int x , y;

	get_cursor(&x , &y);

    switch(c) {
		case '\n' :  putchar('\n');
			 break;
		case '\r' :  pos_cursor(0,y);
			 break;

	default   :
			 _AH = 9;
			 _AL = c;
			 _BL = __curr_scr.attrib;
			 _BH = __curr_scr.active_page;
			 _CX = count;
			 geninterrupt(__VIDEO_INTR);
			 pos_cursor(x+count,y);
			 break;
	}
	return(c);
}

int scr_putch(int c)
{
int x , y;

	get_cursor(&x , &y);

	switch(c) {
		case '\r' : pos_cursor(0,y);
					break;
		case '\n' : ++y;
					if(y == __curr_scr.rows) {
						scr_scroll(__SCROLL_UP,1,0,79,0,24,
								   __curr_scr.attrib);
					--y;
					}
					pos_cursor(0,y);
					break;
		default   :
			 _AH = 9;
			 _AL = c;
			 _BL = __curr_scr.attrib;
			 _BH = __curr_scr.active_page;
			 _CX = 1;
			 geninterrupt(__VIDEO_INTR);
			 pos_cursor(x+1,y);
			 break;
	}
	return(c);
}
	

int scr_puts(char *s)
{
int c = *s;

	while(*s) {
		scr_putch(*s);
		c = *s++;
	}
	return(c);
}

int scr_printf(char *fmt,...)
{
char buf[255];
va_list argptr;
int rv;

	va_start(argptr ,fmt);
	rv = vsprintf(buf ,fmt , argptr);
	scr_puts(buf);
	va_end(argptr);
	return(rv);
}

int set_attrib(int attrib)
{
int old_attrib = __curr_scr.attrib;
	__curr_scr.attrib = attrib;
	return(old_attrib);
}
	

int save_scr(int left, int right, int top, int bottom, char *buf)
{
unsigned char far *p  = MK_FP(__curr_scr.addr,0);
int pos; 
int i, j;

		for(i = top; i <= bottom; ++i) {
			pos =  2 * (__curr_scr.cols * i + left);
			for(j = left; j <= right; ++j) {
				*buf++ = p[pos++];
				*buf++ = p[pos++];
			}
		}
		*buf = '\0';
		return(0);
}
			
int restore_scr(int left, int right, int top, int bottom, char *buff)
{
unsigned char far *p  = MK_FP(__curr_scr.addr,0);
int pos; 
int i, j;

		for(i = top; i <= bottom; ++i) {
			pos =  2 * (__curr_scr.cols * i + left);
			for(j = left; j <= right; ++j) {
				p[pos++] = *buff++;
				p[pos++] = *buff++;
			}
		}
		return(0);
}

void scr_del_rec(int left,int right, int top,int bottom,int att)
{
unsigned char far *p  = MK_FP(__curr_scr.addr,0);
int pos; 
int i, j;

		for(i = top; i < bottom; ++i) {
			pos =  2 * (__curr_scr.cols * i + left);
			for(j = left; j < right; ++j) {
				p[pos++] = ' ';
				p[pos++] = att;
			}
		}
}


void scr_move_rec(int left, int right, int top, int bottom,
					int new_left,int new_top)
{
char buf[4096];
	
		save_scr(left,right,top,bottom,buf);
		scr_del_rec(left,right,top,bottom,__curr_scr.attrib);
		restore_scr(new_left,new_left + right - left,
				 new_top,new_top+ bottom-top,buf);
}

void scr_scroll_line(int cmd,int left, int right,int top, int bottom,
					  int att)
{
	if(cmd == __SCROLL_UP) {
		scr_move_rec(left,right,top+1,bottom,left,top);
	} else { /* scroll down */	
		scr_move_rec(left,right,top,bottom-1,left,top);
	}

}
void scr_del_line(int line)
{
unsigned char far *p  = MK_FP(__curr_scr.addr,0);
int i;	

	/* point to start of line - not end of line */
	if(line) 
		--line;

	p+= (line * __curr_scr.cols) * 2;
	for(i=0; i < __curr_scr.cols; ++i) {
		*p = ' ';
		p+=2;
	}
}

void scr_clear_eol()
{
int x, y;

	get_cursor(&x,&y);			
	while(x < __curr_scr.cols) {
		scr_putch(' ');
	}
}
	
#define _TEST
#ifdef _TEST

main()
{
int i;
char screen[4096];

	init_scr();
	scr_clear();
	pos_cursor(0,0);
	for(i = 0; i < 30; ++i) {
		scr_printf("Line # %d\n",i);
	}
	getch();
	scr_puts("Poo and Oded\n");

}

#endif

