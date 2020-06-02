/*
 * Created on: 18/04/2005
 * Author: yifat har-nof
 * @version $Id: ButtonExtraDataList.java,v 1.1 2005/04/18 06:45:28 yifat Exp $
 */
package com.ness.fw.flower.core;

import java.util.ArrayList;

/**
 *
 */
public class ButtonExtraDataList
{
	private ArrayList list;

	public ButtonExtraDataList()
	{
		list = new ArrayList();
	}

	public ButtonExtraData getButtonExtraData(int index)
	{
		return (ButtonExtraData) list.get(index);
	}

	protected void addButtonExtraData(ButtonExtraData buttonExtraData)
	{
		list.add(buttonExtraData);
	}

	public int getButtonCount()
	{
		return list.size();
	}

}
