#include <stdio.h>
#include <stdlib.h>
#include <ar.h>

void list_archive_file(char *file); 
void read_archive(FILE *fp);
char *str_trail(char *s,int size );
void help();

int listing = 0 , supress = 0;

main(int argc , char **argv)
{
FILE *fp;
extern int optind;
int c;


	if(argc < 2) {
		help();
		return(1);
	}
     

	while(EOF !=  (c = getopt(argc , argv,"hlq"))) {
		switch(c) { 
			case 'h' :	help();
					break;
			case 'l' :	listing = 1;
					break;
			case 'q' :	supress = 1;
					break;
			default  :
					break;
		}
	}
	while(argv[optind] != NULL) {
		list_archive_file(argv[optind]);
		++optind;
	}
	
}




void list_archive_file(char *file) 
{
FILE *fp;
	fp = fopen(file,"r");
	if(NULL == fp) {
		perror("open");
		exit(1);
	}
	if(!supress) {
		printf("members of %s :\n",file);
	}
	read_archive(fp);
	fclose(fp);
}


void read_archive(FILE *fp)
{
struct ar_hdr header;
char mag_str[9];
int stat;
long file_size;

	stat = fread(mag_str,1,SARMAG,fp);
	if(stat != SARMAG) {
		perror("fread");
		exit(1);
	}
	if(memcmp(mag_str,ARMAG,SARMAG)) {
		printf("file is not a valid archive file\n");
		exit(1);
	}
	while(1 == 
	    ( stat = fread(&header,sizeof(struct ar_hdr),1,fp))) {
		if(header.ar_name[0] != '/') {
			printf("%-20s",
			str_trail(header.ar_name,16));
			/* print member files size */
			if(listing) { 
				printf("\t%-20s",
                        	str_trail(header.ar_size,10));
			}
			putchar('\n');
		}
		file_size = atol(header.ar_size);
		fseek(fp,file_size,SEEK_CUR);
	}
	
}


char *str_trail(char *s,int size )
{
char *p = s + size-1;


	while(isspace(*p)) {
		*p-- = '\0';
	}
        if(*p == '/') *p= '\0';
	return(s);
}

	
void help()
{


	printf("%s\n\t%s\n%s\n%s\n%s\n%s\n",
               "***  arlist - list members of archive file ***",
                "By Oded Nissan 13-08-96\n",
                "Usage : arlist -hlq [file....]",
                "-h\t-\tprint this help text",
	  	"-l\t-\tlist archive members size",
                "-q\t-\tquite mode supress messages"); 

}
