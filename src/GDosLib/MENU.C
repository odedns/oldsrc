#include <stdio.h>
#include <string.h>
#include <alloc.h>
/*
#include "mylib.h"
#include "colors.h"
#include "keyboard.h"
#include "window.h"
#include "menu.h"
  */
#include "clib.h"

 int find_max_len(char *sel[] , int nelem)
{
 int max , i;

    max = strlen(sel[0]);

    for(i = 1; i < nelem; ++i)
	if(strlen(sel[i])  > max)
	   max = strlen(sel[i]);


    return(max);

 }

int init_menu(MENU *menu , char *sel[] , unsigned nelem , unsigned xpos ,
	       unsigned ypos , unsigned key , unsigned box_type , char *title)

{
int i;

    if(NULL == (menu->win = (WINDOW *) malloc(sizeof(WINDOW))))
	return(-1);

    menu->width = find_max_len(sel , nelem);
    menu->xpos = xpos;
    menu->ypos = ypos;
    menu->num_items = nelem;
    menu->esc_key = key;
/*    menu->justify = 0; */
    menu->highlight = BLACK_ON_CYAN;
    menu->att = 7;
    menu->win->fill_att = 7;
    menu->win->box_att = 7;
    menu->win->title_att = BLACK_ON_CYAN;

    init_window(menu->win , xpos , ypos , xpos + menu->width-1 ,
		ypos + menu->num_items-1 , box_type ,
		' ' , title);

   if(menu->justify == 2) {
       for(i = 0 ; i < menu->num_items; ++i)
       menu->sel[i] = str_right(sel[i] , menu->width);

    } else {
       if(menu->justify == 3) {
	  for(i = 0 ; i < menu->num_items; ++i)
       menu->sel[i] = str_center(sel[i] , menu->width);
       } else {
	       for(i = 0 ; i < menu->num_items; ++i)
           menu->sel[i] = str_left(sel[i] , menu->width);
       }
    }
    return(0);
 }




		/* sets menu color attributes */
void set_menu_colors(MENU *mymenu , unsigned att ,
		     unsigned highlight , unsigned box_att , unsigned title_att )
{
    mymenu->highlight = highlight;
    mymenu->att = att;
    set_window_colors(mymenu->win , box_att , att,
			title_att );
 }

int set_menu_justify(MENU *menu , unsigned justify)
{
    menu->justify = justify;
    return(justify);
}

int display_menu(MENU *menu)
{
 int i = 0, x = 0 , y = 0 , key , choice = 0;


    if(-1 == (display_window(menu->win)))
       return(-1);

     /* write menu selections */

    for(i = 0 ; i < menu->num_items; ++i)
    {
       wcursor(menu->win ,x,y);
       wputs(menu->win , menu->sel[i]);
       ++y;
    }
    pos_cursor(0 , 0);

    /* highlight first selection */

    change_att(menu->xpos , menu->ypos , menu->width ,
					     menu->highlight);

    /* process menu keystrokes */
    x  = menu->xpos;
    y = menu->ypos;
    while((key = get_key()) != menu->esc_key)
    {                 /* begin while */

	 switch(key)
	 {                 /* begin switch */

		 case ENTER :  return(choice);

		 case 56      :
		 case UP_ARROW  :   if (choice == 0)
					{
                   change_att(x , y , menu->width ,
					menu->att);
					y = y + menu->num_items -1;
               change_att(x , y , menu->width
					       , menu->highlight);
					choice += menu->num_items - 1;
					}
				       else
				       {
               change_att(x , y  , menu->width,
						 menu->att);
				       --y;
				       --choice;
               change_att(x , y , menu->width ,
						 menu->highlight);
					}
					break;

			 case 50:
			 case DOWN_ARROW :
				       if(choice == menu->num_items - 1)
					{
                      change_att(x , y , menu->width ,
						 menu->att);
					y = menu->ypos;
                    change_att(x , y , menu->width ,
						 menu->highlight);
					choice = 0;
					}

					else

					{
                    change_att(x , y , menu->width ,
						 menu->att);
				       ++y;
				       ++choice;
                    change_att(x , y , menu->width ,
						 menu->highlight);
					}
					break;


			    case HOME  : if(choice) {
                         change_att(x , y , menu->width ,
						       menu->att);
					     y = menu->ypos;
                         change_att(x , y , menu->width ,
						       menu->highlight);
					     choice = 0;
					  }
					  break;

			    case END   : if(choice < menu->num_items - 1) {
                       change_att(x , y , menu->width ,
						     menu->att);
					   y = menu->ypos + menu->num_items -1;
                       change_att(x , y , menu->width,
						     menu->highlight);
					   choice += menu->num_items - 1;
					}
					break;

			    default : break;

			    } /* end switch */

			     } /* end while */

			     if(key == menu->esc_key)
			       return(-1);

			       /* if user escaped return -1 */

			       return(choice);
			       }  /* end display menu */

void close_menu(MENU *menu)
{
    close_window(menu->win);
    free(menu);
}

/*************************************************************************/
