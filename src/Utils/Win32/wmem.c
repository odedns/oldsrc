
#include <stdio.h>
#include <windows.h>

int main()
{
MEMORYSTATUS m;

	GlobalMemoryStatus(&m);
	printf("% memory in use: %ld\n",m.dwMemoryLoad);
	printf("Total kbytes : %ld\n",m.dwTotalPhys / 1024);
	printf("Free kbytes : %ld\n",m.dwAvailPhys / 1024);
	printf("Total kbytes paging : %ld\n",m.dwTotalPageFile / 1024);
	printf("Free kbytes paging : %ld\n",m.dwAvailPageFile / 1024);
	printf("User kbytes : %ld\n",m.dwTotalVirtual / 1024);
	printf("Free User kbytes : %ld\n",m.dwAvailVirtual / 1024);

			
	return(0);
}
