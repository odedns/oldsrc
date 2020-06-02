
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <errno.h>

#include "syncsort.h"



void print_file(char *pFile);
int check_stat(int stat);

int error_number;

int main(int argc , char **argv)
{
char *pInput;
char *pOutput;
char *stream_ptr , Line[420];
unsigned stream_len;
int status;

	printf("argc = %d\n",argc);
	if(argc < 3) {
		printf("usage sync <infile> <outfile>\n");
		return(1);
        }
	pInput = argv[1];
	pOutput = argv[2];
	print_file(pInput);

	sprintf(Line ,
       "/infile %s /outfile %s /workspace /usr/syncsort /fields zip 1 char 1 fascii /keys zip /end",
        pInput,pOutput);
	stream_ptr = Line;
	stream_len = strlen(stream_ptr);

 
	printf("sync line : %s\n",stream_ptr);
	status = sync_sort(stream_ptr,&stream_len,0,0,0,0);
	error_number = errno;
	status = check_stat(status);
	if(SYNC_SUCCESS != status) {
		fprintf(stderr,"Error in sort : stat is %d\n",status);
		exit(2);
        }

	print_file(pOutput);





}


void print_file(char *pFile)
{
FILE *fp;
char Line[100];
int line_num = 0;

	fp = fopen(pFile,"r");
	if(NULL == fp) {
		fprintf(stderr,"can't open file %s\n",pFile);
		exit(1);
        }
        while(NULL != fgets(Line,80,fp)) {
                ++line_num;
		printf("Line[%d] = %s\n",line_num,Line);
        }
	fclose(fp);

}

int check_stat(int stat)  
{

	switch(stat) { 
		case SYNC_SUCCESS : 
			if(error_number == 0) 
				printf("sysncsort ended successfully\n");
			else 
				printf("%s %s %d\n",
				"syncsort ended with errors",
				"error is : ",
				error_number);
			break;
		case SYNC_ERROR :
			printf("syncsort completed output may be incorrect\n");
			printf("error is %d\n",error_number);
			break;
		default :
			printf("syncsort completed prematurely");
			printf("error is %d\n",error_number);
			break;
	}
	return(error_number);

}
