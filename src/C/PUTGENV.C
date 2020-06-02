
/* ------------ [ SETTING GLOBAL ENVIRONMENT VARIABLES ]------------- *\ 
 ³                                                                    ³
 ³                                                                    ³
 ³  The Environment is a section of Memory ( up to 32K in size )      ³
 ³  which contains a series ( or a SET ) of 'Environment Variables'   ³
 ³  in the format:                                                    ³
 ³              VariableName=VariableText                             ³
 ³                                                                    ³
 ³  Both the VariableName and VariableText are ASCIIZ                 ³
 ³  (NULL-terminated) strings.  Environment Variables are managed and ³
 ³  manipulated via the DOS 'SET' command ( See your DOS manual for   ³
 ³  more details ).                                                   ³
 ³                                                                    ³
 ³  Before DOS executes an application ( or when a parent program     ³
 ³  calls the DOS EXEC function - INT 21,4B - with the Parent's Env.  ³
 ³  Segment set to zero ), DOS allocates a block of memory to which   ³
 ³  it copies its ( or the parent program's ) current Environment.    ³
 ³  The Segment value of the allocated block ( Environment Pointer )  ³
 ³  is stored in the application's PSP ( at Offset 2Ch ).  Therefore  ³
 ³  each application has a local copy of it's parent Environment.     ³
 ³  The primary SHELL ( which may be set by the SHELL command in      ³
 ³  CONFIG.SYS - usually COMMAND.COM ) creates the *MASTER*           ³
 ³  Environment at boot time.                                         ³
 ³                                                                    ³
 ³  The size of Memory Blocks allocated for Local Copies of the       ³
 ³  Environment are just big enough to accomodate the variables of    ³
 ³  the parent's Environment.  The Borland C/C++ compilers, however,  ³
 ³  provide the putenv() function which may 'realloc' the Local       ³
 ³  Environment Memory Block to make room for additional Environment  ³
 ³  Variables.  Local Environment copies, however, are transient: the ³
 ³  Memory Block of the Local Copy is freed when the application      ³
 ³  terminates.   This implies that changes made to one's local copy  ³
 ³  of the Environment are only active during one's lifetime.  An     ³
 ³  application's changes to its Local Copy can be inherited by a     ³
 ³  child of the application.  Therefore "you can leave a legacy for  ³
 ³  your children but you do not have access to your parent's         ³
 ³  belongings !".                                                    ³
 ³                                                                    ³
 ³                                                                    ³
 ³  To SET a permanent/persistent Environment Variable, one must      ³
 ³  modify the *MASTER* Environment.   The current versions of DOS do ³
 ³  not provide any documented interfaces to modify the *MASTER*      ³
 ³  Environment or even detect its location.  Various methods have    ³
 ³  been suggested and used for finding the Master Environment;  This ³
 ³  example uses a method which relies on the following:              ³
 ³  Since DOS creates an Environment for every application, the Env.  ³
 ³  segment always preceed the application which owns the Environment.³
 ³  However, this logic does not apply for the Env. Seg. created and  ³
 ³  owned by DOS itself: Since DOS creates the Env., the owner in this³
 ³  case is at a lower address than its environment.  Scanning for the³
 ³  Process whose Env. Seg. is higher that the Seg. of the application³
 ³  allows us to determine the Segment of the Master Environment.     ³
                                                                  B.B.
\* ------------------------------------------------------------------ */

#include <stdio.h>
#include <stdlib.h>
#include <dos.h>
#include <bios.h>
#include <dir.h>
#include <string.h>
#include <mem.h>


int  _fstrncmp( const char far * s1, const char far * s2, size_t maxlen )
{
    while(( *s1 || *s2 ) && maxlen )
    {
        if ( *s1 > *s2 )
            return( 1 );
        if ( *s1 < *s2 )
            return( -1 );
        s1++;
        s2++;
        maxlen--;
     }
     return( 0 );
}
size_t  _fstrlen( char far * s )
{
     size_t i = 0;
     while( *s )
     {
        i++;
        s++;
     }
     return( i );
}
char far * _fstrcpy( char far * d, char far * s )
{
    char far * r = d;
    while( *s )
    {
        *d = *s;
         s++;
         d++;
    }
    return( r );
}
char far * _fstrcat( char far * d, char far * s )
{
    char far * r = d;
    while( *d )
        d++;
    while( *s )
    {
       *d = *s;
        s++;
        d++;
    }
    *d = '\0';
    return( r );
}



/* ------------ [ DETECTING LOCATION OF MASTER ENV...  ]------------- *\ 
 ³                                                                    ³
 ³ The following section of the example determines the location of the³
 ³ Master Environment.   The setting/clearing of an Environment Var.  ³
 ³ is separated from the Env. Seg. detection routines so that other   ³
 ³ methods for locating the Master Environment can be easily          ³
 ³ implemented.                                                       ³
\* ------------------------------------------------------------------ */

#define envSeg( p ) (*((unsigned short far *)MK_FP( p->arenaOwner, 0x2C )))
#define parSeg( p ) (*((unsigned short far *)MK_FP( p->arenaOwner, 0x16 )))

