
/*---------------------------------------------------*\
 * File : mouse.c                                    *
 * Mouse functions library.                          *
 *                                                   *
 *                                                   *
 *                                                   *
\*---------------------------------------------------*/

#include <dos.h>

/*------ check if mouse installed --------*/
int mouse_installed(int *buttons)
{
union REGS r;

    r.x.ax = 0;
    int86(0x33 , &r , &r);
    *buttons = r.x.bx;

    return( (int)r.x.ax == -1 ? 1 :0);
}

/*------- show mouse cursor -----------*/
int show_mouse()
{
union REGS r;

    r.x.ax = 1;
    return(int86(0x33 , &r , &r));
}

/*------- hide mouse cursor -----------*/
int hide_mouse()
{
union REGS r;

    r.x.ax = 2;
    return(int86(0x33 , &r , &r));
}

/*------ get mouse position ----------*/
int get_mouse(int *x , int *y)
{
union REGS r;

    r.x.ax = 3;
    int86(0x33 , &r , &r);
    *x = r.x.cx;
    *y = r.x.dx;
    return(r.x.bx);
}

/*--------- set mouse position --------*/
int set_mouse(int x , int y)
{
union REGS r;

    r.x.ax = 4;
    r.x.cx = x;
    r.x.dx = y;
    return(int86(0x33 , &r , &r));
}


int get_button_press(int *x ,int *y , int *button , int *count)
{
union REGS r;

    r.x.bx = *button;
    r.x.ax = 5;
    int86(0x33 , &r, &r);
    *x = r.x.cx;
    *y = r.x.dx;
    *count =  r.x.bx;
    *button = r.x.ax;

    return(*button);
}

int get_button_release(int *x ,int *y , int *button,int *count)
{
union REGS r;

    r.x.bx = *button;
    r.x.ax = 6;
    int86(0x33 , &r, &r);
    *x = r.x.cx;
    *y = r.x.dx;
    *count = r.x.bx;
    *button = r.x.ax;
    return(*button);
}

/*---------- set mouse cursor shape ------------*/
int mouse_cursor(int start , int stop)
{
union REGS r;

    r.h.ah = 0x0a;
    r.x.bx = 0x01;
    r.x.cx = start;
    r.x.dx = stop;
    return(int86(0x33 , &r , &r));
}

/*---------- set mouse speed ------------------*/
int mouse_speed(int horiz , int vert)
{
union REGS r;
    r.h.ah = 0x0f;
    r.x.cx = horiz;
    r.x.dx = vert;
    return(int86(0x33 , &r , &r));
}

int set_mouse_page()
{
union REGS r;
    r.h.ah = 0x1d;
    return(int86(0x33 , &r , &r));
}

int get_mouse_page()
{
union REGS r;
    r.h.ah = 0x1e;
    int86(0x33 , &r , &r);
    return(r.x.bx);
}


/*------- set mouse x range --------*/
int set_x_range(int min , int max)
{
union REGS r;
    r.h.ah = 0x07;
    r.x.cx = min;
    r.x.dx = max;
    return(int86(0x33 , &r , &r));
}

/*------- set mouse y range --------*/
int set_y_range(int min , int max)
{
union REGS r;
    r.h.ah = 0x08;
    r.x.cx = min;
    r.x.dx = max;
    return(int86(0x33 , &r , &r));
}


