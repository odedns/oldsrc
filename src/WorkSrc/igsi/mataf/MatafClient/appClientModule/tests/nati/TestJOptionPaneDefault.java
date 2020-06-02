package tests.nati;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import mataf.types.MatafOptionPane;
import mataf.utils.MatafGuiUtilities;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (17/03/2004 13:51:04).  
 */
public class TestJOptionPaneDefault extends JPanel
{

	static
	{
		Object[] bindings = (Object[])UIManager.get("OptionPane.windowBindings");
		System.out.println("Bindings :");
		for(int i=0;i<bindings.length;i+=2)
			System.out.println(bindings[i]+" --> "+bindings[i+1]);
		
	}
	/**
	 * 
	 */
	public TestJOptionPaneDefault()
	{
		super();
		
		System.out.println("JOptionPane Opened.");
		int x = MatafOptionPane.showConfirmDialog(
						new JFrame(),
						"האם אתה בטוח ? ",
						"יציאה",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
		System.out.println("JOptionPane Closed.");
						
		switch(x)
		{
			case JOptionPane.YES_OPTION : System.out.println("YES");break;
			case JOptionPane.NO_OPTION : System.out.println("NO");break;
			default : System.out.println("Default value : "+x);		
		}
	}

	
}
