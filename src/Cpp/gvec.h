
/*------------------------------------------------------------------------*/
/*   Module       :                                                       */
/*   File         :                                                       */
/*   Date         :                                                       */
/*   Description  :                                                       */
/*   Author       :  Oded Nissan                                          */
/*                   Copyright (c) 1994,1997 Oded Nissan.                 */
/*   History      :                                                       */
/*------------------------------------------------------------------------*/
/*   Date         |   Description                                         */
/*------------------------------------------------------------------------*/
/*   01/01/1997   |   Initial Release.                                    */
/*                |                                                       */
/*                |                                                       */
/*------------------------------------------------------------------------*/
/*------------------------------------------------------------------------*/
/*   Module       :                                                       */
/*   File         :                                                       */
/*   Date         :                                                       */
/*   Description  :                                                       */
/*   Author       :  Oded Nissan                                          */
/*                   Copyright (c) 1994,1997 Oded Nissan.                 */
/*   History      :                                                       */
/*------------------------------------------------------------------------*/
/*   Date         |   Description                                         */
/*------------------------------------------------------------------------*/
/*   01/01/1997   |   Initial Release.                                    */
/*                |                                                       */
/*                |                                                       */
/*------------------------------------------------------------------------*/
#ifndef _GVEC_H_
#define _GVEC_H_

#include "gcont.h"

// the default vector size
#define GVECTOR_DEF_SIZE  (256)

#define GVECTOR_CLASS     (0xff10)

// a simple generic vector template

template <class T> class gVector : public GContainer {

protected:
	int m_size;
	T *m_vec;

public:
	// constructor
	// creates size elements in vector
	gVector(int size)
	{
		m_size = size;
		m_vec = new T[size];
	}

	// empty constructor
	// uses default size
	gVector()
	{
		m_size = GVECTOR_DEF_SIZE;
		m_vec = new T[m_size];
	}

	// destructor
	~gVector()
	{
		m_size = 0;
		delete[] m_vec;
	}
	// return the vector size
	inline int size() { return (m_size); }

	// a referance operator
	T& operator[](int i)
	{
		return(m_vec[i]);
	}

	const char *name()
	{
		return("gVector");
	}

	int type() 
	{ 
		return(GVECTOR_CLASS);
	}

	int empty()
	{
		return(m_size == 0);
	}


};


#endif
