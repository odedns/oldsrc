
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

#ifndef _GLIST_H_
#define _GLIST_H_
#include <iostream.h>

// list node
struct node {
	node *m_next;
	node *m_prev;
};

// base list call iterator forward declaration.
class glist_base_iter;

// generic list base class
class glist_base {

	node *m_first;
	node *m_last;
	friend class glist_base_iter;
public:
	// constructor
	glist_base() { m_first = m_last = 0; }
	int is_empty() { return(m_first ? 0 : 1); }
	// add to end of list
	void append(node *p);
	// add to front of list
	void insert(node *p);

	// insert operations at the middle of the list
	void insert_after(glist_base_iter& iter, node *p);
	void insert_before(glist_base_iter& iter, node *p);
	node *get(glist_base_iter *iter);

	// remove aspecific node from the list
	node *remove(glist_base_iter& iter, int dir);
	node *remove_forw(glist_base_iter& iter) { return(remove(iter,1)); }
	node *remove_back(glist_base_iter& iter) { return(remove(iter,0)); }

	// pop from front of list
	node *pop_front();
	// pop from back of list
	node *pop_back();
	// destructor
	~glist_base();


};

// generic list base class iterator.
class glist_base_iter {
	node *m_curr;
	glist_base *m_list;
public:
	// constructor
	// glist_base_iter() { }
	glist_base_iter(glist_base_iter& iter)
	{
		m_curr = iter.m_curr;
		m_list = iter.m_list;
	}
	glist_base_iter(glist_base& list) {
		m_list = &list;
		m_curr = m_list->m_first;
	 }
	 // destructor
	 ~glist_base_iter() { }

	 // get current node
	 node *current()
	 {
		return(m_curr);
	 }

	 // get next node
	 node *next()
	 {
		m_curr = m_curr->m_next;
		return(m_curr);
	 }
	 // get prev node
	 node *prev()
	 {
		m_curr = m_curr->m_prev;
		return(m_curr);
	 }

	 // set iterator to front of list
	 node *begin()
	 {
		m_curr = m_list->m_first;
		return(m_curr);
	 }

	 // set iterator to end of list
	 node *end()
	 {
		m_curr = m_list->m_last;
		return(m_curr);
	 }
};

// this is the template interface
// to the generic classes

template <class T>
struct Tnode: public node {
	// data for list item
	T m_data;
	// constructor
	Tnode( const T& data) :m_data(data) {  }
	// destructor
	~Tnode() {  }
};


// template based list iterator forward deleration
template <class T> class gList_iter;


// template based list class
template <class T>
class gList : private glist_base {
	friend class gList_iter<T>;
public:
	// constructor
	gList() { }

	int is_empty();
	// insert to front of list
	void insert(const T& data)
	{
	Tnode<T> *p = new Tnode<T>(data);

		glist_base::insert(p);
	}

	// append to end of list
	void append(const T& data)
	{
		glist_base::append(new Tnode<T>(data));
	}

	// insert data after current iterator item
	void insert_after(gList_iter<T>& iter,const T& data);


	// insert data before current iterator item
	void insert_before(gList_iter<T>& iter, const T& data);

	// remove current iterator item and
	// advance iterator
	int remove_forw(gList_iter<T>& iter, T& data);
	// remove current iterator item and
	// rewind iterator
	int remove_back(gList_iter<T>& iter, T& data);

	// destructor
	~gList() { }

	// pop item from front of list
	T pop_front()
	{
	Tnode<T> *p = (Tnode<T>*) glist_base::pop_front();

		return(p ? p->m_data: 0);
	}
	T pop_back()
	{
	Tnode<T> *p = (Tnode<T>*) glist_base::pop_back();

		return(p ? p->m_data: 0);
	}
};

// template based list iterator.
template <class T>
class gList_iter :public glist_base_iter {

public:
	// constructor
	// gList_iter() { }
	gList_iter(gList<T>& p):glist_base_iter(p)  { }
	//
	int begin(T&data)
	{
	Tnode<T> *p = (Tnode <T>*) glist_base_iter::begin();
	int rv;

		if(p) {
			data = p->m_data;
			rv =1;
		} else {
			rv = 0;
		}
		return(rv);
	}
	// reset iterator to end of list
	int end(T&data)
	{
	Tnode<T> *p = (Tnode <T>*) glist_base_iter::end();
	int rv;

		if(p) {
			data = p->m_data;
			rv =1;
		} else {
			rv = 0;
		}
		return(rv);
	}

	// get next item's data
	int next(T& data)
	{
	Tnode<T> *p = (Tnode <T>*) glist_base_iter::next();
	int rv;

		if(p) {
			data = p->m_data;
			rv =1;
		} else {
			rv = 0;
		}
		return(rv);
	}

	// get prev item's data
	int prev(T& data)
	{
	Tnode<T> *p = (Tnode <T>*) glist_base_iter::prev();
	int rv;

		if(p) {
			data = p->m_data;
			rv =1;
		} else {
			rv = 0;
		}
		return(rv);
	}

	// get current item's data
	int curr(T& data)
	{
	Tnode<T> *p = (Tnode <T>*) glist_base_iter::current();
	int rv;

		if(p) {
			data = p->m_data;
			rv =1;
		} else {
			rv = 0;
		}
		return(rv);
	}

};
#endif
