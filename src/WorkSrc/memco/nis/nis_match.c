
#include <stdio.h>
#include <rpc/rpc.h>

#include "yp.h"

ypresp_val *
ypproc_match_2( ypreq_key *argp, CLIENT *clnt);

int main(int argc, char **argv)
{

char *server = "atlas";
char *servaddr = "172.16.136.38";
CLIENT *cl;
struct ypreq_key ypk;
char *domainname, *mapname = "passwd.byname";
char *user = "oded";
ypresp_val *pval;



	if(argc < 2) {
		printf("usage %s <val> <map>\n",argv[0]);
		return(1);
	}
	user = argv[1];
	mapname = argv[2];
	cl = clnt_create(servaddr,YPPROG,YPVERS,"udp");

	if(NULL == cl) {
		clnt_pcreateerror(server);
		exit(1);
	}

	yp_get_default_domain(&domainname);
	
	printf("dmainname = %s\n",domainname);

	ypk.domain= domainname;
	ypk.map = mapname;		
	ypk.key.keydat_len = strlen(user);
	ypk.key.keydat_val = user;

	pval = ypproc_match_2(&ypk,cl);	
	if(pval == NULL) {
		printf("NULL returned\n");
		return(1);
	}

	if(pval->stat != YP_TRUE) {
		int err;

		err = ypprot_err(pval->stat);
		printf("err = %d\n",err);
		printf("err : %s\n",yperr_string(err));

	} else {
	
		printf("stat = %d\n",pval->stat);
		printf("val = %s\n",pval->val.valdat_val);
	}

	return(0);
}
