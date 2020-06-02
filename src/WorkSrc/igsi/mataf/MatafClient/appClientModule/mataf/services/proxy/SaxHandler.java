package mataf.services.proxy;

import java.io.CharArrayWriter;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Oded Nissan 11/06/2003
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SaxHandler extends DefaultHandler {
	CharArrayWriter contents;
	ProxyRequest m_req;
	String m_temp;
	ArrayList m_reqList;
		
		
	/**
	 * create and initialize
	 * the SaxHandler.
	 */
	public SaxHandler()
	{
		contents = new CharArrayWriter();	
		m_reqList = new ArrayList();
	}



	/**
	 * return the parsed proxy request
	 * as a ProxyRequest object.
	 */
	public ProxyRequest getProxyRequest()
	{
		return(m_req);	
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

	//   System.out.println("startElement: qName = " + qName);
	   if(qName.equals("request")) {
			m_req = new ProxyRequest();
			if(attr != null) {
				for(int i=0; i < attr.getLength(); ++i) {
					String name = attr.getQName(i);
					String value = attr.getValue(i);
					if(name.equals("id")) {
						long id = Long.parseLong(value);
						m_req.setId(id);
						continue;
					}					
				} //for
			} // if
			return;
	   } // if request	 	  

   } // startElement


   /**
	*  Override methods of the DefaultHandler class
	*  to gain notification of SAX Events.
	*/
   public void endElement( String namespaceURI,
			  String localName,
			  String qName ) throws SAXException
	{

	//	System.out.println("endElement: qName = " + qName);
		String value = contents.toString().trim();
		contents.reset();
				
		if(qName.equals("command")) {
			m_req.setCommand(Integer.parseInt(value));				
			return;
		}
		/**
		 * store parameter name 
		 * into temp var.
		 */
		if(qName.equals("name")) {
			m_temp = value;				
			return;
		}
		/**
		 * put the param name from temp var
		 * and the value into the ProxyRequest
		 * params table.
		 */
		if(qName.equals("value")) {
//			System.out.println("adding: name=" + m_temp + " valkue= " + value);
			m_req.addParam(m_temp,value);
			m_temp = null;				
			return;
		}
		if(qName.equals("request")) {
			m_reqList.add(m_req);
			//GLogger.debug("parsed request: " + m_req.toString());
		}
		
		

	}
   


	/**
	 * handle input character data.
	 */
    public void characters( char[] ch, int start, int length )
				  throws SAXException 
	{
	  contents.write( ch, start, length );
    }
   
}
