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
/*------------------------------------------------------------------------*/
/*   06/08/2001   |   WIN32 Release (1.1)                                 */
/*                |                                                       */
/*------------------------------------------------------------------------*/

#include <stdio.h>
#include <string.h>
#include <conio.h>

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
	key = _getch();
	if(!key) {
		key = _getch();
	}

	if(verbose)
		printf("%d\n",key);

	return(key);
}

void help()
{
char Msg[] = " *** getch - get keycode utility version 1.1 ***\n \
	  By Oded Nissan  06-08-2001";
	printf("%s\n\n%s\n%s\n%s\n",Msg,
		"usage : getch [-hv]\n",
		"-v 	- verbose. print the keycode",  
		"-h 	- print this help text");
}

