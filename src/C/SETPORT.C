

#include <dos.h>
unsigned set_port(unsigned int newPort);

main(int argc , char **argv)
{
unsigned int port;

        
	sscanf(argv[1],"%X",&port);
	printf("port = %04X\n ",port);
	set_port(port);

}

unsigned set_port(unsigned int newPort)
{
unsigned far *p;

	p = MK_FP(0x00,0x408);
	*p = newPort;
	return(*p);
}

