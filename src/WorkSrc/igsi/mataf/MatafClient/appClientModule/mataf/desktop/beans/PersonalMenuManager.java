package mataf.desktop.beans;

import java.awt.Component;
import java.awt.PopupMenu;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.NoSuchElementException;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import mataf.logger.GLogger;
import mataf.operations.JDBCClientOp;
import mataf.services.JdbcConnectionService;
import mataf.types.MatafSeparator;
import mataf.utils.MatafSqlQueryUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.Hashtable;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.Service;
import com.ibm.dse.base.Vector;
import com.ibm.dse.desktop.Separator;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class PersonalMenuManager {
	
	/** Holds the relative path of personalMenu.xml */
	public static final String PERSONAL_MENU_DEF_FILE_PATH = "";

	/** Holds the text of the personalMenu in the menuBar. */
	public static final String PERSONAL_MENU_TEXT = "ת.אישי";
	
	/**
	 * Constructor for PersonalMenuManager.
	 */
	public PersonalMenuManager() {
		super();
	}
	
	/**
	 * Build the personal menu from the XML and from the database.
	 * 
	 * @param
	 * 		ctx  the context to get the clerk id from and the JdbcConnectionService
	 * @param
	 * 		menuBar  the menuBar of the personalMenu
	 */
	public void buildPersonalMenu(Context ctx, JMenuBar menuBar) {
		
		// add the default menu items from xml
//		buildPersonalMenuFromXml();
		
		// add separator between the default items in the personal menu
		// to the once that the user added
		JMenu personalMenu = getPersonalMenu(menuBar);
		personalMenu.add(new MatafSeparator());
		
		// add the menu items that the user added from the database 
		buildPersonalMenuFromDb(ctx, menuBar);		
	}
	
	/**
	 * Build the base menu according to the clerk type from personalMenu.xml.
	 * If there is any problem building the base personalMenu from the xml,
	 * then writting an error to the log.
	 */
	private void buildPersonalMenuFromXml() {
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(PERSONAL_MENU_DEF_FILE_PATH);
			Element rootElement = document.getDocumentElement();
			
			// TODO - build the personal menu from the xml
		} catch(ParserConfigurationException ex) {
		} catch(SAXException ex) {
		} catch(IOException ex) {
		}
	}
	
	/**
	 * Build the personal menu according to the actions the clerk added.
	 * If Exception has been thrown while tring to build the personalMenu
	 * from the database, then write an error to the GLogger.
	 * 
	 * @param
	 * 		ctx  the context to get the clerk id from and the JdbcConnectionService
	 * @param
	 * 		menuBar  the menuBar of the personalMenu
	 */
	private void buildPersonalMenuFromDb(Context ctx, JMenuBar menuBar) {
		try {
			IndexedCollection personalMenuTrxList = (IndexedCollection) ctx.getElementAt("personalMenuData.personalMenuTrxList");
			
			// get the personal menu to add the menuItems
			JMenu personalMenu = getPersonalMenu(menuBar);
			
			// move over the transactions id's retrieved from the database
			// and add the relevant menuItems.
			KeyedCollection currentKcoll = null;
			String currentTrxId = null;
			for(int counter=0 ; counter<personalMenuTrxList.size() ; counter++) {
				currentKcoll = (KeyedCollection) personalMenuTrxList.getElementAt(counter);
				currentTrxId = (String) currentKcoll.getValueAt("trxId");
				JMenuItem menuItemToAdd = findMenuItemInMenuBar(menuBar, currentTrxId);
				personalMenu.add(menuItemToAdd);
			}
			
		} catch(Exception ex) {
			String errMsg = "Couldn't set personal menu from table named 'PERSONAL_MENU'in matafdb";
			GLogger.error(this.getClass(), null, errMsg, ex, false);
		}
	}
	
	/**
	 * Find the menuItem in the menuBar according to the transactionId.
	 * 
	 * @return
	 * 		the menuItem according to the transactionId sent as parameter
	 * 		if it can be found in the menuBar.
	 * @throws
	 * 		NoSuchElementException - if the MenuItem could'
	 */
	private JMenuItem findMenuItemInMenuBar(JMenuBar menuBar, String transactionId) {
		JMenuItem selectedMenuItem = null;
		
		// move over the menuBar to find the menuItem according the transactionId
		for(int counter=0 ; counter<menuBar.getMenuCount() ; counter++) {
			selectedMenuItem = findMenuItemInMenu(menuBar.getMenu(counter), transactionId);
			if(selectedMenuItem!=null)
				return selectedMenuItem;
		}
		
		throw new NoSuchElementException("Couldn't find transaction named "+transactionId+" in the MenuBar");
	}
	
	/**
	 * This recursive method move over a menu to find a menu item according
	 * the transactionId.
	 * 
	 * @param 
	 * 		menuToLookInto  the menu that this method is looking in to find the menu
	 * 						 item with the sepcific transactionId
	 * @param 
	 * 		transactionId  the transactionId of the menuItem to be found
	 * @return
	 * 		the wanted MenuItem in the menuToLookInto according to the transactionId
	 * 		if it exist in the menuItem. if the menuItem can't be found then return null.
	 */
	private JMenuItem findMenuItemInMenu(JMenu menuToLookInto, String transactionId) {
		Component currentMenuComponent = null;
		
		for(int counter=0 ; counter<menuToLookInto.getMenuComponentCount() ; counter++) {
			currentMenuComponent = menuToLookInto.getMenuComponent(counter);
			
			if(currentMenuComponent instanceof JMenu) {
				// if the current component is a JMenu type then call this method recursivlly
				JMenuItem returnedMenuItem = findMenuItemInMenu((JMenu) currentMenuComponent, transactionId);
				if(returnedMenuItem!=null)
					return returnedMenuItem;
			} else if(currentMenuComponent instanceof JMenuItem) {
				// if the text in the current menuItem is identical to the transactionId
				// then return this JMenuItem
				if(((MatafMenuItem) currentMenuComponent).getTaskName().equals(transactionId))
					return (JMenuItem) currentMenuComponent;
			}
		}
		
		return null;
	}
	
	/**
	 * Adding menuItem to the personalMenu located in the menuBar.
	 * 
	 * @param
	 * 		ctx  the context of the client
	 * @param
	 * 		menuBar  the menuBar to add the menuItem to
	 * @param
	 * 		menuItem  to menu item to be added to the menuBar
	 * @param
	 * 		taskName  the task name of the menu item to add
	 * @param
	 * 		addToTable  true if this menuItem should be added to the PERSONAL_MENU table
	 * @throws
	 * 		DSEObjectNotFoundException  if could'nt find a specific data in the context
	 * @throws
	 * 		SQLException  if there is a problem to add the transactionId to the database
	 * @throws
	 * 		Exception  if there is a problem to add the transactionId to the database (that is NOT SQLException)
	 */
	public void addMenuItemToPersonalMenu(Context ctx, 
										   JMenuBar menuBar, 
										   JMenuItem menuItem, 
										   String taskName, 
										   boolean addToTable) 
											throws DSEObjectNotFoundException, SQLException, Exception {
		
		// get the personalMenu from the menuBar
		JMenu personalMenu = getPersonalMenu(menuBar);
		
		// If was disabled(when empty), enable it.
		if(!personalMenu.isEnabled())
			personalMenu.setEnabled(true);
					
		personalMenu.add(menuItem);
		
		// adding the menuItem to the personal menu in the database
		ctx.setValueAt("personalMenuData.selectedTaskName", taskName);
		if(addToTable) {
			DSEClientOperation clientOp = 
						(DSEClientOperation) DSEClientOperation.readObject("addTrxToPersonalMenuSlClientOp");
			clientOp.execute();
		}
	}
	
	/**
	 * Removing menuItem from the personalMenu located in the menuBar.
	 * 
	 * @param
	 * 		ctx  the context of the client
	 * @param
	 * 		menuBar  the menuBar to remove the menuItem from
	 * @param
	 * 		taskName  the task name of the menu item to add
	 * @throws
	 * 		DSEObjectNotFoundException  if could'nt find a specific data in the context
	 * @throws
	 * 		SQLException  if there is a problem to add the transactionId to the database
	 * @throws
	 * 		NoSuchElementException  if the menu item to be remove could not be found in the personal menu
	 * @throws
	 * 		Exception  if there is a problem to add the transactionId to the database (that is NOT SQLException)
	 */
	public void removeMenuItemFromPersonalMenu(Context ctx, 
										   JMenuBar menuBar, 
										   String taskName) 
											throws DSEObjectNotFoundException, 
													SQLException,
													Exception {
		
		// get the personalMenu from the menuBar
		JMenu personalMenu = getPersonalMenu(menuBar);
		
		if(isRemoveableMenuItem(personalMenu, taskName)) {
			
			removeMenuItemByTaskName(personalMenu, taskName);
			
			// adding the menuItem to the personal menu in the database
			ctx.setValueAt("personalMenuData.selectedTaskName", taskName);
			DSEClientOperation clientOp = 
							(DSEClientOperation) DSEClientOperation.readObject("removeTrxFromPersonalMenuSlClientOp");
			clientOp.execute();
		}
	}
	
	/**
	 * Return the position of menu item by the task name sent as parameter
	 * @return
	 * 		the position of the menu item by the task name sent as parameter
	 * @param
	 * 		personalMenu  the menu to look the menuItem in
	 * @param
	 * 		taskName  the task name of the menu item to look for
	 */ 		
	private void removeMenuItemByTaskName(JMenu personalMenu, String taskName) {
		
		Component currentComponent = null;
		
		for(int counter=0 ; counter<personalMenu.getItemCount() ; counter++) {
			currentComponent = personalMenu.getItem(counter);
			if((currentComponent instanceof MatafMenuItem) &&
				(((MatafMenuItem) currentComponent).getTaskName().equals(taskName)))
			{
				personalMenu.remove(currentComponent);
			}
		}
	}
	
	/**
	 * Move over the personalMenu and check if the menuItem can be removed.
	 * A menuItem can be removed if he's after the separator, namely, this menuItem
	 * has been added by default and not by the user.
	 * @param
	 * 		personalMenu  the personal menu
	 * @param
	 * 		taskName  the name of the task to be remove
	 * @return
	 * 		true if this menu item has been added by default (before the separator in the personalMenu,
	 * 		otherwise, return false.
	 * @throws
	 * 		NoSuchElementException  if the menu item with the specified task name could'nt be found
	 */
	private boolean isRemoveableMenuItem(JMenu personalMenu, String taskName) {
		
		boolean isAfterSeparator = false;
		
		Component currentComponent = null;
		JPopupMenu personalMenuPopupMenu = personalMenu.getPopupMenu();
		
		for(int counter=0 ; counter<personalMenuPopupMenu.getComponentCount() ; counter++) {
			currentComponent = personalMenuPopupMenu.getComponent(counter);

			if(currentComponent instanceof JSeparator) {
				isAfterSeparator = true;
				continue;
			}
			
			if((currentComponent instanceof MatafMenuItem) &&
				(((MatafMenuItem) currentComponent).getTaskName().equals(taskName))) 
			{
				if(isAfterSeparator)
					return true;
				else
					return false;
			}				
		}
		
		throw new NoSuchElementException("Could'nt find menuItem with task name '"+taskName+"' to remove"+
											" from personal menu");
	}
		
	/**
	 * Move over the menuBar to find the personalMenu and return it.
	 * 
	 * @param
	 * 		menuBar  the menuBar to look in to find the personalMenu
	 * @return
	 * 		the personalMenu in the menuBar.
	 * @throws
	 * 		NoSuchElementException  if the personal menu could'nt be found in the menuBar
	 */
	private JMenu getPersonalMenu(JMenuBar menuBar) {
		JMenu currentMenu = null;
		
		for(int counter=0 ; counter<menuBar.getMenuCount() ; counter++) {
			currentMenu = menuBar.getMenu(counter);
			if(currentMenu.getText().equals(PERSONAL_MENU_TEXT))
				return currentMenu;
		}
		
		throw new NoSuchElementException("Couldn't find menu named '"
											+PERSONAL_MENU_TEXT+"' in tmenuBar of the desktop");
	}

}
