package mataf.services.proxy;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import mataf.logger.GLogger;
import mataf.utils.StringUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.Service;
import com.ibm.dse.base.Tag;
import com.ibm.dse.base.TagAttribute;

/**
 * @author Oded Nissan 11/06/2003
 * 
 */
public class ProxyService extends Service								
{
	static final String TRANS_NAME_PARAM 	= "trxid";
	static final String RETCODE_PARAM 	= "retCode";
	
	int 				m_listenPort	=	9999;
	int 				m_sendPort		=	9999;
	String 			  	m_host 			= "localhost";
	RequestDispatcher 	m_dispatcher;
	Socket 				m_sendSock;
	DataInputStream 	m_dis;
	DataOutputStream 	m_dos;
	ProxyReceiveThread 	m_receiver;
	boolean 			m_debug;
	boolean 			m_loadReceiver 	= true;
		
	public ProxyService()
		throws IOException
	{
		// init();	
	}
	
		
	/**
	 * initialize from externalizer.
	 */
	public java.lang.Object initializeFrom(Tag aTag)
                                throws java.io.IOException,
                                       DSEException
	{
		super.initializeFrom(aTag);
//		GLogger.debug("Tag = " + aTag.toString());
		String name = null;
		String value = null;
		
		for (int i=0;i<aTag.getAttrList().size();i++) {
			TagAttribute attribute = (TagAttribute) aTag.getAttrList().elementAt(i);			
			name = attribute.getName();
			value = (String) attribute.getValue();
			if (name.equals("listenPort")) {			
				m_listenPort = Integer.parseInt(value);
				continue;
			}
			if (name.equals("sendPort")) {				
				m_sendPort = Integer.parseInt(value);
				continue;
			}
			if (name.equals("sendHost")) {				
				m_host = value;
				continue;
			}

			if (name.equals("loadReceiver")) {			
				if(value.equalsIgnoreCase("false")) {
					m_loadReceiver = false;	
				} else {
					m_loadReceiver = true;	
				}							
				continue;
			}
			if (name.equals("debug")) {			
				if(value.equalsIgnoreCase("true")) {
					m_debug = true;	
				} else {
					m_debug = false;	
				}							
			}
	
		}		
		/**
		 * initialize the ProxyService.
		 */
		init();
		return(this);
	}


	/**
	 * set the port we will send request to.
	 * @param sendPort the port num we will connect
	 * to to send requests to the RT proxy.
	 */
	public void setSendPort(int sendPort)
	{
		m_sendPort = sendPort;
	}
	/**
	 * set the port we are listening on.
	 * @param listenPort the port to listen to.
	 */
	public void setListenPort(int listenPort) 
	{
		m_listenPort = listenPort;	
	}
	/**
	 * set the host name to send requests to.
	 * @param host the host to send requests to.
	 */
	public void setHost(String host)
	{
		m_host = host;	
	}
	/**
	 * initialize the object.
	 * initialize listening thread.
	 * Add the Global fields request handler to the list
	 * of handlers.
	 */
	void init() throws IOException
	{
		m_dispatcher = new RequestDispatcher();
		if(m_loadReceiver) {
			m_receiver = new ProxyReceiveThread(m_listenPort,m_dispatcher);				
			m_receiver.start();
		}
	}
	
	void initConnection()
		throws Exception
	{	
		GLogger.debug("host=" + m_host + "\tport=" + m_sendPort);	
		m_sendSock = new Socket(m_host, m_sendPort);
		m_dis = new DataInputStream(m_sendSock.getInputStream());		
		m_dos = new DataOutputStream(m_sendSock.getOutputStream());
		m_sendSock.setSoTimeout(0);
		GLogger.info("connected to : " + m_sendSock + ":" + m_sendPort);
		
	}


