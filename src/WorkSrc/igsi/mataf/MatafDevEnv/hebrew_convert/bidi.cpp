/*+++*******************************************************************/
#pragma title("Bidi")
//("Compiling: "__FILE__"(Bidi)")
//("Last modification: "__TIMESTAMP__)

/**
***     I N C L U D E   F I L E S
**/

#define _Optlink 
#include <stdio.h>
#include <string.h>
#include <malloc.h>
#include <ctype.h>

#define TRUE 1
#define FALSE 0

char * _Optlink Heb_dos2win(char * readStr);
char * _Optlink Heb_win2dos(char * readStr);
static void Log2Vis(char * readStr, char * writeStr, int isHebrew);
static void Ansi2Ascii(char * writeStr);
static void Ascii2Ansi(char * writeStr);
static void Ansi2Ebcdic(char * writeStr);
static void Ebcdic2Ansi(char * writeStr);
static void NumbersReversing(char * hebrewPart);
static void DosStrWin(char * strOut, char * strIn);
static void DosStrWinLtr(char * strOut, char * strIn);
static int IsCharBiDi(char c);
static int IsDosHebrew(char * readStr);
static int IsWinHebrew(char * readStr);
static void ReverseBrackets(char *hebrewPart);
static void NumbersReversing(char *hebrewPart);
static void OfficeNumbersReversing(char *hebrewPart);

void strrev(char *s);

/************************************************************************
*   TYPE: FUNCTION          NAME: Heb_dos2win                           *
*=======================================================================*
*   VERSION: I              SUBSYSTEM: BIDI             PROJECT: MHSV   *
*=======================================================================*
*   DATE: 2.02              AUTHORS: T.GLAZER                           *
*   DESCRIPTION: Convert dos hebrew to windows (based on most of Michael*
*                Michlin code).                                         *
*=======================================================================*
* REVISIONS                                                             *
*   DATE:                   AUTHOR:                                     *
*   DESCRIPTION:                                                        *
*=======================================================================*
* TRANSFER OF CONTROL:                                                  *
*=======================================================================*
* CALLING SEQUENCE: outString = Heb_dos2win (inString)                  *
*-----------------------------------------------------------------------*
* IN PARAMETERS                                                         *
*                                                                       *
* <Var Name>        <Description>               <Data Type>             *
* inString           STRING TO BE CONVERTED      char *                 *
*-----------------------------------------------------------------------*
* IN/OUT PARAMETERS                                                     *
*                                                                       *
* <Var Name>        <Description>               <Data Type>             *
*-----------------------------------------------------------------------*
* OUT PARAMETERS                                                        *
*                                                                       *
* <Var Name>        <Description>               <Data Type>             *
* (return)          CONVERTED STRING             char *                 *
*=======================================================================*

#pragma page()

*=======================================================================*
* PROGRAM FLOW:                                                         *
*=======================================================================*
* EXCEPTION HANDLING:                                                   *
*=======================================================================*
* FORM USAGE                                                            *
*                                                                       *
*   LIBRARY:                                                            *
*                                                                       *
*   <Form Name>         <Description>                                   *
*                                                                       *
*=======================================================================*
* FILE USAGE                                                            *
*                                                                       *
* <Name>                <Description>   <Opened By>     <R/W/U/D>       *
*                                                                       *
*=======================================================================*
* ABSOLUTE ADDRESSES:                                                   *
*=======================================================================*
* ENVIRONMENT STRINGS:                                                  *
*=======================================================================*
* MODULES INVOKED                                                       *
*                                                                       *
* <Name>                <Description>                                   *
*=======================================================================*
* UTILITIES/COMMANDS INVOKED                                            *
*                                                                       *
* <Name>        <Description>                                           *
*                                                                       *
*=======================================================================*
* SIDE EFFECTS:                                                         *
*=======================================================================*
* ASSUMPTIONS:                                                          *
*=======================================================================*
* RELATED DOCUMENTS:                                                    *
*=======================================================================*
* REMARKS:                                                              *
*=======================================================================*/

#pragma page()

char * _Optlink Heb_dos2win(char *readStr)
{
#ifdef __OS2__
   return readStr;
#else
   char *tmpStr;
   int i, slen = strlen(readStr);
   if (IsDosHebrew(readStr) == FALSE) return readStr;
   strrev(readStr);
   Ascii2Ansi(readStr);
   tmpStr = (char *)malloc(slen + 1);
   Log2Vis (readStr,  tmpStr,  1);
   strcpy (readStr,  tmpStr);
   free(tmpStr);
   return readStr;
#endif
}

/************************************************************************
*   TYPE: FUNCTION          NAME: Heb_win2dos                           *
*=======================================================================*
*   VERSION: I              SUBSYSTEM: BIDI             PROJECT: MHSV   *
*=======================================================================*
*   DATE: 5.02              AUTHORS: T.GLAZER                           *
*   DESCRIPTION: Convert windows hebrew to dos (based on most of Michael*
*                Michlin code).                                         *
*=======================================================================*
* REVISIONS                                                             *
*   DATE:                   AUTHOR:                                     *
*   DESCRIPTION:                                                        *
*=======================================================================*
* TRANSFER OF CONTROL:                                                  *
*=======================================================================*
* CALLING SEQUENCE: outString = Heb_win2dos (inString)                  *
*-----------------------------------------------------------------------*
* IN PARAMETERS                                                         *
*                                                                       *
* <Var Name>        <Description>               <Data Type>             *
* inString           STRING TO BE CONVERTED      char *                 *
*-----------------------------------------------------------------------*
* IN/OUT PARAMETERS                                                     *
*                                                                       *
* <Var Name>        <Description>               <Data Type>             *
*-----------------------------------------------------------------------*
* OUT PARAMETERS                                                        *
*                                                                       *
* <Var Name>        <Description>               <Data Type>             *
* (return)          CONVERTED STRING             char *                 *
*=======================================================================*

#pragma page()

*=======================================================================*
* PROGRAM FLOW:                                                         *
*=======================================================================*
* EXCEPTION HANDLING:                                                   *
*=======================================================================*
* FORM USAGE                                                            *
*                                                                       *
*   LIBRARY:                                                            *
*                                                                       *
*   <Form Name>         <Description>                                   *
*                                                                       *
*=======================================================================*
* FILE USAGE                                                            *
*                                                                       *
* <Name>                <Description>   <Opened By>     <R/W/U/D>       *
*                                                                       *
*=======================================================================*
* ABSOLUTE ADDRESSES:                                                   *
*=======================================================================*
* ENVIRONMENT STRINGS:                                                  *
*=======================================================================*
* MODULES INVOKED                                                       *
*                                                                       *
* <Name>                <Description>                                   *
*=======================================================================*
* UTILITIES/COMMANDS INVOKED                                            *
*                                                                       *
* <Name>        <Description>                                           *
*                                                                       *
*=======================================================================*
* SIDE EFFECTS:                                                         *
*=======================================================================*
* ASSUMPTIONS:                                                          *
*=======================================================================*
* RELATED DOCUMENTS:                                                    *
*=======================================================================*
* REMARKS:                                                              *
*=======================================================================*/

#pragma page()

char * _Optlink Heb_win2dos(char *readStr)
{
#ifdef __OS2__
   return readStr;
#else
   char *tmpStr;
   int i, slen = strlen(readStr);
   if (IsWinHebrew(readStr) == FALSE) return readStr;
   tmpStr = (char *)malloc(slen + 1);
   Log2Vis (readStr,  tmpStr,  1);
   strcpy (readStr,  tmpStr);
   free(tmpStr);
   Ansi2Ascii(readStr);
   strrev(readStr);
   return readStr;
#endif
}

