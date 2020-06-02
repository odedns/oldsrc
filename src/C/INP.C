#include <dos.h>

#define  INSERT       0x80
#define  CAPS         0x40
#define  NUM_LOCK     0x20
#define  SCROLL_LOCK  0x10
#define  ALT          0x08
#define  CTRL         0x04
#define  LEFT_SHIFT   0x02
#define  RIGHT_SHIFT  0x01


#define TAB 15
#define CTRL_D 0x20
char far *key_flags = (char far *)MK_FP(0x00,0x0417);
main()
{
int x;
int key;

	key = bioskey(0);
	printf("key = %04X\n",key);
	x = inportb(0x60);
	printf("x = %04X\n",x);
	printf("flags = %02X\n",*key_flags);
	if(x == CTRL_D &&(*key_flags & CTRL)) puts("cntrl");
	if(x == TAB) 
		printf("tab\n");
	if(x == CTRL_D) 
		puts("CTRL_D");
		
}

	