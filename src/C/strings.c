
/*-------------------------------------------------------------------------*\
 *  Miscallenous functions.                    Date : 26/11/1993.          *
 *                                                                         *
 *  String handling functions.                                             *
 *                                                                         *
 *  Conversion functions.                                                  *
 *                                                                         *
 *  Date and time functions.                                               *
 *                                                                         *
 *                                                                         *
 *                                                                         *
 *                                                                         *
 *                                                                         *
\*-------------------------------------------------------------------------*/

/* Include Files *\
\* ------------- */

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>

/* Global Variables *\
\* ---------------- */


/* Code *\
\* ---- */



/*------------------------------------------------------------------------*\
 *  Inverse a string.                                                     *
 *                                                                        *
\*------------------------------------------------------------------------*/
char *StrInv(char *s)      /* invers a string */
{
 char c , *s1 = s + strlen(s) - 1 , *t  = s;

    while( s1 > s) {
        c = *s;
        *s++ = *s1;
        *s1-- = c;
   }

 return(t);
 }



/*------------------------------------------------------------------------*\
 *  Sets n chars in string s to char c.                                   *
 *                                                                        *
\*------------------------------------------------------------------------*/

/* set n chars in string to c */

 char *StrnSet(char *s , char c , int count)
 {

    memset(s , c , count);
    *(s + count) = '\0';

    return(s);
 }




/*------------------------------------------------------------------------*\
 *                                                                        *
 * Inserts a string into another string                                   *
\*------------------------------------------------------------------------*/

 char *StrInsert(char *s , char *insert , unsigned int pos)
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


/*------------------------------------------------------------------------*\
 * Deletes count characters in string s starting at position pos          *
\*------------------------------------------------------------------------*/
char *StrDelete(char *s , unsigned int pos , unsigned int count)
{
unsigned newpos = pos + count;

    if(pos > strlen(s))
	return(s);

    if(newpos > strlen(s))
       newpos = strlen(s);


    strcpy((s + pos) , (s + newpos));

    return(s);
 }

/*------------------------------------------------------------------------*\
 *  Convert  a character to hexadecimal string.                           *
 *                                                                        *
\*------------------------------------------------------------------------*/

char *Char2Hex  (char c, char *s)
{
char lut[] = "0123456789ABCDEF" ;
char *sav = s ;                 
int offset ;

    offset = (c & 0xF0) >> 4 ; 
    *s++ = lut[offset] ;      
    *s++ = lut[c & 0x0F] ;   
    *s = 0 ;

    return (sav) ;
}


/*------------------------------------------------------------------------*\
 *    Convers a char to decimal string.                                   *
 *                                                                        *
\*------------------------------------------------------------------------*/



char *Char2Dec  (unsigned char c, char *s)
{
int i ;
char *t = s+3 ;   

    for (*t-- = 0, i=0 ; i<3 ; ++i) {
	*t-- = (c % 10) + 0x30 ;    
	c /= 10 ;                  
    }

   return (s) ;
}


/*------------------------------------------------------------------------*\
 *  Count the words in a string separeted by delimiter                    *
 *                                                                        *
\*------------------------------------------------------------------------*/
int StrCountWords(char *pStr)
{
int charFlag = 0;
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

/*------------------------------------------------------------------------*\
 *  Conver the string into a vector of words.                             *
 *                                                                        *
\*------------------------------------------------------------------------*/

char **StrConvert(char *pStr)
{
char **argv, *pBuff;
int Offset = 0, count;


	count = StrCountWords(pStr);
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


main()
{
char s[121];

int words = 0;


	printf("Enter s :");
	gets(s);

	words = StrCountWords(s);
	printf("words = %d\n",words);
}
