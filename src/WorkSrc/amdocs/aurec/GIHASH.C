#ifndef LINT
   static char rcsid[] ="$Header: /p/infra/ccinfra1/bb/tools/RCS/gi/src/gihash.c,v 6.1.1.1 1995/07/04 11:14:48 ccinfra1 Exp $" ;
#endif


/******************************************************************************
 *
 * Change History
 *-----------------------------------------------------------------------------
 * Revision No.  Date      Time                By
 * Changes/New Features
 *--------------+--------+-------------------+---------------------------------
 * $Log: gihash.c,v $
 * Revision 6.1.1.1  1995/07/04  11:14:48  ccinfra1
 * A new version v04 created from v03
 *
 * Revision 6.1  1995/07/04  11:14:47  ccinfra1
 * Initial major revision
 *
 * Revision 5.1.1.1  1995/02/09  16:46:01  ccinfra1
 * Converted version: v03
 *
 * Revision 5.1  1995/02/09  16:46:00  ccinfra1
 * Initial major revision
 *
 * Revision 2.1  1995/02/05  15:19:51  ccinfra1
 * A new version: v03
 *
 * Revision 2.0  1994/08/11  15:24:58  ccinfra1
 * A new version: v02
 *
 * Revision 1.3  94/08/10  12:47:57  12:47:57  sefi (Seffi Lipkin)
 * 'No
 * 
 * Revision 1.2  94/08/08  10:53:12  10:53:12  sefi (Seffi Lipkin)
 * Replace a blank by a TAB.
 * 
 * Revision 1.1  94/07/17  16:03:59  16:03:59  ccinfra1 (Infra grp CC-new accnt)
 * Initial revision
 * 
 * Revision 1.3  94/04/11  11:30:38  11:30:38  yossia (Yoosi Azaria)
 * change function name
 * 
 * Revision 1.2  94/04/07  08:04:08  08:04:08  yoram (Yoram)
 * *** empty log message ***
 * 
 * Revision 1.1  94/03/15  01:05:28  01:05:28  yoram (Yoram)
 * Initial revision
 * 
 *
 ******************************************************************************
 */
/********************************************************************\
*                                                                    *
* Purpose: allow random access to the inordinate memory tables       *
*                                                                    *
* Description:                                                       *
*                                                                    *
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
* 07/93   | Sharon Argov   | - Add the gihash_delete routine.           *
*         |                | - get the hash table size as a parameter*
*         |                |   to the gihash_create routine. (inf v041) *
*---------+----------------+-----------------------------------------*
*                                                                    *
\********************************************************************/

/* Std library *\
\* ----------- */

#include <string.h>
#include <stdlib.h>

/* Include files *\
\* ------------- */
# define EXTERN extern
/* general storage allocation interface */
# include "giallocg.h"

# undef EXTERN

# define EXTERN

/* hash function internals */
# include "gihashl.h"
/* hash function interface */
# include "gihashg.h"

# undef EXTERN

/* Statics *\
\* ------- */

static hDir_t s_hDir[HASH_DIR_SIZE];

/* Functions *\
\* --------- */

/********************************************************************\
*                                                                    *
* Purpose: create a hash table for specified table                   *
*                                                                    *
\********************************************************************/


