

//-----------------------------
// Class LZW (Lempel-Zif-Welch)
// ----------------------------
// This Java implementation of the LZW algorithm demonstrates the
// use of the BitStreamInputStream and BitStreamOutputStream classes.
// (c) Laurence Vanhelsuwe 1996  E-Mail: LVA@telework.demon.co.uk
//------------------------------------------------------------------

import java.io.*;

//------------------------------------------------------------------
public class LZW {

protected LZWDictionary LZWdict;            // LZW look-up object
protected       int bitFieldSize;           // current bitfield size
protected final int STARTING_BF_SIZE = 9;

//------------------------------------------------------------------
// LZW compressor. For an explanation of the algorithm, see DDJ
// October 1989.
//------------------------------------------------------------------
public boolean compress(InputStream plain, OutputStream compressed) {

BufferedInputStream     b_in;   // these buffering streams are there
BufferedOutputStream    b_out;  // purely to enhance I/O performance

BitStreamOutputStream   out;    // the output stream for LZW data

String  string, longerString;
int     ch;
    bitFieldSize = STARTING_BF_SIZE;


    b_in    = new BufferedInputStream(plain);
    b_out   = new BufferedOutputStream(compressed);

    out     = new BitStreamOutputStream(b_out, bitFieldSize);

    LZWdict = new LZWDictionary();

    // STRING = get input character
    try {
        char initialChar = (char) b_in.read();
        string = String.valueOf( initialChar );
    
        // WHILE there are still input characters DO
        //   CHARACTER = get input character
        while ( (ch = b_in.read()) != -1) {
            longerString =  string + (char) ch;
            // IF STRING+CHARACTER is in the string table THEN
            if ( LZWdict.encountered(longerString) ) {
                // STRING = STRING+character
                string = longerString;
            } else {
                // output the code for STRING
                // add STRING+CHARACTER to the string table
                // STRING = CHARACTER
                int code = LZWdict.stringCode(string);
                try {
                    out.writeBitField( LZWdict.stringCode(string) );
                } catch (IllegalArgumentException bitFieldWontFit) {
                    out.writeBitField( LZWDictionary.GROW_CODE );
                    out.setBitFieldSize( ++bitFieldSize );
                    out.writeBitField( LZWdict.stringCode(string) );
                }
                LZWdict.add(longerString);
                string = String.valueOf( (char) ch);
            }
        }
    // output the code for STRING
    out.writeBitField( LZWdict.stringCode(string) );
    } catch (Exception e) {
        System.out.println("Exception: " + e);
    }
    try {
        out.close();
    } catch (Exception ignored) {}
    return true;
}
//------------------------------------------------------------------
// LZW decompressor. For an explanation of the algorithm, see DDJ
// October 1989.
//------------------------------------------------------------------
public boolean decompress(InputStream compressedByteStream,
                         OutputStream decompressed) {
BufferedInputStream     b_in;

BufferedOutputStream    b_out;
BitStreamInputStream    in;
DataOutputStream        out;

long                    code;
int                     oldCode, newCode;
String                  string;
char                    firstChar;

    bitFieldSize = STARTING_BF_SIZE;

    b_in    = new BufferedInputStream(compressedByteStream);
    b_out   = new BufferedOutputStream(decompressed);

    in      = new BitStreamInputStream(b_in, bitFieldSize);
    out     = new DataOutputStream(b_out);
    LZWdict = new LZWDictionary();

    try {

        // Read OLD_CODE
        // output OLD_CODE
    
        oldCode = (int) in.readBitField();
        out.write(oldCode);
        firstChar = (char) oldCode;

        // WHILE there are still input characters DO
        while( (code = in.readBitField()) != -1) {
            // Read NEW_CODE
            newCode = (int) code;
            switch (newCode) {
                case LZWDictionary.GROW_CODE : 
                    in.setBitFieldSize ( ++bitFieldSize );
                    continue;       // carry on with while()
            }
            // STRING = get translation of NEW_CODE
            string = LZWdict.codeString(newCode);
            if (string == null) {
                String prevString = LZWdict.codeString(oldCode);
                string = prevString + firstChar;
            }
            // output STRING
            out.writeBytes(string);
    
            // CHARACTER = first character in STRING
            firstChar = string.charAt(0);
    
            // add OLD_CODE + CHARACTER to the translation table
            String prevString = LZWdict.codeString(oldCode);
            String compressedString = prevString + firstChar;
            LZWdict.add(compressedString);
  
            // OLD_CODE = NEW_CODE

            oldCode = newCode;
    
        } // End of while

    } catch (Exception e) {
        System.out.println("Exception: " + e);
    }
    try {
        out.close();
    } catch (Exception ignored) {}
    return true;
}} // End of Class LZW (Lempel-Zif-Welch)

