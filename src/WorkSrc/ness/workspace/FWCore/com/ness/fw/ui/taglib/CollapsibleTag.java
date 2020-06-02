package com.ness.fw.ui.taglib;

import com.ness.fw.ui.CollapsibleModel;
import com.ness.fw.shared.ui.FlowerUIUtil;
import com.ness.fw.common.exceptions.UIException;

public class CollapsibleTag extends AbstractModelTag
{
	private CollapsibleModel collapsibleModel;
	private String name = "";
	private String collapsibleTitle = "";
	private String collapsibleOpenImage;
	private String collapsibleCloseImage;

	private String collapsibleTableClassName;
	private String collapsibleTopRowClassName;
	private String collapsibleBottomRowClassName;
	private String collapsibleTitleClassName;
	private String collapsibleIconClassName;
	private String collapsibleContentClassName;

	private int currentRenderedSection = -1;

	private String width = "100%";
	/* (non-Javadoc)
	 * @see com.ness.fw.ui.taglib.AbstractModelTag#initModel()
	 */
	protected void initModel() throws UIException
	{
		startTagReturnValue = EVAL_BODY_INCLUDE;
		unauthorizedMessage = "";
		if (collapsibleModel == null && id != null)
		{
			collapsibleModel = (CollapsibleModel)FlowerUIUtil.getObjectFromContext(getHttpRequest(),id);
		}
	}
	
	protected void initCss()
	{
		collapsibleTableClassName = initUIProperty(collapsibleTableClassName,"ui.collapsible.table");
		collapsibleTopRowClassName = initUIProperty(collapsibleTopRowClassName,"ui.collapsible.row.top");
		collapsibleBottomRowClassName = initUIProperty(collapsibleBottomRowClassName,"ui.collapsible.row.bottom");
		collapsibleTitleClassName = initUIProperty(collapsibleTitleClassName,"ui.collapsible.cell.title");
		collapsibleIconClassName = initUIProperty(collapsibleIconClassName,"ui.collapsible.cell.icon");
		collapsibleContentClassName = initUIProperty(collapsibleContentClassName,"ui.collapsible.cell.content");
	}

	protected int getCurrentRenderedSection()
	{
		currentRenderedSection++;
		return currentRenderedSection;
	}
	
	/* (non-Javadoc)
	 * @see com.ness.fw.ui.taglib.UITag#renderStartTag()
	 */
	protected void renderStartTag() throws UIException
	{
		initCss();
		renderCollapseTable();
	}

	private void renderCollapseTable()
	{
		startTag(TABLE);
		addAttribute(ID,name);
		addAttribute(CLASS,collapsibleTableClassName);
		addAttribute(WIDTH,width);
		addAttribute(CELLPADDING,"0");
		addAttribute(CELLSPACING,"0");
		endTag();
		appendln();		
	}
	
	protected void resetTagState()
	{
		collapsibleModel = null;
		name = "";
		collapsibleTitle = "";
		collapsibleOpenImage = null;
		collapsibleCloseImage = null;
		collapsibleTableClassName = null;
		collapsibleTopRowClassName = null;
		collapsibleBottomRowClassName = null;
		collapsibleTitleClassName = null;
		collapsibleIconClassName = null;
		collapsibleContentClassName = null;
		currentRenderedSection = -1;
		width = "100%";		
		super.resetTagState();		
	}
	
	/* (non-Javadoc)
	 * @see com.ness.fw.ui.taglib.UITag#renderEndTag()
	 */
	protected void renderEndTag() throws UIException
	{
		appendToEnd();
		endTag(TABLE);
		appendln();
		renderHiddenField();
		appendln();
	}

	/**
	 * @return
	 */
	public String getCollapsibleCloseImage()
	{
		return collapsibleCloseImage;
	}

	/**
	 * @return
	 */
	public String getCollapsibleOpenImage()
	{
		return collapsibleOpenImage;
	}

	/**
	 * @return
	 */
	public String getCollapsibleTableClassName()
	{
		return collapsibleTableClassName;
	}

	/**
	 * @return
	 */
	public String getCollapsibleTitle()
	{
		return collapsibleTitle;
	}

	/**
	 * @return
	 */
	public String getCollapsibleTitleClassName()
	{
		return collapsibleTitleClassName;
	}

	/**
	 * @return
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param string
	 */
	public void setCollapsibleCloseImage(String string)
	{
		collapsibleCloseImage = string;
	}

	/**
	 * @param string
	 */
	public void setCollapsibleOpenImage(String string)
	{
		collapsibleOpenImage = string;
	}

	/**
	 * @param string
	 */
	public void setCollapsibleTableClassName(String string)
	{
		collapsibleTableClassName = string;
	}

	/**
	 * @param string
	 */
	public void setCollapsibleTitle(String string)
	{
		collapsibleTitle = string;
	}

	/**
	 * @param string
	 */
	public void setCollapsibleTitleClassName(String string)
	{
		collapsibleTitleClassName = string;
	}

	/**
	 * @param string
	 */
	public void setName(String string)
	{
		name = string;
	}

	/**
	 * @return
	 */
	public CollapsibleModel getCollapsibleModel()
	{
		return collapsibleModel;
	}

	/**
	 * @param model
	 */
	public void setCollapsibleModel(CollapsibleModel model)
	{
		collapsibleModel = model;
	}

	/**
	 * @return
	 */
	public String getCollapsibleIconClassName() {
		return collapsibleIconClassName;
	}

	/**
	 * @param string
	 */
	public void setCollapsibleIconClassName(String string) 
	{
		collapsibleIconClassName = string;
	}

	/**
	 * @return
	 */
	public String getWidth() 
	{
		return width;
	}

	/**
	 * @param string
	 */
	public void setWidth(String string) 
	{
		width = string;
	}

	/**
	 * @return
	 */
	public String getCollapsibleContentClassName() 
	{
		return collapsibleContentClassName;
	}

	/**
	 * @param string
	 */
	public void setCollapsibleContentClassName(String string) 
	{
		collapsibleContentClassName = string;
	}

	/**
	 * @return
	 */
	public String getCollapsibleBottomRowClassName() {
		return collapsibleBottomRowClassName;
	}

	/**
	 * @return
	 */
	public String getCollapsibleTopRowClassName() {
		return collapsibleTopRowClassName;
	}

	/**
	 * @param string
	 */
	public void setCollapsibleBottomRowClassName(String string) {
		collapsibleBottomRowClassName = string;
	}

	/**
	 * @param string
	 */
	public void setCollapsibleTopRowClassName(String string) {
		collapsibleTopRowClassName = string;
	}

}