	/**
	 * activate a RT transaction.
	 * @param name the name of the transaction.
	 * @throws RequestException in case of error.
	 */
	public void activateTransaction(String name)
		throws RequestException
	{
		ProxyRequest rq = new ProxyRequest();
		rq.setCommand(Integer.parseInt(RTCommands.TRANSACTION_COMMAND));
		rq.addParam(TRANS_NAME_PARAM, name);
		sendRequest(rq);
	}
		
	/**
	 * send the request to the RT.
	 * and wait to receive the response. 
	 * if the response is OK return the 
	 * response paramtes.
	 * if there was an error - throw a RequestException
	 * exception to indicate the error.
	 * @param req the ProxyRequest object.
	 * @return HashMap containing the response data.
	 */
	public HashMap sendRequest(ProxyRequest req) 
		throws RequestException
	{
		ProxyReply rep = null;
		DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
		StringWriter sw = null;
		try {
			if(null == m_receiver && m_loadReceiver == false) {
				m_receiver = new ProxyReceiveThread(m_listenPort,m_dispatcher);				
				m_receiver.start();				
			}
			if(null == m_sendSock ) {
				initConnection();	
			}
			DocumentBuilder db = fact.newDocumentBuilder();
			Document doc = db.newDocument();
			Element root = doc.createElement("proxyrequest");
			doc.appendChild(root);
			Element reqElem = doc.createElement("request");
			reqElem.setAttribute("id", new Long(req.getId()).toString());
			root.appendChild(reqElem);
			Element cmdElem = doc.createElement("command");
			cmdElem.appendChild( doc.createTextNode( new Integer(req.getCommand()).toString()));		
			reqElem.appendChild(cmdElem);
			HashMap params = req.getParams();
			if(null != params) {
				Set keySet = params.keySet();
				Iterator iter = keySet.iterator();
				String name = null;
				String value = null;
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
				reqElem.appendChild(eparams);
			} // if params
		
			TransformerFactory trans_factory=TransformerFactory.newInstance();
			Transformer xml_out = trans_factory.newTransformer();
			Properties props = new Properties();
			props.put("method", "xml");
			props.put("indent", "yes");
			xml_out.setOutputProperties(props);		
			sw = new StringWriter();
			xml_out.transform(new DOMSource(doc),
				new StreamResult(sw));			
		
		} catch(Exception e){
			/*
			 * if there was an Excepiton
			 * throw a RequestException with some general
			 * error code.
			 */
			 e.printStackTrace();
			GLogger.debug(e);
//			throw new RequestException(RequestDispatcher.GENERAL_ERROR_CODE,e.getMessage());			
		}
		try {
			byte b[] = sw.getBuffer().toString().getBytes();
			int len = b.length;
						
			m_dos.writeInt(len);			
			m_dos.write(b);
			m_dos.flush();
			
			/*
			 * now read the reply from the input stream.
			 * parse the reply into a HashMap
			 * containing return params.
			 */
			 len = m_dis.readInt();
			 GLogger.debug("got reply len=" + +len);			
			 b = new byte[len];
			 m_dis.read(b);
			 GLogger.debug("reply data: " + new String(b));
			 rep = handleReply(new String(b));			 
			 
		} catch(IOException e){
			/*
			 * if there was an IOExcepiton
			 * throw a RequestException with some general
			 * error code.
			 * set the sending socket to null so that 
			 * connection will be recreated on the next call.
			 */
			m_sendSock = null;
			e.printStackTrace();
			throw new RequestException(RequestDispatcher.GENERAL_ERROR_CODE,e.getMessage());			
		}
		/*
		 * if there was an erorr thorw the exception.
		 * otherwise return the return params.
		 */
		if(RequestDispatcher.ERR_RETURN_CODE == rep.getRetCode()) {
			throw new RequestException(rep.getErrCode(), rep.getErrMsg());	
		} 
		
		HashMap params = rep.getParams();
		return(params);
	}
	
	
	
