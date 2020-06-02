
//[LISTING TWO]

// -------------- window.c

// A C++ window library

#include <stddef.h>
#include <string.h>
#include <ctype.h>
#include "window.hpp"
#include "console.h"

#define HEIGHT (bt - tp + 1)
#define WIDTH  (rt - lf + 1)

// ------- constructor for a Window
Window::Window(unsigned left, unsigned top,
              unsigned right, unsigned bottom,
              color wfg, color wbg)
{
    savecursor();
    initconsole();
    hidecursor();
    // ----- adjust for windows beyond the screen dimensions
    if (right > SCREENWIDTH-1)  {
	left -= right-(SCREENWIDTH-1);
        right = SCREENWIDTH-1;
    }
    if (bottom > SCREENHEIGHT-1)    {
        top -= bottom-(SCREENHEIGHT-1);
        bottom = SCREENHEIGHT-1;
    }
    // ------- initialize window dimensions
    lf = left;
    tp = top;
    rt = right;
    bt = bottom;
    // ------- initialize window colors
    fg = wfg;
    bg = wbg;
    // ------- initialize window cursor and tab stops
    row = col = 0;
    tabs = TABS;
    // ---------- save the video rectangle under the new window
    wsave = new unsigned[HEIGHT * WIDTH];
    hsave = NULL;
    savevideo(wsave, tp, lf, bt, rt);
    // --------- draw the window frame
    box(tp, lf, bt, rt, fg, bg);
    // -------- clear the window text area
    clear_window();
    unhidecursor();
}

// ------- destructor for a Window
Window::~Window(void)
{
    // ----- restore the video RAM covered by the window
    restorevideo(wsave, tp, lf, bt, rt);
    delete wsave;
    if (hsave != NULL)
        delete hsave;
    restorecursor();
}

// ------- hide a window without destroying it
void Window::hidewindow(void)
{
    if (hsave == NULL)  {
        hsave = new unsigned[HEIGHT * WIDTH];
	savevideo(hsave, tp, lf, bt, rt);
	restorevideo(wsave, tp, lf, bt, rt);
    }
}

// --------- restore a hidden window
void Window::restorewindow(void)
{
    if (hsave != NULL)  {
	savevideo(wsave, tp, lf, bt, rt);
	restorevideo(hsave, tp, lf, bt, rt);
        delete hsave;
        hsave = NULL;
	colors(fg,bg);
    }
}

// -------- add a title to a window
void Window::title(char *ttl)
{
    setcursor(lf + (WIDTH - strlen(ttl) - 1) / 2, tp);
    colors(fg, bg);
    window_printf(" %s ", ttl);
    cursor(col, row);
}

// ------- write text body to a window
Window& Window::operator<<(char **btext)
{
    cursor(0, 0);
    text = btext;
    if (*btext != NULL)
        *this << *btext++;
    while (*btext != NULL && row < HEIGHT-3)
        *this << '\n' << *btext++;
}

// -------- write a line of text to a window
Window& Window::operator<<(char *ltext)
{
    while (*ltext && col < WIDTH - 2 && row < HEIGHT - 2)
        *this << *ltext++;
    return *this;
}

// -------- write a character to a window
Window& Window::operator<<(char ch)
{
    cursor(col, row);
    switch (ch) {
	case '\n':
            clreol();
            if (row == HEIGHT-3)
                scroll(1);
            else
                row++;
        case '\r':
            col = 0;
            break;
	case '\b':
            if (col)
                --col;
            break;
        case '\t':
            do
                *this << ' ';
            while (col % tabs);
            break;
        default:
            if (col == WIDTH - 2)
		*this << '\n';
	    colors(fg,bg);
	    window_putc(ch);
            col++;
            return *this;
    }
    cursor(col, row);
    return *this;
}

// ----- position the window cursor
void Window::cursor(unsigned x, unsigned y)
{
    if (x < WIDTH-2 && y < HEIGHT-2)    {
	setcursor(lf+1+x, tp+1+y);
	row = y;
        col = x;
    }
}

// ------ clear a window to all blamks
void Window::clear_window(void)
{
    cursor(0,0);
    clreos();
}

// --- clear from current cursor position to end of window
void Window::clreos(void)
{
    unsigned rw = row, cl = col;
    clreol();
    col = 0;
    while (++row < HEIGHT-2)
        clreol();
    row = rw;
    col = cl;
}

// --- clear from current cursor position to end of line
void Window::clreol(void)
{
    unsigned cl = col;
    colors(fg,bg);
    while (col < WIDTH-2)
        *this << ' ';
    col = cl;
}

// ----- page and scroll through the text file
void Window::page(void)
{
    int c = 0, lines = 0;
    char **tx = text;

    hidecursor();
    // ------ count the lines of text
    while (*(tx + lines) != NULL)
        lines++;
    while (c != ESC)    {
	c = getkey();
        char **htext = text;
        switch (c)  {
            case UP:
                if (tx != text) {
                    --tx;
                    scroll(-1);
		    unsigned x, y;
                    cursor(&x, &y);
		    cursor(0, 0);
                    *this << *tx;
                    cursor(x, y);
                }
                continue;
            case DN:
                if (tx+HEIGHT-3 < text+lines-1) {
                    tx++;
                    scroll(1);
                    unsigned x, y;
                    cursor(&x, &y);
		    cursor(0, HEIGHT-3);
                    *this << *(tx + HEIGHT - 3);
                    cursor(x, y);
                }
                continue;
            case PGUP:
                tx -= HEIGHT-2;
                if (tx < text)
                    tx = text;
                break;
            case PGDN:
                tx += HEIGHT-2;
                if (tx+HEIGHT-3 < text+lines-1)
		    break;
            case END:
                tx = text+lines-(HEIGHT-2);
                if (tx > text)
                    break;
            case HOME:
                tx = text;
		break;
            default:
                continue;
        }
        *this << tx;
        text = htext;
	clreos();
    }
    unhidecursor();
}

// --------- scroll a window
void Window::scroll(int d)
{
    videoscroll(d, tp+1, lf+1, bt-1, rt-1, fg, bg);
}

// ------ utility notice window
Notice::Notice(char *text)
    : ((SCREENWIDTH-(strlen(text)+2)) / 2, 11,
	((SCREENWIDTH-(strlen(text)+2)) / 2) + strlen(text)+2,
        14, NOTICEFG, NOTICEBG)
{
    *this << text << "\n Any key ...";
    hidecursor();
    getkey();
    unhidecursor();
    hidewindow();
}

// ------ utility error window
Error::Error(char *text)
    : ( (SCREENWIDTH-(strlen(text)+2)) / 2, 11,
        ((SCREENWIDTH-(strlen(text)+2)) / 2) + strlen(text)+2,
        14, ERRORFG, ERRORBG)
{
    *this << text << "\n Any key ...";
    hidecursor();
    getkey();
    unhidecursor();
    hidewindow();
}

// ------ utility yes/no window
YesNo::YesNo(char *text)
    : ( (SCREENWIDTH-(strlen(text)+10)) / 2, 11,
        ((SCREENWIDTH-(strlen(text)+10)) / 2) + strlen(text)+10,
        13, YESNOFG, YESNOBG)
{
    *this << text << "? (Y/N) ";
    int c = 0;
    hidecursor();
    while (tolower(c) != 'y' && tolower(c) != 'n')
        c = getkey();
    unhidecursor();
    hidewindow();
    answer = tolower(c) == 'y';
}

