// Sample code C++ course, lecture notes, item 3.2.3. box 3
// Copyright (c) 1994 Avner Ben.
// last update 9.2.94

#include <stdio.h>
#include <string.h>

#define newStrDup( s ) strcpy( new char[strlen( s ) + 1 ], s )

class Socket;
typedef long int WindowHandle;

class NetworkServer {
private:
	Socket** socketList;
	FILE* log;
	char* buf[2];
	WindowHandle mainWin;

	NetworkServer( NetworkServer& );
public:
	NetworkServer( )
	 { /* ... */ }
	void f()
	 { NetworkServer temp = *this; }

       // ...
};

main()
{
	NetworkServer a;
	NetworkServer b = a;

	a.f();

	return 0;
}