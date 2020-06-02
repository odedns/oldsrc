package mataf.desktop.handlers;

import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JMenuItem;

import com.mataf.dse.appl.OpenDesktop;

import mataf.desktop.beans.MatafMenuBar;
import mataf.services.proxy.AbstractRequestHandler;
import mataf.services.proxy.ProxyRequest;
import mataf.services.proxy.RequestException;
import mataf.utils.GLogger;

/**
 * Handler enables/disables menu items in the application menu
 * according to the RT requests.
 * 
 * @author Nati Dykstein. Creation Date : (25/06/2003 15:27:28).  
 */
public class MenuItemHandler extends AbstractRequestHandler {

	
	private static String	ACTIONKEY	= "action";
	private static String	MENUITEMKEY	= "id";
	
	/**
	 * Handles the menu items.
	 */
	public HashMap execRequest(ProxyRequest req) throws RequestException
	{
		HashMap data = req.getParams();

		// Get RT data.
		int action = Integer.parseInt((String)data.get(ACTIONKEY));
		String menuItemKeys = (String)data.get(MENUITEMKEY);
		
		// Disables the application menu.
		if(menuItemKeys.equals("0"))
		{
			MatafMenuBar.setMenuEnabled(action==1);
			return null;
		}
				
		
		// Process the menu items and Enable/Disable them.
		StringTokenizer st = new StringTokenizer(menuItemKeys, ",");

		while(st.hasMoreTokens())
		{
			JMenuItem menuItem = MatafMenuBar.getMenuItemByTaskName(st.nextToken());
			menuItem.setEnabled(action==1);
		}
		
		OpenDesktop.progress("טוען תפריטים...");
				
		return null;
	}
}