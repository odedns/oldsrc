package fwpilot.activities;

import com.ness.fw.flower.core.*;
import com.ness.fw.ui.TabModel;

public class ChooseTabActivity extends Activity
{
	public void initialize(ParameterList parameterList) throws ActivityException
	{
	}

	public ActivityCompletionEvent execute(Context ctx) throws ActivityException, ValidationException
	{
		// init with the first tab
		String state = "generalTabsDetailsState";
		try
		{
			TabModel tabModel = (TabModel)ctx.getField("generalTabModel");
			if (tabModel != null)
			{
				if (tabModel.getLastVisitedTab() != null)
				{
					state = tabModel.getLastVisitedTab();
				}
				System.out.println("TabModel ->MOVE -->" + state);
			}
			else
			{
				System.out.println("tab model is null");
			}			
		}
		catch (ContextException e)
		{
			throw new ActivityException("context", e);
		}
				
		// return the appropiate event
		return new ActivityCompletionEvent(state);
	}
}
