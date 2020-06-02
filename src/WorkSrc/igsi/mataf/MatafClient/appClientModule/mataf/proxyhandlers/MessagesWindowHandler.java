package mataf.proxyhandlers;

import java.awt.Color;
import java.util.HashMap;

import mataf.desktop.beans.MatafWorkingArea;
import mataf.services.proxy.AbstractRequestHandler;
import mataf.services.proxy.ProxyRequest;
import mataf.services.proxy.RequestException;

import com.ibm.dse.desktop.Desktop;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (25/06/2003 15:27:28).  
 */
public class MessagesWindowHandler extends AbstractRequestHandler {

	
	private static String	DATAKEY		= "text";
	private static String	COLORKEY 	= "color";
	
	/**
	 * Handles the message window text.
	 */
	public HashMap execRequest(ProxyRequest req) throws RequestException 
	{
		HashMap data = req.getParams();		

		// Get RT data.
		String errorMsg = (String)data.get(DATAKEY);
		String errorMsgColor = (String)data.get(COLORKEY);
		
		// Get a reference to the working area.
		MatafWorkingArea workingArea = MatafWorkingArea.getWorkingArea();
		
		// Add a message with the specified color.
		workingArea.addMessage(errorMsg,Color.decode(errorMsgColor));
		
		return null;
	}

}
