#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>

#include "dstructs.h"
#include "inifile.h"


static char *get_section(char *s);
static int get_token_val(char *line , char *pItem, char *pvalue);

inifile_t *load_inifile(char *filename)
{
int rv = 0;
FILE *fp;
char *p , line[INI_MAXLINE+1];
char temp1[INI_MAXLINE+1], temp2[INI_MAXLINE+1], temp_token[INI_MAXLINE+1];
inifile_t *pIni;
char *token, *value, *section = NULL;
int has_section = 0;


	/* if open fails return NULL in inifile struct */

	if(NULL == (fp = fopen(filename,"r"))) {
		perror("open");
		return(NULL);
	}

	if(NULL == (pIni = (inifile_t *) malloc(sizeof(inifile_t)))) {
		fclose(fp);
		return(NULL);
	}

	pIni->filename = strdup(filename);
	pIni->updated = 0;
	pIni->hash_tbl = hash_tbl_create(25);


	while(NULL != (p = fgets(line, INI_MAXLINE,fp))) {

		if(*p == '\n' || *p == ';') 
			continue;

		if(*p == '[') {
			section = get_section(p);
			has_section = 1;
		} else {
			/* token */
			if(!has_section) {
				return(NULL);	
			}
			get_token_val(p , temp1, temp2);
			sprintf(temp_token, "%s.%s",section,temp1);
			token = strdup(temp_token);
			value = strdup(temp2);
			printf("adding key = %s\tvalue = %s\n",
					token,value);
			rv = hash_tbl_insert(pIni->hash_tbl,token, value);
		}
	}


	fclose(fp);

	return(pIni);
}

			
			
		
		
	
static char *get_section(char *s)
{
static char sec[INI_MAXLINE+1];
int i =0;


	while(*s != ']' && *s) {
		if(*s != '[') 
			sec[i++] = *s++;
		else 
		   	++s;
	}
	sec[i] ='\0';

	return(&sec[0]);
}
			 
/* parse the string into a token and a value */
static int get_token_val(char *s, char *ptoken, char *pvalue)
{
char token[INI_MAXLINE+1];
int i =0;


	while(*s != '=' && *s) {
		if(!isspace(*s))
			token[i++] = *s++;
		else 
		   	++s;
	}
	token[i] ='\0';
	
	i = 0;
	strcpy(ptoken,token);
	if(*s == '=')
		++s;

	while(*s) {
		if(!isspace(*s))
			token[i++] = *s++;
		else 
		   	++s;
	}
	token[i] ='\0';
	strcpy(pvalue,token);
	return(0);
}


void DumpTbl(HASHTBL *h);
/* dump the ini file data structure */
void dump_inifile(inifile_t *p)
{
	DumpTbl(p->hash_tbl);
}

	
/* unload the ini file data structure from memory */
void unload_inifile(inifile_t *pIni)
{
	hash_tbl_delete(pIni->hash_tbl, free);
}
		
			
			
int write_inifile(inifile_t *pIni)
{
FILE *fp;
char line[INI_MAXLINE+1];

	if(NULL == (fp = fopen(pIni->filename,"w"))) {
		return(-1);
	}
	return(0);
}


int inifile_get_token(inifile_t *pIni,char *section, char *token, char *def,
			char *value)
{
	char key[INI_MAXLINE+1];
	char *tval;
	int rv;

	sprintf(key,"%s.%s",section,token);
	rv = hash_tbl_get(pIni->hash_tbl, key, &tval);

	if(rv) {
		strcpy(value, def);
	} else {
		strcpy(value,tval);
	}
	return(rv);
}

		

