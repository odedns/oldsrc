#include <dos.h>
#include <bios.h>

extern unsigned _heaplen = 1;
extern unsigned _stklen  = 128;

#define INTR 0x1C    /* The clock tick interrupt */

void far interrupt TimerHandler(void);
void interrupt ( *oldhandler)(void);
long bios_time, old_bios_time;
int  hours,     minutes;
char Timestr[20] = "" ;

char far *Equipment = (char far *)(0x00400049l);
#define SCREEN_ADDR (*(char far *)Equipment == 7 ? 0xB0000000l : 0xB8000000l)

int far *screen;

void far interrupt TimerHandler(void)
{ char i;

  bios_time = biostime(0, 0L) * 10 / 182;         // Seconds
    if ( bios_time != old_bios_time )
    { hours   = (int)(bios_time / 3600);
      minutes = (int)(bios_time - 3600 * hours) / 60;
      Timestr[0] = '0' + hours / 10;
      Timestr[1] = '0' + hours % 10;
      Timestr[2] = ':';
      Timestr[3] = '0' + minutes / 10;
      Timestr[4] = '0' + minutes % 10;
      Timestr[5] = ':';
      Timestr[6] = '0' + (bios_time % 60) / 10;
      Timestr[7] = '0' +  bios_time       % 10;
      Timestr[8] =  0;
      for (i=0; Timestr[i]; i++)
        { screen[71+i] = ( 0x1b00 ) | ( Timestr[i] );
        }

      old_bios_time = bios_time;
    }
    /* call the old routine */
  (void far)oldhandler();
}



int main(void)
{
  screen = (int far *)SCREEN_ADDR;

  old_bios_time = 0;
  bios_time = biostime(0, 0L);
  oldhandler = getvect(INTR);   // Get Old Handler
  setvect(INTR, TimerHandler);  // Install New Handler

  keep(0, (_SS + (_SP/16) - _psp) );
  setvect(INTR, oldhandler);
  return 0;
}
