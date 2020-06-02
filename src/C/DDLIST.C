/****************************************************************
 *                                                              *
 * File: ddlist.c                                               *
 *                                                              *
 * Description:                                                 *
 * List device drivers currently loaded.                        *
 *                                                              *
 ****************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <dos.h>


main()
   {
/* registers for general use */
   union REGS r;
   struct SREGS segs;
/* pointer to peek into memory */
   char far *peek;
   int flags,i;
   char name[9];
/* Get MSDOS internal address */
   r.h.ah=0x52;
   int86x(0x21,&r,&r,&segs);
/* Location of nul driver depends on OS version */
   if (_osmajor==2) r.x.bx+=0x17;
   else r.x.bx+=0x22;
/* print header */
   printf("Address      Name        Flags\n");
   do {
/* point PEEK at the device driver */
      FP_SEG(peek)=segs.es;
      FP_OFF(peek)=r.x.bx;
/* read flags */
      flags=*(int far *)(peek+4);
/* get name */
      for (i=0;i<8;i++) name[i]=*(peek+10+i);
      name[8]='\0';
/* print data */
      printf("%Fp    %-8s    %04X\n",peek,flags&0x8000?name:
        "<BLOCK>",flags);
/* get pointer to next driver */
      segs.es=*(int far *)(peek+2);
      r.x.bx=*(int far *) peek;
      } while (r.x.bx!=0xFFFF);    /* keep going til no more */
   }
