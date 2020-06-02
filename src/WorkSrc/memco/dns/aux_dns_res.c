
/* ======================================================================= */
/*  Project : SEOS                                                         */
/*  Module  : SEOS Auxilliary Daemon.               Version: 02.10.00      */
/*  File    : aux_dns_res.c                                                */
/*  Purpose : seauxd dns resolver.                                         */
/* ======================================================================= */
/*  By : Oded Nissan                                                       */
/* ======================================================================= */
/*  Copyright :                                                            */
/*     This source file is copyright (c) to MEMCO Software Ltd.            */
/* ======================================================================= */


#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>

#include <arpa/nameser.h>   /* name server header */
#include <resolv.h>

#include "aux_resolve.h"
#include "aux_dns_res.h"

/* global vars */
static int dns_fd = -1;                /* dns fd */
static struct sockaddr_in _dns_sin;     /* global sockaddr structure */
static int 	dns_port;              /* dns port */
static int	dns_retry;             /* dns retries */
static struct in_addr  dns_addr;       /* dns host address */

static int aux_addr2ptr(char *s);

#ifdef _DEBUG
void print_header(HEADER *header);
#endif



/* reverse an in_addr into a string with the 
 * in-addr.arpa suffix.
 * 192.9.200.1 is transformed into  1.200.9.192.in-addr.arpa
 * returns 0 
 */
static int aux_addr2ptr(char *s)
{
char temp[5] , *t;
char old_addr[MAXCDNAME+1];
char *paddr, *cp;
int i;


	strcpy(old_addr,s);
	*s = '\0';
	paddr = old_addr;
	cp = paddr + strlen(paddr) -1;
	
	for(i=0; i < 4; ++i) {

		t = &temp[3];
		*t-- = '\0';
	
		while( *cp != '.' && cp >=  paddr) {
			*t-- = *cp--;
		}

		++t;
		strcat(s,t);
		strcat(s,".");
		cp--;
	}
	strcat(s,"in-addr.arpa");
	return(0);
}
			

/*  get the host name  from a dns resource record 
 *  returns hostname or NULL on error.
 */
char *dns_get_hname(char *msg,char *cp )
{
int n;
char name[MAXDNAME];


	/* resource record */
	n =  dn_expand(msg,cp + MAXCDNAME,cp ,name , sizeof(name));
	if(n < 0) {
		perror("dn_expand");
	}

	cp+=n;
	/* skip type and class */
	cp+= (2 * sizeof(u_short));

	/* skip ttl */
	cp+= sizeof(u_long);
	
	/* skip size */
	cp+= sizeof(short);

	/* get host name into name */
	n =  dn_expand(msg,cp + MAXCDNAME,cp ,name , sizeof(name));
	if(n < 0) {
		return(NULL);
	}

	return(strdup(name));

}

/* send resolve request to 
 * dns server
 *  returns 0 on success -1 on error.
 */
int res_dns_ip_send(void *arg)
{
req_timeout_t *to;
int timeout_sec;
res_request_t *rq = arg;
int rv;


	if(NULL == rq) {
		return(-1);
	}


	aux_debug("aux_dns_ip_send activated\n");
	/* check if dns table is full 
         * if it's full put request in queue and return
         * else send the request 
         */

	if(NULL == (to = (req_timeout_t *) malloc(sizeof(req_timeout_t)))) {
		return(-1);
	}

	/* initialize num_retrans to 0 */
	/* set max_retrans */
        /* copy request info */
	to->num_retrans = dns_retry;

	/* copy request to time_out structure */
	to->request = rq;  /* copy request */


	/* call the transmitt function */
	rv = res_dns_ip_transmit(rq, &to->num_retrans, &timeout_sec);
	if(rv) {
		return(rv);
	}


	/* install timeout handler */
	aux_debug("installing timeout handler for id: %ld\n",
			rq->id);
	aux_set_timeout(timeout_sec,dns_to_handler,to);
	/* should pass data to retrnsmit + old fd to close */

	return(rv);
}

/*  transmit request to dns server
 *  num_retrans is the retransmition counter which is incremented by 
 *  this function.
 *  timeout is the timeout in seconds that should be given to the 
 *  request.
 *  returns 0 on success -1 on error.
 */
