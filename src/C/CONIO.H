
#ifndef _CONIO_H
#define _CONIO_H

char *itoa(int num , char *s);
void cursor(int y , int x);
int write_reverse(char *s);
int write_bold(char *s);
void cls();
void clreol();
void save_cursor();
void restore_cursor();
int cursor_up(int y);
int cursor_down(int y);
int cursor_forw(int c);
int cursor_back(int c);
#endif
