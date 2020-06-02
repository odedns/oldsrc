
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

#define AUX_NIS_ID_TBL_SZ   (256)

typedef struct {
	unsigned long req_id;
	int seq;
	int req_type;
	int flags;
	char *q_data;
} nis_tbl_t;

static nis_tbl_t nis_tbl[AUX_NIS_ID_TBL_SZ];

/* init the dns table */
void aux_nis_tbl_init()
{
int i;

	for(i=0; i < AUX_NIS_ID_TBL_SZ; ++i) {
		nis_tbl[i].req_id = 0;
		nis_tbl[i].req_type = 0;
		nis_tbl[i].seq= 0;
	}
}


/* find an entry for req_id in the
 * nis table. 
 * return the computed dns id or 0 if not found
 */
int aux_find_nis_req_id(unsigned long req_id)
{
int rv = 0;
int i;

	for(i=0; i < AUX_NIS_ID_TBL_SZ; ++i) {
		if(req_id == nis_tbl[i].req_id) {
			rv = 1;
			break;
		}
	}
	/* if found calc nis_id */
	if(rv) {
		rv = nis_tbl[i].seq << 8 | i; 
	}
	return(rv);
}


/* put request id into table 
 * return the table index as the nis_id
 */
int aux_get_nis_id(unsigned long req_id, int req_type, int flags,
		   char *q_data) 
{
int i, empty_slot = -1;
int nis_id;


	/* try to find an entry for the req id 
	 * if found return the computed nis_id
	 */
        nis_id = aux_find_nis_req_id(req_id);
	if(nis_id) {
		return(nis_id);
	}

	for(i=0; i < AUX_NIS_ID_TBL_SZ; ++i) {
		if(!nis_tbl[i].req_id) {
			empty_slot =i;
			break;
		}
	}
	if(empty_slot >= 0 ) {
		nis_tbl[empty_slot].req_id = req_id;
		nis_tbl[empty_slot].seq++;
		nis_tbl[empty_slot].req_type = req_type;
		nis_tbl[empty_slot].flags = flags;
		nis_tbl[empty_slot].q_data = strdup(q_data);
	}

	nis_id |= nis_tbl[empty_slot].seq << 8 | empty_slot;
#ifdef _DEBUG
	printf("nis id = %d\n",nis_id);
#endif 
	return(nis_id);
		
}

/* get the request id from the nis_id table
 * given the nis_id 
 */
int aux_nis_get_req_id(int nis_id, unsigned long *req_id, 
		int *req_type, int *flags, char **q_data)
{
int index;

	

	/* calculate the index */
	index = nis_id & 0x00ff;
	
	*req_id = nis_tbl[index].req_id;
	*req_type = nis_tbl[index].req_type;
	*flags  = nis_tbl[index].flags;
	*q_data = nis_tbl[index].q_data;

	/* zero the dns table entry */
	nis_tbl[index].seq  = 0;
	nis_tbl[index].req_id = 0;
	nis_tbl[index].req_type = 0;
	nis_tbl[index].flags = 0;
	nis_tbl[index].q_data = NULL;
	return(0);
}
