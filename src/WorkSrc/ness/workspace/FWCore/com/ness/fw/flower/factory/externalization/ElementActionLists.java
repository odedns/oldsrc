package com.ness.fw.flower.factory.externalization;

import java.util.Map;
import com.ness.fw.flower.core.ActionList;

/**
 * @author yharnof
 * 
 * This class holds the {@link ActionList}s for the element declared in the 
 * current flow and state.
 */
class ElementActionLists
{
	/**
	 * A constant for <code>Flow</code> level element.
	 */
	public static int FLOW_LEVEL = 1;

	/**
	 * A constant for <code>State</code> level element.
	 */
	public static int STATE_LEVEL = 2;
	
	/**
	 *  The {@link ActionList}s declared in the <code>Flow</code> level.
	 */
	private Map flowActionLists = null;
	
	/**
	 * The {@link ActionList}s declared in the <code>State</code> level.
	 */
	private Map stateActionLists = null;

	/**
	 * Creates new ElementActionLists.
	 */
	public ElementActionLists()
	{
	}

	/**
	 * Returns the ActionList declared for the given name.
	 * If the list declared in the flow and also in the state, returns the actionList declared in the state level.
	 * @param actionListName The name of the ActionList to retrieve.
	 * @return ActionList The list of actions. 
	 */
	ActionList getActionList (String actionListName)
	{ 
		ActionList list = null;
		if (stateActionLists != null)
		{
			list = (ActionList)stateActionLists.get(actionListName);
		}
		
		if(list == null && flowActionLists != null)
		{
			list = (ActionList)flowActionLists.get(actionListName);
		}
		
		return list;
	}

	/**
	 * Set the map with the <code>ActionList</code>s to the given level.
	 * @param elementLevel The elemnt level. could be FLOW_LEVEL or STATE_LEVEL.
	 * @param actionListMap The map with the <code>ActionList</code>s for the level.
	 */
	void setLevelActionLists(int elementLevel, Map actionListMap)
	{ 
		if(elementLevel == FLOW_LEVEL)
		{
			flowActionLists = actionListMap;
			stateActionLists = null;
		}
		else
		{
			stateActionLists = actionListMap;
		}
	}

}
