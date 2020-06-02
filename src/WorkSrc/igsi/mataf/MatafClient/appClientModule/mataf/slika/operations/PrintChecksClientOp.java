package mataf.slika.operations;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.RecordFormat;
import com.ibm.dse.desktop.Desktop;
import com.mataf.dse.appl.OpenDesktop;

import mataf.services.printer.*;

/**
 * @author Tibi Glazer
 *
 * Class: PrintFormClientOp.java
 * Description:
 * Created at: 21/12/2003
 * 
 */
public class PrintChecksClientOp extends DSEClientOperation {

	/**
	 * @see com.ibm.dse.base.Operation#execute()
	 */
	public void execute() throws Exception {
		try {
			DSEClientOperation op1 = (DSEClientOperation) readObject("printPrepClientOp");
			DSEClientOperation op2 = (DSEClientOperation) readObject("printExecClientOp");
			KeyedCollection kcol = (KeyedCollection) getElementAt("CZSP_LAZER_T110");
			kcol.setValueAt(PrinterConstants.FORM_NAME, "CZ_CHQ10");
			kcol.setValueAt(PrinterConstants.FORMAT_ID, "CZSP_LAZER_T110Fmt");
			kcol.setValueAt(PrinterConstants.PRINT_DATA, null);
 			Context ctx = (Context) op1.getContext();
 			ctx.chainTo(getContext());
			ctx.clearKeyedCollection();
			ctx.setKeyedCollection(kcol);
			op1.execute();
			ctx.unchain();
			String s = (String) kcol.getValueAt(PrinterConstants.PRINT_DATA);
			/* now s can be saved into the journal */
			ctx = (Context) op2.getContext();
			ctx.chainTo(getContext());
			ctx.clearKeyedCollection();
			ctx.setKeyedCollection(op1.getKeyedCollection());
			op2.execute();
			ctx.unchain();

		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
