package fwpilot.customers.utils;

import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.resources.LocalizedResources;
import com.ness.fw.flower.core.*;
import com.ness.fw.flower.util.ApplicationUtil;
import com.ness.fw.persistence.Page;
import com.ness.fw.ui.*;


public class CustomerTablePaging 
{
		
	public static void fillPage(Context ctx, Page page, TableModel model) throws ContextException, FatalException, AuthorizationException, UIException, ResourceException
		{
			LocalizedResources localizable = ApplicationUtil.getLocalizedResources(ctx);

			com.ness.fw.ui.Row row = null;
			int lastPage = model.getSelectedPage();
			model.removeAllRows();
			model.setSelectedPage(lastPage);

			// Adding page's rows to the model
			if (page != null)
			{
				while(page.next())
				{
					row = CustomerTools.createRow(page,localizable);
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