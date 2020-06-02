package fwpilot.agreement.operations;

import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.flower.core.*;
import com.ness.fw.ui.ListModel;

public class PLClearAgreementCriteriaOP extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException, FatalException, AuthorizationException
	{
		Logger.debug("", "PLClearAgreementCriteriaOP executed");
		try
		{
			ctx.setField("name", null);
			ctx.setField("identification",null);
			ctx.setField("description",null);
			ctx.setField("customerID",null);

			// setting the models to the default values
			ListModel statusModel = (ListModel)ctx.getField("statusModel");
			statusModel.unSelectAllKeys();

			ListModel sugModel = (ListModel)ctx.getField("sugModel");
			sugModel.unSelectAllKeys();

			ListModel likeModel = (ListModel)ctx.getField("likeModel");
			likeModel.unSelectAllKeys();
		}
		
		catch (ContextException ce)
		{
			throw new OperationExecutionException("context",ce);
		}
	}
}
