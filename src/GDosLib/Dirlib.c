/*--------------------------- dirlib.c ---------------------------------*/

/*
    Directory Access Library

                    DIRLIB.C by M. J. Weinstein
		 Released to public domain 1-Jan-89

    The author may be contacted at: 
	matt@cs.ucla.edu -or- POB 84524, L.A., CA  90073
 */


/*
 * revision history:
 *
 *	VER	MM/DD/YY	COMMENTS
 *	----	--------	--------
 *	0.99	02/24/86	Beta release to INTERNET
 */

#define LINT_ARGS

#include <stdlib.h>
#include <ctype.h>
#include <errno.h>
#include <string.h>
#include <dos.h>
#include <stdlib.h>
/* #include <alloc.h> */

#include "dirlib.h"

#ifdef DEBUG
#	define	PRIVATE
#else
#	define	PRIVATE	static
#endif	DEBUG

PRIVATE _err;

PRIVATE DIR *_findfirst(char *, DIR *);
PRIVATE DIR *_findnext(DIR *);
PRIVATE char far *_getsetdta(char far *);

#ifdef DEBUG

#include <stdio.h>

PRIVATE void _dumpdir(DIR *);

PRIVATE void _dumpdir(dirp)
DIR *dirp;
{
	int i;
	char *cp;

	printf("\ndump of DIR at %xH: fcb:", dirp);
	for (i = 0, cp = (char *)&dirp->dd_dta; 
	    i < sizeof(dirp->dd_dta.fcb); i++, cp++) {
		if ((i%16) == 0)
			printf("\n%3.3d (%3.3xH):  ", i);
		printf("%3d ", (unsigned) *cp & 0xff);
	}
	printf("\nattr %xH  time %d  date %d  size %ld  name %s\n", 
		dirp->dd_dta.attr, dirp->dd_dta.time, 
		dirp->dd_dta.date, dirp->dd_dta.size, dirp->dd_dta.name);
	printf("ver %d  stat %d  pattern %s\n\n", 
		dirp->dd_dosver, dirp->dd_stat, dirp->dd_name);
}

#endif DEBUG

/*
 * return dos major version #
 */

PRIVATE	int dosver()
{
	union REGS r;
	r.h.ah = 0x30;
	intdos(&r, &r);
	return (int)r.h.al;
}

/*
 * get/set dta address
 */

PRIVATE char far *_getsetdta(newdta)
char far *newdta;
{
	char far *olddta;
	union REGS r;
	struct SREGS s;

	/* get old dta */     	
	r.h.ah = 0x2f;
	intdos(&r, &r);
	segread(&s);
	FP_SEG(olddta) = s.es;
	FP_OFF(olddta) = r.x.bx;

	/* conditionally set new dta */
	if (newdta) {
		r.h.ah = 0x1a;
		s.ds	= FP_SEG(newdta);
		r.x.dx	= FP_OFF(newdta);	
		intdosx(&r, &r, &s);
	}

	return olddta;
}

/*
 * dos findfirst
 */

PRIVATE DIR *_findfirst(name, dirp)
char *name;
DIR *dirp;
{
	union REGS r;  
	struct SREGS s;
	char far *dtasave;

	dtasave = _getsetdta((char far *)dirp);
	
	/* do directory lookup */
	segread(&s);
	r.h.ah	= 0x4e;
	r.x.cx	= 0x10;
	r.x.dx	= FP_OFF((char far *)name);
	s.es	= FP_SEG((char far *)name);
	intdosx(&r, &r, &s);
	/* restore dta */
	_getsetdta(dtasave);
	_err = r.x.ax;
	if (r.x.cflag)
		return (DIR *) 0;
#ifdef	DEBUG
	_dumpdir(dirp);
#endif	DEBUG
	return dirp;
}

/*
 * dos findnext
 */

PRIVATE DIR *_findnext(dirp)
DIR *dirp;
{
	union REGS r;  
	struct SREGS s;
	char far *dtasave;

	dtasave = _getsetdta((char far *)dirp);

	/* do directory lookup */
	r.h.ah = 0x4f;
	intdos(&r, &r);
	/* restore old dta */
	_getsetdta(dtasave);
	_err = r.x.ax;
	if (r.x.cflag)
		return (DIR *) 0;
#ifdef	DEBUG
	_dumpdir(dirp);
#endif	DEBUG
	return dirp;
}

/*
 * get working directory for a particular drive
 */

PRIVATE char *getdcwd(drive)
int drive;
{
	union REGS r;
	struct SREGS s;
	static char xcwd[64];
	char far *cwd = xcwd;

	r.h.ah = 0x47;
	r.h.dl = drive;
	r.x.si = FP_OFF(cwd);
	s.ds = FP_SEG(cwd);
	intdosx(&r, &r, &s);
	_err = r.x.ax;
	if (r.x.cflag)
		return (char *) 0;
	return xcwd;
}

/*
 * opendir
 */

#define SUFFIX	"\\*.*"
#define	SLASH	"\\"
#define streq(a,b)	(strcmp(a,b)==0)

