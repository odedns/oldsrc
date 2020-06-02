package print;

import java.awt.*;
import java.awt.geom.*;
import java.awt.print.*;
import java.awt.font.*;
import java.text.*;


class Listing4 implements Printable {
		

		//--- Private instances declarations
	   private final static int POINTS_PER_INCH = 72;
	   
	   
	   
	   Listing4()
	   {
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
 
       public int print (Graphics g, PageFormat pageFormat, int page) {
 
 
          //--- Create the Graphics2D object
          Graphics2D g2d = (Graphics2D) g;
 
          //--- Translate the origin to 0,0 for the top left corner
          g2d.translate (pageFormat.getImageableX (), pageFormat.getImageableY ());
 
          //--- Set the drawing color to black
          g2d.setPaint (Color.black);
 
          //--- Draw a border around the page using a 12 point border
          g2d.setStroke (new BasicStroke (4));
          Rectangle2D.Double border = new Rectangle2D.Double (0,
                                                              0,
                                                              pageFormat.getImageableWidth (),
                                                              pageFormat.getImageableHeight ());
 
          g2d.draw (border);
 
 
          //--- Create a string and assign the text
          String text = new String ();
         text += "Manipulating raw fonts would be too complicated to render paragraphs of ";
         text += "text. Trying to write an algorithm to fully justify text using ";         
          text += "proportional fonts is not trivial. Adding support for international ";    
          text += "characters adds to the complexity. That's why we will use the ";         
          text += "TextLayout and the LineBreakMeasurer class to "; 
          text += "render text. The TextLayout class offers a lot of ";          
          text += "functionality to render high quality text. This class is capable of ";    
          text += "rendering bidirectional text such as Japanese text where the alignment "; 
          text += "is from right to left instead of the North American style which is left ";
          text += "to right. The TextLayout class offers some additional ";      
          text += "functionalities that we will not use in the course of this ";             
         text += "series. Features such as text input, caret positioning and hit ";        
          text += "testing will not be of much use when printing documents, but it's good "; 
          text += "to know that this functionality exists. ";
          
          text += "The TextLayout class will be used to layout ";               
          text += "paragraphs. The TextLayout class does not work alone. To ";  
          text += "layout text within a specified width it needs the help of the ";         
          text += "LineBreakMeasurer class. This class will wrap a string of "; 
         text += "text to fit a predefined width. Since it's a multi-lingual class, it ";   
          text += "knows exactly where to break a line of text according to the rules ";     
          text += "of the language.  Then again the LineBreakMeasurer does ";   
          text += "not work alone. It needs information from the ";
          text += "FontRenderContext class. This class' main function is to ";  
          text += "return accurate font metrics. To measure text effectively, this class ";  
          text += "needs to know the rendering hints for the targeted device and the font "; 
          text += "type being used. ";  
 
 
          //--- Create a point object to set the top left corner of the TextLayout object
          Point2D.Double pen = new Point2D.Double (0.25 * POINTS_PER_INCH, 0.25 * POINTS_PER_INCH);
 
          //--- Set the width of the TextLayout box
         double width = 7.5 * POINTS_PER_INCH;
 

          //--- Create an attributed string from the text string. We are creating an
          //--- attributed string because the LineBreakMeasurer needs an Iterator as
          //--- parameter.
          AttributedString paragraphText = new AttributedString (text);
 
          //--- Set the font for this text
          paragraphText.addAttribute (TextAttribute.FONT, new Font ("serif", Font.PLAIN, 12));
 
          //--- Create a LineBreakMeasurer to wrap the text for the TextLayout object
          //--- Note the second parameter, the FontRendereContext. I have set the second
          //--- parameter antiAlised to true and the third parameter useFractionalMetrics
         //--- to true to get the best possible output
          LineBreakMeasurer lineBreaker = new LineBreakMeasurer (paragraphText.getIterator(),
                                                                 new FontRenderContext (null, true, true));
 
          //--- Create the TextLayout object
          TextLayout layout;
 
          //--- LineBreakMeasurer will wrap each line to correct length and
          //--- return it as a TextLayout object
          while ((layout = lineBreaker.nextLayout ((float) width)) != null) {
 
             //--- Align the Y pen to the ascend of the font, remember that
             //--- the ascend is origin (0, 0) of a font. Refer to Figure 1
             pen.y += layout.getAscent ();
 
             //--- Draw the line of text
             layout.draw (g2d, (float) pen.x, (float) pen.y);
 
             //--- Move the pen to the next position adding the descent and
             //--- the leading of the font
             pen.y += layout.getDescent () + layout.getLeading ();
          }
 
          //--- Validate the page
          return (PAGE_EXISTS);
       }
       
       
       
       public static void main(String args[])
       {
       		Listing4 lst  = new Listing4();
       		System.exit(1);
       }
    }
 

                 

