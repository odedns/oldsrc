
#include <stdio.h>

#define BASE_MASK (1)

long clear_bit(long l, int bit)
{
	l &= ~(BASE_MASK << bit);
	return(l);
}


long set_bit(long l, int bit)
{
	l |= (BASE_MASK << bit);
	return(l);
}

int get_bit(long l, int bit)
{
	return ( l &  (BASE_MASK << bit));
}
