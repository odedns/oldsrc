package com.ness.fw.ui.taglib.flower;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import com.ness.fw.ui.DisplayStateModel;
import com.ness.fw.ui.ToolBarModel;
import com.ness.fw.ui.UIConstants;
import com.ness.fw.shared.ui.FlowerUIUtil;
import com.ness.fw.ui.events.CustomEvent;
import com.ness.fw.ui.events.Event;
import com.ness.fw.ui.taglib.AbstractModelTag;
import com.ness.fw.common.auth.ElementAuthLevel;
import com.ness.fw.common.exceptions.ResourceException;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.flower.core.*;
import com.ness.fw.flower.util.*;

public class FWToolBarTag extends AbstractModelTag
{
	private static final String JS_TOOLBAR_EVENT_FUNCTION = "toolBarEvent";

	private String enabledClassName;
	private String disabledClassName;
	private String spaceClassName;
	private String largeSpaceClassName;
	private String currentButtonStateString = "";	
	private String group;
	private String toolBarName;
	private boolean isFirstButtonRendered = false;
	
	
	private ToolBarModel toolBarModel;
	
	protected void initModel() throws UIException
	{
		if (toolBarModel == null && id != null)
		{
			toolBarModel = (ToolBarModel)FlowerUIUtil.getObjectFromContext(getHttpRequest(),id);
			if (toolBarModel != null)
			{
				//important to use when 2 tags uses the same model,in order
				//to prevent a conflict in authorization level between 2 
				//buttons with the same name,but different authorization level.
				toolBarModel.clearAuth();
			}
			toolBarName = id;
		}
		else
		{
			toolBarName = String.valueOf(Math.random());
		}
		initState();
	}
	
	protected void initCss()
	{
		enabledClassName = initUIProperty(enabledClassName,"ui.toolbar.button.enabled");
		disabledClassName = initUIProperty(disabledClassName,"ui.toolbar.button.disabled");
		spaceClassName = initUIProperty(spaceClassName,"ui.toolbar.button.space");
		largeSpaceClassName = initUIProperty(largeSpaceClassName,"ui.toolbar.button.largeSpace");
	}
	
	protected void renderStartTag() throws UIException
	{
		try 
		{
			initCss();
			addRemark("start toolbar " + group);
			renderToolBarTable();	
			if (toolBarModel != null)
			{
				if (isFirstButtonRendered)
				{
					renderHiddenField();
				}
				toolBarModel.setAuthLevel(getAuthLevel());
			}
		}
		catch (ContextException ce)
		{
			throw new UIException(ce);
		}
		catch (ResourceException re)
		{
			throw new UIException(re);
		}
		catch (FlowException fe)
		{
			throw new UIException(fe);
		}
		
	}
	
	private void renderToolBarTable() throws ContextException, UIException, ResourceException, FlowException 
	{
		renderToolBar();
		if (isFirstButtonRendered)
		{
			renderToolBarTableEnd();
		}
	}
	
	private void renderToolBarTableStart()
	{
		startTag(TABLE);
		addAttribute(CELLPADDING,"0");
		addAttribute(CELLSPACING,"0");
		addAttribute(BORDER,0);
		endTagLn();
		startTag(ROW);
		endTagLn();		
	}
	
	private void renderToolBarTableEnd()
	{
		endTagLn(ROW);
		endTagLn(TABLE);		
	}
	
