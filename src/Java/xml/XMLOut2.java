

import javax.xml.parsers.*;
import org.w3c.dom.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import weblogic.apache.xml.serialize.*;

import java.io.*;
import java.util.*;


public class XMLOut2 {

	public static void main(String argv[])
	{

		try {
			writeOut();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}


	static void writeOut() throws Exception
	{

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
		/*
		Properties p = xml_out.getOutputProperties();
		System.out.println("p = " + p.toString());
		*/
		xml_out.transform(new DOMSource(doc),
				new StreamResult(System.out));


/*


		OutputFormat of = new OutputFormat();
		of.setEncoding("UTF-8");
		of.setLineWidth(50);
		of.setIndent(4);
		of.setIndenting(true);


		 XMLSerializer xs = new XMLSerializer(System.out, of);
		 xs.serialize(doc);
		 */

	}
}

