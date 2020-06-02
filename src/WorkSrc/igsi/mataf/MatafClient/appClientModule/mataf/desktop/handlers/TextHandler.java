package mataf.desktop.handlers;

import java.awt.Color;
import java.util.HashMap;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import mataf.services.proxy.AbstractRequestHandler;
import mataf.services.proxy.ProxyRequest;
import mataf.services.proxy.RequestException;

import com.ibm.dse.desktop.Desktop;
import com.ibm.dse.desktop.Label;
import com.ibm.dse.desktop.MultipleStateIconLabel;
import com.mataf.dse.appl.OpenDesktop;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (22/06/2003 15:03:10).  
 */
public class TextHandler extends AbstractRequestHandler {

	/**
	 * A static Hashtable that will convert the RT text slot index
	 * to the text names in the matafdesktop.xml
	 */ 
	private static Hashtable	slotsToNames;
	private static String		DEFAULT = "DEFAULT";
	private static String		SLOTKEY	= "textslot";
	private static String		DATA	= "text";
	
	static
	{
		slotsToNames = new Hashtable();
		slotsToNames.put("0","time");
		slotsToNames.put("1","date");
		slotsToNames.put("2","account");
		slotsToNames.put("3","counter");
		slotsToNames.put("4","userID");
		slotsToNames.put("5","PU");
		slotsToNames.put("6","stationNumber");
		slotsToNames.put("7","snifNumber");
	}
	
	public HashMap execRequest(ProxyRequest req) throws RequestException 
	{
		HashMap data = req.getParams();
		
		// Get ID slot to change.
		String rtTextSlot = (String)data.get(SLOTKEY);
		
		// Get the corresponding text name.
		String textName = (String)slotsToNames.get(rtTextSlot);
		if(textName==null)
			throw new RuntimeException("Text Slot not found : "+rtTextSlot);
		
		// Get text from RT.
		String text = (String)data.get(DATA);		
		
		// Get the label.
		Label label = 
			(Label)OpenDesktop.getJComponentFromDesktop(textName);
		
		// Change the text.
		label.setText(text);
		
		OpenDesktop.progress("טוען טקסט...");
		
		return null;
	}
}