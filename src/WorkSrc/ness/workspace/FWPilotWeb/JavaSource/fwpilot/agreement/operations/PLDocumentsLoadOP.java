package fwpilot.agreement.operations;

import java.util.Date;

import fwpilot.agreement.utils.DocumentsTools;
import fwpilot.agreement.vo.AttachedDocument;
import com.ness.fw.flower.core.*;
import com.ness.fw.ui.*;
import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.logger.*;

public class PLDocumentsLoadOP extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException, FatalException, AuthorizationException
	{
		Logger.debug("", "PLDocumentsLoadOP executed");

		try
		{
			// Getting the model
			TableModel model = (TableModel)ctx.getField("documentsTableModel");

			// get the selected row
			String selectedRow = model.getSelectedRowId();

			if (selectedRow == null)
			{
				resetDocumentDetails(ctx);
			}
			else
			{
				// update document details pane from the associated object
				AttachedDocument document = DocumentsTools.getDocument(ctx); 
				loadDocuemntDetails(ctx, document);
			}
		}

		catch(ContextException ce)
		{
			throw new OperationExecutionException("context", ce);
		}

		catch(UIException ue)
		{
			throw new OperationExecutionException("ui", ue);
		} 
		
		catch (ResourceException re)
		{
			throw new OperationExecutionException("resource", re);
		}
	}

	private void resetDocumentDetails(Context ctx) throws ContextException, NumberFormatException, FatalException, AuthorizationException, ResourceException
	{
		ctx.setField("docId", null);
//		ctx.setField("system",null);
//		ctx.setField("systemCode",null);


		ListModel documentsRadioModel = (ListModel)ctx.getField("documentTypeModel");
		String documentType = documentsRadioModel.getSelectedKey();
		ctx.setField("systemCode", new Integer(documentType));
		ctx.setField("system", DocumentsTools.getSystem(Integer.parseInt(documentType),ctx));

		ctx.setField("attachDate",new Date());
		ctx.setField("docDescription",null);		
	}

	private void loadDocuemntDetails(Context ctx, AttachedDocument document) throws ContextException, FatalException, AuthorizationException, ResourceException
	{
		ctx.setField("docId",document.getDocId());
		ctx.setField("system",DocumentsTools.getSystem(document.getSystem().intValue(),ctx));
		ctx.setField("systemCode",document.getSystem());
		ctx.setField("attachDate",new Date(document.getAttachDate().getTime()));
		ctx.setField("docDescription",document.getDescription());
	}
}