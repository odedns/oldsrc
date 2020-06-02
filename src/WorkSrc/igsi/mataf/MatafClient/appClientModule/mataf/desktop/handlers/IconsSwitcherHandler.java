package mataf.desktop.handlers;

import java.util.HashMap;
import java.util.Hashtable;

import com.ibm.dse.desktop.Desktop;
import com.ibm.dse.desktop.MultipleStateIconLabel;
import com.mataf.dse.appl.OpenDesktop;

import mataf.services.proxy.AbstractRequestHandler;
import mataf.services.proxy.ProxyRequest;
import mataf.services.proxy.RequestException;

/**
 *	Class used to handle requests from the RT and send back
 * 	responses.
 * 
 * @author Nati Dykstein. Creation Date : (17/06/2003 12:02:31).  
 */
public class IconsSwitcherHandler extends AbstractRequestHandler 
{
	
	/**
	 * A static Hashtable that will convert the RT icons index number
	 * to the icon states in the matafdesktop.xml
	 */ 
	private static Hashtable	iconsNumber;
	private static String		DEFAULT = "DEFAULT";
	private static String		SLOTKEY	= "iconslot";
	private static String		IDKEY	= "iconid";
	
	/**
	 * Mapping the icon indices recieved from the RT to thier 
	 * corresponding states as defined in the matafdesktop.xml file.
	 */
	static
	{
		iconsNumber = new Hashtable();		
		iconsNumber.put("173","STATE1");
		iconsNumber.put("174",DEFAULT);
		iconsNumber.put("175","STATE1");
		iconsNumber.put("176","STATE1");
		iconsNumber.put("177","STATE1");
		iconsNumber.put("178","STATE1");
		iconsNumber.put("179","STATE1");
		iconsNumber.put("180",DEFAULT);
		iconsNumber.put("181","STATE1");
		iconsNumber.put("182","STATE1");
		iconsNumber.put("183",DEFAULT);
		iconsNumber.put("223","STATE1");
		iconsNumber.put("224","STATE2");
		iconsNumber.put("226","STATE3");
		iconsNumber.put("182","STATE1");
		iconsNumber.put("1000","STATE1");
		iconsNumber.put("2000","STATE2");
		iconsNumber.put("3000","STATE3");
		iconsNumber.put("4000","STATE4");
		iconsNumber.put("5000","STATE5");
		iconsNumber.put("6000","STATE6");
		iconsNumber.put("7000","STATE7");
		iconsNumber.put("8000","STATE8");
		iconsNumber.put("9000","STATE9");
		iconsNumber.put("10000","STATE10");
		iconsNumber.put("11000","STATE11");
		iconsNumber.put("12000","STATE2");
		iconsNumber.put("13000","STATE5");
		iconsNumber.put("14000","STATE12");
		iconsNumber.put("18000","STATE2");
	}
	
	public HashMap execRequest(ProxyRequest req) throws RequestException 
	{
		HashMap data = req.getParams();
		
		// Get ID slot to change.
		String slot = (String)data.get(SLOTKEY);		
		
		// Get RT icon id to switch to.
		String rtIconID = (String)data.get(IDKEY);		
		
		// Get the corresponding icon state.
		String iconState = (String)iconsNumber.get(rtIconID);
		if(iconState==null)
			throw new RuntimeException("Icon ID not found : "+rtIconID);		
		
		// Get the icon.
		MultipleStateIconLabel icon = 
			(MultipleStateIconLabel)OpenDesktop.getJComponentFromDesktop("SLOT"+slot);
		
		// Change its state.
		icon.setState(iconState);
		
		OpenDesktop.progress("טוען צלמיות...");
		
		return null;
	}
}
