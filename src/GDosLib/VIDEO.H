
/*---------------------------------------------------------------------------*\
 *  Video IO functions.                        Date : 26/11/1993.            *
 *  Header file.                                                             *
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

#ifndef _VIDEO_H
#define _VIDEO_H

 #define __VGA  0xb800
/*  #define __MONO 0xb000 */


#define VIDPAGE_SIZ         4096

#if defined(__VGA)
#define Video  0xb800
#define    BOLD_CURSOR       0x0407
#define    NORMAL_CURSOR     0x0607
#define    OFF_CURSOR        0x0100

#else

#define Video  0xb000
#define    BOLD_CURSOR       0x0A0C
#define    NORMAL_CURSOR     0x090C
#define    OFF_CURSOR        0x0100

#endif


#define UP   1
#define DOWN 2
#define YES  1
#define NO   0


/* Macros *\
\* ------ */

#define ClearLine( y , a)            (fill_area(0 , y , 79 ,y ,a , ' '))
#define ClearArea(x,y, x1 ,y1 ,att)  (fill_area(x , y , x1 ,y1 ,att , ' '))
#define HorizLine( y , att ,c)       (fill_area(0 , y , 79 , y, att ,c))
#define VertLine(x , att ,c)         (fill_area(x , 0 , x+1 , 79 , att ,c))
#define Fbox(x ,x1 ,y ,y1 ,att ,type , fillatt , fillchar)  \
		     Box(x , x1 , y , y1 ,att ,type); \
		     fill_area(x+1 , y+1 , x1-1  , y1 -1 , fillatt ,fillchar);


/* Function Declarations *\
\* --------------------- */
/*****************************************************************************\
* Video IO Functions   !                                                     *
*                                                                            *
\*****************************************************************************/
void set_cursor(unsigned int shape);
void pos_cursor(int x , int y);
void get_cursor(int *x , int *y);
int get_active_page(unsigned int page);
int get_key();
void cls();
void Box(int left, int right , int top , int bottom , int att , int type);
int eprintf(int att , char *format , ...);
int Fast_printf(int att , char *format ,...);
int Bios_putc(char c, int att);
int Bios_Dup_putc(char c , int att , int count);
int Bios_puts(char *s , int att);
int Fast_puts(int att , char *s);
int Fast_putc(int att , char c);
int aputs(char *s);
unsigned Bios_getche();
void scroll(int dir , int lines , int att , int lcol , int trow , int rcol , int brow);
int change_att(int x , int y , int len , int att);
char *save_scr(int x , int y , int x1 , int y1 , char *buf);
void rest_scr(int x , int y , int x1 , int y1 , char *buf);
void  fill_area( int x , int y , int x1 , int y1 , int att , char c);
int move_block(int x , int  y , int x1 ,int y1 , int new_x , int new_y,int att);
int del_line(int y ,int att);
void write_direct(char *s,int pos , int att);

#endif

