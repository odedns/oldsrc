// Sample code C++ course, lecture notes, item 7.3.3 / 3.
// Copyright (c) 1994 Avner Ben.
// last update 23.2.94

#include <iostream.h>

typedef int Boolean;

class FileOfRecs {
	char* m_buf;
public:
	FileOfRecs()
	 :m_buf( new char[20] ) { }
	~FileOfRecs() 
	 { delete m_buf; cout << "FileOfRecs::dtor" << endl; }
	virtual Boolean findNext()
	 { return 1; }
};
class FileWKey : public FileOfRecs {
	int* m_data;
public:
	FileWKey()
	 :m_data( new int[100] ) { }
	~FileWKey() 
	 { delete m_data; cout << "FileWKey::dtor" << endl; }
};
class IndexSeqFile : public FileWKey {
	float* m_data;
public:
	IndexSeqFile()
	 :m_data( new float[1000] ) { }
	~IndexSeqFile() 
	 { delete m_data; cout << "IndexSeqFile::dtor" << endl; }
};                  

main()
{
	FileOfRecs* input;
	
	input = new IndexSeqFile;
	delete input;; 
	
	return 0;
}