// ex2.h.
// "file of records" excercise, stage 2, school solution.
// 25.02.92 avner ben coded.
// 08.06.93 avner ben last update.

#ifndef FILEOFRECS_H
#define FILEOFRECS_H

#include <string.h>

typedef int Boolean;
#define TRUE 1
#define FALSE 0

 // general error routine - print message and exit program
void fatalError( const char* message =	"fatal error" );

class DataRecord
{ // currier object for communicating between FileOfRecs and clients.
   // presently just a string.

	private:
		char* m_s;

	public:
		 // instantiation:
		DataRecord( const char* source )
		 : m_s( new char[strlen( source ) + 1] )
		 { strcpy( m_s, source ); }
		DataRecord( const DataRecord& that );
		~DataRecord()
		 { delete m_s; }

		 // accessors:
		const char* str() const
		 { return ( const char* )m_s; }
};

class FileOfRecs
{ // "file of records" implemented over a file of characters.

	private:
		 // attributes:
		FILE* m_file;	// using the standard C file system.
		int m_recSize;	// space-pad/truncate records to this size.
		char* m_buf;	// last read valid record or all spaces

		 // state:
		Boolean m_eof;	// no more records to read

		 // no duplication
		FileOfRecs( FileOfRecs& );

	public:
		 // instantiation:
		FileOfRecs( char* fileName, int recSize );
		~FileOfRecs();

		 // navigation:
		Boolean findFirst();
		Boolean findNext();

		 // accessors
		Boolean atEnd() const // TRUE if no next record
		 { return m_eof; }
		DataRecord current() const // copy of current rec
		 { return DataRecord( m_buf ); }
};

#endif
