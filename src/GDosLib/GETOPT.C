/*
	getopt.c -- Turbo C
 
	Copyright (c) 1986,1990 by Borland International Inc.
	All Rights Reserved.

    Modified for personal Use 22/2/1994
*/
#include <errno.h>
#include <string.h>
#include <stdio.h>
 
int	optind	= 1;	/* index of which argument is next	*/
char   *optarg;		/* pointer to argument of current option */
int	opterr	= 1;	/* allow error message	*/
 
static	char   *letP = NULL;	/* remember next option char's location */

/*
  Parse the command line options, System V style.

  Standard option syntax is:
 
    option ::= SW [optLetter]* [argLetter space* argument]
*/

int getopt(int argc, char *argv[], char *optionS)
{
unsigned char ch;
char *optP;
static  char    SW = '-';
 
	if (argc > optind) {
		if (letP == NULL) {
			if ((letP = argv[optind]) == NULL ||
				*(letP++) != SW)  goto gopEOF;
			if (*letP == SW) {
				optind++;  goto gopEOF;
			}
		}
		if (0 == (ch = *(letP++))) {
			optind++;  goto gopEOF;
		}
		if (':' == ch  ||  (optP = strchr(optionS, ch)) == NULL)  
			goto gopError;
		if (':' == *(++optP)) {
			optind++;
			if (0 == *letP) {
				if (argc <= optind)  goto  gopError;
				letP = argv[optind++];
			}
			optarg = letP;
			letP = NULL;
		} else {
			if (0 == *letP) {
				optind++;
				letP = NULL;
			}
			optarg = NULL;
		}
		return ch;
	}
gopEOF:
	optarg = letP = NULL;  
	return EOF;
 
gopError:
	optarg = NULL;
	errno  = EINVAL;
	if (opterr)
		perror ("get command line option");
	return ('?');
}

