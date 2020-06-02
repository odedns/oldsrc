package fwpilot.customers.utils;

import com.ness.fw.common.exceptions.ResourceException;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.flower.core.Context;
import com.ness.fw.flower.core.ContextException;
import com.ness.fw.ui.ListModel;
import com.ness.fw.ui.Row;
import com.ness.fw.ui.TableModel;

import fwpilot.customer.vo.Customer;
import fwpilot.customer.vo.FamilyMember;

public class FamilyMemeberTools 
{	

	public static FamilyMember getFamilyMember(Context ctx) throws UIException, ContextException 
	{
		
		FamilyMember familyMember = null;

		// get the table from the context
		Customer customer  = (Customer) (ctx).getField("customer");
		// get the table from the context
		TableModel model = (TableModel)ctx.getField("relatedTableModel");

		String selectedRow = model.getSelectedRowId();
		// if there is a selected row
		if (selectedRow != null)
		{
			//	Get the selected row Object 
			Row row = null;
			row = model.getRow(selectedRow);
			// get The document ID from the extra data hash
			if (row.getExtraData("ID")!= null)
			{				
				familyMember = customer.getFamilyMember(Integer.valueOf(
					row.getStringExtraData("ID")));
			}
			else 
			{
				// if it is a new document and he have a temporary ID
				// we will take the temporary id that is the External Cover Object UID
				familyMember = customer.getFamilyMemberByUID(Integer.valueOf(
					row.getStringExtraData("UID")));
			}
		} 
		return familyMember;
	}
	
	public static void setRowWithMemeber(TableModel tableModel, 
		Row row, FamilyMember member, Context ctx) throws ResourceException, ContextException, UIException 
	{
		
		String rowId = row.getId();

		tableModel.getCell(rowId,0).setData(getTranslationType(member.getType().getId().intValue(), ctx));
		tableModel.getCell(rowId,1).setData(member.getFirstName() + " " + member.getLastName());
		tableModel.getCell(rowId,2).setData(String.valueOf(member.getIdentification()));
	}
	
	public static String getTranslationType(int type, Context ctx) throws ContextException
	{
		ListModel customerRelatedTypeModel = (ListModel) ctx.getField("customerRelatedTypeModel");
		return customerRelatedTypeModel.getValue(String.valueOf(type));
	}

	
}
