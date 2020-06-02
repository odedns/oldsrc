// Sample code C++ course, lecture notes, item 3.2.2.
// Copyright (c) 1994 Avner Ben.
// last update 9.2.94

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
	DataRecord( int intVal )
	 : m_s( new char[18] )
	 { sprintf( m_s, "%d", intVal ); }
	DataRecord( const DataRecord& rhs )
	 : m_s( newStrDup( rhs.m_s ))
	 { }
};

main()
{
	DataRecord a = 4;
	DataRecord b = 4;
	DataRecord c = a;
	DataRecord d = a;

	return 0;
}