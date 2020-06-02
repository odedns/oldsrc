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
import com.ness.fw.util.Message;
import com.ness.fw.util.MessagesContainer;
import com.ness.fw.util.StringFormatterUtil;

/**
 * @author bhizkya
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ValidateCustomerSearch extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException, ValidationException, FatalException, AuthorizationException
	{

		Logger.debug("", "ValidateCustomerSearch executed");
		try
		{
			// Filling the table with the search results
			validate(ctx);
		}
		catch (ContextException ce)
		{
			throw new OperationExecutionException("set field ->",ce);
		}

		catch (ValidationException ve)
		{
			throw ve;
		}

	}

	private void validate(Context ctx) throws ValidationException, ContextException, FatalException, AuthorizationException
	{

		boolean errors = false;
		if (!StringFormatterUtil.isEmpty(ctx.getXIString("id")))
		{
			try
			{
				new Integer(ctx.getXIString("id"));
			}
			catch (NumberFormatException nfe)
			{
				errors = true;
				MessagesContainer container =  ApplicationUtil.getGlobalMessageContainer(ctx);
				Message message = new Message("GE0006", Message.SEVERITY_ERROR);
				message.addLocalizableParameter("customerId");
				container.addMessage(message);
				// set the customerId to null
				ctx.setField("customerId", null);
			}

		}

		if (errors)
		{
			throw new ValidationException();
		}

	}
}
