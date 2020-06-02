
#include <stdio.h>



/*
#define MASK32( x )	( ( x ) & 0xFFFFFFFFUL )
*/

#define ROTL(x,s)	( ( ( x ) << ( s ) ) | ( ( x ) >> ( 32 - ( s ) ) ) )
#define ROTR(x,s)	( ( ( x ) >> ( s ) ) | ( ( x ) << ( 32 - ( s ) ) ) )

#define RC5_ROUNDS (5)
#define RC5_KEYSIZE (64)


void encrypt(char *buf, char *key)
{
	unsigned long a,b;

	memcpy(&a,buf,sizeof(unsigned long));
	memcpy(&b,(buf + sizeof(unsigned long)),sizeof(unsigned long));


	printf("before encrypt a = %lu\tb =%lu\n",a,b);
	a+=*key++;
	b+=*key++;


	/* start rounds */

	a = ROTL(a ^ b,b) + *key++;
	b = ROTL(b ^ a,a) + *key++;

	/* end rounds */
	memcpy(buf,&a,sizeof(unsigned long));
	memcpy(buf + sizeof(unsigned long),&b,sizeof(unsigned long));
	printf("after encrypt a = %lu\tb =%lu\n",a,b);
	printf("key = %c\n", *(key-1));


}

void decrypt(char *buf,char *key)
{
	unsigned long a,b;
	memcpy(&a,buf,sizeof(unsigned long));
	memcpy(&b,(buf + sizeof(unsigned long)),sizeof(unsigned long));
	printf("before deccrypt a = %lu\tb =%lu\n",a,b);

	key = key + 4;

	/* start rounds */
	b-= *--key;
	b = ROTR(b,a)  ^ a;
	a-= *--key;
	a = ROTR(a,b)  ^ b;

	/* end rounds */

	b-= *--key;
	printf("key = %c\n", *key);
	a-= *--key;
	printf("key = %c\n", *key);
	printf("after deccrypt a = %lu\tb =%lu\n",a,b);
	memcpy(buf,&a,sizeof(unsigned long));
	memcpy(buf + sizeof(unsigned long),&b,sizeof(unsigned long));
}

void main(void)
{
		/*
	char key[] = "oded";
	char data[] = "secret";
	*/
	char key[80];
	char data[120];
	puts("Enter key: ");
	gets(&key[0]);
	puts("Enter data: ");
	gets(&data[0]);

	
	encrypt(data,key);
	decrypt(data,key);
	printf("after decrypt data = %s\n",data);

}
