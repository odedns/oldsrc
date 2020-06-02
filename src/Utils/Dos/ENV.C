/*-----------------------------------------------------------------------*\
 * ENV for MSDOS - A Unix Like ENV Utility !			         *
 * By : Oded Nissan                                                      *
 * Date : 22/04/1995.							 *
 *                                                                       *
 *                                                                       *
 *                                                                       *
 *                                                                       *
\*-----------------------------------------------------------------------*/


#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void print_env();
void help();

char Msg[] = " *** Unix style env utility for Dos version 1.0 ***\n \
	  By Oded Nissan  22-04-1995";

int main(int argc , char **argv)
{
int i = 1;
char *p;
int ret_stat = 0;

	

	if(argc == 1) {
		print_env();
	} else {
		if(!strcmp("-h",argv[1])) {
			help();
			return(2);
		}
		while(argv[i]) {
			p = strupr(argv[i]);
			if(NULL != (p = getenv(p)) ) {
				printf("%s\n",p);
			} else {
			     fprintf(stderr,
				     "%s not in environment\n",
				      argv[i]);
			     ret_stat = 1;
			}
			++i;
		}
	}
	return(ret_stat);
}

void print_env()
{
int i = 0;
extern char **environ;

     printf("%s\n\n",Msg);
     while(environ[i]!= NULL){
	printf("%s\n",environ[i]);
	++i;
     }

}

void help()
{
	fprintf(stdout,"%s\n%s\n",
		Msg,
		"usage: env [varibables...]");
}
