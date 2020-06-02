package mataf.desktop.beans;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;

import javax.swing.MenuSelectionManager;
import javax.swing.UIManager;

import mataf.utils.FontFactory;
import mataf.utils.HebrewUtilities;

import com.ibm.dse.desktop.Desktop;
import com.ibm.dse.desktop.DesktopComponent;
import com.ibm.dse.desktop.TaskLauncher;
import com.ibm.dse.gui.SpMenu;

/**
 * This class adds special handling for hebrew mnemonics and customizes
 * the menu colors.
 * 
 * @author Eyal Ben Ze'ev & Nati Dykstein.
 *
 */
public class MatafMenu extends SpMenu implements DesktopComponent
{
	private static final Color BG_COLOR = new Color(115,154,192);
	private static final Color FG_COLOR = Color.white;
	private static final Font  FONT		= FontFactory.createFont("Arial", Font.PLAIN,12);
	
	private String taskName;
	
	
	static
	{
		UIManager.put("Menu.disabledForeground", Color.lightGray);
		UIManager.put("Menu.selectionBackground", new Color(32,89,144));
		UIManager.put("Menu.selectionForeground", Color.white);
	}
	
	public MatafMenu()
	{
		super();
		initUI();
		// Enables mix of Lightweight and Heavyweight components.
		//getPopupMenu().setLightWeightPopupEnabled(false);
	}
	
	public MatafMenu(String text)
	{
		super(text);
		initUI();
	}
	
	private void initUI()
	{
		setForeground(FG_COLOR);
		setBackground(BG_COLOR);
		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		super.setFont(FONT);
	}
	
    
	public void setLabel(Object obj) {
       // setText(Desktop.getDesktop().resSampleResourceBundle.getString((String)obj));
       setText((String)obj) ;
    }

	public void setName(Object obj) {
        super.setName(obj.toString());
	}
	
	public void setToolTipText(Object obj) {
		super.setToolTipText((String)obj);
	}


	/**
	 * Set the ForegroundColor of the component from the desktop XML Definitions file
	 */
	public void setForegroundColor(Object obj) {		
		setForeground(FG_COLOR);
	}

	/**
	 * Set the BackgroundColor of the component from the desktop XML Definitions file
	 */
	public void setBackgroundColor(Object obj) {
		setBackground(BG_COLOR);
	}

	/**
	 * Set the Font of the component from the desktop XML Definitions file
	 */
	public void setFont(Object obj) {
  		super.setFont(DesktopUtils.setComponentFont(obj));
    }

	/**
	 * Set the Component Orientation of the component from the desktop XML Definitions file
	 */
    public void setOrientation(Object obj) {
       String s1 = obj.toString();
        if(s1.equals("LEFT_TO_RIGHT"))
            setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        if(s1.equals("RIGHT_TO_LEFT"))
            setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    }
    
    /**
	 * Returns the taskName.
	 * @return String
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * Sets the taskName.
	 * @param taskName The taskName to set
	 */
	public void setTaskName(Object taskName) 
	{
		this.taskName = (String)taskName;
	}		
	
	public void add(Object item) 
	{
       	if(item instanceof MatafMenu)
       	{       		
       		MatafMenu menu = (MatafMenu)item;
       		// Update menu items hashtable.
       		MatafMenuBar.addMenuItem(menu);
       		super.add(menu);
       	}
       	else
	       	if(item instanceof MatafMenuItem)
	       	{
	       		MatafMenuItem menuItem = (MatafMenuItem)item;
	       		menuItem.iconifyMenuItem();
	       		// Update menu items hashtable.
	       		MatafMenuBar.addMenuItem(menuItem);
	       		// Add the popup listener.
	       		//menuItem.addMouseListener(new MenuItemMouseListener());
    	   		super.add(menuItem);
	       	}
     		else
       			if(item instanceof TaskLauncher)
	       			Desktop.getDesktop().addTaskInfo((TaskLauncher)item);
    }
	
	/**
	 * Method maps the hebrew mnemonic read from the matafdesktop.xml to its 
	 * corresponding VK_XXX constant.
	 * It also computes the index of the char to underline as a mnemonic.
	 */
	public void setMnemonic(Object mnemonic) 
	{
		char m = ((String)mnemonic).toCharArray()[0];
		setMnemonic(HebrewUtilities.mapHebrewKeysToVKConstants(m));
		int pos = getText().length()-1;
		setDisplayedMnemonicIndex(pos - getText().indexOf(m));
	}	
	
	/**
	 * Updates the shortcut context.
	 */
	public void setSelected(boolean selected)
	{
		super.setSelected(selected);
		
		// If we're de-selecting this menu perform no action.
		if(!selected)
			return;
		
		if(Desktop.getDesktop()==null)
			return;
		
		// Clear the shortcut field.
		ShortcutAction.clear();
		
		String text = getText();
		int displayedIndex = getDisplayedMnemonicIndex();

		// No mnemonic was found for this menu.
		if(displayedIndex==-1)
			return;
			
		// Check that we are at a top-level menu.
		if(MenuSelectionManager.defaultManager().getSelectedPath().length>2)
			return;
			
		// Update the shortcut context char key on the desktop.
		int index = text.length()-1-displayedIndex;
		MatafWorkingArea.getActiveClientView().setShortcutContextTo(getText().toCharArray()[index]);
	}
}
