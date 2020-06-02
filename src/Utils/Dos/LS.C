/*-----------------------------------------------------------------------*\
 * ls for MSDOS - A Unix Like Dir Utility !                              *
 * By : Oded Nissan                                                      *
 * Date : 02/03/1994.                                                    *
 *                                                                       *
 *                                                                       *
 *                                                                       *
 *                                                                       *
 *                                                                       *
 *                                                                       *
 *                                                                       *
 *                                                                       *
 *                                                                       *
 *                                                                       *
\*-----------------------------------------------------------------------*/

/*---- include Files ----------------*/
#include <stdio.h>
#include <dir.h>
#include <dos.h>
#include <string.h>

#include "misc.h"

/*--------------Typedefs ----------------*/
typedef struct {
		 unsigned sec  : 5;
		 unsigned min  : 6;
		 unsigned hour : 5;
		 } fileTime_t;
typedef struct {
		 unsigned day   : 5;
		 unsigned month : 4;
		 unsigned year  : 7;
		} fileDate_t;

union fDateTime {
		 unsigned int num;
		 fileTime_t fTime;
		 fileDate_t fDate;
		};

/*------------- Macro Definitions -------*/
#define IS_DIR(a)        (FA_DIREC & a)
#define IS_HIDDEN(a)     (FA_HIDDEN &a )
#define IS_SYSTEM(a)     (FA_SYSTEM & a)
#define IS_ARCH(a)       (FA_ARCH & a)
#define IS_RDONLY(a)     (FA_RDONLY & a)

#define MAXDIRS           (20)


/*----- Function Declarations --------*/
struct ffblk *readdir(char *dirname);
char *file_attr(char attrib);
int print_fileinfo(char *name , struct ffblk *fs , int format);
int is_root(char *name);
char *get_file_date(int date);

/*------ Global Variables ----------*/
int format = 0;
int recurs = 0;
int single_col = 0;
int dir_slash = 0;
int size = 0;
int dir_flag = 0;
char *Usage = "usage : ls [-aAcdlfFrRs] files\n";
char Msg[] = " *** Unix style ls utility for Dos version 1.0 ***\n \
     By Oded Nissan  02-03-1994";
int attrib = FA_RDONLY | FA_DIREC | FA_ARCH;
unsigned long tot_size = 0;

/*----------- Code -----------------*/
int main(int argc , char **argv)
{
int stat = 0;
char *p , currdir[MAXPATH];
char c;
extern int optind;

       puts(Msg);
       getcwd(currdir , MAXPATH);

    /* loop over command line options */
    while( (c = getopt(argc,argv,"AacdfFRrls")) != EOF)
    {
	switch(c) {
	    case 'A' :
	    case 'a' :  attrib = attrib | FA_HIDDEN | FA_SYSTEM;
			break;
	    case 's'  : size = 1;
			break;
	    case 'R' :
	    case 'r' :  recurs = 1;
			break;
	    case 'f' :
	    case 'F' :  dir_slash = 1;
			break;
	    case 'c' :  single_col = 1;
			break;
	    case 'l' :  format = 1;
			dir_slash = 1;
			attrib = attrib | FA_HIDDEN | FA_SYSTEM;
			break;
	    case 'd' :  dir_flag = 1;
			attrib =  FA_DIREC;
			break;
	    default :   printf("ilegal option %c\n" , c);
			puts(Usage);
			return(1);
	} /* switch */

    } /* while */

    /* if no params read current directory */
    if(!argv[optind]) {
	if(is_root(currdir))
	   directory(currdir);
	else
	   get_file_info(".");

    } else {

       /* loop over command line parameter  */
       while(argv[optind]) {
	   p = strlwr(argv[optind++]);

	   /* handle root directory */
	   if(is_root(p)) {
	       directory(p);
	   } else {
	       /* get file info */
	       stat = get_file_info(p);
	   }
       }  /* while */
    }

    if(stat)
       printf("\n%s not found\n" , p);

    putchar('\n');

    if(size)
       printf("\nTotal size of listed files : %ld\n" , tot_size);

    return(0);
}