DIR *opendir(name)
char *name;
{
	register DIR *nd;
	char *cwd;
	char drive[3];
	int atroot = 0;
	int rooted = 0;

	/*
	 * hack off drive designator if present
	 */

	if (name[1] == ':') {
		cwd = getdcwd(toupper(name[0]) - 'A' + 1);
		drive[0] = name[0]; drive[1] = ':'; drive[2] = '\0';
		name += 2;
	}
	else {
		cwd = getdcwd(0);
		drive[0] = '\0';
	}

#ifdef	DEBUG
	printf("working on drive %s = /%s\n", drive, cwd);
#endif	DEBUG

	/* is the name 'rooted'? */
	if ((*name == '/') || (*name == '\\')) ++rooted;

	/* see if we are at the root directory for this device */
	if (!*cwd) ++atroot;

	/* 
	 * MSDOS '/' doesn't have a '.' or '..'
	 * also, double '/' sequences don't make sense.
	 * many ported programs expect them to work, so we fix it up...
	 */

	/* chop off leading . and .. if at root */
	if (atroot && (*name == '.')) {
		switch (*++name) {
		case '\0': case '/': case '\\':
			break;
		case '.':
			switch (*++name) {
			case '\0': case '/': case '\\':
				break;
			default:
				--name; 
				--name;
			}
			break;
		default:
			--name;
		}
	}

#ifdef DEBUG
	printf("after chopping leading .'s: %s\n", name);
#endif DEBUG

	/* chop off leading /'s, /.'s and /..'s to make naming sensible */
	while (*name && ((*name == '/') || (*name == '\\'))) {
		if (*++name == '.') {
			switch (*++name) {
			case '\0': case '/': case '\\':
				break;
			case '.':
				switch (*++name) {
				case '\0': case '/': case '\\':
					break;
				default:
					--name; 
					--name;
				}
				break;
			default:
				--name;
			}
		}
	}

#ifdef DEBUG
	printf("after chopping /'s: %s\n", name);
#endif DEBUG

	/*
	 * name should now look like: path/path/path 
	 * we must now construct name based on whether or not it
	 * was 'rooted' (started with a /)
	 */

	if (rooted) cwd = "";

	/* construct DIR */
	if (!(nd = (DIR *)malloc(
	    sizeof(DIR)+strlen(drive)+strlen(cwd)+strlen(SLASH)+
	    strlen(name)+strlen(SUFFIX))))
		return (DIR *) 0;
	/* create long name */
	strcpy(nd->dd_name, drive);
	if (*cwd) {
		strcat(nd->dd_name, SLASH);
		strcat(nd->dd_name, cwd);
	}
	if (*name) {
		strcat(nd->dd_name, SLASH);
		strcat(nd->dd_name, name);
	}
	strcat(nd->dd_name, SUFFIX);

#ifdef	DEBUG
	printf("calling findfirst(%s)\n", nd->dd_name);
#endif	DEBUG

	/* search */
	if (!_findfirst(nd->dd_name, nd)) {
		free((char *)nd);
		errno = ENOENT;
		return (DIR *) 0;
	}
	nd->dd_stat = 0;
	nd->dd_dosver = dosver();
	return nd;
}

struct direct *readdir(dirp)
DIR *dirp;
{
	static struct direct dir;

	if (dirp->dd_stat)
		return (struct direct *) 0;

	/* format structure */
	dir.d_ino = 0; /* not valid for DOS */
	dir.d_reclen = 0;
	strcpy(dir.d_name, dirp->dd_dta.name);
	dir.d_namlen = strlen(dir.d_name);
	strlwr(dir.d_name); /* DOSism */

	/* read ahead */
	if (_findnext(dirp))
		dirp->dd_stat = 0;
	else		
		dirp->dd_stat = _err;

	return &dir;
}

void closedir(dirp)
DIR *dirp;
{
	free((char *)dirp);
}

/*
 * fake seek for DOS 2.x
 */

PRIVATE void dos2seek(dirp, pos)
DIR *dirp;
long pos;
{
	/*
	 * check against DOS limits
	 */

	if ((pos < 0) || (pos > 4095)) {
		dirp->dd_stat = 1;
		return;
	}

	if (pos == 0) {
		if (_findfirst(dirp->dd_name, dirp))
			dirp->dd_stat = 0;
		else
			dirp->dd_stat = _err;
	}
	else {
		pos--;
		dirp->dd_dta.fcb[1] = 2 - (pos % 3);
		*(short *)&dirp->dd_dta.fcb[13] = pos;
		/* read ahead */
		if (_findnext(dirp))
			dirp->dd_stat = 0;
		else		
			dirp->dd_stat = _err;
	}
}

/*
 * fake seek for DOS 3.x
 */

PRIVATE void dos3seek(dirp, pos)
DIR *dirp;
long pos;
{
	/*
	 * check against DOS limits
	 */

	if ((pos < 0) || (pos > 4095)) {
		dirp->dd_stat = 1;
		return;
	}

	*(short *)&dirp->dd_dta.fcb[13] = pos + 1;
	/* read ahead */
	if (_findnext(dirp))
		dirp->dd_stat = 0;
	else		
		dirp->dd_stat = _err;
}

void seekdir(dirp, newpos)
DIR *dirp;
long newpos;
{
	switch (dirp->dd_dosver) {
	case 2:
		dos2seek(dirp, newpos);
		break;
	case 3:
		dos3seek(dirp, newpos);
		break;
	default:
		abort();
		break;
	}
}

PRIVATE long dos2tell(dirp)
DIR *dirp;
{
	return (long) *(short *)&dirp->dd_dta.fcb[13];

}

PRIVATE long dos3tell(dirp)
DIR *dirp;
{
	return (long) (*(short *)&dirp->dd_dta.fcb[13] - 2);
}

long telldir(dirp)
DIR *dirp;
{
	switch (dirp->dd_dosver) {
	case 2:
		return dos2tell(dirp);
	case 3:
		return dos3tell(dirp);
	default:
		abort();
	}
}
