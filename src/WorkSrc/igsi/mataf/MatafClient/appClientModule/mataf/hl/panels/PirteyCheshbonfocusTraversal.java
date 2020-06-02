package mataf.hl.panels;

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
public class PirteyCheshbonfocusTraversal extends AbstractFocusTraversalPolicy {
	
	public PirteyCheshbonfocusTraversal(PirteyCheshbon rootPanel) {
		super();
		buildFocusOrderList(rootPanel);
	}
	
	private void buildFocusOrderList(PirteyCheshbon rootPanel)
	{
		// Construct a linear path of focusable components.
		Vector l = new Vector();
		l.add(rootPanel.getComboBox());
		l.add(rootPanel.getTextField1());
		l.add(rootPanel.getComboBox1());
		l.add(rootPanel.getTextField());
		l.add(rootPanel.getComboBox2());
		l.add(rootPanel.getTextField2());
//		setDefaultComponent(rootPanel.getComboBox());		
		setFocusOrderList(l);
		
	}

}
