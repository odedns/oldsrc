#include <stdio.h>


#define PSIZE (16)


void DumpLine(unsigned char *s , int len)
{
static long offset = 0;
int i;

	printf("%08X |",offset);
	offset+= len;
	for(i=0; i < len; ++i) {
		printf("%02X ",s[i]);
	}

	if(len < PSIZE) {
		for(i=len; i < PSIZE; ++i) {
			printf("   ");
		}
	}
	putchar('|');


	for(i = 0; i < len; ++i) {
		if(isprint(s[i])) 
			printf("%c",s[i]);
		else 
			putchar('.');
	}
	putchar('\n');
}

int DumpFile(char *filename)
{
FILE *fp;
unsigned char s[PSIZE+1];
int len;


        if(NULL == filename) {
		fp = stdin;
	} else {

		if(NULL == (fp = fopen(filename,"rb"))) {
			return(2);
		}
	}
	
	while(0 <  (len = fread(s,1,PSIZE,fp)) ) {
		DumpLine(s,len);
	}

	fclose(fp);
	return(0);
}

void help()
{
char Msg[] = " *** File Dump utility for MsDos version 1.0 ***\n \
	  By Oded Nissan  05-08-1996\n";
	printf("\n%s\n%s\n\n%s\n",Msg,
		"usage : fdump [-h] [file...]",
		"-h 	- print this help text");
}



int main(int argc , char **argv)
{
char c, s[128];
extern int optind;
int stat = 0;

	while( (c = getopt(argc,argv,"h")) != EOF) {
	switch(c) {
	  case 'h' :
		      help();
		      return(1);
	  default  :
	  		 
		      help();
		      return(1);
       }
    }


	if(argc < 2) {
		stat = DumpFile(NULL);
		if(stat) {
			sprintf(s,"fdump stdin");
			perror(s);
		}
		return(stat);
	}
	while(argv[optind] != NULL) { 
		printf("\n%s :\n\n",argv[optind]);
		stat = DumpFile(argv[optind]);
		if(stat) {
			sprintf(s,"fdump %s",argv[optind]);
			perror(s);
		}
		++optind;
	}
	return(stat);
}
