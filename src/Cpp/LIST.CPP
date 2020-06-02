#include <stdio.h>
#include <string.h>

#include <iostream.h>
#include "dstructs.h"

typedef int (*freefunc_t)(void *data);

class List {
	LLIST *first;
	LLIST *last;
	LLIST *current;
	freefunc_t free_func;

	public:
		List();         // constructor
		List(freefunc_t f);
		int insert_after(void *data);  // insert item after current
		int insert_before(void *data); // insert item before current
		int set_first();
		int set_last();
		int delete_item();
		void *get_current();
		void *get_next();
		void *get_prev();
		int delete_current(void **data);
		~List();


};


List::List()
{
	free_func = NULL;
	first = last = current = NULL;
}

List::List(freefunc_t f)
{
	free_func = f;
	first = last = current = NULL;
}

List::~List()
{

	set_first();
	while(!delete_item() )
	;
 	first = last = current = NULL;
}


int List::delete_item()
{
void *data;

	current = llist_delete_item(current,&data);
	if(data != NULL) {
		if (NULL != free_func) {
			free_func(data);
		}
	}
	cout << "deleting item\n";
	return(current ? 0 : 1);
}

int List::insert_after(void *data)
{
LLIST *old_curr = current;

	current = llist_insert_after(current,data);
	if(!first) {
		first = last = current;
	}
	if(old_curr == last) {
		last = current;
	}
	return(0);
}

int List::insert_before(void *data)
{
LLIST *old_curr = current;

	current = llist_insert_before(current,data);
	if(!first) {
		first = last = current;
	}
	if(old_curr == first) {
		first = current;
	}
	return(0);

}

void * List::get_current()
{
void *data;

	current = llist_get_curr(current,&data);
	return(current ? data : NULL);
}

void *List::get_next()
{
void *data;

	current = llist_get_next(current,&data);

	return(data);
}

void *List::get_prev()
{
void *data;

	current = llist_get_prev(current,&data);
	return(data);
}

int List::set_first()
{

	current = first;
	return(current ? 0 : 1);
}

int List::set_last()
{
	current = last;
	return(current ? 0 : 1);
}

int f(void *data)
{
	cout << "freeing " << (char *)data << '\n';
	delete data;
	return(0);
}
#define SIZE (5)

void main()
{
int i;
char temp[256], *p;
List l(f);

	cout << "\n\n\n\n";
	for(i = 0; i < SIZE; ++i) {
		cout << "inserting item" << i << '\n';
	       sprintf(temp, "Item # %d",i);
	       p = strdup(temp);
	       l.insert_after(p);
	}

	l.set_first();
	p = (char *) l.get_next();
	while(NULL != p ) {
		cout << p << '\n';
		p = (char *) l.get_next();
	}

}



