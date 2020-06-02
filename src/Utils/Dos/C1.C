/*------------------------------------------------------------------------*/
/*   Module       :  c1.exe                                               */
/*   File         :  c1.c                                                 */
/*   Date         :  10/04/1994                                           */
/*   Description  :                                                       */
/*                                                                        */
/*  Clock TSR program - Displays Bios time on right of the screen.        */
/*  This clock program intercepts system interrupts 0x28 and 0x1c         */
/*  Therefore the clock is displayed not only when waiting for DOS IO     */
/*  but also when waiting for Bios IO.                                    */
/*                                                                        */
/*   Author       :  Oded Nissan                                          */
/*                                                                        */
/*   History      :                                                       */
/*------------------------------------------------------------------------*/
/*   Date         |   Description                                         */
/*------------------------------------------------------------------------*/
/*   10/04/1994   |   Initial Release.                                    */
/*                |                                                       */
/*                |                                                       */
/*------------------------------------------------------------------------*/

#include <stdio.h>
#include <dos.h>


/* The clock tick interrupt */
#define TIMER 0x1c


/* function prototypes  */

void interrupt ( *old_timer)(void);
void interrupt (*old_28)(void);
void interrupt new_timer(void);
void interrupt new_28(void);
void activate_tsr();
unsigned program_size();
void write_direct(char *s, int pos , int att);

/*------- tsr vars ----------*/

extern unsigned _heaplen = 1;
extern unsigned _stklen = 512;
unsigned int save_ss , save_sp , ss , sp;
volatile char far *dos_busy;
int tsr_active = 0;

/*-------- program vars -----*/

char TimeStr[11];
char far *Screen;
static int counter = 0;
struct time t;

/*------ new_timer ISR --------*/
void interrupt new_timer()
{

    (*old_timer)();
    ++counter;
    if(counter > 20  && !*dos_busy && !tsr_active) {
	activate_tsr();
	counter = 0;
    }
    
 }

 /*------- new int 28 ISR ---------*/

void interrupt new_28()
{

    (*old_28)();
    if( counter > 20 && !tsr_active) {
       activate_tsr();
       counter = 0;
    }

}

/*------ tsr activate function --------*/
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
       gettime(&t);
       sprintf(TimeStr , "%02d:%02d:%02d" ,
	       t.ti_hour , t.ti_min , t.ti_sec);

       write_direct(TimeStr,70 ,112 );

    /* restore old stack */
    disable();
    _SP = save_sp;
    _SS = save_ss;
    enable();

    tsr_active = 0;

 }    /* end activate_tsr  */


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

/*------------ main ------------------*/
int main(void)
{
union REGS reg;
struct SREGS sregs;


/* get dos busy flag  */

    reg.h.ah  = 0x34;
    int86x(0x21 , &reg , &reg , &sregs);
    dos_busy = MK_FP(sregs.es , reg.x.bx);

    Screen = MK_FP(0xb800 , 0);

    ss = _SS;
    sp = _SP;

    /* get old ISR's */
    old_28 = getvect(0x28);
    old_timer = getvect(TIMER);

    /* set new ISR's  */
    setvect(0x28 , new_28);
    setvect(TIMER , new_timer);
    

    /* terminate stay resident */

      keep(0, program_size());
return 0;

}  /*** end main    ***/







