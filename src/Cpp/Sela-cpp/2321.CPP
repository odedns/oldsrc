// Sample code C++ course, lecture notes, item 2.3.2 / 1
// Copyright (c) 1994 Avner Ben.
// last update 27.2.94

#include <stdio.h>

typedef int Boolean;

struct Date {
	long value;
	static int ddBeg, mmBeg, yyBeg;
	
	Date( long inValue )
	 : value( inValue )
	 { }
	void f()
	 { value++; ddBeg++; }
	static void setDdBeg( int newVal )
	 { ddBeg = newVal; }
	
};

int Date::ddBeg = 1, Date::mmBeg = 1, Date::yyBeg = 1980;

main()
{
	Date::ddBeg++;
	Date::setDdBeg( 4 );
	
	Date dy( 1000 );
	dy.ddBeg++;
	dy.setDdBeg( 3 );
	dy.f();
	
	return 0;
}

