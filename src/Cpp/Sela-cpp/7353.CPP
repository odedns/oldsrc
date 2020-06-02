// Sample code C++ course, lecture notes, item 7.3.5 / 3.
// Copyright (c) 1994 Avner Ben.
// last update 23.2.94

#include <iostream.h>

typedef int Boolean;

class FileOfRecs {
public:
	virtual Boolean findNext()
	 { cout << "FileOfRecs::FindNext" << endl; return 1; }
};
class IndexSeqFile : public FileOfRecs {
public:
	Boolean findNext()
	 { cout << "IndexSeqFile::FindNext" << endl; return 1; }
};                  
       

void f( FileOfRecs& aFile ) {
	aFile.findNext();
}

main()
{
	IndexSeqFile input;
	
	f( input );
	
	return 0;
}