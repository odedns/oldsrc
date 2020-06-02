/*
 * Created on: 15/12/2003
 * Author: yifat har-nof
 * @version $Id: XMLUtil.java,v 1.2 2005/03/14 12:57:43 baruch Exp $
 */
package com.ness.fw.common.externalization;

import org.w3c.dom.*;
import org.xml.sax.*;

import javax.xml.parsers.*;
import java.io.*;
import java.util.*;

/**
 * A Utility class for handling XML files.
 * This class supports various methods for handling XML files.
 */
public class XMLUtil
{
	private static final String CRIMSON = "crimson";
	public static final char BACK_SLASH = '/';
	public static final char SLASH = '\\';

	public static final String XML_PARSER_VERSION_UNKNOWN =
		"XML Parser version is unknown";

	public static final String UNABLE_TO_READ_XML_FILE =
		"Unable to read XML file";

	/**
	 * errorHandler
	 */
	private static ErrorHandler errorHandler = createErrorHandler();

	/**
	 * entityResolver
	 */
	private static EntityResolver entityResolver = createEntityResolver();

	/**
	 * flowerEntityResolver
	 */
	private static EntityResolver flowerEntityResolver =
		new XMLEntityResolver();

	/**
	 * readXML
	 * @param fileName
	 * @param validate
	 * @return Document
	 * @throws XMLUtilException
	 */
	public static Document readXML(String fileName, boolean validate)
		throws XMLUtilException
	{
		try
		{
			DocumentBuilderFactory factory =
				DocumentBuilderFactory.newInstance();
			factory.setValidating(validate);
			factory.setNamespaceAware(false);
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();
			if (documentBuilder.getClass().getName().indexOf(CRIMSON) != -1)
			{
				documentBuilder.setEntityResolver(entityResolver);
			}

			// set the entity resolver. this resolver handles
			// the flower dtd files
			documentBuilder.setEntityResolver(flowerEntityResolver);

			documentBuilder.setErrorHandler(errorHandler);
			int fileSeparatorIndex = fileName.lastIndexOf(BACK_SLASH);
			fileSeparatorIndex =
				fileSeparatorIndex == -1
					? fileName.lastIndexOf(SLASH)
					: fileSeparatorIndex;
			fileSeparatorIndex =
				fileSeparatorIndex == -1
					? fileName.length()
					: fileSeparatorIndex + 1;

			Document doc =
				documentBuilder.parse(
					new FileInputStream(fileName),
					fileName.substring(0, fileSeparatorIndex));

			filterXML(doc.getDocumentElement());
			return doc;
		}
		catch (Throwable ex)
		{
			throw new XMLUtilException(
				UNABLE_TO_READ_XML_FILE + " [" + fileName + "]",
				ex);
		}
	}

	/**
	 * Read XML from String with XML format, and return the generated Document.
	 * @param xmlStr - expected to be in XML format.
	 * @param validate - should validation be done on the file?
	 * @return Document the generated document
	 * @throws XMLUtilException
	 */
	public static Document readXMLFromString(String xmlStr, boolean validate)
		throws XMLUtilException
	{
		return readXMLFromString(xmlStr, validate, false);
	}

	/**
	 * Read XML from String with XML format, and return the generated Document.
	 * @param xmlStr - expected to be in XML format.
	 * @param validate - should validation be done on the file?
	 * @param filterNonElements - if true remove all non elements from the xml
	 * @return Document the generated document
	 * @throws XMLUtilException
	 */