static int IsCharAlpha(char c)
{
   return ((c >= 'a' && c <= 'z') ||
           (c >= '‡' && c <= '˙') ||
           (c >= 'A' && c <= 'Z'));
}

static int IsCharAlphaNumeric(char c)
{
   return ((c >= 'a' && c <= 'z') ||
           (c >= 'A' && c <= 'Z') ||
           (c >= '‡' && c <= '˙') ||
           (c >= '0' && c <= '9') );
}

static int IsWinHebrew(char * readStr)
{
      int strLen = strlen(readStr);

	  int i;
      for(i = 0; i<strLen; i++)
      {
           if(IsCharBiDi(readStr[i])) return TRUE;
      }
      return FALSE;
}

static int IsDosHebrew(char * readStr)
{
      int strLen = strlen(readStr);

	  int i;
      for(i = 0; i<strLen; i++)
      {
           if((unsigned char)readStr[i] <= 154/*˙*/  && (unsigned char)readStr[i] >= 128/*‡*/ ) return TRUE;
      }
      return FALSE;
}

#define BAD 0x6f                 // all bad ASCII characters denoted by '?'
#define BADE '?'                 // all bad EBCDIC chars denoted by '?'
//translation tables from asccii to ebsdic
static unsigned char  tAsc2Ebc[] =
{
     BAD ,BAD ,BAD ,BAD ,BAD ,BAD ,BAD ,BAD ,                    /* 00 - 07 */
     BAD ,BAD ,BAD ,BAD ,BAD ,BAD ,BAD ,BAD ,                    /* 08 - 0f */
     BAD ,BAD ,BAD ,BAD ,BAD ,0xdc,BAD ,BAD ,                    /* 10 - 17 */
     BAD ,BAD ,BAD ,BAD ,0x1e,BAD ,BAD ,BAD ,                    /* 18 - 1f */
     0x40,0x5a,0x7f,0x7b,0x5b,0x6c,0x50,0x7d,                    /* 20 - 27 */
     0x4d,0x5d,0x5c,0x4e,0x6b,0x60,0x4b,0x61,                    /* 28 - 2f */
     0xf0,0xf1,0xf2,0xf3,0xf4,0xf5,0xf6,0xf7,                    /* 30 - 37 */
     0xf8,0xf9,0x7a,0x5e,0x4c,0x7e,0x6e,0x6f,                    /* 38 - 3f */
     0x7c,0xc1,0xc2,0xc3,0xc4,0xc5,0xc6,0xc7,                    /* 40 - 47 */
     0xc8,0xc9,0xd1,0xd2,0xd3,0xd4,0xd5,0xd6,                    /* 48 - 4f */
     0xd7,0xd8,0xd9,0xe2,0xe3,0xe4,0xe5,0xe6,                    /* 50 - 57 */
     0xe7,0xe8,0xe9,0xad,0xed,0xbd,0x5f,0x6d,                    /* 58 - 5f */
     0x40,0x81,0x82,0x83,0x84,0x85,0x86,0x87,                    /* 60 - 67 */
     0x88,0x89,0x91,0x92,0x93,0x94,0x95,0x96,                    /* 68 - 6f */
     0x97,0x98,0x99,0xa2,0xa3,0xa4,0xa5,0xa6,                    /* 70 - 77 */
     0xa7,0xa8,0xa9,0x8b,0x4f,0x9b,0x70,BAD ,                    /* 78 - 7f */
     0x41,0x42,0x43,0x44,0x45,0x46,0x47,0x48,                    /* 80 - 87 */
     0x49,0x51,0x52,0x53,0x54,0x55,0x56,0x57,                    /* 88 - 8f */
     0x58,0x59,0x62,0x63,0x64,0x65,0x66,0x67,                    /* 90 - 97 */
     0x68,0x69,0x71,0x4a,0x8e,BAD ,BAD ,BAD ,                    /* 98 - 9f */
     0x90,0xea,0xfc,0xfe,BAD ,BAD ,BAD ,BAD ,                    /* a0 - a7 */
     BAD ,BAD ,0x1b,BAD ,BAD ,BAD ,BAD ,BAD ,                    /* a8 - af */
     BAD ,BAD ,BAD ,0xbf,0x3f,0x3f,0x3f,0x1b,                    /* b0 - b7 */
     0x1b,0x3f,0xbf,0x1b,0x1f,0x1f,0x1f,0x1b,                    /* b8 - bf */
     0x1e,0x3e,0x3b,0x3d,0x2d,0x2c,0x3d,0x3d,                    /* c0 - c7 */
     0x1e,0x1c,0x3e,0x3b,0x3d,0x2d,0x2c,0x3e,                    /* c8 - cf */
     0x3e,0x3b,0x3b,0x1e,0x1e,0x1c,0x1c,0x2c,                    /* d0 - d7 */
     0x2c,0x1f,0x1c,0x9f,BAD ,BAD ,0x7a,BAD ,                    /* d8 - df */
     BAD ,BAD ,BAD ,BAD ,BAD ,BAD ,BAD ,BAD ,                    /* e0 - e7 */
     BAD ,BAD ,BAD ,BAD ,BAD ,BAD ,BAD ,BAD ,                    /* e8 - ef */
     BAD ,0x9e,0xae,0x8c,BAD ,BAD ,BAD ,BAD ,                    /* f0 - f7 */
     BAD ,0x4b,0xfa,0xfb,BAD ,0xb2,0x4b,BAD                      /* f8 - ff */
};

