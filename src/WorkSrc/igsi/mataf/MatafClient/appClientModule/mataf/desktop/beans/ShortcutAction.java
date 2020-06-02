package mataf.desktop.beans;

import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTextField;

import mataf.desktop.views.MatafClientView;
import mataf.logger.GLogger;

import mataf.dse.appl.OpenDesktop;

/**
 * Class for handling the menu shortcut keys.
 * 
 * It keeps track of the pressed shortcut digits, and activates
 * the RT transaction according to the prefix char and the 
 * transaction number.
 * 
 * @author Nati Dykstein. Creation Date : (06/08/2003 17:46:23).  
 */

public class ShortcutAction extends AbstractAction
{
	private 		 	int		shortcutKeyPressed;
	private static 	int 		strokesCounter;
	private static	String		shortcutString;
		
	public ShortcutAction(int shortcutKeyPressed)
	{
		this.shortcutKeyPressed = shortcutKeyPressed;
	}
		
	public void actionPerformed(ActionEvent e)
	{
		// Shortcut is not active while business screen is open.
		if(OpenDesktop.isBusinessScreenOpen())
			return;
			
		// Shortcut is not active while we're inside a textfield
		if(KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() instanceof JTextField)
			return;
			
		JTextField field = MatafWorkingArea.getActiveClientView().getShortcutField();
		
		// New stroke deletes previous text.
		if(strokesCounter==0)
		{
			if(field!=null)
				field.setText("");
			shortcutString="";
			
			// We didn't activate the transaction from the menu
			// so we don't need the menu to be re-opened.
			//MatafMenuBar.disableMenuReopening();
		}
			
		shortcutString += shortcutKeyPressed;
		// Update the textfield
		if(field!=null)
			field.setText(shortcutString);
		
		// Increment stroke count.
		strokesCounter++;
		
		GLogger.debug("SHORT CUT : "+shortcutString);
		
		// Limit shortcut to 3 strokes.
		if(strokesCounter==3)
		{
			try
			{
				String ch = ""+MatafWorkingArea.getActiveClientView().getShortcutContextChar();
				int trxPrefix = 
					((Integer)(MenuShortcutManager.shortcutCharToPrefixConverter.get(ch))).intValue();
				OpenDesktop.activateTranscationByNumber(trxPrefix+shortcutString);
			}
			catch(Exception e2)
			{
				e2.printStackTrace();
			}
			finally
			{
				strokesCounter=0;
			}
		}
	}
	
	/**
	 * Clears the input previously typed into the shortcut textfield.
	 */
	public static void clear()
	{
		MatafClientView matafDSEPanel = MatafWorkingArea.getActiveClientView();
		if(matafDSEPanel!=null)
		{
			if(matafDSEPanel.getShortcutField()!=null)
				matafDSEPanel.getShortcutField().setText("");
		}
		shortcutString = "";
		strokesCounter=0;
	}
}