// Sample code C++ course, lecture notes, item 2.3.2 / 2
// Copyright (c) 1994 Avner Ben.
// last update 27.2.94

#include <stdio.h>
#include <stdlib.h>

typedef int Boolean;

struct Scenario {
	static int useCount;
	Scenario() { 
		if ( ++useCount > 1 ) {
			printf( "too many scenarios!\a\n" );
			exit( 99 );
		}
	}
};

int Scenario::useCount = 0;

main()
{
	Scenario theRealThing;
	Scenario fake;
	
	return 0;
}

