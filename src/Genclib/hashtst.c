#include <stdio.h>
#include <string.h>
#include "dstructs.h"

#define SIZE (25)
void main()
{
HASHTBL *pHash;
int i;
char *data ,*key, temp[256], temp2[256];


	pHash = hash_tbl_create(20);
	puts("inserting");
	for(i = 0; i < SIZE; ++i) {
		sprintf(temp, "data[%d]",i);
		data = strdup(temp);
		sprintf(temp2, "key %d",i);
		key = strdup(temp2);
		hash_tbl_insert(pHash, key, data);
	}

	puts("iterating");
	hash_tbl_iterate(pHash,ITER_BEGIN);
	data = hash_tbl_iterate(pHash,ITER_NEXT);
	while(NULL != data) {
		printf("data :%s\n",data);
		data = hash_tbl_iterate(pHash,ITER_NEXT);
	}


	puts("getting");
	for(i = 0; i < SIZE; ++i) {
		sprintf(temp2, "key %d",i);
		hash_tbl_get(pHash, temp2, &data);
		printf("data :%s\n",data);
	}



}
