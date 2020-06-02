// ex5b.h.
// "file of records" excercise, stage 5, part 3, school solution.
// 09.07.93 avner ben separated from ex5.h
// 08.06.93 last update by avner ben 

#ifndef FILEOFRECS_B_H
#define FILEOFRECS_B_H

#include "ex5.h"

class FileOfRecsView 
{ // temporary object, formatting the current data record for output

	private:
		const char* m_title; // references FileOfRecs
		const Field** m_fld; // references DataRecord
		const int& m_numFlds; // references DataRecord
		
		 // attribute data
		char* m_sepr;
		
	public:		
		 // instantiation
		FileOfRecsView( FileOfRecs& theFile, const char* sepr = " " )
		 : m_title( ( const char* )theFile.m_name ),
		 m_fld ( ( const Field** )theFile.m_buf.m_fld ),
		 m_numFlds ( ( const int )theFile.m_buf.m_numFlds ),
		 m_sepr( new char[strlen( sepr ) + 1] )
		 { strcpy( m_sepr, sepr ); }
		~FileOfRecsView()
		 { delete m_sepr; }
		                 
		 // operations		                 
		void list( ostream& media ); // current record and newline
		void listTitle( ostream& media ); // title line, then dash line
};

inline ostream& operator<<( ostream& output, FileOfRecsView& view )
{ view.list( output ); return output; }

#endif
