// Decompiled by Jad v1.5.8d. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CheckReader.java

package mataf.services.chequereader;

import java.io.IOException;
import java.util.Enumeration;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEEventObject;
import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.FormatElement;
import com.ibm.dse.base.Service;
import com.ibm.dse.base.Tag;
import com.ibm.dse.base.TagAttribute;
import com.ibm.dse.base.Trace;

// Referenced classes of package com.ibm.dse.services.checkreader:
//            CheckReaderInterface, AmperReaderListener, AmperReaderDriver, DSECheckReaderException, 
//            AmperReaderException

public class CheckReader extends Service
    implements CheckReaderInterface, AmperReaderListener
{
	private static boolean isOpen=false;
	
	private void setAmperReaderDriver(){
		if(checkDriver == null)	
			checkDriver = new AmperReaderDriver();
	}

    public CheckReader()
    {
         if(Trace.doTrace("#JCHR", 1024, 2))
            Trace.trace("#JCHR", 1024, 2, null, "CheckReader..");
        port = 1;
        baudrate = 9600;
        databits = 8;
        stopbits = 1;
        parity = 0;
        checkDtrCts = true;
        reset = true;
        timeout = 6000;
        trace = true;
        messageRead = null;
        setAmperReaderDriver();
//        checkDriver = new AmperReaderDriver();
		System.out.println("checkDriver: "+checkDriver);
        
        context = null;
        format = null;
    }

    public CheckReader(String s)
        throws IOException
    {
        super(s);
         if(Trace.doTrace("#JCHR", 1024, 2))
            Trace.trace("#JCHR", 1024, 2, null, "CheckReader(String s)..");
       port = 1;
        baudrate = 9600;
        databits = 7;
        stopbits = 1;
        parity = 2;
        checkDtrCts = true;
        reset = true;
        timeout = 11000;
        trace = false;
        messageRead = null;
        setAmperReaderDriver();
//        checkDriver = new AmperReaderDriver();
		System.out.println("checkDriver: "+checkDriver);
        context = null;
        format = null;
    }

    private static void a_5530N400_()
    {
    }
	public int UniGetAck(long timeOut){
		return checkDriver.UniGetAck(timeOut);
	}

    public void cancel()
        throws DSECheckReaderException
    {
        if(Trace.doTrace("#JCHR", 1024, 2))
            Trace.trace("#JCHR", 1024, 2, null, "Cancels the read operation...");
        try
        {
            checkDriver.cancel();
        	checkDriver = null;
        }
        catch(AmperReaderException amperreaderexception)
        {
            throw new DSECheckReaderException(DSEException.critical, amperreaderexception.getCode(), "Cancel error: " + amperreaderexception.getMessage());
        }
    }

    public void close()
        throws DSECheckReaderException
    {
        if(Trace.doTrace("#JCHR", 1024, 2))
            Trace.trace("#JCHR", 1024, 2, null, "Closes the communication...");
        checkDriver.close();
		isOpen = false;
    }

    private int getBaudrate()
    {
        return baudrate;
    }

    private int getDatabits()
    {
        return databits;
    }

    public String getDataRead()
    {
        return messageRead;
    }

    private int getParity()
    {
        return parity;
    }

    private int getPort()
    {
        return port;
    }

    private int getStopbits()
    {
        return stopbits;
    }

    private int getTimeout()
    {
        return timeout;
    }

    public Object initializeFrom(Tag tag)
        throws IOException, DSEException
    {
        for(Enumeration enumeration = tag.getAttrList().elements(); enumeration.hasMoreElements();)
        {
            TagAttribute tagattribute = (TagAttribute)enumeration.nextElement();
            if(tagattribute.getName().equals("id"))
                setName((String)tagattribute.getValue());
            else
            if(tagattribute.getName().equals("port"))
            {
                int i = (new Integer((String)tagattribute.getValue())).intValue();
                if(i >= 1 && i <= 4)
                    setPort(i);
                else
                    throw new DSECheckReaderException(DSEException.critical, "0", "The port attribute value must be 1..4.");
            } else
            if(tagattribute.getName().equals("baudRate"))
                setBaudrate((new Integer((String)tagattribute.getValue())).intValue());
            else
            if(tagattribute.getName().equals("dataBits"))
            {
                if((new Integer((String)tagattribute.getValue())).intValue() == 7)
                    setDatabits(7);
                else
                if((new Integer((String)tagattribute.getValue())).intValue() == 8)
                    setDatabits(8);
                else
                    throw new DSECheckReaderException(DSEException.critical, "0", "The dataBits attibute value must be 7 or 8.");
            } else
            if(tagattribute.getName().equals("stopBits"))
            {
                if((new Integer((String)tagattribute.getValue())).intValue() == 2)
                    setStopbits(2);
                else
                if((new Integer((String)tagattribute.getValue())).intValue() == 1)
                    setStopbits(1);
                else
                    throw new DSECheckReaderException(DSEException.critical, "0", "The stopBits attribute value must be 1 or 2.");
            } else
            if(tagattribute.getName().equals("parity"))
            {
                if(((String)tagattribute.getValue()).equals("none"))
                    setParity(0);
                else
                if(((String)tagattribute.getValue()).equals("even"))
                    setParity(2);
                else
                if(((String)tagattribute.getValue()).equals("odd"))
                    setParity(1);
                else
                    throw new DSECheckReaderException(DSEException.critical, "0", "The parity attibute value must be none, even or odd.");
            } else
            if(tagattribute.getName().equals("timeout"))
                setTimeout((new Integer((String)tagattribute.getValue())).intValue());
            else
            if(tagattribute.getName().equals("checkLink"))
            {
                if(((String)tagattribute.getValue()).equals("yes"))
                    setCheckDtrCts(true);
                else
                if(((String)tagattribute.getValue()).equals("no"))
                    setCheckDtrCts(false);
                else
                    throw new DSECheckReaderException(DSEException.critical, "0", "The checkLink attribute value must be yes or no.");
            } else
            if(tagattribute.getName().equals("reset"))
            {
                if(((String)tagattribute.getValue()).equals("yes"))
                    setReset(true);
                else
                if(((String)tagattribute.getValue()).equals("no"))
                    setReset(false);
                else
                    throw new DSECheckReaderException(DSEException.critical, "0", "The reset attribute value must be yes or no.");
            } else
            if(tagattribute.getName().equals("trace"))
            {
                if(((String)tagattribute.getValue()).equals("yes"))
                    setTrace(true);
                else
                if(((String)tagattribute.getValue()).equals("no"))
                    setTrace(false);
                else
                    throw new DSECheckReaderException(DSEException.critical, "0", "The trace attribute value must be yes or no.");
            } else
            {
                throw new DSECheckReaderException(DSEException.critical, "0", "Attribute " + tagattribute.getName() + " is unknown.");
            }
        }

        return this;
    }

    private boolean isCheckDtrCts()
    {
        return checkDtrCts;
    }

    private boolean isReset()
    {
        return reset;
    }

    private boolean isTrace()
    {
        return trace;
    }

    public void open()
        throws DSECheckReaderException
    {
        if(Trace.doTrace("#JCHR", 1024, 2))
            Trace.trace("#JCHR", 1024, 2, null, "Opens and initializes the communication...");
        try
        {
//     		if(!isOpen){
            	checkDriver.open(getPort(), getBaudrate(), getDatabits(), getStopbits(), getParity(), isTrace());
//     		}
        }
        catch(AmperReaderException amperreaderexception)
        {
            throw new DSECheckReaderException(DSEException.critical, amperreaderexception.getCode(), amperreaderexception.getMessage());
        }
        if(getTimeout() != 0)
            checkDriver.setTimeout(getTimeout());
        checkDriver.setCheckDtrCts(isCheckDtrCts());
        checkDriver.setReset(isReset());
		isOpen = true;
    }
    
    public void armCheckReader () {
    	char c1 = 0x2;
		char c2 = 0x3;
		String commandString = c1 + "A" + c2;
		checkDriver.writeCommand(commandString);
    }
    	
    public void writeCommand (String commandString) {
		checkDriver.writeCommand(commandString);
    }

    public void read()
        throws DSECheckReaderException
    {
        if(Trace.doTrace("#JCHR", 1024, 2))
            Trace.trace("#JCHR", 1024, 2, null, "Reads the data from the device in asynchronous mode...");
        try
        {
            checkDriver.readAsync(this);
        }
        catch(AmperReaderException amperreaderexception)
        {
            throw new DSECheckReaderException(DSEException.critical, amperreaderexception.getCode(), amperreaderexception.getMessage());
        }
        actualAction = 0;
    }

    public void readAndUnformat(Context context1, FormatElement formatelement)
        throws DSECheckReaderException
    {
        if(Trace.doTrace("#JCHR", 1024, 2))
            Trace.trace("#JCHR", 1024, 2, null, "Reads the data from the device in ansynchronous mode and unformats it...");
        try
        {
            checkDriver.readAsync(this);
       }
        catch(AmperReaderException amperreaderexception)
        {
            throw new DSECheckReaderException(DSEException.critical, amperreaderexception.getCode(), amperreaderexception.getMessage());
        }
        context = context1;
        format = formatelement;
        actualAction = 1;
    }

    public String readAndWait()
        throws DSECheckReaderException
    {
        if(Trace.doTrace("#JCHR", 1024, 2))
            Trace.trace("#JCHR", 1024, 2, null, "Reads the data from the device in synchronous mode...");
        try
        {
            messageRead = checkDriver.readSync();
			messageRead = messageRead.trim();
        }
        catch(AmperReaderException amperreaderexception)
        {
 		System.out.println("readAndWait: AmperReaderException!!!");
           if(Trace.doTrace("#JCHR", 256, 8))
                Trace.trace("#JCHR", 256, 8, null, "Synchronous read operation not performed correctly: " + amperreaderexception.getMessage());
            throw new DSECheckReaderException(DSEException.critical, amperreaderexception.getCode(), amperreaderexception.getMessage());
        }
        return messageRead;
    }

    public void readAndWaitAndUnformat(Context context1, FormatElement formatelement)
        throws DSEInvalidArgumentException, DSEInvalidRequestException, DSECheckReaderException
    {
        if(Trace.doTrace("#JCHR", 1024, 2))
            Trace.trace("#JCHR", 1024, 2, null, "Reads the data from the device in synchronous mode and unformats it...");
        try
        {
            messageRead = checkDriver.readSync();
            unformatMessageReceived(context1, formatelement);
        }
        catch(AmperReaderException amperreaderexception)
        {
            if(Trace.doTrace("#JCHR", 256, 8))
                Trace.trace("#JCHR", 256, 8, null, "Synchronous unformat read operation not performed correctly: " + amperreaderexception.getMessage());
            throw new DSECheckReaderException(DSEException.critical, amperreaderexception.getCode(), amperreaderexception.getMessage());
        }
    }

    public void readCompleted(int i, String s)
    {
        if(Trace.doTrace("#JCHR", 1024, 2))
            Trace.trace("#JCHR", 1024, 2, null, "readCompleted :"+s);
        messageRead = s;
        try
        {
            String s1 = null;
            switch(i)
            {
            case 7: // '\007'
                s1 = new String("Cancel");
                break;

            case 1: // '\001'
                s1 = new String("Timeout");
                break;

            case 6: // '\006'
                s1 = new String("Malfunction");
                break;

            case 2: // '\002'
                s1 = new String("BadChar");
                break;

            case 0: // '\0'
                s1 = new String("Completed");
                if(actualAction != 1)
                    break;
                try
                {
                    unformatMessageReceived(context, format);
                }
                catch(DSEInvalidRequestException _ex) { }
                break;

            case 3: // '\003'
            case 4: // '\004'
            case 5: // '\005'
            default:
                s1 = new String("UnknownRetCode");
                break;
            }
            signalEvent(new DSEEventObject(s1, this));
        }
        catch(DSEInvalidArgumentException _ex) { }
    }

    private void setBaudrate(int i)
    {
        baudrate = i;
    }

    private void setCheckDtrCts(boolean flag)
    {
        checkDtrCts = flag;
    }

    private void setDatabits(int i)
    {
        databits = i;
    }

    private void setParity(int i)
    {
        parity = i;
    }

    private void setPort(int i)
    {
        port = i;
    }

    private void setReset(boolean flag)
    {
        reset = flag;
    }

    private void setStopbits(int i)
    {
        stopbits = i;
    }

    private void setTimeout(int i)
    {
        timeout = i;
    }

    private void setTrace(boolean flag)
    {
        trace = flag;
    }

    public String toString()
    {
        String s = "<";
        s = s + getTagName() + " ";
        if(getName() != null && getName().length() > 0)
            s = s + "id=" + getName() + " ";
        if(getPort() != 0)
            s = s + "port=" + getPort() + " ";
        s = s + "baudRate=" + getBaudrate() + " ";
        if(getDatabits() == 7)
            s = s + "dataBits=7 ";
        else
        if(getDatabits() == 8)
            s = s + "dataBits=8 ";
        if(getStopbits() == 1)
            s = s + "stopBits=1 ";
        else
        if(getStopbits() == 2)
            s = s + "stopBits=2 ";
        if(getParity() == 0)
            s = s + "parity=none ";
        else
        if(getParity() == 2)
            s = s + "parity=even ";
        else
        if(getParity() == 1)
            s = s + "parity=odd ";
        if(getTimeout() != 0)
            s = s + "timeout=" + getTimeout() + " ";
        return s + (isCheckDtrCts() ? "checkLink=yes " : "checklink=no ") + (isReset() ? "reset=yes " : "reset=no ") + (isTrace() ? "trace=yes />" : "trace=no />");
    }

    private void unformatMessageReceived(Context context1, FormatElement formatelement)
        throws DSEInvalidRequestException, DSEInvalidArgumentException
    {
        if(Trace.doTrace("#JCHR", 1024, 2))
            Trace.trace("#JCHR", 1024, 2, null, "Unformatting the data read from the device.");
        formatelement.unformat(messageRead, context1);
    }

    private static final String COPYRIGHT = "Licensed Materials - Property of IBM 5648-D89 (C) Copyright IBM Corp. 2000 All Rights Reserved. US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp ";
    private int port;
    private int baudrate;
    private int databits;
    private int stopbits;
    private int parity;
    private boolean checkDtrCts;
    private boolean reset;
    private int timeout;
    private boolean trace;
    private String messageRead;
    private static AmperReaderDriver checkDriver;
    static final int READ = 0;
    static final int READ_AND_UNFORMAT = 1;
    private int actualAction;
    private Context context;
    private FormatElement format;
    public static final String portAttrib = "port";
    public static final String baudRateAttrib = "baudRate";
    public static final String dataBitsAttrib = "dataBits";
    public static final String stopBitsAttrib = "stopBits";
    public static final String parityAttrib = "parity";
    public static final String timeoutAttrib = "timeout";
    public static final String checkLinkAttrib = "checkLink";
    public static final String traceAttrib = "trace";
    public static final String resetAttrib = "reset";
    public static final String none = "none";
    public static final String odd = "odd";
    public static final String even = "even";
    public static final String COMPID = "#JCHR";
}
