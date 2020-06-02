

//----------
// LZWClient
// ---------
// Demonstration LZW (de)compressing utility which relies on the LZW
// classes, which in turn build on the BitStream I/O classes.
// (c) Laurence Vanhelsuwe 1996. E-Mail: LVA@telework.demon.co.uk
//------------------------------------------------------------------

import java.io.*;
import java.util.*;


public class LZWClient {

private final static int ACTION_INVALID    = 0;
private final static int ACTION_COMPRESS   = 1;
private final static int ACTION_DECOMPRESS = 2;
private final static String usage = 
       "Usage: java LZW <COMPRESS|DECOMPRESS> <file>";
public static void main (String[] args) {
int action = ACTION_INVALID;

    // need an action and filename as command line arguments
    if (args.length != 2) {
        System.out.println(usage);
        System.exit(10);
    }
    // validate requested action
    if (args[0].equals("COMPRESS")) {
        action = ACTION_COMPRESS;
    } else
    if (args[0].equals("DECOMPRESS")) {
        action = ACTION_DECOMPRESS;
    }
    switch (action) {
        case ACTION_COMPRESS:
            compressFile(args[1]); break;
        case ACTION_DECOMPRESS:
            decompressFile(args[1]); break;
        case ACTION_INVALID:
            System.out.println("Invalid action requested: " + args[0]);
            System.out.println(usage);
            System.exit(20);
            break;                  // for consistency
    }
    System.out.println("LZWClient Done.");
}
//------------------------------------------------------------------
// Compress a file to "compressed.out"
//------------------------------------------------------------------
protected static void compressFile( String filename ) {

InputStream  inputFile      = null;
OutputStream compressedFile = null;
int originalSize, compressedSize;
LZW lzw;
    try {
        inputFile = new FileInputStream( filename );

        originalSize = fileLength( filename );
    
        lzw = new LZW();
    
        System.out.print("Compressing.."); System.out.flush();
        compressedFile  = new FileOutputStream("compressed.out");
    
        Date timer = new Date();
        lzw.compress(inputFile, compressedFile);
    
        compressedSize  = fileLength("compressed.out");
        printCPS(timer, originalSize);
    
        printEfficiency(originalSize, compressedSize);

    } catch (FileNotFoundException badFile) {
        System.err.println("'" + filename + "' could not be found.");
        System.exit(10);
    } catch (IOException ioErr) {
        System.out.println("IO Error: " + ioErr);
        System.exit(10);
    }
}
//------------------------------------------------------------------
// Decompress a file to "original.out"
//------------------------------------------------------------------
protected static void decompressFile( String filename ) {

InputStream  inputFile    = null;
OutputStream restoredFile = null;
int originalSize, compressedSize;
LZW lzw;
    try {
        inputFile = new FileInputStream( filename );

        lzw = new LZW();
    
        System.out.print("Decompressing.."); System.out.flush();
        restoredFile = new FileOutputStream("original.out");
    
        Date timer = new Date();
        lzw.decompress(inputFile, restoredFile);
    
        originalSize = fileLength("original.out");
        printCPS(timer, originalSize);

    } catch (FileNotFoundException badFile) {
        System.err.println("'" + filename + "' could not be found.");
        System.exit(10);
    } catch (IOException ioErr) {
        System.out.println("IO Error: " + ioErr);
        System.exit(10);
    }
}
//------------------------------------------------------------------
// Utility function to determine a file's size
//------------------------------------------------------------------
public static int fileLength (String fileName) {
File f;
    f = new File(fileName);
    return (int) f.length();
}
//------------------------------------------------------------------
// Determine and print how well we've compressed a file
//------------------------------------------------------------------
public static void printEfficiency(int original, int compressed) {
double percent;
    percent         = 100.0 * ((double)compressed)/(double)original;
    System.out.println("Original size   : " + original);
    System.out.println("Compressed size : " + compressed);
    System.out.println("% of original   : " + percent + "%");
}
//------------------------------------------------------------------
// Determine and print how fast we've (de)compressed a file
//------------------------------------------------------------------
public static void printCPS( Date then, int numBytes) {
double ms, cps;
    ms  = (double) ( (new Date()).getTime() - then.getTime() );
    cps = 1000.0 * ((double) numBytes) / ms;
    System.out.println(" (at " + cps + " bytes per second)");
}
} // End of Class LZWClient






