/*------------------------------------------------------------------------*/
/*   Module       :  wls.exe                                              */
/*   File         :  wls.c                                                */
/*   Date         :  31/08/1998                                           */
/*   Description  :  An ls utility for WIN95/NT.                          */
/*   Author       :  Oded Nissan                                          */
/*                   Copyright (c) 1994-1998 Oded Nissan.                 */
/*   History      :                                                       */
/*------------------------------------------------------------------------*/
/*   Date         |   Description                                         */
/*------------------------------------------------------------------------*/
/*   31/08/1998   |   Initial Release Vecrion 1.0                         */
/*------------------------------------------------------------------------*/
#include <stdio.h>
#include <windows.h>

#include "getopt.h"
#include "dstructs.h"

struct tagAttrib {
	DWORD attrib;
	char  c1;
	char  c2;
};

#define IS_DIR(a) ( a & FILE_ATTRIBUTE_DIRECTORY)
#define IS_HIDDEN(a) ( a & FILE_ATTRIBUTE_HIDDEN)
#define MAX_ATTRIBS (9)
static struct tagAttrib attVec[] = {
        { FILE_ATTRIBUTE_DIRECTORY,'d','-'},
	{  0xFFFFFFFF            ,'r','-'},
	{ FILE_ATTRIBUTE_READONLY,'-','w'},
	{ FILE_ATTRIBUTE_ARCHIVE, 'a','-'},
	{ FILE_ATTRIBUTE_COMPRESSED,'c','-'},
	{ FILE_ATTRIBUTE_HIDDEN,'h','-'},
	{ FILE_ATTRIBUTE_NORMAL,'n','-'},
        { FILE_ATTRIBUTE_SYSTEM,'s','-'},
	{ FILE_ATTRIBUTE_TEMPORARY,'t','-'},
	{ FILE_ATTRIBUTE_OFFLINE,  'o','-'}
};


void print_file_info(LPWIN32_FIND_DATA pdata);
char *get_attrib_str(DWORD fAttrib);
char *get_filetime(FILETIME *ftime);
void list_directory(char *name);
void list_file(char *name);
int is_root(char *s);
void remove_trailing_slash(char *s);
void help();

/*
 * command line option flags
 */
#define LONG_FORMAT_FLAG   (0x0001)
#define SHOW_HIDDEN_FLAG   (0x0002)
#define RECURSE_FLAG       (0x0004)
#define DIR_SLASH_FLAG     (0x0008)
#define SHOW_DOTS_FLAG     (0x0010)
#define DIRS_FLAG          (0x0020)
#define SHOW_SIZE          (0x0040)
#define SINGLE_LINE        (0x0080)

static int options = 0;

int main(int argc, char **argv)
{
char *dir;
int c;
extern int optind;



	/* loop over command line options */
	while( (c = getopt(argc,argv,"AaCdfFhlRs1")) != EOF)
	{
		switch(c) {
			case 'A' : options |= SHOW_HIDDEN_FLAG | SHOW_DOTS_FLAG;
				   break;
			case 'a' : options |= SHOW_HIDDEN_FLAG;
				   break;
			case 'C':  options &= ~(SINGLE_LINE);
				   break;
		 	case 'd':  options |= DIRS_FLAG;
				   options &= ~(RECURSE_FLAG);
				   break;
			case 'f' : 
				   options &= ~(RECURSE_FLAG|LONG_FORMAT_FLAG);
				   break;
			case 'F' : options |= DIR_SLASH_FLAG;
				   break;
			case 'h' : help();
				   return(1);
			case 'l' : options |= LONG_FORMAT_FLAG;
				   options &= ~(SINGLE_LINE);
				   break;
			case 'R' : options |= RECURSE_FLAG;
			           break;
			case 's' : options |= SHOW_SIZE;
			           break;
			case '1' : options |= SINGLE_LINE;
			           break;
			default :  printf("ilegal option %c\n" , c);
				   help();
				   return(1);
		} /* switch */

	} /* while */

	/* if no params read current directory */
	if(!argv[optind]) {
		list_directory(".");
	} else {
	/* loop over command line parameter  */
		while(argv[optind]) {
			if(is_root(argv[optind])) {
				list_directory(argv[optind]);
			} else {
				list_file(argv[optind]);
			}
			++optind;
		}  /* while */
	}
	return(0);

} /** main **/

void help()
{
char *copyright = "wls version 1.0\n"
		  "a UNIX like ls utility for WIN95/NT\n"
	          "Copyright (c) 1998 Oded Nissan\n";

char *Usage = "Usage: wls -[aACdfFhlRs1] [files..]\n"
	      "Options:\n"
	      "\t-a	Show hidden files.\n"
	      "\t-A	Show hidden files including (.) and (..).\n"
	      "\t-C	Multi-column output. This is the default.\n"
	      "\t-d	List directory entries only.\n"
	      "\t-f	Turn off the -l -r flags.\n"
	      "\t-F	Append a slash (/) to directory names.\n"
	      "\t-h	Show this help text.\n"
	      "\t-l	List files in long format.\n"
	      "\t-R	Recurse subdirs.\n"
	      "\t-s	List file size in blocks\n"
	      "\t-1	List one entry per line\n";
	fprintf(stderr,"%s\n%s\n",copyright,Usage);
}

