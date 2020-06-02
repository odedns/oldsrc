

/* ======================================================================= */
/*  Project : SEOS                                                         */
/*  Module  : SEOS Auxilliary Daemon.               Version: 02.10.00      */
/*  File    :                                                              */
/*  Purpose :                                                              */
/* ======================================================================= */
/*  By : Oded Nissan                                                       */
/* ======================================================================= */
/*  Copyright :                                                            */
/*     This source file is copyright (c) to MEMCO Software Ltd.            */
/* ======================================================================= */


#ifndef __AUX_DNS_RES_H_
#define __AUX_DNS_RES_H_

int res_dns_ip_send(void *arg);

/*  transmit request to dns server
 *   returns timeout and fd 
 */
int res_dns_ip_transmit(res_request_t *rq, int *num_retrans,int *time_out);

int res_dns_ip_recv(int fd, void *arg );

int res_dns_init();
int res_dns_close();

#define AUX_DNS_ID_TBL_SZ   (256)

/* put request id into table 
 * return the table index as the dns_id
 */
int aux_get_dns_id(unsigned long req_id, int flags, char *q_data); 

/* get the request id from the dns_id table
 * given the dns_id 
 */
int aux_dns_get_req_id(int dns_id, unsigned long *req_id, int *flags,
		       char **q_data);
void aux_dns_tbl_init();
int aux_find_nis_req_id(unsigned long req_id);
void dns_to_handler(void *arg);
void dns_fd_handler(int fd, void *arg);

#endif
