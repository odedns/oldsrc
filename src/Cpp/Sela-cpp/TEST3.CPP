// test3.cpp.
// "file of records" excercise, stage 3, school solution.
// 25.02.92 avner ben coded.
// 08.06.93 avner ben last update.

#include <stdlib.h>
#include "ex3.h"

 // command-line arguments after process:
static char* fileName = "test.dat";

static int recSize = 40;

 // internal routines:
static void init( int argc, char* argv[] );

int main( int argc, char* argv[] )
{ // test program

	init( argc, argv );
	FileOfRecs input( fileName, recSize );

	 // pass 1: list file forwards
	for ( int fileSize = 0; !input.atEnd(); ) {
		fileSize++;
		cout << input << endl; // op<<( Ostream&, FileOfrecs& )
		input.findNext();
	}
	
	 // pass 2: list file backwards 
	cout << endl;
	for ( ; fileSize; fileSize-- )
		cout << input[fileSize] << endl; // op<<( Ostream&, const char* )

	cin.get();
	return 0;
}

static void init( int argc, char* argv[] )
{ // process command-line arguments

        if ( argc >= 2 ) {
                fileName = argv[1];
		if ( argc >= 3 )
			if ( ( recSize = atoi( argv[2] )) < 1 )
				fatalError( "illegal record size" );
        }
}