void list_file(char *name)
{
HANDLE h;
WIN32_FIND_DATA fdata;

	remove_trailing_slash(name);

	if(strchr(name, '*') || strchr(name,'?')) {
		options |= DIRS_FLAG;
		list_directory(name);
		return;
	}

	h = FindFirstFile(name,&fdata);
	if(INVALID_HANDLE_VALUE == h) {
		printf("%s: not found\n",name);
		exit(1);
	}
	/* 
	 * if file is a directory list
	 * the whole directory.
	 * else just list the file.
	 */
	if(IS_DIR(fdata.dwFileAttributes)) {
		list_directory(name);
	} else {
		print_file_info(&fdata);
	}
}

/*
 * list all files in a directory
 */
void list_directory(char *name)
{
WIN32_FIND_DATA fdata;
HANDLE h;
BOOL b = TRUE;
char dir_name[MAX_PATH];
char temp[MAX_PATH];
char *p;
QUEUE *dq;


	printf("\n%s:\n",name);
	dq = queue_create();

	if(options & DIRS_FLAG) {
		options &= ~(RECURSE_FLAG);
		strcpy(dir_name,name);
	} else {
		sprintf(dir_name,"%s\\*.*",name);
	}

	h = FindFirstFile(dir_name,&fdata);
	if(INVALID_HANDLE_VALUE == h) {
		printf("invalid handle returned\n");
		exit(1);
	}
	while(b) {
		print_file_info(&fdata);
		/* if recurs glag and a dir
		 * save the dir name.
		 */
		if(options & RECURSE_FLAG && 
		   IS_DIR(fdata.dwFileAttributes) && 
		   strcmp(fdata.cFileName,".") && 
		   strcmp(fdata.cFileName,"..")) {
			sprintf(temp,"%s\\%s",name,fdata.cFileName);
			queue_add(dq,strdup(temp));
		}
		
		b = FindNextFile(h,&fdata);
	}

	while(!queue_empty(dq)) {
		queue_remove_front(dq,&p);
		list_directory(p);
	}


}

void print_file_info(LPWIN32_FIND_DATA pdata)
{
	DWORD file_size;
	char fmt[25];
	char fname[MAX_PATH];
	/*
	 * only show hidden files 
	 * if the hidden flag is set.
	 */
	if(!(options & SHOW_HIDDEN_FLAG) && 
	   IS_HIDDEN(pdata->dwFileAttributes)) {
			return;
	}
	if(!(options & SHOW_DOTS_FLAG) &&  
	     ( !strcmp(pdata->cFileName,".") ||
	       !strcmp(pdata->cFileName,".."))) {

	     return;
	}


	strcpy(fname,pdata->cFileName);

	if( options & DIR_SLASH_FLAG &&  IS_DIR(pdata->dwFileAttributes)) {
		strcat(fname,"/");
	}


	file_size = (pdata->nFileSizeHigh * MAXDWORD) + pdata->nFileSizeLow;
	if(options & SHOW_SIZE) {
		printf("%-5d", (file_size / 1024) + 1);
		strcpy(fmt,"%-35s");
	} else {
		strcpy(fmt,"%-40s");
	}
	if(options & LONG_FORMAT_FLAG) {
		printf("%-15s%-15ld%-15s%-20s\n", 
			get_attrib_str(pdata->dwFileAttributes),		
			file_size,
			get_filetime(&pdata->ftCreationTime),
			strlwr(fname));
	} else {
		
		printf(fmt,strlwr(fname));
	}
	if(options & SINGLE_LINE && !(options & LONG_FORMAT_FLAG) ) {
		putchar('\n');
	}
}

/*
 * convert the FILETIME structure
 * into a date string in the format:
 * dd-mm-yyyy.
 * returns a string.
 */
char *get_filetime(FILETIME *ftime)
{
BOOL rv;
SYSTEMTIME st;
static char time_str[12];

	rv = FileTimeToSystemTime(ftime, &st);
	if(!rv) {
		return(NULL);
	}
	sprintf(time_str,"%02d-%02d-%04d",st.wDay,st.wMonth,st.wYear);
	return(&time_str[0]);

}

/* 
 * convert the attribute word into a 
 * string representing the file attributes.
 * returns a string.
 */
char *get_attrib_str(DWORD fAttrib)
{
	static char tmp[10];
	int i;

	for(i=0; i < MAX_ATTRIBS; ++i) {
		tmp[i] = (fAttrib & attVec[i].attrib ? 
				attVec[i].c1 : attVec[i].c2);
	}
	tmp[i] = '\0';

	return(&tmp[0]);
}
		
/*
 * check if s is a root dir.
 * return 1 if root dir
 * 0 otherwise.
 */
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


void remove_trailing_slash(char *s)
{
	char *p = s + strlen(s) - 1;

	if(*p == '\\') {
		*p = '\0';
	}
}
