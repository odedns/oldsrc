
#include <windows.h>
#include <stdio.h>

#include "wmisc.h"
#include "werror.h"


LPSTR w32_read_file(LPSTR fname)
{
	HANDLE	fHandle;
	BOOL	rv;
	LPSTR buff;
	DWORD fsize, nread;


	fHandle = CreateFile(fname,GENERIC_READ,0,0,
			     OPEN_EXISTING,0,0);
	if(INVALID_HANDLE_VALUE == fHandle) {
		win_perror("CreateFile");
		return(NULL);
	}

	fsize = GetFileSize(fHandle,NULL);
	if(fsize == 0xFFFFFFFF) {
		win_perror("GetFileSize");
		return(NULL);
	}
	buff = GlobalAlloc(GMEM_FIXED,fsize +1);
	if(NULL == buff) {
		win_perror("GlobalAlloc");
		return(NULL);
	}
	rv = ReadFile(fHandle,buff,fsize,&nread,0);
	if(!rv ) {
		win_perror("ReadFile");
		return(NULL);
	}
	CloseHandle(fHandle);
	buff[nread] = '\0';

	return(buff);
}