	/**
	 * parse the xml reply string into
	 * a PrxoyReply object containing return 
	 * data.
	 * @return ProxyReply the reply object.
	 */
	ProxyReply handleReply(String reply)
	{
		ProxyReply rep = null;
		StringReader sw = new StringReader(reply);
		SaxReplyHandler handler = new SaxReplyHandler();

		try {
	 	    SAXParserFactory fact = SAXParserFactory.newInstance();
			SAXParser sp = fact.newSAXParser();
			sp.parse(new InputSource(sw),handler);
			rep = handler.getProxyReply();			
		} catch(Exception e) {
			e.printStackTrace();	
			rep.setRetCode(RequestDispatcher.ERR_RETURN_CODE);
			rep.setErrCode(RequestDispatcher.GENERAL_ERROR_CODE);
			rep.setErrMsg(RequestDispatcher.GENERAL_ERR_MSG);
		}
			
		return(rep);
	}
	/**
	 * send the request to the RT.
	 * and wait to receive the response. 
	 * if the response is OK return the 
	 * response paramtes.
	 * if there was an error - throw a RequestException
	 * exception to indicate the error.
	 * @param cmd the command string.
	 * @parem params a HashMap containing request parameters.
	 * @return HashMap containing the response data.
	 */
	public HashMap sendRequest(int cmd, HashMap params) 
		throws RequestException
	{
		ProxyRequest req = new ProxyRequest(cmd,params);		
		return(sendRequest(req));
	}

	/**
	 * send the request to the RT.
	 * and wait to receive the response. 
	 * if the response is OK return the 
	 * response paramtes.
	 * if there was an error - throw a RequestException
	 * exception to indicate the error.
	 * @param cmd the command string.
	 * @parem paramKey the key for the single param passed to the function.
	 * @parem paramValue the value for the single param passed to the function.
	 * @return HashMap containing the response data.
	 */
	public HashMap sendRequest(int cmd, Object paramKey, Object paramValue) 
		throws RequestException
	{
		HashMap params = new HashMap();
		params.put(paramKey,paramValue);
		ProxyRequest req = new ProxyRequest(cmd,params);		
		return(sendRequest(req));
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
		m_dispatcher.addRequestHandler(cmd,rh);
	}	

	/**
	 * remove a requesthandler from the handlers
	 * table.
	 * @param cmd the command that the request will handle.
	 * if "*" then handle all commands.
	 */	
	public void removeRequestHandler(String cmd)
	{
		m_dispatcher.removeRequestHandler(cmd);
	}	


	/**
	 * stopReceiver.
	 */
	public void stopReceiver()
	{
		m_receiver.stopThread();	
	}

	/**
	 * terminate the service.
	 * called when context is unchained.
	 */
	public void terminate() throws DSEException
	{
		try {
			close();
		} catch(IOException e) {
			throw new DSEException("ERROR","",e.getMessage());
		}
		
	}
	/**
	 * close the service and stop the thread.
	 */
	private void close()
		throws IOException
	{
		m_receiver.stopThread();		
		if(m_dos != null) {
			m_dos.close();
		}
		if(m_dis != null) {
			m_dis.close();
		}
		if(m_sendSock != null) {
			m_sendSock.close();
		}
	}
	
	/**
	 * try to authenticate a user and his password to the 
	 * RT system.
	 * @param userId the userId of the user to authenticate.
	 * @param password the password of the user to authenticate.
	 * @return boolean true if password is correct for the user, 
	 * false otherwise.
	 */
	public int authenticate(String userId, String password)
	{		
		HashMap params = new HashMap();
		params.put(userId,password);
		int n = -1;
		try {
			HashMap retParams = sendRequest(
				Integer.parseInt(RTCommands.AUTHENTICATE_COMMAND),params);
			/*
			 * if authentication succeded the retCode
			 * should be 0 else should be non-zero.
			 */
			String s  = (String) retParams.get(RETCODE_PARAM);
			n = Integer.parseInt(s);
		
		} catch(RequestException re) {
			GLogger.error(this.getClass(), null, "Error in Proxy.authenticate()",re,false);
		}
		return(n);
	}
	

