package mataf.desktop.beans;

import java.util.Hashtable;

import javax.swing.ActionMap;
import javax.swing.JComponent;

/**
 * Class binds a component with the menu shortcut keys.
 * It also needs an instance of JTextfield in order to display the
 * keys that were pressed.
 * 
 * @author Nati Dykstein. Creation Date : (06/08/2003 12:08:23).  
 */
public class MenuShortcutManager
{
	private static final int SHORTCUT_LENGTH;
	
	public static final Hashtable shortcutCharToPrefixConverter;
	
	static
	{
		SHORTCUT_LENGTH = 3;
		
		// Hashtable used to map between the hebrew char representing
		// the context of the currently open menu to its corresponding
		// transaction prefix.
		shortcutCharToPrefixConverter = new Hashtable();
		shortcutCharToPrefixConverter.put("ù",new Integer(1));
		shortcutCharToPrefixConverter.put("è",new Integer(2));
		shortcutCharToPrefixConverter.put("î",new Integer(3));
		shortcutCharToPrefixConverter.put("ô",new Integer(4));
		shortcutCharToPrefixConverter.put("ø",new Integer(5));
		shortcutCharToPrefixConverter.put("ú",new Integer(6));
		shortcutCharToPrefixConverter.put("÷",new Integer(7));
		shortcutCharToPrefixConverter.put("ç",new Integer(8));
		shortcutCharToPrefixConverter.put("ã",new Integer(9));
		shortcutCharToPrefixConverter.put("à",new Integer(10));
		shortcutCharToPrefixConverter.put("ò",new Integer(11));
	}
	
	private MenuShortcutManager()
	{/* Prevent Instanciation */}
	
	/**
	 * Method is static to allow other components to attach
	 * the functionality of the shortcut keys to themselves.
	 */
	public static void bindShortcutKeys(JComponent comp)
	{
		// Create the input map.
	/*	InputMap im = comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);		
		im.put(KeyStroke.getKeyStroke("NUMPAD0"), "0");
		im.put(KeyStroke.getKeyStroke("0"), "0");
		im.put(KeyStroke.getKeyStroke("NUMPAD1"), "1");
		im.put(KeyStroke.getKeyStroke("1"), "1");
		im.put(KeyStroke.getKeyStroke("NUMPAD2"), "2");
		im.put(KeyStroke.getKeyStroke("2"), "2");
		im.put(KeyStroke.getKeyStroke("NUMPAD3"), "3");
		im.put(KeyStroke.getKeyStroke("3"), "3");
		im.put(KeyStroke.getKeyStroke("NUMPAD4"), "4");
		im.put(KeyStroke.getKeyStroke("4"), "4");
		im.put(KeyStroke.getKeyStroke("NUMPAD5"), "5");
		im.put(KeyStroke.getKeyStroke("5"), "5");
		im.put(KeyStroke.getKeyStroke("NUMPAD6"), "6");
		im.put(KeyStroke.getKeyStroke("6"), "6");
		im.put(KeyStroke.getKeyStroke("NUMPAD7"), "7");
		im.put(KeyStroke.getKeyStroke("7"), "7");
		im.put(KeyStroke.getKeyStroke("NUMPAD8"), "8");
		im.put(KeyStroke.getKeyStroke("8"), "8");
		im.put(KeyStroke.getKeyStroke("NUMPAD9"), "9");
		im.put(KeyStroke.getKeyStroke("9"), "9");*/
		
		// Create the action map.
		ActionMap am = comp.getActionMap();
		am.put("0",new ShortcutAction(0));
		am.put("1",new ShortcutAction(1));
		am.put("2",new ShortcutAction(2));
		am.put("3",new ShortcutAction(3));
		am.put("4",new ShortcutAction(4));
		am.put("5",new ShortcutAction(5));
		am.put("6",new ShortcutAction(6));
		am.put("7",new ShortcutAction(7));
		am.put("8",new ShortcutAction(8));
		am.put("9",new ShortcutAction(9));
	}
}