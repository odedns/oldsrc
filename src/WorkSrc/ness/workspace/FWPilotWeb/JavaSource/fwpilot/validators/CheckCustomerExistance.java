package fwpilot.validators;

import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.exceptions.PersistenceException;
import com.ness.fw.flower.core.ValidationProcessException;
import com.ness.fw.flower.factory.ValidationCheck;
import com.ness.fw.flower.factory.ValidationCheckBundle;
import com.ness.fw.persistence.ConnectionSequence;
import com.ness.fw.persistence.exceptions.ObjectNotFoundException;

import fwpilot.customer.dao.CustomerDAO;


public class CheckCustomerExistance implements ValidationCheck
{
	public boolean check(ValidationCheckBundle checkBundle, Integer customerID) throws ValidationProcessException, FatalException
	{
		boolean errors = false;

		if (customerID == null)
		{
			errors = true;
		}

		if (!errors)
		{
			ConnectionSequence seq = null;
			try
			{
				seq = ConnectionSequence.beginSequence();
				CustomerDAO.findByID(customerID.intValue(), seq);
			}

			catch (ObjectNotFoundException onfe)
			{
				errors = true;
			}

			catch (PersistenceException pe)
			{
				throw new ValidationProcessException("persistance->", pe);
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
					throw new ValidationProcessException("persistance->", e);					
				}
			}
		}


		return !errors;


	}
}
