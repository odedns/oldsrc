/*--------------------------------------------------------
   Template for win32 programs.
                 (c) Oded Nissan
  --------------------------------------------------------*/

#include <windows.h>

LRESULT CALLBACK WndProc(HWND hwnd,
                         UINT uMessage,
                         WPARAM wparam,
                         LPARAM lparam);


/*-------------------*\
 * Functions         *
\*-------------------*/
void w32_print(HDC hdc, LPSTR s, UINT flags );

int WINAPI WinMain (HINSTANCE hInstance, HINSTANCE hPrevInstance,
                    LPSTR lpszCmdParam, int nCmdShow)
{
static char szAppName[] = "Template App" ;

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
		    "Template Program",     // window caption
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

 
   		

	while(GetMessage(&msg,NULL,0,0)) {
		TranslateMessage(&msg);
		DispatchMessage(&msg);
	}
	return 0;
}





//
//  FUNCTION: WndProc(HWND, UINT, WPARAM, LPARAM)
//
//  PURPOSE:  Processes messages
//
//  PARAMETERS:
//    hwnd     - window handle
//    uMessage - message number
//    wparam   - additional information (dependant of message number)
//    lparam   - additional information (dependant of message number)
//
//  MESSAGES:
//
//    WM_COMMAND    - exit command
//    WM_DESTROY    - destroy window
//
//  RETURN VALUE:
//
//    Depends on the message number.
//
//  COMMENTS:
//
//

#define LINES 30
LRESULT CALLBACK WndProc(HWND hwnd,
                         UINT uMessage,
                         WPARAM wparam,
                         LPARAM lparam)
{
	PAINTSTRUCT ps;
	TEXTMETRIC tm;
	HDC hdc;
	static RECT rect;
	static long cxChar, cyChar, cxCaps;
	char buff[256];
	int i;

	switch (uMessage) {

	//
	// **TODO** Add cases here for application messages
	//
		case WM_CREATE:
			GetClientRect(hwnd,&rect);
			hdc = GetDC(hwnd);
			GetTextMetrics(hdc,&tm);
			cxChar = tm.tmAveCharWidth;
			cxCaps = (tm.tmPitchAndFamily & 1 ? 3 : 2) *  cxChar /2;
			cyChar = tm.tmHeight + tm.tmExternalLeading;
			ReleaseDC(hwnd,hdc);

			return(0);

		case WM_PAINT:
			
			hdc = BeginPaint(hwnd,&ps);


#if 0
			SetTextAlign(hdc,TA_LEFT);
			for(i=0; i < LINES; ++i) {
				wsprintf(buff,"This is Line %d",i);
				TextOut(hdc,cxChar,i * cyChar ,buff,lstrlen(buff));
			}
#endif



			DrawText(hdc,"First Line",-1,&rect,0);
			MoveToEx(hdc,50,50,NULL);
			DrawText(hdc,"Second Line",-1,&rect,0);

			EndPaint(hwnd,&ps);
			return(0);

		case WM_COMMAND: // message: command from application menu

	    	// Message packing of wparam and lparam have changed for Win32,
	    	// so use the GET_WM_COMMAND macro to unpack the commnad

#if 0
	    	switch (GET_WM_COMMAND_ID(wparam,lparam)) {

		//
		// **TODO** Add cases here for application specific commands
		//

			case IDM_EXIT:
			    DestroyWindow(hwnd);
			    break;


			default:
			    return DefWindowProc(hwnd, uMessage, 
					    wparam, lparam);
		}
#endif
		break;

		case WM_DESTROY:  // message: window being destroyed
			PostQuitMessage(0);
			break;

		default:          // Passes it on if unproccessed
			return DefWindowProc(hwnd, uMessage, wparam, lparam);
	} // end switch

	return 0;
} // end WndProc 

void w32_print(HDC hdc, LPSTR s, UINT flags )
{
	SetTextAlign(hdc,flags | TA_UPDATECP);
	TabbedTextOut(hdc,0,0,s,lstrlen(s),0,0,0);

}

