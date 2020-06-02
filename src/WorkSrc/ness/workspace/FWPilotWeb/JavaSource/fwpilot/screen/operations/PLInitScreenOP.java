package fwpilot.screen.operations;

import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.resources.LocalizedResources;
import com.ness.fw.flower.core.*;
import com.ness.fw.flower.util.ApplicationUtil;
import com.ness.fw.ui.*;
import com.ness.fw.ui.events.*;
import com.ness.fw.ui.events.Event;

public class PLInitScreenOP extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException, FatalException, AuthorizationException
	{
		Logger.debug("", "PLInitScreenOP executed");
		try
		{
			ctx.setField("checkTableModel", buildSearchResultTableModel(ctx));
			fillResults(ctx);
		}
		catch (ContextException ce)
		{
			throw new OperationExecutionException("set field ->",ce);
		}

		catch (ResourceException re)
		{
			throw new OperationExecutionException("resources->",re);
		} 
		
		catch (GeneralException e)
		{
			throw new OperationExecutionException("general->",e);
		}
	}

	private TableModel buildSearchResultTableModel(Context ctx) throws ResourceException
	{
		LocalizedResources localizable = ApplicationUtil.getLocalizedResources(ctx);
		TableModel model = new TableModel();
		model.setSelectionType(1);

		model.setHeader(localizable.getString("searchResults"));
		model.setSummary("&nbsp;");

		// columns
		Column c1 = new Column(localizable.getString("agreementTable_name"),true);	
		Event link = new Event();
		link.setEventTargetType(Event.EVENT_TARGET_TYPE_DEFAULT);
		c1.setCellClickEvent(link);
		c1.setWidth("50%");
		model.addColumn(c1);

		Column c2 = new Column(localizable.getString("agreementTable_id"),false);
		c2.setWidth("25%");
		model.addColumn(c2);

		Column c3 = new Column(localizable.getString("agreementTable_status"),false);
		c3.setWidth("25%");
		model.addColumn(c3);

		return model;
	}
		
	private void fillResults(Context ctx) throws ContextException, GeneralException
	{
		TableModel model = (TableModel)ctx.getField("checkTableModel");
		model.setAllowMenus(true);

		LocalizedResources localizable = ApplicationUtil.getLocalizedResources(ctx);
		com.ness.fw.ui.Row row = new com.ness.fw.ui.Row();
		Cell cell = new Cell("x");
		row.addCell(cell);
		cell = new Cell("y");
		row.addCell(cell);
		cell = new Cell("z");
		row.addCell(cell);
		
		CustomEvent link1 = new CustomEvent("link1");
		link1.setEventTargetType(Event.EVENT_TARGET_TYPE_DEFAULT);
		com.ness.fw.ui.MenuItem menuItem1 = new com.ness.fw.ui.MenuItem(localizable.getString("btnSame"),link1);

		CustomEvent link2 = new CustomEvent("link4");
		link2.setEventTargetType(Event.EVENT_TARGET_TYPE_DIALOG);
		com.ness.fw.ui.MenuItem menuItem2 = new com.ness.fw.ui.MenuItem(localizable.getString("btnDialog"),link2);

		CustomEvent link3 = new CustomEvent("link5");
		link3.setEventTargetType(Event.EVENT_TARGET_TYPE_POPUP);
		link3.setWindowExtraParams("scroll=no,status=no,height=250,width=350,left=550,top=250");
		com.ness.fw.ui.MenuItem menuItem3 = new com.ness.fw.ui.MenuItem(localizable.getString("btnPopup"),link3);
	
		Menu menu = new Menu();
		menu.addMenuItem(menuItem1);
		menu.addMenuItem(menuItem2);
		menu.addMenuItem(menuItem3);

		row.setMenu(menu);
		model.addRow(row);
	}		
}
