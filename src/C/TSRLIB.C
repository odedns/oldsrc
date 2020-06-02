

#include <dos.h>

/*------- compute program size --------------*/

 unsigned program_size()
 {
    return(*((unsigned far *) (MK_FP(_psp -1,3))) );
 }

/*---------get dos busy flag -------- */

char far *get_dos_flag()
{
	_AH = 0x34;
	geninterrupt(0x21);
	return(MK_FP(_ES , _BX));
}

int keypressed()
{
char far *t = (char far *) 1050;
    return((*t != *(t+2)) ? 1 : 0);
}
int get_key()
{
char far *t = (char far *)1050;

	if(*t != *(t+2) ) { /* if not empty */
		t += *t -30 + 5; /* advance to next char */
		return(*t);
	}
	return(0);
}

void reset_keyb()
{
int far *t2 = (int far *)1050;
char far *t = (char far *)1050;

	if(*t != *(t+2) ) { /* if not empty */
		*(t2 + 1) = *t2;
	}
}


main()
{

	while(!keypressed()) ;

			printf("key = %d\n",get_key());
			reset_keyb(); 
}