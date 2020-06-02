
/* ======================================================================= */
/*  Project : SEOS                                                         */
/*  Module  : SEOS Auxilliary Daemon.               Version: 02.10.00      */
/*  File    : aux_nis_res.c                                                */
/*  Purpose : seauxd nis resolver.                                         */
/* ======================================================================= */
/*  By : Oded Nissan                                                       */
/* ======================================================================= */
/*  Copyright :                                                            */
/*     This source file is copyright (c) to MEMCO Software Ltd.            */
/* ======================================================================= */

#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <unistd.h>
#include <string.h>


/* socket and network include files */
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>

/* rpc include files */
#include <rpc/rpc.h>
#include <rpc/xdr.h>
#include <rpc/pmap_clnt.h>

/* application include files */
#include "yp.h"
#include "aux_resolve.h"
#include "aux_nis_res.h"


#define XDR_SZ (8192)
static int nis_fd = -1;
static int nis_retry = 2;
static char nis_domain[100];
static struct sockaddr_in nis_sin;

static void res_nis_error(int error);
#ifdef _DEBUG

void printbuf(unsigned char *buf, int len)
{
	int i;
	int j;

	for (i = 0; i < len; i+= 16) {
		printf("%08x: ", i);
		for (j = 0; j < 16; j++)
			printf("%02x ", buf[i + j]);
		printf("  ");
		for (j = 0; j < 16; j++)
			printf("%c", (buf[i + j] >= 0x20 && buf[i+j] < 0x7f) ? buf[i+j] : '.');
		putchar('\n');
	}
	putchar('\n');
	putchar('\n');
	putchar('\n');
	putchar('\n');
}

#endif

/* the nis fd handler 
 * receive reply from the specified
 * fd.
 */
void nis_fd_handler(int fd, void *arg)
{
	/* allocate space for reply buffer for fd handler */
	arg = (res_reply_t *)malloc(sizeof(res_reply_t));
	if(NULL == arg) {
		return;
	}
	/* get reply from dns server */
	res_nis_recv(fd,arg);
}

/* the nis timeout handler 
 * retransmit the nis request when a timeout
 * occures
 */
void nis_to_handler(void *arg)
{
int timeout;
req_timeout_t *r = (req_timeout_t *)arg;
res_reply_t *rp;

	aux_debug("nis_to_handler activated for %ld\n",
			r->request->id);
	/* if more retransmitions required retranmit request */
	if(r->num_retrans > 0 ) {

		aux_debug("retransmitting request for id : %ld\n",r->request->id);
		res_nis_transmit(r->request,&r->num_retrans,&timeout);
		aux_set_timeout(timeout,nis_to_handler,arg);

	} else {
        
		/* no more retranmitions required  */

		/* if fallback available call resolver 
                     fallback option */
		if(aux_resolve_fallback(r->request)) {

			aux_activate_resolver(r->request);
		} else {	
		   /* return unresolved request */
			aux_debug("unresolved event returned for id : %ld\n",
                                r->request->id);

			rp = (res_reply_t *) malloc(sizeof(res_reply_t));
			if(NULL == rp) 
				return;

			rp->id = r->request->id;
			rp->q_data = r->request->data;
			rp->data = NULL;
			rp->ret_code = 1;
			aux_post_event(AUX_RESOLVED_EVENT,rp);
		}

	}
	

}

/* close the nis resolver
 * unset the nis handlers 
 */

int res_nis_close()
{
	close(nis_fd);
	aux_unset_fd_handler(nis_fd);
	return(0);

}

int nis_open_server(long server_addr)
{
int port;

	memset(&nis_sin,0,sizeof(struct sockaddr_in));

	nis_sin.sin_addr.s_addr  = server_addr;
	nis_sin.sin_family = AF_INET;
	nis_sin.sin_port = 0;
	port = pmap_getport(&nis_sin,YPPROG,YPVERS,IPPROTO_UDP);
	nis_sin.sin_port = port;

	/* open socket to nis server */
	nis_fd = socket(AF_INET,SOCK_DGRAM,0);
	if(nis_fd < 0) {
		perror("socket");
		return(-1);
	}
	aux_debug("opening nis_fd : %d\n",nis_fd);

	return(0);

}

