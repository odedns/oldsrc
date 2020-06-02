package com.ness.fw.ui.taglib;

import java.util.ArrayList;

import com.ness.fw.ui.ButtonModel;
import com.ness.fw.ui.UIConstants;
import com.ness.fw.ui.events.*;
import com.ness.fw.shared.ui.*;
import com.ness.fw.common.exceptions.UIException;

public class FWButtonTag extends AbstractButtonTag
{
	protected final static String JS_BUTTON_EVENT = "buttonEvent";
		
	protected final static String JS_CONFIRM_SUBMIT_SCRIPT_START = "if (!";
	protected final static String JS_CONFIRM_SUBMIT_SCRIPT_END = ") return;";
	
	protected final static String HTML_BUTTON_SUFFIX = "Button";
	
	protected String eventName;
	protected String targetType = Event.EVENT_TARGET_TYPE_DEFAULT;
	protected String dialogParams = " ";
	private CustomEvent buttonCustomEvent;
	
	protected void initModel()  throws UIException
	{
		buttonCustomEvent = new CustomEvent();
		
		if (id != null)
		{
			buttonModel = (ButtonModel)FlowerUIUtil.getObjectFromContext(getHttpRequest(),id);
		}

		if (buttonModel != null)
		{
			if (eventName != null)
			{
				buttonCustomEvent.setEventName(eventName);
				buttonCustomEvent.setEventTargetType(targetType);
				buttonCustomEvent.setWindowExtraParams(dialogParams);
				buttonModel.setButtonClickEvent(buttonCustomEvent);
			}
			else if (buttonModel.getButtonClickEvent() != null)
			{
				buttonCustomEvent = buttonModel.getButtonClickEvent();
			}		

			if (buttonModel.getValue() != null)
			{
				value = buttonModel.getValue();
				if (buttonModel.isTranslateValue())
				{
					value = getLocalizedText(value);
				}
			}	
			else
			{
				value = getLocalizedText(value);		 
			}
		}
		
		else
		{
			if (eventName != null)
			{
				buttonCustomEvent.setEventName(eventName);
				buttonCustomEvent.setEventTargetType(targetType);
				buttonCustomEvent.setWindowExtraParams(dialogParams);
			}
			value = getLocalizedText(value);
		}
		
		initState();
		if (isEventRendered(buttonCustomEvent.getEventType()))
		{
			stateString = "";
		}
		else
		{
			stateString = DISABLED;
		}		
	}

	protected void setModelState()
	{
		if (buttonModel != null && buttonModel.getState() != null)
		{
			if (isStricterState(buttonModel.getState(),state))
			{
				state = buttonModel.getState();
			}			
		}			
	}
	
	protected void renderLink() throws UIException
	{
		String buttonFunction = null;
		boolean isDisableState = isDisabled();
		if (imageSrc != null)
		{
			startTag(IMAGE);
			addAttribute(SRC,getLocalizedImagesDir() + (isDisableState ? disableImageSrc : imageSrc));			
		}
		else
		{
			startTag(INPUT);
			addAttribute(TYPE,BUTTON);
			addAttribute(CLASS,getClassByState());
			addAttribute(VALUE,value);						
		}
		
		if (id != null)
		{
			addAttribute(ID,id + HTML_BUTTON_SUFFIX);
		}
		if (tooltip != null)
		{
			addAttribute(TITLE,getLocalizedText(tooltip));
		}
		renderStyle();
		renderConfirmSubmit();
		if (state.equals(UIConstants.COMPONENT_ENABLED_STATE))
		{
			buttonFunction = onClick + getButtonEventFunction() + getSendEventFunction();
			addAttribute(ONCLICK,buttonFunction);
		}			
		append(stateString);
		endTag();
		if (shortCutKey != null && buttonFunction != null)
		{
			addShortCutKey(shortCutKey,buttonFunction,true);
		}		
	}
	
	protected void renderConfirmSubmit()
	{
		if (confirmSubmit != null)
		{
			onClick = JS_CONFIRM_SUBMIT_SCRIPT_START + confirmSubmit + JS_CONFIRM_SUBMIT_SCRIPT_END;
		}		
	}
	
	protected String getSendEventFunction() throws UIException
	{
		if (buttonCustomEvent.getEventName() != null)
		{
			checkDirtyFlag(buttonCustomEvent);
			ArrayList jsParams = new ArrayList();
			AuthorizedEventData authorizedEventData = addAuthorizedEvent(buttonCustomEvent.getEventName(),buttonCustomEvent.getEventTargetType(),false,buttonCustomEvent.isCheckWarnings()); 
			
			//Add the event's parameters : 
			//eventName,eventName,flowPath,flowState,eventTargetType,dialogParams
			jsParams.addAll(getEventCoreParametersList(buttonCustomEvent.getEventName(),buttonCustomEvent,authorizedEventData));
		
			//Add the dirty flag's parameters : 
			//eventTargetType,checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage
			jsParams.addAll(getEventDirtyFlagsList(buttonCustomEvent));
			
			return getFunctionCall(JS_SEND_EVENT_GLOBAL,jsParams,true);
		}
		return "";
	}
	
	protected void resetTagState()
	{
		eventName = null;
		targetType = Event.EVENT_TARGET_TYPE_DEFAULT;
		dialogParams = " ";
		buttonCustomEvent = null;
		super.resetTagState();
	}
	
	protected String getButtonEventFunction()
	{
		if (buttonModel != null)
		{
			return getFunctionCall(JS_BUTTON_EVENT,id,true) + ";";
		}
		else
		{
			return "";
		}
	}
	
	/**
	 * @return
	 */
	public String getTargetType()
	{
		return targetType;
	}

	/**
	 * @return
	 */
	public String getEventName()
	{
		return eventName;
	}

	/**
	 * @param string
	 */
	public void setTargetType(String targetType)
	{
		this.targetType = targetType;
	}

	/**
	 * @param string
	 */
	public void setEventName(String eventName)
	{
		this.eventName = eventName;
	}

	/**
	 * @return
	 */
	public String getDialogParams()
	{
		return dialogParams;
	}

	/**
	 * @param string
	 */
	public void setDialogParams(String string)
	{
		dialogParams = string;
	}
}
