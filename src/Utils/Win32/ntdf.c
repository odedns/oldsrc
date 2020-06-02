
/*------------------------------------------------------------------------*/
/*   Module       :  ntdf.exe                                             */
/*   File         :  ntdf.c                                               */
/*   Date         :  14-08-1998                                           */
/*   Description  :  A UNIX like disk free utility for win95/NT.          */
/*   Author       :  Oded Nissan                                          */
/*                   Copyright (c) 1994-1998 Oded Nissan.                 */
/*   History      :                                                       */
/*------------------------------------------------------------------------*/
/*   Date         |   Description                                         */
/*------------------------------------------------------------------------*/
/*   14/08/1998   |   Initial Release.                                    */
/*------------------------------------------------------------------------*/

#include <windows.h>
#include <stdio.h>
#include <ctype.h>

#define SHOW_CLUSTERS (0x0001)
#define SHOW_NORMAL   (0x0000)

/*-------------------*\
 * Functions         *
\*-------------------*/
int disk_free(char *drive);
void print_header();
void help();
static int flags = 0;

int main(int argc, char **argv)
{
char drive[1024], *drv = NULL;
int i = 1;
UINT type;


/*
 * parse command line for
 * arguments
 */
	while(--argc) {
		if(!strcmp(argv[i],"-i")) {
			flags |= SHOW_CLUSTERS;
			continue;
		}
		if(!strcmp(argv[i],"-n")) {
			flags |= SHOW_NORMAL;
			continue;
		}
		if(!strcmp(argv[i],"-h")) {
			help();
			return(1);
		}
		/*
		 * get free disk space for
		 * a specific drive.
		 */
		drv = argv[i];
		sprintf(drive,"%c:\\",toupper(*drv));
		type = GetDriveType(drive);
		if(DRIVE_UNKNOWN != type && DRIVE_NO_ROOT_DIR != type) 
			disk_free(drive);

		++i;	
	}

	/*
	 * loop over all drives and 
	 * get the free disk space.
	 */
	if(NULL == drv) {
		/* no drives given */
		for(i =2; i < 26; ++i) {
			sprintf(drive,"%c%c%c",i+ 'A',':','\\');
			type = GetDriveType(drive);
			if(DRIVE_UNKNOWN != type && DRIVE_NO_ROOT_DIR != type) 
				disk_free(drive);
		}
	}

	return 0;
}


void print_header()
{
	printf("drive\tmbytes\tused\tfree\t%c",'%');
	if(flags & SHOW_CLUSTERS) {
		printf("\tClusters\tUsed\tFree");
	}
	putchar('\n');

}


/*
 * get the free disk space for drive.
 * return 0 if succesfull 
 * FALSE  in case of error.
 */
int disk_free(char *drive)
{

DWORD total, avail,used;
DWORD pfree;
int rv;
DWORD  SPerCluster;
DWORD  BPerSector;
DWORD  FreeClusters;
DWORD  TClusters;

static int first_time = 1;

	if(first_time) {
		print_header();
		first_time = 0;
	}

	rv = GetDiskFreeSpace(drive, &SPerCluster,&BPerSector,
                   			&FreeClusters,&TClusters);
	if(FALSE == rv) {
		printf("%s\tnot ready\n",drive);
		return(rv);
	}
	avail = (double)FreeClusters * BPerSector * SPerCluster /(1024 * 1024);
	total = (double) TClusters * BPerSector * SPerCluster /(1024 * 1024);
	used = total - avail;
	pfree = ((float) used / total) * 100;

	printf("%s\t%ld\t%ld\t%ld\t%d",
		drive,total,used,avail,pfree);

	if(flags & SHOW_CLUSTERS) {
		printf("\t%ld\t\t%ld\t%ld", 
			TClusters,TClusters-FreeClusters,
			FreeClusters);
	} 
	putchar('\n');
	return(rv);
}


void help()
{
char Msg[] = "ntdf version 1.0\n"
	     "A UNIX like disk free utility for WIN95/NT\n"
	     "Copyright (c) 1998 Oded Nissan\n" 
	     "Usage: ntdf [-inh] [drive...]\n"
	     "Options:\n"
	     "\t-n 	print disk usage by Megabytes.\n"
	     "\t-i 	print disk usage by clusters\n"
	     "\t-h 	print this help text\n";

	fprintf(stderr, Msg);
}

