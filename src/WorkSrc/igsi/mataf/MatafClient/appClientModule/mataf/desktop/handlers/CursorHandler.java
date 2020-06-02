package mataf.desktop.handlers;

import java.awt.Cursor;
import java.util.HashMap;

import mataf.services.proxy.AbstractRequestHandler;
import mataf.services.proxy.ProxyRequest;
import mataf.services.proxy.RequestException;

import com.ibm.dse.desktop.Desktop;

/**
 * Handler changes the cursor back to the default cursor.
 * 
 * @author Nati Dykstein. Creation Date : (25/06/2003 15:27:28).  
 */
public class CursorHandler extends AbstractRequestHandler 
{		
	/**
	 * Handles the cursor.
	 */
	public HashMap execRequest(ProxyRequest req) throws RequestException
	{
		Desktop.getDesktop().getFrame().setCursor(Cursor.getDefaultCursor());
		return null;
	}
}