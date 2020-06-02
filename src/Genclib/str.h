
/*------------------------------------------------------------------------*/
/*   Module       :  genclib.lib                                          */
/*   File         :  str.h                                                */
/*   Date         :  03-10-1998                                           */
/*   Description  :  string handling functions.                           */
/*   Author       :  Oded Nissan                                          */
/*                   Copyright (c) 1994-1998 Oded Nissan.                 */
/*   History      :                                                       */
/*------------------------------------------------------------------------*/
/*   Date         |   Description                                         */
/*------------------------------------------------------------------------*/
/*   03/10/1998   |   Initial Release.                                    */
/*                |                                                       */
/*------------------------------------------------------------------------*/


#ifndef _STR_H_
#define _STR_H_
/*
 * str_set()
 * set len character in the string s to char c.
 */
void str_set(char *s, int len, char c);
/*
 * str_trim()
 * Trim leading and trailing whitespaces from
 * the input string s.
 * return pointer to the modified string.
 */
char *str_trim(char *s);

/*
 * str_trimt()
 * Trim trailing whitespaces from
 * the input string s.
 */
void str_trimt(char *s);

/*
 * str_triml()
 * Trim leading whitespaces from
 * the input string s.
 * return pointer to the modified string.
 */
char *str_triml(char *s);

/*
 * str_inv()
 * inverse a string.
 * inverses the input string s,
 * returns pointer to the modified string.
 */
char *str_inv(char *s);

/* set n chars in string to c */
char *str_nset(char *s , char c , int count);

/*
 * str_insert()
 * Inserts a string into another string
 * inserts string insert into s at position pos,
 * returns pointer to the new string.
 */
char *str_insert(char *s , char *insert , unsigned int pos);

/* 
 * str_delete()
 * Deletes characters from a string.
 * delete count characters in string s starting at position pos 
 * returns pointer to the new string.
 */
char *str_delete(char *s , unsigned int pos , unsigned int count);

/*
 * char2hex()
 * Convert  a character to hexadecimal string.
 * c 		the character to convert.
 * s 		the output hex string.
 * return 	pointer to the output string.
 */
char *char2hex  (char c, char *s);

/*
 * char2dec
 * Convers a char to decimal string.
 */
char *char2dec  (unsigned char c, char *s);

/*
 * str_count_words()
 * count the words in the input string
 * separated by a whitespace.
 * returns the number of words in the string.
 */
int str_cnt_words(char *pStr);

/*
 * str_cnvert()
 * Conver the string into a vector of words.
 */
char **str_convert(char *pStr);
#endif
