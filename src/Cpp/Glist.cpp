
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
#include "glist.h"


//-------------------------------
// glist_base implementation
//-------------------------------

// destructor
// delete all nodes from the list
glist_base::~glist_base()
{
node *p, *q;

	p = m_first;
	while(p != 0) {
		q = p->m_next;
		delete p;
		p = q;
	}
}


// add to front of list
void glist_base::insert(node *p)
{
	// if not empty
	if(m_first) {
		m_first->m_prev = p;
	}
	p->m_next = m_first;
	p->m_prev = 0;
	m_first = p;
}

// add to end of list
void glist_base::append(node *p)
{
	if(m_last) {
		m_last->m_next = p;
	} else {
		m_first = p;
	}
	p->m_prev = m_last;
	m_last = p;
	p->m_next = 0;
}

node *glist_base::get(glist_base_iter *iter)
{
	return(iter->current());
}

void glist_base::insert_after(glist_base_iter& iter, node *p)
{
node *after = iter.current();

	if(after) {
		// forward link
		p->m_next = after->m_next;
		after->m_next = p;
	} else {
		p->m_next = 0;
	}
	// back link
	p->m_prev = after;

}

void glist_base::insert_before(glist_base_iter& iter, node *p)
{
node *before = iter.current();

	if(before) {
		p->m_prev = before->m_prev;
		before->m_prev = p;
	} else {
		p->m_prev = 0;
	}
	p->m_next = before;
}

node *glist_base::remove(glist_base_iter& iter,int dir)
{
node *p = iter.current();

	if(!p) {
		return(0);
	}

	if(p->m_next) {
		p->m_next->m_prev = p->m_prev;
	}
	if(p->m_prev) {
		p->m_prev->m_next = p->m_next;
	}
	// adjust iterator
	if(dir) {
		iter.next();
	} else {
		iter.prev();
	}
	return(p);

}

node *glist_base::pop_front()
{
node *p;

	if(!m_first) {
		return(m_first);
	}
	p = m_first;

	if(m_first->m_next) {
		m_first->m_next->m_prev = 0;
	}
	m_first = m_first->m_next;
	return(p);
}

node *glist_base::pop_back()
{
node *p;

	if(!m_last)
		return(m_last);

	p = m_last;
	if(m_last->m_prev) {
		m_last->m_prev->m_next = 0;
	}
	m_last = m_last->m_prev;
	return(p);
}

template<class T> int gList<T>::is_empty()
{
	return(glist_base::is_empty());
}

template <class T> void gList<T>:: insert_after(gList_iter<T>& iter,const T& data)
{
	glist_base::insert_after(iter,new Tnode<T>(data));
}

template <class T> void gList<T>:: insert_before(gList_iter<T>& iter,const T& data)
{
	glist_base::insert_before(iter,new Tnode<T>(data));
}

template <class T> int gList<T>:: remove_forw(gList_iter<T>& iter, T& data)
{
Tnode<T> *n;
int rv = 0;

	n = (Tnode<T> *) glist_base::remove_forw(iter);
	if(n) {
		data = n->m_data;
		delete n;
		rv = 1;
	}
	return(rv);
}

template <class T> int gList<T>:: remove_back(gList_iter<T>& iter, T& data)
{
Tnode<T> *n;
int rv = 0;

	n = (Tnode<T> *) glist_base::remove_back(iter);
	if(n) {
		data = n->m_data;
		delete n;
		rv = 1;
	}
	return(rv);
}

//  *****************
//  main test program
//  *****************
void main()
{
#define MAX (3)

gList<int> a;

int n;
int rv;

	cout << "\n\n\n\n";
	for(int i = 0; i < MAX; ++i) {
		a.append(i);
	}

	gList_iter<int> iter(a);
#if 0
	for(rv = iter.begin(n); rv; rv = iter.next(n)) {
		cout << "n = " << n << endl;
	}

	for(rv = iter.end(n); rv; rv = iter.prev(n)) {
		cout << "n = " << n << endl;
	}

#endif
	rv = iter.end(n);
	while(rv)  {
		rv = a.remove_back(iter,n);
		if(rv) {
			cout << "removing n = "<< n << endl;
		}
	}
}
