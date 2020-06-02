/*  This program will print the Ascii table for this computer in both
decimal and hex ;   the output will be written into the file "ascii.dat" */

#include <stdio.h>

main()
{                  /** begin program **/

int ascii , line,c ;
FILE *output;

/* open file for output  */
output = fopen("ascii.dat","w");

/* print the header  */
fprintf(output , "\n \t\t ASCII TABLE !! \n\n");

fprintf(output , "\n char \t decimal  \t hex \n");
fprintf(output , "================================\n");
/* set line counter */
line = 6;

/* the print loop ! */
for(ascii = 30; ascii < 255; ascii++ , line++)
{

if(line > 62)
{
 /* if end of page then  print the header after a form feed ! */
fprintf(output , "\f");
fprintf(output , "\n \t\t ASCII TABLE !! \n\n");
fprintf(output , " \n char \t decimal  \t hex \n");
fprintf(output , "================================\n");
line = 6;
}
fprintf(output , " %c \t %d \t \t %x \n", ascii ,ascii,ascii);
 }       /* end loop */

 fclose(output);   /* close output file */

}       /* end program */
