/*------------------------------------------------------------------------*/
/*   Module       :  dstructs.h                                           */
/*   File         :  dstructs.h                                           */
/*   Date         :  01-01-1998                                           */
/*   Description  :  Header for data structures.                          */
/*   Author       :  Oded Nissan                                          */
/*                   Copyright (c) 1994-1998 Oded Nissan.                 */
/*   History      :                                                       */
/*------------------------------------------------------------------------*/
/*   Date         |   Description                                         */
/*------------------------------------------------------------------------*/
/*   01/01/1998   |   Initial Release.                                    */
/*                |                                                       */
/*                |                                                       */
/*------------------------------------------------------------------------*/
#ifndef _DSTRUCT_H
#define _DSTRUCT_H

#ifdef __cplusplus
extern "C" {
#endif
/**************************************************************************\
*  llists.h -- Include file for linked lists.                              *
*  contains : typedef definitions.                                         *
*	      function definitions.                                        *
\**************************************************************************/

/*---------------------------------------
   Linked list Data struct
----------------------------------------*/

    typedef struct LL {
		       void *data;
		       struct LL *next;
		       struct LL *prev;
		      }LLIST;

/*---------------------------------------
   Queue Data struct
----------------------------------------*/
typedef struct { 
		LLIST *pFront;
		LLIST *pRear;
		int    numItems;
		} QUEUE;
/*---------------------------------------
   Stack Data struct
----------------------------------------*/

typedef struct { 
		LLIST *pCurrent;
		int    numItems;
		} STACK;

/*---------------------------------------
   Hash  Data struct
----------------------------------------*/

typedef struct {
	char *ItemKey;
	void *pData;
	} HASH_ITEM;

typedef struct {
	LLIST *pEntry;
	} HASH_ENTRY; 

typedef struct {
	int  TblSize;
	HASH_ENTRY **pTable;
	} HASHTBL;

/*----------------------------
	Function Protos
------------------------------*/

LLIST *llist_insert_after(LLIST * , void *);
LLIST *llist_insert_before(LLIST * , void *);
LLIST *llist_get_next(LLIST * , void **data);
LLIST *llist_get_prev(LLIST * , void **data);
LLIST *llist_get_curr(LLIST * , void **data);
LLIST *llist_delete_item(LLIST *,void **data);
LLIST *llist_get_first(LLIST *);
LLIST *llist_get_last(LLIST *);
LLIST *llist_get_currback(LLIST *pList , void **data);
LLIST *llist_get_currforw(LLIST *pList , void **data);


/*
 * queue library functions.
 */
QUEUE *queue_create();
int queue_add(QUEUE *pQueue , void *pData);
int queue_remove_front(QUEUE *pQueue , void **pData);
int queue_remove_back(QUEUE *pQueue , void **pData);
int queue_empty(QUEUE *pQueue);
int queue_items(QUEUE *pQueue);


/*
 * stack library functions.
 */
STACK *stack_create();
int stack_push(STACK *pStack , void *pData);
int stack_pop(STACK *pStack , void **pData);
int stack_empty(STACK *pStack);
int stack_items(STACK *pStack);
/*
 * hashtable functions.
 */
enum { iter_begin, iter_next };

#define ITER_BEGIN (1)
#define ITER_NEXT  (2)

HASHTBL *hash_tbl_create(int size);
int hash_tbl_delete(HASHTBL *pTbl , void (*DataFree )(void *));
int hash_tbl_insert(HASHTBL *pTbl , char *key , void *data);
int hash_tbl_get(HASHTBL *pTbl, char *key , void **data);
void *hash_tbl_iterate(HASHTBL *pTbl, int verb);




#define VEC_DEF_SIZE (256)

typedef struct {
	void **v;
	int nelem;
	int size;
	int threshold;
}VECTOR;


int vector_size(VECTOR *vec);
VECTOR *vector_create(int size);
void vector_add(VECTOR *vec, void *object);
void *vector_remove(VECTOR *vec);
void *vector_remove_at(VECTOR *vec, int index);
void *vector_get_at(VECTOR *vec, int index);
void vector_set_at(VECTOR *vec, void *object, int index);

#ifdef _cplusplus
}
#endif

#endif
