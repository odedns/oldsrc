package fwpilot.agreement.operations;

import com.ness.fw.bl.ValueObjectList;
import com.ness.fw.bl.proxy.BPOProxy;
import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.exceptions.GeneralException;
import com.ness.fw.common.exceptions.ResourceException;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.resources.LocalizedResources;
import com.ness.fw.flower.core.*;
import com.ness.fw.flower.util.ApplicationUtil;
import com.ness.fw.ui.*;
import fwpilot.agreement.bpc.PLAgreementBPC;
import fwpilot.agreement.utils.DocumentsTools;
import fwpilot.agreement.vo.*;

public class PLInitDocumentsOP extends Operation
{

	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException, FatalException, AuthorizationException
	{
		Logger.debug("", "PLInitDocumentsOP executed");
		try
		{
			TableModel model = null;
			if (ctx.getField("documentsTableModel") == null)
			{
				model = buildDocumentTableModel(ctx);
				fillTable(model,ctx);
				ctx.setField("documentsTableModel", model);
			}

			if (ctx.getField("documentTypeModel") == null)
			{
				ctx.setField("documentTypeModel",buildDocumentTypeModel(ctx));
			}

		}

		catch (ContextException ce)
		{
			throw new OperationExecutionException("context",ce);
		} 
		catch (ResourceException re)
		{
			throw new OperationExecutionException("resource",re);
		} 
		
		catch (UIException ue)
		{
			throw new OperationExecutionException("ui",ue);
		} 
		catch (GeneralException ge)
		{
			throw new OperationExecutionException("general",ge);
		}
	}

	private ListModel buildDocumentTypeModel(Context ctx) throws ResourceException
	{

		LocalizedResources localizable = ApplicationUtil.getLocalizedResources(ctx);
		ListModel model = new ListModel();
		model.setSelectionType(UIConstants.LIST_TYPE_SINGLE);
		model.addValue(String.valueOf(AttachedDocument.SYSTEM_DIMUT), localizable.getString("dimut"),true);
		model.addValue(String.valueOf(AttachedDocument.SYSTEM_ARCHIVE), localizable.getString("archive"));
		model.addValue(String.valueOf(AttachedDocument.SYSTEM_FILE), localizable.getString("system"));

		return model;
	}


	private TableModel buildDocumentTableModel(Context ctx) throws ResourceException
	{
		TableModel model = new TableModel();
		model.setSelectionType(AbstractTableModel.SELECTION_SINGLE);
		model.setAllowMenus(false);
		LocalizedResources localizable = ApplicationUtil.getLocalizedResources(ctx);

		// columns
		Column c1 = new Column(localizable.getString("documentsTable_id"),false);
		c1.setWidth("20%");
		model.addColumn(c1);

		Column c2 = new Column(localizable.getString("documentsTable_system"),false);
		c2.setWidth("20%");
		model.addColumn(c2);


		Column c3 = new Column(localizable.getString("documentsTable_date"),false);
		c3.setWidth("20%");
		model.addColumn(c3);


		Column c4 = new Column(localizable.getString("documentsTable_description"),false);
		c4.setWidth("40%");
		model.addColumn(c4);

		return model;

	}

	private void fillTable(TableModel tableModel, Context ctx) throws GeneralException
	{
		
		Agreement agreement = (Agreement)ctx.getField("agreement");
		
		PLAgreementBPC container = new PLAgreementBPC();
		container.setAgreement(agreement);
		BPOProxy.execute("loadDocumentCmd", container);
		agreement = container.getAgreement();
		ValueObjectList documents = agreement.getDocumentList();
		tableModel.removeAllRows();

		for (int i=0; i<documents.size(); i++)
		{
			AttachedDocument doc = (AttachedDocument)documents.getValueObject(i);
			Row row = tableModel.createRow();
			// save the ID of the row

			// TODO: seems redundant. check it
			if(doc.getId()!= null) 
			{
				row.addExtraData("ID", String.valueOf(doc.getId()));
			} 
			else 
			{
				row.addExtraData("UID", String.valueOf(doc.getUID()));
			}
			
			// add the row to the table Model
			DocumentsTools.setRowWithDocuemnt(tableModel, row, doc, ctx);
		}

		if (documents.size() > 0)
		{
			// selecting the row
			tableModel.setSelectedRow(0);
		}
	}
}
