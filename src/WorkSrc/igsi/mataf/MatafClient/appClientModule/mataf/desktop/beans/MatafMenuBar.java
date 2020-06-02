package mataf.desktop.beans;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.MenuElement;
import javax.swing.MenuSelectionManager;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import mataf.dse.appl.OpenDesktop;
import mataf.logger.GLogger;
import mataf.services.proxy.RTCommands;

import Excel.Window;

import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DSEOperation;
import com.ibm.dse.desktop.Desktop;
import com.ibm.dse.desktop.DesktopComponent;
import com.ibm.dse.gui.SpMenuBar;

/**
 * This class adds the following functionality over the MenuBar :
 * 
 * It holds a hashtable of all of its components and their
 * corresponding task names. This is done to enable direct 
 * access to every menu item.
 * 
 * It keeps the path of menuItems that leads to the last selected MenuItem
 * to allow re-opening of the menu at the last selected item.
 * 
 * @author Eyal Ben Ze'ev & Nati Dykstein.
 * 
 */
public class MatafMenuBar extends SpMenuBar implements DesktopComponent
{
	private static final Color BG_COLOR = new Color(115,154,192);
	//private static final Color FG_COLOR = Color.white;
	
	private String id;

	/** Holds all of this menuBar's menu items.*/
	private static Hashtable		allMenuItems = new Hashtable();

	/** Array of menuItems that hold the path to the selected menuItem.*/
	private static MenuElement[] 	jPath;
	
	/** Used to reference the instance of this MatafMenuBar by static methods.*/
	private static MatafMenuBar	refForStatic;
	
	/** The Default MenuSelectionManager used to track the selection path.*/
	private static MenuSelectionManager selectionManager;
	
	/** When true, we will ignore any changes to the MenuSelectionManager.*/
	private static boolean automaticOpening;
	
	/** Used for special treatment of the tellerMenu.*/
	private static MatafMenu tellerMenu;
	
	/** Manage the personal menu **/
	private static PersonalMenuManager personalMenuManager;
	
	/** True if application has just finished loading. */
	private static boolean firstTimeEnablingTheMenus = true;
	
	/** The glass pane of the desktop. */
	private static JComponent glassPane;
	
	
	static 
	{
		String[] keys = new String[] {"ESCAPE","cancel",
									  "DOWN","selectNext",
									  "KP_DOWN","selectNext",
									  "UP","selectPrevious",
									  "KP_UP","selectPrevious",
									  "LEFT","selectChild",
									  "KP_LEFT","selectChild",
									  "RIGHT","selectParent",
									  "KP_RIGHT","selectParent",
									  "ENTER","return",
									  "SPACE","return"};
									  
		UIManager.put("PopupMenu.selectedWindowInputMapBindings",keys);
	}

	
	public MatafMenuBar()
	{
		refForStatic = this;
		selectionManager = MenuSelectionManager.defaultManager();
		selectionManager.addChangeListener(new ChangeListener()
		{		
			/**
			 * Build the menu path as it opens.
			 * (Cannot use the MenuSelectionManager's selectionPath
			 * because it closes after selecting an item)
			 */
			public void stateChanged(ChangeEvent e) 
			{
				// Igonre tracking of the automatic opening.
				if(automaticOpening)
					return;
				MenuElement[] path = selectionManager.getSelectedPath();
				if(path.length==0)
					return;
				jPath = path;
			}
		});
		
		bindKeyboardActions();
/*		
		Toolkit.getDefaultToolkit().addAWTEventListener(
			new AWTEventListener() 
			{
				public void eventDispatched(AWTEvent event) 
				{
					System.out.println("EVENT DISPATCHER : "+event);
					// Cast to KeyEvent.
					KeyEvent keyEvent = (KeyEvent)event;
					// Operate only on KEY_PRESSED events.
					if(keyEvent.getID()!=KeyEvent.KEY_PRESSED)
						return;
					// Get the reference to the rootpane.
					JRootPane root = Desktop.getDesktop().getRootPane();
					// Get it's action map.
					ActionMap map = root.getActionMap();
					// Extract the pressed key as String.
					String keyPressed = ""+keyEvent.getKeyChar();
					System.out.println("KeyPressed="+keyPressed);
					// Perform the action specified in the action map.
					map.get(keyPressed).actionPerformed(
						new ActionEvent(this,0,"Opearte Shortcut "+keyPressed));
				}
			},AWTEvent.KEY_EVENT_MASK);*/
			
		setBackground(BG_COLOR);
	}
	
