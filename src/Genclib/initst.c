
#include <stdio.h>
#include "dstructs.h"
#include "inifile.h"

main()
{

char *file = "ini.ini";
int stat;
inifile_t *p;
char val[90];


	p  = load_inifile(file);
	if(p == NULL) {
		printf("cannot load ini file\n");
		exit(1);
	}
	/* dump_inifile(p); */
	if(!inifile_get_token(p,"system","disk","NULL",val)) {
		printf("val = %s\n",val);
	} else {
		puts("not found");
	}
	dump_inifile(p);
/*	write_inifile(p);
	unload_inifile(p); */
}
	
