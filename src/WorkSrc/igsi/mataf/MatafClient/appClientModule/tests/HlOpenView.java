package tests;
import com.ibm.dse.gui.DSEPanel;
/**
 * Insert the type's description here.
 */
public class HlOpenView extends DSEPanel {
	private com.ibm.dse.gui.SpButton ivjok = null;
/**
 * HlOpenView constructor comment.
 */
public HlOpenView() {
	super();
	initialize();
}
/**
 * HlOpenView constructor comment.
 * @param layout java.awt.LayoutManager
 */
public HlOpenView(java.awt.LayoutManager layout) {
	super(layout);
}
/**
 * HlOpenView constructor comment.
 * @param layout java.awt.LayoutManager
 * @param isDoubleBuffered boolean
 */
public HlOpenView(java.awt.LayoutManager layout, boolean isDoubleBuffered) {
	super(layout, isDoubleBuffered);
}
/**
 * HlOpenView constructor comment.
 * @param isDoubleBuffered boolean
 */
public HlOpenView(boolean isDoubleBuffered) {
	super(isDoubleBuffered);
}
/**
 * Return the ok property value.
 * @return com.ibm.dse.gui.SpButton
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private com.ibm.dse.gui.SpButton getok() {
	if (ivjok == null) {
		try {
			ivjok = new com.ibm.dse.gui.SpButton();
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjok;
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
		setBounds(0,0,800,600);
		add(getok(), getok().getName());
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
		HlOpenView aHlOpenView;
		aHlOpenView = new HlOpenView();
		frame.setContentPane(aHlOpenView);
		frame.setSize(aHlOpenView.getSize());
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});
		frame.setVisible(true);
	} catch (Throwable exception) {
		System.err.println("Exception occurred in main() of com.mataf.ui.MatafPanel");
		exception.printStackTrace(System.out);
	}
}
}
