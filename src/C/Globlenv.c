Setting Permanent/Persistent Environment Variables.

 How can a program set a environment variable which remains  
 active after terminating the application. 


/* ------------ [ SETTING GLOBAL environment VARIABLES ]------------- *\ 
 �                                                                    �
 �                                                                    �
 �  The eNVIRONMENT is a section of Memory ( up to 32K in size )      �
 �  which contains a series ( or a SET ) of 'Environment Variables'   �
 �  in the format:                                                    �
 �              VariableName=VariableText                             �
 �                                                                    �
 �  Both the VariableName and VariableText are ASCIIZ                 �
 �  (NULL-terminated) strings.  eNVIRONMENT Variables are managed and �
 �  manipulated via the DOS 'SET' command ( See your DOS manual for   �
 �  more details ).                                                   �
 �                                                                    �
 �  Before DOS executes an application ( or when a PARENT program     �
 �  calls the DOS EXEC function - INT 21,4B - with the Parent's Env.  �
 �  Segment set to zero ), DOS allocates a block of memory to which   �
 �  it copies its ( or the PARENT program's ) current eNVIRONMENT.    �
 �  The Segment value of the allocated block ( eNVIRONMENT Pointer )  �
 �  is stored in the application's PSP ( at Offset 2Ch ).  Therefore  �
 �  each application has a local copy of it's PARENT eNVIRONMENT.     �
 �  The primary SHELL ( which may be set by the SHELL command in      �
 �  CONFIG.SYS - usually COMMAND.COM ) creates the *MASTER*           �
 �  eNVIRONMENT at boot time.                                         �
 �                                                                    �
 �  The size of Memory Blocks allocated for Local Copies of the       �
 �  eNVIRONMENT are just big enough to accomodate the variables of    �
 �  the parent's eNVIRONMENT.  The Borland C/C++ compilers, however,  �
 �  provide the putenv() function which may 'realloc' the Local       �
 �  eNVIRONMENT Memory Block to make room for additional eNVIRONMENT  �
 �  Variables.  Local eNVIRONMENT copies, however, are transient: the �
 �  Memory Block of the Local Copy is freed when the application      �
 �  terminates.   This implies that changes made to one's local copy  �
 �  of the eNVIRONMENT are only active during one's lifetime.  An     �
 �  application's changes to its Local Copy can be inherited by a     �
 �  child of the application.  Therefore "you can leave a legacy for  �
 �  your children but you can't touch your PARENT !".                 �
 �                                                                    �
 �                                                                    �
 �  To SET a permanent/persistent eNVIRONMENT Variable, one must      �
 �  modify the *MASTER* eNVIRONMENT.   The current versions of DOS do �
 �  not provide any documented interfaces to modify the *MASTER*      �
 �  eNVIRONMENT or even detect its location.  Various methods have    �
 �  been suggested and used for finding the Master eNVIRONMENT;  This �
 �  example uses a method which relies on the following:              �
 �  Since DOS creates an eNVIRONMENT for every application, the Env.  �
 �  segment always preceed the application which owns the eNVIRONMENT.�
 �  However, this logic does not apply for the Env. Seg. created and  �
 �  owned by DOS itself: Since DOS creates the Env., the owner in this�
 �  case is at a lower address than its ENVIRONMENT.  Scanning for the�
 �  Process whose Env. Seg. is higher that the Seg. of the application�
 �  allows us to determine the Segment of the Master eNVIRONMENT.     �
                                                                  B.B.
\* ------------------------------------------------------------------ */

#include <stdio.h>
#include <stdlib.h>
#include <dos.h>
#include <bios.h>
#include <dir.h>
#include <string.h>
#include <mem.h>



