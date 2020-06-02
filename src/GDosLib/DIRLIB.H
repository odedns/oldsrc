/*----------------------------  dirlib.h -------------------------------*/
/* DIRLIB.H by M. J. Weinstein   Released to public domain 1-Jan-89 */

#ifndef _DIRLIB_H
#define _DIRLIB_H

#define MAXNAMLEN	15

struct direct {
	long		d_ino;
	unsigned short 	d_reclen;
	unsigned short 	d_namlen;
	char		d_name[MAXNAMLEN+1];
};

typedef struct {
	struct {
		char	fcb[21];
		char	attr;
		short	time;
		short	date;
		long	size;
		char	name[13];
	} dd_dta;
	short	dd_dosver;	/* which version of dos? */
	short	dd_stat;	/* status return from last lookup */
	char	dd_name[1];	/* full name of file -- struct is extended */
} DIR;

#ifdef LINT_ARGS
	DIR *opendir(char *);
	struct direct *readdir(DIR *);
	long telldir(DIR *);
	void seekdir(DIR *, long);
	void closedir(DIR *);
#else
	DIR *opendir();
	struct direct *readdir();
	long telldir();
#endif LINT_ARGS

#define rewinddir(dirp)	seekdir(dirp,0L)
#endif
