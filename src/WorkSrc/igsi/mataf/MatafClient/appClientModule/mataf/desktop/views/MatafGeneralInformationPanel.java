package mataf.desktop.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;

import mataf.types.MatafEmbeddedPanel;
import mataf.types.MatafLabel;
import mataf.utils.FontFactory;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (25/04/2004 19:52:25).  
 */
public class MatafGeneralInformationPanel extends MatafEmbeddedPanel
{
	private static final Color 	BG_COLOR = new Color(203,223,250);
	static final Font	FONT = FontFactory.createFont("Arial",Font.BOLD,10);	

	private GeneralInformationPanelLabel peulaLabel;
	private GeneralInformationPanelLabel stateLabel;
	private mataf.types.MatafComboBox actionComboBox = null;
	private mataf.types.MatafComboBox matafComboBox = null;
	private GeneralInformationPanelLabel accountNumberLabel = null;
	private mataf.desktop.views.MatafGeneralInformationPanel.GeneralInformationPanelLabel IDLabel = null;
	private mataf.types.textfields.MatafNumericField accountNumberField = null;
	private mataf.types.textfields.MatafNumericField IDField = null;
	/**
	 * 
	 */
	public MatafGeneralInformationPanel()
	{
		super();
		initialize();
		setPreferredSize(new Dimension(100,40));
		setBackground(BG_COLOR);
		setBorder(null);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.add(getPeulaLabel(), null);
        this.add(getStateLabel(), null);
        this.add(getActionComboBox(), null);
        this.add(getMatafComboBox(), null);
        this.add(getAccountNumberLabel(), null);
        this.add(getIDLabel(), null);
        this.add(getAccountNumberField(), null);
        this.add(getIDField(), null);
        this.setSize(782, 40);
			
	}
	/**
	 * This method initializes peulaLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getPeulaLabel() {
		if(peulaLabel == null) {
			peulaLabel = new GeneralInformationPanelLabel();
			peulaLabel.setBounds(732, 3, 43, 16);
			peulaLabel.setText("פעולה :");
		}
		return peulaLabel;
	}
	/**
	 * This method initializes stateLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getStateLabel() {
		if(stateLabel == null) {
			stateLabel = new GeneralInformationPanelLabel();
			stateLabel.setBounds(733, 22, 42, 16);
			stateLabel.setText("מדינה :");			
		}
		return stateLabel;
	}
	
	/**
	 * Static Inner class defining a label used in the panel.
	 *
	 * @author Nati Dykstein. Creation Date : (25/04/2004 20:05:19).
	 */
	static class GeneralInformationPanelLabel extends MatafLabel
	{
		
		static final Color	FOREGROUND = new Color(32,89,144);
		
		public GeneralInformationPanelLabel()
		{
			setFont(FONT);
			setForeground(FOREGROUND);	
		}
	}
	/**
	 * This method initializes actionComboBox
	 * 
	 * @return mataf.types.MatafComboBox
	 */
	private mataf.types.MatafComboBox getActionComboBox() {
		if(actionComboBox == null) {
			actionComboBox = new mataf.types.MatafComboBox();
			actionComboBox.setBounds(653, 3, 77, 16);
		}
		return actionComboBox;
	}
	/**
	 * This method initializes matafComboBox
	 * 
	 * @return mataf.types.MatafComboBox
	 */
	private mataf.types.MatafComboBox getMatafComboBox() {
		if(matafComboBox == null) {
			matafComboBox = new mataf.types.MatafComboBox();
			matafComboBox.setBounds(653, 22, 77, 16);
		}
		return matafComboBox;
	}
	/**
	 * This method initializes accountNumberLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private GeneralInformationPanelLabel getAccountNumberLabel() {
		if(accountNumberLabel == null) {
			accountNumberLabel = new GeneralInformationPanelLabel();
			accountNumberLabel.setBounds(583, 3, 55, 16);
			accountNumberLabel.setText("מס' חשבון :");
			//accountNumberLabel.setFont(FONT);
		}
		return accountNumberLabel;
	}
	/**
	 * This method initializes IDLabel
	 * 
	 * @return mataf.desktop.views.MatafGeneralInformationPanel.GeneralInformationPanelLabel
	 */
	private mataf.desktop.views.MatafGeneralInformationPanel.GeneralInformationPanelLabel getIDLabel() {
		if(IDLabel == null) {
			IDLabel = new mataf.desktop.views.MatafGeneralInformationPanel.GeneralInformationPanelLabel();
			IDLabel.setBounds(583, 22, 24, 16);
			IDLabel.setText("ת.ז :");
		}
		return IDLabel;
	}
	/**
	 * This method initializes accountNumberField
	 * 
	 * @return mataf.types.textfields.MatafNumericField
	 */
	private mataf.types.textfields.MatafNumericField getAccountNumberField() {
		if(accountNumberField == null) {
			accountNumberField = new mataf.types.textfields.MatafNumericField();
			accountNumberField.setBounds(508, 3, 71, 16);
		}
		return accountNumberField;
	}
	/**
	 * This method initializes IDField
	 * 
	 * @return mataf.types.textfields.MatafNumericField
	 */
	private mataf.types.textfields.MatafNumericField getIDField() {
		if(IDField == null) {
			IDField = new mataf.types.textfields.MatafNumericField();
			IDField.setBounds(508, 22, 71, 16);
		}
		return IDField;
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="10,10"
