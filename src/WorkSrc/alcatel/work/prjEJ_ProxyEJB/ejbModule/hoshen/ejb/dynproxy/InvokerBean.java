package hoshen.ejb.dynproxy;

import hoshen.common.utils.exception.HoshenRuntimeException;
import hoshen.common.utils.xstream.XStreamFacade;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.log4j.Logger;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;

/**
 * Bean implementation class for Enterprise Bean: Invoker
 */
public class InvokerBean implements javax.ejb.SessionBean {
	private static final FastDateFormat FORMAT = FastDateFormat.getInstance("yyyyMMdd'T'HHmmssZZ");

	private static final boolean TRACE = Boolean.getBoolean("hoshen.ejb.dynproxy.InvokerBean.TRACE");

	private javax.ejb.SessionContext mySessionCtx;

	private static Logger log = Logger.getLogger(InvokerBean.class);

	/**
	 * getSessionContext
	 */
	public javax.ejb.SessionContext getSessionContext() {
		return mySessionCtx;
	}

	/**
	 * setSessionContext
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) {
		mySessionCtx = ctx;
	}

	/**
	 * ejbCreate
	 */
	@SuppressWarnings("unused")
	public void ejbCreate() throws javax.ejb.CreateException {
	}

	/**
	 * ejbActivate
	 */
	public void ejbActivate() {
	}

	/**
	 * ejbPassivate
	 */
	public void ejbPassivate() {
	}

	/**
	 * ejbRemove
	 */
	public void ejbRemove() {
	}

	/**
	 * @author yakovl invoke a method on the specific object.
	 * 
	 * @param c
	 * @param methodName
	 * @param args
	 * @throws InvocationTargetException
	 *             invocation failure
	 * @return
	 */
	public Object invoke(String className, String methodName,
			List paramsStrings, Object[] args,
			String identity) throws InvocationTargetException
	{
		Identity.setIdentity(identity);
		
		if (className == null)
		{
			throw new HoshenRuntimeException("className is null");
		}
		if (methodName == null) {
			throw new HoshenRuntimeException("methodName is null");
		}

		List paramsList = ClassUtilsHelper
				.convertClassNamesToClasses(paramsStrings);
		Class[] params = (Class[]) paramsList.toArray(new Class[0]);
		// resolveParams(params);
		// javax.rmi.PortableRemoteObject
		// .narrow(, InvokerHome.class)
		log.debug("executing method on class :" + className);
		Object result = null;
		Method m = null;
		try {
			Class c = Class.forName(className);
			// Method m = findMatchingMethod(c, method.getName(),
			// method.getParameterTypes());
			m = c.getMethod(methodName, params);
			Object obj = c.newInstance();
			result = m.invoke(obj, args);
		} catch (Throwable throwable) {
			if (throwable instanceof InvocationTargetException) {
				log.error("An invocation exception has occured at " + className
						+ "#" + ((m != null) ? m.getName() : "method is null")
						+ ": ", throwable.getCause());
				getSessionContext().setRollbackOnly();
				throw (InvocationTargetException)throwable;
			} else
			{
				log.error(
					"An exception has occured at " + className + "#"
							+ ((m != null) ? m.getName() : "method is null")
							+ ": ", throwable);
				throw new HoshenRuntimeException("Invocation failed:",
						throwable);
			}
		}
		if (result != null) {
			log.debug("returning result = " + result.toString());
		}
		return (result);
	}

	/**
	 * This method searches a class for a method of a specified name whose
	 * parameters are assignable from the supplied arguments. Methods of
	 * superclasses are also taken into account.
	 * 
	 * This method was the previous means of finding the method to execute.
	 * It remains here in case we want to fallback.
	 * 
	 * @deprecated
	 * @author nadavw
	 * @param cls
	 *            the class in which to look for a method
	 * @param methodName
	 *            the name of a method to look for in a class
	 * @param args
	 *            arguments against which to look for a method
	 * @return a matching method
	 * @throws NoSuchMethodException
	 *             thrown if no matching method is found
	 * 
	 */
	private Method findMatchingMethod(Class cls, String methodName,
			Class[] params) throws NoSuchMethodException {
		Method[] declaredMethods = cls.getDeclaredMethods();
		for (int i = 0; i < declaredMethods.length; i++) {
			Method method = declaredMethods[i];
			if (!method.getName().equals(methodName)) {
				continue;
			}
			if (checkMethodMatch(method, params)) {
				return method;
			}
		}

		throw new NoSuchMethodException("No method " + cls.getName() + "#"
				+ methodName + "() was found " + "with the given arguments.");
	}

