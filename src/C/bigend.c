#include <stdio.h>
#include <ctype.h>

#define BIGEND (*(char *)0x10203040 == 0x10 ? 1 : 0)
int bn = 0x10203040;

void main()
{
long f = 0x10203040;
int i = 0;
unsigned char *p;


/*
	printf("enter f : ");
	scanf("%f",&f);
	printf("f = %f\n",f);
	*/
	p = (unsigned char *) &f;

	while(i < 4) {
		printf("p = %02X\n", *p);
		++p;
		++i;


	}

	printf("b = %04X\n", *(unsigned char *)&bn);
	if(BIGEND ) {
		printf("machine is BIGENDIAN\n");
	} else {
		printf("machine is LITTLE ENDIAN\n");
	}
}

