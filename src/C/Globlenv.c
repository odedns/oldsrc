Setting Permanent/Persistent Environment Variables.

 How can a program set a environment variable which remains  
 active after terminating the application. 


/* ------------ [ SETTING GLOBAL environment VARIABLES ]------------- *\ 
 ³                                                                    ³
 ³                                                                    ³
 ³  The eNVIRONMENT is a section of Memory ( up to 32K in size )      ³
 ³  which contains a series ( or a SET ) of 'Environment Variables'   ³
 ³  in the format:                                                    ³
 ³              VariableName=VariableText                             ³
 ³                                                                    ³
 ³  Both the VariableName and VariableText are ASCIIZ                 ³
 ³  (NULL-terminated) strings.  eNVIRONMENT Variables are managed and ³
 ³  manipulated via the DOS 'SET' command ( See your DOS manual for   ³
 ³  more details ).                                                   ³
 ³                                                                    ³
 ³  Before DOS executes an application ( or when a PARENT program     ³
 ³  calls the DOS EXEC function - INT 21,4B - with the Parent's Env.  ³
 ³  Segment set to zero ), DOS allocates a block of memory to which   ³
 ³  it copies its ( or the PARENT program's ) current eNVIRONMENT.    ³
 ³  The Segment value of the allocated block ( eNVIRONMENT Pointer )  ³
 ³  is stored in the application's PSP ( at Offset 2Ch ).  Therefore  ³
 ³  each application has a local copy of it's PARENT eNVIRONMENT.     ³
 ³  The primary SHELL ( which may be set by the SHELL command in      ³
 ³  CONFIG.SYS - usually COMMAND.COM ) creates the *MASTER*           ³
 ³  eNVIRONMENT at boot time.                                         ³
 ³                                                                    ³
 ³  The size of Memory Blocks allocated for Local Copies of the       ³
 ³  eNVIRONMENT are just big enough to accomodate the variables of    ³
 ³  the parent's eNVIRONMENT.  The Borland C/C++ compilers, however,  ³
 ³  provide the putenv() function which may 'realloc' the Local       ³
 ³  eNVIRONMENT Memory Block to make room for additional eNVIRONMENT  ³
 ³  Variables.  Local eNVIRONMENT copies, however, are transient: the ³
 ³  Memory Block of the Local Copy is freed when the application      ³
 ³  terminates.   This implies that changes made to one's local copy  ³
 ³  of the eNVIRONMENT are only active during one's lifetime.  An     ³
 ³  application's changes to its Local Copy can be inherited by a     ³
 ³  child of the application.  Therefore "you can leave a legacy for  ³
 ³  your children but you can't touch your PARENT !".                 ³
 ³                                                                    ³
 ³                                                                    ³
 ³  To SET a permanent/persistent eNVIRONMENT Variable, one must      ³
 ³  modify the *MASTER* eNVIRONMENT.   The current versions of DOS do ³
 ³  not provide any documented interfaces to modify the *MASTER*      ³
 ³  eNVIRONMENT or even detect its location.  Various methods have    ³
 ³  been suggested and used for finding the Master eNVIRONMENT;  This ³
 ³  example uses a method which relies on the following:              ³
 ³  Since DOS creates an eNVIRONMENT for every application, the Env.  ³
 ³  segment always preceed the application which owns the eNVIRONMENT.³
 ³  However, this logic does not apply for the Env. Seg. created and  ³
 ³  owned by DOS itself: Since DOS creates the Env., the owner in this³
 ³  case is at a lower address than its ENVIRONMENT.  Scanning for the³
 ³  Process whose Env. Seg. is higher that the Seg. of the application³
 ³  allows us to determine the Segment of the Master eNVIRONMENT.     ³
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
 ³                                                                    ³
 ³ The following section of the example determines the location of the³
 ³ Master eNVIRONMENT.   The setting/clearing of an eNVIRONMENT Var.  ³
 ³ is separated from the Env. Seg. detection routines so that other   ³
 ³ methods for locating the Master eNVIRONMENT can be easily          ³
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
                          



/* ------------ [ MODIFYING THE MASTER environment...  ] ------------ *\
 ³                                                                    ³
 ³ The following function modifies the Master eNVIRONMENT...   The    ³
 ³ eNVIRONMENT variable name as well as its value ( Text ) are the two³
 ³ variables required.   If the second variable is NULL, the variable ³
 ³ Name ( if currently SET ) will be cleared from the                 ³
 ³ Master eNVIRONMENT.                                                ³
 ³                                                                    ³
 ³ The return values are :                                            ³
 ³     0 :  Successful                                                ³
 ³    -1 :  General Error                                             ³
 ³    -2 :  Insufficient space in eNVIRONMENT for New Variable        ³
 ³                                                                    ³
 ³ NOTE:  Changes made to the Master eNVIRONMENT do *NOT* affect the  ³
 ³        application's local eNVIRONMENT.  Use the Borland C/C++     ³
 ³        putenv() function to modify the Local eNVIRONMENT.          ³
 ³                                                                    ³
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
