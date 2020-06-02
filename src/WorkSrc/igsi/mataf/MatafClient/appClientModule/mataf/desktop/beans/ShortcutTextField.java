package mataf.desktop.beans;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import mataf.types.textfields.MatafNumericField;

import mataf.dse.appl.OpenDesktop;

/**
 * Class adds a KeyListener to the textfield.
 * This KeyListener remembers the last 3 keys typed and activate the
 * corresponding transaction in the menu.
 * 
 * @author Nati Dykstein. Creation Date : (24/08/2003 12:17:08).  
 */
public class ShortcutTextField extends MatafNumericField
									implements KeyListener
{
	private	String		shortcutString="";
	
	public ShortcutTextField()
	{
		this("");		
	}
	
	public ShortcutTextField(String text)
	{
		super();
		setText(text);
		addKeyListener(this);
		setEditable(false);
		setFocusable(false);
		setColumns(8);
	}
	
	/**
	 * Handles typing of shortcuts to the menu.
	 */
	public void keyPressed(KeyEvent e) 
	{
		// We're dealing only with digits.
		if(!Character.isDigit(e.getKeyChar()))
			return;
		
		// New stroke deletes previous text.
		if(shortcutString.length()==0)
		{
			setText("");
			MatafMenuBar.disableMenuReopening();
		}
		
		shortcutString+=e.getKeyChar();
		
		System.out.println("SHORTCUT : "+shortcutString);
		
		// Limit shortcut to 3 strokes.
		if(shortcutString.length()==3)
		{
			try
			{				
				String ch = ""+OpenDesktop.getActiveClientView().getShortcutContextChar();
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
				shortcutString="";
			}
		}
	}

	public void keyReleased(KeyEvent e) {}

	public void keyTyped(KeyEvent e) {}

}
