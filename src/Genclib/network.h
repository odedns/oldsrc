


#define MYPORT (1602)

typedef struct {
	int len;
	char *data;
	} databuff_t;

void print_data(databuff_t *pBuff);
int ClientConnect(char *host);
int ServerInit();
int AcceptConnection(int sock);
int NetRead(int fd , void *buff , int len);
int NetWrite(int fd , void *buff , int len);
int SendData(int sd , databuff_t *pBuff);
int RecvData(int sd , databuff_t *pBuff);
