/*------------------------------------------------------------------------*/
/*   Module       :                                                       */
/*   File         :  hash.c                                               */
/*   Date         :  10/06/1996                                           */
/*   Description  :  generic hash functions.                              */
/*   Author       :  Oded Nissan                                          */
/*                                                                        */
/*   History      :                                                       */
/*------------------------------------------------------------------------*/
/*   Date         |   Description                                         */
/*------------------------------------------------------------------------*/
/*   10/06/1996   |   Initial Release.                                    */
/*                |                                                       */
/*                |                                                       */
/*------------------------------------------------------------------------*/
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#include "dstructs.h"

static long hash_str_code(char *s);
static int hash_func(HASHTBL *pTbl , int key);

HASHTBL *hash_tbl_create(int size)
{
HASHTBL *pHash;
int i;

	pHash = (HASHTBL *)malloc(sizeof(HASHTBL));
	pHash->TblSize = size;
	pHash->pTable = (HASH_ENTRY **) malloc(sizeof(HASH_ENTRY *) * size);
	
	for(i = 0;  i < size; ++i) {
		pHash->pTable[i] = NULL;
	}

	return(pHash);
}	

int hash_tbl_delete(HASHTBL *pTbl , void (*DataFree )(void *))
{
int i;
HASH_ITEM *pItem;
LLIST *pList , *pTemp;

	for(i=0; i < pTbl->TblSize; ++i) {
		if(pTbl->pTable[i] != NULL) {
			pList = pTbl->pTable[i]->pEntry;
			while(NULL != pList ) {
				pTemp = pList; 
				pList = pList->prev;
				llist_delete_item(pTemp,(void **)&pItem);
				(*DataFree)(pItem->pData);
				
			} 
		}
	}

	free(pTbl->pTable);
	free(pTbl);
	return(0);
}
				


static int hash_func(HASHTBL *pTbl , int key)
{
	return((unsigned) key % pTbl->TblSize);
}


int hash_tbl_insert(HASHTBL *pTbl , char *key , void *data)
{
int index;
HASH_ITEM *pItem;

	pItem = (HASH_ITEM *)malloc(sizeof(HASH_ITEM));

	pItem->ItemKey = key;
	pItem->pData = data;

	index = hash_func(pTbl,hash_str_code(key));
	if(NULL == pTbl->pTable[index]) {
		pTbl->pTable[index] = (HASH_ENTRY *) malloc(sizeof(HASH_ENTRY));
		pTbl->pTable[index]->pEntry = NULL;
	}
	pTbl->pTable[index]->pEntry = 
		llist_insert_after(pTbl->pTable[index]->pEntry,pItem);
	return(index);
}
	
int hash_tbl_get(HASHTBL *pTbl, char *key , void **data)
{
LLIST *pList;
HASH_ITEM *pItem;
int index , stat = 1;

	index = hash_func(pTbl,hash_str_code(key));
	if(NULL == pTbl->pTable[index]) {
		return(-1);
	}

	pList = pTbl->pTable[index]->pEntry;
	while(pList != NULL) {
		pList = llist_get_prev(pList , (void **) &pItem);
		if( !strcmp(key, pItem->ItemKey)) {
			*data = pItem->pData;
			stat = 0;
			break;
		}
	}
	return(stat);
}

void *hash_tbl_iterate(HASHTBL *pTbl, int verb)
{
	static int i = 0;
	static LLIST *pList = NULL;
	HASH_ITEM *pItem;
	void *p = NULL;

	if(iter_begin == verb) {
		i = 0;
		while(i < pTbl->TblSize && NULL == pTbl->pTable[i]) {
			++i;
		}
		printf("getting from i = %d\n",i);
		pList = pTbl->pTable[i]->pEntry;
		return(NULL);
	} else {
		printf("calling next\n");
		if(NULL != pList) {
			pList = llist_get_prev(pList , (void **) &pItem);
			p = pItem->pData;
			printf("got %s\n",p);
		} else {
			++i;
			while(i < pTbl->TblSize && NULL == pTbl->pTable[i] ) {
				++i;
			}
			printf("getting from i = %d\n",i);
			if(i < pTbl->TblSize) {
				pList = pTbl->pTable[i]->pEntry;
				if(NULL != pList) {
					pList =
					llist_get_prev(pList,(void **) &pItem);
					p = pItem->pData;
					printf("got %s\n",p);
				}
			}

		}
		
	}

	return(p);
}


void DumpTbl(HASHTBL *pTbl)
{
int i;
LLIST *pList;
HASH_ITEM *pItem;

	for(i=0; i < pTbl->TblSize; ++i) {
		if(NULL != pTbl->pTable[i]) {
			pList = pTbl->pTable[i]->pEntry;
			while(pList != NULL) {
				pList = llist_get_prev(pList , (void **) &pItem);
				printf("index = %d\tkey = %s\ts= %s\n",
                                        i,pItem->ItemKey,(char *)pItem->pData);
		
			}
		}
	}
}


static long hash_str_code(char *s)
{
long hash_value = 0;
int  len,i,skip;

	len = strlen(s);

	if(len < 16) {
	/* sample everything */
		while(*s != '\0') { 
			hash_value = hash_value * 37 + *s;
			++s;
		}

	} else {
		skip = len / 8;
		for(i = len; i > 0;  i-= skip) {
			s += skip;
			hash_value = hash_value * 39 + *s;
		}

	}
	return(hash_value);
}

