package com.ness.fw.ui.taglib;

import java.util.ArrayList;

import com.ness.fw.shared.ui.FlowerUIUtil;
import com.ness.fw.ui.ListModel;
import com.ness.fw.ui.UIConstants;
import com.ness.fw.ui.data.SelectionTagData;
import com.ness.fw.ui.events.*;
import com.ness.fw.common.exceptions.UIException;

public class SelectionTag extends AbstractListTag
{
	private final static String SELECTION_SUFFIX = "Selection";
	protected final static String JS_SELECTION_EVENT = "selectionEvent";
	protected final static String JS_SELECTION_SUBMIT_EVENT = "selectionSubmitEvent";
	protected final static String JS_SELECTION_COMPLEX_EVENT = "selectionComplexEvent";
	private final static String JS_SELECTION_RADIO = "radioClick";

	protected String type = UIConstants.SELECTION_SINGLE;
	protected String orientation = UIConstants.SELECTION_VERTICAL;
	protected String clickEventName;
	protected boolean breakable = false;
	protected CustomEvent clickCustomEvent;

	public SelectionTag()
	{
		dirtable = true;
	}
	
	/**
	 * returns the tag css prefix
	 * @return the tag css prefix 
	 */
	protected String getDefaultCssPrefix()
	{
		return getUIProperty("ui.selection." + type + ".prefix"); 
	}
	
	/**
	 * Initialize the list model and other properties of the selection tag
	 */
	protected void initModel() throws UIException
	{
		//getting the list model from the context
		if (listModel == null)
		{
			listModel = (ListModel)FlowerUIUtil.getObjectFromContext(getHttpRequest(),id);
		}
		//throw exception if model does mot exist
		if (listModel == null)
		{
			throw new UIException("no list model exists in context's field " + id);
		}
		
		//initialize the clickCustomEvent for this selection tag
		clickCustomEvent = new CustomEvent();		
		initEvents();
		
		//initialize the state of this list tag
		initState();

		//initialize more properties for this tag
		if (isEventRendered(clickCustomEvent.getEventType()))
		{
			stateString = "";
		}
		else
		{
			stateString = DISABLED;
		}
									
		//search for errors
		if (FlowerUIUtil.isErrorField(getHttpRequest(),id))
		{
			isError = true;
		}
		//set inputType to normal
		inputType = UIConstants.COMPONENT_NORMAL_INPUT_TYPE;
	}
	
	protected void initAttributes()
	{
		super.initAttributes();
		if (tagData != null)
		{
			SelectionTagData selectionTagData = (SelectionTagData)tagData;
			if (selectionTagData.getType() != null)
			{
				setType(selectionTagData.getType());
			}
			if (selectionTagData.getOrientation() != null)
			{
				setOrientation(selectionTagData.getOrientation());
			}
			if (selectionTagData.isBreakable() != null)
			{
				setBreakable(selectionTagData.isBreakable().booleanValue());
			}
			if (selectionTagData.getClickEventName() != null)
			{
				setClickEventName(selectionTagData.getClickEventName());
			}
		}
	}	

	protected void initEvents() throws UIException
	{
		if (clickEventName == null)
		{
			if (listModel.getListClickEvent() != null)
			{
				//initialize click event
				clickCustomEvent = listModel.getListClickEvent();
				
				//check event's flags
				checkDirtyFlag(clickCustomEvent);				
			}
		}
		else
		{
			clickCustomEvent.setEventName(clickEventName);
		}
	}

	protected void renderStartTag() throws UIException
	{
		startTag(TABLE);
		addAttribute(CELLSPACING,0);
		addAttribute(CELLPADDING,0);
		renderComponentWrapperStyle();
		addAttribute(ID,id + SELECTION_SUFFIX + COMPLEX_COMPONENT_SUFFIX);
		if (direction != null)
		{
			addAttribute(DIR,direction);	
		}
		if (!allowClientEnabling)
		{
			append(ENABLE_NOT_AUTHORIZED);
		}	
		endTag();
		if (orientation.equals(UIConstants.SELECTION_VERTICAL)) 
		{
			renderVertical();
		}
		else
		{
			renderHorizontal();
		}
		endTag(TABLE);
		renderHiddenField();	
		listModel.setAuthLevel(getAuthLevel());	
	}	
	
	protected void renderVertical() throws UIException
	{
		ArrayList keys = listModel.getKeys();
		for (int index = 0;index < keys.size();index++)
		{
			String key = (String)keys.get(index);
			String value = listModel.getValue(key);
			startTag(ROW,true);
			if (type.equals(UIConstants.SELECTION_MULTIPLE))
			{
				renderCheckBox(value,key,listModel.isValueSelected(key));
			}
			else
			{
				renderRadioButton(value,key,listModel.isValueSelected(key));
			}		
			endTag(ROW);	
		}
	}
	
