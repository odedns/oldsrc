package fwpilot.agreement.operations;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.*;
import com.ness.fw.bl.proxy.BPOProxy;
import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.flower.core.*;
import com.ness.fw.persistence.Page;
import com.ness.fw.persistence.PagingService;
import com.ness.fw.ui.*;
import com.ness.fw.util.StringFormatterUtil;
import fwpilot.agreement.bpc.*;
import fwpilot.agreement.utils.*;
import fwpilot.common.bpc.PLPagingBPC;

public class PLFillAgreementSearchResults extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException
	{

		Logger.debug("", "PLFillAgreementSearchResults executed");
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
		//LocalizedResources localizable = ApplicationUtil.getLocalizedResources(ctx);
		Page page = null;
		PagingService pagingService = null;

		HashMap attributes = new HashMap();

		if (!StringFormatterUtil.isEmpty(ctx.getXIString("name")))
		{
			attributes.put("name",ctx.getXIString("name"));
		}

		ListModel statusModel = (ListModel)ctx.getField("statusModel");

		int status = 0;
		if (statusModel.getSelectedKey() != null)
		{
			status = Integer.parseInt(statusModel.getSelectedKey());
		}

		if (status != 0)
		{
			attributes.put("status",new Integer(status));
		}

		if (ctx.getXIInteger("identification") != null)
		{
			attributes.put("id",ctx.getXIInteger("identification"));
		}

		if (ctx.getField("customerID") != null)
		{
			attributes.put("customerId",ctx.getXIInteger("customerID"));
		}

		if (!StringFormatterUtil.isEmpty(ctx.getXIString("description")))
		{
			attributes.put("description",ctx.getXIString("description"));
		}

		ListModel sugModel = (ListModel) ctx.getField("sugModel");
		int type = -1;
		if (sugModel.getSelectedKey() != null)
		{
			type = Integer.parseInt(sugModel.getSelectedKey());
		}

		if (type != -1)
		{
			attributes.put("type", new Integer(type));
		}


		ListModel likeModel = (ListModel) ctx.getField("likeModel");
		Integer like = new Integer(likeModel.getSelectedKey());
		attributes.put("likeType",like);


		PLFindAgreementsBPC container = new PLFindAgreementsBPC();
		container.setCriteriaFields(attributes);
		BPOProxy.execute("findAgreementsCmd",container);
		pagingService = container.getPagingService();
		ctx.setField("pagingService", pagingService);
		
		if (pagingService.isOperationAllowed(PagingService.GET_FIRST))
		{
			PLPagingBPC pagingBPC = new PLPagingBPC();
			pagingBPC.setPagingSrv(pagingService);
			pagingBPC.setPagingOperation(PagingService.GET_FIRST);
			BPOProxy.execute("pagingCmd",pagingBPC);
			pagingService = pagingBPC.getPagingSrv();
			ctx.setField("pagingService", pagingService);
			page = pagingBPC.getPage();			
		}

		AgreementTablePaging.fillPage(ctx,page,model);
	}
	
	private void ser(PLFindAgreementsBPC container)
	{
			try
			{
				ObjectOutput out = new ObjectOutputStream(new java.io.FileOutputStream("c:/temp/bpc.ser"));
				out.writeObject(container);
				out.close();
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
    

		
	}
}
