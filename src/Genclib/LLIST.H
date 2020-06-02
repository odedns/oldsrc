/**************************************************************************\
*  llists.h -- Include file for linked lists.                              *
*  contains : typedef definitions.                                         *
*	      function definitions.                                        *
\**************************************************************************/

     /**********************************\
     * Linked List Typedef declaration. *
     \**********************************/

    typedef struct LL {
		       void *data;
		       struct LL *next;
		       struct LL *prev;
		      }LLIST;


     /************************\
     * Function declarations  *
     \************************/


    LLIST *LlistInsertAfter(LLIST * , void *);
    LLIST *LlistInsertBefore(LLIST * , void *);
    LLIST *LlistGetNext(LLIST *);
    LLIST *LlistGetPrev(LLIST *);
	LLIST *LlistDeleteItem(LLIST *,void **data);
    LLIST *LlistGetFirst(LLIST *);
    LLIST *LlistGetLast(LLIST *);

