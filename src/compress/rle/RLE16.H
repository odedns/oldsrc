/* rle16.h                      */
/* by Shaun Case 1991           */
/* Borland C++ 2.0              */
/* 16 bit header version        */
/* public domain                */

#define MAX_LEN   (0x7fff)              /* maximum length for runs or sequences     */
#define MAX_RUN_HEADER (0xffff)         /* bit 15 == 1 : run follows                */
                                        /* bit 14 - 0  : length of run              */

#define MAX_SEQ_HEADER (0x7fff)         /* bit 15 == 0 : unencode sequence follows  */
                                        /* bit 14 - 0  : length of sequence         */

#define RUN (0x8000)                    /* bit 15 == 1 : run follows                */

#define SEQ (0x0000)                    /* bit 15 == 0 : unencoded sequence follows */

