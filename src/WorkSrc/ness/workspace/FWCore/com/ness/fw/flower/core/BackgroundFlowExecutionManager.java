/*
 * Created on: 04/05/2005
 * Author: yifat har-nof
 * @version $Id: BackgroundFlowExecutionManager.java,v 1.2 2005/05/08 13:45:56 yifat Exp $
 */
package com.ness.fw.flower.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.ness.fw.common.auth.UserAuthData;
import com.ness.fw.common.auth.UserAuthDataFactory;
import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.exceptions.GeneralException;
import com.ness.fw.common.resources.SystemResources;
import com.ness.fw.flower.common.LanguagesManager;
import com.ness.fw.flower.factory.FlowElementsFactory;
import com.ness.fw.flower.factory.FlowElementsFactoryException;
import com.ness.fw.flower.util.DynamicGlobals;
import com.ness.fw.flower.util.StringList;

/**
 * Responsible for executing a Background flows, with input & output parameters. 
 */
public class BackgroundFlowExecutionManager
{
	/**
	 * String used as context for all logger prints
	 */
	public static final String LOGGER_CONTEXT =
		FLOWERConstants.LOGGER_CONTEXT + "BACKGROUND ";

	public static final String BACKGROUND_FLOW_CONTEXT = "FWBackgroundFlowCtx";
	public static final String BACKGROUND_FLOW_FINAL_STATE = "FinalOK";
	public static final String BACKGROUND_FLOW_SESSION_ID =
		"BackgroundFlowSessionId";
	public static final String OBJECT_CLASS = "java.lang.Object";
	public static final String BACKGROUND_FLOW_IN_STR = "FWBackgroundFlowInSTR";
	public static final String BACKGROUND_FLOW_OUT_STR =
		"FWBackgroundFlowOutSTR";

	/**
	 * Execute the flow on the background, 
	 * format the input parameters into the flow context,
	 * and in the end return the output parameters as requested, 
	 * only if the flow finished with the {@link BACKGROUND_FLOW_FINAL_STATE}.   
	 * 
	 * @param flowName The name of the flow to run.
	 * @param inputParameters A Map with the input parameters to format into 
	 * the flow context.
	 * @param outputParametersNames A List with the output parameters names, 
	 * to return when the flow is finished. 
	 * @return Map A map with the requested output values.
	 */
	public static Map executeBackgroundFlow(
		String flowName,
		Map inputParameters,
		List outputParametersNames)
		throws
			FlowElementsFactoryException,
			BackgroundFlowException,
			ValidationException
	{
		StringList goodFinalStates = new StringList();
		goodFinalStates.addString(BACKGROUND_FLOW_FINAL_STATE);
		return executeBackgroundFlow(
			flowName,
			inputParameters,
			outputParametersNames,
			goodFinalStates);
	}

	/**
	 * Execute the flow on the background, 
	 * format the input parameters into the flow context,
	 * and in the end return the output parameters as requested, 
	 * only if the flow finished with one of the given goodFinalStates.   
	 * 
	 * @param flowName The name of the flow to run.
	 * @param inputParameters A Map with the input parameters to format into 
	 * the flow context.
	 * @param outputParametersNames A List with the output parameters names, 
	 * to return when the flow is finished. 
	 * @param goodFinalStates A StringList with the names of the good final states.
	 * @return Map A map with the requested output values.
	 */
	public static Map executeBackgroundFlow(
		String flowName,
		Map inputParameters,
		List outputParametersNames,
		StringList goodFinalStates)
		throws
			BackgroundFlowException,
			ValidationException
	{
		Map outputParameters = null;
		ResultEvent resultEvent = null;
		Flow flow = null;
		Context context = null;

		if (SystemResources.getInstance().isDebugMode())
		{
			StringBuffer sb = new StringBuffer(256);
			sb.append("start runBackgroundFlow, flow name: ");
			sb.append(flowName);
			sb.append(", Input parameters: ");
			sb.append(inputParameters);
			sb.append(", Output parameters names: ");
			for (int i = 0;
				outputParametersNames != null
					&& i < outputParametersNames.size();
				i++)
			{
				sb.append(outputParametersNames.get(i));
				sb.append(", ");
			}
		}

		// create the flow instance
		try
		{
			flow = FlowElementsFactory.getInstance().createFlow(flowName);
		}
		catch (GeneralException ex)
		{
			throw new BackgroundFlowException(
				"Unable to Create flow with name [" + flowName + "]",
				ex);
		}

		try
		{
			// creates the UserAuthData without a specific user.
			Locale locale = LanguagesManager.getLocaleByDefaultLanguageSet();
			UserAuthData userAuthData =
				UserAuthDataFactory.getEmptyUserAuthData();
				
			// creates the DynamicGlobals
			DynamicGlobals dynamicGlobals =
				new DynamicGlobals(
					BACKGROUND_FLOW_SESSION_ID,
					locale,
					userAuthData);

			// get the static parent context for the new dynamic context.
			Context parentContext =
				FlowElementsFactory.getInstance().getFirstStaticContext();
			// creates the dynamic context for the output formatter.
			context =
				FlowElementsFactory.getInstance().createContext(
					BACKGROUND_FLOW_CONTEXT,
					parentContext,
					dynamicGlobals);

			// creates the output structure and add in the context.
			setOutputStructure(outputParametersNames, context);

			// creates the input formatter (format the values as is, with "fromValue").
			Formatter inFormatter = getInputFormatter(inputParameters);
			// creates the output formatter (format the vlaues from the flow context 
			// into the output structure) 
			Formatter outFormatter = getOutputFormatter(outputParametersNames);

			SubFlowData subFlowData =
				new SubFlowData(
					flowName,
					inFormatter,
					outFormatter,
					goodFinalStates,
					false);

			// running the flow
			resultEvent =
				flow.initiate(
					null,
					context,
					"",
					subFlowData,
					dynamicGlobals,
					false);

		}
		catch (GeneralException e)
		{
			throw new BackgroundFlowException(
				"error initializing background flow",
				e);
		}

		if (resultEvent.getExceptionsCount() > 0)
		{
			throw new BackgroundFlowException(
				"error running background flow",
				resultEvent.getException(0));
		}
		else if (resultEvent.isValidationExceptionThrown())
		{
			throw new ValidationException();
		}
		else
		{
			// get output values
			try
			{
				outputParameters =
					getOutputParameters(context, outputParametersNames);
			}
			catch (ContextException e)
			{
				throw new BackgroundFlowException(
					"error running background flow",
					e);
			}
		}

		return outputParameters;
	}

