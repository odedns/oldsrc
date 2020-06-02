package fwpilot.agreement.operations;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.flower.core.*;
import com.ness.fw.ui.*;

import fwpilot.agreement.utils.DocumentsTools;
import fwpilot.agreement.vo.*;

public class PLDeleteDocumentOP extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException, FatalException, AuthorizationException
	{
		Logger.debug("", "DeleteDocument executed");

		try
		{
			Agreement agreement = (Agreement)ctx.getField("agreement");
			TableModel model = (TableModel)ctx.getField("documentsTableModel");	
			AttachedDocument document = DocumentsTools.getDocument(ctx);
			if (document != null)
			{
				agreement.removeDocument(document);
				ctx.setField("numDocs",new Integer(agreement.getNonDeletedDocumentsCount()));
				deleteDocument(model);
			}
		}

		catch (UIException ue)
		{
			throw new OperationExecutionException("DeleteDocument->UI", ue);
		}

		catch (ContextException ce)
		{
			throw new OperationExecutionException("DeleteDocument->Context", ce);
		}
	}

	private void deleteDocument(TableModel tableModel) throws UIException
	{
		// delete this document row from the table
		String selectedRow = tableModel.getSelectedRowId();
		// if there is a selected Row 
		if (selectedRow != null)
		{
			tableModel.removeRow(selectedRow);
		}
		
		// in case there are other documents to show
		//	select the first row 
		if (tableModel.getRowsCount() > 0)
		{
			tableModel.setSelectedRow(0);
		}
	}
}
