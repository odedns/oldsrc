// memdemo.cpp
// demonstration of overloaded memory operators:
// frequent memory violation diagnostics, using malloc and free
// 04.01.91 avner ben coded.
// 24.6.93 avner ben last update.

#include <stdio.h>
#include <stdlib.h>

void memError( const char* message )
{
	fprintf( stderr, "memory error: %s\a!\n",message );
	getchar();
	exit( 1 );
}

/////////////////////////////////////////////////////////////////////////
// the memory debugger class:

class MemDebugger
{
	private:
		static void* m_buf[2000];		// here we keep the pointers
		static void ** m_current,			// next vacant cell
		 ** m_last;			// sentinel

	public:
		~MemDebugger(); 		// check if any pointers left

		static void push( void* NewEntry );	// checks internal buf overflow
		static void pop( void* entryToDelete );// verifies allocation
};


MemDebugger:: ~MemDebugger()
{
	if ( m_current != m_buf ) {
		fprintf( stderr, "%d pointers not deleted!\007\n", m_current - m_buf );
		getchar();
	}
}

void MemDebugger:: push( void* entry )
{
	if ( m_current == m_last )
		memError( "memory-tracking buffer overflow" );
	*( m_current++ ) = entry;
}

void MemDebugger:: pop( void* ptr )
{
	for ( void **entry = m_buf; entry < m_last; entry++ )
		if ( *entry == ptr ) break;
	if ( *entry != ptr )
		memError( "attemp to delete address not allocated" );
	for ( ; entry != m_current; entry++ )
		*entry = *( entry + 1 );
	m_current--;
}

void* MemDebugger:: m_buf[2000];
void** MemDebugger:: m_current = MemDebugger::m_buf,
 ** MemDebugger:: m_last = MemDebugger::m_buf + 2000 - 1;


static MemDebugger theMemDebugger;

//////////////////////////////////////////////////////////////////////////
// the overloaded memory-allocation operators:

void *operator new( size_t siz )
{
	void* entry = malloc( siz );
	if ( !entry )
		memError( "out of memory" );
	theMemDebugger.push( entry );
	return entry;
}

void operator delete( void *ptr )
{
	if ( !ptr )
		memError( "delete through null pointer" );
	theMemDebugger.pop( ptr );
	free( ptr );
}
