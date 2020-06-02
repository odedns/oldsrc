// ex3.cpp.
// "file of records" excercise, stage 3, school solution.
// 25.02.92 avner ben coded.
// 13.02.94 avner ben last update.

#include <stdlib.h>
#include <string.h>

#include "ex3.h"

DataRecord:: DataRecord( const DataRecord& that )
 : m_s( new char[strlen( that.m_s ) + 1] )
{
	strcpy( m_s, that.m_s );
}

DataRecord& DataRecord:: operator=( const DataRecord& that )
{
	if ( &that == this ) return *this;
	delete m_s;
	m_s = new char[strlen( that.m_s ) + 1];
	strcpy( m_s, that.m_s );
	return *this;
}

FileOfRecs:: FileOfRecs( char* fileName, int recSize )
 : m_recSize( recSize ),
 m_eof( FALSE )
{

	if ( m_recSize < 1 ) fatalError( "size less than 1" );
	m_file = fopen( fileName, "r" );
	if ( !m_file ) fatalError( "file not found" );
	m_buf = new char[m_recSize + 1];
	char* s = m_buf + m_recSize; *s = '\0';
	do  *( --s ) = ' '; while ( s > m_buf );
	findNext();
}

FileOfRecs:: ~FileOfRecs()
{
	fclose( m_file );
	delete m_buf;
}

Boolean FileOfRecs:: findFirst()
{
	rewind( m_file );
	m_eof = FALSE;
	return findNext();
}

Boolean FileOfRecs:: findNext()
{
	char* s = m_buf;
	char c;
	while( (c=getc( m_file )) != '\n' && !feof( m_file ))
		if( s < m_buf + m_recSize ) *s++ = c;
	if( c=='\n' || s > m_buf )
		while ( s < m_buf + m_recSize ) *s++ = ' ';
	return( !( m_eof = ( s == m_buf )));
}

DataRecord FileOfRecs:: operator[]( int recNum )
{
	if (recNum < 1 ) return "";
	DataRecord recBefore = m_buf;
	long int posBefore = ftell( m_file );
	for ( findFirst(); !m_eof; findNext() )
		if ( !--recNum ) return m_buf; // found
	// not found
	fseek( m_file, posBefore, SEEK_SET );
	strcpy( m_buf, recBefore );
	return "";
}

void fatalError( const char* message )
{
	cerr << message << endl;
	cin.get();
	exit( 1 );
}
