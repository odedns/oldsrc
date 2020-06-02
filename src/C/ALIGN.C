#include <stdio.h>

union long_union {
	long l;
	unsigned char c[4];
	};

int BigEndian()
{
union long_union my;
int stat;

	my.l  = 0x00000010;
	my.l |= 0x00002000;
	my.l |= 0x00300000;
	my.l |= 0x40000000;

	if(my.c[0] == 0x10 && my.c[3] == 0x40) 
		stat = 1;
	else
		stat = 0;
	return(stat);
}
		

main()
{
	if(BigEndian() ) 
		printf("machine is big endian\n");
	else 
		printf("machine is little endian\n");
}



	