	/**
	 * Renders the buttons of the toolbar
	 * @throws FlowException
	 * @throws UIException
	 */
	private void renderToolBar() throws FlowException, UIException
	{
		//Retrieve the list of button extra data.Each buttonExtraData includes
		//the ButtonData object,its Flow and its pageElement.
		ButtonExtraDataList buttonExtraDataList = FlowerUIUtil.getCurrentFlow(getHttpRequest()).getButtonExtraDataList(group);

		//Saves the current rendered button data
		ButtonData prevButton = null;
		
		//Iterate the list of button extra data.
		for (int index = 0;index < buttonExtraDataList.getButtonCount();index++)
		{
			//Get the current button extra data
			ButtonExtraData buttonExtraData = buttonExtraDataList.getButtonExtraData(index);
			//Get the current button data
			ButtonData buttonData = buttonExtraData.getButtonData();
			//Get the flow of the button
			Flow buttonFlow = buttonExtraData.getButtonFlow();
			//Get the pageElement of the flow
			PageElementAuthLevel pageElement = buttonExtraData.getPageElement();
			//Get the authorization level from the pageElement 
			int authLevel = FlowerUIUtil.getAuthLevelInformation((HttpServletRequest)pageContext.getRequest(), pageElement,"ToolBarTag - flow " + buttonFlow.getName());
			//If the authorization level of the flow is not none,render the button
			if (authLevel != ElementAuthLevel.AUTH_LEVEL_NONE || ignoreAuth)
			{	
				//Get the ElementAuthLevel of the button
				ElementAuthLevel elementAuth = FlowerUIUtil.getAuthLevelInformation(buttonData.getAuthId(),getHttpRequest(),"ToolBarTag " + buttonData.getEventName());
				//Get the state of the button - hidden,disabled or enabled
				String buttonState = getButtonState(buttonFlow,buttonData,elementAuth);
				//Set the authorization level of the button - relevant only
				//if there is a model
				setButtonAuth(buttonData.getName(),buttonData.getButtonSetName(),FlowerUIUtil.getAuthLevel(elementAuth),buttonData.getEventType());
				
				//If the authorization level of the button allows it,render the button
				if ((FlowerUIUtil.getAuthLevel(elementAuth) != FlowerUIUtil.AUTH_LEVEL_NONE || ignoreAuth)
					&& (!buttonState.equals(UIConstants.COMPONENT_HIDDEN_STATE) || ignoreState))
				{
					//Render button
					if (buttonData.getType() == ButtonData.TYPE_BUTTON)
					{
						//Build CustomEvent for the button,based on the 
						//ButtonData
						CustomEvent toolBarButtonEvent = new CustomEvent();
						toolBarButtonEvent.setDirtyModels(buttonData.getCheckDirtyModelIds());
						toolBarButtonEvent.setCheckDirty(buttonData.isCheckDirtyFlag());
						toolBarButtonEvent.setCheckWarnings(buttonData.isCheckWarnings());
						toolBarButtonEvent.setAllowEventOnDirtyIds(buttonData.isAllowEventOnDirtyIds());
						toolBarButtonEvent.setConfirmMessageId(buttonData.getConfirmMessageId());
						toolBarButtonEvent.setEventTargetType(buttonData.getOpenWindowType());
					
						//Render the wrapper table, only if first button is being rendered
						if (!isFirstButtonRendered)
						{
							isFirstButtonRendered = true;
							renderToolBarTableStart();		
						}		
						//Render normal space only if previous button is not a spacer
						else
						{
							if (prevButton != null && prevButton.getType() != ButtonData.TYPE_SPACER)
							{
								renderToolBarSpace(false);
							}
						}	
					
						//Render the toolbar button	itself			
						renderToolBarButton(buttonData.getName(),buttonData.getButtonSetName(),buttonData.getEventName(),buttonData.getOpenWindowType(),buttonFlow,buttonFlow.getFlowPathString(),buttonFlow.getCurrentState().getName(),buttonState,toolBarButtonEvent,buttonData.getWindowExtraParams(),buttonData.getShortCutKey());
					}
					//Render spacer(large space)
					else
					{	
						//Render spacer only if 
						//1.The first button of the toolbar was already rendered 
						//2.The spacer is not the last item in the toolbar
						//3.The previous item is a button and it was rendered
						if (isFirstButtonRendered && index != buttonExtraDataList.getButtonCount() - 1)
						{
							if ((prevButton == null || prevButton.getType() == ButtonData.TYPE_BUTTON))
							{
								renderToolBarSpace(true);
							}
						}
					}
					//saves the current rendered button
					prevButton = buttonData;				
				}
				//Removes the authorization level of the button 			
				FlowerUIUtil.removeAuthLevel(elementAuth,getHttpRequest(),"ToolBarTag " + buttonData.getEventName());
			}	
			//Removes the authorization level of the button's flow
			FlowerUIUtil.removeAuthLevel(pageElement,(HttpServletRequest)pageContext.getRequest(),"ToolBarTag - flow " + buttonFlow.getName());					
		}		
	}
	
