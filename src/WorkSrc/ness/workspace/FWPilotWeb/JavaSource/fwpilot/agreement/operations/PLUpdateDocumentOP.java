package fwpilot.agreement.operations;

import java.util.Date;

import com.ness.fw.common.exceptions.GeneralException;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.flower.core.Context;
import com.ness.fw.flower.core.ContextException;
import com.ness.fw.flower.core.Operation;
import com.ness.fw.flower.core.OperationExecutionException;
import com.ness.fw.flower.core.ParameterList;
import com.ness.fw.ui.Row;
import com.ness.fw.ui.TableModel;

import fwpilot.agreement.utils.DocumentsTools;
import fwpilot.agreement.vo.Agreement;
import fwpilot.agreement.vo.AttachedDocument;

public class PLUpdateDocumentOP extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException
	{
		Logger.debug("", "PLUpdateDocumentOP executed");
		try
		{

			AttachedDocument document = null;
			Row row = null;
			
			// get the model
			TableModel model = (TableModel)ctx.getField("documentsTableModel");

			// get the agreeemnt
			Agreement  agreement = (Agreement)ctx.getField("agreement");

			if (model.getSelectedRowId() == null)
			{
				// creating the document
				document = agreement.createDocument();
				updateDocuemnt(ctx,document);

				// adding the document to the agreement
				agreement.addDocument(document);
				
				row = model.createRow();
				// setting the extraData
				row.addExtraData("ID", null);
				row.addExtraData("UID",String.valueOf(document.getUID()));

				DocumentsTools.setRowWithDocuemnt(model,row,document,ctx);
	
				model.setSelectedRow(row.getId());
				ctx.setField("numDocs",new Integer(agreement.getDocumentsCount()));
			}
			else
			{
				// find the selected ext. cover
				document = DocumentsTools.getDocument(ctx);
				// update the ExternalCover object from context
				updateDocuemnt(ctx, document);
				// update row in table
				row = model.getSelectedRow();
				DocumentsTools.setRowWithDocuemnt(model, row, document,ctx);			
			}
		}

		catch(UIException ue)
		{
			throw new OperationExecutionException("UpdateDocument->UI", ue);
		}

		catch(ContextException ce)
		{
			throw new OperationExecutionException("UpdateDocument->Context", ce);
		}

		catch(GeneralException ge)
		{
			throw new OperationExecutionException("UpdateDocument->General", ge);
		}

	}

	private void updateDocuemnt(Context ctx, AttachedDocument document) throws ContextException
	{
		document.setDocId((ctx.getXIInteger("docId")));
		document.setAttachDate(new Date((ctx.getXIDate("attachDate")).getTime()));
//		document.setSystem((ctx.getXIInteger("systemCode")));
		document.setDescription(ctx.getXIString("docDescription"));
	}

}
