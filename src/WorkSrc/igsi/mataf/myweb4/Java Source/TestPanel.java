import javax.swing.JPanel;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class TestPanel extends JPanel {

     private javax.swing.JLabel jLabel = null;
     private javax.swing.JButton jButton = null;
	/**
	 * This method initializes 
	 * 
	 */
	public TestPanel() {
		super();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.add(getJButton(), null);
        this.add(getJLabel(), null);
        this.setSize(413, 151);
			
	}
	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		if(jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setText("Test Panel");
		}
		return jLabel;
	}
	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton() {
		if(jButton == null) {
			jButton = new javax.swing.JButton();
			jButton.setText("Press Me");
		}
		return jButton;
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="-2,18"
