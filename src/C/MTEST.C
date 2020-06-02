#include "mouse.h"
#include "video.h"

main()
{
int stat , buttons ,x,y, c;

   clrscr();
   if(!(stat = mouse_installed(&buttons)) )
      return(1);

   printf("stat = %d\nbuttones = %d\n", stat , buttons);

   show_mouse();

   c = 0; buttons = 0;
   cls();
   pos_cursor(0,0);
   Fast_printf(7,"X\tY\n");  
   while(!kbhit()) {
	   stat = get_mouse(&x,&y);
	   
	   pos_cursor(0,1);
	   ClearLine(1,7);
	   Fast_printf(7,"%d\t%d",x,y);
	   if(stat & 1) {
			++c;
			pos_cursor(0,2);
			ClearLine(2,7);
			Fast_printf(7,"Left");
			while(1 & (stat = get_mouse(&x,&y)))
			;
	   } else {
			if(stat & 2)  {
				ClearLine(2,7);
				pos_cursor(0,2);
				Fast_printf(7,"Right");
			} else  {
				pos_cursor(0,3);
				Fast_printf(7,"stat = %d\n",stat); 
			}
	   }

	  
  }
  
  Fast_printf(7,"c = %d\n",c);
  hide_mouse();
  
}
