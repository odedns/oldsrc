/*---------------------------------------------------*\
 * File : mouse.h                                    *
 * Mouse functions library header.                   *
 *                                                   *
 *                                                   *
 *                                                   *
\*---------------------------------------------------*/

#ifndef _MOUSE_H
#define _MOUSE_H
/*------ check if mouse installed --------*/
int mouse_installed(int *buttons);

/*------- show mouse cursor -----------*/
int show_mouse();

/*------- hide mouse cursor -----------*/
int hide_mouse();

/*------ get mouse position ----------*/
int get_mouse(int *x , int *y);

/*--------- set mouse position --------*/
int set_mouse(int x , int y);

int get_button_press(int *x ,int *y , int *button , int *count);

int get_button_release(int *x ,int *y , int *button,int *count);

/*---------- set mouse cursor shape ------------*/
int mouse_cursor(int start , int stop);

/*---------- set mouse speed ------------------*/
int mouse_speed(int horiz , int vert);

int set_mouse_page();

int get_mouse_page();

/*------- set mouse x range --------*/
int set_x_range(int min , int max);

/*------- set mouse y range --------*/
int set_y_range(int min , int max);
#endif