static unsigned char tEbc2Asc[]=
{
     BADE,BADE,BADE,BADE,BADE,BADE,BADE,BADE,                    /* 00 - 07 */
     BADE,BADE,BADE,BADE,BADE,BADE,BADE,BADE,                    /* 08 - 0f */
     BADE,BADE,BADE,BADE,BADE,BADE,BADE,BADE,                    /* 10 - 17 */
     BADE,BADE,BADE,0xbf,0xda,BADE,0xc0,0xd9,                    /* 18 - 1f */
     BADE,BADE,BADE,BADE,BADE,BADE,BADE,BADE,                    /* 20 - 27 */
     0x4d,BADE,BADE,BADE,0xc5,0xc4,BADE,BADE,                    /* 28 - 2f */
     BADE,BADE,BADE,BADE,BADE,BADE,BADE,BADE,                    /* 30 - 37 */
     BADE,BADE,BADE,0xc2,BADE,0xc3,0xc1,0xb4,                    /* 38 - 3f */
     0x20,0x80,0x81,0x82,0x83,0x84,0x85,0x86,                    /* 40 - 47 */
     0x87,0x88,0x9b,0x2e,0x3c,0x28,0x2b,0x7c,                    /* 48 - 4f */
     0x26,0x89,0x8a,0x8b,0x8c,0x8d,0x8e,0x8f,                    /* 50 - 57 */
     0x90,0x91,0x21,0x24,0x2a,0x29,0x3b,0x5e,                    /* 58 - 5f */
     0x2d,0x2f,0x92,0x93,0x94,0x95,0x96,0x97,                    /* 60 - 67 */
     0x98,0x99,BADE,0x2c,0x25,0x5f,0x3e,0x3f,                    /* 68 - 6f */
     0x7e,0x9a,BADE,BADE,BADE,BADE,BADE,BADE,                    /* 70 - 77 */
     BADE,BADE,0x3a,0x23,0x40,0x27,0x3d,0x22,                    /* 78 - 7f */
     BADE,0x61,0x62,0x63,0x64,0x65,0x66,0x67,                    /* 80 - 87 */
     0x68,0x69,BADE,0x7b,0xf3,BADE,0x9c,BADE,                    /* 88 - 8f */
     0xa0,0x6a,0x6b,0x6c,0x6d,0x6e,0x6f,0x70,                    /* 90 - 97 */
     0x71,0x72,BADE,0x7d,BADE,BADE,0xf1,0xd8,                    /* 98 - 9f */
     BADE,BADE,0x73,0x74,0x75,0x76,0x77,0x78,                    /* a0 - a7 */
     0x79,0x7a,BADE,BADE,BADE,0x5b,0xf2,BADE,                    /* a8 - af */
     BADE,BADE,0xf2,BADE,BADE,BADE,BADE,BADE,                    /* b0 - b7 */
     BADE,BADE,BADE,BADE,BADE,0x5d,BADE,0xb3,                    /* b8 - bf */
     BADE,0x41,0x42,0x43,0x44,0x45,0x46,0x47,                    /* c0 - c7 */
     0x48,0x49,BADE,BADE,BADE,BADE,BADE,BADE,                    /* c8 - cf */
     BADE,0x4a,0x4b,0x4c,0x4d,0x4e,0x4f,0x50,                    /* d0 - d7 */
     0x51,0x52,BADE,BADE,BADE,0x51,BADE,BADE,                    /* d8 - df */
     BADE,BADE,0x53,0x54,0x55,0x56,0x57,0x58,                    /* e0 - e7 */
     0x59,0x5a,0xa1,BADE,BADE,0x5c,BADE,BADE,                    /* e8 - ef */
     0x30,0x31,0x32,0x33,0x34,0x35,0x36,0x37,                    /* f0 - f7 */
   //0x38,0x39,0xb3,BADE,0xa2,BADE,0xa3,BADE,                    /* f8 - ff */
     0x38,0x39,0xfa,0xfb,0xa2,BADE,0xfe,0xff,                    /* f8 - ff */
};

//
// the function translate logical layout of windows string to visual
//
//it must be allocated memory for the writeStr not less then readStr length plus 1
//
static void Log2Vis(char * readStr, char * writeStr, int isHebrew)
{
  if(isHebrew == TRUE)
  {
      DosStrWin(writeStr, readStr);

  }
  else
  {
      DosStrWinLtr(writeStr, readStr);
  }
}
static void Ansi2Ascii(char *writeStr)
{
      int strLen = strlen(writeStr);
	  int i;  
      for(i = 0; i<strLen; i++)
      {
        unsigned char tav = (unsigned char)'˙';
        unsigned char alef = (unsigned char)'‡';


        if((unsigned char)writeStr[i] <= tav  && (unsigned char)writeStr[i] >= alef )
         writeStr[i] = (unsigned char)((unsigned char)writeStr[i] - 96);
      }

}
static void Ascii2Ansi(char * writeStr)
{
      int strLen = strlen(writeStr);

	  int i;
      for(i = 0; i<strLen; i++)
      {

		if((unsigned char)writeStr[i] <= 154/*˙*/  && (unsigned char)writeStr[i] >= 128/*‡*/ )
        {
          writeStr[i] = (unsigned char)((unsigned char)writeStr[i] + 96);
        }
      }

}

static void Ansi2Ebcdic(char *writeStr)
{
      Ansi2Ascii(writeStr);
      int strLen = strlen(writeStr);

	  int i;
      for(i = 0; i<strLen; i++)
      {
        writeStr[i] = tAsc2Ebc[(unsigned char)writeStr[i]];
      }
}

 void Ebcdic2Ansi(char *writeStr)
{
      int strLen = strlen(writeStr);

	  int i;
      for(i = 0; i<strLen; i++)
      {
        writeStr[i] = tEbc2Asc[(unsigned char)writeStr[i]];
      }

      Ascii2Ansi(writeStr);

}


//-----from the other cpp file----------------

#define HEBREW_PART           0
#define ENGLISH_PART          1
#define PUNCTUATION_PART      2
#define NUMBER_PART           3
//* *********************************************************************** *
//  Function    : IsSemyDigit
//  Author      : Mikhael Mikhlin
//  Date        : 8/11/95
//  Parameters  : char c - character been analised
//  Output      : non
//  Return Val  : int  result 1 - true 0 - false
//  Globals Chg : none
//  Description : checks character if it is one of the hebrew alphabetic characters
//* *********************************************************************** *
static int IsCharBiDi(char c)
{
  static char hebrewChar[] ="‡·‚„‰ÂÊÁËÈÎÍÏÓÌÔÒÚÙÛˆı˜¯˘˙";

  if(c == 0)
    return FALSE;


  if(strchr(hebrewChar, c) != NULL)
    return TRUE;
  else
    return FALSE;
}

//* *********************************************************************** *
//  Function    : IsSemyDigit
//  Author      : Mikhael Mikhlin
//  Date        : 8/11/95
//  Parameters  : char c - character been analised
//  Output      : non
//  Return Val  : int  result 1 - true 0 - false
//  Globals Chg : none
//  Description : checks character if it is one of the group    + - . , $ :
//                Windows's hebrew somtimes think about them
//                that they are digits
//* *********************************************************************** *
static int IsSemyDigit(char c)
{
   switch (c)
   {
    case '.':
    case ',':
    case '$':
    case '/':
    case ':':
    case '+':
    case '-':
     return (1);
    default :
      return (0);
   }

}
//* *********************************************************************** *
//  Function    : DosStrWin
//  Author      : Mikhael Mikhlin
//  Date        : 15/11/94
//  Parameters  : char *strOut -  output string
//                char *strIn  -  input string
//                HINSTANCE hInst - current task instance
//  Output      : none
//  Return Val  : none
//  Globals Chg : none
//  Description : function translates hebrew string in Tadmail-DOS format
//                to right-to-left Windows reading order string
//* *********************************************************************** *

