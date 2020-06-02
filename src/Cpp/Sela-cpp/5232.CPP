// Sample code C++ course, lecture notes, item 5.3.2.2.
// Copyright (c) 1994 Avner Ben.
// last update 17.2.94

#include <iostream.h>

class part {
	int m_i;
public:
	part()
	 : m_i( 1 )
	 { cout << "default ctor...\n"; }
	part( const part& rhs )
	 : m_i( rhs.m_i )
	 { cout << "copy ctor...\n"; }
	part& operator=( const part& rhs ) {
		m_i = rhs.m_i;
		cout << "assignmnet op...\n";
		return *this;
	}
};
class assembly {
	part m_part;
	int m_j;
public:
	assembly()
	 : m_j( 1 ) { }
	assembly( const assembly& rhs )
	 : m_j ( rhs.m_j ) { }
	assembly& operator=( const assembly& rhs ) {
		m_j = rhs.m_j;
		return *this;
	}
};

main()
{
	assembly a;
	assembly b = a;
	a = b;

	cout << '\n';
	return 0;
}
