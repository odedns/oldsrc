package com.mataf.hl.views;

import java.awt.Event;

import com.ibm.dse.base.OperationRepliedEvent;
import com.ibm.dse.gui.DSECoordinationEvent;

import mataf.desktop.beans.MatafPanel;
import mataf.dse.gui.MatafButton;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class EmptyView extends MatafPanel {
	private MatafButton closeButton;
	
	public EmptyView() {
		initialize();		
	}
	
	private void initialize() {
//		this.setSize(0,0);
		this.setContextName("emptyCtx");
		this.add(getCloseButton());
		this.setSize(120, 67);
		this.setName("EmptyPanel");
		this.setPreferredSize(new java.awt.Dimension(10,10));
	}
	
	private MatafButton getCloseButton() {
		if (closeButton==null) {
			closeButton = new MatafButton();
			closeButton.setSize(16, 19);
			closeButton.setSize(5, 5);
			closeButton.setType("Close");
			closeButton.setPreferredSize(new java.awt.Dimension(5,5));
		}
		return closeButton;
	}	
}  //  @jve:visual-info  decl-index=0 visual-constraint="0,0"
