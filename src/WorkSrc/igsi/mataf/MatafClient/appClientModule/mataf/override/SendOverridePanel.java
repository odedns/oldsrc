package mataf.override;


import mataf.types.MatafComboTextField;
import mataf.types.MatafEmbeddedPanel;
import mataf.types.MatafTableComboBoxButton;
import mataf.types.table.MatafTable;
import mataf.types.textfields.MatafStringField;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SendOverridePanel extends MatafEmbeddedPanel {

     private mataf.types.MatafButton matafButton = null;
     private mataf.types.MatafLabel matafLabel2 = null;
     private mataf.types.MatafLabel matafLabel = null;
     private MatafStringField matafTextField = null;
     private mataf.types.MatafButton matafButton2 = null;
     private mataf.types.MatafLabel matafLabel3 = null;
     private MatafTableComboBoxButton matafTableComboBox = null;
     private MatafTableComboBoxButton matafTableComboBox2 = null;
     private MatafComboTextField matafTextField2 = null;
     private MatafComboTextField matafTextField3 = null;
     private mataf.types.MatafLabel matafLabel4 = null;
     private mataf.types.MatafLabel matafLabel5 = null;
     private mataf.types.MatafComboTextField matafComboTextField3 = null;
	/**
	 * This method initializes 
	 * 
	 */
	public SendOverridePanel() {
		super();
		initialize();				
	}
	
	public void disableSendButton()
	{
		matafButton.setEnabled(false);		
	}
	
	public static void main(String[] args) {
			try {
		javax.swing.JFrame frame = new javax.swing.JFrame();		
		SendOverridePanel orView = new SendOverridePanel();
		frame.setContentPane(orView);
		frame.setSize(orView.getSize());
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});
		frame.show();
		java.awt.Insets insets = frame.getInsets();
		frame.setSize(frame.getWidth() + insets.left + insets.right, frame.getHeight() + insets.top + insets.bottom);
		frame.setVisible(true);
	} catch (Throwable exception) {
		exception.printStackTrace(System.out);
	}
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.add(getMatafButton(), null);
        this.add(getMatafLabel2(), null);
        this.add(getMatafLabel(), null);
        this.add(getMatafTextField(), null);
        this.add(getMatafButton2(), null);
        this.add(getMatafLabel3(), null);
        this.add(getMatafTableComboBox(), null);
        this.add(getMatafTableComboBox2(), null);
        this.add(getMatafTextField2(), null);
        this.add(getMatafComboTextField3(), null);
        this.add(getMatafLabel4(), null);
        this.add(getMatafLabel5(), null);
        this.add(getMatafComboTextField3(), null);
        this.setSize(780, 144);
                
        
	}
	/**
	 * This method initializes matafButton
	 * 
	 * @return mataf.types.MatafButton
	 */
	private mataf.types.MatafButton getMatafButton() {
		if(matafButton == null) {
			matafButton = new mataf.types.MatafButton();
			matafButton.setBounds(622, 104, 120, 20);
			matafButton.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("activeView",0,"overrideReqOp",0,"","","","","","",0,0,0,0,false,false));
			matafButton.setType("Execute_Operation");
			matafButton.setText("ודא נתונים");
		}
		return matafButton;
	}
	/**
	 * This method initializes matafLabel2
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel2() {
		if(matafLabel2 == null) {
			matafLabel2 = new mataf.types.MatafLabel();
			matafLabel2.setBounds(650, 68, 100, 20);
			matafLabel2.setText("הערה");
		}
		return matafLabel2;
	}
	/**
	 * This method initializes matafLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel() {
		if(matafLabel == null) {
			matafLabel = new mataf.types.MatafLabel();
			matafLabel.setBounds(650, 12, 100, 20);
			matafLabel.setText("זיהוי מנהל");
		}
		return matafLabel;
	}
	/**
	 * This method initializes matafTextField
	 * 
	 * @return mataf.types.MatafTextField
	 */
	private MatafStringField getMatafTextField() {
		if(matafTextField == null) {
			matafTextField = new MatafStringField();
			matafTextField.setBounds(304, 67, 328, 20);
			matafTextField.setDataName("comment");			
		}
		return matafTextField;
	}
	/**
	 * This method initializes matafButton2
	 * 
	 * @return mataf.types.MatafButton
	 */
	private mataf.types.MatafButton getMatafButton2() {
		if(matafButton2 == null) {
			matafButton2 = new mataf.types.MatafButton();
			matafButton2.setBounds(458, 106, 100, 20);
			matafButton2.setText("צא");
			matafButton2.setType("Execute_Operation");
			matafButton2.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("processor",0,"overrideCancelClientOp",0,"","","","","","",0,0,0,0,false,false));
		}
		return matafButton2;
	}
	/**
	 * This method initializes matafLabel3
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel3() {
		if(matafLabel3 == null) {
			matafLabel3 = new mataf.types.MatafLabel();
			matafLabel3.setBounds(650, 40, 100, 20);
			matafLabel3.setText("קוד הודעה");
		}
		return matafLabel3;
	}
	/**
	 * This method initializes matafTableComboBox
	 * 
	 * @return mataf.types.MatafTableComboBox
	 */
	private MatafTableComboBoxButton getMatafTableComboBox() {
		if(matafTableComboBox == null) {
			MatafTable table = new MatafTable();
			table.setDataNameForList("managersList");
			table.getOurModel().addColumn(Integer.class, "זיהוי ","managersList.managerId");
			table.getOurModel().addColumn(String.class, "שם ", "managersList.managerName");
			table.getOurModel().addColumn(String.class, "מצב תחנה ", "managersList.wksStatus");
			
			matafTableComboBox = new MatafTableComboBoxButton();
			matafTableComboBox.setBounds(394, 10, 60, 20);
			matafTableComboBox.setTable(table);
		}
		return matafTableComboBox;
	}
	/**
	 * This method initializes matafTableComboBox2
	 * 
	 * @return mataf.types.MatafTableComboBox
	 */
	private MatafTableComboBoxButton getMatafTableComboBox2() {
		if(matafTableComboBox2 == null) {
			MatafTable table = new MatafTable();
			table.setDataNameForList("answerList");
			table.getOurModel().addColumn(String.class, "קוד", "answerList.GL_KOD_BAKASHA");
			table.getOurModel().addColumn(String.class, "תיאור", "answerList.GL_TEUR_BAKASHA");			

			matafTableComboBox2 = new MatafTableComboBoxButton();
			matafTableComboBox2.setBounds(394, 40, 60, 20);			
			matafTableComboBox2.setTable(table);
			matafTableComboBox2.setRequired(false);
		}
		return matafTableComboBox2;
	}
	/**
	 * This method initializes matafTextField2
	 * 
	 * @return mataf.types.MatafTextField
	 */
	private mataf.types.MatafComboTextField getMatafTextField2() {
		if(matafTextField2 == null) {
			matafTextField2 = new mataf.types.MatafComboTextField();
			matafTextField2.setBounds(579, 11, 51, 20);
			matafTextField2.setDataName("chosenManagerId");
			matafTextField2.setTableComboBox(getMatafTableComboBox());
			matafTextField2.setDescriptionLabel(getMatafLabel4());
		}
		return matafTextField2;
	}
	
	/**
	 * This method initializes matafLabel4
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel4() {
		if(matafLabel4 == null) {
			matafLabel4 = new mataf.types.MatafLabel();
			matafLabel4.setBounds(518, 11, 55, 20);	
			matafLabel4.setDataName("chosenManagerName");
		}
		return matafLabel4;
	}
	/**
	 * This method initializes matafLabel5
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel5() {
		if(matafLabel5 == null) {
			matafLabel5 = new mataf.types.MatafLabel();
			matafLabel5.setBounds(477, 40, 96, 20);
			matafLabel5.setDataName("answerTxt");	
		}
		return matafLabel5;
	}
	/**
	 * This method initializes matafComboTextField
	 * 
	 * @return mataf.types.MatafComboTextField
	 */
	private mataf.types.MatafComboTextField getMatafComboTextField3() {
		if(matafComboTextField3 == null) {
			matafComboTextField3 = new mataf.types.MatafComboTextField();
			matafComboTextField3.setBounds(600, 42, 30, 20);
			matafComboTextField3.setDataName("answerCode");
			matafComboTextField3.setMaxChars(1);
			matafComboTextField3.setTableComboBox(getMatafTableComboBox2());
			matafComboTextField3.setDescriptionLabel(getMatafLabel5());
			matafComboTextField3.setRequired(false);

		}
		return matafComboTextField3;
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="646,53"
