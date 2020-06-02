/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: FlowExternalizerImpl.java,v 1.4 2005/04/13 08:53:09 yifat Exp $
 */
package com.ness.fw.flower.factory.externalization;

import com.ness.fw.flower.core.*;
import com.ness.fw.flower.factory.*;
import com.ness.fw.util.StringFormatterUtil;
import com.ness.fw.common.externalization.*;
import com.ness.fw.common.logger.*;
import org.w3c.dom.*;

import java.util.*;
import java.lang.reflect.*;

/**
 * Factory used to parse XML and create <code>Flow</code> instances
 */
public class FlowExternalizerImpl extends FlowExternalizer
{
	/**
	 * The logger context
	 */
	private static final String LOGGER_CONTEXT = FlowElementsFactoryImpl.LOGGER_CONTEXT + " FLOW EXT.";

	public static final String DISPATCHER_STATE_NAME = "DISPATCHER";

	private HashMap flowDefinitions;
	private HashMap customGuards;
	private HashMap activities;
	private HashMap implementations;
	private HashMap buttonSets;

	public FlowExternalizerImpl(DOMRepository domRepository) throws ExternalizerInitializationException
	{
		flowDefinitions = new HashMap();
		customGuards = new HashMap();
		activities = new HashMap();
		implementations = new HashMap();
		buttonSets = new HashMap();

		//read custom guards
		DOMList domList = domRepository.getDOMList(ExternalizerConstants.GUARD_DEFINITION_TAG_NAME);
		if (domList != null)
		{
			for (int i = 0; i < domList.getDocumentCount(); i++)
			{
				Document doc = domList.getDocument(i);

				processCustomGuardsDOM(doc);
			}
		}

		//read activities
		domList = domRepository.getDOMList(ExternalizerConstants.ACTIVITY_TAG_NAME);
		if (domList != null)
		{
			for (int i = 0; i < domList.getDocumentCount(); i++)
			{
				Document doc = domList.getDocument(i);

				processActivitiesDOM(doc);
			}
		}

		//read Flow Implementations
		domList = domRepository.getDOMList(ExternalizerConstants.FLOW_IMPLEMENTATIONS_TAG_NAME);
		if (domList != null)
		{
			for (int i = 0; i < domList.getDocumentCount(); i++)
			{
				Document doc = domList.getDocument(i);

				processFlowsImplementationsDOM(doc);
			}
		}

		//read button lists
		domList = domRepository.getDOMList(ExternalizerConstants.BUTTON_SET_TAG);
		if (domList != null)
		{
			for (int i = 0; i < domList.getDocumentCount(); i++)
			{
				Document doc = domList.getDocument(i);

				processButtonSetsDOM(doc);
			}
		}

		//read flows
		domList = domRepository.getDOMList(ExternalizerConstants.FLOW_TAG_NAME);
        if (domList != null)
        {
			for (int i = 0; i < domList.getDocumentCount(); i++)
			{
				Document doc = domList.getDocument(i);

				processFlowsDOM(doc);
			}
        }
	}

