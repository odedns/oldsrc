// FrontEnd Plus GUI for JAD
// DeCompiled : BidiReorder.class

package mataf.utils;

import java.io.PrintStream;

public class BidiReorder
{

    protected static final int INIT = 0;
    protected static final int LATIN = 1;
    protected static final int LOCAL = 2;
    protected static final int NEUTRAL = 3;
    protected static final int LOCALNUMBER = 4;
    protected static final int LATINNUMBER = 5;
    protected static final int LATINNUMERICTERMINATOR = 6;
    protected static final int NUMERICSEPARATOR = 8;
    protected static final int ENDOFLINE = 9;
    protected static final int CARRIAGERETURN = 10;
    protected static final int LINEFEED = 11;
    protected static final int SPACE = 12;
    protected static final int ARABICDIGIT = 13;
    protected static final int SEGMENTSEPARATOR = 14;
    protected static final int BLOCKSEPARATOR = 15;
    protected static final int BIDISPECIAL = 16;
    protected static final int LATIN_STATE = 0;
    protected static final int LOCAL_STATE = 1;
    protected static final int LATIN_NUM_STATE = 2;
    protected static final int LOCAL_NUM_STATE = 3;
    protected static final int NEUTRAL_STATE = 4;
    protected static final int EOL_STATE = 5;
    protected static final int MASK_STATE = 255;
    protected static final int MASK_ACTION = 65280;
    protected static final int ACTION = 16384;
    protected static final int NEUTRALSTOLATIN = 16640;
    protected static final int NEUTRALSTOLOCAL = 16896;
    protected static final int LOCALTONUMBER = 17152;
    protected static final int LN = 17152;
    protected static final int NL = 16640;
    protected static final int NR = 16896;
    protected static final int STATE_H = 6;
    protected static final int STATE_W = 10;
    static int LTR_HWstate[][] = {
        {
            0, 0, 1, 0, -1, 2, -1, -1, 0, 5
        }, {
            0, 0, 1, 4, -1, 3, -1, -1, 4, 5
        }, {
            0, 0, 1, 0, -1, 2, -1, -1, 0, 5
        }, {
            0, 0, 1, 4, -1, 3, -1, -1, 4, 5
        }, {
            16640, 16640, 16897, 4, -1, 16899, -1, -1, 4, 16645
        }, {
            0, 0, 1, 0, -1, 2, -1, -1, 0, 5
        }
    };
    static int RTL_HWstate[][] = {
        {
            1, 0, 1, 4, -1, 2, -1, -1, 4, 5
        }, {
            1, 0, 1, 1, -1, 17155, -1, -1, 1, 5
        }, {
            1, 0, 1, 4, -1, 2, -1, -1, 0, 5
        }, {
            1, 0, 1, 1, -1, 3, -1, -1, 1, 5
        }, {
            16897, 16640, 16897, 4, -1, 16642, -1, -1, 4, 16901
        }, {
            1, 0, 1, 1, -1, 3, -1, -1, 1, 5
        }
    };
    static int StateToClass[] = {
        1, 2, 5, 4, 3, 9
    };
    protected static final int I = 0;
    protected static final int R = 2;
    protected static final int L = 1;
    protected static final int D = 5;
    protected static final int X = 6;
    protected static final int E = 8;
    protected static final int N = 3;
    protected static final int S = 12;
    protected static final int C = 10;
    protected static final int F = 11;
    protected static final int A = 13;
    protected static final int G = 14;
    protected static final int B = 15;
    protected static final int H = 16;
    protected static final int SIZE_OF_UNITABLE = 256;
    protected static final int SIZE_OF_UNISYMM = 24;
    protected static int UnicodeTable[][] = {
        {
            3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 
            11, 3, 3, 10, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 12, 3, 3, 6, 6, 6, 3, 3, 
            3, 3, 3, 3, 8, 3, 8, 8, 5, 5, 
            5, 5, 5, 5, 5, 5, 5, 5, 8, 3, 
            3, 3, 3, 3, 3, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 3, 3, 3, 3, 3, 3, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            12, 3, 6, 6, 6, 6, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 6, 6, 5, 5, 
            3, 3, 3, 3, 3, 5, 3, 3, 3, 3, 
            3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 
            1, 1, 1, 1, 1, 1
        }, {
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 3, 3, 1, 
            1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 3, 1, 3, 3, 
            3, 3, 3, 3, 3, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 3, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 3, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 
            2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3
        }, {
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 2, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 2, 3, 3, 
            3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 
            3, 3, 3, 3, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 13, 13, 13, 13, 
            13, 13, 13, 13, 13, 13, 6, 13, 13, 2, 
            3, 3, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 3, 3, 2, 2, 2, 2, 
            2, 3, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 3, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 
            5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 
            3, 3, 3, 3, 3, 3
        }, {
            12, 12, 12, 12, 12, 12, 12, 8, 12, 12, 
            12, 12, 3, 3, 1, 2, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            15, 15, 16, 3, 3, 3, 16, 3, 6, 6, 
            6, 6, 6, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 16, 16, 
            16, 16, 5, 3, 3, 3, 5, 5, 5, 5, 
            5, 5, 6, 6, 3, 3, 3, 3, 5, 5, 
            5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 
            6, 6, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3
        }, {
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3
        }, {
            12, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 
            3, 3, 3, 1, 1, 1, 1, 1, 1, 3, 
            3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 3
        }, {
            1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 
            1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 3, 2, 2, 2, 2, 
            2, 3, 2, 3, 2, 2, 3, 2, 2, 3, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2
        }, {
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 2, 2, 2, 3, 2, 3, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 3, 3, 3
        }, {
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 5, 5, 5, 5, 
            5, 5, 5, 5, 5, 5, 3, 3, 3, 3, 
            3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 
            3, 3, 3, 3, 3, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 3, 3, 3, 1, 1, 1, 1, 1, 1, 
            3, 3, 1, 1, 1, 1, 1, 1, 3, 3, 
            1, 1, 1, 1, 1, 1, 3, 3, 1, 1, 
            1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3
        }
    };
    static char UnicodeSymmSwap[][] = {
        {
            '(', ')'
        }, {
            ')', '('
        }, {
            '<', '>'
        }, {
            '>', '<'
        }, {
            '[', ']'
        }, {
            ']', '['
        }, {
            '{', '}'
        }, {
            '}', '{'
        }, {
            '\253', '\273'
        }, {
            '\273', '\253'
        }, {
            '\u207D', '\u207E'
        }, {
            '\u207E', '\u207D'
        }, {
            '\u208D', '\u208E'
        }, {
            '\u208E', '\u208D'
        }, {
            '\u2329', '\u232A'
        }, {
            '\u232A', '\u2329'
        }, {
            '\uFE59', '\uFE5A'
        }, {
            '\uFE5A', '\uFE59'
        }, {
            '\uFE5B', '\uFE5C'
        }, {
            '\uFE5C', '\uFE5B'
        }, {
            '\uFE5D', '\uFE5E'
        }, {
            '\uFE5E', '\uFE5D'
        }, {
            '\uFE64', '\uFE65'
        }, {
            '\uFE65', '\uFE64'
        }
    };
    protected static final int UBIDI_DO_MIRRORING = 2;
    static int class2level[][] = {
        {
            -1, 0, 1, -1, 2, 0, -1, -1, -1, 0
        }, {
            -1, 2, 1, -1, 2, 2, -1, -1, -1, 0
        }
    };
    int m_class[];
    int m_len;
    boolean m_bDir;
    int m_RunCount;
    int m_TerminatorFlag;
    private static final int IsLocalInvert = 1;
    private static final int IsLocalNumInvert = 2;
    private static final int IsLatinInvert = 3;
    private static final int IsAllLineInvert = 4;

