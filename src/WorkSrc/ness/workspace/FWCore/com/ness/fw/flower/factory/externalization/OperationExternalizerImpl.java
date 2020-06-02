/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: OperationExternalizerImpl.java,v 1.1 2005/02/21 15:07:09 baruch Exp $
 */
package com.ness.fw.flower.factory.externalization;

import com.ness.fw.flower.core.*;
import com.ness.fw.flower.factory.*;
import com.ness.fw.common.externalization.*;
import com.ness.fw.common.logger.*;
import org.w3c.dom.*;

import java.util.*;

public class OperationExternalizerImpl extends OperationExternalizer
{
	private static final String LOGGER_CONTEXT = FlowElementsFactoryImpl.LOGGER_CONTEXT + " OPERATION EXT.";

	private HashMap operations;

	public OperationExternalizerImpl(DOMRepository domRepository)
	{
		operations = new HashMap();

        DOMList domList = domRepository.getDOMList(ExternalizerConstants.OPERATION_TAG_NAME);

		if (domList != null)
		{
			for (int i = 0; i < domList.getDocumentCount(); i++)
			{
				Document doc = domList.getDocument(i);
				processDOM(doc);
			}
		}
	}

	public Operation createOperation(String operationName) throws ExternalizationException
	{
		Object res = operations.get(operationName);

		if (res == null)
		{
			throw new ExternalizationException("No operation is defined with name [" + operationName + "]");
		}

		return (Operation) res;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Private methods
	//
	///////////////////////////////////////////////////////////////////////////////////////////////////
	private void processDOM(Document doc)
	{
		Element rootElement = doc.getDocumentElement();

		NodeList nodes = XMLUtil.getElementsByTagName(rootElement, ExternalizerConstants.OPERATION_TAG_NAME);

		for (int i = 0; i < nodes.getLength(); i++)
		{
			Element element = (Element) nodes.item(i);
			try
			{
				processOperation(element);
			} catch (Throwable ex)
			{
				Logger.error(LOGGER_CONTEXT, "Error initializing operation. See exception. Continue to initialize other operations");
				Logger.error(LOGGER_CONTEXT, ex);
			}
		}
	}

	private void processOperation(Element element) throws ExternalizerNotInitializedException, ExternalizationException, ClassNotFoundException, IllegalAccessException, InstantiationException, ExternalizerInitializationException
	{
        String name = ExternalizerUtil.getName(element);

		if(operations.containsKey(name))
		{
			Logger.warning(LOGGER_CONTEXT, "Unable to initialize operation [" + name + "]. A operation with that name is already defined.");
		}
		else
		{
			String className = ExternalizerUtil.getClassName(element);
	
			Logger.debug(LOGGER_CONTEXT, "Loading operation with name [" + name + "] and class name [" + className + "]");
	
			int activityType = ExternalizerUtil.getActivityType(element);
	
			String contextName = XMLUtil.getAttribute(element, ExternalizerConstants.ATTR_CONTEXT);
	
	        Validator validator = ExternalizerUtil.createValidator(element);
	
			Operation operation = (Operation) Class.forName(className).newInstance();
	        operation.setName(name);
	        operation.setContextName(contextName);
			operation.setValidator(validator);
			operation.setActivityType(activityType);
	
			operations.put(name,  operation);
		}
	}
}
