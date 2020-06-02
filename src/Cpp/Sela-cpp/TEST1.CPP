// test1.cpp.
// "file of records" excercise, stage 1, school solution.
// 25.02.92 avner ben coded.
// 06.03.93 avner ben last update.

#include <stdio.h>
#include <stdlib.h>
#include "ex1.h"

 // command-line arguments after process:
static char* fileName = "test.dat";
static int recSize = 40;

 // internal routines:
static void init( int argc, char* argv[] );

int main( int argc, char* argv[] )
{ // test program

	init( argc, argv );
	FileOfRecs input( fileName,recSize ); // already gets first rec

	 // print ( and count ) all records in file:
	for ( int fileSize = 0; !input.m_eof; ) {
		fileSize++;
		printf( "%s\n", input.m_buf );
		input.findNext();
        }
	printf( "\nread %d records\n", fileSize );
	 // show we can get back to start:
	input.findFirst();
	printf( "first record was: \"%s\"\n", input.m_buf );

	getchar();
	 // no need to close the file here!
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
