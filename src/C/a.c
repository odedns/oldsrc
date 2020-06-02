

#include <stdio.h>
#include <windows.h>

#define BACKGROUND_WHITE  (WORD) 0x00f0
#define BACKGROUND_CYAN   (WORD) 0x0030
#define FOREGROUND_YELLOW (WORD) 0x0006
#define FOREGROUND_CYAN   (WORD) 0x0003
#define FOREGROUND_WHITE  (WORD) 0x0007


#define PERR(bSuccess, api) {if (!(bSuccess)) \
	printf("error: %s\n%s\n",api, GetLastError());}
/*
#define PERR(bSuccess, api) {if (!(bSuccess)) perr(__FILE__, __LINE__, \
		    api, GetLastError());}
*/

void print_it(char *s, WORD att);
void w32_perror(LPSTR msg);
void init();
void cls(HANDLE hConsole);

HANDLE hStdout;


void main(void)
{
	init();
	cls(hStdout);
	print_it("green | blue \n", FOREGROUND_GREEN | FOREGROUND_BLUE );
	print_it("green | red \n", FOREGROUND_GREEN | FOREGROUND_RED);
	print_it("blue | red \n", FOREGROUND_BLUE | FOREGROUND_RED);
	print_it("yellow\n", FOREGROUND_YELLOW);
	print_it("red \n", FOREGROUND_RED);
	print_it("blue \n", FOREGROUND_BLUE);
	print_it("green \n", FOREGROUND_GREEN);

}

void init()
{
	DWORD mode;
	CONSOLE_SCREEN_BUFFER_INFO  cons;

	hStdout = GetStdHandle(STD_OUTPUT_HANDLE);
	GetConsoleMode(hStdout,&mode);
	GetConsoleScreenBufferInfo(hStdout,&cons);
//	SetConsoleActiveScreenBuffer(hStdout);
}
void w32_perror(LPSTR msg)
{
	LPVOID lpmsg;
	DWORD err;

	err = GetLastError();
	if(!FormatMessage(FORMAT_MESSAGE_ALLOCATE_BUFFER |
			  FORMAT_MESSAGE_FROM_SYSTEM,NULL,err,
			  MAKELANGID(LANG_NEUTRAL,SUBLANG_DEFAULT),
			  (LPSTR)&lpmsg,0,NULL)) {
		fprintf(stderr,"w32_perror: FormatMessage Error %d\n",
				GetLastError());
		return;
	}
	fprintf(stderr, "%s: Error %d: %s\n",msg,err,lpmsg);
	LocalFree(lpmsg);
}

void print_it(char *s, WORD att)
{
int n;
BOOL rv;


	rv = SetConsoleTextAttribute(hStdout,att);
	if(!rv) {
		w32_perror("");
	}
	WriteConsole(hStdout,s,strlen(s),&n,NULL);
	FlushConsoleInputBuffer(hStdout);
}


/************************************************************************
* FUNCTION: cls(HANDLE hConsole)                                        *
*                                                                       *
* PURPOSE: clear the screen by filling it with blanks, then home cursor *
*                                                                       *
* INPUT: the console buffer to clear                                    *
*                                                                       *
* RETURNS: none                                                         *
*************************************************************************/

void cls(HANDLE hConsole)
{
  COORD coordScreen = { 0, 0 }; /* here's where we'll home the cursor */
  BOOL bSuccess;
  DWORD cCharsWritten;
  CONSOLE_SCREEN_BUFFER_INFO csbi; /* to get buffer info */
  DWORD dwConSize; /* number of character cells in the current buffer */

  /* get the number of character cells in the current buffer */
  bSuccess = GetConsoleScreenBufferInfo(hConsole, &csbi);
  PERR(bSuccess, "GetConsoleScreenBufferInfo");
  
  /*
  dwConSize = csbi.dwSize.X * csbi.dwSize.Y;
  */
  dwConSize = csbi.dwSize.X * 1;
  /* fill the entire screen with blanks */
  bSuccess = FillConsoleOutputCharacter(hConsole, (TCHAR) ' ',
      dwConSize, coordScreen, &cCharsWritten);
  PERR(bSuccess, "FillConsoleOutputCharacter");
  /* get the current text attribute */
  bSuccess = GetConsoleScreenBufferInfo(hConsole, &csbi);
  PERR(bSuccess, "ConsoleScreenBufferInfo");
  /* now set the buffer's attributes accordingly */
  bSuccess = FillConsoleOutputAttribute(hConsole, csbi.wAttributes,
      dwConSize, coordScreen, &cCharsWritten);
  PERR(bSuccess, "FillConsoleOutputAttribute");
  /* put the cursor at (0, 0) */

  /*
  bSuccess = SetConsoleCursorPosition(hConsole, coordScreen);
  */
  bSuccess = SetConsoleCursorPosition(hConsole, csbi.dwCursorPosition);

  PERR(bSuccess, "SetConsoleCursorPosition");
  return;
}

