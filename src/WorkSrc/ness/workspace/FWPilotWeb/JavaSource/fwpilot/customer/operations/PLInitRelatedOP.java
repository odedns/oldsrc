package fwpilot.customer.operations;

import java.util.List;

import com.ness.fw.flower.core.*;
import com.ness.fw.flower.util.ApplicationUtil;
import com.ness.fw.ui.*;
import com.ness.fw.bl.ValueObjectList;
import com.ness.fw.bl.proxy.BPOProxy;
import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.logger.*;
import com.ness.fw.common.resources.LocalizedResources;
import fwpilot.customer.bpc.PLCustomerBPC;
import fwpilot.customer.vo.*;
import fwpilot.customers.utils.FamilyMemeberTools;

public class PLInitRelatedOP extends Operation
{

	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException
	{
		Logger.debug("", "InitRelated executed");
		try
		{
			TableModel model = null;

			if (ctx.getField("relatedSexModel") == null)
			{
				ctx.setField("relatedSexModel", buildRelatedSexModel());
			}


			if (ctx.getField("customerRelatedTypeModel") == null)
			{
				ctx.setField("customerRelatedTypeModel",buildaRelatedTypeModel());
			}

			if (ctx.getField("relatedTableModel") == null)
			{
				model = buildRelatedTableModel(ctx);
				fillTable(model,ctx);
				ctx.setField("relatedTableModel", model);
			}

		}

		catch (ContextException ce)
		{
			throw new OperationExecutionException("InitRelated->set field ->",ce);
		}

		catch (PersistenceException pe)
		{
			throw new OperationExecutionException("InitRelated->",pe);
		}

		catch (GeneralException ge)
		{
			throw new OperationExecutionException("InitRelated->",ge);
		}


	}

	private TableModel buildRelatedTableModel(Context ctx) throws ResourceException
	{
		LocalizedResources localizable = ApplicationUtil.getLocalizedResources(ctx);

		TableModel model = new TableModel();
		model.setSelectionType(1);
		model.setAllowMenus(false);

		// columns
		Column c1 = new Column(localizable.getString("customerTable_relationType"),true);
		c1.setWidth("33%");
		model.addColumn(c1);

		Column c2 = new Column(localizable.getString("customerTable_relationName"),false);
		c2.setWidth("33%");
		model.addColumn(c2);


		Column c3 = new Column(localizable.getString("customerTable_id"),false);
		c3.setWidth("33%");
		model.addColumn(c3);

		return model;
	}

	private void fillTable(TableModel model, Context ctx) throws PersistenceException, GeneralException,  ContextException
	{
		com.ness.fw.ui.Row row;

		Customer customer = (Customer)ctx.getField("customer");
		ValueObjectList familyMembers;

		PLCustomerBPC customerBPC = new PLCustomerBPC();
		customerBPC.setCustomer(customer);
//		PLCustomerBPO.loadRelated(customerBPC);
		BPOProxy.execute("loadRelatedCmd",customerBPC);
		customer = customerBPC.getCustomer();
		ctx.setField("customer", customer);

		familyMembers = customer.getFamilyMemberList();
		
		model.removeAllRows();

		for (int i=0; i<familyMembers.size(); i++)
		{
			FamilyMember member = (FamilyMember)familyMembers.getValueObject(i);
			row = model.createRow();

			if(member.getId()!= null) 
			{
				row.addExtraData("ID", String.valueOf(member.getId()));
			} 
			else 
			{
				row.addExtraData("UID", String.valueOf(member.getUID()));
			}

			// add the row to the table Model
			FamilyMemeberTools.setRowWithMemeber(model, row, member, ctx);
		}

		if (familyMembers.size() > 0)
		{
			// selecting the row
			model.setSelectedRow(0);
		}
	}


	private ListModel buildaRelatedTypeModel() throws ResourceException, FatalException
	{
	//	LocalizedResources localizable = ApplicationUtil.getLocalizedResources(ctx);

		ListModel model = new ListModel();
		model.setSelectionType(UIConstants.LIST_TYPE_SINGLE);


		List list = RelatedTypeVOFactory.getAll();
		for (int i=0; i<list.size(); i++)
		{
			RelatedTypeStatus relatedTypeStatus = (RelatedTypeStatus)list.get(i);
			model.addValue(String.valueOf(relatedTypeStatus.getId()), relatedTypeStatus.getName());
		}



//		model.addValue(String.valueOf(FamilyMember.TYPE_BROTHER), localizable.getString("brother"));
//		model.addValue(String.valueOf(FamilyMember.TYPE_CHILD), localizable.getString("child"));
//		model.addValue(String.valueOf(FamilyMember.TYPE_PARENT), localizable.getString("parent"));
//		model.addValue(String.valueOf(FamilyMember.TYPE_SISTER), localizable.getString("sister"));
//		model.addValue(String.valueOf(FamilyMember.TYPE_SPOUSE), localizable.getString("spouse"));

		return model;
	}

	private ListModel buildRelatedSexModel() throws ResourceException, FatalException
	{
//		LocalizedResources localizable = ApplicationUtil.getLocalizedResources(ctx);
		ListModel model = new ListModel();
		model.setSelectionType(UIConstants.LIST_TYPE_SINGLE);

		List list = SexVOFactory.getAll();
		for (int i=0; i<list.size(); i++)
		{
			SexStatus sexStatus = (SexStatus)list.get(i);
			model.addValue(String.valueOf(sexStatus.getId()), sexStatus.getName());
		}
//		model.addValue(String.valueOf(Customer.SEX_MALE), localizable.getString("male"));
//		model.addValue(String.valueOf(Customer.SEX_FEMALE), localizable.getString("female"));
//		model.addValue(String.valueOf(Customer.SEX_UNKNOWN), localizable.getString("unknown"));
//		model.setSelectedKey(String.valueOf(Customer.SEX_MALE));

		return model;
	}
}
