import com.ibm.dse.gui.*;
/**
 * Insert the type's description here.
 */
public class MyPanel extends com.ibm.dse.gui.DSEPanel {
	private javax.swing.JLabel ivjmylabel = null;
	private javax.swing.JButton ivjokButton = null;
/**
 * MyPanel constructor comment.
 */
public MyPanel() {
	super();
	initialize();
}
/**
 * MyPanel constructor comment.
 * @param layout java.awt.LayoutManager
 */
public MyPanel(java.awt.LayoutManager layout) {
	super(layout);
}
/**
 * MyPanel constructor comment.
 * @param layout java.awt.LayoutManager
 * @param isDoubleBuffered boolean
 */
public MyPanel(java.awt.LayoutManager layout, boolean isDoubleBuffered) {
	super(layout, isDoubleBuffered);
}
/**
 * MyPanel constructor comment.
 * @param isDoubleBuffered boolean
 */
public MyPanel(boolean isDoubleBuffered) {
	super(isDoubleBuffered);
}
/**
 * Return the mylabel property value.
 * @return javax.swing.JLabel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JLabel getmylabel() {
	if (ivjmylabel == null) {
		try {
			ivjmylabel = new javax.swing.JLabel();
			ivjmylabel.setBounds(300, 200, 100, 100);
			ivjmylabel.setName("myLabel");
			ivjmylabel.setText("Some Text");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjmylabel;
}
/**
 * Return the okButton property value.
 * @return javax.swing.JButton
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JButton getokButton() {
	if (ivjokButton == null) {
		try {
			ivjokButton = new javax.swing.JButton();
			ivjokButton.setBounds(200, 200, 200, 200);
			ivjokButton.setName("okButton");
			ivjokButton.setVisible(true);
			ivjokButton.setText("OK");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjokButton;
}
/**
 * Called whenever the part throws an exception.
 * @param exception java.lang.Throwable
 */
private void handleException(java.lang.Throwable exception) {

	/* Uncomment the following lines to print uncaught exceptions to stdout */
	// System.out.println("--------- UNCAUGHT EXCEPTION ---------");
	// exception.printStackTrace(System.out);
}
/**
 * Initialize the class.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initialize() {
	try {
		// user code begin {1}
		// user code end
		setLayout(null);
		add(getmylabel(), getmylabel().getName());
		add(getokButton(), getokButton().getName());
		this.setSize(302, 173);
		this.setBackground(java.awt.Color.white);
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	}
	// user code begin {2}
	// user code end
}
/**
 * main entrypoint - starts the part when it is run as an application
 * @param args java.lang.String[]
 */
public static void main(java.lang.String[] args) {
	try {
		javax.swing.JFrame frame = new javax.swing.JFrame();
		MyPanel aMyPanel;
		aMyPanel = new MyPanel();
		frame.setContentPane(aMyPanel);
		frame.setSize(aMyPanel.getSize());
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});
		frame.setVisible(true);
	} catch (Throwable exception) {
		System.err.println("Exception occurred in main() of com.ibm.dse.gui.DSEPanel");
		exception.printStackTrace(System.out);
	}
}
}  //  @jve:visual-info  decl-index=0 visual-constraint="0,0"
