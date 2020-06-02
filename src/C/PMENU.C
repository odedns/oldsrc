#include <stdio.h>
#include "usr\colors.h"
#include "usr\tcu.h"


main()
{
int choice = 1;
TCU_MENU *pmenu;
TCU_WINDOW *pwin;
char buf[2000];
char *selections[] = { "choice 1" , "choice 2" , "choice 3" ,"choice 4" ,
		       "choice 5" , NULL};


    pmenu = (TCU_MENU *) malloc(sizeof(TCU_MENU));
    pwin = (TCU_WINDOW *) malloc(sizeof(TCU_WINDOW));


    tcu_define_menu(pmenu , "Program Menu ",
		WHITE ,                  /* title attrib  */
		CYAN  ,                 /* box attrib    */
		CYAN ,                  /* option attrib  */
		BLACK_ON_CYAN ,         /* select attrib  */
		0 ,                     /* unavail attrib  */
		TCU_ESC_ESC ,           /* escape keys     */
		TCU_BOX_SINGLE ,        /* box type         */
		selections ,             /* select options   */
		0                        /* hot key attrib   */
		);

    tcu_open_window(pwin , 1 , 1 , 80 , 24 ,"WINDOW " ,
		    7,
		    7,
		    7,
		    TCU_BOX_SINGLE);

    tcu_display_menu(pmenu , 30 , 10);


    while((choice = tcu_read_menu_selection(pmenu)) > 0) {
	tcu_save_environment();
	switch(choice) {
	    case 1:  clrscr();
		     puts(" Oded selected \n");
		     break;
	    case 2:  clrscr();
		     puts(" Poo  selected \n");
		     break;
	    case 3:  clrscr();
		     puts(" YAEL selected \n");
		     break;
	    case 4:  clrscr();
		     puts(" PATCH selected \n");
		     break;
	    case 5:  clrscr();
		     puts(" TURBO-C selected \n");
		     break;
	 } /* end switch */
    tcu_restore_environment();
    } /* end while */

    tcu_remove_menu(pmenu);
    tcu_close_window(pwin);
  }
