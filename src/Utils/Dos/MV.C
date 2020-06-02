/*-----------------------------------------------------------------------*\
 *                                                                       *
 * mv for MSDOS - A Unix Like mv Utility !	                         *
 *                                                                       *
 *                                                                       *
 * By : Oded Nissan                                                      *
 * Date : 22/03/1995.							 *
 *                                                                       *
 *                                                                       *
 *                                                                       *
 *                                                                       *
 *                                                                       *
 *                                                                       *
 *                                                                       *
 *                                                                       *
 *                                                                       *
 *                                                                       *
\*-----------------------------------------------------------------------*/
/* Unix like mv program   */
/* include files *\
\* ------------- */

#include <stdio.h>
#include <string.h>
#include "misc.h"

/* Macros *\
\* ------ */

/* functions *\
\* --------- */

void help();


/* global vars *\
\* ----------- */

char Msg[] = " *** Unix style mv utility for Dos version 1.0 ***\n \
	  By Oded Nissan  22-03-1995";
int verbose = 0 , recurse = 0, force = 0, prompt = 0, emulate = 0;

/* Code *\
\* ---- */

int main(int argc , char **argv)
{
char c ,**argp;
extern int optind;
int stat = 0 ,i = 0 ,j = 1;


	argp = (char **)calloc(argc,sizeof(char *));

	while( (c = getopt(argc,argv,"vifhn")) != EOF) {
	switch(c) {
	  case 'v' :
		      verbose = 1;
		      break;
	  case 'i' :
		      prompt = 1;
		      break;
	  case 'f' :
		      force    = 1;
		      break;
	  case 'h' :  help();
		      return(1);
	  case 'n' :
		      emulate = 1;
		      break;
	  default  :
			  printf("usage: mv [-vifhn] source target \n");
		      return(2);
       }
    }

	if(argv[optind] == NULL) {
		program_msg("mv: no filename given");
		help();
		return(1);
	}

        argp[i++] = argv[0];
	argp[i++] = strdup("-rq");
	while(argv[j] != NULL) {
		argp[i++] = argv[j++];
	}

	argp[i] = NULL;
	stat = exec_process("cp ", argp);
        argp[argc] = NULL;
	stat = exec_process("rm ", argp);

    return(stat);
}  /**** main ****/


void help()
{
	printf("%s\n\n%s%s%s%s%s%s\n",Msg,
		"usage: mv [-vifnh] source target \n\n",
		"-v 	- verbose.\n",
		"-i 	- prompt before overwritting\n",
		"-f      - force. override protection\n",
		"-n      - emulate. do not move files\n",
		"-h 	- print this help text\n");

}
