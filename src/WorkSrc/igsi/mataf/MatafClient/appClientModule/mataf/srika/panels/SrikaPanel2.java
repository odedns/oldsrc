package mataf.srika.panels;

import mataf.types.MatafButton;
import mataf.types.MatafEmbeddedPanel;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (10/09/2003 17:36:24).  
 */
public class SrikaPanel2 extends MatafEmbeddedPanel {

    private mataf.types.MatafTitle matafTitle = null;
	private mataf.types.MatafLabel systemNameLabel = null;
	private mataf.types.MatafLabel systemName = null;
	private mataf.types.MatafLabel yomAsakimLabel = null;
	private mataf.types.MatafComboTextField yomAsakimComboTextField = null;
	private mataf.types.MatafLabel yomAsakimDescLabel = null;
	private mataf.types.MatafTableComboBoxButton yomAsakimTableComboBoxButton = null;
	private mataf.types.MatafLabel shiftLabel = null;
	private mataf.types.MatafLabel hourLabel = null;
	private mataf.types.MatafLabel workstationLabel = null;
	private mataf.types.MatafLabel clerkLabel = null;
	private mataf.types.MatafLabel neededActionsLabel = null;
	private mataf.types.MatafLabel transmittedActionsLabel1 = null;
	private mataf.types.MatafLabel managerApprovalLabel = null;
	private mataf.types.MatafLabel managerLabel = null;
	private mataf.types.MatafLabel secondaryOpLabel = null;
	private mataf.types.MatafButton exitButton = null;
	private mataf.types.MatafButton scanButton = null;
	private mataf.types.MatafButton countersButton = null;
	private mataf.types.MatafComboTextField shiftComboTextField = null;
	private mataf.types.MatafLabel shiftDescLabel = null;
	private mataf.types.MatafTableComboBoxButton shiftTableComboBoxButton = null;
	private mataf.types.MatafLabel fromHourLabel = null;
	private mataf.types.MatafTextField fromHourTextField = null;
	private mataf.types.MatafLabel untilHourLabel = null;
	private mataf.types.MatafTextField untilHourTextField = null;
	private mataf.types.MatafTextField workstationTextField = null;
	private mataf.types.MatafLabel workstationDescMatafLabel = null;
	private mataf.types.MatafTextField clerkTextField = null;
	private mataf.types.MatafLabel clerkDescLabel = null;
	private mataf.types.MatafComboTextField neededActionsComboTextField = null;
	private mataf.types.MatafTableComboBoxButton neededActionsTableComboBoxButton = null;
	private mataf.types.MatafLabel neededActionsDescLabel = null;
	private mataf.types.MatafComboTextField transmittedActionsComboTextField = null;
	
