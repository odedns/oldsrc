// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DumpNameSpace.java

package work;

//import com.ibm.ejs.ras.*;
import com.ibm.ws.naming.ipbase.ContextID;
import com.ibm.ws.naming.ipbase.UuidContext;
import java.io.*;
import java.text.MessageFormat;
import java.util.*;
import javax.naming.*;

// Referenced classes of package com.ibm.websphere.naming:
//            CannotInstantiateObjectException

public class DumpNameSpace
{
    private class ContextInfo
    {

        public String _contextName;
        public String _sequence;

        public ContextInfo(String s, String s1)
        {
            _contextName = null;
            _sequence = null;
            _contextName = s1;
            _sequence = s;
        }
    }


    public static void main(String args[])
    {
        setResourceBundle();
        Object obj = null;
        String s = "localhost";
        String s1 = "900";
        String s2 = "com.ibm.websphere.naming.WsnInitialContextFactory";
        String s3 = "";
        String s4 = "jndi";
        String s5 = "legacydomainroot";
        String s6 = "legacy";
        int i = 1;
        for(int j = 0; j < args.length; j++)
            if(args[j].equals("-host"))
            {
                j++;
                s = args[j];
            } else
            if(args[j].equals("-port"))
            {
                j++;
                s1 = args[j];
            } else
            if(args[j].equals("-factory"))
            {
                j++;
                s2 = args[j];
            } else
            if(args[j].equals("-startAt"))
            {
                j++;
                s3 = args[j];
            } else
            if(args[j].equals("-report"))
            {
                j++;
                if(args[j].equals("short"))
                    i = 1;
                else
                if(args[j].equals("long"))
                    i = 2;
                else
                    System.out.println(fmtMsg("reportOptBad"));
            } else
            if(args[j].equals("-root"))
            {
                j++;
                if(args[j].equals("tree"))
                {
                    s5 = "treeinfrastructureroot";
                    s6 = "tree";
                } else
                if(args[j].equals("host"))
                {
                    s5 = "bootstraphostroot";
                    s6 = "host";
                } else
                if(args[j].equals("legacy"))
                {
                    s5 = "legacydomainroot";
                    s6 = "legacy";
                } else
                {
                    System.out.println(fmtMsg("treeOptBad"));
                }
            } else
            if(args[j].equals("-format"))
            {
                j++;
                if(args[j].equals("jndi") || args[j].equals("ins"))
                    s4 = args[j];
                else
                    System.out.println(fmtMsg("formatOptBad", new String[] {
                        "jndi", "ins"
                    }));
            } else
            if(args[j].equals("-?") || args[j].equals("?") || args[j].equals("-help") || args[j].equals("help"))
            {
                printHelp();
                return;
            }

        String s7 = providerUrl(s, s1);
        Context context = getStartingContext(System.out, s7, s2, s5, s3, s4);
        DumpNameSpace dumpnamespace = new DumpNameSpace(System.out, i);
        printCmdLineHeader(System.out, s7, s2, s6, s3, s4);
        dumpnamespace.doGenerateDump(context);
        System.exit(0);
    }

    private static Context getStartingContext(PrintStream printstream, String s, String s1, String s2, String s3, String s4)
    {
        Hashtable hashtable = new Hashtable();
        hashtable.put("java.naming.provider.url", s);
        hashtable.put("java.naming.factory.initial", s1);
        hashtable.put("com.ibm.websphere.naming.name.syntax", s4);
        hashtable.put("com.ibm.websphere.naming.namespaceroot", s2);
        Context context = null;
        try
        {
            printstream.println("");
            printstream.println(fmtMsg("gettingInitCtx"));
            InitialContext initialcontext = new InitialContext(hashtable);
            printstream.println(fmtMsg("gettingStartCtx"));
            Object obj = initialcontext.lookup(s3);
            context = (Context)obj;
        }
        catch(Exception exception)
        {
            printstream.println(fmtMsg("icErr"));
            printstream.println(fmtMsg("xcptInfo", new String[] {
                exception.toString()
            }));
            exception.printStackTrace(printstream);
            System.exit(1);
        }
        return context;
    }

    private static String providerUrl(String s, String s1)
    {
        return "iiop://" + s + ":" + s1;
    }

    private static void setResourceBundle()
    {
        if(_msgs == null)
            _msgs = ResourceBundle.getBundle("com.ibm.websphere.naming.DumpNameSpaceResourceBundle");
    }

    public DumpNameSpace()
    {
        _reportFmt = 1;
        init(System.out, 1);
    }

    public DumpNameSpace(PrintStream printstream, int i)
    {
        _reportFmt = 1;
        init(printstream, i);
    }

    private void init(PrintStream printstream, int i)
    {
        if(printstream != null)
            _out = printstream;
        else
            _out = System.out;
        _reportFmt = i;
        if(_reportFmt != 1 && _reportFmt != 2)
            _reportFmt = 1;
        setResourceBundle();
        _topLabel = fmtMsg("top");
        initForEachDump();
    }

