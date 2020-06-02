#ifndef LINT
    static char rcsid[] = "$Header: /p/infra/ccinfra1/bb/tools/RCS/gi/src/gilist.c,v 6.1.1.1 1995/07/04 11:14:53 ccinfra1 Exp $";
#endif


/******************************************************************************
 *
 * Change History
 *-----------------------------------------------------------------------------
 * Revision No.  Date      Time                By
 * Changes/New Features
 *--------------+--------+-------------------+---------------------------------
 * $Log: gilist.c,v $
 * Revision 6.1.1.1  1995/07/04  11:14:53  ccinfra1
 * A new version v04 created from v03
 *
 * Revision 6.1  1995/07/04  11:14:52  ccinfra1
 * Initial major revision
 *
 * Revision 5.1.1.1  1995/02/09  16:46:03  ccinfra1
 * Converted version: v03
 *
 * Revision 5.1  1995/02/09  16:46:02  ccinfra1
 * Initial major revision
 *
 * Revision 2.1  1995/02/05  15:19:52  ccinfra1
 * A new version: v03
 *
 * Revision 2.0  1994/08/11  15:24:58  ccinfra1
 * A new version: v02
 *
 * Revision 1.1  94/07/17  16:04:00  16:04:00  ccinfra1 (Infra grp CC-new accnt)
 * Initial revision
 * 
 * Revision 1.4  94/04/11  11:02:08  11:02:08  yossia (Yoosi Azaria)
 * c[DChange the file na[D[D[D[D[D[D[D[D[D[C[Cfunction name.
 * 
 * Revision 1.3  94/04/10  04:38:45  04:38:45  yossia (Yoosi Azaria)
 * *** empty log message ***
 * 
 * Revision 1.1  94/03/15  01:10:59  01:10:59  yoram (Yoram)
 * Initial revision
 * 
 *
 ******************************************************************************
 */
/********************************************************************\
*                                                                    *
* Purpose: Link list data structure handling                         * 
*                                                                    *
* Supervisor: Tilly Gelman                                           *
*                                                                    * 
* Written by: Dmitry Perel                                           *
*                                                                    *
* Date: December 1992                                                *
*                                                                    *
* Changes History:                                                   *
*                                                                    *
* Date    | By             | Changes/New Features                    *
*---------+----------------+-----------------------------------------*
*12-09-93 | Dmitry P.      |New functions:putAfterLast,linkListList, *
*         |                |getLast. (v06)                           *
*---------+----------------+-----------------------------------------*
*                                                                    *
\********************************************************************/

/* Std library *\
\* ----------- */

#include <stdio.h>
#include <stdlib.h>

/* Include files *\
\* ------------- */

#define EXTERN

/* General memory allocation *\
\* ------------------------- */
#include "giallocg.h"

/* General link list handling *\
\* -------------------------- */
#include "gilistg.h"

#undef EXTERN

/* Functions *\
\* --------- */

/********************************************************************\
* Function: gilist_putAfter                                       *
*                                                                    *
* Purpose: Insert item after current item in the link list.          *
*                                                                    *
* Input Arguments :                                                  *
* i_currentItem_p : pointer to the item we want to insert after it.  *
* i_dataItem_p    : pointer to the data to insert.                   *
*                   It is the coller responsibility to allocate      *
*                   the memory for the data.                         *
* Returns:                                                           *
*       Pointer to the new list item.                                *
*                                                                    *
\********************************************************************/
      
Llist_t *gilist_putAfter( Llist_t *i_currentItem_p,
                                             void *i_dataItem_p) 
{
  Llist_t *newItem_p;

  newItem_p =(Llist_t *) gimalloc_memAlloc(sizeof(Llist_t));

  if (i_currentItem_p == NULL) { /* list creation */
     newItem_p -> next_p = NULL;
  } else {
     newItem_p -> next_p = i_currentItem_p -> next_p;
     i_currentItem_p -> next_p = newItem_p;
  }

  if (newItem_p -> next_p != NULL) {

     newItem_p -> next_p -> prev_p = newItem_p;

  }

  newItem_p -> prev_p = i_currentItem_p;
  newItem_p -> data_p = i_dataItem_p;
  
  return(newItem_p);

}/* gilist_putAfter end */

/********************************************************************\
* Function: gilist_putBefore                                      *
*                                                                    *
* Purpose: Insert item before current item in the link list.         *
*                                                                    *
* Input Arguments :                                                  *
*    urrentItem_p : pointer to the item we want to insert before it  *
*  _dataItem_p    : pointer to the data to insert.                   *
*                   It is the coller responsibility to allocate      *
*                   the memory for the data.                         *
* Returns:                                                           *
*       Pointer to the new list item.                                *
*                                                                    *
\********************************************************************/
      
