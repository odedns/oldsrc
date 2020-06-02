// Sample code C++ course, lecture notes, item 4.1.7.5.
// Copyright (c) 1994 Avner Ben.
// last update 10.2.94

#include <stdio.h>
#include <string.h>

#define newStrDup( s ) strcpy( new char[strlen( s ) + 1 ], s )

class DataRecord {
private:
	char* m_s;
public:
	DataRecord( char* s )
	 : m_s( newStrDup( s ))
	 { }
	DataRecord( const DataRecord& rhs )
	 : m_s( newStrDup( rhs.m_s ))
	 { }
	~DataRecord()
	 { delete m_s; }

	char& operator[]( int offset )
	 { return m_s[offset]; }
};

main()
{
	DataRecord buf( "hello wo ld!" );
	buf[8] = 'r';
	const DataRecord constBuf = buf;
	constBuf[8] = 'r';

	return 0;
}