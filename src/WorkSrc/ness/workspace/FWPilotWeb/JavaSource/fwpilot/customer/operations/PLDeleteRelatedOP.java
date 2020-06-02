package fwpilot.customer.operations;

import com.ness.fw.flower.core.*;
import com.ness.fw.ui.*;
import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.logger.*;
import fwpilot.customer.vo.*;
import fwpilot.customers.utils.FamilyMemeberTools;

public class PLDeleteRelatedOP extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException
	{
		Logger.debug("", "PLDeleteRelatedOP executed");

		try
		{
			Customer customer = (Customer)ctx.getField("customer");
			TableModel model = (TableModel)ctx.getField("relatedTableModel");	
			FamilyMember familyMember = FamilyMemeberTools.getFamilyMember(ctx);
			if (familyMember != null)
			{
				customer.removeFamilyMember(familyMember);
				deleteFamilyMember(model);
			}
		}

		catch (UIException ue)
		{
			throw new OperationExecutionException("DeleteRelated->UI", ue);
		}

		catch (ContextException ce)
		{
			throw new OperationExecutionException("DeleteRelated->Context", ce);
		}

	}

	private void deleteFamilyMember(TableModel tableModel) throws UIException
	{
		// delete this document row from the table
		String selectedRow = tableModel.getSelectedRowId();
		// if there is a selected Row 
		if (selectedRow != null)
		{
			tableModel.removeRow(selectedRow);
		}
		
		// in case there are other documents to show
		//	select the first row 
		if (tableModel.getRowsCount() > 0)
		{
			tableModel.setSelectedRow(0);
		}
	}
}
