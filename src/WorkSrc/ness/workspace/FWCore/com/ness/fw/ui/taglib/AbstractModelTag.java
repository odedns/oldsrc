package com.ness.fw.ui.taglib;

import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.ui.AbstractModel;

public abstract class AbstractModelTag extends UITag
{
	/**
	 * @return the id of this model tag
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param string
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	protected void renderHiddenField()
	{
		if (id != null)
		{
			renderHidden(id + AbstractModel.MODEL_EVENT_DATA_CONSTANT, "");
		}
		appendln();
	}
	
	protected void renderHiddenField(String initialValue)
	{
		renderHidden(id + AbstractModel.MODEL_EVENT_DATA_CONSTANT,initialValue);
		appendln();		
	}

	
	/**
	 * Initializing the model tag by calling the initModel method.
	 * @throws UIException
	 */
	protected void init() throws UIException
	{
		initModel();
	}
				
	/**
	 * Initializing the model of the tag.
	 * @throws UIException
	 */	
	protected abstract void initModel() throws UIException; 

}
