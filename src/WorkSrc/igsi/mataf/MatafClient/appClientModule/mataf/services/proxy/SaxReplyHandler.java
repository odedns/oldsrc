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
public class SaxReplyHandler extends DefaultHandler {
	CharArrayWriter contents;
	ProxyReply m_rep;
	ArrayList m_replyList;
	String m_temp= null;
	

	/**
	 * create a SaxReplyHandler object.
	 */	
	public SaxReplyHandler()
	{
		contents = new CharArrayWriter();	
		m_replyList = new ArrayList();
	}

	/**
	 * get the proxy reply object
	 * the object should contain the XML 
	 * parsed data.
	 * @return ProxyReply the reply object.
	 */
	public ProxyReply getProxyReply()
	{
		return(m_rep);	
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

	 //  System.out.println("startElement: qName = " + qName);
       if(qName.equals("reply")) {
			m_rep = new ProxyReply();
			if(attr != null) {
				for(int i=0; i < attr.getLength(); ++i) {
					String name = attr.getQName(i);
					String value = attr.getValue(i);
					if(name.equals("id")) {
						long id = Long.parseLong(value);
						m_rep.setId(id);
						continue;
					}					
					if(name.equals("retCode")) {
						int retCode = Integer.parseInt(value);
						m_rep.setRetCode(retCode);
						continue;
					}					

				} //for
			} // if
			return;
	   } // if request	
       if(qName.equals("error")) {
			if(attr != null) {
				for(int i=0; i < attr.getLength(); ++i) {
					String name = attr.getQName(i);
					String value = attr.getValue(i);
					if(name.equals("errCode")) {
						int errCode = Integer.parseInt(value);
						m_rep.setErrCode(errCode);
						continue;
					}					
				} //for
			} // if
			return;
	   } // if request	

  }
  
  /**
	*  Override methods of the DefaultHandler class
	*  to gain notification of SAX Events.
	*/
   public void endElement( String namespaceURI,
			  String localName,
			  String qName ) throws SAXException
	{

//		System.out.println("endElement: qName = " + qName);
		String value = contents.toString().trim();
		contents.reset();						
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
			m_rep.addParam(m_temp,value);

			m_temp = null;				
			return;
		}
		
		if(qName.equals("err-msg")) {
			m_rep.setErrMsg(value);
			return;
		}

		if(qName.equals("reply")) {
			m_replyList.add(m_rep);
			//GLogger.debug("parsed request: " + m_rep.toString());
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
