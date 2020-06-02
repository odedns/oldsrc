package fwpilot.managment.customer;

import com.ness.fw.flower.core.*;
import com.ness.fw.ui.*;
import com.ness.fw.ui.Row;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.exceptions.GeneralException;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.common.logger.*;
import fwpilot.customer.vo.Customer;
import fwpilot.customer.vo.CustomerManager;
import fwpilot.customer.vo.CustomerVOFactory;
import fwpilot.customer.vo.FamilyMemberStatus;
import fwpilot.customer.vo.FamilyMemberVOFactory;
import fwpilot.customer.vo.SexStatus;
import fwpilot.customer.vo.SexVOFactory;
import fwpilot.customers.utils.CustomerTools;

public class PLUpdateCustomerOP extends Operation
{
	protected void execute(Context ctx, ParameterList list) throws OperationExecutionException
	{
		Logger.debug("", "UpdateAddress executed");
		try
		{

			Customer customer = null;
			Row row = null;
			
			// get the model
			TableModel model = (TableModel)ctx.getField("customersTable");

			// get the customer
			CustomerManager customerManager  = (CustomerManager)ctx.getField("customerManager");

			if (model.getSelectedRow() == null)
			{
				customer = CustomerVOFactory.createCustomer();
				updateCustomer(customer,ctx);
				customerManager.add(customer);

				row = model.createRow();
				// setting the extraData
				row.addExtraData("ID", null);
				row.addExtraData("UID",String.valueOf(customer.getUID()));

				CustomerTools.setRowWithCustomer(model,row,customer,ctx);
				model.setSelectedRow(row.getId());
			}

			// UPDATE mode
			else
			{
				// find the selected member
				customer = CustomerTools.getCustomer(ctx);
				// update the FamilyMember object from context
				updateCustomer(customer,ctx);
				// update row in table
				row = model.getSelectedRow();
				CustomerTools.setRowWithCustomer(model, row, customer,ctx);	
				customerManager.addCustomerForLocking(customer);		
			}
		}

		catch(UIException ue)
		{
			throw new OperationExecutionException("UpdateDocument->UI", ue);
		}

		catch(ContextException ce)
		{
			throw new OperationExecutionException("UpdateDocument->Context", ce);
		}

		catch(GeneralException ge)
		{
			throw new OperationExecutionException("UpdateDocument->General", ge);
		}

	}

	private void updateCustomer(Customer customer, Context ctx) throws ContextException, FatalException
	{
		customer.setFirstName(ctx.getXIString("customerName"));
		customer.setLastName(ctx.getXIString("customerFamily"));
		SexStatus sexStatus = SexVOFactory.getById(Customer.SEX_FEMALE);
		customer.setSex(sexStatus);
		customer.setType(new Integer(Customer.TYPE_PRIVATE));
		customer.setSmoking(false);
		
		FamilyMemberStatus status = FamilyMemberVOFactory.getById(Customer.STATUS_SINGLE);
		customer.setFamilyStatus(status);
	}
}
