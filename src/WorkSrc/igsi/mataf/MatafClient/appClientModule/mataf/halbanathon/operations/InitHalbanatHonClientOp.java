package mataf.halbanathon.operations;

import java.awt.Cursor;
import java.io.IOException;

import mataf.general.operations.MatafClientOp;
import mataf.proxyhandlers.GlobalFieldsUpdateHandler;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEInvalidClassException;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DataMapperFormat;
import com.ibm.dse.base.FormatElement;
import com.ibm.dse.base.OperationRepliedEvent;
import com.ibm.dse.desktop.Desktop;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (27/10/2003 12:28:53).  
 */
public class InitHalbanatHonClientOp extends MatafClientOp {

	/**
	 * @see mataf.general.operations.MatafClientOp#execute()
	 */
	public void execute() throws Exception 
	{
		try 
		{
			Desktop.getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			
			Context ctx = getContext();
			ctx.chainToContextNamed("halbanathonCtx");
			
			// Operate a generic formatter.
//			mapHalbanatHonData();
			
			executeOp();			
			
			ctx.unchain();
			fireHandleOperationRepliedEvent(new OperationRepliedEvent(this));
		} 
		catch(Exception ex) 
		{
			ex.printStackTrace();
		}
		finally 
		{
			Desktop.getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
	/**
	 * Gets the appropriate mapper from the business client dseData XML.
	 */
	private void mapHalbanatHonData() 
			throws DSEException, IOException {
		String mapperName = (String) getValueAt("HalbanatHonDataMapperName");
		DataMapperFormat dmf = (DataMapperFormat) FormatElement.readObject(mapperName);
		dmf.mapContents(getContext(), getContext());
	}
}
