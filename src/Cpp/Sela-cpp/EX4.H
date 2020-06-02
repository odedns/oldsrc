// ex4.h.
// "file of records" excercise, stage 4, school solution.
// 25.02.92 avner ben coded.
// 21.06.93 avner ben last update.

#ifndef FILEOFRECS_H
#define FILEOFRECS_H

#include <string.h> 
#include <fstream.h>
#include <iomanip.h>                                                

typedef int Boolean;
#define TRUE 1
#define FALSE 0

 // general error routine - print message and exit program
void fatalError( const char* message =	"fatal error" );

class DataRecord
{ // fixed-size buffer. string used by file objects.

	private:
		char* m_s;
		int m_size;

	public:
		 // instantiation:
		DataRecord( int length );
		DataRecord( const char* source )
		 : m_s( new char[( m_size = strlen( source )) + 1] )
		 { strcpy( m_s, source ); }
		DataRecord( const DataRecord& that );
		DataRecord& operator=( const DataRecord& that );
		~DataRecord()
		 { delete m_s; }

		 // accessors:
		int length()
		 { return m_size; }
		const char* str() const
		 { return ( const char* )m_s; }
		operator const char* () const
		 { return ( const char* )m_s; }

		 // operations
		Boolean loadFrom( ifstream& input ); // read from file

		 // misc
		Boolean operator==( const DataRecord& that ) const
		 { return !strcmp( m_s, that.m_s ); }
};

class FileOfRecs
{ // "file of records" implemented over a file of characters.

	private:
		 // attributes:
		ifstream m_file;
		DataRecord m_buf;

		 // state:
		Boolean m_eof;	// no more records to read

		 // warning - no duplication!
		FileOfRecs( FileOfRecs &that );
		void operator=( FileOfRecs &that );

	public:
		 // instantiation:
		FileOfRecs( char* fileName, int recSize );
		// ~FileOfRecs(); deststructor not needed

		 // navigation:
		Boolean findFirst();
		Boolean findNext();
		DataRecord operator[]( int recNumber );

		 // accessors
		Boolean atEnd() const // TRUE if no next record
		 { return m_eof; }
		DataRecord current() const // copy of current rec
		 { return m_buf; }
};

inline ostream& operator<<( ostream& output, FileOfRecs& input )
{ return output << input.current(); }

#endif