    private void initForEachDump()
    {
        _visitedCxtNames = new Hashtable();
        _visitedCxtUuids = new Hashtable();
        _seq = 0L;
        _seqNum = null;
    }

    public void generateDump(Context context)
    {
        printApiHeader(context);
        doGenerateDump(context);
    }

    private void doGenerateDump(Context context)
    {
        initForEachDump();
        _out.println(" ");
        _out.println(fmtMsg("sepLine"));
        _out.println(fmtMsg("startDump"));
        _out.println(fmtMsg("sepLine"));
        _out.println(" ");
        dumpContext(_topLabel, context, "", null);
        _out.println(" ");
        _out.println(fmtMsg("sepLine"));
        _out.println(fmtMsg("endDump"));
        _out.println(fmtMsg("sepLine"));
        _out.println(" ");
    }

    private static void printCmdLineHeader(PrintStream printstream, String s, String s1, String s2, String s3, String s4)
    {
        String s5 = "   ";
        printstream.println(" ");
        printstream.println(fmtMsg("sepLine"));
        printstream.println(fmtMsg("nsDump"));
        printstream.println(s5 + fmtMsg("providerUrl", new String[] {
            s
        }));
        printstream.println(s5 + fmtMsg("ctxFactory", new String[] {
            s1
        }));
        printstream.println(s5 + fmtMsg("rootCtx", new String[] {
            s2
        }));
        if(s3.equals(""))
            printstream.println(s5 + fmtMsg("startingCtxRoot"));
        else
            printstream.println(s5 + fmtMsg("startingCtx", new String[] {
                s3
            }));
        printstream.println(s5 + fmtMsg("fmtRules", new String[] {
            s4
        }));
        printstream.println(s5 + fmtMsg("dumpTime", new String[] {
            (new Date()).toString()
        }));
        printstream.println(fmtMsg("sepLine"));
        printstream.println(" ");
    }

    private void printApiHeader(Context context)
    {
        String s = "   ";
        _out.println(" ");
        _out.println(fmtMsg("sepLine"));
        _out.println(fmtMsg("nsDump"));
        String s1 = fmtMsg("topNotAvail");
        try
        {
            s1 = context.getNameInNamespace();
        }
        catch(NamingException namingexception) { }
        _out.println(s + fmtMsg("startingCtx", new String[] {
            s1
        }));
        _out.println(s + fmtMsg("dumpTime", new String[] {
            (new Date()).toString()
        }));
        _out.println(fmtMsg("sepLine"));
        _out.println(" ");
    }

    private void dumpContext(String s, Context context, String s1, String s2)
    {
        printBinding(s, context, s1, s2);
        boolean flag = contextWasAlreadyDumped(context, s);
        if(!flag)
            try
            {
                for(NamingEnumeration namingenumeration = context.listBindings(""); namingenumeration.hasMore();)
                {
                    Object obj = null;
                    String s3 = null;
                    String s4 = null;
                    Object obj1 = null;
                    s2 = null;
                    try
                    {
                        Binding binding = (Binding)namingenumeration.next();
                        s3 = binding.getName();
                        s4 = binding.getClassName();
                        obj1 = binding.getObject();
                    }
                    catch(Exception ex)
                    {
                       
                     	ex.printStackTrace();                       
                       
                    }
                    String s5 = null;
                    if(s3 != null)
                        s5 = s + "/" + s3;
                    else
                        s5 = fmtMsg("bindingNameNotAvail");
                    if(s4 == null || s4.length() == 0)
                        s4 = fmtMsg("classNameNotAvail");
                    if(obj1 instanceof Context)
                        dumpContext(s5, (Context)obj1, s4, s2);
                    else
                        printBinding(s5, obj1, s4, s2);
                }

            }
            catch(NamingException namingexception)
            {
                _out.println(fmtMsg("listErr", new String[] {
                    s, namingexception.toString()
                }));
            }
    }

