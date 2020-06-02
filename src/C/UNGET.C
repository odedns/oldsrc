#include <stdio.h>
#include <dos.h>


#define TAB  (9)

#define CMD_LEN  (128)
#define IS_CHAR(c)  ( c >= '0' && c <= 'z')
#define IS_DELIMITER(c)  (c == ' ' || c == '>' || c == '|' || c == '<')

 int _ungetkey(int key)  /* push value onto keyboard stack ! */
     {

     _AH = 5;
     _CX = key;

     geninterrupt(0x16);
     return((_AL != 1) ? 1 : 0);
     }

unsigned int _getkey()   /* get a key from enhanced keyboard  */
     {
     union key_u {
		  unsigned code;
		  char ch[2];
		 } key;

	 key.code = bioskey(0);

     return(key.ch[0] ? key.ch[0]  : key.ch[1]);

     }



void read_str(char *s)
{
char c,*p;

		p = s;
		while((c= _getkey()) != '\r') {
			if(c == TAB) {
					_ungetkey('d');
					_ungetkey('d');
					_ungetkey('e');
					_ungetkey('o');
			} else {
				*s++ = c;
				putchar(c);
			}
		}
		putchar('\n');
		*s = '\0';
}

/* main **/
main()
{
char s[80];

	while(1)  {
		printf("CMD >>");
		read_str(s);
		if(!strcmp(s,"exit")) 
			break;
		printf("s = %s\n",s);
	}
}
