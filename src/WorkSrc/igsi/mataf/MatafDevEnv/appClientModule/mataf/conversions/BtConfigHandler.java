package mataf.conversions;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

//import javax.resource.spi.EISSystemException;
import javax.xml.parsers.*;
import java.io.*;
import java.util.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BtConfigHandler extends DefaultHandler {
	private CharArrayWriter contents = new CharArrayWriter();
	ArrayList m_fields;
	BtEntityInfo m_eInfo;
	String m_fTag;
	/**
	 * Constructor for BtConfigReader.
	 */
	public BtConfigHandler(String fTag) {
		super();
		m_fields= new ArrayList();
		m_eInfo = new BtEntityInfo();
		m_fTag = fTag;
	}
	/**
	*  Override methods of the DefaultHandler class
	*  to gain notification of SAX Events.
	*/
   public void startElement( String namespaceURI,
			  String localName,
			  String qName,
			  Attributes attr ) throws SAXException 
  {

//	   System.out.println("startElement: qName = " + qName);
	 
	  if(qName.equals(m_fTag)) {
	  	return;	
	  }	
	  
	  if(qName.equals("group")) { 
		  if(attr != null) {
			for(int i=0; i < attr.getLength(); ++i) {
				String name = attr.getQName(i);
				String value = attr.getValue(i);
				if(name.equals("name")) {
					m_eInfo.setGroup(value);
					continue;
				}
			} //for
		  }
	  	return;	
	  }
	  
	  if(qName.equals("entity")) {
	  	
		 
	   	if(attr != null) {
			for(int i=0; i < attr.getLength(); ++i) {
				String name = attr.getQName(i);
				String value = attr.getValue(i);
//				System.out.println("attr name: " + name + " value: " + value);
				if(name.equals("name")) {
					m_eInfo.setEntityName(value);
					continue;
				}
				if(name.equals("relation")) {
					m_eInfo.setRelatedEntity(value);
					continue;
				}
				if(name.equals("pk")) {
					m_eInfo.setRelatedEntityPK(value);
					continue;
				}
			}
	   	} //if
	  	
	  	
	  	return;	
	  }

	  BtField fld = new BtField();
	  fld.setYn(false);
	  /**
	   * now loop over all Attributes.
	   */
	   if(attr != null) {
		for(int i=0; i < attr.getLength(); ++i) {
			String name = attr.getQName(i);
			String value = attr.getValue(i);
//			System.out.println("attr name: " + name + " value: " + value);
			if(name.equals("name")) {
				fld.setName(value);
				continue;
			}
			if(name.equals("length")) {
				int len=0;
				try {
					len = Integer.parseInt(value);
				} catch(NumberFormatException ne) {
					ne.printStackTrace();
					len = 0;	
				}
				fld.setLength(len);
				continue;
			}
			if(name.equals("ignore")) {
				boolean ignore = false;
				if(value.equalsIgnoreCase("true")) {
					ignore = true;
				}
				fld.setIgnore(ignore);
				continue;
			}
			if(name.equals("yn")) {
				boolean yn = false;
				if(value.equalsIgnoreCase("true")) {
					yn = true;
				}
				fld.setYn(yn);
				continue;
			}

		} // for
	   } // if
	   m_fields.add(fld);


   }

   public void endElement( String namespaceURI,
			  String localName,
			  String qName ) throws SAXException
	{

//		System.out.println("endElement: qName = " + qName);
		String value = contents.toString().trim();
		contents.reset();
		

	}
   


    public void characters( char[] ch, int start, int length )
				  throws SAXException 
	{

	  contents.write( ch, start, length );

   }
   
   /**
    * get the field array from the handler
    */
   	public BtField[] getBtFields()
   	{
   		BtField fields[] = new BtField[m_fields.size()];
   		fields = (BtField[]) m_fields.toArray(fields);
   		return(fields);
   	}


	/**
	 * return the entity info class.
	 * @return BtEntityInfo the BtEntityInfo class initialized
	 * by the handler.
	 */
	public BtEntityInfo getEntityInfo()
	{
		return(m_eInfo);	
	}
	/** 
	 * main test program.
	 */
	public static void main(String argv[])
	{
			System.out.println( "BtConfigHandler" );
	  try {

		 // Set the ContentHandler...
		 BtConfigHandler handler = new BtConfigHandler("fldlst");
		 SAXParserFactory fact = SAXParserFactory.newInstance();
		 SAXParser sp = fact.newSAXParser();
		 sp.parse(new FileInputStream("d:/work/dev_env/conversion/fldlst.xml"),handler);

	  }catch ( Exception e )  {
		 e.printStackTrace();
	  }
		
		
	}

}
