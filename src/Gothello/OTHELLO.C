#include <stdio.h>
#define EXTERN
#include "gothello.h"
#undef EXTERN

#define DEBUG


void printMat()
{
int i, j;

	putchar('\t');
	for(i = 0; i < MAXROW; ++i)  {
		for(j = 0; j < MAXCOL; ++j) {
			putchar(Mat[i][j].Val);
			putchar(' ');
			putchar('|');
		}
		putchar('\n');
		putchar('\t');
	 }
	 putchar('\n');
}

void printMatStates()
{
int i, j;

	putchar('\t');
	for(i = 0; i < MAXROW; ++i)  {
		for(j = 0; j < MAXCOL; ++j) {
			printf("%d",Mat[i][j].State.Xstate);
			printf("%d",Mat[i][j].State.Ostate);
			putchar('|');
		}
		putchar('\n');
		putchar('\t');
	 }
	 putchar('\n');
}

void printMaxStates()
{
	printf("XState = %d row = %d col = %d\n",
		Xmax.State,Xmax.row,Xmax.col);
	printf("OState = %d row = %d col = %d\n",
		Omax.State,Omax.row,Omax.col);
}

void printWinnerMsg()
{
	switch(winnerFlag) {
		case 1 :
			printf("\nGAME OVER !!\nX Player Has Won !!!\n\n");
			break;
		case 2 :
			printf("\nGAME OVER !!\nO Player Has Won !!!\n\n");
			break;
		default :
			break;
	}
}

void GetUserInput()
{
int stat = 0, row,col;

	while(!stat) {
		printf("Enter X Cords : Row/Col ");
		scanf("%d%d",&row,&col);
		if(Mat[row][col].Val == ' ') {
			Mat[row][col].Val = USERCHAR;
			stat = 1;
		}
	 }
}

int UpdateCell(int row, int col, char c)
{
}


main()
{

	puts("\n\n\n\n");
	InitMat();
	printMat();
	while(1) {
		initMaxStates();
		GetUserInput();
		computerPlay();
		printMat();
		getch();
		printMatStates();
		printMaxStates();
		getch();
	   printMat();
		if (winnerFlag)
			break;
	}
	printWinnerMsg();
}

