// ex4.cpp.
// "file of records" excercise, stage 4, school solution.
// 25.02.92 avner ben coded.
// 13.02.94 avner ben last update.

#include <stdlib.h>

#include "ex4.h"

DataRecord:: DataRecord( const DataRecord& that )
 : m_s( new char[( m_size = strlen( that.m_s )) + 1] )
{ strcpy( m_s, that.m_s ); }

DataRecord:: DataRecord( int sz )
 : m_s( new char[( m_size = sz ) + 1] )
{
	if ( sz < 1 ) fatalError( "invalid record size" );
	char* t = m_s + m_size; *t = '\0';
	do  *( --t ) = ' '; while ( t > m_s );
}

DataRecord& DataRecord:: operator=( const DataRecord& that )
{
	if ( &that == this ) return *this;
	delete m_s;
	m_s = new char[( m_size = that.m_size ) + 1];
	strcpy( m_s, that.m_s );
	return *this;
}

Boolean DataRecord:: loadFrom( ifstream& input )
{
	char* s = m_s;
	char c;
	while( ( c=input.get() ) != '\n' && !input.eof() )
		if( s < m_s + m_size ) *s++ = c;
	if( c=='\n' || s > m_s )
		while ( s < m_s + m_size ) *s++ = ' ';
	return( s > m_s );
}

FileOfRecs:: FileOfRecs( char* fileName, int recSize )
 : m_file( fileName, ios::in | ios::nocreate ),
 m_buf( recSize ),
 m_eof( FALSE )
{
	if ( m_file.bad() ) fatalError( "file not found");
	findNext();
}

Boolean FileOfRecs:: findFirst()
{
	m_file.clear();
	m_file.seekg( 0L );
	m_eof = FALSE;
	return findNext();
}

Boolean FileOfRecs:: findNext()
{
	if ( !m_buf.loadFrom( m_file )) m_eof = TRUE;
	return !m_eof;
}

DataRecord FileOfRecs:: operator[]( int recNum )
{
	if (recNum < 1 ) return "";
	DataRecord recBefore = m_buf;
	long int posBefore = m_file.tellg();
	for ( findFirst(); !m_eof; findNext() )
		if ( !--recNum ) return m_buf; // found
	// not found
	m_file.clear(); m_file.seekg( posBefore );
	m_buf = recBefore;
	return "";
}

void fatalError( const char* message )
{
	cerr << message << endl;
	cin.get();
	exit( 1 );
}
