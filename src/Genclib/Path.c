
#include <stdio.h>
#include <ctype.h>


#include "path.h"

/* split a drive letter from a path
 * return 0 if path contains a drive letter
 * return non-zero otherwise
 */
int path_split_drive(char *path, char *drive)
{
int rv = 1;

	*drive =  '\0';;
	if(NULL == path)
		return(1);

	if(!IS_SEPARATOR(*path)) {
		if(path[1] == ':') {
			*drive = path[0];
			rv = 0;
		}
	}
	return(rv);
}

/* split a filename from a path
 * return 0 if path contains a filename 
 * return non-zero otherwise
 */
int path_split_file(char *path, char *file)
{
int rv = 1;
char *p = path + strlen(path) -1;

	*file =  '\0';
	if(NULL == path)
		return(1);

	
	while(p > path) {
		if(IS_SEPARATOR(*p) || *p == ':') { 
			++p;
			break;
		}
		--p;
	}
	
	if(p > path) {
		strcpy(file,p);
		rv = 0;
	}
	return(rv);
}

/* split a directory path from a path
 * return 0 if path contains a directory path
 * return non-zero otherwise
 */
int path_split_path(char *path, char *pname)
{
int rv = 1, len;
char *p = path + strlen(path) -1;

	*pname =  '\0';
	if(NULL == path)
		return(1);

	while(!IS_SEPARATOR(*p) && p > path ) {
		--p;
	}
	len = p - path;

	if(len) {
		memcpy(pname,path,len);
		pname[len] = '\0';
		rv = 0;
	}
	return(rv);
}

#ifdef _TEST
main()
{
char s[122];
char t[122];

	printf("enter s : ");
	gets(s);

	if(!path_split_drive(s,t)) {
		printf("DRIVE = %c\n",*t);
	}
	if(!path_split_file(s,t)) {
		printf("FILE = %s\n",t);
	}
	if(!path_split_path(s,t)) {
		printf("PATH = %s\n",t);
	}
}
#endif
