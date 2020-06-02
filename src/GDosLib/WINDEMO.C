
#include <stdio.h>

#include "usr\clib.h"

#define MOVE_UP       1
#define MOVE_DOWN     2
#define MOVE_LEFT     3
#define MOVE_RIGHT    4

/*************************************************************************/

main()
{
WINDOW *win , *mywin;
int x ,y , c;
char s[256];
    cls();
    Fbox(0,79,0,24,7,3,7,177);

    alloc_window(&win);
    init_window(win , 10 , 2 , 60 , 12 , 2 , ' ',NULL);
    set_window_colors(win , CYAN , YELLOW_ON_BLUE ,WHITE_ON_BLUE);
    display_window(win);
    title_window(win , " Oded Nissan's Window ! ",BLUE_ON_CYAN);
    wcursor(win ,0,0);
    getkey();
    wscroll(win ,DOWN,1);
     wcursor(win ,0,0);
     getkey();
     wcursor(win ,5 ,5);
     wdel_line(win ,5);
     alloc_window(&mywin);
    init_window(mywin , 10 , 15 , 60 , 22 , 2 , ' ',NULL);
    set_window_colors(mywin , CYAN , YELLOW_ON_BLUE ,WHITE_ON_BLUE);
    display_window(mywin);
    title_window(mywin , " Second Window ! ",BLUE_ON_CYAN);
    wcursor(mywin ,1 ,1);
    wputs(mywin ,"This is the second window \n");
    wgets(mywin , s , 200);
    wputs(mywin ,"press any key to clear first window !!\n\n");
    getkey();
    clear_window(win);
    getkey();
    close_window(win);
    wcursor(mywin , 0 , 4);
    wget_cursor(mywin , &x , &y);
    wprintf(mywin, "cursor at x = %d y = %d \n" , x ,y);
    wcursor(mywin , 47 ,7);
    for(x = 0; x < 200; ++x) {
       wprintf(mywin , "this is line %d\n !!",x);
    }
    wcursor(mywin , 0 , 5);
    getkey();
    wins_line(mywin , 5);
    wcursor(mywin , 0 , 5);
    getkey();
    wdel_line(mywin , 5);

    while((c =getkey()) != ENTER) {
	switch(c) {

	case LEFT_ARROW :  move_window(mywin , MOVE_LEFT ,1);
			   break;
	case RIGHT_ARROW :  move_window(mywin , MOVE_RIGHT,1);
			   break;
	case UP_ARROW :  move_window(mywin , MOVE_UP ,1);
			   break;
	case DOWN_ARROW :  move_window(mywin , MOVE_DOWN ,1);
			   break;
	}
    }
}
