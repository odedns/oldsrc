/* ====================================================================== */
/*  This File Contains a routine based on pufi.h which display            */
/*  an English Error Message which is a sytem message                     */
/* ====================================================================== */
/*  By : Zakie Mashiah                                                    */
/* ====================================================================== */
#define __SYSERR_C

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <dos.h>
#include <mem.h>

#include "pufi.h"
#include <conio.h>

#define IGNORE            0x00
#define RETRY             0x01
#define TERMINATE         0x02
#define RETURN_TO_PROGRAM 0x10




/* *************************************************** */
/*  some prototypes of routines private to this module */
/* *************************************************** */
static int handler(int errval, int ax, int bp, int si);
static int err_window(void);


/* ******************************************************* */
/* below is a function that works with  errno of Turbo-C++ */
/* ******************************************************* */
void ShowSystemMessage(char X1, char Y1, char X2, char Y2)
{  Whistle(3);

   FlushKeyboardBuffer();
   ShowMessage(X1, Y1, X2, Y2, "[ System Error ]", sys_errlist[errno]);
} /* end of function ShowSystemMessage() */
/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */




/* ====================================================================== */
/*  The Following Part is Hard Error Handlers Routines                    */
/* ====================================================================== */
/*  By : Zakie Mashiah                                                    */
/* ====================================================================== */

/* ************************* */
/*  static variables         */
/* ************************* */

static char *dos_err_msg[] = { "Write Protected",
                               "Invalid Drive",
                               "Drive not ready",
                               "Unknown disk command",
                               "CRC Error",
                               "Bad request",
                               "Seek Error",
                               "Unformatted Disk",
                               "Sector not found",
                               "Printer out of paper",
                               "Write fault",
                               "Read fault",
                               "General Error",
                               "File share violation",
                               "File lock violation",
                               "Invalid disk change"
                             };

static char screen[800];
static char     msg[80];
static int ErrWinX1,ErrWinY1;
/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */



static int err_window(void)
{ char battr, wattr, choice;
  int  key;

  _setcursortype(_NOCURSOR);
  battr=Attr4.Frame; wattr=Attr4.Window;
  Attr4.Frame = Attr4.Window = 0x4e;
  XGetText(ErrWinX1, ErrWinY1, ErrWinX1+53, ErrWinY1+6, screen);
  DrawBox (ErrWinX1, ErrWinY1, ErrWinX1+53, ErrWinY1+6,
           DOUBLE_FR, NO, NOSHADOW);
  Attr4.Frame=battr; Attr4.Window=wattr;
  FillChAt(ErrWinX1+1, ErrWinY1+1, ErrWinX1+52, ErrWinY1+5, 0x20, 0x4e);

  PutStr( ErrWinX1+18,ErrWinY1, 0x4e, "[ Fatal Error ]");
  Paint(ErrWinX1+18, ErrWinY1, ErrWinX1+32, ErrWinY1, 0x4e);

  ShowButton(ErrWinX1+2,  ErrWinY1+4, 15, "Retry", YES);
  ShowButton(ErrWinX1+19, ErrWinY1+4, 15, "Leave Program", NO);
  ShowButton(ErrWinX1+36, ErrWinY1+4, 15, "Ignore Error",  NO);

  key = 0; choice = 0;
  PutStr( ErrWinX1+2, ErrWinY1+2, 0x4e, msg);
  Paint ( ErrWinX1+2, ErrWinY1+2, ErrWinX1+53, ErrWinY1+2, 0x4e);
  while ( key != CR )
    key = Scroll( ErrWinX1+2,     ErrWinY1+4,
                  ErrWinX1+36+15, ErrWinY1+4,
                  14, 17, HORIZONTAL_SCROLL,
                  &choice,
                  0x20, 0x2f);
  XPutText(ErrWinX1, ErrWinY1, ErrWinX1+53, ErrWinY1+6, screen);
  _setcursortype(_NORMALCURSOR);
  return choice;
} /* end of function err_window()  */
/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */



#pragma warn -par
static int handler(int errval, int ax, int bp, int si)
{ unsigned int    drive;
  unsigned int    di;
  int             errorno;
  struct DOSERROR xdoserror;


  /* get important parameters firs */
  di      = _DI;
  errorno = di & 0x00ff;
  drive   = ax & 0x00ff;

  memset( msg, 0, sizeof(msg) );
  if ( ax >= 0 )
     sprintf(msg, "%s on drive %c",dos_err_msg[errorno], drive+65);
  else
     if ( errorno <= 0x22 )
        sprintf(msg, "%s on device",  dos_err_msg[errorno]);
     else
        sprintf(msg, "Unknown Error");
  strcat(msg," Try Again?");
  dosexterr(&xdoserror);
  switch (xdoserror.de_action)
    { case 0x01 : // Retry First than ask user
      case 0x03 : // Prompt User
      case 0x04 : // Abort with cleanup
      case 0x07 : // Retry after user intervention
           switch ( err_window() )
            { case 0 : hardresume(RETRY);     break;
              case 1 : hardresume(TERMINATE); break;
              case 2 : hardresume(IGNORE);    break;
            }
           break;
       case 0x02 : // Retry after a delay than ask user
            for (drive = 1; drive < 65000u ; drive++); /* delay */
            hardresume(RETRY);
            break;
       case 0x05: // Abort without cleanup
            hardresume(TERMINATE);
       case 0x06:  /* ignore */
            hardresume(IGNORE);
            break;
    } /* end switch */
  return TERMINATE;
} /* end of function handler()  */
/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
#pragma warn +par






void SYSERR_Init(int X1, int Y1)
{ harderr(handler);
  ErrWinX1 = X1;
  ErrWinY1 = Y1;
} /* end of function SYSERR_Init()  */
/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
