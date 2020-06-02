/* $Header: /p/infra/ccinfra1/bb/tools/RCS/gi/inc/gihashl.h,v 6.1.1.1 1995/07/04 11:15:33 ccinfra1 Exp $ */
/********************************************************************\
* Name      : gihashl.h                                              *
*                                                                    *
* Purpose   : This header contains the declerations of hash function *
*             Internal routines and types and constants.             *
*                                                                    *
* Written by  : Dmitry Perl                                          *
* date        : Feb 93                                               *
*                                                                    *
* Changes History :                                                  *
* Date        |  By          |  Changes/New Feature                  *
*--------------------------------------------------------------------*
* 07/93       | Sharon A.    |- Change the default hash table size   *
*             |              |  from 101 to 511.                     *
*             |              |- Add the hash tabke size field to the *
*             |              |  hash table directory structure.      *
*             |              |  (inf v041).                          *
\********************************************************************/

# ifndef _gihashl_h
# define _gihashl_h

/* Constants *\
\* --------- */

# define HASH_DIR_SIZE   (30)  
                          /* the size of hash tables directory */
# define HASH_TABLE_SIZE (1001)      /* the size of hash table */
# define FALSE (0)
# define TRUE  (1)

/* Structures *\
\* ---------- */

/* Type definitions *\
\* ---------------- */

typedef struct hTableItem_st {        /* item of a hash table */
    struct hTableItem_st *nextItem_p; 
                                   /* pointer to the next item */
    int itemValue;                 /* the original table index*/
} hTableItem_t;

typedef struct hDir_st {         /* hash tables directory */
    int           hTableSize;    /* size of the hash table */
    hTableItem_t **hTable_p;     /* hash table */ 
    void         *keyTable_p;    /* key  table */
    int           keyType;       /* key  type (int or character) */
    int           keyLen;        /* key  lenght for character key */
} hDir_t;

typedef unsigned int hValue_t;    
                          /* the value returned by hash function */
typedef unsigned long int hArg_t;  /* the hash function argument */

/* Function Declaration *\
\* -------------------- */

/* function that returns integer interpretation of character   *\ 
\* string                                                      */

hArg_t gihash_convKey (
    int,     /* hash table Id */
    char *); 
    /* poiter to the string that should be interpreted as integer */

/* function that calculates and returns the value of hash function */

hValue_t gihash_int (
    hArg_t, /* argument wich hash-value should be calculated */
    int);   /* the hash table size */

# endif
