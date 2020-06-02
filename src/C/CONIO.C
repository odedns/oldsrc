#include <stdio.h>
#include "conio.h"

char ESC = 27;
char CLS[] = "[2J";
char BOLD[] = "[1m";
char NORMAL[] = "[0m";
char REVERSE[] = "[7m";
char CLREOL[] = "[K";
char SAVE_CURSOR[] = "[s";
char RESTORE_CURSOR[] = "[u";


char *itoa(int num , char *s)
{
char *p = s , *t;
char digit;

   while(num) {
     digit = num % 10;
     *p++ = digit + 0x30;
     num /= 10;
   }
   *p = '\0';

   /* reverse string */
   t = s;
   p--;

   while(t < p) {
      digit = *t;
      *t++ = *p;
      *p-- = digit;
   }

   return(s);
}


void cursor(int y , int x)
{
char s[20];
char col[3];
char row[3];

 itoa(x , col );
 itoa(y , row);

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
char s[3];

    itoa(y,s);
    fprintf(stdout ,"%c[%sA",ESC,s);
    return(y - 0x30);
}

int cursor_down(int y)
{
char s[3];

    itoa(y,s);
    fprintf(stdout,"%c[%sB",ESC,s);
    return(y - 0x30);
}

int cursor_forw(int c)
{
char s[3];

    itoa(c , s);
    fprintf(stdout,"%c[%sC",ESC,s);
    return(c);
}

int cursor_back(int c)
{
char s[3];

    itoa(c,s);
    fprintf(stdout , "%c[%sD",ESC,s);
    return(c);
}
