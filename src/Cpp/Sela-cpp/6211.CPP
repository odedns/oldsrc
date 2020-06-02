// Sample code C++ course, lecture notes, item 6.2.1
// Copyright (c) 1994 Avner Ben.
// last update 23.2.94

#include <ctype.h>
#include <stdlib.h>
#include <fstream.h>

class point {
	int m_y, m_x;
public:
	point( ifstream& db );
	void store( ofstream& db );
};
class stroke {
	char m_pix;
	int m_len;
	enum direction { nodir, right, left, up, down } m_dir;
	point m_location;
	stroke* m_next;
public:
	stroke( ifstream& db );
	void store( ofstream& db );
friend class shape;
};
class string {
	char * m_s;
	point m_location;
	string* m_next;
public:
	string( ifstream& db );
	void store( ofstream& db );
friend class shape;
};
class shape {
	stroke* m_fStroke;
	string* m_fString;
public:
	shape( ifstream& db );
	void store( ofstream& db );
};

main()
{
	ifstream inDB( "6211.dat" );
	shape aShape( inDB );
	inDB.close();

	ofstream outDB( "6212.dat" );
	aShape.store( outDB );
	return 0;
}

static void fatalError( const char* msg )
{
	cout << msg << endl;
	exit( 1 );
}

point::point( ifstream& db )
{
	if ( !isdigit( db.peek() )) fatalError( "number expected" );
	db >> m_y;
	if ( db.get() != ':' ) fatalError( "\":\" expected" );
	if ( !isdigit( db.peek() )) fatalError( "number expected" );
	db >> m_x;
}
void point::store( ofstream& db )
{ db << m_y << ':' << m_x; }
stroke::stroke( ifstream& db )
 : m_location( db ), m_next( 0 )
{
	if ( db.get() != '\'' ) fatalError( "\"'\" expected" );
	db >> m_pix;
	if ( db.get() != '\'' ) fatalError( "\"'\" expected" );
	if ( !isdigit( db.peek() )) fatalError( "number expected" );
	db >> m_len;
	if ( m_len > 1 )
		switch ( db.get() ) {
			case '>' :
				m_dir = right; break;
			case '^' :
				m_dir = up; break;
			case '<' :
				m_dir = left; break;
			case 'V' :
				m_dir = down; break;
			default:
				fatalError( "<direction> expected" );
		}
	else m_dir = nodir;
}
void stroke::store( ofstream& db )
{
	m_location.store( db );
	db << '\'' << m_pix << '\'' << m_len;
	switch ( m_dir ) {
		case right :
			db << '>'; break;
		case up :
			db << '^'; break;
		case left :
			db << '<'; break;
		case down :
			db << 'V'; break;
	}
}
string::string( ifstream& db )
 : m_location( db ), m_next( 0 )
{
	if ( db.get() != '"' ) fatalError( "\" \" \" expected" );
	m_s = new char[32];
	db.get( m_s, 32, '"' );
	if ( db.get() != '"' ) fatalError( "\" \" \" expected" );
}
void string::store( ofstream& db )
{
	m_location.store( db );
	db << '"' << m_s << '"';
}
shape::shape( ifstream& db )
{
	while ( isspace( db.peek() )) db.get();
	for ( const char* s = "strokes"; *s; s++ )
		if ( db.get() != *s )
			fatalError( "\"strokes\" expected" );
	while ( isspace( db.peek() )) db.get();
	if ( db.peek() == '{' ) {
		do {
			db.get();
		} while ( isspace( db.peek() ));
		for ( stroke* aStroke = 0; db.peek() != '}';  ) {
			if ( !aStroke )
				aStroke = m_fStroke = new stroke( db );
			else aStroke = aStroke->m_next = new stroke( db );
			while ( isspace( db.peek() )) db.get();
		}
		db.get();
	} else m_fStroke = new stroke( db );

	while ( isspace( db.peek() )) db.get();
	for ( s = "strings"; *s; s++ )
		if ( db.get() != *s )
			fatalError( "\"strings\" expected" );
	while ( isspace( db.peek() )) db.get();
	if ( db.peek() == '{' ) {
		do {
			db.get();
		} while ( isspace( db.peek() ));
		for ( string* aString = 0; db.peek() != '}';  ) {
			if ( !aString )
				aString = m_fString = new string( db );
			else aString = aString->m_next = new string( db );
			while ( isspace( db.peek() )) db.get();
		}
	} else m_fString = new string( db );
}
void shape::store( ofstream& db )
{
	db << "strokes ";
	if ( !m_fStroke->m_next )
		m_fStroke->store( db );
	else {
		db << "{\n";
		for ( stroke* aStroke = m_fStroke; aStroke; aStroke = aStroke->m_next ) {
			db << '\t'; aStroke->store( db ); db << endl;
		}
		db << "\n}";
	}
	db << endl;
	db << "strings ";
	if ( !m_fString->m_next )
		m_fString->store( db );
	else {
		db << "{\n";
		for ( string* aString = m_fString; aString; aString = aString->m_next ) {
			db << '\t'; aString->store( db ); db << endl;
		}
		db << "\n}";
	}
	db << endl;
}
