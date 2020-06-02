

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


#ifndef __AUX_NIS_RES_H_
#define __AUX_NIS_RES_H_

int res_nis_send(void *arg);

/*  transmit request to dns server
 *   returns timeout and fd 
 */
int res_nis_transmit(res_request_t *rq, int *num_retrans,int *time_out);

int res_nis_recv(int fd, void *arg );

int res_nis_init();
int res_nis_close();

#define AUX_NIS_ID_TBL_SZ   (256)

/* put request id into table 
 * return the table index as the dns_id
 */
int aux_get_nis_id(unsigned long req_id, int req_type, int flags,
		   char *q_data); 

/* get the request id from the dns_id table
 * given the dns_id 
 */
int aux_nis_get_req_id(int dns_id, unsigned long *req_id,
		int *req_type, int *flags, char **q_data);
void aux_nis_tbl_init();
int aux_find_req_id(unsigned long req_id);
void nis_to_handler(void *arg);
void nis_fd_handler(int fd, void *arg);

#endif
