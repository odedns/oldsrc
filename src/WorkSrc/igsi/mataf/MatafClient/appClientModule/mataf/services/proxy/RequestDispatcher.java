package mataf.services.proxy;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import mataf.logger.GLogger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

/**
 * @author Oded Nissan 11/06/2003
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class RequestDispatcher {
	
	HashMap m_handlers;
	String m_xmlReply;
	public static final int ERR_RETURN_CODE = -1;
	static final int GENERAL_ERROR_CODE = 999;
	static final String GENERAL_ERR_MSG = "Some unknown error occured";
	static final String GENERAL_ERROR =
	"<?xml version=\"1.0\" encoding=\"UTF-8\"?> <proxyreply><reply id=\"0\" retcode=\"999\" ></reply></proxyreply>";

	/**
	 * create the request dispatcher 
	 * object.
	 */
	public RequestDispatcher()	
	{
		m_handlers = new HashMap();
	}

	/**
	 * add a requesthandler to the handlers
	 * table.
	 * @param cmd the command that the request will handle.
	 * if "*" then handle all commands.
	 * @param rh the handler that will handle the command.
	 */	
	public void addRequestHandler(String cmd,RequestHandlerIF rh)
	{
		m_handlers.put(cmd,rh);
	}
	
	
	/**
	 * remove a requesthandler from the handlers
	 * table.
	 * @param cmd the command that the request will handle.
	 * if "*" then handle all commands.
	 */	
	public void removeRequestHandler(String cmd)
	{
		m_handlers.remove(cmd);
	}
	
	String getReply()
	{
		return(m_xmlReply);
	}
	
	/**
	 * parse the XML request into a ProxyRequest object.
	 * activate the appropriate handler for the ProxyRequest.
	 * @param req the XML request as String.
	 */
	void handleRequest(String req)
	{
		StringReader sw = new StringReader(req);
		SaxHandler handler = new SaxHandler();
		ProxyRequest pr = null;
		try {
	 	    SAXParserFactory fact = SAXParserFactory.newInstance();
			SAXParser sp = fact.newSAXParser();
			sp.parse(new InputSource(sw),handler);
			pr = handler.getProxyRequest();
			String cmd = String.valueOf(pr.getCommand());
			//System.out.println("Command = "+cmd);
			/*
			 * if appropriate handler not found.
			 * search for generic handler.
			 */
			RequestHandlerIF ar = (RequestHandlerIF)m_handlers.get(cmd);
			if(null == ar) {
				ar =(RequestHandlerIF)m_handlers.get("*");
				if(null == ar) {
					throw new RequestException(GENERAL_ERROR_CODE, "No handler to handle request");	
				}
			}
			/*
			 * execute the request.
			 */
			HashMap params = ar.execRequest(pr);
			// System.out.println("now sending response: " + params.toString());
			sendResponse(pr.getId(),params);
		} catch(RequestException re) {
			/*
			 * in case of error send an 
			 * error response with error code
			 * and error message.
			 */
			re.printStackTrace();
			long id = pr.getId();
			int errCode = re.getErrorCode();
			String msg = re.getMessage();
			sendErrorResponse(id, errCode, msg);
		} catch(Exception e) {
			GLogger.error(this.getClass(), null, null,e,false);	
			sendErrorResponse(0, 999, e.getMessage());
		}
	
	}
	
	
	/**
	 * send the response with the return 
	 * params to the caller.
	 * @param id the request id.
	 * @param the response params.
	 */
	void sendResponse(long id, HashMap params)
	{
		String retCode = null;
		DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = fact.newDocumentBuilder();
			Document doc = db.newDocument();
			Element root = doc.createElement("proxyreply");
			doc.appendChild(root);
			Element req = doc.createElement("reply");
			req.setAttribute("id", new Long(id).toString());
			if(null != params) {
				retCode = (String) params.remove("retCode");
			}
			if(null == retCode) {
				retCode = "0";
			}
			req.setAttribute("retCode",retCode );
			root.appendChild(req);
			String name = null;
			String value = null;
			/*
			 * add params tags only if there is
			 * data in params.
			 */
			if(null != params ) {
				Set keySet = params.keySet();
				Iterator iter = keySet.iterator();
				Element eparams = doc.createElement("params");		
			
				while(iter.hasNext()) {
					name = (String) iter.next();
					value = (String) params.get(name);
					Element param = doc.createElement("param");
					Element elem = doc.createElement("name");
					elem.appendChild( doc.createTextNode( name));	
					param.appendChild(elem);
					elem = doc.createElement("value");
					elem.appendChild( doc.createTextNode(value));	
					param.appendChild(elem);
					eparams.appendChild(param);
				}
				req.appendChild(eparams);
				
			} // if
		
			TransformerFactory trans_factory=TransformerFactory.newInstance();
			Transformer xml_out = trans_factory.newTransformer();
			Properties props = new Properties();
			props.put("method", "xml");
			props.put("indent", "yes");
			xml_out.setOutputProperties(props);		
			StringWriter sw = new StringWriter();
			xml_out.transform(new DOMSource(doc),
				new StreamResult(sw));
			m_xmlReply = sw.getBuffer().toString();			
			// System.out.println("outputXml:\n" + m_xmlReply);
		} catch(Exception e){
			e.printStackTrace();
			m_xmlReply = GENERAL_ERROR;
		}
		
	}
	
	/**
	 * send the error response with the error code
	 * and error message to the caller.
	 * @param id the request id.
	 * @param errCode the error code.
	 * @param errMsg the error message.
	 */
	void sendErrorResponse(long id, int errCode, String errMsg)		
	{
		GLogger.debug("sendErrorResponse: id=" + id + " errCode = " + errCode + " errMsg = " + errMsg);
		DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = fact.newDocumentBuilder();
			Document doc = db.newDocument();
			Element root = doc.createElement("proxyreply");
			doc.appendChild(root);
			Element req = doc.createElement("reply");
			req.setAttribute("id", new Long(id).toString());
			req.setAttribute("retCode",new Integer(ERR_RETURN_CODE).toString() );
			root.appendChild(req);
			Element err = doc.createElement("error");
			err.setAttribute("errCode", new Integer(errCode).toString());
			Element msg = doc.createElement("err-msg");
			msg.appendChild(doc.createTextNode(errMsg));
			err.appendChild(msg);
			req.appendChild(err);
		
			TransformerFactory trans_factory=TransformerFactory.newInstance();
			Transformer xml_out = trans_factory.newTransformer();
			Properties props = new Properties();
			props.put("method", "xml");
			props.put("indent", "yes");
			xml_out.setOutputProperties(props);		
			StringWriter sw = new StringWriter();
			xml_out.transform(new DOMSource(doc),
				new StreamResult(sw));
			m_xmlReply = sw.getBuffer().toString();			
			// System.out.println("outputXml:\n" + m_xmlReply);
		} catch(Exception e){
			e.printStackTrace();
			m_xmlReply = GENERAL_ERROR;
		}
	
		
	}	
	
	
	
}
