#include "network.h"



main()
{
databuff_t Data;
char s[80];
int sd;


	printf("Enter Data ");
	gets(s);

	Data.data = s;
	Data.len = strlen(s) + 1;
	
	sd = ClientConnect("localhost");
	if(sd < 0 ) {
		perror("ClientConnect");
		exit(1);
	}
/*
	if( -1 == send(sd,&Data,sizeof(Data),0)) {
		perror("send");
		exit(1);
	}
*/
	SendData(sd,&Data);

}
