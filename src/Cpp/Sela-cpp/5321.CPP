// Sample code C++ course, lecture notes, item 5.3.2.1
// Copyright (c) 1994 Avner Ben.
// last update 17.2.94

#include <string.h>
#include <iostream.h>

#define newStrDup( s ) strcpy( new char[strlen( s ) + 1 ], s )

class String {
	class InString {
		char* m_s;
		int m_used;
	public:
		InString( char * s )
		 : m_s ( newStrDup( s )), m_used( 1 )
		 { }
		~InString()
		 { delete m_s; }
	friend class String;
	} *m_data;
public:
	String( char* s )
	 : m_data( new InString( s ))
	 { cout << "create " << s << " at: " << (void*)s << '\n'; }
	String( const String& rhs )
	 : m_data( rhs.m_data ) {
		m_data->m_used++;
		cout << "copy " << m_data->m_s << " at: " << (void*)m_data << '\n';
	 }
	~String() {
		if ( !--m_data->m_used ) {
			cout << "delete " << m_data->m_s << " at: " << (void*)m_data << '\n';
			delete m_data;
		} else cout << "no delete " << m_data->m_s << " at: " << (void*)m_data << '\n';
	}
	String& operator=( const String& rhs ) {
		if ( rhs.m_data == m_data ) return *this;
		if ( !--m_data->m_used )
			delete m_data;
		m_data = rhs.m_data;
		m_data->m_used++;
		cout << "assign " << m_data->m_s << " at: " << (void*)m_data << '\n';
		return * this;
	}
};

main()
{
	cout << '\n';

	String a( "hello" );
	String b = a;
	return 0;
}
