package fwpilot.common.operations;

import com.ness.fw.bl.proxy.BPOProxy;
import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.flower.core.*;
import com.ness.fw.persistence.Page;
import com.ness.fw.persistence.PagingService;
import com.ness.fw.ui.*;
import fwpilot.common.bpc.PLPagingBPC;

public abstract class PLGetRandomPage extends Operation
{


	protected abstract void fillTable(Context ctx, Page page) throws OperationExecutionException, FatalException, AuthorizationException, UIException, ResourceException;
	protected abstract TableModel getTableModel(Context ctx) throws OperationExecutionException, FatalException, AuthorizationException, UIException, ResourceException;
	
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException
	{
		Logger.debug("", "PLGetRandomPage executed");
		try
		{
			PLPagingBPC pagingBPC = new PLPagingBPC();
			pagingBPC.setPagingSrv((PagingService)ctx.getField("pagingService"));
			int selectedPage = getTableModel(ctx).getSelectedPage() - 1;
			pagingBPC.setPagingOperation(selectedPage);
//			getTableModel(ctx).setSelectedPage(selectedPage);
			BPOProxy.execute("randomPagingCmd", pagingBPC);
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
