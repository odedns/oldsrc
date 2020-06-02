package mataf.ui;

import java.awt.AWTEvent;
import java.awt.ComponentOrientation;
import java.awt.EventQueue;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.MenuSelectionManager;
import javax.swing.plaf.basic.BasicGraphicsUtils;
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
public class MatafMenuItemUI extends BasicMenuItemUI {

	/**
	 * Constructor for MatafMenuItemUI.
	 */
	
	//private ButtonModel 	model;
	//private AbstractButton button;
	
	public MatafMenuItemUI() 
	{
		super();
	}
	
	public MatafMenuItemUI(ButtonModel model,AbstractButton button)
	{
	//	this.model = model;
	//	this.button = button;
	}

	protected void doClick(MenuSelectionManager msm) {
		AWTEvent event = EventQueue.getCurrentEvent();
		
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
	protected void paintText(Graphics g, AbstractButton b, Rectangle textRect, String text)
	{
		ButtonModel model = b.getModel();
   	    FontMetrics fm = g.getFontMetrics();
// 	    g.setFont(button.getFont());
   	
   	    // Consider component orientaion.
   	    int mnemonicIndex = b.getComponentOrientation()==ComponentOrientation.RIGHT_TO_LEFT ?
		   	    (text.length()-1) - b.getDisplayedMnemonicIndex() :
		   	    b.getDisplayedMnemonicIndex();
		   	    			
		// Draw the Text
		if(model.isEnabled()) {
		    // Paint the text normally.
		    g.setColor(b.getForeground());
		    BasicGraphicsUtils.drawStringUnderlineCharAt(g,text, mnemonicIndex,
						  textRect.x ,
						  textRect.y + fm.getAscent());
		}
		else 
		{
		    // Paint the text disabled.
		    BasicGraphicsUtils.drawStringUnderlineCharAt(g,text, mnemonicIndex,
						  textRect.x, textRect.y + fm.getAscent());
		}
	}
}
