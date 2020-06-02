package com.ness.fw.ui;

import java.util.HashMap;

public class TabModel
{
	private HashMap tabs = new HashMap();
	
	/**
	 * Constant used for the state When this value is used,a tab click is enabled<br>
	 * and its css class is set by this state. 
	 */
	public final static String TAB_STATE_ENABLED = UIConstants.COMPONENT_ENABLED_STATE;

	/**
	 * Constant used for the state When this value is used,a tab click is disabled<br>
	 * and its css class is set by this state. 
	 */
	public final static String TAB_STATE_DISABLED = UIConstants.COMPONENT_DISABLED_STATE;

	/**
	 * Constant used for the state When this value is used,the tab<br>
	 * is not rendered by the tag. 
	 */
	public final static String TAB_STATE_HIDDEN = UIConstants.COMPONENT_HIDDEN_STATE;
	
	public final static int TAB_DIRTY_ACTION_NONE = UIConstants.TAB_DIRTY_ACTION_NONE;
	public final static int TAB_DIRTY_ACTION_IGNORE = UIConstants.TAB_DIRTY_ACTION_IGNORE;
	public final static int TAB_DIRTY_ACTION_WARNING = UIConstants.TAB_DIRTY_ACTION_WARNING;
	public final static int TAB_DIRTY_ACTION_ERROR = UIConstants.TAB_DIRTY_ACTION_ERROR;

	private final String TAB_LAST_VISITED = "lastVisited";

	private String lastVisitedTab;
	private int dirtyAction;
	
	/**
	 * Sets the UI state of the tab,may be one of 3 constants :<br>
	 * TAB_STATE_ENABLED - normal state
	 * TAB_STATE_DISABLED - tab is disabled
	 * TAB_STATE_HIDDEN - tab is not rendered
	 * The key of the tab is its "flower" state
	 * @param tabState the "flower" state of the tab
	 * @param tabUIState the "ui" state of the tab
	 */
	public void setTabUIState(String tabState,String tabUIState)
	{
		tabs.put(tabState,tabUIState);
	}
	
	/**
	 * Indicates if a tab is in hidden ui state.
	 * @param tabState the "flower" state of the tab
	 * @return true if the tab is hidden.If no tab with this tabState exists,<br>
	 * the method returns false.
	 */
	public boolean isTabHidden(String tabState)
	{
		String tabUIState = (String)tabs.get(tabState);
		if (tabUIState == null)
		{
			return false;
		}
		else
		{
			return tabUIState.equals(TAB_STATE_HIDDEN);
		}
	}
	
	public boolean isTabDisabled(String tabState)
	{
		String tabUIState = (String)tabs.get(tabState);
		if (tabUIState == null)
		{
			return false;
		}
		else
		{
			return tabUIState.equals(TAB_STATE_DISABLED);
		}
	}	

	/**
	 * @return last visited tab
	 */
	public String getLastVisitedTab()
	{
		return lastVisitedTab;
	}

	/**
	 * @param lastVisited
	 */
	public void setLastVisitedTab(String lastVisitedTab)
	{
		this.lastVisitedTab = lastVisitedTab;
	}
	/**
	 * @return
	 */
	public int getDirtyAction() 
	{
		return dirtyAction;
	}

	/**
	 * @param dirtyAction
	 */
	public void setDirtyAction(int dirtyAction) 
	{
		this.dirtyAction = dirtyAction;
	}

}
