#include <dos.h>
unsigned get_port();

main()
{
unsigned int port;

	port = get_port();
	printf("port = %04X\n",port);
	if(plugFound(++port))
		puts("Plug Found!!");
	else
		puts("Plug Not Found!!");

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
