/*------------------------------------------------------------------------*/
/*   Module       :                                                       */
/*   File         :   queue.c                                             */
/*   Date         :   01/01/1996                                          */
/*   Description  :   generic queue functions.                            */
/*   Author       :   Oded Nissan                                         */
/*                                                                        */
/*   History      :                                                       */
/*------------------------------------------------------------------------*/
/*   Date         |   Description                                         */
/*------------------------------------------------------------------------*/
/*   01/01/1996   |   Initial Release.                                    */
/*                |                                                       */
/*                |                                                       */
/*------------------------------------------------------------------------*/
#include <stdio.h>
#include <stdio.h>
#include <stdlib.h>

#include "dstructs.h"


QUEUE *queue_create()
{
QUEUE *pQueue;

	pQueue = (QUEUE *) malloc(sizeof(QUEUE));
	pQueue->pFront = NULL;
	pQueue->pRear = NULL;
	pQueue->numItems = 0;

	return(pQueue);
}

int queue_add(QUEUE *pQueue , void *pData)
{
	pQueue->pRear = llist_insert_after(pQueue->pRear , pData);
	if(pQueue->pFront == NULL) {
		pQueue->pFront = pQueue->pRear;
	}
	pQueue->numItems++;

	return(pQueue->pRear != NULL ? 1 : 0);
}

int queue_remove_front(QUEUE *pQueue , void **pData)
{
LLIST *p;
int stat;

	if(queue_empty(pQueue)) {
		stat = 0;
	} else {
		*pData = pQueue->pFront->data;
		p = pQueue->pFront;
		pQueue->pFront = pQueue->pFront->next;
		llist_delete_item(p,pData);
		pQueue->numItems--;
		stat = 1;
	}
	return(stat);
}
int queue_remove_back(QUEUE *pQueue , void **pData)
{
LLIST *p;
int stat;

	if(queue_empty(pQueue)) {
		stat = 0;
	} else {
		*pData = pQueue->pRear->data;
		p = pQueue->pRear;
		pQueue->pRear = pQueue->pRear->prev;
		llist_delete_item(p,pData);
		pQueue->numItems--;
		stat = 1;
	}
	return(stat);
}

int queue_empty(QUEUE *pQueue)
{

	return(pQueue->pFront == NULL ? 1 : 0);
}

int queue_items(QUEUE *pQueue)
{
	return(pQueue->numItems);
}