	/**
	 * Returns the menu by its text,null if no menu with the 
	 * specified text could be found.
	 * 
	 * @param menuText - The text of the desired menu.
	 * @return MatafMenu - The first menu that has the same text as menuText.
	 */
	public static MatafMenu getMenuByText(String menuText)
	{
		// Enumerate through menu items to find the desired menu.
		for(Enumeration e = allMenuItems.elements();e.hasMoreElements();)
		{
			Object o = e.nextElement();
			if(o instanceof MatafMenu)
			{
		 		MatafMenu menu = (MatafMenu)o;
		 		if(menu.getText().equals(menuText))
			 		return menu;
			}
		}
		
		// The desired menu could not be found.
		return null;
	}
	
	/**
	 * Returns a reference to the teller menu component.
	 */
	public static MatafMenu getTellerMenu()
	{
		if(tellerMenu==null)
			tellerMenu = getMenuByText("טלר");
		return tellerMenu;
	}
	
	/**
	 * Returns a reference to the personalmenu menu component.
	 */
	public static MatafMenu getPersonalMenu()
	{
		return MatafMenuBar.getMenuByText("ת.אישי");
	}
	
	/**
	 * Disables menu re-opening after RT transaction ends.
	 * The disabling is valid for one transaction only !.
	 */
	public static void disableMenuReopening()
	{
		jPath=null;
	}
	
	/**
	 * Creates the additional keys functionality :
	 * - Pressing ESCAPE while no menu is opened will cause
	 *   the RT to exit it's nesting mode.
	 */
	private void bindKeyboardActions()
	{
		// Create the input map.
		InputMap im = getInputMap(WHEN_FOCUSED);
		im.put(KeyStroke.getKeyStroke("ESCAPE"), "exitNesting");		
		
		// Create the action map.
		ActionMap am = getActionMap();
		am.put("exitNesting", new AbstractAction() 
		{			
			public void actionPerformed(ActionEvent e) 
			{			
				OpenDesktop.asynchronicSend(RTCommands.RT_KEYBOARD_COMMANDS,
											"Key",
											""+KeyEvent.VK_ESCAPE);
			}
		});
	}

	public void setMenuSize(Object size) 
	{
		setSize(DesktopUtils.getComponentSize(size));
	}

	public void setBackgroundColor(Object bg) 
	{
		setForeground(BG_COLOR);
	}
	
	/**
	 * Set the ForegroundColor of the component from the desktop XML Definitions file
	 */
	public void setForegroundColor(Object obj)	
	{
		//setForeground(FG_COLOR);
	}

	/**
	 * Set the Font of the component from the desktop XML Definitions file
	 */
	public void setFont(Object obj)	
	{
		//((JMenuBar)this).setFont(DesktopUtils.setComponentFont(obj));
	}
	
	public void setName(Object obj) 
	{
    	super.setName((String)obj);
    }
    
    public void setId(Object id) 
    {
		this.id = (String)id;
	}
	
	/**
	 * Set the Component Orientation of the component from the desktop XML Definitions file
	 */
    public void setOrientation(Object obj) 
    {
        String s1 = obj.toString();
        if(s1.equals("LEFT_TO_RIGHT"))
            setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        if(s1.equals("RIGHT_TO_LEFT"))
            setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    }
    
