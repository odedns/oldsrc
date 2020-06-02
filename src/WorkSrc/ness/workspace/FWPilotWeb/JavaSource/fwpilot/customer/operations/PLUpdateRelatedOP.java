package fwpilot.customer.operations;

import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.exceptions.GeneralException;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.flower.core.Context;
import com.ness.fw.flower.core.ContextException;
import com.ness.fw.flower.core.Operation;
import com.ness.fw.flower.core.OperationExecutionException;
import com.ness.fw.flower.core.ParameterList;
import com.ness.fw.ui.ListModel;
import com.ness.fw.ui.Row;
import com.ness.fw.ui.TableModel;

import fwpilot.customers.utils.FamilyMemeberTools;
import fwpilot.customer.vo.Customer;
import fwpilot.customer.vo.FamilyMember;
import fwpilot.customer.vo.RelatedTypeVOFactory;
import fwpilot.customer.vo.SexVOFactory;

public class PLUpdateRelatedOP extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException
	{
		Logger.debug("", "PLUpdateRelatedOP executed");
		try
		{

			FamilyMember member = null;
			Row row = null;
			
			// get the model
			TableModel model = (TableModel)ctx.getField("relatedTableModel");

			// get the customer
			Customer  customer = (Customer)ctx.getField("customer");

			if (model.getSelectedRow() == null)
			{
				// create the document according to the choice
				member = customer.createFamilyMemeber();
				updateMember(member,ctx);
				customer.addFamilyMember(member);

				row = model.createRow();
				// setting the extraData
				row.addExtraData("ID", null);
				row.addExtraData("UID",String.valueOf(member.getUID()));

				FamilyMemeberTools.setRowWithMemeber(model,row,member,ctx);
				model.setSelectedRow(row.getId());
			}
			else
			{
				// find the selected member
				member = FamilyMemeberTools.getFamilyMember(ctx);
				// update the FamilyMember object from context
				updateMember(member,ctx);
				// update row in table
				row = model.getSelectedRow();
				FamilyMemeberTools.setRowWithMemeber(model, row, member,ctx);			
			}
		}

		catch(UIException ue)
		{
			throw new OperationExecutionException("UpdateRelated->UI", ue);
		}

		catch(ContextException ce)
		{
			throw new OperationExecutionException("UpdateRelated->Context", ce);
		}

		catch(GeneralException ge)
		{
			throw new OperationExecutionException("UpdateRelated->General", ge);
		}

	}


	private void updateMember(FamilyMember member, Context ctx) throws ContextException, NumberFormatException, FatalException
	{
		member.setIdentification(ctx.getXIInteger("relatedIdentification"));
		member.setFirstName(ctx.getXIString("relatedFirstName"));
		member.setLastName((ctx.getXIString("relatedLastName")));
		member.setBirthDate(ctx.getXIDate("relatedBirthDate"));

		// updating customer sex
		ListModel relatedSexModel = (ListModel) ctx.getField("relatedSexModel");

//		member.setSex(new Integer((relatedSexModel.getSelectedKey())));
		member.setSex(SexVOFactory.getById(Integer.parseInt(relatedSexModel.getSelectedKey())));

		// updating customer related
		ListModel customerRelatedTypeModel = (ListModel) ctx.getField("customerRelatedTypeModel");
//		member.setType(new Integer((customerRelatedTypeModel.getSelectedKey())));
		member.setType(RelatedTypeVOFactory.getById(Integer.parseInt(customerRelatedTypeModel.getSelectedKey())));

	}
}
