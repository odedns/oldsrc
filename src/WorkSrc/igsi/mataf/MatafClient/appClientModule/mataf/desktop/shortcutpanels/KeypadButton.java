/*
 * Created on 23/05/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mataf.desktop.shortcutpanels;

import java.awt.Dimension;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 * @author eyalbz
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class KeypadButton extends JButton {

	/**
	 * 
	 */
	public KeypadButton() {
		super();
		initUI();

	}

	/**
	 * @param text
	 */
	public KeypadButton(String text) {
		super(text);
		initUI();
	}

	/**
	 * @param a
	 */
	public KeypadButton(Action a) {
		super(a);
		initUI();
	}

	/**
	 * @param icon
	 */
	public KeypadButton(Icon icon) {
		super(icon);
		initUI();
	}

	/**
	 * @param text
	 * @param icon
	 */
	public KeypadButton(String text, Icon icon) {
		super(text, icon);
		initUI();
	}

	/**
	 * 
	 */
	private void initUI() {
		setSize(15, 15);

	}

}
