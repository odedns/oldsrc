// Sample code C++ course, lecture notes, item 7.3.3 / 2.
// Copyright (c) 1994 Avner Ben.
// last update 23.2.94

#include <iostream.h>

typedef int Boolean;

class FileOfRecs {
	char* m_buf;
public:
	virtual Boolean findNext() 
	 { cout << "FileOfRecs::findNext" << endl; return 1; }
};
class FileWKey : public FileOfRecs {
public:
	virtual Boolean find( const char* )
	 { return 1; }
};
class IndexSeqFile : public FileWKey {
public:
	void findNext()
	 { cout << "IndexSeqFile::findNext" << endl; }
};                  

main()
{
	FileOfRecs* input;
	
	input = new IndexSeqFile;
	int i = input->findNext(); 
	
	return 0;
}