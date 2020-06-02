

//--------------------
// Class LZWDictionary
// -------------------
// This class encapsulates the core LZW string/compression code
// associative data structure and its ADT manipulation methods.
// (c) Laurence Vanhelsuwe 1996    email: LVA@telework.demon.co.uk
//------------------------------------------------------------------

import java.util.*;     // mainly for Hashtable

//------------------------------------------------------------------
class LZWDictionary {25


private final static int ASCII_SET_SIZE = 256;
private final static int COMMAND_CODES  = 1;
private final static int FIRST_CODE     = ASCII_SET_SIZE + COMMAND_CODES;

// List of command codes:
public final static int GROW_CODE = FIRST_CODE - 1;

protected Hashtable string2codeLUT;
protected Hashtable code2stringLUT;
protected int       nextCode;

/------------------------------------------------------------------
// LZWDictionary constructor
// 1) create the two parallel string/code look-up tables
// 2) initialize them to contain codes for every 8-bit ASCII char
// 3) init compression code counter
//------------------------------------------------------------------
public LZWDictionary () {

String string;      // the look-up table key
Integer code;       // the look-up table value
    string2codeLUT  = new Hashtable();
    code2stringLUT  = new Hashtable();
    for (int charCode=0; charCode < ASCII_SET_SIZE; charCode++) {
        string = String.valueOf( (char) charCode );
        code   = new Integer(charCode);
        string2codeLUT.put(string, code);
        code2stringLUT.put(code, string);
    }
    nextCode = FIRST_CODE;
}
//------------------------------------------------------------------
// Boolean function to determine whether a string sequence has
// already been registered (and can therefore be compressed).
//------------------------------------------------------------------
public boolean encountered(String string) {
    return string2codeLUT.containsKey(string);
}
//------------------------------------------------------------------
// A new string needs to be recorded in the LZW dictionary.
// The new string becomes associated with a unique compression code.
// To be able to look up the code for a string and vice versa, two
// Hashtable objects (containing essentially the same data) are
// maintained and kept in sync.
//------------------------------------------------------------------
public void add(String string) {
    Integer code = new Integer(nextCode);
    string2codeLUT.put(string, code);
    code2stringLUT.put( code, string);
    nextCode++;
}
//------------------------------------------------------------------
// Look up the compression code for a given string
//------------------------------------------------------------------
public int stringCode(String string) {
Integer code;
    code = (Integer) string2codeLUT.get(string);
    return code.intValue();
}
//------------------------------------------------------------------
// Look up a string associated with a given compression code
//------------------------------------------------------------------
public String codeString(int strCode) {
Integer code;
    code = new Integer(strCode);
    return (String) code2stringLUT.get(code);
}} // End of Class LZWDictionary



