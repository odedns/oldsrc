/*------------------------------------------------------------------------*/
/*   Module       :   ecd.exe                                             */
/*   File         :   ecd.c                                               */
/*   Date         :   15/03/1997                                          */
/*   Description  :   ecd - an  enhanced cd program for MSDOS.            */
/*   Author       :   Oded Nissan                                         */
/*                                                                        */
/*   History      :                                                       */
/*------------------------------------------------------------------------*/
/*   Date         |   Description                                         */
/*------------------------------------------------------------------------*/
/*   15/03/1997   |   Initial Release.  (1.0)                             */
/*                |                                                       */
/*------------------------------------------------------------------------*/

#include <stdio.h>
#include <string.h>
#ifdef __TURBOC__
#include <dir.h>
#endif
#ifdef _WIN32
#include <direct.h>
#endif

#ifndef MAXPATH
#define MAXPATH (80)
#endif

#include <errno.h>

#define TOUPPER(ch)  ( (ch > 'Z' ? ch - 32 : ch))
#ifdef _WIN32
#define getdisk _getdrive
#define chdir _chdir
#define setdisk _chdrive
#endif

void help();
int split_cmd(char *path,int *drive, char *dir);


int main(int argc, char **argv)
{
int rv;
char path[MAXPATH];
int drive;

	/* if no arguments display current dir */
	if(argc < 2) {
		getcwd(path,MAXPATH);
		puts(path);
		return(1);
	}

	/* display help */
	if(!strcmp(argv[1],"-h")) {
		help();
		return(0);
	}

	rv = split_cmd(argv[1],&drive,path);
	
	/* if disk drive given then change drive */
	if(rv) {
		drive = TOUPPER(drive) - 'A';
		setdisk(drive);
		/* check if setdisk succeeded */
		if(drive != getdisk()) {
			printf("Invalid drive specification.\n");
			exit(2);
		}
		
	}

	/* if path specified */
	if(path[0] != '\0') {
		/* change directory */
		/* if(-1 == chdir(path)) { */
		 if(-1 == chdir(argv[1])) { 
			perror(path);
			exit(2);
		}
	}
	return(0);
	
}

void help()
{
char Msg[] = " *** ecd - an enhanced cd utility for MsDos version 1.0 ***\n \
	  By Oded Nissan  15-03-1997";
	printf("%s\n\n%s\n",Msg,
		"usage : ecd  [-h] [drive]:[dir]");
}

int split_cmd(char *path,int *drive,char *dir)
{
int rv = 0;

	if(*(path+1) == ':') {
		rv = 1;
		*drive = *path;

		/* check if path specified as well */
		if(*(path+2) != '\0') {
			strcpy(dir,(path+2));
		} else {
			*dir = '\0';
		}
	} else {
		strcpy(dir,path);
	}

	return(rv);
}

