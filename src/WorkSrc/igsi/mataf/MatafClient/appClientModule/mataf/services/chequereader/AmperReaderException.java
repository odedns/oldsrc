// Decompiled by Jad v1.5.8d. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AmperReaderException.java

package mataf.services.chequereader;


public class AmperReaderException extends Exception
{

    protected AmperReaderException(int i)
    {
        rc = i;
    }

    private static void a_5530N400_()
    {
    }

    protected String getCode()
    {
        return Integer.toString(rc);
    }

    public String getMessage()
    {
        switch(rc)
        {
        case 0: // '\0'
            return new String("Ok");

        case 1: // '\001'
            return new String("Timeout");

        case 2: // '\002'
            return new String("Bad characters");

        case 3: // '\003'
            return new String("Not armed");

        case 4: // '\004'
            return new String("Not connected");

        case 5: // '\005'
            return new String("Not completed");

        case 6: // '\006'
            return new String("Malfunction");

        case 7: // '\007'
            return new String("Cancelled");

        case 8: // '\b'
            return new String("Port does not exist");

        case 9: // '\t'
            return new String("Port is already used");

        case 10: // '\n'
            return new String("Input stream creation error");

        case 11: // '\013'
            return new String("Output stream creation error");

        case 12: // '\f'
            return new String("Too many serial port listeners");

        case 13: // '\r'
            return new String("Bad open parameters");
        }
        return new String("Unknown");
    }

    private static final String COPYRIGHT = "(c) Copyright IBM Corporation 2000. ";
    private int rc;
}
