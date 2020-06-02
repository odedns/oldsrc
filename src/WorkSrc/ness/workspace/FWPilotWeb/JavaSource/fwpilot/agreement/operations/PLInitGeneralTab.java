package fwpilot.agreement.operations;

import com.ness.fw.bl.proxy.BPOCommandException;
import com.ness.fw.bl.proxy.BPOCommandNotFoundException;
import com.ness.fw.bl.proxy.BPOProxy;
import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.BusinessLogicException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.flower.core.*;
import com.ness.fw.flower.util.ApplicationUtil;
import com.ness.fw.ui.*;

import fwpilot.agreement.bpc.PLAgreementBPC;
import fwpilot.agreement.vo.Agreement;

public class PLInitGeneralTab extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException, FatalException, AuthorizationException
	{
		try
		{
			setEnablement(ctx);
			refreshCustomerName(ctx);
			
			// TODO : remove it. should replace by the tabModel
			ctx.setField("lastTabState","generalTabsDetailsState");	
			
			if (ctx.getField("generalTabModel") == null)
			{
				TabModel tabModel = new TabModel();
				ctx.setField("generalTabModel",tabModel);		
			}
		}

		catch (BusinessLogicException e)
		{
			ApplicationUtil.mergeGlobalMessageContainer(ctx,e.getMessagesContainer());
		}

		catch (ContextException e)
		{
			throw new OperationExecutionException("context",e);
		} 
		catch (BPOCommandNotFoundException e)
		{
		} 

		catch (BPOCommandException e)
		{
		} 
	}
	
	private void refreshCustomerName(Context ctx) throws ContextException, FatalException, AuthorizationException, BPOCommandNotFoundException, BPOCommandException, BusinessLogicException
	{
		Agreement agreement = (Agreement)ctx.getField("agreement");
		if(agreement.getCustomerId() != null)
		{
			// loading the customer because agreement contains a loaded one
			// with the previous name
			PLAgreementBPC agreementBPC = new PLAgreementBPC();
			agreementBPC.setAgreement(agreement);
			BPOProxy.execute("loadAgreementCustomerCmd", agreementBPC);
			ctx.setField("customerName", agreementBPC.getAgreement().getCustomer().getName());
		}
	}

	private void setEnablement(Context ctx) throws ContextException
	{
		TextModel model = (TextModel)ctx.getField("minimalPeriodTextModel");
		ListModel canBeCanceledCheckBoxModel = (ListModel) ctx.getField("canBeCanceledCheckBoxModel");
		if (canBeCanceledCheckBoxModel.isValueSelected("0"))
		{
			model.setState(TextModel.MODEL_STATE_ENABLED);
		}
		else
		{
			model.setState(TextModel.MODEL_STATE_DISABLED);
		}
	}
}
