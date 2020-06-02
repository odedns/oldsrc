
#include <stdio.h>
#include <errno.h>
#include <string.h>
#include <sys/types.h>

#ifdef _WIN32

#include <windows.h>
#include <winsock.h>
#else 
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#endif

#define SIZE    100

int init();
void w32_perror(LPSTR msg);

int read_string(int fd, char *s)
{
	char c;
	int cnt = 0, n;

		n = recv(fd,&c,1,0);
		if(n <= 0 ) {
			return(cnt);
		}
		while(c != '\n') {
			
			s[cnt++] = c;
			n= recv(fd,&c,1,0);
			if(n <= 0) {
				break;
			}
		}
		s[cnt] = '\0';
		return(cnt);
}
int main(int argc , char **argv)
{
int sockfd , n;
char buf[SIZE];
struct sockaddr_in their_addr;
struct hostent *he;
int port;
FILE *fp;

	if(argc < 3 ) {
		printf("usage %s : host port\n",argv[0]);
		return(1);
	}


	init();
	if((he = gethostbyname(argv[1])) == NULL) {
		w32_perror("hostname");
		exit(1);
	}

	/* get port */
	port = atoi(argv[2]);

	if( (sockfd = socket(AF_INET , SOCK_STREAM , 0)) == -1) {
		w32_perror("socket");
		exit(1);
	}
	their_addr.sin_family = AF_INET;
	their_addr.sin_port = htons(port);
	their_addr.sin_addr = *((struct in_addr *)he->h_addr);
	memset(&their_addr.sin_zero,0,8);

	printf("connecting to %s on port %d\n",he->h_name,port);
	if( (connect(sockfd , (struct sockaddr *)&their_addr,
		sizeof(struct sockaddr))) == -1) {
		w32_perror("connect");
		exit(1);
	}

	printf("got connection");

	while(1) {
		printf("nclient>");
		gets(buf);
		strcat(buf,"\r\n");
		n = send(sockfd,buf,strlen(buf),0);
	//	n = recv(sockfd,buf,40,0);
		
		read_string(sockfd,buf);
		
		printf("%s\n",buf);
	}


	/*
	fp = fdopen(sockfd,"r+");
	if(NULL == fp) {
		w32_perror("fdopen");
		exit(1);
	}

	while(1) {
		printf("nclient>");
		gets(buf);
		strcat(buf,"\r\n");
		fputs(buf,fp);
		fflush(fp);
		fgets(buf,SIZE,fp);
		fflush(fp);
		printf("%s\n",buf);
	}
	*/
	close(sockfd);
	return(0);
}
	

void w32_perror(LPSTR msg)
{

		fprintf(stderr," %s failed with error %d\n",
				msg,WSAGetLastError());
		WSACleanup();
}


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
