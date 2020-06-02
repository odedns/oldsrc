// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JVMID.java

package weblogic.rjvm;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import weblogic.rmi.extensions.HostID;
import weblogic.rmi.internal.ReferenceConstants;
import weblogic.utils.*;

// Referenced classes of package weblogic.rjvm:
//            LocalRJVM, Protocol

public final class JVMID
    implements HostID, ReferenceConstants, Externalizable
{

    public JVMID()
    {
        preambleAsString = null;
    }

    JVMID(InetAddress inetaddress, int ai[])
    {
        this(inetaddress, ai, true);
    }

    private JVMID(InetAddress inetaddress, int ai[], boolean flag)
    {
        preambleAsString = null;
        if(flag)
        {
            differentiator = 0L;
        } else
        {
            byte abyte0[] = LocalRJVM.getLocalRJVM().getPublicKey();
            differentiator = 0L;
            for(int i = 0; i < abyte0.length; i++)
                differentiator = differentiator << 8 ^ differentiator ^ (long)abyte0[i];

        }
        hostAddress = inetaddress.getHostAddress();
        setHasHostAddressFlag();
        byte abyte1[] = inetaddress.getAddress();
        inetAddress = inetaddress;
        rawAddress = ((abyte1[0] & 0xff) << 24) + ((abyte1[1] & 0xff) << 16) + ((abyte1[2] & 0xff) << 8) + (abyte1[3] & 0xff);
        ports = ai;
        router = null;
    }

    public InetAddress address()
    {
        if(inetAddress == null)
            try
            {
                inetAddress = InetAddress.getByName(hostAddress);
            }
            catch(UnknownHostException unknownhostexception)
            {
                throw new NestedError("This address was valid earlier, but now we get: ", unknownhostexception);
            }
        return inetAddress;
    }

    private static String addressToString(InetAddress inetaddress)
    {
        if(localHost == null)
            try
            {
                localHost = InetAddress.getLocalHost();
            }
            catch(UnknownHostException unknownhostexception)
            {
                throw new AssertionError(unknownhostexception);
            }
        if(inetaddress.equals(localHost))
            return "localhost/127.0.0.1";
        else
            return inetaddress.getHostAddress();
    }

    private static JVMID createJVMID(long l, String s, int i, int ai[], JVMID jvmid, String s1)
        throws IOException
    {
        JVMID jvmid1 = new JVMID();
        jvmid1.differentiator = l;
        jvmid1.hostAddress = s;
        if(jvmid1.hasHostAddress())
            jvmid1.setHasHostAddressFlag();
        jvmid1.rawAddress = i;
        jvmid1.ports = ai;
        jvmid1.router = jvmid;
        if(jvmid1.hasRouter())
            jvmid1.setHasRouterFlag();
        jvmid1.clusterAddress = s1;
        if(jvmid1.hasClusterAddress())
            jvmid1.setHasClusterAddressFlag();
        jvmid1.preambleAsString = null;
        UnsyncStringBuffer unsyncstringbuffer = new UnsyncStringBuffer(Integer.toString(jvmid1.rawAddress >> 24 & 0xff));
        unsyncstringbuffer.append('.').append(jvmid1.rawAddress >> 16 & 0xff);
        unsyncstringbuffer.append('.').append(jvmid1.rawAddress >> 8 & 0xff);
        unsyncstringbuffer.append('.').append(jvmid1.rawAddress & 0xff);
        jvmid1.inetAddress = InetAddress.getByName(unsyncstringbuffer.toString());
        return jvmid1;
    }

    public boolean equals(Object obj)
    {
        if(obj == this)
            return true;
        if(obj instanceof JVMID)
        {
            JVMID jvmid = (JVMID)obj;
            if(differentiator == jvmid.differentiator)
                return rawAddress == jvmid.rawAddress;
            else
                return false;
        } else
        {
            return false;
        }
    }

    public final String getClusterAddress()
    {
        return clusterAddress;
    }

    public long getDifferentiator()
    {
        return differentiator;
    }

    public String getFormattedID(byte abyte0[], char c)
    {
        String s = "";
        for(int i = 0; i < abyte0.length; i++)
        {
            if(i != 0)
                s = s + c;
            switch(abyte0[i])
            {
            default:
                break;

            case 0: // '\0'
                s = s + differentiator;
                break;

            case 1: // '\001'
                if(hostAddress.indexOf(".") == -1)
                    s = s + hostAddress;
                else
                    s = s + rawAddress;
                break;

            case 2: // '\002'
                s = s + ports[0];
                break;

            case 3: // '\003'
                s = s + ports[2];
                break;
            }
        }

        return s;
    }

    public String getHostAddress()
    {
        return hostAddress;
    }

    public int getPort(Protocol protocol)
    {
        byte byte0 = protocol.toByte();
        if(ports != null && ports.length > byte0)
            return ports[byte0];
        else
            return -1;
    }

    public JVMID getRouter()
    {
        return router;
    }

    private boolean hasClusterAddress()
    {
        return (flags & 0x4) != 0;
    }

    private boolean hasHostAddress()
    {
        return (flags & 0x1) != 0;
    }

    private boolean hasRouter()
    {
        return (flags & 0x2) != 0;
    }

    public int hashCode()
    {
        if(hashCodeValue == 0)
            hashCodeValue = (int)differentiator ^ rawAddress;
        return hashCodeValue;
    }

    public boolean isBootstrapping()
    {
        return differentiator == 0L;
    }

    public boolean isServer()
    {
        return ports != null;
    }

    public static JVMID localID()
    {
        if(localID == null)
            synchronized(class$weblogic$rjvm$JVMID == null ? (class$weblogic$rjvm$JVMID = class$("weblogic.rjvm.JVMID")) : class$weblogic$rjvm$JVMID)
            {
                if(localID == null)
                    setLocalID(null);
            }
        return localID;
    }

    public String objectToString()
    {
        UnsyncStringBuffer unsyncstringbuffer = new UnsyncStringBuffer(Long.toString(differentiator));
        if(isServer())
        {
            if(preambleAsString == null)
            {
                unsyncstringbuffer.append("/").append(hostAddress);
                unsyncstringbuffer.append("/").append(clusterAddress);
                unsyncstringbuffer.append("/").append(Integer.toString(rawAddress));
                unsyncstringbuffer.append("/").append(ports.length);
                for(int i = 0; i < ports.length; i++)
                    unsyncstringbuffer.append("/").append(ports[i]);

                if(router == null)
                    preambleAsString = unsyncstringbuffer.toString();
                else
                    preambleAsString = unsyncstringbuffer.append("/").append(router.objectToString()).toString();
            }
            return preambleAsString;
        }
        unsyncstringbuffer.append("/").append(Integer.toString(rawAddress));
        unsyncstringbuffer.append("/").append('0');
        if(router == null)
        {
            return unsyncstringbuffer.toString();
        } else
        {
            unsyncstringbuffer.append("/").append(router.objectToString());
            return unsyncstringbuffer.toString();
        }
    }

    public int[] ports()
    {
        return ports;
    }

    public boolean precedes(JVMID jvmid)
    {
        return differentiator > jvmid.differentiator || differentiator == jvmid.differentiator && rawAddress > jvmid.rawAddress;
    }

    public void readExternal(ObjectInput objectinput)
        throws IOException, ClassNotFoundException
    {
        flags = objectinput.readByte();
        differentiator = objectinput.readLong();
        if(hasHostAddress())
            hostAddress = objectinput.readUTF();
        rawAddress = objectinput.readInt();
        ports = null;
        int i = objectinput.readInt();
        if(i > 0)
        {
            ports = new int[i];
            preambleAsString = null;
            for(int j = 0; j < i; j++)
                ports[j] = objectinput.readInt();

        }
        if(hasRouter())
        {
            router = new JVMID();
            router.readExternal(objectinput);
        }
        if(hasClusterAddress())
            clusterAddress = objectinput.readUTF();
    }

    public final void setClusterAddress(String s)
    {
        clusterAddress = s;
        if(s != null)
            setHasClusterAddressFlag();
    }

    private void setHasClusterAddressFlag()
    {
        flags += 4;
    }

    private void setHasHostAddressFlag()
    {
        flags++;
    }

    private void setHasRouterFlag()
    {
        flags += 2;
    }

    public static void setLocalID(String s, int ai[])
        throws UnknownHostException
    {
        Object obj = null;
        if(s == null)
        {
            InetAddress inetaddress = InetAddress.getLocalHost();
            localID = new JVMID(inetaddress, ai, false);
        } else
        {
            InetAddress inetaddress1 = InetAddress.getByName(s);
            localID = new JVMID(inetaddress1, ai, false);
            localID.hostAddress = s;
        }
    }

    private static void setLocalID(int ai[])
    {
        InetAddress inetaddress;
        try
        {
            inetaddress = InetAddress.getLocalHost();
        }
        catch(UnknownHostException unknownhostexception)
        {
            unknownhostexception.printStackTrace();
            throw new NestedError("Local host not known?!", unknownhostexception);
        }
        catch(SecurityException _ex)
        {
            try
            {
                inetaddress = InetAddress.getByName("0.0.0.0");
            }
            catch(Throwable throwable)
            {
                throw new NestedError("Failed to even get InetAddress for unknown host: ", throwable);
            }
        }
        localID = new JVMID(inetaddress, ai, false);
    }

    public void setRouter(JVMID jvmid)
    {
        if(router == null)
        {
            router = jvmid;
            if(jvmid != null)
                setHasRouterFlag();
        }
    }

    public static JVMID stringToObject(String s)
        throws IOException
    {
        int i = s.indexOf(47, 0);
        if(i == -1)
            throw new IllegalArgumentException("Bad VM ID: '" + s + "'");
        long l = Long.valueOf(s.substring(0, i)).longValue();
        int j = ++i;
        i = s.indexOf(47, j);
        if(i == -1)
            throw new IllegalArgumentException("Bad VM ID: '" + s + "'");
        String s1 = s.substring(j, i);
        j = ++i;
        i = s.indexOf(47, j);
        if(i == -1)
            throw new IllegalArgumentException("Bad VM ID: '" + s + "'");
        String s2 = s.substring(j, i);
        j = ++i;
        i = s.indexOf(47, j);
        if(i == -1)
            throw new IllegalArgumentException("Bad VM ID: '" + s + "'");
        int i1 = Integer.valueOf(s.substring(j, i)).intValue();
        j = ++i;
        i = s.indexOf(47, j);
        if(i == -1)
            throw new IllegalArgumentException("Bad VM ID: '" + s + "'");
        int j1 = Integer.valueOf(s.substring(j, i)).intValue();
        int ai[] = null;
        if(j1 > 0)
        {
            ai = new int[j1];
            for(int k1 = 0; k1 < j1; k1++)
            {
                int k = ++i;
                i = s.indexOf(47, k);
                if(i == -1)
                    i = s.length();
                ai[k1] = Integer.valueOf(s.substring(k, i)).intValue();
            }

            return createJVMID(l, s1, i1, ai, null, s2);
        }
        JVMID jvmid = null;
        if(++i < s.length())
            jvmid = stringToObject(s.substring(i));
        return createJVMID(l, s1, i1, ai, jvmid, s2);
    }

    public String toPrettyString()
    {
        try
        {
            UnsyncStringBuffer unsyncstringbuffer = new UnsyncStringBuffer(addressToString(address()));
            if(ports != null)
            {
                for(byte byte0 = 0; byte0 < ports.length; byte0++)
                    if(ports[byte0] != -1)
                    {
                        unsyncstringbuffer.append(' ').append(Protocol.getProtocolName(byte0));
                        unsyncstringbuffer.append(':').append(ports[byte0]);
                    }

            }
            unsyncstringbuffer.append(' ').append(differentiator);
            if(hasClusterAddress())
            {
                unsyncstringbuffer.append(":");
                unsyncstringbuffer.append(clusterAddress);
            }
            return unsyncstringbuffer.toString();
        }
        catch(SecurityException _ex)
        {
            return toString();
        }
    }

    public String toString()
    {
        UnsyncStringBuffer unsyncstringbuffer = new UnsyncStringBuffer(Long.toString(differentiator));
        unsyncstringbuffer.append(isServer() ? 'S' : 'C').append(':');
        unsyncstringbuffer.append(hostAddress);
        if(ports != null)
        {
            unsyncstringbuffer.append(":[");
            for(int i = 0; i < ports.length; i++)
            {
                if(i > 0)
                    unsyncstringbuffer.append(',');
                unsyncstringbuffer.append(ports[i]);
            }

            unsyncstringbuffer.append(']');
        }
        if(hasClusterAddress())
        {
            unsyncstringbuffer.append(":");
            unsyncstringbuffer.append(clusterAddress);
        }
        return unsyncstringbuffer.toString();
    }

    public void writeExternal(ObjectOutput objectoutput)
        throws IOException
    {
        objectoutput.writeByte(flags);
        objectoutput.writeLong(differentiator);
        if(hasHostAddress())
            objectoutput.writeUTF(hostAddress);
        objectoutput.writeInt(rawAddress);
        if(ports == null)
        {
            objectoutput.writeInt(0);
        } else
        {
            int i = ports.length;
            objectoutput.writeInt(i);
            for(int j = 0; j < i; j++)
                objectoutput.writeInt(ports[j]);

        }
        if(hasRouter())
            router.writeExternal(objectoutput);
        if(hasClusterAddress())
            objectoutput.writeUTF(clusterAddress);
    }

    static Class class$(String s)
    {
        try
        {
            return Class.forName(s);
        }
        catch(ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    private static final long serialVersionUID = 0xdc49c23ede121e2aL;
    public static final int INVALID_PORT = -1;
    private static final long INVALID_DIFFERENTIATOR = 0L;
    private static final byte HAS_HOST_ADDRESS = 1;
    private static final byte HAS_ROUTER = 2;
    private static final byte HAS_CLUSTER_ADDRESS = 4;
    private byte flags;
    private static JVMID localID = null;
    private String hostAddress;
    private String clusterAddress;
    private long differentiator;
    private int rawAddress;
    private int ports[];
    private JVMID router;
    private transient InetAddress inetAddress;
    private transient String preambleAsString;
    private transient int hashCodeValue;
    private static InetAddress localHost = null;
    public static final byte DIFFERENTIATOR = 0;
    public static final byte LISTEN_ADDRESS = 1;
    public static final byte HTTP_PORT = 2;
    public static final byte HTTPS_PORT = 3;
    static Class class$weblogic$rjvm$JVMID; /* synthetic field */

}
