package fwpilot.customer.operations;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.flower.core.Context;
import com.ness.fw.flower.core.ContextException;
import com.ness.fw.flower.core.Operation;
import com.ness.fw.flower.core.OperationExecutionException;
import com.ness.fw.flower.core.ParameterList;
import com.ness.fw.flower.core.ValidationException;
import com.ness.fw.flower.util.ApplicationUtil;
import com.ness.fw.ui.ListModel;
import com.ness.fw.util.Message;
import com.ness.fw.util.StringFormatterUtil;

public class CustomerValidation2Op extends Operation
{

	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException, FatalException, AuthorizationException, ValidationException
	{
		Logger.debug("", "CheckCustomerExistance executed");

		boolean errors = false;

		try
		{
			ListModel smokingModel = (ListModel)ctx.getField("customerSmokingModel");
			String engLastName = ctx.getXIString("englishFirstName");
			if(StringFormatterUtil.isEmpty(engLastName) && smokingModel.isBooleanChecked())
			{
				errors = true;
				ApplicationUtil.addGlobalMessage(ctx, new Message("GE0047", Message.SEVERITY_ERROR));
			}
			
			if(errors)
			{
				throw new ValidationException();
			}
			
		}
		
		catch (ContextException ce)
		{
			throw new OperationExecutionException("context->", ce);
		} 

	}
}
