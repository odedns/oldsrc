package work;

import java.awt.event.*;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class Vtest extends Frame {

     private java.awt.Label label = null;
     private java.awt.Button button = null;
	/**
	 * Constructor for Vtest.
	 */
	public Vtest() {
		super();
		initialize();
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		} );
	}

	/**
	 * Constructor for Vtest.
	 * @param arg0
	 */
	public Vtest(GraphicsConfiguration arg0) {
		super(arg0);
	}

	/**
	 * Constructor for Vtest.
	 * @param arg0
	 */
	public Vtest(String arg0) {
		super(arg0);
	}

	/**
	 * Constructor for Vtest.
	 * @param arg0
	 * @param arg1
	 */
	public Vtest(String arg0, GraphicsConfiguration arg1) {
		super(arg0, arg1);
	}


	
	public static void main(String[] args) {
		Vtest vtest = new Vtest();
		vtest.show();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
        this.setLayout(layFlowLayout1);
        this.add(getLabel(), null);
        this.add(getButton(), null);
        this.setSize(356, 170);
        this.setTitle("Vtest");
			
	}
	/**
	 * This method initializes label
	 * 
	 * @return java.awt.Label
	 */
	private java.awt.Label getLabel() {
		if(label == null) {
			label = new java.awt.Label();
			label.setText("Some text");
		}
		return label;
	}
	/**
	 * This method initializes button
	 * 
	 * @return java.awt.Button
	 */
	private java.awt.Button getButton() {
		if(button == null) {
			button = new java.awt.Button();
			button.setName("ok");
			button.setLabel("OK");
		}
		return button;
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="0,0"
