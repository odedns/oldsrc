// ex6.cpp.
// "file of records" excercise, stage 6, school solution.
// 25.02.94 avner ben adapted from ex5..

#include <stdlib.h>
#include <ctype.h>

#include "ex6.h"
#include "ex6a.h" // needs class FileOfTokens

String& String::operator=( const String& rhs ) { 
	if ( &rhs != this ) { 
		delete m_s; 
		m_s = strcpy( new char[strlen( rhs.m_s ) + 1], rhs.m_s ); 
	}
	return *this;
}
	                                   
String& String::operator+=( const String& rhs ) {
	char* t = new char[strlen( m_s ) + strlen( rhs.m_s ) + 1 ];
	strcpy( t, m_s ); strcat( t, rhs.m_s );
	delete m_s; m_s = t;
	return *this;
}

String::String( int size ) 
 : m_s( new char[size + 1] ) 
{  
	m_s[size] = '\0';
	if ( !size ) return;
	char* s = m_s + size;
	do  *( --s ) = ' '; while ( s > m_s );
}

Field:: Field( const Field& that )
 : m_size( that.m_size )
{
	if ( that.m_name ) 
		m_name = strcpy( new char[strlen( that.m_name ) + 1], that.m_name );
	else m_name =  0;
}


Field& Field:: operator=( const Field& that )
{
	if ( &that == this ) return *this;
	m_size = that.m_size;
	if ( m_name ) delete m_name;
	if ( that.m_name ) 
		m_name = strcpy( new char[strlen( that.m_name ) + 1], that.m_name );
	else m_name =  0;
	return *this;
}

const char* Field:: name() const
{
	if (m_name) return ( const char* )m_name;
	return "";
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
	if ( m_name && ( int )strlen( m_name ) > m_size )
		m_name[m_size] = '\0'; // truncate long name
	spec.getNext();         // for object following
}

DataRecord:: DataRecord( FileOfTokens& spec )
 : m_numFlds( 0 ), m_fld( 0 )
{
	if ( !spec.current() )
		fatalError( "file definition error: record specification expected" );
	int length = atoi( spec.current() );
	if ( length ) {  // 1 field record ( old style )
		m_numFlds = 1;
		m_fld = new Field*[1]; *m_fld = Field::createDefault( length );
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
			if (m_fld) delete m_fld; m_fld = fld;
			m_fld[m_numFlds++] = Field::create( spec );
		}
	}  else  {  // 1 field record
		m_numFlds = 1;
		m_fld = new Field*[1]; m_fld[0] = Field::create( spec );
	}
	if ( !m_numFlds ) fatalError( "file definition error: field list empty" );
	spec.getNext(); // for the object that follows
}

DataRecord:: DataRecord( const DataRecord& that )
 : m_numFlds( that.m_numFlds )
{
	m_fld = new Field*[m_numFlds];
	for ( int i = 0; i < m_numFlds; i++ )
		m_fld[i] = that.m_fld[i]->clone();
}

DataRecord:: DataRecord( int sz )
 : m_fld( new Field*[1] ), m_numFlds( 1 )
{ *m_fld = Field::createDefault( sz ); }

DataRecord:: DataRecord( const char* source )
 : m_fld( new Field*[1] ), m_numFlds( 1 )
{ *m_fld = Field::createDefault( source ); }

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
		m_fld[i] = that.m_fld[i]->clone();
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

DataRecord:: operator String() const
{
	String result = m_fld[0]->str();
	for ( int i = 1; i < m_numFlds; i++ )
		result += m_fld[i]->str();
	return result;
}

Boolean DataRecord:: operator==( const DataRecord& that ) const
{
	if ( m_numFlds != that.m_numFlds ) return FALSE;
	for ( int i = 0; i < m_numFlds; i++ )
		if ( !m_fld[i]->equalTo( that.m_fld[i] )) return FALSE;
	return TRUE;
}

ostream& operator<<( ostream& output, const DataRecord& input )
{ 
	for ( int i = 0; i < input.m_numFlds; i++ )
		output << input.m_fld[i]->str();
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
 : m_file( fileName, ios::in| ios::nocreate ),
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

