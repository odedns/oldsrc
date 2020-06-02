The follow code running under GNUWIN32 print small files ok but
as the file size get close to 1meg it start GPF or hanging.

Sometime it prints fine and other time it create a problem. I have
played with it for a while and can seem to get printer raw data
working.

I am running WIN95.

Am i missing something. Missing something in the WIN32 API or does
the code just not work with gnuwin32 stuff.

Can somepoint me a place that explain how this might be done better.

#include <windows.h>
WINBOOL	WINAPI OpenPrinterA(LPTSTR, LPHANDLE, LPVOID);
WINBOOL WINAPI ClosePrinter(HANDLE);
DWORD   WINAPI StartDocPrinterA(HANDLE, DWORD, LPBYTE);
WINBOOL	WINAPI EndDocPrinter(HANDLE);
WINBOOL WINAPI StartPagePrinter(HANDLE);
WINBOOL WINAPI EndPagePrinter(HANDLE);
WINBOOL	WINAPI WritePrinter(HANDLE, LPVOID, DWORD, LPDWORD);
#include "tol.h"
#include <ctype.h>
#include <string.h>
#include <errno.h>
#include <fcntl.h>

WINBOOL CALLBACK printer_abort(HANDLE printer, int error)
{
	fprintf(stderr, "Printer abort called\n");
	return True;
}

void
static print_file(char *file_name)
{
	static	char	print_info[80];
	char		*print_device;
	char		*print_port;
	char		*print_driver;
	char		buffer[8192];
	int		size;
	DWORD		tmp;
	int		total = 0;
	HANDLE		print_handle;
	DOC_INFO_1	print_doc_info;
	int		input_fd;

	fprintf(stderr, "printing file %s\n", file_name);

	/*
	 * Open the file that we are about to spool to the printer
	 */
	if ((input_fd = open(file_name, O_RDONLY | O_BINARY)) < 0) {
		fprintf(stderr,"Open error on %s (%s)", file_name,strerror(errno));
		return;
	}

	/*
	 * Get the default printer,device, and port for windows system
	 */
	GetProfileString("windows", "device", ",,,", print_info, 80);
	print_device = strtok(print_info, ",");
	print_driver = strtok(NULP, ",");
	print_port   = strtok(NULP, ",");

	if (print_device == NULL || print_driver == NULL || print_port == NULL) {
		log_it("splprint: No default printer found (%s)", print_info);
		return;
	}

	if (OpenPrinterA(print_device, &print_handle, NULP) == 0) {
		fprintf(stderr, "Can not open Printer\n");
		return;
	}

	if (!SetAbortProc(print_handle, printer_abort)) {
		fprintf(stderr, "Can not install printer abort\n");
		ClosePrinter(print_handle);
		return;
	}

	memset(&print_doc_info, 0, sizeof(print_doc_info));
	print_doc_info.pDocName = "mico";
	print_doc_info.pOutputFile = NULL;
	print_doc_info.pDatatype = "RAW";

	if (!StartDocPrinterA(print_handle, 1, (LPSTR)&print_doc_info)) {
		fprintf(stderr, "Could not create print job\n");
		ClosePrinter(print_handle);
		return;
	}

	if (!StartPagePrinter(print_handle)) {
		fprintf(stderr, "Could not start page on printer\n");
		EndDocPrinter(print_handle);
		ClosePrinter(print_handle);
		return;
	}

	/*
	 * Loop copying data from the file to printer. Keep on
	 * copying data till we hit the end of the file.
	 */
	for(;;) {
		if ((size = read(input_fd, buffer, sizeof(buffer))) <= 0)
			break;

		total += size;

		fprintf(stderr, "%d ", total/1024);
		if (!WritePrinter(print_handle, buffer, size, &tmp)) {
			fprintf(stderr, "Not able to write data to printer\n");
			EndPagePrinter(print_handle);
			EndDocPrinter(print_handle);
			ClosePrinter(print_handle);
			close(input_fd);
			return;
		}

}
	if (!EndPagePrinter(print_handle)) {
		fprintf(stderr, "Can not end printer page\n");
		EndDocPrinter(print_handle);
		ClosePrinter(print_handle);
		return;
	}

	if (!EndDocPrinter(print_handle)) {
		fprintf(stderr, "Can not end doc on printer\n");
		ClosePrinter(print_handle);
		return;
	}

	ClosePrinter(print_handle);

	close(input_fd);
}


