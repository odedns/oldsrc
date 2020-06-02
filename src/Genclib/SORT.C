
/*-----------------------------------------------------------------*\
 * Generic shell sort function. 								   *
 *																   *
 *																   *
 *																   *
\*-----------------------------------------------------------------*/


#include <stdio.h>
#include <stdlib.h>

void swap(void *x , void *y, int size )
{
char t;
	  while(size--) {
		t = *(char *)x;
		*(char *)x = *(char*)y;
		*(char *)y = t;
		++(char *)x;
		++(char *)y;
	   }
}


void shell_sort(void *pVec, int size , int width,
                int (*fcmp)(void *p1, void *p2) )
{
int gap,i,j;
char *v = pVec;

	for(gap = size/2; gap > 0; gap/=2) {
		for(i = gap; i < size; ++i) {
		     for(j=i-gap; j>= 0 &&
		     (*fcmp)((v+j*width),(v+width*(j+gap))) > 0; j-=gap) {
				swap((v+j*width),(v+width*(j+gap)),width);
		     }
		 }
	}
}


/*-----------------------------------------------------------*\
 *                                                           *
 * Generic Heap Sort Function.                               *
 *                                                           *
 * Date : 01/07/95                                           *
 *                                                           *
 *                                                           *
 *                                                           *
 *                                                           *
 *                                                           *
\*-----------------------------------------------------------*/
void heapify(void *pVec, int i, int n, int width,
	     int (*fcmp)(void *p1, void *p2) )
{
int j, max;
char *vec = pVec;

    j = i * 2;

    if(j <= n && 0 < (*fcmp)((vec+width*j) ,(vec+width*i)))
		max = j;
	else
		max = i;

    if(j+1 <= n && 0 < (*fcmp)( (vec+(width *(j+1))) ,(vec+width*max)))
		max = j+1;

    if(max != i) {
		swap((vec+width*i),(vec+width*max),width);
		heapify(vec,max,n,width,fcmp);
    }
}

void heap_sort(void *pVec , int size,int width,
	      int (*fcmp)(void *p1, void *p2) )
{
int i,n = size-1;
char *vec = pVec;

	for(i =size/2; i >=0; --i) {
		heapify(vec,i,size,width,fcmp);
	}
	for(i = size-1; i > 0; --i) {
		swap((vec+width*i),(vec),width);
		n--;
		heapify(vec,0,n,width,fcmp);
	}
}
