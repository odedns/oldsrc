// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MQException.java

package com.ibm.mq;

import java.io.OutputStreamWriter;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.resource.NotSupportedException;
import javax.resource.ResourceException;
import javax.resource.spi.ApplicationServerInternalException;
import javax.resource.spi.CommException;
import javax.resource.spi.EISSystemException;
import javax.resource.spi.LocalTransactionException;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.resource.spi.ResourceAllocationException;

import com.ibm.mqservices.Trace;

// Referenced classes of package com.ibm.mq:
//            ReasonCodeInfo

public class MQException extends Exception
{

    public Throwable initCause(Throwable throwable)
    {
        if(throwable == null)
        {
            underlyingException = null;
            underlyingSet = true;
        } else
        {
            if(throwable.equals(this))
                throw new IllegalArgumentException();
            if(underlyingSet)
                throw new java.lang.IllegalStateException();
            underlyingException = throwable;
            underlyingSet = true;
        }
        return this;
    }

    public Throwable getCause()
    {
        return underlyingException;
    }

    public MQException(int i, int j, Object obj)
    {
        ostrMessage = null;
        underlyingException = null;
        underlyingSet = false;
        completionCode = i;
        reasonCode = j;
        exceptionSource = obj;
        msgId = 0;
        numInserts = 0;
        insert1 = null;
        insert2 = null;
        if(Trace.isOn())
        {
            Trace.entry(this, "MQException constructor(cc, rc, source)");
            Trace.trace(2, this, "common/javabase/com/ibm/mq/MQException.java, java, j530, j530-L020820  02/08/09 13:29:42 @(#) 1.39");
            Trace.trace(2, this, "cc     = " + i);
            Trace.trace(2, this, "rc     = " + j);
            Trace.trace(2, this, "source = " + obj);
        }
        if(log != null && exceptionMessages != null)
            log(getMessage());
        if(Trace.isOn())
            Trace.exit(this, "MQException constructor");
    }

    protected MQException(int i, int j, Object obj, int k)
    {
        ostrMessage = null;
        underlyingException = null;
        underlyingSet = false;
        completionCode = i;
        reasonCode = j;
        exceptionSource = obj;
        msgId = k;
        numInserts = 0;
        insert1 = null;
        insert2 = null;
        if(Trace.isOn())
        {
            Trace.entry(this, "MQException constructor(cc, rc, source, msgid)");
            Trace.trace(2, this, "common/javabase/com/ibm/mq/MQException.java, java, j530, j530-L020820  02/08/09 13:29:42 @(#) 1.39");
            Trace.trace(2, this, "cc     = " + i);
            Trace.trace(2, this, "rc     = " + j);
            Trace.trace(2, this, "source = " + obj);
            Trace.trace(2, this, "msgId  = " + k);
        }
        if(log != null && exceptionMessages != null)
            log(getMessage());
        if(Trace.isOn())
        {
            Trace.trace(2, this, "Explanation is '" + getMessage() + "'");
            Trace.exit(this, "MQException constructor");
        }
    }

    protected MQException(int i, int j, Object obj, int k, String s)
    {
        ostrMessage = null;
        underlyingException = null;
        underlyingSet = false;
        completionCode = i;
        reasonCode = j;
        exceptionSource = obj;
        msgId = k;
        numInserts = 1;
        insert1 = s;
        insert2 = null;
        if(Trace.isOn())
        {
            Trace.entry(this, "MQException constructor(cc, rc, source, msgid, insrt)");
            Trace.trace(2, this, "common/javabase/com/ibm/mq/MQException.java, java, j530, j530-L020820  02/08/09 13:29:42 @(#) 1.39");
            Trace.trace(2, this, "cc     = " + i);
            Trace.trace(2, this, "rc     = " + j);
            Trace.trace(2, this, "source = " + obj);
            Trace.trace(2, this, "msgId  = " + k);
            Trace.trace(2, this, "insrt  = '" + s + "'");
        }
        if(log != null && exceptionMessages != null)
            log(getMessage());
        if(Trace.isOn())
        {
            Trace.trace(2, this, "Explanation is '" + getMessage() + "'");
            Trace.exit(this, "MQException constructor");
        }
    }

    protected MQException(int i, int j, Object obj, int k, String s, String s1)
    {
        ostrMessage = null;
        underlyingException = null;
        underlyingSet = false;
        completionCode = i;
        reasonCode = j;
        exceptionSource = obj;
        msgId = k;
        numInserts = 2;
        insert1 = s;
        insert2 = s1;
        if(Trace.isOn())
        {
            Trace.entry(this, "MQException constructor(cc, rc, source, msgid, insrt1, insrt2)");
            Trace.trace(2, this, "common/javabase/com/ibm/mq/MQException.java, java, j530, j530-L020820  02/08/09 13:29:42 @(#) 1.39");
            Trace.trace(2, this, "cc     = " + i);
            Trace.trace(2, this, "rc     = " + j);
            Trace.trace(2, this, "source = " + obj);
            Trace.trace(2, this, "msgId  = " + k);
            Trace.trace(2, this, "insrt1 = '" + s + "'");
            Trace.trace(2, this, "insrt2 = '" + s1 + "'");
        }
        if(log != null && exceptionMessages != null)
            log(getMessage());
        if(Trace.isOn())
        {
            Trace.trace(2, this, "Explanation is '" + getMessage() + "'");
            Trace.exit(this, "MQException constructor");
        }
    }

