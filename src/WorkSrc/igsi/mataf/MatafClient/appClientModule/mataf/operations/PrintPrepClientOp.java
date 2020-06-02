package mataf.operations;

import java.io.IOException;

import mataf.services.printer.PrinterConstants;
import mataf.services.printer.PrinterService;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;

/**
 * @author Tibi Glazer
 *
 * Class: PrintClientOp.java
 * Description: Generic print data prepare client operation
 * Created at: 21/12/2003
 * 
 */
public class PrintPrepClientOp extends DSEClientOperation {

	/**
	 * Constructor for PrintPrepClientOp.
	 */
	public PrintPrepClientOp() {
		super();
	}

	/**
	 * Constructor for PrintPrepClientOp.
	 * @param arg0
	 * @throws IOException
	 */
	public PrintPrepClientOp(String arg0) throws IOException {
		super(arg0);
	}

	/**
	 * Constructor for PrintPrepClientOp.
	 * @param arg0
	 * @param arg1
	 * @throws IOException
	 * @throws DSEInvalidRequestException
	 */
	public PrintPrepClientOp(String arg0, Context arg1) throws IOException, DSEInvalidRequestException {
		super(arg0, arg1);
	}

	/**
	 * Constructor for PrintPrepClientOp.
	 * @param arg0
	 * @param arg1
	 * @throws IOException
	 * @throws DSEInvalidRequestException
	 * @throws DSEObjectNotFoundException
	 */
	public PrintPrepClientOp(String arg0, String arg1) throws IOException, DSEInvalidRequestException, DSEObjectNotFoundException {
		super(arg0, arg1);
	}

	/**
	 * @see com.ibm.dse.base.Operation#execute()
	 */
	public void execute() throws Exception {
		PrinterService ps = (PrinterService)getService("printerService");
		setValueAt(PrinterConstants.REPLACE_CONTROL_TAGS, Boolean.FALSE);
		String s = ps.buildForm(null,getContext());
		setValueAt(PrinterConstants.PRINT_DATA, s);
		setValueAt(PrinterConstants.REPLACE_CONTROL_TAGS, Boolean.TRUE);
		setValueAt(PrinterConstants.CODEPAGE, PrinterConstants.CP_WIN);
	    IndexedCollection icol = (IndexedCollection)getElementAt("printDataList");
    	KeyedCollection kcol = (KeyedCollection)DataElement.readObject("printDataRecord");
        kcol.setValueAt("printPage",ps.buildForm(s,getContext()));
        icol.addElement(kcol);
	}
}
