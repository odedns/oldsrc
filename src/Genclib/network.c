#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>

#ifdef _WIN32
#include <winsock.h>
#include <io.h>
#else 
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#endif

#include "network.h"



void print_data(databuff_t *pBuff)
{
	printf("len = %d\n",pBuff->len);
	printf("data = %s\n",pBuff->data);
}

/* connect a client application to a host server */
int ClientConnect(char *host)
{
int sd , stat = 1;
struct hostent *hp;
struct sockaddr_in pin;

	if((hp = gethostbyname(host)) == 0) {
		return(-1);
	}
	memset(&pin,sizeof(pin),0);
	pin.sin_family = AF_INET;
	pin.sin_addr.s_addr = ((struct in_addr *)(hp->h_addr))->s_addr;
	pin.sin_port = htons(MYPORT);
	if(-1 == (sd = socket(AF_INET,SOCK_STREAM,0))) {
		return(-1);
	}

	if(-1 == (connect(sd,(struct sockaddr *)&pin,sizeof(pin)))) {
		return(-1);
	}
	return(sd);
}

/* init the server */
int ServerInit()
{
struct sockaddr_in sin;
int sd;


	if(-1 == (sd = socket(AF_INET,SOCK_STREAM,0))) {
		return(sd);
	}
	memset(&sin,sizeof(sin),0);
	sin.sin_family = AF_INET;
	sin.sin_addr.s_addr = INADDR_ANY;
	sin.sin_port = htons(MYPORT);

	if(-1 == (bind(sd,(struct sockaddr *)&sin,sizeof(sin)))) {
		return(-1);
	}
	
	if(-1 == listen(sd,5)) {
		return(-1);
	}
	return(sd);
}


int AcceptConnection(int sock)
{
struct sockaddr_in pin;
int addrlen , new_sd;

	new_sd = accept(sock,(struct sockaddr *)&pin,&addrlen);
	return(new_sd);
}


int NetRead(int fd , void *buff , int len)
{
int bytes = 0 , nelem;

	while(bytes < len) {
		nelem = read(fd,(char *)buff + bytes , len - bytes);
		if(nelem <= 0) {
			return(nelem);
		}
		bytes+= nelem;
	}
	return(bytes);
}

	
int NetWrite(int fd , void *buff , int len)
{
int bytes = 0 , nelem;

	while(bytes < len) {
		nelem = write(fd,(char *)buff + bytes , len - bytes);
		if(nelem <= 0) {
			return(nelem);
		}
		bytes+= nelem;
	}
	return(bytes);
}


int SendData(int sd , databuff_t *pBuff)
{
int stat = 0;

	stat = send(sd,(char *)&pBuff->len,sizeof(int),0);
	if(stat != sizeof(int)) {
		return(-1);
	}
	stat = send(sd,pBuff->data,pBuff->len + 1,0);

	return(stat);
}

int RecvData(int sd , databuff_t *pBuff)
{
int stat;

	
	stat = recv(sd,(char *)&pBuff->len ,sizeof(int),0);
	if(stat != sizeof(int)) {
		return(-1);
	}
	if(NULL == (pBuff->data = (char *)malloc(pBuff->len + 1))) {
		return(-1);
	}
	stat = recv(sd,pBuff->data ,pBuff->len+1,0);
	return(stat);
}
