
/*---------------------------------------------------*\
 *                                                   *
 *                                                   *
 *                                                   *
 *                                                   *
 *                                                   *
\*---------------------------------------------------*/
#include <stdio.h>
#include <string.h>
#include <alloc.h>
#include <stdarg.h>
#include <ctype.h>
#include <dos.h>

#include "clib.h"

#define MOVE_UP       1
#define MOVE_DOWN     2
#define MOVE_LEFT     3
#define MOVE_RIGHT    4

/* ------------------------------------------------------------------------*/

/* ------------------------------------------------------------------------*/
int alloc_window(WINDOW **win)
{
    if(NULL == (*win = (WINDOW *) malloc(sizeof(WINDOW))))
       return(-1);
    return(0);
}

    int init_window(WINDOW *win , int x , int y , int x1 , int y1,
		    int box_type , int fill_char ,char *title)
    {
    win->upper_row = y;
    win->left_col = x;
    win->right_col = x1;
    win->bottom_row = y1;
    win->box_type = box_type;
    win->fill_char = fill_char;
    if(title != NULL) {
       if(NULL== (win->title = strdup(title)))
	  return(-1);
    }else
       win->title = NULL;
    return(0);

    }
/* ------------------------------------------------------------------------*/

void set_window_colors(WINDOW *win ,int box_att , int fill_att,
		       int title_att )
{
    win->box_att = box_att;
    win->fill_att = fill_att;
    win->title_att = title_att;
}
/* ------------------------------------------------------------------------*/
int display_window(WINDOW *win)
{
 char *buf;

    if(NULL == (buf = (char *)malloc(2000)))
       return(-1);
    if(NULL == (win->save_buf = save_scr(win->left_col -1 ,win->upper_row-1 ,
		win->right_col+1 ,win->bottom_row +1,buf)))
       return(-1);

    if(win->box_att)
	 Fbox(win->left_col-1 , win->right_col+1 ,
	      win->upper_row-1 , win->bottom_row+1,
	      win->box_att , win->box_type , win->fill_att ,win->fill_char);

    if(win->title != NULL)
       title_window(win , win->title , win->title_att);
    wcursor(win ,0 , 0);
    return(0);
}


/***************************************************************************\
* window io functions                                                       *
\***************************************************************************/
int wputc(WINDOW *win , char c)
{
char far *Screen;
int pos ,x ,y;

    wget_cursor(win , &x , &y);
    if(x > (win->right_col - win->left_col)) {
       if(y < (win->bottom_row - win->upper_row))
	   ++y;
       else
	 wscroll(win , UP , 1);

       x = 0;
    }

    Screen = MK_FP(Video , 0);
    pos = 160 * (y +win->upper_row) + 2 * (x+ win->left_col);

    if(c == '\n') {
       if(y < (win->bottom_row - win->upper_row))
	   ++y;
       else
	 wscroll(win , UP , 1);

	wcursor(win , 0 , y);
	return(c);
    }
    if(c == '\r') {
       wcursor(win , 0 , y);
       return(c);
    }
    Screen[pos++] = c;
    Screen[pos] = win->fill_att;

    wcursor(win , x + 1 ,y);

    return((int)c);
}


int wputs(WINDOW *win , char *s)
{

    while(*s != '\0') {
       wputc(win , *s);
       ++s;
    }
    return((int)*s);
}
/* ------------------------------------------------------------------------*/
int wprintf(WINDOW *win ,char *format, ...)
{
char buf[255];
va_list argptr;
int stat;

    va_start(argptr ,format);
    stat = vsprintf(buf ,format , argptr);
    wputs(win , buf);
    va_end(argptr);
    return(stat);
}
/* ------------------------------------------------------------------------*/
int wgets(WINDOW *win , char *s , int nelem)
{
 int x ,y , count = 0;
 char c;
    wget_cursor(win ,&x , &y);
    while((c = getkey()) != 13 && nelem-- > 0) {
	if(x > win->right_col - win->left_col - 1) {
	  x = -1;
	  ++y;
	}
	switch (c) {
	   case SPACE : *s++ = ' ';
			Fast_putc(win->fill_att ,c);
			++x;
			wcursor(win , x , y);
			break;

	   case TAB   : *s++ = '\t';
			x+=8;
			if( x > win->right_col)
			   x = 0;
			wcursor(win , x , y);
			break;

	   default :
		       if(isalnum(c) && c) {
			  *s++ = c;
			  ++count;
			  wputc(win , c);
		       }
       } /* end switch */
    }    /* end while */
    *s = '\0';
    return(count);
 }     /* end wgets */
/* ------------------------------------------------------------------------*/
void clear_window(WINDOW *win)
{
    ClearArea(win->left_col , win->upper_row ,
	      win->right_col , win->bottom_row ,
	      win->fill_att);
    wcursor(win , 0 , 0);
 }

/* ------------------------------------------------------------------------*/

 void title_window(WINDOW *win , char *title ,int att)
 {
 int len = strlen(title);
 int pos;
 char lchar ,rchar;

    pos = (win->right_col - win->left_col) /2 - (len / 2 ) -1 + win->left_col;

    if(win->box_type == 1) {
      lchar = 180;
      rchar = 195;
    }
    else
       if(win->box_type == 2) {
	  lchar = 185;
	  rchar = 204;
       } else
	  lchar = rchar =  221;

    pos_cursor(pos , win->upper_row -1);
    Fast_putc(win->box_att,lchar);
    Fast_puts(att , title);
    Fast_putc(win->box_att,rchar);
   }

/* ------------------------------------------------------------------------*/