	private ArrayList renderFlowButtons(Flow currentFlow,ArrayList buttons) throws ContextException, ResourceException, UIException
	{
		FlowState state = currentFlow.getCurrentState();
		Iterator subFlowIdsIterator = currentFlow.getHierarchySubFlowIdsIterator();
		Flow subFlow;
		while (subFlowIdsIterator.hasNext())
		{
			subFlow = currentFlow.getSubFlowById((String)subFlowIdsIterator.next());
			renderFlowButtons(subFlow,buttons);
		}		
		if (state.getType() == FlowState.STATE_TYPE_COMPLEX)
		{
			ButtonSet buttonSet = ((ComplexFlowState)state).getButtonSet(group);
			if (buttonSet != null && buttonSet.getCount() > 0)
			{
				PageElementAuthLevel pageElement = currentFlow.getCurrentStatePageElement();
				int authLevel = FlowerUIUtil.getAuthLevelInformation((HttpServletRequest)pageContext.getRequest(), pageElement,"ToolBarTag - flow " + currentFlow.getName());
				if (authLevel != ElementAuthLevel.AUTH_LEVEL_NONE || ignoreAuth)
				{	
					buttons.add(getButtonsetButtons(buttonSet));			
					renderToolBar(buttonSet,currentFlow,currentFlow.getFlowPathString(),currentFlow.getCurrentState().getName());
				}
				FlowerUIUtil.removeAuthLevel(pageElement,(HttpServletRequest)pageContext.getRequest(),"ToolBarTag - flow " + currentFlow.getName());
			}
		}
		return buttons;
	}
	
	private ArrayList getButtonsetButtons(ButtonSet buttonSet)
	{
		ArrayList buttons = new ArrayList();
		for (int index = 0;index < buttonSet.getCount();index++)
		{
			buttons.add(buttonSet.getButtonData(index));
		}
		return buttons;	
	}
	
	private void renderToolBar(ButtonSet buttonSet,Flow flow,String flowPath,String flowState) throws UIException
	{
		for (int i = 0; i < buttonSet.getCount();i++)
		{
			ButtonData prevButton = null;
			ButtonData buttonData = buttonSet.getButtonData(i);
			//Get the previous button in order to check if it is a spacer
			if (i != 0)
			{
				prevButton = buttonSet.getButtonData(i - 1);
			}
			//Render button
			if (buttonData.getType() == ButtonData.TYPE_BUTTON)
			{
				ElementAuthLevel elementAuth = FlowerUIUtil.getAuthLevelInformation(buttonData.getAuthId(),getHttpRequest(),"ToolBarTag " + buttonData.getEventName());
				String buttonState = getButtonState(flow,buttonData,elementAuth);
				setButtonAuth(buttonData.getName(),buttonData.getButtonSetName(),FlowerUIUtil.getAuthLevel(elementAuth),buttonData.getEventType());
				if ((FlowerUIUtil.getAuthLevel(elementAuth) != FlowerUIUtil.AUTH_LEVEL_NONE || ignoreAuth)
					&& (!buttonState.equals(UIConstants.COMPONENT_HIDDEN_STATE) || ignoreState))
				{
					CustomEvent toolBarButtonEvent = new CustomEvent();
					toolBarButtonEvent.setDirtyModels(buttonData.getCheckDirtyModelIds());
					toolBarButtonEvent.setCheckDirty(buttonData.isCheckDirtyFlag());
					toolBarButtonEvent.setCheckWarnings(buttonData.isCheckWarnings());
					toolBarButtonEvent.setAllowEventOnDirtyIds(buttonData.isAllowEventOnDirtyIds());
					toolBarButtonEvent.setConfirmMessageId(buttonData.getConfirmMessageId());
					toolBarButtonEvent.setEventTargetType(buttonData.getOpenWindowType());
					
					//Render the wrapper table, only if first button is being rendered
					if (!isFirstButtonRendered)
					{
						isFirstButtonRendered = true;
						renderToolBarTableStart();		
					}		
					else
					{
						if (prevButton != null && prevButton.getType() != ButtonData.TYPE_SPACER)
						{
							renderToolBarSpace(false);
						}
					}	
					
					//Render the toolbar button				
					renderToolBarButton(buttonData.getName(),buttonData.getButtonSetName(),buttonData.getEventName(),buttonData.getOpenWindowType(),flow,flowPath,flowState,buttonState,toolBarButtonEvent,buttonData.getWindowExtraParams(),buttonData.getShortCutKey());
				}				
				FlowerUIUtil.removeAuthLevel(elementAuth,getHttpRequest(),"ToolBarTag " + buttonData.getEventName());
			}
			//Render spacer
			else
			{	
				//Render spacer only if 
				//1.The first button of the toolbar was already rendered 
				//2.The spacer is not the last item in the toolbar
				//3.The previous item is not a spacer
				if (isFirstButtonRendered && i != buttonSet.getCount() - 1)
				{
					if (prevButton == null || prevButton.getType() == ButtonData.TYPE_BUTTON)
					{
						renderToolBarSpace(true);
					}
				}
			}
		}		
	}
	
