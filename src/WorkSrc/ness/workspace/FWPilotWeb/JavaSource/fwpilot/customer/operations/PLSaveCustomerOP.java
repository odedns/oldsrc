package fwpilot.customer.operations;

import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.exceptions.GeneralException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.flower.core.Context;
import com.ness.fw.flower.core.ContextException;
import com.ness.fw.flower.core.Operation;
import com.ness.fw.flower.core.OperationExecutionException;
import com.ness.fw.flower.core.ParameterList;
import com.ness.fw.flower.core.ValidationException;
import com.ness.fw.shared.flower.bl.FlowerBusinessLogicUtil;
import com.ness.fw.ui.ListModel;
import com.ness.fw.ui.TableModel;

import fwpilot.customer.CustomerConstants;
import fwpilot.customer.bpc.PLCustomerBPC;
import fwpilot.customer.vo.Address;
import fwpilot.customer.vo.Customer;
import fwpilot.customer.vo.FamilyMember;
import fwpilot.customer.vo.FamilyMemberStatus;
import fwpilot.customer.vo.FamilyMemberVOFactory;
import fwpilot.customer.vo.SexStatus;
import fwpilot.customer.vo.SexVOFactory;

public class PLSaveCustomerOP extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException, ValidationException
	{
		Logger.debug("", "PLSaveCustomerOP executed");
		try
		{
			saveCustomer(ctx);
		}
		catch (ContextException ce)
		{
			throw new OperationExecutionException("save",ce);
		}

		catch (ValidationException ve)
		{
			throw ve;
		}

		catch (GeneralException ge)
		{
			throw new OperationExecutionException("general",ge);
		}

	}

	private void saveCustomer(Context ctx) throws ValidationException, FatalException, GeneralException
	{
		// load the agreement
		Customer customer = (Customer)ctx.getField("customer");
		customer.setIdentification(ctx.getXIInteger("identification"));
		customer.setFirstName(ctx.getXIString("firstName"));
		customer.setLastName(ctx.getXIString("lastName"));
		customer.setEnglishFirstName(ctx.getXIString("englishFirstName"));
		customer.setEnglishLastName(ctx.getXIString("englishLastName"));
		customer.setBirthDate(ctx.getXIDate("birthDate"));
		customer.setTelephone(ctx.getXIInteger("telephone"));
		customer.setFax((ctx.getXIInteger("fax") == null) ? null : ctx.getXIInteger("fax"));
		customer.setMobilePhone(ctx.getXIInteger("mobilePhone") == null ? null :ctx.getXIInteger("mobilePhone"));
		customer.setNumberOfChilds(ctx.getXIInteger("numberOfChilds") == null ? null :ctx.getXIInteger("numberOfChilds"));

		// updating customer status
		ListModel customerStatusModel = (ListModel) ctx.getField("customerStatusModel");

		if (customerStatusModel.getSelectedKey() == null)
		{
			customer.setFamilyStatus(null);
		}
		else
		{
			FamilyMemberStatus familyMemberStatus = FamilyMemberVOFactory.getById(Integer.parseInt(customerStatusModel.getSelectedKey()));
			customer.setFamilyStatus(familyMemberStatus);
		}

//		customer.setFamilyStatus(customerStatusModel.getSelectedKey() == null ? null : new Integer(customerStatusModel.getSelectedKey()));

		// updating customer sex
		ListModel customerSexModel = (ListModel) ctx.getField("customerSexModel");

		SexStatus sexStatus = SexVOFactory.getById(Integer.parseInt(customerSexModel.getSelectedKey()));
		customer.setSex(sexStatus);
//		customer.setSex(new Integer((customerSexModel.getSelectedKey())));

		// updating smoking
		ListModel customerSmokingModel = (ListModel) ctx.getField("customerSmokingModel");
		customer.setSmoking(customerSmokingModel.isBooleanChecked());

		// updating customer professions
		ListModel customerProfessionModel = (ListModel) ctx.getField("customerProfessionModel");
		customer.setProfessions(customerProfessionModel.getSelectedKeys());

		PLCustomerBPC customerBPC = new PLCustomerBPC();
		customerBPC.setCustomer(customer);
			
		FlowerBusinessLogicUtil.executeBPOCommand(ctx, CustomerConstants.SAVE_CUSTOMER_BPO, customerBPC);
		
		ctx.setField(CustomerConstants.CUSTOMER_CTX_FIELD, customerBPC.getCustomer());
		

		//Looging through all the rows in the table and replace the UID with the new ID

		// addesssTable
		TableModel model = (TableModel)ctx.getField("addressTableModel");
		if (model != null)
		{
			int size = model.getRowsCount();
			for (int i=0; i<size; i++)
			{
				com.ness.fw.ui.Row row = model.getRow(i);
				String uniqueId = (String)row.getExtraData("UID");
				if (uniqueId != null)
				{
					Address address = customer.getAddressByUID(new Integer(uniqueId));
					row.addExtraData("UID",null);
					row.addExtraData("ID",String.valueOf((address.getId())));
				}
			}
		}


		// relatedTable
		TableModel relatedModel = (TableModel)ctx.getField("relatedTableModel");
		if (relatedModel != null)
		{
			int size = relatedModel.getRowsCount();
			for (int i=0; i<size; i++)
			{
				com.ness.fw.ui.Row row = relatedModel.getRow(i);
				String uniqueId = (String)row.getExtraData("UID");
				if (uniqueId != null)
				{
					FamilyMember member = customer.getFamilyMemberByUID(new Integer(uniqueId));
					row.addExtraData("UID",null);
					row.addExtraData("ID",String.valueOf((member.getId())));
				}
			}
		}

		// load the customer to the context
		//ctx.setField("customer", customer);
	}
}
