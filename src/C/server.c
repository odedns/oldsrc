
#include "network.h"




main()
{
int sd , sd_new , len;
databuff_t Data;

	sd = ServerInit();
	if(sd < 0) {
		perror("Server Init");
		exit(1);
	}
	sd_new = AcceptConnection(sd);
	if(sd_new < 0) {
		perror("Accept Conn");
		exit(1);
	}
/*
	if( -1 == recv(sd_new,&Data ,sizeof(Data),0)) {
		perror("recv");
		exit(1);
	}
*/
	RecvData(sd_new,&Data);
	print_data(&Data);
/*
	close(sd);
*/
	close(sd_new);
}	


