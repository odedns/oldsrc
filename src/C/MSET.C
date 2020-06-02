#include <stdio.h>
#include <string.h>
#include <dos.h>


unsigned char my_stack[512];

int send_cmd(char *s)
{
int len = strlen(s);
unsigned save_ss , save_sp, save_es ,save_ds;
char cmd[129];

	if(*(s+len-1) == '\n') {
		*(s+len-1) = '\0';
		--len;
	}
	
	memcpy(&cmd[1],s,len);
	cmd[++len] = '\r';
	cmd[len+1] = '\0';
	cmd[0] = len;
#ifdef DEBUG
	printf("len = %d\ts = %s\n",cmd[0],&cmd[1]);
#else
	save_es = _ES;
	save_ds = _DS;
	save_ss = _SS;
	save_sp = _SP;
	_DS = FP_SEG(cmd);
	_SI = FP_OFF(cmd);
	geninterrupt(0x2e);
	_SP = save_sp;
	_SS = save_ss;
	_DS = save_ds;
	_ES = save_es;
#endif
	return(_AX);

}
int set_var(char *var , char *value)
{
char env_str[129];

	sprintf(env_str,"set %s=%s",var,value);
	return(send_cmd(env_str));
}

unsigned program_size(void){   /* return the size of the current memory   */
                           /* control block (in paragraphs)               */

    return(* ((unsigned far *) (MK_FP(_psp-1, 3))) );

} /* program_size() */

void free_mem()
{
unsigned size;
	size = program_size();
	printf("prog size = %d\n",size);
	_ES = FP_SEG(_psp);
	_BX = size + 4;
	_AX = 0x4a00;
	geninterrupt(0x21);
}
main(int argc , char **argv)
{
char cmd[129];

	if(argc < 2) {
		printf("no arg !!\n");
		exit(1);
	}

	_SP = &my_stack[0];
	sprintf(cmd ,"set %s",argv[1]);
/*
	free_mem();
*/
	send_cmd(cmd);
}


