package fwpilot.common.operations;

import com.ness.fw.bl.proxy.BPOProxy;
import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.flower.core.*;
import com.ness.fw.persistence.Page;
import com.ness.fw.persistence.PagingService;
import fwpilot.common.bpc.PLPagingBPC;

public abstract class PLGetPage extends Operation
{

	private int SPECIFIC_PAGE = -1;

	protected abstract void fillTable(Context ctx, Page page) throws OperationExecutionException, FatalException, AuthorizationException, UIException, ResourceException;
	
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException
	{
		Logger.debug("", "PLGetPage executed");
		try
		{
			PLPagingBPC pagingBPC = new PLPagingBPC();
			pagingBPC.setPagingSrv((PagingService)ctx.getField("pagingService"));
			int operatinType = ((Integer)(parameterList.getParameter(0).getValue())).intValue();
			pagingBPC.setPagingOperation(operatinType);
			BPOProxy.execute("pagingCmd", pagingBPC);
			PagingService pagingService = pagingBPC.getPagingSrv();
			ctx.setField("pagingService", pagingService);
			Page page = pagingBPC.getPage();			
			fillTable(ctx,page);
		}
		catch (ContextException ce)
		{
			throw new OperationExecutionException("set field ->",ce);
		}

		catch (GeneralException ge)
		{
			throw new OperationExecutionException("persistance ->",ge);
		}
	}
}
