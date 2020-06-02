#include <stdio.h>
#include <dos.h>
#include <bios.h>

typedef struct {
		int xpos;
		int ypos;
		unsigned char att;
	       } CLOCK_t;

CLOCK_t clock_p;
unsigned char CLOCK_ACTIVE = 0;

void show_time()
{
long far *Time;
int hour , min , sec , pos  , i = 0;
char TimeStr[10];

    pos = clock_p.xpos *2 + clock_p.ypos *160;
    Time = MK_FP(0x40 , 0x6c);

    hour =(int) (*Time / 65520L);
    min = (int) (*Time % 65520L / 1092);
    sec = (int) (*Time % 65520L  % 1092);
    sec = (int)(sec / 18.2) % 60;

    sprintf(TimeStr , "%02d:%02d:%02d" , hour , min , sec);

    while(TimeStr[i]) {
	  pokeb(0xb800 , pos++ ,TimeStr[i++]);
	  pokeb(0xb800 , pos++ , clock_p.att);

    }

}

int get_key()
{
extern unsigned char CLOCK_ACTIVE;

    while(0 == bioskey(1))
	 if(CLOCK_ACTIVE)
	     show_time();
    return(0x00ff & bioskey(0));
 }

void clock(int x , int y , unsigned char att)
{
    CLOCK_ACTIVE = 1;
    clock_p.xpos = x;
    clock_p.ypos = y;
    clock_p.att = att;
 }
 main()
 {
 int key;

    clock(70 ,0 , 0x70);

    while((key = get_key()) != 27) {
	printf("key = %d\n" , key);
    }
 }


