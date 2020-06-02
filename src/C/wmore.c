
#include <windows.h>

#define NUMLINES 100
#define min(a,b) (((a) < (b)) ? (a) : (b))
#define max(a,b) (((a) > (b)) ? (a) : (b))

long FAR PASCAL WndProc (HWND, UINT, UINT, LONG) ;
LPSTR win32_read_text_file(LPSTR fname);

char fname[256];

int PASCAL WinMain (HANDLE hInstance, HANDLE hPrevInstance,
                    LPSTR lpszCmdLine, int nCmdShow)
     {
     static char szAppName[] = "Wmore" ;
     HWND        hwnd ;
     MSG         msg ;
     WNDCLASS    wndclass ;


	 /*
     if(*lpszCmdLine == '\0') {
	     MessageBox(NULL,"File Name Required",szAppName,MB_OK);
	     exit(1);
     }
	 */

     strcpy(fname,"c:\\tmp\\makefile");

     if (!hPrevInstance) 
          {
          wndclass.style         = CS_HREDRAW | CS_VREDRAW ;
          wndclass.lpfnWndProc   = WndProc ;
          wndclass.cbClsExtra    = 0 ;
          wndclass.cbWndExtra    = 0 ;
          wndclass.hInstance     = hInstance ;
          wndclass.hIcon         = LoadIcon (NULL, IDI_APPLICATION) ;
          wndclass.hCursor       = LoadCursor (NULL, IDC_ARROW) ;
          wndclass.hbrBackground = GetStockObject (WHITE_BRUSH) ;
          wndclass.lpszMenuName  = NULL ;
          wndclass.lpszClassName = szAppName ;

          RegisterClass (&wndclass) ;
          }

     hwnd = CreateWindow (szAppName, "Wmore",
                          WS_OVERLAPPEDWINDOW | WS_VSCROLL | WS_HSCROLL,
                          CW_USEDEFAULT, CW_USEDEFAULT,
                          CW_USEDEFAULT, CW_USEDEFAULT,
                          NULL, NULL, hInstance, NULL) ;

     ShowWindow (hwnd, nCmdShow) ;
     UpdateWindow (hwnd) ;

     while (GetMessage (&msg, NULL, 0, 0))
          {
          TranslateMessage (&msg) ;
          DispatchMessage (&msg) ;
          }
     return msg.wParam ;
     }

