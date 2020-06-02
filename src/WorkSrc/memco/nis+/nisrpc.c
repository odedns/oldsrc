
#include <stdio.h>
#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <rpc/rpc.h>
#include <netinet/in.h>

#include "nis.h"


#define NUM_COLS(x) \
	(NIS_RES_OBJECT(x)->zo_data.objdata_u.en_data.en_cols.en_cols_len)

#define COL_VAL(x,col) \
	(ENTRY_VAL(NIS_RES_OBJECT(x),col))

static int nis_fd;
static struct timeval TIMEOUT = { 0 , 0 };
#define FD_SET_SIZE (256)


int main()
{

char *servaddr = "172.16.136.29";
char *server = "yemanja";
CLIENT *cl;
char *domainname;
char *user = "oded";
nis_result *pres;
ib_request q;
nis_attr attr;
int i;

struct sockaddr_in sock_in;
char table_name[256], key[256];

	memset(&attr,0,sizeof(nis_attr));
	memset(&q,0,sizeof(ib_request));

	domainname = nis_local_directory();
	sprintf(table_name,"passwd.org_dir.%s",domainname);
	sprintf(key,"[uid=%d],%s",252,table_name);

	cl = clnt_create(servaddr,NIS_PROG,NIS_VERSION,"udp");

	if(NULL == cl) {
		clnt_pcreateerror(server);
		exit(1);
	}
	if(FALSE == clnt_control(cl,CLGET_FD,(char *)&nis_fd)) {
		printf("can't get clnt FD\n");
		exit(1);
	}
	printf("nis_fd = %d\n",nis_fd);


	/*
	q.ibr_name = strdup("passwd.org.dir");
	*/
	q.ibr_name = strdup(domainname);
	attr.zattr_ndx = strdup(key);
	attr.zattr_val.zattr_val_val = strdup("252");
	attr.zattr_val.zattr_val_len = strlen("252");
	q.ibr_srch.ibr_srch_val = &attr;
	/*
	q.ibr_srch_len = (strlen(q.ibr_srch_val);
	*/
	pres = nis_iblist_3(&q,cl);	
	if(pres == NULL) {
		printf("NULL returned\n");
		return(1);
	}

	if(pres->status != NIS_SUCCESS) {
		int err;

		nis_perror(pres->status,"nis_lookup_3");
		nis_freeresult(pres);
	}

	printf("zo_name = %s\n",NIS_RES_OBJECT(pres)->zo_name);
	printf("zo_group= %s\n",NIS_RES_OBJECT(pres)->zo_group);
	printf("zo_owner = %s\n",NIS_RES_OBJECT(pres)->zo_owner);
	printf("cols = %d\n", NUM_COLS(pres));
	for(i=0; i < NUM_COLS(pres) -1 ; ++i) {
		printf("data[%d] = %s\n",i,ENTRY_VAL(NIS_RES_OBJECT(pres),i));
	}


	nis_freeresult(pres);
	

	return(0);
}
