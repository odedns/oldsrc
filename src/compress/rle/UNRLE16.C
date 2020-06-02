/* Run Length Decoder program
 * decodes system.rle file that uses 16 bit headers.
 *
 * written 1991 by Shaun Case in Borland C++ 2.0
 * This program and its source code are public domain.
 *
 * You can't afford not to make the left decision.
 */


#include <stdio.h>
#include <string.h>
#include "rle16.h"

int main(int argc, char **argv)
{
    register int byte;
    register unsigned short i;
    register unsigned short length;
    int packet_hdr;
    
    FILE *infile, *outfile;

    char orig_filename[14]; /* original filename */
    char *infile_name;
    char scratch_space[134];

    if (argc != 2)
    {
        puts("Usege: unrle16 filename");
        return 1;
    }
    puts("unlre16   by Shaun Case 1991  public domain");

    infile_name = argv[1];

    if ((infile=fopen(infile_name, "rb")) == NULL)
    {
        strcpy(scratch_space, "Unable to open ");
        strcat(scratch_space, infile_name);
        puts(scratch_space);
        return 1;
    }

    for (i = 0; i < 13; i++)   /* get original filename */
        if ((orig_filename[i] = fgetc(infile)) == EOF)
        {
            puts("Error reading original filename from input file.");
            return 1;
        }

    if ((outfile=fopen(orig_filename, "wb")) == NULL)
    {
        strcpy(scratch_space, "Unable to open ");
        strcat(scratch_space, orig_filename);
        puts(scratch_space);
        return 1;
    }


    while (!feof(infile))
    {
        packet_hdr = fgetc(infile);         /* get lo byte   */

        if (feof(infile))
            continue;

        packet_hdr |= (((short)fgetc(infile)) << 8) ;  /* get high byte */

        if (feof(infile))
            continue;


        length = MAX_LEN & packet_hdr;

        if (packet_hdr & RUN)  /* if it's a run... */
        {
            byte = fgetc(infile);

            for (i = 0; i < length; i++)
                fputc(byte, outfile);
        }

        else /* it's a sequence */

            for (i = 0; i < length; i++)
                fputc(fgetc(infile), outfile);

    }
    fclose(infile);
    fclose(outfile);
    return 0;
}