int res_dns_ip_transmit(res_request_t *rq, 
		        int *num_retrans, int *timeout )
{
int sd,n;
int len;
struct in_addr in;
char addr[MAXCDNAME+20];
char dns_packet[PACKETSZ * 4];


	strcpy(addr,rq->data);
	aux_addr2ptr(addr);
	
	/* make a dns query, store result in dns_packet */
	_res.id = aux_get_dns_id(rq->id,rq->flags, rq->data);
	len = res_mkquery(QUERY,addr,C_IN,T_PTR,NULL,0,NULL,
                          dns_packet,sizeof(dns_packet));

	if(len < 0) {
		return(-1);
	}


	/* send dns packet to dns server */
        n = sendto(dns_fd,dns_packet,len,0,(struct sockaddr*)&_dns_sin,
                  sizeof(_dns_sin));
	if(len != n) {
		perror("sendto");
		return(-1);
	}

	/* decrement num of transmitions */
	(*num_retrans)--;

	/* return timeout */
	*timeout = dns_retry << (dns_retry - *num_retrans);
	
	aux_debug("tranmittinig dns req : %ld\ttimeout = %d\n",
			rq->id, *timeout);

	return(0);
}


/* receive the reply from the server
 *  fd is the fd to receive reply from
 *  arg is the reply buffer passed from the fd handler
 *  which called this function.
 *  returns 0 on success -1 on error.
 */
int res_dns_ip_recv(int fd, void *arg )
{
char answer[PACKETSZ * 4];
char name[MAXDNAME];
int len, n;
#ifdef _AIX
size_t from_len;
#else 
int from_len;
#endif
HEADER *hp;
struct sockaddr from_addr;
res_reply_t *rep;
res_request_t *rq, *newrq;
char *cp;

	aux_debug("aux_dns_recv activated\n");

	if(arg == NULL) {
		aux_debug("error : arg is null in recv func\n");
	}
	rep = arg;

	memset(rep,0,sizeof(res_reply_t));
	rep->ret_code = -1;

	n =  recvfrom(dns_fd,answer,sizeof(answer),0,
                    (struct sockaddr *) &from_addr,&from_len);
	if(n < 0) {
		perror("recvfrom");
		return(-1);
	}
	aux_debug("recvfrom : %d bytes received\n",n);
	
	hp = (HEADER *)answer;

#ifdef _DEBUG
	print_header(hp);
#endif
	aux_dns_get_req_id(hp->id,&rep->id, &rep->flags, 
			(char **)&rep->q_data);
	rep->ret_code = hp->rcode;

	/* no answer */
	if(!hp->ancount ) { 
		aux_debug("anscount is 0\n");

		/* get the request */
		rq = aux_get_req(rep->id);
		/* resolver has a fallback option 
		 * activate the fallback resolver
		 */
		if(aux_resolve_fallback(rq)) {
			aux_debug("fall back activated for id : %ld\n",
                        rq->id);

			/* duplicate the request and send
			 * through new resolver
			 */
			newrq = aux_dup_request(rq);

			aux_unset_timeout(rep->id);
			if(NULL != newrq) {
				aux_activate_resolver(rq);
			}
		} else {
			/* return unresolved event */

			rep->data = NULL;
			rep->length = 0;
			rep->ret_code = 1;   /* error */
			aux_post_event(AUX_RESOLVED_EVENT, arg);
			aux_debug("returning unresolved event for %d\n",
					rep->id);
		}
		return(1);
	}

	cp = answer + sizeof(HEADER);
	/* skip query */
	while(*cp != '\0') {
		++cp;
	}
	++cp;
	/* skip query type  and class */
	cp += 2 * sizeof(u_short);
	
	/* get the returned host name */
	rep->data = dns_get_hname(answer,cp);
	rep->length = (rep->data != NULL ? strlen(rep->data) : 0);
	rep->ret_code = 0;
	rep->type = REQTYPE_HOST;


	/* post the reply event */
	aux_post_event(AUX_RESOLVED_EVENT, arg);


	/* unset the request's timeout handler */
	aux_debug("unsetting timout for : %ld\n",rep->id);
	aux_unset_timeout(rep->id);


	/* if request queue isn't empty
         * get request from  queue
         * send it to resolver
         */


	return(0);
}


