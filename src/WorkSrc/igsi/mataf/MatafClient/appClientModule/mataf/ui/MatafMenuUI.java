package mataf.ui;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;

import javax.swing.MenuSelectionManager;
import javax.swing.plaf.basic.BasicMenuItemUI;

/**
 * @author asyr
 * @revised by Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MatafMenuUI extends BasicMenuItemUI {

	/**
	 * Constructor for MatafMenuItemUI.
	 */
	public MatafMenuUI() 
	{
		super();
	}

	protected void doClick(MenuSelectionManager msm) {
		AWTEvent event = Toolkit.getDefaultToolkit().getSystemEventQueue().getCurrentEvent();
		
		if (event instanceof MouseEvent) {
			MouseEvent me = (MouseEvent)event;
			if (me.getButton() == MouseEvent.BUTTON3)
				return;
		}
		
		super.doClick(msm);
	}
	
	/**
	 * Draws the button's text.
	 * Method sends a different mnemonicIndex to support Right-To-Left
	 * Component Orientation.
	 */
	/**
	 * @see javax.swing.plaf.basic.BasicMenuItemUI#paintText(Graphics, JMenuItem, Rectangle, String)
	 */
	
}
