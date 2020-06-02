#include <stdio.h>
#include <string.h>

#include "usr\colors.h"
#include "usr\mylib.h"
#include "usr\keyboard.h"

#define TRUE 1
#define FALSE 0

typedef struct {
		 unsigned int fieldlen;
		 char *legal;
		 int field_att;
		 int xpos;
		 int ypos;
		 char *fieldStr;
		 char *prompt;
		 int prompt_att;
	       } FIELD;



int alloc_field(FIELD **p)
{
    if(NULL == (*p = (FIELD *) malloc(sizeof(FIELD))))
      return(-1);
    return(0);
}


int init_field(FIELD *field_p ,unsigned len , char *legal ,
	       int att , char *prompt , int prompt_att)
{


    field_p->fieldlen = len;
    field_p->field_att = att;
    field_p->prompt_att = prompt_att;
    field_p->legal = strdup(legal);
    field_p->prompt = strdup(prompt);
    field_p->fieldStr = (char *) malloc(field_p->fieldlen + 1);
    field_p->fieldStr[0] = '\0';

    return(0);
}


int display_field(FIELD *field_p , int x , int y)
{

    PosCursor(x , y);
    Fast_puts(field_p->prompt_att , field_p->prompt);
    GetCursor(&field_p->xpos , &field_p->ypos);
    ChangeAtt(field_p->xpos , field_p->ypos ,
	      field_p->fieldlen , field_p->field_att);
}

int print_field(FIELD *field_p)
{
int len = 0;

    if(field_p->fieldStr[0] != '\0')
	len = strlen(field_p->fieldStr);

    PosCursor(field_p->xpos , field_p->ypos);
    if( len < field_p->fieldlen) {
	memset(&field_p->fieldStr[len] , ' ', field_p->fieldlen - len );
	field_p->fieldStr[field_p->fieldlen + 1] = '\0';
    }

    if(field_p->fieldStr != '\0')
       Fast_puts(field_p->field_att , field_p->fieldStr);
    return(len);
}

int edit_field(FIELD *field_p)
{
int pos = 0;
int len;
int insert  = 0;
char c , *s;

    s = field_p->fieldStr;
    len = strlen(s);

    do {
	print_field(field_p);
	PosCursor(pos +field_p->xpos , field_p->ypos);

	switch(c = GetKey()) {

	case HOME :
		       pos = 0;
		       break;
	case END :
		       pos = len;
		       break;
	case INS    :
		       insert = !insert;
		       if(insert == TRUE)
			  SetCursor(BOLD_CURSOR);
		       else
			  SetCursor(NORMAL_CURSOR);
		       break;
	case LEFT_ARROW :
		       if (pos > 0)
			  pos--;
		       break;
	case RIGHT_ARROW :
		       if (pos < len && len < field_p->fieldlen)
			    pos++;
		       break;
	case BACKSPACE :
		       if (pos > 0)
		       {
			 memmove(&s[pos - 1],&s[pos], len - pos + 1);
			 pos--;
			 len--;
		       }
		       break;
       case DEL :
		       if (pos < len)
		       {
			memmove(&s[pos ], &s[pos + 1], len - pos);
			len--;
		       }
		       break;
      case ENTER :
		       break;
      case UP_ARROW :
		       c = ENTER;
		       break;
      case DOWN_ARROW :
		       c = ENTER;
		       break;
      case ESCAPE :
		       len = 0;
		       break;
     default :
		       if (((field_p->legal[0] == 0) ||
			 (strchr(field_p->legal, c) != NULL)) &&
			  ((c >= ' ') && (c <= '~')) &&
			  (len < field_p->fieldlen))  {

			  if (insert) {
			      memmove(&s[pos + 1], &s[pos], len - pos + 1);
			      len++;
			  }
			  else if (pos >= len)
			      len++;
			      s[pos++] = c;
		      }
		      break;
		}  /* end switch */

		     field_p->fieldStr[len] = 0;

		     }     /* end do while */
    while ((c != ENTER) && (c != ESCAPE));
    if (insert)
	SetCursor(NORMAL_CURSOR);   /* reset cursor */

    return(c != ESCAPE ? -1 : 0);
}

main()
{
FIELD *field_p;

    Cls();
    alloc_field(&field_p);
    init_field(field_p , 20 ,"abcdefghijklmnopqrstuvwxyz " , BLACK_ON_CYAN ,
	       "Enter Name --> " , 7);
/*    strcpy(field_p->fieldStr , "Poo Dov"); */
    display_field(field_p , 20 , 5);
    edit_field(field_p);
    PosCursor(0 , 0);
    printf("%s" , field_p->fieldStr);
 }





