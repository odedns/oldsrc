
#include <dos.h>
unsigned get_port();
unsigned set_port(unsigned int newPort);
int plugFound(int port);

main()
{
unsigned int port;
	set_port(0x378);

	set_port(0x377);
	port = get_port();
	printf("port = %04X\n",port);

	outportb(++port,0xbf);
	if(plugFound(port) ) {
		printf("Plug Found\n");
	} else {
		printf("Plug not Found\n");
	}
       set_port(0x378);
}

unsigned get_port()
{
unsigned far *p;

	p = MK_FP(0x00,0x408);
	return(*p);
}

unsigned set_port(unsigned int newPort)
{
unsigned far *p;

	p = MK_FP(0x00,0x408);
	*p = newPort;
	return(*p);
}

int plugFound(int port)
{

	return(0xBF == inportb(port) ? 1 : 0);

}
