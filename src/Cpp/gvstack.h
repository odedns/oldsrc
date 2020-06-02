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
#ifndef _GVSTACK_H_
#define _GVSTACK_H_

#include "gcont.h"
#include "gvec.h"

#define GVSTACK_CLASS  (0xff11)

template <class T> class gVStack_iterator;

// stack template based on the gVector
// template.
// Stack is implemented as a vector
template <class T> class gVStack : public gVector <T> {

friend class gVStack_iterator;
int m_sp;  // the stack pointer

public:
	// constructor - create a stack of a specified size
	gVStack(int size): gVector<T>(size) {  m_sp = 0; }
	// default constructor - use the gVector default constructor
	gVStack() : gVector<T>() { m_sp = 0; }
	// push an item into the stack
	int push(T p)
	{
		int rv;

		// check for overflow
		if(m_size > m_sp) {
			this->m_vec[m_sp++] = p;
			rv = 0;
		}  else {
			rv = -1;
		}
		return(rv);
	}

	// pop an item from the stack
	T pop()
	{
		T n;

		// check if stack isn't empty
		if(m_sp) {
			n = this->m_vec[--m_sp];
		} else {
			cerr << "error: empty stack" << endl;
		}
		return(n);
	}


	T peek(const int i)
	{
	   T n;
		n = m_vec[i];
		return(n);
	}
	// return the number of elements inserted into the stack.
	const int nelem() { return(m_sp); }
	int is_empty() { return(!m_sp); }
	int empty() { return(m_sp); }

	const char *name()
	{
		return("gVStack");
	}

	int type()
	{
		return(GVSTACK_CLASS);
	}


};
template <class T> class gVStack_iterator  {

gVStack <T> *pStack;
int m_iter_sp;
public:
	gVStack_iterator(gVStack<T> & st) { pStack = &st;
					  m_iter_sp = pStack->nelem(); }
	~gVStack_iterator()  {  }
	T next()
	{
	 T n;
		if(m_iter_sp) {
			n = pStack->peek(--m_iter_sp);
		}
		return(n);
	 }
	 int is_empty() { return(!m_iter_sp); }
};


#endif