	/**
	 * Return the structure field name.
	 * @param structureName
	 * @param fieldName
	 * @return String name
	 */
	private static String getStructureFieldName(
		String structureName,
		String fieldName)
	{
		return structureName
			+ ContextStructureField.STRUCTURE_NAME_SEPARATOR
			+ fieldName;
	}

	/**
	 * creates the output structure and add in the context.
	 * @param outputParametersNames
	 * @param context
	 * @throws ContextException
	 * @throws FatalException
	 * @throws AuthorizationException
	 */
	private static void setOutputStructure(
		List outputParametersNames,
		Context context)
		throws ContextException, FatalException, AuthorizationException
	{
		if (outputParametersNames != null && outputParametersNames.size() > 0)
		{
			DynamicStructure outputStructure =
				new DynamicStructure(
					BACKGROUND_FLOW_OUT_STR,
					BACKGROUND_FLOW_OUT_STR,
					DynamicStructure.CHILD_ACCESS_FULL);
			for (int i = 0; i < outputParametersNames.size(); i++)
			{
				String fieldName = (String) outputParametersNames.get(i);
				outputStructure.addField(fieldName, OBJECT_CLASS);
			}
			context.addDynamicStructure(outputStructure);
		}
	}

	/**
	 * creates the input formatter (format the values as is, with "fromValue").
	 * @param inputParameters
	 * @return Formatter
	 */
	private static Formatter getInputFormatter(Map inputParameters)
	{
		FormatterImpl formatter = null;
		if (inputParameters != null)
		{
			formatter = new FormatterImpl();

			Iterator inParamsIterator = inputParameters.keySet().iterator();
			while (inParamsIterator.hasNext())
			{
				String fieldName = (String) inParamsIterator.next();
				Object value = inputParameters.get(fieldName);
				formatter.addFormatterEntry(null, value, fieldName);
			}
		}
		return formatter;
	}

	/**
	 * creates the output formatter (format the vlaues from the flow context 
	 * into the output structure) 
	 * @param outputParametersNames
	 * @return Formatter
	 */
	private static Formatter getOutputFormatter(List outputParametersNames)
	{
		FormatterImpl formatter = null;
		if (outputParametersNames != null && outputParametersNames.size() > 0)
		{
			formatter = new FormatterImpl();

			for (int i = 0; i < outputParametersNames.size(); i++)
			{
				String fieldName = (String) outputParametersNames.get(i);
				formatter.addFormatterEntry(
					fieldName,
					null,
					getStructureFieldName(BACKGROUND_FLOW_OUT_STR, fieldName));
			}
		}
		return formatter;
	}

	/**
	 * Returns a map with the output values
	 * @param context
	 * @param outputParametersNames
	 * @return Map
	 * @throws ContextException
	 */
	private static Map getOutputParameters(
		Context context,
		List outputParametersNames)
		throws ContextException
	{
		Map outputParameters = null;
		if (outputParametersNames != null && outputParametersNames.size() > 0)
		{
			outputParameters = new HashMap();
			for (int i = 0; i < outputParametersNames.size(); i++)
			{
				String fieldName = (String) outputParametersNames.get(i);
				Object value =
					context.getField(
						getStructureFieldName(
							BACKGROUND_FLOW_OUT_STR,
							fieldName));
				outputParameters.put(fieldName, value);
			}
		}
		return outputParameters;
	}
}
