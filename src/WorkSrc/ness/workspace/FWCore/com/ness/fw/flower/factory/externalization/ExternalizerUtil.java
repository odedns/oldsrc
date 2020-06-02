/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ExternalizerUtil.java,v 1.2 2005/03/29 17:44:54 yifat Exp $
 */
package com.ness.fw.flower.factory.externalization;

import java.util.HashMap;
import org.w3c.dom.*;
import com.ness.fw.common.externalization.*;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.flower.core.*;
import com.ness.fw.flower.util.*;
import com.ness.fw.shared.common.SystemConstants;

/**
 * Contains usefull methods for XML parsing
 */
public class ExternalizerUtil
{
	private static final String LOGGER_CONTEXT = FLOWERConstants.LOGGER_CONTEXT + "FACTORY EXT";
	
	public static String getName(Element el)
	{
		return XMLUtil.getAttribute(el, ExternalizerConstants.ATTR_NAME);
	}

    public static String getContext(Element el)
	{
		return XMLUtil.getAttribute(el, ExternalizerConstants.ATTR_CONTEXT);
	}

	private static String getInFormatterName(Element el)
	{
		return XMLUtil.getAttribute(el, ExternalizerConstants.ATTR_IN_FORMATTER);
	}

	private static String getOutFormatterName(Element el)
	{
		return XMLUtil.getAttribute(el, ExternalizerConstants.ATTR_OUT_FORMATTER);
	}

	private static String getValidator(Element el)
	{
		return XMLUtil.getAttribute(el, ExternalizerConstants.ATTR_VALIDATOR);
	}

	public static String getClassName(Element el)
	{
		return XMLUtil.getAttribute(el, ExternalizerConstants.ATTR_CLASS_NAME);
	}

	public static String getMethodName(Element el)
	{
		return XMLUtil.getAttribute(el, ExternalizerConstants.ATTR_METHOD_NAME);
	}

	public static String getType(Element el)
	{
		return XMLUtil.getAttribute(el, ExternalizerConstants.ATTR_TYPE);
	}

	public static String getValue(Element el)
	{
		return XMLUtil.getAttribute(el, ExternalizerConstants.ATTR_VALUE);
	}

	public static int getActivityType(Element el) throws ExternalizationException
	{
		int activityType = SystemConstants.EVENT_TYPE_READWRITE;
		
		// retrieve activity type		
		String activityTypeStr = XMLUtil.getAttribute(el, ExternalizerConstants.ATTR_ACTIVITY_TYPE);
		if(activityTypeStr != null)
		{
			if(activityTypeStr.equals(ExternalizerConstants.ACTIVITY_TYPE_READONLY))
			{
				activityType = SystemConstants.EVENT_TYPE_READONLY;
			}
			else if(! activityTypeStr.equals(ExternalizerConstants.ACTIVITY_TYPE_READWRITE))
			{
				throw new ExternalizationException("Unable to initialize " + el.getNodeName() + " [" + ExternalizerUtil.getName(el) + "]. Illegal activity type [" + activityTypeStr + "]");
			}
		}
		return activityType;

	}

	public static String getAuthId(Element el)
	{
		return XMLUtil.getAttribute(el, ExternalizerConstants.ATTR_AUTH_ID);
	}


	public static ParameterList createParametersList(Element element) throws ExternalizerInitializationException
	{
		ParameterList params = new ParameterList();

		NodeList nodes = XMLUtil.getElementsByTagName(element, ExternalizerConstants.PARAM_TAG_NAME);
		for (int i = 0; i < nodes.getLength(); i++)
		{
			Element el = (Element) nodes.item(i);
			String paramName = ExternalizerUtil.getName(el);
			String paramValue = ExternalizerUtil.getValue(el);
			String paramClassName = ExternalizerUtil.getClassName(el);

			if (paramName == null || paramValue == null || paramClassName == null)
			{
				throw new ExternalizerInitializationException("Unable to create parameter object with name [" + paramName + "] " +
				                                              "one of its fields is omitted");
			}

			Object paramObject;
			try
			{
				paramObject = Class.forName(paramClassName).getConstructor(new Class[]{String.class}).newInstance(new Object[]{paramValue});
			} catch (Throwable ex)
			{
				throw new ExternalizerInitializationException("Unable to create parameter object with name [" + paramName + "]. Error initializing parameter [" + paramName + "]. See Exception:", ex);
			}

            params.addParameter(new Parameter(paramName, paramObject));
		}

		return params;
	}

