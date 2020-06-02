/*
 * Created on 23/05/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mataf.desktop.shortcutpanels;

import javax.swing.JSplitPane;
;

/**
 * @author eyalbz
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ShortcutPanel extends JSplitPane {

	public ShortcutPanel() {
		setSize(400,200);
		setOneTouchExpandable(true);
		
		setRightComponent(new KeypadPanel());
	}
}
