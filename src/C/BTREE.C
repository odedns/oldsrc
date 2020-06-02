/*-------------------------------------------------------------------------*/
/*   binnary tree demonstration  program !   date written - 26-08-93       */
/*-------------------------------------------------------------------------
     This program demonsrates the use of the binnary tree data structure
     to sort and count words from standard input !
     The program reads in words from stdin , installs them in a binnary tree
     structure , then prints the words sorted and the number of occurences
     for each words !!

  -------------------------------------------------------------------------*/

#include <stdio.h>
#include <alloc.h>
#include <string.h>

#define MAXLEN  80

  /* binnary tree structure  */

 typedef struct Bindery {
			 struct Bindery *left;
			 struct Bindery *right;
			 }BINDS;
			 /* the bind structure */

 typedef struct {
		 char *word;
		 long count;
		 BINDS bind;
		}BTREE , *pBTREE;


/* allocate memory for tree structure   */

  BTREE *talloc(BTREE *p)
	{
	return(p = (BTREE *) malloc(sizeof(BTREE)));
	}


  /* print the sorted tree     */

 void print_tree(BTREE *p)
      {
       if(p->bind.left != NULL)
	 print_tree(p->bind.left);

       printf("word = %s count = %ld \n" , p->word , p->count);

       if(p->bind.right != NULL)
	  print_tree(p->bind.right);

	}   /* end print_tree  */



   BTREE *add_node( BTREE *p , char *word)
	 {
	   if(p == NULL)   /* empty list */
	     {
	      p = talloc(p);            /* allocate mem & install word */
	      p->word = strdup(word);
	      p->count = 1;

	      p->bind.right = NULL;
	      p->bind.left = NULL;

	      return(p);

	     }

	    if(!strcmp(p->word , word))  /* strings are equal */
		  p->count++;
	      else if(0 < strcmp(p->word , word))
		      p->bind.left =  add_node(p->bind.left , word);
			/* search left side */
		   else
		     p->bind.right = add_node(p->bind.right , word);
		       /* search right side */

		     return(p);

		 } /* end add_node */


	     char *get_word(char *s , int len)
		  {
		  /* reads a word from stdin */
		  char c , *w = s;


		  while((c=getchar()) == ' ' || c == '\n' || c == '\t')
		  ;     /* skip whitespaces */


	   while(c != EOF && c != ' ' && c != '\n' && c != '\t')
			{
			 *s++ = c;
			  --len;
			  if(!len)
			    break;
			    c = getchar();
			 }
			 *s = '\0';
		 return((c == EOF) ? NULL : w);

		 }  /* end get_word */



      main()          /**** begin btree program  ****/
      {
      BTREE  *root = NULL;
      char *word;

      word = malloc(80);

      /* loop until EOF  */

      while(( word = get_word(word , MAXLEN)) != NULL)

	  /* add the word to the tree structure  */
	   root = add_node(root , word);

	   /* print the tree */

	   if(root != NULL)
	      print_tree(root);
	   else
	      fprintf(stderr , "Tree is Empty !! \n\n");

	   }    /**** end btree program !! ****/





