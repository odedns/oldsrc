package fwpilot.desktop.operations;

import com.ness.fw.bl.proxy.BPOProxy;
import com.ness.fw.common.exceptions.GeneralException;
import com.ness.fw.common.exceptions.ResourceException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.resources.LocalizedResources;
import com.ness.fw.flower.core.Context;
import com.ness.fw.flower.core.ContextException;
import com.ness.fw.flower.core.Operation;
import com.ness.fw.flower.core.OperationExecutionException;
import com.ness.fw.flower.core.ParameterList;
import com.ness.fw.flower.util.ApplicationUtil;
import com.ness.fw.persistence.Page;
import com.ness.fw.ui.Cell;
import com.ness.fw.ui.Column;
import com.ness.fw.ui.Menu;
import com.ness.fw.ui.TableModel;
import com.ness.fw.util.DateFormatterUtil;
import fwpilot.general.bpc.PLQueueBPC;


public abstract class InitDesktop extends Operation
{

	protected abstract int getType();

	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException
	{

//		System.out.println("InitDesktop ctx = " + ctx.getName());
		Logger.debug("", "InitDesktop executed");
		try
		{
			// Filling the table with the search results
			TableModel model = (TableModel)ctx.getField("queueTableModel");
			if (model == null)
			{
				model = buildModel(ctx);
				ctx.setField("queueTableModel", model);
			}
			fillResults(ctx, model);
		}
		catch (ContextException ce)
		{
			throw new OperationExecutionException("set field ->",ce);
		}

		catch (GeneralException ge)
		{
			throw new OperationExecutionException("persistance ->",ge);
		}

	}


	private TableModel buildModel(Context ctx) throws ResourceException
	{
	
		System.out.println("building model");
	
		TableModel queueModel = new TableModel();
		LocalizedResources localizable = ApplicationUtil.getLocalizedResources(ctx);

	//	queueModel.setShowRoot(false);

//		queueModel.setId("queueTable");
		queueModel.setSelectionType(4);

		//columns
		Column col1 = new Column(localizable.getString("desktopTable_name"),true);
		col1.setLinkable(true);
		col1.setWidth("200");
		Column col2 = new Column(localizable.getString("desktopTable_stage"),true);
		col2.setWidth("100");
		Column col3 = new Column(localizable.getString("desktopTable_customer"),true);
		col3.setWidth("200");
		Column col4 = new Column(localizable.getString("desktopTable_agent"),true);
		col4.setWidth("100");
		Column col5 = new Column(localizable.getString("desktopTable_start"),true);
		col5.setWidth("100");
		Column col6 = new Column(localizable.getString("desktopTable_end"),true);
		col6.setWidth("100");
		Column col7 = new Column(localizable.getString("desktopTable_status"),true);
		col7.setWidth("80");
		Column col8 = new Column(localizable.getString("desktopTable_priority"),true);
		col8.setWidth("60");

		queueModel.addColumn(col1);
		queueModel.addColumn(col2);
		queueModel.addColumn(col3);
		queueModel.addColumn(col4);
		queueModel.addColumn(col5);
		queueModel.addColumn(col6);
		queueModel.addColumn(col7);
		queueModel.addColumn(col8);


		return queueModel;

	}


	private void fillResults(Context ctx, TableModel pModel) throws ContextException, GeneralException
	{
		com.ness.fw.ui.Row node = null;

		Page page;
		PLQueueBPC queueBPC = new PLQueueBPC();
		queueBPC.setType(getType());
		BPOProxy.execute("queueCmd", queueBPC, "server25");
		
		page = queueBPC.getPage();

//		Menu menu = new Menu();
//		
//		/*new code ala kefac*/
//		CustomEvent event1 = new CustomEvent(CustomEvent.EVENT_TYPE_READONLY,"link1");
//		CustomEvent event2 = new CustomEvent("link2");
//		CustomEvent event3 = new CustomEvent("link3");
//		CustomEvent event4 = new CustomEvent(CustomEvent.EVENT_TYPE_READONLY,"link4");
//
//		MenuItem menuItem1 = new MenuItem(localizable.getString("do"),event1);
//		MenuItem menuItem2 = new MenuItem(localizable.getString("send"),event2);
//		MenuItem menuItem3 = new MenuItem(localizable.getString("dial"),event3);
//		MenuItem menuItem4 = new MenuItem(localizable.getString("map"),event4);
//						
//		menu.addMenuItem(menuItem1);
//		menu.addMenuItem(menuItem2);
//		menu.addMenuItem(menuItem3);
//		menu.addMenuItem(menuItem4);


		pModel.removeAllRows();
		
		while (page.next())
		{
			node = buildNode(page,null,ctx);
//			node = buildNode(page,menu,ctx);
			pModel.addRow(node);
		}		
	}

	private com.ness.fw.ui.Row buildNode(Page page, Menu menu,Context ctx) throws ResourceException, ContextException
	{
		com.ness.fw.ui.Row row = new com.ness.fw.ui.Row();

		Cell cell = new Cell(page.getString("PROCESS"));
		//com.ness.fw.ui.events.Event cellEvent = new com.ness.fw.ui.events.Event();		
		//cell.setCellClickEvent(cellEvent);
		
		row.addCell(cell);

		cell = new Cell(page.getString("STEP"));
		row.addCell(cell);

		cell = new Cell(page.getString("CUSTOMER"));
		row.addCell(cell);

		cell = new Cell(page.getString("AGENT"));
		row.addCell(cell);

		cell = new Cell(DateFormatterUtil.printDate(ApplicationUtil.getLocalizedResources(ctx),page.getDate("START_DATE"),"date.inmask.0"));
		row.addCell(cell);

		cell = new Cell(DateFormatterUtil.printDate(ApplicationUtil.getLocalizedResources(ctx),page.getDate("END_DATE"),"date.inmask.0"));
		row.addCell(cell);

		cell = new Cell(page.getString("STATUS"));
		row.addCell(cell);

		cell = new Cell(String.valueOf(page.getInt("PRIORITY")));
		row.addCell(cell);


//		row.setMenu(menu);

		return row;
	}


}
