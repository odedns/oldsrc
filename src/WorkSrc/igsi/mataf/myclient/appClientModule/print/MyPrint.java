package print;

import java.awt.*;
import java.awt.geom.*;
import java.awt.print.*;
import java.io.*;
import java.util.*;

public class MyPrint implements Printable {

   //--- Private instances declarations
   private final double INCH = 72;


	String getFile(String fname) throws Exception
	{
		BufferedReader br =new BufferedReader(new FileReader(fname));
		String line = null;
		StringBuffer sb = new StringBuffer();
		
		while(null != (line = br.readLine())) {
			sb.append(line);
		}
		
		return(sb.toString());
	}


	/**
	 * convert a String containing serveral Strings seperated by
	 * the delimiter char into a String array.
	 * @param s the String to split.
	 * @param delim the delimiter character in the String.
	 * @return String[] a String array.
	 */
	public static String[] toStringArray(String s, char delim)
	{
		ArrayList ar = new ArrayList();
		StringTokenizer st = new StringTokenizer(s, new Character(delim).toString());
		String token=null;
		while(st.hasMoreTokens()) {
			token = st.nextToken();
			ar.add(token.trim());
		}
		String v[] = new String[ar.size()];
		return((String[])ar.toArray(v));
	}
	
   /**
    * Constructor: Example1 <p>
    *
    */
   public MyPrint () {
     
      //--- Create a printerJob object
      PrinterJob printJob = PrinterJob.getPrinterJob ();

      //--- Set the printable class to this one since we
      //--- are implementing the Printable interface
      printJob.setPrintable (this); 
        
      //--- Show a print dialog to the user. If the user
      //--- click the print button, then print otherwise
      //--- cancel the print job
      if (printJob.printDialog()) {
         try {
            printJob.print();  
         } catch (Exception PrintException) {
            PrintException.printStackTrace();
         }
      }

   }


   /**
    * Method: print <p>
    * 
    * This class is responsible for rendering a page using
    * the provided parameters. The result will be a grid
    * where each cell will be half an inch by half an inch.  
    *
    * @param g a value of type Graphics
    * @param pageFormat a value of type PageFormat
    * @param page a value of type int
    * @return a value of type int
    */
   public int print (Graphics g, PageFormat pageFormat, int page) {

  		String s = "This is the first line\n this is the second line\n third line";
   		if(page == 0) {
   			String lines[] = toStringArray(s,'\n');
  			Font normalFont = new Font ("serif", Font.PLAIN, 12);
  			g.setFont(normalFont);
  			int x = 20;
  			int y = 20;
  			for(int i=0; i < lines.length; ++i) {  				  				
  				y += normalFont.getSize();
  				System.out.println("line=" + lines[i] + " x=" + x + " y=" + y);
				g.drawString(lines[i],x,y);
  			}
	         return (PAGE_EXISTS);
	         
   		} else {
  			 return (NO_SUCH_PAGE);	
   		}
   }


	public static void main(String args[])
	{
		MyPrint pr = new MyPrint();
		System.exit(0);	
	}

} //Example1



















