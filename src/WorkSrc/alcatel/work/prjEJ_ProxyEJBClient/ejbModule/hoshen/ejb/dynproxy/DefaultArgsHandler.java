// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DefaultArgsHandler.java

package hoshen.ejb.dynproxy;

import org.apache.log4j.Logger;


// Referenced classes of package hoshen.ejb.dynproxy:
//            ArgsHandlerIF

public class DefaultArgsHandler
    implements ArgsHandlerIF
{
	private static Logger log = Logger.getLogger(DefaultArgsHandler.class);

    public DefaultArgsHandler()
    {
    }

    public Object packArgs(Object args)
    {
        log.debug("in DefaultArgsHandler.packArgs()");
        return args;
    }

    public Object unpackArgs(Object args)
    {
        log.debug("in DefaultArgsHandler.unpackArgs()");
        return args;
    }
}
