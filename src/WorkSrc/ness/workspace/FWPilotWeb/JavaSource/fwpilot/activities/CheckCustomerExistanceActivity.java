package fwpilot.activities;

import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.exceptions.PersistenceException;
import com.ness.fw.flower.core.Activity;
import com.ness.fw.flower.core.ActivityCompletionEvent;
import com.ness.fw.flower.core.ActivityException;
import com.ness.fw.flower.core.Context;
import com.ness.fw.flower.core.ContextException;
import com.ness.fw.flower.core.ParameterList;
import com.ness.fw.flower.core.ValidationException;
import com.ness.fw.persistence.ConnectionSequence;
import com.ness.fw.persistence.exceptions.ObjectNotFoundException;

import fwpilot.customer.dao.CustomerDAO;

/**
 * @author bhizkya
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CheckCustomerExistanceActivity extends Activity
{

	public void initialize(ParameterList parameterList) throws ActivityException
	{
	}

	public ActivityCompletionEvent execute(Context ctx) throws ActivityException, ValidationException
	{

	//	System.out.println("**************CheckCustomerExistance****************");

		Integer customerID;

		boolean errors = false;


		ConnectionSequence seq = null;
		try
		{
			customerID = new Integer(ctx.getXIString("customerID"));
			if (customerID == null)
			{
				errors = true;
			}
			else
			{
				seq = ConnectionSequence.beginSequence();
				try
				{
					CustomerDAO.findByID(customerID.intValue(), seq);
				}
				catch (FatalException e)
				{
					throw new ActivityException("fatal", e);
				}
			}
		}

		catch (ObjectNotFoundException onfe)
		{
			errors = true;
		}

		catch (PersistenceException pe)
		{
			throw new ActivityException("persistance->", pe);
		}

		catch (ContextException ce)
		{
			throw new ActivityException("context->", ce);
		}

		finally
		{
			try
			{
				if(seq != null)
					seq.end();
			}
			catch (PersistenceException e)
			{
				throw new ActivityException("persistence", e);
			}
		}

		// return the appropiate event
		if (errors)
		{
			return new ActivityCompletionEvent("customerExist");
		}
		else
		{
			return new ActivityCompletionEvent("customerNotExist");
		}

		

	}
}
