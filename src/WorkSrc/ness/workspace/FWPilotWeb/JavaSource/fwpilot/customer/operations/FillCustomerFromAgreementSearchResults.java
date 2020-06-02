package fwpilot.customer.operations;

import java.util.*;
import com.ness.fw.flower.core.*;
import com.ness.fw.persistence.Page;
import com.ness.fw.persistence.PagingService;
import com.ness.fw.ui.*;
import com.ness.fw.util.StringFormatterUtil;
import com.ness.fw.bl.proxy.BPOProxy;
import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.logger.*;
import fwpilot.common.bpc.PLPagingBPC;
import fwpilot.customer.bpc.PLFindCustomersBPC;
import fwpilot.customers.utils.CustomerTablePaging;
import fwpilot.utils.Utils;

public class FillCustomerFromAgreementSearchResults extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException
	{

		Logger.debug("", "FillCustomerFromAgreementSearchResults executed");
		try
		{
			// Filling the table with the search results
			fillResults(ctx);
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

	private void fillResults(Context ctx) throws ContextException, GeneralException
	{
		TableModel model = (TableModel)ctx.getField("searchResultTableModel");

		HashMap attributes = new HashMap();

		ListModel customerTypeModel = (ListModel) ctx.getField("customerTypeModel");

		int type = -1;
		if (customerTypeModel.getSelectedKey() != null)
		{
			type = Integer.parseInt(customerTypeModel.getSelectedKey());
		}

		if (type != -1)
		{
			attributes.put("type", new Integer(type));
		}


		if (!StringFormatterUtil.isEmpty(ctx.getXIString("city")))
		{
			attributes.put("city",ctx.getXIString("city"));
		}

		if (!StringFormatterUtil.isEmpty(ctx.getXIString("firstName")))
		{
			attributes.put("name",ctx.getXIString("firstName"));
		}

		if (!StringFormatterUtil.isEmpty(ctx.getXIString("telephone")))
		{
			attributes.put("telephone",new Integer(ctx.getXIString("telephone")));
		}

		if (!Utils.isEmpty(ctx.getXIString("id")))
		{
			attributes.put("id",new Integer(ctx.getXIString("id")));
		}

		PLFindCustomersBPC customerBPC = new PLFindCustomersBPC();
		customerBPC.setCriteriaFields(attributes);
		BPOProxy.execute("findCustomersCmd", customerBPC);
		PagingService pagingService = customerBPC.getPagingService();
		Page page = null;
	
		model.removeAllRows();

		if (pagingService.isOperationAllowed(PagingService.GET_FIRST))
		{
			PLPagingBPC pagingBPC = new PLPagingBPC();
			pagingBPC.setPagingSrv(pagingService);
			pagingBPC.setPagingOperation(PagingService.GET_FIRST);
			BPOProxy.execute("pagingCmd",pagingBPC);
			page = pagingBPC.getPage();			
		}
		
		CustomerTablePaging.fillPage(ctx,page,model);

	}


}