long FAR PASCAL WndProc (HWND hwnd, UINT message, UINT wParam,
                                                          LONG lParam)
     {
     static short  cxChar, cxCaps, cyChar, cxClient, cyClient, nMaxWidth,
                   nVscrollPos, nVscrollMax, nHscrollPos, nHscrollMax ;
     HDC           hdc ;
     short         i, x, y, nPaintBeg, nPaintEnd, nVscrollInc, nHscrollInc ;
     PAINTSTRUCT   ps ;
     TEXTMETRIC    tm ;
     static LPSTR 	   pbuff;
     static RECT  	   rect;

     switch (message)
          {
          case WM_CREATE:
               hdc = GetDC (hwnd) ;

               GetTextMetrics (hdc, &tm) ;
	       GetClientRect(hwnd,&rect);

               cxChar = tm.tmAveCharWidth ;
               cxCaps = (tm.tmPitchAndFamily & 1 ? 3 : 2) * cxChar / 2 ;
               cyChar = tm.tmHeight + tm.tmExternalLeading ;

               ReleaseDC (hwnd, hdc) ;

               nMaxWidth = 40 * cxChar + 22 * cxCaps ;
	       pbuff = win32_read_text_file(fname);
               return 0 ;

          case WM_SIZE:
               cxClient = LOWORD (lParam) ;
               cyClient = HIWORD (lParam) ;

	       nVscrollMax = max (0, NUMLINES + 2 - cyClient / cyChar) ;
               nVscrollPos = min (nVscrollPos, nVscrollMax) ;

               SetScrollRange (hwnd, SB_VERT, 0, nVscrollMax, FALSE) ;
               SetScrollPos   (hwnd, SB_VERT, nVscrollPos, TRUE) ;

	       nHscrollMax = max (0, 2 + (nMaxWidth - cxClient) / cxChar) ;
               nHscrollPos = min (nHscrollPos, nHscrollMax) ;

               SetScrollRange (hwnd, SB_HORZ, 0, nHscrollMax, FALSE) ;
               SetScrollPos   (hwnd, SB_HORZ, nHscrollPos, TRUE) ;
               return 0 ;

          case WM_VSCROLL:
               switch (wParam)
                    {
                    case SB_TOP:
                         nVscrollInc = -nVscrollPos ;
                         break ;

                    case SB_BOTTOM:
                         nVscrollInc = nVscrollMax - nVscrollPos ;
                         break ;

                    case SB_LINEUP:
                         nVscrollInc = -1 ;
                         break ;

                    case SB_LINEDOWN:
                         nVscrollInc = 1 ;
                         break ;

                    case SB_PAGEUP:
                         nVscrollInc = min (-1, -cyClient / cyChar) ;
                         break ;

                    case SB_PAGEDOWN:
                         nVscrollInc = max (1, cyClient / cyChar) ;
                         break ;

                    case SB_THUMBTRACK:
                         nVscrollInc = LOWORD (lParam) - nVscrollPos ;
                         break ;

                    default:
                         nVscrollInc = 0 ;
                    }
               nVscrollInc = max (-nVscrollPos,
                             min (nVscrollInc, nVscrollMax - nVscrollPos)) ;

               if (nVscrollInc != 0)
                    {
                    nVscrollPos += nVscrollInc ;
                    ScrollWindow (hwnd, 0, -cyChar * nVscrollInc, NULL, NULL) ;
                    SetScrollPos (hwnd, SB_VERT, nVscrollPos, TRUE) ;
                    UpdateWindow (hwnd) ;
                    }
               return 0 ;

          case WM_HSCROLL:
               switch (wParam)
                    {
                    case SB_LINEUP:
                         nHscrollInc = -1 ;
                         break ;

                    case SB_LINEDOWN:
                         nHscrollInc = 1 ;
                         break ;

                    case SB_PAGEUP:
                         nHscrollInc = -8 ;
                         break ;

                    case SB_PAGEDOWN:
                         nHscrollInc = 8 ;
                         break ;

                    case SB_THUMBPOSITION:
                         nHscrollInc = LOWORD (lParam) - nHscrollPos ;
                         break ;

                    default:
                         nHscrollInc = 0 ;
                    }
               nHscrollInc = max (-nHscrollPos,
                             min (nHscrollInc, nHscrollMax - nHscrollPos)) ;

               if (nHscrollInc != 0)
                    {
                    nHscrollPos += nHscrollInc ;
                    ScrollWindow (hwnd, -cxChar * nHscrollInc, 0, NULL, NULL) ;
                    SetScrollPos (hwnd, SB_HORZ, nHscrollPos, TRUE) ;
                    }
               return 0 ;

          case WM_KEYDOWN:
               switch (wParam)
                    {
                    case VK_HOME:
                         SendMessage (hwnd, WM_VSCROLL, SB_TOP, 0L) ;
                         break ;

                    case VK_END:
                         SendMessage (hwnd, WM_VSCROLL, SB_BOTTOM, 0L) ;
                         break ;

                    case VK_PRIOR:
                         SendMessage (hwnd, WM_VSCROLL, SB_PAGEUP, 0L) ;
                         break ;

                    case VK_NEXT:
                         SendMessage (hwnd, WM_VSCROLL, SB_PAGEDOWN, 0L) ;
                         break ;

                    case VK_UP:
                         SendMessage (hwnd, WM_VSCROLL, SB_LINEUP, 0L) ;
                         break ;

                    case VK_DOWN:
                         SendMessage (hwnd, WM_VSCROLL, SB_LINEDOWN, 0L) ;
                         break ;

                    case VK_LEFT:
                         SendMessage (hwnd, WM_HSCROLL, SB_PAGEUP, 0L) ;
                         break ;

                    case VK_RIGHT:
                         SendMessage (hwnd, WM_HSCROLL, SB_PAGEDOWN, 0L) ;
                         break ;
                    }
               return 0 ;

          case WM_PAINT:
               hdc = BeginPaint (hwnd, &ps) ;

	       DrawText(hdc,pbuff,-1,&rect,0);

               EndPaint (hwnd, &ps) ;
               return 0 ;

          case WM_DESTROY:
               PostQuitMessage (0) ;
               return 0 ;
          }

     return DefWindowProc (hwnd, message, wParam, lParam) ;
}

void win32_perror(LPSTR msg)
{
	LPVOID lpmsg;
	DWORD err;
	char errbuff[1024];

	err = GetLastError();
	if(!FormatMessage(FORMAT_MESSAGE_ALLOCATE_BUFFER |
			  FORMAT_MESSAGE_FROM_SYSTEM,NULL,err,
			  MAKELANGID(LANG_NEUTRAL,SUBLANG_DEFAULT),
			  (LPSTR)&lpmsg,0,NULL)) {
		wsprintf(errbuff,"w32_perror: FormatMessage Error %d\n",
				GetLastError());
		MessageBox(NULL,errbuff,NULL,MB_OK);
		return;
	}
	wsprintf(errbuff, "%s: Error %d: %s\n",msg,err,lpmsg);
	MessageBox(NULL,errbuff,NULL,MB_OK);
	LocalFree(lpmsg);
}

LPSTR win32_read_text_file(LPSTR fname)
{
	HANDLE	fHandle;
	BOOL	rv;
	LPSTR buff;
	DWORD fsize, nread;


	fHandle = CreateFile(fname,GENERIC_READ,0,0,
			     OPEN_EXISTING,0,0);
	if(INVALID_HANDLE_VALUE == fHandle) {
		win32_perror("CreateFile");
		return(NULL);
	}

	fsize = GetFileSize(fHandle,NULL);
	if(fsize == 0xFFFFFFFF) {
		win32_perror("GetFileSize");
		return(NULL);
	}
	buff = GlobalAlloc(GMEM_FIXED,fsize +1);
	if(NULL == buff) {
		win32_perror("GlobalAlloc");
		return(NULL);
	}
	rv = ReadFile(fHandle,buff,fsize,&nread,0);
	if(!rv ) {
		win32_perror("ReadFile");
		return(NULL);
	}
	CloseHandle(fHandle);
	buff[fsize] = '\0';

	return(buff);
}
