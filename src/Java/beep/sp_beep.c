#include <stdio.h>
#include <windows.h>

int main(int argc, char **argv)
{
	printf("in sp_beep\n");
	Beep(500,1000);
	return(0);
}
