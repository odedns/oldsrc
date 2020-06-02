// ex6c.h.
// "file of records" excercise, stage 6, school solution.
// 25.02.94 avner ben coded.

#ifndef FILEOFRECS_C_H
#define FILEOFRECS_C_H

#include <stdlib.h>
#include <string.h>

// various Field derivatives

class StrField : public Field, public String
{ // The default, format-less data field. Intended for polymorphic use.

public:
	 // instantiation:
	StrField( int size )
	 : Field( size ), String( size )  
	 { }
	StrField( const char* data ) 
	 : Field( strlen( data ) ), String( data )  
	 { }
	StrField( FileOfTokens& specification );
	Field* clone() 
	 { return new StrField( *this ); }
		 
	 // accessors:
	String str() const 
	 { return *this; }
	Boolean equalTo( const Field* rhs ) const {
	 	return !strcmp( rhs->typeId(), typeId() ) 
	 	 && !strcmp( m_s, (( StrField* )rhs )->m_s ); 
	}
	
	 // run-time type identification
	const char* typeId() const 
	 { return "StrField"; }

	 // operations
	Boolean loadFrom( ifstream& stream, Boolean waitForNewLine );
	void clear(); 
};

class IntField : public Field
{ // an integer data field. Intended for polymorphic use.

private:
	int m_i;

public:
	 // instantiation:
	IntField( int size )
	 : Field( size ), m_i( 0 ) 
	 { }
	IntField( int val, int size )
	 : Field( size ), m_i( val ) 
	 { }
	IntField( FileOfTokens& specification );
	Field* clone() 
	 { return new IntField( *this ); }
		 
	 // Accessors:
	String str() const;
	Boolean equalTo( const Field* rhs ) const {
	 	return !strcmp( rhs->typeId(), typeId() ) 
	 	 && m_i == (( IntField* )rhs )->m_i; 
	 } 
	
	 // Run-time type identification
	const char* typeId() const
	 { return "IntField"; }

	 // Operations
	Boolean loadFrom( ifstream& stream, Boolean waitForNewLine );
	void clear()
	 { m_i = 0; }
};
  
class FillerField : public Field
{ // Dummi data field. Intended for polymorphic use.

public:
	 // instantiation:
	FillerField( int size )
	 : Field( size )
	 { }
	FillerField( FileOfTokens& specification );
	Field* clone() 
	 { return new FillerField( *this ); }
		 
	 // accessors:
	String str() const 
	 { return m_size; }
	Boolean equalTo( const Field* rhs ) const {
	 	return !strcmp( rhs->typeId(), typeId() ) 
	 	 && m_size == (( FillerField* )rhs )->m_size; 
	}
	
	 // run-time type identification
	const char* typeId() const 
	 { return "FillerField"; }

	 // operations
	Boolean loadFrom( ifstream& stream, Boolean waitForNewLine );
	void clear() 
	 { }
};

#endif
