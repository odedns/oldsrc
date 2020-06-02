package fwpilot.agreement.operations;

import com.ness.fw.common.exceptions.*;
import com.ness.fw.flower.core.*;
import com.ness.fw.persistence.Page;
import com.ness.fw.ui.*;
import fwpilot.agreement.utils.AgreementTablePaging;
import fwpilot.common.operations.PLGetPage;

public class PLAgreementGetPage extends PLGetPage
{

	protected void fillTable(Context ctx, Page page) throws OperationExecutionException, FatalException, AuthorizationException, UIException, ResourceException
	{
		try
		{	
			TableModel model = (TableModel)ctx.getField("searchResultTableModel");
			AgreementTablePaging.fillPage(ctx,page,model);		
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