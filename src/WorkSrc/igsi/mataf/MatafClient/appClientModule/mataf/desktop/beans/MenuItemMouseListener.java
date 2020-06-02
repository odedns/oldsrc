package mataf.desktop.beans;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;
import javax.swing.MenuElement;



/**  
 *	This class listens to mouse events on the menu item and decides if they should
 *	trigger a context sensitive popup menu.
 *
 * @author Nati Dykstein. Creation Date : (18/05/2004 15:59:53).
 *
 */
public class MenuItemMouseListener extends MouseAdapter
{
	/** The context sensitive menu that opens on right-click.*/
	private static ContextSensitiveMenu csSubMenu = new ContextSensitiveMenu();
	
	/** The menu item on which we right-clicked.*/
	private static MatafMenuItem selectedItem;
	
	/** The index of the selected menu item inside its containing menu.*/
	private static int selectedItemIndex;
	
	/** The popup menu that contains the selected menu item.*/
	private static JPopupMenu parentPopup;
	
	
	
	public MenuItemMouseListener()
	{
		super();
	}
	
	/**
	 * Check if we triggered the opening of the context sensitive menu
	 * by pressing right click on the menu item.
	 */
	public void mousePressed(MouseEvent e) 
	{
		// React only to right-click.
		if(e.getButton()==MouseEvent.BUTTON3)
			openContextSensitiveMenu((MatafMenuItem)e.getComponent());
	}
	
	private void openContextSensitiveMenu(MatafMenuItem menuItem)
	{
		// Get the clicked-on menu item.
		selectedItem = menuItem;
		
		// Get its popup parent.
		parentPopup = (JPopupMenu)selectedItem.getParent();
		
		// Get the item index inside its containing menu.
		selectedItemIndex = getIndexOfSelectedItem();
		
		// Prepare the popup-menu options.
		csSubMenu.prepareMenu(selectedItem);
		
		// Remove the item from the menu.
		parentPopup.remove(selectedItem);
		
		// Set the sub-menu with the item's text.
		csSubMenu.setText(selectedItem.getText());
			
		// Add the new sub-menu to the popup parent at the right index.
		parentPopup.add(csSubMenu,selectedItemIndex);
	
		// Validate.
		parentPopup.validate();
		
		// Repaint.
		parentPopup.repaint();
	}

	/**
	 * Returns the index of the menuitem inside its containing popup menu.
	 * @return
	 */
	private int getIndexOfSelectedItem()
	{
		// We're getting the list of components (to include also the seperator).
		Component[] elements = parentPopup.getComponents();
			 
		// Find the item index in the menu.
		for(int index = 0;index<elements.length;index++)
			if(selectedItem.equals(elements[index]))
				return index;
		
		// Should never happen !
		return -1;
	}

	
	/**
	 * @return
	 */
	public static JPopupMenu getParentPopup()
	{
		return parentPopup;
	}

	/**
	 * @return
	 */
	public static MatafMenuItem getSelectedItem()
	{
		return selectedItem;
	}

	/**
	 * @return
	 */
	public static int getSelectedItemIndex()
	{
		return selectedItemIndex;
	}

}