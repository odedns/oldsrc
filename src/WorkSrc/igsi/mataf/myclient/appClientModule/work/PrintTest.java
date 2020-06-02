package work;
import java.io.*;
import java.awt.print.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class PrintTest {

	static void print() 
	{
		
		 FileOutputStream outstream;
		 StreamPrintService psPrinter;
		 String psMimeType = "application/postscript";

		 StreamPrintServiceFactory[] factories =
	     PrinterJob.lookupStreamPrintServices(psMimeType);
		 if (factories.length > 0) {
		     try {
        		 outstream = new File("out.ps");
		         psPrinter =  factories[0].getPrintService(fos);
        		 // psPrinter can now be set as the service on a PrinterJob 
		     } catch (FileNotFoundException e) {
		     }	
		 }            
	}
	
	
	static void simplePrint()
	{
		try {
 		   FileOutputStream fos = new FileOutputStream("LPT1");
		    PrintStream ps = new PrintStream(fos);
            ps.print("Your string goes here");
            ps.print("\f");
            ps.close();
		} catch (Exception e) {
			e.printStackTrace();
	    	System.out.println("Exception occurred: " + e);
		}
	}
	
	public static void main(String[] args) {
	
	
	}
}