int wscroll(WINDOW *win , int dir , int lines)
{
  scroll(dir , lines , win->fill_att ,win->left_col ,
	 win->upper_row , win->right_col , win->bottom_row);
  return(lines);
 }
/* ------------------------------------------------------------------------*/
int  wdel_line(WINDOW *win ,int y)
{
    scroll(UP , 1,win->fill_att , win->left_col ,
	   win->upper_row + y , win->right_col ,win->bottom_row );
     return(0);
}
/* ------------------------------------------------------------------------*/
int wins_line(WINDOW *win ,int y)
{

    scroll(DOWN , 1,win->fill_att , win->left_col ,
	   win->upper_row + y , win->right_col ,win->bottom_row );
     return(0);
}

/* ------------------------------------------------------------------------*/
void wcursor(WINDOW *win ,int x , int y)
{
 pos_cursor(win->left_col+x, win->upper_row+y);
}
/* ------------------------------------------------------------------------*/
void wget_cursor(WINDOW *win , int *x , int *y)
{
    get_cursor(x ,y);
    *x = *x - win->left_col;
    *y = *y - win->upper_row;
}
/* ------------------------------------------------------------------------*/
void close_window(WINDOW *win)
{

    rest_scr(win->left_col-1 ,win->upper_row -1,win->right_col+1,
	    win->bottom_row +1 ,win->save_buf);
    free(win->save_buf);
    free(win->title);
    free(win);
}
/* ------------------------------------------------------------------------*/
int move_window(WINDOW *win , int dir , int count)
{
char TempBuf[2000];
int x , y;
    wget_cursor(win , &x , &y);
    switch (dir) {
	case MOVE_UP :  if(win->upper_row - count < 1)
			   return(-1);
			    /* save window contents */
			     if(NULL == (save_scr(win->left_col-1 ,
					       win->upper_row-1 ,
					       win->right_col +1 ,
					       win->bottom_row +1 ,
					       TempBuf)))
				    return(-1);
			   /* restore screen under window */
			  rest_scr(win->left_col - 1 ,
				  win->upper_row -1 ,
				  win->right_col +1 ,
				  win->bottom_row +1 ,
				  win->save_buf);
			  /* save screen in window's new position */
			  save_scr(win->left_col - 1 ,
				  win->upper_row -1 - count ,
				  win->right_col +1 ,
				  win->bottom_row +1 - count,
				  win->save_buf);
			  /* restore window contents in new position */
			  rest_scr(win->left_col - 1 ,
				  win->upper_row -1 - count ,
				  win->right_col +1 ,
				  win->bottom_row +1 - count,
				  TempBuf);
			  win->upper_row-= count;
			  win->bottom_row = win->bottom_row - count;
			  break;

	case MOVE_DOWN : if(win->bottom_row > 23)
			    return(-1);
			    if(NULL == (save_scr(win->left_col-1 ,
					       win->upper_row-1 ,
					       win->right_col +1 ,
					       win->bottom_row +1 ,
					       TempBuf)))
				    return(-1);
			  rest_scr(win->left_col - 1 ,
				  win->upper_row -1 ,
				  win->right_col +1 ,
				  win->bottom_row +1 ,
				  win->save_buf);

			  save_scr(win->left_col - 1 ,
				  win->upper_row -1 + count ,
				  win->right_col +1 ,
				  win->bottom_row +1 + count,
				  win->save_buf);

			  rest_scr(win->left_col - 1 ,
				  win->upper_row -1 + count ,
				  win->right_col +1 ,
				  win->bottom_row +1 + count,
				  TempBuf);
			  win->upper_row +=count;
			  win->bottom_row += count;
			  break;

	case MOVE_LEFT :  if(win->left_col - count < 1)
			      return(-1);
			    if(NULL == (save_scr(win->left_col-1 ,
					       win->upper_row-1 ,
					       win->right_col +1 ,
					       win->bottom_row +1 ,
					       TempBuf)))
				    return(-1);
			  rest_scr(win->left_col - 1 ,
				  win->upper_row -1 ,
				  win->right_col +1 ,
				  win->bottom_row +1 ,
				  win->save_buf);

			  save_scr(win->left_col - 1 - count,
				  win->upper_row -1  ,
				  win->right_col +1 - count,
				  win->bottom_row +1 ,
				  win->save_buf);

			  rest_scr(win->left_col - 1 - count,
				  win->upper_row -1  ,
				  win->right_col +1 - count,
				  win->bottom_row +1 ,
				  TempBuf);
			  
			  win->left_col -=count;
			  win->right_col -= count;
			  break;



	case MOVE_RIGHT : if(win->right_col + count > 78)
			      return(-1);
			    if(NULL == (save_scr(win->left_col-1 ,
					       win->upper_row-1 ,
					       win->right_col +1 ,
					       win->bottom_row +1 ,
					       TempBuf)))
				    return(-1);
			  rest_scr(win->left_col - 1 ,
				  win->upper_row -1 ,
				  win->right_col +1 ,
				  win->bottom_row +1 ,
				  win->save_buf);

			  save_scr(win->left_col - 1 + count,
				  win->upper_row -1  ,
				  win->right_col +1 + count,
				  win->bottom_row +1 ,
				  win->save_buf);

			  rest_scr(win->left_col - 1 + count,
				  win->upper_row -1  ,
				  win->right_col +1 + count,
				  win->bottom_row +1 ,
				  TempBuf);

			  win->left_col +=count;
			  win->right_col += count;
			  break;

	default :         return(-1);

    }  /* end switch  */
    wcursor(win , x ,y);
    return(0);
}
/*************************************************************************/