	public static Formatter createInFormatter(Element element) throws ExternalizationException, ExternalizerNotInitializedException
	{
		return createFormatter(getInFormatterName(element));
	}

	public static Formatter createOutFormatter(Element element) throws ExternalizationException, ExternalizerNotInitializedException
	{
		return createFormatter(getOutFormatterName(element));
	}

	public static SubFlowData createSubFlowData(Element element) throws ExternalizerNotInitializedException, ExternalizationException
	{
		String subFlowName = XMLUtil.getAttribute(element, ExternalizerConstants.ATTR_FLOW_NAME);

		if (subFlowName != null)
		{
			Formatter inFormatter;
			Formatter outFormatter;
			ComplexFormatter complexFormatter = createComplexFormatter(element);

			if (complexFormatter != null)
			{
				inFormatter = complexFormatter.getInFormatter();
				outFormatter = complexFormatter.getOutFormatter();
			}
			else
			{
				inFormatter = createFormatter(XMLUtil.getAttribute(element, ExternalizerConstants.ATTR_SUB_FLOW_IN_FORMATTER));
				outFormatter = createFormatter(XMLUtil.getAttribute(element, ExternalizerConstants.ATTR_SUB_FLOW_OUT_FORMATTER));
			}

			String goodFinalStatesStr = XMLUtil.getAttribute(element, ExternalizerConstants.ATTR_SUB_FLOW_GOOD_FINAL_STATES);
			String formatContextOnInterruptStr = XMLUtil.getAttribute(element, ExternalizerConstants.FLOW_ATTR_FORMAT_CONTEXT_ON_INTERRUPT);
			boolean formatContextOnInterrupt = false;
			if (formatContextOnInterruptStr != null && formatContextOnInterruptStr.equals(ExternalizerConstants.TRUE))
			{
				formatContextOnInterrupt = true;
			}

			return new SubFlowData(
			        subFlowName,
			        inFormatter,
			        outFormatter,
			        goodFinalStatesStr == null ? null : new StringList(goodFinalStatesStr, ExternalizerConstants.GOOD_FINAL_STATES_DELIMITER),
			        formatContextOnInterrupt
					);
		}

		return null;
	}

	public static boolean isExistSubFlowData(Element element) throws ExternalizerNotInitializedException, ExternalizationException
	{
		NodeList subFlowDataNodeList = XMLUtil.getElementsByTagName(element, ExternalizerConstants.SUB_FLOW_DATA_TAG_NAME);
		if (subFlowDataNodeList.getLength() > 0)
			return true;
		return false;
	}


	public static SubFlowDataList createSubFlowDataList(Element element) throws ExternalizerNotInitializedException, ExternalizationException
	{
		NodeList subFlowDataNodeList = XMLUtil.getElementsByTagName(element, ExternalizerConstants.SUB_FLOW_DATA_TAG_NAME);

		if (subFlowDataNodeList.getLength() > 0)
		{
			SubFlowDataList list = new SubFlowDataList();
			for (int i = 0; i < subFlowDataNodeList.getLength(); i++)
			{
				Element flowDataElement = (Element) subFlowDataNodeList.item(i);
				list.addSubFlowData(createSubFlowData(flowDataElement));
			}

			return list;
		}

		return null;
	}

	private static Formatter createFormatter(String formatterName) throws ExternalizerNotInitializedException, ExternalizationException
	{
        Formatter formatter = null;
		if (formatterName != null && formatterName.length() > 0)
		{
			formatter = FormatterExternalizer.getInstance().createFormatter(formatterName);
		}

		return formatter;
	}

	public static ComplexFormatter createComplexFormatter(Element actionElement) throws ExternalizerNotInitializedException, ExternalizationException
	{
		ComplexFormatter formatter = null;
		String complexformatterName = XMLUtil.getAttribute(actionElement, ExternalizerConstants.ATTR_COMPLEX_FORMATTER);

		if (complexformatterName != null && complexformatterName.length() > 0)
		{
			formatter = FormatterExternalizer.getInstance().createComplexFormatter(complexformatterName);
		}

		return formatter;
	}

    public static Validator createValidator(Element element) throws ExternalizerNotInitializedException, ExternalizationException
    {
	    Validator validator = null;
		String validatorStr = getValidator(element);
		if (validatorStr != null && validatorStr.length() > 0)
		{
			validator = ValidatorExternalizer.getInstance().createValidator(validatorStr);
		}

	    return validator;
    }

