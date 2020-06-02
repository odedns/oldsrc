#include <stdio.h>
#include <limits.h>

/**
 * count the number of 1 bits in an integer.
 * return the number of 1 bits.
 */
int bitcount(int n);


/*
 * main program
 */
int main(void)
{

	int cnt = 0;
	int d;


	puts("Enter number for bitcount:");
	scanf("%d",&d);
	cnt = bitcount(d);
	printf("input number = %d \tnumber of bits = %d\n",d,cnt);
	

}


/**
 * count the number of 1 bits in an integer.
 * return the number of 1 bits.
 */
int bitcount(int n)
{

	int numBits = sizeof(int) * CHAR_BIT;
	int mask = (int)0x01;
	int count = 0;
	int i=0;
	

	while(numBits > i ) {
		if( (n & (mask << i++)) > 0) {
			++count;
		}
	}
	return(count);


}