	private String getButtonState(Flow currentFlow,ButtonData buttonData,ElementAuthLevel elementAuth) throws UIException
	{
		String buttonState = state;
		String stateModelId = buttonData.getStateModelId();  
		if (stateModelId != null)
		{
			//DisplayStateModel displayStateModel = (DisplayStateModel)FlowerUIUtil.getObjectFromContext(getHttpRequest(),stateModelId);
			DisplayStateModel displayStateModel = (DisplayStateModel)FlowerUIUtil.getObjectFromFlowContext(currentFlow,stateModelId);
			if (displayStateModel == null)
			{
				throw new UIException("button " + buttonData.getName() + " of toolbar with stateModelId " + stateModelId + " does not have a model in the context");
			}
			if (isStricterState(displayStateModel.getState(),buttonState))
			{
				buttonState = displayStateModel.getState();
			}
		}
		if (toolBarModel != null)
		{
			String toolBarModelButtonState = toolBarModel.getButtonState(buttonData.getName(),buttonData.getButtonSetName());
			if (isStricterState(toolBarModelButtonState,buttonState))
			{
				buttonState = toolBarModelButtonState;
			}
		}
		
		//set the button state's string
		//if (!isEventRendered(elementAuth,buttonData.getEventType(),state))
		if (!isEventRendered(elementAuth,buttonData.getEventType(),buttonState,""))
		{
			currentButtonStateString = DISABLED;
		}
		else
		{
			currentButtonStateString = "";
		}
		return buttonState;		
	}
	
	private void setButtonAuth(String buttonName,String buttonSet,int buttonAuthLevel,int buttonEventType)
	{
		if (toolBarModel != null)
		{
			toolBarModel.setButtonAuthLevel(buttonName,buttonSet,buttonAuthLevel,buttonEventType);
		}
	}
	
	private void renderToolBarButton(String buttonName,String buttonSet,String eventName,String buttonTargetType,Flow flow,String flowPath,String flowState,String buttonState,CustomEvent toolbarButtonEvent,String windowExtraParams,String shortCutKey) throws UIException
	{
		startTag(CELL);
		if (buttonState.equals(ToolBarModel.BUTTON_STATE_HIDDEN))
		{
			addAttribute(STYLE,DISPLAY_NONE);
		}		
		endTag();
		startTag(INPUT);
		addAttribute(TYPE,BUTTON);
		addAttribute(ID,toolBarName + buttonName);
		addAttribute(CLASS,getClassByState(buttonName,buttonSet,buttonState));	
		addAttribute(VALUE,getLocalizedText(buttonName));
		addAttribute(ONCLICK);
		append(QUOT);
		if (toolBarModel != null)
		{
			renderToolBarEvent(buttonName,buttonSet);
			append(";");
			if (toolBarModel.getButtonOnClickScript(buttonName,buttonSet) != null)
			{
				append(toolBarModel.getButtonOnClickScript(buttonName,buttonSet));
			}
		}
		renderToolBarButtonEvent(eventName,buttonTargetType,flow,flowPath,flowState,toolbarButtonEvent,windowExtraParams,buttonName);
		append(QUOT);
		append(currentButtonStateString);
		endTag();
		endTagLn(CELL);
		addToolBarButtonShortCut(buttonName,shortCutKey);
	}
	
