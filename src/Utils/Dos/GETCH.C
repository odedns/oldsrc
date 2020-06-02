/*------------------------------------------------------------------------*/
/*   Module       :                                                       */
/*   File         :                                                       */
/*   Date         :                                                       */
/*   Description  :                                                       */
/*   Author       :  Oded Nissan                                          */
/*                   Copyright (c) 1994,1997 Oded Nissan.                 */
/*   History      :                                                       */
/*------------------------------------------------------------------------*/
/*   Date         |   Description                                         */
/*------------------------------------------------------------------------*/
/*   01/01/1997   |   Initial Release.                                    */
/*                |                                                       */
/*                |                                                       */
/*------------------------------------------------------------------------*/

#include <stdio.h>
#include <string.h>
#include <dos.h>

int GetKey();
void help();

int main(int argc, char **argv)
{
int key;
int verbose = 0;

	if(argc == 2) {
		if(!strcmp("-h",argv[1])) {
			help();
			return(0);
		}
		if(!strcmp("-v",argv[1])) {
			verbose = 1;
		}
	}
	key = GetKey();

	if(verbose)
		printf("%d\n",key);

	return(key);
}
/*-------------------------------------------------------------------------*\
 *  Read the keyboard buffer returns ascii code of character               *
 *  If key is a function key scan code is returned.                        *
\*-------------------------------------------------------------------------*/
int GetKey()
{
union REGS reg;  /* get a key from keyboard return ascii  & scan code */

    reg.h.ah = 0;
    int86(0x16 , &reg , &reg);
    return((reg.h.al ? reg.h.al : reg.h.ah));
}

void help()
{
char Msg[] = " *** getch - get keycode utility version 1.0 ***\n \
	  By Oded Nissan  19-03-1997";
	printf("%s\n\n%s\n%s\n%s\n",Msg,
		"usage : getch [-hv]\n",
		"-v 	- verbose. print the keycode",  
		"-h 	- print this help text");
}

