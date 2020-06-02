#include <stdlib.h>
#include <stdio.h>

#include "llist.h"


 LLIST *create_list(LLIST *p , int nelem)
 {
 LLIST *head;
 int i;
 char *s;

    for(i = 0 ; i <nelem; ++i)
    {
	s = (char *) malloc(40);

	sprintf(s ,"This is Item # %d " , i);

	p = llist_insert_after(p , (char *) s);
	if(i == 0)
	  head = p;
/*	printf("%s \n" ,p->data); */
    }

  return(head);
 }

 void print_list(LLIST *p)
 {

     puts("\n\n\n\n");

     while(p != NULL) {
     
     printf("%s \n" , (char *)p->data);
     p = llist_get_next(p);

    }

 }



 void free_list(LLIST *p)
 {
   char *s;

     while(p != NULL) {
	 p = llist_delete_item(p,(void **)&s);
	 printf("free = %s \n" , s);
	 free(s);

     
    }

 }


 main()
 {
 LLIST *ll_p = NULL , *ll_head;

 puts(" creating list \n\n");
 ll_p = create_list(ll_p , 10);

 puts("\n printing list ! \n\n");
 print_list(ll_p);

 ll_head = llist_get_first(ll_p);
 getchar();
 puts("\n\n\n\n");
 free_list(ll_head);

 }

