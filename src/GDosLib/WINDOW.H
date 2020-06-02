#ifndef _WINDOW_H
#define _WINDOW_H
/* ------------------------------------------------------------------------*/

typedef struct {
		int upper_row;
		int left_col;
		int bottom_row;
		int right_col;
		int box_type;
		int box_att;
		int fill_att;
		char fill_char;
		char *title;
		int title_att;
		char *save_buf;
		}WINDOW;

 void title_window(WINDOW *win ,char *title , int att);
 void wcursor(WINDOW *win ,int x , int y);
 void wget_cursor(WINDOW *win , int *x , int *y);
/* ------------------------------------------------------------------------*/
int alloc_window(WINDOW **win);

int init_window(WINDOW *win , int x , int y , int x1 , int y1,
                    int box_type , int fill_char ,char *title);
/* ------------------------------------------------------------------------*/

void set_window_colors(WINDOW *win ,int box_att , int fill_att,
                       int title_att );
/* ------------------------------------------------------------------------*/
int display_window(WINDOW *win);

/***************************************************************************\
* window io functions                                                       *
\***************************************************************************/

int wputc(WINDOW *win , char c);
int wputs(WINDOW *win , char *s);
/* ------------------------------------------------------------------------*/
int wprintf(WINDOW *win ,char *format, ...);
/* ------------------------------------------------------------------------*/
int wgets(WINDOW *win , char *s , int nelem);
/* ------------------------------------------------------------------------*/
void clear_window(WINDOW *win);
 void title_window(WINDOW *win , char *title ,int att);
/* ------------------------------------------------------------------------*/

int wscroll(WINDOW *win , int dir , int lines);
/* ------------------------------------------------------------------------*/
int  wdel_line(WINDOW *win ,int y);
/* ------------------------------------------------------------------------*/
int wins_line(WINDOW *win ,int y);
/* ------------------------------------------------------------------------*/
void wcursor(WINDOW *win ,int x , int y);
void wget_cursor(WINDOW *win , int *x , int *y);
/* ------------------------------------------------------------------------*/
void close_window(WINDOW *win);
/* ------------------------------------------------------------------------*/
int move_window(WINDOW *win , int dir , int count);
/*************************************************************************/
#endif
