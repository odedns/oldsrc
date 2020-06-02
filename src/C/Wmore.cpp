/*--------------------------------------------------------
   HELLOWIN.C -- Displays "Hello, Windows" in client area
                 (c) Charles Petzold, 1992
  --------------------------------------------------------*/

#include <windows.h>

long FAR PASCAL WndProc (HWND, UINT, UINT, LONG) ;

int PASCAL WinMain (HANDLE hInstance, HANDLE hPrevInstance,
                    LPSTR lpszCmdParam, int nCmdShow)
     {
     static char szAppName[] = "Wmore" ;
     HWND        hwnd ;
     MSG         msg ;
     WNDCLASS    wndclass ;

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

     hwnd = CreateWindow (szAppName,         // window class name
		    "The Hello Program",     // window caption
                    WS_OVERLAPPEDWINDOW,     // window style
                    CW_USEDEFAULT,           // initial x position
                    CW_USEDEFAULT,           // initial y position
                    CW_USEDEFAULT,           // initial x size
                    CW_USEDEFAULT,           // initial y size
                    NULL,                    // parent window handle
                    NULL,                    // window menu handle
                    hInstance,               // program instance handle
		    NULL) ;		     // creation parameters

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
     HDC         hdc ;
     PAINTSTRUCT ps ;
     RECT	 rect ;
	 char s[256];
	 int i;

     switch (message)
          {
          case WM_PAINT:
	       hdc = BeginPaint (hwnd, &ps) ;

               GetClientRect (hwnd, &rect) ;

			   for( i=0; i < 15; ++i) {
				    wsprintf(s,"This is line %d\nPoo ther Dov\n",i);
					DrawText (hdc, s, -1, &rect, 0);
			   }

	       EndPaint (hwnd, &ps) ;
               return 0 ;

          case WM_DESTROY:
               PostQuitMessage (0) ;
               return 0 ;
          }

     return DefWindowProc (hwnd, message, wParam, lParam) ;
     }
