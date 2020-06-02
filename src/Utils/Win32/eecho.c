
#include <stdio.h>

int main(int argc, char **argv)
{

	if(argc < 2) {
		putchar('\n');
	} else {
		printf(argv[1]);
	}

	return(0);
}