/* init the dns resolver 
 * get prot and address of resolver from ini file
 * connect to resolver
 * install the dns fd_handler 
 */
int res_dns_init()
{
struct hostent *hp;
struct servent *sp;
res_reply_t *rp;
char buf[100];

	/* get dns host address from ini file */
	if(!aux_ini_get_str("dns","dns_host","",buf,sizeof(buf))) {
		/* if no host defined fail */
		return(-1);
	}
	aux_debug("dns host at : %s\n",buf);
	dns_addr.s_addr  = inet_addr(buf);

	/* get dns port number from ini file */
	aux_ini_get_str("dns","dns_port","53",buf,sizeof(buf));
	dns_port = atoi(buf);

	/* get number of retries from ini file */
	aux_ini_get_str("dns","num_retries","3",buf,sizeof(buf));
	dns_retry = atoi(buf);

        memset(&_dns_sin,0,sizeof(_dns_sin));
        _dns_sin.sin_family = AF_INET;
        _dns_sin.sin_addr.s_addr = dns_addr.s_addr;
        _dns_sin.sin_port = dns_port;

        if(-1 == (dns_fd = socket(AF_INET,SOCK_DGRAM,0))) {
                perror("socket");
                return(-1);
        }
	/* init the dns table */
	aux_dns_tbl_init();

	/* install fd handler 
	 * the dns fd_handler should be installed just once.
         * at initialization time.
         * it will be unset when the daemon is terminated by
         * the library itself.
         */
	aux_debug("dns fd = %d\n",dns_fd);
        aux_set_fd_handler(dns_fd,dns_fd_handler,NULL);
	return(0);
}


/* close the dns fd
 * unset the dns_fd_handler 
 */
int res_dns_close()
{

	close(dns_fd);
	aux_unset_fd_handler(dns_fd);
	return(0);
}

/* dns fd handler
 * called when fd has data.
 * calls res_dns_recv
 */
void dns_fd_handler(int fd, void *arg)
{

	/* allocate space for reply buffer for fd handler */
	arg = (res_reply_t *)malloc(sizeof(res_reply_t));
	if(NULL == arg) {
		return;
	}
	/* get reply from dns server */
	res_dns_ip_recv(fd,arg);


}

/* dns timeout handler
 * called when timeout has expired
 * calls res_dns_transmit to retransmit the message
 */
void dns_to_handler(void *arg)
{
int timeout;
req_timeout_t *r = (req_timeout_t *)arg;
res_reply_t *rp;

	/* if more retransmitions required retranmit request */
	if(r->num_retrans) {

		aux_debug("retransmitting request for id : %ld\n",r->request->id);
		res_dns_ip_transmit(r->request,&r->num_retrans,&timeout);
		aux_set_timeout(timeout,dns_to_handler,arg);

	} else {
        
		/* no more retranmitions required  */

		/* if fallback available call resolver 
                     fallback option */
		if(aux_resolve_fallback(r->request)) {
			aux_debug("fall back activated for id : %ld\n",
                        r->request->id);
			aux_activate_resolver(r->request);
		} else {	
		   /* return unresolved request */
			aux_debug("unresolved event returned for id : %ld\n",
                                r->request->id);

			rp = (res_reply_t *) malloc(sizeof(res_reply_t));
			if(NULL == rp) {
				return;
			}
			rp->id = r->request->id;
			rp->data = r->request->data;
			rp->ret_code = 1;
			rp->data = NULL;
			aux_post_event(AUX_RESOLVED_EVENT,rp);
		}
	}
	

}


/* print dns reply header
 *
 */
#ifdef _DEBUG
void print_header(HEADER *header)
{

	aux_debug("HEADER size = %d\n",sizeof(HEADER));

	aux_debug("id = %d\n",header->id);
	/*
	printf("qr = %d\n",header->qr);
	printf("opcode = %d\n",header->opcode);
	printf("rd = %d\n",header->rd);
	printf("pr = %d\n",header->pr);

	printf("ra = %d\n",header->ra);
	printf("rcode = %d\n",header->rcode);
	printf("qdcount  = %d\n",header->qdcount);
	printf("ancount  = %d\n",ntohs(header->ancount));
	printf("nscount  = %d\n",header->nscount);
	printf("arcount  = %d\n",header->arcount);
	*/
}

#endif

