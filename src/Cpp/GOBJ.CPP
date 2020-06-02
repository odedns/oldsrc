
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
#include <iostream.h>
#include <ctype.h>
#include <limits.h>

#include "gcont.h"

#define GOBJECT_CLASS (0x0001)

class GObject {

public:
	// default constructor
	GObject()   {    }
	// destructor
	virtual ~GObject()  {    }
	// class type
	virtual int type()
	{
		return(GOBJECT_CLASS);
	}
	// class name
	virtual const char *get_name()
	{
		return("GObject");
	}
	// hash code
	virtual int hash_code()
	{
		return ((int)this);
	}
	// print the object
	virtual void print()     {  }
	// convert the object to string
	virtual const char *to_string()
	{
		return(0);
	}
	int equals(const GObject& obj)
	{
		return(&obj == this);
	}

};

#define GCHAR_CLASS (0x0001)
class gChar: public GObject {

	short m_ch;
public:
	gChar()	 // constructor
	{
	}

	~gChar() // destructor
	{
	}
	// constructor
	gChar(char c)
	{
		m_ch = c;
	}
	int max_value() 
	{
		return(CHAR_MAX);
	}
	int min_value()
	{
		return(CHAR_MIN);
	}

	short value_of()
	{
		return(m_ch);
	}

	int tolower()
	{
		return(::tolower(m_ch));
	}
	int toupper()
	{
		return(::toupper(m_ch));
	}

	int type()   { return (GCHAR_CLASS); }
	const char *get_name() { return("gChar"); }
	void print()    { cout << (char ) m_ch << endl; }

	/////////////////////////////////////////////////////
	// operators
	/////////////////////////////////////////////////////
	friend ostream& operator<<(ostream& s, const gChar& c)
	{
		return(s << c.m_ch);
	}
	friend istream& operator>>(istream& s, const gChar& c)
	{
		return(s >> c.m_ch);
	}

	gChar& operator=(gChar& c)
	{
		m_ch = c.m_ch;
		return(*this);
	}
	int equals(gChar& c)
	{
		return(m_ch == c.m_ch);
	}
	int operator==(gChar& c)
	{
		return(m_ch == c.m_ch);
	}
	int operator!=(gChar& c)
	{
		return(m_ch != c.m_ch);
	}
	int operator<(gChar& c)
	{
		return(m_ch < c.m_ch);
	}
	int operator>(gChar& c)
	{
		return(m_ch > c.m_ch);
	}

};

#define GINTEGER_CLASS (0x0003)
class GInteger: public GObject {
	long m_int;

public:
	// default constructor
	GInteger()  { }
	// conversion constructor
	GInteger(int n)
	{
		m_int = n;
	}
	// copy constructor
	GInteger(GInteger& n)
	{
		m_int = n.m_int;
	}
	// destructor
	~GInteger() { }

	// class type
	int type()   { return (GINTEGER_CLASS); }

	// class name
	const char *get_name() { return("GInteger"); }

	// print object
	void print()    { cout <<  m_int << endl; }
	// operators

	// output operator
	friend ostream& operator<<(ostream& s, const GInteger& n)
	{
		return(s << n.m_int);
	}
	// input operator
	friend istream& operator>>(istream& s, const GInteger& n)
	{
		return(s >> n.m_int);
	}

	long max_value()
	{
		return(LONG_MAX);
	}
	long min_value()
	{
		return(LONG_MIN);
	}

	GInteger& operator=(GInteger& n)
	{
		m_int = n.m_int;
		return(*this);
	}
	int operator==(GInteger& n)
	{
		return(m_int == n.m_int);
	}
	int operator!=(GInteger& n)
	{
		return(m_int != n.m_int);
	}
	int operator<(GInteger& n)
	{
		return(m_int < n.m_int);
	}
	int operator>(GInteger& n)
	{
		return(m_int > n.m_int);
	}


};

#define GFLOAT_CLASS (0x0004)
class GFloat : public GObject {
	float m_float;

public:
	// default constructor
	GFloat()   { }
	// destructor
	~GFloat()  { }
	// conversion constructor
	GFloat(float f)
	{
		m_float = f;
	}
	// copy constructor
	GFloat(GFloat& n)
	{
		m_float = n.m_float;
	}

	// class type
	int type()   { return (GFLOAT_CLASS); }

	// class name
	const char *get_name() { return("GFloat"); }

	// print object
	void print()    { cout <<  m_float << endl; }
	// operators

	// output operator
	friend ostream& operator<<(ostream& s, const GFloat& n)
	{
		return(s << n.m_float);
	}
	// input operator
	friend istream& operator>>(istream& s, const GFloat& n)
	{
		return(s >> n.m_float);
	}

	long max_value()
	{
		return(LONG_MAX);
	}
	long min_value()
	{
		return(LONG_MIN);
	}

	GFloat& operator=(GFloat& f)
	{
		m_float = f.m_float;
		return(*this);
	}
	int operator==(GFloat& f)
	{
		return(m_float == f.m_float);
	}
	int operator!=(GFloat& f)
	{
		return(m_float != f.m_float);
	}
	int operator<(GFloat& f)
	{
		return(m_float < f.m_float);
	}
	int operator>(GFloat& f)
	{
		return(m_float > f.m_float);
	}


};

#define GDOUBLE_CLASS (0x0005)
class GDouble : public GObject {
	double m_double;
public:
	// default constructor
	GDouble()   { }
	// destructor
	~GDouble()  { }
	// conversion constructor
	GDouble(double d)
	{
		m_double= d;
	}
	// copy constructor
	GDouble(GDouble& n)
	{
		m_double= n.m_double;
	}

	// class type
	int type()   { return (GDOUBLE_CLASS); }

	// class name
	const char *get_name() { return("GDouble"); }

	// print object
	void print()    { cout <<  m_double<< endl; }
	// operators

	// output operator
	friend ostream& operator<<(ostream& s, const GDouble& n)
	{
		return(s << n.m_double);
	}
	// input operator
	friend istream& operator>>(istream& s, const GDouble& n)
	{
		return(s >> n.m_double);
	}

	long max_value()
	{
		return(LONG_MAX);
	}
	long min_value()
	{
		return(LONG_MIN);
	}

	GDouble& operator=(GDouble& d)
	{
		m_double = d.m_double;
		return(*this);
	}
	int operator==(GDouble& d)
	{
		return(m_double == d.m_double);
	}
	int operator!=(GDouble& d)
	{
		return(m_double != d.m_double);
	}
	int operator<(GDouble& d)
	{
		return(m_double < d.m_double);
	}
	int operator>(GDouble& d)
	{
		return(m_double > d.m_double);
	}


};

#define GSTRING_CLASS (0x0005)
class GString : public GObject {

};


#define GVECTOR_CLASS (0x0010)
class GVector: public GContainer {
	GObject *m_vec;
	int     m_size;

public:
	// constuctor 
	GVector(int size);
	// default constuctor 
	GVector();
	// copy constructor
	GVector(GVector& vec);

	// class type
	int type();
	// class name 
	const char* name();
	int is_empty();
	int empty();
	GObject& element_at(int index);
	GObject& operator[](int index);
	int size();


};
	
class GBitset: public GContainer {


};

class GList: public GContainer {


};

void main()
{
int n = 5;

GInteger gint(n);

	cout << gint << endl;
	if(gint == 5) {
		cout << "equals " << endl;
	}
}
