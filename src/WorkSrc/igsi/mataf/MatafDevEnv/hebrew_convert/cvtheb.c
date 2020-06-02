#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAXLEN (999)
#define BTARRAY (10)

typedef struct {
		int from;
		int len;
} btfield_t;

char * Heb_dos2win(char * readStr);
char *getSubstr(char *s, int index, int len);
void putSubstr(char *s, char *subStr, int index, int len);
char *cvtLine(char *line, int from, int len);
int cvtFile(char *fname, btfield_t *btarray[]);


/**
 * get a substring a a specific offset
 * and legth from a string.
 */
char *getSubstr(char *s, int index, int len)
{
	char *sub = (char *) malloc(len+1);
	int i=0;
	char *p = sub;
	char *q = s + index;
	while(i < len) {
		*p++ = *q++;
		++i;
	}
	*p = '\0';
	return(sub);
}


/**
 * put a string into another string
 * at a specific index and length.
 */
void putSubstr(char *s, char *subStr, int index, int len)
{
	char *p = s + index;
	memcpy(p,subStr,len-1);

}


/**
 * Convert hebrew strings in the specific file.
 * fname - the name of the file to process.
 * btarray - an array of btfields, that contain 
 * an offset and a length into the record. The string
 * in that range should be translated to hebrew.
 */
int cvtFile(char *fname, btfield_t *btarray[])
{
	FILE *fp = NULL;
	char line[MAXLEN+1];
	char *p;
	int cnt=0;
	int from;
	int len;
	int i;
	/*
	for(i=0; btarray[i] != NULL; ++i) {
		printf("btarray[%d]= %d - %d\n",i,btarray[i]->from, btarray[i]->len);
	}
	*/
	
	fprintf(stderr, "processing file: %s\n", fname);
	if(NULL == (fp = fopen(fname,"r"))) {
		fprintf(stderr,"cannot open file: %s\n",fname);
		exit(2);
	}


	/**
	 * loop over all lines.
	 * foreach line loop over all btfield entry and
	 * replace the hebrew string.
	 * write the new string to stdout.
	 */
	while(NULL != (p = fgets(line,MAXLEN,fp))) {
		for(i=0; btarray[i] != NULL; ++i) {
			from = btarray[i]->from;
			len = btarray[i]->len;
			p = cvtLine(p, from,len);
		}
		printf("%s",p);
		++cnt;
	}
	fclose(fp);
	return(0);
}


char *cvtLine(char *line, int from, int len)
{
	char *p = getSubstr(line, from,len);
	p = Heb_dos2win(p);
	putSubstr(line, p, from,len);
	free(p);
	return(line);
}
/**
 * main conversion program.
 */
int main(int argc, char **argv)
{

	btfield_t *pbtarray[BTARRAY];
	char *fname;
	if(argc < 2) {
		fprintf(stderr,"usage cvtheb <file> [from-to]...\n");
		exit(1);
	}
	fname = argv[1];
	int i=2;
	char *pfrom;
	char *plen;
	int cnt=0;
	while(i < argc) {
		pfrom = strtok(argv[i],"-");
		plen = strtok(NULL,"-");
		btfield_t *pbt = (btfield_t *) malloc(sizeof(btfield_t));
		pbt->from = atoi(pfrom);
		pbt->len = atoi(plen);
		pbtarray[cnt++] = pbt;
		++i;
	}
	pbtarray[cnt]=NULL;

	cvtFile(fname, pbtarray);
	return(0);

}
