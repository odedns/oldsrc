/* $Header: /p/infra/ccinfra1/bb/tools/RCS/pub/gilistg.h,v 6.1.1.1 1995/07/04 11:04:48 ccinfra1 Exp $ */
/********************************************************************\
* Name        : glistg.h                                             *
*                                                                    *
* Purpose     : This header contains the declerations of general use *
*               linked list routines.                                *
*                                                                    * 
* Supervisor  : Dmitry Perl                                          *
* Written by  : Ran Avital                                           *
* date        : Feb 93                                               *
*                                                                    *
* Changes History :                                                  *
* Date        |  By          |  Changes/New Feature                  *
*--------------------------------------------------------------------*
* 9/93        | Oded Nissan  |linkListList, putAfterLast, getLast.   *
\********************************************************************/

# ifndef _gilistg_h
# define _gilistg_h

/* Type definition *\
\* --------------- */

typedef struct LlistStruct 
{
  void *data_p;
  struct LlistStruct *next_p;
  struct LlistStruct *prev_p;
} Llist_t;

/********************************************************************\ 

               / \                 / \                / \ 
 Llist_p        |                   |                  |
     |          |                   |                  |
     |    ________________    _______________   ________________
     +-> |prev|data|next|-->|prev|data|next|-->|prev|data|next|-->NULL
          ----------------   ----------------   ----------------
              |          / \     |          / \     |
              |           |      |           |      |
   NULL<------+           +------+           +------+
         
\********************************************************************/ 

/* Functions definition *\
\* -------------------- */

Llist_t *gilist_putAfter(Llist_t *i_currentItem_p, 
                                             void *i_dataItem_p);
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
      
Llist_t *gilist_putBefore(Llist_t *i_currentItem_p,
                                              void *i_dataItem_p);
/********************************************************************\
* Function: gilist_putBefore                                      *
*                                                                    * 
* Purpose: Insert item before current item in the link list.         *
*                                                                    *
* Input Arguments :                                                  *
* i_currentItem_p : pointer to the item we want to insert before it  *
* i_dataItem_p    : pointer to the data to insert.                   *
*                   It is the coller responsibility to allocate      *
*                   the memory for the data.                         *
* Returns:                                                           *
*       Pointer to the new list item.                                *
*                                                                    *
\********************************************************************/

Llist_t *gilist_getNext(Llist_t *i_currentItem_p,
                                         void **o_dataItem_p);
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
      
Llist_t *gilist_getPrev(Llist_t *i_currentItem_p,
                                           void **o_dataItem_p);
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
      
Llist_t *gilist_getCurr(Llist_t *i_currentItem_p,
                                             void **o_dataItem_p);
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

Llist_t *gilist_getCurrForw(Llist_t *i_currentItem_p,
                                             void **o_dataItem_p);
/********************************************************************\
* Function: gilist_getCurrForw                                    *
*                                                                    *
* Purpose: Get current item in the link list ann return pointer      *
*          to next item                                              *
*                                                                    *
* Input Arguments :                                                  *
*       i_currentItem_p : pointer to the item we want to get         *
*       o_dataItem_p    : pointer to the pointer of data to get.     *
* Returns:                                                           *
*       Pointer to the next item in the link list.                   *
\********************************************************************/

Llist_t *gilist_getCurrBack(Llist_t *i_currentItem_p,
                                             void **o_dataItem_p);
/********************************************************************\
* Function: gilist_getCurrBack                                    *
*                                                                    *
* Purpose: Get current item in the link list ann return pointer      *
*          to previose item                                          *
*                                                                    *
* Input Arguments :                                                  *
*       i_currentItem_p : pointer to the item we want to get         *
*       o_dataItem_p    : pointer to the pointer of data to get.     * 
* Returns:                                                           *
*       Pointer to the previose item in the link list.               *
\********************************************************************/

Llist_t *gilist_deleteItem(Llist_t *i_currentItem_p,
                                               void **o_dataItem);
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


Llist_t *gilist_getLast(Llist_t *Llist_p);

/********************************************************************\
* Function: gilist_getLast                                        *
*                                                                    *
* Purpose: Gets the last item in a linked list.                      *
*                                                                    *
* Input Arguments :                                                  *
*       Llist_p   :   pointer to the linked list.                    *
*                                                                    *
*       Returns   :   pointer to the last item in the list.          *
*                                                                    *
\********************************************************************/

Llist_t *gilist_linkListList(Llist_t *firstList_p , 
                               Llist_t *secondList_p);

/********************************************************************\
* Function: gilist_linkListList                                    *
*                                                                    *
* Purpose: Links two linked lists into one list.                     *
*                                                                    *
* Input Arguments :                                                  *
* firstList_p     :   pointer to the first linked list.              *
* secondList_p    :   pointer to the second linked list.             *
*                                                                    *
* Returns         :   pointer to the first list which now contains   *
*                     both lists.                                    *
\********************************************************************/

Llist_t *gilist_putAfterLast(Llist_t *Llist_p , void *dataItem_p);

/********************************************************************\
* Function: gilist_putAfterLast                                   *
*                                                                    *
* Purpose:  Inserts an item into the linked list after the last item.*
*                                                                    *
* Input Arguments :                                                  *
*    Llist_p      :   pointer to the linked list.                    *
*    dataItem_p   :   pointer to the data to be inserted.            *
*                                                                    *
* Returns         :   pointer to the item which was inserted.        *
*                                                                    *
\********************************************************************/

# endif
