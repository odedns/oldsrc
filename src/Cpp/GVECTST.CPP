
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
#include <string.h>

#include "gvec.h"
#include "gvstack.h"

#define _GVSTACK_TEST
void main()
{

#ifdef _GVSTACK_TEST
gVStack <int> my(10);
int i,n;

	for(i=0; i < 5; ++i) {
		my.push(i);
	}
	cout << "iterating stack" << endl;
	gVStack_iterator <int> iter(my);
	while(!iter.is_empty()) {
		n = iter.next();
		cout << "n = " << n << endl;
	}

	cout << "popping elements from stack" << endl;
	while(my.nelem()) {
		n = my.pop();
		cout << "n = " << n << endl;
	}

#endif
#ifdef _GVECTOR_TEST
	gVector <int> v(10);
	int i;
	char temp[80];
	typedef char mystr[70];
	mystr my;
	gVector <char *> vs(10);

	for(i = 0; i <10; ++i) {
		v[i] = i;
	}
	for(i = 0; i <10; ++i) {
		sprintf(temp,"Line %d",i);
		vs[i] = strdup(temp);
	}


	for(i=0; i < v.size(); ++i) {
		cout << "v[" << i << "] =" << v[i] << endl;
	}
	for(i=0; i < vs.size(); ++i) {
		cout << "vs[" << i << "] =" << vs[i] << endl;
	}


#endif
}
