// ex5.cpp.
// "file of records" excercise, stage 5, school solution.
// 25.02.92 avner ben coded.
// 13.02.94 avner ben last update.

#include <stdlib.h>
#include <ctype.h>

#include "ex5.h"
#include "ex5a.h" // needs class FileOfTokens

Field:: Field( const Field& that )
 : m_s( new char[( m_size = strlen( that.m_s )) + 1] ),
 m_name( that.m_name ? new char[strlen( that.m_name ) + 1] : 0 )
{
	strcpy( m_s, that.m_s );
	if ( m_name ) strcpy( m_name, that.m_name );
}

void Field:: clear()
{
	if ( !m_size ) return;
	char* s = m_s + m_size;
	do  *( --s ) = ' '; while ( s > m_s );
}

Field& Field:: operator=( const Field& that )
{
	if ( &that == this ) return *this;
	delete m_s;
	m_s = new char[( m_size = that.m_size ) + 1];
	strcpy( m_s, that.m_s );
	if ( m_name ) delete m_name;
	if ( that.m_name ) {
		m_name = new char[strlen( that.m_name ) + 1];
		strcpy( m_name, that.m_name );
	} else m_name = 0;
	return *this;
}

Field& Field:: operator+=( const char* thatS )
{
	char* s = new char[( m_size += strlen( thatS ) ) + 1];
	strcpy( s, m_s );
	strcat( s, thatS );
	delete m_s;
	m_s = s;
	return *this;
}

const char* Field:: name() const
{
	if (m_name) return ( const char* )m_name;
	else return "";
}

Field:: Field( FileOfTokens& spec )
{
	if ( spec.current() == "name" ) {
		spec.getNext();
		m_name = new char[spec.current().length() + 1];
		strcpy( m_name, spec.current() );
		spec.getNext();
	} else m_name = 0;
	if ( !( m_size = atoi( spec.current() )))
		fatalError( "file definition error: invalid field specification" );
	m_s = new char[m_size + 1]; *( m_s + m_size ) = '\0';
	if ( m_name && ( int )strlen( m_name ) > m_size )
		m_name[m_size] = '\0'; // truncate long name
	clear();
	spec.getNext(); 	// for object following
}

Boolean Field:: loadFrom( ifstream& input, Boolean waitNewLine )
{
	char* s = m_s;
	char c;
	while( ( c=input.get() ) != '\n' && !input.eof() )
		if( s < m_s + m_size ) {
			*s++ = c;
			if ( !waitNewLine && s == m_s + m_size ) break;
		}
	if( c=='\n' || s > m_s )
		while ( s < m_s + m_size ) *s++ = ' ';
	return( s > m_s );
}

Boolean Field:: operator!() const
{
	for ( char* s = m_s; *s; s++ )
		if ( *s != ' ' ) return FALSE;
	return TRUE;
}

DataRecord:: DataRecord( FileOfTokens& spec )
{
	m_numFlds = 0; m_fld = 0;
	if ( !spec.current() )
		fatalError( "file definition error: record specification expected" );
	int length = atoi( spec.current() );
	if ( length ) {  // 1 field record ( old style )
		m_numFlds = 1;
		m_fld = new Field*; *m_fld = new Field( length );
		spec.getNext();
		return;
	}
	if ( spec.current() != "fields" )
		fatalError( "file definition error: \"fields\" expected" );
	spec.getNext();
	if ( spec.current() == "{" ) {
		for ( spec.getNext(); spec.current() != "}" && !spec.atEnd(); ) {
			 // destroy list, build new one
			Field** fld = new Field*[m_numFlds + 1];
			for ( int i = 0; i < m_numFlds; i++ )
				fld[i] = m_fld[i];
			m_fld = fld;
			m_fld[m_numFlds++] = new Field( spec );
		}
	}  else  {  // 1 field record
		m_numFlds = 1;
		m_fld = new Field*; m_fld[0] = new Field( spec );
	}
	if ( !m_numFlds ) fatalError( "file definition error: field list empty" );
	spec.getNext(); // for the object that follows
}

DataRecord:: DataRecord( const DataRecord& that )
 : m_numFlds( that.m_numFlds )
{
	m_fld = new Field*[m_numFlds];
	for ( int i = 0; i < m_numFlds; i++ )
		m_fld[i] = new Field( *that.m_fld[i] );
}

DataRecord:: DataRecord( int sz )
 : m_fld( new Field* ), m_numFlds( 1 )
{ *m_fld = new Field( sz ); }

DataRecord:: DataRecord( const char* source )
 : m_fld( new Field* ), m_numFlds( 1 )
{ *m_fld = new Field( source ); }

DataRecord:: ~DataRecord()
{
	for ( int i = 0; i < m_numFlds; i++ )
		delete m_fld[i];
	delete m_fld;
}

DataRecord& DataRecord:: operator=( const DataRecord& that )
{
	if ( &that == this ) return *this;
	for ( int i = 0; i < m_numFlds; i++ )
		delete m_fld[i];
	delete m_fld;
	m_fld = new Field*[( m_numFlds = that.m_numFlds )];
	for ( i = 0; i < m_numFlds; i++ )
		m_fld[i] = new Field( *that.m_fld[i] );
	return *this;
}

Boolean DataRecord:: loadFrom( ifstream& stream )
{
	for ( int i = 0; i < m_numFlds; i++ )
		if ( !m_fld[i]->loadFrom( stream, i == m_numFlds - 1 )) break;
	if ( !i ) return FALSE; // eof on start
	for ( ; i < m_numFlds; i++ ) // blank-pad is now on field level
		m_fld[i]->clear();
	return TRUE;
}

int DataRecord:: length() const
{
	for ( int result = 0, i = 0; i < m_numFlds; i++ )
		result += m_fld[i]->length();
	return result;
}

DataRecord:: operator Field() const
{
	Field result = *m_fld[0];
	for ( int i = 1; i < m_numFlds; i++ )
		result += *m_fld[i];
	return result;
}

Boolean DataRecord:: operator==( const DataRecord& that ) const
{
	if ( m_numFlds != that.m_numFlds ) return FALSE;
	for ( int i = 0; i < m_numFlds; i++ )
		if ( *m_fld[i] != ( const char* )*( that.m_fld[i] ) ) return FALSE;
	return TRUE;
}

ostream& operator<<( ostream& output, const DataRecord& input )
{
	for ( int i = 0; i < input.m_numFlds; i++ )
		output << *input.m_fld[i];
	return output;
}

FileOfRecs:: FileOfRecs( FileOfTokens& spec, const char* fileName )
 : m_file( fileName, ios::in | ios::nocreate ),
 m_buf( spec ), // first to parse
 m_eof( FALSE )
{
	if ( m_file.bad() ) fatalError( "file not found" );
	if ( spec.current() == "name" ) {
		spec.getNext();
		m_name = new char[spec.current().length() + 1];
		strcpy( m_name, spec.current() );
		 // name
		spec.getNext();
	} else m_name = 0;
	findNext();
}

FileOfRecs:: FileOfRecs( const char* fileName, int recSize )
 : m_file( fileName, ios::in | ios::nocreate ),
 m_buf( recSize ),
 m_eof( FALSE ),
 m_name( 0 )
{
	if ( m_file.bad() ) fatalError( "file not found" );
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

