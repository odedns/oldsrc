
#include <stdio.h>
#include <errno.h>
#include <string.h>
#include <sys/types.h>

#ifdef _WIN32
#include <windows.h>
#include <winsock.h>
#include "werror.h"
#define PERROR winsock_perror

#else 
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#define PERROR perror
#endif

#define SIZE    100
#define FINGER_PORT   (79)

static int long_format = 0;
void finger(char *user);
int init();
void help();



/*
 * finger users on a remote machine
 * return output in long and short format.
 */
int main(int argc, char **argv)
{
int i = 1;
char *user = NULL;


/* init winsock */
#ifdef _WIN32
	init();
#endif

/*
 * parse command line for
 * arguments
 */

	if(argc < 2) {
		help();
		return(1);
	}

	while(--argc) {
		if(!strcmp(argv[i],"-h")) {
			help();
			return(1);
		}
		if(!strcmp(argv[i],"-l")) {
			long_format = 1;
		} else {
			user = argv[i];
			finger(user);
		}
		++i;	
	}

	if(NULL == user) {
		/* no users given */
		finger("");
	}
	return(0);
}


/*
 * init winsock
 * mandatory under WIN32 
 */
#ifdef _WIN32
int init()
{
WSADATA wsaData;
int err; 

	if (WSAStartup(0x202,&wsaData) == SOCKET_ERROR) {
		fprintf(stderr,"WSAStartup failed with error %d\n",
				WSAGetLastError());
		WSACleanup();
		return -1;
	}

}

#endif

/*
 * finger the user user
 * print return info to stdout.
 */
void finger(char *user)
{
int sockfd;
char buf[SIZE];
struct sockaddr_in their_addr;
struct hostent *he;
int port;
FILE *fp;
struct servent *sr;
char *hname;
char *fmt;
char c;
int n;

	if(long_format) {
		fmt = "/w " ;
	}else {
		fmt = "";
	}

	/*
	 * get the finger port if from 
	 * the services database.
	 */
	sr = getservbyname("finger", "tcp");
	if(NULL == sr) {
		PERROR("getservbyname");
		port = FINGER_PORT;
	} else {
		port = sr->s_port;
	}

	/* 
	 * if under win32 the finger port should correspond
	 * to the port of the finger server on the UNIX
	 * machine. 
	 * so we default to 79.
	 */ 
#ifdef _WIN32
	port = FINGER_PORT;
#endif

	/* 
	 * get the hostname we are quering
	 * if no hostname is supplied
	 * we default to the localhost.
	 */
	hname = strchr(user,'@');
	if(NULL == hname) {
		strcpy(buf,"localhost");
		hname = buf;
	} else {
		++hname;
	}

	/*
	 * get the host address from the hosts
	 * database.
	 */
	if((he = gethostbyname(hname)) == NULL) {
		PERROR("gethostname");
		WSACleanup();
		exit(1);
	}

	/* create socket */
	if( (sockfd = socket(AF_INET , SOCK_STREAM , 0)) == -1) {
		PERROR("socket");
		WSACleanup();
		exit(1);
	}
	their_addr.sin_family = AF_INET;
	their_addr.sin_port =  htons(port);
	their_addr.sin_addr = *((struct in_addr *)he->h_addr);
	memset(&their_addr.sin_zero,0,8);


	/*
	 * connect to the finger server
	 */
	if( (connect(sockfd , (struct sockaddr *)&their_addr,
		sizeof(struct sockaddr))) == -1) {
		PERROR("connect");
		WSACleanup();
		exit(1);
	}


	/* 
	 * send the finger query to the 
	 * server.
	 * send /w for long output
	 * receive reply byte by byte.
	 */
	sprintf(buf, "%s%s\r\n",fmt,user);
	send(sockfd,buf,strlen(buf),0);
	n = recv(sockfd,(char *)&c,1,0);
	while(n > 0 ) {
		putchar(c);
		n = recv(sockfd,(char *)&c,1,0);
	}
	/* close socket */
	close(sockfd);
}
	
void help()
{
char Msg[] = "finger version 1.0 15-09-1998\n"
	     "A finger utility for WIN95/NT\n"
	     "Copyright (c) 1998 Oded Nissan\n" 
	     "Usage: finger [-l] [user@hostname...]\n"
	     "Options:\n"
	     "\t-l 	print user finger information in long format\n"
	     "\t-h 	print this help text\n";

	fprintf(stderr, Msg);
}

