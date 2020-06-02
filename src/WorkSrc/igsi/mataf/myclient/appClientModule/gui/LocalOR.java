package gui;

import javax.swing.JFrame;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class LocalOR extends JFrame {

     private javax.swing.JPanel jContentPane = null;
	/**
	 * This method initializes 
	 * 
	 */
	public LocalOR() {
		super();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.setContentPane(getJContentPane());
			
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			java.awt.BorderLayout layBorderLayout1 = new java.awt.BorderLayout();
			jContentPane.setLayout(layBorderLayout1);
		}
		return jContentPane;
	}
}
