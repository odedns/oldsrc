/*------------------------------------------------------------------------*/
/*   Module       :  wc.exe                                               */
/*   File         :  wc.c                                                 */
/*   Date         :  31/03/1997                                           */
/*   Description  :  A UNIX like word count program for MSDOS.            */
/*   Author       :  Oded Nissan                                          */
/*                   Copyright (c) 1994,1997 Oded Nissan.                 */
/*   History      :                                                       */
/*------------------------------------------------------------------------*/
/*   Date         |   Description                                         */
/*------------------------------------------------------------------------*/
/*   31/03/1997   |   Initial Release.                                    */
/*                |                                                       */
/*                |                                                       */
/*------------------------------------------------------------------------*/

#include <stdio.h>
#include <string.h>
#include <ctype.h>

long chars = 0;
long words = 0;
long lines = 0;
long t_chars = 0;
long t_words = 0;
long t_lines = 0;

#define __CHAR_CNT (0x0001)
#define __WORD_CNT (0x0020)
#define __LINE_CNT (0x0300)
extern int optind;
int options = 0;

int file_count(char *name);
void help();
void print_count(char *name);
void print_t_count();


int main(int argc, char **argv)
{
int c;
int num_files = 0;

		while( (c = getopt(argc,argv,"hlwc")) != EOF) {
			switch(c) {
				case 'w' :
						options |= __WORD_CNT;
						break;
				case 'l' :
						options |= __LINE_CNT;
						break;
				case 'c' :
						options |=  __CHAR_CNT;
						break;
				case 'h' :
						help();
						return(1);
				default  :
						help();
						return(1);
			
			}
		}

       if(!options) 
       		options = __LINE_CNT | __WORD_CNT | __CHAR_CNT;
		
     	if(argv[optind] != NULL) {
			while(argv[optind] != NULL) {
				++num_files;
				file_count(strlwr(argv[optind]));
				print_count(argv[optind]);
				t_chars += chars;
				t_words += words;
				t_lines += lines;
				++optind;
			}
		} else {
			file_count(NULL);
			print_count("(stdin)");
		}		
		if(num_files > 1) {
			print_t_count();
		}
		return(0);
}		

void help()
{
char Msg[] = " *** Unix style wc utility for Dos version 1.0 ***\n \
	  By Oded Nissan  31-03-1997";
	printf("%s\n\n%s\n%s\n%s\n%s\n%s\n",Msg,
		"usage : wc [-lwch] [file...]\n",
		"-l 	- print count for lines, words and chars (default).",
		"-w 	- print word count only",
		"-c 	- print character count only",
		"-h 	- print this help text");
}


void print_count(char *name)
{
	if(options & __LINE_CNT) 
		printf("%ld\t",lines);
	if(options & __WORD_CNT) 
		printf("%ld\t",words);
	if(options & __CHAR_CNT) 
		printf("%ld\t",chars);
	printf("%s\n",strlwr(name));
}

void print_t_count()
{
	if(options & __LINE_CNT) 
		printf("%ld\t",t_lines);
	if(options & __WORD_CNT) 
		printf("%ld\t",t_words);
	if(options & __CHAR_CNT) 
		printf("%ld\t",t_chars);
	printf("total\n");
}

int file_count(char *name)
{
FILE *fp;
int c, last_char = ' ';

	if(NULL == name) {
		fp = stdin;
	} else {
		if(NULL == (fp = fopen(name,"r"))) {
			perror(name);
			exit(1);
		}
	}

	while(0 < fread(&c, sizeof(char),1,fp)) {
		++chars;
		if(isspace(c)) {
			if(!isspace(last_char)) 
					++words;
		}
		if(c == '\n') 
			++lines;
		last_char = c;
	}
	fclose(fp);

	return(0);
}
		
