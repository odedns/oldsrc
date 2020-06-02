package fwpilot.agreement.operations;

import com.ness.fw.flower.core.*;
import com.ness.fw.flower.util.ApplicationUtil;
import com.ness.fw.persistence.Page;
import com.ness.fw.ui.*;
import com.ness.fw.ui.events.Event;
import com.ness.fw.bl.proxy.BPOProxy;
import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.logger.*;
import com.ness.fw.common.resources.LocalizedResources;
import fwpilot.agreement.bpc.PLAgreementBPC;

public class PLFillCatalogOP extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException
	{
		Logger.debug("", "PLFillCatalogOP executed");
		try
		{
			// Filling the table with the search results
			TreeTableModel model = buildModel(ctx);
			fillResults(ctx, model);
			ctx.setField("catalogTableModel", model);
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


	private TreeTableModel buildModel(Context ctx) throws ResourceException
	{
		TreeTableModel catalogModel = new TreeTableModel();
		catalogModel.setShowRoot(false);
		LocalizedResources localizable = ApplicationUtil.getLocalizedResources(ctx);

		catalogModel.setSelectionType(TreeTableModel.SELECTION_SINGLE);
		Event dblEvent = new Event();
		dblEvent.setEventTargetType(Event.EVENT_TARGET_TYPE_CLOSE_DIALOG);
		catalogModel.setRowDefaultDblClickEvent(dblEvent);

		//columns
		Column col1 = new Column(localizable.getString("catalog_name"),true);
		col1.setWidth("10%");
		Column col2 = new Column(localizable.getString("catalog_minAge"),true);
		col2.setWidth("10%");
		Column col3 = new Column(localizable.getString("catalog_maxAge"),true);
		col3.setWidth("20%");
		Column col4 = new Column(localizable.getString("catalog_endAge"),true);
		col4.setWidth("10%");
		Column col5 = new Column(localizable.getString("catalog_description"),true);
		col5.setWidth("50%");

		catalogModel.addColumn(col1);
		catalogModel.addColumn(col2);
		catalogModel.addColumn(col3);
		catalogModel.addColumn(col4);
		catalogModel.addColumn(col5);

		return catalogModel;

	}


	private void fillResults(Context ctx, TreeTableModel pModel) throws ContextException, GeneralException
	{
		String packageRowID = null;
		String rootId = null;
		int level;

		com.ness.fw.ui.Row packageRow = null;
		com.ness.fw.ui.Row programRow = null;
				
		PLAgreementBPC container = new PLAgreementBPC();
		BPOProxy.execute("getCatalogTreeCmd",container);
		Page page = container.getPage();	

		while (page.next())
		{
			level = page.getInt("LEVEL");

			// insert the root
			if (level == 0)
			{
				// do nothing
			}
			else if (level == 1)
			{
				packageRow = buildPackageNode(page);
				packageRowID = pModel.addRow(packageRow, true, rootId);
			}
			else
			{
				programRow = buildProgramNode(page);
				pModel.addRow(programRow, true, packageRowID);
			}
		}
		
		pModel.setSelectedRow(pModel.getRoot().getChild(0).getId());
	}


	private com.ness.fw.ui.Row buildPackageNode(Page page)
	{
		com.ness.fw.ui.Row row = new com.ness.fw.ui.Row();

		Cell cell = new Cell(page.getString("NAME"),"tooltip");
		row.addCell(cell);

		cell = new Cell(String.valueOf(page.getInt("MINIMAL_START_AGE")),"tooltip");
		row.addCell(cell);

		cell = new Cell(String.valueOf(page.getInt("MAXIMAL_START_AGE")),"tooltip");
		row.addCell(cell);

		cell = new Cell(String.valueOf(page.getInt("END_AGE")),"tooltip");
		row.addCell(cell);

		cell = new Cell(String.valueOf(page.getString("DESCRIPTION")),"tooltip");
		row.addCell(cell);

		row.addExtraData("ID", String.valueOf(page.getInt("PACKAGE_ID")));
		row.addExtraData("type","package");

		return row;
	}

	private com.ness.fw.ui.Row buildProgramNode(Page page)
	{
		com.ness.fw.ui.Row row = new com.ness.fw.ui.Row();

		Cell cell = new Cell(page.getString("NAME"),"tooltip");
		row.addCell(cell);

		cell = new Cell(String.valueOf(page.getInt("MINIMAL_START_AGE")),"tooltip");
		row.addCell(cell);

		cell = new Cell(String.valueOf(page.getInt("MAXIMAL_START_AGE")),"tooltip");
		row.addCell(cell);

		cell = new Cell(String.valueOf(page.getInt("END_AGE")),"tooltip");
		row.addCell(cell);

		cell = new Cell(String.valueOf(page.getString("DESCRIPTION")),"tooltip");
		row.addCell(cell);

		row.addExtraData("ID", String.valueOf(page.getInt("PROGRAM_ID")));
		row.addExtraData("type","program");
		row.setSelectable(false);
		return row;
	}
}