	protected void renderHorizontal() throws UIException
	{
		ArrayList keys = listModel.getKeys();		
		startTag(ROW,true);
		for (int index = 0;index < keys.size();index++)
		{
			String key = (String)keys.get(index);
			String value = listModel.getValue(key);
			if (type.equals(UIConstants.SELECTION_MULTIPLE))
			{
				renderCheckBox(value,key,listModel.isValueSelected(key));
			}
			else
			{
				renderRadioButton(value,key,listModel.isValueSelected(key));
			}		
		}
		endTag(ROW);
	}
	
	protected void renderCheckBox(String label,String value,boolean isChecked) throws UIException
	{
		startTag(CELL,true);
		appendln();
		startTag(INPUT);
		addAttribute(ID,id + SELECTION_SUFFIX);	
		addAttribute(TYPE,CHECKBOX);
		addAttribute(VALUE,value);
		renderClickEvent();
		if (isChecked)
		{
			append(CHECKED);
		}
		append(stateString);
		renderSetDirtyProperty();
		endTag();
		appendln();
		endTag(CELL);
		appendln();				
		startTag(CELL);
		append(stateString);		
		renderClassByState();
		renderStyle(false);
		if (!breakable)
		{
			addAttribute(NOWRAP,NOWRAP);
		}
		endTag();
		startTag(NOBR,true);
		append(label);
		endTag(NOBR);
		endTag(CELL);
		appendln();
	}

	protected void renderRadioButton(String label,String value,boolean isChecked) throws UIException
	{
		startTag(CELL,true);
		appendln();
		startTag(INPUT);
		addAttribute(ID,id + SELECTION_SUFFIX);
		addAttribute(TYPE,RADIO_BUTTON);
		addAttribute(VALUE,value);
		onClick = getFunctionCall(JS_SELECTION_RADIO,"",false) + ";" + onClick;
		renderClickEvent();
		if (isChecked)
		{
			append(CHECKED);
			if (clickCustomEvent.getEventName() != null)
			{
				append(CURRENTLY_CHECKED);
			}
		}
		append(stateString);
		renderSetDirtyProperty();
		endTag();
		appendln();
		endTag(CELL);	
		appendln();							
		startTag(CELL);
		append(stateString);
		renderClassByState();
		renderStyle(false);
		if (breakable)
		{
			addAttribute(NOWRAP,NOWRAP);
		}		
		endTag();
		startTag(NOBR,true);
		append(label);
		endTag(NOBR);
		endTag(CELL);
		appendln();
	}
	
	protected void renderClickEvent() throws UIException
	{
		addAttribute(ONCLICK);
		append(QUOT);
		append(onClick);
		if (type.equals(UIConstants.SELECTION_SINGLE) && clickCustomEvent.getEventName() != null)
		{
			renderSelectionComplexEvent();
		}
		else
		{
			renderSelectionEvent();
			if (clickCustomEvent.getEventName() != null)
			{
				renderSelectionSubmitEvent();
			}
		}
		append(QUOT);
	}
	
	protected void renderSelectionEvent()
	{
		append
		(
			getFunctionCall
			(
				JS_SELECTION_EVENT,
				getSingleQuot(id) + COMMA + 
				id + SELECTION_SUFFIX + COMMA + 
				type.equals(UIConstants.SELECTION_MULTIPLE)
				,false
			)
		);
		append(";");		
	}
	
	protected void renderSelectionSubmitEvent() throws UIException
	{
		append(getFunctionCall(JS_SELECTION_SUBMIT_EVENT,id + COMMA + clickCustomEvent.isCheckDirty() + COMMA + clickCustomEvent.isCheckWarnings(),true));
	}

	protected void renderSelectionComplexEvent() throws UIException
	{
		append(getFunctionCall(JS_SELECTION_COMPLEX_EVENT,id + COMMA + clickCustomEvent.isCheckDirty() + COMMA + clickCustomEvent.isCheckWarnings(),true));
	}

	
	protected void resetTagState()
	{
		type = UIConstants.SELECTION_SINGLE;
		orientation = UIConstants.SELECTION_VERTICAL;
		clickEventName = null;
		breakable = false;		
		clickCustomEvent = null;
		super.resetTagState();
	}
	
	protected void renderEndTag()
	{

	}	

	/**
	 * Returns the type.
	 * @return String
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * Sets the type.
	 * @param type The type to set
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * Returns the orientation.
	 * @return String
	 */
	public String getOrientation()
	{
		return orientation;
	}

	/**
	 * Sets the orientation.
	 * @param orientation The orientation to set
	 */
	public void setOrientation(String orientation)
	{
		this.orientation = orientation;
	}

	/**
	 * @return
	 */
	public String getClickEventName()
	{
		return clickEventName;
	}

	/**
	 * @param string
	 */
	public void setClickEventName(String string)
	{
		clickEventName = string;
	}

	/**
	 * @return
	 */
	public boolean isBreakable()
	{
		return breakable;
	}

	/**
	 * @param b
	 */
	public void setBreakable(boolean b)
	{
		breakable = b;
	}	
}
