package fwpilot.customer.operations;


import java.util.Date;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.flower.core.Context;
import com.ness.fw.flower.core.ContextException;
import com.ness.fw.flower.core.Operation;
import com.ness.fw.flower.core.OperationExecutionException;
import com.ness.fw.flower.core.ParameterList;
import com.ness.fw.ui.ListModel;
import com.ness.fw.ui.TableModel;

import fwpilot.customers.utils.FamilyMemeberTools;
import fwpilot.customer.vo.FamilyMember;

public class PLLoadRelatedOP extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException, FatalException, AuthorizationException
	{
		Logger.debug("", "RelatedRowClick executed");

		try
		{
			// Getting the model
			TableModel model = (TableModel)ctx.getField("relatedTableModel");

			// get the selected row
			String selectedRow = model.getSelectedRowId();

			if (selectedRow == null)
			{
				resetFamilyMemberDetails(ctx);
			}
			else
			{
				FamilyMember familyMember = FamilyMemeberTools.getFamilyMember(ctx); 
				loadDocuemntDetails(ctx, familyMember);
			}
		}

		catch(ContextException ce)
		{
			throw new OperationExecutionException("Context->", ce);
		}

		catch(UIException ue)
		{
			throw new OperationExecutionException("UI->", ue);
		}
	}

	private void loadDocuemntDetails(Context ctx, FamilyMember member) throws ContextException, FatalException, AuthorizationException
	{
		// Loading the object fields into the contexts fields
		ctx.setField("relatedIdentification",member.getIdentification());
		ctx.setField("relatedFirstName",member.getFirstName());
		ctx.setField("relatedLastName", member.getLastName());
		ctx.setField("relatedBirthDate", member.getBirthDate() == null ? null : new Date(member.getBirthDate().getTime()));

		ListModel customerRelatedTypeModel = (ListModel)ctx.getField("customerRelatedTypeModel");
		customerRelatedTypeModel.setSelectedKey(String.valueOf(member.getType().getId()));

		ListModel relatedSexModel = (ListModel) ctx.getField("relatedSexModel");
		relatedSexModel.setSelectedKey(String.valueOf(member.getSex().getId()));				
	}

	private void resetFamilyMemberDetails(Context ctx) throws ContextException, FatalException, AuthorizationException
	{
		// Loading the object fields into the contexts fields
		ctx.setField("relatedIdentification",null);
		ctx.setField("relatedFirstName",null);
		ctx.setField("relatedLastName",null);
		ctx.setField("relatedBirthDate", new Date());

		ListModel customerRelatedTypeModel = (ListModel)ctx.getField("customerRelatedTypeModel");
		customerRelatedTypeModel.unSelectAllKeys();

		ListModel relatedSexModel = (ListModel) ctx.getField("relatedSexModel");
		relatedSexModel.unSelectAllKeys();				
	}

}
