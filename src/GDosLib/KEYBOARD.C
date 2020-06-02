    /* Enhanced Keyboard Functions  !!!  */


#include <stdio.h>
#include <dos.h>
#include "keyboard.h"

CLOCK_t clock_p;

/* Code *\
\* ---- */


int keypressed()
{
char far *t = (char far *) 1050;
    return((*t != *(t+2)) ? 1 : 0);
}


int get_shift_stat()    /* get enhanced keyboard shift flags */
   {

    _AH = 0x12;
    geninterrupt(0x16);

    return(_AL);
    }

 int unget_key(int key)  /* push value onto keyboard stack ! */
     {

     _AH = 5;
     _CX = key;

     geninterrupt(0x16);
     return((_AL != 1) ? 1 : 0);
     }

unsigned int _getkey()   /* get a key from enhanced keyboard  */
     {
     union key_u {
		  unsigned code;
		  char ch[2];
		 } key;

     key.code = Bioskey(0);

     return(key.ch[0] ? key.ch[0]  : key.ch[1]);

     }

  int get_keyb_type()   /* checks for enhanced keyboard */
      {

       unget_key(0xaa55);
       unget_key(0x55aa);

       if( ((_getkey()) == 0xaa55) && ((_getkey()) == 0x55aa))
	  return(ENHANCED);

	  return(0);
	  }



void show_time()
{
long far *Time;
int hour , min , sec , pos  , i = 0;
char TimeStr[10];
extern CLOCK_t clock_p;

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
 extern CLOCK_t clock_p;

    while(!Kbhit())
     if(clock_p.clock_active)
	     show_time();
    return(_getkey());
 }

void clock(int x , int y , unsigned char att)
{
extern CLOCK_t clock_p;

    clock_p.clock_active = 1;
    clock_p.xpos = x;
    clock_p.ypos = y;
    clock_p.att = att;
 }

unsigned Bioskey(int mode)
{
union REGS reg;

    switch(mode) {

	case 1 :  reg.h.ah = 0x01;
		  int86(0x16 , &reg , &reg);
		  return(reg.x.flags & 0x80 ? 1 : 0);
	case 11:  reg.h.ah = 0x11;
		  int86(0x16 , &reg , &reg);
		  return(reg.x.flags & 0x80 ? 1 : 0);

	default:  reg.h.ah = mode;
		  return(int86(0x16 , &reg , &reg));

    }
}  /* _bioskey */


unsigned Biosprint(int cmd , char c , int port)
{
union REGS reg;

    switch(cmd) {
       case 0 :  reg.h.ah = 0;
		 reg.x.dx = port;
		 reg.h.al = c;
		 return(int86(0x17 , &reg , &reg));

       case 1 :  reg.h.ah = 1;
		 reg.x.dx = port;
		 return(int86(0x17 , &reg , &reg));
       case 2 :  reg.h.ah = 2;
		 reg.x.dx = port;
		 return(int86(0x17 , &reg , &reg));
      }
      return(0);
} /* end Biosprint */


