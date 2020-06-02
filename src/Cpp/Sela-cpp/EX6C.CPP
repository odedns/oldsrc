// ex6c.cpp.
// "file of records" excercise, stage 6, school solution.
// 25.02.94 avner ben coded.

#include <stdlib.h>
#include <ctype.h>

#include "ex6.h"
#include "ex6a.h" // needs class FileOfTokens
#include "ex6c.h"


Field* Field::create( FileOfTokens& spec ) 
{
	if ( spec.current() == "string" ) {
		spec.getNext();                                                                                               
		return new StrField( spec );
	} else if ( spec.current() == "integer" ) {
		spec.getNext();
		return new IntField( spec );
	} else if ( spec.current() == "filler" ) {
		spec.getNext();
		return new FillerField( spec );
	} else 
		return new StrField( spec );
}		

Field* Field::createDefault( int len )
 { return new StrField( len ); }

Field* Field::createDefault( const char* s )
 { return new StrField( s ); }

StrField::StrField( FileOfTokens& spec )
 : Field( spec ), String( m_size )
{ }

void StrField::clear( )
{
	for ( char* s = m_s; *s; s++ )
		*s = ' ';
}

Boolean StrField::loadFrom( ifstream& input, Boolean waitNewLine )
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


IntField::IntField( FileOfTokens& spec )
 : Field( spec ), m_i( 0 )
{ }

String IntField::str() const
{ 
	String result( m_size );
	int i = m_size - 1;
	for ( int temp = m_i; temp; temp /= 10 )
		result[i--] = ( char )( temp % 10 ) + '0';
	if ( m_i < 0 ) result[i] = '-';
	return result;
}

Boolean IntField::loadFrom( ifstream& input, Boolean waitNewLine )
{
	int len = 0;
	char sign = '\0';
	char c;     
	for( m_i = 0; ( c=input.get() ) != '\n' && !input.eof(); )
		if( ++len <= m_size ) {
			if ( isdigit( c )) 
				m_i = m_i * 10 + ( c - '0' );
			else if ( len == 1 || len == m_size && !sign )
				if ( c == '-' || c == '+' ) sign = c;
				else break;
			else break; 				
			if ( !waitNewLine && len == m_size ) break;
		}
	if ( sign == '-' ) m_i *= -1;
	return( len > 0 );
}

FillerField::FillerField( FileOfTokens& spec )
 : Field( spec )
{ }

Boolean FillerField::loadFrom( ifstream& input, Boolean waitNewLine )
{
	for ( int len = 0; input.get() != '\n' && !input.eof(); )
		if ( ++len == m_size && !waitNewLine ) break;
	return ( len > 0 );
}
