#include <stdio.h>
#include <windows.h>
#include <winbase.h>
#include <tlhelp32.h>



int main()
{
	HANDLE h;
	DWORD dwFlags;
	DWORD psId;
	LPPROCESSENTRY32 lpps;
	BOOL rv;


	h = CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS,0);

	if (!h) {
		printf("error \n");
		
		exit(1);
	}

	rv = Process32First(h,lpps);
	if(rv == TRUE) {
		printf("got pid %ld\n",lpps->th32ProcessID);
	} else {
		printf("error occured\n");
	}
	return(0);
}