    private void log(String s)
    {
    	/*
        try
        {
            log.write(s);
            log.write(10);
            log.flush();
        }
        catch(IOException ioexception)
        {
            if(Trace.isOn())
                Trace.trace(1, this, "Exception writing to log stream: " + ioexception);
        }
        */
    }

    public String getMessage()
    {
        if(ostrMessage == null)
            if(exceptionMessages == null)
                ostrMessage = "Message catalog not found";
            else
            if(msgId == 0)
            {
                String s = exceptionMessages.getString(MQJE001b_AS_STRING);
                String as[] = new String[2];
                as[0] = Integer.toString(completionCode);
                as[1] = Integer.toString(reasonCode);
                ostrMessage = MessageFormat.format(s, as);
            } else
            {
                String s1 = exceptionMessages.getString(Integer.toString(msgId));
                if(numInserts > 0)
                {
                    String as1[] = new String[numInserts];
                    if(numInserts > 0)
                        as1[0] = insert1;
                    if(numInserts > 1)
                        as1[1] = insert2;
                    s1 = MessageFormat.format(s1, as1);
                }
                String s2 = exceptionMessages.getString(MQJE001_AS_STRING);
                String as2[] = new String[3];
                as2[0] = Integer.toString(completionCode);
                as2[1] = Integer.toString(reasonCode);
                as2[2] = s1;
                ostrMessage = MessageFormat.format(s2, as2);
            }
        return ostrMessage;
    }

    public static String getNLSMsg(int i)
    {
        String s;
        if(exceptionMessages != null)
            s = exceptionMessages.getString(Integer.toString(i));
        else
            s = "Message catalog not found";
        return s;
    }

    public static String getNLSMsg(int i, String s)
    {
        String s1;
        if(exceptionMessages != null)
        {
            String s2 = exceptionMessages.getString(Integer.toString(i));
            String as[] = new String[1];
            as[0] = s;
            s1 = MessageFormat.format(s2, as);
        } else
        {
            s1 = "Message catalog not found";
        }
        return s1;
    }

    public static String getNLSMsg(int i, String s, String s1)
    {
        String s2;
        if(exceptionMessages != null)
        {
            String s3 = exceptionMessages.getString(Integer.toString(i));
            String as[] = new String[2];
            as[0] = s;
            as[1] = s1;
            s2 = " " + MessageFormat.format(s3, as);
        } else
        {
            s2 = " Message catalog not found";
        }
        return s2;
    }

    ResourceException getResourceException()
    {
        return getResourceException(getMessage());
    }

    ResourceException getResourceException(String s)
    {
        int i = ReasonCodeInfo.getResourceExceptionClass(reasonCode);
        Object obj;
        switch(i)
        {
        case 0: // '\0'
        case 15: // '\017'
            obj = new ResourceException(s, "" + reasonCode);
            break;

        case 1: // '\001'
            obj = new ApplicationServerInternalException(s, "" + reasonCode);
            break;

        case 2: // '\002'
            obj = new CommException(s, "" + reasonCode);
            break;

        case 3: // '\003'
            obj = new EISSystemException(s, "" + reasonCode);
            break;

        case 4: // '\004'
            obj = new javax.resource.spi.IllegalStateException(s, "" + reasonCode);
            break;

        case 5: // '\005'
            obj = new LocalTransactionException(s, "" + reasonCode);
            break;

        case 6: // '\006'
            obj = new NotSupportedException(s, "" + reasonCode);
            break;

        case 7: // '\007'
            obj = new ResourceAdapterInternalException(s, "" + reasonCode);
            break;

        case 8: // '\b'
            obj = new ResourceAllocationException(s, "" + reasonCode);
            break;

        case 9: // '\t'
            obj = new javax.resource.spi.SecurityException(s, "" + reasonCode);
            break;

        case 10: // '\n'
        case 11: // '\013'
        case 12: // '\f'
        case 13: // '\r'
        case 14: // '\016'
        default:
            Trace.trace(2, this, "Bad ResourceException Class encountered");
            obj = new ResourceAdapterInternalException(s, "" + reasonCode);
            break;
        }
        ((ResourceException) (obj)).setLinkedException(this);
        return ((ResourceException) (obj));
    }

