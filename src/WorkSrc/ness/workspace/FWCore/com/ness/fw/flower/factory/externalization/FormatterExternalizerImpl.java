/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: FormatterExternalizerImpl.java,v 1.1 2005/02/21 15:07:10 baruch Exp $
 */
package com.ness.fw.flower.factory.externalization;

import com.ness.fw.flower.core.*;
import com.ness.fw.flower.factory.*;
import com.ness.fw.common.externalization.*;
import com.ness.fw.common.logger.*;
import java.util.*;
import org.w3c.dom.*;

public class FormatterExternalizerImpl extends FormatterExternalizer
{
	private static final String LOGGER_CONTEXT = FlowElementsFactoryImpl.LOGGER_CONTEXT + " FORMATTER EXT.";

	private HashMap formatters;
	private HashMap complexFormatters;

	public FormatterExternalizerImpl(DOMRepository domRepository)
	{
		formatters = new HashMap();
		complexFormatters = new HashMap();
        DOMList domList = domRepository.getDOMList(ExternalizerConstants.FORMATTER_TAG_NAME);

		if (domList != null)
		{
			for (int i = 0; i < domList.getDocumentCount(); i++)
			{
				Document doc = domList.getDocument(i);
				processDOM(doc);
			}
		}
	}

	public Formatter createFormatter(String formatterName) throws ExternalizationException
	{
		Object res = formatters.get(formatterName);

		if (res == null)
		{
			throw new ExternalizationException("No simple / custom formatter is defined with name [" + formatterName + "]");
		}

		return (Formatter) res;
	}