Llist_t *gilist_putBefore( Llist_t *i_currentItem_p,
                                             void *i_dataItem_p)
{
  Llist_t *newItem_p;

  newItem_p =(Llist_t *) gimalloc_memAlloc(sizeof(Llist_t));

  if (i_currentItem_p == NULL) {/* list creation */
     newItem_p -> prev_p = NULL;
  } else {
     newItem_p -> prev_p = i_currentItem_p -> prev_p;
     i_currentItem_p -> prev_p = newItem_p;
  }

  if (newItem_p -> prev_p != NULL) {

     newItem_p -> prev_p -> next_p = newItem_p;

  }

  newItem_p -> next_p = i_currentItem_p;
  newItem_p -> data_p = i_dataItem_p;

  return(newItem_p);

}/* gilist_putBefore end */

/********************************************************************\
* Function: gilist_getNext                                        *
*                                                                    *
* Purpose: Get next item in the link list.                           *
*                                                                    *
* Input Arguments :                                                  *
* i_currentItem_p : pointer to the item whose next we want to get.   *
* o_dataItem_p    : pointer to the pointer of data to get.           *
* Returns:                                                           *
*       Pointer to the next item in the link list.                   *
*                                                                    *
\********************************************************************/
      
Llist_t *gilist_getNext( Llist_t *i_currentItem_p, 
                                            void **o_dataItem_p)
{


  if (i_currentItem_p != NULL) {/* any error */
     if (i_currentItem_p -> next_p != NULL) {/* last in list */
        *o_dataItem_p = i_currentItem_p -> next_p -> data_p;
        return(i_currentItem_p -> next_p);
     }
  } 

  return(NULL);
  
}/* gilist_getNext end */

/********************************************************************\
* Function: gilist_getPrev                                        *
*                                                                    *
* Purpose: Get previouse item in the link list.                      *
*                                                                    *
* Input Arguments :                                                  *
*       i_currentItem_p : pointer to the item whose previous we want *
*                         to get.                                    *
*       o_dataItem_p    : pointer to the pointer to data to get.     *
* Returns:                                                           *
*       Pointer to the previous item in the link list.               * 
*                                                                    *
\********************************************************************/
      
Llist_t *gilist_getPrev( Llist_t *i_currentItem_p,
                                             void **o_dataItem_p)
{


  if (i_currentItem_p != NULL) {/* any error */
     if (i_currentItem_p -> prev_p != NULL) { /* first in list */
        *o_dataItem_p = i_currentItem_p -> prev_p -> data_p;
        return(i_currentItem_p -> prev_p);
     }
  }
 
  return(NULL);

}/* gilist_getPrev end */

/********************************************************************\
* Function: gilist_getCurr                                        *
*                                                                    *
* Purpose: Get current item in the link list.                        *
*                                                                    *
* Input Arguments :                                                  *
*       i_currentItem_p : pointer to the item we want to get         *
*       o_dataItem_p    : pointer to the pointer of data to get.     *
* Returns:                                                           *
*       Pointer to the current item in the link list.                *
\********************************************************************/
      
Llist_t *gilist_getCurr( Llist_t *i_currentItem_p,
                                             void **o_dataItem_p)
{

  if (i_currentItem_p != NULL) {/* any error */
      *o_dataItem_p = i_currentItem_p -> data_p;
      return(i_currentItem_p);
  }

  return(NULL);

}/* gilist_getCurr end */

/********************************************************************\
* Function: gilist_getCurrForw                                    *
*                                                                    *
* Purpose: Get current item in the link list and return pointer to   *
*          the next item                                             *
*                                                                    *
* Input Arguments :                                                  *
*       i_currentItem_p : pointer to the item we want to get         *
*       o_dataItem_p    : pointer to the pointer of data to get.     *
* Returns:                                                           *
*       Pointer to the next item in the link list.                   *
\********************************************************************/
      
Llist_t *gilist_getCurrForw( Llist_t *i_currentItem_p,
                                          void **o_dataItem_p)
{

  if (i_currentItem_p != NULL) {/* any error */
      *o_dataItem_p = i_currentItem_p -> data_p;
      return(i_currentItem_p -> next_p);
  }

  return(NULL);

}/* gilist_getCurrForw end */

