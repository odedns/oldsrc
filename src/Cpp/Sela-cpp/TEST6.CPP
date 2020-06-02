// test6.cpp.
// "file of records" excercise, stage 6, school solution.
// 25.02.94 avner ben adapted from test5..

#include "ex6.h"
#include "ex6a.h"
#include "ex6b.h"
#include "ex6c.h"

 // command-line arguments after process:
static char* fileName = "test6.dat";
static int recSize = 40;

 // internal routines:
static void init( int argc, char* argv[] );

int main( int argc, char* argv[] )
{ // test program

	init( argc, argv );
	FileOfTokens theSpec( fileName, "fil" );
	FileOfRecs input( theSpec, fileName );
	FileOfRecsView theView( input );
	
	 // pass 1: list file forwards  
	theView.listTitle( cout );
	for ( int fileSize = 0; !input.atEnd(); ) {
		fileSize++;
		cout << theView;
		input.findNext();
	}                             
	
	 // pass 2: list file backwards 
	cout << endl;
	for ( ; fileSize; fileSize-- ) {
		cout << input[fileSize] << endl;
	}

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
