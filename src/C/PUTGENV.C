
/* ------------ [ SETTING GLOBAL ENVIRONMENT VARIABLES ]------------- *\ 
 �                                                                    �
 �                                                                    �
 �  The Environment is a section of Memory ( up to 32K in size )      �
 �  which contains a series ( or a SET ) of 'Environment Variables'   �
 �  in the format:                                                    �
 �              VariableName=VariableText                             �
 �                                                                    �
 �  Both the VariableName and VariableText are ASCIIZ                 �
 �  (NULL-terminated) strings.  Environment Variables are managed and �
 �  manipulated via the DOS 'SET' command ( See your DOS manual for   �
 �  more details ).                                                   �
 �                                                                    �
 �  Before DOS executes an application ( or when a parent program     �
 �  calls the DOS EXEC function - INT 21,4B - with the Parent's Env.  �
 �  Segment set to zero ), DOS allocates a block of memory to which   �
 �  it copies its ( or the parent program's ) current Environment.    �
 �  The Segment value of the allocated block ( Environment Pointer )  �
 �  is stored in the application's PSP ( at Offset 2Ch ).  Therefore  �
 �  each application has a local copy of it's parent Environment.     �
 �  The primary SHELL ( which may be set by the SHELL command in      �
 �  CONFIG.SYS - usually COMMAND.COM ) creates the *MASTER*           �
 �  Environment at boot time.                                         �
 �                                                                    �
 �  The size of Memory Blocks allocated for Local Copies of the       �
 �  Environment are just big enough to accomodate the variables of    �
 �  the parent's Environment.  The Borland C/C++ compilers, however,  �
 �  provide the putenv() function which may 'realloc' the Local       �
 �  Environment Memory Block to make room for additional Environment  �
 �  Variables.  Local Environment copies, however, are transient: the �
 �  Memory Block of the Local Copy is freed when the application      �
 �  terminates.   This implies that changes made to one's local copy  �
 �  of the Environment are only active during one's lifetime.  An     �
 �  application's changes to its Local Copy can be inherited by a     �
 �  child of the application.  Therefore "you can leave a legacy for  �
 �  your children but you do not have access to your parent's         �
 �  belongings !".                                                    �
 �                                                                    �
 �                                                                    �
 �  To SET a permanent/persistent Environment Variable, one must      �
 �  modify the *MASTER* Environment.   The current versions of DOS do �
 �  not provide any documented interfaces to modify the *MASTER*      �
 �  Environment or even detect its location.  Various methods have    �
 �  been suggested and used for finding the Master Environment;  This �
 �  example uses a method which relies on the following:              �
 �  Since DOS creates an Environment for every application, the Env.  �
 �  segment always preceed the application which owns the Environment.�
 �  However, this logic does not apply for the Env. Seg. created and  �
 �  owned by DOS itself: Since DOS creates the Env., the owner in this�
 �  case is at a lower address than its environment.  Scanning for the�
 �  Process whose Env. Seg. is higher that the Seg. of the application�
 �  allows us to determine the Segment of the Master Environment.     �
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
 �                                                                    �
 � The following section of the example determines the location of the�
 � Master Environment.   The setting/clearing of an Environment Var.  �
 � is separated from the Env. Seg. detection routines so that other   �
 � methods for locating the Master Environment can be easily          �
 � implemented.                                                       �
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
 �                                                                    �
 � The following function modifies the Master Environment...   The    �
 � Environment variable name as well as its value ( Text ) are the two�
 � variables required.   If the second variable is NULL, the variable �
 � Name ( if currently SET ) will be cleared from the                 �
 � Master Environment.                                                �
 �                                                                    �
 � The return values are :                                            �
 �     0 :  Successful                                                �
 �    -1 :  General Error                                             �
 �    -2 :  Insufficient space in Environment for New Variable        �
 �                                                                    �
 � NOTE:  Changes made to the Master Environment do *NOT* affect the  �
 �        application's local Environment.  Use the Borland C/C++     �
 �        putenv() function to modify the Local Environment.          �
 �                                                                    �
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