/********************************************************************\
* Function: gilist_getCurrBack                                    *
*                                                                    *
* Purpose: Get current item in the link list and return pointer to   *
*          the previose item                                         *
*                                                                    *
* Input Arguments :                                                  *
*       i_currentItem_p : pointer to the item we want to get         *
*       o_dataItem_p    : pointer to the pointer of data to get.     * 
* Returns:                                                           *
*       Pointer to the previose item in the link list.               *
\********************************************************************/
      
Llist_t *gilist_getCurrBack( Llist_t *i_currentItem_p, 
                                             void **o_dataItem_p)
{

  if (i_currentItem_p != NULL) {/* any error */
      *o_dataItem_p = i_currentItem_p -> data_p;
      return(i_currentItem_p -> prev_p);
  }

  return(NULL);

}/* gilist_getCurrBack end */

/********************************************************************\
* Function: gilist_deleteItem                                     *
*                                                                    *
* Purpose: Delete the item from the Link List.                       *
*                                                                    *
* Input Arguments :                                                  *
*       i_currentItem_p : pointer to the item we want to delete      *
*       o_dataItem_p    : pointer to the pointer of data to delete.  *
*                         It is the coller responsibility to release *
*                         the memory allocated for the data.         *
* Returns:                                                           *
*       Pointer to the next item in the link list.                   *
\********************************************************************/

Llist_t *gilist_deleteItem(Llist_t *i_currentItem_p,
                                           void **o_dataItem_p)
{
  Llist_t *nextItem_p;

  if (i_currentItem_p != NULL) { /* any error */

     if (i_currentItem_p -> prev_p != NULL) {

        i_currentItem_p -> prev_p -> next_p =
                             i_currentItem_p -> next_p;

     }

     if (i_currentItem_p -> next_p != NULL) {

        i_currentItem_p -> next_p -> prev_p =
                               i_currentItem_p -> prev_p;

     }

     nextItem_p = i_currentItem_p -> next_p;

     *o_dataItem_p = i_currentItem_p -> data_p;

     free (i_currentItem_p);

     return (nextItem_p);

  } else {

     return (NULL);

  }
}/* gilist_deleteItem end */


/********************************************************************\
* Function: gilist_getLast                                        *
*                                                                    *
* Purpose: gets the last item in a linked list.                      *
*                                                                    *
* Input Arguments :                                                  *
*       Llist_p   : pointer to the linked list                       *
*                                                                    *
* Returns         : pointer to the last item in the list.            *
*                                                                    *
\********************************************************************/

Llist_t *gilist_getLast(Llist_t *Llist_p)
{
Llist_t *currItem_p;

/* check if Llist_p is not NULL ! */

    if(Llist_p == NULL) {
	return(Llist_p);
    }
           

/* loop until end of list is  reached */
 
    currItem_p = Llist_p; 

    while (currItem_p->next_p != NULL) {
        currItem_p = currItem_p->next_p;
    }

    return(currItem_p);

} /* gilist_getLast end */


/********************************************************************\
* Function: gilist_linkListList                                      *
*                                                                    *
* Purpose: Links to linked lists into one list.                      *
*                                                                    *
* Input Arguments :                                                  *
* firstList_p     : pointer to the first linked list                 *
*                                                                    *
* secondList_p    : pointer to the second linked list.               *
*                                                                    *
* Returns         : pointer to the top of the first list.            *
*                                                                    *
\********************************************************************/

Llist_t *gilist_linkListList(Llist_t *firstList_p , 
                               Llist_t *secondList_p) 
{
Llist_t *listTail_p;

/* find last item in first list */           
    listTail_p = gilist_getLast(firstList_p);
     
    if(listTail_p == NULL) {
	return(secondList_p);
    }

/* point to first item in second list */

    listTail_p->next_p = secondList_p;
    secondList_p->prev_p = listTail_p;
        
    return(firstList_p);

}  /* gilist_linklistlist end */



/********************************************************************\
*                                                                    *
* Function: gilist_putAfterLast.                                   *
*                                                                    *
* Purpose: Inserts an item to the linked list after the last item.   *
*                                                                    *
* Input Arguments :                                                  *
*  Llist_p        : pointer to the linked list.                      *
*                                                                    *
* dataItem_p      : pointer to the data to be inserted.              *
*                                                                    *
* Returns         : pointer to the item that was inserted.           *
*                                                                    *
\********************************************************************/



Llist_t *gilist_putAfterLast(Llist_t *Llist_p , void *dataItem_p)
{
Llist_t *listTail_p;

/* find pointer to last item  */
    listTail_p = gilist_getLast(Llist_p);

/*  insert new item after last item in list */
    listTail_p = gilist_putAfter(listTail_p, dataItem_p);
          
    return(listTail_p);

}
