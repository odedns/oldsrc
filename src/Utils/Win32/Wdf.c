
#include <windows.h>



/*-------------------*\
 * Functions         *
\*-------------------*/
int disk_free(char *drive);


static char buf[5024];

int WINAPI WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance,
                    LPSTR lpszCmdParam, int nCmdShow)
{
static char szAppName[] = "Win Disk Free" ;
char drive[1024];
int i;
//MSG msg;


	if(hPrevInstance)
   	return(FALSE);

	wsprintf(buf,"drive\tMbytes\tused\tfree\t%c\n",'%');



	for(i =2; i < 26; ++i) {
   	wsprintf(drive,"%c%c%c",i+ 'A',':','\\');
      if(DRIVE_FIXED == GetDriveType(drive))
			disk_free(drive);


	}


	MessageBox(NULL,buf,szAppName,MB_OK);
	return 0;
}





int disk_free(char *drive)
{

char tempbuf[256];
DWORD total, avail,used;
DWORD pfree;
int rv;


DWORD  SPerCluster;
DWORD  BPerSector;
DWORD  FreeClusters;
DWORD  TClusters;

	rv = GetDiskFreeSpace(drive, &SPerCluster,&BPerSector,
                   			&FreeClusters,&TClusters);
	if(FALSE == rv) {
		wsprintf(tempbuf,"%s%s:\tnot ready\n",buf,drive);
		strcat(buf,tempbuf);
		return(rv);
	}
	avail = (double) FreeClusters * BPerSector * SPerCluster /(1024 * 1024);
	total = (double) TClusters * BPerSector * SPerCluster / (1024 * 1024);
	used = total - avail;
	pfree = ((float) used / total) * 100;
	wsprintf(tempbuf,"%s\t%ld\t%ld\t%ld\t%d\n",
		drive,total,used,avail,pfree);
      strcat(buf,tempbuf);
	return(rv);
}

