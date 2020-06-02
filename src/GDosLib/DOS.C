#include <dos.h>
#include <stdio.h>
#include <stdarg.h>

#define __DOS_INTR (0x21)

typedef struct {
	unsigned r_ax;
	unsigned r_bx;
	unsigned r_cx;
	unsigned r_dx;
	unsigned r_ds;
	unsigned r_cs;
	unsigned r_ss;
	unsigned r_es;
	unsigned r_si;
	unsigned r_di;
	unsigned r_bp;
	unsigned r_sp;
	unsigned r_flags;
	} dosregs_t;

 void save_regs(dosregs_t *r)
 {
	r->r_ax = _AX;
	r->r_bx = _BX;
	r->r_cx = _CX;
	r->r_dx = _DX;
	r->r_cs = _CS;
	r->r_ss = _SS;
	r->r_ds = _DS;
	r->r_es = _ES;
	r->r_si = _SI;
	r->r_di = _DI;
	r->r_sp = _SP;
	r->r_bp = _BP;
	r->r_flags = _FLAGS;
}

void restore_regs(dosregs_t *r)
{
	_AX = r->r_ax;
	_BX = r->r_bx;
	_CX = r->r_cx;
	_DX = r->r_dx;
	_CS = r->r_cs;
	_SS = r->r_ss;
	_DS = r->r_ds;
	_ES = r->r_es;
	_SI = r->r_si;
	_DI = r->r_di;
	_SP = r->r_sp;
	_BP = r->r_bp;
	_FLAGS = r->r_flags;
}



int dos_putch(char c)
{
	_AH = 0x02;
	_DL = c;
	geninterrupt(__DOS_INTR);
	return((int)c);
}

void dos_puts(char *s)
{
     while(*s)
		dos_putch(*s++);
}

char *dos_gets(char *s)
{
char *s1 = s;
char c;

	while((c = dos_getche()) != '\r')
	   *s++ = c;

    *s = '\0';
    return(s1);

}


void print_string(char *s)
{
    _AH = 0x09;
    _ES = FP_SEG(s);
    _DX = FP_OFF(s);
	geninterrupt(__DOS_INTR);
}

int dos_getch()
{

	_AH = 0x07;
	geninterrupt(__DOS_INTR);
	return(_AX);
}

int dos_getche()
{
	_AH = 0x01;
	geninterrupt(__DOS_INTR);
	return(_AX);
}


int tty_putchar(int c)
{
	 _AL = c;
	 geninterrupt(0x29);
	 if(c == '\n') {
		_AL = '\r';
		geninterrupt(0x29);
	 }

	 return(c);
}

int tty_puts(char *s)
{

	while(*s) {
		tty_putchar(*s++);
	}
	return(0);
}
int tty_printf(char *fmt , ...)
{
char buf[255];
va_list argptr;
int stat;

	va_start(argptr ,fmt);
	stat = vsprintf(buf ,fmt , argptr);
	tty_puts(buf);
	va_end(argptr);
	return(stat);
}

int dos_keybhit()
{
		_AH = 0x0b;
		geninterrupt(__DOS_INTR);		

		return(_AL == 0xff ? 1 :  0);
}

int dos_set_disk(int drive)
{
	_AL = 0x0e;
	_DL = drive;
	geninterrupt(__DOS_INTR);
	return(_AL);
}

int dos_get_disk()
{
	_AH = 0x19;
	geninterrupt(__DOS_INTR);
	return(_AL);
}

int dos_set_dta(char *buff)
{
	_AH = 0x1a;
	_DS = FP_SEG(buff);
	_DX = FP_OFF(buff);
	geninterrupt(__DOS_INTR);
	return(_AX);
}

/*-----------------------------------------------*/

main()
{
char c;
char s[40] = "Fuck Off !! \n";


	 while(27 != (c = dos_getch()))
	 printf("key = %d\n",c);

	 dos_puts("\n\nEnter String :\t ");
	 dos_gets(s);
	 printf("s = %s\n",s);
	 print_string(s);

	puts("\n\n\n\n");
	tty_puts("Oded\nand Poo");

}
