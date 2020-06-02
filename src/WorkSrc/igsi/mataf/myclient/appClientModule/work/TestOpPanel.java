package work;
import com.ibm.dse.gui.*;

/**
 * Insert the type's description here.
 */
public class TestOpPanel extends javax.swing.JPanel {
	private javax.swing.JButton ivjSpButton1 = null;
	private javax.swing.JButton ivjSpButton2 = null;
     private javax.swing.JLabel jLabel = null;
/**
 * TestOpPanel constructor comment.
 */
public TestOpPanel() {
	super();
	initialize();
}
/**
 * TestOpPanel constructor comment.
 * @param layout java.awt.LayoutManager
 */
/*
public TestOpPanel(java.awt.LayoutManager layout) {
	super(layout);
}
*/
/**
 * TestOpPanel constructor comment.
 * @param layout java.awt.LayoutManager
 * @param isDoubleBuffered boolean
 */
public TestOpPanel(java.awt.LayoutManager layout, boolean isDoubleBuffered) {
	super(layout, isDoubleBuffered);
}
/**
 * TestOpPanel constructor comment.
 * @param isDoubleBuffered boolean
 */
public TestOpPanel(boolean isDoubleBuffered) {
	super(isDoubleBuffered);
}
/**
 * Return the SpButton1 property value.
 * @return javax.swing.JButton
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JButton getSpButton1() {
	if (ivjSpButton1 == null) {
		try {
			ivjSpButton1 = new javax.swing.JButton();
			ivjSpButton1.setName("SpButton1");
//			ivjSpButton1.setBackground(204,204,204);
			ivjSpButton1.setBounds(80, 193, 104, 30);
			ivjSpButton1.setText("SpButton1");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjSpButton1;
}
/**
 * Return the SpButton2 property value.
 * @return javax.swing.JButton
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JButton getSpButton2() {
	if (ivjSpButton2 == null) {
		try {
			ivjSpButton2 = new javax.swing.JButton();
			ivjSpButton2.setName("SpButton2");
	//		ivjSpButton2.setBackground(204,204,204);
			ivjSpButton2.setBounds(226, 193, 109, 30);
			ivjSpButton2.setText("SpButton2");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjSpButton2;
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
		setName("TestOpPanel");
//		setBackground(204,204,204);
		setBounds(0,0,383,262);
		add(getSpButton1(), getSpButton1().getName());
		add(getSpButton2(), getSpButton2().getName());
		this.add(getJLabel(), null);
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
		TestOpPanel aTestOpPanel;
		aTestOpPanel = new TestOpPanel();
		frame.setContentPane(aTestOpPanel);
		frame.setSize(aTestOpPanel.getSize());
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});
		frame.setVisible(true);
	} catch (Throwable exception) {
		System.err.println("Exception occurred in main() of javax.swing.JPanel");
		exception.printStackTrace(System.out);
	}
}
	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		if(jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setBounds(71, 99, 255, 58);
			jLabel.setText("My Fucking text");
		}
		return jLabel;
	}
}