	public static Document readXMLFromString(
		String xmlStr,
		boolean validate,
		boolean filterNonElements)
		throws XMLUtilException
	{
		try
		{
			DocumentBuilderFactory factory =
				DocumentBuilderFactory.newInstance();
			factory.setValidating(validate);
			factory.setNamespaceAware(false);

			DocumentBuilder documentBuilder = factory.newDocumentBuilder();
			documentBuilder.setErrorHandler(new ErrorHandler()
			{
				/**
				 * @param exception
				 */
				public void warning(SAXParseException exception)
					throws SAXException
				{
					throw exception;
				}

				/**
				 * @param exception
				 */
				public void error(SAXParseException exception)
					throws SAXException
				{
					throw exception;
				}

				/**
				 * @param exception
				 */
				public void fatalError(SAXParseException exception)
					throws SAXException
				{
					throw exception;
				}
			});

			InputSource is = new InputSource(new StringReader(xmlStr));
			Document doc = documentBuilder.parse(is);
			if (filterNonElements)
			{
				filterXML(doc.getDocumentElement());
			}
			return doc;
		}
		catch (Throwable e)
		{
			throw new XMLUtilException(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @param element
	 * @param tagName
	 * @return NodeList
	 */
	public static NodeList getElementsByTagName(
		Element element,
		String tagName)
	{
		NodeList nodeList = element.getChildNodes();
		ArrayList nodes = new ArrayList();

		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Element el = (Element) nodeList.item(i);
			if (el.getNodeName().equals(tagName))
			{
				nodes.add(el);
			}
		}

		return new _NodeList(nodes);
	}

	/**
	 * getAttribute
	 * @param element
	 * @param attributeName
	 * @return String
	 */
	public static String getAttribute(Element element, String attributeName)
	{
		String value = element.getAttribute(attributeName);
		return value.length() == 0 ? null : value;
	}

	private static class _NodeList implements NodeList
	{
		/**
		 * ArrayList of nodes.
		 */
		private ArrayList nodes;

		/**
		 * constructor.
		 * @param nodes
		 */
		_NodeList(ArrayList nodes)
		{
			this.nodes = nodes;
		}

		/**
		 * @param index
		 * @return Node
		 */
		public Node item(int index)
		{
			return (Node) nodes.get(index);
		}

		/**
		 * @return int
		 */
		public int getLength()
		{
			return nodes.size();
		}
	}

	/**
	 * 
	 * @param root
	 */
	public static void filterXML(Element root)
	{
		//runs recursively over all the nodes and removes everyting that is not Element
		NodeList children = root.getChildNodes();
		for (int i = 0; i < children.getLength();)
		{
			Node node = children.item(i);

			if (!(node instanceof Element))
			{
				root.removeChild(node);
			}
			else
			{
				filterXML((Element) node);
				i++;
			}
		}
	}

	/**
	 * 
	 * @return ErrorHandler
	 */
	private static ErrorHandler createErrorHandler()
	{
		return new ErrorHandler()
		{
			/**
			 * @param exception
			 */
			public void warning(SAXParseException exception)
				throws SAXException
			{
				throw exception;
			}

			/**
			 * @param exception
			 */
			public void error(SAXParseException exception) throws SAXException
			{
				throw exception;
			}

			/**
			 * @param exception
			 */
			public void fatalError(SAXParseException exception)
				throws SAXException
			{
				throw exception;
			}
		};
	}

	/**
	 * 
	 * @return EntityResolver
	 */
	private static EntityResolver createEntityResolver()
	{
		return new EntityResolver()
		{
			/**
			 * @param publicId
			 * @param systemId
			 * @return InputSource
			 */
			public InputSource resolveEntity(String publicId, String systemId)
				throws SAXException, IOException
			{
				return new InputSource(new FileInputStream(systemId));
			}

		};
	}

	/**
	 * This method is used to retrieve an element's field value. 
	 * @param document - the document from where the information
	 * will be retrieved.
	 * @param fieldName
	 * @return String the received field
	 */
	public String getField(Document document, String fieldName)
		throws XMLUtilException
	{
		String result = "";
		try
		{
			Element element =
				(Element) document.getElementsByTagName(fieldName).item(0);
			if (element != null && element.hasChildNodes())
			{
				result = ((CharacterData) element.getFirstChild()).getData();
			}

		}
		catch (DOMException ex)
		{
			throw new XMLUtilException(ex.getMessage(), ex);
		}
//		finally
//		{
//			return result;
//		}
		return result;
	}

}
