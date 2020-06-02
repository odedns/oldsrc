package mataf.services.chequereader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import javax.comm.CommPortIdentifier;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;


public class SimpleReader extends AmperReaderDriver implements Runnable, SerialPortEventListener {
	
	static CommPortIdentifier portId;
	static Enumeration portList;
	private InputStream inputStream;
	private SerialPort serialPort;
	private Thread readThread;
	private StringBuffer readData;
	private StringBuffer tempReadData =new StringBuffer();
	private Vector readQueue;
	private Date Timer;
	private boolean timeOut = false;
	
	public SimpleReader(SerialPort serialPort) {
		this.serialPort = serialPort;
		Timer= new java.util.Date();
		readQueue = new Vector();
		readData = new StringBuffer();		
		try {
			inputStream = serialPort.getInputStream();
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
		}
//		readThread = new Thread(this);
//		readThread.start();
//		readThread.stop();
	}
	
	public Object getReadData () {
/*		Object data = null;
		if (readQueue.size()>0 ) {
			data = readQueue.elementAt(0);
			readQueue.removeElementAt(0);
		}*/
		
				
		long lTimer=System.currentTimeMillis();
		Object data=null;				
		while ((!timeOut)) {			
			data = readData.toString();
			//System.out.println("lTimer:" + lTimer + " Timer.getTime():" + Timer.getTime());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(System.currentTimeMillis()>=lTimer+3000) {
				data = (String)readData.toString();
				readData.delete(0,readData.length());
				timeOut = true;
			}
		}
		System.out.println("String len:"+((String)data).length());
		return data;
	}
	
	public void run() {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
		}
	}
	public void serialEvent(SerialPortEvent event) {
		
		switch (event.getEventType()) {
			
			case SerialPortEvent.BI :
			case SerialPortEvent.OE :
			case SerialPortEvent.FE :
			case SerialPortEvent.PE :
			case SerialPortEvent.CD :
			case SerialPortEvent.CTS :
			case SerialPortEvent.DSR :
			case SerialPortEvent.RI :
			case SerialPortEvent.OUTPUT_BUFFER_EMPTY :
				System.out.println("getEventType()="+event.getEventType());
				break;
			
			case SerialPortEvent.DATA_AVAILABLE :
				byte[] readBuffer = new byte[120];
				int numBytes=0;
				try {
					while (inputStream.available() > 0) {
						numBytes = inputStream.read(readBuffer);
					}
					//System.out.println("numBytes:"+numBytes + "readBufferLen:"+new String(readBuffer).length());
					String sBuffer=new String(readBuffer).substring(0,numBytes);
					
					//System.out.println("ReadBuffer:"+readBuffer.length);
					//readData.append(new String(readBuffer));
					readData.append(sBuffer);
					System.out.println("readData  ="+readData +" Len:"+readData.length());
				} catch (IOException e) {
					System.out.println("serialEvent IOException!!");
				}
				
				break;
			default:
				System.out.println("default=getEventType()="+event.getEventType());
				break;
		}
	}
}