    public void add(Object item)
    {
		if (item instanceof MatafMenu)
		{
			// Add MatafMenu to the hashtable.
			addMenuItem((MatafMenu)item);
			super.add((MatafMenu)item);
		}
		else if (item instanceof Component)
			super.add((Component)item);
		else
			System.out.println("can't add " + item + " to menubar");
    }
    
    /** 
     * Adds a menu to the hashtable.
     */
    public static void addMenuItem(MatafMenuItem menuItem)
    {
		if(menuItem.getTaskName()!=null)
			allMenuItems.put(menuItem.getTaskName(),menuItem);
    }
    
    /** 
     * Adds a menu item to the hashtable.
     */
    public static void addMenuItem(MatafMenu menu)
    {
    	if(menu.getTaskName()!=null)
    		allMenuItems.put(menu.getTaskName(),menu);
    }
    
    /**
     * Retruns a MatafMenuItem by its task name.
     */
    public static JMenuItem getMenuItemByTaskName(String taskName)
	{
		return (JMenuItem)allMenuItems.get(taskName);
	}
	
	/**
	 * Returns a reference to the hashtable of all the menu items.
	 */
	public static Hashtable getMenuItemsHashtable()
	{
		return allMenuItems;
	}
	
	/**
	 * 'Capture' the path to the selected MenuItem.
	 */
	public static void setPath()
	{
		jPath = MenuSelectionManager.defaultManager().getSelectedPath();
	}
	
	/** 
	 * Disables the application menu or Enable it and Grab the focus.
	 */
	public static void setMenuEnabled(boolean enable)
	{
		setMenuEnabled(enable, true);
	}
	
	/**
	 * Here we perform all the operations we need to do
	 * just after the system has finished loading.
	 *
	 */
	private static void performPostStartupActions()
	{	
		try {
			// loading tables 
			DSEClientOperation clientOp = (DSEClientOperation) DSEOperation.readObject("tablesLoaderClientOp");
			clientOp.execute();
			
			// Create the personal menu manager.
			personalMenuManager = new PersonalMenuManager();
			personalMenuManager.buildPersonalMenu(MatafWorkingArea.getActiveClientView().getContext(), 
												  (JMenuBar)getInstance());
		} catch(Exception ex) {
			GLogger.error(MatafMenuBar.class, null, null, ex, false);
		}
	}
	
