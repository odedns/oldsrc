/*----------------- system include files ---------------------*/
#include <stdio.h>
#include <alloc.h>
#include <string.h>
#include <dir.h>


/*---------------- application include files ----------------*/

#include "clib.h"

/*---------------- definitions -------------------------------*/

#define MAX 20

typedef struct {
		char *path;
		char *command;
	       } ITEM;

/*------------ Function Declarations -------------------*/

void print_sel(char ** , int nelem , ITEM *item[]);
int load_file(FILE *fp , char **sel , ITEM *item[]);
int execute_command(ITEM *item_p);
void init_screen();
int get_options(int argc , char **argv);
void exec_msg(char *s);
void error_msg(char *s);

/*----------- Global Variables ------------------------*/

char Usage[] =
	"usage : qmenu - t title -a attribute -h highlight -f filename\n";

char *filename ,*title = NULL;
int menu_att = 7 , highlight = BLACK_ON_CYAN;


/*------------- QMENU MENU PROGRAM ---------------------*/

main(int argc , char **argv)  /*** begin main ***/
{

/*---------------- Local Variables --------------------*/
ITEM *item[MAX];
MENU *mymenu;
char **sel = NULL;

FILE *fp;
int nelem = 0 , choice ,  stat = 0 , box_att , len ,x;


/*----------------- Code ------------------------------*/

    if(get_options(argc , argv))
       return(1);

    box_att = menu_att;
    if(NULL == (fp = fopen(filename , "r"))) {
	fputs("can't open file \n", stderr);
	exit(1);
    }


    nelem = load_file(fp , sel , item);

    mymenu = (MENU *) malloc(sizeof(MENU));
    mymenu->sel = (char **) calloc(nelem , sizeof(char *));
    len = find_max_len(sel , nelem);
    x = (80 - len)/2;

    set_menu_justify(mymenu , 2);
    init_menu(mymenu , sel , nelem , x ,
	  5 , ESCAPE , 2 , title);
    set_menu_colors(mymenu , menu_att ,
		    highlight , box_att , highlight );

    init_screen();
    while(( choice = display_menu(mymenu)) >= 0) {
       stat = execute_command(item[choice]);
       if(-1 == stat) {
	   error_msg(item[choice]->command);
       }
       init_screen();

   }
   close_menu(mymenu);
   cls();
}   /***-------------- end main ----------------------***/



/*----------- Qmenu Functions -----------------------*/

void print_sel(char **sel , int nelem , ITEM *item[])
{

int i;

    for(i = 0; i < nelem; ++i) {
       printf("sel[%d] = %s\tpath[i] = %s\tcommand = %s\n",
	       i , sel[i], item[i]->path, item[i]->command);
    }

}


/*---------- load the data file --------------------*/

int load_file(FILE *fp , char **sel , ITEM *item[])
{
int i = 0;
char line[80] , *p;


   while(NULL != (p  = fgets(line , 80 , fp)) && i < 20) {
	sel[i] = strdup(p);
	sel[i] = strtok(sel[i] , ",");
	item[i] = (ITEM *) malloc(sizeof(ITEM));
	item[i]->path = strtok(NULL , ",");
	item[i]->command = strtok(NULL , ",");

	++i;
    }
    fclose(fp);
    return(i);
}

/*------------- execute the selected choice --------------*/
int execute_command(ITEM *item_p)
{
char path[MAXPATH] , *p;
int stat;

     exec_msg(item_p->command);
     p = getcwd(path , MAXPATH);
     chdir(item_p->path);

     stat = system(item_p->command);
     chdir(p);

     return(stat);
}


/*-------------- initialize the screen ----------------------*/

void init_screen()
{
    int att = BLACK_ON_GREEN;

    cls();
/*    fill_area( 0 , 0 , 79 , 23 , 41 , 177);
    Box(0 , 79 , 0 , 24 , 7 , 3);             */
    Fbox(0 , 15 , 0 , 2 , att, 2 ,att  , ' ');
    pos_cursor(1 , 1);
    Bios_puts(" 2.0 - „‘˜‰‚ " , att);

    Fbox(64 , 79 , 0 , 2 , att , 2 , att , ' ');
    Fbox(17 , 62 , 0 , 2 , 112 , 2 , 112 , ' ');
    pos_cursor(18 , 1);
    Bios_puts("      Quick Menu - ™š™Œ š…‹…š ˆ‰˜”š " , 112);

    Fbox(0 , 79 , 22 , 24 , 112 , 2 , 112 , ' ');
    pos_cursor(1 , 23);
    Bios_puts(
     "       ! „€‰–‰Œ < ESC > „Œ’”„Œ < ENTER >  „†…†šŒ ‰–‰‡„ ‰™— ™š™„ " ,
	      112);
    clock(67 , 1 , att);


}


/*----------------- get command line arguments -------------------*/

 int get_options(int argc , char **argv)
 {
 char *arg_p;
 int i = 0;


    if(argc < 2) {
	fputs(Usage , stderr);
	return(1);
    }

    while(--argc && (arg_p = argv[++i]) ) {

     if (*arg_p++ == '-') {
           switch(*arg_p) {

              case 't' : title = argv[++i];
                         break;
              case 'a' : menu_att = atoi(argv[++i]);
                         break;
              case 'h' : highlight = atoi(argv[++i]);
                         break;
              case 'f' : filename = argv[++i];
                         break;
              default  : fputs(Usage , stderr);
                         return(1);
           } /* end switch  */
      } else {
        fputs(Usage , stderr);
        return(1);
      }
    } /* end while */
    return(0);
}    /*** get_options  ***/

/*-------------print execute message --------------*/

void exec_msg(char *s)
{
    cls();
    Fast_printf(78 ,"Executing item - %s" ,s);
}

/*-------print error msg --------------------------*/
void error_msg(char *s)
{
    cls();
    Fast_printf(78 ,"Can't Execute item - %s" ,s);
    getkey();
}
