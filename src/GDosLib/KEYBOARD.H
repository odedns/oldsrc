/*---------------------------------------------------------------------------*\
*  keyboard.h - keyboard definition file.         Date - 29/10/93            *
*  extended keyboard codes used.                                             *
*                                                                            *
\*---------------------------------------------------------------------------*/
#ifndef _KEYBOARD_H
#define _KEYBOARD_H

#define ENTER         13
#define SPACE         32
#define INS           82
#define DEL           83
#define HOME          71
#define END           79
#define TAB            9
#define PAGE_UP       73
#define PAGE_DOWN     81
#define UP_ARROW      72
#define DOWN_ARROW    80
#define LEFT_ARROW    75
#define RIGHT_ARROW   77
#define BACKSPACE      8
#define ESCAPE        27
#define F1            59
#define F2            60
#define F3            61
#define F4            62
#define F5            63
#define F6            64
#define F7            65
#define F8            66
#define F9            67
#define F10           68
#define F11          133
#define F12          134

/* Type Definitions *\
\* ---------------- */

typedef struct {
		int xpos;
		int ypos;
		unsigned char att;
                unsigned char clock_active;
	       } CLOCK_t;



/* Macro Definitions *\
\* ----------------- */

/*  shift flags definitions   */

#define  INSERT       0x80
#define  CAPS         0x40
#define  NUM_LOCK     0x20
#define  SCROLL_LOCK  0x10
#define  ALT          0x08
#define  CTRL         0x04
#define  LEFT_SHIFT   0x02
#define  RIGHT_SHIFT  0x01



/* enhanced keyboard definition   */

#define Key()                  (Bioskey(0))
#define Kbhit()                (Bioskey(1))
#define Shift_Stat()           (Bioskey(2))
  
#define ENHANCED      1

#define Caps_On()              (CAPS & Shift_Stat())
#define Num_Lock_On()          (NUM_LOCK & Shift_Stat())
#define Alt_On()               (ALT & Shift_Stat())
#define Ctrl_On()              (CTRL & Shift_Stat())
#define Insert_On()            (INSERT & Shift_Stat())
#define Scroll_Lock_On()       (SCROLL_LOCK & Shift_Stat())
#define Shift_On()             (LEFT_SHIFT & Shift_Stat() || RIGHT_SHIFT & Shift_Stat())


/* Function Declarations *\
\* --------------------- */

int keypressed();
void show_time();
int get_key();
int get_shift_stat();
void clock();
unsigned Bioskey(int mode);
unsigned _getkey();
unsigned Biosprint(int cmd , char c , int port);
#endif