	/**
	 * get a list of managers in the branch currently logged on 
	 * to the RT system. Return the user's manager as the default manager.
	 * @param userId the userId of the teller sending the request.
	 * @return IndexedCollection a list contaianing the userids and 
	 * names of the managers retreived.
	 * @exception RequestException in case of error.
	 */	
	public IndexedCollection getManagersList(String snif60,String samchut)
		throws RequestException
	{
		GLogger.debug("getManbagersList : samchut=" + samchut + "\tsnif60=" + snif60);
		HashMap hm = sendRequest(
			Integer.parseInt(RTCommands.MANAGERSLIST_COMMAND),"samchut", snif60 + ',' 
				+ samchut);
		IndexedCollection ic = null;
		try {
			ic = createManagersCollection(hm);
		} catch(IOException ie) {
			throw new RequestException(ie.getMessage());
		}
		return(ic);		
	}
	
	/**
	 * convert a HashMap with managers data into
	 * an IndexedCollection containing the data.
	 */
	private IndexedCollection createManagersCollection(HashMap hp)	
		throws IOException
	{
		IndexedCollection ic = (IndexedCollection) DataElement.readObject("managersList");
		Set entries = hp.entrySet();		
		Iterator iter = entries.iterator();
		Map.Entry entry = null;
		String key = null;		
		while(iter.hasNext()) {
			entry = (Map.Entry) iter.next();		
			KeyedCollection kc = (KeyedCollection) DataElement.readObject("managerData");
			kc.setDynamic(true);
			key = (String) entry.getKey();
			String value = (String) entry.getValue();
			StringTokenizer st = new StringTokenizer(value,",");
			String mgrId = st.nextToken();
			String samchut = null;
			String wksStatus = null;
			
			if(st.hasMoreTokens()) {
				samchut = st.nextToken();
			}
			if(st.hasMoreTokens()) {
				wksStatus = st.nextToken();
			}
			GLogger.debug("mgrId - " + mgrId);
			GLogger.debug("samchut - " + samchut);						
			try {
				kc.trySetValueAt("managerName",key);
				kc.trySetValueAt("managerId",mgrId);			
				kc.trySetValueAt("samchutMeasheret",samchut);			
				kc.trySetValueAt("wksStatus",wksStatus);			
			} catch(DSEInvalidArgumentException dse) {
			}
			ic.addElement(kc);
		}
		return(ic);
	}
	
	/**
	 * Sets value at the context and updates the global field in the RT environment
	 * as well.
	 */
	public void setGlobalValueAt(String dataName,Object value) throws Exception, RequestException
	{
		String v[] = StringUtils.toStringArray(dataName, '.');
		StringBuffer dataNameCodeBuffer = new StringBuffer();
		GlobalRecordsMap gMap = null;
		
		gMap = GlobalRecordsMap.getInstance();
		
		for(int i=0;i<v.length;i++)
			dataNameCodeBuffer.append(gMap.getFieldCode(v[i])+".");
			
		String dataNameCode = dataNameCodeBuffer.substring(0,dataNameCodeBuffer.length()-1);
		GLogger.debug("Created dataNameCode : "+dataNameCode);		
		sendRequest(Integer.parseInt(RTCommands.GLOBAL_UPDATE_COMMAND),dataNameCode,value.toString());
	}


	/**
	 * check access to new Composer tranactions. 
	 * using RT security system.
	 */
	public boolean checkAccess(String trxId) throws RequestException
	{
		boolean result = false;
		int n = -1;
		HashMap retParams = sendRequest(
				Integer.parseInt(RTCommands.CHECK_ACCESS_COMMAND),TRANS_NAME_PARAM,trxId);				
		String s  = (String) retParams.get(RETCODE_PARAM);
		n = Integer.parseInt(s);	
		result = (n > 0 ? true : false);		
		return(result);	
	}		

	
}
