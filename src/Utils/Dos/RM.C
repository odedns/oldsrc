/*------------------------------------------------------------------------*/
/*   Module       :   rm.exe                                              */
/*   File         :   rm.c                                                */
/*   Date         :   04/02/1995                                          */
/*   Description  :   rm for MSDOS - a UNIX like rm utility.              */
/*   Author       :   Oded Nissan                                         */
/*                                                                        */
/*   History      :                                                       */
/*------------------------------------------------------------------------*/
/*   Date         |   Description                                         */
/*------------------------------------------------------------------------*/
/*   04/02/1995   |   Initial Release.                                    */
/*                |                                                       */
/*                |                                                       */
/*------------------------------------------------------------------------*/

/* Unix like rm program   */
/* include files *\
\* ------------- */

#include <stdio.h>
#include <string.h>
#include <dos.h>
#include <dir.h>
#include <fcntl.h>
#include <sys\stat.h>
#include "misc.h"
#include "llist.h"

/* Macros *\
\* ------ */
#define IS_DIR(a)        (FA_DIREC & a)
#define IS_HIDDEN(a)     (FA_HIDDEN &a )
#define IS_SYSTEM(a)     (FA_SYSTEM & a)
#define IS_ARCH(a)       (FA_ARCH & a)
#define IS_RDONLY(a)     (FA_RDONLY & a)

/* functions *\
\* --------- */

void help();
void clean_up();
int rm(char *source);
int is_root(char *s);
int delete_file(char *source);
int delete_dir(char *source);


/* global vars *\
\* ----------- */

char Msg[] = " *** Unix style rm utility for Dos version 1.0 ***\n \
	  By Oded Nissan  22-03-1995";
int attrib = FA_DIREC | FA_ARCH;
int verbose = 0 , recurse = 0, force = 0, prompt = 0, emulate = 0,
    quiet =0;

LLIST *ll_p = NULL;

/* Code *\
\* ---- */

int main(int argc , char **argv)
{
char c;
extern int optind;
int stat = 0;

	while( (c = getopt(argc,argv,"virfhnq")) != EOF) {
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
	  case 'f' :
		      force    = 1;
		      break;
	  case 'h' :  help();
		      return(1);
	  case 'n' :
		      emulate = 1;
		      break;
	  case 'q' :
		      quiet = 1;
		      verbose = 0;
		      force = 1;
		      break;
	  default  :
			  printf("usage: rm [-virfhn] source target \n");
		      return(2);
       }
    }

    if(argv[optind] == NULL)
	program_msg("rm: no filename given");

	while(argv[optind]) {
	   stat = rm(argv[optind]);
	   ++optind;
    }
    clean_up();
	 return(stat);
}  /**** main ****/


void help()
{
	printf("%s\n\n%s%s%s%s%s%s%s\n",Msg,
		"usage: rm [-virfnh] files....\n\n",
		"-v 	- verbose.\n",
		"-i 	- prompt before overwritting\n",
		"-r      - recurse. remove all subdirectories\n",
		"-f      - force. override protection\n",
		"-n	- emulate. do not remove files\n",
		"-h 	- print this help text\n");

}

int rm(char *source)
{
struct ffblk f1;
int stat = 0;
char *pFile;

    if(is_root(source))
	return(stat = delete_dir(source));

    if(findfirst(source,&f1,attrib))
		program_msg(" %s : file not found",strlwr(source));

    if(IS_DIR(f1.ff_attrib))  {
		 pFile = strdup(source);
		 ll_p = llist_insert_after(ll_p, pFile);
		 stat = delete_dir(source);
	 } else {
	stat = delete_file(source);
	 }

	 return(stat);
}

int delete_file(char *source)
{
struct ffblk f;
int stat = 0;
char Temp[200];

	if(findfirst(source,&f,attrib)) {
		 program_msg("%s : not found",strlwr(source));
		 return(1);
	}

	if(IS_HIDDEN(f.ff_attrib) ||
		IS_RDONLY(f.ff_attrib)) {
		if(!force)  {
			program_msg("%s : no permission",strlwr(source));
			return(1);
		} else {
			 chmod(source, S_IWRITE);
		}
	 }
	 sprintf(Temp,"remove %s ? y/n",strlwr(source));
	 if(prompt && !yorn(Temp,NO))
		  return(2);

	 if(verbose) {
		fprintf(stdout,"removing -> %s\n",strlwr(source));
	  }
	  if(!emulate) {
		  stat = unlink(source);
		  if(stat)
				perror("rm");
	  }
	return(stat);

}

int delete_dir(char *source)
{
struct ffblk f;
int stat = 0;
char src[MAXPATH] ,Temp[MAXPATH];
char *pFile;
static LLIST *head , *h;
static int flag = 1;

	  sprintf(src,"%s\\*.*",source);
	 if(-1 == (stat= findfirst(src , &f,attrib)) ) {
		program_msg("%s : directory is empty",src);
		return(1);
	 }


	  /* loop over source dir and remove files */
	  while(!stat) {
	if(strcmp(f.ff_name,".") && strcmp(f.ff_name,"..")) {
		  /* if directory */
		 if(IS_DIR(f.ff_attrib)) {
			 sprintf(Temp,"%s\\%s",source,f.ff_name);
			pFile = strdup(Temp);
			ll_p = llist_insert_after(ll_p, pFile);
		 } else {
		  sprintf(Temp,"%s\\%s",source,f.ff_name);
		  delete_file(Temp);
		 }

	 }
	 /* get next file */
	  stat = findnext(&f);

   } /* end while */

     if(flag) {
	  h = head = llist_get_first(ll_p);
	  flag = 0;
     } else {
	 head = llist_get_next(head);
    }

     /* recurs subdirs */
    if(recurse) {
      while(NULL != head ) {
		  pFile = head->data;
		  delete_dir(pFile);
      }
   }

     return(0);
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

void clean_up()
{
LLIST *t, *t1;
char *s;

	t = llist_get_last(ll_p);
	while(t != NULL) {
		if(recurse && force && !emulate) {
		        if(verbose)
				fprintf(stdout,"removing dir: %s\n",
				        (char *)t->data);
			rmdir((char *)t->data);
		}
		free(t->data);
		t1 = t;
		llist_delete_item(t1,(void**)&s);
		free(s);
		t = llist_get_prev(t);
	}
}
