package fwpilot.common.operations;

import com.ness.fw.common.logger.Logger;
import com.ness.fw.flower.core.*;
import com.ness.fw.persistence.PagingService;
import com.ness.fw.persistence.SequentialPagingService;
import com.ness.fw.ui.TableModel;

public abstract class  SetRandomButtonsPagingState extends Operation
{
	protected abstract TableModel getTableModel(Context ctx) throws OperationExecutionException;

	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException
	{
		Logger.debug("", "SetRandomButtonsPagingState executed");
		try
		{	
			TableModel model = getTableModel(ctx);
			PagingService  pagingSrv = (PagingService)ctx.getField("pagingService");

			if (pagingSrv != null)
			{			

				System.out.println("allow first" + pagingSrv.isOperationAllowed(SequentialPagingService.GET_FIRST));
				if (!pagingSrv.isOperationAllowed(SequentialPagingService.GET_FIRST))
				{
					model.setPagingFirstAllowed(false);
				}
				else
				{
					model.setPagingFirstAllowed(true);
				}
				
				System.out.println("allow last" + pagingSrv.isOperationAllowed(SequentialPagingService.GET_LAST));
				if (!pagingSrv.isOperationAllowed(SequentialPagingService.GET_LAST))
				{
					model.setPagingLastAllowed(false);
				}
				else
				{					
					model.setPagingLastAllowed(true);
				}

				System.out.println("allow next" + pagingSrv.isOperationAllowed(SequentialPagingService.GET_NEXT));
				if (!pagingSrv.isOperationAllowed(SequentialPagingService.GET_NEXT))
				{
					model.setPagingNextAllowed(false);
				}
				else
				{
					model.setPagingNextAllowed(true);
				}
				
				System.out.println("allow prev" + pagingSrv.isOperationAllowed(SequentialPagingService.GET_PREVIOUS));
				if (!pagingSrv.isOperationAllowed(SequentialPagingService.GET_PREVIOUS))
				{
					model.setPagingPrevAllowed(false);
				}
				else
				{					
					model.setPagingPrevAllowed(true);
				}
			}
		}

		catch (ContextException ce)
		{
			throw new OperationExecutionException("context->",ce);
		}

		catch (Exception e)
		{
			throw new OperationExecutionException("page->",e);
		}
	}	
}
