package gui;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.font.TextLayout;

import javax.swing.JFrame;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class TestLayout extends JFrame {

     private javax.swing.JPanel jContentPane = null;
     private javax.swing.JTextArea jTextArea = null;
     private javax.swing.JButton jButton = null;
	/**
	 * Constructor for TestLayout.
	 * @throws HeadlessException
	 */
	public TestLayout() throws HeadlessException {
		super();
		initialize();
	}

	/**
	 * Constructor for TestLayout.
	 * @param gc
	 */
	public TestLayout(GraphicsConfiguration gc) {
		super(gc);
	}

	/**
	 * Constructor for TestLayout.
	 * @param title
	 * @throws HeadlessException
	 */
	public TestLayout(String title) throws HeadlessException {
		super(title);
	}

	/**
	 * Constructor for TestLayout.
	 * @param title
	 * @param gc
	 */
	public TestLayout(String title, GraphicsConfiguration gc) {
		super(title, gc);
	}

	public static void main(String[] args) {
		
		TestLayout t = new TestLayout();
		t.show();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.setContentPane(getJContentPane());
        this.setSize(445, 149);
			
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			javax.swing.BoxLayout layBoxLayout11 = new javax.swing.BoxLayout(jContentPane,  javax.swing.BoxLayout.Y_AXIS);
			jContentPane.setLayout(layBoxLayout11);
			jContentPane.add(getJTextArea(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.setLayout(layBoxLayout11);
		}
		return jContentPane;
	}
	/**
	 * This method initializes jTextArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private javax.swing.JTextArea getJTextArea() {
		if(jTextArea == null) {
			jTextArea = new javax.swing.JTextArea();
			jTextArea.setText("TextArea");
		}
		return jTextArea;
	}
	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton() {
		if(jButton == null) {
			jButton = new javax.swing.JButton();
			jButton.setText("OK");
		}
		return jButton;
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="0,0"