/*------ Reads files in Directory ----*/
int directory(char *name)
{
char *p;
struct ffblk *f;
char *dirs[MAXDIRS];
char Temp[80];
int i = 0;

    printf("\n\n%s :\n" ,name);
    while(NULL != (f = readdir(name)) ) {
       p = strlwr(f->ff_name);
       if(IS_DIR(f->ff_attrib) && recurs &&
	  strcmp(f->ff_name,".") && strcmp(f->ff_name,"..")) {
	  if(strcmp(name , "\\"))
	      sprintf(Temp , "%s\\%s",name,f->ff_name);
	  else
	      sprintf(Temp , "\\%s",f->ff_name);

	  dirs[i++] = strdup(Temp);
       }
       print_fileinfo(p ,f ,format);

    }
    dirs[i] = NULL;
   /* recurs subdirs */
   if(recurs) {
      for(i = 0; dirs[i]; ++i) {
	 directory(dirs[i]);
      }
   }

   /* clean up */
   i = 0;
   while(dirs[i])
      free(dirs[i++]);

   return(0);
}

/*------------- reads a directory ---------------*/

struct ffblk *readdir(char *dirname)
{
static int Flag = 0;
static struct ffblk f;
static char filespec[80];
int stat = 0;

    if(!Flag) {

      /* handle root dir */
      if(is_root(dirname)) {
	 if(*dirname == '\\')
	   strcpy(filespec,"\\*.*");
	 else   /* drive letter only */
	   sprintf(filespec,"%s\\*.*" , dirname);
      } else {
	sprintf(filespec , "%s\\*.*", dirname);
      }

      stat = findfirst(filespec , &f , attrib);
      Flag = 1;
    } else {
      stat = findnext(&f);
    }

    if (stat) Flag = 0;

    return(stat ? NULL : &f);
}
/*------- format file attribute string ---------*/

char *file_attr(char attrib)
{
static char Str[8];
int i = 0;

    ( IS_DIR(attrib) ?   Str[i++] = 'd' : (Str[i++] = '-') );
    ( IS_RDONLY(attrib) ?  Str[i++] = '-' : (Str[i++] = 'r') );
    Str[i++] = 'w';
    ( IS_ARCH(attrib) ?  Str[i++] = 'a' : (Str[i++] = '-'));
    ( IS_HIDDEN(attrib) ?  Str[i++] = 'h' : (Str[i++] = '-'));
    ( IS_SYSTEM(attrib) ?  Str[i++] = 's' : (Str[i++] = '-'));

    Str[i] = '\0';
    return(Str);
}
/*------- print file info according to format-----------*/

int print_fileinfo(char *name , struct ffblk *fs , int format)
{
char *p ;
char *p1;

    if(dir_flag && fs->ff_attrib != attrib)
       return(1);

    if(dir_slash && IS_DIR(fs->ff_attrib))
       strcat(name,"/");

    if (format == 0) {
	printf("%-20s", name);
	if(single_col)
	  putchar('\n');
    } else {
	p = file_attr(fs->ff_attrib);
	p1 = get_file_date(fs->ff_fdate);
	printf("%-8s" , p);
	printf("%-20s",p1);
	printf("%-10ld", fs->ff_fsize);
	printf("%-20s\n", name);

    }

    tot_size+= fs->ff_fsize;

    return(0);
}

int get_file_info(char *name)
{
struct ffblk fs;
int stat = 0;

    if(0 != (stat = findfirst(name , &fs ,attrib)) ) {
	 return(1);
    }
	if(!(FA_DIREC & fs.ff_attrib)) {
	   print_fileinfo(name , &fs , format);
	} else {
	   directory(name);
	}
    return(stat);

}

/*----------- Format file date from ffblk struct ---------*/
char *get_file_date(int date)
{
union fDateTime u;
static char s[21];

    u.num = date;
    sprintf(s,"%02d/%02d/%4d\t%02d:%02d",
	    u.fDate.day , u.fDate.month,(u.fDate.year+1980),
	    u.fTime.hour,u.fTime.min );

    return(s);
}

int is_root(char *s)
{
char *p = s+ strlen(s) -1;

    if(*p == ':') return(1);

    if(*p == '\\' && (p == s || *(p-1) == ':')) {
	*p ='\0';
	return(1);
    }

    return(0);
}
