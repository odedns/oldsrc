package com.ness.fw.ui.taglib;

import com.ness.fw.ui.*;

public abstract class AbstractListTag extends AbstractInputTag
{	
	protected ListModel listModel;
		
	/**
	 * Returns the listModel.
	 * @return ListModel
	 */	
	public ListModel getListModel()
	{
		return listModel;
	}

	/**
	 * Sets the listModel.
	 * @param listModel The listModel to set
	 */
	public void setListModel(ListModel listModel)
	{
		this.listModel = listModel;
	}	
	
	protected void setModelState()
	{
		if (listModel.getState() != null)
		{
			if (isStricterState(listModel.getState(),state))
			{
				state = listModel.getState();
			}			
		}			
	}
	
	protected void resetTagState()
	{
		listModel = null;
		super.resetTagState();
	}
}
