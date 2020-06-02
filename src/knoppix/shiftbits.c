#include <stdio.h>
#include <limits.h>


/**
 * count the number of 1 bits in an integer.
 * return the number of 1 bits.
 */
int bitcount(int n);

/**
 * count the number of 1 bits
 * in the input integer and shift the 
 * bits to the left of the number.
 * return an int with its number of 1 bits
 * shifted to the left.
 */
int shiftbits(int n);



/*
 * main program
 * The program reads an integer from stdin 
 * calls shiftbits on the integer and prints the result in hex.
 */
int main(void)
{

	int n = 0;
	int d;


	puts("Enter number for shiftbits:");
	scanf("%d",&d);
	n = shiftbits(d);
	printf("input number = %08X \tnumber with shifted bits = %08X\n",d,n);
	

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





/**
 * count the number of 1 bits
 * in the input integer and shift the 
 * bits to the left of the number.
 * return an int with its number of 1 bits
 * shifted to the left.
 */
int shiftbits(int n)
{
	int num = INT_MAX;
	int num_bits = sizeof(int) * CHAR_BIT;
	int bits = bitcount(n);
	return(num << (num_bits - bits));
}