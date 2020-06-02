package com.ness.fw.ui.taglib;

import com.ness.fw.common.exceptions.*;

import com.ness.fw.shared.ui.FlowerUIUtil;
import com.ness.fw.ui.FormParametersModel;
import com.ness.fw.ui.UIConstants;
import com.ness.fw.ui.events.CustomEvent;
import com.ness.fw.ui.events.Event;
import com.ness.fw.util.StringFormatterUtil;

public class FormParametersTag extends AbstractModelTag
{
	protected FormParametersModel formParametersModel;
	protected String srcFormName;
	protected String inputParameters;
	protected String outputParameters;
	protected String value;
	protected String imageSrc;
	protected String disableImageSrc;
	protected String eventName;
	protected String target = " ";
	protected String buttonClassName;
	protected String buttonDisabledClassName;
	protected String action = " ";
	protected boolean openDialog = false;
	protected String dialogParams = " ";
	protected String callBackFunction = " ";
	protected String callBackEvent = " ";
	protected String tooltip;
	protected int eventType = Event.EVENT_TYPE_DEFAULT;
		
	protected final static String JS_CREATE_FORM = "createFormParameters";
	protected final static String JS_RETURN_FORM = "returnFormParameters";
	
	protected void initModel() throws UIException 
	{
		if (id != null)
		{
			formParametersModel = (FormParametersModel)FlowerUIUtil.getObjectFromContext(getHttpRequest(),id);
		}
		if (formParametersModel != null)
		{
			if (eventName == null && formParametersModel.getButtonClickEvent() != null)
			{
				eventName = formParametersModel.getButtonClickEvent().getEventName();
				eventType = formParametersModel.getButtonClickEvent().getEventType();
			}
			else
			{
				formParametersModel.setButtonClickEvent(new CustomEvent(eventName));
			}
			state = (formParametersModel.getState() == null) ? state : formParametersModel.getState();
		}
		if (eventName == null)
		{
			throw new UIException("eventName is null in tag formParameters id " + id);
		}		
		if (isEventRendered(eventType))
		{
			stateString = "";	
		}	
		else
		{
			stateString = DISABLED;
		}		
		
		initState();	
	}

	protected void setModelState()
	{
		if (formParametersModel != null && formParametersModel.getState() != null)
		{
			if (isStricterState(formParametersModel.getState(),state))
			{
				state = formParametersModel.getState();
			}			
		}			
	}
		
	protected void initCss()
	{
		buttonClassName = initUIProperty(buttonClassName,"ui.formParameters.button.enabled");
		buttonDisabledClassName = initUIProperty(buttonDisabledClassName,"ui.formParameters.button.disabled");
	}
	
	protected void renderStartTag() throws UIException
	{
		initCss();
		if (!openDialog && !dialogParams.equals(" "))
		{
			dialogParams = StringFormatterUtil.replace(dialogParams,",",COMMA_HTML);
		}
		renderButton();		
		if (formParametersModel != null)
		{
			formParametersModel.setAuthLevel(getAuthLevel());
			renderHiddenField();
		}
	}
	
	protected void renderButton() throws UIException
	{
		boolean isDisableState = isDisabled();
		if (!isDisableState && imageSrc == null || isDisableState &&  disableImageSrc == null)
		{
			startTag(INPUT);
			addAttribute(TYPE,BUTTON);
			addAttribute(VALUE,getLocalizedText(value));
			if (tooltip != null)
			{
				addAttribute(TITLE,getLocalizedText(tooltip));
			}
		}
		else
		{
			startTag(IMAGE);
			addAttribute(SRC,getLocalizedImagesDir() + (isDisableState ? disableImageSrc : imageSrc));
			if (tooltip != null)
			{
				addAttribute(ALT,getLocalizedText(tooltip));
			}	
		}		
		addAttribute(CLASS,getClassByState());
		if (!isDisableState)
		{
			addAttribute(ONCLICK);
			append(QUOT);
			renderJsCallCreateForm();
			append(QUOT);
		}
		if (state.equals(UIConstants.COMPONENT_HIDDEN_STATE))
		{
			addAttribute(STYLE,DISPLAY_NONE);
		}		
		append(stateString);	
		endTag();	
	}
	
	protected void renderJsCallCreateForm() throws UIException
	{
		renderFunctionCall
		(
			JS_CREATE_FORM,
			(formParametersModel == null ? " " : id) + COMMA +  
			srcFormName + COMMA + 
			inputParameters + COMMA + 
			outputParameters + COMMA + 
			eventName + COMMA + 
			action + COMMA +
			openDialog + COMMA + 
			dialogParams
		);
	}
	
	protected void renderJsCallReturnValues()
	{
		renderFunctionCall(JS_RETURN_FORM,String.valueOf(openDialog));
	}
	
