#include <stdio.h>
#include <stdlib.h>

char ESC = 27;
char CLS[] = "[2J";
char BOLD[] = "[1m";
char NORMAL[] = "[0m";
char REVERSE[] = "[7m";
char CLREOL[] = "[K";
char SAVE_CURSOR[] = "[s";
char RESTORE_CURSOR[] = "[u";


void cursor(int y , int x)
{
char s[20];
char col[3];
char row[3];

 itoa(x , col , 10);
 itoa(y , row , 10);

  sprintf(s,"%c[%s;%sH",ESC,row,col);
  fprintf(stdout,"%s",s);

}


int write_reverse(char *s)
{
int bytes;

    bytes = fprintf(stdout,"%c%s%s%c%s",ESC,REVERSE,s,ESC,NORMAL);
    return(bytes);
}

int write_bold(char *s)
{
int bytes;

    bytes = fprintf(stdout,"%c%s%s%c%s",ESC,BOLD,s,ESC,NORMAL);
    return(bytes);
}



void cls()
{
    fprintf(stdout,"%c%s",ESC,CLS);
}


void clreol()
{
    fprintf(stdout,"%c%s",ESC,CLREOL);
}

void save_cursor()
{

    fprintf(stdout,"%c%s",ESC,SAVE_CURSOR);
}

void restore_cursor()
{
    fprintf(stdout,"%c%s",ESC,RESTORE_CURSOR);
}

int cursor_up(int y)
{

	fprintf(stdout ,"%c[%dA",ESC,y);
    return(y - 0x30);
}

int cursor_down(int y)
{

	fprintf(stdout,"%c[%dB",ESC,y);
	return(y - 0x30);
}

int cursor_forw(int c)
{

	fprintf(stdout,"%c[%dC",ESC,c);
    return(c);
}

int cursor_back(int c)
{
	fprintf(stdout , "%c[%dD",ESC,c);
    return(c);
}
main()
{

	 cls();
    cursor(15 , 15);
	 save_cursor();
    write_bold("This is Bold!\n");
    write_reverse("This is reverse\r");
	 clreol();
    restore_cursor();
    cursor_up(10);
    cursor_down(10);
    cursor_forw(29);
    write_reverse("This is reverse");

}