	/**
	 * Used to create instance of <code>Flow</code>
	 *
	 * @param flowName name of flow to create
	 */
	public Flow createFlow(String flowName) throws ExternalizationException
	{
		FlowDefinition flowDefinition = (FlowDefinition) flowDefinitions.get(flowName);
		if (flowDefinition == null)
		{
			throw new ExternalizationException("Unable to create flow instance. No flow is defined for name [" + flowName + "]");
		}

		Flow flow;
		try
		{
			flow = (Flow) flowDefinition.getFlowConstructor().newInstance(new Object[]{flowDefinition});
		} catch (Throwable ex)
		{
			throw new ExternalizationException("Unable to create flow instance for [" + flowName + "]", ex);
		}

		return flow;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Private methods
	//
	///////////////////////////////////////////////////////////////////////////////////////////////////
	private void processButtonSetsDOM(Document doc)
	{
		Element rootElement = doc.getDocumentElement();
		NodeList buttonSetNodes = XMLUtil.getElementsByTagName(rootElement,ExternalizerConstants.BUTTON_SET_TAG);

		for (int i = 0; i < buttonSetNodes.getLength(); i++)
		{
			Element element = (Element) buttonSetNodes.item(i);

			try
			{
				readButtonSet(element);
			} catch (Throwable ex)
			{
				Logger.error(LOGGER_CONTEXT, "Unable to read flow implementation. Continue with other.");
				Logger.error(LOGGER_CONTEXT, ex);
			}
		}
	}

	private void readButtonSet(Element element) throws ExternalizerInitializationException
	{
		String buttonSetName = ExternalizerUtil.getName(element);
		
		if(buttonSets.containsKey(buttonSetName))
		{
			Logger.warning(LOGGER_CONTEXT, "Unable to initialize ButtonSet [" + buttonSetName + "]. A ButtonSet with that name is already defined.");
		}
		else
		{
	
			ButtonSet buttonSet = new ButtonSet(buttonSetName);
	
			NodeList buttonNodesList = XMLUtil.getElementsByTagName(element, ExternalizerConstants.BUTTON_TAG);
			for (int i = 0; i < buttonNodesList.getLength(); i++)
			{
				Element buttonElement = (Element) buttonNodesList.item(i);
				String buttonName = ExternalizerUtil.getName(buttonElement);			
				String eventName = XMLUtil.getAttribute(buttonElement, ExternalizerConstants.BUTTON_ATTR_EVENT_NAME);
				String buttonGroup = XMLUtil.getAttribute(buttonElement, ExternalizerConstants.BUTTON_ATTR_GROUP);
				String authId = ExternalizerUtil.getAuthId(buttonElement);
				String windowExtraParams = XMLUtil.getAttribute(buttonElement, ExternalizerConstants.BUTTON_ATTR_WINDOW_EXTRA_PARAMS); 
				String confirmMessageId = XMLUtil.getAttribute(buttonElement, ExternalizerConstants.BUTTON_ATTR_CONFIRM_MESSAGE_ID);
				
				String typeStr = ExternalizerUtil.getType(buttonElement);
				int type = ButtonData.TYPE_DEFAULT;
				if(typeStr != null)
				{
					if(typeStr.equals(ExternalizerConstants.BUTTON_TYPE_SPACER))
					{
						type = ButtonData.TYPE_SPACER; 
					}
					else if(! typeStr.equals(ExternalizerConstants.BUTTON_TYPE_BUTTON))
					{
						throw new ExternalizerInitializationException("Unable to initialize button. Invalid button type for button [" + buttonName + "]");
					}
				}
				
				if(eventName == null && type == ButtonData.TYPE_BUTTON)
				{
					throw new ExternalizerInitializationException("Unable to initialize button. Missing attribute eventName, is specified for button [" + buttonName + "] in buttonSet [" + buttonSet.getButtonSetName() + "]");			
				}
				else if(eventName != null && type == ButtonData.TYPE_SPACER)
				{
					throw new ExternalizerInitializationException("Unable to initialize button. Attribute eventName is not allowed for button of type spacer, is specified for button [" + buttonName + "] in buttonSet [" + buttonSet.getButtonSetName() + "]");			
				}
	
				String eventTypeStr = XMLUtil.getAttribute(buttonElement, ExternalizerConstants.BUTTON_ATTR_ACTIVITY_TYPE);
				int eventType = ButtonData.EVENT_TYPE_DEFAULT;
				if(eventTypeStr != null)
				{
					if(eventTypeStr.equals(ExternalizerConstants.ACTIVITY_TYPE_READONLY))
					{
						eventType = ButtonData.EVENT_TYPE_READONLY; 
					}
					else if(! eventTypeStr.equals(ExternalizerConstants.ACTIVITY_TYPE_READWRITE))
					{
						throw new ExternalizerInitializationException("Unable to initialize button. Invalid button event type is specified for button [" + buttonName + "] in buttonSet [" + buttonSet.getButtonSetName() + "]");
					}
				}
	
				String openWindowTypeStr = XMLUtil.getAttribute(buttonElement, ExternalizerConstants.BUTTON_ATTR_OPEN_WINDOW_TYPE);
				String openWindowType = ButtonData.OPEN_WINDOW_TYPE_DEFAULT;
				if(openWindowTypeStr != null)
				{
					if(openWindowTypeStr.equals(ExternalizerConstants.BUTTON_OPEN_WINDOW_DIALOG))
					{
						openWindowType = ButtonData.OPEN_WINDOW_TYPE_DIALOG; 
					}
					else if(openWindowTypeStr.equals(ExternalizerConstants.BUTTON_OPEN_WINDOW_POPUP))
					{
						openWindowType = ButtonData.OPEN_WINDOW_TYPE_POPUP; 
					}
					else if(openWindowTypeStr.equals(ExternalizerConstants.BUTTON_OPEN_WINDOW_NORMAL))
					{
						openWindowType = ButtonData.OPEN_WINDOW_TYPE_NORMAL; 
					}
					else if(openWindowTypeStr.equals(ExternalizerConstants.BUTTON_OPEN_WINDOW_CLOSE_DIALOG))
					{
						openWindowType = ButtonData.OPEN_WINDOW_TYPE_CLOSE_DIALOG; 
					}
					else if(openWindowTypeStr.equals(ExternalizerConstants.BUTTON_OPEN_WINDOW_CLOSE_POPUP))
					{
						openWindowType = ButtonData.OPEN_WINDOW_TYPE_CLOSE_POPUP; 
					}
					else 
					{
						throw new ExternalizerInitializationException("Unable to initialize button. Invalid button open window type is specified for button [" + buttonName + "] in buttonSet [" + buttonSet.getButtonSetName() + "]");
					}
				}
	
				if(windowExtraParams != null 
					&& !(openWindowType.equals(ButtonData.OPEN_WINDOW_TYPE_DIALOG) 
					   || openWindowType.equals(ButtonData.OPEN_WINDOW_TYPE_POPUP)) )
				{
					throw new ExternalizerInitializationException("Unable to initialize button [" + buttonName + "] in ButtonSet [" + buttonSetName + "]. The attribute " + ExternalizerConstants.BUTTON_ATTR_WINDOW_EXTRA_PARAMS + " is not allowed for " + ExternalizerConstants.BUTTON_ATTR_OPEN_WINDOW_TYPE + " that is not of type [" + ExternalizerConstants.BUTTON_OPEN_WINDOW_DIALOG + " or " + ExternalizerConstants.BUTTON_OPEN_WINDOW_POPUP + "]");
				}

				boolean checkDirtyFlag = false;
				String checkDirtyStr = XMLUtil.getAttribute(buttonElement, ExternalizerConstants.BUTTON_ATTR_CHECK_DIRTY);
				if (checkDirtyStr != null)
				{
					if (checkDirtyStr.equals(ExternalizerConstants.TRUE))
					{
						checkDirtyFlag = true;
					}
					else if (! checkDirtyStr.equals(ExternalizerConstants.FALSE))
					{
						throw new ExternalizerInitializationException("Illegal " + ExternalizerConstants.BUTTON_ATTR_CHECK_DIRTY + " value [" + checkDirtyStr + "] is specified for button [" + buttonName + "] in buttonSet [" + buttonSet.getButtonSetName() + "]");
					}
				}
	
				boolean checkWarnings = false;
				String checkWarningsStr = XMLUtil.getAttribute(buttonElement, ExternalizerConstants.BUTTON_ATTR_CHECK_WARNINGS);
				if (checkWarningsStr != null)
				{
					if (checkWarningsStr.equals(ExternalizerConstants.TRUE))
					{
						checkWarnings = true;
					}
					else if (! checkWarningsStr.equals(ExternalizerConstants.FALSE))
					{
						throw new ExternalizerInitializationException("Illegal " + ExternalizerConstants.BUTTON_ATTR_CHECK_WARNINGS + " value [" + checkWarningsStr + "] is specified for button [" + buttonName + "] in buttonSet [" + buttonSet.getButtonSetName() + "]");
					}
				}

				if(checkWarnings  
					&& (openWindowType.equals(ButtonData.OPEN_WINDOW_TYPE_DIALOG) 
					||  openWindowType.equals(ButtonData.OPEN_WINDOW_TYPE_POPUP)))
				{
					throw new ExternalizerInitializationException("Unable to initialize button [" + buttonName + "] in ButtonSet [" + buttonSetName + "]. The attribute " + ExternalizerConstants.BUTTON_ATTR_CHECK_WARNINGS + " is not allowed for " + ExternalizerConstants.BUTTON_ATTR_OPEN_WINDOW_TYPE + " [" + ExternalizerConstants.BUTTON_OPEN_WINDOW_DIALOG + " or " + ExternalizerConstants.BUTTON_OPEN_WINDOW_POPUP + "]");
				}

				boolean allowEventOnDirtyIds = true;
				String allowEventOnDirtyIdsStr = XMLUtil.getAttribute(buttonElement, ExternalizerConstants.BUTTON_ATTR_ALLOW_EVENT_ON_DIRTY_IDS);
				if (allowEventOnDirtyIdsStr != null)
				{
					if (allowEventOnDirtyIdsStr.equals(ExternalizerConstants.FALSE))
					{
						allowEventOnDirtyIds = false;
					}
					else if (! allowEventOnDirtyIdsStr.equals(ExternalizerConstants.TRUE))
					{
						throw new ExternalizerInitializationException("Illegal " + ExternalizerConstants.BUTTON_ATTR_ALLOW_EVENT_ON_DIRTY_IDS + " value [" + allowEventOnDirtyIdsStr + "] is specified for button [" + buttonName + "] in buttonSet [" + buttonSet.getButtonSetName() + "]");
					}
				}
	
				List checkDirtyModelIds = null;
				String checkDirtyIds = XMLUtil.getAttribute(buttonElement, ExternalizerConstants.BUTTON_ATTR_CHECK_DIRTY_IDS);
				if(checkDirtyIds != null)
				{
					checkDirtyModelIds = StringFormatterUtil.convertStringToList(checkDirtyIds, ",");
				}

				String stateModelId = XMLUtil.getAttribute(buttonElement, ExternalizerConstants.BUTTON_ATTR_STATE_MODEL_ID);
				String shortCutKey = XMLUtil.getAttribute(buttonElement, ExternalizerConstants.BUTTON_ATTR_SHORT_CUT_KEY);
				
				buttonSet.addButtonData(new ButtonData(buttonName, eventName, buttonGroup, type, eventType, openWindowType, windowExtraParams, checkDirtyFlag, checkWarnings, checkDirtyModelIds, allowEventOnDirtyIds, confirmMessageId, authId, buttonSetName, stateModelId, shortCutKey));
			}
	
			buttonSets.put(buttonSet.getButtonSetName(), buttonSet);
		}
	}

	private void processFlowsImplementationsDOM(Document doc)
	{
		Element rootElement = doc.getDocumentElement();
		NodeList implementationNodes = XMLUtil.getElementsByTagName(rootElement,ExternalizerConstants.FLOW_IMPLEMENTATIONS_TAG_NAME);

		for (int i = 0; i < implementationNodes.getLength(); i++)
		{
			Element element = (Element) implementationNodes.item(i);

			try
			{
				readFlowImplementation(element);
			} catch (Throwable ex)
			{
				Logger.error(LOGGER_CONTEXT, "Unable to read flow implementation. Continue with other.");
				Logger.error(LOGGER_CONTEXT, ex);
			}
		}
	}

	private void readFlowImplementation(Element element) throws ExternalizerInitializationException
	{
		NodeList implementationNodes = XMLUtil.getElementsByTagName(element, ExternalizerConstants.IMPLEMENTATION_TAG_NAME);
		for (int i = 0; i < implementationNodes.getLength(); i++)
		{
			Element implEl = (Element) implementationNodes.item(i);
			String name = ExternalizerUtil.getName(implEl);
			if(implementations.containsKey(name))
			{
				Logger.warning(LOGGER_CONTEXT, "Unable to initialize flow implementation [" + name + "]. A flow implementation with that name is already defined.");
			}
			else
			{			
				String className = ExternalizerUtil.getClassName(implEl);
				implementations.put(name,  className);
			}
		}
	}

	private void processActivitiesDOM(Document doc)
	{
		Element rootElement = doc.getDocumentElement();
		NodeList activitiesNodes = XMLUtil.getElementsByTagName(rootElement,ExternalizerConstants.ACTIVITY_TAG_NAME);

		for (int i = 0; i < activitiesNodes.getLength(); i++)
		{
			Element element = (Element) activitiesNodes.item(i);

			try
			{
				readActivity(element);
			} catch (Throwable ex)
			{
				Logger.error(LOGGER_CONTEXT, "Unable to initialize activity. Continue with other.");
				Logger.error(LOGGER_CONTEXT, ex);
			}
		}
	}

	private void readActivity(Element element) throws ExternalizationException, ExternalizerNotInitializedException, ClassNotFoundException, IllegalAccessException, InstantiationException, ExternalizerInitializationException, ActivityException
	{
		String name = ExternalizerUtil.getName(element);

		if(activities.containsKey(name))
		{
			Logger.warning(LOGGER_CONTEXT, "Unable to initialize activity [" + name + "]. An activity with that name is already defined.");
		}
		else
		{
		
			String className = ExternalizerUtil.getClassName(element);
			Validator validator = ExternalizerUtil.createValidator(element);
			ParameterList parameterList = ExternalizerUtil.createParametersList(element);
	
			Logger.debug(LOGGER_CONTEXT, "Loading activity with name [" + name + "] and class name [" + className + "]");
			
			// retrieve activity type		
			int activityType = ExternalizerUtil.getActivityType(element);
	
			Activity activity = (Activity) Class.forName(className).newInstance();
			activity.setName(name);
			activity.setValidator(validator);
			activity.setActivityType(activityType);
			activity.initialize(parameterList);
	
			activities.put(activity.getName(), activity);
		}
	}

	private void processCustomGuardsDOM(Document doc)
	{
		Element rootElement = doc.getDocumentElement();

		NodeList guardNodes = XMLUtil.getElementsByTagName(rootElement,ExternalizerConstants.GUARD_DEFINITION_TAG_NAME);

		for (int i = 0; i < guardNodes.getLength(); i++)
		{
			Element guardElement = (Element) guardNodes.item(i);

			try
			{
				readCustomGuard(guardElement);
			} catch (Throwable ex)
			{
				Logger.error(LOGGER_CONTEXT, "Unable to initialize custom guard. Continue with other.");
				Logger.error(LOGGER_CONTEXT, ex);
			}
		}
	}

	private void readCustomGuard(Element guardElement) throws ExternalizerInitializationException
	{
		String name = ExternalizerUtil.getName(guardElement);
		if(customGuards.containsKey(name))
		{
			Logger.warning(LOGGER_CONTEXT, "Unable to initialize custom guard [" + name + "]. A custom guard with that name is already defined.");
		}
		else
		{

			try{
	
				String clazz = ExternalizerUtil.getClassName(guardElement);
	
				ParameterList parameterList = ExternalizerUtil.createParametersList(guardElement);
	
				Guard guard = (Guard) Class.forName(clazz).newInstance();
				guard.initialize(parameterList);
	
				customGuards.put(name, guard);
			}catch (Throwable ex)
			{
				throw new ExternalizerInitializationException("Unable to initialize guard with name [" + name + "]", ex);
			}
		}
	}

	private void processFlowsDOM(Document doc)
	{
        Element rootElement = doc.getDocumentElement();

		NodeList flowNodes = XMLUtil.getElementsByTagName(rootElement, ExternalizerConstants.FLOW_TAG_NAME);

		for (int i = 0; i < flowNodes.getLength(); i++)
		{
			Element flowElement = (Element) flowNodes.item(i);

			try
			{
				if(flowDefinitions.containsKey(ExternalizerUtil.getName(flowElement)))
				{
					Logger.warning(LOGGER_CONTEXT, "Unable to initialize flow [" + ExternalizerUtil.getName(flowElement) + "]. A flow with that name is already defined.");
				}
				else
				{
					FlowDefinition fd = createFlowDefinition(flowElement);
					flowDefinitions.put(fd.getName(), fd);
				}
			} 
			catch (Throwable ex)
			{
				Logger.error(LOGGER_CONTEXT, "Unable to initialize flow [" + ExternalizerUtil.getName(flowElement) + "]. Continue with other flows.");
				Logger.error(LOGGER_CONTEXT, ex);
			}
		}
	}

	private FlowDefinition createFlowDefinition(Element flowElement) throws ExternalizerNotInitializedException, ExternalizationException, ExternalizerInitializationException
	{
		//retrieve flow name
		String flowName = ExternalizerUtil.getName(flowElement);
		Logger.debug(LOGGER_CONTEXT, "Loading flow [" + flowName + "]");

		//retrieve flow constructor
		String implementation = XMLUtil.getAttribute(flowElement, ExternalizerConstants.ATTR_IMPLEMENTATION);
		if (implementation == null)
		{
			implementation = ExternalizerConstants.DEFAULT_IMPLEMENTATION;
		}

		// gte flo wclass name according to the flow implementation
		String flowClassName = (String) implementations.get(implementation);
		if (flowClassName == null)
		{
			throw new ExternalizerInitializationException("Unable to initialize flow. No class name is defined for flow [" + flowName + "]");
		}

		// create flow constructor
		Constructor constructor;
		try
		{
			constructor = Class.forName(flowClassName).getConstructor(new Class[]{FlowDefinition.class});
		} catch (Throwable ex)
		{
			throw new ExternalizerInitializationException("Unable to initialize flow. Unable to create constructor for flow class for flow [" + flowName + "]", ex);
		}

		//retrieve context name
		String contextName = ExternalizerUtil.getContext(flowElement);

		//creating validator
		Validator validator = ExternalizerUtil.createValidator(flowElement);

		//retrieve authId
		String authId = XMLUtil.getAttribute(flowElement, ExternalizerConstants.ATTR_AUTH_ID); 

		//retrieve independent
		String independentStr = XMLUtil.getAttribute(flowElement, ExternalizerConstants.FLOW_ATTR_INDEPENDENT);
		boolean independent = independentStr != null && independentStr.equals(ExternalizerConstants.TRUE);

		//retrieve menu name
		String menuName = XMLUtil.getAttribute(flowElement, ExternalizerConstants.ATTR_MENU_NAME);

		//determine tab flow
		String flowTypeStr = ExternalizerUtil.getType(flowElement);
		
		// get flow type
		boolean tabFlow = false;
		if(flowTypeStr != null)
		{
			if(flowTypeStr.equals(ExternalizerConstants.FLOW_TYPE_TAB))
			{
				tabFlow = true;
			}
			else if (! flowTypeStr.equals(ExternalizerConstants.FLOW_TYPE_GENERIC))
			{
				throw new ExternalizationException("Illegal " + ExternalizerConstants.ATTR_TYPE + " value [" + flowTypeStr + "] is specified for flow [" + flowName + "]");				
			}
		}
		
		//creating flow definition instance
		FlowDefinition flowDefinition = new FlowDefinition(constructor, flowName, contextName, validator, independent, menuName, authId);

		// read flow ActionsLists 
		ElementActionLists elementActionsLists = new ElementActionLists ();
		ExternalizerUtil.readActionLists(flowElement, elementActionsLists, ElementActionLists.FLOW_LEVEL);

        //creating states
        NodeList statesNodes = XMLUtil.getElementsByTagName(flowElement, ExternalizerConstants.STATE_TAG_NAME);
        for (int i = 0; i < statesNodes.getLength(); i++)
        {
	        Element stateElement = (Element) statesNodes.item(i);
	        String stateName = ExternalizerUtil.getName(stateElement);
	        if(flowDefinition.isContainsState(stateName))
	        {
				Logger.warning(LOGGER_CONTEXT, "Unable to initialize flow [" + flowName + "]. A state with the name [" + stateName + "] is already defined.");
	        }
	        else
	        {
		        FlowState flowState = createState(stateElement, tabFlow, flowName, elementActionsLists);
		        if (flowState.isInitialState())
		        {
		        	if(flowDefinition.getInitialStateName() != null)
		        	{
						throw new ExternalizerInitializationException("Unable to initialize state [" + stateName + "] in flow [" + flowName + "]. An initial state is already defined.");
		        	}
		        	else
		        	{
						flowDefinition.setInitialStateName(flowState.getName());
						Logger.warning(LOGGER_CONTEXT, "Initial State set to [" + flowState.getName() + "]");
		        	}
		        }
	
		        flowDefinition.addFlowState(flowState);
	        }
        }

		//checking for initial state existence
		if (flowDefinition.getInitialStateName() == null)
		{
			throw new ExternalizerInitializationException("Unable to initialize flow. No initial state is defined for flow [" + flowDefinition.getName() + "]");
		}

		//retrieve default self transitions
		String defaultSelfTransitionsStr = XMLUtil.getAttribute(flowElement, ExternalizerConstants.FLOW_ATTR_DEFAULT_INTERNAL_TRANSITIONS);
		boolean defaultSelfTransitions = false;
		if (defaultSelfTransitionsStr != null)
		{
			if (defaultSelfTransitionsStr.equals(ExternalizerConstants.TRUE))
			{
				if (tabFlow)
				{
					Logger.warning(LOGGER_CONTEXT, "Flow can't be defined as tab flow and to have default self transitions attribute turned on. The default self transitins attribute will be ignored");
				}
				else
				{
					defaultSelfTransitions = true;
				}
			}
			else if (defaultSelfTransitionsStr.equals(ExternalizerConstants.FALSE))
			{
				defaultSelfTransitions = false;
			}
			else
			{
				throw new ExternalizationException("Illegal " + ExternalizerConstants.FLOW_ATTR_DEFAULT_INTERNAL_TRANSITIONS + " value [" + defaultSelfTransitionsStr + "] is specified for flow [" + flowName + "]");
			}
		}
		
		if (defaultSelfTransitions)
		{
			//creating default self transitions
			flowDefinition.createDefaultInternalTransitions();
		}

		//create tab transitions and generate dispatcher state
		
		// handle tab system definition & checks
		createTabSystemDefinition(tabFlow, flowElement, flowDefinition);

		return flowDefinition;
	}

	private void createTabSystemDefinition (boolean tabFlow, Element flowElement, FlowDefinition flowDefinition) throws ExternalizerInitializationException
	{
		NodeList tabSystemDataNodeList = XMLUtil.getElementsByTagName(flowElement, ExternalizerConstants.TAB_SYSTEM_DATA_TAG_NAME);
		if (tabFlow && tabSystemDataNodeList.getLength() == 0)
		{
			throw new ExternalizerInitializationException("Unable to initialize flow [" + flowDefinition.getName() + "]. The " + ExternalizerConstants.TAB_SYSTEM_DATA_TAG_NAME + " element was not defined for flow of type " + ExternalizerConstants.FLOW_TYPE_TAB);
		}
		else if(!tabFlow && tabSystemDataNodeList.getLength() > 0)
		{
			throw new ExternalizerInitializationException("Unable to initialize flow [" + flowDefinition.getName() + "]. A " + ExternalizerConstants.TAB_SYSTEM_DATA_TAG_NAME + " element was defined for the flow, which is not of type " + ExternalizerConstants.FLOW_TYPE_TAB);
		}
		
		if (tabFlow)
		{
			Element tabSystemDataElement = (Element) tabSystemDataNodeList.item(0);
			
			String page = XMLUtil.getAttribute(tabSystemDataElement, ExternalizerConstants.TAB_SYSTEM_ATTR_PAGE);
			if(page == null)
			{
				throw new ExternalizerInitializationException("Unable to initialize flow [" + flowDefinition.getName() + "]. The page attribute was not defined for flow of type " + ExternalizerConstants.FLOW_TYPE_TAB);
			}
			flowDefinition.setPage(page);
			
			FlowStatesList flowStatesList = flowDefinition.getVisibleFlowStatesList();

			//create tab transitions
			createTabTransitions(flowStatesList);

			//generate dispatcher state transitions
			TransitionsListMap dispatcherTransitions = generateTabDispatcherTransitions(flowStatesList);

			// create default dispatcher state. 
			SimpleFlowState dispatcherState = new SimpleFlowState(DISPATCHER_STATE_NAME, null, null, null, null, null, dispatcherTransitions, null, false, null);
			flowDefinition.addFlowState(dispatcherState);
		}
	}

	private TransitionsListMap generateTabDispatcherTransitions(FlowStatesList flowStatesList)
	{
		TransitionsListMap transitionsListMap = new TransitionsListMap();
		for (int i = 0; i < flowStatesList.getFlowStatesCount(); i++)
		{
			FlowState flowState = flowStatesList.getFlowState(i);			
			Transition transition = new Transition(
				null, new TabGuard(flowState.getName()), flowState.getName(), 
				false, false, null, false, false);
//			        null,
//			        new TabGuard(flowState.getName()),
//			        null,
//			        null,
//			        null,
//			        null,
//			        flowState.getName(),
//			        null,
//			        false,
//			        null,
//					null,
//					null,
//			        false,
//			        null,
//			        false,
//			        false
//			        );

			transitionsListMap.addTransition(transition);
		}

		return transitionsListMap;
	}

	private void createTabTransitions(FlowStatesList flowStatesList)
	{
		for (int i = 0; i < flowStatesList.getFlowStatesCount(); i++)
		{
			FlowState flowState = flowStatesList.getFlowState(i);
			if (flowState.getDefaultTransition() == null)
			{
				flowState.getTransitions().addTransition(
					new Transition(null, null, DISPATCHER_STATE_NAME, false, false, null, true, false));
//					new Transition(null, null, null, null, null, null, DISPATCHER_STATE_NAME, null, false, null, null, null, false, null, true, false));
			}
		}
	}

	private FlowState createState(Element element, boolean tabFlow, String flowName, ElementActionLists elementActionsLists) throws ExternalizerInitializationException, ExternalizationException, ExternalizerNotInitializedException
	{
		String stateName = ExternalizerUtil.getName(element);
		Logger.debug(LOGGER_CONTEXT, "Loading state [" + stateName + "]");

		String typeStr = ExternalizerUtil.getType(element);

		boolean initial = false;
		String initialStr = XMLUtil.getAttribute(element, ExternalizerConstants.STATE_ATTR_INITIAL);
		if (initialStr != null && initialStr.equals(ExternalizerConstants.TRUE))
		{
			initial = true;
		}

		Formatter inFormatter;
		Formatter outFormatter;
		ComplexFormatter complexFormatter = ExternalizerUtil.createComplexFormatter(element);
		if (complexFormatter != null)
		{
			inFormatter = complexFormatter.getInFormatter();
			outFormatter = complexFormatter.getOutFormatter();
		}
		else
		{
			inFormatter = ExternalizerUtil.createInFormatter(element);
			outFormatter = ExternalizerUtil.createOutFormatter(element);
		}

		boolean visible = false;
		String visibleStr = XMLUtil.getAttribute(element, ExternalizerConstants.STATE_ATTR_VISIBLE);
		if (visibleStr != null)
		{
			if (visibleStr.equals(ExternalizerConstants.FALSE))
			{
				visible = false;
			}
			else if (visibleStr.equals(ExternalizerConstants.TRUE))
			{
				if(!tabFlow)
					throw new ExternalizationException("Unable to initialize state [" + stateName + "] in flow [" + flowName + "]. Attribute [" + ExternalizerConstants.STATE_ATTR_VISIBLE + "=true] is not allowed for a flow of type <> " + ExternalizerConstants.FLOW_TYPE_TAB);				
				
				visible = true;
			}
			else
			{
				throw new ExternalizationException("Illegal " + ExternalizerConstants.STATE_ATTR_VISIBLE + " value [" + visibleStr + "] is specified for flow [" + flowName + "] at state [" + stateName + "]");
			}
		}

		boolean reachableByFlowOnly = !(tabFlow && visible);
		String reachableByFlowOnlyStr = XMLUtil.getAttribute(element, ExternalizerConstants.STATE_ATTR_REACHABLE_BY_FLOW_ONLY);
		if (reachableByFlowOnlyStr != null)
		{
			if(!tabFlow)
				throw new ExternalizationException("Illegal attribute " + ExternalizerConstants.STATE_ATTR_REACHABLE_BY_FLOW_ONLY + ", not allowed for state in flow that is not of type " + ExternalizerConstants.FLOW_TYPE_TAB + "], is specified for flow [" + flowName + "] at state [" + stateName + "]");
			
			if (reachableByFlowOnlyStr.equals(ExternalizerConstants.FALSE))
			{
				reachableByFlowOnly = false;
			}
			else if (reachableByFlowOnlyStr.equals(ExternalizerConstants.TRUE))
			{
				reachableByFlowOnly = true;
			}
			else
			{
				throw new ExternalizationException("Illegal " + ExternalizerConstants.STATE_ATTR_REACHABLE_BY_FLOW_ONLY + " value [" + reachableByFlowOnlyStr + "] is specified for flow [" + flowName + "] at state [" + stateName + "]");
			}
		}

		// clear state ActionsLists
		elementActionsLists.setLevelActionLists(ElementActionLists.STATE_LEVEL, null);

		// retrieve authorization id 
		String authId = XMLUtil.getAttribute(element, ExternalizerConstants.ATTR_AUTH_ID); 

		String buttonSetNamesStr = XMLUtil.getAttribute(element, ExternalizerConstants.STATE_ATTR_BUTTONSET);

		// check elements / attributes that is relevant only for complex state
		if(!typeStr.equals(ExternalizerConstants.STATE_TYPE_COMPLEX))
		{
			if(buttonSetNamesStr != null)
			{
				throw new ExternalizationException("Unable to load state [" + stateName + "] in flow [" + flowName + "]. Attribute " + ExternalizerConstants.STATE_ATTR_BUTTONSET + " declaration is not allowed for state that is not of type " + ExternalizerConstants.STATE_TYPE_COMPLEX+ ".");
			}

			if (ExternalizerUtil.isExistSubFlowData(element))
			{
				throw new ExternalizationException("Unable to load state [" + stateName + "] in flow [" + flowName + "]. Element " + ExternalizerConstants.SUB_FLOW_DATA_TAG_NAME + " declaration is not allowed for state that is not of type " + ExternalizerConstants.STATE_TYPE_COMPLEX+ ".");
			}
		}

		FlowState flowState = null;
        if (typeStr.equals(ExternalizerConstants.STATE_TYPE_FINAL))
        {
			NodeList nodeList = XMLUtil.getElementsByTagName(element, ExternalizerConstants.STATE_TRANSITION_TAG_NAME);
			if(nodeList.getLength() > 0)
			{
				throw new ExternalizationException("Unable to load state [" + stateName + "] in flow [" + flowName + "]. Transition declaration is not allowed for state of type " + ExternalizerConstants.STATE_TYPE_FINAL+ ".");				        	
			}
			
			if(initial)
			{
				throw new ExternalizationException("Unable to load state [" + stateName + "] in flow [" + flowName + "]. initial=true declaration is not allowed for state of type " + ExternalizerConstants.STATE_TYPE_FINAL + ".");
			}
	        flowState = new FinalFlowState(stateName, authId);
        }
		else if (typeStr.equals(ExternalizerConstants.STATE_TYPE_SIMPLE))
		{
			String contextName = ExternalizerUtil.getContext(element);
			Validator validator = ExternalizerUtil.createValidator(element);
			
			// read state ActionsLists 
			ExternalizerUtil.readActionLists(element, elementActionsLists, ElementActionLists.STATE_LEVEL);

			ActionList entryActions = ExternalizerUtil.readActions(element, ExternalizerConstants.ENTRY_ACTIONS_TAG_NAME, elementActionsLists);
			ActionList exitActions = ExternalizerUtil.readActions(element, ExternalizerConstants.EXIT_ACTIONS_TAG_NAME, elementActionsLists);
            TransitionsListMap transitions = readTransitions(element, elementActionsLists, false);

			flowState = new SimpleFlowState(stateName, contextName, inFormatter, outFormatter, validator, entryActions, transitions, exitActions, initial, authId);
		}
		else if (typeStr.equals(ExternalizerConstants.STATE_TYPE_COMPLEX))
		{
			String contextName = ExternalizerUtil.getContext(element);
			Validator validator = ExternalizerUtil.createValidator(element);
			String page = XMLUtil.getAttribute(element, ExternalizerConstants.ATTR_PAGE);

			// retrieve sub flow data
			SubFlowDataList subFlowDataList = ExternalizerUtil.createSubFlowDataList(element);
			SubFlowData subFlowData = subFlowDataList == null ? null : subFlowDataList.getSubFlowData(0);

			//retrieve activity
			String activityStr = XMLUtil.getAttribute(element, ExternalizerConstants.STATE_ATTR_ACTIVITY);
			Activity activity = null;
			if (activityStr != null)
			{
				activity = (Activity) activities.get(activityStr);
				if (activity == null)
				{
					throw new ExternalizerInitializationException("No activity is defined for ["+ activityStr + "]");
				}
			}

			if(activity != null && subFlowData != null)
			{
				throw new ExternalizerInitializationException("ActivityImpl and SubFlow together is not allowed for state. Specified for flow [" + flowName + "] at state [" + stateName + "]");
			}

			if(buttonSetNamesStr != null && subFlowData == null && page == null)
			{
				throw new ExternalizationException("Unable to load state [" + stateName + "] in flow [" + flowName + "]. Attribute + " + ExternalizerConstants.STATE_ATTR_BUTTONSET + " declaration is not allowed for state of type " + ExternalizerConstants.STATE_TYPE_COMPLEX+ " without a page or subflow.");
			}

			// read state ActionsLists 
			ExternalizerUtil.readActionLists(element, elementActionsLists, ElementActionLists.STATE_LEVEL);

			ActionList entryActions = ExternalizerUtil.readActions(element, ExternalizerConstants.ENTRY_ACTIONS_TAG_NAME, elementActionsLists);
			ActionList exitActions = ExternalizerUtil.readActions(element, ExternalizerConstants.EXIT_ACTIONS_TAG_NAME, elementActionsLists);
			TransitionsListMap transitions = readTransitions(element, elementActionsLists, true);

			//retrieve menu name (transitionSupplierName) 
			String menuName = XMLUtil.getAttribute(element, ExternalizerConstants.ATTR_MENU_NAME);

			//retrieve buttonList;
			ButtonSetList buttonSetList = null;
			if(buttonSetNamesStr != null)
			{
				String[] buttonSetNames = StringFormatterUtil.separateByDelimiter(buttonSetNamesStr, ",");
				buttonSetList = new ButtonSetList();
				for (int i = 0; i < buttonSetNames.length; i++)
				{
					ButtonSet buttonSet = (ButtonSet) buttonSets.get(buttonSetNames[i]);
					if (buttonSet == null)
					{
						throw new ExternalizerInitializationException("No button set is defined for ["+ buttonSetNames[i] + "]");
					}
					buttonSetList.addButtonSet(buttonSet);
				}
			}

			boolean isWaitingForEvent = false;
			String isWaitingForEventStr = XMLUtil.getAttribute(element, ExternalizerConstants.STATE_ATTR_WAITING_FOR_EVENT);
			if (isWaitingForEventStr != null)
			{
				if (isWaitingForEventStr.equals(ExternalizerConstants.FALSE))
				{
					isWaitingForEvent = false;
				}
				else if (isWaitingForEventStr.equals(ExternalizerConstants.TRUE))
				{
					isWaitingForEvent = true;
				}
				else
				{
					throw new ExternalizationException("Illegal " + ExternalizerConstants.STATE_ATTR_WAITING_FOR_EVENT + " value [" + isWaitingForEventStr + "] is specified for flow [" + flowName + "] at state [" + stateName + "]");
				}
			}

			if(page == null && activity == null && subFlowData == null &&
				!isWaitingForEvent)
			{
				throw new ExternalizerInitializationException("Unable to initialize state [" + stateName + "] in flow [" + flowName + "]. At least one of the attributes: Page, ActivityImpl Or SubFlowData should be supplied.");
			}


			if(contextName == null && (inFormatter != null || outFormatter != null))
			{
				Logger.warning(LOGGER_CONTEXT, "The state [" + stateName + "] in flow [" + flowName + "] contains in/out formatter without a specific context.");
			}

            flowState = new ComplexFlowState(stateName, contextName, page, inFormatter, outFormatter, validator, entryActions, transitions,  exitActions, reachableByFlowOnly, initial, subFlowData, activity, buttonSetList, menuName, isWaitingForEvent, visible, authId);
		}
		else
		{
            throw new ExternalizerInitializationException("Unable to initialize state [" + stateName + "] in flow [" + flowName + "]. Illegal State type [" + typeStr + "]");
		}

		return flowState;
	}

	private TransitionsListMap readTransitions(Element element, ElementActionLists elementActionsLists, boolean allowEventName) throws ExternalizerInitializationException, ExternalizerNotInitializedException, ExternalizationException
	{
		TransitionsListMap transitionsList = new TransitionsListMap();

        NodeList nodeList = XMLUtil.getElementsByTagName(element, ExternalizerConstants.STATE_TRANSITION_TAG_NAME);
        for (int i = 0; i < nodeList.getLength(); i++)
        {
	        Element transitionElement = (Element) nodeList.item(i);
            transitionsList.addTransition(readTransition(transitionElement, ExternalizerUtil.getName(element), elementActionsLists, allowEventName));
        }

		return transitionsList;
	}

	private Transition readTransition(Element transitionElement, String stateName, ElementActionLists elementActionsLists, boolean allowEventName) throws ExternalizerInitializationException, ExternalizationException, ExternalizerNotInitializedException
	{
        String defaultTransitionStr = XMLUtil.getAttribute(transitionElement, ExternalizerConstants.TRANSITION_ATTR_DEFAULT);
		boolean defaultTransition = defaultTransitionStr != null && defaultTransitionStr.equals(ExternalizerConstants.TRUE);

        String eventName = XMLUtil.getAttribute(transitionElement, ExternalizerConstants.TRANSITION_ATTR_EVENT_NAME);

		if(eventName != null && ! allowEventName)
		{
			throw new ExternalizerInitializationException("Unable to load transition for state ["+ stateName + "], attribute " + ExternalizerConstants.TRANSITION_ATTR_EVENT_NAME + " is not allowed for states of type " + ExternalizerConstants.STATE_TYPE_SIMPLE);
		}


		CombinedGuard combinedGuard = new CombinedGuard();

		//retrieve validation guard
		String customValidationStr = XMLUtil.getAttribute(transitionElement, ExternalizerConstants.TRANSITION_ATTR_VALIDATION_GUARD);
		if (customValidationStr != null)
		{
			Validator guardValidator = ValidatorExternalizer.getInstance().createValidator(customValidationStr);
			if (guardValidator != null)
			{
				combinedGuard.addGuard(new ValidationGuard(guardValidator));
			}
		}

		//retrieve custom guard
		String customGuardStr = XMLUtil.getAttribute(transitionElement, ExternalizerConstants.TRANSITION_ATTR_CUSTOM_GUARD);
		Guard customGuard = null;
		if (customGuardStr != null)
		{
			customGuard = (Guard) customGuards.get(customGuardStr);

			if (customGuard != null)
			{
				combinedGuard.addGuard(customGuard);
			}
		}

		//retrieve guard
		String guardStr = XMLUtil.getAttribute(transitionElement, ExternalizerConstants.TRANSITION_ATTR_GUARD);
		Guard guard = null;
		if (guardStr != null && guardStr.length() > 0)
		{
			try
			{
				guard = GuardFactory.createGuard(guardStr);

				combinedGuard.addGuard(guard);
			} catch (GuardException ex)
			{
				throw new ExternalizerInitializationException("Unable to create guard with expression [" + guardStr + "]", ex);
			}
		}

		if(eventName == null && !defaultTransition && combinedGuard.getCount() == 0)
		{
			throw new ExternalizerInitializationException("Unable to load transition for state ["+ stateName + "], EventName or gurad or defaultTransition=true must be supplied.");
		}

		Logger.debug(LOGGER_CONTEXT, "Loading transition with event [" + eventName + "] and guard [" + guardStr + "] and custom guard name [" + customGuardStr + "]");

		String targetState = XMLUtil.getAttribute(transitionElement, ExternalizerConstants.TRANSITION_ATTR_TARGET_STATE);

		boolean internal = false;
		String internalStr = XMLUtil.getAttribute(transitionElement, ExternalizerConstants.TRANSITION_ATTR_INTERNAL);
		if (internalStr != null && internalStr.equals(ExternalizerConstants.TRUE))
		{
			if(targetState != null)
			{
				throw new ExternalizerInitializationException("Unable to load transition for state ["+ stateName + "]. Attribute [" + ExternalizerConstants.TRANSITION_ATTR_INTERNAL+ "=true] is not allowed with Attribute " + ExternalizerConstants.TRANSITION_ATTR_TARGET_STATE + "[" + targetState + "]");
			}

			internal = true;
		}
		
		if(targetState == null && !internal)
		{
			throw new ExternalizerInitializationException("Unable to load transition for state ["+ stateName + "], targetState or internal=true must be supplied.");
		}

		String alternativeTargetState = XMLUtil.getAttribute(transitionElement, ExternalizerConstants.TRANSITION_ATTR_ALTERNATIVE_TARGET_STATE);
		String handleValidationErrorStr = XMLUtil.getAttribute(transitionElement, ExternalizerConstants.TRANSITION_ATTR_HANDLE_VALIDATION_ERROR);
		boolean handleValidationError = handleValidationErrorStr != null && handleValidationErrorStr.equals(ExternalizerConstants.TRUE);

		Formatter inFormatter;
		Formatter outFormatter;
		ComplexFormatter complexFormatter = ExternalizerUtil.createComplexFormatter(transitionElement);
		if (complexFormatter != null)
		{
			inFormatter = complexFormatter.getInFormatter();
			outFormatter = complexFormatter.getOutFormatter();
		}
		else
		{
			inFormatter = ExternalizerUtil.createInFormatter(transitionElement);
			outFormatter = ExternalizerUtil.createOutFormatter(transitionElement);
		}

		Validator validator = ExternalizerUtil.createValidator(transitionElement);

		String contextName = ExternalizerUtil.getContext(transitionElement);

		ActionList actions = ExternalizerUtil.readActions(transitionElement, ExternalizerConstants.TRANSITION_ACTIONS_TAG_NAME, elementActionsLists);
		ActionList validationExceptionActions = ExternalizerUtil.readActions(transitionElement, ExternalizerConstants.TRANSITION_VALIDATION_EXC_ACTIONS_TAG_NAME, elementActionsLists);
		ActionList validationActions = ExternalizerUtil.readValidationActions(transitionElement);

		SubFlowDataList subFlowDataList = ExternalizerUtil.createSubFlowDataList(transitionElement);

		boolean traverse = false;
		if (subFlowDataList != null)
		{
			if(!internal && targetState != null && targetState.equals(stateName))
			{
				throw new ExternalizerInitializationException("Unable to load transition for state ["+ stateName + "], transition with sub flows cannot be declared with targetState to itself.");
			}
			
			String traverseStr = XMLUtil.getAttribute(transitionElement, ExternalizerConstants.TRANSITION_ATTR_TRAVERSE);
			traverse = traverseStr != null && traverseStr.equals(ExternalizerConstants.TRUE);			
		}

		return new Transition(eventName, combinedGuard, contextName, inFormatter, outFormatter, validator, targetState, alternativeTargetState, handleValidationError, actions, validationExceptionActions, validationActions, internal, subFlowDataList, defaultTransition, traverse);
	}
}


class TabGuard implements Guard
{
	private String stateName;

	public TabGuard(String stateName)
	{
		this.stateName = stateName;
	}

	public boolean check(Context ctx) throws GuardException
	{
		try
		{
			return ctx.getField(Event.EVENT_EXTRA_PARAMS_FIELD_CMB).equals(stateName);
		} catch (Throwable ex)
		{
			throw new GuardException("Error checking TabGuard for target state [" + stateName + "]", ex);
		}
	}

	public void initialize(ParameterList parameterList) throws GuardException
	{
	}

}