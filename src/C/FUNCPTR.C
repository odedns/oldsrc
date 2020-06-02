#include <stdio.h>

int a[] = { 1 , 2 ,3 , 4 ,0 };
int b[] = { 5 , 6 ,7 , 8 ,0 };

int *Array[] = { a , b ,NULL };

void f1();
void f2();

typedef void (*func_t)(void);

static func_t func_array[] = { f1 , f2 , NULL};

main()
{

int **p;
func_t *f;

    p = Array;
    puts("\n\n\n\n\n\n");
    while(*p) {
	    while(**p ) {
		printf("**p = %d\n" , **p);
	       ++*p;
	   }
	   ++p;
    }

    f = func_array;

    while(*f)  {
       (*f)();
       ++f;
    }

}   /** main **/

void f1()
{
   puts("Func f1\n");
}


void f2()
{

   puts("Func f2\n");
}