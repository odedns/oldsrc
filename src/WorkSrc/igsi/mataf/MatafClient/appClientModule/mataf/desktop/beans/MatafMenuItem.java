package mataf.desktop.beans;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

import mataf.logger.GLogger;
import mataf.operations.ActivateRTTransactionOp;
import mataf.utils.FontFactory;

import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.desktop.Desktop;
import com.ibm.dse.desktop.MenuItem;
import com.ibm.dse.gui.Settings;

/**
 * @author Eyal Ben Ze'ev & Nati Dykstein.
 *
 */
public class MatafMenuItem extends MenuItem 
								implements Cloneable
{
	private static final Color BG_COLOR = new Color(115, 154, 192);
	private static final Color FG_COLOR = Color.white;
	private static final Font FONT = FontFactory.createFont("Arial", Font.PLAIN, 12);

	private String longDescription;
	private String shortDescription;

	private boolean visualAction;

	/** Indicates whether the menuItem activates a RT transaction.*/
	private boolean remoteTransaction;

	/** Indicates the deployment date of the transaction that this menu Item represents.*/
	private String newAction;

	private int deploymentDay;
	private int deploymentMonth;
	private int deploymentYear;
	private StringTokenizer tokenizer;
	private String iconName;

	private static ResourceBundle bundle = ResourceBundle.getBundle("deploymentFile");

	static {
		UIManager.put("MenuItem.disabledForeground", Color.lightGray);
		UIManager.put("MenuItem.selectionBackground", new Color(32, 89, 144));
		UIManager.put("MenuItem.selectionForeground", Color.white);
	}

	/**
	 * Constructor for MatafMenuItem.
	 */
	public MatafMenuItem() {
		super();
		addMouseListener(new MenuItemMouseListener());
		initUI();
	}

	/**
	 * Constructor for MatafMenuItem with text.
	 * Used When creating an object not through the externalizer.
	 */
	public MatafMenuItem(String text) {
		super(text);
		initUI();
	}

	public void iconifyMenuItem() {
		if (checkNewIcon())
			setIconName("/images/icons/new.gif");
		else {
			// here we will handle default icons in the future
		}
	}
	/**
	 * @return
	 */
	private boolean checkNewIcon() {
		if ("no".equalsIgnoreCase(newAction) || newAction == null)
			return false;
		int duration = Integer.parseInt((String) Settings.getValueAt("newIconDuration"));
		String deploymentString = bundle.getString("deploymentDate");

		Calendar currentCalendar = Calendar.getInstance();

		Calendar deploymentCalendar = parseDate(deploymentString);
		deploymentCalendar.add(Calendar.DATE, duration);

		return deploymentCalendar.after(currentCalendar);
	}

	/**
	 * Init the component UI.
	 * Good mainly when creating an object not through the externalizer.
	 */
	private void initUI() {
		setForeground(FG_COLOR);
		setBackground(BG_COLOR);
		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		super.setFont(FONT);
	}

	/**
	 * Set the ForegroundColor of the component from the desktop XML Definitions file
	 * We're overriding the XML preferences.
	 */
	public void setForegroundColor(Object obj) {
		setForeground(FG_COLOR);
	}

	/**
	 * Set the BackgroundColor of the component from the desktop XML Definitions file
	 * We're overriding the XML preferences.
	 */
	public void setBackgroundColor(Object obj) {
		setBackground(BG_COLOR);
	}

	/**
	 * Set the Font of the component from the desktop XML Definitions file
	 * We're overriding the XML preferences.
	 */
	public void setFont(Object obj) {
		super.setFont(FONT);
	}

	/**
	 * Set the Component Orientation of the component from the desktop XML Definitions file
	 */
	public void setOrientation(Object obj) {
		String s1 = obj.toString();
		if (s1.equals("LEFT_TO_RIGHT"))
			setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		if (s1.equals("RIGHT_TO_LEFT"))
			setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	}

	public void setToolTipText(Object obj) {
		((JMenuItem) this).setToolTipText((String) obj);
	}

	public void setLabel(Object obj) {
		setText((String) obj);
	}

	public String getLongDescription() {
		return longDescription;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setLongDescription(Object obj) {
		longDescription = (String) obj;
	}

	public void setShortDescription(Object obj) {
		shortDescription = (String) obj;
	}

	/**
	 * Set the enable/disable property of the component from the desktop XML Definitions file
	 */
	public void setEnable(Object obj) {
		if (((String) obj).equals("false")) {
			super.setEnabled(false);
		} else {
			super.setEnabled(true);
		}

	}

	/**
	 * Set the Icon of the component from the desktop XML Definitions file
	 */
	public void setIcon(Object obj) {
		super.setIcon(new javax.swing.ImageIcon(getClass().getResource((String) obj)));
	}

	/**
	 * Returns the nonVisualAction.
	 * @return String
	 */
	public boolean isVisualAction() {
		return visualAction;
	}

	/**
	 * Sets the nonVisualAction.
	 * @param nonVisualAction The nonVisualAction to set
	 */
	public void setVisualAction(Object visualAction) {
		this.visualAction = Boolean.valueOf((String) visualAction).booleanValue();
	}

	/**
	 * Returns the remoteTransaction.
	 * @return String
	 */
	public boolean isRemoteTransaction() {
		return remoteTransaction;
	}

	/**
	 * Sets the remoteTransaction.
	 * @param remoteTransaction The remoteTransaction to set
	 */
	public void setRemoteTransaction(Object remoteTransaction) {
		this.remoteTransaction = Boolean.valueOf((String) remoteTransaction).booleanValue();
	}

	/**
	 * @see com.ibm.dse.desktop.MenuItem#actionPerformed(ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		try {
			if (!visualAction) {
				DSEClientOperation clientOp = (DSEClientOperation) DSEClientOperation.readObject(getOperation());

				// If we're activating a RT transaction.
				if (remoteTransaction)
					 ((ActivateRTTransactionOp) clientOp).execute(getTaskName());
				else // We're activating a non-visual operation.
					clientOp.execute();
			} else // We're activating a regular operation.
				{
				Desktop.getDesktop().openTask(this);
			}
		} catch (Exception ex) {
			GLogger.error(this.getClass(), null, "Error in actionPerformed of MatafMenuItem", ex, false);
		}
	}
	/* (non-Javadoc)
	* @see javax.swing.AbstractButton#setIcon(javax.swing.Icon)
	 */
	public void setIconName(Object obj) {
		iconName = (String) obj;
		ImageIcon icon = Desktop.getDesktop().getImageIcon(obj);
		super.setIcon(icon);
	}

	/**
		 * @return
		 */
	public String getIconName() {
		return iconName;
	}

	/**
	 * @param date
	 */
	private Calendar parseDate(String date) {
		tokenizer = new StringTokenizer(date, "/");
		deploymentDay = Integer.parseInt(tokenizer.nextToken());
		deploymentMonth = Integer.parseInt(tokenizer.nextToken());
		deploymentYear = Integer.parseInt(tokenizer.nextToken());
		Calendar cal = Calendar.getInstance();
		cal.set(deploymentYear, deploymentMonth, deploymentDay);
		return cal;
	}

	/**
	 * @return
	 */
	public String getNewAction() {
		return newAction;
	}

	/**
	 * @param string
	 */
	public void setNewAction(Object string) {
		newAction = (String) string;
	}
	
	/**
	 * Clone this menu item and send it for adding to the 
	 * personal menu.
	 * PENDING : Move code to copy constructor.
	 *
	 */
	public void addToPersonalMenu() 
	{
		MatafMenuItem clone = new MatafMenuItem();
		clone.setText(getText());
		clone.setTaskName(getTaskName());
		clone.setMnemonic(getMnemonic());
		clone.setOperation(getOperation());
		clone.setShortDescription(getShortDescription());
		clone.setLongDescription(getLongDescription());
		clone.setCode(getCode());
		clone.setExecuteOperation(getExecuteOperation().toString());
		clone.setVisualAction(Boolean.valueOf(isVisualAction()).toString());
		clone.setRemoteTransaction(Boolean.valueOf(isRemoteTransaction()).toString());
		
		MatafMenuBar.getInstance().addMenuItemToPersonalMenu(clone);
	}
	
	/**
	 * Removes this menu item from the personal menu.
	 */
	public void removeFromPersonalMenu() 
	{
		MatafMenuBar.getInstance().removeMenuItemFromPersonalMenu(this);
	}
}
