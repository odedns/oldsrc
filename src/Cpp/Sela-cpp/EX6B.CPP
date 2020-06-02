// ex6b.cpp.
// "file of records" excercise, stage 6, part 3, school solution.
// 25.02.94 avner ben adapted from ex5b.cpp

#include "ex6b.h"

void FileOfRecsView:: list( ostream& output )
{
	for ( int i = 0; i < m_numFlds; i++ ) {
		output<< m_fld[i]->str();
		if ( i < m_numFlds - 1 ) 
			output << m_sepr;
	}
	output << endl;
}
				      
void FileOfRecsView:: listTitle( ostream& output )
{                                                      
	if (m_title) 
		output << m_title << endl;
	for ( int i = 0; i < m_numFlds; i++ ) {
		output << m_fld[i]->name(); // may not overflow
		for ( int j = m_fld[i]->length() - strlen( m_fld[i]->name() ); j; j-- )
			output << ' ';  
		if ( i < m_numFlds - 1 ) 
			output << m_sepr;
	}
	output << endl;
	for ( i = 0; i < m_numFlds; i++ ) {
		for ( int j = m_fld[i]->length(); j; j-- )
			output << '-';
		if ( i < m_numFlds - 1 ) 
			output << m_sepr;
	}
	output << endl;
}