    protected static int CLASSACTION(int i)
    {
        return (0xff00 & i & 0xffffbfff) >> 8;
    }

    protected static boolean DIR_IS_STRONG(int i)
    {
        return i == 1 || i == 2 || i == 5;
    }

    private boolean Need(int i, int j)
    {
        switch(i)
        {
        case 1: // '\001'
            return j == 2 || j == 4;

        case 2: // '\002'
            return j == 4;

        case 3: // '\003'
            return j == 1 || j == 5;

        case 4: // '\004'
            return j != 9;
        }
        return false;
    }

    void InvertSegment(char ac[], int i, int ai[], int j, int ai1[], int ai2[])
    {
        for(int k = 0; k < i; k++)
            if(Need(j, ai[k]))
            {
                int l;
                for(l = k + 1; l < i; l++)
                    if(!Need(j, ai[l]))
                        break;

                int i1 = (l - 1) + k;
                if(ai1 != null)
                {
                    for(int j1 = i; j1 > 0;)
                        if(--j1 >= k && j1 < l)
                            j1 = i1 - j1;

                }
                for(; k < i1 - k; k++)
                {
                    if(ac != null)
                    {
                        char c = ac[k];
                        ac[k] = ac[i1 - k];
                        ac[i1 - k] = c;
                    }
                    if(ai2 != null)
                    {
                        int k1 = ai2[k];
                        ai2[k] = ai2[i1 - k];
                        ai2[i1 - k] = k1;
                    }
                }

                k = l - 1;
            }

    }