	protected String getClassByState()
	{
		return (stateString.trim().equals(UIConstants.COMPONENT_DISABLED_STATE) ? buttonDisabledClassName : buttonClassName);
	}	
		
	protected void resetTagState()
	{
		formParametersModel = null;
		srcFormName = null;
		inputParameters = null;
		outputParameters = null;
		value = null;
		imageSrc = null;
		eventName = null;
		target = " ";
		buttonClassName = null;
		action = " ";
		openDialog = false;
		dialogParams = " ";
		callBackFunction = " ";
		callBackEvent = " ";
		tooltip = null;	
		super.resetTagState();		
	}
	
	protected void renderEndTag() throws UIException
	{	
		
	}
	
	/**
	 * Returns the inputParameters.
	 * @return String
	 */
	public String getInputParameters()
	{
		return inputParameters;
	}
	/**
	 * Returns the outputParameters.
	 * @return String
	 */
	public String getOutputParameters()
	{
		return outputParameters;
	}
	/**
	 * Returns the srcFormName.
	 * @return String
	 */
	public String getSrcFormName()
	{
		return srcFormName;
	}
	/**
	 * Sets the inputParameters.
	 * @param inputParameters The inputParameters to set
	 */
	public void setInputParameters(String inputParameters)
	{
		this.inputParameters = inputParameters;
	}
	/**
	 * Sets the outputParameters.
	 * @param outputParameters The outputParameters to set
	 */
	public void setOutputParameters(String outputParameters)
	{
		this.outputParameters = outputParameters;
	}
	/**
	 * Sets the srcFormName.
	 * @param srcFormName The srcFormName to set
	 */
	public void setSrcFormName(String srcFormName)
	{
		this.srcFormName = srcFormName;
	}
	/**
	 * Returns the value.
	 * @return String
	 */
	public String getValue()
	{
		return value;
	}
	/**
	 * Sets the value.
	 * @param value The value to set
	 */
	public void setValue(String value)
	{
		this.value = value;
	}
	/**
	 * Returns the clickEventName.
	 * @return String
	 */
	public String getEventName()
	{
		return eventName;
	}
	/**
	 * Sets the clickEventName.
	 * @param clickEventName The clickEventName to set
	 */
	public void setEventName(String eventName)
	{
		this.eventName = eventName;
	}

	/**
	 * Returns the target.
	 * @return String
	 */
	public String getTarget()
	{
		return target;
	}
	/**
	 * Sets the target.
	 * @param target The target to set
	 */
	public void setTarget(String target)
	{
		this.target = target;
	}
	/**
	 * Returns the buttonClassName.
	 * @return String
	 */
	public String getButtonClassName()
	{
		return buttonClassName;
	}

	/**
	 * Sets the buttonClassName.
	 * @param buttonClassName The buttonClassName to set
	 */
	public void setButtonClassName(String buttonClassName)
	{
		this.buttonClassName = buttonClassName;
	}

	/**
	 * Returns the action.
	 * @return String
	 */
	public String getAction()
	{
		return action;
	}

	/**
	 * Sets the action.
	 * @param action The action to set
	 */
	public void setAction(String action)
	{
		this.action = action;
	}

	/**
	 * @return
	 */
	public boolean isOpenDialog()
	{
		return openDialog;
	}

	/**
	 * @param b
	 */
	public void setOpenDialog(boolean b)
	{
		openDialog = b;
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

	/**
	 * @return
	 */
	public String getCallBackFunction()
	{
		return callBackFunction;
	}

	/**
	 * @param string
	 */
	public void setCallBackFunction(String string)
	{
		callBackFunction = string;
	}

	/**
	 * @return
	 */
	public String getImageSrc()
	{
		return imageSrc;
	}

	/**
	 * @param string
	 */
	public void setImageSrc(String string)
	{
		imageSrc = string;
	}

	/**
	 * @return
	 */
	public String getCallBackEvent()
	{
		return callBackEvent;
	}

	/**
	 * @param string
	 */
	public void setCallBackEvent(String string)
	{
		callBackEvent = string;
	}

	/**
	 * @return
	 */
	public String getTooltip()
	{
		return tooltip;
	}

	/**
	 * @param string
	 */
	public void setTooltip(String string)
	{
		tooltip = string;
	}

	/**
	 * @return
	 */
	public FormParametersModel getFormParametersModel() 
	{
		return formParametersModel;
	}

	/**
	 * @param model
	 */
	public void setFormParametersModel(FormParametersModel model) 
	{
		formParametersModel = model;
	}

	/**
	 * @param string
	 */
	public void setDisableImageSrc(String string) 
	{
		disableImageSrc = string;
	}
	/**
	 * @return
	 */
	public String getButtonDisbledClassName() {
		return buttonDisabledClassName;
	}

	/**
	 * @param string
	 */
	public void setButtonDisbledClassName(String string) {
		buttonDisabledClassName = string;
	}

}
