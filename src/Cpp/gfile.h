
////////////////////////////////////////////////////////////////////////////
//   Module       :                                                       //
//   File         :                                                       //
//   Date         :                                                       //
//   Description  :                                                       //
//   Author       :  Oded Nissan                                          //
//                   Copyright (c) 1994-1998 Oded Nissan.                 //
//   History      :                                                       //
//------------------------------------------------------------------------//
//   Date         |   Description                                         //
//------------------------------------------------------------------------//
//   01/01/1998   |   Initial Release.                                    //
//                |                                                       //
//                |                                                       //
////////////////////////////////////////////////////////////////////////////
#ifndef _GFILE_H_
#define _GFILE_H_

#ifndef MAX_PATH
#define MAX_PATH (1024)
#endif

// use the date and time classes.
#include "gtime.h"



class GFile {
	const char *m_fname;  		// file name
	int   m_flags;  		// file mode
		//	S_IFMT   File type mask
		//	S_IFDIR   Directory
		//	S_IFIFO   FIFO special
		//	S_IFCHR   Character special
		//	S_IFBLK   Block special
		//	S_IFREG   Regular file
		//	S_IREAD   Owner can read
		//	S_IWRITE  Owner can write
		//	S_IEXEC   Owner can execute

	long m_size;    		// file size
	GTime m_mtime;   		// last modified time
	GTime m_ctime;   		// creation time
	GTime m_atime;   		// last access time
public:
	GFile();        		// default constructor
	GFile(const char *fname); 		// constructor

	long size() const             	// get the file size
	{
		return(m_size);
	}
	GTime& get_mtime() 		// get modified time
	{
		return(m_mtime);
	}
	GTime& get_ctime()  	// get creation time
	{
		return(m_ctime);
	}

	GFile& operator=(const GFile& f); // assignment operator
	~GFile()  { }                 	// destructor

	friend ostream& operator<<(ostream& s, const GFile& f);
	void print_flags() const;

	// returns true is file is a directory
	int is_dir() const
	{
		return(m_flags & S_IFDIR);
	}
	// returns true if a regular file.
	int is_regular() const
	{
		return(m_flags & S_IFREG);
	}

	// returns true if file is writable
	int is_write() const
	{
		return(m_flags & S_IWRITE);
	}

	// returns true if file is readable
	int is_read() const
	{
		return(m_flags & S_IREAD);
	}
};
// end GFile class


// GDir class
class GDir: public GFile {
	char *m_name;			// directory name
	DIR  *m_pdir;			// directory struct
	int  m_empty;			// dir empty flag

public:
	GDir();				// default constructor
	GDir(const char *dir_name);	// constructor
	~GDir();			// destructor
	int open(const char *dir_name);	// open the directory
	GFile& read();			// read files from the directory
	void seek(int count);		// seek the dir
	int rewind();			// rewind the dir
	int close();			// close the directory
	GDir& operator=(const GDir& d); // assignment operator
	int is_empty() const
	{
		return(m_empty);
	}
};
#endif
