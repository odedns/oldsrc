

/*---------------------------------------------------------------------------*/
/* menu header file  */
/*---------------------------------------------------------------------------*/
#ifndef _MENU_H #define _MENU_H

#include "window.h"
#include "colors.h"
#include "keyboard.h"
/*---------------------------------------------------------------------------*/
/* menu struct definition */
/*---------------------------------------------------------------------------*/

  typedef struct {
                   WINDOW  *win;  /* pointer to window structure */
                   unsigned xpos;
                   unsigned ypos;
                   unsigned width;
                   char     **sel;
                   unsigned num_items;
                   unsigned att;
                   unsigned highlight;
                   int      esc_key;
                   unsigned justify;
                 } MENU;


/*---------------------------------------------------------------------------*/
/*  Menu functions definition                                                */
/*---------------------------------------------------------------------------*/

int find_max_len(char *sel[] , int nelem);
int init_menu(MENU *mymenu , char *sel[] , unsigned nelem , unsigned xpos ,
               unsigned ypos , unsigned key , unsigned box_type , char *title);
void set_menu_colors(MENU *mymenu , unsigned att ,
                     unsigned highlight , unsigned box_att , unsigned title_att);
int set_menu_justify(MENU *menu , unsigned justify);
int display_menu(MENU *menu);
void close_menu(MENU *menu);
/*************************************************************************/
#endif
