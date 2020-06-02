// Sample code C++ course, lecture notes, item 7.3.4 / 2.
// Copyright (c) 1994 Avner Ben.
// last update 23.2.94

#include <iostream.h>

typedef int Boolean;

class FileOfRecs {
public:
	FileOfRecs() { 
		cout << "fileOfRecs::ctor {" << endl; 
		findNext(); 
		cout << "} fileOfRecs::ctor" << endl; 
	}
	virtual ~FileOfRecs() { 
		cout << "fileOfRecs::dtor {" << endl; 
		findNext(); 
		cout << "} fileOfRecs::dtor" << endl; 
	}
	virtual Boolean findNext()
	 { cout << "\tFileOfRecs::FindNext" << endl; return 1; }
};
class IndexSeqFile : public FileOfRecs {
public:
	IndexSeqFile() { 
		cout << "IndexSeqFile::ctor {" << endl; 
		findNext(); 
		cout << "} IndexSeqFile::ctor" << endl; 
	}
	~IndexSeqFile() { 
		cout << "IndexSeqFile::dtor {" << endl; 
		findNext(); 
		cout << "} IndexSeqFile::dtor" << endl; 
	}
	Boolean findNext()
	 { cout << "\tIndexSeqFile::FindNext" << endl; return 1; }
};                  

main()
{
	FileOfRecs* input;
	
	input = new IndexSeqFile;
	delete input; 
	
	return 0;
}