package tests.nati;

import java.awt.LayoutManager;

import javax.swing.JPanel;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (15/03/2004 16:25:07).  
 */
public class TestLNF extends JPanel
{

	private mataf.types.textfields.MatafNumericField matafNumericField = null;
	private mataf.types.textfields.MatafNumericField matafNumericField1 = null;
	private mataf.types.textfields.MatafNumericField matafNumericField2 = null;
	private mataf.types.MatafLabel matafLabel = null;
	private mataf.types.MatafLabel matafLabel1 = null;
	private javax.swing.JTextField jTextField = null;
	private javax.swing.JTextField jTextField1 = null;
	private javax.swing.JTextField jTextField2 = null;
	private mataf.types.MatafLabel matafLabel2 = null;
	private mataf.types.MatafLabel matafLabel3 = null;
	private mataf.types.MatafDecimalLabel matafLabel4 = null; 
	/**
	 * 
	 */
	public TestLNF()
	{
		super();
			initialize();
	// TODO Auto-generated constructor stub
	}

	/**
	 * @param isDoubleBuffered
	 */
	public TestLNF(boolean isDoubleBuffered)
	{
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param layout
	 */
	public TestLNF(LayoutManager layout)
	{
		super(layout);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param layout
	 * @param isDoubleBuffered
	 */
	public TestLNF(LayoutManager layout, boolean isDoubleBuffered)
	{
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.setLayout(null);
        this.add(getMatafNumericField1(), null);
        this.add(getMatafNumericField(), null);
        this.add(getMatafLabel(), null);
        this.add(getMatafLabel1(), null);
        this.add(getJTextField(), null);
        this.add(getJTextField1(), null);
        this.add(getMatafNumericField2(), null);
        this.add(getMatafLabel2(), null);
        this.add(getJTextField2(), null);
        this.add(getMatafLabel3(), null);
        this.add(getMatafFormatedLabel(),null);
        this.setSize(442, 138);
			
	}
	/**
	 * This method initializes matafNumericField
	 * 
	 * @return mataf.types.textfields.MatafNumericField
	 */
	private mataf.types.textfields.MatafNumericField getMatafNumericField() {
		if(matafNumericField == null) {
			matafNumericField = new mataf.types.textfields.MatafNumericField();
			matafNumericField.setBounds(151, 29, 67, 19);
			matafNumericField.setEnabled(false);
		}
		return matafNumericField;
	}
	/**
	 * This method initializes matafNumericField1
	 * 
	 * @return mataf.types.textfields.MatafNumericField
	 */
	private mataf.types.textfields.MatafNumericField getMatafNumericField1() {
		if(matafNumericField1 == null) {
			matafNumericField1 = new mataf.types.textfields.MatafNumericField();
			matafNumericField1.setBounds(153, 5, 65, 19);
		}
		return matafNumericField1;
	}
	
	private mataf.types.MatafDecimalLabel getMatafFormatedLabel()
	{
		if(matafLabel4 == null) {
					matafLabel4 = new mataf.types.MatafDecimalLabel();
					matafLabel4.setBounds(298, 11, 142, 19);
					matafLabel4.setText("99");
				}
		return  matafLabel4;		
	}
	
	/**
	 * This method initializes matafLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel() {
		if(matafLabel == null) {
			matafLabel = new mataf.types.MatafLabel();
			matafLabel.setBounds(77, 5, 65, 19);
			matafLabel.setText("Enabled :");
		}
		return matafLabel;
	}
	/**
	 * This method initializes matafLabel1
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel1() {
		if(matafLabel1 == null) {
			matafLabel1 = new mataf.types.MatafLabel();
			matafLabel1.setBounds(77, 29, 67, 19);
			matafLabel1.setText("Disabled :");
		}
		return matafLabel1;
	}
	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField() {
		if(jTextField == null) {
			jTextField = new javax.swing.JTextField();
			jTextField.setBounds(232, 5, 65, 21);
		}
		return jTextField;
	}
	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField1() {
		if(jTextField1 == null) {
			jTextField1 = new javax.swing.JTextField();
			jTextField1.setBounds(232, 29, 65, 19);
			jTextField1.setEnabled(false);
		}
		return jTextField1;
	}
	/**
	 * This method initializes matafNumericField2
	 * 
	 * @return mataf.types.textfields.MatafNumericField
	 */
	private mataf.types.textfields.MatafNumericField getMatafNumericField2() {
		if(matafNumericField2 == null) {
			matafNumericField2 = new mataf.types.textfields.MatafNumericField();
			matafNumericField2.setBounds(151, 63, 67, 19);
			matafNumericField2.setEditable(false);
		}
		return matafNumericField2;
	}
	/**
	 * This method initializes matafLabel2
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel2() {
		if(matafLabel2 == null) {
			matafLabel2 = new mataf.types.MatafLabel();
			matafLabel2.setBounds(57, 63, 87, 19);
			matafLabel2.setText("Non-Editable :");
		}
		return matafLabel2;
	}
	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField2() {
		if(jTextField2 == null) {
			jTextField2 = new javax.swing.JTextField();
			jTextField2.setBounds(232, 63, 65, 21);
			jTextField2.setEditable(false);
		}
		return jTextField2;
	}
	/**
	 * This method initializes matafLabel3
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel3() {
		if(matafLabel3 == null) {
			matafLabel3 = new mataf.types.MatafLabel();
			matafLabel3.setBounds(144, 99, 157, 19);
			matafLabel3.setText("Java Version : "+System.getProperty("java.version"));
			matafLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		}
		return matafLabel3;
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="10,11"
