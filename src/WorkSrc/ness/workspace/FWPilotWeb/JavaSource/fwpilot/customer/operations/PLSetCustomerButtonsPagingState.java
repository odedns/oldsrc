package fwpilot.customer.operations;

import com.ness.fw.flower.core.Context;
import com.ness.fw.flower.core.ContextException;
import com.ness.fw.flower.core.OperationExecutionException;
import com.ness.fw.flower.core.ParameterList;
import com.ness.fw.ui.TableModel;
import fwpilot.common.operations.SetRandomButtonsPagingState;

public class PLSetCustomerButtonsPagingState extends SetRandomButtonsPagingState
{

	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException
	{
		super.execute(ctx,parameterList);
	}
	
	protected TableModel getTableModel(Context ctx) throws OperationExecutionException
	{
		try
		{
			return (TableModel)ctx.getField("searchResultTableModel");
		}
		catch (ContextException e)
		{
			throw new OperationExecutionException("context",e);
		}
	}
}