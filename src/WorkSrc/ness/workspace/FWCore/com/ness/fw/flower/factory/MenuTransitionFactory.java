/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: MenuTransitionFactory.java,v 1.2 2005/03/29 14:56:51 yifat Exp $
 */
package com.ness.fw.flower.factory;

import com.ness.fw.common.externalization.ExternalizationException;
import com.ness.fw.flower.common.MenuItem;
import com.ness.fw.flower.core.Formatter;
import com.ness.fw.flower.core.SubFlowData;
import com.ness.fw.flower.core.SubFlowDataList;
import com.ness.fw.flower.core.Transition;
import com.ness.fw.flower.factory.externalization.ExternalizerNotInitializedException;
import com.ness.fw.flower.factory.externalization.FormatterExternalizer;

/**
 * A factory for menu item transitions
 */
public class MenuTransitionFactory
{
	/**
	 * Creates a Transition with data from the given menu item.
	 * @param menuItem The MenuItem to create the transition.
	 * @return Transition
	 * @throws ExternalizerNotInitializedException
	 * @throws ExternalizationException
	 */
	public static Transition createMenuTransition(MenuItem menuItem) throws ExternalizerNotInitializedException, ExternalizationException 
	{
		SubFlowDataList subFlowDataList = new SubFlowDataList();
		subFlowDataList.addSubFlowData(createSubFlowData(menuItem.getFlowName(), menuItem.getInFormatterName(), menuItem.getOutFormatterName()));
		boolean internal = menuItem.getOpenAs() != MenuItem.OPEN_AS_NORMAL; 
//		return new Transition(menuItem.getEventName(), null, null, null, null, null, null, null, false, null, null, internal, subFlowDataList, false, false);
		return new Transition(menuItem.getEventName(), null, null, false, internal, subFlowDataList, false, false);
	}

	/**
	 * Creates the sub flow data for the menu item transition
	 * @param flowName The flow name.
	 * @param inFormatterName The input formatter name.
	 * @param outFormatterName The output formatter name.
	 * @return SubFlowData
	 * @throws ExternalizerNotInitializedException
	 * @throws ExternalizationException
	 */
	private static SubFlowData createSubFlowData(String flowName, String inFormatterName, String outFormatterName) throws ExternalizerNotInitializedException, ExternalizationException 
	{
		Formatter subFlowInFormatter = null;
		if (inFormatterName != null)
		{
			subFlowInFormatter = FormatterExternalizer.getInstance().createFormatter(inFormatterName);
		}

		Formatter subFlowOutFormatter = null;
		if (outFormatterName != null)
		{
			subFlowOutFormatter = FormatterExternalizer.getInstance().createFormatter(outFormatterName);
		}

		return new SubFlowData(flowName, subFlowInFormatter, subFlowOutFormatter, null, false);
	}
}
