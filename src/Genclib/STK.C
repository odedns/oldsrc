/*------------------------------------------------------------------------*/
/*   Module       :                                                       */
/*   File         :   stk.c                                               */
/*   Date         :   01/01/1996                                          */
/*   Description  :   stack library functions.                            */
/*   Author       :   Oded Nissan.                                        */
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
#include <stdlib.h>

#include "dstructs.h"


STACK *stack_create()
{
STACK *pStack;

	pStack = (STACK *) malloc(sizeof(STACK));
	pStack->pCurrent = NULL;
	pStack->numItems = 0;

	return(pStack);
}

int stack_push(STACK *pStack , void *pData)
{
	pStack->pCurrent = llist_insert_after(pStack->pCurrent , pData);
	pStack->numItems++;

	return(pStack->pCurrent != NULL ? 1 : 0);
}

int stack_pop(STACK *pStack , void **pData)
{
LLIST *p;
int stat;

	if(stack_empty(pStack)) {
		stat = 0;
	} else {
		*pData = pStack->pCurrent->data;
		p = pStack->pCurrent;
		pStack->pCurrent = pStack->pCurrent->prev;
		llist_delete_item(p,pData);
		pStack->numItems--;
		stat = 1;
	}
	return(stat);
}

int stack_empty(STACK *pStack)
{

	return(pStack->pCurrent == NULL ? 1 : 0);
}

int stack_items(STACK *pStack)
{
	return(pStack->numItems);
}
