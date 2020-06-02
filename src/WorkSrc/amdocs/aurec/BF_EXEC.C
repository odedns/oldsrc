
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>
#include <unistd.h>

#define BADFORK  -1
#define BADEXEC  -2 
#define BADWAIT  -3
#define BADPATH  -4
#define BADMALLOC -5


int StrCountWords(char *pStr);
char **StrConvert(char *pStr);
int BF_execvp(char *pCommand);



main()
{
char Cmd[120];
int stat = 0;

	printf("Enter Command : ");
	gets(Cmd);
	stat = BF_execv(Cmd);

	printf("stat = %d\n",stat);
}


/* first call to fork() return 0 so control is passed to the exec()
   part. after the exec() fork is executed again and this time returns 1.
   so the parent executed a wait() to wait for the child to complete.
   If no wait is executed then the child runs in the background. 
   The parent may communicate with the child through pipes mailboxes etc.
*/
   

/* exec with var args. the exec() routine does not search for argv[0] 
   using the path  */

int BF_execv(char *pCommand) 
{
int status , child_status;
char **argv;

	argv = StrConvert(pCommand);


	if((status = vfork()) != 0) {
		/* after exec control resumes here */
		if(status < 0 ) {
			printf("Parent : child Failed\n");
			return(BADFORK);
                } else {
			printf("parent - Waiting for Child\n");
			if((status = wait(&child_status)) == -1) {
				printf("Parent : Wait Failed\n");
 				return(BADWAIT);
			}
		}
	} else {
		/* vfork returns 0 */
		/* execute command after initial vfork */
		printf("Parent : Starting Child\n");
		if((status = execvp(argv[0],argv)) == -1) {
			printf("Parent exec on child Failed\n");
			return(BADEXEC);
		}
	}
	return(status);
}





int StrCountWords(char *pStr)
{
int charFlag = 0;
int wordCount = 0;

	while(*pStr) {
		if(isspace(*pStr++) && charFlag) {
			++wordCount;
            /* eat white space */
			while(isspace(*pStr) && *pStr)  {
				++pStr;
			}
		} else {
			charFlag = 1;
		}
		if(*pStr == '\0') ++wordCount;
	}
	return(wordCount);
}

char **StrConvert(char *pStr)
{
char **argv, *pBuff;
int Offset = 0, count;


	if(!(count = StrCountWords(pStr)) ) {
		return(NULL);
        }
        
	argv = (char **)calloc((count +1) ,sizeof(char*));

	pBuff = pStr;
	while(*pBuff) {
		while(isspace(*pBuff)) {
			*pBuff = '\0';
			++pBuff;
		}
		argv[Offset++] = pBuff;
		/* skip rest of word  */
		while(*pBuff && !isspace(*pBuff)) {
			++pBuff;
		}
	}
	argv[Offset] = NULL;
	return(argv);
}
