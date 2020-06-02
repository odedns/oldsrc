

/*-------------------------------------------------------------------
	
 get the symbol table from an HP-UX executable  and print them in an
 nm style output format.
 This will only work for programs created in the HP-UX environment.
 
 
 By 	: 	Oded Nissan
 Date 	:	28/03/1996

----------------------------------------------------------------------*/
#include <stdio.h>
#include <string.h>
#include <a.out.h>



char *get_str_tbl(FILE *fp, struct header *pHead);
int get_sym_tbl(char *pFileName);
char *get_sym_type(unsigned int sym_type);
char *get_sym_scope(unsigned int sym_scope);
void help();

int main(int argc , char **argv)
{
char *p;
int status = 0;

	if(argc < 2) {
		help();
		return(2);
	}
	while(--argc) {
		++argv;
		printf("Symbol Table For File : %s\n",*argv);
		status = get_sym_tbl(*argv);
	}

	return(status);



}


int get_sym_tbl(char *pFileName)
{
FILE *fp;
struct header fHeader;
int stat = 0, numSyms;
char *pBuff;
struct symbol_dictionary_record Symbol;

	if(NULL == (fp = fopen(pFileName,"r"))) {
		fprintf(stderr,"Can't open file :%s\n",pFileName);
		exit(1);
	}

	stat = fread(&fHeader,sizeof(struct header),1,fp);
	printf("Total Symbols : %d\n",fHeader.symbol_total);
	pBuff = get_str_tbl(fp,&fHeader);
	if(pBuff == NULL ) {
		return(1);
	}

	printf("Symbol Table : \n");
	printf("%-30s|%-15s|%-15s|%-15s\n\n",
		"Name","Value","Scope","Type");

	fseek(fp,fHeader.symbol_location,SEEK_SET);
	stat = fread(&Symbol,sizeof(struct symbol_dictionary_record),1,fp);
	
        while(--fHeader.symbol_total) {
		printf("%-30s|%15d|%-15s|%-15s\n",
                       &pBuff[Symbol.name.n_strx],
			Symbol.symbol_value,
			get_sym_scope(Symbol.symbol_scope),
			get_sym_type(Symbol.symbol_type));

		stat = fread(&Symbol,
                       sizeof(struct symbol_dictionary_record),1,fp);
        }
	free(pBuff);
	return(0);
	
}

char *get_str_tbl(FILE *fp , struct header *pHead)
{
char *pBuff;
int stat;


	pBuff = (char *) malloc(pHead->symbol_strings_size);

#ifdef DEBUG
	printf("symbol strings = %d\n",pHead->symbol_strings_location);
	printf("symbol strings size = %d\n",pHead->symbol_strings_size);
#endif 

	fseek(fp,pHead->symbol_strings_location,SEEK_SET);
	stat = fread(pBuff,pHead->symbol_strings_size,1,fp);

	return(stat == 1 ? pBuff : NULL);
}


char *get_sym_type(unsigned int sym_type)
{
static char Str[120];

	switch(sym_type) { 
		case ST_NULL: 
			 strcpy(Str,"null");
			break;
		case ST_ABSOLUTE : 
			strcpy(Str,"abs");
			break;
		case ST_DATA :
			strcpy(Str,"data");
			break;
		case ST_CODE :
			strcpy(Str,"code");
			break;
		case ST_PRI_PROG :
			strcpy(Str,"entry");
			break;
		case ST_MILLICODE :
			strcpy(Str,"milli");
			break;
		case ST_STUB :
			strcpy(Str,"stub");
			break;

		default :
			strcpy(Str,"unknown");
			break;

	}
	return(Str);
}
			
				
char *get_sym_scope(unsigned int sym_scope)
{
static char Str[120];

	switch(sym_scope) {
		case SS_UNSAT :
			strcpy(Str,"undef");
			break;
		case SS_EXTERNAL :
			strcpy(Str,"uext");
			break;
		case SS_LOCAL :
			strcpy(Str,"static");
			break;
		case SS_UNIVERSAL :
			strcpy(Str,"extern");
			break;
		default :
			strcpy(Str,"unknown");
			break;
	}
	return(Str);
}

void help()
{

	fprintf(stdout,"%s\n%s\n",
		"getsym : print symbol table of a HP-UX binary",
		"usage getsym [files...]");
}
