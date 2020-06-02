package tests;



import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import mataf.services.proxy.ProxyReceiveThread;
import mataf.services.proxy.RequestDispatcher;
import mataf.services.proxy.SampleRequestHandler;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ProxyThreadTest {
	
	
	static String readFile(String fname) throws IOException
	{
		String s = null;
		BufferedReader br = new BufferedReader(new FileReader(new File(fname)));
		StringBuffer sb = new StringBuffer();
		while(null !=(s = br.readLine())) {
			sb.append(s);
		}
		return(sb.toString());
		
	}
	
	
	void sendToProxy(DataOutputStream dos) throws Exception
	{
			for(int i=0; i < 3; ++i) {
				DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = fact.newDocumentBuilder();
				Document doc = db.newDocument();
				Element message = doc.createElement("message");
				doc.appendChild(message);
				Element text = doc.createElement("text");
				text.appendChild(doc.createTextNode("Hello world."));
				message.appendChild(text);
				Element timestamp = doc.createElement("timestamp");
				timestamp.appendChild( doc.createTextNode( "text foo"));
				message.appendChild(timestamp);
				TransformerFactory trans_factory=TransformerFactory.newInstance();
				Transformer xml_out = trans_factory.newTransformer();
				Properties props = new Properties();
				props.put("method", "xml");
				props.put("indent", "yes");
				xml_out.setOutputProperties(props);
		
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				xml_out.transform(new DOMSource(doc),
					new StreamResult(bos));
				byte b[] = bos.toByteArray();

				dos.writeInt(b.length);
				dos.write(b,0,b.length);
				dos.flush();
			
			}

	}
	/**
	 * main test.
	 */
	public static void main(String args[])
	{
		try {
			
			RequestDispatcher rd = new RequestDispatcher();
			rd.addRequestHandler("*", new SampleRequestHandler());
			ProxyReceiveThread proxy = new ProxyReceiveThread(9999, rd);
			proxy.start();
		/*
			Socket sock = new Socket("10.11.103.153",9999);
			DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
			DataInputStream dis = new DataInputStream(sock.getInputStream());
			String s = readFile("d:/work/proxy/proxyrequest.xml");
			byte b[] = s.getBytes();
			dos.writeInt(b.length);
			dos.write(b,0,b.length);
			dos.flush();
			int len = dis.readInt();
			b = new byte[len];
			dis.read(b);
			System.out.println("got reply : \n" + new String(b));
			
			dos.close();
			dis.close();
			sock.close();
		*/
			Thread.currentThread().sleep(500000);
			proxy.stopThread();
			System.out.println("stopping thread ..");
		} catch(Exception e) {
			e.printStackTrace();	
			
		}
		
	}
}
