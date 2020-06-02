
#include <stdio.h>
#include <limits.h>

#define SHORT_BITS ( CHAR_BIT * 2)

char *decToBin(unsigned short byte);

main()
{
int num;

	printf("Enter Num : ");
	scanf("%x",&num);
	decToBin(num);
}

char *decToBin(unsigned short byte)
{
static char Str[SHORT_BITS+1];
unsigned short mask;
int i;

	for(i=0; i < SHORT_BITS; ++i)  {
		mask = (unsigned int) (( 0xFFFF >> SHORT_BITS - 1 ) << i);
		Str[SHORT_BITS-1-i] = ( byte & mask ? '1' : '0');

        }
	Str[SHORT_BITS] = '\0';
	printf("Str = %s\n",Str);
	return(Str);
}


	

	
