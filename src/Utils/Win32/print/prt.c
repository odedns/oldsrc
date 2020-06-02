#include <stdio.h>
#include <sys/fcntl.h>

#include <w32api/windows.h>


void WPerror(LPSTR msg)
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

int main(int argc, char **argv)
{	
	char *s = "Some fucking string to print\n";
	char print_info[80];
	LPSTR name = "hp";
	HANDLE printer;
	BOOL rv;
	DOC_INFO_1 docInfo;
	DWORD tmp;
	DWORD jobId;
	int fd;
	char buff[1024];
	int bytesRead = 0;


	puts("starting prt...\n");

	/*
	 * Get the default printer,device, and port for windows system
	 */
	GetProfileString("windows", "device", ",,,", print_info, 80);
	name = strtok(print_info, ",");

	printf("printer name : %s\n", name);
	
	rv = OpenPrinter(name,&printer,NULL);
	if(!rv) {
		WPerror("Error in OpenPrinter");
		exit(1);
	}
	printf("printer opened\n");


    docInfo.pDocName  = "MyDoc";
    docInfo.pDatatype = NULL;
    docInfo.pOutputFile = NULL;

    jobId = StartDocPrinter(printer, 1, (LPBYTE)&docInfo);
	if(!jobId) {
		WPerror("Error in StartDocPrinter");
		exit(1);
	}
	StartPagePrinter(&printer);

	printf("writing data to printer ..\n");
	/*
	 * Open the file that we are about to spool to the printer
	 */
	if ((fd = open("d:/work/print/AutoFont/form150.map", O_RDONLY | O_BINARY)) < 0) {
		fprintf(stderr,"error opening file\n");
		return;
	}

	bytesRead = read(fd, buff, sizeof(buff));
	while(bytesRead > 0) {
		rv = WritePrinter(printer,buff, bytesRead, &tmp);
		if(!rv) {
			WPerror("Error in WritePrinter");
			exit(1);
		}
		bytesRead = read(fd, buff, sizeof(buff));
	}
	close(fd);
	
	if (!EndPagePrinter(printer)) {
		WPerror("EndPagePrinter");
		EndDocPrinter(printer);
		ClosePrinter(printer);
		return;
	}

	if (!EndDocPrinter(printer)) {
		WPerror("EndDocPrinter");
		fprintf(stderr, "Can not end doc on printer\n");
		ClosePrinter(printer);
		return;
	}

	printf("now closing printer ..\n");

	ClosePrinter(printer);
	return(0);
}
