/*
 * Created on 20/07/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ness.fw.ui.data;

import com.ness.fw.ui.UIConstants;

/**
 * @author srancus
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SelectionTagData extends InputTagData 
{
	public void setType(String type)
	{
		setAttribute("type",type);
	}
	
	public String getType()
	{
		return getStringAttribute("type");
	}
	
	public void setOrientation(String orientation)
	{
		setAttribute("orientation",orientation);	
	}
	
	public String getOrientation()
	{
		return getStringAttribute("orientation");
	}
	
	public void setBreakable(boolean breakable)
	{
		setBooleanAttribute("breakable",breakable);
	}
	
	public Boolean isBreakable()
	{
		return getBooleanAttribute("breakable");
	}
	
	public void setClickEventName(String clickEventName)
	{
		setAttribute("clickEventName",clickEventName);
	}
	
	public String getClickEventName()
	{
		return getStringAttribute("clickEventName");
	}

	public String getTagType() 
	{
		return UIConstants.TAG_TYPE_SELECTION;
	}
}