	public static ActionList readActions(Element element, String actionsTagName, ElementActionLists elementActionLists) throws ExternalizerInitializationException, ExternalizerNotInitializedException, ExternalizationException
	{
		//the list of actions groups (entry or exit actions).
		//Assuming that there is only one group of the type is exists
        NodeList actionsNodeList = XMLUtil.getElementsByTagName(element, actionsTagName);
        if (actionsNodeList.getLength() == 0)
        {
	        return null;
        }

		ActionList actionList = new ActionList();

		//the list of actions in the group
		actionsNodeList = XMLUtil.getElementsByTagName((Element)actionsNodeList.item(0), ExternalizerConstants.ACTION_TAG_NAME);
        for (int i = 0; i < actionsNodeList.getLength(); i++)
        {
	        Element actionElement = (Element) actionsNodeList.item(i);
	        actionList.addAction(readAction(actionElement, elementActionLists));
        }

		return actionList;
	}

	public static ActionList readValidationActions(Element transitionElement) throws ExternalizerInitializationException, ExternalizerNotInitializedException, ExternalizationException
	{
		//the list of actions groups (entry or exit actions).
		//Assuming that there is only one group of the type is exists
		NodeList actionsNodeList = XMLUtil.getElementsByTagName(transitionElement, ExternalizerConstants.TRANSITION_VALIDATION_ACTIONS_TAG_NAME);
		if (actionsNodeList.getLength() == 0)
		{
			return null;
		}

		ActionList actionList = new ActionList();

		//the list of actions in the group
		actionsNodeList = XMLUtil.getElementsByTagName((Element)actionsNodeList.item(0), ExternalizerConstants.VALIDATION_ACTION_TAG_NAME);
		for (int i = 0; i < actionsNodeList.getLength(); i++)
		{
			Element actionElement = (Element) actionsNodeList.item(i);
	        
			Action action = readOperationAction(actionElement, true);
			actionList.addAction(action);
		}

		return actionList;
	}


	public static void readActionLists(Element element, ElementActionLists elementActionLists, int elementLevel) throws ExternalizerInitializationException, ExternalizationException, ExternalizerNotInitializedException
	{
		NodeList actionListsNodeList = XMLUtil.getElementsByTagName(element, ExternalizerConstants.ACTION_LIST_TAG_NAME);
        HashMap actionListMap = new HashMap();
		elementActionLists.setLevelActionLists(elementLevel, actionListMap);

		for (int i = 0; i < actionListsNodeList.getLength(); i++)
		{
			Element actionListElement = (Element) actionListsNodeList.item(i);

			String levelName;
			if(elementLevel == ElementActionLists.FLOW_LEVEL)
				levelName = " flow ";
			else
				levelName = " state ";

			String actionListName = getName(actionListElement);
			if (actionListName == null)
			{
				throw new ExternalizerInitializationException("Action list name should be defined. " + levelName + " [" + getName(element) + "]");
			}
			
			if(actionListMap.containsKey(actionListName))
			{
				Logger.warning(LOGGER_CONTEXT, "Unable to initialize action list [" + actionListName + "] in " + levelName + "[" + getName(element) + "]" + ". A action list is already defined.");
			}
			else
			{
				NodeList actionNodeList = XMLUtil.getElementsByTagName(actionListElement, ExternalizerConstants.ACTION_TAG_NAME);
				ActionList actionList = new ActionList();
				actionListMap.put(actionListName, actionList);
				for (int j = 0; j < actionNodeList.getLength(); j++)
				{
					Element actionElement = (Element) actionNodeList.item(j);
					actionList.addAction(readAction(actionElement, elementActionLists));
				}
			}
		}
	}

	private static Action readAction(Element actionElement, ElementActionLists elementActionLists) throws ExternalizerInitializationException, ExternalizationException, ExternalizerNotInitializedException
	{
        String type = getType(actionElement);
        if (type.equals(ExternalizerConstants.ACTION_TYPE_SERVICE))
        {
            return readServiceAction(actionElement);
        }
		else if (type.equals(ExternalizerConstants.ACTION_TYPE_OPERATION))
		{
			return readOperationAction(actionElement, false);
		}
		else if (type.equals(ExternalizerConstants.ACTION_TYPE_ACTION_LIST))
		{
			return readActionListAction(actionElement, elementActionLists);
		}
		else
		{
			throw new ExternalizerInitializationException("Illegal Action type [" + type + "]");
		}
	}

