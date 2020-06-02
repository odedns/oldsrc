#include <iostream.h>

#include <sys\stat.h>
#include <dirent.h>
#include <stdio.h>  // for sprintf


// use the date and time classes.
#include "gtime.h"

#include "gfile.h"

// /////////////////////////////////////////////////
//
// GFile implementation
//
////////////////////////////////////////////////////

// default GFile constructor
//


GFile::GFile()
{

}

// GFile constructor
GFile::GFile(const char *fname)
{
struct stat st;
int rv;

	m_fname = fname;
	rv = stat((char *)fname,&st);
	if(rv) {
		cout << "error stating:"  << fname << endl;
		return;
	}
	m_size = st.st_size;
	m_atime = st.st_atime;
	m_mtime = st.st_mtime;
	m_ctime = st.st_ctime;
	m_flags = st.st_mode;
}

// print a GFile object
ostream& operator<<(ostream& s, const GFile& f)
{
	return(s << f.m_fname << "\tsize: " << f.m_size << "\tflags:" << f.m_flags);
}

void GFile::print_flags() const
{
int flags = m_flags;

	if(flags & S_IFMT) {
		cout << "S_IFMT" << endl;
	}
	if(flags & S_IFIFO) {
			cout << "S_IFIFO" << endl;
	}
	if(flags & S_IFDIR) {
		cout << "S_IFDIR" << endl;
	}
	if(flags & S_IFREG) {
		cout << "S_IFREG" << endl;
	}
	if(flags & S_IFCHR) {
		cout << "S_IFCHR" << endl;
	}
	if(flags & S_IFBLK) {
		cout << "S_IFBLK" << endl;
	}
	if(flags & S_IEXEC) {
		cout << "S_IEXEC" << endl;
	}
	if(flags & S_IWRITE) {
		cout << "S_IWRITE" << endl;
	}
	if(flags & S_IREAD) {
		cout << "S_IREAD" << endl;
	}

}

GFile& GFile::operator=(const GFile& f)
{
	m_fname = f.m_fname;
	m_size = f.m_size;
	m_atime = f.m_atime;
	m_mtime = f.m_mtime;
	m_ctime = f.m_ctime;
	m_flags = f.m_flags;

	return(*this);
}
// /////////////////////////////////////////////////
//
// GDir implementation
//
////////////////////////////////////////////////////

// default constructor
GDir::GDir()
{
	m_pdir = 0;
	m_name = 0;
	m_empty = 0;

}

// constructor
GDir::GDir(const char *dir_name):GFile(dir_name)
{
	this->open(dir_name);

}

// GDir destructor close the directory
GDir::~GDir()
{
	this->close();
}


// open the directory
int GDir::open(const char *dir_name)
{
	m_name = (char *)dir_name;
	m_pdir = opendir((char *)dir_name);
	if(!m_pdir) {
		cout << "error opening: " << dir_name << endl;
		m_empty = 1;
		return(-1);
	}
	m_empty = 0;
	return(0);
}

// read a file from the directory
// returns a GFile object or 0 if empty
GFile& GDir::read()
{
struct dirent *ent;
static GFile f;
char path[MAX_PATH];

	// read next entry in dir
	ent = readdir(m_pdir);
	if(ent) {
		sprintf(path,"%s\\%s",m_name,ent->d_name);
		f = path;
		return(f);
	} else {
		m_empty = 1;
		return(*(GFile*)0);
	}
}

// seek count items into the directory
// fake the read operation
void GDir::seek(int count)
{
	while(--count) {
		// fake one read
		this->read();
	}
}

// close the directory
int GDir::close()
{
int rv = 0;

	if(m_pdir) {
		closedir(m_pdir);
		m_pdir = 0;
	} else {
		rv = 1;
	}
	return(rv);
}

// rewind the dir to the first
// file entry
// returns 0 on success
int GDir::rewind()
{
int rv = 0;

	if(m_pdir) {
		rewinddir(m_pdir);
	} else {
		rv = 1;
	}
	return(rv);
}

//assigment operator
GDir& GDir::operator=(const GDir &d)
{
	m_pdir = d.m_pdir;
	m_name = d.m_name;
	return(*this);
}



#ifdef _TEST
// main test
void main()
{
GFile ff;

	GDir d("c:\\bat");
	GFile f("c:\\Image.dat");

	cout << f << endl;
	f.print_flags();


	ff = d.read();
	while(!d.is_empty()) {
		ff = d.read();
		if(!ff.is_dir()) {
			cout << ff << endl;
//			ff.print_flags();
		}

	}
	d.rewind();
	ff = d.read();
	cout << ff << endl;
}

#endif
