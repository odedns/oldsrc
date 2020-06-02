
#ifndef _INIFILE_H
#define _INIFILE_H

#define INI_MAXLINE   (80)


typedef struct {
	char *filename;
	int updated;
	HASHTBL *hash_tbl;
	} inifile_t;

/* function prototypes */
inifile_t *load_inifile(char *filename);
void dump_inifile(inifile_t *p);
void unload_inifile(inifile_t *pIni);
int write_inifile(inifile_t *pIni);
int inifile_get_token(inifile_t *pIni,char *section, char *token, char *def,
			char *value);
int inifile_get_int(inifile_t *pIni,char *section, char *token,int def);
#endif
