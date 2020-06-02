package fwpilot.customer.operations;

import java.util.HashMap;
import com.ness.fw.bl.proxy.BPOProxy;
import com.ness.fw.common.exceptions.GeneralException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.flower.core.Context;
import com.ness.fw.flower.core.ContextException;
import com.ness.fw.flower.core.Operation;
import com.ness.fw.flower.core.OperationExecutionException;
import com.ness.fw.flower.core.ParameterList;
import com.ness.fw.flower.core.ValidationException;
import com.ness.fw.flower.util.ApplicationUtil;
import com.ness.fw.persistence.Page;
import com.ness.fw.persistence.PagingService;
import com.ness.fw.ui.ListModel;
import com.ness.fw.ui.TableModel;
import com.ness.fw.util.Message;
import com.ness.fw.util.StringFormatterUtil;
import fwpilot.common.bpc.PLPagingBPC;
import fwpilot.customer.bpc.PLFindCustomersBPC;
import fwpilot.customers.utils.CustomerTablePaging;


public class PLFillCustomerSearchResultsOP extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException, ValidationException
	{

		Logger.debug("", "PLFillCustomerSearchResultsOP executed");
		try
		{
			// Filling the table with the search results
			fillResults(ctx);
		}
		catch (ContextException ce)
		{
			throw new OperationExecutionException("set field ->",ce);
		}

		catch (ValidationException ve)
		{
			throw ve;
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
		Page page = null;
	
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
			try
			{
				attributes.put("telephone",new Integer(ctx.getXIString("telephone")));
			}
			catch (NumberFormatException e)
			{
				Message message = new com.ness.fw.util.Message("GE0006",Message.SEVERITY_ERROR);
				message.addLocalizableParameter("telephone");
				ApplicationUtil.addGlobalMessage(ctx,message);
				throw new ValidationException();
			}
		}

		if (ctx.getField("id") != null)
		{
			attributes.put("id",ctx.getXIInteger("id"));
		}

		PLFindCustomersBPC customerBPC = new PLFindCustomersBPC();
		customerBPC.setCriteriaFields(attributes);
		BPOProxy.execute("findCustomersCmd",customerBPC);
		PagingService pagingService  = customerBPC.getPagingService();
		ctx.setField("pagingService",pagingService);
	
		//System.err.println("customerBPC.getTotalPages()" + customerBPC.getTotalPages());
		model.setTotalPages(customerBPC.getTotalPages());
	
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
