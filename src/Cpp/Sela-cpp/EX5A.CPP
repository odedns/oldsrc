// ex5a.cpp.
// "file of records" excercise, stage 5, part 2, school solution.
// 03.08.93 avner ben separated from ex5.cpp.
// 30.10.93 avner ben last change.

#include <ctype.h>

#include "ex5a.h"

const int FileOfTokens:: inc = 10; // memory for class data

Field& FileOfTokens:: fileName( const char* name, const char* ext )
{
	static Field result(1); result = name;
	if ( ext ) {
		char* periodPos = strchr( result, '.' ); // sneaking inside
		if ( periodPos )
			*periodPos = '\0'; 
		result += ".";
		result += ext;
	}
	return result;
}

FileOfTokens:: FileOfTokens( const char* name, const char* ext )
 : m_token( 1 ), m_file( fileName( name, ext ), ios::in | ios::nocreate )
{
	if ( m_file.bad() )
		fatalError( "definition file not found" );
	ch = m_file.get();
	getNext(); 
} 

const Field& FileOfTokens:: getNext()
{

	int len = inc;
	char* s = new char[len + 1];
	for ( ; isspace( ch ); ch = m_file.get() ) ; // eat ws
	for ( char*t = s, * limit = s + inc; !isspace( ch ); ch = m_file.get() ) {
		if ( m_file.eof() ) break;
		if ( t == limit ) { // overflow
			len += inc;
			char* newS = new char[len + 1];
			strcpy( newS, s );
			delete s; s = newS;
			limit = s + len; t = s + len - inc; 
		}
		*( t++ ) = ch;
	}
	*t = '\0';
	m_token = s; delete s;
	return m_token;
}
