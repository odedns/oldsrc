// ex5.h.
// "file of records" excercise, stage 5, school solution.
// 25.02.92 avner ben coded.
// 06.08.93 avner ben last update.

#ifndef FILEOFRECS_H
#define FILEOFRECS_H

#include <stdlib.h>
#include <string.h>
#include <fstream.h>
#include <iomanip.h>

typedef int Boolean;
#define TRUE 1
#define FALSE 0

 // general error routine - print message and exit program
void fatalError( const char* message =	"fatal error" ); 

class FileOfTokens;		// forward declaration

class Field
{ // fixed size buffer-section. used to divide the data-record to string-oriented fields.

	private:
		char* m_s,
		 * m_name;
		int m_size;

	public:
		 // instantiation:
		Field( int inSize )
		 : m_s( new char[( m_size = inSize ) + 1] ), m_name( 0 )
		 { *( m_s + m_size ) = '\0'; clear(); }
		Field( const char* source )
		 : m_s( new char[( m_size = strlen( source )) + 1] ), m_name( 0 )
		 { strcpy( m_s, source ); }
		Field( const Field& that );
		Field& operator= ( const Field& that );
		~Field()
		 { delete m_s; if ( m_name ) delete m_name; }
		Field( FileOfTokens& specification );
		 // read structure from spec
		 // see documentation (seperate) for syntax diagram

		 // accessors:
		int length() const
		 { return m_size; }
		const char* str() const
		 { return ( const char* )m_s; }
		operator const char*() const
		 { return ( const char* )m_s; }
		const char* name() const;

		 // operations related to token-parsing
		Boolean operator== ( const char* s ) const
		 { return !strcmp( m_s, s ); }
		Boolean operator!= ( const char* s ) const
		 { return strcmp( m_s, s ); }
		Boolean operator! () const;

		 // operations
		Boolean loadFrom( ifstream& stream, Boolean waitForNewLine );
		void clear(); // space fill
		Field& operator+=( const char* );
};

class DataRecord
{ // fixed-size buffer. string used by file objects.
	private:
		Field** m_fld;	// array of pointers to field
		int m_numFlds;

	public:
		 // instantiation:
		DataRecord( int length ); // 1 field record
		DataRecord( const char* source ); // 1 field record
		DataRecord( const DataRecord& that );
		DataRecord( FileOfTokens& specification );
		 // read structure from spec
		 // see documentation (seperate) for syntax diagram
		DataRecord& operator=( const DataRecord& that );
		~DataRecord();

		 // accessors:
		int length() const;
		operator Field() const; // for char* conversion

		 // operations
		Boolean loadFrom( ifstream& stream ); // read from file.

		 // misc
		Boolean operator==( const DataRecord& that ) const;

	friend ostream& operator<<( ostream& output, const DataRecord& input );
    friend class FileOfRecsView;
};

class FileOfRecs
{ // "file of records" implemented over a file of characters.

	private:
		 // attributes:
		ifstream m_file;
		DataRecord m_buf;
		char* m_name;

		 // state:
		Boolean m_eof;	// no more records to read

		 // warning - duplication prohibited!
		FileOfRecs( FileOfRecs &that );
		void operator=( FileOfRecs &that );

	public:
		 // instantiation:
		FileOfRecs( FileOfTokens& specification, const char* fileName );
		 // read structure from spec in seperate file,
		 // see documentation ( seperate ) for syntax diagram
		FileOfRecs( const char* fileName, int recSize );
		~FileOfRecs()
		 { if ( m_name ) delete m_name; }

		 // accessors
		Boolean atEnd() const // TRUE if no next record
		 { return m_eof; }
		DataRecord current() const // copy of current rec
		 { return m_buf; }
		const char* name() const
		 { if ( m_name ) return ( const char* )m_name; else return ""; }

		 // navigation:
		Boolean findFirst();
		Boolean findNext();
		DataRecord operator[]( int recNumber );

	 // not member! (but invades data hiding)
	friend ostream& operator<<( ostream& output, FileOfRecs& input );
    friend class FileOfRecsView;
};
                                       
inline ostream& operator<<( ostream& output, FileOfRecs& input )
{ return output << input.m_buf; }

#endif
