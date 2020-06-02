// ex5a.h.
// "file of records" excercise, stage 5, part 2, school solution.
// 09.07.93 avner ben separated from ex5.h.
// 06.08.93 last update by avner ben

#ifndef FILEOFRECS_A_H
#define FILEOFRECS_A_H

#include "ex5.h"

class FileOfTokens
{ // stream of white-space delimited tokens, read sequentially.
  // only current token is stored.

	private:
		ifstream m_file;
		Field m_token;
		char ch;

		 // class constants
		static const int inc; // dynamically sized string

		 // internal routines
		static Field& fileName( const char* name, const char* ext );

	public:
		 // instantiation:
		FileOfTokens( const char* name, const char* extension = 0 );
		 // extension, if given ,overrides that in given name

		 // navigation:
		const Field& getNext();

		 // accessors:
		const Field& current() const
		 { return ( const Field& )m_token; }
		Boolean atEnd() const
		 { return ((( ifstream& )m_file).eof() && !m_token ); }
};

#endif