/* ------------ [ DETECTING LOCATION OF MASTER ENV...  ]------------- *\ 
 �                                                                    �
 � The following section of the example determines the location of the�
 � Master eNVIRONMENT.   The setting/clearing of an eNVIRONMENT Var.  �
 � is separated from the Env. Seg. detection routines so that other   �
 � methods for locating the Master eNVIRONMENT can be easily          �
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
                          



/* ------------ [ MODIFYING THE MASTER environment...  ] ------------ *\
 �                                                                    �
 � The following function modifies the Master eNVIRONMENT...   The    �
 � eNVIRONMENT variable name as well as its value ( Text ) are the two�
 � variables required.   If the second variable is NULL, the variable �
 � Name ( if currently SET ) will be cleared from the                 �
 � Master eNVIRONMENT.                                                �
 �                                                                    �
 � The return values are :                                            �
 �     0 :  Successful                                                �
 �    -1 :  General Error                                             �
 �    -2 :  Insufficient space in eNVIRONMENT for New Variable        �
 �                                                                    �
 � NOTE:  Changes made to the Master eNVIRONMENT do *NOT* affect the  �
 �        application's local eNVIRONMENT.  Use the Borland C/C++     �
 �        putenv() function to modify the Local eNVIRONMENT.          �
 �                                                                    �
\* ------------------------------------------------------------------ */
int GlobalPutEnv( char *varName, char *varText )
{
    void far * envPtr = NULL;

    
    if ( ( envPtr = GetMasterEnvPtr() ) == NULL )       // Find Master
        return( -1 );                                  // eNVIRONMENT
    else
    {
        char far *eptr = ( char far * )envPtr, far *p =
        ( char far *)envPtr;
        size_t   envSize, varLen, reqLen;

        strupr( varName );                             // Convert to
        strupr( varText );                             // Upper Case..

        varLen  = strlen( varName );                   // Length of Var
        reqLen  = strlen( varText ) + varLen + 2;      // Add = and NULL

        envSize = ((( struct ARENA far * )              // Get Size Env
                  MK_FP( FP_SEG( envPtr )-1, 0x0000 ))->arenaSize );
        envSize <<= 4;                                 // Para -> Bytes

        while( *( unsigned short far * )eptr != 0x0000 )// Find the End
        {                                           // Of ENVIRONMENT
            eptr++;                                // Increment

            if ( FP_OFF( eptr ) == 0x7FFF )      // Stop when we
                return( -1 );                    // reach 32K !!
        }


        if ( ( FP_OFF( eptr ) + reqLen ) >= envSize )// Enough Space?
            return( -2 );                            // End if not !

        while ( p < eptr )                            // While ! END
        {                                             // Scan VarName
         if ( ( _fstrncmp( varName, p, varLen ) == 0x00 )// Remove Prior
                &&  ( *( p + varLen ) == '=' ) )     // Settings...
            {
                char far *q = p;                  // Point VarName
                q += ( _fstrlen( q ) + 1 );      // Skip to End..
                while( q < eptr )               // Haul Variables
                    *p++ = *q++;                      // past varName
                *( eptr = ( p++ )) = NULL;            // Update EndPtr
                break;                                // Break Out!
            }
            while( *p )                                 // Skip to end
                p++;                                   // of current var
           p++;                                       // Skip the NULL
        }


        if ( varText == NULL )                    // If Text==NULL
            return( 0 );                          // Bail out !

        eptr++;                                   // Skip NULL

        _fstrcpy( eptr, varName );                // Copy varName
        _fstrcat( eptr, "=" );                    // Insert '='
        _fstrcat( eptr, varText );                // Add varText

        eptr += reqLen;                            // Skip past
        *( unsigned short far *)eptr = 0x0000;     // Insert
    }                                              // Terminating
    return( 0 );                                   // double NULLs.
}




int main( int argc, char *argv[] )
{
    printf( "\n\nExample for Setting Global eNVIRONMENT Variable.\n" );
    printf( "------- --- ------- ------ ----------- ---------\n\n" );
    printf( "The Master EnvPtr is @%Fp\n\n", GetMasterEnvPtr() );

    if ( argc == 3 )
        GlobalPutEnv( argv[1], argv[2] );

    if ( argc == 2 )
        GlobalPutEnv( argv[1], NULL );
    
    if ( argc == 1 )
        printf( "USAGE: %s  varName [ varText ] \n"
                "       If varText is not provided, varName ( if SET ) "
                "is cleared\n\n", ( argv[0] ? argv[0] : "Putgenv" ));
    return( 0 );
} 
