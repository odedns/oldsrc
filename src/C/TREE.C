#include <stdio.h>
#include <dir.h>
#include <string.h>
#include <dos.h>


/* function declarations  */
void get_tree( char *pathname);
char *get_parent(char *path);



main(int argc , char **argv)
{
char path[MAXPATH];

    if(argc < 2) {
	printf("Enter Root Dir : ");
	gets(path);
    } else
       strcpy(path , argv[1]);

    get_tree(path);

}     /***    main ***/


void get_tree( char *pathname)
{
int stat;
struct ffblk f;
static char currpath[MAXPATH] ;
char *p;

    strcpy(currpath , pathname);
    strcat(pathname ,"\\*.*" );
    stat = findfirst(pathname ,&f ,0x10 );

    while(!stat && f.ff_attrib == FA_DIREC) {
	    if((strcmp(".",f.ff_name) && strcmp(".." , f.ff_name))) {
		 printf("directory in %s = %s \n", pathname ,f.ff_name);

		 *(pathname + strlen(currpath)+1) = '\0';
		 strcat(pathname , f.ff_name);
		 get_tree(pathname);
	    }
	    stat = findnext(&f);
    }

    /* remove extention */
    p = strstr(pathname , "\\*.*");
    if (p != NULL)
	*p = '\0';
    else  {
    /* if no extention remove '\\'  */

	 p = pathname + (strlen(pathname) -2);
	*p = '\0';
    }

    get_parent(pathname);


}  /** get tree **/



char *get_parent(char *path)
{
char *t;

    t = &path[strlen(path) -1];

    while(*t != '\\')
       --t;
    if(*t == '\\') {
      ++t;
      *t = '\0';
   }
   return(path);
}