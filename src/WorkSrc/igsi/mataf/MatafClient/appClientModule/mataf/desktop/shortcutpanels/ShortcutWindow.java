/*
 * Created on 23/05/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mataf.desktop.shortcutpanels;

import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JWindow;

/**
 * @author eyalbz
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ShortcutWindow extends JFrame implements KeyListener {

	/**
	 * 
	 */
	public ShortcutWindow() {
		super();
		initUI();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		System.out.println(e);

	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
		System.out.println(e);

	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {
		System.out.println(e);

	}

	/**
	 * 
	 */
	private void initUI() {

		ShortcutPanel panel = new ShortcutPanel();
		getContentPane().add(panel);
		setSize(panel.getSize());
		addKeyListener(this);
	}

	public static void main(String[] args) {

		ShortcutWindow frame = new ShortcutWindow();
		
		frame.validate();
		frame.show();
	}

}
