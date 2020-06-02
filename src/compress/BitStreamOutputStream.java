
//----------------------------
// Class BitStreamOutputStream
// ---------------------------
// Implements an enhanced OutputStream which allows you to write a
// stream of bit fields ranging in size from 1 bit (a true bit
// stream) to 32 bits (a stream of integers). The size of the current
// bitfield can be changed at any point while writing the stream.
// (c) Laurence Vanhelsuwe 1996. E-Mail: LVA@telework.demon.co.uk
//------------------------------------------------------------------

import java.io.*;

public class BitStreamOutputStream extends FilterOutputStream {

final static int EIGHT = 8;     // 8 bits per byte

protected short buffer;         // our BYTE bitstream write buffer
protected int bitsInCache;      // how many cached bits in our byte
protected int fieldSize;        // current size of bitstream fields
protected long maxFieldValue;   // max value that fits bitfield
//------------------------------------------------------------------

public BitStreamOutputStream(OutputStream out) {
    this(out, EIGHT);           // default to a normal byte stream
}
//------------------------------------------------------------------
public BitStreamOutputStream(OutputStream out, int bitFieldSize) {
    super(out);                 // call FilterOutputStream constr.
    setBitFieldSize(bitFieldSize);
    bitsInCache = 0;            // we haven't got any cached bits
    buffer      = 0;            // start w/ clean buffer (for ORs !)
}
//------------------------------------------------------------------
public void setBitFieldSize(int bits) throws IllegalArgumentException {
    if (bits>32 || bits<1) throw new IllegalArgumentException
        (
 "BitField size ("+ bits + ") no good. Has to be between 1 and 32."
        );
    this.fieldSize = bits;
    this.maxFieldValue = (1L << bits) - 1;  // precalc max bf value
}
//------------------------------------------------------------------
public int getBitFieldSize() {
    return this.fieldSize;
}
//------------------------------------------------------------------
// Write a bitfield to the output stream. The number of bits written is
// the current bitfield length. Bitfield can be on arbitrary bit boundaries.
//------------------------------------------------------------------
public void writeBitField(int bf) throws IOException {

int bitsToWrite;            // how many bits left to write
int capacity;               // how many bits fit in write buffer
int partial, partialSize;   // partial bitfield and its size in bits
int bfExtractPos;           // bitfield extract position (bit number)

        // check that bitfield fits in current bitfield size
    if (bf > maxFieldValue ) {
        throw new IllegalArgumentException
            (
     "Can not pack bitfield " + bf + " in " + fieldSize + " bits."
            );
    }
    bitsToWrite  = fieldSize;
    bfExtractPos = fieldSize;
        // a single bitfield might have to be written out in several
        // passes since the lot has to pass through the single byte
        // write buffer. This inefficient situation is a result of
        // the complex aligning required to append any bitfield to
        // the currently written stream.
    while (bitsToWrite > 0) {
        if (bitsInCache != EIGHT) {         // if capacity left
            capacity = EIGHT - bitsInCache; // in write buffer...

            partialSize = Math.min( bitsToWrite, capacity);
            bfExtractPos -= partialSize;

            partial = extract (bf, partialSize, bfExtractPos);
            buffer |= partial << (capacity - partialSize);
            bitsToWrite -= partialSize;
            bitsInCache += partialSize;
        }
        if (bitsInCache == EIGHT) {         // if write buffer full,
            out.write((int) buffer);        // send it on its way
            bitsInCache = 0;                // and continue with
            buffer      = 0;                // clean buffer
        }
    }
}
//------------------------------------------------------------------
// extract a bitfield of length 'bits' from an integer source.
// bitfield starts at bit 'pos' and is returned right-aligned to bitpos 0
//------------------------------------------------------------------
private int extract (int source, int bits, int pos) {

    source = source >> pos;     // align bitfield to bit 0
    int mask = ~( (-1) << bits);// create a mask to get clean bitfld
    return source & mask;       // return bitfield (0 bits padded)
}
        //---------------------------------------------
        // The remaining methods are methods we override from
        // our parent class: FilterOutputStream
        //---------------------------------------------
//------------------------------------------------------------------
// Override write() method to write a byte on any bit boundary.
//------------------------------------------------------------------
public void write(int b) throws IOException {
int previousBFSize;

    previousBFSize = getBitFieldSize();
    setBitFieldSize(EIGHT);
    try {
        writeBitField( b & 0xFF );
    }
    // if writeBitField threw an exception, make sure we reset
    // the current bitfield size before letting the exception thru
    finally {
        setBitFieldSize(previousBFSize);
    }
}

//------------------------------------------------------------------
// Override block write() methods to use basic write() as building block.
//------------------------------------------------------------------
public void write(byte b[], int off, int len) throws IOException {
    for (int i = 0 ; i < len ; i++) {
        write(b[off + i]);
    }
}
public void write(byte b[]) throws IOException {
   write(b, 0, b.length);
}

//------------------------------------------------------------------
// Override flush() method.
//------------------------------------------------------------------
public void flush() throws IOException {

    // REFUSE to flush as this advances file pointer of underlying stream !
}    
//------------------------------------------------------------------
// Override close() method to correctly flush any remaining bitfields
// in write buffer before closing output chain.
//------------------------------------------------------------------
public void close() throws IOException {
    if (bitsInCache != 0) {
        out.write((int) buffer);
    }
    out.flush();
    out.close();
}    
} // End of class BitStreamOutputStream



