 /*----------------------------------------------------------------------*\
 * cp for MSDOS - A Unix Like file copy Utility !                        *
 *                                                                       *
 * By : Oded Nissan                                                      *
 *                                                                       *
 * Date : 04/02/1995.                                                    *
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
/* Unix like cp program   */
/* include files *\
\* ------------- */

#include <stdio.h>
#include <string.h>
#include <dir.h>
#include <dos.h>
#include <io.h>
#include <fcntl.h>
#include <sys/stat.h>
#include "misc.h"
#include "llist.h"

/* Macros *\
\* ------ */
#define BLOCKSIZE        (32000)
#define IS_DIR(a)        (FA_DIREC & a)
#define IS_HIDDEN(a)     (FA_HIDDEN &a )
#define IS_SYSTEM(a)     (FA_SYSTEM & a)
#define IS_ARCH(a)       (FA_ARCH & a)
#define IS_RDONLY(a)     (FA_RDONLY & a)


struct dircp {
	       char *src;
	       char *dest;
	      };

/* functions *\
\* --------- */

int file_copy(char *source , char *target);
int cp(char *source , char *target);
void help();
int copy_dir(char *source , char *target);
int copy_file(char *source ,char *target);
void error(char *s);
char *getFileName(char *path);


/* global vars *\
\* ----------- */

char Msg[] = " *** Unix style cp utility for Dos version 1.0 ***\n \
	  By Oded Nissan  04-02-1995";
int attrib = FA_DIREC | FA_ARCH;
int verbose = 0 , recurse = 0, prompt = 0, preserve = 0, emulate = 0,
    quiet =0;

/* Code *\
\* ---- */

int main(int argc , char **argv)
{
char *Target,c;
extern int optind;
int stat = 0;

    if(argc < 3 ) {
	help();
	return(1);
    }

    while( (c = getopt(argc,argv,"ihprvnq")) != EOF) {
	switch(c) {
	  case 'v' :
		      verbose = (!quiet ? 1 : 0);
		      break;
	  case 'i' :
		      prompt = 1;
		      break;
	  case 'r' :
		      recurse = 1;
		      break;
	  case 'p' :
		      preserve = 1;
		      break;
	  case 'h' :  help();
		      return(1);
	  case 'n' :
		      emulate = 1;
		      break;
	  case 'q' :
		      quiet = 1;
		      verbose = 0;
		      break;
	  default  :
		      printf("usage: cp [-virphn] source target \n");
		      return(2);
       }
    }

    if(argv[optind] == NULL)
	error("no filename given\n");

    Target = argv[argc - 1];
    while(argv[optind] && argv[optind] != Target) {
       stat = cp(argv[optind], Target);
       ++optind;
    }
    return(stat);
}  /**** main ****/


int file_copy(char *source , char *target)
{
struct ftime ft;
int fd1 , fd2;
char buffer[BLOCKSIZE];
int bytes1 ,bytes2, status = 0;

    /* emulate copy */
    if(emulate)	return(status);

    if( -1 == (fd1 = open(source , O_RDONLY | O_BINARY)) )
	 return(1);
    if( -1 == (fd2 = open(target,O_CREAT|O_WRONLY | O_TRUNC| O_BINARY,
                          S_IREAD | S_IWRITE)))
	 return(2);

    while( (bytes1 = read(fd1 , buffer , BLOCKSIZE)) > 0) {
	   if((bytes2 = write(fd2 , buffer , bytes1)) == -1) {
		 status = 3;
		 break;
	   }
	   if(bytes1 != bytes2) {
		status = 4;
		break;
	   }
    }

    if(preserve) {
	getftime(fd1,&ft);
	setftime(fd2,&ft);
    }
    close(fd1);
    close(fd2);
    return(status);
}

void help()
{
	printf("%s\n\n%s%s%s%s%s%s%s\n",Msg,
		"usage: cp [-virpnh] source target \n\n",
		"-v 	- verbose.\n",
		"-i 	- prompt before overwritting\n",
		"-r      - recurse. copy all subdirectories\n",
		"-p	- preserve original file timestamp\n",
		"-n	- emulate. do not copy files\n",
		"-h 	- print this help text\n");

}