static void DosStrWin(char * strOut, char * strIn)
{


  char **part = (char **)malloc((strlen(strIn)+1)*sizeof(char*)); //array of the parts of the string been translated
  char *partTypes = (char*)malloc(strlen(strIn)+1);//array of the types of every part
  int partsNumber = 0;// namber of the parts in the array

  char *hebrewPart  = (char*)malloc(strlen(strIn)+1);//hebrew buffer
  char *englishPart = (char*)malloc(strlen(strIn)+1);//english buffer
  char *punctPart  =  (char*)malloc(strlen(strIn)+1);//punctuation buffer
  char *numberPart  =  (char*)malloc(strlen(strIn)+1);//punctuation buffer

  strOut[0] ='\0';//output string is empty
  //curent pointer in output buffer
  char *outPoint = strOut;

  //number of hebrew characters in current buffer
  short hebNumber = 0;

  //number of english characters in current buffer
  short engNumber = 0;

  //number of punctuation characters in current buffer
  short punNumber = 0;

  //number of number characters in current buffer
  short numNumber = 0;


  int i;
  for (i = 0 ; strIn[i]; i++)
  {

    //if current character is hebrew it is been added to hebrew buffer
    if (IsCharAlpha(strIn[i]) && IsCharBiDi(strIn[i]))
    {
       //endes english part if it is exists and save it in the parts array
       if(engNumber != 0)
       {
         englishPart[engNumber] ='\0';
         part[partsNumber]= (char*)malloc(engNumber + 1);
         partTypes[partsNumber] = ENGLISH_PART;
         strcpy(part[partsNumber], englishPart);
         partsNumber++;
         engNumber = 0;
       }

       //finishs punctuation part if it is exists and save it in the parts array
       if(punNumber != 0)
       {
         punctPart[punNumber] ='\0';
         part[partsNumber]= (char*)malloc(punNumber + 1);
         partTypes[partsNumber] = PUNCTUATION_PART;
         strcpy(part[partsNumber], punctPart);
         partsNumber++;
         punNumber = 0;
       }

       //endes number part if it is exists and save it in the parts array
       if(numNumber != 0)
       {
         numberPart[numNumber] ='\0';
         part[partsNumber]= (char*)malloc(numNumber + 1);
         partTypes[partsNumber] = NUMBER_PART;
         strcpy(part[partsNumber], numberPart);
         partsNumber++;
         numNumber = 0;
       }

       //adds to hebrew part
       hebrewPart[hebNumber] = strIn[i];
       hebNumber ++;
       continue;
    }


    //if current character is english  it is been added
    // to english buffer
    if (IsCharAlpha(strIn[i]) && !IsCharBiDi(strIn[i]))
    {

       //endes hebrew part if it is exists and save it in the parts array
       if(hebNumber != 0)
       {
         hebrewPart[hebNumber] ='\0';
         part[partsNumber]= (char*)malloc(hebNumber + 1);
         partTypes[partsNumber] = HEBREW_PART;
         strcpy(part[partsNumber], hebrewPart);
         partsNumber++;
         hebNumber = 0;
       }

       //endes punctuation part if it is exists and save it in the parts array
       if(punNumber != 0)
       {
         punctPart[punNumber] ='\0';
         part[partsNumber]= (char*)malloc(punNumber + 1);
         partTypes[partsNumber] = PUNCTUATION_PART;
         strcpy(part[partsNumber], punctPart);
         partsNumber++;
         punNumber = 0;
       }

       //endes number part if it is exists and save it in the parts array
       if(numNumber != 0)
       {
         numberPart[numNumber] ='\0';
         part[partsNumber]= (char*)malloc(numNumber + 1);
         partTypes[partsNumber] = NUMBER_PART;
         strcpy(part[partsNumber], numberPart);
         partsNumber++;
         numNumber = 0;
       }

       //adds to english part
       englishPart[engNumber] = strIn[i];
       engNumber ++;
       continue;

    }

    //if current character is digit it is added
    // to a number buffer
    if (
        (
          IsCharAlphaNumeric(strIn[i]) &&
          !IsCharAlpha(strIn[i])       &&
          !IsCharBiDi(strIn[i])
        )
        ||
        (
          i < (strlen(strIn) - 1) &&
          strIn[i] == '$'         &&
          isdigit((unsigned char)strIn[i+1])

        )
       )
    {

       //endes english part if it is exists and save it in the parts array
       if(engNumber != 0)
       {
         englishPart[engNumber] ='\0';
         part[partsNumber]= (char*)malloc(engNumber + 1);
         partTypes[partsNumber] = ENGLISH_PART;
         strcpy(part[partsNumber], englishPart);
         partsNumber++;
         engNumber = 0;
       }

       //endes hebrew part if it is exists and save it in the parts array
       if(hebNumber != 0)
       {
         hebrewPart[hebNumber] ='\0';
         part[partsNumber]= (char*)malloc(hebNumber + 1);
         partTypes[partsNumber] = HEBREW_PART;
         strcpy(part[partsNumber], hebrewPart);
         partsNumber++;
         hebNumber = 0;
       }

       //endes punctuation part if it is exists and save it in the parts array
       if(punNumber != 0)
       {
         punctPart[punNumber] ='\0';
         part[partsNumber]= (char*)malloc(punNumber + 1);
         partTypes[partsNumber] = PUNCTUATION_PART;
         strcpy(part[partsNumber], punctPart);
         partsNumber++;
         punNumber = 0;
       }

       //adds to number part
       numberPart[numNumber] = strIn[i];
       numNumber ++;
       continue;

    }


    //if current character is punctuation  it is been added
    // to the punctuation buffer
    {

       //endes english part if it is exists and save it in the parts array
       if(engNumber != 0)
       {
         englishPart[engNumber] ='\0';
         part[partsNumber]= (char*)malloc(engNumber + 1);
         partTypes[partsNumber] = ENGLISH_PART;
         strcpy(part[partsNumber], englishPart);
         partsNumber++;
         engNumber = 0;
       }

       //endes hebrew part if it is exists and save it in the parts array
       if(hebNumber != 0)
       {
         hebrewPart[hebNumber] ='\0';
         part[partsNumber]= (char*)malloc(hebNumber + 1);
         partTypes[partsNumber] = HEBREW_PART;
         strcpy(part[partsNumber], hebrewPart);
         partsNumber++;
         hebNumber = 0;
       }

       //endes number part if it is exists and save it in the parts array
       if(numNumber != 0)
       {
         numberPart[numNumber] ='\0';
         part[partsNumber]= (char*)malloc(numNumber + 1);
         partTypes[partsNumber] = NUMBER_PART;
         strcpy(part[partsNumber], numberPart);
         partsNumber++;
         numNumber = 0;
       }

       //adds to punctuation part
       punctPart[punNumber] = strIn[i];
       punNumber ++;
       continue;

    }

  }//the first loop end

  //finish english part if it is exists and save it in the parts array
  if(engNumber != 0)
  {
         englishPart[engNumber] ='\0';
         part[partsNumber]= (char*)malloc(engNumber + 1);
         partTypes[partsNumber] = ENGLISH_PART;
         strcpy(part[partsNumber], englishPart);
         partsNumber++;
         engNumber = 0;
  }

  //finish hebrew part if it is exists and save it in the parts array
  if(hebNumber != 0)
  {
     hebrewPart[hebNumber] ='\0';
     part[partsNumber]= (char*)malloc(hebNumber + 1);
     partTypes[partsNumber] = HEBREW_PART;
     strcpy(part[partsNumber], hebrewPart);
     partsNumber++;
     hebNumber = 0;
  }

  //finish english part if it is exists and save it in the parts array
  if(punNumber != 0)
  {
      punctPart[punNumber] ='\0';
      part[partsNumber]= (char*)malloc(punNumber + 1);
      partTypes[partsNumber] = PUNCTUATION_PART;
      strcpy(part[partsNumber], punctPart);
      partsNumber++;
      punNumber = 0;
  }

  //finish number part if it is exists and save it in the parts array
  if(numNumber != 0)
  {
    numberPart[numNumber] ='\0';
    part[partsNumber]= (char*)malloc(numNumber + 1);
    partTypes[partsNumber] = NUMBER_PART;
    strcpy(part[partsNumber], numberPart);
    partsNumber++;
    numNumber = 0;
  }

  //determination of the english part contained  other punctuation
  //or number parts
  //it mast start with english part and finish with english or number part
  int ifStructeredEnglish = 0;
  for(i = 0 ; i < partsNumber; i++)
  {
    if( partTypes[i] == ENGLISH_PART)
    {
      ifStructeredEnglish = 1;
      continue;
    }

    if(ifStructeredEnglish == 1)
    {
      if( partTypes[i] == PUNCTUATION_PART
          &&
          i<partsNumber - 1  //this part is not last one
          &&
          (
            partTypes[i+1] == NUMBER_PART
            ||
            partTypes[i+1] == ENGLISH_PART
          )
        )
      {
        partTypes[i] = ENGLISH_PART;
        continue;
      }

      if( partTypes[i] == NUMBER_PART)
      {
        partTypes[i] = ENGLISH_PART;
        continue;
      }

       ifStructeredEnglish = 0;
    }
  }

  //determination of special characters position
  //sometime Windows's Hebrew work with them as with digits
  //and sometimes as punctuations
  // in both cases in very amazing if not to say foolish manner

  for(i = 0 ; i < partsNumber - 1; i++)
  {
    if ( i <  partsNumber - 2)
    {
      if ( partTypes[i] == NUMBER_PART
           &&
           partTypes[i+1] == PUNCTUATION_PART
           &&
           partTypes[i+2] == NUMBER_PART
         )
      {
         if(
             part[i+1][1] ==  '\0'
             &&
             IsSemyDigit(part[i+1][0])
            )
            partTypes[i+1] = NUMBER_PART;
         continue;
      }
    }

    if( partTypes[i] == ENGLISH_PART
        &&
        partTypes[i+1] == PUNCTUATION_PART
       )
    {
       int partLength = strlen(part[i]);
       if(
           isdigit((unsigned char)part[i][partLength-1])
           &&
           IsSemyDigit(part[i+1][0])
           &&
           (
             part[i+1][1] ==  '\0'
             ||
             !IsSemyDigit(part[i+1][1])
           )
         )
       {

         part[i] = (char*)realloc(part[i], partLength + 2*sizeof(char));
         part[i][partLength] = part[i+1][0];
         part[i][partLength + 1] = '\0';

         partLength = strlen(part[i+1]);
         memmove(part[i+1], part[i+1] + 1, partLength);

       }
       continue;
    }

  }


  //output string building
  englishPart[0] = '\0';
  engNumber = 0;
  numberPart[0] = '\0';
  numNumber = 0;
  int j;
  for(i = 0 ; i < partsNumber; i++)
  {
    switch(partTypes[i])
    {
      case HEBREW_PART:
      case PUNCTUATION_PART:

      //if exists english parts it is added
      if(engNumber != 0)
      {
        strrev(englishPart);
        strcat(outPoint, englishPart);
        englishPart[0] = '\0';
        engNumber = 0;
      }

      //if exists english parts it is added
      if(numNumber != 0)
      {
        strrev(numberPart);
        strcat(outPoint, numberPart);
        numberPart[0] = '\0';
        numNumber = 0;
       }

       if (partTypes[i] == PUNCTUATION_PART)
         for (j = 0; j < strlen(part[i]); j++)
         {
           //adds to hebrew part
           if (part[i][j] == '(')
             part[i][j] = ')';
           else
            if (part[i][j] == ')')
              part[i][j] = '(';

           if (part[i][j] == '[')
             part[i][j] = ']';
           else
             if (part[i][j] == ']')
               part[i][j] = '[';

           if (part[i][j] == '{')
             part[i][j] = '}';
           else
             if (part[i][j] == '}')
               part[i][j] = '{';

           if (part[i][j] == '<')
             part[i][j] = '>';
           else
             if (part[i][j] == '>')
               part[i][j] = '<';
         }
       strcat(outPoint, part[i]);

       break;

       case NUMBER_PART:
         numNumber ++;
         strcat(numberPart, part[i]);
       break;

       case ENGLISH_PART:
         engNumber ++;
         strcat(englishPart, part[i]);
       break;
       default:
         //error message
//         MessageBox(NULL, "Memory destruction : worthwin", "", MB_TASKMODAL);
       break;
    }

  }

  //if exists english parts have been merged
  if(engNumber != 0)
  {
    strrev(englishPart);
    strcat(outPoint, englishPart);
    englishPart[0] = '\0';
    engNumber = 0;
  }

  //if exists number parts have been merged
  if(numNumber != 0)
  {
    strrev(numberPart);
    strcat(outPoint, numberPart);
    numberPart[0] = '\0';
    numNumber = 0;
  }

  //memory free
  for(i = 0 ; i < partsNumber; i++)
    free(part[i]);


  free(partTypes);
  free(part);
  free(hebrewPart);
  free(englishPart);
  free(punctPart);
  free(numberPart);


}

