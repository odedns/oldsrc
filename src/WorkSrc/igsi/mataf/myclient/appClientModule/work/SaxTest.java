package work;
/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2000
 * Company:
 * @author Oded Nissan
 * @version 1.0
 */

import org.xml.sax.*;
import org.xml.sax.helpers.*;
import javax.xml.parsers.*;
import java.io.*;


public class SaxTest extends DefaultHandler {

   // Local variables to store data
   // found in the XML document
  String m_fileName;
  String m_logLevel;
  long m_rotationSize;
  int m_maxRotations;
  String s;

   // Buffer for collecting data from // the "characters" SAX event.
   private CharArrayWriter contents = new CharArrayWriter();



   // Override methods of the DefaultHandler class
   // to gain notification of SAX Events.
   //
		// See org.xml.sax.ContentHandler for all available events.
   //

   public void startElement( String namespaceURI,
			  String localName,
			  String qName,
			  Attributes attr ) throws SAXException {

	  contents.reset();
	  System.out.println("startElement: localName=" + localName + " qName=" + qName);

   }

   public void endElement( String namespaceURI,
			  String localName,
			  String qName ) throws SAXException {




	  if ( localName.equals( "logfile-name" ) ) {
		 m_fileName = contents.toString();
	  }

	  if ( localName.equals( "log-level" ) ) {
		 m_logLevel = contents.toString();

	  }

	  if ( localName.equals( "rotation-size" ) ) {
		 s = contents.toString();
		 try {
			 m_rotationSize = Integer.parseInt(s);
		 }
		 catch (Exception ex) {
			ex.printStackTrace();
		 }
	  }

	if ( localName.equals( "max-rotations" ) ) {
		 s = contents.toString();
		 try {
			 m_maxRotations = Integer.parseInt(s);
		 }
		 catch (Exception ex) {
			ex.printStackTrace();
		 }
	  }


   }



   public void characters( char[] ch, int start, int length )
				  throws SAXException {

	  contents.write( ch, start, length );

   }

   void dump()
   {
		System.out.println("logfile-name = " + m_fileName);
		System.out.println("log-level = " + m_logLevel);
		System.out.println("rotation-size = " + m_rotationSize);
		System.out.println("max-rotations = " + m_maxRotations);

	}

   public static void main( String[] argv ){

	  System.out.println( "SaxTest" );
	  try {

		 // Create SAX 2 parser...
		 //XMLReader xr = XMLReaderFactory.createXMLReader();

		 // Set the ContentHandler...
		 SaxTest sx = new SaxTest();
		 SAXParserFactory fact = SAXParserFactory.newInstance();
		 fact.setValidating(true);
		 SAXParser sp = fact.newSAXParser();
		 sp.parse(new FileInputStream("c:/dev/src/java/xml/logger.xml"),sx);

		sx.dump();

	  }catch ( Exception e )  {
		 e.printStackTrace();
	  }


   }

}

