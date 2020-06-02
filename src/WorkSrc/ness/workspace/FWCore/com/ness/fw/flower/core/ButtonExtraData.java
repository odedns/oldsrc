/*
 * Created on: 18/04/2005
 * Author: yifat har-nof
 * @version $Id: ButtonExtraData.java,v 1.2 2005/04/18 12:58:12 shay Exp $
 */
package com.ness.fw.flower.core;

import com.ness.fw.flower.util.PageElementAuthLevel;

/**
 *
 */
public class ButtonExtraData
{

	private ButtonData buttonData;
	private PageElementAuthLevel pageElement;
	private String flowPath;
	private String flowState;
	private Flow buttonFlow;
	
	/**
	 * 
	 * @param buttonData
	 * @param pageElement
	 * @param flowPath
	 * @param flowState
	 */
	public ButtonExtraData(ButtonData buttonData, PageElementAuthLevel pageElement,Flow buttonFlow)
	{
		this.buttonData = buttonData;
		this.pageElement = pageElement;
		this.buttonFlow = buttonFlow;
	}

	/**
	 * @return
	 */
	public ButtonData getButtonData()
	{
		return buttonData;
	}

	/**
	 * @return
	 */
	public String getFlowPath()
	{
		return flowPath;
	}

	/**
	 * @return
	 */
	public String getFlowState()
	{
		return flowState;
	}

	/**
	 * @return
	 */
	public PageElementAuthLevel getPageElement()
	{
		return pageElement;
	}

	/**
	 * @return
	 */
	public Flow getButtonFlow() {
		return buttonFlow;
	}

}
