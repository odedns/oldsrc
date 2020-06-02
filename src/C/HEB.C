#include <dos.h>
#include <conio.h>

main()
{
int i;  char Str[30];

unsigned int far *p ;
unsigned char far *p1;

	p = (unsigned far *) MK_FP(0,0x42);
	printf("seg = %Fx\n",*p);
	p1 = MK_FP(0x10e4,0);
	for(i = 5;i <21; ++i)
	   printf("p1[%d] = %d\n", i, p1[i]);

       p1[6] = 1;
       p1[8] = 0;
       p1[9] = 1;
       gotoxy(65,20);
       printf("Enter String : ");
       gotoxy(50,20);
       gets(Str);
       p1[6] = 0;
       p1[8] = 0;
       p1[9] = 0;
       printf("s = %s\n",Str);
}