    void DoSymSwap(char ac[], int i, int ai[])
    {
        for(int i1 = 0; i1 < i; i1++)
            if(ai[i1] == 2)
            {
                int k = 0;
                int l = 23;
                while(k < l) 
                {
                    int j = (k + l) / 2;
                    if(ac[i1] < UnicodeSymmSwap[j][0])
                    {
                        l = j - 1;
                        continue;
                    }
                    if(ac[i1] > UnicodeSymmSwap[j][0])
                    {
                        k = j + 1;
                        continue;
                    }
                    ac[i1] = UnicodeSymmSwap[j][1];
                    break;
                }
                if(k == l && ac[i1] == UnicodeSymmSwap[k][0])
                    ac[i1] = UnicodeSymmSwap[k][1];
            }

    }

    void HebrewReordering(char ac[], int i, int ai[])
    {
        int j = 0;
        for(int k = i - 1; j < k;)
        {
            char c = ac[j];
            ac[j++] = ac[k];
            ac[k--] = c;
        }

        DoSymSwap(ac, i, ai);
    }

    public BidiReorder()
    {
        m_class = new int[1];
        m_len = 1;
        m_RunCount = 1;
    }

    protected boolean SetPara(char ac[], char ac1[], int i, boolean flag, boolean flag1, boolean flag2)
    {
        int ai[] = new int[i];
        int ai1[] = new int[i];
        boolean flag3 = false;
        if(i > m_len)
            m_class = new int[i];
        m_len = i;
        StringToClass(ac, i, flag);
        if(ac1 != null)
        {
            System.arraycopy(ac, 0, ac1, 0, i);
            DoSymSwap(ac1, i, m_class);
        }
        InitMap(ai, i);
        InitMap(ai1, i);
        ApplyClass(ac1, i, flag, flag1, flag2, ai, ai1);
        int j = m_bDir ? 1 : 0;
        for(int k = 0; k < i; k++)
        {
            if(class2level[j][m_class[k]] != 1)
                continue;
            flag3 = true;
            break;
        }

        return flag3;
    }

    protected void DoReorder(char ac[], int i, boolean flag, int ai[], int ai1[])
    {
        if(flag)
        {
            InvertSegment(ac, i, m_class, 3, ai, ai1);
            InvertSegment(ac, i, m_class, 2, ai, ai1);
            InvertSegment(ac, i, m_class, 4, ai, ai1);
        } else
        {
            InvertSegment(ac, i, m_class, 2, ai, ai1);
            InvertSegment(ac, i, m_class, 1, ai, ai1);
        }
    }

    protected void ApplyClass(char ac[], int i, boolean flag, boolean flag1, boolean flag2, int ai[], int ai1[])
    {
        if(flag2)
            if(flag)
            {
                InvertSegment(ac, i, m_class, 3, ai, ai1);
                InvertSegment(ac, i, m_class, 2, ai, ai1);
            } else
            {
                InvertSegment(ac, i, m_class, 2, ai, ai1);
                InvertSegment(ac, i, m_class, 1, ai, ai1);
            }
        if(flag != flag1)
            InvertSegment(ac, i, m_class, 4, ai, ai1);
    }

    protected void StringToClass(char ac[], int i, boolean flag)
    {
        int l = 0;
        int ai[][];
        byte byte0;
        if(flag)
        {
            byte0 = 2;
            ai = RTL_HWstate;
        } else
        {
            byte0 = 1;
            ai = LTR_HWstate;
        }
        int j1 = NextState(l, byte0, ai);
        for(int j = 0; j < i; j++)
        {
            int i1 = j1;
            int k1 = GetCharacterClass(ac, i, j);
            j1 = NextState(i1, k1, ai);
            m_class[j] = StateToClass[j1 & 0xff] | m_TerminatorFlag;
            DoAction(j1, j);
            j1 &= 0xff;
        }

        for(int k = i - 1; k >= 0; k--)
            if((m_class[k] & 0xff00) == 16384)
                m_class[k] &= 0xff;

    }