int   gihash_create (
            void       *i_keyTable_p,
      enum  keyTypes    i_keyType,
            int         i_keyLen,
            int         i_keyTableSize,
            int         i_hashTableSize)
{

/* Local Variables *\
\* --------------- */

   static int firstTime = TRUE;
   int    i;
   int    hTableId;

/* Code *\
\* ---- */

   if (firstTime) {

       for (i=0; i < HASH_DIR_SIZE; i++) {

           s_hDir [i].hTable_p = NULL;

       }

       firstTime = FALSE;
   }

   hTableId = NO_ENTRY;

   for (i=0; i< HASH_DIR_SIZE && hTableId == NO_ENTRY; i++) {
 
       if (s_hDir [i].hTable_p == NULL) {
  
           s_hDir [i].keyTable_p = i_keyTable_p;
           s_hDir [i].keyType    = i_keyType;
           s_hDir [i].keyLen     = i_keyLen; 
           if (i_hashTableSize > 0) {
               s_hDir [i].hTableSize = i_hashTableSize;
	   } else {
	       s_hDir [i].hTableSize = HASH_TABLE_SIZE;
	   }
           s_hDir [i].hTable_p   = (hTableItem_t **)
             gimalloc_memAlloc (s_hDir [i].hTableSize *  
                                    sizeof(hTableItem_t*));
           hTableId = i;
       }
   }

   if (hTableId == NO_ENTRY) {
        return (hTableId);
   }

   for (i=0; i< s_hDir[hTableId].hTableSize; i++) { 

       s_hDir[hTableId].hTable_p [i] = NULL;

   }

   for (i = 0; i < i_keyTableSize; i++) {

       gihash_insert (hTableId,i);

   }
       
   return (hTableId);

}/* gihash_create */

/********************************************************************\
*                                                                    *
* Purpose: look for specified key in hash table                      *
*                                                                    *
\********************************************************************/

int gihash_lookup (int i_hTableId, void *i_key)
{
/* Local Variables *\
\* --------------- */

    hTableItem_t *curItem_p,*nextItem_p;

/* Code *\
\* ---- */

    if (s_hDir [i_hTableId].hTable_p == NULL) {

        return (NO_ENTRY);

    }

    if (s_hDir [i_hTableId].keyType == INT_KEY) {

        curItem_p = s_hDir[i_hTableId].hTable_p
                      [gihash_int(*((hArg_t*)i_key),
                                     s_hDir [i_hTableId].hTableSize)];

        while (curItem_p != NULL) { 
                 
            if (((int *) s_hDir [i_hTableId].keyTable_p)
                [curItem_p -> itemValue] == *((int*)i_key)) {
                
               return (curItem_p -> itemValue);

            }

            curItem_p = curItem_p -> nextItem_p;

        }
            
    } else {

        curItem_p = s_hDir [i_hTableId].hTable_p 
         [gihash_int (gihash_convKey (i_hTableId,i_key),
                         s_hDir [i_hTableId].hTableSize)];

        while (curItem_p != NULL) { 
                 
         if (strncmp( ((char**) s_hDir [i_hTableId].keyTable_p)
                                      [curItem_p -> itemValue],
                          i_key, 
                          s_hDir [i_hTableId].keyLen) == 0) {

                return (curItem_p -> itemValue);

            }

            curItem_p = curItem_p -> nextItem_p;

        }
    }

    return (NO_ENTRY);

} /* gihash_lookup */

/********************************************************************\
*                                                                    *
* Purpose: insert a new item in hash table                           *
*                                                                    *
\********************************************************************/

int gihash_insert (int i_hTableId, int i_index)
{
/* Local Variables *\
\* --------------- */

    hTableItem_t *hTableItem_p;
    hValue_t hValue;

/* Code *\
\* ---- */

    if (s_hDir [i_hTableId].hTable_p == NULL) {

        return (NO_ENTRY);

    }

    if (s_hDir[i_hTableId].keyType == INT_KEY) {

           hValue = gihash_int (
           ((int *) s_hDir [i_hTableId].keyTable_p)[i_index],
            s_hDir[i_hTableId].hTableSize);

    } else {

           hValue = gihash_int(gihash_convKey(i_hTableId,
            ((char**) s_hDir [i_hTableId].keyTable_p)[i_index]),
             s_hDir[i_hTableId].hTableSize);

    }

    hTableItem_p =
         (hTableItem_t*) gimalloc_memAlloc (sizeof (hTableItem_t));

    hTableItem_p -> nextItem_p =
                          s_hDir [i_hTableId].hTable_p [hValue];

    hTableItem_p -> itemValue  = i_index; 

    s_hDir [i_hTableId].hTable_p [hValue] = hTableItem_p;
    
    return (i_index);

} /* gihash_insert */

/********************************************************************\
*                                                                    *
* Purpose: delete an item from the hash table                        *
*                                                                    *
\********************************************************************/

