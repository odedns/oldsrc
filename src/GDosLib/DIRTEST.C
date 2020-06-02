
/*---- include Files ----------------*/
#include <stdio.h>
#include <sys\stat.h>
#include <time.h>
#include "dirlib.h"

/*----- Function Declarations --------*/
char *file_attr(short mode);
void print_fileinfo(char *name , struct stat *fs , int format);
char *format_time(time_t *time);
void directory(char *s);

/*------ Global Variables ----------*/
int recurs = 0;
int format = 1;
char Usage[] = "usage : ls [-aAlfFrR] files\n";


/*----------- Code -----------------*/
main(int argc , char **argv)
{

    if(argc < 2)   {
       puts(Usage);
       return(1);
    }
    ++argv;
    while(*argv) {

       file_info(*argv);
       ++argv;
    }
    putchar('\n');
}

void directory(char *s)
{
DIR *mydir;
struct direct *entry;
struct stat f_stat;
char s1[80] ;


    mydir = opendir(s);
    printf("directory of : %s\n" ,s);

    while((entry = readdir(mydir)) != NULL)  {
	 sprintf(s1 , "%s\\%s", s,entry->d_name);
	 file_info(entry->d_name);
	 /*
	 stat(s1 , &f_stat);

	 if(f_stat.st_mode & S_IFDIR)
	    strcat(entry->d_name,"/");

	 print_fileinfo(entry->d_name , &f_stat ,format);
	   */
    }
    putchar('\n');
}    /* directory */

/*------- Format file attribute to string -----*/
char *file_attr(short mode)
{
static char Str[5];
int i = 0;

    ( (mode & S_IFDIR) ?   Str[i++] = 'd' : (Str[i++] = '-') );
    ( (mode & S_IREAD) ?  Str[i++] = 'w' : (Str[i++] = '-') );
    ( (mode & S_IWRITE) ?  Str[i++] = 'r' : (Str[i++] = '-'));

    return(Str);
}
/*------ Print File Info according to Format ---*/
void print_fileinfo(char *name , struct stat *fs , int format)
{
char *p ,*p1;

    if (format == 0) {
	printf("%-20s", name);
    } else {
	p = file_attr(fs->st_mode);
	printf("%-8s" , p);
	p1 = ctime(&fs->st_ctime);
	printf("%-20s" , format_time(&fs->st_ctime));
	printf("%-10ld", fs->st_size);
	printf("%-20s\n", name);
    }
}
/*------ Format File Date Time ------*/
char *format_time(time_t *time)
{
struct tm *t;
static char s[40];

	 t = localtime(time);
	 strftime(s,20 , "%d-%m-%Y %H:%M", t);
	 return(s);
}

/*----- Get File Info -------------*/
int file_info(char *name)
{
struct stat fs;

	 stat(name , &fs);
	 if((fs.st_mode & S_IFDIR) &&
	    strcmp(name,".") && strcmp(name,"..") ) {
	     directory(name);
	 } else {
	     if(fs.st_mode & S_IFDIR) {
		 strcat(name,"/");
	     }
	     print_fileinfo(name , &fs ,format);
	 }

    return(0);

}