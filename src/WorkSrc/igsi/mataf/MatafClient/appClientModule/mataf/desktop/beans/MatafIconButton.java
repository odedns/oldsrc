/*
 * Created on 15/03/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mataf.desktop.beans;

import java.awt.event.ActionEvent;

import mataf.logger.GLogger;

import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.desktop.IconButton;

/**
 * @author eyalbz
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MatafIconButton extends IconButton {
	private boolean visualAction;
	public void actionPerformed(ActionEvent e) {
		try {
			if (!visualAction) {
				DSEClientOperation clientOp = (DSEClientOperation) DSEClientOperation.readObject(getOperation());
				clientOp.execute();
			} else // We're activating a regular operation.
				{
				super.actionPerformed(e);
			}
		} catch (Exception ex) {
			GLogger.error(this.getClass() ,null,"Error in actionPerformed of MatafIconButton",ex,false);
		}
	}

	/**
	 * @return
	 */
	public boolean isVisualAction() {
		return visualAction;
	}

	/**
	 * @param b
	 */
	public void setVisualAction(boolean b) {
		visualAction = b;
	}

}
