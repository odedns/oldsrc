package com.mataf.hl.views;

import java.util.Vector;

import com.mataf.focusmanager.AbstractFocusTraversalPolicy;

/**
 * @author administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class OpenHlfocusTraversal extends AbstractFocusTraversalPolicy {
	
	public OpenHlfocusTraversal(OpenHlView rootPanel) {
		super();
		buildFocusOrderList(rootPanel);
	}
	
	private void buildFocusOrderList(OpenHlView rootPanel)
	{
		// Construct a linear path of focusable components.
		Vector l = new Vector();
//		l.add(rootPanel.getComboBox());
//		l.add(rootPanel.getTextField24());
//		l.add(rootPanel.getComboBox1());
//		l.add(rootPanel.getTextField());
//		l.add(rootPanel.getComboBox6());
//		l.add(rootPanel.getTextField2());
		
//		l.add(rootPanel.getTextField4());
		setFocusOrderList(l);
	}

}
