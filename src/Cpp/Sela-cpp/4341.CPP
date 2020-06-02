// Sample code C++ course, lecture notes, item 4.3.4.1.
// Copyright (c) 1994 Avner Ben.
// last update 16.2.94

#include <fstream.h>
#include <stdlib.h>

static void fatalError( const char* msg )
{
	cout << msg << endl;
	exit( 1 );
}

main()
{
	cout << "file name: ";
	char fileName[13];
	cin.get( fileName, 13 );
	ifstream input( fileName );
	if ( input.bad() )
		fatalError( "file not found!" );
	input.get();
	if ( input.eof() )
		fatalError( "file is empty!" );

	return 0;
}
