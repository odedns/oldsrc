#include <stdio.h>
#include <dir.h>
#include <dos.h>
#include <conio.h>


#define TAB  (9)
#define CTRL_D (4)
#define IS_DIR(a)        (FA_DIREC & a)
#define TOLOWER(ch)  ( (ch < 'a' ? ch + 32 : ch))


#define CMD_LEN  (128)
#define IS_CHAR(c)  ( c >= '0' && c <= 'z')
#define IS_DELIMITER(c)  (c == ' ' || c == '>' || c == '|' || c == '<')


char far *pVideo = MK_FP(0xb800 , 0); 
char *prompt;

void beep()
{
   sound(400);
   delay(100);
   nosound();
}



 int _unget(int key)  /* push value onto keyboard stack ! */
	 {

     _AH = 5;
     _CX = key;

     geninterrupt(0x16);
     return((_AL != 1) ? 1 : 0);
     }



void get_cursor(int *x , int *y)
{
union REGS reg;

    reg.h.ah = 3;
	reg.h.bh = 0;
    int86( 0x10 , &reg , &reg);
    *x = reg.h.dl;
    *y = reg.h.dh;
}


/*-------------------------------------------------------------------------*\
 *  Positions the cursor at column x row y  in the active video page.      *
 *  screen starts at 0 , 0.                                                *
\*-------------------------------------------------------------------------*/
void pos_cursor(int x , int y)   /* position the cursor */
{
union REGS reg;

    reg.h.ah = 2;
	reg.h.bh = 0;
    reg.h.dl = x;
    reg.h.dh = y;
    int86(0x10 , &reg , &reg );
 }


char *fname_split(char *path)
{
char *file = path + strlen(path) -1;

	while(file >  path) {
	  if(*file == ':' ||  *file == '\\') {
			++file;
			break;
	  }
		--file;
	}

	if(file == path && *file == '\\')
		++file;
	return(file);
}

char *read_at_cursor()
{
int x,y , pos;
char far *p;
static char scr_word[CMD_LEN+1];
char *word = &scr_word[CMD_LEN-1];


	get_cursor(&x,&y);
	pos = 2 * ( y * 80 + x);
	p = pVideo + pos;
	p-=2;
	*word = '\0';
	while(!isspace(*p)) {
		if(IS_DELIMITER(*p)) 
			break;

		--word;
		*word = *p;
		p-=2;
	}
	scr_word[CMD_LEN] = '\0';

	return(word);
}


char *read_to_eol()
{
int x,y , pos;
char far *p;
static char scr_word[CMD_LEN+1];
char *word = &scr_word[CMD_LEN-1];


	get_cursor(&x,&y);
	pos = 2 * ( y * 80 + x);
	p = pVideo + pos;
	p-=2;
	*word = '\0';
	while(x) {
		--x;
		--word;
		*word = *p;
		p-=2;
	}
	scr_word[CMD_LEN] = '\0';

	return(word);
}


int complete(char *s , char *filename)
{
int stat;
struct ffblk fs;

	*filename = '\0';
	if(s) {
		if(strchr(s,'.'))
			strcat(s,"*");
		else
			strcat(s,"*.*");
	}
	stat = findfirst(s,&fs,FA_DIREC | FA_ARCH | FA_RDONLY);
	if(!stat)  { 
		strcpy(filename , strlwr(fs.ff_name));
		if(IS_DIR(fs.ff_attrib)) {
			strcat(filename,"\\");
		}
		stat = findnext(&fs);
		if(!stat) {
			beep();
			return(1);
		 }

	} 	else {
		perror("complete");
	}

	 return(0);
}

int listfiles(char *cmd)
{

int stat,ret_stat = 0;
struct ffblk fs;
char *s;

	prompt = read_to_eol();
	s = read_at_cursor();

	if(s) {
		if(strchr(s,'.'))
			strcat(s,"*");
		else
			strcat(s,"*.*");
	}
	stat = findfirst(s,&fs,FA_DIREC | FA_ARCH | FA_RDONLY);
	if(!stat) {
		putchar('\n');
		while(!stat)  { 
			cprintf("%-20s",strlwr(fs.ff_name));
			stat = findnext(&fs);
		}
		putchar('\n');
	} else {
		ret_stat = 1;
	}

	 return(ret_stat);
}

int complete_cmd(char *s)
{
char *mask , cmd[MAXPATH] , *pcmd;
int stat;
    *s = '\0';
	mask = read_at_cursor();
	stat = complete(mask,cmd);
	pcmd = cmd;
	if(stat) {
		return(stat);
	}
	mask = fname_split(mask);
	while(TOLOWER(*mask) == TOLOWER(*pcmd) && *mask && *pcmd) {
		++mask;
		++pcmd;
	}
	while(*pcmd) {
		*s++ = *pcmd++;
	}
	*s = '\0';
	return(stat);
}

void read_str(char *s)
{
char c, *p,comp[121];

		p = comp;
		while((c= getch()) != '\r') {
			if(c == TAB) {
				if(!complete_cmd(p)) {
				while(*p) {
						_unget(*p++);
				 }
			   }
			} else {
				if(c == CTRL_D) {
					if(!listfiles(p)) {
						printf("%s",prompt);
					}
				} else {
					*s++ = c;
					putchar(c);
				}
			}
		}
		putchar('\n');
		*s = '\0';
}

/* main **/
main()
{
char s[80], filename[81], *p;

	beep();
	while(1)  {
		printf("CMD >>");
		read_str(s);
		if(!strcmp(s,"exit")) 
			break;
		printf("s = %s\n",s);
		system(s);
	}
}
