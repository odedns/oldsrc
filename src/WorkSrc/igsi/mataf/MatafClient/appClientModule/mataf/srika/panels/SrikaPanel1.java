package mataf.srika.panels;

import mataf.types.MatafButton;
import mataf.types.MatafEmbeddedPanel;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (18/08/2003 10:48:29).  
 */
public class SrikaPanel1 extends MatafEmbeddedPanel {

     private mataf.types.MatafTitle matafTitle = null;
     private mataf.types.MatafLabel fromTrxNumLabel = null;
     private mataf.types.MatafTextField fromTrxNumTextField = null;
     private mataf.types.MatafLabel untilTrxNumLabel = null;
     private mataf.types.MatafTextField untilTrxNumTextField = null;
     private mataf.types.MatafLabel sugPeulaLabel = null;
     private mataf.types.MatafLabel snifLabel = null;
     private mataf.types.MatafLabel accountTypeLabel = null;
     private mataf.types.MatafLabel accountLabel = null;
     private mataf.types.MatafButton exitButton = null;
     private mataf.types.MatafButton nextViewButton = null;
     private mataf.types.MatafButton scanButton = null;
     private mataf.types.MatafTextField sugPeulaTextField = null;
	private mataf.types.MatafComboTextField snifComboTextField = null;
	private mataf.types.MatafComboTextField accountComboTextField = null;
	private mataf.types.MatafComboTextField accountTypeComboTextField = null;
	private mataf.types.MatafLabel snifDescLabel = null;
	private mataf.types.MatafLabel accountTypeDescLabel = null;
	private mataf.types.specific.BranchTableComboBoxButton snifTableComboBoxButton = null;
	private mataf.types.specific.AccountTypeTableComboBoxButton accountTypeTableComboBoxButton = null;
	private mataf.types.MatafLabel fromAmountLabel = null;
	private mataf.types.MatafLabel upToAmountLabel = null;
	private mataf.types.MatafTextField fromAmountTextField = null;
	private mataf.types.MatafTextField upToAmountTextField = null;
	/**
	 * This method initializes 
	 * 
	 */
	public SrikaPanel1() {
		super();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.add(getMatafTitle(), null);
        this.add(getFromTrxNumLabel(), null);
        this.add(getFromTrxNumTextField(), null);
        this.add(getUntilTrxNumLabel(), null);
        this.add(getUntilTrxNumTextField(), null);
        this.add(getSugPeulaLabel(), null);
        this.add(getSnifLabel(), null);
        this.add(getAccountTypeLabel(), null);
        this.add(getAccountLabel(), null);
        this.add(getExitButton(), null);
        this.add(getNextViewButton(), null);
        this.add(getScanButton(), null);
        this.add(getSugPeulaTextField(), null);
        this.add(getSnifComboTextField(), null);
        this.add(getAccountComboTextField(), null);
        this.add(getAccountTypeComboTextField(), null);
        this.add(getSnifDescLabel(), null);
        this.add(getAccountTypeDescLabel(), null);
        this.add(getSnifTableComboBoxButton(), null);
        this.add(getAccountTypeTableComboBoxButton(), null);
        this.add(getFromAmountLabel(), null);
        this.add(getUpToAmountLabel(), null);
        this.add(getFromAmountTextField(), null);
        this.add(getUpToAmountTextField(), null);
        this.setBounds(0, 0, 780, 450);
        this.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
			
	}
	/**
	 * This method initializes matafTitle
	 * 
	 * @return mataf.types.MatafTitle
	 */
	private mataf.types.MatafTitle getMatafTitle() {
		if(matafTitle == null) {
			matafTitle = new mataf.types.MatafTitle();
			matafTitle.setText("497 - סריקת המחאות מזומן");
		}
		return matafTitle;
	}
	/**
	 * This method initializes fromTrxNumLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getFromTrxNumLabel() {
		if(fromTrxNumLabel == null) {
			fromTrxNumLabel = new mataf.types.MatafLabel();
			fromTrxNumLabel.setBounds(680, 30, 93, 20);
			fromTrxNumLabel.setText("מספר פעולה מ :");
		}
		return fromTrxNumLabel;
	}
	/**
	 * This method initializes fromTrxNumTextField
	 * 
	 * @return mataf.types.MatafTextField
	 */
	private mataf.types.MatafTextField getFromTrxNumTextField() {
		if(fromTrxNumTextField == null) {
			fromTrxNumTextField = new mataf.types.MatafTextField();
			fromTrxNumTextField.setBounds(640, 30, 38, 21);
		}
		return fromTrxNumTextField;
	}
	/**
	 * This method initializes untilTrxNumLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getUntilTrxNumLabel() {
		if(untilTrxNumLabel == null) {
			untilTrxNumLabel = new mataf.types.MatafLabel();
			untilTrxNumLabel.setBounds(600, 30, 30, 20);
			untilTrxNumLabel.setText("עד :");
		}
		return untilTrxNumLabel;
	}
	/**
	 * This method initializes untilTrxNumTextField
	 * 
	 * @return mataf.types.MatafTextField
	 */
	private mataf.types.MatafTextField getUntilTrxNumTextField() {
		if(untilTrxNumTextField == null) {
			untilTrxNumTextField = new mataf.types.MatafTextField();
			untilTrxNumTextField.setBounds(560, 30, 38, 20);
		}
		return untilTrxNumTextField;
	}
	/**
	 * This method initializes sugPeulaLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getSugPeulaLabel() {
		if(sugPeulaLabel == null) {
			sugPeulaLabel = new mataf.types.MatafLabel();
			sugPeulaLabel.setBounds(680, 55, 93, 20);
			sugPeulaLabel.setText("סוג פעולה :");
		}
		return sugPeulaLabel;
	}
	/**
	 * This method initializes snifLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getSnifLabel() {
		if(snifLabel == null) {
			snifLabel = new mataf.types.MatafLabel();
			snifLabel.setBounds(680, 105, 93, 20);
			snifLabel.setText("סניף :");
		}
		return snifLabel;
	}
	/**
	 * This method initializes accountTypeLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getAccountTypeLabel() {
		if(accountTypeLabel == null) {
			accountTypeLabel = new mataf.types.MatafLabel();
			accountTypeLabel.setBounds(680, 130, 93, 20);
			accountTypeLabel.setText("סוג חשבון :");
		}
		return accountTypeLabel;
	}
	/**
	 * This method initializes accountLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getAccountLabel() {
		if(accountLabel == null) {
			accountLabel = new mataf.types.MatafLabel();
			accountLabel.setBounds(680, 155, 93, 20);
			accountLabel.setText("חשבון :");
		}
		return accountLabel;
	}
	/**
	 * This method initializes exitButton
	 * 
	 * @return mataf.types.MatafButton
	 */
	private mataf.types.MatafButton getExitButton() {
		if(exitButton == null) {
			exitButton = new mataf.types.MatafButton();
			exitButton.setBounds(30, 425, 50, 20);
			exitButton.setType(MatafButton.CLOSE_VIEW);
			exitButton.setText("צא");
		}
		return exitButton;
	}
	/**
	 * This method initializes nextViewButton
	 * 
	 * @return mataf.types.MatafButton
	 */
	private mataf.types.MatafButton getNextViewButton() {
		if(nextViewButton == null) {
			nextViewButton = new mataf.types.MatafButton();
			nextViewButton.setBounds(100, 425, 130, 20);
			nextViewButton.setText("פרמטרים כלליים");
			nextViewButton.setType(MatafButton.NEXT_VIEW);
			nextViewButton.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("activeView",2,"",0,"advancedSrikaView","mataf.srika.views.AdvancedSrikaView","","","","",0,0,0,0,false,false));
		}
		return nextViewButton;
	}
	/**
	 * This method initializes scanButton
	 * 
	 * @return mataf.types.MatafButton
	 */
	private mataf.types.MatafButton getScanButton() {
		if(scanButton == null) {
			scanButton = new mataf.types.MatafButton();
			scanButton.setBounds(240, 425, 66, 20);
			scanButton.setText("סרוק");
			scanButton.setType(MatafButton.NEXT_VIEW);
			scanButton.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("activeView",2,"",0,"srikaResultsView","mataf.srika.views.SrikaResultsView","","","","",0,0,0,0,false,false));
		}
		return scanButton;
	}
	/**
	 * This method initializes sugPeulaTextField
	 * 
	 * @return mataf.types.MatafTextField
	 */
	private mataf.types.MatafTextField getSugPeulaTextField() {
		if(sugPeulaTextField == null) {
			sugPeulaTextField = new mataf.types.MatafTextField();
			sugPeulaTextField.setBounds(580, 55, 98, 21);
		}
		return sugPeulaTextField;
	}
	/**
	 * This method initializes snifComboTextField
	 * 
	 * @return mataf.types.MatafComboTextField
	 */
	private mataf.types.MatafComboTextField getSnifComboTextField() {
		if(snifComboTextField == null) {
			snifComboTextField = new mataf.types.MatafComboTextField();
			snifComboTextField.setBounds(643, 105, 35, 20);
			snifComboTextField.setMaxChars(3);
			snifComboTextField.setFillInChar('0');
			snifComboTextField.setTableComboBox(getSnifTableComboBoxButton());
			snifComboTextField.setDescriptionLabel(getSnifDescLabel());
			
		}
		return snifComboTextField;
	}
	/**
	 * This method initializes accountComboTextField
	 * 
	 * @return mataf.types.MatafComboTextField
	 */
	private mataf.types.MatafComboTextField getAccountComboTextField() {
		if(accountComboTextField == null) {
			accountComboTextField = new mataf.types.MatafComboTextField();
			accountComboTextField.setBounds(604, 155, 74, 20);
		}
		return accountComboTextField;
	}
	/**
	 * This method initializes accountTypeComboTextField
	 * 
	 * @return mataf.types.MatafComboTextField
	 */
	private mataf.types.MatafComboTextField getAccountTypeComboTextField() {
		if(accountTypeComboTextField == null) {
			accountTypeComboTextField = new mataf.types.MatafComboTextField();
			accountTypeComboTextField.setBounds(643, 130, 35, 20);
			accountTypeComboTextField.setMaxChars(3);
			accountTypeComboTextField.setFillInChar('0');
			accountTypeComboTextField.setDescriptionLabel(getAccountTypeDescLabel());
			accountTypeComboTextField.setTableComboBox(getAccountTypeTableComboBoxButton());
		}
		return accountTypeComboTextField;
	}
	/**
	 * This method initializes snifDescLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getSnifDescLabel() {
		if(snifDescLabel == null) {
			snifDescLabel = new mataf.types.MatafLabel();
			snifDescLabel.setBounds(520, 105, 114, 20);
			snifDescLabel.setText("משוב לסניף");
		}
		return snifDescLabel;
	}
	/**
	 * This method initializes accountTypeDescLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getAccountTypeDescLabel() {
		if(accountTypeDescLabel == null) {
			accountTypeDescLabel = new mataf.types.MatafLabel();
			accountTypeDescLabel.setBounds(520, 130, 114, 20);
			accountTypeDescLabel.setText("משוב לסוג חשבון");
		}
		return accountTypeDescLabel;
	}
	/**
	 * This method initializes snifTableComboBoxButton
	 * 
	 * @return mataf.types.specific.SnifTableComboBoxButton
	 */
	private mataf.types.specific.BranchTableComboBoxButton getSnifTableComboBoxButton() {
		if(snifTableComboBoxButton == null) {
			snifTableComboBoxButton = new mataf.types.specific.BranchTableComboBoxButton();
			snifTableComboBoxButton.setBounds(460, 105, 60, 20);
		}
		return snifTableComboBoxButton;
	}
	/**
	 * This method initializes accountTypeTableComboBoxButton
	 * 
	 * @return mataf.types.specific.AccountTypeTableComboBoxButton
	 */
	private mataf.types.specific.AccountTypeTableComboBoxButton getAccountTypeTableComboBoxButton() {
		if(accountTypeTableComboBoxButton == null) {
			accountTypeTableComboBoxButton = new mataf.types.specific.AccountTypeTableComboBoxButton();
			accountTypeTableComboBoxButton.setBounds(460, 130, 60, 20);
		}
		return accountTypeTableComboBoxButton;
	}
	/**
	 * This method initializes fromAmountLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getFromAmountLabel() {
		if(fromAmountLabel == null) {
			fromAmountLabel = new mataf.types.MatafLabel();
			fromAmountLabel.setBounds(680, 180, 93, 20);
			fromAmountLabel.setText("סכום מ :");
		}
		return fromAmountLabel;
	}
	/**
	 * This method initializes upToAmountLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getUpToAmountLabel() {
		if(upToAmountLabel == null) {
			upToAmountLabel = new mataf.types.MatafLabel();
			upToAmountLabel.setBounds(563, 180, 30, 20);
			upToAmountLabel.setText("עד :");
		}
		return upToAmountLabel;
	}
	/**
	 * This method initializes fromAmountTextField
	 * 
	 * @return mataf.types.MatafTextField
	 */
	private mataf.types.MatafTextField getFromAmountTextField() {
		if(fromAmountTextField == null) {
			fromAmountTextField = new mataf.types.MatafTextField();
			fromAmountTextField.setBounds(604, 180, 74, 19);
		}
		return fromAmountTextField;
	}
	/**
	 * This method initializes upToAmountTextField
	 * 
	 * @return mataf.types.MatafTextField
	 */
	private mataf.types.MatafTextField getUpToAmountTextField() {
		if(upToAmountTextField == null) {
			upToAmountTextField = new mataf.types.MatafTextField();
			upToAmountTextField.setBounds(486, 180, 74, 19);
		}
		return upToAmountTextField;
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="-92,10"
