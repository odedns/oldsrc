// Decompiled by Jad v1.5.8d. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AmperReaderDriver.java

package mataf.services.chequereader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;

// Referenced classes of package com.ibm.dse.services.checkreader:
//            AmperReaderConst, AmperReaderListener, AmperReaderException
class AmperReaderDriver
    implements Runnable, SerialPortEventListener, AmperReaderConst, AmperReaderListener
{	
	public Object getReadData () {
		Object data = readData.toString();
		return data;
	}
	
	public int UniRead(myString pcBufS,myInteger piLen,int dwTimeout,int dwDelay){
//		int	idDevice,		// Dos File handle number from "open"
//		LPSTR	pcBuf,			// User Buffer to read into
//		LPINT	piLen,			// Data length to read (Without Frame chars)
//		DWORD dwTimeout,		// Ticks to wait for data (in mili sec)
//		DWORD dwDelay)			// Ticks to wait between reads
//{
	Date timer= new Date();
	long  dwStartTime;

//	char   cTmp[CTMP_LEN];	// temp buffer for reading

	int   lenBuf=0;
	int   lenTmp = 0;
	myInteger   retlen = new myInteger(0);
	int   rc = SUCCESS;
	int   nChars=0;
//	LPSTR	pt;
//
//	// zero the temp buffer
//	pt = (LPSTR)cTmp;
	byte cTmp[] = new byte[260];
	byte pt[]=new byte[260];
//	_fmemset(pt, 0, sizeof(cTmp));

	/* Wait "iTimeout" ticks for data from Cheque Mate Reader */

	lenTmp = 0;
//	lenBuf = sizeof(cTmp) - 1;
	lenBuf = cTmp.length - 1;
    System.out.println("*** UniRead:"); 
    
    String tempStr=null;

//	WriteLog("(%d)UniRead: req to read %d bytes, timeout = %lu", idDevice, *piLen, dwTimeout);

	dwStartTime = timer.getTime();
	int k = 0;
	while ((timer.getTime() - dwStartTime) < dwTimeout)
	{
//		DoYield();
    	System.out.println("***  UniRead (0)"); 
		try{
			nChars = inputStream.read(pt, k, lenBuf-lenTmp);
		}
		catch(IOException e){
    		System.out.println("*** IOException"); 
		}
//		nChars = ReadComm(idDevice, pt, lenBuf-lenTmp);
		tempStr = new String(pt);
    	System.out.println("*** UniRead1: 'ReadComm' returned :"+nChars+ " pt: " +tempStr); 
//		WriteLog("(%d)UniRead1: 'ReadComm' returned %d", idDevice, nChars);

		if (nChars < 0 )
		{
			int		commRc;
//			COMSTAT	commStat;

//			commRc = GetCommError(idDevice, (COMSTAT FAR*)&commStat);
//			WriteLog("(%d)UniRead2: read error %d", idDevice, commRc);

			rc = READ_ERROR;		// Unsuccessful Read
			break;
		}

		if (nChars > 0)
		{
//			pt += nChars;
    System.out.println("*** nChars > 0"); 
			k += nChars;
			lenTmp += nChars;
			//Added by R.K.
			readDataStr = new myString(tempStr);
			
			pcBufS.setValue(new String(tempStr));
			piLen=new myInteger(tempStr.length());
			return 0;
//			*pt = '\0';
		}

		if ( lenTmp >= lenBuf )
		{
			rc = BUFFER_OVERFLOW;
			break;
		}
    System.out.println("***  UniRead (1) lenTmp: "+lenTmp+" " +pt[lenTmp-1-CMR_BccLen]); 

		// if we recieved the 'ETX' char then break
		if ( (lenTmp > 2) && (pt[lenTmp-1-CMR_BccLen] == ETX) ){
			break;
		}
    System.out.println("***  UniRead (2)"); 
//		if ( (lenTmp > 2) && (*(cTmp+lenTmp-1-CMR_BccLen) == ETX) )
//			break;
	}	/* End While (...) */

	// if didn't read any data then return TIMEOUT error
	if ( (rc == SUCCESS) && (lenTmp == 0) )
		rc = READ_TIMEOUT;		// Timeout
    System.out.println("***  UniRead (3)"); 

	retlen = new myInteger(lenTmp);
    System.out.println("***  UniRead (4) retlen:"+retlen); 
	if ( rc == SUCCESS ){		/* Don't destroy previuos error code in rc */
		myString s = new myString(pt);
    	System.out.println("***  UniRead (5)"); 
		rc = UniCheckInputFrame( s, retlen );
    	System.out.println("***  UniRead (6) rc: "+rc); 
    	pt = s.getBytes();
	}
    System.out.println("*** UniRead : rc: "+rc+" retlen: "+retlen); 

	/*-------------------------*/
	/* Transfer data to caller */
	/*-------------------------*/
	if ( piLen.intValue() < retlen.intValue() )		// Send back to user the smallest of
		retlen = piLen;			// requested and actual read length
	else
		piLen = retlen;

//	pt = cTmp + 1;
	int cnt = 1;
	int cntT = 0;
	int len = retlen.intValue();
	while ( len-- > 0 )			// Transfer data to user buffer.
	{
//    System.out.println("*** UniRead pt["+cnt+"]: "+pt[cnt]); 
		bufInp[cntT] = pt[cnt];
		cnt++;
		cntT++;
	}
	pcBufS.setValue(new String(bufInp));
	readDataStr = new myString(bufInp);
    System.out.println("*** UniRead : pcBufS: "+pcBufS+" piLen: "+piLen); 

	return rc;					// Return RC
}
	
/*----------------------------------------------------------------------------*/
/* Check input data frame structure					    					  */
/*									    									  */
/* Returns Error code							    						  */
/*----------------------------------------------------------------------------*/
int UniCheckInputFrame( myString pcBufS, myInteger piLen )
{
//	LPSTR pt = pcBuf;
	int   len = piLen.intValue();

	char  lrc_bcc = 0;
	int   retlen = 0;
	int   rc = SUCCESS;
	int   j;
	byte pcBuf[]=new byte[260];
	int ptCnt=0;
	
    System.out.println("*** UniCheckInputFrame : pcBufS "+pcBufS+ " piLen: "+piLen); 
	pcBuf = pcBufS.getBytes();

	// every buffer should begin with 'STX' and end with 'ETX'
	if ( (pcBuf[0] != STX) || (pcBuf[len-1-CMR_BccLen] != ETX) )
	{
		piLen.setValue(0);
		rc = BAD_DATA_STRUCTURE;    // Bad Frame structure

		return rc;
	}

	switch ( CMR_TransEnd )
	{
		/*-------------------------------------*/
		/* Transmition ends with LRC character */
		/* ST ... ETX LRC                      */
		/*-------------------------------------*/
		case UNI_CD_LRC:

    System.out.println("*** UNI_CD_LRC"); 
			// calc the XOR for the buffer except the last char
			for ( j=0; j<len-1; j++ ){
				lrc_bcc ^= pcBuf[ptCnt];
				ptCnt++;
			}

			// the last char should be as we jus calculated
			if ( lrc_bcc != pcBuf[ptCnt] )
				rc = BAD_BCC;		// Bad End of transmition character

			break;

		/*-------------------------------------*/
		/* Transmition ends with BCC character */
		/* STX ... ETX BCC		       		   */
		/*-------------------------------------*/
		case UNI_CD_BCC:

    System.out.println("*** UNI_CD_BCC"); 
			for ( j=0; j<len-1; j++ ){
				lrc_bcc += pcBuf[ptCnt];
				ptCnt++;
			}

			// the last char should be as we jus calculated
			if ( lrc_bcc != pcBuf[ptCnt] )
				rc = BAD_BCC;		// Bad End of transmition character

			break;

		/*-------------------------------------*/
		/* Transmition ends with CR+LF chars   */
		/* STX ... ETX CR LF		       	   */
		/*-------------------------------------*/
		case UNI_CD_CRLF:

    System.out.println("*** UNI_CD_CRLF"); 
			if ( pcBuf[len-2] != A_CR || pcBuf[len-1] != A_LF )
				rc = BAD_BCC;	// Bad End of transmition character

			break;

		/*-------------------------------------*/
		/* No Transmition end character	       */
		/* STX ... ETX			       		   */
		/*-------------------------------------*/
		case UNI_CD_NONE:
		default:

				break;

	} /* End of Switch */

	// set the new size without the trailing chars
	retlen = len - CMR_BccLen - 2;
	piLen.setValue(retlen);

	/*--------------------------------------*/
	/* Scan received data frame for errors. */
	/*--------------------------------------*/
	if ( pcBuf[1] == NAK )
		rc = CMR_NAK;
	else
	if (pcBuf[1] == 'E' )		// Cheque Mate Reader Error message
	{
		if ( retlen != 2 )
			rc = CMR_UNKNOWN_ERR;
		else
		{
			rc = (int)(pcBuf[2] - '0');
			if ( (rc < 1) || (rc > 9) )
				rc = CMR_UNKNOWN_ERR;
			else
				rc = CMR_UNKNOWN_ERR - rc;
		}
	}

	return rc;
}


/*----------------------------------------------------------------------------*/
/* Read ACK Message from Cheque Mate Reader				    				  */
/*----------------------------------------------------------------------------*/
int UniGetAck(long dwTimeout)
{
	int	rc;
	myInteger	len = new myInteger(1);
	byte	cAckBuf[]=new byte[10];
	myString 	cAckBufS=new myString("");
	

	if ( (rc = UniRead(cAckBufS, len, (int)dwTimeout, 1000 )) < 0 ){
    System.out.println("*** UniGetAck: "+ cAckBufS+" rc: "+rc+" len: "+len+" cAckBuf[0]: "+cAckBuf[0]); 
		return rc;				// UniRead Error
	}
    if(cAckBufS == null || cAckBufS.length() == 0) 
    	return BAD_ACK;
	cAckBuf = cAckBufS.getBytes();
	if ( (len.intValue() == 1) && (cAckBuf[0] == ACK) )
		return SUCCESS;		// Function completed successfully
	else
		return BAD_ACK;		// Not ACK Character
}
	
//	public void serialEvent(SerialPortEvent event) {
//		
////		System.out.println("serialEvent: "+event);
//		switch (event.getEventType()) {
//			case SerialPortEvent.BI :
//			case SerialPortEvent.OE :
//			case SerialPortEvent.FE :
//			case SerialPortEvent.PE :
//			case SerialPortEvent.CD :
//			case SerialPortEvent.CTS :
//			case SerialPortEvent.DSR :
//			case SerialPortEvent.RI :
//			case SerialPortEvent.OUTPUT_BUFFER_EMPTY :
//				System.out.println("getEventType()="+event.getEventType());
//				break;
//			case SerialPortEvent.DATA_AVAILABLE :
//				byte[] readBuffer = new byte[120];
//				try {
//					while (inputStream.available() > 0) {
//						int numBytes = inputStream.read(readBuffer);
//					}
//					readData.append(new String(readBuffer));
//					System.out.println("readData  ="+readData);
//				} catch (IOException e) {
//					System.out.println("serialEvent IOException!!");
//				}
//				break;
//			default:
//				System.out.println("default=getEventType()="+event.getEventType());
//				break;
//		}
//	}

    AmperReaderDriver()
    {
        pollTime = 30;
		readData = new StringBuffer();		
    	System.out.println("*** AmperReaderDriver "+bufInpSize); 
        answerWait = 11000;
        checkDtrCts = true;
        reset = true;
        connected = false;
        bufInp = new byte[260];
        bufOut = new byte[4];
//		dwTimeout=60;	// Ticks to wait for data (in mili sec)
//		dwDelay=10;			// Ticks to wait between reads
    }

    private static void a_5530N400_()
    {
    }

    protected void cancel()
        throws AmperReaderException
    {
    	System.out.println("*** cancel "+bufInpSize); 
        if(commStatus == 0)
            throw new AmperReaderException(3);
        cancelled = true;
        while(!allDone) 
            synchronized(this)
            {
                try
                {
                    wait(100L);
                }
                catch(InterruptedException _ex) { }
            }
        if(!reset)
            return;
        while(readSyncBusy) 
            synchronized(this)
            {
                try
                {
                    wait(100L);
                }
                catch(InterruptedException _ex) { }
            }
        launch(2, null);
        for(int i = 0; i < 3000 / pollTime && !allDone; i++)
            synchronized(this)
            {
                try
                {
                    wait(pollTime);
                }
                catch(InterruptedException _ex) { }
            }

        if(!allDone)
            cancelled = true;
    }

    protected void checkRead()
        throws AmperReaderException
    {
    	System.out.println("*** checkRead "+bufInpSize); 
        launch(1, null);
        for(int j = 0; j < 3000 / pollTime && !allDone; j++)
            synchronized(this)
            {
                try
                {
                    wait(pollTime);
                }
                catch(InterruptedException _ex) { }
            }

        if(!allDone)
        {
            cancelled = true;
            throw new AmperReaderException(1);
        }
        int i = bufInpSize;
        if(i < 0)
            throw new AmperReaderException(1);
        if(i < 2)
            throw new AmperReaderException(6);
        if(i >= 2 && bufInp[0] == 83 && bufInp[1] == 48)
            return;
        if(i >= 2 && bufInp[0] == 83 && bufInp[1] == 50)
            launch(2, null);
        throw new AmperReaderException(6);
    }

    private void clearInput()
    {
     	System.out.println("*** clearInput "+bufInpSize); 
       try
        {
            serialPort.enableReceiveTimeout(10);
        }
        catch(UnsupportedCommOperationException _ex) { }
        try
        {
            while(inputStream.read(bufInp, 0, 1) > 0) ;
        }
        catch(IOException _ex) { }
    }

    protected void close()
    {
    	System.out.println("*** close: "+bufInpSize); 
        if(!connected)
            return;
        try
        {
            cancel();
        }
        catch(AmperReaderException _ex) { }
        serialPort.close();
        try
        {
            inputStream.close();
        }
        catch(IOException _ex) { }
        try
        {
            outputStream.close();
        }
        catch(IOException _ex) { }
        connected = false;
    }

    private void doCallBack()
    {
    	System.out.println("*** doCallBack readData: "+readData); 
        if(theListener == null)
            return;
        int i = bufInpSize;
        byte byte0;
        if(cancelled)
            byte0 = 7;
        else
            switch(i = bufInpSize)
            {
            case -4: 
            case -3: 
            case -2: 
            case -1: 
                byte0 = 1;
                break;

            case -5: 
                byte0 = 7;
                break;

            case 0: // '\0'
            case 1: // '\001'
                byte0 = 6;
                break;

            default:
                if(bufInp[0] == 83 && bufInp[1] == 49)
                {
                    byte0 = 1;
                    break;
                }
                String s = new String(bufInp, 0, i);
                if(s.indexOf('!') >= 0)
                {
                    byte0 = 2;
                } else
                {
                    byte0 = 0;
                    theListener.readCompleted(0, s);
                    return;
                }
                break;
            }
        theListener.readCompleted(byte0, (new AmperReaderException(byte0)).getMessage());
    }

    private boolean isDeviceReady()
    {
    	System.out.println("*** isDeviceReady: "+bufInpSize); 
        if(!connected)
            return false;
        clearInput();
        if(!checkDtrCts)
            return true;
        serialPort.setDTR(false);
        serialPort.setRTS(false);
        boolean flag = serialPort.isCTS() && serialPort.isDSR();
        try
        {
            Thread.sleep(50L);
        }
        catch(InterruptedException _ex) { }
        serialPort.setDTR(true);
        serialPort.setRTS(true);
        return flag;
    }

    private void launch(int i, AmperReaderListener amperreaderlistener)
        throws AmperReaderException
    {
    	System.out.println("*** launch "+i); 
        if(commStatus != 0)
            throw new AmperReaderException(5);
//        if(!isDeviceReady())
		if (false)
        {
            throw new AmperReaderException(4);
        } else
        {
            allDone = false;
            cancelled = false;
            operation = i;
            theListener = amperreaderlistener;
//            Thread thread = new Thread(this);
//            thread.start();
//			run();
			UniRead(readDataStr,new myInteger(BLOCKSIZE),CMR_ReadTimeout*10,CMR_Retry);
    System.out.println("*** launch: "+ readDataStr); 
    		syncRetMsg = readDataStr.getValue();
            return;
        }
    }

    protected void open(int i, int j, int k, int l, int i1, boolean flag)
        throws AmperReaderException
    {
    	System.out.println("*** open: i "+i); 
        if(connected)
            return;
        traceOn = flag;
        for(Enumeration enumeration = CommPortIdentifier.getPortIdentifiers(); enumeration.hasMoreElements();)
        {
            CommPortIdentifier commportidentifier = (CommPortIdentifier)enumeration.nextElement();
            if(commportidentifier.getPortType() == 1)
            {
                if(traceOn)
                    System.out.println(commportidentifier.getName());
                if(commportidentifier.getName().equals("COM" + i))
                {
                    try
                    {
                        serialPort = (SerialPort)commportidentifier.open("Reader", 2000);
                    }
                    catch(PortInUseException _ex)
                    {
                        throw new AmperReaderException(9);
                    }
                    try
                    {
                        inputStream = serialPort.getInputStream();
                    }
                    catch(IOException _ex)
                    {
                        throw new AmperReaderException(10);
                    }
                    try
                    {
                        outputStream = serialPort.getOutputStream();
                    }
                    catch(IOException _ex)
                    {
                        throw new AmperReaderException(11);
                    }
                    try
                    {
                        serialPort.addEventListener(this);
						serialPort.notifyOnDataAvailable(true);
                    }
                    catch(TooManyListenersException _ex)
                    {
                        throw new AmperReaderException(12);
                    }
                    serialPort.notifyOnParityError(true);
                    try
                    {
                        serialPort.setSerialPortParams(j, k, l, i1);
                    }
                    catch(UnsupportedCommOperationException _ex)
                    {
                        throw new AmperReaderException(13);
                    }
                    connected = true;
                    bufInpSize = 0;
                    commStatus = 0;
                    allDone = false;
                    readSyncBusy = false;
                    return;
                }
            }
        }
        
        Enumeration e = CommPortIdentifier.getPortIdentifiers();
        System.out.println("e.hasMoreElements=" + e.hasMoreElements());

        throw new AmperReaderException(8);
    }

    protected void open(int i, boolean flag)
        throws AmperReaderException
    {
    	System.out.println("*** open 2 "+i+" " +flag); 
        open(i, 9600, 7, 1, 2, flag);
    }
    
    // Added By eyal Ben Ze'ev    
    protected void writeCommand(String checkReaderCommand) {
    	System.out.println("*** writeCommand "+checkReaderCommand); 
    	try 
    	{    		
    		outputStream.write(checkReaderCommand.getBytes());
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    		
    }

    protected void readAsync(AmperReaderListener amperreaderlistener)
        throws AmperReaderException
    {
    	System.out.println("*** readAsync (1)"); 
        launch(0, amperreaderlistener);
    	System.out.println("*** readAsync (2)"); 
    }

    public void readCompleted(int i, String s)
    {
    	System.out.println("*** readCompleted: "+s); 
        syncRetCode = i;
        syncRetMsg = s;
    }

    protected String readSync()
        throws AmperReaderException
    {
    	System.out.println("*** readSync: "+bufInpSize); 
        readAsync(this);
    	System.out.println("*** readSync: "+bufInpSize); 
        readSyncBusy = true;
//        while(!allDone) 
//            synchronized(this)
//            {
//                try
//                {
//                    wait(100L);
//                }
//                catch(InterruptedException _ex) { }
//            }
        readSyncBusy = false;
        if(syncRetCode != 0)
            throw new AmperReaderException(syncRetCode);
        else
            return syncRetMsg;
    }

    private int receiveBlock(byte byte0)
        throws IOException
    {
    	System.out.println("*** receiveBlock: "+byte0); 
        boolean flag = false;
        boolean flag1 = false;
        byte byte1 = byte0;
        int j = 0;
        int k = 260;
        int i = 0;
        try
        {
            serialPort.enableReceiveTimeout(10);
        }
        catch(UnsupportedCommOperationException _ex)
        {
    	System.out.println("*** UnsupportedCommOperationException: "+byte0); 
            return -4;
        }
        int l;
        do
        {
            try
            {
                Thread.sleep(50L);
            }
            catch(InterruptedException _ex) { }
            l = inputStream.read(bufInp, j, k);
   	System.out.println("*** l:"+l+" bufInp:"+bufInp.toString()+" j:"+j+" k:"+k); 
             for(int i1 = 0; i1 < l && !flag;)
            {
                i = bufInp[j];
   	System.out.println("*** i:"+ i); 
                if(flag1)
                {
                    flag = true;
                } else
                {
                    byte1 ^= i;
                    if(i == 3 || i == 23)
                        flag1 = true;
                }
                i1++;
                j++;
            }

            k -= l;
        } while(!flag && k > 0 && l > 0);
        if(flag)
            if(byte1 == i && !badParity)
                return j - 2;
            else
                return -3;
        return l <= 0 ? -1 : -2;
    }

    private byte receiveByte(int i)
        throws IOException
    {
    	System.out.println("*** receiveByte: "+i); 
        byte abyte0[] = new byte[1];
        int j = 0;
        try
        {
            serialPort.enableReceiveTimeout(pollTime);
        }
        catch(UnsupportedCommOperationException _ex)
        {
            return -4;
        }
        badParity = false;
        int k = i / pollTime;
        for(int l = 0; l < k && j <= 0 && (!cancelled || !reset); l++)
            j = inputStream.read(abyte0, 0, 1);

        if(j > 0)
            return abyte0[0];
        return ((byte)(!cancelled || !reset ? -1 : -5));
    }

    public void run()
    {
        int i = 0;
    	System.out.println("*** run: "); 
        try
        {
            do
            {
                System.out.println("commStatus: "+commStatus+" operation: "+operation);
                switch(commStatus)
                {
                default:
                    continue;

                case 0: // '\0'
                    bufInpSize = 0;
                    if(traceOn)
                        System.out.println("IDLE, op=" + operation);
                    switch(operation)
                    {
                    case 0: // '\0'
						String messageString = STX + "ABP000" + ETX;
                    	writeCommand(messageString);
//                        sendCmd((byte)65);
                        commStatus = 1;
                        break;

                    case 1: // '\001'
                        sendCmd((byte)83);
                        commStatus = 4;
                        break;

                    case 2: // '\002'
                        sendCmd((byte)90);
                        commStatus = 6;
                        break;
                    }
                    continue;

                case 1: // '\001'
                    if(traceOn)
                        System.out.println("WRS_AR");
                    i = receiveByte(answerWait);
                    if(traceOn)
                        showOne(i);
                    switch(i)
                    {
                    case 2: // '\002'
                        commStatus = 2;
                        break;

                    case 21: // '\025'
                        sendCmd((byte)65);
                        break;

                    case -5: 
                    case -4: 
                    case -1: 
                        commStatus = 3;
                        break;
                    }
                    continue;

                case 2: // '\002'
                    i = receiveBlock((byte)2);
                    if(traceOn)
                    {
                        System.out.println("WMSG_AR");
                        if(i > 0)
                            showRx(i + 2);
                        else
                            showOne(i);
                    }
                    switch(i)
                    {
                    case -3: 
//                        sendByte((byte)21);
                        commStatus = 3;
                        break;
                    	
                    case -4: 
                    case -2: 
                    case -1: 
                        sendByte((byte)21);
                        commStatus = 1;
                        break;

                    default:
                        sendByte((byte)6);
                        commStatus = 3;
                        if(cancelled)
                            i = -5;
                        else
                        if(i >= 2 && bufInp[0] == 83 && bufInp[1] == 49)
                        {
                            if(traceOn)
                                System.out.println("rearm...");
                            commStatus = 1;
                            sendCmd((byte)65);
                        }
                        break;
                    }
                    continue;

                case 3: // '\003'
                    if(traceOn)
                        System.out.println("END_AR");
                    bufInpSize = i;
                    commStatus = 8;
                    continue;

                case 4: // '\004'
                    i = receiveByte(2000);
                    if(traceOn)
                    {
                        System.out.println("WRS_CH");
                        showOne(i);
                    }
                    switch(i)
                    {
                    case 2: // '\002'
                        commStatus = 5;
                        break;

                    case 21: // '\025'
                        sendCmd((byte)83);
                        break;

                    case -5: 
                    case -4: 
                    case -1: 
                        bufInpSize = i;
                        commStatus = 8;
                        break;
                    }
                    continue;

                case 5: // '\005'
                    i = receiveBlock((byte)2);
                    if(traceOn)
                    {
                        System.out.println("WMSG_CH");
                        if(i > 0)
                            showRx(i + 2);
                        else
                            showOne(i);
                    }
                    switch(i)
                    {
                    case -4: 
                    case -3: 
                    case -2: 
                    case -1: 
                        sendByte((byte)21);
                        commStatus = 4;
                        break;

                    default:
                        sendByte((byte)6);
                        if(cancelled)
                            bufInpSize = -5;
                        else
                            bufInpSize = i;
                        commStatus = 8;
                        break;
                    }
                    continue;

                case 6: // '\006'
                    i = receiveByte(2000);
                    if(traceOn)
                    {
                        System.out.println("WRS_RST");
                        showOne(i);
                    }
                    switch(i)
                    {
                    case 2: // '\002'
                        commStatus = 7;
                        break;

                    case 21: // '\025'
                        sendCmd((byte)90);
                        break;

                    default:
                        commStatus = 8;
                        break;
                    }
                    continue;

                case 7: // '\007'
                    i = receiveBlock((byte)2);
                    if(traceOn)
                    {
                        System.out.println("WMSG_RST");
                        if(i > 0)
                            showRx(i + 2);
                        else
                            showOne(i);
                    }
                    sendByte((byte)6);
                    commStatus = 8;
                    continue;

                case 8: // '\b'
                    if(traceOn)
                        System.out.println("END op=" + operation);
                    doCallBack();
                    commStatus = 0;
                    allDone = true;
                    break;
                }
                break;
            } while(true);
            return;
        }
        catch(IOException _ex)
        {
            return;
        }
    }

    private void sendByte(byte byte0)
        throws IOException
    {
    	System.out.println("*** sendByte: "+byte0); 
        bufOut[0] = byte0;
        outputStream.write(bufOut, 0, 1);
        if(traceOn)
            showTx(1);
    }

    private void sendCmd(byte byte0)
        throws IOException
    {
    	System.out.println("*** sendCmd: "+byte0); 
        bufOut[0] = 2;
        bufOut[1] = byte0;
        bufOut[2] = 3;
        bufOut[3] = (byte)(2 ^ byte0 ^ 3);
        outputStream.write(bufOut, 0, 4);
        if(traceOn)
            showTx(4);
    }

    public void serialEvent(SerialPortEvent serialportevent)
    {
        switch(serialportevent.getEventType())
        {
        case 8: // '\b'
            badParity = true;
            break;
        }
    }

    protected void setCheckDtrCts(boolean flag)
    {
        checkDtrCts = flag;
    }

    protected void setReset(boolean flag)
    {
        reset = flag;
    }

    protected void setTimeout(int i)
    {
        answerWait = i;
    }

    private void showOne(int i)
    {
        if(traceOn)
        {
            System.out.print("rx: ");
            if(i > 32)
                System.out.println((char)i);
            else
            if(i >= 0)
                System.out.println("0x" + Integer.toHexString(i));
            else
                switch(i)
                {
                case -1: 
                    System.out.println("ERR_TO");
                    break;

                case -2: 
                    System.out.println("ERR_OV");
                    break;

                case -3: 
                    System.out.println("ERR_RX");
                    break;

                case -4: 
                    System.out.println("ERR_XC");
                    break;

                case -5: 
                    System.out.println("ERR_KL");
                    break;
                }
        }
    }

    private void showRx(int i)
    {
        if(traceOn)
        {
            System.out.print("Rx: ");
            for(int j = 0; j < i; j++)
            {
                byte byte0 = bufInp[j];
                if(byte0 > 32)
                    System.out.print((char)byte0);
                else
                    System.out.print(" 0x" + Integer.toHexString(byte0) + ' ');
            }

            System.out.println("");
        }
    }

    private void showTx(int i)
    {
        if(traceOn)
        {
            System.out.print("Tx: ");
            for(int j = 0; j < i; j++)
            {
                byte byte0 = bufOut[j];
                if(byte0 > 32)
                    System.out.print((char)byte0);
                else
                    System.out.print(" 0x" + Integer.toHexString(byte0) + ' ');
            }

            System.out.println("");
        }
    }

    private static final String COPYRIGHT = "Licensed Materials - Property of IBM 5648-D89 (C) Copyright IBM Corp. 2000 All Rights Reserved. US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp ";
    private static final char STX = 0x2;
    private static final char ETX = 0x3;

    private static final byte A_LF   =10;
    private static final byte A_CR   =13;
    private static final byte NAK    =21;
    private static final byte ESC    =27;

    private static final byte EOT = 4;
    private static final byte ENQ = 5;
    private static final byte ACK = 6;
//    private static final byte NAK = 21;
    private static final byte ETB = 23;
    private static final int ERR_TO = -1;
    private static final int ERR_OV = -2;
    private static final int ERR_RX = -3;
    private static final int ERR_XC = -4;
    private static final int ERR_KL = -5;
    private static final int IDLE = 0;
    private static final int WRS_AR = 1;
    private static final int WMSG_AR = 2;
    private static final int END_AR = 3;
    private static final int WRS_CH = 4;
    private static final int WMSG_CH = 5;
    private static final int WRS_RST = 6;
    private static final int WMSG_RST = 7;
    private static final int END = 8;
    private static final int OP_READ = 0;
    private static final int OP_CHECK = 1;
    private static final int OP_RESET = 2;
    private static final int BLOCKSIZE = 260;
    
    private static final int  SUCCESS		=		0;
    private static final int  BAD_ACK		=		-1;
    private static final int  BAD_BCC		=		-2;
    private static final int  READ_ERROR	=		-3;
    private static final int  WRITE_ERROR	=		-4;
    private static final int  DOS_ERROR		=		-5;
    private static final int  BUFFER_OVERFLOW		=-6;
    private static final int  BAD_DATA_STRUCTURE	=-7;
    private static final int  READ_TIMEOUT			=-8;
    private static final int  INVALID_PARAMETER		=-9;
    private static final int  INVALID_DOC_STATUS	=-10;
    private static final int  CMR_NAK				=-11;
    
    private static final int   CMR_UNKNOWN_ERR    =  -12;
    private static final int   CMR_NO_DOCUMENT    =  CMR_UNKNOWN_ERR-1;
    private static final int   CMR_DOC_STUCKED    =  CMR_UNKNOWN_ERR-2;
    private static final int   CMR_HARDWARE_ERROR =  CMR_UNKNOWN_ERR-3;
    private static final int   CMR_FRAME_ERROR    =  CMR_UNKNOWN_ERR-4;
    private static final int   CMR_INVALID_CMD    =  CMR_UNKNOWN_ERR-5;
    private static final int   CMR_NO_MAGNETIC_DATA =CMR_UNKNOWN_ERR-6;
    private static final int   CMR_DOCUMENT_JAM    = CMR_UNKNOWN_ERR-7;
    private static final int   CMR_INVALID_PARAM   = CMR_UNKNOWN_ERR-8;
    private static final int   CMR_READ_TIMEOUT    = CMR_UNKNOWN_ERR-9;
    private static final int   CMR_ALREADY_LOCKED  = CMR_UNKNOWN_ERR-10;
    private static final int   CMR_NOT_LOCKED      = CMR_UNKNOWN_ERR-11;
    private static final int   CMR_NETWORK_ERROR	=  CMR_UNKNOWN_ERR-12;
    private static final int   CMR_NETMSG_ERROR    = CMR_UNKNOWN_ERR-13;
    private static final int   CMR_NOT_INIT			=  CMR_UNKNOWN_ERR-14;
    private static final int   CMR_CANT_OPEN_PORT  =CMR_UNKNOWN_ERR-15;

// Check digit / end of transmition character
    private static final int    UNI_CD_NONE  =20;
    private static final int    UNI_CD_LRC   =21;
    private static final int    UNI_CD_BCC   =22;
    private static final int    UNI_CD_CRLF  =23;

// Motor movement direction after Reset
    private static final int    UNI_RM_NONE  =30;
    private static final int    UNI_RM_BACK  =31;
    private static final int   UNI_RM_FORWD =32;

// Send Acknoledgement after reset
    private static final int   UNI_RA_NONE  =40;
    private static final int    UNI_RA_ACK   =41;

    private static final int   CMR_Retry	   = 3;
    private static final int   CMR_ResetMotor  = UNI_RM_NONE;
    private static final int   CMR_ResetAck	   = UNI_RA_NONE;
    private static final int   CMR_TransEnd	   = UNI_CD_BCC;
    private static final int   CMR_BccLen	   = 1;
    private static final int   CMR_ReadTimeout = 6;
    
    private int pollTime;
    private int answerWait;
    private boolean checkDtrCts;
    private boolean reset;
    private SerialPort serialPort;
    private OutputStream outputStream;
    private InputStream inputStream;
    private AmperReaderListener theListener;
    private boolean connected;
    private boolean badParity;
    private boolean cancelled;
    private boolean traceOn;
    private int commStatus;
    private int bufInpSize;
    private int operation;
    private byte bufInp[];
    private byte bufOut[];
    private int syncRetCode;
    private String syncRetMsg;
    private boolean allDone;
    private boolean readSyncBusy;
	private StringBuffer readData;
//	private int dwTimeout;	// Ticks to wait for data (in mili sec)
//	private int dwDelay;			// Ticks to wait between reads
	private myString readDataStr=null;
	private String readDataStrNoSE=null;

}
   

class myInteger{
	private Integer iValue;
	myInteger(int i){
		iValue = new Integer(i);
	}
	myInteger(){
		iValue = new Integer(0);
	}
	myInteger(Integer i){
		iValue = i;
	}
	void setValue(int i){
		iValue = new Integer(i);
	}
	Integer getValue(){
		return iValue;	
	}
	int intValue(){
		return iValue.intValue();
	}
	public String toString(){
		return iValue.toString();
	}
}
class myString{
	private String str=null;
	public myString(byte[] bArr){
		str = new String(bArr);
	}
	public myString(String s){
		str = s;
	}
	public void setValue(String s){
		str = s;
	}
	String getValue(){
		return str;
	}
	public String toString(){
		return str.toString();
	}
	public int length(){
		return str.length();
	}
	public byte[] getBytes(){
		return str.getBytes();
	}
	
}