int gihash_delete (int i_hTableId, int i_index)
{

/* Local Variables *\
\* --------------- */

    hValue_t hValue;  /* the hash value of the given index */
    hTableItem_t *curItem_p,*prevItem_p; /* items pointers */

/* Code *\
\* ---- */

    /* If hash table is empty - return NO_ENTRY */

    if (s_hDir[i_hTableId].hTable_p == NULL) {

        return (NO_ENTRY);

    }

    /* Find the hash value of the given index */

    if (s_hDir[i_hTableId].keyType == INT_KEY) {

       hValue = gihash_int (
           ((int *) s_hDir [i_hTableId].keyTable_p)[i_index],
            s_hDir[i_hTableId].hTableSize);

    } else {

       hValue = gihash_int(gihash_convKey(i_hTableId,
            ((char**) s_hDir [i_hTableId].keyTable_p)[i_index]),
             s_hDir[i_hTableId].hTableSize);

    }

    /* Set the current and previous item pointers to point to
       the first item
       in the hash table entry */

    curItem_p = s_hDir[i_hTableId].hTable_p[hValue];
    prevItem_p = curItem_p;

    /* Scan the hash table entry to get the required item,
       and delete it */

    while (curItem_p != NULL) { 
                 
        if (curItem_p->itemValue == i_index) {
                
            if (curItem_p == prevItem_p) { 
             /* the first item in the entry */

                s_hDir[i_hTableId].hTable_p[hValue] =
                curItem_p->nextItem_p;

            } else {

                prevItem_p->nextItem_p = curItem_p->nextItem_p;

            }

            free (curItem_p);

            return (i_index);

        }

        prevItem_p = curItem_p;

        curItem_p = curItem_p -> nextItem_p;

    }
            
    /* reaching here means that the item was not found in the table */

    return (NO_ENTRY);


} /* gihash_delete */

/********************************************************************\
*                                                                    *
* Purpose: drop a specified hash table and release memory            *
*                                                                    *
\********************************************************************/

int gihash_drop (int i_hTableId)
{
/* Local Variables *\
\* --------------- */

    hTableItem_t *curItem_p,*nextItem_p;
    int i;

/* Code *\
\* ---- */

    if (s_hDir [i_hTableId].hTable_p == NULL) {

        return (NO_ENTRY);

    }

    for (i = 0; i < s_hDir [i_hTableId].hTableSize; i++) {
        curItem_p = s_hDir [i_hTableId].hTable_p [i];
        while (curItem_p != NULL) {
            nextItem_p = curItem_p -> nextItem_p;
            free (curItem_p);
            curItem_p = nextItem_p;
        }
    }

    free (s_hDir [i_hTableId].hTable_p);

    s_hDir [i_hTableId].hTable_p = NULL;

    return (i_hTableId);

} /* gihash_drop */

/********************************************************************\
*                                                                    *
* Purpose: interptreter character string as integer number           *
*                                                                    *
\********************************************************************/

hArg_t gihash_convKey (int i_hTableId, char *i_keyChar_p)
{

/* Local Variables *\
\* --------------- */

    hArg_t convValue;
    int    i;

/* Code *\
\* ---- */

    for (convValue = 0, i = 0;
         *i_keyChar_p != '\0' && i < s_hDir [i_hTableId].keyLen; 
         i_keyChar_p++, i++) {

        convValue = *i_keyChar_p + 31 * convValue;

    }

    return (convValue);

} /* gihash_convKey */

/********************************************************************\
*                                                                    *
* Purpose: compute hash value for specified  integer number          *
*                                                                    *
\********************************************************************/

hValue_t gihash_int (hArg_t i_arg, int i_hTableSize)
{
/* Local Variables *\
\* --------------- */

/*    static const double ls_hashConst = (sqrt(5) - 1)/2; */

/* Code *\
\* ---- */

/*    return ((hValue_t) floor (HASH_TABLE_SIZE *
        (i_arg * ls_hashConst - floor (i_arg * ls_hashConst));
*/
    return ((hValue_t) i_arg % i_hTableSize);

}	/* gihash_int */

