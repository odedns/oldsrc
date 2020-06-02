// Decompiled by Jad v1.5.8d. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CheckReaderInterface.java

package mataf.services.chequereader;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.FormatElement;
import com.ibm.dse.base.Notifier;

// Referenced classes of package com.ibm.dse.services.checkreader:
//            DSECheckReaderException

public interface CheckReaderInterface
    extends Notifier
{

    public abstract void cancel()
        throws DSECheckReaderException;

    public abstract void close()
        throws DSECheckReaderException;

    public abstract String getDataRead();

    public abstract void open()
        throws DSECheckReaderException;

    public abstract void read()
        throws DSECheckReaderException;

    public abstract void readAndUnformat(Context context, FormatElement formatelement)
        throws DSECheckReaderException;

    public abstract String readAndWait()
        throws DSECheckReaderException;

    public abstract void readAndWaitAndUnformat(Context context, FormatElement formatelement)
        throws DSEInvalidArgumentException, DSEInvalidRequestException, DSECheckReaderException;

    public static final String COMPLETED_EVENT = "Completed";
    public static final String CANCEL_EVENT = "Cancel";
    public static final String TIMEOUT_EVENT = "Timeout";
    public static final String MALFUNCTION_EVENT = "Malfunction";
    public static final String BAD_CHARACTER_EVENT = "BadChar";
    public static final String UNKNOWN_EVENT = "UnknownRetCode";
    public static final int a_5530N400_ = 0;
}