int cp(char *source , char *target)
{
struct ffblk f1;
int stat = 0;

    if(is_root(source))
		return(stat = copy_dir(source , target));

    if(findfirst(source,&f1,attrib))
		error("source file not found");

    if(IS_DIR(f1.ff_attrib))
		stat = copy_dir(source , target);
	else
		stat = copy_file(source ,target);

    return(stat);
}

int copy_file(char *source , char *target)
{
struct ffblk f1;
int stat = 0;
char dest[MAXPATH], *pFname;


    if(findfirst(target,&f1,attrib)) {
	  if(is_root(target)) {
	    pFname = getFileName(source);
	    sprintf(dest, "%s\\%s",target,pFname);
	    /*
		sprintf(dest,"%s\\%s",target,source);
		*/
	  } else
		strcpy(dest,target);

     } else {  /* file exists */
	/* if target is directory create filename */
	if(IS_DIR(f1.ff_attrib)) {
	    pFname = getFileName(source);
	    sprintf(dest, "%s\\%s",target,pFname);
	} else {
	   /* prompt for overwrite */

	   if(prompt && !yorn("file exists overwrite ? y/n",NO))
	       return(-1);
	   strcpy(dest,target);
	}
     }

    if(verbose)
	fprintf(stdout,"copying: %s -> %s\n",strlwr(source),strlwr(dest));

    /* copy the file */
	stat = file_copy(source ,dest);
   return(stat);

}

int copy_dir(char *source , char *target)
{
struct ffblk f1,f2;
int stat = 0;
char src[MAXPATH], Temp[MAXPATH], Temp1[MAXPATH];
struct dircp *dircp_p;
static LLIST *ll_p = NULL, *head , *h;
static int flag = 1;

     sprintf(src,"%s\\*.*",source);
     if(-1 == (stat= findfirst(src , &f1,attrib)) )
	error("directory is empty");

     /* if directory doesn't exist - create it ! */
     if(-1 == findfirst(target,&f2,attrib)) {

	 if(!is_root(target) && !emulate) {
	    if(-1 == mkdir(target))
	       error("cannot create directory");
	 }

     }

     /* loop over source dir and copy files */
     while(!stat) {
	if(strcmp(f1.ff_name,".") && strcmp(f1.ff_name,"..")) {
	     /* if directory */
	    if(IS_DIR(f1.ff_attrib)) {
		    sprintf(Temp,"%s\\%s",source,f1.ff_name);
		    dircp_p = (struct dircp *)malloc(sizeof(struct dircp));
		    dircp_p->src = strdup(Temp);
		    sprintf(Temp,"%s\\%s",target,f1.ff_name);
		    dircp_p->dest = strdup(Temp);
		    ll_p = llist_insert_after(ll_p, dircp_p);
	    } else {
	     sprintf(Temp,"%s\\%s",source,f1.ff_name);
	     sprintf(Temp1,"%s\\%s",target,f1.ff_name);
		 copy_file(Temp, Temp1);
	    }

	}
	/* get next file */
        stat = findnext(&f1);
	
     }

     if(flag) {
	  h = head = llist_get_first(ll_p);
	  flag = 0;
     } else {
	 head = llist_get_next(head);
    }

     /* recurs subdirs */
    if(recurse) {
      while(NULL != head ) {
	 dircp_p = head->data;
	 copy_dir(dircp_p->src,dircp_p->dest);
      }
   }

   /* clean up */

     while(h != NULL) {
		dircp_p = h->data;
		free(dircp_p->src);
		free(dircp_p->dest);
		h = llist_delete_item(h,&dircp_p);
		
	 }

     return(0);
}

void error(char *s)
{
   fprintf(stderr,"cp error : %s\n",s);
   exit(1);
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

char *getFileName(char *path)
{
char *p = path + strlen(path) -1;

	while(*p != '\\' && p != path) {
	    --p;
	}
	return(*p == '\\' ? ++p : p);
}