static char *nis_get_data(int req_type,char *buff)
{
	char *p,*ptemp;
	int i = 0;


	switch(req_type) {
		case REQTYPE_UID :
			p = buff;
			ptemp =p;
			/* skip user name 
			 * insert NULL at end 
			 */
			while(*ptemp != ':' && i < XDR_SZ) {
				++i;
				++ptemp;
			}
			*ptemp = '\0';
			break;

		case REQTYPE_GID :

			p = buff;
			ptemp = p;
			/* skip group name 
			 * insert NULL at end 
			 */
			while(*ptemp != ':' && i < XDR_SZ) {
				++i;
				++ptemp;
			}
			*ptemp = '\0';
			break;

		case REQTYPE_HOST :

			/* point to buffer start */
			ptemp = p = buff;

			/* skip address field */
			while(!isspace(*ptemp) && i < XDR_SZ) {
				++i;
				++ptemp;
			}

			/* skip white space separators */
			while(isspace(*ptemp) && i < XDR_SZ) {
				++i;
				++ptemp;
			}
			p = ptemp;

			/* skip host name */
			while(!isspace(*ptemp) && i < XDR_SZ) {
				++i;
				++ptemp;
			}
			*ptemp = '\0';
		        break;	

		case REQTYPE_SERVICE :
			p = buff;
			ptemp =p;
			/* skip service  name 
			 * insert NULL at end 
			 */
			while(*ptemp != ':' && i < XDR_SZ) {
				++i;
				++ptemp;
			}
			*ptemp = '\0';
			break;

	}
	return(strdup(p));

}
/* init the nis resolver
 * get the addres of the nis server
 * get the nis domain
 */
int res_nis_init()
{
char buf[100];
long server_addr;

	/* if nis fd is already open return 
	 */
	if(nis_fd != -1) { 
		return(0);
	}

	/* get nis host address from ini file */
	if(!aux_ini_get_str("nis","nis_server","",buf,sizeof(buf))) {
		/* if no host defined fail */
		return(-1);
	}

	server_addr = inet_addr(buf);

	/* get nis domain */
	if(!aux_ini_get_str("nis","nis_domain","",
			 nis_domain,sizeof(nis_domain))) {
		/* if no host defined fail */
		return(-1);
	}
	aux_debug("nis_domain : %s\n",nis_domain);

	/* get number of retries from ini file */
	aux_ini_get_str("nis","num_retries","0",buf,sizeof(buf));
	nis_retry = atoi(buf);
	if(nis_open_server(server_addr)) {
		return(-1);
	}

	/* init the dns table */
	aux_nis_tbl_init();

	/* install fd handler 
	 * the nis fd_handler should be installed just once.
         * at initialization time.
         * it will be unset when the daemon is terminated by
         * the library itself.
         */
        aux_set_fd_handler(nis_fd,nis_fd_handler,NULL);
	return(0);

}



/*  transmit request to nis server
 *  num_retrans is the retransmition counter which is incremented by 
 *  this function.
 *  timeout is the timeout in seconds that should be given to the 
 *  request.
 *  returns 0 on success -1 on error.
 */
