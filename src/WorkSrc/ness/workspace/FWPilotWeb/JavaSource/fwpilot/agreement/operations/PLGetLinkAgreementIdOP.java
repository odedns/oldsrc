package fwpilot.agreement.operations;

import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.flower.core.*;
import com.ness.fw.ui.TableModel;

public class PLGetLinkAgreementIdOP extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException, FatalException, AuthorizationException
	{
		Logger.debug("", "GetLinkAgreementIdOP executed");

		try
		{
			ctx.setField("selectedAgreementID", loadHeskem(ctx));
		}
		catch (ContextException e)
		{
			throw new OperationExecutionException("set->",e);
		}
	}

	public Integer loadHeskem(Context ctx) throws ContextException
	{
		String agreementID;
		try
		{
			TableModel model = (TableModel)ctx.getField("searchResultTableModel");
			String selectedRow = model.getSelectedLinkRowId();
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
