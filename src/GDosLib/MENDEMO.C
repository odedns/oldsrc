/*
#include <usr\menu.h>
#include <usr\colors.h>
#include <usr\mylib.h>
#include <alloc.h>
#include <usr\keyboard.h>
 */

#include <alloc.h>
#include <stdio.h>
#include "clib.h"

   main()
	 {

          int choice;
	 MENU *mymenu;


    char *options[] = { " Turbo C             " , " Turbo Pascal         " ,
			" Norton Commander         " ,
			" Dosshell 6.0    " ," Magic 4.2  " ,
			" Shez  7.1 "  ," Magic 5 Demo " ,
			   " BIG CHOICE " };




       cls();

       fill_area( 0 , 0 , 79 , 23 , 41 , 177);
       Box(0 , 79 , 0 , 24 , 7 , 3);
	   mymenu = (MENU *) malloc(sizeof(MENU));
	   init_menu(mymenu , options , 8 ,  25 , 5 , ESCAPE , 3 , NULL );
	   set_menu_colors( mymenu , LIGHTGRAY_ON_BLUE , BLACK_ON_CYAN , YELLOW ,0x70);
	   choice = display_menu(mymenu);
	   close_menu(mymenu);
	   getkey();

	   init_menu(mymenu , options , 8 , 10 , 14 ,ESCAPE , 2 , NULL);
	   set_menu_colors(mymenu , LIGHTGRAY , BLACK_ON_CYAN , CYAN , 0);
	   choice = display_menu(mymenu);
	   close_menu(mymenu);

	   init_menu(mymenu , options , 8 , 50 , 12 , ESCAPE , 1 , NULL);
	   set_menu_colors(mymenu , WHITE_ON_BLUE , YELLOW_ON_RED , GREEN_ON_BLUE ,0 );
	   choice = display_menu(mymenu);
	   close_menu(mymenu);



      cls();
	  printf(" option num chosen = %5d \n" , choice);
	  printf( " option = %s \n " , options[choice]);

	 }



