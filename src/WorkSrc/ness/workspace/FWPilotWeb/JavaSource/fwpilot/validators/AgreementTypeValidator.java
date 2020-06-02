package fwpilot.validators;


import java.util.Date;

import com.ness.fw.flower.core.ValidationProcessException;
import com.ness.fw.flower.factory.ValidationCheck;
import com.ness.fw.flower.factory.ValidationCheckBundle;
import com.ness.fw.ui.AbstractModel;
import com.ness.fw.ui.ListModel;
import com.ness.fw.util.Message;

import fwpilot.agreement.dao.AgreementDAO;

public class AgreementTypeValidator implements ValidationCheck
{
	//isExists
	public boolean check(ValidationCheckBundle checkBundle, Date startDate, Date endDate, AbstractModel typeModel) throws ValidationProcessException
	{

	//	System.out.println("AgreementTypeValidator");

		ListModel listModel = (ListModel)typeModel;

		int type = Integer.parseInt(listModel.getSelectedKey());
	//	System.out.println("type" + type);

		if (type == AgreementDAO.TYPE_SALE && (startDate == null || endDate == null))
		{
			Message message = new Message("LK0014",Message.SEVERITY_INFO);
			message.addFieldName("startDate");
			message.addFieldName("endDate");
			checkBundle.getMessagesContainer().addMessage(message);
		}

		// it's an info. shouldn't stop the process
		return true;


	}
}
