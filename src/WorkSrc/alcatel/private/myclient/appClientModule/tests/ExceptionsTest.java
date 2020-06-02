package tests;

import java.io.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;

class WrappingException extends Exception {
	private Throwable m_th;

	public WrappingException(String s)
	{
		super(s);
		m_th = null;

	}

	public WrappingException(String s, Throwable th)
	{
		super(s);
		m_th = th;
	}

	public void printStackTrace()
	{
		m_th.printStackTrace();
	}

	public void printStackTrace(PrintStream ps)
	{
		m_th.printStackTrace(ps);
	}
	public void printStackTrace(PrintWriter pw)
	{
		m_th.printStackTrace(pw);
	}


}

class MyAppException extends Exception {
	
	MyAppException(String message)
	{
		super(message);
	}	
}

class MyAppException2 extends Exception {	
	int m_msgCode;
	int m_locale;
	
	public MyAppException2(int msgCode, int locale)
	{
		m_msgCode =  msgCode;
		m_locale = locale;
	}	
		
	public int getLocal()
	{
		return(m_locale);
	}
	
	public int getMsgCode()
	{
		return(m_msgCode);	
	}
}

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ExceptionsTest {
	
	
	static void example1(String fName)
	{
		try {
		  FileInputStream fis = new FileInputStream(fName);			
		} catch(FileNotFoundException fe) {
			System.err.println("cannot find file: " + fName);
			fe.printStackTrace();	
		}
		
		
	}


	static void example2(String fName)
	{
		String defName = "c:/default_config.xml";
		try {
		  FileInputStream fis = new FileInputStream(fName);			
		} catch(FileNotFoundException fe) {
			
			try {
				// try to read default configuration file.
				  FileInputStream fis = new FileInputStream(defName);			
			} catch(FileNotFoundException fe2) {
				System.err.println("cannot find file: " + fName);
				System.exit(1);
			}

		}
		
		
	}

	
	
	
	static void example3(String fName)
		throws FileNotFoundException, ParserConfigurationException, 
				SAXException, IOException
	{
		FileInputStream fis = new FileInputStream(fName);
		DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = fact.newDocumentBuilder();
	    Document doc = db.parse(new InputSource(fis));
		Element root = doc.getDocumentElement();
		// some code
	}	
	
	
	static void example5(String fName)
	{
		try {
			
			example3(fName);
		} catch(FileNotFoundException fe) {
			System.out.println("Cannot find configuration file");	
		}
		catch(ParserConfigurationException pe) {
			System.out.println("Error loading XML parser");		
		}
		catch(IOException ie) {
			System.out.println("IO Error reading configuration file");		
		}
		catch(SAXException se) {
			System.out.println("Error parsing configuration file");		
		}
	
	}
	
	
	static void example6(String fName)
		throws MyAppException
	{
		try {
			FileInputStream fis = new FileInputStream(fName);
			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = fact.newDocumentBuilder();
		    Document doc = db.parse(new InputSource(fis));
			Element root = doc.getDocumentElement();
		} catch(FileNotFoundException fe) {
			throw new MyAppException("Cannot find configuration file");	
		}
		catch(ParserConfigurationException pe) {
			throw new MyAppException("Error loading XML parser");		
		}
		catch(IOException ie) {
			throw new MyAppException("IO Error reading configuration file");		
		}
		catch(SAXException se) {
			throw new MyAppException("Error parsing configuration file");		
		}
	
	}	


	static void example8(String fName)
		throws WrappingException
	{
		try {
			FileInputStream fis = new FileInputStream(fName);
			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = fact.newDocumentBuilder();
		    Document doc = db.parse(new InputSource(fis));
			Element root = doc.getDocumentElement();
		} catch(FileNotFoundException fe) {
			throw new WrappingException("Cannot find configuration file",fe);	
		}
		catch(ParserConfigurationException pe) {
			throw new WrappingException("Error loading XML parser",pe);		
		}
		catch(IOException ie) {
			throw new WrappingException("IO Error reading configuration file",ie);		
		}
		catch(SAXException se) {
			throw new WrappingException("Error parsing configuration file",se);					
		}
	
	}	


	static void showUserDialog(String msg)
	{
		
	}
	
	static void example9(String fName)
	{
		PrintWriter logger = new PrintWriter(System.out);
		try {
			example8(fName);			
			
		} catch(WrappingException we) {
			showUserDialog(we.getMessage());			
			we.printStackTrace(logger);
		}	
		
	}

	
	public static void main(String argv[])
	{
		String fName = "c:/a.xml";
				
		try {
			example3(fName);			
			example6(fName);			
		} catch(MyAppException me) {
			showUserDialog(me.getMessage());
		} catch(Exception e) {
			
			e.printStackTrace();
		//	showUserExitDialog("System error: " + e.getMessage());							
		}
		
	}	


}
