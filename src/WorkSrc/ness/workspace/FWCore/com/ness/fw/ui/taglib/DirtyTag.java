/*
 * Created on 09/05/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ness.fw.ui.taglib;

import java.util.Enumeration;
import java.util.Stack;

import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.shared.ui.FlowerUIUtil;
import com.ness.fw.ui.DirtyModel;

public class DirtyTag extends AbstractModelTag 
{
	private DirtyModel dirtyModel;
	
	private final static String JS_ADD_DIRTY_FLAG = "addDirtyFlag";
	
	public DirtyTag()
	{
		ignoreAuth = true;
		initAuth = false;
	}
	
	protected void initModel() throws UIException 
	{
		startTagReturnValue = EVAL_BODY_INCLUDE;
		dirtyModel = (DirtyModel)FlowerUIUtil.getObjectFromContext(getHttpRequest(),id);
		if (dirtyModel == null)
		{
			throw new UIException("no dirty model exists in context's field " + id);
		}
	}

	protected void renderStartTag() throws UIException 
	{
		Stack dirtyStack = (Stack)getRequestContextValue(REQUEST_ATTRIBUTE_DIRTY);
		if (dirtyStack == null)
		{
			dirtyStack = new Stack();
		}		
		dirtyStack.push(id);
		setRequestContextValue(REQUEST_ATTRIBUTE_DIRTY,dirtyStack);
		renderHiddenField(DirtyModel.DIRTY_MODEL_ISDIRTY_PROPERTY + "=" + dirtyModel.getDirtyIndication());
		appendln();
		renderAddDirtyFlagToTree(dirtyStack);
		appendln();
	}

	protected void resetTagState()
	{
		dirtyModel = null;
		super.resetTagState();
	}

	protected void renderEndTag() throws UIException 
	{
		appendToEnd();
		Stack dirtyStack = (Stack)getRequestContextValue(REQUEST_ATTRIBUTE_DIRTY);
		dirtyStack.pop();
		if (dirtyStack.isEmpty())
		{
			pageContext.getRequest().removeAttribute(REQUEST_ATTRIBUTE_DIRTY);	
		}	
	}
	
	private void renderAddDirtyFlagToTree(Stack dirtyStack) throws UIException
	{
		startTag(SCRIPT,true);
		renderFunctionCall(JS_ADD_DIRTY_FLAG,getDirtyFlagPath(dirtyStack) + COMMA + getDirtyFlagJSObject(dirtyStack),false);
		endTag(SCRIPT);
		appendln();
	}
	
	private String getDirtyFlagPath(Stack dirtyStack)
	{
		//add to the root of the dirty tree
		if (dirtyStack.size() == 1)
		{
			return "null";
		}
		//build the path(parents) to the new dirty flag to add to the dirty tree
		else
		{
			String path = "";
			Enumeration enum = dirtyStack.elements();
			int counter = 0;
			while (counter < dirtyStack.size() - 1)
			{
				path += (String)enum.nextElement();
				if (counter < dirtyStack.size() - 2)
				{
					path += SEPERATOR;
				}
				counter++;
			}
			return getSingleQuot(path);
		}
	}

	private String getDirtyFlagJSObject(Stack dirtyStack) throws UIException
	{
		String messageId = dirtyModel.getMessageId() == null ? "ui.general.message.confirmDirtyAreas" : dirtyModel.getMessageId();
		return getFunctionCall(JS_NEW_DIRTY_FLAG,id + COMMA + getLocalizedText(messageId,true),true);
	}
	
}
