package fwpilot.agreement.operations;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.flower.core.*;
import com.ness.fw.ui.*;
import fwpilot.agreement.vo.*;

public class PLDeleteAllDocumentOP extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException, FatalException, AuthorizationException
	{
		Logger.debug("", "DeleteDocument executed");

		try
		{
			Agreement agreement = (Agreement)ctx.getField("agreement");
			TableModel model = (TableModel)ctx.getField("documentsTableModel");	
			if (model.getRowsCount() > 0)
			{
				agreement.removeAllDocuments();
				ctx.setField("numDocs",0);
				deleteDocuments(model);
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

	private void deleteDocuments(TableModel tableModel) throws UIException
	{
		tableModel.removeAllRows();
	}
}
