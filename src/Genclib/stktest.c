
#include <stdio.h>
#include <stdlib.h>
#include "dstructs.h"

void main()
{
int i = 0;
char *s;
STACK *pStack;

	pStack = stack_create();

	puts("\n\n\n\n");

	for(i = 0; i < 5; ++i) {
		s = (char*)malloc(50);
		sprintf(s,"This is item %d",i);
		stack_push(pStack,s);
	}
	printf("items = %d\n",stack_items(pStack));
	while(!stack_empty(pStack)) {
		stack_pop(pStack,(void **)&s);
		printf("s = %s\n",s);
	}
}
