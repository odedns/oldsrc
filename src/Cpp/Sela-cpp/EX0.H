// ex0.h
// "file of records" excercise, stage 0, interface.
// purpose: modular C program used as raw material for C++ processing.
// 25.02.92 avner ben coded.
// 06.03.93 avner ben last update.

#ifndef FILEOFRECS_H
#define FILEOFRECS_H

typedef int Boolean;
#define TRUE 1
#define FALSE 0

struct FileOfRecs
{ // "file of records" implemented over a file of characters.

	FILE* m_file;	// using the standard C file system.
	int m_recSize;	// space-pad/truncate rcords to this size.
	char* m_buf;	// last read valid ecord or all spaces
};

// procedures on this data structure (using pointer):

void openFile( FileOfRecs* input, char* fileName, int recSize );
void closeFile( FileOfRecs* input );
Boolean findNext( FileOfRecs* input );
 // get next record into buffer. FALSE if no record waiting (last
 // record left untouched in that case).

 // general error routine - print message and exit program
void fatalError( const char* message =	"fatal error" );

#endif
