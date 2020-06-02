
#include <stdio.h>
#include <winsock2.h>
#include <windows.h>


#include "werror.h"
/*
 * holds the winsock error code
 * and error message
 */
struct winsock_error {
	int code;
	char *msg;
};


/* 
 * the winsock error codes and 
 * error messages.
 */
static struct winsock_error winsock_err[] =  {
{WSAEACCES, "WSAEACCES (10013) Permission denied. "},
{WSAEADDRINUSE, "WSAEADDRINUSE (10048) Address already in use. "},
{WSAEADDRNOTAVAIL, "WSAEADDRNOTAVAIL (10049) Cannot assign requested address. "},
{WSAEAFNOSUPPORT, "WSAEAFNOSUPPORT (10047) Address family not supported by protocol family."},
{WSAEALREADY, "WSAEALREADY (10037) Operation already in progress. "},
{WSAECONNABORTED, "WSAECONNABORTED (10053) Software caused connection abort. "},
{WSAECONNREFUSED, "WSAECONNREFUSED (10061) Connection refused. "},
{WSAECONNRESET, "WSAECONNRESET (10054) Connection reset by peer. "},
{WSAEDESTADDRREQ, "WSAEDESTADDRREQ (10039) Destination address required. "},
{WSAEFAULT, "WSAEFAULT (10014) Bad address. "},
{WSAEHOSTDOWN, "WSAEHOSTDOWN (10064) Host is down. "},
{WSAEHOSTUNREACH, "WSAEHOSTUNREACH (10065) No route to host. "},
{WSAEINPROGRESS, "WSAEINPROGRESS (10036) Operation now in progress. "},
{WSAEINTR, "WSAEINTR (10004) Interrupted function call. "},
{WSAEINVAL, "WSAEINVAL (10022) Invalid argument. "},
{WSAEISCONN, "WSAEISCONN (10056) Socket is already connected. "},
{WSAEMFILE, "WSAEMFILE (10024) Too many open files. "},
{WSAEMSGSIZE, "WSAEMSGSIZE (10040) Message too long. "},
{WSAENETDOWN, "WSAENETDOWN (10050) Network is down. "},
{WSAENETRESET, "WSAENETRESET (10052) Network dropped connection on reset. "},
{WSAENETUNREACH, "WSAENETUNREACH (10051) Network is unreachable. "},
{WSAENOBUFS, "WSAENOBUFS (10055) No buffer space available. "},
{WSAENOPROTOOPT, "WSAENOPROTOOPT (10042) Bad protocol option. "},
{WSAENOTCONN, "WSAENOTCONN (10057) Socket is not connected. "},
{WSAENOTSOCK, "WSAENOTSOCK (10038) Socket operation on non-socket. "},
{WSAEOPNOTSUPP, "WSAEOPNOTSUPP (10045) Operation not supported. "},
{WSAEPFNOSUPPORT, "WSAEPFNOSUPPORT (10046) Protocol family not supported. "},
{WSAEPROCLIM, "WSAEPROCLIM (10067) Too many processes. "},
{WSAEPROTONOSUPPORT, "WSAEPROTONOSUPPORT (10043) Protocol not supported. "},
{WSAEPROTOTYPE, "WSAEPROTOTYPE (10041) Protocol wrong type for socket. "},
{WSAESHUTDOWN, "WSAESHUTDOWN (10058) Cannot send after socket shutdown. "},
{WSAESOCKTNOSUPPORT, "WSAESOCKTNOSUPPORT (10044) Socket type not supported. "},
{WSAETIMEDOUT, "WSAETIMEDOUT (10060) Connection timed out. "},
{WSAEWOULDBLOCK, "WSAEWOULDBLOCK (10035) Resource temporarily unavailable. "},
{WSAHOST_NOT_FOUND, "WSAHOST_NOT_FOUND (11001) Host not found. "},
{WSA_INVALID_HANDLE, "WSA_INVALID_HANDLE (OS dependent) Specified event object handle is invalid. "},
{WSA_INVALID_PARAMETER, "WSA_INVALID_PARAMETER (OS dependent) One or more parameters are invalid. "},
{WSAEINVALIDPROCTABLE, "WSAINVALIDPROCTABLE (OS dependent) Invalid procedure table from service provider. "},
{WSAEINVALIDPROVIDER, "WSAINVALIDPROVIDER (OS dependent) Invalid service provider version number. "},
{WSA_IO_INCOMPLETE, "WSA_IO_INCOMPLETE (OS dependent) Overlapped I/O event object not in signaled state. "},
{WSA_IO_PENDING, "WSA_IO_PENDING (OS dependent) Overlapped operations will complete later."},
{WSA_NOT_ENOUGH_MEMORY, "WSA_NOT_ENOUGH_MEMORY (OS dependent) Insufficient memory available. "},
{WSANOTINITIALISED, "WSANOTINITIALISED (10093) Successful WSAStartup not yet performed. "},
{WSANO_DATA, "WSANO_DATA (11004) Valid name, no data record of requested type. "},
{WSANO_RECOVERY, "WSANO_RECOVERY (11003) This is a non-recoverable error. "},
{WSAEPROVIDERFAILEDINIT, "WSAPROVIDERFAILEDINIT (OS dependent) Unable to initialize a service provider. "},
{WSASYSCALLFAILURE, "WSASYSCALLFAILURE (OS dependent) System call failure. "},
{WSASYSNOTREADY, "WSASYSNOTREADY (10091) Network subsystem is unavailable. "},
{WSATRY_AGAIN, "WSATRY_AGAIN (11002) Non-authoritative host not found. "},
{WSAVERNOTSUPPORTED, "WSAVERNOTSUPPORTED (10092) WINSOCK.DLL version out of range. "},
{WSAEDISCON, "WSAEDISCON (10094) Graceful shutdown in progress. "},
{WSA_OPERATION_ABORTED, "WSA_OPERATION_ABORTED (OS dependent) Overlapped operation aborted. "},
{-1, NULL}
};


char *winsock_get_error()
{
	int i;
	int error = WSAGetLastError();
	static char *perr = NULL;

	for(i=0;  i < (sizeof(winsock_err)/ sizeof(struct winsock_error)); 
			++i) {
		if(error == winsock_err[i].code ) {
			perr = winsock_err[i].msg;
			break;
		}
	}
	return(perr != NULL ? perr : "Unknown Error");
}


void winsock_perror(LPSTR msg)
{
	printf("%s: %s", msg,winsock_get_error());
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
