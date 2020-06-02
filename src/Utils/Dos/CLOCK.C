/*------------------------------------------------------------------------*/
/*   Module       :  clock.exe                                            */
/*   File         :  clock.c                                              */
/*   Date         :  08/04/1994                                           */
/*   Description  :                                                       */
/*                   This TSR program will display a clock on the right   */
/*                   side of the screen it uses the dos idle interrupt    */
/*                   (INT 0x28).                                          */
/*                   This means that the clock will be displayed only     */
/*                   when dos IO is performed.                            */
/*   Author       :  Oded Nissan                                          */
/*                                                                        */
/*   History      :                                                       */
/*------------------------------------------------------------------------*/
/*   Date         |   Description                                         */
/*------------------------------------------------------------------------*/
/*   08/04/1994   |   Initial Release.                                    */
/*                |                                                       */
/*                |                                                       */
/*------------------------------------------------------------------------*/

#include <stdio.h>
#include <dos.h>

#define VIDEO  (0xB800)


/*--------- function prototypes --------*/
void interrupt (*old_64)(void);
void interrupt new_64(void);
void interrupt (*old_28)(void);
void interrupt new_28(void);
void activate_tsr();
unsigned program_size();
void write_direct(char *s, int pos, int att);

/*---------- tsr vars -----------------*/

extern unsigned _heaplen = 1;
extern unsigned _stklen = 256;
unsigned int save_ss , save_sp , ss , sp;
int tsr_active = 0;
int counter = 0;

/*---------- program vars -------------*/

char TimeStr[10];
char far *Screen;
struct time t;

 /*-------- new int 28 ISR -----------*/
void interrupt new_28()
{

    (*old_28)();   /* call old interrupt */
    ++counter;
    if(counter > 18 && !tsr_active) {
       activate_tsr();
       counter = 0;
    }

}

/*-------- tsr activate function -------*/
void activate_tsr()
{

    /* save old stack */
    disable();
    save_ss = _SS;
    save_sp = _SP;
    _SS = ss;
    _SP = sp;
    enable();

    /* if tsr isn't active - activate it */
       tsr_active = 1;

       /* get the time from dos */
       gettime(&t);
       sprintf(TimeStr , "%02d:%02d:%02d" ,
	       t.ti_hour , t.ti_min , t.ti_sec);

       write_direct(TimeStr,72 ,112 );


    /*  restore old stack */

    disable();
    _SP = save_sp;
    _SS = save_ss;
    enable();

    tsr_active = 0;

 }    /*--------- end activate_tsr  ---------*/


/*------- compute program size --------------*/

 unsigned program_size()
 {
    return(*((unsigned far *) (MK_FP(_psp -1,3))) );
 }

/*------- write string to video ram ---------*/

void write_direct(char *s,int pos , int att)
{
int i;

    for(i = 2 * pos; *s != '\0'; i++) {
       Screen[i++] = *s++;
       Screen[i] = att;
    }
}

void interrupt new_64()
{
}

/****   main program ****/

int main(void)
{
char Msg[] = " *** clock.exe - displays the system clock version 1.0 ***\n \
	  By Oded Nissan  08-04-1994\n";

    puts(Msg);
    /* check if TSR already loaded */
    old_64 = getvect(0x64);
    if(!old_64)
	/* set TSR loaded indication */
	setvect(0x64,new_64);
    else {
	puts("clock is already resident!");
	return(1);
    }
    /* get pointer to video ram */
    Screen = MK_FP(VIDEO , 0);

    /* get program stack */
    ss = _SS;
    sp = _SP;

    /* get old ISR's */
    old_28 = getvect(0x28);

    /* set new ISR's  */
    setvect(0x28 , new_28);


    /* terminate stay resident */
	puts("clock is resident!");
      keep(0, program_size());

return(0);

}  /*** end main    ***/