	/**
	 * Sets the glass pane with its preferred size and allow it
	 * to consume mouse events.
	 */
	private static void configureGlassPane()
	{
		// Get the glasspane of the desktop.
		glassPane = (JComponent)Desktop.getDesktop().getRootPane().getGlassPane();
		
		glassPane.setPreferredSize(Desktop.getFrame().getSize());

		MouseAdapter ma = new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				// Blocking mouse events.
			}
		};
		
		glassPane.addMouseListener(ma);
	}
	
	/** 
	 * Enables/Disables the application menu with the option to grab
	 * the focus.
	 */
	public static void setMenuEnabled(boolean enable, boolean getFocusBack)
	{
		// If this is the first time we're enabling the menus
		// it means that the application has just finished loading.
		if(enable && firstTimeEnablingTheMenus)
		{
			performPostStartupActions();
			firstTimeEnablingTheMenus = false;
		}
		
		// Change cursor according to our state.
		int cursor = enable ? Cursor.DEFAULT_CURSOR : Cursor.WAIT_CURSOR;
		Desktop.getFrame().setCursor(Cursor.getPredefinedCursor(cursor));
		
		// Configure the glass pane if neccesary.
		if(glassPane==null)
			configureGlassPane();		
		
		// Show/Hide it.
		glassPane.setVisible(!enable);
		
		// Apply enabling status to all menu items.
		for(Enumeration e = allMenuItems.elements();e.hasMoreElements();)
		{
			Component c = (Component)e.nextElement();
			if(c instanceof MatafMenu)
				c.setEnabled(enable);
		}
		
		if(enable)
		{
			OpenDesktop.updateProgress(OpenDesktop.RT_LOAD_COMPLETED);
			
			// Update menu states.
			recursiveDisabling(refForStatic);
			
			// Re-open the menu on last selected menu item.
			ReopenMenu();
			
			if(getFocusBack)
			{
				// Move the java application window to the front.
				Desktop.getFrame().toFront();
			}
		}
	}
	
	/**
	 * Re-open menu on last selected option.
	 */
	private static void ReopenMenu()
	{	
		// Make it Thread-safe.
		SwingUtilities.invokeLater(new Runnable()
		{			
			public void run()
			{				
				// If path is empty return.
				if((jPath==null) || (jPath.length==0))
					return;
				
				try
				{
					// Indicates we are opening the menu automatically.
					automaticOpening = true;
					
					// Open all the sub-menus.
					for(int i=0;i<jPath.length-1;i++)
						if(jPath[i] instanceof JMenuItem)
						{
							((AbstractButton)jPath[i]).doClick();
							//GLogger.debug(i+"."+jPath[i]);
						}
				
					if(jPath[jPath.length-1] instanceof JMenuItem)
					{
						// Mark the item as selected by simulating a mouse entered
						// event on it.
						JMenuItem selectedItem = (JMenuItem)jPath[jPath.length-1];
						selectedItem.dispatchEvent(new MouseEvent(  MatafMenuBar.refForStatic,
															MouseEvent.MOUSE_ENTERED,
															0,
															0,
															0,
															0,
															0,
															false));
					}
				}
				finally
				{
					// Automatic opening finished.
					automaticOpening = false;					
				}
			}
		});
	}
	
	/**
	 * Method recursively determines the state of a sub-menu :
	 * If all of its sub-elements are disabled - disable the menu,
	 * if not - enable the menu.
	 */
	private static boolean recursiveDisabling(MenuElement m)
	{
		// Stopping rule.
		if(m instanceof MatafMenuItem)
			return ((Component)m).isEnabled();
	
		MenuElement[] elements = m.getSubElements();
		boolean enabled = false;
		for(int i=0;i<elements.length;i++)
			enabled |= recursiveDisabling(elements[i]);
		
		((Component)m).setEnabled(enabled);
		return enabled;
	}
	
	/**
	 * Returns a reference to the one instance of MenuBar that exists 
	 * in the application.
	 */
	public static MatafMenuBar getInstance()
	{
		return refForStatic;
	}
	
	/**
	 * Adds a new menu item to the personal menu.
	 */
	public void addMenuItemToPersonalMenu(MatafMenuItem newItem)
	{
		try {
			getPersonalMenuManager().addMenuItemToPersonalMenu(
										MatafWorkingArea.getActiveClientView().getContext(),
										(JMenuBar)this, 
										(JMenuItem)newItem,
										newItem.getTaskName(), 
										true);
		} catch(Exception ex) {
			String errMsg = "Could'nt add new menuItem '"+newItem.getText()+"' to the personal menu";
			GLogger.error(this.getClass(), null, errMsg, ex, false);
		}
	}
	
	/**
	 * Remove a new menu item to the personal menu.
	 */
	public void removeMenuItemFromPersonalMenu(MatafMenuItem itemToRemove)
	{
		try {
			getPersonalMenuManager().removeMenuItemFromPersonalMenu(
										MatafWorkingArea.getActiveClientView().getContext(),
										this, 
										itemToRemove.getTaskName());
		} catch(Exception ex) {
			String errMsg = "Could'nt remove menuItem '"+itemToRemove.getText()+"' from the personal menu";
			GLogger.error(this.getClass(), null, errMsg, ex, false);
		}
	}

	/**
	 * Returns the personalMenuManager.
	 * @return PersonalMenuManager
	 */
	public static PersonalMenuManager getPersonalMenuManager() {
		return personalMenuManager;
	}

}