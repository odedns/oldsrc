
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>

#define PORT (1600)

int main(int argc , char **argv)
{
int sd;
struct sockaddr_in sin , pin;
char cmd[BUFSIZ] , outbuff[BUFSIZ * 4];
struct hostent *hp;

	if(argc < 3 ) {
		printf("usage : %s <host> <command>\n",argv[0]);
		return(1);
	}

	strcpy(cmd,argv[2]);

	if((hp = gethostbyname(argv[1])) == 0) {
		perror("gethostbyname");
		exit(1);
	}

	bzero(&pin,sizeof(pin));
	pin.sin_family = AF_INET;
	pin.sin_addr.s_addr = ((struct in_addr *)(hp->h_addr))->s_addr;
	pin.sin_port = htons(PORT);

	if(-1 == (sd = socket(AF_INET,SOCK_STREAM,0))) {
		perror("socket");
		exit(1);
	}

	if(-1 == (connect(sd,(struct sockaddr *)&pin,sizeof(pin)))) {
		perror("connect");
		exit(1);
	}
	
	if( -1 == send(sd,cmd,strlen(cmd),0)) {
		perror("send");
		exit(1);
	}

	if( -1 == recv(sd,outbuff,sizeof(outbuff),0)) {
		perror("accept");
		exit(1);
	}


	
	printf("result : \n%s\n",outbuff);
	close(sd);
	return(0);
		
}
		