struct  ARENA
{
    unsigned char   arenaSignature;
    unsigned short  arenaOwner;
    unsigned short  arenaSize;
    unsigned char   reserved[3];
    unsigned char   areneName[8];
};



static struct ARENA far * GetFirstArenaPtr()
{
    _AH = 0x52;
    geninterrupt( 0x21 );
    return ((struct ARENA far *)
        MK_FP( *(unsigned short _es *)(_BX-2), 0 ));
}



static void far * GetMasterEnvPtr()
{
    struct ARENA far * aPtr;
    
    if ( ( aPtr = GetFirstArenaPtr() )  !=  NULL )
    {
        while( aPtr->arenaSignature == 0x4D )
        {
            if ( aPtr->arenaOwner == ( FP_SEG( aPtr ) + 1 ) )
                if ( envSeg( aPtr ) > ( aPtr->arenaOwner  ) )
                    if ( parSeg( aPtr ) == ( aPtr->arenaOwner ) )
                        return( MK_FP( envSeg( aPtr ), 0x00 ) );
            aPtr = ( struct ARENA far * )
                     MK_FP( FP_SEG( aPtr ) + aPtr->arenaSize + 1, 0);
        }
    }
    return( NULL );
}
                          


/* ------------ [ MODIFYING THE MASTER ENVIRONMENT...  ] ------------ *\
 ³                                                                    ³
 ³ The following function modifies the Master Environment...   The    ³
 ³ Environment variable name as well as its value ( Text ) are the two³
 ³ variables required.   If the second variable is NULL, the variable ³
 ³ Name ( if currently SET ) will be cleared from the                 ³
 ³ Master Environment.                                                ³
 ³                                                                    ³
 ³ The return values are :                                            ³
 ³     0 :  Successful                                                ³
 ³    -1 :  General Error                                             ³
 ³    -2 :  Insufficient space in Environment for New Variable        ³
 ³                                                                    ³
 ³ NOTE:  Changes made to the Master Environment do *NOT* affect the  ³
 ³        application's local Environment.  Use the Borland C/C++     ³
 ³        putenv() function to modify the Local Environment.          ³
 ³                                                                    ³
\* ------------------------------------------------------------------ */
int GlobalPutEnv( char *varName, char *varText )
{
    void far * envPtr = NULL;

    if ( ( envPtr = GetMasterEnvPtr() ) == NULL )
        return( -1 );
    else
    {
        char far *eptr = ( char far * )envPtr;
        char far *p    = ( char far * )envPtr;
        size_t varLen  = strlen( varName );
        size_t reqLen  = varLen + strlen( varText ) + 2;
        size_t envSize = ((( struct ARENA far * )
                          MK_FP( FP_SEG( envPtr )-1, 0 ))->arenaSize << 4 );

        while( *( unsigned short far * )eptr )
        {
            eptr++;
            if ( FP_OFF( eptr ) == 0x7FFF )
                return( -1 );
        }

        if ( ( FP_OFF( eptr ) + reqLen ) >= envSize )
            return( -2 );

        strupr( varName );
        strupr( varText );

        while ( p < eptr )
        {
            if (( _fstrncmp( varName, p, varLen ) == 0 )
                    &&  ( *( p + varLen ) == '=' ) )
            {
                char  far *q = p;
                q += _fstrlen( q );

                if( *( unsigned short far * )q == 0 )
                {
                    p--;
                    break;
                }
                q++;
                movedata( FP_SEG( q ), FP_OFF( q ),
                          FP_SEG( p ), FP_OFF( p ),
                          FP_OFF( eptr ) - FP_OFF( q ) + 2 );
                while( *( unsigned short far * )p )
                    p++;
                break;
            }
            while ( *p ) p++;
            if ( *( unsigned short far * )p )
                p++;
        }

        if ( varText == NULL )
        {
           *( unsigned short far * )p = 0;
            return( 0 );
        }

        p++;
        _fstrcpy( p, varName );
        _fstrcat( p, "=" );
        _fstrcat( p, varText );
        p += reqLen;
       *p  = '\0';
    }
    return( 0 );
}




int main( int argc, char *argv[] )
{
    char far * gEnvPtr = ( char far * )GetMasterEnvPtr();

    if ( gEnvPtr == NULL )
        return( 2 );

    printf( "The Master EnvPtr is @%Fp\n\n", gEnvPtr );

    if ( argc == 1 )
    {
        printf( "USAGE: %s  varName [ varText ] \n"
                "       If varText is not provided, varName ( if SET ) "
                "is cleared\n\n", ( argv[0] ? argv[0] : "Putgenv" ));
        return( 1 );
    }

    if ( argc == 2 )
        GlobalPutEnv( argv[1], NULL );

    if ( argc >= 3 )
        GlobalPutEnv( argv[1], argv[2] );

    puts( "\n<<< Current Environment >>>");
    while( *( unsigned short far * )gEnvPtr )
    {
        if ( *gEnvPtr != '\0' )
            putchar( *gEnvPtr );
        else
            putchar( '\n' );
        gEnvPtr++;
    }
    puts( "\n<<< >>>>\n" );
    return( 0 );
}
