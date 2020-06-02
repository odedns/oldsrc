/*
 * Created on 15/03/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mataf.operations;

import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;

import mataf.desktop.beans.MatafIconButton;
import mataf.services.printer.PrinterService;
import mataf.types.MatafTitle;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.desktop.Desktop;

/**
 * @author eyalbz
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SwitchPrinterOp extends DSEClientOperation {
	private ImageIcon localPrinterIcon;
	private ImageIcon branchPrinterIcon;
	public void execute() throws Exception {
		switchPrinter();
	}
	public SwitchPrinterOp() {
		super();
		initPrinterButtonIcons();
	}

	public SwitchPrinterOp(String anOperationName) throws IOException {
		super(anOperationName);
		initPrinterButtonIcons();
	}

	public SwitchPrinterOp(String anOperationName, Context aParentContext) throws IOException, DSEInvalidRequestException {
		super(anOperationName, aParentContext);
		initPrinterButtonIcons();
	}

	public SwitchPrinterOp(String anOperationName, String aParentContext)
		throws IOException, DSEInvalidRequestException, DSEObjectNotFoundException {
		super(anOperationName, aParentContext);
		initPrinterButtonIcons();
	}

	private void initPrinterButtonIcons() {
		String localPrinterIconPath = "images/Desktop/icon_print.gif";
		String branchPrinterIconPath = "images/Desktop/icon_print_other.gif";

		URL iconURL = ClassLoader.getSystemResource(localPrinterIconPath);
		localPrinterIcon = new ImageIcon(iconURL);

		iconURL = ClassLoader.getSystemResource(branchPrinterIconPath);
		branchPrinterIcon = new ImageIcon(iconURL);
	}
	private void switchPrinter() throws Exception {

		PrinterService printerService = (PrinterService) getService("printerService");
		String printerName = printerService.getServerName();
		MatafIconButton printerSwitchButton = null;
		Object object = Desktop.getDesktop().getComponentByName("printerSwitchButton");
		if (object == null) {
			throw new Exception("Can not get printer switch button from the Desktop");
		}
		try {
			printerSwitchButton = (MatafIconButton) object;
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (printerName.equalsIgnoreCase("default")) {
			printerService.setServerName("secondary");
			printerSwitchButton.setToolTipText((Object) "Branch_Printer_Button_ToolTip");
			printerSwitchButton.setIcon(branchPrinterIcon);
		} else if (printerName.equalsIgnoreCase("secondary")) {
			printerService.setServerName("default");
			printerSwitchButton.setToolTipText((Object) "Local_Printer_Button_ToolTip");
			printerSwitchButton.setIcon(localPrinterIcon);

		}

	}

}
