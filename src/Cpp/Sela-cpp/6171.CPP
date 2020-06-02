// Sample code C++ course, lecture notes, item 6.1.6 / 2
// Copyright (c) 1994 Avner Ben.
// last update 22.2.94

#include <stdio.h>

template< class T >
class Array {
	T* m_data;
	int nElem;
public:
	Array( int num = 1 )
	 : m_data( new T[ nElem = num ])
	 { }
	~Array() 
	 { delete[] m_data; } 
	T& operator[]( int offset ) { 
	 return m_data[ offset ]; } 
}
    
main()
{
	Array< int > vec1( 100 );

	return 0;
}