//* *********************************************************************** *
//  Function    : WinAmpPut
//  Author      : Mikhael Mikhlin
//  Date        : 22/11/94
//  Parameters  : char *strIn  -  input string
//  Output      : none
//  Return Val  : none
//  Globals Chg : none
//  Description : function puts '& ' simbol befor hebrew character if it
//                was after it ,it's need in Windows version since in windows
//                buttons and othe objects text is keeped in back order
//* *********************************************************************** *
static void WinAmpPut(char * strIn)
{

	int i;
   for (i = 0; strIn[i]; i++)
     if (IsCharAlpha(strIn[i]) && IsCharBiDi(strIn[i]))
       if (strIn[i+1]== '&')
       {
         strIn[i+1] = strIn[i];
         strIn[i]   = '&';
       }
}


//* *********************************************************************** *
//  Function    : CarriageReturnAdd
//  Author      : Mikhael Mikhlin
//  Date        : 21/02/95
//  Parameters  : char *strIn  -  input string
//                int maxLength - input UIW_TEXT maximum string legth(memory
//                size )
//  Output      : char * pointer to the resoult string
//  Return Val  : none
//  Globals Chg : none
//  Description : function puts '\r' simbol befor '\n' if it is'nt exists
//                and reallocates memory if it is necessary
//* *********************************************************************** *
static char *CarriageReturnAdd(char * strIn)
{

  int lfNum = 0; //line feeds number
  // oded 16/03/2003
  int j=0;
  int i=0;
  for (i = 0; strIn[i]; i++)
  {
    if (i == 0 && strIn[0] == '\n')
     lfNum++;
    if (i != 0 && strIn[i-1] != '\r' && strIn[i] == '\n')
     lfNum++;
  }

  char *strOut = NULL;
  if (lfNum)
  {
    strOut = new char[lfNum + strlen(strIn) + 1];
    for (i = 0,j = 0; strIn[i]; i++)
    {
      if (i == 0 && strIn[0] == '\n')
      {
        strOut[0] = '\r';
        strOut[1] = '\n';
        j = 2;
      }
      else
        if (i != 0 && strIn[i-1] != '\r' && strIn[i] == '\n')
        {
          strOut[j] =   '\r';
          strOut[j+1] = '\n';
          j += 2;
        }
        else
        {
          strOut[j] =   strIn[i];
          j++;
        }
    }

    strOut[j] = 0;//ending zerou

    return strOut;
  }
  else
    return NULL;

}
//* *********************************************************************** *
//  Function    : DosStrWinLtr
//  Author      : Mikhael Mikhlin
//  Date        : 3/03/97
//  Parameters  : char *strIn  -  input string
//                char *strOut - output string
//  Return Val  : none
//  Description : translate left to right window hebrew string  layout to the
//                dos one . Sufficient memory space must be allocated
//                for the strOut
//* *********************************************************************** *
static void DosStrWinLtr(char * strOut, char * strIn)
{
   //At the firs copy in to out
   strcpy(strOut, strIn);

   char *hebBegin = NULL;
   char *hebEnd;
   char saveChar;
   //hebrew part location
   for (int i = 0 ; i <= strlen(strOut); i++)
   {
      //it's  begining of a hebrew part of the string
      if(
          IsCharBiDi(strOut[i])
          &&
          hebBegin == NULL
        )
       {
         hebBegin = (char*)strOut + i;
       }

       //if we are at the end of a hebrew part
       //we have met the first english character in the hebrew part
       if(
           hebBegin != NULL
           &&
           (
             IsCharAlpha(strOut[i]) && !IsCharBiDi(strOut[i])
             ||
             strOut[i] == 0
           )
         )
       {

         //the all punctuation characters that befor it belong to the english part
         for (i--; ;i--)
           if ( IsCharBiDi(strOut[i])
                ||
                (IsCharAlphaNumeric(strOut[i]) && !IsCharAlpha(strOut[i]))
              )
             break;

         //now i appoints to last character in current hebrew part
         hebEnd = (char*)strOut + i + 1;
         saveChar = *hebEnd;
         *hebEnd = 0;

         //reverse numbers in it
         NumbersReversing(hebBegin);

         //reverse hebrew part
         strrev(hebBegin);

         //reverse brackets in it
         ReverseBrackets(hebBegin);


         hebBegin = NULL;
         *hebEnd  = saveChar;
       }

   }
}
//* *********************************************************************** *
//  Function    : ReverseBrackets
//  Author      : Mikhael Mikhlin
//  Date        : 3/03/97
//  Parameters  : char *hebrewPart  -  input string
//  Description : Reverse brackets in the  hebrew part of english string
//* *********************************************************************** *
static void  ReverseBrackets(char *hebrewPart)
{
 for (int j = 0; j < strlen(hebrewPart); j++)
 {
   //adds to hebrew part
   if (hebrewPart[j] == '(')
     hebrewPart[j] = ')';
   else
     if (hebrewPart[j] == ')')
       hebrewPart[j] = '(';

   if (hebrewPart[j] == '[')
     hebrewPart[j] = ']';
   else
     if (hebrewPart[j] == ']')
       hebrewPart[j] = '[';

   if (hebrewPart[j] == '{')
     hebrewPart[j] = '}';
   else
    if (hebrewPart[j] == '}')
      hebrewPart[j] = '{';

   if (hebrewPart[j] == '<')
     hebrewPart[j] = '>';
   else
     if (hebrewPart[j] == '>')
       hebrewPart[j] = '<';
 }
}



