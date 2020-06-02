#include <stdio.h>

#define low16(x)  ((x) & 0xFFFF)

void Update(long *un,long *vn,long q)
{
long tn;

	tn = *un - *vn * q;
	*un = *vn;
	*vn = tn;
}
long extended_euclidian(long u , long v , long *u1_out , long *u2_out)
{
long u1 = 1;
long u3 = u;
long v1 = 0;
long v3 = v;
long q;

	while(v3 > 0 ) {
		q = u3 / v3;
		Update(&u1,&v1,q);
		Update(&u3,&v3,q);
	}
	*u1_out = u1; 
	*u2_out = (u3 - u1 * u) / v;
	return(u3);
}

long modexp(long a , long x , long n)
{
long r = 1;

	while(x > 0 ) {
		if(x % 2 == 1 )
			r = (r * a) % n;
		a = (a * a) % n;
		x /= 2;
	}
	return(r);
}			 


long inv(long x)
{
long t0,t1 , q,y;

	if(x <= 1)
		return(x); /* 0 and 1 self-inverse */

	t1 = 0x10001L / x;
	y = 0x10001L % x;
	if (y == 1)
		return(low16(1-t1));
	t0 = 1;
	do {
		q = x /y;
		x = x % y;
		t0 += q * t1;
		if (x == 1)
			return(t0);
		q = y /x;
		y = y % x;
		t1 += q * t0;
	} while(y != 1);
	return(low16(1-t1));
} /* inv() */

long RSA_encrypt(long M , long e , long p , long q)
{
	return(modexp(M,e,q*p));
}

long RSA_decrypt(long M , long e , long p, long q)
{
long d , u1 , u2,t;

	t = (p-1) *(q-1);
	extended_euclidian(e,t,&u1 , &u2);
	d = u1;
	return(modexp(M,d, q*p));
}



main()
{
long num;
long p = 47;
long q = 71;
long e = 79;

	printf("\n\nEnter Num :");
	scanf("%ld",&num);

	printf("Num = %ld\n",num);

	num = RSA_encrypt(num,e,p,q);
	printf("encrypte = %ld\n",num);
	printf("decrypt = %ld\n",RSA_decrypt(num,e,p,q));
}
