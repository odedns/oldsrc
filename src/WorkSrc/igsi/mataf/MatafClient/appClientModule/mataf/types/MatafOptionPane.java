package mataf.types;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import mataf.utils.FontFactory;


/** 
 * This class is used to open a JOptionPane customized for right-to-left 
 * component orientation.
 * 
 * @author Nati Dykshtein 
 */
public class MatafOptionPane
{
	private static final Font FONT = FontFactory.createFont("Arial", Font.BOLD, 16);

    private static JOptionPane pane;
    private static JFrame defaultParent = new JFrame();
   
    private static Object closeOptions[] = {
        "סגור"
    };
    private static Object yesNoOptions[] = {
        "כן", "לא"
    };
    private static Object yesNoCancelOptions[] = {
        "כן", "לא", "ביטול"
    };
    
    static 
    {
        UIManager.put("OptionPane.messageFont", FONT);
        UIManager.put("OptionPane.buttonFont", FONT);
        pane = new JOptionPane();       
        pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        
        configureCustomeKeyActions();
    }
    
    private MatafOptionPane()
    {/** Prevent Implementaion. */}

	/**
	 * Allows the arrow keys to switch between the options.
	 */
	private static void configureCustomeKeyActions()
	{
		InputMap iMap = pane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		iMap.put(KeyStroke.getKeyStroke("LEFT"),"TEST");
		iMap.put(KeyStroke.getKeyStroke("RIGHT"),"TEST");
				
		ActionMap aMap = pane.getActionMap();
		aMap.put("TEST",new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				int emulatedKeyCode = KeyEvent.VK_TAB;
				pane.dispatchEvent(new KeyEvent(pane,KeyEvent.KEY_PRESSED,0,0,emulatedKeyCode,KeyEvent.CHAR_UNDEFINED));
			}
		});
	}

    
	/** 
	 * Method displays simple message dialog.
	 */
    public static void showMessageDialog(Component parentComponent, Object message, String title, int messageType)
    {        
        pane.setMessage(message);
        pane.setMessageType(messageType);
        pane.setOptions(closeOptions);
        pane.createDialog((parentComponent == null) ? defaultParent : parentComponent, title).setVisible(true);
    }

	/**
	 * Method displays confirmation dialog and gets the user's selection.
	 */
    public static int showConfirmDialog(Component parentComponent, Object message, String title, int optionType, int messageType)
    {        
        // Build the message.
        pane.setMessage(message);
        pane.setMessageType(messageType);
        
        // Choose between 2 types of confirm dialogs.
        switch(optionType)
        {
        case JOptionPane.YES_NO_OPTION :
            pane.setOptions(yesNoOptions);
            break;

        case JOptionPane.YES_NO_CANCEL_OPTION :
            pane.setOptions(yesNoCancelOptions);
            break;

        default:
            throw new UnsupportedOperationException("Illegal message type in OptionPane.");
        }
        
        // Show the dialog.
        pane.createDialog((parentComponent == null) ? defaultParent : parentComponent, title).setVisible(true);
        
        // Get user's selection.
        Object selectedValue = pane.getValue();
        
        // Get dialog's available options.
        Object options[] = pane.getOptions();
        
        // Match user's selection to a dialog's option.
        int counter;
        for(counter = 0; counter < options.length; counter++)
            if(options[counter].equals(selectedValue))
                break;
                
		// Return the int representing the user's selection.
        return counter;
    }

    public static void main(String args[])
    {
        
        int s = showConfirmDialog(null, "בדיקת הדיאלוג תוך שילוב\n כל הנתונים", "בדיקת דיאלוג" , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(s == 0)
            System.out.println("YES.");
        if(s == 1)
            System.out.println("NO.");
        if(s == 2)
            System.out.println("CANCEL.");
    }  
}
