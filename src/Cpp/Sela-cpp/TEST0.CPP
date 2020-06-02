// ex0.h
// "file of records" excercise, stage 0, test program.
// purpose: modular C program used as raw material for C++ processing.
// 25.02.92 avner ben coded.
// 06.03.93 avner ben last update.


#include <stdio.h>
#include <stdlib.h>
#include "ex0.h"

 // command-line arguments after process:
static char* fileName = "test.dat";
static int recSize = 40;

 // internal routines:
static void init( int argc, char* argv[] );

int main( int argc, char* argv[] )
{ // test program

        init( argc, argv );
        FileOfRecs input;
        openFile( &input, fileName, recSize );

         // print ( and count ) all records in file:
        Boolean rc = findNext( &input );
        for ( int fileSize = 0; rc; fileSize++ ) {
		printf( "%s\n", input.m_buf );
                rc=findNext( &input );
        }
        printf( "\nread %d records\n", fileSize );
         // show we can get back to start:
	rewind( input.m_file );
        findNext( &input );
	printf( "first record was: \"%s\"\n", input.m_buf );

        getchar();
        closeFile( &input );
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
