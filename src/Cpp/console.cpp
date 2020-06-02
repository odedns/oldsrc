
//[LISTING FOUR]

/* ----------- console.c --------- */

/* PC-specific console functions */

#include <dos.h>
#include <conio.h>
#include "console.h"

/* ------- video BIOS (0x10) functions --------- */
#define VIDEO         0x10
#define SETCURSORTYPE 1
#define SETCURSOR     2
#define READCURSOR    3
#define HIDECURSOR    0x20

#define SAVEDEPTH     20  /* depth to which cursors are saved */

static int cursorpos[SAVEDEPTH];
static int cursorshape[SAVEDEPTH];
static int sd;

union REGS rg;

/* ---- Low-level get cursor shape and position ---- */
static void getcursor(void)
{
    rg.h.ah = READCURSOR;
    rg.h.bh = 0;
    int86(VIDEO,&rg,&rg);
}

/* ---- Save the current cursor configuration ---- */
void savecursor(void)
{
    getcursor();
    if (sd < SAVEDEPTH) {
        cursorshape[sd] = rg.x.cx;
        cursorpos[sd++] = rg.x.dx;
    }
}

/* ---- Restore the saved cursor configuration ---- */
void restorecursor(void)
{
    if (sd) {
        rg.h.ah = SETCURSOR;
        rg.h.bh = 0;
        rg.x.dx = cursorpos[--sd];
        int86(VIDEO,&rg,&rg);
        rg.h.ah = SETCURSORTYPE;
        rg.x.cx = cursorshape[sd];
        int86(VIDEO,&rg,&rg);
    }
}

/* ---- Hide the cursor ---- */
void hidecursor(void)
{
    getcursor();
    rg.h.ch |= HIDECURSOR;
    rg.h.ah = SETCURSORTYPE;
    int86(VIDEO,&rg,&rg);
}

/* ---- Unhide the cursor ---- */
void unhidecursor(void)
{
    getcursor();
    rg.h.ch &= ~HIDECURSOR;
    rg.h.ah = SETCURSORTYPE;
    int86(VIDEO,&rg,&rg);
}

/* ---- Read a keystroke ---- */
int getkey(void)
{
    rg.h.ah = 0;
    int86(0x16,&rg,&rg);
    if (rg.h.al == 0)
	return (rg.h.ah | 0x80) & 255;
    return rg.h.al & 255;
}