	private static Action readActionListAction(Element actionElement, ElementActionLists elementActionLists) throws ExternalizerInitializationException
	{
        String actionListName = XMLUtil.getAttribute(actionElement, ExternalizerConstants.ACTION_ATTR_REF_NAME);

		if (actionListName == null)
		{
			throw new ExternalizerInitializationException("Action list name should be specified for action of type actionList.");
		}

		ActionList actionList = elementActionLists.getActionList(actionListName);

		if (actionList == null)
		{
			throw new ExternalizerInitializationException("Unable to initialize Action of type actionList. No action list with name [" + actionListName + "] found.");
		}

		NodeList nodes = XMLUtil.getElementsByTagName(actionElement, ExternalizerConstants.PARAM_TAG_NAME);
		if(nodes.getLength() > 0)
		{
			throw new ExternalizerInitializationException("Unable to initialize action list [" + actionListName + "]. The param tag is not allowed for action of type actionList.");
		}

		return new ActionListAction(actionList);
	}

	private static Action readServiceAction(Element actionElement) throws ExternalizerInitializationException, ExternalizerNotInitializedException, ExternalizationException
	{
        String serviceName = XMLUtil.getAttribute(actionElement, ExternalizerConstants.ACTION_ATTR_REF_NAME);
		if (serviceName == null)
		{
            throw new ExternalizerInitializationException("Unable to initialize Service Action. Service name is omitted.");
		}

		String methodName = XMLUtil.getAttribute(actionElement, ExternalizerConstants.ACTION_ATTR_METHOD_NAME);
		if (methodName == null)
		{
            throw new ExternalizerInitializationException("Unable to initialize Service Action. Method name is omitted.");
		}

		Formatter inFormatter;
		Formatter outFormatter;
		ComplexFormatter complexFormatter = createComplexFormatter(actionElement);

		if (complexFormatter != null)
		{
			inFormatter = complexFormatter.getInFormatter();
			outFormatter = complexFormatter.getOutFormatter();
		}
		else
		{
			inFormatter = createInFormatter(actionElement);
			outFormatter = createOutFormatter(actionElement);
		}

		ParameterList parameterList = createParametersList (actionElement);

        return new ServiceAction(serviceName, methodName, inFormatter, outFormatter, parameterList);
	}

	private static Boolean readStopOnError (Element actionElement)
	{
		Boolean stopValidationOnError = null;
		String stopValidationOnErrorStr = XMLUtil.getAttribute(actionElement, ExternalizerConstants.VALIDATION_ACTION_ATTR_STOP_ON_ERROR);
		if (stopValidationOnErrorStr != null && stopValidationOnErrorStr.equals(ExternalizerConstants.TRUE))
		{
			stopValidationOnError = new Boolean(true);
		}
		return stopValidationOnError;
	}

	private static Action readOperationAction(Element actionElement, boolean validationAction) throws ExternalizerNotInitializedException, ExternalizationException, ExternalizerInitializationException
	{
		String operationName = XMLUtil.getAttribute(actionElement, ExternalizerConstants.ACTION_ATTR_REF_NAME);
		if (operationName == null)
		{
            throw new ExternalizerInitializationException("Unable to initialize Operation Action. Operation name is omitted.");
		}

		String methodName = XMLUtil.getAttribute(actionElement, ExternalizerConstants.ACTION_ATTR_METHOD_NAME);
		if (methodName != null)
		{
			throw new ExternalizerInitializationException("Unable to initialize Operation Action[" + operationName + "]. Method name is not allowed.");
		}

		Formatter inFormatter;
		Formatter outFormatter;
		ComplexFormatter complexFormatter = createComplexFormatter(actionElement);

        if (complexFormatter != null)
        {
	        inFormatter = complexFormatter.getInFormatter();
	        outFormatter = complexFormatter.getOutFormatter();
        }
		else
        {
			inFormatter = createInFormatter(actionElement);
			outFormatter = createOutFormatter(actionElement);
        }

		Operation operation = OperationExternalizer.getInstance().createOperation(operationName);

		ParameterList parameterList = createParametersList (actionElement);
		
		if(validationAction)
		{
			return new ValidationOperationAction(operation, inFormatter, outFormatter, parameterList, readStopOnError(actionElement));
		}
		else
		{
			return new OperationAction(operation, inFormatter, outFormatter, parameterList);
		}
		
	}

}
