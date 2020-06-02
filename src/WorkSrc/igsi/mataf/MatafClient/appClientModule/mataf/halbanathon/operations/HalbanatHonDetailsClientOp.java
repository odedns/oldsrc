package mataf.halbanathon.operations;

import java.awt.Cursor;

import mataf.general.operations.MatafClientOp;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.desktop.Desktop;

/**
 * 
 * 
 * @author Nati Dykstein. Creation Date : (13/11/2003 13:04:03).  
 */
public class HalbanatHonDetailsClientOp extends MatafClientOp 
{	
	/**
	 * @see mataf.general.operations.MatafClientOp#execute()
	 */
	public void execute() throws Exception 
	{
		try 
		{
			Desktop.getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			
			Context ctx = getContext();			
			ctx.chainToContextNamed("halbanatHonDetailsCtx");
			
			executeOp();
			
			ctx.unchain();	
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
}
