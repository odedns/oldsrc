
/* Include Files *\
\* ------------- */

#include <stdio.h>
#include <graphics.h>
#include <stdlib.h>
#include <dos.h>
#include "keyboard.h"

#define EXTERN
#include "gothello.h"
#undef EXTERN


/* Code *\
\* ---- */


void DrawBoard()
{
int cgap, gap,i,left,right,top,bottom;


    DrawHeader();
    DrawFooter();
    setcolor(WHITE);
    setfillstyle(SOLID_FILL,BLUE);
    bar(BOARD_LEFT,BOARD_TOP,BOARD_RIGHT,BOARD_BOTTOM);

   settextstyle(DEFAULT_FONT,HORIZ_DIR,1);
   left = BOARD_LEFT;
   top = BOARD_TOP;
   right = BOARD_RIGHT;
   bottom = BOARD_BOTTOM;
   cgap = gap = (right - left) / 20;

   while(cgap < (bottom - top)) {
	   line(left,top+cgap,right,top+cgap);
	   cgap +=gap;
   }
   cgap = gap;
   while(cgap < (right-left)) {
	    line(left + cgap,top,left+cgap,bottom);
	    cgap+=gap;
   }

   /* draw a rectangle */
   rectangle(left,top,right,bottom);
}

void PlayGame()
{
int i =0 , j = 0;
char c;
	setcolor(YELLOW);
	setfillstyle(SOLID_FILL,RED);
	bar(Mat[i][j].xCord-3,Mat[i][j].yCord-3,
	    Mat[i][j].xCord+10,Mat[i][j].yCord+10);
	while(ESCAPE != (c = getkey()) && !winnerFlag) {
		switch(c) {
		   case UP_ARROW :
			   if(i) {
				LeaveCell(i,j);
				--i;
				EnterCell(i,j);
			   }
			   break;

		   case LEFT_ARROW :
			   if(j) {
				LeaveCell(i,j);
				--j;
				EnterCell(i,j);
			   }
			   break;

		   case RIGHT_ARROW:
			   if(j < MAT_SIZE -1) {
			     LeaveCell(i,j);
			     ++j;
			     EnterCell(i,j);
			   }
			   break;

		   case DOWN_ARROW :
			   if(i < MAT_SIZE - 1) {
				LeaveCell(i,j);
				++i;
				EnterCell(i,j);
			   }
			   break;
		   case ENTER :
				setfillstyle(EMPTY_FILL,BLUE);
				if(UpdateCell(i,j,USERCHAR) && !winnerFlag)
					computerPlay();
				break;
			 case F1  : DisplayHelp();
				    break;
			default :
				break;
	       }
	}
       if (winnerFlag)
		printWinnerMsg();


}

void EnterCell(int i , int j)
{
	setfillstyle(SOLID_FILL,RED);
	bar(Mat[i][j].xCord-3,Mat[i][j].yCord-3,
	    Mat[i][j].xCord+10,Mat[i][j].yCord+10);
}
void LeaveCell(int i, int j)
{
char s[2];
	s[0] = Mat[i][j].Val;
	s[1] = '\0';
	setfillstyle(SOLID_FILL,BLUE);
	bar(Mat[i][j].xCord-3,Mat[i][j].yCord-3,
	    Mat[i][j].xCord+10,Mat[i][j].yCord+10);
	outtextxy(Mat[i][j].xCord,Mat[i][j].yCord,s);
}


int UpdateCell(int i , int j , char c)
{
char s[2];
int stat = 0;

	if(Mat[i][j].Val == ' ') {
		Mat[i][j].Val = c;
		s[0] = Mat[i][j].Val;
		s[1] = '\0';
		outtextxy(Mat[i][j].xCord,Mat[i][j].yCord,s);
		stat = 1;
	}
	return(stat);
}

void DrawHeader()
{
	setfillstyle(SOLID_FILL,BLACK);
	bar(0,0,getmaxx(),getmaxy());
	settextstyle(TRIPLEX_FONT,HORIZ_DIR,4);
	setfillstyle(SOLID_FILL,CYAN);
	bar(BOARD_LEFT,0,BOARD_RIGHT,70);
	setlinestyle(SOLID_LINE,0,THICK_WIDTH);
	rectangle(BOARD_LEFT,0,BOARD_RIGHT,70);
	setcolor(BLACK);
	outtextxy(150,0,"OTHELLO GAME");
	settextstyle(TRIPLEX_FONT,HORIZ_DIR,2);
	outtextxy(150,40 , "By Oded Nissan");

	setcolor(WHITE);

}

