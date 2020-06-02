package mataf.hl.panels;

import mataf.types.MatafButton;
import mataf.types.MatafComboBox;
import mataf.types.MatafEmbeddedPanel;
import mataf.types.textfields.MatafNumericField;


/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class PirteyCheshbon extends MatafEmbeddedPanel {

     private PirteyCheshbonController controller;
     private mataf.types.MatafLabel label = null;
     private mataf.types.MatafLabel label1 = null;
     private mataf.types.MatafLabel label2 = null;
     private mataf.types.MatafLabel label3 = null;
     private mataf.types.MatafLabel label4 = null;
     private mataf.types.MatafLabel label5 = null;
     private mataf.types.MatafComboBox comboBox = null;
     private MatafNumericField textField = null;
     private mataf.types.MatafComboBox comboBox1 = null;
     private MatafNumericField textField1 = null;
     private mataf.types.MatafLabel label6 = null;
     private mataf.types.MatafLabel label7 = null;
     private mataf.types.MatafComboBox comboBox2 = null;
     private mataf.types.MatafLabel label8 = null;
     private MatafNumericField textField2 = null;
     private mataf.types.MatafLabel label9 = null;
     private MatafButton button = null;
     private MatafButton button1 = null;
	/**
	 * Constructor for PirteyCheshbon.
	 */
	public PirteyCheshbon() {
		super();
		controller = new PirteyCheshbonController(this);
		initialize();
		this.setFocusCycleRoot(true);
		
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.add(getLabel(), null);
        this.add(getLabel1(), null);
        this.add(getLabel2(), null);
        this.add(getLabel3(), null);
        this.add(getLabel4(), null);
        this.add(getLabel5(), null);
        this.add(getComboBox(), null);
        this.add(getTextField(), null);
        this.add(getComboBox1(), null);
        this.add(getTextField1(), null);
        this.add(getLabel6(), null);
        this.add(getLabel7(), null);
        this.add(getComboBox2(), null);
        this.add(getLabel8(), null);
        this.add(getTextField2(), null);
        this.add(getLabel9(), null);
        this.add(getButton(), null);
        this.add(getButton1(), null);
        this.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
        this.setBorder(null);
        getComboBox().requestFocusInWindow();
        
        /*
        /// TEST COMBO-BOX.
		final SpComboBox combo = new SpComboBox();
		combo.setDataNameForList("comboList");
		combo.setUseKeyValues(false);
		combo.setDataName("selected");
		combo.setValueInContext("value");
		combo.setBounds(0,0,120,20);
		combo.addItemListener(new ItemListener() {			
			public void itemStateChanged(ItemEvent e) 
			{
				System.out.println("combo.getDataValue()=" + combo.getDataValue());
			}
		});
		add(combo);
		*/
			
	}
	/**
	 * This method initializes label
	 * 
	 * @return mataf.dse.gui.MatafLabel
	 */
	private mataf.types.MatafLabel getLabel() {
		if(label == null) {
			label = new mataf.types.MatafLabel();
			label.setBounds(944, 5, 60, 19);
			label.setText("מערכת");
			label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		}
		return label;
	}
	/**
	 * This method initializes label1
	 * 
	 * @return mataf.dse.gui.MatafLabel
	 */
	private mataf.types.MatafLabel getLabel1() {
		if(label1 == null) {
			label1 = new mataf.types.MatafLabel();
			label1.setBounds(944, 35, 60, 19);
			label1.setText("פרטי חשבון");
			label1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			label1.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
		}
		return label1;
	}
	/**
	 * This method initializes label2
	 * 
	 * @return mataf.dse.gui.MatafLabel
	 */
	private mataf.types.MatafLabel getLabel2() {
		if(label2 == null) {
			label2 = new mataf.types.MatafLabel();
			label2.setBounds(944, 65, 60, 19);
			label2.setText("ס.ח הלוואה");
			label2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		}
		return label2;
	}
	/**
	 * This method initializes label3
	 * 
	 * @return mataf.dse.gui.MatafLabel
	 */
	private mataf.types.MatafLabel getLabel3() {
		if(label3 == null) {
			label3 = new mataf.types.MatafLabel();
			label3.setBounds(575, 65, 70, 19);
			label3.setText("מספר הלוואה");
			label3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		}
		return label3;
	}
	/**
	 * This method initializes label4
	 * 
	 * @return mataf.dse.gui.MatafLabel
	 */
	private mataf.types.MatafLabel getLabel4() {
		if(label4 == null) {
			label4 = new mataf.types.MatafLabel();
			label4.setBounds(407, 65, 64, 19);
			label4.setText("תאריך מתן");
			label4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		}
		return label4;
	}
	/**
	 * This method initializes label5
	 * 
	 * @return mataf.dse.gui.MatafLabel
	 */
	public mataf.types.MatafLabel getLabel5() {
		if(label5 == null) {
			label5 = new mataf.types.MatafLabel();
			label5.setBounds(665, 5, 180, 19);
			label5.setText("מערכת - שדה משוב");
			label5.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
			label5.setDataName("chosenMarechetText");
		}
		return label5;
	}
	/**
	 * This method initializes comboBox
	 * 
	 * @return mataf.types.MatafComboBox
	 */
	public mataf.types.MatafComboBox getComboBox() {
		if(comboBox == null) {
			comboBox = new MatafComboBox();
			comboBox.setBounds(865, 5, 55, 19);
			comboBox.setDataNameForList("marechetList");			
			comboBox.setDataName("chosenMarechetCode");
			comboBox.addItemListener(controller);
			comboBox.setName("MaarechectComboBox");
		}
		return comboBox;
	}
	/**
	 * This method initializes textField
	 * 
	 * @return mataf.dse.gui.MatafTextField
	 */
	public MatafNumericField getTextField() {
		if(textField == null) {
			textField = new MatafNumericField();
			textField.setBounds(575, 35, 70, 19);
			textField.setDataName("cheshbon");
		}
		return textField;
	}
	/**
	 * This method initializes comboBox1
	 * 
	 * @return mataf.types.MatafComboBox
	 */
	public mataf.types.MatafComboBox getComboBox1() {
		if(comboBox1 == null) {
			comboBox1 = new MatafComboBox();
			comboBox1.setBounds(765, 35, 80, 19);
			comboBox1.setDataNameForList("snifList");
			comboBox1.setDataName("chosenSnifCode");			
			comboBox1.setName("SnifComboBox");
			comboBox1.addItemListener(controller);
		}
		return comboBox1;
	}
	/**
	 * This method initializes textField1
	 * 
	 * @return mataf.dse.gui.MatafTextField
	 */
	public MatafNumericField getTextField1() {
		if(textField1 == null) {
			textField1 = new MatafNumericField();
			textField1.setBounds(865, 35, 55, 19);
			textField1.setDataName("bankKupa");
			textField1.addActionListener(controller);
		}
		return textField1;
	}
	/**
	 * This method initializes label6
	 * 
	 * @return mataf.dse.gui.MatafLabel
	 */
	private mataf.types.MatafLabel getLabel6() {
		if(label6 == null) {
			label6 = new mataf.types.MatafLabel();
			label6.setBounds(375, 36, 180, 19);
			label6.setText("חשבון שדה משוב");
			label6.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
			label6.setDataName("chosenSugCheshbonHlText");
		}
		return label6;
	}
	/**
	 * This method initializes label7
	 * 
	 * @return mataf.dse.gui.MatafLabel
	 */
	public mataf.types.MatafLabel getLabel7() {
		if(label7 == null) {
			label7 = new mataf.types.MatafLabel();
			label7.setBounds(665, 35, 80, 19);
			label7.setText("סניף - שדה משוב");
			label7.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
			label7.setDataName("chosenSnifText");
		}
		return label7;
	}
	/**
	 * This method initializes comboBox2
	 * 
	 * @return mataf.types.MatafComboBox
	 */
	public mataf.types.MatafComboBox getComboBox2() {
		if(comboBox2 == null) {
			comboBox2 = new MatafComboBox();
			comboBox2.setBounds(865, 65, 55, 19);
			comboBox2.setDataNameForList("sugCheshbonHlList");
			comboBox2.setDataName("chosenSugCheshbonHlCode");			
			comboBox2.setName("SugCheshbonHlComboBox");
			comboBox2.addItemListener(controller);
		}
		return comboBox2;
	}
	/**
	 * This method initializes label8
	 * 
	 * @return mataf.dse.gui.MatafLabel
	 */
	public mataf.types.MatafLabel getLabel8() {
		if(label8 == null) {
			label8 = new mataf.types.MatafLabel();
			label8.setBounds(665, 65, 180, 19);
			label8.setText("סוג חשבון שדה משוב");
			label8.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
			label8.setDataName("chosenSugCheshbonHlText");
		}
		return label8;
	}
	/**
	 * This method initializes textField2
	 * 
	 * @return mataf.dse.gui.MatafTextField
	 */
	public MatafNumericField getTextField2() {
		if(textField2 == null) {
			textField2 = new MatafNumericField();
			textField2.setBounds(486, 65, 69, 19);
			textField2.setDataName("misparHl");
		}
		return textField2;
	}
	/**
	 * This method initializes label9
	 * 
	 * @return mataf.dse.gui.MatafLabel
	 */
	private mataf.types.MatafLabel getLabel9() {
		if(label9 == null) {
			label9 = new mataf.types.MatafLabel();
			label9.setBounds(304, 65, 90, 19);
			label9.setText("תאריך מתן");
			label9.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
			label9.setDataName("taarichMatan");
			label9.setFormatter(new com.ibm.dse.gui.DateConverter("","dd/MM/yyyy",true,"/",true,0));
		}
		return label9;
	}
	
	/**
	 * This method initializes button
	 * 
	 * @return com.mataf.dse.gui.MatafButton
	 */
	private MatafButton getButton() {
		if(button == null) {
			button = new MatafButton();			
			button.setBounds(115, 440, 100, 20);
			button.setText("ודא נתונים");
			button.setEnabled(true);
			button.setType("Execute_Operation");
			button.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("processor",0,"confirmOpenHlClientOp",0,"","","","","","",0,0,0,0,false,false));
		}
		return button;
	}
	/**
	 * This method initializes button1
	 * 
	 * @return com.mataf.dse.gui.MatafButton
	 */
	private MatafButton getButton1() {
		if(button1 == null) {
			button1 = new MatafButton();			
			button1.setBounds(5, 440, 100, 20);
			button1.setText("צא >>");
			button1.setType("Close");
		}
		return button1;
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="0,0"
