package hoshen.ejb.dynproxy;

import hoshen.common.utils.xstream.XStreamFacade;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;

/**
 * @author yakovl
 *  
 */

public class XStreamArgsHandler implements ArgsHandlerIF {

	private static Logger log = Logger.getLogger(XStreamArgsHandler.class);
	private static Pattern stackTracePattern = Pattern.compile("<(stackTrace|detailMessage)>.*?</\0>", Pattern.DOTALL);

	private static XStream xstream = XStreamFacade.getXStreamFacade();

	/*
	 * (non-Javadoc)
	 * 
	 * @see hoshen.common.proxy.ArgsHandlerIF#unpackArgs(java.lang.Object)
	 */
	public Object unpackArgs(Object args) {
		String value = null;
		// defensive programming: SOAPElement isn't present nor is necessary on IKVM.
//		try {
//			Class soapElementClass = Class.forName("javax.xml.soap.SOAPElement");
//			Method getValueMethod = soapElementClass.getMethod("getValue", null);
//			value = (String)getValueMethod.invoke(args, null);
//		} catch (Throwable t) {}
		if (value == null) {
			value = args.toString();
		}
		
		log.debug("Deserializing using XStream.");
		return xstream.fromXML(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hoshen.common.proxy.ArgsHandlerIF#packArgs(java.lang.Object)
	 */
	public Object packArgs(Object args) {
		log.debug("Serializing using XStream.");
		String result = xstream.toXML(args);
		if (args instanceof Throwable) {
//			Matcher matcher = stackTracePattern.matcher(result);
//			result = matcher.replaceAll("");
		}
		return result;
	}
}