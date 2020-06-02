
#include <stdio.h>
#include <errno.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <sys/wait.h>


#define MYPORT 3490

#define BACKLOG 10
#define SIZE    100

int main(int argc , char **argv)
{
int sockfd , numbytes;
char buf[SIZE];
struct sockaddr_in their_addr;
struct hostent *he;

	if(argc < 2 ) {
		printf("usage %s : host\n",argv[0]);
		return(1);
	}
	if((he = gethostbyname(argv[1])) == NULL) {
		perror("hostname");
		exit(1);
	}

	if( (sockfd = socket(AF_INET , SOCK_STREAM , 0)) == -1) {
		perror("socket");
		exit(1);
	}
	their_addr.sin_family = AF_INET;
	their_addr.sin_port = htons(MYPORT);
	their_addr.sin_addr = *((struct in_addr *)he->h_addr);
	bzero(&(their_addr.sin_zero),8);

	if( (connect(sockfd , (struct sockaddr *)&their_addr,
		sizeof(struct sockaddr))) == -1) {
		perror("connect");
		exit(1);
	}
	if((numbytes = recv(sockfd,buf,SIZE,0)) == -1 ) {
		perror("recv");
		exit(1);
	}
	buf[numbytes] = '\0';
	printf("Received = %s\n",buf);
	close(sockfd);
	return(0);
}
	
