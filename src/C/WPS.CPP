#include <stdio.h>
#include <windows.h>
#include <winbase.h>
#include <tlhelp32.h>

void win_perror(LPSTR msg);
void print_processentry32(LPPROCESSENTRY32 lpps);
void print_ps(LPPROCESSENTRY32 lpps);


int main()
{
	HANDLE h;
	DWORD dwFlags;
	DWORD psId;
	PROCESSENTRY32 ps;
	BOOL rv;


	h = CreateToolhelp32Snapshot(TH32CS_SNAPALL,0);

	if (!h) {
		win_perror("CreateToolHelp32Snapshot");
		exit(1);
	}

		
	ps.dwSize = sizeof(PROCESSENTRY32);
	rv = Process32First(h,&ps);
	if(TRUE == rv) {
		puts("PID\tPPID\tName\n");
	}
	while(rv == TRUE) {
		print_ps(&ps);
		ps.dwSize = sizeof(PROCESSENTRY32);
		rv = Process32Next(h,&ps);
	}
	return(0);
}

void win_perror(LPSTR msg)
{
	LPVOID lpmsg;
	DWORD err;

	err = GetLastError();
	if(!FormatMessage(FORMAT_MESSAGE_ALLOCATE_BUFFER |
			  FORMAT_MESSAGE_FROM_SYSTEM,NULL,err,
			  MAKELANGID(LANG_NEUTRAL,SUBLANG_DEFAULT),
			  (LPSTR)&lpmsg,0,NULL)) {
		fprintf(stderr,"%s: unknown error %d\n",msg,err);
		return;
	}
	fprintf(stderr, "%s: Error %d: %s\n",msg,err,lpmsg);
	LocalFree(lpmsg);
}

void print_ps(LPPROCESSENTRY32 lpps)
{
	printf("%10lu\t",lpps->th32ProcessID );
	printf("%10lu\t",lpps->th32ParentProcessID);
	printf("%s\n",lpps->szExeFile);
}

void print_processentry32(LPPROCESSENTRY32 lpps)
{
	    printf("dwSize = %ld\n",lpps->dwSize);
	    printf("cntUsage= %ld\n",lpps->cntUsage);
	    printf("th32ProcessID= %ld\n",lpps->th32ProcessID);
	    printf("th32DefaultHeapID= %ld\n",lpps->th32DefaultHeapID);
	    printf("th32ModuleID= %ld\n",lpps->th32ModuleID);
	    printf("cntThreads= %ld\n",lpps->cntThreads);
	    printf("th32ParentProcessID= %ld\n",lpps->th32ParentProcessID);
	    printf("pcPriClassBase= %ld\n",lpps->pcPriClassBase);
	    printf("dwFlags = %ld\n",lpps->dwFlags);
	    printf("szExeFile= %s\n",lpps->szExeFile);
}

