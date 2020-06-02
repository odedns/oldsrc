/*------------------------------------------------------------------------*/
/*   Module       :  genclib                                              */
/*   File         :  str.c                                                */
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

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>


#include "str.h"
/*
 * str_set()
 * set len character in the string s to char c.
 */
void str_set(char *s, int len, char c)
{
	memset(s,len,c);
}


/*
 * str_trim()
 * Trim leading and trailing whitespaces from
 * the input string s.
 * return pointer to the modified string.
 */
char *str_trim(char *s)
{
	str_trimt(s);
	return(str_triml(s));
}

/*
 * str_trimt()
 * Trim trailing whitespaces from
 * the input string s.
 */
void str_trimt(char *s)
{
	char *p = s + strlen(s) -1;

	while(p > s && isspace(*p)) {
		*p-- = '\0';
	}
}


/*
 * str_triml()
 * Trim leading whitespaces from
 * the input string s.
 * return pointer to the modified string.
 */
char *str_triml(char *s)
{
	while(isspace(*s) ) {
		*s++ = '\0';
	}
	return(s);
}


/*
 * str_inv()
 * inverse a string.
 * inverses the input string s,
 * returns pointer to the modified string.
 */
char *str_inv(char *s)      /* invers a string */
{
char c, *s1 = s + strlen(s) - 1, *t  = s;

	while( s1 > s) {
		c = *s;
		*s++ = *s1;
		*s1-- = c;
	}

	return(t);
}


/* set n chars in string to c */

char *str_nset(char *s , char c , int count)
{
	memset(s , c , count);
	*(s + count) = '\0';
	return(s);
}


/*
 * str_insert()
 * Inserts a string into another string
 * inserts string insert into s at position pos,
 * returns pointer to the new string.
 */
char *str_insert(char *s , char *insert , unsigned int pos)
{
char *new;
int slen = strlen(s) , ilen = strlen(insert);

	if( NULL == (new = (char *) malloc(slen + ilen+1))  || pos > slen)
		 return(NULL);
	memcpy(new , s , pos);
	memcpy((new + pos) , insert , ilen);
	strcpy((new + pos + ilen) , (s + pos ));
	return(new);
}


/* 
 * str_delete()
 * Deletes characters from a string.
 * delete count characters in string s starting at position pos 
 * returns pointer to the new string.
 */
char *str_delete(char *s , unsigned int pos , unsigned int count)
{
unsigned newpos = pos + count;

	if(pos > strlen(s))
		return(s);

	if(newpos > strlen(s))
		newpos = strlen(s);

	strcpy((s + pos) , (s + newpos));
	return(s);
}

/*
 * char2hex()
 * Convert  a character to hexadecimal string.
 * c 		the character to convert.
 * s 		the output hex string.
 * return 	pointer to the output string.
 */
char *char2hex  (char c, char *s)
{
char lut[] = "0123456789ABCDEF" ;
char *sav = s ;                 
int offset ;

	offset = (c & 0xF0) >> 4 ; 
	*s++ = lut[offset] ;      
	*s++ = lut[c & 0x0F] ;   
	*s = '\0';
	return (sav) ;
}


/*
 * char2dec
 * Convers a char to decimal string.
 */
char *char2dec  (unsigned char c, char *s)
{
int i ;
char *t = s+3 ;   

	for (*t-- = 0, i=0 ; i<3 ; ++i) {
		*t-- = (c % 10) + 0x30 ;    
		c /= 10 ;                  
	}
	return (s) ;
}


/*
 * str_count_words()
 * count the words in the input string
 * separated by a whitespace.
 * returns the number of words in the string.
 */
int str_cnt_words(char *pStr)
{
int wordCount = 0;

	while(1) {
            /* eat white space */
		while(isspace(*pStr) && *pStr)  {
			++pStr;
		}

		if (*pStr == '\0')
			break;

		++wordCount;
		while(!isspace(*pStr) && *pStr) {
			++pStr;
		}
				
	}
	return(wordCount);
}

/*
 * str_cnvert()
 * Conver the string into a vector of words.
 */
char **str_convert(char *pStr)
{
char **argv, *pBuff;
int Offset = 0, count;


	count = str_cnt_words(pStr);
	argv = (char **)calloc((count +1),sizeof(char*));

	pBuff = pStr;
	while(*pBuff) {
		while(isspace(*pBuff)) {
			*pBuff = '\0';
			++pBuff;
		}
		argv[Offset++] = pBuff;
		/* skip rest of word  */
		while(*pBuff && !isspace(*pBuff)) {
			++pBuff;
		}
	}
	argv[Offset] = NULL;
	return(argv);
}



/*
 * str_index
 * searches for the first occurance of 
 * ch in string s.
 * returns the position ch was found or
 * -1 if not found.
 */
int str_indexOf(char *s, int ch)
{
	int rv = -1;
	char *p = strchr(s,ch);
	if(NULL != p) {
		rv = p - s;
	}
	return(rv);
}


/*
 * str_index
 * searches for the last occurance of 
 * ch in string s.
 * returns the position ch was found or
 * -1 if not found.
 */
int str_lastIndexOf(char *s, int ch)
{
	int rv = -1;
	char *p = strrchr(s,ch);
	if(NULL != p) {
		rv = p - s;
	}
	return(rv);
}

/*
 * checks if the string s starts 
 * with the prefix pre.
 * return > 0  if true 0 if false.
 */
int str_startsWith(char *s, char *pre)
{
	int rv = 0;
	int len = strlen(pre);


	if(len <= strlen(s) && !strncmp(s,pre,len)) {
		rv = 1;
	}
	return(rv);
}


/*
 * checks if the string s ends
 * with the prefix pre.
 * return > 0  if true 0 if false.
 */
int str_endsWith(char *s, char *suff)
{
	/*
	int rv = 0;
	int len = strlen(suff);
	char *p = 

	return(rv);	
	*/
}

char *str_cvtLong(long l);
char *str_cvtInt(int i);
char *str_cvtFloat(float f);
char *str_cvtDouble(double d);
