package fwpilot.validators;


import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.flower.core.ValidationProcessException;
import com.ness.fw.flower.factory.ValidationCheck;
import com.ness.fw.flower.factory.ValidationCheckBundle;
import com.ness.fw.ui.AbstractModel;
import com.ness.fw.ui.ListModel;
import com.ness.fw.ui.TableModel;
import com.ness.fw.util.Message;

import fwpilot.customer.dao.FamilyMemberDAO;

public class MultyRelationTypeValidator implements ValidationCheck
{
	//isExists
	public boolean check(ValidationCheckBundle checkBundle, AbstractModel table,  AbstractModel list) throws ValidationProcessException
	{

	//	System.out.println("MultyRelationTypeValidator");

		boolean multy = false;
		TableModel tableModel = (TableModel)table;
		int size = tableModel.getRowsCount();

		ListModel listModel = (ListModel)list;

		String selectedRelation = listModel.getValue(listModel.getSelectedKey());

		String relation = listModel.getValue(String.valueOf(FamilyMemberDAO.TYPE_SPOUSE));

	//	System.out.println("selectedType" + relation);


	//	System.out.println("rowID" + rowID);

		try
		{
			String rowID = tableModel.getSelectedRowId();

			for (int i=0; i<size; i++)
			{
				String cellRowID;
				String cellRelation = (String)tableModel.getRow(i).getCell(0).getData();

				cellRowID = tableModel.getRow(i).getId();

			//	System.out.println("cellRelation" +cellRelation + "cellID" + cellRowID);

				if (selectedRelation != null && selectedRelation.equals(relation))
				{

					// update
					if (rowID != null)
					{
						if (relation != null && (!cellRowID.equals(rowID)) && cellRelation.equals(relation))
						{
							multy = true;
						}
					}
					else
					{
						// new
						if (relation != null && cellRelation.equals(relation))
						{
							multy = true;
						}
					}
				}
			}
		}

		catch (UIException ue)
		{
			throw new ValidationProcessException("ui->",ue);
		}

		if (multy)
		{
			Message message = new Message("LK0012",Message.SEVERITY_ERROR);
			message.addFieldName(checkBundle.getValidationCheckFieldData()[1].getFieldName());
			message.addParameter(relation);
			checkBundle.getMessagesContainer().addMessage(message);

			return false;
		}
		else
		{
			return true;
		}
	}
}
