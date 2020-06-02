/*
 * Created on 29/03/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mataf.operations;

import java.io.IOException;

import mataf.services.printer.PrinterConstants;
import mataf.services.printer.PrinterService;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.DSEObjectNotFoundException;

/**
 * @author eyalbz
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CheckConnectionToPrinter extends DSEClientOperation {
	private final String PRINTER_FOUND = "0";
	private final String PRINTER_NOT_FOUND = "1";
	public CheckConnectionToPrinter() {
		super();
	}

	public CheckConnectionToPrinter(String anOperationName) throws IOException {
		super(anOperationName);
	}

	public CheckConnectionToPrinter(String anOperationName, Context aParentContext) throws IOException, DSEInvalidRequestException {
		super(anOperationName, aParentContext);
	}

	public CheckConnectionToPrinter(String anOperationName, String aParentContext)
		throws IOException, DSEInvalidRequestException, DSEObjectNotFoundException {
		super(anOperationName, aParentContext);
	}

	public void execute() throws Exception {
		PrinterService printerService = (PrinterService) getService("printerService");
		int printerStatus = printerService.getStatus();
		if ((printerStatus == PrinterConstants.IDLE) |
			(printerStatus == PrinterConstants.PROCESSING) |
			(printerStatus == PrinterConstants.STOPPED) |
			(printerStatus == PrinterConstants.UNKNOWN)) {
			setValueAt("returnCode", PRINTER_FOUND);
		} else 
			setValueAt("returnCode", PRINTER_NOT_FOUND);
	}
}
