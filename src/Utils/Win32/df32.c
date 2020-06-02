
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


#define TOUPPER(ch)  ( (ch > 'Z' ? ch - 32 : ch))
/*-------------------*\
 * Functions         *
\*-------------------*/
int disk_free(unsigned char drive);
void print_header();
void help();


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
struct diskfree_t df;
long total, avail,used;
int pfree;
union REGS r;
int rv;

	rv = _dos_getdiskfree(drive+1,&df);
	if(rv) {
		printf("%c:\tnot ready\n",drive + 'A');
		return(1);
	}
   /*
	if(df.df_sclus == 0xFFFF) {
		return(0);
	}
   */
	avail = ((long)df.avail_clusters * (long) df.bytes_per_sector * (long)df.sectors_per_cluster)/1024;
	total = ((long)df.total_clusters * (long) df.bytes_per_sector * (long)df.sectors_per_cluster)/1024;
	used = total - avail;
	pfree = ((float)used/total) * 100;
	printf("%c:\t%ld\t%ld\t%ld\t%d",
		drive+'A',total,used,avail,pfree);
	if(inode) {
		printf("\t%u\t\t%u\t%u",df.total_clusters,df.total_clusters-df.avail_clusters,
			df.avail_clusters);
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




