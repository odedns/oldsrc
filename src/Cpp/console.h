
//[LISTING THREE]

/* ----------- console.h -------- */

#ifndef CONSOLE
#define CONSOLE

//#include <disp.h>

// -------- cursor and keyboard functions (via BIOS)
void hidecursor(void);
void unhidecursor(void);
void savecursor(void);
void restorecursor(void);
int getkey(void);

// -------- key values returned by getkey()
#define BELL      7
#define ESC      27
#define UP      200
#define BS      203
#define FWD     205
#define DN      208
#define HOME    199
#define END     207
#define PGUP    201
#define PGDN    209

#define attr(fg,bg) ((fg)+(((bg)&7)<<4))

// --------- video functions (defined as Zortech C++ equivalents)
#ifdef __ZORTECH_CPP__
#define initconsole()            disp_open()
#define closeconsole()           disp_flush()
#define savevideo(bf,t,l,b,r)    disp_peekbox(bf,t,l,b,r)
#define restorevideo(bf,t,l,b,r) disp_pokebox(bf,t,l,b,r)
#define box(t,l,b,r,fg,bg)       disp_box(1,attr(fg,bg),t,l,b,r)
#define colors(fg,bg)            disp_setattr(attr(fg,bg))
#define setcursor(x,y)           disp_move(y,x)
#define window_printf            disp_printf
#define window_putc              disp_putc
#define videoscroll(d,t,l,b,r,fg,bg) \
	disp_scroll(d,t,l,b,r,attr(fg,bg));
#endif


#endif


