#include <stdio.h>


int main(int argc, char **argv)
{	
	char *s = "Some fucking string to print\n";
	char *name = "\\mataf11\HP LaserJet 4100 PCL 6";
	FILE *fp = NULL;

	fp = fopen(name, "w");
	if(NULL == fp) {
		perror("fopen");
		exit(1);
	}
	fprintf(fp,"Some fucking line to print\n");
	fclose(fp);
}
