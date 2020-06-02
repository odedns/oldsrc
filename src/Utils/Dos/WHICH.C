/*------------------------------------------------------------------------*/
/*   Module       :  which.exe                                            */
/*   File         :  which.c                                              */
/*   Date         :  01/08/1996                                           */
/*   Description  :  a UNIX like which utility for MSDOS.                 */
/*                   searches path for an executable.                     */
/*   Author       :  Oded Nissan                                          */
/*                                                                        */
/*   History      :                                                       */
/*------------------------------------------------------------------------*/
/*   Date         |   Description                                         */
/*------------------------------------------------------------------------*/
/*   01/08/1996   |   Initial Release.                                    */
/*                |                                                       */
/*                |                                                       */
/*------------------------------------------------------------------------*/


/* include files *\
\* ------------- */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <dir.h>
#include <dos.h>


/* code *\
\* ---- */

char *find_cmd(char *cmd)
{
/* this is the search order supported by DOS */
char *exts[] = { "com","exe","bat", NULL };

char *path, *curr_path,temp_path[250] , *ptr;
static char search_path[MAXPATH];
struct ffblk fs;
int found = 0, i = 0, full_name = 0;

	if(strchr(cmd,'.'))
		full_name = 1;

	path = getenv("PATH");
	/* copy path to temp string so strtok won't mess up */
	sprintf(temp_path,".;%s",path);
	curr_path = strtok(temp_path,";");
	while(NULL != curr_path && !found ) {
			/* point to end of path */
			 ptr = curr_path + strlen(curr_path) - 1;
			 /* remove trailing // */
			 if(*ptr == '\\') 
				*ptr = '\0';
			/* search only for full_name */
			if(full_name) {
				sprintf(search_path,"%s\\%s",
						curr_path,cmd,exts[i]);
				if(!findfirst(search_path,&fs,FA_HIDDEN | FA_ARCH)) {
						sprintf(search_path,"%s\\%s",
							curr_path,fs.ff_name);
						found = 1;
				}

			} else {

			/* search for all executable extensions */
		
				for(i=0; exts[i]; ++i) {
					sprintf(search_path,"%s\\%s.%s",
							curr_path,cmd,exts[i]);
					if(!findfirst(search_path,&fs,FA_HIDDEN | FA_ARCH)) {
							sprintf(search_path,"%s\\%s",
							curr_path,fs.ff_name);
							found = 1;
							break;
					}

				} /* for */
			} /* if */

			curr_path = strtok(NULL,";");


	}
	return(found ? search_path : NULL);
}

/* prints help message *\
\* ------------------- */
void help()
{
char Msg[] = " *** UNIX like which utility for MsDos version 1.0 ***\n \
	  By Oded Nissan  29-08-1996\n";
	fprintf(stdout,"\n%s\n%s\n",Msg,
		"usage : which  [file...]");
}


/* main program *\
\* ------------ */
int main(int argc , char **argv)
{
char *cmd_path;
int i = 1;

	if(argc < 2 ) {
		help();
		return(1);
	}
	if(!strcmp(argv[1],"-h")) {
		help();
		return(1);
	}
	/* loop over command line args */
	while(--argc) {

		if(NULL != (cmd_path = find_cmd(strlwr(argv[i])))) 
			fprintf(stdout,"%s\n",strlwr(cmd_path));
		else 
			fprintf(stdout,"%s : command not found\n",argv[i]);
		++i;
	}

	return(0);
}
