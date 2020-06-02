// Sample code C++ course, lecture notes, item 3.2.4.
// Copyright (c) 1994 Avner Ben.
// last update 9.2.94

#include <stdio.h>
#include <string.h>

#define newStrDup( s ) strcpy( new char[strlen( s ) + 1 ], s )

class DataRecord {
private:
	char* m_s;  
	static int m_countAll;
	int m_count; 
public:
	DataRecord( char* s )
	 : m_s( newStrDup( s ))
	 { printf( "ctor #%d\n", m_count = ++m_countAll); }
	DataRecord( const DataRecord& rhs )
	 : m_s( newStrDup( rhs.m_s ))
	 { printf( "cctor #%d\n", m_count = ++m_countAll); }
	~DataRecord()
	 { delete m_s; printf( "dtor #%d\n", m_count); }

	char* str()
	 { return m_s; }
};                      

int DataRecord::m_countAll = 0;

class FileOfRecs {
private:
	char* m_buf;
public:
	FileOfRecs( )
	 : m_buf( newStrDup( "hello world" ))
	 { }
	DataRecord current() {
		DataRecord temp = m_buf;
		return temp;
	}
	DataRecord efficientCurrent()
	 { return DataRecord( m_buf ); }
};

main()
{
	FileOfRecs input;
	printf( "%s\n", input.current().str() );
	// printf( "%s\n", input.efficientCurrent().str() );

	return 0;
}