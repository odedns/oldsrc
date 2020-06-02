// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 

package com.ibm.dse.services.mq;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSENotifier;
import com.ibm.dse.base.Externalizer;
import com.ibm.dse.base.FormatElement;
import com.ibm.dse.base.Hashtable;
import com.ibm.dse.base.JavaExtensions;
import com.ibm.dse.base.Semaphore;
import com.ibm.dse.base.Service;
import com.ibm.dse.base.Tag;
import com.ibm.dse.base.TagAttribute;
import com.ibm.dse.base.Trace;
import com.ibm.dse.services.comms.CCMessage;
import com.ibm.dse.services.comms.DSECCException;
import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMD;
import com.ibm.mq.MQManagedObject;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.Vector;

// Referenced classes of package com.ibm.dse.services.mq:
//            ConnectionHandler, MqDeadLetterQueueThread, MQConnectionService, QueueEventMulticaster, 
//            QueueListener, QueueEvent

public class MQConnection extends Service
    implements Runnable, MQConnectionService
{

    public MQConnection()
    {
        AliasQueueManagerNameSend = "";
        connectionHandler = null;
        sendTypeNormal = true;
        replyTypeNormal = true;
        deadLetterQueueThread = null;
        isDeadLetterThreadAlive = false;
        initializeValues();
    }

    public MQConnection(String s)
        throws IOException
    {
        super(s);
        AliasQueueManagerNameSend = "";
        connectionHandler = null;
        sendTypeNormal = true;
        replyTypeNormal = true;
        deadLetterQueueThread = null;
        isDeadLetterThreadAlive = false;
        initializeValues();
    }

    public void addQueueListener(QueueListener queuelistener)
    {
        aQueueListener = QueueEventMulticaster.add(aQueueListener, queuelistener);
    }

    public void ccClose()
        throws DSECCException
    {
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "ccClose method. Closing connections");
        try
        {
            closeConnection();
        }
        catch(Exception exception)
        {
            throw returnException(exception);
        }
    }

    public void ccOpen()
        throws DSECCException
    {
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "ccOpen method. Opening connections");
        try
        {
            establishConnection();
        }
        catch(Exception exception)
        {
            throw returnException(exception);
        }
    }

    public CCMessage ccReceiveData(long l)
        throws DSECCException
    {
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "ccReceiveData method.");
        throw new DSECCException(DSEException.critical, "ccReceiveData not supported for MQConnection", "ccReceiveData not supported for MQConnection");
    }

    public void ccSendData(String s)
        throws DSECCException
    {
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "ccSendData method. Sending data");
        try
        {
            send(s);
        }
        catch(Exception exception)
        {
            throw returnException(exception);
        }
    }

    public CCMessage ccSendReceive(String s, long l)
        throws DSECCException
    {
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "ccSendReceive method.");
        CCMessage ccmessage = createWhatReceived();
        Object obj = null;
        Object obj2 = null;
        try
        {
            byte abyte0[];
            synchronized(sendSyncronizer)
            {
                sendReturnCode = 0;
                abyte0 = send(s);
                if(sendReturnCode != 0)
                {
                    ccmessage.setDataSent(false);
                    ccmessage.setReceiveRC(sendReturnCode);
                    CCMessage ccmessage1 = ccmessage;
                    return ccmessage1;
                }
                ccmessage.setDataSent(true);
            }
            synchronized(receiveSyncronizer)
            {
                receiveReturnCode = 0;
                Object obj1 = receive(abyte0, l);
                if(receiveReturnCode != 0)
                {
                    if(receiveReturnCode == 2033)
                        ccmessage.setTimeout(true);
                    ccmessage.setReceiveRC(receiveReturnCode);
                    CCMessage ccmessage2 = ccmessage;
                    return ccmessage2;
                }
                ccmessage.setDataReceived(returnStringFromMessage(obj1));
            }
            return ccmessage;
        }
        catch(Exception exception)
        {
            throw returnException(exception);
        }
    }

    private void checkThread()
    {
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "Checking listen thread status");
        if(listenThread == null || !listenThread.isAlive())
            start("Listen");
    }

    public void closeConnection()
        throws MQException
    {
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "Closing connections");
        closedIssued = true;
        if(!getSynchronousMode())
            isListenThreadAlive = false;
        if(getDeadLetterQName() != null)
            isDeadLetterThreadAlive = false;
        if(!getSynchronousMode() && (ReplyToQName != null || ReplyModelQName != null))
        {
            MQQueue mqqueue = null;
            if(ReplyToQName != null)
                mqqueue = QueueManagerSend.accessQueue(ReplyToQName, 1040);
            else
            if(ReplyModelQName != null)
                mqqueue = QueueManagerSend.accessQueue(ReplyModelQName, 1040, "", ReplyDynamicQName, getUserId());
            MQMessage mqmessage = new MQMessage();
            mqmessage.userId = "dseUser";
            pmo.options = 1088;
            mqqueue.put(mqmessage, pmo);
            mqqueue.close();
        }
        if(SendToQueue != null)
            SendToQueue.close();
        SendQueueAccessed = false;
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "SendQueue closed");
        if(ReplyToQueue != null)
            ReplyToQueue.close();
        ReplyQueueAccessed = false;
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "ReplyQueue closed");
        if(DeadLetterQ != null)
        {
            MQQueue mqqueue1 = QueueManagerSend.accessQueue(getDeadLetterQName(), 1040);
            MQMessage mqmessage1 = new MQMessage();
            mqmessage1.userId = "dseUser";
            pmo.options = 1088;
            mqqueue1.put(mqmessage1, pmo);
            DeadLetterQ.close();
            mqqueue1.close();
            DeadLetterAccessed = false;
            if(Trace.doTrace("#MQ", 512, 2))
                Trace.trace("#MQ", 512, 2, getName(), "DeadLetterQueue closed");
        }
        if(channelQueue != null && ChannelQueueAccessed)
        {
            channelQueue.close();
            ChannelQueueAccessed = false;
            if(Trace.doTrace("#MQ", 512, 2))
                Trace.trace("#MQ", 512, 2, getName(), "ChannelQueue closed");
        }
        try
        {
            signalEvent("closed");
        }
        catch(DSEInvalidArgumentException dseinvalidargumentexception)
        {
            if(Trace.doTrace("#MQ", 512, 8))
                Trace.trace("#MQ", 512, 8, getName(), dseinvalidargumentexception.toString());
        }
    }

    private CCMessage createWhatReceived()
    {
        CCMessage ccmessage = new CCMessage();
        ccmessage.setDataSent(false);
        ccmessage.setTimeout(false);
        ccmessage.setDataReceived(null);
        ccmessage.setReceiveRC(0);
        return ccmessage;
    }

    public void disconnectQueueManager()
        throws MQException
    {
        QueueManagerReceive.disconnect();
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "Receive QueueManager disconnected");
        QueueManagerSend.disconnect();
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "Send QueueManager disconnected");
        if(DeadLetterQ != null)
        {
            DeadLetterQManager.disconnect();
            if(Trace.doTrace("#MQ", 512, 2))
                Trace.trace("#MQ", 512, 2, getName(), " DeadLetterQueue Manager disconnected");
        }
        if(channelQueue != null)
        {
            ChannelQManager.disconnect();
            if(Trace.doTrace("#MQ", 512, 2))
                Trace.trace("#MQ", 512, 2, getName(), " Channel QueueManager disconnected");
        }
    }

    public void establishConnection()
        throws MQException
    {
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "Establishing connections");
        try
        {
            pmo = new MQPutMessageOptions();
            gmo = new MQGetMessageOptions();
            pmo.options = PutMessageOptionsOptions;
            gmo.options = GetMessageOptionsOptions;
            if(sendTypeNormal && SendToQName != null)
            {
                closedIssued = false;
                if(AliasQueueManagerNameSend.equals(""))
                    SendToQueue = QueueManagerSend.accessQueue(SendToQName, SendToQOpenOptions);
                else
                    SendToQueue = QueueManagerSend.accessQueue(SendToQName, SendToQOpenOptions, AliasQueueManagerNameSend, "", getUserId());
                SendQueueAccessed = true;
                if(Trace.doTrace("#MQ", 512, 2))
                    Trace.trace("#MQ", 512, 2, getName(), "establishConnection, SendQueue accesed");
            }
            if(!sendTypeNormal && SendModelQName != null && SendToQName == null)
            {
                if(SendDynamicQName == null)
                    SendDynamicQName = getName() + "sendQ";
                closedIssued = false;
                SendToQueue = QueueManagerSend.accessQueue(SendModelQName, SendToQOpenOptions, AliasQueueManagerNameSend, SendDynamicQName, getUserId());
                setSendQName(((MQManagedObject) (SendToQueue)).name);
                SendQueueAccessed = true;
                if(Trace.doTrace("#MQ", 512, 2))
                    Trace.trace("#MQ", 512, 2, getName(), "establishConnection, dynamic SendQueue accesed " + SendDynamicQName);
            }
            if(replyTypeNormal && ReplyToQName != null)
            {
                closedIssued = false;
                ReplyToQueue = QueueManagerReceive.accessQueue(ReplyToQName, ReplyToQOpenOptions);
                ReplyQueueAccessed = true;
                if(Trace.doTrace("#MQ", 512, 2))
                    Trace.trace("#MQ", 512, 2, getName(), "establishConnection, ReplyQueue accesed");
            }
            if(!replyTypeNormal && ReplyModelQName != null && ReplyToQName == null)
            {
                closedIssued = false;
                if(ReplyDynamicQName == null)
                    ReplyDynamicQName = getName() + "replyQ";
                ReplyToQueue = QueueManagerReceive.accessQueue(ReplyModelQName, ReplyToQOpenOptions, "", ReplyDynamicQName, getUserId());
                setReplyQName(((MQManagedObject) (ReplyToQueue)).name);
                ReplyQueueAccessed = true;
                if(Trace.doTrace("#MQ", 512, 2))
                    Trace.trace("#MQ", 512, 2, getName(), "establishConnection, dynamic ReplyQueue accesed " + ReplyDynamicQName);
            }
            if(channelQName != null)
            {
                closedIssued = false;
                channelQueue = ChannelQManager.accessQueue(channelQName, ReplyToQOpenOptions);
                ChannelQueueAccessed = true;
                systemCommandQueue = QueueManagerSend.accessQueue("SYSTEM.ADMIN.COMMAND.QUEUE", SendToQOpenOptions);
                if(Trace.doTrace("#MQ", 512, 2))
                    Trace.trace("#MQ", 512, 2, getName(), "establishConnection, ChannelQueue accesed");
            }
        }
        catch(MQException mqexception)
        {
            try
            {
                if(mqexception.reasonCode == 2019 || mqexception.reasonCode == 2009 || mqexception.reasonCode == 2161)
                {
                    initManagers();
                    signalEvent("errorReceived");
                } else
                {
                    throw mqexception;
                }
            }
            catch(DSEInvalidArgumentException dseinvalidargumentexception1)
            {
                if(Trace.doTrace("#MQ", 512, 8))
                    Trace.trace("#MQ", 512, 8, getName(), dseinvalidargumentexception1.toString());
            }
        }
        try
        {
            signalEvent("opened");
        }
        catch(DSEInvalidArgumentException dseinvalidargumentexception)
        {
            if(Trace.doTrace("#MQ", 512, 8))
                Trace.trace("#MQ", 512, 8, getName(), dseinvalidargumentexception.toString());
        }
        if(!synchronousMode && (ReplyToQName != null || ReplyModelQName != null))
        {
            if(listenThread == null)
                listenThread = new Thread(this, "Listen");
            closedIssued = false;
            receiveSem.signalOn();
            if(Trace.doTrace("#MQ", 512, 2))
                Trace.trace("#MQ", 512, 2, getName(), "Asynchronous mode:Receive semaphore signaled.Listen thread will be started");
            checkThread();
        }
        if(deadLetterQName != null && deadLetterQueueThread == null)
            try
            {
                DeadLetterQ = DeadLetterQManager.accessQueue(deadLetterQName, DeadLetterQOpenOptions);
                DeadLetterAccessed = true;
                if(Trace.doTrace("#MQ", 512, 2))
                    Trace.trace("#MQ", 512, 2, getName(), "establishConnection, DeadLetterQueue accesed");
                start("DeadLetterQ");
            }
            catch(Exception exception)
            {
                if(Trace.doTrace("#MQ", 512, 8))
                    Trace.trace("#MQ", 512, 8, getName(), "Cannot start dead letter queue: " + exception.toString());
            }
    }

    private void finalizeValues()
    {
        receiveSem = null;
        aQueueListener = null;
        tempMqMessage = null;
        eventParameter = null;
        eventParameterD = null;
        messageTable = null;
        messageQTable = null;
        deadLetterQueueThread = null;
        inUse = false;
        closedIssued = true;
    }

    public void fireMessageReceived(QueueEvent queueevent)
    {
        if(aQueueListener == null)
        {
            return;
        } else
        {
            aQueueListener.messageReceived(queueevent);
            return;
        }
    }

    public String getAliasQueueManagerNameSend()
    {
        return AliasQueueManagerNameSend;
    }

    public String getApplicationName()
    {
        return applicationName;
    }

    public boolean getAutomaticConnectionEstablishment()
    {
        return automaticConnectionEstablishment;
    }

    public String getCcClosedEventName()
    {
        return "closed";
    }

    public String getCcDataReceivedEventName()
    {
        return "message";
    }

    public String getCcErrorReceivedEventName()
    {
        return "errorReceived";
    }

    public String getCcOpenedEventName()
    {
        return "opened";
    }

    public String getchannelName()
    {
        return channel;
    }

    public int getChannelStatus(String s)
        throws MQException
    {
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "getChannelStatus for channel : " + s);
        if(!ChannelQueueAccessed)
            channelQueue = ChannelQManager.accessQueue(channelQName, ReplyToQOpenOptions);
        try
        {
            MQMessage mqmessage = new MQMessage();
            mqmessage.messageType = 1;
            mqmessage.replyToQueueManagerName = QMgrName;
            mqmessage.replyToQueueName = channelQName;
            mqmessage.format = "MQADMIN ";
            mqmessage.report = 128;
            mqmessage.persistence = 1;
            mqmessage.writeInt(1);
            mqmessage.writeInt(36);
            mqmessage.writeInt(1);
            mqmessage.writeInt(42);
            mqmessage.writeInt(1);
            mqmessage.writeInt(1);
            mqmessage.writeInt(0);
            mqmessage.writeInt(0);
            mqmessage.writeInt(1);
            mqmessage.writeInt(4);
            mqmessage.writeInt(40);
            mqmessage.writeInt(3501);
            mqmessage.writeInt(0);
            mqmessage.writeInt(20);
            int i = s.length();
            for(int j = i; j < 20; j++)
                s = s.concat(" ");

            mqmessage.writeString(s);
            systemCommandQueue.put(mqmessage, pmo);
            return receiveStatus(((MQMD) (mqmessage)).messageId);
        }
        catch(IOException ioexception)
        {
            return -1;
        }
    }

    public int getCharSet()
    {
        return charSet;
    }

    public String getDeadLetterQName()
    {
        return deadLetterQName;
    }

    public int getEncoding()
    {
        return encoding;
    }

    public int getEstablishConnectionRetries()
    {
        return EstablishConnectionRetries;
    }

    public int getExpiry()
    {
        return expiry;
    }

    public String getFormat()
    {
        return format;
    }

    public MQMessage getFromDeadLetterQueue()
        throws MQException
    {
        MQMessage mqmessage = new MQMessage();
        DeadLetterQ.get(mqmessage, dlo);
        return mqmessage;
    }

    public MQMessage getFromGetReplyQueue()
        throws MQException, IOException
    {
        if(ReplyToQueue == null)
            throw new MQException(2, 2085, null);
        boolean flag = true;
        MQMessage mqmessage = new MQMessage();
        while(flag) 
            try
            {
                flag = false;
                ReplyToQueue.get(mqmessage, gmo);
                if(Trace.doTrace("#MQ", 512, 2))
                    Trace.trace("#MQ", 512, 2, getName(), "Message obtained from reply queue");
            }
            catch(MQException mqexception)
            {
                receiveReturnCode = mqexception.reasonCode;
                if(receiveReturnCode == 2033)
                {
                    flag = true;
                } else
                {
                    if(Trace.doTrace("#MQ", 512, 8))
                        Trace.trace("#MQ", 512, 8, getName(), "Exception on receive method:" + mqexception.toString() + "\n Return Code:" + receiveReturnCode);
                    if(receiveReturnCode == 2080)
                    {
                        try
                        {
                            mqmessage.resizeBuffer(mqmessage.getTotalMessageLength());
                            ReplyToQueue.get(mqmessage, gmo);
                        }
                        catch(MQException mqexception1)
                        {
                            eventParameter.put(EXCEPTION, mqexception1);
                            if(ReplyToQName != null)
                                eventParameter.put("QUEUE", ReplyToQName);
                            else
                            if(ReplyDynamicQName != null)
                                eventParameter.put("QUEUE", ReplyDynamicQName);
                            try
                            {
                                signalEvent("errorReceived", eventParameter);
                            }
                            catch(DSEInvalidArgumentException dseinvalidargumentexception1)
                            {
                                if(Trace.doTrace("#MQ", 512, 8))
                                    Trace.trace("#MQ", 512, 8, getName(), "Exception on receive method:" + dseinvalidargumentexception1.toString());
                            }
                        }
                    } else
                    {
                        if(receiveReturnCode == 2019 || receiveReturnCode == 2009 || receiveReturnCode == 2161)
                            ReplyQueueAccessed = false;
                        eventParameter.put(EXCEPTION, mqexception);
                        if(ReplyToQName != null)
                            eventParameter.put("QUEUE", ReplyToQName);
                        else
                        if(ReplyDynamicQName != null)
                            eventParameter.put("QUEUE", ReplyDynamicQName);
                        try
                        {
                            signalEvent("errorReceived", eventParameter);
                        }
                        catch(DSEInvalidArgumentException dseinvalidargumentexception)
                        {
                            if(Trace.doTrace("#MQ", 512, 8))
                                Trace.trace("#MQ", 512, 8, getName(), "Exception on receive method:" + dseinvalidargumentexception.toString());
                        }
                    }
                }
            }
        return mqmessage;
    }

    public MQMessage getFromGetReplyQueue(byte abyte0[])
        throws MQException, IOException
    {
        if(ReplyToQueue == null)
            throw new MQException(2, 2085, null);
        MQMessage mqmessage = new MQMessage();
        boolean flag = true;
        if(matchCorrelationId)
            mqmessage.correlationId = abyte0;
        else
            mqmessage.messageId = abyte0;
        while(flag) 
            try
            {
                flag = false;			
                ReplyToQueue.get(mqmessage, gmo);             
//                System.out.println("MQConnection: got message from reply queue");
                if(Trace.doTrace("#MQ", 512, 2))
                    Trace.trace("#MQ", 512, 2, getName(), "Message obtained from reply queue");
            }
            catch(MQException mqexception)
            {
                receiveReturnCode = mqexception.reasonCode;
                if(Trace.doTrace("#MQ", 512, 8))
                    Trace.trace("#MQ", 512, 8, getName(), "Exception on receive method:" + mqexception.toString() + "\n Return Code:" + receiveReturnCode);
                if(receiveReturnCode == 2080)
                {
                    try
                    {
                        mqmessage.resizeBuffer(mqmessage.getTotalMessageLength());
                        ReplyToQueue.get(mqmessage, gmo);
                    }
                    catch(MQException mqexception1)
                    {
                        eventParameter.put(EXCEPTION, mqexception1);
                        if(ReplyToQName != null)
                            eventParameter.put("QUEUE", ReplyToQName);
                        else
                        if(ReplyDynamicQName != null)
                            eventParameter.put("QUEUE", ReplyDynamicQName);
                        try
                        {
                            signalEvent("errorReceived", eventParameter);
                        }
                        catch(DSEInvalidArgumentException dseinvalidargumentexception1)
                        {
                            if(Trace.doTrace("#MQ", 512, 8))
                                Trace.trace("#MQ", 512, 8, getName(), "Exception on receive method:" + dseinvalidargumentexception1.toString());
                        }
                    }
                } else
                {
                    if(receiveReturnCode == 2019 || receiveReturnCode == 2009 || receiveReturnCode == 2161)
                        ReplyQueueAccessed = false;
                    eventParameter.put(EXCEPTION, mqexception);
                    if(ReplyToQName != null)
                        eventParameter.put("QUEUE", ReplyToQName);
                    else
                    if(ReplyDynamicQName != null)
                        eventParameter.put("QUEUE", ReplyDynamicQName);
                    try
                    {
                        signalEvent("errorReceived", eventParameter);
                    }
                    catch(DSEInvalidArgumentException dseinvalidargumentexception)
                    {
                        if(Trace.doTrace("#MQ", 512, 8))
                            Trace.trace("#MQ", 512, 8, getName(), "Exception on receive method:" + dseinvalidargumentexception.toString());
                    }
                }
            }
        return mqmessage;
    }

    public int getGetMessageOptionsOptions()
    {
        return gmo.options;
    }

    public String getHostName()
    {
        return hostName;
    }

    public synchronized boolean getInUse()
    {
        return inUse;
    }

    public boolean getMatchCorrelationId()
    {
        return matchCorrelationId;
    }

    public int getMessageType()
    {
        return messageType;
    }

    public int getPersistence()
    {
        return persistence;
    }

    public int getPortNumber()
    {
        return port;
    }

    public int getPutMessageOptionsOptions()
    {
        return pmo.options;
    }

    public String getQmgrReply()
    {
        return QmgrReply;
    }

    public String getQueueManagerName()
    {
        return QMgrName;
    }

    public int getReceiveReturnCode()
    {
        return receiveReturnCode;
    }

    public String getReplyDynamicQName()
    {
        return ReplyDynamicQName;
    }

    public String getReplyToQName()
    {
        return ReplyToQName;
    }

    public int getReplyToQOpenOptions()
    {
        return ReplyToQOpenOptions;
    }

    public String getSendDynamicQName()
    {
        return SendDynamicQName;
    }

    public int getSendReturnCode()
    {
        return sendReturnCode;
    }

    public String getSendToQName()
    {
        return SendToQName;
    }

    public int getSendToQOpenOptions()
    {
        return SendToQOpenOptions;
    }

    public boolean getServer()
    {
        return server;
    }

    public String getStatus()
    {
        if(isResetStatus())
            return "RESET";
        else
            return "opened";
    }

    public boolean getSynchronousMode()
    {
        return synchronousMode;
    }

    public int getTimeBetweenRetries()
    {
        return TimeBetweenRetries;
    }

    public int getTimeout()
    {
        return timeout;
    }

    public String getUserId()
    {
        return userId;
    }

    protected void initializeCounter()
    {
        establishConnectionCounter = EstablishConnectionRetries;
    }

    public Object initializeFrom(Tag tag)
        throws IOException, DSEException
    {
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "initializeFrom method. Initializing parameters...");
        for(Enumeration enumeration = tag.getAttrList().elements(); enumeration.hasMoreElements();)
        {
            TagAttribute tagattribute = (TagAttribute)enumeration.nextElement();
            if(tagattribute.getName().equals("id"))
            {
                if(getName() == null)
                    setName((String)tagattribute.getValue());
                else
                    setName((String)tagattribute.getValue() + getName());
            } else
            if(tagattribute.getName().equals("synchronousMode"))
            {
                if(tagattribute.getValue().equals("enabled"))
                    synchronousMode = true;
                else
                    synchronousMode = false;
            } else
            if(tagattribute.getName().equals("serverOrClient"))
            {
                if(tagattribute.getValue().equals("server"))
                    server = true;
                else
                    server = false;
            } else
            if(tagattribute.getName().equals("automaticConnectionEstablishment"))
            {
                if(tagattribute.getValue().equals("enabled"))
                    automaticConnectionEstablishment = true;
                else
                    automaticConnectionEstablishment = false;
            } else
            if(tagattribute.getName().equals("establishConnectionRetries"))
                EstablishConnectionRetries = Integer.parseInt((String)tagattribute.getValue());
            else
            if(tagattribute.getName().equals("timeBetweenRetries"))
                TimeBetweenRetries = Integer.parseInt((String)tagattribute.getValue());
            else
            if(tagattribute.getName().equals("QMgrName"))
                QMgrName = (String)tagattribute.getValue();
            else
            if(tagattribute.getName().equals("sendToQ"))
                SendToQName = (String)tagattribute.getValue();
            else
            if(tagattribute.getName().equals("replyToQ"))
                ReplyToQName = (String)tagattribute.getValue();
            else
            if(tagattribute.getName().equals("channelQ"))
                channelQName = (String)tagattribute.getValue();
            else
            if(tagattribute.getName().equals("deadLetterQ"))
                deadLetterQName = (String)tagattribute.getValue();
            else
            if(tagattribute.getName().equals("putMessageOptions"))
                PutMessageOptionsOptions = parseInt((String)tagattribute.getValue());
            else
            if(tagattribute.getName().equals("getMessageOptions"))
                GetMessageOptionsOptions = parseInt((String)tagattribute.getValue());
            else
            if(tagattribute.getName().equals("sendToQOpenOptions"))
                SendToQOpenOptions = parseInt((String)tagattribute.getValue());
            else
            if(tagattribute.getName().equals("replyToQOpenOptions"))
                ReplyToQOpenOptions = parseInt((String)tagattribute.getValue());
            else
            if(tagattribute.getName().equals("hostName"))
                hostName = (String)tagattribute.getValue();
            else
            if(tagattribute.getName().equals("channel"))
                channel = (String)tagattribute.getValue();
            else
            if(tagattribute.getName().equals("port"))
                port = Integer.valueOf((String)tagattribute.getValue()).intValue();
            else
            if(tagattribute.getName().equals("persistence"))
                persistence = parseInt((String)tagattribute.getValue());
            else
            if(tagattribute.getName().equals("messageType"))
                messageType = parseInt((String)tagattribute.getValue());
            else
            if(tagattribute.getName().equals("charSet"))
                charSet = parseInt((String)tagattribute.getValue());
            else
            if(tagattribute.getName().equals("timeout"))
                timeout = Integer.valueOf((String)tagattribute.getValue()).intValue();
            else
            if(tagattribute.getName().equals("encoding"))
                encoding = parseInt((String)tagattribute.getValue());
            else
            if(tagattribute.getName().equals("replyToQmgr"))
                QmgrReply = (String)tagattribute.getValue();
            else
            if(tagattribute.getName().equals("format"))
                format = parseString((String)tagattribute.getValue());
            else
            if(tagattribute.getName().equals("applicationName"))
                applicationName = (String)tagattribute.getValue();
            else
            if(tagattribute.getName().equals("userId"))
                userId = (String)tagattribute.getValue();
            else
            if(tagattribute.getName().equals("expiry"))
                expiry = Integer.valueOf((String)tagattribute.getValue()).intValue();
            else
            if(tagattribute.getName().equals("messageOrCorrelation"))
            {
                if(tagattribute.getValue().equals("message"))
                    matchCorrelationId = false;
                else
                    matchCorrelationId = true;
            } else
            if(tagattribute.getName().equals("AliasQMgrNameSend"))
                AliasQueueManagerNameSend = (String)tagattribute.getValue();
            else
            if(tagattribute.getName().equals("sendModelQ"))
                SendModelQName = (String)tagattribute.getValue();
            else
            if(tagattribute.getName().equals("sendDynamicQTemplate"))
                SendDynamicQName = (String)tagattribute.getValue();
            else
            if(tagattribute.getName().equals("replyModelQ"))
                ReplyModelQName = (String)tagattribute.getValue();
            else
            if(tagattribute.getName().equals("replyDynamicQTemplate"))
                ReplyDynamicQName = (String)tagattribute.getValue();
            else
            if(tagattribute.getName().equals("sendToQType"))
            {
                if(((String)tagattribute.getValue()).equals(MODEL))
                    sendTypeNormal = false;
                else
                    sendTypeNormal = true;
            } else
            if(tagattribute.getName().equals("replyToQType"))
                if(((String)tagattribute.getValue()).equals(MODEL))
                    replyTypeNormal = false;
                else
                    replyTypeNormal = true;
        }

        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "Parameters initialized");
        externalizer();
        if(Externalizer.isRunTime())
            initInstance();
        return this;
    }

    private void initializeValues()
    {
        inUse = false;
        closedIssued = true;
        receiveSem = new Semaphore();
        aQueueListener = null;
        deadLetterQName = null;
        channelQName = null;
        receiveReturnCode = 0;
        sendReturnCode = 0;
        synchronousMode = false;
        server = true;
        isListenThreadAlive = false;
        ReplyQueueAccessed = false;
        SendQueueAccessed = false;
        DeadLetterAccessed = false;
        ChannelQueueAccessed = false;
        sendSyncronizer = new Object();
        receiveSyncronizer = new Object();
        tempMqMessage = null;
        expiry = -1;
        automaticConnectionEstablishment = false;
        eventParameter = new Hashtable(2);
        eventParameterD = new Hashtable(2);
        messageTable = new Hashtable(2);
        messageQTable = new Hashtable(2);
        deadLetterQueueThread = null;
        SendToQOpenOptions = 16;
        ReplyToQOpenOptions = 1;
        DeadLetterQOpenOptions = 1;
        PutMessageOptionsOptions = 64;
        GetMessageOptionsOptions = 0x12001;
        DeadLetterOptionsOptions = 8193;
        port = 1414;
        Latin1 = "8859_1";
        EXCEPTION = "exception";
        persistence = 2;
        messageType = 8;
        charSet = 0;
        encoding = 273;
        QmgrReply = null;
        format = "        ";
        timeout = 0;
        userId = null;
        applicationName = null;
        matchCorrelationId = true;
    }

    public void initInstance()
        throws DSECCException, DSEInvalidArgumentException
    {
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "initInstance method");
        connectionHandler = new ConnectionHandler();
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "Registering handlers for events");
        if(!getSynchronousMode())
            addHandler(connectionHandler, "message");
        addHandler(connectionHandler, "errorReceived");
        addHandler(connectionHandler, "opened");
        addHandler(connectionHandler, "closed");
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "Handlers for events registered");
        if(!server)
        {
            MQEnvironment.hostname = hostName;
            MQEnvironment.channel = channel;
            MQEnvironment.port = port;
            if(Trace.doTrace("#MQ", 512, 2))
                Trace.trace("#MQ", 512, 2, getName(), "MQSeries working as client.Environment parameters initialized.");
        }
        try
        {
            QueueManagerSend = new MQQueueManager(QMgrName);
            if(Trace.doTrace("#MQ", 512, 2))
                Trace.trace("#MQ", 512, 2, getName(), "Send MQManager created");
            QueueManagerReceive = new MQQueueManager(QMgrName);
            if(Trace.doTrace("#MQ", 512, 2))
                Trace.trace("#MQ", 512, 2, getName(), "Reply MQManager created");
        }
        catch(MQException mqexception)
        {
        	mqexception.printStackTrace();
            throw returnException(mqexception);
        }
        if(deadLetterQName != null)
        {
            try
            {
                DeadLetterQManager = new MQQueueManager(QMgrName);
                if(Trace.doTrace("#MQ", 512, 2))
                    Trace.trace("#MQ", 512, 2, getName(), "DeadLetterQ MQManager created");
            }
            catch(MQException mqexception1)
            {
                throw returnException(mqexception1);
            }
            dlo = new MQGetMessageOptions();
            dlo.options = DeadLetterQOpenOptions;
        }
        if(channelQName != null)
            try
            {
                ChannelQManager = new MQQueueManager(QMgrName);
                if(Trace.doTrace("#MQ", 512, 2))
                    Trace.trace("#MQ", 512, 2, getName(), "Channel MQManager created");
            }
            catch(MQException mqexception2)
            {
            	mqexception2.printStackTrace();
                throw returnException(mqexception2);
            }
        if(automaticConnectionEstablishment)
            try
            {
                establishConnection();
            }
            catch(Exception exception)
            {
            	exception.printStackTrace();
                signalEvent("closed");
            }
    }

    public void initManagers()
        throws MQException
    {
        try
        {
            QueueManagerSend = new MQQueueManager(QMgrName);
            if(Trace.doTrace("#MQ", 512, 2))
                Trace.trace("#MQ", 512, 2, getName(), "Send MQManager created");
            QueueManagerReceive = new MQQueueManager(QMgrName);
            if(Trace.doTrace("#MQ", 512, 2))
                Trace.trace("#MQ", 512, 2, getName(), "Reply MQManager created");
        }
        catch(MQException mqexception)
        {
            throw mqexception;
        }
        if(deadLetterQName != null)
            try
            {
                DeadLetterQManager = new MQQueueManager(QMgrName);
                if(Trace.doTrace("#MQ", 512, 2))
                    Trace.trace("#MQ", 512, 2, getName(), "DeadLetterQ MQManager created");
            }
            catch(MQException mqexception1)
            {
                throw mqexception1;
            }
        if(channelQName != null)
            try
            {
                ChannelQManager = new MQQueueManager(QMgrName);
                if(Trace.doTrace("#MQ", 512, 2))
                    Trace.trace("#MQ", 512, 2, getName(), "Channel MQManager created");
            }
            catch(MQException mqexception2)
            {
                throw mqexception2;
            }
        if(automaticConnectionEstablishment)
            try
            {
                establishConnection();
            }
            catch(Exception exception)
            {
                try
                {
                    signalEvent("closed");
                }
                catch(DSEInvalidArgumentException dseinvalidargumentexception)
                {
                    if(Trace.doTrace("#MQ", 512, 8))
                        Trace.trace("#MQ", 512, 8, getName(), "Exception in signalEvent CLOSED");
                }
            }
    }

    public boolean isCloseMessage(Object obj)
    {
        return ((MQMD) ((MQMessage)obj)).userId.equals("dseUser".trim());
    }

    public boolean isDeadLetterQReset()
    {
        return DeadLetterQManager == null || !DeadLetterQManager.isConnected();
    }

    public boolean isFree()
    {
        return !getInUse();
    }

    public boolean isOffline()
    {
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "Service is offline:" + (!SendQueueAccessed || !ReplyQueueAccessed));
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "SendQueue access:" + SendQueueAccessed);
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "ReplyQueue access:" + ReplyQueueAccessed);
        return !SendQueueAccessed || !ReplyQueueAccessed;
    }

    public boolean isResetStatus()
    {
        return QueueManagerSend == null || QueueManagerReceive == null || !QueueManagerSend.isConnected() || !QueueManagerReceive.isConnected();
    }

    public int parseInt(String s)
    {
        int i = 0;
        int j = s.length();
        int k = 0;
        try
        {
            k = Integer.valueOf(s).intValue();
        }
        catch(NumberFormatException numberformatexception)
        {
            StringBuffer stringbuffer = new StringBuffer(s);
            stringbuffer.append(' ');
            while(i < j - 1) 
            {
                StringBuffer stringbuffer1 = new StringBuffer();
                for(; (stringbuffer.charAt(i) != '|') & (i < j); i++)
                    stringbuffer1 = stringbuffer1.append(stringbuffer.charAt(i));

                i++;
                try
                {
                    encode = (class$com$ibm$mq$MQC != null ? class$com$ibm$mq$MQC : (class$com$ibm$mq$MQC = class$("com.ibm.mq.MQC"))).getDeclaredField(stringbuffer1.toString().trim());
                    k += encode.getInt(encode.getName());
                }
                catch(NoSuchFieldException nosuchfieldexception)
                {
                    if(Trace.doTrace("#MQ", 512, 8))
                        Trace.trace("#MQ", 512, 8, getName(), "Unable to find MQC field");
                }
                catch(IllegalAccessException illegalaccessexception)
                {
                    if(Trace.doTrace("#MQ", 512, 8))
                        Trace.trace("#MQ", 512, 8, getName(), "Unable to access MQC Interface");
                }
            }
        }
        return k;
    }

    private String parseString(String s)
    {
        int i = 0;
        int j = s.length();
        String s1 = null;
        if(j < 8)
        {
            s1 = s;
        } else
        {
            StringBuffer stringbuffer = new StringBuffer(s);
            stringbuffer.append(' ');
            while(i < j - 1) 
            {
                StringBuffer stringbuffer1 = new StringBuffer();
                for(; (stringbuffer.charAt(i) != '|') & (i < j); i++)
                    stringbuffer1 = stringbuffer1.append(stringbuffer.charAt(i));

                i++;
                try
                {
                    encode = (class$com$ibm$mq$MQC != null ? class$com$ibm$mq$MQC : (class$com$ibm$mq$MQC = class$("com.ibm.mq.MQC"))).getDeclaredField(stringbuffer1.toString());
                    s1 = (String)encode.get(encode);
                }
                catch(NoSuchFieldException nosuchfieldexception)
                {
                    if(Trace.doTrace("#MQ", 512, 8))
                        Trace.trace("#MQ", 512, 8, getName(), "Unable to find MQC field");
                }
                catch(IllegalAccessException illegalaccessexception)
                {
                    if(Trace.doTrace("#MQ", 512, 8))
                        Trace.trace("#MQ", 512, 8, getName(), "Unable to access MQC Interface");
                }
            }
        }
        return s1;
    }

    public byte[] putToPutSendQueue(MQMessage mqmessage)
        throws MQException
    {
        if(SendToQueue == null)
            throw new MQException(2, 2085, null);
        try
        {
            if(Trace.doTrace("#MQ", 512, 2))
                Trace.trace("#MQ", 512, 2, getName(), "Putting message in the send queue");
            SendToQueue.put(mqmessage, pmo);
            if(Trace.doTrace("#MQ", 512, 2))
                Trace.trace("#MQ", 512, 2, getName(), "Message has been put in send the queue");
        }
        catch(MQException mqexception)
        {
            if(Trace.doTrace("#MQ", 512, 8))
                Trace.trace("#MQ", 512, 8, getName(), "Exception on send method:" + mqexception.toString());
            sendReturnCode = mqexception.reasonCode;
            if(sendReturnCode == 2019 || sendReturnCode == 2009 || sendReturnCode == 2161)
            {
                SendQueueAccessed = false;
                initManagers();
            }
            eventParameter.put(EXCEPTION, mqexception);
            if(SendToQName != null)
                eventParameter.put("QUEUE", SendToQName);
            else
            if(SendDynamicQName != null)
                eventParameter.put("QUEUE", SendDynamicQName);
            try
            {
                signalEvent("errorReceived", eventParameter);
            }
            catch(DSEInvalidArgumentException dseinvalidargumentexception)
            {
                if(Trace.doTrace("#MQ", 512, 8))
                    Trace.trace("#MQ", 512, 8, getName(), "Exception on send method:" + dseinvalidargumentexception.toString());
            }
        }
        return ((MQMD) (mqmessage)).messageId;
    }

    public byte[] putToPutSendQueue(String s)
        throws MQException, IOException
    {
        if(SendToQueue == null)
            throw new MQException(2, 2085, null);
        MQMessage mqmessage = new MQMessage();
        mqmessage.messageType = getMessageType();
        if(ReplyToQName != null || ReplyModelQName != null)
        {
            if(QmgrReply != "")
                mqmessage.replyToQueueManagerName = getQmgrReply();
            if(ReplyToQName != null)
                mqmessage.replyToQueueName = ReplyToQName;
            else
            if(ReplyModelQName != null)
                mqmessage.replyToQueueName = ReplyDynamicQName;
        }
        mqmessage.persistence = getPersistence();
        mqmessage.characterSet = getCharSet();
        mqmessage.encoding = getEncoding();
        mqmessage.format = getFormat();
        mqmessage.userId = getUserId();
        mqmessage.putApplicationName = getApplicationName();
        mqmessage.expiry = getExpiry();
        mqmessage.write(s.getBytes(Latin1));
        try
        {
            if(Trace.doTrace("#MQ", 512, 2))
                Trace.trace("#MQ", 512, 2, getName(), "Putting message in the send queue");
            SendToQueue.put(mqmessage, pmo);
            if(Trace.doTrace("#MQ", 512, 2))
                Trace.trace("#MQ", 512, 2, getName(), "Message has been put in send the queue");
        }
        catch(MQException mqexception)
        {
            if(Trace.doTrace("#MQ", 512, 8))
                Trace.trace("#MQ", 512, 8, getName(), "Exception on putToPutSendQueue method:" + mqexception.toString());
            sendReturnCode = mqexception.reasonCode;
            if(sendReturnCode == 2019 || sendReturnCode == 2009 || sendReturnCode == 2161 || sendReturnCode == 2195)
            {
                SendQueueAccessed = false;
                initManagers();
            }
            eventParameter.put(EXCEPTION, mqexception);
            eventParameter.put("QUEUE", ReplyToQName);
            try
            {
                signalEvent("errorReceived", eventParameter);
            }
            catch(DSEInvalidArgumentException dseinvalidargumentexception)
            {
                if(Trace.doTrace("#MQ", 512, 8))
                    Trace.trace("#MQ", 512, 8, getName(), "Exception on send method:" + dseinvalidargumentexception.toString());
            }
        }
        return ((MQMD) (mqmessage)).messageId;
    }

    protected void receive()
        throws IOException
    {
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "Receive in listen thread");
        try
        {
            gmo.waitInterval = -1;
            tempMqMessage = getFromGetReplyQueue();
            if(!isCloseMessage(tempMqMessage))
            {
                receiveReturnCode = 0;
                messageTable.put("message", tempMqMessage);
                messageTable.put("QUEUE", getReplyToQName());
                signalEvent("message", messageTable);
                if(Trace.doTrace("#MQ", 512, 2))
                    Trace.trace("#MQ", 512, 2, getName(), "Event DATA signaled");
            }
        }
        catch(DSEInvalidArgumentException dseinvalidargumentexception)
        {
            dseinvalidargumentexception.printStackTrace();
        }
        catch(MQException mqexception)
        {
            if(Trace.doTrace("#MQ", 512, 8))
                Trace.trace("#MQ", 512, 8, getName(), "Exception on receive MQException" + mqexception.toString());
            receiveReturnCode = mqexception.reasonCode;
            eventParameter.put(EXCEPTION, mqexception);
            if(ReplyToQName != null)
                eventParameter.put("QUEUE", ReplyToQName);
            else
            if(ReplyDynamicQName != null)
                eventParameter.put("QUEUE", ReplyDynamicQName);
            try
            {
                signalEvent("errorReceived", eventParameter);
            }
            catch(DSEInvalidArgumentException dseinvalidargumentexception1)
            {
                if(Trace.doTrace("#MQ", 512, 8))
                    Trace.trace("#MQ", 512, 8, getName(), "Exception in receive" + dseinvalidargumentexception1.toString());
            }
        }
        catch(NullPointerException nullpointerexception)
        {
            if(Trace.doTrace("#MQ", 512, 8))
                Trace.trace("#MQ", 512, 8, getName(), "Exception on receive NullPointerException" + nullpointerexception.toString());
            eventParameter.put(EXCEPTION, nullpointerexception);
            if(ReplyToQName != null)
                eventParameter.put("QUEUE", ReplyToQName);
            else
            if(ReplyDynamicQName != null)
                eventParameter.put("QUEUE", ReplyDynamicQName);
            try
            {
                signalEvent("errorReceived", eventParameter);
            }
            catch(DSEInvalidArgumentException dseinvalidargumentexception2)
            {
                if(Trace.doTrace("#MQ", 512, 8))
                    Trace.trace("#MQ", 512, 8, getName(), "Exception in receive" + dseinvalidargumentexception2.toString());
            }
        }
    }

    public Object receive(byte abyte0[])
        throws MQException, IOException
    {
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "Receive method:correlationId");
        MQMessage mqmessage = null;
        if(getTimeout() > 0)
            gmo.waitInterval = getTimeout();
        else
            gmo.waitInterval = -1;
        mqmessage = getFromGetReplyQueue(abyte0);
        return mqmessage;
    }

    public Object receive(byte abyte0[], long l)
        throws MQException, IOException
    {
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "Receive method:correlationId,timeout");
        gmo.waitInterval = (int)l;
        tempMqMessage = getFromGetReplyQueue(abyte0);
        return tempMqMessage;
    }

    private int receive1(byte abyte0[])
    {
        int k1 = 0;
        int l1 = 0;
        MQMessage mqmessage = new MQMessage();
        if(matchCorrelationId)
            mqmessage.correlationId = abyte0;
        else
            mqmessage.messageId = abyte0;
        gmo.waitInterval = -1;
        try
        {
            channelQueue.get(mqmessage, gmo);
            int i = mqmessage.readInt();
            int j = mqmessage.readInt();
            int k = mqmessage.readInt();
            int l = mqmessage.readInt();
            int i1 = mqmessage.readInt();
            int j1 = mqmessage.readInt();
            k1 = mqmessage.readInt();
            l1 = mqmessage.readInt();
            int i2 = mqmessage.readInt();
        }
        catch(MQException mqexception)
        {
            mqexception.printStackTrace();
        }
        catch(IOException ioexception)
        {
            ioexception.printStackTrace();
        }
        if(k1 != 0)
            try
            {
                channelQueue.get(mqmessage, gmo);
            }
            catch(MQException mqexception1)
            {
                receiveReturnCode = mqexception1.reasonCode;
                if(Trace.doTrace("#MQ", 512, 8))
                    Trace.trace("#MQ", 512, 8, getName(), "Exception on receive method:" + mqexception1.toString() + "\n Return Code:" + receiveReturnCode);
                eventParameter.put(EXCEPTION, mqexception1);
                if(ReplyToQName != null)
                    eventParameter.put("QUEUE", ReplyToQName);
                else
                if(ReplyDynamicQName != null)
                    eventParameter.put("QUEUE", ReplyDynamicQName);
                try
                {
                    signalEvent("errorReceived", eventParameter);
                }
                catch(DSEInvalidArgumentException dseinvalidargumentexception)
                {
                    if(Trace.doTrace("#MQ", 512, 8))
                        Trace.trace("#MQ", 512, 8, getName(), "Exception on receive method:" + dseinvalidargumentexception.toString());
                }
            }
        if(l1 == 4031)
            return 1;
        else
            return k1;
    }

    public void receiveFromDeadLetterQueue()
    {
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "receiveFromDeadLetterQueue method");
        try
        {
            dlo.waitInterval = -1;
            dlo.matchOptions = 0;
            MQMessage mqmessage = getFromDeadLetterQueue();
            messageQTable.put("message", mqmessage);
            messageQTable.put("QUEUE", deadLetterQName);
            if(Trace.doTrace("#MQ", 512, 2))
                Trace.trace("#MQ", 512, 2, getName(), "Data received FromDeadLetterQueue");
            signalEvent("message", messageQTable);
            if(Trace.doTrace("#MQ", 512, 2))
                Trace.trace("#MQ", 512, 2, getName(), "Event DATA signaled");
        }
        catch(Exception exception)
        {
            eventParameterD.put(EXCEPTION, exception);
            eventParameterD.put("QUEUE", deadLetterQName);
            try
            {
                signalEvent("errorReceived", eventParameter);
            }
            catch(DSEInvalidArgumentException dseinvalidargumentexception)
            {
                if(Trace.doTrace("#MQ", 512, 8))
                    Trace.trace("#MQ", 512, 8, getName(), "Exception in receive from deadletterqueue" + dseinvalidargumentexception.toString());
            }
        }
    }

    private int receiveStatus(byte abyte0[])
    {
        int k2 = -1;
        MQMessage mqmessage = new MQMessage();
        if(matchCorrelationId)
            mqmessage.correlationId = abyte0;
        else
            mqmessage.messageId = abyte0;
        gmo.waitInterval = -1;
        try
        {
            channelQueue.get(mqmessage, gmo);
            int i = mqmessage.readInt();
            int j = mqmessage.readInt();
            int k = mqmessage.readInt();
            int l = mqmessage.readInt();
            int i1 = mqmessage.readInt();
            int j1 = mqmessage.readInt();
            int k1 = mqmessage.readInt();
            int l1 = mqmessage.readInt();
            int i2 = mqmessage.readInt();
            int l2 = 1;
            for(boolean flag = false; l2 <= i2 && !flag;)
            {
                int j2 = mqmessage.readInt();
                switch(j2)
                {
                case 4: // '\004'
                    mqmessage.readInt();
                    mqmessage.readInt();
                    mqmessage.readInt();
                    int i3 = mqmessage.readInt();
                    String s = mqmessage.readString(i3);
                    break;

                case 3: // '\003'
                    mqmessage.readInt();
                    int j3 = mqmessage.readInt();
                    k2 = mqmessage.readInt();
                    if(j3 == 1527)
                        flag = true;
                    break;

                default:
                    k2 = -1;
                    flag = true;
                    break;
                }
            }

        }
        catch(MQException mqexception)
        {
            mqexception.printStackTrace();
        }
        catch(IOException ioexception)
        {
            ioexception.printStackTrace();
        }
        if(k2 == -1)
            try
            {
                channelQueue.get(mqmessage, gmo);
            }
            catch(MQException mqexception1)
            {
                mqexception1.printStackTrace();
            }
        return k2;
    }

    public void removeQueueListener(QueueListener queuelistener)
    {
        aQueueListener = QueueEventMulticaster.remove(aQueueListener, queuelistener);
    }

    public int resetChannel(String s)
        throws MQException
    {
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "Reset channel: " + s);
        if(!ChannelQueueAccessed)
            channelQueue = ChannelQManager.accessQueue(channelQName, ReplyToQOpenOptions);
        try
        {
            MQMessage mqmessage = new MQMessage();
            mqmessage.messageType = 1;
            mqmessage.replyToQueueManagerName = QMgrName;
            mqmessage.replyToQueueName = channelQName;
            mqmessage.format = "MQADMIN ";
            mqmessage.report = 128;
            mqmessage.persistence = 1;
            mqmessage.writeInt(1);
            mqmessage.writeInt(36);
            mqmessage.writeInt(1);
            mqmessage.writeInt(27);
            mqmessage.writeInt(1);
            mqmessage.writeInt(1);
            mqmessage.writeInt(0);
            mqmessage.writeInt(0);
            mqmessage.writeInt(1);
            mqmessage.writeInt(4);
            mqmessage.writeInt(40);
            mqmessage.writeInt(3501);
            mqmessage.writeInt(0);
            mqmessage.writeInt(20);
            int i = s.length();
            for(int j = i; j < 20; j++)
                s = s.concat(" ");

            mqmessage.writeString(s);
            systemCommandQueue.put(mqmessage, pmo);
            return receive1(((MQMD) (mqmessage)).messageId);
        }
        catch(IOException ioexception)
        {
            return 2;
        }
    }

    public int resetChannel(String s, int i)
        throws MQException
    {
        if(Trace.doTrace("#MQ", 512, 2))
        {
            Trace.trace("#MQ", 512, 2, getName(), "Reset channel: " + s);
            Trace.trace("#MQ", 512, 2, getName(), "New Message Sequence Number: " + i);
        }
        if(!ChannelQueueAccessed)
            channelQueue = ChannelQManager.accessQueue(channelQName, ReplyToQOpenOptions);
        try
        {
            MQMessage mqmessage = new MQMessage();
            mqmessage.messageType = 1;
            mqmessage.replyToQueueManagerName = QMgrName;
            mqmessage.replyToQueueName = channelQName;
            mqmessage.format = "MQADMIN ";
            mqmessage.report = 128;
            mqmessage.persistence = 1;
            mqmessage.writeInt(1);
            mqmessage.writeInt(36);
            mqmessage.writeInt(1);
            mqmessage.writeInt(27);
            mqmessage.writeInt(1);
            mqmessage.writeInt(1);
            mqmessage.writeInt(0);
            mqmessage.writeInt(0);
            mqmessage.writeInt(2);
            mqmessage.writeInt(4);
            mqmessage.writeInt(40);
            mqmessage.writeInt(3501);
            mqmessage.writeInt(0);
            mqmessage.writeInt(20);
            int j = s.length();
            for(int k = j; k < 20; k++)
                s = s.concat(" ");

            mqmessage.writeString(s);
            mqmessage.writeInt(3);
            mqmessage.writeInt(16);
            mqmessage.writeInt(1514);
            mqmessage.writeInt(i);
            systemCommandQueue.put(mqmessage, pmo);
            return receive1(((MQMD) (mqmessage)).messageId);
        }
        catch(IOException ioexception)
        {
            return 2;
        }
    }

    public DSECCException returnException(Exception exception)
    {
        if(((MQException)exception).completionCode == 1)
            return new DSECCException(DSEException.harmless, String.valueOf(((MQException)exception).reasonCode), exception.toString());
        else
            return new DSECCException(DSEException.critical, String.valueOf(((MQException)exception).reasonCode), exception.toString());
    }

    public String returnStringFromMessage(Object obj)
        throws DSECCException
    {
        try
        {
            MQMessage mqmessage = (MQMessage)obj;
            StringBuffer stringbuffer = new StringBuffer(mqmessage.readString(mqmessage.getDataLength()));
            return new String(stringbuffer);
        }
        catch(IOException ioexception)
        {
            throw new DSECCException(DSEException.critical, "00000000", "Error getting String from message ,error : " + ioexception);
        }
    }

    public void run()
    {
    	System.out.println("in MQConnection.run()");
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "Running in listen thread");
        while(isListenThreadAlive) 
        {
            if(!ReplyQueueAccessed)
                receiveSem.waitOn();
            try
            {
                receive();
            }
            catch(Throwable throwable)
            {
                if(Trace.doTrace("#MQ", 512, 8))
                    Trace.trace("#MQ", 512, 8, getName(), "listenThread exception: " + throwable.toString());
            }
        }
        listenThread = null;
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "Listen thread stopped");
    }

    public byte[] send(Context context, FormatElement formatelement)
        throws DSEException, MQException, IOException
    {
        byte abyte0[] = null;
        String s = formatelement.format(context);
        try
        {
            abyte0 = send(s);
        }
        catch(IOException ioexception)
        {
            if(Trace.doTrace("#MQ", 512, 8))
                Trace.trace("#MQ", 512, 8, getName(), "Error - send(Context, Format=" + formatelement + " exception: " + ioexception);
            throw new DSEException(DSEException.critical, "000000000000", "Exception: " + ioexception);
        }
        return abyte0;
    }

    public byte[] send(Context context, String s)
        throws DSEException, MQException
    {
        byte abyte0[];
        try
        {
            abyte0 = send(context, (FormatElement)FormatElement.readObject(s));
        }
        catch(IOException ioexception)
        {
            if(Trace.doTrace("#MQ", 512, 8))
                Trace.trace("#MQ", 512, 8, getName(), "Error - send(Context, FormatName=" + s + " exception: " + ioexception);
            throw new DSEException(DSEException.critical, "000000000000", "Exception: " + ioexception);
        }
        return abyte0;
    }

    public byte[] send(Object obj)
        throws MQException
    {
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "send method:inputMessage");
        byte abyte0[] = null;
        if((SendToQName != null || SendDynamicQName != null) && !SendQueueAccessed || (ReplyToQName != null || ReplyDynamicQName != null) && !ReplyQueueAccessed)
            establishConnection();
        abyte0 = putToPutSendQueue((MQMessage)obj);
        return abyte0;
    }

    public byte[] send(String s)
        throws MQException, IOException
    {
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "send method:String: " + s);
        byte abyte0[] = null;
        if((SendToQName != null || SendDynamicQName != null) && !SendQueueAccessed || (ReplyToQName != null || ReplyDynamicQName != null) && !ReplyQueueAccessed)
            establishConnection();
        abyte0 = putToPutSendQueue(s);
        return abyte0;
    }

    public void setAliasQueueManagerNameSend(String s)
    {
        AliasQueueManagerNameSend = s;
    }

    public void setApplicationName(String s)
    {
        applicationName = s;
    }

    public void setAutomaticConnectionEstablishment(boolean flag)
    {
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "setAutomaticConnectionEstablishment: " + flag);
        automaticConnectionEstablishment = flag;
    }

    public void setChannelName(String s)
    {
        channel = s;
    }

    public void setCharSet(int i)
    {
        charSet = i;
    }

    public void setDeadLetterMessageOptionsMatchOptions(int i)
    {
        dlo.matchOptions = i;
    }

    public void setDeadLetterMessageOptionsWaitInterval(int i)
    {
        dlo.waitInterval = i;
    }

    public void setDeadLetterQName(String s)
    {
        deadLetterQName = s;
    }

    public void setEncoding(int i)
    {
        encoding = i;
    }

    public void setEstablishConnectionRetries(int i)
    {
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "setEstablishConnectionRetries: " + i);
        EstablishConnectionRetries = i;
    }

    public void setExpiry(int i)
    {
        expiry = i;
    }

    public void setFormat(String s)
    {
        format = s;
    }

    public void setGetMessageOptionsMatchOptions(int i)
    {
        gmo.matchOptions = i;
    }

    public void setGetMessageOptionsOptions(int i)
    {
        gmo.options = i;
    }

    public void setGetMessageOptionsWaitInterval(int i)
    {
        gmo.waitInterval = i;
    }

    public void setHostName(String s)
    {
        hostName = s;
    }

    public synchronized void setInUse(boolean flag)
    {
        inUse = flag;
    }

    public void setMatchCorrelation(boolean flag)
    {
        matchCorrelationId = flag;
    }

    public void setMessageType(int i)
    {
        messageType = i;
    }

    public void setPersistence(int i)
    {
        persistence = i;
    }

    public void setPortNumber(int i)
    {
        port = i;
    }

    public void setPutMessageOptionsOptions(int i)
    {
        pmo.options = i;
    }

    public void setQmgrName(String s)
    {
        QMgrName = s;
    }

    public void setQmgrReply(String s)
    {
        QmgrReply = s;
    }

    public void setReplyDynamicQName(String s)
    {
        ReplyDynamicQName = s;
    }

    public void setReplyQName(String s)
    {
        ReplyToQName = s;
    }

    public void setReplyToQOpenOptions(int i)
    {
        ReplyToQOpenOptions = i;
    }

    public void setSendDynamicQName(String s)
    {
        SendDynamicQName = s;
    }

    public void setSendQName(String s)
    {
        SendToQName = s;
    }

    public void setSendToQOpenOptions(int i)
    {
        SendToQOpenOptions = i;
    }

    public void setServer(boolean flag)
    {
        server = flag;
    }

    public void setSynchronousMode(boolean flag)
    {
        synchronousMode = flag;
    }

    public void setTimeBetweenRetries(int i)
    {
        if(i < 0)
            TimeBetweenRetries = 0;
        else
            TimeBetweenRetries = i * 1000;
        establishConnectionCounter = TimeBetweenRetries;
    }

    public void setTimeout(int i)
    {
        timeout = i;
    }

    public void setUserId(String s)
    {
        userId = s;
    }

    private void start(String s)
    {
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "Start thread: " + s);
        if(s.equals("Listen"))
        {
            isListenThreadAlive = true;
            listenThread.setPriority(1);
            listenThread.start();
        }
        if(s.equals("DeadLetterQ"))
        {
            if(deadLetterQueueThread == null)
                deadLetterQueueThread = new MqDeadLetterQueueThread(this, s);
            isDeadLetterThreadAlive = true;
            deadLetterQueueThread.start();
        }
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "Thread: " + s + " started");
    }

    public int startChannel(String s)
        throws MQException
    {
        if(server)
        {
            if(Trace.doTrace("#MQ", 512, 2))
                Trace.trace("#MQ", 512, 2, getName(), "Start channel : " + s);
            if(!ChannelQueueAccessed)
                channelQueue = ChannelQManager.accessQueue(channelQName, ReplyToQOpenOptions);
            try
            {
                MQMessage mqmessage = new MQMessage();
                mqmessage.messageType = 1;
                mqmessage.replyToQueueManagerName = QMgrName;
                mqmessage.replyToQueueName = channelQName;
                mqmessage.format = "MQADMIN ";
                mqmessage.report = 128;
                mqmessage.persistence = 1;
                mqmessage.writeInt(1);
                mqmessage.writeInt(36);
                mqmessage.writeInt(1);
                mqmessage.writeInt(28);
                mqmessage.writeInt(1);
                mqmessage.writeInt(1);
                mqmessage.writeInt(0);
                mqmessage.writeInt(0);
                mqmessage.writeInt(1);
                mqmessage.writeInt(4);
                mqmessage.writeInt(40);
                mqmessage.writeInt(3501);
                mqmessage.writeInt(0);
                mqmessage.writeInt(20);
                int i = s.length();
                for(int j = i; j < 20; j++)
                    s = s.concat(" ");

                mqmessage.writeString(s);
                systemCommandQueue.put(mqmessage, pmo);
                return receive1(((MQMD) (mqmessage)).messageId);
            }
            catch(IOException ioexception)
            {
                return 2;
            }
        } else
        {
            return -1;
        }
    }

    public int stopChannel(String s)
        throws MQException
    {
        if(server)
        {
            if(Trace.doTrace("#MQ", 512, 2))
                Trace.trace("#MQ", 512, 2, getName(), "Stop channel : " + s);
            if(!ChannelQueueAccessed)
                channelQueue = ChannelQManager.accessQueue(channelQName, ReplyToQOpenOptions);
            try
            {
                MQMessage mqmessage = new MQMessage();
                mqmessage.messageType = 1;
                mqmessage.replyToQueueManagerName = QMgrName;
                mqmessage.replyToQueueName = channelQName;
                mqmessage.format = "MQADMIN ";
                mqmessage.report = 128;
                mqmessage.persistence = 1;
                mqmessage.writeInt(1);
                mqmessage.writeInt(36);
                mqmessage.writeInt(1);
                mqmessage.writeInt(29);
                mqmessage.writeInt(1);
                mqmessage.writeInt(1);
                mqmessage.writeInt(0);
                mqmessage.writeInt(0);
                mqmessage.writeInt(1);
                mqmessage.writeInt(4);
                mqmessage.writeInt(40);
                mqmessage.writeInt(3501);
                mqmessage.writeInt(0);
                mqmessage.writeInt(20);
                int i = s.length();
                for(int j = i; j < 20; j++)
                    s = s.concat(" ");

                mqmessage.writeString(s);
                systemCommandQueue.put(mqmessage, pmo);
                return receive1(((MQMD) (mqmessage)).messageId);
            }
            catch(IOException ioexception)
            {
                return 2;
            }
        } else
        {
            return -1;
        }
    }

    public void terminate()
    {
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "Terminating connections");
        if(!closedIssued)
            try
            {
                closeConnection();
            }
            catch(MQException mqexception)
            {
                if(Trace.doTrace("#MQ", 512, 8))
                    Trace.trace("#MQ", 512, 8, getName(), "Exception in close connection: " + mqexception.toString());
            }
        try
        {
            disconnectQueueManager();
        }
        catch(MQException mqexception1)
        {
            if(Trace.doTrace("#MQ", 512, 8))
                Trace.trace("#MQ", 512, 8, getName(), "Exception in disconnect queuemanagers: " + mqexception1.toString());
        }
        finalizeValues();
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "Connections terminated");
        try
        {
            super.terminate();
        }
        catch(DSEException dseexception)
        {
            if(Trace.doTrace("#MQ", 512, 8))
                Trace.trace("#MQ", 512, 8, getName(), "exception in super.terminate: " + dseexception.toString());
        }
    }

    public String toString()
    {
        StringBuffer stringbuffer = new StringBuffer("<MQConnection ");
        stringbuffer.append("QMgrName=" + JavaExtensions.doubleQuote(QMgrName) + " ");
        if(SendToQName != null)
            stringbuffer.append("sendToQ=" + JavaExtensions.doubleQuote(SendToQName) + " ");
        if(ReplyToQName != null)
            stringbuffer.append("replyToQ=" + JavaExtensions.doubleQuote(ReplyToQName) + " ");
        if(SendDynamicQName != null)
            stringbuffer.append("sendToQ=" + JavaExtensions.doubleQuote(SendDynamicQName) + " ");
        if(ReplyDynamicQName != null)
            stringbuffer.append("replyToQ=" + JavaExtensions.doubleQuote(ReplyDynamicQName) + " ");
        if(deadLetterQName != null)
            stringbuffer.append("deadLetterQ=" + JavaExtensions.doubleQuote(deadLetterQName) + " ");
        if(synchronousMode)
            stringbuffer.append("synchronousMode=\"enabled\" ");
        else
            stringbuffer.append("synchronousMode=\"disabled\" ");
        if(server)
        {
            stringbuffer.append("serverOrClient=\"server\" ");
        } else
        {
            stringbuffer.append("ServerOrClient=\"client\" ");
            stringbuffer.append("host=" + JavaExtensions.doubleQuote(hostName) + " " + "channel=" + JavaExtensions.doubleQuote(channel) + " " + "port=" + JavaExtensions.doubleQuote((new Integer(port)).toString()) + " ");
        }
        if(channelQName != null)
            stringbuffer.append(" channelQ=" + channelQName + " ");
        stringbuffer.append("sendToQOpenOptions=" + JavaExtensions.doubleQuote((new Integer(SendToQOpenOptions)).toString()) + " " + "replyToQOpenOptions=" + JavaExtensions.doubleQuote((new Integer(ReplyToQOpenOptions)).toString()) + " " + "putMessageOptions=" + JavaExtensions.doubleQuote((new Integer(PutMessageOptionsOptions)).toString()) + " " + "getMessageOptions=" + JavaExtensions.doubleQuote((new Integer(GetMessageOptionsOptions)).toString()) + " ");
        stringbuffer.append("persistence=" + JavaExtensions.doubleQuote((new Integer(persistence)).toString()) + " ");
        stringbuffer.append("messageType=" + JavaExtensions.doubleQuote((new Integer(messageType)).toString()) + " ");
        stringbuffer.append("charSet=" + JavaExtensions.doubleQuote((new Integer(charSet)).toString()) + " ");
        stringbuffer.append("timeout=" + JavaExtensions.doubleQuote((new Integer(timeout)).toString()) + " ");
        stringbuffer.append("encoding=" + JavaExtensions.doubleQuote((new Integer(encoding)).toString()) + " ");
        if(QmgrReply != null)
            stringbuffer.append("replyToQmgr=" + JavaExtensions.doubleQuote(QmgrReply) + " ");
        if(format != null)
            stringbuffer.append("format=" + JavaExtensions.doubleQuote(format) + " ");
        if(applicationName != null)
            stringbuffer.append("applicationName=" + JavaExtensions.doubleQuote(applicationName) + " ");
        if(userId != null)
            stringbuffer.append("userId=" + JavaExtensions.doubleQuote(userId) + " ");
        if(matchCorrelationId)
            stringbuffer.append("messageOrCorrelation=\"correlation\" ");
        else
            stringbuffer.append("messageOrCorrelation=\"message\" ");
        stringbuffer.append("/>");
        return stringbuffer.toString();
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

    private static final String COPYRIGHT = "Licensed Materials - Property of IBM Restricted Materials of IBM 5648-D89 (C) Copyright IBM Corp. 1998, 2003 All Rights Reserved. US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp ";
    private String AliasQueueManagerNameSend;
    private MQQueueManager QueueManagerSend;
    private MQQueueManager QueueManagerReceive;
    private MQQueueManager DeadLetterQManager;
    private MQQueueManager ChannelQManager;
    protected MQQueue SendToQueue;
    protected MQQueue ReplyToQueue;
    private MQQueue systemCommandQueue;
    private MQQueue channelQueue;
    private MQQueue DeadLetterQ;
    private int expiry;
    private MQPutMessageOptions pmo;
    protected MQGetMessageOptions gmo;
    private MQGetMessageOptions dlo;
    public Semaphore receiveSem;
    public static final String LISTEN_THREAD = "Listen";
    public static final String DEADLETTERQ = "DeadLetterQ";
    protected transient QueueListener aQueueListener;
    protected int receiveReturnCode;
    private int sendReturnCode;
    private Object sendSyncronizer;
    private Object receiveSyncronizer;
    private boolean synchronousMode;
    private boolean server;
    private boolean isListenThreadAlive;
    protected boolean ReplyQueueAccessed;
    protected boolean SendQueueAccessed;
    private boolean DeadLetterAccessed;
    private boolean ChannelQueueAccessed;
    private Thread listenThread;
    private ConnectionHandler connectionHandler;
    protected MQMessage tempMqMessage;
    private boolean automaticConnectionEstablishment;
    protected Hashtable eventParameter;
    protected Hashtable eventParameterD;
    protected Hashtable messageTable;
    protected Hashtable messageQTable;
    protected String EXCEPTION;
    private int EstablishConnectionRetries;
    private int TimeBetweenRetries;
    private int establishConnectionCounter;
    private static final String systemCommandQueueName = "SYSTEM.ADMIN.COMMAND.QUEUE";
    public String QMgrName;
    public String SendToQName;
    public String ReplyToQName;
    public String channelQName;
    public String deadLetterQName;
    public String ReplyDynamicQName;
    public String ReplyModelQName;
    public String SendDynamicQName;
    public String SendModelQName;
    private static String MODEL = "model";
    private boolean sendTypeNormal;
    private boolean replyTypeNormal;
    public MqDeadLetterQueueThread deadLetterQueueThread;
    private int SendToQOpenOptions;
    private int ReplyToQOpenOptions;
    private int DeadLetterQOpenOptions;
    private int PutMessageOptionsOptions;
    private int GetMessageOptionsOptions;
    private int DeadLetterOptionsOptions;
    private String hostName;
    private String channel;
    private int port;
    public static final String RESET = "RESET";
    public static final String DATA = "message";
    public static final String QUEUE = "QUEUE";
    public static final String QMGRNAME = "QMgrName";
    public static final String SENDTOQ = "sendToQ";
    public static final String REPLYTOQ = "replyToQ";
    public static Field encode;
    public static final String COMPID = "#MQ";
    private static String Latin1;
    private boolean inUse;
    private boolean closedIssued;
    protected boolean isDeadLetterThreadAlive;
    private int persistence;
    private int messageType;
    private int charSet;
    private int encoding;
    private String QmgrReply;
    private String format;
    private int timeout;
    private String userId;
    private String applicationName;
    private boolean matchCorrelationId;
    static Class class$com$ibm$mq$MQC; /* synthetic field */

}
