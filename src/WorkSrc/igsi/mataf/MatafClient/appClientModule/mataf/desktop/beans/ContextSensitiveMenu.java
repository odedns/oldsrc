package mataf.desktop.beans;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.ibm.dse.desktop.Desktop;
import com.ibm.dse.desktop.TaskInfo;

/**
 * This menu is opened when right-clicking on a 
 * menu item in the menu.
 * 
 * @author Nati Dykstein. Creation Date : (19/05/2004 15:59:53).  
 */
public class ContextSensitiveMenu extends MatafMenu
{
	private static final String[] POPUP_ITEMS_TEXT = 
	{
		"הוסף לתפריט אישי",
		"הסר מתפריט אישי",
		"הוסף לחלון שאילתות"
	};
		
	/** The predefined right-click options.*/
	private MatafMenuItem[] popupItems = new MatafMenuItem[POPUP_ITEMS_TEXT.length];
	

	/**
	 * Creates the context-sensitive menu with reference to the listener.
	 */
	public ContextSensitiveMenu()
	{
		super("");
		
		createPopupMenuItems();
		
		// Reverse the procedure when moving away from the sub-menu.
		addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{			 
				JMenu m = (JMenu)e.getSource();
				JPopupMenu parentPopup = MenuItemMouseListener.getParentPopup();
				// Check if we left the menu.
				if(!m.isSelected())
				{
					// Remove this sub-menu.
					parentPopup.remove(ContextSensitiveMenu.this);
					
					// Re-add the selected item to his place.
					parentPopup.add(MenuItemMouseListener.getSelectedItem(),
									MenuItemMouseListener.getSelectedItemIndex());
					
					parentPopup.validate();
					parentPopup.repaint();
					
					// Clear the context sensitive options.
					removeAll();
				}
			}
		});
		
		/**
		 * Prevents dragging of the new sub-menu.
		 * When exiting the new sub-menu while holding down a mouse
		 * key it will become deselected.
		 */
		addMouseListener(new MouseAdapter()
		{
			public void mouseExited(MouseEvent e)
			{
				if(e.getModifiers()!=0)
					setSelected(false);
			}
		});
	}
	
	/**
	 * This method decides which items to display, according to
	 * the menu item context in which it is opened.
	 * Hence : context sensitive pop-up menu.
	 */
	public void prepareMenu(MatafMenuItem selectedItem)
	{
		// Display 'add/remove from personal menu' option.
		if(clickedOnPersonalMenu(selectedItem))		
			add((Object)popupItems[1]);
		else
			add((Object)popupItems[0]);
		
		// Display additional option for a query menu item.
		if(isQueryMenuItem(selectedItem))
			add((Object)popupItems[2]);

		
		// Workaround for the following bug :
		// When the popup width changes to accomodate its dynamic content,
		// it is displayed in the right location only after the second time
		// it is opened.Thus, we 'simulate' an open+close operation to 
		// workaround the bug.
		getPopupMenu().setPreferredSize(new Dimension(0,0));
		getPopupMenu().setVisible(true);
		getPopupMenu().setVisible(false);
		
		getPopupMenu().setPreferredSize(computePreferredSize());
	}
	
	/**
	 * Returns true if we clicked on an item from the personal menu.
	 * @param menuItem
	 * @return
	 */
	private boolean clickedOnPersonalMenu(JMenuItem menuItem)
	{
		MatafMenu personalMenu = MatafMenuBar.getPersonalMenu();
		for(int i=0;i<personalMenu.getItemCount();i++)
		{
			JMenuItem pItem = personalMenu.getItem(i);
			
			// Component at the index is not an item.(probably a seperator)
			if(pItem==null)
				continue;
			
			// Check if the item exists on the personal menu.
			if(menuItem.getText().equals(pItem.getText()))
				return true;
		}
		
		return false;
	}
	
	/**
	 * Return true if we clicked on an item that represents a query.
	 * @param menuItem
	 * @return
	 */
	private boolean isQueryMenuItem(MatafMenuItem menuItem)
	{
		TaskInfo tiTemp=Desktop.getDesktop().getTaskInfo(menuItem.getTaskName());
			
		if(tiTemp instanceof MatafTaskInfo)
			return ((MatafTaskInfo)tiTemp).getOperationType().equals("query");
			
		return false;
	}

	
	/**
	 * Currently set the preferred size of the popup according to the
	 * widest text.
	 * @return
	 */
	private Dimension computePreferredSize()
	{
		final int ROW_HEIGHT = 22;
		final int CHAR_WIDTH = 7;
		
		int numberOfElements = getPopupMenu().getSubElements().length;
		int height = ROW_HEIGHT*numberOfElements;
		int maxWidth = 0;
		for(int i=0;i<numberOfElements;i++)
		{
			int width = CHAR_WIDTH*POPUP_ITEMS_TEXT[i].length(); 
			if(maxWidth<width)
				maxWidth=width;
		}
		
		return new Dimension(maxWidth, height);
	}
	
	/**
	 * This is where we create the items we want the context sensitive
	 * popup menu to display.
	 * Additional configuration of each item is done here.
	 */
	private void createPopupMenuItems()
	{
		// Create the menu items with their text.
		for(int i=0;i<POPUP_ITEMS_TEXT.length;i++)
			popupItems[i] = new MatafMenuItem(POPUP_ITEMS_TEXT[i]);
		
		// Make item add to personal menu.
		popupItems[0].addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				MatafMenuItem menuItem = 
							(MatafMenuItem)MenuItemMouseListener.getSelectedItem();
				menuItem.addToPersonalMenu();
			}
		});
		
		// Make item remove from personal menu.
		popupItems[1].addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				MatafMenuItem menuItem = 
							(MatafMenuItem)MenuItemMouseListener.getSelectedItem();
				menuItem.removeFromPersonalMenu();
			}
		});
		
		//	Add queries to queries list
		 popupItems[2].addActionListener(new ActionListener()
		 {
			 public void actionPerformed(ActionEvent e)
			 {
			 }
		 });
	}
}