	/**
	 * This method checks to see if the given method's parameters are assignable
	 * from the given arguments.
	 * 
	 * @author nadavw
	 * @param method
	 *            method to examine
	 * @param args
	 *            arguments to match
	 * @return true if the method matches, false if it doesn't
	 */
	private boolean checkMethodMatch(Method method, Class[] params) {
		Class[] parameterTypes = method.getParameterTypes();

		// if no arguments are taken, return true.
		if (params == null && parameterTypes.length == 0) {
			return true;
		}

		if ((params == null) && (parameterTypes.length > 0)) {
			return false;
		}
		// ensure the number of parameters matches .
		if (params.length != parameterTypes.length) {
			log.debug("in method=" + method.getName() + " params.length="
					+ params.length + "parameterTypes.length="
					+ parameterTypes.length);
			return false;
		}

		for (int i = 0; i < parameterTypes.length; i++) {
			Class paramType = parameterTypes[i];
			// case the argument is null, continue to the next argument
			if (params[i] != null) {
				// //check if the argument is primitive
				// if argType.
				if (!paramType.isAssignableFrom(params[i])) {
					log.debug(method.getName()
							+ "() found incompatible at argument #" + (i + 1));
					return false;
				}
			}

		}
		return true;
	}

	private static final XStream xstream = XStreamFacade.getXStreamFacade();

	/**
	 * wraps the other <code>invoke()</code> method in this bean. meant to be
	 * used by a web service.
	 * 
	 * @param c
	 * @param methodName
	 * @param args
	 * @return
	 */
	public String invoke(String className, String methodName, String params,
			String args, String identity) {
		Object[] argsArray;

		if (TRACE) {
			try {
				writeToFile(className + "#" + methodName + " request", args);
			} catch (IOException e) {
				log.error("Failed to write request.", e);
			}
		}

       argsArray = (Object[]) xstream.fromXML(args);

		Class[] paramsArray = (Class[]) xstream.fromXML(params);
		String result;
		Method m = null;
		try {
			List paramsList = ClassUtilsHelper
					.convertClassesToClassNames(Arrays.asList(paramsArray));
			Object invocationResult = invoke(className, methodName, paramsList,
				argsArray, identity);
			result = xstream.toXML(invocationResult);
		} catch (InvocationTargetException invocationTargetException) {
			Throwable cause = invocationTargetException.getCause();
			log.error("An invocation exception has occured at " + className
					+ "#" + ((m != null) ? m.getName() : "method is null")
					+ ": ", cause);
			result = xstream.toXML(cause);
			// TBD: This patch enables us to send dumbed down exceptions to
			// IKVM.
			// Hopefully to be fixed in a future release.
			// Previous hack looked like this:
			// result = result.replaceAll(
			// "(?s)<(detailMessage)>.*?</detailMessage>", "");
			// Note that we can reasonably ignore the exceptions thrown here,
			// since we already are doing a best effort to report a failure.
			try {
				DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder documentBuilder = documentBuilderFactory
						.newDocumentBuilder();
				Reader reader = new StringReader(result);
				InputSource source = new InputSource(reader);
				Document document = documentBuilder.parse(source);
				removeElements(document, "//cause");
				removeElements(document, "//detailMessage");
				removeElements(document, "//stackTrace");
				DOMSource source2 = new DOMSource(document);
				StringWriter writer = new StringWriter();
				StreamResult streamResult = new StreamResult(writer);
				TransformerFactory transformerFactory = TransformerFactory
						.newInstance();
				Transformer serializer = transformerFactory.newTransformer();
				serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				serializer.transform(source2, streamResult);
				result = writer.toString();
			} catch (SAXException e) {
				log.error("Problem encountered while serializing an exception",
						e);
			} catch (IOException e) {
				log.error("Problem encountered while serializing an exception",
						e);
			} catch (ParserConfigurationException e) {
				log.error("Problem encountered while serializing an exception",
						e);
			} catch (TransformerException e) {
				log.error("Problem encountered while serializing an exception",
						e);
			}
		}

		// log xml data to file
		if (TRACE) {
			try {
				writeToFile(className + "#" + methodName + " result", result);
			} catch (IOException e)
			{
				log.error("Failed to write response.", e);
			}
		}

		return result;
	}

	private void writeToFile(String filename, String contents)
			throws IOException {
		String date = FORMAT.format(new Date());
		
		FileWriter fileWriter = new FileWriter(filename + "#" + date + ".xml");
		fileWriter.write(contents);
		fileWriter.close();
	}

	/**
	 * @param document
	 * @param filter
	 * @throws TransformerException
	 */
	private void removeElements(Document document, String filter)
			throws TransformerException {
		NodeList nodes = XPathAPI.selectNodeList(document, filter);
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			node.getParentNode().removeChild(node);
		}
	}

}
