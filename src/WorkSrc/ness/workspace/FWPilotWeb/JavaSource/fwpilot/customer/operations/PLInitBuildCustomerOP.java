package fwpilot.customer.operations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ness.fw.bl.proxy.BPOProxy;
import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.exceptions.GeneralException;
import com.ness.fw.common.exceptions.ResourceException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.resources.LocalizedResources;
import com.ness.fw.flower.core.Context;
import com.ness.fw.flower.core.ContextException;
import com.ness.fw.flower.core.Operation;
import com.ness.fw.flower.core.OperationExecutionException;
import com.ness.fw.flower.core.ParameterList;
import com.ness.fw.flower.util.ApplicationUtil;
import com.ness.fw.ui.ListModel;
import com.ness.fw.ui.UIConstants;
import fwpilot.customer.bpc.PLCustomerBPC;
import fwpilot.customer.vo.Customer;
import fwpilot.customer.vo.FamilyMemberStatus;
import fwpilot.customer.vo.FamilyMemberVOFactory;
import fwpilot.customer.vo.Profession;
import fwpilot.customer.vo.ProfessionVOFactory;
import fwpilot.customer.vo.SexStatus;
import fwpilot.customer.vo.SexVOFactory;

public class PLInitBuildCustomerOP extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException
	{
		Logger.debug("", "InitBuildCustomer executed");
		LocalizedResources localizable = ApplicationUtil.getLocalizedResources(ctx);

		try
		{
			Customer customer = null;			
			Integer custID = ctx.getString("custID") == null ? null : Integer.valueOf(ctx.getString("custID"));
			Integer customerType = ctx.getInteger("customerType");
			customer = loadCustomer(custID,customerType);
			ctx.setField("customer",customer);

			InitScreen(ctx,customer);

			ctx.setField("identification", customer.getIdentification());
			ctx.setField("firstName", customer.getFirstName());
			ctx.setField("lastName", customer.getLastName());
			ctx.setField("englishFirstName", customer.getEnglishFirstName());
			ctx.setField("englishLastName", customer.getEnglishLastName());
			ctx.setField("birthDate", customer.getBirthDate() == null ? null : new Date(customer.getBirthDate().getTime()));
			ctx.setField("telephone", customer.getTelephone());
			ctx.setField("fax", customer.getFax());
			ctx.setField("mobilePhone", customer.getMobilePhone());
			ctx.setField("numberOfChilds", customer.getNumberOfChilds());

			ListModel customerStatusModel = (ListModel) ctx.getField("customerStatusModel");
			ListModel customerSexModel = (ListModel) ctx.getField("customerSexModel");
			ListModel customerSmokingModel = (ListModel) ctx.getField("customerSmokingModel");

			customerSmokingModel.markBooleanValue(customer.isSmoking());
			
			customerStatusModel.setSelectedKey(customer.getFamilyStatus() == null ? String.valueOf(Customer.STATUS_SINGLE) : String.valueOf(customer.getFamilyStatus().getId()));
			customerSexModel.setSelectedKey(customer.getSex() == null ? null : String.valueOf(customer.getSex().getId()));
			ctx.setField("title",localizable.getString("custDetails") + " " + getType(customer.getType().intValue(),ctx));
			ctx.setField("customer",customer);
		}

		catch(ContextException ce)
		{
			throw new OperationExecutionException("InitBuildCustomer->setField->", ce);
		}

		catch(GeneralException ge)
		{
			throw new OperationExecutionException("InitBuildCustomer->general", ge);
		}

	}

	private void InitScreen(Context ctx, Customer customer) throws ContextException, FatalException, AuthorizationException, GeneralException
	{
		ctx.setField("customerStatusModel", buildCustomerStatusModel(ctx));
		ctx.setField("customerSexModel", buildCustomerSexModel(ctx));
		ctx.setField("customerSmokingModel", buildCustomerSmokingModel(ctx));
		ctx.setField("customerProfessionModel",buildCustomerProfessionModel(customer));
	}

	private ListModel buildCustomerStatusModel(Context ctx) throws ResourceException, FatalException
	{
//		LocalizedResources localizable = ApplicationUtil.getLocalizedResources(ctx);
		ListModel model = new ListModel();
		model.setSelectionType(UIConstants.LIST_TYPE_SINGLE);

//		model.addValue(String.valueOf(Customer.STATUS_SINGLE), localizable.getString("single"));
//		model.addValue(String.valueOf(Customer.STATUS_MARRIED), localizable.getString("married"));
//		model.addValue(String.valueOf(Customer.STATUS_DIVORCED), localizable.getString("divorced"));
//		model.addValue(String.valueOf(Customer.STATUS_WIDOWER), localizable.getString("widower"));
//		model.setSelectedKey(String.valueOf(Customer.STATUS_SINGLE));

		List list = FamilyMemberVOFactory.getAll();
		for (int i=0; i<list.size();i++)
		{
			FamilyMemberStatus familyMemberStatus = (FamilyMemberStatus)list.get(i);
			model.addValue(String.valueOf(familyMemberStatus.getId()),familyMemberStatus.getName());
		}
		
		model.setSelectedKey(String.valueOf(Customer.STATUS_SINGLE));

		return model;
	}

	private ListModel buildCustomerSexModel(Context ctx) throws ResourceException, FatalException
	{
			ListModel model = new ListModel();
//			LocalizedResources localizable = ApplicationUtil.getLocalizedResources(ctx);

			ArrayList keys = new ArrayList();
			keys.add(String.valueOf(Customer.SEX_MALE));

			model.setSelectionType(UIConstants.LIST_TYPE_SINGLE);

//			model.addValue(String.valueOf(Customer.SEX_MALE), localizable.getString("male"));
//			model.addValue(String.valueOf(Customer.SEX_FEMALE), localizable.getString("female"));
//			model.addValue(String.valueOf(Customer.SEX_UNKNOWN), localizable.getString("unknown"));

			List list = SexVOFactory.getAll();
			for (int i=0; i<list.size();i++)
			{
				SexStatus sexStatus = (SexStatus)list.get(i);
				model.addValue(String.valueOf(sexStatus.getId()),sexStatus.getName());
			}

			model.setSelectedKey(String.valueOf(Customer.SEX_MALE));
			
			return model;
	}


	private ListModel buildCustomerSmokingModel(Context ctx) throws ResourceException
	{
		LocalizedResources localizable = ApplicationUtil.getLocalizedResources(ctx);
		ListModel model = new ListModel();
		model.setSelectionType(ListModel.TYPE_MULTIPLE);
		model.addBooleanValue(localizable.getString("smoke"), false);
		return model;
	}

	private ListModel buildCustomerProfessionModel(Customer customer) throws GeneralException
	{
		ListModel model = new ListModel();
		model.setSelectionType(UIConstants.LIST_TYPE_MULTIPLE);

//		PLCustomerBPC customerBPC = new PLCustomerBPC();

//		BPOProxy.execute("loadProfessionsCmd", customerBPC);
//		Object[] professions = customerBPC.getValues().toArray();

		Object[] professions = ProfessionVOFactory.getAll().toArray();

		for (int i=0; i<professions.length; i++)
		{
			// TODO : use getall vofactory
			Profession pair = (Profession)professions[i];
			model.addValue(String.valueOf(pair.getId()), pair.getName());
		}

		// load the customer's profession
		model.setSelectedKeys(customer.getSelectedProfessions());
//		ArrayList keys = new ArrayList();
//		if (customer != null)
//		{
//			ValueObjectList custProf = customer.getProfessionList();
//			for (int i=0; i<custProf.size(); i++)
//			{
//				CustomerProfession  pair = (CustomerProfession)custProf.getValueObject(i);
//				keys.add(String.valueOf(pair.getProfessionId()));
//			}
//			model.setSelectedKeys(keys);
//
//		}
		return model;
	}

	private Customer loadCustomer(Integer customerId, Integer customerType) throws GeneralException
	{
		PLCustomerBPC customerBPC = new PLCustomerBPC();
		customerBPC.setCustomerId(customerId);

		if (customerType == null)
		{
			customerType = new Integer(Customer.TYPE_PRIVATE);
		}
		customerBPC.setCustomerType(customerType);
		BPOProxy.execute("loadCustomerCmd", customerBPC);
		return customerBPC.getCustomer();
	}


	private String getType(int type, Context ctx) throws ResourceException
	{
		LocalizedResources localizable = ApplicationUtil.getLocalizedResources(ctx);

		return (type == Customer.TYPE_PRIVATE ? "(" + localizable.getString("customerTable_private") + ")" : "(" + localizable.getString("customerTable_business") + ")");
	}

}
