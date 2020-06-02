package mataf.srika.panels;

import java.awt.Dimension;
import java.awt.LayoutManager;

import mataf.types.MatafButton;
import mataf.types.MatafEmbeddedPanel;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (15/02/2004 15:03:21).  
 */
public class SrikaNavigationInnerPanel extends MatafEmbeddedPanel
{

	private mataf.types.MatafEmbeddedPanel navigationButtonsSouthPanel = null;
	private mataf.types.MatafButton exitButton = null;
	private mataf.types.MatafButton printButton = null;
	private mataf.types.MatafButton countersButton = null;
	private mataf.types.MatafButton detailsButton = null;
	private mataf.types.MatafButton nextButton = null;
	private mataf.types.MatafButton nextDealButton = null;
	private mataf.types.MatafButton previousDealButton = null;
	/**
	 * @param layout
	 */
	public SrikaNavigationInnerPanel(LayoutManager layout)
	{
		super(layout);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public SrikaNavigationInnerPanel(LayoutManager arg0, boolean arg1)
	{
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public SrikaNavigationInnerPanel(boolean arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public SrikaNavigationInnerPanel()
	{
		super();
			initialize();
	// TODO Auto-generated constructor stub
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.setLayout(new java.awt.BorderLayout());
        this.add(getNavigationButtonsSouthPanel(), java.awt.BorderLayout.SOUTH);
        this.setBorder(null);
        this.setBounds(0, 0, 780, 450);
			
	}
	/**
	 * This method initializes navigationButtonsSouthPanel
	 * 
	 * @return mataf.types.MatafEmbeddedPanel
	 */
	private mataf.types.MatafEmbeddedPanel getNavigationButtonsSouthPanel() {
		if(navigationButtonsSouthPanel == null) {
			navigationButtonsSouthPanel = new mataf.types.MatafEmbeddedPanel();
			navigationButtonsSouthPanel.setBorder(null);
			navigationButtonsSouthPanel.add(getExitButton(), null);
			navigationButtonsSouthPanel.add(getPrintButton(), null);
			navigationButtonsSouthPanel.add(getCountersButton(), null);
			navigationButtonsSouthPanel.add(getDetailsButton(), null);
			navigationButtonsSouthPanel.add(getNextButton(), null);
			navigationButtonsSouthPanel.add(getNextDealButton(), null);
			navigationButtonsSouthPanel.add(getPreviousDealButton(), null);
			navigationButtonsSouthPanel.setPreferredSize(new Dimension(780,30));
		}
		return navigationButtonsSouthPanel;
	}
	/**
	 * This method initializes exitButton
	 * 
	 * @return mataf.types.MatafButton
	 */
	private mataf.types.MatafButton getExitButton() {
		if(exitButton == null) {
			exitButton = new mataf.types.MatafButton();
			exitButton.setBounds(10, 5, 50, 20);
			exitButton.setType(MatafButton.CLOSE_VIEW);
			exitButton.setText("צא");
		}
		return exitButton;
	}
	/**
	 * This method initializes printButton
	 * 
	 * @return mataf.types.MatafButton
	 */
	private mataf.types.MatafButton getPrintButton() {
		if(printButton == null) {
			printButton = new mataf.types.MatafButton();
			printButton.setBounds(80, 5, 80, 20);
			printButton.setText("הדפסה");
		}
		return printButton;
	}
	/**
	 * This method initializes countersButton
	 * 
	 * @return mataf.types.MatafButton
	 */
	private mataf.types.MatafButton getCountersButton() {
		if(countersButton == null) {
			countersButton = new mataf.types.MatafButton();
			countersButton.setBounds(170, 5, 70, 20);
			countersButton.setText("מונים");
		}
		return countersButton;
	}
	/**
	 * This method initializes detailsButton
	 * 
	 * @return mataf.types.MatafButton
	 */
	private mataf.types.MatafButton getDetailsButton() {
		if(detailsButton == null) {
			detailsButton = new mataf.types.MatafButton();
			detailsButton.setBounds(250, 5, 110, 20);
			detailsButton.setText("מסך הבא >");
		}
		return detailsButton;
	}
	/**
	 * This method initializes nextButton
	 * 
	 * @return mataf.types.MatafButton
	 */
	private mataf.types.MatafButton getNextButton() {
		if(nextButton == null) {
			nextButton = new mataf.types.MatafButton();
			nextButton.setBounds(370, 5, 110, 20);
			nextButton.setText("< מסך קודם");
		}
		return nextButton;
	}
	/**
	 * This method initializes nextDealButton
	 * 
	 * @return mataf.types.MatafButton
	 */
	private mataf.types.MatafButton getNextDealButton() {
		if(nextDealButton == null) {
			nextDealButton = new mataf.types.MatafButton();
			nextDealButton.setBounds(490, 5, 130, 20);
			nextDealButton.setText("עסקה הבאה >>");
		}
		return nextDealButton;
	}
	/**
	 * This method initializes previousDealButton
	 * 
	 * @return mataf.types.MatafButton
	 */
	private mataf.types.MatafButton getPreviousDealButton() {
		if(previousDealButton == null) {
			previousDealButton = new mataf.types.MatafButton();
			previousDealButton.setBounds(630, 5, 130, 20);
			previousDealButton.setText("<< עסקה קודמת");
		}
		return previousDealButton;
	}
}
