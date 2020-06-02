/*
 * Created on 20/07/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ness.fw.ui.data;

import com.ness.fw.ui.UIConstants;


public class TextAreaTagData extends TextTagData 
{
	public void setRows(String rows)
	{
		setAttribute("rows",rows);
	}
	
	public String getRows()
	{
		return getStringAttribute("rows");
	}
	
	public void setCols(String cols)
	{
		setAttribute("cols",cols);
	}
	
	public String getCols()
	{
		return getStringAttribute("cols");
	}
	
	public void setExpandable(boolean expandable)
	{
		setBooleanAttribute("expandable",expandable);
	}
	
	public Boolean isExpandaple()
	{
		return getBooleanAttribute("expandable");
	}
	
	public void setExpanderTitle(String expanderTitle)
	{
		setAttribute("expanderTitle",expanderTitle);
	}
	
	public String getExpanderTitle()
	{
		return getStringAttribute("expanderTitle");
	}
	
	public void setExpanderDialogParams(String expanderDialogParams)
	{
		setAttribute("expanderDialogParams",expanderDialogParams);
	}
	
	public String getExpanderDialogParams()
	{
		return getStringAttribute("expanderDialogParams");
	}

	public String getTagType() 
	{
		return UIConstants.TAG_TYPE_TEXT_AREA;
	}	
}
