package mataf.override;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import mataf.types.MatafButton;
import mataf.types.MatafEmbeddedPanel;
import mataf.types.MatafLabel;
import mataf.types.table.MatafTable;
import mataf.types.textfields.MatafStringField;

/**
 * @author o000131
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class OverrideResponcePanel extends MatafEmbeddedPanel {

     //private com.ibm.dse.gui.SpLabel aSpLabel = null;
     private MatafStringField aSpTextField = null;
     private MatafButton aSpButton = null;
     private MatafButton aSpButton1 = null;
     private MatafLabel aSpLabel2 = null;
     private mataf.types.MatafLabel matafLabel = null;
     private mataf.types.MatafLabel matafLabel2 = null;
     private mataf.types.MatafLabel matafLabel3 = null;
     private mataf.types.MatafLabel matafLabel4 = null;
     private mataf.types.MatafButton matafButton = null;
	private mataf.types.MatafButton matafButton1 = null;
	private mataf.types.MatafTableComboBoxButton matafTableComboBoxButton = null;
	private mataf.types.MatafLabel matafLabel1 = null;
	private mataf.types.MatafComboTextField matafComboTextField = null;
	private mataf.types.MatafLabel matafLabel5 = null;
	/**
	 * This method initializes 
	 * 
	 */
	public OverrideResponcePanel() {
		super();
		initialize();
	}
	public static void main(String[] args) {
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
//        this.add(getASpLabel(), null);
        this.add(getASpTextField(), null);
        this.add(getASpButton(), null);
        this.add(getASpButton1(), null);
        this.add(getASpLabel2(), null);
        this.add(getMatafLabel(), null);
        this.add(getMatafLabel2(), null);
        this.add(getMatafLabel3(), null);
        this.add(getMatafLabel4(), null);
        this.add(matafButton, null);
        this.add(getMatafButton1(), null);
        this.add(getMatafTableComboBoxButton(), null);
        this.add(getMatafLabel1(), null);
        this.add(getMatafComboTextField(), null);
        this.add(getMatafLabel5(), null);
        this.add(getMatafButton(), null);
        this.setSize(780, 134);
        this.setPreferredSize(new Dimension(780,134));
        /*
        this.setViewName("SampleOverrideView");               
        setContextName("slikaCtx");
		*/	
	}
	/**
	 * This method initializes aSpTextField
	 * 
	 * @return com.ibm.dse.gui.SpTextField
	 */
	public MatafStringField getASpTextField() {
		if(aSpTextField == null) {
			aSpTextField = new MatafStringField();
			
			aSpTextField.setBounds(33, 67, 397, 20);
			aSpTextField.setDataName("managerComment");
		}
		return aSpTextField;
	}
	/*
        this.setViewName("SampleOverrideView");               
        setContextName("slikaCtx");
			
	}
	/**
	 * This method initializes aSpLabel
	 * 
	 * 			aSpLabel = new com.ibm.dse.gui.SpLabel();
			aSpLabel.setBounds(37, 20, 460, 32);
			aSpLabel.setText("מסך אישור לדוגמה");
			aSpLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
03, 94, 139, 27);
bel.setText("JLabel");
SpLabel = new			aSpButton.setFont(new java.awt.Font("Tahoma", 0, 12));
 com.ibm.dse.gui.SpLabel();
			aSpLabel.setBounds(37, 20, 460, 32);
			aSpLabel.setText("JLabel");
		}
		return aSpLabel;
	}	private com.ibm.dse.gui.SpTextField getASpTextField() {
		if(aSpTextField == null) {
			aSpTextField = new com.ibm.dse.gui.SpTextField();
			aSpTextField.setBounds(33, 67, 465, 20);
		}
		return aSpTextField;
	}
	/**
	 * This method initializes aSpButton
	 * 
	 * @return com.ibm.dse.gui.SpButton
	 */
	private com.ibm.dse.gui.SpButton getASpButton() {
		if(aSpButton == null) {
			aSpButton = new MatafButton();
			aSpButton.setBounds(30, 98, 139, 27);
			aSpButton.setText("דחיית בקשה");
			aSpButton.setType("Execute_Operation");
			aSpButton.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("processor",0,"overrideResponceRefuseOp",0,"","","","","","",0,0,0,0,false,false));
		}
		return aSpButton;
	}
	/**
	 * This method initializes aSpButton1
	 * 
	 * @return com.ibm.dse.gui.SpButton
	 */
	private com.ibm.dse.gui.SpButton getASpButton1() {
		if(aSpButton1 == null) {
			aSpButton1 = new MatafButton();
			aSpButton1.setSize(139, 27);
			aSpButton1.setBounds(294, 95, 139, 27);
			aSpButton1.setText("אישור בקשה");
			aSpButton1.setPreferredSize(new java.awt.Dimension(92,27));
			aSpButton1.setType("Execute_Operation");
			aSpButton1.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("processor",0,"overrideResponceApproveOp",0,"","","","","","",0,0,0,0,false,false));
		}
		return aSpButton1;
	}
	/**
	 * This method initializes aSpLabel2
	 * 
	 * @return com.ibm.dse.gui.SpLabel
	 */
	private com.ibm.dse.gui.SpLabel getASpLabel2() {
		if(aSpLabel2 == null) {
			aSpLabel2 = new MatafLabel();
			aSpLabel2.setBounds(229, 16, 272, 20);
			aSpLabel2.setText("אישור מנהל");
		}
		return aSpLabel2;
	}
	/**
	 * This method initializes matafLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel() {
		if(matafLabel == null) {
			matafLabel = new mataf.types.MatafLabel();
			matafLabel.setBounds(733, 16, 37, 20);
			matafLabel.setText("סיבה:");
		}
		return matafLabel;
	}
	/**
	 * This method initializes matafLabel2
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel2() {
		if(matafLabel2 == null) {
			matafLabel2 = new mataf.types.MatafLabel();
			matafLabel2.setBounds(505, 16, 207, 20);
			matafLabel2.setText("");
			matafLabel2.setDataName("trxORData.answerTxt");
		}
		return matafLabel2;
	}
	/**
	 * This method initializes matafLabel3
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel3() {
		if(matafLabel3 == null) {
			matafLabel3 = new mataf.types.MatafLabel();
			matafLabel3.setBounds(721, 40, 49, 20);
			matafLabel3.setText("הערות:");
		}
		return matafLabel3;
	}
	/**
	 * This method initializes matafLabel4
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel4() {
		if(matafLabel4 == null) {
			matafLabel4 = new mataf.types.MatafLabel();
			matafLabel4.setBounds(229, 40, 483, 20);
			matafLabel4.setText("JLabel");
			matafLabel4.setDataName("comment");
		}
		return matafLabel4;
	}
	/**
	 * This method initializes matafButton
	 * 
	 * @return mataf.types.MatafButton
	 */
	private mataf.types.MatafButton getMatafButton() {
		if(matafButton == null) {
			matafButton = new mataf.types.MatafButton();
			matafButton.setBounds(75, 18, 92, 27);
			matafButton.setVisible(true);
			matafButton.addActionListener(new ActionListener(){
				/**
				 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
				 */
				public void actionPerformed(ActionEvent e)
				{
					matafButton.getDSECoordinatedPanel().refreshDataExchangers();
					OverrideResponcePanel temp = OverrideResponcePanel.this;
				}

			});
		}
		//for some reason the visible property has no effect on the control
		//return matafButton;
		return null;
	}
	/**
	 * This method initializes matafButton1
	 * 
	 * @return mataf.types.MatafButton
	 */
	private mataf.types.MatafButton getMatafButton1() {
		if(matafButton1 == null) {
			matafButton1 = new mataf.types.MatafButton();
			matafButton1.setBounds(558, 95, 139, 27);
			matafButton1.setText("יציאה");
			matafButton1.setType("Execute_Operation");
			matafButton1.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("processor",0,"overrideResponceExitOp",0,"","","","","","",0,0,0,0,false,false));
		}
		return matafButton1;
	}
	/**
	 * This method initializes matafTableComboBoxButton
	 * 
	 * @return mataf.types.MatafTableComboBoxButton
	 */
	private mataf.types.MatafTableComboBoxButton getMatafTableComboBoxButton() {
		if(matafTableComboBoxButton == null) {
			
			MatafTable table = new MatafTable();
			table.setDataNameForList("managerAnswerList");
			table.getOurModel().addColumn(String.class, "קוד", "managerAnswerList.GL_KOD_TESHUVA");
			table.getOurModel().addColumn(String.class, "תיאור", "managerAnswerList.GL_TEUR_TESHUVA");			
		
			matafTableComboBoxButton = new mataf.types.MatafTableComboBoxButton();
			matafTableComboBoxButton.setBounds(467, 67, 73, 20);
			matafTableComboBoxButton.setTable(table);
			matafTableComboBoxButton.setRequired(false);
		}
		return matafTableComboBoxButton;
	}
	/**
	 * This method initializes matafLabel1
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel1() {
		if(matafLabel1 == null) {
			matafLabel1 = new mataf.types.MatafLabel();
			matafLabel1.setBounds(545, 67, 120, 20);
			matafLabel1.setDataName("managerAnswerTxt");
		}
		return matafLabel1;
	}
	/**
	 * This method initializes matafComboTextField
	 * 
	 * @return mataf.types.MatafComboTextField
	 */
	private mataf.types.MatafComboTextField getMatafComboTextField() {
		if(matafComboTextField == null) {
			matafComboTextField = new mataf.types.MatafComboTextField();
			matafComboTextField.setBounds(675, 67, 37, 20);
			matafComboTextField.setMaxChars(1);
			matafComboTextField.setDataName("managerAnswerCode");
			matafComboTextField.setTableComboBox(getMatafTableComboBoxButton());
			matafComboTextField.setDescriptionLabel(getMatafLabel1());
		}
		return matafComboTextField;
	}
	/**
	 * This method initializes matafLabel5
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel5() {
		if(matafLabel5 == null) {
			matafLabel5 = new mataf.types.MatafLabel();
			matafLabel5.setBounds(728, 67, 42, 20);
			matafLabel5.setText("סיבה:");
		}
		return matafLabel5;
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="26,3"
