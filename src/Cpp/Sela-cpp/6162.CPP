// Sample code C++ course, lecture notes, item 6.1.6 / 2
// Copyright (c) 1994 Avner Ben.
// last update 22.2.94

#include <stdio.h>

#define ARRAY( T ) \
class T##_ARRAY { \
	T* m_data; \
	int nElem; \
public: \
	T##_ARRAY( int num = 1 ) \
	 : m_data( new T[ nElem = num ])  \
	 { } \
	~T##_ARRAY() \
	 { delete[] m_data; } \
	T& operator[]( int offset ) { \
	 return m_data[ offset ]; } \
}

typedef ARRAY( int ) intArray;

main()
{
	intArray vec1( 100 );

	return 0;
}
