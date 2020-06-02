package mataf.operations;

import java.io.IOException;

import mataf.services.printer.PrinterConstants;
import mataf.services.printer.PrinterService;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.DSEObjectNotFoundException;

/**
 * @author Tibi Glazer
 *
 * Class: PrintClientOp.java
 * Description: Generic print client operation
 * Created at: 21/12/2003
 * 
 */
public class PrintExecClientOp extends DSEClientOperation {

	/**
	 * Constructor for PrintExecClientOp.
	 */
	public PrintExecClientOp() {
		super();
	}

	/**
	 * Constructor for PrintExecClientOp.
	 * @param arg0
	 * @throws IOException
	 */
	public PrintExecClientOp(String arg0) throws IOException {
		super(arg0);
	}

	/**
	 * Constructor for PrintExecClientOp.
	 * @param arg0
	 * @param arg1
	 * @throws IOException
	 * @throws DSEInvalidRequestException
	 */
	public PrintExecClientOp(String arg0, Context arg1) throws IOException, DSEInvalidRequestException {
		super(arg0, arg1);
	}

	/**
	 * Constructor for PrintExecClientOp.
	 * @param arg0
	 * @param arg1
	 * @throws IOException
	 * @throws DSEInvalidRequestException
	 * @throws DSEObjectNotFoundException
	 */
	public PrintExecClientOp(String arg0, String arg1) throws IOException, DSEInvalidRequestException, DSEObjectNotFoundException {
		super(arg0, arg1);
	}

	/**
	 * @see com.ibm.dse.base.Operation#execute()
	 */
	public void execute() throws Exception {
		PrinterService ps = (PrinterService)getService("printerService");
        String s = (String)getValueAt(PrinterConstants.PRINT_DATA);
        if (((String)getValueAt(PrinterConstants.PRINT_MODE)).equals(PrinterConstants.MODE_NORMAL)) {
			setValueAt(PrinterConstants.REPLACE_CONTROL_TAGS, Boolean.TRUE);
			setValueAt(PrinterConstants.CODEPAGE, PrinterConstants.CP_WIN);
 			ps.printFormAndWait(s,getContext());
        }
        else if (((String)getValueAt(PrinterConstants.PRINT_MODE)).equals(PrinterConstants.MODE_RESTORE)) {
 			ps.printFormAndWait(s);
        }
	}
}
