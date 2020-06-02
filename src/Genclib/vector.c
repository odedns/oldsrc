

#include <stdio.h>
#include <stdlib.h>

#include "dstructs.h"

int vector_size(VECTOR *vec)
{
	return(vec->size);
}

VECTOR *vector_create(int size)
{
	int i;
	VECTOR *vec = (VECTOR *)malloc(sizeof(VECTOR));
	if(NULL == vec) {
		return(NULL);
	}

	/* use default size */
	if(size <= 0) {
		size = VEC_DEF_SIZE;
	}
	vec->v = (void **)calloc(size,sizeof(void *));
	vec->size = size;
	vec->nelem = 0;
	vec->threshold = VEC_DEF_SIZE;
	for(i=0; i < vec->size; ++i) {
		vec->v[i] = NULL;
	}

	return(vec);
}


void vector_add(VECTOR *vec, void *object)
{
	int i=0;
	
	printf("nelem = %d\t size = %d\n", vec->nelem,vec->size);

	if(vec->nelem == vec->size) {

		vec->size += vec->threshold;
		vec->v = (void **) realloc(vec->v, 
				sizeof(void *) * vec->size);
	}
		
	i = vec->nelem;
	printf("putting obj at %d\n",i);
	vec->v[i] = object;
	vec->nelem++;
}


void *vector_remove(VECTOR *vec)
{
	void *p;
	
	vec->nelem--;
	p = vec->v[vec->nelem];
	vec->v[vec->nelem] = NULL;
	return(p);
}

void *vector_remove_at(VECTOR *vec, int index)
{
	void *p;
	
	if(index < 0 || index >= vec->nelem) {
		return(NULL);
	}

	--vec->nelem;
	p = vec->v[index];
	vec->v[index] = NULL;
	return(p);
}


void *vector_get_at(VECTOR *vec, int index)
{
	void *p;
	if(index < 0 || index >= vec->nelem) {
		return(NULL);
	}
	p = vec->v[index];
	return(p);
}

void vector_set_at(VECTOR *vec, void *object, int index)
{
	if(index < 0 || index >= vec->nelem) {
		return;
	}
	++vec->nelem;
	vec->v[index] = object;
}



#ifdef _TEST

#define SIZE (19)
void main()
{
	int i;
	int n[SIZE];
	VECTOR *v;
	int *pn;


	v = vector_create(-1);

	if (v == NULL) {
		printf("v = NULL\n");
		exit(1);
	}
	for(i = 0; i < SIZE; ++i) {
		n[i] = i;
		vector_add(v,&n[i]);
		printf("adding %d\n",n[i]);
	}

	for(i=0; i < SIZE; ++i) {
		pn = (int *)vector_remove(v);
		if(NULL == pn) {
			printf("got null\n");
		} else {
			printf("removing %d\n",*pn);
		}
	}
}
#endif
