#include <stdio.h>


#define _PSPACE_ " "
#define _PNAME_ "program.exe: Program description here."
#define _PDATE_ "dd-mm-yyyy"
#define _PVERSION_  "1.0"

char *cpright = _PNAME_ "\n" \
		"by Oded Nissan" _PSPACE_ _PDATE_ \
		_PSPACE_ "Version:" _PSPACE_ _PVERSION_;

char Msg[] = " *** ecd - an enhanced cd utility for MsDos version 1.0 ***\n \
	  By Oded Nissan  15-03-1997";




main()
{

	puts(cpright);
}