// Sample code C++ course, lecture notes, item 7.3.3 / 1.
// Copyright (c) 1994 Avner Ben.
// last update 23.2.94

#include <iostream.h>

typedef int Boolean;

class FileOfRecs {
	char* m_buf;
public:
	Boolean findFirst() 
	 { return 1; }
	virtual Boolean findNext() 
	 { cout<< "FileOfRecs::findNext" << endl; return 1; }
	Boolean atEnd() 
	 { return 1; }
};
class FileWKey : public FileOfRecs {
public:
	virtual Boolean find( const char* )
	 { return 1; }
};
class IndexSeqFile : public FileWKey {
public:
	Boolean findNext()
	 { cout<< "IndexSeqFile::findNext" << endl; return 1; }
};                  

main()
{
	FileOfRecs* input;
	
	input = new FileOfRecs;
	input->findNext();
	
	delete input;
	input = new IndexSeqFile;
	input->findNext(); 
	
	return 0;
}