    private boolean contextWasAlreadyDumped(Context context, String s)
    {
        if(s.endsWith("nodeSystemNameSpaceRoot"))
        {
            _out.println(_seqNum + "   " + fmtMsg("nodeSNSRoot"));
            return true;
        }
        if(context instanceof UuidContext)
        {
            UuidContext uuidcontext = (UuidContext)context;
            String s2 = uuidcontext.getContextID().toString();
            Object obj1 = _visitedCxtUuids.get(s2);
            if(obj1 == null)
            {
                ContextInfo contextinfo2 = new ContextInfo(_seqNum, s);
                _visitedCxtUuids.put(s2.toString(), contextinfo2);
                return false;
            } else
            {
                ContextInfo contextinfo3 = (ContextInfo)obj1;
                _out.println(_seqNum + "   " + fmtMsg("revisitedCtx"));
                _out.println(_seqNum + "   " + fmtMsg("forCtxInfoSee", new String[] {
                    contextinfo3._sequence, contextinfo3._contextName
                }));
                return true;
            }
        }
        String s1 = null;
        try
        {
            s1 = context.getNameInNamespace();
        }
        catch(Exception exception)
        {
            _out.println(fmtMsg("getNameErr", new String[] {
                exception.toString()
            }));
        }
        Object obj = _visitedCxtNames.get(s1);
        if(obj == null)
        {
            ContextInfo contextinfo = new ContextInfo(_seqNum, s);
            _visitedCxtNames.put(s1, contextinfo);
            return false;
        } else
        {
            ContextInfo contextinfo1 = (ContextInfo)obj;
            _out.println(_seqNum + "   " + fmtMsg("revisitedCtx"));
            _out.println(_seqNum + "   " + fmtMsg("forCtxInfoSee", new String[] {
                contextinfo1._sequence, contextinfo1._contextName
            }));
            return true;
        }
    }

    private void printBinding(String s, Object obj, String s1, String s2)
    {
        _seqNum = getNextSeqNum();
        if(s2 != null)
            _out.println(_seqNum + " " + s2);
        switch(_reportFmt)
        {
        default:
            break;

        case 1: // '\001'
            String s3 = getPaddedString(s, 50);
            if(s3.length() <= 50)
            {
                _out.println(_seqNum + s3 + " " + s1);
            } else
            {
                _out.println(_seqNum + s3);
                _out.println(_seqNum + getPaddedString(" ", 51) + s1);
            }
            break;

        case 2: // '\002'
            String s4 = fmtMsg("null");
            String s5 = "";
            if(obj != null)
            {
                s4 = obj.getClass().getName();
                s5 = obj.toString();
            }
            _out.println(" ");
            _out.println(_seqNum + s);
            _out.println(_seqNum + "   " + fmtMsg("boundType", new String[] {
                s1
            }));
            _out.println(_seqNum + "   " + fmtMsg("localType", new String[] {
                s4
            }));
            if(obj instanceof UuidContext)
            {
                UuidContext uuidcontext = (UuidContext)obj;
                String s6 = uuidcontext.getContextID().toString();
                _out.println(_seqNum + "   " + fmtMsg("ctxId", new String[] {
                    s6
                }));
            }
            _out.println(_seqNum + "   " + fmtMsg("objToString", new String[] {
                s5
            }));
            break;
        }
    }

    private String getNextSeqNum()
    {
        _seq++;
        if(_seq > 0x1869fL)
            _seq = 0L;
        StringBuffer stringbuffer = new StringBuffer(6);
        stringbuffer.insert(0, " ");
        stringbuffer.insert(0, _seq);
        for(; stringbuffer.length() < 6; stringbuffer.insert(0, " "));
        return stringbuffer.toString();
    }

    private String getPaddedString(String s, int i)
    {
        int j = s.length();
        if(j >= i)
            return s;
        StringBuffer stringbuffer = new StringBuffer(i);
        stringbuffer.insert(0, s);
        for(int k = j; k < i; k++)
            stringbuffer.insert(k, " ");

        return stringbuffer.toString();
    }

    private static String fmtMsg(String s)
    {
        String s1 = null;
        try
        {
            if(_msgs != null)
                return MessageFormat.format(_msgs.getString(s), (Object[])null);
            s1 = "Message table not found.";
        }
        catch(MissingResourceException missingresourceexception)
        {
            s1 = "Message or message table not found.";
        }
        return s1;
    }

    private static String fmtMsg(String s, String as[])
    {
        String s1 = null;
        try
        {
            if(_msgs != null)
                return MessageFormat.format(_msgs.getString(s), as);
            s1 = "Message table not found.";
        }
        catch(MissingResourceException missingresourceexception)
        {
            s1 = "Message or message table not found.";
        }
        return s1;
    }

    private static void printHelp()
    {
        System.out.println(" ");
        System.out.println(fmtMsg("sepLine"));
        System.out.println(fmtMsg("cmdLineUsage"));
        System.out.println(fmtMsg("sepLine"));
    }

    public static final int SHORT = 1;
    public static final int LONG = 2;
    private static final String THIS_CONTEXT = "";
    private static final String BLANK_LINE = " ";
    private static final String INDENT_AMOUNT = "   ";
    private static final String COMPONENT_SEPARATOR = "/";
    private static final String BUNDLE_NAME = "com.ibm.websphere.naming.DumpNameSpaceResourceBundle";
    private static ResourceBundle _msgs = null;
    private PrintStream _out;
    private int _reportFmt;
    private String _topLabel;
    private long _seq;
    private String _seqNum;
    private Hashtable _visitedCxtNames;
    private Hashtable _visitedCxtUuids;

}
