package com.ness.fw.ui.taglib;

import com.ness.fw.common.exceptions.UIException;

public class AuthGroupTag extends UITag 
{
	private String replacementHtml;

	protected void init() throws UIException
	{
		startTagReturnValue = EVAL_BODY_INCLUDE;
		if (replacementHtml != null)
		{
			unauthorizedMessage = replacementHtml;	
		}
	}
	
	/* (non-Javadoc)
	 * @see com.ness.fw.ui.taglib.UITag#renderStartTag()
	 */
	protected void renderStartTag() throws UIException 
	{
	}

	/* (non-Javadoc)
	 * @see com.ness.fw.ui.taglib.UITag#renderEndTag()
	 */
	protected void renderEndTag() throws UIException 
	{
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

}