int res_nis_transmit(res_request_t *rq, 
		        int *num_retrans, int *timeout )
{
struct ypreq_key ypk;
unsigned char xdr_buffer[XDR_SZ];
int n,len;
struct rpc_msg r;
XDR xdrs;

	aux_debug("res_nis_transmit\n");
	
	memset(xdr_buffer,0,sizeof(xdr_buffer));

	ypk.domain = nis_domain;

	/* get the mapname according to
	 * request type
	 */
	switch(rq->type) {
		case REQTYPE_UID : 
			ypk.map = "passwd.byuid";
			break;
		case REQTYPE_GID : 
			ypk.map = "group.bygid";
			break;
		case REQTYPE_HOST: 
			ypk.map = "hosts.byaddr";
			break;
		case REQTYPE_SERVICE: 
			ypk.map = "service.byport";
			break;
	}
	ypk.key.keydat_len = rq->length;
	ypk.key.keydat_val = rq->data;


	/* create xdr buffer */
	xdrmem_create(&xdrs,(char *)xdr_buffer,XDR_SZ,XDR_ENCODE);

	/* get the nis id from the 
	 * nis table for the specified request id
	 * and request type
	 */
	r.rm_xid = aux_get_nis_id(rq->id,rq->type, rq->flags, rq->data);

	/* fill rpc XDR stream */
	r.rm_direction = CALL;
	r.ru.RM_cmb.cb_rpcvers = 2;
	r.ru.RM_cmb.cb_prog = YPPROG;
	r.ru.RM_cmb.cb_vers = YPVERS;
	r.ru.RM_cmb.cb_proc = YPPROC_MATCH;
	r.ru.RM_cmb.cb_cred.oa_flavor = 0;
	r.ru.RM_cmb.cb_cred.oa_base = 0;
	r.ru.RM_cmb.cb_cred.oa_length = 0;
	r.ru.RM_cmb.cb_verf.oa_flavor = 0;
	r.ru.RM_cmb.cb_verf.oa_base = 0;
	r.ru.RM_cmb.cb_verf.oa_length = 0;

	/* create the header */
	xdr_callmsg(&xdrs,&r);
	len = xdr_getpos(&xdrs);

#ifdef _DEBUG
	printf("After xdr_callhdr.  Len=%d\n", len);
	printbuf(xdr_buffer,len);
#endif

	xdr_ypreq_key(&xdrs,&ypk);
	
	len = xdr_getpos(&xdrs);
#ifdef _DEBUG
	printf("After xdr__ypreq_key.  Len=%d\n", len);
	printbuf(xdr_buffer,len);
#endif
	n = sendto(nis_fd,xdr_buffer,len,0,(struct sockaddr *) &nis_sin,
			sizeof(nis_sin));
	if(len != n) {
		perror("sendto");
		return(-1);
	}
	(*num_retrans)--;
	*timeout = 4;
	return(0);

}


/* send resolve request to 
 * dns server
 *  returns 0 on success -1 on error.
 */
int res_nis_send(void *arg)
{
req_timeout_t *to;
int timeout_sec;
res_request_t *rq = arg;
int rv;


	if(NULL == rq) {
		return(-1);
	}

	aux_debug("res_nis_send\n");

	/* check if nis  table is full 
         * if it's full put request in queue and return
         * else send the request 
         */

	if(NULL == (to = (req_timeout_t *) malloc(sizeof(req_timeout_t)))) {
		return(-1);
	}

	/* initialize num_retrans to 0 */
	/* set max_retrans */
        /* copy request info */
	to->num_retrans = nis_retry;

	/* copy request to time_out structure */
	to->request = rq;  /* copy request */


	/* call the transmitt function */
	rv = res_nis_transmit(rq, &to->num_retrans, &timeout_sec);
	if(rv) {
		return(rv);
	}


	/* install timeout handler */
	aux_set_timeout(timeout_sec,nis_to_handler,to);
	/* should pass data to retrnsmit + old fd to close */

	return(rv);
}



/* receive the reply from the server
 *  fd is the fd to receive reply from
 *  arg is the reply buffer passed from the fd handler
 *  which called this function.
 *  returns 0 on success -1 on error.
 */
