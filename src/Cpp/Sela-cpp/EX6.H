// ex6.h.
// "file of records" excercise, stage 6, school solution.
// 25.02.94 avner ben adapted from ex5.h.

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
void fatalError( const char* message =  "fatal error" ); 

class FileOfTokens;             // defined elsewhere

class String
{ // A thin wrapping for char*. Normally used as temporary.
  // Also, generic base for string-type classes.

protected:
	char* m_s;

public:
	String( const char* val)
	 : m_s( strcpy( new char[strlen( val) + 1], val )) 
	 {}
	String( int size ); // for fixed-length field output
	String( const String& rhs)
	 : m_s( strcpy( new char[strlen( rhs.m_s ) + 1], rhs.m_s )) 
	 {}
	~String()
	 { delete m_s; }
	String& operator=( const String& rhs );
	
	 // misc
	operator const char*() const
	 { return ( const char* )m_s; } 
	int length() const
	 { return strlen( m_s ); }
	Boolean operator==( const char* rhs )const
	 { return !strcmp( m_s, rhs ); }
	Boolean operator!=( const char* rhs )const
	 { return strcmp( m_s, rhs ); }
	Boolean operator!() const
	 { return ( *m_s == '\0' ); }       
	 
	 // operations
	String& operator+=( const String& rhs );
	char& operator[]( int offset )
	 { return m_s[offset]; }
	char operator[]( int offset ) const
	 { return m_s[offset]; }
	void clear(); // to spaces.
};

class Field
{ // Abstract definition for data-field, capable of being displayed and entered
  // as string. Normally, a DataRecord component, in array.

private:
	char* m_name;
protected:
	int m_size;
public:
	 // instantiation:
	Field( int size )
	 : m_name( 0 ), m_size( size ) 
	 { }
	Field( const Field& that );
	Field& operator=( const Field& that );
	virtual ~Field()
	 { if ( m_name ) delete m_name; }
	Field( FileOfTokens& specification );
	 // read structure from spec
	 // see documentation (seperate) for syntax diagram
	virtual Field* clone() = 0; // used by copy-ctor of Field-collection
		 
	 // accessors:
	int length() const
	 { return m_size; }
	const char* name() const;
	virtual String str() const = 0;
	virtual Boolean equalTo( const Field* ) const = 0;
	
	 // run-time type identification
	virtual const char* typeId() const = 0; 
	static Field* create( FileOfTokens& specification ); // virtual ctor 
	 // string-field (compatibility mode)
	static Field* createDefault( int len ); 
	static Field* createDefault( const char*  ); 

	 // operations
	virtual Boolean loadFrom( 
	 ifstream& stream, Boolean waitForNewLine 
	 ) =0;
	virtual void clear() = 0; 
};

class DataRecord
{ // Fixed-size buffer. String used by file objects. 

private:
	Field** m_fld;  // array of pointers to field
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
	operator String() const; 
	
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
	Boolean m_eof;  // no more records to read

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
