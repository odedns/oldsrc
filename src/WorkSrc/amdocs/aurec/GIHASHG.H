/* $Header: /p/infra/ccinfra1/bb/tools/RCS/pub/gihashg.h,v 6.1.1.1 1995/07/04 11:04:45 ccinfra1 Exp $ */
# ifndef _gihashg_h
# define _gihashg_h

/********************************************************************\
* Name      : gihashg.h                                                  *
*                                                                    *
* Purpose   : This header contains the declerations of hash function *
*               Interface routines.                                  *
*                                                                    *
* Supervisor  : Dmitry Perl                                          *
* Written by  : Sharon Argov                                         *
* date        : Feb 93                                               *
*                                                                    *
* Changes History :                                                  *
* Date        |  By          |  Changes/New Feature                  *
*--------------------------------------------------------------------*
* 07/93       | Sharon A.    |-Add declaration to gihash_delete routine *
*             |              |-Add a new parameter to the gihash_create *
*             |              | routine: hash table size.(inf v041)   *
\********************************************************************/

/* Constants *\
\* --------- */

enum keyTypes {
    CHAR_KEY ,       /* key of character table */
    INT_KEY          /* key of integer type */               
};

# define NO_ENTRY  (-1) /* this status is returned by:
                        Tpm_gihash_lookup when it fails to find the 
                            specified key in the key table,
                  Tpm_gihash_create when it fails to find an empthy
                                slot to create a new hash table
                  Tpm_gihash_insert and Tpm_gihash_drop when 
                        the table with specified ID does not exist */ 


/* Structures *\
\* ---------- */

/* Type definitions *\
\* ---------------- */

/* Function Declaration *\
\* -------------------- */

/* function that creates a hash table and returns the table ID */

int gihash_create (
    void *       , /* poiter to the key table */
    enum keyTypes, /* key type */
    int          , /* key syze */
    int          , /* number of keys that is inserted
                      into the hash table during its creation */
    int);          /* size of the hash table */

     
/* function that looks for specified key in hash table and returns 
   the  index of this key in the key table                        */

int gihash_lookup (
    int,    /* hash table Id */
    void*); /* pointer to the key to look for */

/* function that performs insert of new item in the hash table */

int gihash_insert (
    int,    /* hash table Id */
    int);   /* index of the key in the key table */

/* function that performs delete of a given item to the hash table */

int gihash_delete (
    int,    /* hash table Id */
    int);   /* index of the key in the key table */

/* function that drops the specified hash table */

int gihash_drop (
     int);   /* hash table Id */

# endif
