// Sample code C++ course, lecture notes, item 7.3.4 / 1.
// Copyright (c) 1994 Avner Ben.
// last update 23.2.94

#include <iostream.h>

typedef int Boolean;

class FileOfRecs {
	Boolean eof;
public:
	Boolean findFirst()
	 { eof = 0; return findNext(); }
	virtual Boolean findNext()
	 { cout << "FileOfRecs::findnext" << endl; return 1; }
};
class IndexSeqFile : public FileOfRecs {
public:
	virtual Boolean findNext()
	 { cout << "IndexSeqFile::findnext" << endl; return 1; }
};                  

main()
{
	FileOfRecs* input;
	
	input = new IndexSeqFile;
	input->findFirst(); 

	return 0;
}