//* *********************************************************************** *
//  Function    : IsSemyDigit
//  Author      : Mikhael Mikhlin
//  Date        : 13/04/2000
//  Parameters  : char c - character been analised
//  Output      : non
//  Return Val  : int  result 1 - true 0 - false
//  Globals Chg : none
//  Description : checks character if it is one that
//                office in right alligned paragraf think about it
//                that it is a digit
//* *********************************************************************** *
int IsOfficeSemyDigit(char c)
{
   switch (c)
   {
    case '.':
    case ',':
    case '/':
    case ':':
     return (1);
    default :
      return (0);
   }

}
//* *********************************************************************** *
//  Function    : NumbersReversing
//  Author      : Mikhael Mikhlin
//  Date        : 3/03/97
//  Parameters  : char *hebrewPart  -  input string
//  Description : Reverse number parts in the  hebrew part of the left to right
//                alligned string containes hebrew
//* *********************************************************************** *
static void NumbersReversing(char *hebrewPart)
{
   char *numBegin = NULL;
   char *numEnd;
   char saveChar;
   //hebrew part location
   for (int i = 0 ; i <= strlen(hebrewPart); i++)
   {
      //it's  begining of a number part of the string
      if(
          (
            isdigit((unsigned char)hebrewPart[i])
            ||
            (
              (i < (strlen(hebrewPart) - 1))  &&
              hebrewPart[i] == '$'            &&
              isdigit((unsigned char)hebrewPart[i+1])
            )
          )
          &&
          numBegin == NULL
        )
       {
         numBegin = hebrewPart + i;
         continue;
       }

       //if we are at the end of a number part
       //we have met the first non digit character in the number part
       if(
           numBegin != NULL
           &&
           (
             (
               !isdigit((unsigned char)hebrewPart[i])
               &&
               !IsSemyDigit(hebrewPart[i])
             )
             ||
             (
               (i < (strlen(hebrewPart) - 1))  &&
               !isdigit((unsigned char)hebrewPart[i+1])       &&
               IsSemyDigit(hebrewPart[i])
             )
             ||
             hebrewPart[i] == 0

           )

         )
       {

         //now i appoints to last character in current hebrew part
         numEnd = hebrewPart + i;
         saveChar = *numEnd;
         *numEnd = 0;

         //reverse hebrew part
         strrev(numBegin);


         numBegin = NULL;
         *numEnd  = saveChar;
       }

   }

}

//* *********************************************************************** *
//  Function    : OfficeNumbersReversing
//  Author      : Mikhael Mikhlin
//  Date        : 3/03/97
//  Parameters  : char *hebrewPart  -  input string
//  Description : Reverse number parts in the  hebrew part of the rtf paragraf of
//                the rtf that is made by an office application
//* *********************************************************************** *
static void OfficeNumbersReversing(char *hebrewPart)
{
   char *numBegin = NULL;
   char *numEnd;
   char saveChar;
   //hebrew part location
   for (int i = 0 ; i <= strlen(hebrewPart); i++)
   {
      //it's  begining of a number part of the string
      if(
          (
            isdigit((unsigned char)hebrewPart[i])
            ||
            ((unsigned char)hebrewPart[i] == '%')
          )
          &&
          numBegin == NULL
        )
       {
         numBegin = hebrewPart + i;
         continue;
       }

       //if we are at the end of a number part
       //we have met the first non digit character in the number part
       if(
           numBegin != NULL
           &&
           (
             (
               !isdigit((unsigned char)hebrewPart[i])
               &&
               ((unsigned char)hebrewPart[i] != '%')
               &&
               !IsOfficeSemyDigit(hebrewPart[i])
             )
             ||
             (
               IsOfficeSemyDigit(hebrewPart[i]) &&
               (i < (strlen(hebrewPart) - 1))   &&
               !isdigit((unsigned char)hebrewPart[i+1])
             )
             ||
             (
               IsOfficeSemyDigit(hebrewPart[i]) &&
               (hebrewPart[i+1] == 0)
             )
             ||
             hebrewPart[i] == 0

           )

         )
       {

         //now i appoints to last character in current hebrew part
         numEnd = hebrewPart + i;
         saveChar = *numEnd;
         *numEnd = 0;

         //reverse hebrew part
         strrev(numBegin);


         numBegin = NULL;
         *numEnd  = saveChar;
       }

   }

}

//here I begin new part to make window rtf from dos heberew-english mixed text
//first function makes right to left rtf
//hebrew font
char hebFont[] = "\\lang1037\\f1\\rtlch ";
//english font
char engFont[] = "\\lang1033\\f0\\ltrch ";

char acPar[] = "\\par ";

