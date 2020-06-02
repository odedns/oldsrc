package fwpilot.validators;


import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.flower.core.ValidationProcessException;
import com.ness.fw.flower.factory.ValidationCheck;
import com.ness.fw.flower.factory.ValidationCheckBundle;
import com.ness.fw.ui.AbstractModel;
import com.ness.fw.ui.ListModel;
import com.ness.fw.ui.TableModel;
import com.ness.fw.util.Message;

public class MultyMainAddressValidator implements ValidationCheck
{
	//isExists
	public boolean check(ValidationCheckBundle checkBundle, AbstractModel table,  AbstractModel list) throws ValidationProcessException
	{

	//	System.out.println("MultyMainAddressValidator");

		boolean multy = false;
		TableModel tableModel = (TableModel)table;
		int size = tableModel.getRowsCount();

		ListModel listModel = (ListModel)list;

		boolean main = listModel.isValueSelected("0");
	//	System.out.println("main" + main);


	//	System.out.println("rowID" + rowID);


		try
		{

			String rowID =  tableModel.getSelectedRowId();

			for (int i=0; i<size; i++)
			{
				String cellRowID;
				boolean cellMain = (tableModel.getRow(i).getCell(4).getData() == "λο" ? true : false);

				cellRowID = tableModel.getRow(i).getId();

			//	System.out.println("cellMain" +cellMain);

				// update
				if (rowID != null)
				{
					if ((!cellRowID.equals(rowID)) && cellMain && main)
					{
						multy = true;
					}
				}
				// new
				else
				{
					if (cellMain && main)
					{
						multy = true;
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
			Message message = new Message("LK0013",Message.SEVERITY_ERROR);
			message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[1].getCaption());
			message.addFieldName(checkBundle.getValidationCheckFieldData()[1].getFieldName());
			checkBundle.getMessagesContainer().addMessage(message);

			return false;
		}
		else
		{
			return true;
		}
	}
}
