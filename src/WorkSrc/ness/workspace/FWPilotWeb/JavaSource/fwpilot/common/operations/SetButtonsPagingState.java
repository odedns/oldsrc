package fwpilot.common.operations;

import com.ness.fw.common.logger.Logger;
import com.ness.fw.flower.core.*;
import com.ness.fw.persistence.PagingService;
import com.ness.fw.persistence.SequentialPagingService;
import com.ness.fw.ui.ButtonModel;
import com.ness.fw.ui.ToolBarModel;

public class SetButtonsPagingState extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException
	{
		Logger.debug("", "setButtonsPagingState executed");
		try
		{	
			PagingService  pagingSrv = (PagingService)ctx.getField("pagingService");
			ToolBarModel pagingModel = (ToolBarModel)ctx.getField("pagingToolBar");
			if (pagingSrv != null)
			{			

				System.out.println("allow first" + pagingSrv.isOperationAllowed(SequentialPagingService.GET_FIRST));
				if (!pagingSrv.isOperationAllowed(SequentialPagingService.GET_FIRST))
				{
					pagingModel.setButtonState("first","pagingBS",ButtonModel.BUTTON_STATE_DISABLED);
				}
				else
				{
					pagingModel.setButtonState("first","pagingBS",ButtonModel.BUTTON_STATE_ENABLED);
				}
				
				System.out.println("allow last" + pagingSrv.isOperationAllowed(SequentialPagingService.GET_LAST));
				if (!pagingSrv.isOperationAllowed(SequentialPagingService.GET_LAST))
				{
					pagingModel.setButtonState("last","pagingBS",ButtonModel.BUTTON_STATE_DISABLED);
				}
				else
				{					
					pagingModel.setButtonState("last","pagingBS",ButtonModel.BUTTON_STATE_ENABLED);
				}

				System.out.println("allow next" + pagingSrv.isOperationAllowed(SequentialPagingService.GET_NEXT));
				if (!pagingSrv.isOperationAllowed(SequentialPagingService.GET_NEXT))
				{
					pagingModel.setButtonState("next","pagingBS",ButtonModel.BUTTON_STATE_DISABLED);
				}
				else
				{
					pagingModel.setButtonState("next","pagingBS",ButtonModel.BUTTON_STATE_ENABLED);
				}
				
				System.out.println("allow prev" + pagingSrv.isOperationAllowed(SequentialPagingService.GET_PREVIOUS));
				if (!pagingSrv.isOperationAllowed(SequentialPagingService.GET_PREVIOUS))
				{
					pagingModel.setButtonState("prev","pagingBS",ButtonModel.BUTTON_STATE_DISABLED);
				}
				else
				{					
					pagingModel.setButtonState("prev","pagingBS",ButtonModel.BUTTON_STATE_ENABLED);
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