    private static final String sccsid = "common/javabase/com/ibm/mq/MQException.java, java, j530, j530-L020820  02/08/09 13:29:42 @(#) 1.39";
    private static final String copyright_notice = "Licensed Materials - Property of IBM 5648-C60 5694-137 (c) Copyright IBM Corp. 1997, 1999, 2001   All Rights Reserved. US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.";
    public static final int MQCC_WARNING = 1;
    public static final int MQCC_FAILED = 2;
    public static final int MQCC_UNKNOWN = -1;
    public static final int MQCC_OK = 0;
    public static final int MQRC_NONE = 0;
    public static final int MQRC_ALIAS_BASE_Q_TYPE_ERROR = 2001;
    public static final int MQRC_ALREADY_CONNECTED = 2002;
    public static final int MQRC_BACKED_OUT = 2003;
    public static final int MQRC_BUFFER_ERROR = 2004;
    public static final int MQRC_BUFFER_LENGTH_ERROR = 2005;
    public static final int MQRC_CHAR_ATTR_LENGTH_ERROR = 2006;
    public static final int MQRC_CHAR_ATTRS_ERROR = 2007;
    public static final int MQRC_CHAR_ATTRS_TOO_SHORT = 2008;
    public static final int MQRC_CONNECTION_BROKEN = 2009;
    public static final int MQRC_DATA_LENGTH_ERROR = 2010;
    public static final int MQRC_DYNAMIC_Q_NAME_ERROR = 2011;
    public static final int MQRC_ENVIRONMENT_ERROR = 2012;
    public static final int MQRC_EXPIRY_ERROR = 2013;
    public static final int MQRC_FEEDBACK_ERROR = 2014;
    public static final int MQRC_GET_INHIBITED = 2016;
    public static final int MQRC_HANDLE_NOT_AVAILABLE = 2017;
    public static final int MQRC_HCONN_ERROR = 2018;
    public static final int MQRC_HOBJ_ERROR = 2019;
    public static final int MQRC_INHIBIT_VALUE_ERROR = 2020;
    public static final int MQRC_INT_ATTR_COUNT_ERROR = 2021;
    public static final int MQRC_INT_ATTR_COUNT_TOO_SMALL = 2022;
    public static final int MQRC_INT_ATTRS_ARRAY_ERROR = 2023;
    public static final int MQRC_SYNCPOINT_LIMIT_REACHED = 2024;
    public static final int MQRC_MAX_CONNS_LIMIT_REACHED = 2025;
    public static final int MQRC_MD_ERROR = 2026;
    public static final int MQRC_MISSING_REPLY_TO_Q = 2027;
    public static final int MQRC_MSG_TYPE_ERROR = 2029;
    public static final int MQRC_MSG_TOO_BIG_FOR_Q = 2030;
    public static final int MQRC_MSG_TOO_BIG_FOR_Q_MGR = 2031;
    public static final int MQRC_NO_MSG_AVAILABLE = 2033;
    public static final int MQRC_NO_MSG_UNDER_CURSOR = 2034;
    public static final int MQRC_NOT_AUTHORIZED = 2035;
    public static final int MQRC_NOT_OPEN_FOR_BROWSE = 2036;
    public static final int MQRC_NOT_OPEN_FOR_INPUT = 2037;
    public static final int MQRC_NOT_OPEN_FOR_INQUIRE = 2038;
    public static final int MQRC_NOT_OPEN_FOR_OUTPUT = 2039;
    public static final int MQRC_NOT_OPEN_FOR_SET = 2040;
    public static final int MQRC_OBJECT_CHANGED = 2041;
    public static final int MQRC_OBJECT_IN_USE = 2042;
    public static final int MQRC_OBJECT_TYPE_ERROR = 2043;
    public static final int MQRC_OD_ERROR = 2044;
    public static final int MQRC_OPTION_NOT_VALID_FOR_TYPE = 2045;
    public static final int MQRC_OPTIONS_ERROR = 2046;
    public static final int MQRC_PERSISTENCE_ERROR = 2047;
    public static final int MQRC_PERSISTENT_NOT_ALLOWED = 2048;
    public static final int MQRC_PRIORITY_EXCEEDS_MAXIMUM = 2049;
    public static final int MQRC_PRIORITY_ERROR = 2050;
    public static final int MQRC_PUT_INHIBITED = 2051;
    public static final int MQRC_Q_DELETED = 2052;
    public static final int MQRC_Q_FULL = 2053;
    public static final int MQRC_Q_NOT_EMPTY = 2055;
    public static final int MQRC_Q_SPACE_NOT_AVAILABLE = 2056;
    public static final int MQRC_Q_TYPE_ERROR = 2057;
    public static final int MQRC_Q_MGR_NAME_ERROR = 2058;
    public static final int MQRC_Q_MGR_NOT_AVAILABLE = 2059;
    public static final int MQRC_REPORT_OPTIONS_ERROR = 2061;
    public static final int MQRC_SECOND_MARK_NOT_ALLOWED = 2062;
    public static final int MQRC_SECURITY_ERROR = 2063;
    public static final int MQRC_SELECTOR_COUNT_ERROR = 2065;
    public static final int MQRC_SELECTOR_LIMIT_EXCEEDED = 2066;
    public static final int MQRC_SELECTOR_ERROR = 2067;
    public static final int MQRC_SELECTOR_NOT_FOR_TYPE = 2068;
    public static final int MQRC_SIGNAL_OUTSTANDING = 2069;
    public static final int MQRC_SIGNAL_REQUEST_ACCEPTED = 2070;
    public static final int MQRC_STORAGE_NOT_AVAILABLE = 2071;
    public static final int MQRC_SYNCPOINT_NOT_AVAILABLE = 2072;
    public static final int MQRC_TRIGGER_CONTROL_ERROR = 2075;
    public static final int MQRC_TRIGGER_DEPTH_ERROR = 2076;
    public static final int MQRC_TRIGGER_MSG_PRIORITY_ERR = 2077;
    public static final int MQRC_TRIGGER_TYPE_ERROR = 2078;
    public static final int MQRC_TRUNCATED_MSG_ACCEPTED = 2079;
    public static final int MQRC_TRUNCATED_MSG_FAILED = 2080;
    public static final int MQRC_UNKNOWN_ALIAS_BASE_Q = 2082;
    public static final int MQRC_UNKNOWN_OBJECT_NAME = 2085;
    public static final int MQRC_UNKNOWN_OBJECT_Q_MGR = 2086;
    public static final int MQRC_UNKNOWN_REMOTE_Q_MGR = 2087;
    public static final int MQRC_WAIT_INTERVAL_ERROR = 2090;
    public static final int MQRC_XMIT_Q_TYPE_ERROR = 2091;
    public static final int MQRC_XMIT_Q_USAGE_ERROR = 2092;
    public static final int MQRC_NOT_OPEN_FOR_PASS_ALL = 2093;
    public static final int MQRC_NOT_OPEN_FOR_PASS_IDENT = 2094;
    public static final int MQRC_NOT_OPEN_FOR_SET_ALL = 2095;
    public static final int MQRC_NOT_OPEN_FOR_SET_IDENT = 2096;
    public static final int MQRC_CONTEXT_HANDLE_ERROR = 2097;
    public static final int MQRC_CONTEXT_NOT_AVAILABLE = 2098;
    public static final int MQRC_SIGNAL1_ERROR = 2099;
    public static final int MQRC_OBJECT_ALREADY_EXISTS = 2100;
    public static final int MQRC_OBJECT_DAMAGED = 2101;
    public static final int MQRC_RESOURCE_PROBLEM = 2102;
    public static final int MQRC_ANOTHER_Q_MGR_CONNECTED = 2103;
    public static final int MQRC_UNKNOWN_REPORT_OPTION = 2104;
    public static final int MQRC_STORAGE_CLASS_ERROR = 2105;
    public static final int MQRC_COD_NOT_VALID_FOR_XCF_Q = 2106;
    public static final int MQRC_XWAIT_CANCELED = 2107;
    public static final int MQRC_XWAIT_ERROR = 2108;
    public static final int MQRC_SUPPRESSED_BY_EXIT = 2109;
    public static final int MQRC_FORMAT_ERROR = 2110;
    public static final int MQRC_SOURCE_CCSID_ERROR = 2111;
    public static final int MQRC_SOURCE_INTEGER_ENC_ERROR = 2112;
    public static final int MQRC_SOURCE_DECIMAL_ENC_ERROR = 2113;
    public static final int MQRC_SOURCE_FLOAT_ENC_ERROR = 2114;
    public static final int MQRC_TARGET_CCSID_ERROR = 2115;
    public static final int MQRC_TARGET_INTEGER_ENC_ERROR = 2116;
    public static final int MQRC_TARGET_DECIMAL_ENC_ERROR = 2117;
    public static final int MQRC_TARGET_FLOAT_ENC_ERROR = 2118;
    public static final int MQRC_NOT_CONVERTED = 2119;
    public static final int MQRC_CONVERTED_MSG_TOO_BIG = 2120;
    public static final int MQRC_TRUNCATED = 2120;
    public static final int MQRC_NO_EXTERNAL_PARTICIPANTS = 2121;
    public static final int MQRC_PARTICIPANT_NOT_AVAILABLE = 2122;
    public static final int MQRC_OUTCOME_MIXED = 2123;
    public static final int MQRC_OUTCOME_PENDING = 2124;
    public static final int MQRC_ADAPTER_STORAGE_SHORTAGE = 2127;
    public static final int MQRC_ADAPTER_CONN_LOAD_ERROR = 2129;
    public static final int MQRC_ADAPTER_SERV_LOAD_ERROR = 2130;
    public static final int MQRC_ADAPTER_DEFS_ERROR = 2131;
    public static final int MQRC_ADAPTER_DEFS_LOAD_ERROR = 2132;
    public static final int MQRC_ADAPTER_CONV_LOAD_ERROR = 2133;
    public static final int MQRC_MULTIPLE_REASONS = 2136;
    public static final int MQRC_OPEN_FAILED = 2137;
    public static final int MQRC_ADAPTER_DISC_LOAD_ERROR = 2138;
    public static final int MQRC_CNO_ERROR = 2139;
    public static final int MQRC_CICS_WAIT_FAILED = 2140;
    public static final int MQRC_DLH_ERROR = 2141;
    public static final int MQRC_HEADER_ERROR = 2142;
    public static final int MQRC_SOURCE_LENGTH_ERROR = 2143;
    public static final int MQRC_TARGET_LENGTH_ERROR = 2144;
    public static final int MQRC_SOURCE_BUFFER_ERROR = 2145;
    public static final int MQRC_TARGET_BUFFER_ERROR = 2146;
    public static final int MQRC_DBCS_ERROR = 2150;
    public static final int MQRC_OBJECT_NAME_ERROR = 2152;
    public static final int MQRC_OBJECT_Q_MGR_NAME_ERROR = 2153;
    public static final int MQRC_RECS_PRESENT_ERROR = 2154;
    public static final int MQRC_OBJECT_RECORDS_ERROR = 2155;
    public static final int MQRC_RESPONSE_RECORDS_ERROR = 2156;
    public static final int MQRC_ASID_MISMATCH = 2157;
    public static final int MQRC_PMO_RECORD_FLAGS_ERROR = 2158;
    public static final int MQRC_PUT_MSG_RECORDS_ERROR = 2159;
    public static final int MQRC_CONN_ID_IN_USE = 2160;
    public static final int MQRC_Q_MGR_QUIESCING = 2161;
    public static final int MQRC_Q_MGR_STOPPING = 2162;
    public static final int MQRC_DUPLICATE_RECOV_COORD = 2163;
    public static final int MQRC_PMO_ERROR = 2173;
    public static final int MQRC_API_EXIT_NOT_FOUND = 2182;
    public static final int MQRC_API_EXIT_LOAD_ERROR = 2183;
    public static final int MQRC_REMOTE_Q_NAME_ERROR = 2184;
    public static final int MQRC_INCONSISTENT_PERSISTENCE = 2185;
    public static final int MQRC_GMO_ERROR = 2186;
    public static final int MQRC_STOPPED_BY_CLUSTER_EXIT = 2188;
    public static final int MQRC_CLUSTER_RESOLUTION_ERROR = 2189;
    public static final int MQRC_CONVERTED_STRING_TOO_BIG = 2190;
    public static final int MQRC_TMC_ERROR = 2191;
    public static final int MQRC_PAGESET_FULL = 2192;
    public static final int MQRC_PAGESET_ERROR = 2193;
    public static final int MQRC_NAME_NOT_VALID_FOR_TYPE = 2194;
    public static final int MQRC_UNEXPECTED_ERROR = 2195;
    public static final int MQRC_UNKNOWN_XMIT_Q = 2196;
    public static final int MQRC_UNKNOWN_DEF_XMIT_Q = 2197;
    public static final int MQRC_DEF_XMIT_Q_TYPE_ERROR = 2198;
    public static final int MQRC_DEF_XMIT_Q_USAGE_ERROR = 2199;
    public static final int MQRC_NAME_IN_USE = 2201;
    public static final int MQRC_CONNECTION_QUIESCING = 2202;
    public static final int MQRC_CONNECTION_STOPPING = 2203;
    public static final int MQRC_ADAPTER_NOT_AVAILABLE = 2204;
    public static final int MQRC_MSG_ID_ERROR = 2206;
    public static final int MQRC_CORREL_ID_ERROR = 2207;
    public static final int MQRC_FILE_SYSTEM_ERROR = 2208;
    public static final int MQRC_NO_MSG_LOCKED = 2209;
    public static final int MQRC_FILE_NOT_AUDITED = 2216;
    public static final int MQRC_CONNECTION_NOT_AUTHORIZED = 2217;
    public static final int MQRC_MSG_TOO_BIG_FOR_CHANNEL = 2218;
    public static final int MQRC_CALL_IN_PROGRESS = 2219;
    public static final int MQRC_RMH_ERROR = 2220;
    public static final int MQRC_Q_MGR_ACTIVE = 2222;
    public static final int MQRC_Q_MGR_NOT_ACTIVE = 2223;
    public static final int MQRC_Q_DEPTH_HIGH = 2224;
    public static final int MQRC_Q_DEPTH_LOW = 2225;
    public static final int MQRC_Q_SERVICE_INTERVAL_HIGH = 2226;
    public static final int MQRC_Q_SERVICE_INTERVAL_OK = 2227;
    public static final int MQRC_UNIT_OF_WORK_NOT_STARTED = 2232;
    public static final int MQRC_CHANNEL_AUTO_DEF_OK = 2233;
    public static final int MQRC_CHANNEL_AUTO_DEF_ERROR = 2234;
    public static final int MQRC_CFH_ERROR = 2235;
    public static final int MQRC_CFIL_ERROR = 2236;
    public static final int MQRC_CFIN_ERROR = 2237;
    public static final int MQRC_CFSL_ERROR = 2238;
    public static final int MQRC_CFST_ERROR = 2239;
    public static final int MQRC_INCOMPLETE_GROUP = 2241;
    public static final int MQRC_INCOMPLETE_MSG = 2242;
    public static final int MQRC_INCONSISTENT_CCSIDS = 2243;
    public static final int MQRC_INCONSISTENT_ENCODINGS = 2244;
    public static final int MQRC_INCONSISTENT_UOW = 2245;
    public static final int MQRC_INVALID_MSG_UNDER_CURSOR = 2246;
    public static final int MQRC_MATCH_OPTIONS_ERROR = 2247;
    public static final int MQRC_MDE_ERROR = 2248;
    public static final int MQRC_MSG_FLAGS_ERROR = 2249;
    public static final int MQRC_MSG_SEQ_NUMBER_ERROR = 2250;
    public static final int MQRC_OFFSET_ERROR = 2251;
    public static final int MQRC_ORIGINAL_LENGTH_ERROR = 2252;
    public static final int MQRC_SEGMENT_LENGTH_ZERO = 2253;
    public static final int MQRC_UOW_NOT_AVAILABLE = 2255;
    public static final int MQRC_WRONG_GMO_VERSION = 2256;
    public static final int MQRC_WRONG_MD_VERSION = 2257;
    public static final int MQRC_GROUP_ID_ERROR = 2258;
    public static final int MQRC_INCONSISTENT_BROWSE = 2259;
    public static final int MQRC_XQH_ERROR = 2260;
    public static final int MQRC_SRC_ENV_ERROR = 2261;
    public static final int MQRC_SRC_NAME_ERROR = 2262;
    public static final int MQRC_DEST_ENV_ERROR = 2263;
    public static final int MQRC_DEST_NAME_ERROR = 2264;
    public static final int MQRC_TM_ERROR = 2265;
    public static final int MQRC_CLUSTER_EXIT_ERROR = 2266;
    public static final int MQRC_CLUSTER_EXIT_LOAD_ERROR = 2267;
    public static final int MQRC_CLUSTER_PUT_INHIBITED = 2268;
    public static final int MQRC_CLUSTER_RESOURCE_ERROR = 2269;
    public static final int MQRC_NO_DESTINATIONS_AVAILABLE = 2270;
    public static final int MQRC_OPTION_ENVIRONMENT_ERROR = 2274;
    public static final int MQRC_CD_ERROR = 2277;
    public static final int MQRC_CLIENT_CONN_ERROR = 2278;
    public static final int MQRC_CHANNEL_STOPPED_BY_USER = 2279;
    public static final int MQRC_HCONFIG_ERROR = 2280;
    public static final int MQRC_FUNCTION_ERROR = 2281;
    public static final int MQRC_CHANNEL_STARTED = 2282;
    public static final int MQRC_CHANNEL_STOPPED = 2283;
    public static final int MQRC_CHANNEL_CONV_ERROR = 2284;
    public static final int MQRC_SERVICE_NOT_AVAILABLE = 2285;
    public static final int MQRC_INITIALIZATION_FAILED = 2286;
    public static final int MQRC_TERMINATION_FAILED = 2287;
    public static final int MQRC_UNKNOWN_Q_NAME = 2288;
    public static final int MQRC_SERVICE_ERROR = 2289;
    public static final int MQRC_Q_ALREADY_EXISTS = 2290;
    public static final int MQRC_USER_ID_NOT_AVAILABLE = 2291;
    public static final int MQRC_UNKNOWN_ENTITY = 2292;
    public static final int MQRC_UNKNOWN_AUTH_ENTITY = 2293;
    public static final int MQRC_UNKNOWN_REF_OBJECT = 2294;
    public static final int MQRC_CHANNEL_ACTIVATED = 2295;
    public static final int MQRC_CHANNEL_NOT_ACTIVATED = 2296;
    public static final int MQRC_UOW_CANCELED = 2297;
    public static final int MQRC_SELECTOR_TYPE_ERROR = 2299;
    public static final int MQRC_COMMAND_TYPE_ERROR = 2300;
    public static final int MQRC_MULTIPLE_INSTANCE_ERROR = 2301;
    public static final int MQRC_SYSTEM_ITEM_NOT_ALTERABLE = 2302;
    public static final int MQRC_BAG_CONVERSION_ERROR = 2303;
    public static final int MQRC_SELECTOR_OUT_OF_RANGE = 2304;
    public static final int MQRC_SELECTOR_NOT_UNIQUE = 2305;
    public static final int MQRC_INDEX_NOT_PRESENT = 2306;
    public static final int MQRC_STRING_ERROR = 2307;
    public static final int MQRC_ENCODING_NOT_SUPPORTED = 2308;
    public static final int MQRC_SELECTOR_NOT_PRESENT = 2309;
    public static final int MQRC_OUT_SELECTOR_ERROR = 2310;
    public static final int MQRC_STRING_TRUNCATED = 2311;
    public static final int MQRC_SELECTOR_WRONG_TYPE = 2312;
    public static final int MQRC_INCONSISTENT_ITEM_TYPE = 2313;
    public static final int MQRC_INDEX_ERROR = 2314;
    public static final int MQRC_SYSTEM_BAG_NOT_ALTERABLE = 2315;
    public static final int MQRC_ITEM_COUNT_ERROR = 2316;
    public static final int MQRC_FORMAT_NOT_SUPPORTED = 2317;
    public static final int MQRC_SELECTOR_NOT_SUPPORTED = 2318;
    public static final int MQRC_ITEM_VALUE_ERROR = 2319;
    public static final int MQRC_HBAG_ERROR = 2320;
    public static final int MQRC_PARAMETER_MISSING = 2321;
    public static final int MQRC_CMD_SERVER_NOT_AVAILABLE = 2322;
    public static final int MQRC_STRING_LENGTH_ERROR = 2323;
    public static final int MQRC_INQUIRY_COMMAND_ERROR = 2324;
    public static final int MQRC_NESTED_BAG_NOT_SUPPORTED = 2325;
    public static final int MQRC_BAG_WRONG_TYPE = 2326;
    public static final int MQRC_ITEM_TYPE_ERROR = 2327;
    public static final int MQRC_SYSTEM_BAG_NOT_DELETABLE = 2328;
    public static final int MQRC_SYSTEM_ITEM_NOT_DELETABLE = 2329;
    public static final int MQRC_CODED_CHAR_SET_ID_ERROR = 2330;
    public static final int MQRC_MSG_TOKEN_ERROR = 2331;
    public static final int MQRC_MISSING_WIH = 2332;
    public static final int MQRC_WIH_HEADER = 2333;
    public static final int MQRC_RFH_ERROR = 2334;
    public static final int MQRC_RFH_STRING_ERROR = 2335;
    public static final int MQRC_RFH_COMMAND_ERROR = 2336;
    public static final int MQRC_RFH_PARM_ERROR = 2337;
    public static final int MQRC_RFH_DUPLICATE_PARM = 2338;
    public static final int MQRC_RFH_PARM_MISSING = 2339;
    public static final int MQRC_CHAR_CONVERSION_ERROR = 2340;
    public static final int MQRC_UCS2_CONVERSION_ERROR = 2341;
    public static final int MQRC_DB2_NOT_AVAILABLE = 2342;
    public static final int MQRC_OBJECT_NOT_UNIQUE = 2343;
    public static final int MQRC_CONN_TAG_NOT_RELEASED = 2344;
    public static final int MQRC_CF_NOT_AVAILABLE = 2345;
    public static final int MQRC_CF_STRUC_IN_USE = 2346;
    public static final int MQRC_CF_STRUC_LIST_HDR_IN_USE = 2347;
    public static final int MQRC_CF_STRUC_AUTH_FAILED = 2348;
    public static final int MQRC_CF_STRUC_ERROR = 2349;
    public static final int MQRC_CONN_TAG_NOT_USABLE = 2350;
    public static final int MQRC_GLOBAL_UOW_CONFLICT = 2351;
    public static final int MQRC_LOCAL_UOW_CONFLICT = 2352;
    public static final int MQRC_HANDLE_IN_USE_FOR_UOW = 2353;
    public static final int MQRC_UOW_ENLISTMENT_ERROR = 2354;
    public static final int MQRC_UOW_MIX_NOT_SUPPORTED = 2355;
    public static final int MQRC_WXP_ERROR = 2356;
    public static final int MQRC_CURRENT_RECORD_ERROR = 2357;
    public static final int MQRC_NEXT_OFFSET_ERROR = 2358;
    public static final int MQRC_NO_RECORD_AVAILABLE = 2359;
    public static final int MQRC_OBJECT_LEVEL_INCOMPATIBLE = 2360;
    public static final int MQRC_NEXT_RECORD_ERROR = 2361;
    public static final int MQRC_BACKOUT_THRESHOLD_REACHED = 2362;
    public static final int MQRC_MSG_NOT_MATCHED = 2363;
    public static final int MQRC_JMS_FORMAT_ERROR = 2364;
    public static final int MQRC_PARTICIPANT_NOT_DEFINED = 2372;
    public static final int MQRC_SSL_NOT_ALLOWED = 2396;
    public static final int MQRC_JSSE_ERROR = 2397;
    public static final int MQRC_SSL_PEER_NAME_MISMATCH = 2398;
    public static final int MQRC_SSL_PEER_NAME_ERROR = 2399;
    public static final int MQRC_UNSUPPORTED_CIPHER_SUITE = 2400;
    public static final int MQRC_SSL_CERTIFICATE_REVOKED = 2401;
    public static final int MQRC_SSL_CERT_STORE_ERROR = 2402;
    protected static final int MQJI001 = 1;
    protected static final int MQJI002 = 2;
    protected static final int MQJI003 = 3;
    protected static final int MQJI004 = 4;
    protected static final int MQJI005 = 5;
    protected static final int MQJI006 = 6;
    protected static final int MQJI007 = 7;
    protected static final int MQJI008 = 8;
    protected static final int MQJI009 = 9;
    protected static final int MQJI010 = 10;
    protected static final int MQJI011 = 11;
    protected static final int MQJI012 = 12;
    protected static final int MQJI013 = 13;
    protected static final int MQJI014 = 14;
    protected static final int MQJI015 = 15;
    protected static final int MQJI016 = 16;
    protected static final int MQJI017 = 17;
    protected static final int MQJI018 = 19;
    protected static final int MQJI019 = 20;
    protected static final int MQJI020 = 21;
    protected static final int MQJI021 = 22;
    protected static final int MQJI022 = 24;
    protected static final int MQJI023 = 25;
    protected static final int MQJI024 = 26;
    protected static final int MQJI025 = 27;
    protected static final int MQJI026 = 28;
    protected static final int MQJI027 = 29;
    protected static final int MQJI028 = 30;
    protected static final int MQJI029 = 31;
    protected static final int MQJI030 = 32;
    protected static final int MQJI031 = 33;
    protected static final int MQJI032 = 34;
    protected static final int MQJI033 = 35;
    protected static final int MQJI034 = 36;
    protected static final int MQJI035 = 37;
    protected static final int MQJI036 = 38;
    protected static final int MQJI037 = 41;
    protected static final int MQJI038 = 42;
    protected static final int MQJI039 = 105;
    protected static final int MQJI040 = 106;
    public static final int MQJI041 = 107;
    protected static final int MQJI042 = 108;
    protected static final int MQJE001 = 40;
    protected static final int MQJE001b = 39;
    protected static final int MQJE002 = 43;
    protected static final int MQJE003 = 44;
    protected static final int MQJE004 = 45;
    protected static final int MQJE005 = 46;
    protected static final int MQJE006 = 47;
    protected static final int MQJE007 = 48;
    protected static final int MQJE008 = 49;
    protected static final int MQJE009 = 50;
    protected static final int MQJE010 = 51;
    protected static final int MQJE011 = 52;
    protected static final int MQJE012 = 53;
    protected static final int MQJE013 = 54;
    protected static final int MQJE014 = 55;
    protected static final int MQJE015 = 56;
    protected static final int MQJE016 = 57;
    protected static final int MQJE017 = 58;
    protected static final int MQJE018 = 59;
    protected static final int MQJE019 = 60;
    protected static final int MQJE020 = 61;
    protected static final int MQJE021 = 62;
    protected static final int MQJE022 = 63;
    protected static final int MQJE023 = 64;
    protected static final int MQJE024 = 65;
    protected static final int MQJE025 = 66;
    protected static final int MQJE026 = 67;
    protected static final int MQJE027 = 68;
    protected static final int MQJE028 = 69;
    protected static final int MQJE029 = 70;
    protected static final int MQJE030 = 71;
    protected static final int MQJE031 = 72;
    protected static final int MQJE032 = 73;
    protected static final int MQJE033 = 74;
    protected static final int MQJE034 = 75;
    protected static final int MQJE035 = 76;
    protected static final int MQJE036 = 77;
    protected static final int MQJE037 = 78;
    protected static final int MQJE038 = 79;
    protected static final int MQJE039 = 80;
    protected static final int MQJE040 = 81;
    protected static final int MQJE041 = 82;
    protected static final int MQJE042 = 83;
    protected static final int MQJE043 = 84;
    protected static final int MQJE044 = 85;
    protected static final int MQJE045 = 86;
    protected static final int MQJE046 = 87;
    protected static final int MQJE047 = 88;
    protected static final int MQJE048 = 89;
    protected static final int MQJE049 = 90;
    protected static final int MQJE050 = 91;
    protected static final int MQJE051 = 92;
    protected static final int MQJE052 = 93;
    protected static final int MQJE053 = 95;
    protected static final int MQJE054 = 96;
    protected static final int MQJE055 = 97;
    protected static final int MQJE056 = 98;
    protected static final int MQJE057 = 99;
    protected static final int MQJE058 = 100;
    protected static final int MQJE059 = 101;
    protected static final int MQJE060 = 102;
    protected static final int MQJE061 = 103;
    protected static final int MQJE062 = 104;
    public static final int MID_ProductName = 109;
    public static final int MID_MngCon_CmdLvl = 110;
    public static final int MID_SecManError = 111;
    public static final int MID_XANativeError = 112;
    public static final int MID_OpenFailed = 113;
    public static final int MID_OpFailed = 114;
    public static final int MID_ResourceClosed = 115;
    protected static final int MQJE063 = 116;
    protected static final int MQJE064 = 117;
    protected static final int MQJE065 = 118;
    protected static final int MID_Rejected_XA_Client = 119;
    protected static final int MQJE066 = 120;
    protected static final int MQJE067 = 121;
    protected static final int MQJE068 = 122;
    protected static final int MQJE069 = 123;
    protected static final int MQJE070 = 124;
    private static final String MQJE001_AS_STRING = Integer.toString(40);
    private static final String MQJE001b_AS_STRING = Integer.toString(39);
    public static OutputStreamWriter log;
    protected static ResourceBundle exceptionMessages = null;
    public int completionCode;
    public int reasonCode;
    public transient Object exceptionSource;
    private String ostrMessage;
    private final int msgId;
    private final int numInserts;
    private final String insert1;
    private final String insert2;
    private Throwable underlyingException;
    private boolean underlyingSet;

    static 
    {
        try
        {
            exceptionMessages = ResourceBundle.getBundle("mqji");
        }
        catch(MissingResourceException missingresourceexception)
        {
            System.err.println("Unable to load message catalog - mqji");
        }
        /*
        try
        {
            log = new OutputStreamWriter(System.err);
            String s = (String)AccessController.doPrivileged(new  Object()     
    class _anm1 {}

);
            if(s.equals("Windows NT") || s.equals("OS/2"))
            {
                Locale locale = Locale.getDefault();
                String s1 = locale.getLanguage();
                if(s1.equals("en") || s1.equals("fr") || s1.equals("es") || s1.equals("it") || s1.equals("pt") || s1.equals("de"))
                    log = new OutputStreamWriter(System.err, "850");
            }
        }
        catch(Exception exception)
        {
            log = new OutputStreamWriter(System.err);
        }
        */
    }
}
