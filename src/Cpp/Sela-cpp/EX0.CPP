// ex0.cpp
// "file of records" excercise, stage 0, implementaton.
// purpose: modular C program used as raw material for C++ processing.
// 25.02.92 avner ben coded.
// 13.02.94 avner ben last update.

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "ex0.h"

void openFile( FileOfRecs* input, char* fileName, int recSize )
{
	input->m_file = fopen( fileName, "r" );
	if ( !input->m_file ) fatalError( "file not found" );
	input->m_buf = new char[recSize + 1];
	char* s = input->m_buf + recSize; *s = '\0';
	do *( --s ) = ' '; while ( s > input->m_buf );
	input->m_recSize = recSize;
}

void closeFile( FileOfRecs* input )
{
	fclose( input->m_file );
	delete input->m_buf;
}

Boolean findNext( FileOfRecs* input )
{
	char* s = input->m_buf;
	char c;
	 while( (c=getc( input->m_file )) != '\n' && !feof( input->m_file ))
		if( s < input->m_buf + input->m_recSize ) *s++ = c;	     
		 // else truncate...
	 if( c=='\n' || s > input->m_buf ) // space pad
		while ( s < input->m_buf + input->m_recSize) *s++ = ' '; 
	 return( s > input->m_buf );  // TRUE if non-empty record
}

void fatalError( const char* message )
{
	fprintf( stderr, "%s\n", message );
	getchar();
	exit( 1 );
}
