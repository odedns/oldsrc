package com.ness.fw.ui.taglib;

import com.ness.fw.common.exceptions.UIException;

public abstract class FWTag extends UITag 
{
	protected void initSystemProperties() throws UIException
	{
		startOutput = new StringBuffer(tagBufferSize);
		endOutput = new StringBuffer(tagBufferSize);
	}
	
	protected boolean isTagAuthorizedToRender()
	{
		return true;
	}	
}