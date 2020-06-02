
/*------------------------------------------------------------------------*/
/*   Module       :   df.exe                                              */
/*   File         :   df.c                                                */
/*   Date         :   04/02/1995                                          */
/*   Description  :   df for MSDOS - a UNIX like disk free utility.       */
/*   Author       :   Oded Nissan                                         */
/*                                                                        */
/*   History      :                                                       */
/*------------------------------------------------------------------------*/
/*   Date         |   Description                                         */
/*------------------------------------------------------------------------*/
/*   04/02/1995   |   Initial Release.  (1.0)                             */
/*   08/02/1997   |   Handle drive not ready problem. (1.1)               */
/*                |                                                       */
/*------------------------------------------------------------------------*/

#include <stdio.h>
#include <dos.h>
#include <dir.h>

#include "misc.h"

#define TOUPPER(ch)  ( (ch > 'Z' ? ch - 32 : ch))
/*-------------------*\
 * Functions         *
\*-------------------*/
int disk_free(unsigned char drive);
void print_header();
void help();
int handler(int errval,int ax,int bp,int si);


int crit_err = 0;
/*--------------*\
*  Global Vars   *
\*--------------*/
char normal = 1, inode = 0;
extern int optind;


int main(int argc , char **argv)
{
unsigned char drive;
unsigned char curr_drive;
int c;



        harderr(handler);
	while( (c = getopt(argc,argv,"hin")) != EOF) {
	switch(c) {
	  case 'n' :
		      normal = 1;
		      break;
	  case 'i' :
		      inode = 1;
		      break;
	  case 'h' :
		      help();
		      return(1);
	  default  :
				  puts("usage : df [-inh] [drive...]\n");
				  return(1);
	   }
    }

    print_header();
	 curr_drive = getdisk();

if(argv[optind] != NULL) {
	while(argv[optind] != NULL) {
		drive = TOUPPER(*argv[optind] ) - 'A';
		setdisk(drive);
		if (drive == getdisk())  {
			disk_free(drive);
		}
		++optind;
	}
    } else {

	    for(drive =2; drive < 26; ++drive) {
		   setdisk(drive);
		   if (drive == getdisk())  {
			 disk_free(drive);
		   }
	   }
   }

    setdisk(curr_drive);
    putchar('\n');
    return(0);

}

void print_header()
{
	printf("drive\tkbytes\tused\tfree\t%");
	if(inode)
		printf("\tclusters\tused\tfree");
	putchar('\n');
}


int disk_free(unsigned char drive)
{
struct dfree df;
long total, avail,used;
int pfree;
union REGS r;

	getdfree(drive+1,&df);
	if(crit_err) {
		printf("%c:\tnot ready\n",drive + 'A');
		crit_err = 0;
		return(1);
	}	
	if(df.df_sclus == 0xFFFF) {
		return(0);
	}
	avail = ((long)df.df_avail * (long) df.df_bsec * (long)df.df_sclus)/1024;
	total = ((long)df.df_total * (long) df.df_bsec * (long)df.df_sclus)/1024;
	used = total - avail;
	pfree = ((float)used/total) * 100;
	printf("%c:\t%ld\t%ld\t%ld\t%d",
		drive+'A',total,used,avail,pfree);
	if(inode) {
		printf("\t%u\t\t%u\t%u",df.df_total,df.df_total-df.df_avail,
			df.df_avail);
	}
	putchar('\n');
	return(1);
}


void help()
{
char Msg[] = " *** Unix style df utility for Dos version 1.1 ***\n \
	  By Oded Nissan  06-02-1995";
	printf("%s\n\n%s\n%s\n%s\n%s\n",Msg,
		"usage : df [-inh] [drive...]\n",
		"-n 	- print disk usage by Kbytes.",
		"-i 	- print disk usage by clusters",
		"-h 	- print this help text");
}



/* pragma warn -par reduces warnings which occur due to the non use */
/* of the parameters errval, bp and si to the handler.              */


#pragma warn -par

int handler(int errval,int ax,int bp,int si)
{
/* if this is not a disk error then it was another device having trouble */

   if (ax < 0)
   {
      /* report the error */
      /* and return to the program directly
      requesting abort */
      hardretn(0);
   }
/* otherwise it was a disk error */
/* return to the program via dos interrupt 0x23 with abort, retry */
/* or ignore as input by the user.  */
  crit_err = 1;
  hardretn(2);
   return (0);
}
