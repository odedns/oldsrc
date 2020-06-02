
#include <stdio.h>
#include <winsock.h>


int handle_error();
int  GetHost(char *hname );


int main(int argc , char **argv)
{

	WSADATA wsaData;
	if(argc < 2) {
		printf("usage %s <hostname>",argv[0]);
		exit(1);
	} 

	if (WSAStartup(0x202,&wsaData) == SOCKET_ERROR) {
		fprintf(stderr,"WSAStartup failed with error %d\n",
				WSAGetLastError());
		WSACleanup();
		return -1;
	}
	get_host(argv[1]);
	return(0);
}

int  get_host(char *hname)
{
	int rv = 0;
	struct hostent *h;
	struct in_addr in;
	
	// if name given search by name
	if(!isdigit(hname[0])) {
		h = gethostbyname(hname);
	} else {
		// address given search by address
		in.s_addr = inet_addr(hname);
		h = gethostbyaddr((char *)&in.s_addr, 
				sizeof(in.s_addr), AF_INET);
	}
	
	/* handle errors */
	if(!h) {
		rv = handle_error();
		return(rv);
	}

	/* format response data */
	in = *(struct in_addr *)h->h_addr;
	printf("%s\r\n%s", h->h_name, inet_ntoa(in));

	return(rv);
}



int handle_error()
{
int error;

	error = WSAGetLastError();
	switch(error) {
		case WSAENETDOWN:
			printf("The network subsystem has failed.");
			break;
 		case WSAHOST_NOT_FOUND:
			printf("Authoritative Answer Host not found.");
			break;
		case WSATRY_AGAIN:
			printf("Non-Authoritative Host not found, or server failed. ");
			break;
		case WSANO_RECOVERY:
			printf("Nonrecoverable error occurred.");
			break;
		case WSANO_DATA:
			printf("Hostname not found.");
			break;
		default:
			printf("Unknown error");
			break;
	}
	return(error);
}



