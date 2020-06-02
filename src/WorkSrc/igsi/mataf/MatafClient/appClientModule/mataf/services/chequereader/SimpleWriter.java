package mataf.services.chequereader;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;

public class SimpleWriter {
	
	
	private static SimpleWriter instance = null;
	private SerialPort serialPort = null;
	private OutputStream outputStream = null;
	//For testing only
	private SimpleReader reader = null;
		
	
	private SimpleWriter () {
		try {
			Enumeration portList = CommPortIdentifier.getPortIdentifiers();
			while (portList.hasMoreElements()) {
				CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
				if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
					if (portId.getName().equals("COM1")) {
						try {
							if(portId.isCurrentlyOwned()){
								System.out.println("portId.isCurrentlyOwned()");
//								serialPort.close();
							}
							serialPort = (SerialPort) portId.open("SimpleWriteApp", 2000);
							//reader = new SimpleReader(serialPort);
							//reader.run();
							System.out.println("serialPort: " + serialPort);
						} catch (PortInUseException e) {
							e.printStackTrace();
						}
						try {
							outputStream = serialPort.getOutputStream();
							System.out.println("outputStream: "+ outputStream);
						} catch (IOException e) {
							e.printStackTrace();
						}
						try {
							serialPort.setSerialPortParams(
								9600,
								SerialPort.DATABITS_8,
								SerialPort.STOPBITS_1,
								SerialPort.PARITY_NONE);
						} catch (UnsupportedCommOperationException e) {
							e.printStackTrace();
						}
						
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected SerialPort getSerialPort() {
		return serialPort;
	}
	
	public static synchronized SimpleWriter getInstance () {
		if (instance==null) 
			instance = new SimpleWriter();
		return instance;
	}
	
	public void write (String messageString) {
		try {
			outputStream.write(messageString.getBytes());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}

