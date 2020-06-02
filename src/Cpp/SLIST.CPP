#include <iostream.h>

struct slink {
	slink *next;
	slink() { next = 0;}
	slink(slink *p) { next = p; }
};

class slist_base {
	//
	slink *last;
	slink *first;
public:
	slist_base() { last = 0;}
	slist_base(slink *p) { last = p->next =p;}
	void insert(slink *);  // add at head of list
	void append(slink *);  // add at end of list
	slink *get();

};

void slist_base::insert(slink *a)
{
	if(last) {
		a->next = last->next;
	} else  {
		last = a;
	}

		last->next = a;
}

void slist_base::append(slink *p)
{
	if(last) {
		p->next = last->next;
		last = last->next = p;
	} else {
		last = p->next = p;
	}
}

slink *slist_base::get()
{
	if(last == 0) cout << "get from empty list" << endl;
	slink *f = last->next;
	if(f == last)
		last = 0;
	else
		last->next = f->next;
	return(f);
}

template <class T>
struct Tlink : public slink {
	T info;
	Tlink(const T&a) : info(a) { info = a; }
};

template <class T>
class Slist : public slist_base{

public:
	void insert(const T& a)
	{ slist_base::insert(new Tlink<T>(a)); }
	void append(const T& a)
	{ slist_base::append(new Tlink<T>(a)); }
	T get();
	// ..
};

template <class T>
T Slist<T>::get()
{
     Tlink<T> *lnk = (Tlink<T>*) slist_base::get();
     T i = lnk->info;
     delete lnk;
     return(i);
}

void main()
{
Slist <int> lst1;

	lst1.insert(1);
	int n = lst1.get();
	cout << "n = " << n;

}