void DrawFooter()
{
	settextstyle(DEFAULT_FONT,HORIZ_DIR,0);
	outtextxy(50,470 ,
	 "USE ARROW KEYS TO MOVE , ENTER TO INSERT , ESC TO QUIT ,F1 FOR HELP !");

}


void printMaxStates()
{
	moveto(100,470);
	printf("XState = %d row = %d col = %d",
		Xmax.State,Xmax.row,Xmax.col);
	printf("OState = %d row = %d col = %d",
		Omax.State,Omax.row,Omax.col);
}

void printWinnerMsg()
{
char Str[80];

	setlinestyle(SOLID_LINE,0,THICK_WIDTH);
	switch(winnerFlag) {
		case 1 :

			sprintf(Str,"You Have Won !!!");
			break;
		case 2 :
			sprintf(Str,"COMPUTER HAS WON !!");
			break;
		default :
			break;
	}
	setfillstyle(SOLID_FILL,RED);
	bar(50,200,510,300);
	rectangle(50,200,510,300);
	settextstyle(TRIPLEX_FONT,HORIZ_DIR,4);
	setcolor(YELLOW);
	outtextxy(120,215,"GAME OVER !!");
	outtextxy(120,250,Str);
	getkey();

}

/*-------------------------------------------------------------------------*\
 *  Read the keyboard buffer returns ascii code of character               *
 *  If key is a function key scan code is returned.                        *
\*-------------------------------------------------------------------------*/
int getkey()
{
union REGS reg;  /* get a key from keyboard return ascii  & scan code */

    reg.h.ah = 0;
    int86(0x16 , &reg , &reg);
    return((reg.h.al ? reg.h.al : reg.h.ah));
}


void GraphInit()
{
int gdriver = DETECT , gmode= VGAHI, errorcode;


	errorcode = registerbgidriver(EGAVGA_driver);

/* report any registration errors */
if (errorcode < 0)
{
   printf("Graphics error: %s\n", grapherrormsg(errorcode));
   printf("Press any key to halt:");
   getkey();
   exit(1); /* terminate with an error code */
}
	errorcode = registerbgifont(triplex_font);
/* report any registration errors */
if (errorcode < 0)
{
   printf("Graphics error: %s\n", grapherrormsg(errorcode));
   printf("Press any key to halt:");
   getkey();
   exit(1); /* terminate with an error code */
}


   initgraph(&gdriver, &gmode, "");

   errorcode = graphresult();
   if (errorcode != grOk)     {
      printf("Graphics error: %s\n", grapherrormsg(errorcode));
      printf("Press any key to halt:");
      getkey();
      exit(1);
   }
}
void PrepareHelp()
{
int i = 0, x = 50 , y = 150;
char *Str[] =
   {"The Purpose of the game is to get 4 consecutive X's",
   "You can win with 4 X's in a row , collumn or across",
   "You play with the X's while the computer plays the O's",
   "Use the arrow keys to move around the matrix ",
   "use the ENTER key to insert an 'X'",
   "Press ESCAPE at any time to quit the game.",NULL};

    setactivepage(1);
    cleardevice();
    setfillstyle(SOLID_FILL,BLACK);
    bar(0,0,getmaxx(),getmaxy());
    settextstyle(TRIPLEX_FONT,HORIZ_DIR,4);
    line(50,100,400,100);
    outtextxy(50,50,"Othello Game Help");
    settextstyle(DEFAULT_FONT,HORIZ_DIR,1);
    while(Str[i] != NULL) {
		moveto(x,y+(i*20));
		outtext(Str[i]);
		++i;
    }

    setactivepage(0);

}

void DisplayHelp()
{
	setvisualpage(1);
	getkey();
	setactivepage(0);
	setvisualpage(0);
}

int GetYN()
{
char c;
	c = toupper(getkey());
	while(c != 'Y' && c != 'N')
		c = toupper(getkey());

	return(c == 'Y' ? 1 : 0);
}

int DisplayPrompt(char *Str)
{
	setfillstyle(SOLID_FILL,RED);
	bar(50,200,510,300);
	rectangle(50,200,510,300);
	settextstyle(TRIPLEX_FONT,HORIZ_DIR,4);
	setcolor(YELLOW);
	outtextxy(120,215,Str);
	return(GetYN());
}

int main(void)
{
int ContGame = 1;

   GraphInit();
   PrepareHelp();

   while(ContGame) {
	   winnerFlag = 0;
	   ContGame = 0;
	   DrawBoard();
	   InitMat();
	   initMaxStates();
	   PlayGame();
	   ContGame = DisplayPrompt("PLAY AGAIN ? Y/N");
   }
   /* clean up */
   getkey();
   closegraph();
   return 0;
}

