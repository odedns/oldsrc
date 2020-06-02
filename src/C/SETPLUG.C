
#include <dos.h>
unsigned get_port();
unsigned set_port(unsigned int newPort);
int plugFound(int port);

main()
{
unsigned int port;
	port = get_port();
	printf("port = %04X\n",port);

        outportb(port,0x00);
	++port;
	outportb(port,0xbf);
	++port;
	outportb(port,0xec);

}

unsigned get_port()
{
unsigned far *p;

	p = MK_FP(0x00,0x408);
	return(*p);
}


int plugFound(int port)
{

	return(0xBF == inportb(port) ? 1 : 0);

}
