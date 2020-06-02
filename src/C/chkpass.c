
#include <stdio.h>
#include <crypt.h>
#include <pwd.h>
#include <shadow.h>



int  main(void)
{
	char user[12];
	char pass[12];
	struct passwd *pw;
	char *encpw;
	struct spwd *spw;


	printf("enter user:");
	gets(user);
	printf("enter pass:");
	gets(pass);

	
	pw = getpwnam(user);

	if(NULL == pw) {
		perror("getpwnam");
		exit(1);
	}

	/*
	 * check for shadow file
	 */
	if( *(pw->pw_passwd) == 'x') {
		printf("checking shadow file ..\n");
		spw = getspnam(user);
		if(NULL == spw) {
			perror("getspnam");
			exit(1);
		}
		encpw = spw->sp_pwdp;
	} else {
		encpw = pw->pw_passwd;
	}
	if( !strcmp(pw->pw_passwd,crypt(pass,encpw))) {
		printf("matched password\n");
	} else {
		printf("non matched password\n");
	}

	return(0);
}

		

