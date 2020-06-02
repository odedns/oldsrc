
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

#include <stdio.h>

#include <string.h>

#define AUX_DNS_ID_TBL_SZ   (256)

typedef struct {
	int   	      flags;
	unsigned long req_id;
	unsigned char seq;
	char *q_data;
} dns_tbl_t;

static dns_tbl_t dns_tbl[AUX_DNS_ID_TBL_SZ];

/* init the dns table */
void aux_dns_tbl_init()
{
int i;

	for(i=0; i < AUX_DNS_ID_TBL_SZ; ++i) {
		dns_tbl[i].req_id = 0;
		dns_tbl[i].seq = 0;
	}
}


/* find an entry for req_id in the
 * dns table. 
 * return the computed dns id or 0 if not found
 */
int aux_find_req_id(unsigned long req_id)
{
int rv = 0;
int i;

	for(i=0; i < AUX_DNS_ID_TBL_SZ; ++i) {
		if(req_id == dns_tbl[i].req_id) {
			rv = 1;
			break;
		}
	}
	/* if found calc dns_id */
	if(rv) {
		rv = dns_tbl[i].seq << 8 | i; 
	}
	return(rv);
}


/* put request id into table 
 * return the table index as the dns_id
 */
int aux_get_dns_id(unsigned long req_id,int flags, char *q_data) 
{
int i, empty_slot = -1;
int dns_id;


	/* try to find an entry for the req id 
	 * if found return the computed dns_id
	 */
        dns_id = aux_find_req_id(req_id);
	if(dns_id) {
		return(dns_id);
	}

	for(i=0; i < AUX_DNS_ID_TBL_SZ; ++i) {
		if(!dns_tbl[i].req_id) {
			empty_slot =i;
			break;
		}
	}
	if(empty_slot >= 0 ) {
		dns_tbl[empty_slot].req_id = req_id;
		dns_tbl[empty_slot].seq++;
		dns_tbl[empty_slot].flags = flags;
		dns_tbl[empty_slot].q_data = strdup(q_data);
	}

	dns_id |= dns_tbl[empty_slot].seq << 8 | empty_slot;
	aux_debug("dns id = %d\n",dns_id);
	return(dns_id);
		
}

/* get the request id from the dns_id table
 * given the dns_id 
 */
int aux_dns_get_req_id(int dns_id, unsigned long *req_id, int *flags, 
		char **q_data)
{
int index;

	

	/* subtract 1 from dns_id since it was incremented by res_mkquery */
	--dns_id;
	/* calculate the index */
	index = dns_id & 0x00ff;
	
	*req_id = dns_tbl[index].req_id;
	*flags = dns_tbl[index].flags;
	*q_data = dns_tbl[index].q_data;

	/* zero the dns table entry */
	dns_tbl[index].seq  = 0;
	dns_tbl[index].req_id = 0;
	return(0);
}
