#ifndef _GOTHELLO_H
#define _GOTHELLO_H

/* Macros *\
\* -------*/

#define MAT_SIZE      (20)
#define BOARD_LEFT    (100)
#define BOARD_RIGHT   (460)
#define BOARD_TOP     (100)
#define BOARD_BOTTOM  (460)
#define GAP ((BOARD_RIGHT - BOARD_LEFT) / MAT_SIZE)

#ifndef MAXROW
#define MAXROW (20)
#define MAXCOL (20)
#endif
#define MAXI   (4)
#define MAXJ   (4)
#define USERCHAR ('X')
#define COMPCHAR ('O')
#define MAX_X_STATE  (80)
#define MAX_O_STATE  (-80)
#define USER_VAL     (20)
#define COMP_VAL     (-20)
#define IS_EMPTY(m) (m.Val == ' ')
#define IS_X(m)     (m.Val == USERCHAR)
#define IS_O(m)     (m.Val == COMPCHAR)


/* Typedefs *\
\* -------- */

typedef struct {
	int State;
	int row;
	int col;
	} Cords_t;


typedef struct {
	int Xstate;
	int Ostate;
	} state_t;

typedef struct {
	unsigned char Val;
	int xCord;
	int yCord;
	state_t State;
	} MatBox_t;

typedef struct {
	int rowState[4];
	int colState[4];
	int cross[2];
	} MatState_t;

/* global vars *\
\* ----------- */

EXTERN MatBox_t Mat[MAT_SIZE][MAT_SIZE];
EXTERN MatState_t MatState;
EXTERN int winnerFlag;
EXTERN Cords_t Xmax;
EXTERN Cords_t Omax;



/* Functions *\
\*---------- */

void DrawBoard();
void PlayGame();
void InitMat();
int  UpdateCell(int i , int j, char c);
void EnterCell(int i , int j);
void LeaveCell(int i , int j);
void DrawHeader();
int checkMat(int rowStart, int colStart);
int getVal(int row , int col);
int putState(int state , int row , int col);
int updateStates(int rowStart , int colStart);
int chooseMove(int rowStart, int colStart);
void initStates();
void initMaxStates();
int checkWinner();
void computerPlay();
void printWinnerMsg();
void DrawFooter();
void GraphInit();
void printMaxStates();
void DisplayHelp();
int getkey();
int UserNear(int row , int col);
#endif