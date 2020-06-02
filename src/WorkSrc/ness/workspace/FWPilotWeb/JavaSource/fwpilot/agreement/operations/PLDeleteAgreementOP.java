package fwpilot.agreement.operations;

import com.ness.fw.bl.proxy.BPOProxy;
import com.ness.fw.common.exceptions.BusinessLogicException;
import com.ness.fw.common.exceptions.GeneralException;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.flower.core.Context;
import com.ness.fw.flower.core.ContextException;
import com.ness.fw.flower.core.Operation;
import com.ness.fw.flower.core.OperationExecutionException;
import com.ness.fw.flower.core.ParameterList;
import com.ness.fw.flower.core.ValidationException;
import com.ness.fw.flower.util.ApplicationUtil;
import com.ness.fw.shared.flower.bl.FlowerBusinessLogicUtil;
import com.ness.fw.ui.TableModel;

import fwpilot.agreement.bpc.PLAgreementBPC;

public class PLDeleteAgreementOP extends Operation
{

	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException, ValidationException
	{

		Logger.debug("", "PLDeleteAgreementOP executed");

		try
		{
			PLAgreementBPC agreementBPC = new PLAgreementBPC();
			agreementBPC.setId(loadHeskem(ctx));
			FlowerBusinessLogicUtil.setBPOContainerUserData(ctx, agreementBPC);
			BPOProxy.execute("deleteAgreement", agreementBPC);
			
		}
		catch (ContextException e)
		{
			throw new OperationExecutionException("set->",e);
		} 
		catch (BusinessLogicException ble)
		{
			ApplicationUtil.mergeGlobalMessageContainer(ctx,ble.getMessagesContainer());
			if (ApplicationUtil.getGlobalMessageContainer(ctx).containsErrors())
			{
				throw new ValidationException();
			}
		}
		catch (GeneralException e)
		{
			throw new OperationExecutionException ("general", e);
		} 	

	}

	public Integer loadHeskem(Context ctx) throws ContextException
	{
		String agreementID;
		try
		{
			TableModel model = (TableModel)ctx.getField("searchResultTableModel");
			String selectedRow = model.getSelectedRowId();
			agreementID = (String)model.getRow(selectedRow).getCell(1).getData();
		}

		catch (UIException ue)
		{
			throw new ContextException("ui",ue);
		}

		catch (GeneralException ge)
		{
			throw new ContextException("general",ge);
		}

		return new Integer(agreementID);
	}
}
