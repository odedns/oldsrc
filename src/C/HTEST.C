
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define MAX 10

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


int scmp(void *s1, void *s2)
{
	return(strcmp(s1,s2));
}

#define SIZE 6

main()
{
int i;
char list[SIZE][4] = { "eee", "ddd", "ccc", "bbb", "aaa","h6h" };


	heap_sort((void *)list,SIZE,sizeof(list[0]),scmp);
	for(i = 0; i < SIZE; ++i) {
	  printf("list[%d] = %s\n",i,list[i]);
       }
}