    protected void InitMap(int ai[], int i)
    {
        if(ai != null)
        {
            for(int j = 0; j < i; j++)
                ai[j] = j;

        }
    }

    protected int NextState(int i, int j, int ai[][])
    {
        return ai[i][j];
    }

    protected void DoAction(int i, int j)
    {
        int l = i & 0xff00;
label0:
        switch(l)
        {
        default:
            break;

        case 16640: 
        case 16896: 
            int k = CLASSACTION(l);
            while(j-- > 0) 
            {
                if((m_class[j] & 0xff) != 3)
                    break;
                if((m_class[j] & 0xff00) == 16384 && (m_class[j + 1] == 4 || m_class[j + 1] == 5))
                    m_class[j] = m_class[j + 1];
                else
                    m_class[j] = k;
            }
            break;

        case 17152: 
            while(j-- > 0) 
            {
                if((m_class[j] & 0xff00) != 16384)
                    break label0;
                if(m_class[j + 1] == 4 || m_class[j + 1] == 5)
                    m_class[j] = m_class[j + 1];
            }
            break;
        }
    }

    protected int GetCharacterClass(char ac[], int i, int j)
    {
        int k = 3;
        m_TerminatorFlag = 0;
        if(j > 0)
            k = GetCharacterType(ac[j - 1]);
        int l = GetCharacterType(ac[j]);
        int i1;
        switch(l)
        {
        case 14: // '\016'
        case 15: // '\017'
            i1 = m_bDir ? 2 : 1;
            break;

        case 13: // '\r'
            i1 = 5;
            break;

        case 6: // '\006'
            if(j > 0)
                k = m_class[j - 1];
            if(k == 5 || k == 4)
            {
                i1 = 5;
            } else
            {
                m_TerminatorFlag = 16384;
                i1 = 3;
            }
            break;

        case 8: // '\b'
            i1 = 3;
            if(k != 5)
                break;
            int j1 = 3;
            if(++j < i)
                j1 = GetCharacterType(ac[j]);
            switch(j1)
            {
            case 5: // '\005'
                i1 = 5;
                break;

            case 2: // '\002'
            case 3: // '\003'
                i1 = 8;
                break;
            }
            break;

        case 12: // '\f'
        case 16: // '\020'
            i1 = 3;
            break;

        case 10: // '\n'
            i1 = 9;
            break;

        case 11: // '\013'
            i1 = 9;
            break;

        case 7: // '\007'
        case 9: // '\t'
        default:
            i1 = l;
            break;
        }
        return i1;
    }

    protected int GetCharacterType(char c)
    {
        int i = (c & 0xff00) >> 8;
        switch(i)
        {
        case 5: // '\005'
            i = 1;
            break;

        case 6: // '\006'
            i = 2;
            break;

        case 32: // ' '
            i = 3;
            break;

        case 33: // '!'
            i = 4;
            break;

        case 48: // '0'
            i = 5;
            break;

        case 251: 
            i = 6;
            break;

        case 254: 
            i = 7;
            break;

        case 255: 
            i = 8;
            break;

        default:
            return 3;

        case 0: // '\0'
            break;
        }
        return UnicodeTable[i][c & 0xff];
    }

    public void Convert(char ac[], char ac1[], int i)
    {
        switch(i)
        {
        case 0: // '\0'
            SetPara(ac, ac1, ac.length, false, false, true);
            break;

        case 1: // '\001'
            SetPara(ac, ac1, ac.length, false, true, true);
            break;

        case 2: // '\002'
            SetPara(ac, ac1, ac.length, true, false, true);
            break;

        case 3: // '\003'
            SetPara(ac, ac1, ac.length, true, true, true);
            break;

        case 4: // '\004'
            SetPara(ac, ac1, ac.length, false, false, true);
            break;

        case 5: // '\005'
            SetPara(ac, ac1, ac.length, false, true, true);
            break;

        case 6: // '\006'
            SetPara(ac, ac1, ac.length, true, false, true);
            break;

        case 7: // '\007'
            SetPara(ac, ac1, ac.length, true, true, true);
            break;
        }
    }

    protected char[] Inverse(char ac[])
    {
        int j = ac.length - 1;
        try
        {
            for(int i = 0; i < j / 2; i++)
            {
                char c = ac[i];
                ac[i] = ac[j - i];
                ac[j - i] = c;
            }

        }
        catch(Exception exception)
        {
            System.out.println("Exception");
        }
        return ac;
    }

}
