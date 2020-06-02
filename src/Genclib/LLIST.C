/*------------------------------------------------------------------------*/
/*   Module       :                                                       */
/*   File         :   llist.c                                             */
/*   Date         :   10/09/1993                                          */
/*   Description  :   liked list library functions.                       */
/*   Author       :   Oded Nissan                                         */
/*                                                                        */
/*   History      :                                                       */
/*------------------------------------------------------------------------*/
/*   Date         |   Description                                         */
/*------------------------------------------------------------------------*/
/*   10/09/1993   |   Initial Release.                                    */
/*                |                                                       */
/*                |                                                       */
/*------------------------------------------------------------------------*/

#include <stdio.h>
#include <stdlib.h>

#include "dstructs.h"

/***************************************************************************\
*  Linked list library functions !                                         *
*  date written 10/9/93                                                    *
\**************************************************************************/




/***************************************************************************\
*  Function : llist_insert_after.                                                 *
*             Inserts a new item in the list.                               *
*  Parameters :                                                             *
*  *after     : pointer to item to insert after.                            *
*  *data      : pointer to data to be inserted.                             *
*                                                                           *
* Returns     : pointer to the new item                                     *
*                                                                           *
\***************************************************************************/

    LLIST *llist_insert_after(LLIST *after , void *data)
    {
    LLIST *new;
    new = (LLIST *) malloc(sizeof(LLIST));
    new->data = data;

    if(after == NULL) {
	new->next = NULL;
	new->prev = NULL;

    } else {
	    new->next = after->next;
	    after->next = new;
	    new->prev = after;
   }

    if(new->next != NULL)
	new->next->prev = new;

    return(new);

    }

/***************************************************************************\
*  Function : llist_insert_before.                                                *
*             Inserts a new item in the list.                               *
*  Parameters :                                                             *
*  *before    : pointer to item to insert before.                           *
*  *data      : pointer to data to be inserted.                             *
*                                                                           *
* Returns     : pointer to the new item                                     *
*                                                                           *
\***************************************************************************/



    LLIST *llist_insert_before(LLIST *before , void *data)
    {

    LLIST *new;

    new = (LLIST *) malloc(sizeof(LLIST));

    new->data = data;

    if(before == NULL) {
	new->next = NULL;
	new->prev = NULL;

    } else {
	    new->next = before;
	    new->prev = before->prev;
	    before->prev = new;
	   }

    if (new->prev != NULL)
	new->prev->next = new;

    return(new);

    }


/***************************************************************************\
*  Function : llist_get_next.                                                     *
*             returns a pointer to the next item in the list.               *
*  Parameters :                                                             *
*  *llist_p   : pointer to the linked list.     .                           *
*                                                                           *
*  Returns    : pointer to the next item.                                   *
*                                                                           *
\***************************************************************************/

LLIST *llist_get_next(LLIST *Llist_p , void **data)
{
     *data = NULL;

    if(Llist_p != NULL) {
		*data = Llist_p->data;
    }
    return(Llist_p->next);
}


/*************************************************************************\
*  Function : llist_get_prev. 						  *				*
*		  returns a pointer to the previous item in the list.	  *
*  Parameters :                                                           *
*  *llist_p   : pointer to the linked list. 				  *			*
*                                                                         *
*  Returns	  : pointer to the previous item.			  *					*
*                                                                         *
\*************************************************************************/
LLIST *llist_get_prev(LLIST *Llist_p , void **data)
{
    *data = NULL;

    if(Llist_p != NULL)
	*data = Llist_p->data;


    return(Llist_p->prev);

}

LLIST *llist_get_curr(LLIST *pList , void **data)
{
	*data = NULL;

	if(NULL != pList) {
		*data = pList->data;
	}
	return(pList);
}

LLIST *llist_get_currback(LLIST *pList , void **data)
{
	if(NULL != pList) {
		*data = pList->data;
	}
	return(pList->prev);
}

LLIST *llist_get_currforw(LLIST *pList , void **data)
{
	if(NULL != pList) {
		*data = pList->data;
	}
	return(pList->next);
}
/**************************************************************************\
*  Function   : llist_delete_item.                                               *
*               delete an item from the list & frees the item & it's data. *
*  Parameters :                                                            *
*  *delete    : pointer to the item to be deleted.                         *
*                                                                          *
*  Returns    : pointer to the next item.                                  *
*                                                                          *
\**************************************************************************/

LLIST  *llist_delete_item(LLIST *delete,void **data)
{
LLIST *p;

    *data = NULL;

    if(delete == NULL)
	return(NULL);

    if(delete->prev != NULL)
	delete->prev->next = delete->next;

	if(delete->next != NULL)
		delete->next->prev = delete->prev;
	*data = delete->data;
	p = delete->next;
	free(delete);

	return(p);
}



/**************************************************************************\
*  Function   : llist_get_first.					           *							*
*			returns a pointer to the first item in the list.   *
*  Parameters :                                                            *
*  *llist_p   : pointer to the linked list. 				   *				*
*                                                                          *
*  Returns	  : pointer to the first item.				   *					*
*                                                                          *
\**************************************************************************/

LLIST *llist_get_first(LLIST *Llist_p)
{
    LLIST *p;

    if(Llist_p == NULL)
       return(NULL);

    for(p = Llist_p; p->prev != NULL; p = p->prev)
    ;

    return(p);
}



/***************************************************************************\
*  Function   : llist_get_last.													*
*				returns a pointer to the last item in the list. 			*
*  Parameters :                                                             *
*  *llist_p   : pointer to the linked list. 								*
*                                                                           *
*  Returns	  : pointer to the last item.									*
*                                                                           *
\***************************************************************************/


LLIST *llist_get_last(LLIST *Llist_p)
{
    LLIST *p;

    if(Llist_p == NULL)
	return(NULL);

    for(p = Llist_p; p->next != NULL; p = p->next)
    ;

    return(p);
}

