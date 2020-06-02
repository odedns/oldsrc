package mataf.proxyhandlers;

import java.util.HashMap;
import java.util.StringTokenizer;

import javax.swing.JMenuItem;

import com.ibm.dse.desktop.Desktop;

import mataf.desktop.beans.MatafMenuBar;
import mataf.logger.GLogger;
import mataf.services.proxy.AbstractRequestHandler;
import mataf.services.proxy.ProxyRequest;
import mataf.services.proxy.RequestException;

import mataf.dse.appl.OpenDesktop;

/**
 * Handler enables/disables menu items in the application menu
 * according to the RT requests.
 * Also may enable/disable the whole menu :
 * 1. menuItemKeys="0" && action = 0 --> Disable Menu.
 * 2. menuItemKeys="0" && action = 1 --> Enable Menu And Request Focus.
 * 3. menuItemKeys="0" && action = 2 --> Enable Menu And Don't Request Focus
 * 	  (Singnals that the RT is executing an external application).
 * 
 * @author Nati Dykstein. Creation Date : (25/06/2003 15:27:28).  
 */
public class MenuItemHandler extends AbstractRequestHandler {

	
	private static String	ACTIONKEY	= "action";
	private static String	MENUITEMKEY	= "id";
	
	private static boolean launchedExternalApplication;
	
	/**
	 * Handles the menu items.
	 */
	public HashMap execRequest(ProxyRequest req) throws RequestException
	{
		HashMap data = req.getParams();

		// Get RT data.
		int action = Integer.parseInt((String)data.get(ACTIONKEY));
		String menuItemKeys = (String)data.get(MENUITEMKEY);
		
		// Disables/Enables the application menu.
		if(menuItemKeys.equals("0"))
		{
			if(action==0)
			{
				GLogger.debug("Disabling Menus");
				MatafMenuBar.setMenuEnabled(false);
			}
			
			if(action==1)
			{
				GLogger.debug("Enabling Menus - ");
				
				if(launchedExternalApplication)
				{
					GLogger.debug("No Focus Grabbing");
					// Enable the menu without getting the focus back.
					MatafMenuBar.setMenuEnabled(true, false);
					// Reset flag.
					launchedExternalApplication = false;
				}
				else
				{
					GLogger.debug("Grabbing Focus");
					Desktop.getDesktop().getRootPane().requestFocus();
					// Enable the menu without getting the focus back.
					MatafMenuBar.setMenuEnabled(true, true);
				}
			}
			
			if(action==2)
			{
				GLogger.debug("Launched External Application !!");
				// Mark the flag of that external application was launched.
				launchedExternalApplication = true;
			}
				
			// Back-up thread for catching the focus back to the java application.
			/*
			new Thread()
			{
				public void run()
				{
					System.out.println("Starting focus tracking thread...");
					while(true)
					{
						try{Thread.sleep(1000);}
						catch(Exception e){e.printStackTrace();}
						Component c = FocusManager.getCurrentManager().getFocusOwner();
						
						// No java focus owner.
						if(c==null)
						{
							System.out.println("Current Focus Owner = null");
							System.out.println("Requesting focus again...");
							OpenDesktop.getMainMatafPanel().getRootPane().requestFocus();
						}
						else
						{
							System.out.println("Current Focus Owner = "+c);
							System.out.println("Finished.");
							break;
						}
					}
				}
			}.start();*/
				
			return null;
		}
				
		
		
		// Create a StringTokenizer with the menu items tasks names.
		StringTokenizer st = new StringTokenizer(menuItemKeys, ",");

		// Process the menu items and Enable/Disable them.
		while(st.hasMoreTokens())
		{
			String s = st.nextToken();
			JMenuItem menuItem = MatafMenuBar.getMenuItemByTaskName(s);
			try
			{
				menuItem.setEnabled(action==1);
			}
			catch(NullPointerException e)
			{
				GLogger.warn("Could not find TaskName : "+s+" in matafdesktop.xml !.");
			}
		}
		
		OpenDesktop.updateProgress();
				
		return null;
	}
}