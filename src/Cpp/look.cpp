
//[LISTING FIVE]

// ---------- look.c

// A C++ program to demonstrate the use of the window library.
// This program lets you view a text file

#include <stdio.h>
#include <string.h>
#include <iostream.h>
#include <fstream.h>
#include <stdlib.h>
#include "window.hpp"

#define MAXLINES 200            // maximum number of text lines

static char *wtext[MAXLINES+1]; // pointers to text lines

// --- taken from BS; handles all free store (heap) exhaustions
void out_of_store(void);
typedef void (*PF)();
extern PF set_new_handler(PF);

main(int argc, char *argv[])
{
    set_new_handler(&out_of_store);
    if (argc > 1)   {
        // ---- open a full-screen window
	Window wnd(0,0,79,24,CYAN,BLUE);
        char ttl[80];
        // ------ put the file name in the title
        sprintf(ttl, "Viewing %s", argv[1]);
        wnd.title(ttl);
        filebuf buf;
        if (buf.open(argv[1], input))   {
            istream infile(&buf);
            int t = 0;
            // --- read the file and load the pointer array
            char bf[120], *cp = bf;
            while (t < MAXLINES && !infile.eof())   {
                infile.get(*cp);
		if (*cp != '\r')    {
                    if (*cp == '\n')    {
                        *cp = '\0';
                        wtext[t] = new char [strlen(bf)+1];
                        strcpy(wtext[t++], bf);
                        cp = bf;
                    }
                    else
                        cp++;
                }
            }
            wtext[t] = NULL;
            // ---- write all the text to the window
            wnd << wtext;
            // ---- a YesNo window
            YesNo yn("Continue");
            if (yn.answer)
                wnd.page();
            // ------ a Notice window
            Notice nt("All done.");
	}
        else
            // ------ error windows
            Error err("No such file");
    }
    else
        Error err("No file name specified");
}

// ----- the BS free-store exhaustion handler
void out_of_store(void)
{
    cerr << "operator new failed: out of store\n";
    exit(1);
}

