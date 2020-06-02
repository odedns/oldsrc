#include <stdio.h>
#include <stdlib.h>
#include <time.h>

/* this program demonstrates the quick sort algorithm in C !
   the algorithm splits the list into two parts and exchanges items
   until the lists are divided into a higher list and a lower list according
   to the middle value !
   the algorithm then calls itself recursivly and performs the same on
   the two sublists created and so on !!!
   the recursion terminates when the sublist contains 1 element !!      */



void create_list(int l[],int n); /* creates a random list */

void print_list(int l[],int n);  /* prints the list       */

void q_sort(int l[],int low,int high);  /* quick sorts the list */


#define  MAX 100

main()
{
int list[MAX] ,n = MAX;

    create_list(list,n);    /*  create a list with random numbers */

    printf("\n Sorting list with Quick Sort ! \n\n");
    q_sort(list,0,n - 1);     /* perform quick sort on the list      */

    print_list(list,n);   /*  print the list !!                  */

}       /** end main **/


void create_list(l,n)      /* creates a random list */
int l[],n;
{
int i;

    randomize();
    for(i = 0; i < n; ++i)
	l[i] = random(1222);
 }                       /* end create list */

void print_list(l,n)      /* prints the list */
int l[],n;
{
int i;
    for(i =0; i < n; ++i)
       printf("list[%d] = %d \n",i,l[i]);
}                                     /* end print list */


void q_sort(l,low,high)        /* quick sort   algorithm */
int l[] ,low ,high;

{                       /* begin quicksort    */
int t ,middle ,i,j;
i = low; j = high;

    middle = (low + high) / 2;  /* find a pivot value */
    do
    {      /* begin do while loop */

        while (l[i] < l[middle])  /* move up while items are lower than pivot */
               ++i;
        while (l[j] > l[middle]) /* move down while items are higher than pivot */
        --j;

        if(i <=j)      /* if we haven't pased the whole list exchange the  */
	{             /* two value which are out of order                   */
           t = l[i];
           l[i] = l[j];
           l[j] = t;
	 /* keep moving up with i and down with j after the exchange */
           ++i;
           --j;  }  /* end if */

    }    /* end do while loop */

    while(i <= j && i < middle && j > middle);

      /* repeat until i is smaller than j  */
     /* and both haven't passed middle */
    if (i < high) q_sort(l,i ,high);
   /* if i is smaller than the highest index call q_sort with i as low */

    if (j > low)  q_sort(l,low,j);
  /* if j is higher than low call recursivly with j as high */


 }      /* end q_sort */
