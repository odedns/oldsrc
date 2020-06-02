// ex6a.h.
// "file of records" excercise, stage 6, part 2, school solution.
// 05.01.94 avner ben adapted from ex5a.h.

#ifndef FILEOFRECS_A_H
#define FILEOFRECS_A_H

#include "ex6.h"

class FileOfTokens
{ // stream of white-space delimited tokens, read sequentially.
  // only current token is stored.

	private:
		ifstream m_file;
		String m_token;
		char ch;

		 // class constants
		static const int inc; // dynamically sized string

		 // internal routines
		static String& fileName( const char* name, const char* ext );

	public:
		 // instantiation:
		FileOfTokens( const char* name, const char* extension = 0 );
		 // extension, if given ,overrides that in given name

		 // navigation:
		const String& getNext();

		 // accessors:
		const String& current() const
		 { return ( const String& )m_token; }
		Boolean atEnd() const
		 { return ((( ifstream& )m_file).eof() && !m_token ); }
};

#endif
