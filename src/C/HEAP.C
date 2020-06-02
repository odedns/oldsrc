#include <stdio.h>
#include <stdlib.h>


void print_list(int *v, int size)
{
int i;

	puts("************\n\n\n");
	for(i=0; i < size; ++i) {
		printf("v[%d] = %d\n",i,v[i]);
	}
}

void create_list(int *v, int size)
{
int i;

	randomize();
	for(i=0; i < size; ++i) {
		v[i] = random(100);
//		v[i] = i;

	}
}

void heapify(int *v, int i, int n)
{
int max, j;
int loopFlag;

	max = v[i];
	j = i * 2;

	loopFlag = (j <= n);

	while(loopFlag) {
		if(j < n) {
			if(v[j+1] > v[j]) {
				++j;
			}
		}
		if(v[j] <= max) {
			loopFlag = 0;
		} else {
			v[i] = v[j];
			i = j;
			j = 2 * i;
			loopFlag = (j <= n);
		}
	} /* while */
		/* final placement of root key */
	v[i] = max;
}

void heap_sort(int *v, int n)
{
int i, t;
int size = n - 1;

	for(i = (size /2 ); i >= 0; --i) {
		printf("calling heapify i = %d size = %d\n",
		       i, size);
		heapify(v,i,size);
	}
	print_list(v,n);

	t = 1;
	for(i = t; i < size; ++i) {
		heapify(v,i,size);
		++t;
	}


	for(i=size-1; i >= 0; --i) {

		t = v[i+1];
		v[i+1] = v[0];
		v[0] = t;
		heapify(v,0,i);

	}

}
#define SIZE (15)

void main()
{
int v[SIZE];

	create_list(v,SIZE);
	print_list(v,SIZE);
	heap_sort(v,SIZE);
print_list(v,SIZE);
}