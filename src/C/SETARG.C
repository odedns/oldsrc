#include <string.h>
#include <dos.h>
				
unsigned _stklen = 20000;
char far *env;


#define FAR_WORD(seg ,ofs) \
(* (unsigned far *) MK_FP(seg , ofs))

void print_env()
{
extern unsigned int _psp;
unsigned int far *parent_psp;
int len;

	printf("master env = \n");
	parent_psp = MK_FP(FAR_WORD(_psp,0x16),0);
	env = MK_FP(FAR_WORD(parent_psp,0x2c),0);
	while(*env) {
		len = printf("%Fs\n",env);
		env += len;
	}


}

void add_var(char *s)
{
	while(*s) {
		*env++ = *s++;
	}
	*env = '\n';
}

main()
{
char s[81] , cmd[81];

	printf("DS = %04x\n",_DS);
	printf("Enter cmd : ");
	gets(s);
	send_cmd(s);
}