	private void renderToolBarSpace(boolean isLargeSpace)
	{
		startTag(CELL);
		addAttribute(CLASS,isLargeSpace ? largeSpaceClassName : spaceClassName);
		endTag();
		append(SPACE);
		endTagLn(CELL);		
	}
	
	private void addToolBarButtonShortCut(String buttonName,String shortCutKey) throws UIException
	{
		if (shortCutKey != null)
		{
			addShortCutKey(shortCutKey,toolBarName + buttonName,true,true);
		}
	}
	
	private void renderToolBarButtonEvent(String eventName,String buttonTargetType,Flow flow,String flowPath,String flowState,CustomEvent toolbarButtonEvent,String windowExtraParams,String buttonName) throws UIException
	{
		String componentDesc = "toolbar button " + buttonName;
		if (id != null)
		{
			componentDesc += " id " + id;
		}		
		checkDirtyFlag(toolbarButtonEvent);
		FlowerUIUtil.addAuthorizedEvent(eventName,flow,componentDesc);
		if (toolbarButtonEvent.isCheckWarnings())
		{
			FlowerUIUtil.addAuthorizedEvent(eventName + Event.WARNING_EVENT_SUFFIX,flow,componentDesc);
		}
		renderFunctionCall
		(
			getEventJsFunctionName(buttonTargetType),
			eventName + COMMA +
			flowPath + COMMA +
			flowState + COMMA +
			Event.getTargetNameByTargetType(buttonTargetType) + COMMA + 
			getEventDirtyFlagsAsString(toolbarButtonEvent,false,COMMA) + COMMA + 
			getEventWindowExtraParams(windowExtraParams,buttonTargetType),
			true
		);
	}

	private void renderToolBarEvent(String buttonName,String buttonSet)
	{
		renderFunctionCall(JS_TOOLBAR_EVENT_FUNCTION,id + COMMA + buttonName + COMMA + buttonSet,true);
	}
	
	private String getClassByState(String buttonName,String buttonSet,String buttonState)
	{
		String className = toolBarModel == null ? enabledClassName : toolBarModel.getButtonClassName(buttonName,buttonSet);
		if (className == null)
		{
			if (currentButtonStateString.trim().equals(ToolBarModel.BUTTON_STATE_DISABLED))
			{
				className = disabledClassName;
			}
			else
			{ 
				className = enabledClassName;
			}
		}
		return className;
	}
	
	protected void resetTagState()
	{
		enabledClassName = null;
		disabledClassName = null;		
		group = null;	
		toolBarModel = null;	
		isFirstButtonRendered = false;	
		super.resetTagState();		
	}
	
	protected void renderEndTag()
	{
		
	}
				
	/**
	 * @return
	 */
	public String getDisabledClassName()
	{
		return disabledClassName;
	}

	/**
	 * @return
	 */
	public String getEnabledClassName()
	{
		return enabledClassName;
	}

	/**
	 * @return
	 */
	public String getGroup()
	{
		return group;
	}

	/**
	 * @param string
	 */
	public void setDisabledClassName(String string)
	{
		disabledClassName = string;
	}

	/**
	 * @param string
	 */
	public void setEnabledClassName(String string)
	{
		enabledClassName = string;
	}

	/**
	 * @param string
	 */
	public void setGroup(String string)
	{
		group = string;
	}

	/**
	 * @param model
	 */
	public void setToolBarModel(ToolBarModel model)
	{
		toolBarModel = model;
	}
}
