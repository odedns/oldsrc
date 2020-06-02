package mataf.conversions;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import javax.xml.parsers.*;
import java.io.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BtConfigReader {

	/**
	 * Constructor for BtConfigReader.
	 */
	private BtConfigReader() {
		super();
	}
	

	/**
	 * read the configuration file specified by fname.
	 * @param fname the config file to read.
	 * @return BtField an array of fields to return.
	 */	
	public static BtConfigData readConfig(String fname, String fTag) throws Exception
	{
	 // Set the ContentHandler...
		 BtConfigHandler handler = new BtConfigHandler(fTag);
		 SAXParserFactory fact = SAXParserFactory.newInstance();
		 SAXParser sp = fact.newSAXParser();
		 sp.parse(new FileInputStream(fname),handler);
		 BtConfigData data = new BtConfigData(handler.getBtFields(), 
			 handler.getEntityInfo());
		return(data);
	}
	

}
