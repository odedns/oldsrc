#include <iostream.h>
#include <string.h>


// A Simple String Class 
//

class String {
	private :
		char *m_buff;
		int  m_len;
        public :
		// Constructors
		String(int len );
		String(const String& s);
		String(const char *s);

		// Destructor 
		~String()   { delete m_buff; } // Destructor


		int len()  const  { return (m_len); }
		int compare(String& s1, String& s2);
		const char *get()  { return(m_buff); }
		String& operator=(const String& s);
		String& operator+=(const String& s1);
		int operator==(String& s1) { return(0 == compare(*this,s1)); }
		int operator<(const String& s1) { return(0 > strcmp(m_buff,s1.m_buff)); }
		int operator>(const String& s1) { return(0 < strcmp(m_buff, s1.m_buff)); }
		char operator[](int index) { return(m_buff[index]); }
};

// Constructor
String :: String(int len)
{
	m_buff = new char[len+1];
	m_len = 0;
}


// Constructor
String :: String(const String &s)
{
	m_len = s.len();
	m_buff = new char[m_len+1];
	strcpy(m_buff, s.m_buff);
}

// Constructor
String :: String(const char *s)
{
	m_len = strlen(s);
	m_buff = new char [m_len+1];
	strcpy(m_buff,s);
}

// --------------------
// Operator functions
// --------------------


String& String :: operator=(const String& s)
{
	m_buff = strdup(s.m_buff);
	m_len = s.len();
	return(*this);
}


String& String :: operator+=(const String& s1 )
{
	strcat(m_buff,s1.m_buff);
	m_len += s1.len();
	return(*this);
}

//------------------------------------------------






void main()
{
String MyStr("C Plus Plus ");
char *p = "AAA";

	cout << " String : " <<  MyStr.get()  << '\n' ;
	cout << "len : " << MyStr.len() << '\n';

	MyStr = "Oded";
	MyStr = p;
	cout << " String : " <<  MyStr.get()  << '\n' ;

	MyStr += "Nissan";
	cout << " String : " <<  MyStr.get()  << '\n' ;
	MyStr = "ABC";

	if(MyStr > "AAC")
		cout << "Equal\n";

}