int res_nis_recv(int fd, void *arg)
{
	int n,size;
#ifdef  _AIX
	size_t from_len;
#else 
	int from_len;
#endif
	struct sockaddr_in from_addr;
	unsigned char answer[XDR_SZ];
	XDR xdrs;
	struct rpc_msg *rp;
	res_reply_t *rep = arg; 
	res_request_t *rq, *newrq;
	char *p;


	aux_debug("res_nis_recv\n");
	memset(answer,0,sizeof(answer));
	/* receive the answer from the
	 * nis fd
	 */
	n = recvfrom(nis_fd,answer,sizeof(answer),0,
			(struct sockaddr *)&from_addr, &from_len);
	if(n < 0) {
		perror("recvfrom");
		exit(1);
	}
	aux_debug("recv : %d bytes from nis_fd : %d\n",n,nis_fd);

	/* put answer into XDR stream */
	xdrmem_create(&xdrs,(char *)&answer[0],XDR_SZ,XDR_DECODE);


	/* access rpc_msg struct */
	rp = (struct rpc_msg *) &answer[0];
	aux_debug("id = %d\n",rp->rm_xid);

	/* get the request's id from the nis id 
	 * table.
	 * get the request type as well
	 */
	aux_nis_get_req_id(rp->rm_xid,&rep->id,&rep->type,
			&rep->flags, (char **)&rep->q_data);


	aux_debug("id = %d\n",rp->rm_xid);



	/* check status of answer */
	if(!rp->ru.RM_rmb.rp_stat && 
	    rp->acpted_rply.ar_stat == YP_TRUE  ) {
		/* calculate address of answer data */
		p  = (char *) &answer[0] + sizeof(struct accepted_reply) + 8;	
		aux_debug("answer : %s\n",p);

		/* access the data according to the request */
		rep->data = nis_get_data(rep->type,p);
		rep->length = strlen(rep->data);
		rep->ret_code = 0;   /* success */
	} else {
		/* data is unresolved
		 */
		/* get the request */
		rq = aux_get_req(rep->id);
		/* resolver has a fallback option 
		 * unset the timeout 
		 * activate the fallback resolver
		 */
		if( aux_resolve_fallback(rq)) {
			aux_debug("fall back activated for id : %ld\n",
                        rq->id);

			/* duplicate the request and send
			 * through new resolver
			 */
			newrq = aux_dup_request(rq);

			/* unset the request's timeout handler */
			aux_unset_timeout(rep->id);

			if(NULL != newrq) {
				aux_activate_resolver(newrq);
			}
			return(0);
		}
		
		if(rp->ru.RM_rmb.rp_stat) {
			/* rejected */
			rep->ret_code = 2;
		} else {
			/* unresolved */
			rep->ret_code = 1;
			res_nis_error(rp->acpted_rply.ar_stat);
		}

		size = sizeof(struct rejected_reply);	
		rep->data = NULL;
		rep->length = 0;
	}

	/* unset the request's timeout handler */
	aux_unset_timeout(rep->id);

        aux_post_event(AUX_RESOLVED_EVENT,rep);		

	return(0);
}

static void res_nis_error(int error)
{

	switch(error) {
		case  YP_NOMORE: 
			aux_debug("nis error: YP_NOMORE\n");
			break;
		case  YP_FALSE: 
			aux_debug("nis error: YP_FALSE\n");
			break;
		case  YP_NOMAP: 
			aux_debug("nis error: YP_NOMAP\n");
			break;
		case  YP_NODOM: 
			aux_debug("nis error: YP_NODOM\n");
			break;
		case  YP_NOKEY: 
			aux_debug("nis error: YP_NOKEY\n");
			break;
		case  YP_BADOP: 
			aux_debug("nis error: YP_BADOP\n");
			break;
		case  YP_BADDB: 
			aux_debug("nis error: YP_BADDB\n");
			break;
		case  YP_YPERR: 
			aux_debug("nis error: YP_YPERR\n");
			break;
		case  YP_BADARGS: 
			aux_debug("nis error: YP_BADARGS\n");
			break;
		case  YP_VERS: 
			aux_debug("nis error: YP_VERS\n");
			break;
		default:
			aux_debug("nis error: unknown nis error\n");
			break;
	}
}
