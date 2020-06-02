package com.ness.fw.ui.taglib;

import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.shared.ui.FlowerUIUtil;
import com.ness.fw.ui.DisplayStateModel;
import com.ness.fw.ui.UIConstants;

import java.util.Stack;

public class DisplayStateTag extends AbstractModelTag
{
	private String replacementHtml;
	private boolean overrideParentState = false;
	private boolean overrideParentStateDeafult = false;
	private DisplayStateModel displayStateModel = null;
	
	private final static String PROPERTY_KEY_OVERRIDE_PARENT_STATE = "ui.displayState.overrideParentState";

	/* (non-Javadoc)
	 * @see com.ness.fw.ui.taglib.AbstractModelTag#initModel()
	 */
	protected void initModel() throws UIException 
	{
		startTagReturnValue = EVAL_BODY_INCLUDE;
		displayStateModel = (DisplayStateModel)FlowerUIUtil.getObjectFromContext(getHttpRequest(),id);
		if (displayStateModel == null)
		{
			throw new UIException("no state model exists in context's field " + id);
		}
		if (displayStateModel.getState().equals(UIConstants.COMPONENT_HIDDEN_STATE))
		{
			startTagReturnValue = SKIP_BODY;
			setRenderTag(false);
			if (replacementHtml != null)
			{
				unauthorizedMessage = replacementHtml;	
			}			
		}
		
		overrideParentStateDeafult = new Boolean(getUIProperty(PROPERTY_KEY_OVERRIDE_PARENT_STATE)).booleanValue();
	}

	/* (non-Javadoc)
	 * @see com.ness.fw.ui.taglib.UITag#renderStartTag()
	 */
	protected void renderStartTag() throws UIException 
	{
		//Get the state stack from the request
		Stack stateStack = (Stack)getRequestContextValue(REQUEST_ATTRIBUTE_DISPLAY_STATE);
		
		//If it is the first DisplayTag in the request,the state stack is null
		//and should be created
		if (stateStack == null)
		{
			stateStack = new Stack();
		}
				
		//Get the last state in the stack,which is the state of the parent of
		//this tag. 
		String parentState = stateStack.isEmpty() ? null : (String)stateStack.peek();
		
		//If this tag does not have a parent(the first DisplayTag of the reuquest)
		//push its state to the stack without checking
		if (parentState == null || overrideParentStateDeafult)
		{
			stateStack.push(displayStateModel.getState());
		}
		
		//If this tag has a parent,check its state
		else
		{
			//If this tag overrides its parent state,push its state to the stack
			if (overrideParentState)
			{
				stateStack.push(displayStateModel.getState());
			}
			//If this tag inherits its parent state,check if the parent's state is
			//stricter
			else
			{
				//If the parent's state is stricter insert it to the state stack
				if (isStricterState(parentState,displayStateModel.getState()))
				{
					stateStack.push(parentState);
				}
				//If the parent's state is not stricter insert the tags's state
				//to the state stack
				else
				{
					stateStack.push(displayStateModel.getState());
				}
			}
		}

		setRequestContextValue(REQUEST_ATTRIBUTE_DISPLAY_STATE,stateStack);
	}

	/* (non-Javadoc)
	 * @see com.ness.fw.ui.taglib.UITag#renderEndTag()
	 */
	protected void renderEndTag() throws UIException 
	{
		Stack stateStack = (Stack)getRequestContextValue(REQUEST_ATTRIBUTE_DISPLAY_STATE);
		stateStack.pop();
		if (stateStack.isEmpty())
		{
			removeRequestContextValue(REQUEST_ATTRIBUTE_DISPLAY_STATE);	
		}
	}
	
	protected void resetTagState()
	{
		super.resetTagState();
		overrideParentState = false;
		overrideParentStateDeafult = false;
	}
		
	/**
	 * @return
	 */
	public String getReplacementHtml() {
		return replacementHtml;
	}

	/**
	 * @param string
	 */
	public void setReplacementHtml(String string) {
		replacementHtml = string;
	}

	/**
	 * @param b
	 */
	public void setOverrideParentState(boolean overrideParentState) 
	{
		this.overrideParentState = overrideParentState;
	}

}
