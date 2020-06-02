package mataf.desktop.views;

import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Enumeration;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.LookAndFeel;
import javax.swing.MenuElement;
import javax.swing.MenuSelectionManager;
import javax.swing.UIManager;

import mataf.calculator.Calculator;
import mataf.desktop.beans.MatafMenuBar;
import mataf.desktop.beans.MatafMenuItem;
import mataf.desktop.beans.MenuShortcutManager;
import mataf.dse.appl.OpenDesktop;
import mataf.logger.GLogger;
import mataf.services.proxy.RTCommands;

import com.ibm.dse.desktop.Desktop;

/**
 * Adding keys to the root pane to allow them to operate fron both 
 * the menu and the content pane.	
 * Creates the additional keys functionality :
 * - Pressing F7 under the teller menu will activate
 *   a teller-related transaction.
 * - Pressing Shift+F9 will toggle the nesting mode.
 * 
 * Note : These keys are attached to the InputMap of the root pane.
 * 			to view keys that are processed with key listeners view the
 * 			comments inside the method.
 *
 * @author Nati Dykstein. Creation Date : (05/05/2004 12:30:05).  
 */
public class RootPaneCustomizer
{
	public static void initializeRootPane(final JRootPane rootPane)
	{
		// Create the input map.
		InputMap im = rootPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		
		im.put(KeyStroke.getKeyStroke("alt control SCROLL_LOCK"), "DEBUG");
		
		im.put(KeyStroke.getKeyStroke("F7"), "tellerInfo");
		im.put(KeyStroke.getKeyStroke("control F7"), "openRTDebugger");
		im.put(KeyStroke.getKeyStroke("shift F9"), "toggleNesting");
		im.put(KeyStroke.getKeyStroke("shift F5"),"openCalculator");
		im.put(KeyStroke.getKeyStroke("shift F4"),"openJavaCalculator");

		ActionMap am = rootPane.getActionMap();
		
		// DEBUG
		am.put("DEBUG", new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				Desktop.getFrame().setSize(1024,568);
				JOptionPane.showMessageDialog(Desktop.getFrame(),"DUMPING DEBUG INFORMATION");
				System.out.println("------------------------- DUMPING JAVA SYSTEM INFORMATION ----------------------");
				Properties p = System.getProperties();
				Enumeration e2 = p.keys();
				
				while(e2.hasMoreElements())
				{
					String key = (String)e2.nextElement();
					System.out.println(key+" = "+p.getProperty(key));
				}
				
				System.out.println("------------------------- DUMPING DEBUG INFORMATION ----------------------");

				LookAndFeel	lnf = UIManager.getLookAndFeel();
				System.out.println("L&F = "+lnf.getDescription());
			}
		});
		
		// Verify that the teller menu is open !
		am.put("tellerInfo", new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				MatafMenuBar.disableMenuReopening();
				
				MenuElement[] path = MenuSelectionManager.defaultManager().getSelectedPath();

				// No menu is open.
				if(path.length==0)
					return;
					
				// Check if currently opened menu element is part of
				// the teller menu.
				if(MatafMenuBar.getTellerMenu().isMenuComponent((Component)path[path.length-1]))
				OpenDesktop.asynchronicSend(RTCommands.RT_KEYBOARD_COMMANDS,
									"Key",
									""+KeyEvent.VK_F7);
			}
		});				
		
		/**
		 * Switches between two opened RT business screens.
		 */
		am.put("toggleNesting", new AbstractAction() 
		{			
			public void actionPerformed(ActionEvent e) 
			{			   	
				OpenDesktop.asynchronicSend(RTCommands.RT_KEYBOARD_COMMANDS,
									"Key",
									""+((KeyEvent.VK_SHIFT<<8) + KeyEvent.VK_F9));
			}
		});		
		
		/**
		 * Opens the RT Calcualtor
		 */
		am.put("openCalculator", new AbstractAction() 
		{			
			public void actionPerformed(ActionEvent e) 
			{
				System.out.println("Opening RT calculator");			   	
				OpenDesktop.asynchronicSend(RTCommands.RT_KEYBOARD_COMMANDS,
									"Key",
									""+((KeyEvent.VK_SHIFT<<8) + KeyEvent.VK_F5));
			}
		});
		
		am.put("openRTDebugger", new AbstractAction() 
		{			
			public void actionPerformed(ActionEvent e) 
			{
				OpenDesktop.asynchronicSend(RTCommands.RT_KEYBOARD_COMMANDS,
									"Key",
									""+((KeyEvent.VK_CONTROL<<8) + KeyEvent.VK_F7));
			}
		});
		
		am.put("openJavaCalculator", new AbstractAction() 
		{			
			public void actionPerformed(ActionEvent e) 
			{			   	
				Component c = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
				System.out.println("Focus Owner =  "+c);
				Calculator.openCalculator(Desktop.getFrame(), (c instanceof JTextField) ? (JTextField)c : null);
			}
		});
		
		// Bind the shortcut keys to the rootPane.		
		MenuShortcutManager.bindShortcutKeys(rootPane);
		
		/**
		 * The following keys are processed in this listener :
		 * 'All numeric keys' - Dispatched to the corresponding Action in
		 * the component's ActionMap.
		 * 'ENTER' - When the menu is closed, it will open the 'Sheiltot' menu.
		 * 'ESCAPE - When the menu is closed, it will exit from nesting mode.
		 * 
		 * This keyListener is invoked when typing keys while the JRootPane is
		 * in focus.
		 */
		rootPane.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e)
			{
				GLogger.debug("From ROOTPANE e = "+e);
				
				// Make the ESCAPE key exit from nesting mode (only
				// when the menu is not open).
				if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
				{
					// Check that the menu is closed.
					if(MenuSelectionManager.defaultManager().getSelectedPath().length==0)
					{
						OpenDesktop.asynchronicSend(RTCommands.RT_KEYBOARD_COMMANDS,
											"Key",
											""+KeyEvent.VK_ESCAPE);
					}
				}
				
				// When the menu is closed, Enter opens the first menu.
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					// Check that the menu is closed.
					if(MenuSelectionManager.defaultManager().getSelectedPath().length==0)
					{
						JMenuItem menuItem = MatafMenuBar.getMenuItemByTaskName("1000");
						menuItem.doClick();
					}
				}
				
				// Dispatch digits to thier corresponding Action class.
				if(Character.isDigit(e.getKeyChar()))
				{
					Action a = (Action)rootPane.getActionMap().get(""+e.getKeyChar());
					GLogger.debug("Action = "+a);
					
					// Activate the desired Action.
					if(a!=null)
						a.actionPerformed(new ActionEvent(this,0,"Shortcut Key = "+e.getKeyChar()));
				}
				
				// Adding menuItem to the PersonalMenuItem
				if(e.getKeyCode()==KeyEvent.VK_INSERT && e.isControlDown())
				{
					MenuElement[] path = MenuSelectionManager.defaultManager().getSelectedPath();
					if(path.length!=0)
					{
						Component itemComponent = path[path.length-1].getComponent();
						if(itemComponent instanceof MatafMenuItem)
						{
							MatafMenuBar.getInstance().addMenuItemToPersonalMenu((MatafMenuItem)itemComponent);
						}
					}
				}
					
			}
		});
		
		
		/*
		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
		
			public void eventDispatched(AWTEvent event) 
			{
				KeyEvent keyEvent = (KeyEvent)event;
				System.out.println("Key Event : "+keyEvent);
				
			}
		},AWTEvent.KEY_EVENT_MASK);*/

		}
	}

