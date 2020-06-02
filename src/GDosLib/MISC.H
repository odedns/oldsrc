
/*---------------------------------------------------------------------------*\
 *  Miscalleneous functions.                   Date : 26/11/1993.            *
 *  Header file.                                                             *
 *                                                                           *
 *                                                                           *
 *                                                                           *
 *                                                                           *
 *                                                                           *
 *                                                                           *
 *                                                                           *
 *                                                                           *
 *                                                                           *
 *                                                                           *
\*---------------------------------------------------------------------------*/

#ifndef _MISC_H
#define _MISC_H

#define YES  1
#define NO   0

/* Function Declarations *\
\* --------------------- */

/*---------------------------------------------------------------------------*\
* String Functions     !                                                     *
*                                                                            *
\*---------------------------------------------------------------------------*/


char *strn_set(char *s , char c , int count);
char *str_right(char *s , int width);
char *str_left(char *s , int width);
char *str_center(char *s , int width);
char *str_inv(char *s);
char *str_insert(char *s , char *insert , unsigned int pos );
char *str_delete(char *s , unsigned int pos , unsigned int count);



/*---------------------------------------------------------------------------*\
* Conversion functions !                                                     *
*                                                                            *
\*---------------------------------------------------------------------------*/
char *char2hex(char c , char *s);
char *char2dec(unsigned char c , char *s);



/*---------------------------------------------------------------------------*\
* Miscallenues Functions !                                                   *
\*---------------------------------------------------------------------------*/

int printer_stat();
int yorn(char *prompt , int dfault);
char *getDate();
char *get_time(char *t);
int sys_error();
int exec_process(char *pCommand,char **pArgs);
char *getDayOfWeek(int day, int month, int year);
/*-----------------------------------------------*\
* getopt program from Borland                    *
\*-----------------------------------------------*/
int getopt(int argc , char **argv , char *optionS);

/*-----------------------------------------------*\
* shell sort algorithm. 						 *
\*-----------------------------------------------*/
void shell_sort(void *v , int size,int width,int (*fcmp)(void *p1 , void *p2) );
void exchange(void *s1 , void *s2, int size);
int program_msg(char *format, ...);
int StrCountWords(char *pStr);
char **StrConvert(char *pStr);

#endif
