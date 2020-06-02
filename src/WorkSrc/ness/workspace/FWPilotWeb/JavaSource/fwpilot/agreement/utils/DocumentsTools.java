package fwpilot.agreement.utils;

import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.resources.LocalizedResources;
import com.ness.fw.flower.core.*;
import com.ness.fw.flower.util.ApplicationUtil;
import com.ness.fw.ui.*;
import com.ness.fw.util.DateFormatterUtil;
import fwpilot.agreement.vo.*;

public class DocumentsTools 
{	

	public static AttachedDocument getDocument(Context ctx) throws UIException, ContextException 
	{
		
		AttachedDocument document = null;

		// get the table from the context
		Agreement agreement = (Agreement) (ctx).getField("agreement");
		// get the table from the context
		TableModel docuemntsTable = (TableModel)ctx.getField("documentsTableModel");

		String selectedRow = docuemntsTable.getSelectedRowId();
		// if there is a selected row
		if (selectedRow != null)
		{
			//	Get the selected row Object 
			Row row = null;
			row = docuemntsTable.getRow(selectedRow);
			// get The document ID from the extra data hash
			if (row.getExtraData("ID")!= null)
			{				
				document = agreement.getDocument(Integer.valueOf(
					row.getStringExtraData("ID")));
			}
			else 
			{
				// if it is a new document and he have a temporary ID
				// we will take the temporary id that is the External Cover Object UID
				document = agreement.getDocumentByUID(Integer.valueOf(
					row.getStringExtraData("UID")));
			}
		} 
		return document;
	}
	
	public static void setRowWithDocuemnt(TableModel tableModel, 
		Row row, AttachedDocument document, Context ctx) throws UIException, ResourceException 
	{
		
		String rowId = row.getId();
		// set doc Id cell in row
		tableModel.getCell(rowId,0).setData(document.getDocId());
		tableModel.getCell(rowId,1).setData(DocumentsTools.getSystem(document.getSystem().intValue(),ctx));
		tableModel.getCell(rowId,2).setData(DateFormatterUtil.format(document.getAttachDate()));
		// TODO: check if not fail because null
		tableModel.getCell(rowId,3).setData(document.getDescription());
	}
	
	public static  String getSystem(int documentType, Context ctx) throws ResourceException
	{
		String system = null;

		LocalizedResources localizable = ApplicationUtil.getLocalizedResources(ctx);

		if (documentType == AttachedDocument.SYSTEM_ARCHIVE)
		{
			system = localizable.getString("title_archive");
		}
		else if (documentType == AttachedDocument.SYSTEM_DIMUT)
		{
			system = localizable.getString("title_dimut");
		}
		else if (documentType == AttachedDocument.SYSTEM_FILE)
		{
			system = localizable.getString("title_system");
		}

		return system;
	}

	
}