	private mataf.types.MatafTableComboBoxButton transmittedActionsTableComboBoxButton = null;
	private mataf.types.MatafLabel transmittedActionsLabel = null;
	private mataf.types.MatafComboTextField managerApprovalComboTextField = null;
	private mataf.types.MatafTableComboBoxButton managerApprovalTableComboBoxButton = null;
	private mataf.types.MatafLabel managerApprovalDescLabel = null;
	private mataf.types.MatafComboTextField managerComboTextField = null;
	private mataf.types.MatafTableComboBoxButton managerTableComboBoxButton = null;
	private mataf.types.MatafLabel managerDescLabel = null;
	private mataf.types.MatafTextField secondaryOpTextField = null;
	/**
	 * This method initializes 
	 * 
	 */
	public SrikaPanel2() {
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
        this.add(getSystemNameLabel(), null);
        this.add(getSystemName(), null);
        this.add(getYomAsakimLabel(), null);
        this.add(getYomAsakimComboTextField(), null);
        this.add(getYomAsakimDescLabel(), null);
        this.add(getYomAsakimTableComboBoxButton(), null);
        this.add(getShiftLabel(), null);
        this.add(getHourLabel(), null);
        this.add(getWorkstationLabel(), null);
        this.add(getClerkLabel(), null);
        this.add(getNeededActionsLabel(), null);
        this.add(getTransmittedActionsLabel1(), null);
        this.add(getManagerApprovalLabel(), null);
        this.add(getManagerLabel(), null);
        this.add(getSecondaryOpLabel(), null);
        this.add(getExitButton(), null);
        this.add(getScanButton(), null);
        this.add(getCountersButton(), null);
        this.add(getShiftComboTextField(), null);
        this.add(getShiftDescLabel(), null);
        this.add(getShiftTableComboBoxButton(), null);
        this.add(getFromHourLabel(), null);
        this.add(getFromHourTextField(), null);
        this.add(getUntilHourLabel(), null);
        this.add(getUntilHourTextField(), null);
        this.add(getWorkstationTextField(), null);
        this.add(getWorkstationDescMatafLabel(), null);
        this.add(getClerkTextField(), null);
        this.add(getClerkDescLabel(), null);
        this.add(getNeededActionsComboTextField(), null);
        this.add(getNeededActionsTableComboBoxButton(), null);
        this.add(getNeededActionsDescLabel(), null);
        this.add(getTransmittedActionsComboTextField(), null);
        this.add(getTransmittedActionsTableComboBoxButton(), null);
        this.add(getTransmittedActionsLabel(), null);
        this.add(getManagerApprovalComboTextField(), null);
        this.add(getManagerApprovalTableComboBoxButton(), null);
        this.add(getManagerApprovalDescLabel(), null);
        this.add(getManagerComboTextField(), null);
        this.add(getManagerTableComboBoxButton(), null);
        this.add(getManagerDescLabel(), null);
        this.add(getSecondaryOpTextField(), null);
        this.setBounds(0, 0, 780, 450);
			
	}
	/**
	 * This method initializes matafTitle
	 * 
	 * @return mataf.types.MatafTitle
	 */
	private mataf.types.MatafTitle getMatafTitle() {
		if(matafTitle == null) {
			matafTitle = new mataf.types.MatafTitle();
			matafTitle.setText("פרמטרים כלליים לחתך");
		}
		return matafTitle;
	}
	/**
	 * This method initializes systemNameLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getSystemNameLabel() {
		if(systemNameLabel == null) {
			systemNameLabel = new mataf.types.MatafLabel();
			systemNameLabel.setBounds(708, 30, 52, 20);
			systemNameLabel.setText("מערכת :");
		}
		return systemNameLabel;
	}
	/**
	 * This method initializes systemName
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getSystemName() {
		if(systemName == null) {
			systemName = new mataf.types.MatafLabel();
			systemName.setBounds(613, 30, 92, 20);
			systemName.setText("");
		}
		return systemName;
	}
	/**
	 * This method initializes yomAsakimLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getYomAsakimLabel() {
		if(yomAsakimLabel == null) {
			yomAsakimLabel = new mataf.types.MatafLabel();
			yomAsakimLabel.setBounds(530, 30, 65, 20);
			yomAsakimLabel.setText("יום עסקים :");
		}
		return yomAsakimLabel;
	}
	/**
	 * This method initializes yomAsakimComboTextField
	 * 
	 * @return mataf.types.MatafComboTextField
	 */
	private mataf.types.MatafComboTextField getYomAsakimComboTextField() {
		if(yomAsakimComboTextField == null) {
			yomAsakimComboTextField = new mataf.types.MatafComboTextField();
			yomAsakimComboTextField.setBounds(509, 30, 19, 20);
			//yomAsakimComboTextField.setTableComboBox(getYomAsakimTableComboBoxButton());
			yomAsakimComboTextField.setDescriptionLabel(getYomAsakimDescLabel());
		}
		return yomAsakimComboTextField;
	}
	/**
	 * This method initializes yomAsakimDescLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getYomAsakimDescLabel() {
		if(yomAsakimDescLabel == null) {
			yomAsakimDescLabel = new mataf.types.MatafLabel();
			yomAsakimDescLabel.setBounds(437, 30, 70, 19);
			yomAsakimDescLabel.setText("משוב");
		}
		return yomAsakimDescLabel;
	}
	/**
	 * This method initializes yomAsakimTableComboBoxButton
	 * 
	 * @return mataf.types.MatafTableComboBoxButton
	 */
	private mataf.types.MatafTableComboBoxButton getYomAsakimTableComboBoxButton() {
		if(yomAsakimTableComboBoxButton == null) {
			yomAsakimTableComboBoxButton = new mataf.types.MatafTableComboBoxButton();
			yomAsakimTableComboBoxButton.setBounds(366, 30, 58, 20);
		}
		return yomAsakimTableComboBoxButton;
	}
	/**
	 * This method initializes shiftLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getShiftLabel() {
		if(shiftLabel == null) {
			shiftLabel = new mataf.types.MatafLabel();
			shiftLabel.setBounds(708, 80, 52, 20);
			shiftLabel.setText("משמרת :");
		}
		return shiftLabel;
	}
	/**
	 * This method initializes hourLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getHourLabel() {
		if(hourLabel == null) {
			hourLabel = new mataf.types.MatafLabel();
			hourLabel.setBounds(708, 105, 52, 20);
			hourLabel.setText("שעה :");
		}
		return hourLabel;
	}
	/**
	 * This method initializes workstationLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getWorkstationLabel() {
		if(workstationLabel == null) {
			workstationLabel = new mataf.types.MatafLabel();
			workstationLabel.setBounds(708, 130, 52, 20);
			workstationLabel.setText("תחנה :");
		}
		return workstationLabel;
	}
	/**
	 * This method initializes clerkLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getClerkLabel() {
		if(clerkLabel == null) {
			clerkLabel = new mataf.types.MatafLabel();
			clerkLabel.setBounds(708, 155, 52, 20);
			clerkLabel.setText("פקיד :");
		}
		return clerkLabel;
	}
	/**
	 * This method initializes neededActionsLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getNeededActionsLabel() {
		if(neededActionsLabel == null) {
			neededActionsLabel = new mataf.types.MatafLabel();
			neededActionsLabel.setBounds(659, 205, 101, 20);
			neededActionsLabel.setText("פעולות דרושות :");
		}
		return neededActionsLabel;
	}
	/**
	 * This method initializes transmittedActionsLabel1
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getTransmittedActionsLabel1() {
		if(transmittedActionsLabel1 == null) {
			transmittedActionsLabel1 = new mataf.types.MatafLabel();
			transmittedActionsLabel1.setBounds(659, 230, 101, 20);
			transmittedActionsLabel1.setText("פעולות ששודרו :");
		}
		return transmittedActionsLabel1;
	}
	/**
	 * This method initializes managerApprovalLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getManagerApprovalLabel() {
		if(managerApprovalLabel == null) {
			managerApprovalLabel = new mataf.types.MatafLabel();
			managerApprovalLabel.setBounds(659, 255, 101, 20);
			managerApprovalLabel.setText("אישור מנהל :");
		}
		return managerApprovalLabel;
	}
	/**
	 * This method initializes managerLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getManagerLabel() {
		if(managerLabel == null) {
			managerLabel = new mataf.types.MatafLabel();
			managerLabel.setBounds(659, 280, 101, 20);
			managerLabel.setText("מנהל מאשר :");
		}
		return managerLabel;
	}
	/**
	 * This method initializes secondaryOpLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getSecondaryOpLabel() {
		if(secondaryOpLabel == null) {
			secondaryOpLabel = new mataf.types.MatafLabel();
			secondaryOpLabel.setBounds(659, 305, 101, 20);
			secondaryOpLabel.setText("פעולה משנית :");
		}
		return secondaryOpLabel;
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
	 * This method initializes scanButton
	 * 
	 * @return mataf.types.MatafButton
	 */
	private mataf.types.MatafButton getScanButton() {
		if(scanButton == null) {
			scanButton = new mataf.types.MatafButton();
			scanButton.setBounds(90, 425, 80, 20);
			scanButton.setType(MatafButton.NEXT_VIEW);
			scanButton.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("activeView",2,"",0,"srikaResultsView","mataf.srika.views.SrikaResultsView","","","","",0,0,0,0,false,false));
			scanButton.setText("סרוק");
		}
		return scanButton;
	}
	/**
	 * This method initializes countersButton
	 * 
	 * @return mataf.types.MatafButton
	 */
	private mataf.types.MatafButton getCountersButton() {
		if(countersButton == null) {
			countersButton = new mataf.types.MatafButton();
			countersButton.setBounds(175, 425, 80, 20);
			countersButton.setText("מונים");
		}
		return countersButton;
	}
	/**
	 * This method initializes shiftComboTextField
	 * 
	 * @return mataf.types.MatafComboTextField
	 */
	private mataf.types.MatafComboTextField getShiftComboTextField() {
		if(shiftComboTextField == null) {
			shiftComboTextField = new mataf.types.MatafComboTextField();
			shiftComboTextField.setBounds(691, 80, 14, 20);
			shiftComboTextField.setDescriptionLabel(getShiftDescLabel());
			//shiftComboTextField.setTableComboBox(getShiftTableComboBoxButton());
		}
		return shiftComboTextField;
	}
	/**
	 * This method initializes shiftDescLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getShiftDescLabel() {
		if(shiftDescLabel == null) {
			shiftDescLabel = new mataf.types.MatafLabel();
			shiftDescLabel.setBounds(614, 80, 75, 20);
			shiftDescLabel.setText("משוב");
		}
		return shiftDescLabel;
	}
	/**
	 * This method initializes shiftTableComboBoxButton
	 * 
	 * @return mataf.types.MatafTableComboBoxButton
	 */
	private mataf.types.MatafTableComboBoxButton getShiftTableComboBoxButton() {
		if(shiftTableComboBoxButton == null) {
			shiftTableComboBoxButton = new mataf.types.MatafTableComboBoxButton();
			shiftTableComboBoxButton.setBounds(537, 80, 58, 20);
		}
		return shiftTableComboBoxButton;
	}
	/**
	 * This method initializes fromHourLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getFromHourLabel() {
		if(fromHourLabel == null) {
			fromHourLabel = new mataf.types.MatafLabel();
			fromHourLabel.setBounds(691, 105, 14, 20);
			fromHourLabel.setText("מ-");
		}
		return fromHourLabel;
	}
	/**
	 * This method initializes fromHourTextField
	 * 
	 * @return mataf.types.MatafTextField
	 */
	private mataf.types.MatafTextField getFromHourTextField() {
		if(fromHourTextField == null) {
			fromHourTextField = new mataf.types.MatafTextField();
			fromHourTextField.setBounds(614, 105, 75, 20);
		}
		return fromHourTextField;
	}
	/**
	 * This method initializes untilHourLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getUntilHourLabel() {
		if(untilHourLabel == null) {
			untilHourLabel = new mataf.types.MatafLabel();
			untilHourLabel.setBounds(589, 105, 24, 20);
			untilHourLabel.setText("עד-");
		}
		return untilHourLabel;
	}
	/**
	 * This method initializes untilHourTextField
	 * 
	 * @return mataf.types.MatafTextField
	 */
	private mataf.types.MatafTextField getUntilHourTextField() {
		if(untilHourTextField == null) {
			untilHourTextField = new mataf.types.MatafTextField();
			untilHourTextField.setBounds(511, 105, 75, 19);
		}
		return untilHourTextField;
	}
	/**
	 * This method initializes workstationTextField
	 * 
	 * @return mataf.types.MatafTextField
	 */
	private mataf.types.MatafTextField getWorkstationTextField() {
		if(workstationTextField == null) {
			workstationTextField = new mataf.types.MatafTextField();
			workstationTextField.setBounds(673, 130, 32, 20);
		}
		return workstationTextField;
	}
	/**
	 * This method initializes workstationDescMatafLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getWorkstationDescMatafLabel() {
		if(workstationDescMatafLabel == null) {
			workstationDescMatafLabel = new mataf.types.MatafLabel();
			workstationDescMatafLabel.setBounds(595, 130, 75, 20);
			workstationDescMatafLabel.setText("משוב");
		}
		return workstationDescMatafLabel;
	}
	/**
	 * This method initializes clerkTextField
	 * 
	 * @return mataf.types.MatafTextField
	 */
	private mataf.types.MatafTextField getClerkTextField() {
		if(clerkTextField == null) {
			clerkTextField = new mataf.types.MatafTextField();
			clerkTextField.setBounds(626, 155, 79, 20);
		}
		return clerkTextField;
	}
	/**
	 * This method initializes clerkDescLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getClerkDescLabel() {
		if(clerkDescLabel == null) {
			clerkDescLabel = new mataf.types.MatafLabel();
			clerkDescLabel.setBounds(554, 155, 70, 20);
			clerkDescLabel.setText("משוב");
		}
		return clerkDescLabel;
	}
	/**
	 * This method initializes neededActionsComboTextField
	 * 
	 * @return mataf.types.MatafComboTextField
	 */
	private mataf.types.MatafComboTextField getNeededActionsComboTextField() {
		if(neededActionsComboTextField == null) {
			neededActionsComboTextField = new mataf.types.MatafComboTextField();
			neededActionsComboTextField.setBounds(642, 205, 14, 20);
		}
		return neededActionsComboTextField;
	}
	/**
	 * This method initializes neededActionsTableComboBoxButton
	 * 
	 * @return mataf.types.MatafTableComboBoxButton
	 */
	private mataf.types.MatafTableComboBoxButton getNeededActionsTableComboBoxButton() {
		if(neededActionsTableComboBoxButton == null) {
			neededActionsTableComboBoxButton = new mataf.types.MatafTableComboBoxButton();
			neededActionsTableComboBoxButton.setBounds(581, 205, 58, 20);
		}
		return neededActionsTableComboBoxButton;
	}
	/**
	 * This method initializes neededActionsDescLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getNeededActionsDescLabel() {
		if(neededActionsDescLabel == null) {
			neededActionsDescLabel = new mataf.types.MatafLabel();
			neededActionsDescLabel.setBounds(484, 205, 92, 20);
			neededActionsDescLabel.setText("משוב");
		}
		return neededActionsDescLabel;
	}
	/**
	 * This method initializes transmittedActionsComboTextField
	 * 
	 * @return mataf.types.MatafComboTextField
	 */
	private mataf.types.MatafComboTextField getTransmittedActionsComboTextField() {
		if(transmittedActionsComboTextField == null) {
			transmittedActionsComboTextField = new mataf.types.MatafComboTextField();
			transmittedActionsComboTextField.setBounds(642, 230, 14, 20);
		}
		return transmittedActionsComboTextField;
	}
	/**
	 * This method initializes transmittedActionsTableComboBoxButton
	 * 
	 * @return mataf.types.MatafTableComboBoxButton
	 */
	private mataf.types.MatafTableComboBoxButton getTransmittedActionsTableComboBoxButton() {
		if(transmittedActionsTableComboBoxButton == null) {
			transmittedActionsTableComboBoxButton = new mataf.types.MatafTableComboBoxButton();
			transmittedActionsTableComboBoxButton.setBounds(581, 230, 58, 20);
		}
		return transmittedActionsTableComboBoxButton;
	}
	/**
	 * This method initializes transmittedActionsLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getTransmittedActionsLabel() {
		if(transmittedActionsLabel == null) {
			transmittedActionsLabel = new mataf.types.MatafLabel();
			transmittedActionsLabel.setBounds(484, 230, 92, 20);
			transmittedActionsLabel.setText("משוב");
		}
		return transmittedActionsLabel;
	}
	/**
	 * This method initializes managerApprovalComboTextField
	 * 
	 * @return mataf.types.MatafComboTextField
	 */
	private mataf.types.MatafComboTextField getManagerApprovalComboTextField() {
		if(managerApprovalComboTextField == null) {
			managerApprovalComboTextField = new mataf.types.MatafComboTextField();
			managerApprovalComboTextField.setBounds(642, 255, 14, 20);
		}
		return managerApprovalComboTextField;
	}
	/**
	 * This method initializes managerApprovalTableComboBoxButton
	 * 
	 * @return mataf.types.MatafTableComboBoxButton
	 */
	private mataf.types.MatafTableComboBoxButton getManagerApprovalTableComboBoxButton() {
		if(managerApprovalTableComboBoxButton == null) {
			managerApprovalTableComboBoxButton = new mataf.types.MatafTableComboBoxButton();
			managerApprovalTableComboBoxButton.setBounds(581, 255, 58, 20);
		}
		return managerApprovalTableComboBoxButton;
	}
	/**
	 * This method initializes managerApprovalDescLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getManagerApprovalDescLabel() {
		if(managerApprovalDescLabel == null) {
			managerApprovalDescLabel = new mataf.types.MatafLabel();
			managerApprovalDescLabel.setBounds(484, 255, 92, 20);
			managerApprovalDescLabel.setText("משוב");
		}
		return managerApprovalDescLabel;
	}
	/**
	 * This method initializes managerComboTextField
	 * 
	 * @return mataf.types.MatafComboTextField
	 */
	private mataf.types.MatafComboTextField getManagerComboTextField() {
		if(managerComboTextField == null) {
			managerComboTextField = new mataf.types.MatafComboTextField();
			managerComboTextField.setBounds(642, 280, 14, 20);
		}
		return managerComboTextField;
	}
	/**
	 * This method initializes managerTableComboBoxButton
	 * 
	 * @return mataf.types.MatafTableComboBoxButton
	 */
	private mataf.types.MatafTableComboBoxButton getManagerTableComboBoxButton() {
		if(managerTableComboBoxButton == null) {
			managerTableComboBoxButton = new mataf.types.MatafTableComboBoxButton();
			managerTableComboBoxButton.setBounds(581, 280, 58, 20);
		}
		return managerTableComboBoxButton;
	}
	/**
	 * This method initializes managerDescLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getManagerDescLabel() {
		if(managerDescLabel == null) {
			managerDescLabel = new mataf.types.MatafLabel();
			managerDescLabel.setBounds(484, 280, 92, 20);
			managerDescLabel.setText("משוב");
		}
		return managerDescLabel;
	}
	/**
	 * This method initializes secondaryOpTextField
	 * 
	 * @return mataf.types.MatafTextField
	 */
	private mataf.types.MatafTextField getSecondaryOpTextField() {
		if(secondaryOpTextField == null) {
			secondaryOpTextField = new mataf.types.MatafTextField();
			secondaryOpTextField.setBounds(581, 305, 75, 19);
		}
		return secondaryOpTextField;
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="16,113"
