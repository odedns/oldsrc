#include <stdio.h>
#include <stdlib.h>
#include <dos.h>
#define EXTERN extern
#include "gothello.h"
#undef EXTERN

int checkMat(int rowStart, int colStart)
{
int i,j,k;

	for(i=rowStart,k=0; i < rowStart+MAXI; ++i,++k) {
		for(j=colStart; j<colStart+MAXJ; ++j) {
		   MatState.rowState[k] += getVal(i,j);
		   MatState.colState[k]+= getVal(j,i);
		}
		MatState.cross[0] += getVal(i,k+colStart);
		MatState.cross[1] += getVal(i,colStart+MAXJ-k-1);
	}
	return(0);
}

/* get box value */
int getVal(int row , int col)
{
int val = 0;

	if(Mat[row][col].Val == USERCHAR) {
		val = USER_VAL;
		/*
		val += UserNear(row,col);
		*/
	}
	if(Mat[row][col].Val == COMPCHAR)
		val = COMP_VAL;

	return(val);
}

int putState(int state , int row , int col)
{
	if(Mat[row][col].State.Xstate < state) {
		if(UserNear(row,col)) ++state;
		Mat[row][col].State.Xstate = state;
		if(IS_EMPTY(Mat[row][col]) &&
		   Mat[row][col].State.Xstate >= Xmax.State) {
			Xmax.State = Mat[row][col].State.Xstate;
			Xmax.row = row;
			Xmax.col = col;
		}

	}
	if(Mat[row][col].State.Ostate > state) {
		Mat[row][col].State.Ostate = state;
		if(IS_EMPTY(Mat[row][col]) &&
		   Mat[row][col].State.Ostate < Omax.State) {
			Omax.State = Mat[row][col].State.Ostate;
			Omax.row = row;
			Omax.col = col;
		}

	 }

	return(0);
}


int updateStates(int rowStart , int colStart)
{
int i , j,k;

	for(i=rowStart,k=0; i < rowStart+MAXI; ++i,++k) {
		for(j=colStart; j<colStart+MAXJ; ++j) {
			putState(MatState.rowState[k],i,j);
			putState(MatState.colState[k],j,i);
		}
		putState(MatState.cross[0],i,k+colStart);
		putState(MatState.cross[1],i,colStart+MAXJ-k-1);
	 }
	 return(0);
}

int chooseMove(int rowStart, int colStart)
{
int stat = 0;

	checkMat(rowStart,colStart);
	updateStates(rowStart,colStart);
	stat = checkWinner();
	return(stat);
}



void initStates()
{
int i;
	for(i = 0; i < MAXI; ++i) {
		MatState.rowState[i] = 0;
		MatState.colState[i] = 0;
	}
	MatState.cross[0] = 0;
	MatState.cross[1] = 0;
}

void initMaxStates()
{
	Xmax.State = 0;
	Xmax.row = 0;
	Xmax.col = 0;
	Omax.State = 0;
	Omax.row = 0;
	Omax.col = 0;
}


int checkWinner()
{
int stat = 0 , i,j;
	for(i = 0; i < MAXI; ++i) {
		if(MatState.rowState[i] >= MAX_X_STATE ||
		   MatState.colState[i] >= MAX_X_STATE ) {
		    stat = 1;
		}
		if(MatState.rowState[i] == MAX_O_STATE ||
		   MatState.colState[i] == MAX_O_STATE )
		    stat = 2;
	}
	if(MatState.cross[0] >= MAX_X_STATE ||
	   MatState.cross[1] >= MAX_X_STATE)
		stat = 1;
	if(MatState.cross[0] == MAX_O_STATE ||
	   MatState.cross[1] == MAX_O_STATE)
		stat = 2;

		/*
	for(i=0; i < MAXROW && !stat ; ++i) {
		for(j=0; j < MAXCOL; ++j) {
			if(Mat[i][j].State.Xstate >= MAX_X_STATE) {
			      puts("X winner Determined\n");
				stat = 1;
			}
		}
	}
	*/
	return(stat);
}

int UserNear(int row , int col)
{
int stat = 0;

	if(row < MAXROW -1 ) {
	    stat = (Mat[row+1][col].Val == USERCHAR);
	    if(col < MAXCOL -1 && !stat) {
		stat = ((Mat[row+1][col+1].Val == USERCHAR) ||
			(Mat[row][col+1].Val == USERCHAR));
	    }
	    if(!stat && col > 1) {
		stat = (Mat[row+1][col-1].Val == USERCHAR);
	    }
	 }

	 if(row > 1 && !stat) {
		stat = (Mat[row-1][col].Val == USERCHAR);
		if(col > 1 && !stat) {
			stat = ((Mat[row-1][col-1].Val == USERCHAR) ||
			(Mat[row][col-1].Val == USERCHAR));
		}
		if(!stat && col < MAXCOL -1) {
			stat = (Mat[row-1][col+1].Val == USERCHAR);
		}
	  }
	  return(stat);
}
void computerPlay()
{
int row , col, i ,j, stat = 0;

	initMaxStates();

	for(i = 0; i < MAXROW - MAXI + 1 && !stat; ++i) {
		for(j = 0; j < MAXCOL -MAXJ + 1 && !stat; ++j) {
			initStates();
			if(stat = chooseMove(i,j)) {
				winnerFlag = stat;
				break;
			}
		 }
	}
	if(Xmax.State-10 > (-1 * Omax.State) ) {
		row = Xmax.row;
		col = Xmax.col;
	} else {
		row = Omax.row;
		col = Omax.col;
		if(Omax.State == (-60) && !winnerFlag) {
			winnerFlag = 2;
		}

	}
#ifdef DEBUG
	printMaxStates();
#endif
	if(winnerFlag != 1) {
		UpdateCell(row,col,COMPCHAR);
		Mat[row][col].Val = COMPCHAR;
	}
}


void InitMat()
{
int i, j, x , y;

	winnerFlag = 0;
	x = BOARD_LEFT +5;
	y = BOARD_TOP+5;
	Xmax.State = 0;
	Omax.State = 0;

	for(i=0; i < MAT_SIZE; ++i) {
		for(j = 0; j < MAT_SIZE; ++j) {
			Mat[i][j].xCord = x;
			Mat[i][j].yCord = y;
			Mat[i][j].Val = ' ';
			Mat[i][j].State.Xstate = 0;
			Mat[i][j].State.Ostate = 0;
			x+=GAP;
		}
		y+=GAP;
		x = BOARD_LEFT + 5;
	}

}