	public ComplexFormatter createComplexFormatter(String name) throws ExternalizationException
	{
		Object res = complexFormatters.get(name);

		if (res == null)
		{
			throw new ExternalizationException("No complex formatter is defined with name [" + name + "]");
		}

		return (ComplexFormatter) res;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Private methods
	//
	///////////////////////////////////////////////////////////////////////////////////////////////////
	private void processDOM(Document doc)
	{
		Element rootElement = doc.getDocumentElement();

		NodeList nodes = XMLUtil.getElementsByTagName(rootElement, ExternalizerConstants.FORMATTER_TAG_NAME);

		for (int i = 0; i < nodes.getLength(); i++)
		{
			Element element = (Element) nodes.item(i);
			try
			{
				processFormatter(element);
			} catch (Throwable ex)
			{
				Logger.error(LOGGER_CONTEXT, "Error initializing formatter. See exception. Continue to initialize other formatters");
				Logger.error(LOGGER_CONTEXT, ex);
			}
		}
	}

	private void processFormatter(Element element) throws ExternalizerInitializationException
	{
        String formatterType = ExternalizerUtil.getType(element);
		String name = ExternalizerUtil.getName(element);

		Logger.debug(LOGGER_CONTEXT, "Loading formatter: [" + name + "]");

		if (ExternalizerConstants.FORMATTER_TYPE_SIMPLE.equals(formatterType))
		{
			if(formatters.containsKey(name))
			{
				Logger.warning(LOGGER_CONTEXT, "Unable to initialize simple formatter [" + name + "]. A formatter with that name is already defined.");
			}
			else
			{
				Formatter formatter = createSimpleFormatter(element, name);
				formatters.put(name, formatter);
			}
		}
		else if (ExternalizerConstants.FORMATTER_TYPE_CUSTOM.equals(formatterType))
		{
			if(formatters.containsKey(name))
			{
				Logger.warning(LOGGER_CONTEXT, "Unable to initialize custom formatter [" + name + "]. A formatter with that name is already defined.");
			}
			else
			{
				Formatter formatter = createCustomFormatter(element, name);
				formatters.put(name, formatter);
			}
		}
		else if (ExternalizerConstants.FORMATTER_TYPE_COMPLEX.equals(formatterType))
		{
			if(complexFormatters.containsKey(name))
			{
				Logger.warning(LOGGER_CONTEXT, "Unable to initialize complex formatter [" + name + "]. A formatter with that name is already defined.");
			}
			else
			{
				ComplexFormatter formatter = readComplexFormatter(element, name);
				complexFormatters.put(name, formatter);
			}
		}
		else
		{
			throw new ExternalizerInitializationException("Illegal formatter type for Formatter with name [" + ExternalizerUtil.getName(element) + "]");
		}
	}

	private ComplexFormatter readComplexFormatter(Element element, String name) throws ExternalizerInitializationException
	{
		//input formatter element
		NodeList nodeList = XMLUtil.getElementsByTagName(element, ExternalizerConstants.IN_FORMATTER_TAG_NAME);
		if (nodeList.getLength() == 0)
		{
			throw new ExternalizerInitializationException("Unable to read complex formatter [" + name + "]. Input formatter is missing");
		}

		Element formatterEl = (Element)nodeList.item(0);
		//input formatter type
		String formatterType = ExternalizerUtil.getType(formatterEl);
		Formatter inFormatter, outFormatter;

		//read input formatter
		if (ExternalizerConstants.FORMATTER_TYPE_SIMPLE.equals(formatterType))
		{
			inFormatter = createSimpleFormatter(formatterEl, name);
		}
		else if (ExternalizerConstants.FORMATTER_TYPE_CUSTOM.equals(formatterType))
		{
			inFormatter = createCustomFormatter(formatterEl, name);
		}
		else
		{
			throw new ExternalizerInitializationException("Illegal formatter type for Formatter with name [" + ExternalizerUtil.getName(element) + "]");
		}

		//output formatter element
		nodeList = XMLUtil.getElementsByTagName(element, ExternalizerConstants.OUT_FORMATTER_TAG_NAME);

		if (nodeList.getLength() == 0)
		{
			throw new ExternalizerInitializationException("Unable to read complex formatter [" + name + "]. Output formatter is missing");
		}
		formatterEl = (Element) nodeList.item(0);
		//output formatter type
		formatterType = ExternalizerUtil.getType(formatterEl);

		//read output formatter
		if (ExternalizerConstants.FORMATTER_TYPE_SIMPLE.equals(formatterType))
		{
			outFormatter = createSimpleFormatter(formatterEl, name);
		}
		else if (ExternalizerConstants.FORMATTER_TYPE_CUSTOM.equals(formatterType))
		{
			outFormatter = createCustomFormatter(formatterEl, name);
		}
		else
		{
			throw new ExternalizerInitializationException("Illegal formatter type for Formatter with name [" + ExternalizerUtil.getName(element) + "]");
		}

		return new ComplexFormatter(name, inFormatter, outFormatter);
	}

	private Formatter createCustomFormatter(Element element, String name) throws ExternalizerInitializationException
	{
		String className = ExternalizerUtil.getClassName(element);

		Formatter formatter;
		try
		{
			formatter = (Formatter) Class.forName(className).newInstance();

			formatter.setName(name);

			formatter.initialize(ExternalizerUtil.createParametersList(element));
		} catch (Throwable e)
		{
			throw new ExternalizerInitializationException("Unable to create formatter instance for formatter [" + name + "]", e);
		}

		return formatter;
	}

	private Formatter createSimpleFormatter(Element element, String name) throws ExternalizerInitializationException
	{

        FormatterImpl formatter = new FormatterImpl();
		formatter.setName(name);

		NodeList nodes = XMLUtil.getElementsByTagName(element, ExternalizerConstants.FORMATTER_FORMAT_TAG_NAME);
		for (int i = 0; i < nodes.getLength(); i++)
		{
			Element el = (Element) nodes.item(i);
			String fromName = XMLUtil.getAttribute(el, ExternalizerConstants.FORMATTER_ATTR_FROM_NAME);
			Object fromValue = XMLUtil.getAttribute(el, ExternalizerConstants.FORMATTER_ATTR_FROM_VALUE);
			String toName = XMLUtil.getAttribute(el, ExternalizerConstants.FORMATTER_ATTR_TO_NAME);

			if (fromName == null && fromValue == null)
			{
				throw new ExternalizerInitializationException("Unable to read formatter with name [" + name + "]. Both " + ExternalizerConstants.FORMATTER_ATTR_FROM_NAME + " and " + ExternalizerConstants.FORMATTER_ATTR_FROM_VALUE + " can not be omitted.");
			}

			if (fromName != null && fromValue != null)
			{
				throw new ExternalizerInitializationException("Unable to read formatter with name [" + name + "]. Both " + ExternalizerConstants.FORMATTER_ATTR_FROM_NAME + " and " + ExternalizerConstants.FORMATTER_ATTR_FROM_VALUE + " was defined.");
			}

			if (toName == null)
			{
				throw new ExternalizerInitializationException("Unable to read formatter with name [" + name + "]. " + ExternalizerConstants.FORMATTER_ATTR_TO_NAME + " can not be omitted");
			}

			formatter.addFormatterEntry(fromName, fromValue, toName);
		}

		return formatter;
	}
}
