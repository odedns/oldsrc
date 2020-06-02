package fwpilot.common.operations;

import java.util.ArrayList;
import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.resources.LocalizedResources;
import com.ness.fw.flower.core.*;
import com.ness.fw.flower.util.ApplicationUtil;
import com.ness.fw.persistence.Page;
import com.ness.fw.ui.*;

public class TablePaging
{
		
	protected static Row createRow(Page page, LocalizedResources localizable) throws UIException, ResourceException
	{
		return null;
	}

	public static void fillPage(Context ctx, Page page, TableModel model) throws ContextException, FatalException, AuthorizationException, UIException, ResourceException
	{
		LocalizedResources localizable = ApplicationUtil.getLocalizedResources(ctx);

		com.ness.fw.ui.Row row = null;
		ArrayList extraData = new ArrayList();
		model.removeAllRows();

		// Adding page's rows to the model
		while(page.next())
		{
			row = createRow(page,localizable);
			model.addRow(row);
		}

		// Sorting the model after adding the rows
		if (model.getRowsCount() > 0)
		{
			model.sort();
		}
	}
}