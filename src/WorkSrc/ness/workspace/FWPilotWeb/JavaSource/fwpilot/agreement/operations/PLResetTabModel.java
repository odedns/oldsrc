package fwpilot.agreement.operations;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.flower.core.*;
import com.ness.fw.ui.*;

public class PLResetTabModel extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException, FatalException, AuthorizationException
	{
		try
		{
			TabModel tabModel = (TabModel)ctx.getField("generalTabModel");
			if (tabModel != null)
			{
				tabModel.setLastVisitedTab(null);
			}
		}

		catch (ContextException e)
		{
			throw new OperationExecutionException("context",e);
		} 
	}	
}