static void  Dos2WinRtf(char * strIn, char *strOut, int outBuffLength)
{


  char **part = (char **)malloc((strlen(strIn)+1)*sizeof(char*)); //array of the parts of the string been translated
  char *partTypes = (char*)malloc(strlen(strIn)+1);//array of the types of every part
  int partsNumber = 0;// namber of the parts in the array

  char *hebrewPart  = (char*)malloc(strlen(strIn)+1);//hebrew buffer
  char *englishPart = (char*)malloc(strlen(strIn)+1);//english buffer
  char *punctPart  =  (char*)malloc(strlen(strIn)+1);//punctuation buffer
  char *numberPart  =  (char*)malloc(strlen(strIn)+1);//punctuation buffer

  //strOut[0] ='\0';//output string is empty
  //curent pointer in output buffer
  //char *outPoint = strOut;

  //number of hebrew characters in current buffer
  short hebNumber = 0;

  //number of english characters in current buffer
  short engNumber = 0;

  //number of punctuation characters in current buffer
  short punNumber = 0;

  //number of number characters in current buffer
  short numNumber = 0;


  int i;
  for (i = 0 ; strIn[i]; i++)
  {

    //if current character is hebrew it is been added to hebrew buffer
    if (IsCharAlpha(strIn[i]) && IsCharBiDi(strIn[i]))
    {
       //endes english part if it is exists and save it in the parts array
       if(engNumber != 0)
       {
         englishPart[engNumber] ='\0';
         part[partsNumber]= (char*)malloc(engNumber + 1);
         partTypes[partsNumber] = ENGLISH_PART;
         strcpy(part[partsNumber], englishPart);
         partsNumber++;
         engNumber = 0;
       }

       //finishs punctuation part if it is exists and save it in the parts array
       if(punNumber != 0)
       {
         punctPart[punNumber] ='\0';
         part[partsNumber]= (char*)malloc(punNumber + 1);
         partTypes[partsNumber] = PUNCTUATION_PART;
         strcpy(part[partsNumber], punctPart);
         partsNumber++;
         punNumber = 0;
       }

       //endes number part if it is exists and save it in the parts array
       if(numNumber != 0)
       {
         numberPart[numNumber] ='\0';
         part[partsNumber]= (char*)malloc(numNumber + 1);
         partTypes[partsNumber] = NUMBER_PART;
         strcpy(part[partsNumber], numberPart);
         partsNumber++;
         numNumber = 0;
       }

       //adds to hebrew part
       hebrewPart[hebNumber] = strIn[i];
       hebNumber ++;
       continue;
    }


    //if current character is english  it is been added
    // to english buffer
    if (IsCharAlpha(strIn[i]) && !IsCharBiDi(strIn[i]))
    {

       //endes hebrew part if it is exists and save it in the parts array
       if(hebNumber != 0)
       {
         hebrewPart[hebNumber] ='\0';
         part[partsNumber]= (char*)malloc(hebNumber + 1);
         partTypes[partsNumber] = HEBREW_PART;
         strcpy(part[partsNumber], hebrewPart);
         partsNumber++;
         hebNumber = 0;
       }

       //endes punctuation part if it is exists and save it in the parts array
       if(punNumber != 0)
       {
         punctPart[punNumber] ='\0';
         part[partsNumber]= (char*)malloc(punNumber + 1);
         partTypes[partsNumber] = PUNCTUATION_PART;
         strcpy(part[partsNumber], punctPart);
         partsNumber++;
         punNumber = 0;
       }

       //endes number part if it is exists and save it in the parts array
       if(numNumber != 0)
       {
         numberPart[numNumber] ='\0';
         part[partsNumber]= (char*)malloc(numNumber + 1);
         partTypes[partsNumber] = NUMBER_PART;
         strcpy(part[partsNumber], numberPart);
         partsNumber++;
         numNumber = 0;
       }

       //adds to english part
       englishPart[engNumber] = strIn[i];
       engNumber ++;
       continue;

    }

    //if current character is digit it is added
    // to a number buffer
    if (
        (
          IsCharAlphaNumeric(strIn[i]) &&
          !IsCharAlpha(strIn[i])       &&
          !IsCharBiDi(strIn[i])
        )
        ||
        (
          i < (strlen(strIn) - 1) &&
          strIn[i] == '$'         &&
          isdigit((unsigned char)strIn[i+1])

        )
       )
    {

       //endes english part if it is exists and save it in the parts array
       if(engNumber != 0)
       {
         englishPart[engNumber] ='\0';
         part[partsNumber]= (char*)malloc(engNumber + 1);
         partTypes[partsNumber] = ENGLISH_PART;
         strcpy(part[partsNumber], englishPart);
         partsNumber++;
         engNumber = 0;
       }

       //endes hebrew part if it is exists and save it in the parts array
       if(hebNumber != 0)
       {
         hebrewPart[hebNumber] ='\0';
         part[partsNumber]= (char*)malloc(hebNumber + 1);
         partTypes[partsNumber] = HEBREW_PART;
         strcpy(part[partsNumber], hebrewPart);
         partsNumber++;
         hebNumber = 0;
       }

       //endes punctuation part if it is exists and save it in the parts array
       if(punNumber != 0)
       {
         punctPart[punNumber] ='\0';
         part[partsNumber]= (char*)malloc(punNumber + 1);
         partTypes[partsNumber] = PUNCTUATION_PART;
         strcpy(part[partsNumber], punctPart);
         partsNumber++;
         punNumber = 0;
       }

       //adds to number part
       numberPart[numNumber] = strIn[i];
       numNumber ++;
       continue;

    }


    //if current character is punctuation  it is been added
    // to the punctuation buffer
    {

       //endes english part if it is exists and save it in the parts array
       if(engNumber != 0)
       {
         englishPart[engNumber] ='\0';
         part[partsNumber]= (char*)malloc(engNumber + 1);
         partTypes[partsNumber] = ENGLISH_PART;
         strcpy(part[partsNumber], englishPart);
         partsNumber++;
         engNumber = 0;
       }

       //endes hebrew part if it is exists and save it in the parts array
       if(hebNumber != 0)
       {
         hebrewPart[hebNumber] ='\0';
         part[partsNumber]= (char*)malloc(hebNumber + 1);
         partTypes[partsNumber] = HEBREW_PART;
         strcpy(part[partsNumber], hebrewPart);
         partsNumber++;
         hebNumber = 0;
       }

       //endes number part if it is exists and save it in the parts array
       if(numNumber != 0)
       {
         numberPart[numNumber] ='\0';
         part[partsNumber]= (char*)malloc(numNumber + 1);
         partTypes[partsNumber] = NUMBER_PART;
         strcpy(part[partsNumber], numberPart);
         partsNumber++;
         numNumber = 0;
       }

       //adds to punctuation part
       punctPart[punNumber] = strIn[i];
       punNumber ++;
       continue;

    }

  }//the first loop end

  //finish english part if it is exists and save it in the parts array
  if(engNumber != 0)
  {
         englishPart[engNumber] ='\0';
         part[partsNumber]= (char*)malloc(engNumber + 1);
         partTypes[partsNumber] = ENGLISH_PART;
         strcpy(part[partsNumber], englishPart);
         partsNumber++;
         engNumber = 0;
  }

  //finish hebrew part if it is exists and save it in the parts array
  if(hebNumber != 0)
  {
     hebrewPart[hebNumber] ='\0';
     part[partsNumber]= (char*)malloc(hebNumber + 1);
     partTypes[partsNumber] = HEBREW_PART;
     strcpy(part[partsNumber], hebrewPart);
     partsNumber++;
     hebNumber = 0;
  }

  //finish english part if it is exists and save it in the parts array
  if(punNumber != 0)
  {
      punctPart[punNumber] ='\0';
      part[partsNumber]= (char*)malloc(punNumber + 1);
      partTypes[partsNumber] = PUNCTUATION_PART;
      strcpy(part[partsNumber], punctPart);
      partsNumber++;
      punNumber = 0;
  }

  //finish number part if it is exists and save it in the parts array
  if(numNumber != 0)
  {
    numberPart[numNumber] ='\0';
    part[partsNumber]= (char*)malloc(numNumber + 1);
    partTypes[partsNumber] = NUMBER_PART;
    strcpy(part[partsNumber], numberPart);
    partsNumber++;
    numNumber = 0;
  }

  //determination of the english part contained  other punctuation
  //or number parts
  //it mast start with english part and finish with english or number part
  int ifStructeredEnglish = 0;
  for(i = 0 ; i < partsNumber; i++)
  {
    if( partTypes[i] == ENGLISH_PART)
    {
      ifStructeredEnglish = 1;
      continue;
    }

    if(ifStructeredEnglish == 1)
    {
      if( partTypes[i] == PUNCTUATION_PART
          &&
          i<partsNumber - 1  //this part is not last one
          &&
          (
            partTypes[i+1] == NUMBER_PART
            ||
            partTypes[i+1] == ENGLISH_PART
          )
        )
      {
        partTypes[i] = ENGLISH_PART;
        continue;
      }

      if( partTypes[i] == NUMBER_PART)
      {
        partTypes[i] = ENGLISH_PART;
        continue;
      }

       ifStructeredEnglish = 0;
    }
  }

  //determination of special characters position
  //sometime Windows's Hebrew work with them as with digits
  //and sometimes as punctuations
  // in both cases in very amazing if not to say foolish manner

  for(i = 0 ; i < partsNumber - 1; i++)
  {
    if ( i <  partsNumber - 2)
    {
      if ( partTypes[i] == NUMBER_PART
           &&
           partTypes[i+1] == PUNCTUATION_PART
           &&
           partTypes[i+2] == NUMBER_PART
         )
      {
         if(
             part[i+1][1] ==  '\0'
             &&
             IsSemyDigit(part[i+1][0])
            )
            partTypes[i+1] = NUMBER_PART;
         continue;
      }
    }

    if( partTypes[i] == ENGLISH_PART
        &&
        partTypes[i+1] == PUNCTUATION_PART
       )
    {
       int partLength = strlen(part[i]);
       if(
           isdigit((unsigned char)part[i][partLength-1])
           &&
           IsSemyDigit(part[i+1][0])
           &&
           (
             part[i+1][1] ==  '\0'
             ||
             !IsSemyDigit(part[i+1][1])
           )
         )
       {

         part[i] = (char*)realloc(part[i], partLength + 2*sizeof(char));
         part[i][partLength] = part[i+1][0];
         part[i][partLength + 1] = '\0';

         partLength = strlen(part[i+1]);
         memmove(part[i+1], part[i+1] + 1, partLength);

       }
       continue;
    }

  }


  //output string building
  englishPart[0] = '\0';
  engNumber = 0;
  numberPart[0] = '\0';
  numNumber = 0;
  int j;

  int outLen = 1;
  //strOut = (LPSTR)malloc(10);

  strOut[0] = 0;
  for(i = 0 ; i < partsNumber; i++)
  {
    switch(partTypes[i])
    {
      case HEBREW_PART:
      case PUNCTUATION_PART:

      //if exists english parts it is added
      if(engNumber != 0)
      {
        strrev(englishPart);

        outLen += strlen(engFont) + strlen(englishPart);
        if(outLen > outBuffLength)
        {
          //it must be thorough decision for error and debug messaging in
          // the application, I'll do it more later
          //ShowErrorMessage();

          //in  this case I cant to add more information in the output buffer
          // it will be cutted at this place
          goto FunctionEnd;
        }
        //strOut = (LPSTR)realloc(strOut, outLen);

        strcat(strOut,engFont);
        strcat(strOut, englishPart);
        englishPart[0] = '\0';
        engNumber = 0;
      }

      //if exists english parts it is added
      if(numNumber != 0)
      {
        strrev(numberPart);

        outLen += strlen(engFont) + strlen(numberPart);
        if(outLen > outBuffLength)
        {
          //it must be thorough decision for error and debug messaging in
          // the application, I'll do it more later
          //ShowErrorMessage();

          //in  this case I cant to add more information in the output buffer
          // it will be cutted at this place
          goto FunctionEnd;
         }
 //strOut = (LPSTR)realloc(strOut, outLen);

        strcat(strOut,engFont);
        strcat(strOut, numberPart);
        numberPart[0] = '\0';
        numNumber = 0;
       }

       if (partTypes[i] == PUNCTUATION_PART)
         for (j = 0; j < strlen(part[i]); j++)
         {
           //adds to hebrew part
           if (part[i][j] == '(')
             part[i][j] = ')';
           else
            if (part[i][j] == ')')
              part[i][j] = '(';

           if (part[i][j] == '[')
             part[i][j] = ']';
           else
             if (part[i][j] == ']')
               part[i][j] = '[';

           if (part[i][j] == '{')
             part[i][j] = '}';
           else
             if (part[i][j] == '}')
               part[i][j] = '{';

           if (part[i][j] == '<')
             part[i][j] = '>';
           else
             if (part[i][j] == '>')
               part[i][j] = '<';
         }

       outLen += strlen(hebFont) + strlen(part[i]);
       if(outLen > outBuffLength)
       {
         //it must be thorough decision for error and debug messaging in
         // the application, I'll do it more later
         //ShowErrorMessage();

         //in  this case I cant to add more information in the output buffer
         // it will be cutted at this place
         goto FunctionEnd;
       }
       //strOut = (LPSTR)realloc(strOut, outLen);

       strcat(strOut,hebFont);
       strcat(strOut, part[i]);

       break;

       case NUMBER_PART:
         numNumber ++;
         strcat(numberPart, part[i]);
       break;

       case ENGLISH_PART:
         engNumber ++;
         strcat(englishPart, part[i]);
       break;
       default:
         //error message
//         MessageBox(NULL, "Memory destruction : worthwin", "", MB_TASKMODAL);
       break;
    }

  }

  //if exists english parts have been merged
  if(engNumber != 0)
  {
    strrev(englishPart);

    outLen += strlen(engFont) + strlen(englishPart);
    if(outLen > outBuffLength)
    {
      //it must be thorough decision for error and debug messaging in
      // the application, I'll do it more later
      //ShowErrorMessage();

     //in  this case I cant to add more information in the output buffer
     // it will be cutted at this place
      goto FunctionEnd;
    }
    //strOut = (LPSTR)realloc(strOut, outLen);

    strcat(strOut,engFont);
    strcat(strOut, englishPart);
    englishPart[0] = '\0';
    engNumber = 0;
  }

  //if exists number parts have been merged
  if(numNumber != 0)
  {
    strrev(numberPart);

    outLen += strlen(engFont) + strlen(numberPart);
    if(outLen > outBuffLength)
    {
      //it must be thorough decision for error and debug messaging in
      // the application, I'll do it more later
      //ShowErrorMessage();

     //in  this case I cant to add more information in the output buffer
     // it will be cutted at this place
      goto FunctionEnd;
    }
//    strOut = (LPSTR)realloc(strOut, outLen);

    strcat(strOut,engFont);
    strcat(strOut, numberPart);
    numberPart[0] = '\0';
    numNumber = 0;
  }

FunctionEnd:
  //memory free
  for(i = 0 ; i < partsNumber; i++)
    free(part[i]);


  free(partTypes);
  free(part);
  free(hebrewPart);
  free(englishPart);
  free(punctPart);
  free(numberPart);


}

#ifndef __OS2__
void strrev(char *s)
{
	char c;
	char *start = s;
	char *end = s + strlen(s) -1;
	while(end > start) {
		c = *end;
		*end = *start;
		*start = c;
		++start;
		--end;
	}
}
#endif

#ifdef _TEST
int main(int argc, char **argv)
{
	
	char s[80];
	char *p;
	/*
	strcpy(s,"èÖÜâÄ òáÄå èâíÅ Ñòöâ ");
	*/
	
	gets(s);
	printf("translating string: %s\n",s);
	p = Heb_dos2win(s);
	printf("after translating string: %s\n",p);
	
	return(0);
}
#endif
