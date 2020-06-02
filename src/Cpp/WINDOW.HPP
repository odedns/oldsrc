
//_C PROGRAMMING COLUMN_
//by Al Stevens

//[LISTING ONE]



// -------------- window.h

#ifndef WINDOWS
#define WINDOWS

// ---------- screen dimensions
#define SCREENWIDTH 80
#define SCREENHEIGHT 25

// --------- atrribute values for colors
enum color {
    BLACK, BLUE, GREEN, CYAN, RED, MAGENTA, BROWN, LIGHTGRAY,
    GRAY, LIGHTBLUE, LIGHTGREEN, LIGHTCYAN, LIGHTRED,
    LIGHTMAGENTA, YELLOW, WHITE, BLINK = 128
};

// ------------ spaces per tab stop (text displays)
#define TABS 4
// ------------ color assignments for window types
#define YESNOFG  WHITE
#define YESNOBG  GREEN
#define NOTICEFG BLACK
#define NOTICEBG CYAN
#define ERRORFG  (YELLOW | BLINK)
#define ERRORBG  RED

// ------------ a video window
class Window {
    unsigned bg, fg;        // window colors
    unsigned lf,tp,rt,bt;   // window position
    unsigned *wsave;        // video memory save buffer
    unsigned *hsave;        // hide window save buffer
    unsigned row, col;      // current cursor row and column
    int tabs;               // tab stops, this window
    char **text;            // window text content
public:
    Window(unsigned left, unsigned top,
	   unsigned right, unsigned bottom,
	   color wfg, color wbg);
    ~Window(void);
    void title(char *ttl);
    Window& operator<<(char **btext);
    Window& operator<<(char *ltext);
    Window& operator<<(char ch);
    void cursor(unsigned x, unsigned y);
    void cursor(unsigned *x, unsigned *y)
	{ *y = row, *x = col; }
    void clear_window(void);
    void clreos(void);          // clear to end of screen
    void clreol(void);          // clear to end of line
    void hidewindow(void);      // hide an in-scope window
    void restorewindow(void);   // unhide a hidden window
    void page(void);            // page through the text
    void scroll(int d);         // scroll the window up, down
    void set_colors(int cfg, int cbg)   // change the colors
	{ fg = cfg, bg = cbg; }
    void set_tabs(int t)        // change the tab stops
	{ if (t > 1 && t < 8) tabs = t; }
};

// ---------- utility notice window
class Notice : Window   {
public:
    Notice(char *text);
    ~Notice(){}
};

// ---------- utility yes/no window
class YesNo : Window    {
public:
    YesNo(char *text);
    ~YesNo(){}
    int answer;
};

// ---------- utility error window
class Error : Window    {
public:
    Error(char *text);
    ~Error(){}
};

#define max(x,y) (((x) > (y)) ? (x) : (y))
#define min(x,y) (((x) > (y)) ? (y) : (x))

#endif

