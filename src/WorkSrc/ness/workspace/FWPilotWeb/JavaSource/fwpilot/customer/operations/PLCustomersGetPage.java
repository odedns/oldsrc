package fwpilot.customer.operations;

import com.ness.fw.common.exceptions.*;
import com.ness.fw.flower.core.*;
import com.ness.fw.persistence.Page;
import com.ness.fw.ui.*;
import fwpilot.common.operations.PLGetRandomPage;
import fwpilot.customers.utils.CustomerTablePaging;

public class PLCustomersGetPage extends PLGetRandomPage
{

	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException
	{
		super.execute(ctx, parameterList);
	}
	
	protected void fillTable(Context ctx, Page page) throws OperationExecutionException, FatalException, AuthorizationException, UIException, ResourceException
	{
		try
		{	
			TableModel model = (TableModel)ctx.getField("searchResultTableModel");
			CustomerTablePaging.fillPage(ctx,page,model);		
		}

		catch (ContextException ce)
		{
			throw new OperationExecutionException("set field ->",ce);
		}
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