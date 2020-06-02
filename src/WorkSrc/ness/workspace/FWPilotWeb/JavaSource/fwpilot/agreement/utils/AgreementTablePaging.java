package fwpilot.agreement.utils;

import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.resources.LocalizedResources;
import com.ness.fw.flower.core.*;
import com.ness.fw.flower.util.ApplicationUtil;
import com.ness.fw.persistence.Page;
import com.ness.fw.ui.*;

public class AgreementTablePaging 
{
		
	public static void fillPage(Context ctx, Page page, TableModel model) throws ContextException, FatalException, AuthorizationException, UIException, ResourceException
		{
			LocalizedResources localizable = ApplicationUtil.getLocalizedResources(ctx);

			com.ness.fw.ui.Row row = null;
			model.removeAllRows();

			// Adding page's rows to the model
			if (page != null)
			{
				while(page.next())
				{
					row = AgreementsTools.createRow(page,localizable);
					model.addRow(row);
				}
	
				// Sorting the model after adding the rows
				if (model.getRowsCount() > 0)
				{
					model.sort();
				}
			}
		}
}