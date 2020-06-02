/*
 * Created on: 25/01/2005
 * Author:  user name
 * @version $Id: DirtableTag.java,v 1.1 2005/02/21 15:07:12 baruch Exp $
 */
package com.ness.fw.ui.taglib;

import com.ness.fw.common.exceptions.UIException;

/**
 * @author srancus
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DirtableTag extends UITag 
{
	public DirtableTag()
	{
		ignoreAuth = true;
		initAuth = false;
	}
	
	protected void init() throws UIException
	{
		startTagReturnValue = EVAL_BODY_INCLUDE;
	}
	
	/* (non-Javadoc)
	 * @see com.ness.fw.ui.taglib.UITag#renderStartTag()
	 */
	protected void renderStartTag() throws UIException 
	{
		setRequestContextValue(REQUEST_ATTRIBUTE_DIRTABLE,new Boolean(false));
	}

	/* (non-Javadoc)
	 * @see com.ness.fw.ui.taglib.UITag#renderEndTag()
	 */
	protected void renderEndTag() throws UIException 
	{
		removeRequestContextValue(REQUEST_ATTRIBUTE_DIRTABLE);
	}
}
