package mataf.services.chequereader;

import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;

public class PortsTester								
{
	java.util.Enumeration portList;
	SerialPort			  port;
	CommPortIdentifier    portId;
	String 				  portName = "COM3";
	InputStreamReader 	  in;
    OutputStream 		  out;	

	public PortsTester()	//Default Constructor
	{super();}
	
	public void start()
	{
		if (openPort(portName))
		{
			createPort();
			openConnection();			
			closeIO();
		}	
	}
	
///////////////////////////////////////////////////////////////

	private boolean openPort(String portName)
	{
		portList = CommPortIdentifier.getPortIdentifiers();
		if (portList==null || !portList.hasMoreElements())
		{
			System.out.println("No comm ports found!");
			Toolkit.getDefaultToolkit().beep();
			System.exit(1);
		}
		System.out.println("portList = "+portList);
		try
		{
			System.out.println("Ports List : ");
			while (portList.hasMoreElements()) 
			{
            	portId = (CommPortIdentifier) portList.nextElement();			
				System.out.println("----- " + portId.getName() + " : -----");
				System.out.println("Port Type : " + ((portId.getPortType()==CommPortIdentifier.PORT_SERIAL) ? "Serial" : "Parallel"));
				System.out.println("Current Owner : " + portId.getCurrentOwner());
				
			}
			
			portId = 
			   CommPortIdentifier.getPortIdentifier(portName);
			System.out.println("Opening port "
							    + portId.getName());
		}
	
		catch (NoSuchPortException e)
		{
			System.out.println("Port "+portName+" not found!");
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

//////////////////////////////////////////////////////////////////

	private void createPort()
	{
		 // Is this a serial port?
		if (portId.getPortType()==CommPortIdentifier.PORT_SERIAL)
		{
			System.out.println("Serial port detected.");
			if (portId.isCurrentlyOwned()) // Is the port in use?
			{
				System.out.println("Detected "
							   + portId.getName()
							   + " in use by "
							   + portId.getCurrentOwner());
			}
			System.out.println("Port is unowned.");
			try	// Open the port
			{
				System.out.print("Opening port for connection...");
				port=(SerialPort) portId.open("MyDialer", 2000);

				if (port==null)
				{
					System.out.println("Error opening port "
							  + portId.getName());
					System.exit(1);
				}
				System.out.println("Success.");
			}
			
			catch(PortInUseException e) 
			{
				System.out.println("Port "+portId.getName()+
									" is in use!");
				System.exit(2);				
			}
		}
	}

//////////////////////////////////////////////////////////////////
	
	private void openConnection()
	{
		try
		{
			in=new InputStreamReader(port.getInputStream());
		}
	
		catch (IOException e)
		{
			System.out.println("Cannot open input stream");
			System.exit(3);
		}
	 
	
		try	// Get the output stream
		{
			out = port.getOutputStream();
		}
		catch(IOException e)
		{
			System.out.println("Cannot open output stream");
			System.exit(4);
		}
	
//		port.notifyOnCTS(true);
//		port.notifyOnDSR(true);
		port.notifyOnRingIndicator(true);
		port.notifyOnCarrierDetect(true);
//		port.notifyOnOverrunError(true);
		port.notifyOnParityError(true);
//		port.notifyOnFramingError(true);
		port.notifyOnBreakInterrupt(true);
		port.notifyOnDataAvailable(true);
//		port.notifyOnOutputEmpty(true);		
	
	}

///////////////////////////////////////////////////////////////////


	private void closeIO()
	{
		System.out.print("Closing port and IO...");
		try
		{
			if (in!=null) in.close();
			if (out!=null) out.close();
			if (port!=null) port.close();
		}
		catch(IOException e)
		{
			System.out.println("Could not close : "+e.getMessage());
		}
	System.out.println("closed.");
	}

///////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////

	public static void main(String[] args)
	{
		PortsTester md=new PortsTester();
		md.start();
		System.exit(0);	
	}
}