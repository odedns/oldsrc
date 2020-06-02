
#include <stdio.h>
#include <signal.h>

void Catcher()
{
   printf("Got SIGINT\n");
   exit(1);
}

int main(void)
{

int c;

   signal(SIGINT,Catcher);
   while(EOF != (c=getchar())) 
   ;
   
  return 0;
}

