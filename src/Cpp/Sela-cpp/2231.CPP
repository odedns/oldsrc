// Sample code C++ course, lecture notes, item 2.2.3.
// Copyright (c) 1994 Avner Ben.
// last update 27.2.94

#include <stdio.h>
#include <string.h>

typedef int Boolean;

struct OutputString {
	char s[256];
	void add( char* msg )
	 { strcat( s, msg ); strcat( s, "\n" ); }
	~OutputString() 
	 { printf( s ); }
};

OutputString msg;

struct FileOfRecs {
	char fname[13];
	FileOfRecs( char* s ) {
		strcpy( fname, s );
		msg.add( "ctor " );
		msg.add( fname );
	}
	~FileOfRecs() {
		msg.add( "dtor " );
		msg.add( fname );
	}
};

FileOfRecs file1( "file1.dat" );

void f( Boolean sw )
{
	msg.add( "f begin************" );
	if ( sw ) return;
	FileOfRecs file2( "file2.dat" );
	static FileOfRecs file3( "test3.dat" );
	msg.add( "f end**************" );
}                 

main()
{
	msg.add( "program begin************" );
	f( 1 );
	f( 1 );
	msg.add( "program end**************" ); 
	
	return 0;
}

