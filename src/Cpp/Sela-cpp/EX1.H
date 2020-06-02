// ex1.h
// "file of records" excercise, stage 1, interface.
// 25.02.92 avner ben coded.
// 06.03.93 avner ben last update.

#ifndef FILEOFRECS_H
#define FILEOFRECS_H

typedef int Boolean;
#define TRUE 1
#define FALSE 0

 // general error routine - print message and exit program
void fatalError( const char* message =	"fatal error" );

struct FileOfRecs
{ // "file of records" implemented over a file of characters.

	 // attributes:
	FILE* m_file;	// using the standard C file system.
	int m_recSize;	// space-pad/truncate records to this size.
	char* m_buf;	// last read valid record or all spaces

	 // state:
	Boolean m_eof;	// no more records to read

	 // instantiation:
	FileOfRecs( char* fileName, int recSize );
	~FileOfRecs();

	 // services:
	Boolean findFirst(); // TRUE if file non-empty
	Boolean findNext();  // TRUE if next record found
};

#endif
