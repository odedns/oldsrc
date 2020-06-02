package mataf.types;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import mataf.exchangers.VisualPropertiesExchanger;
import mataf.utils.FontFactory;

import com.ibm.dse.base.Context;
import com.ibm.dse.gui.DSECoordinationEvent;
import com.ibm.dse.gui.Settings;
import com.ibm.dse.gui.SpButton;

/**
 * This class defines a custom button for the application.
 * 
 * @author Nati Dikshtein.
 */

public class MatafButton extends SpButton implements VisualPropertiesExchanger
{
	// Allow the composer's constants to be checked at compile time.
	public static final String CLOSE_VIEW 		= "Close";
	public static final String CLOSE_NAVIGATION	= "Close_Navigation";
	public static final String PREVIOUS_VIEW 	= "Previous_View";
	public static final String NEXT_VIEW 		= "Next_View";
	public static final String CLEAR_VIEW 		= "Clear";
	public static final String EXECUTE_OPERATION  = "Execute_Operation";

	private static final Color TEXT_COLOR;
	private static final Font FONT;
	public static final Dimension DEFAULT_SIZE;

	private boolean defaultButton;
	
	/** Indicates that the button was activated by the server.*/
	//private boolean autoClose;
	private String dataName;

	static 
	{
		FONT = FontFactory.createFont("Arial", Font.BOLD, 14);
		TEXT_COLOR = Color.black; // Overrided by Visual Properties mechanism.
		DEFAULT_SIZE = new Dimension(100, 20);
		//UIManager.put("Button.focus", new Color(90, 90, 90));
	}

	/**
	 * Constructor for MatafButton
	 */
	public MatafButton()
	{
		this("טקסט ריק", false);
	}

	public MatafButton(String text)
	{
		this(text, false);
	}

	public MatafButton(String text, boolean withIcon)
	{
		super();
		//setUI(new MatafButtonUI(model, this));
		setText(text);
		customizeUIProperties();
		initKeyMaps();
	}
	
	/**
	 * Make custom changes to the button.
	 */
	private void customizeUIProperties()
	{
		setForeground(TEXT_COLOR);
		setRolloverEnabled(true);
		setFont(FONT);
		setHorizontalTextPosition(SwingConstants.LEFT);
		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		setSize(DEFAULT_SIZE);
		//initKeyMaps();
	}

	/**
	 * Method binds the new keystoke to already defined actions in
	 * the JButton actionMap.
	 * The keystrokes represents the use of the enter key as a trigger
	 * of activating the button.
	 * 
	 * If the button is defined as the default button then the keystroke
	 * will activate the button while the window is in focus.
	 * If the button is not defined as the default button then the keystroke
	 * will active the button only when it is in focus.
	 */
	private void initKeyMaps()
	{
		InputMap ciMap = new InputMap();
		//		ComponentInputMap ciMap = new ComponentInputMap(this);
		ciMap.put(KeyStroke.getKeyStroke("pressed ENTER"), "pressed");
		ciMap.put(KeyStroke.getKeyStroke("released ENTER"), "released");
		int condition =
			defaultButton
				? JComponent.WHEN_IN_FOCUSED_WINDOW
				: JComponent.WHEN_FOCUSED;
		this.setInputMap(condition, ciMap);
	}

	/**
	 * Returns the defaultButton.
	 * @return boolean
	 */
	public boolean isDefaultButton()
	{
		return defaultButton;
	}

	/**
	 * Sets the defaultButton.
	 * @param defaultButton The defaultButton to set
	 */
	public void setDefaultButton(boolean defaultButton)
	{
		this.defaultButton = defaultButton;
		initKeyMaps();
	}

	/**
	 * Method maps the hebrew mnemonic read from the matafdesktop.xml to its 
	 * corresponding VK_XXX constant.
	 * It also computes the index of the char to underline as a mnemonic.
	 */
	public void setMnemonic(Object mnemonic)
	{
		char m = ((String)mnemonic).toCharArray()[0];
		//setMnemonic(HebrewUtilities.mapHebrewKeysToVKConstants(m));
		int pos = getText().length() - 1;
		setDisplayedMnemonicIndex(pos - getText().indexOf(m));
	}

	public void setDataName(String dataName)
	{
		this.dataName = dataName;
	}

	public String getDataName()
	{
		return dataName;
	}

	/**
	 * @see mataf.types.VisualPropertiesExchanger#exchangeVisualProperties()
	 */
	public void exchangeVisualProperties(Context ctx)
	{
		ADAPTER.exchangeVisualProperties(ctx, this);
	}

	/**
	 * @see mataf.types.VisualPropertiesExchanger#setErrorMessage(String)
	 */
	public void setErrorMessage(String message)
	{ /* Empty Implementation */}

	/**
	 * @see mataf.types.VisualPropertiesExchanger#setInErrorFromServer(boolean)
	 */
	public void setInErrorFromServer(boolean errorFromServer)
	{ /* Empty Implementation */}

	/**
	 * @see mataf.exchangers.VisualPropertiesExchanger#setRequired(boolean)
	 */
	public void setRequired(boolean mandatory)
	{ /* Empty Implementation */}

	/**
	 * Returns true if this button is used to close a view.
	 * 
	 * @return
	 */
	public boolean isCloseButton()
	{
		String type = getType();
		return type.equalsIgnoreCase(CLOSE_VIEW) || type.equalsIgnoreCase(CLOSE_NAVIGATION) ;
	}

	/**
	 * If button's type is CLOSE_VIEW or CLOSE_NAVIGATION
	 * display a confirmation pop-up for the user.
	 */
	public void fireCoordinationEvent()
	{
		if(isCloseButton())
		{			
			int input =
				MatafOptionPane.showConfirmDialog(
					getParent(),
					"האם אתה בטוח ? ",
					"יציאה",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			
			// User cancelled exit.
			if (input != JOptionPane.YES_OPTION)
				return;
		}

		// Create, initialize and fire the event.
		DSECoordinationEvent newEvent = getNavigationParameters().generateCoordinationEvent(getType(),this);
		newEvent.setRefresh(false);
		newEvent.setEventName(getName()+".actionPerformed");
		try
		{
			fireCoordinationEvent(newEvent);
		}
		catch(Exception e)
		{
			System.err.println("\n"+e.getClass().getName()+" has occured while trying to fire the following event :" +
								"\n"+newEvent);
			System.err.println("Tips :");
			System.err.println("1.If this event opens a new view - check the view and it's panel for errors.");
			System.err.println("2.If this event triggers a server operation - check the server for errors.");
			System.err.println("3.Check that the event parameters are configured correctly.");
			/*MatafOptionPane.showMessageDialog(
				Desktop.getFrame(),
				"\n"+e.getClass().getName()+" has occured while trying to execute an event.\nCheck console for details."
				,"Error Warning",
				JOptionPane.ERROR_MESSAGE);*/
		}
	}
	
	/**
	 * Binds a special ESCAPE functionality to a close button.
	 * 
	 * @see com.ibm.dse.gui.SpButton#setType(java.lang.String)
	 */
	public void setType(String type)
	{
		super.setType(type);
		
		if(isCloseButton())
		{
			// Allow this button to be clicked without the need
			// to recieve the focus.
			setFocusable(false);
		}
	}
	
	/**
	 * Make the the button clear only the input fields.
	 */
	public String getDataToClear()
	{
		return Settings.INPUT_DIRECTION + " " + Settings.BOTH_DIRECTION;
	}

	public static void main(String[] args)
	{
		JFrame f = new JFrame("Testing Button");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().setLayout(new FlowLayout());
		f.getContentPane().setBackground(Color.white);
		MatafButton pb = new MatafButton("בדיקה כפתור 1");
		pb.setMnemonic('ה');
		MatafButton pb2 = new MatafButton("בדיקת כפתור 2");
		pb2.setMnemonic('ב');
		MatafButton pb3 = new MatafButton("בדיקת כפתור 3");
		pb3.setMnemonic('כ');
		MatafButton pb4 = new MatafButton("Testing Button 4");
		pb4.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		pb4.setMnemonic('i');
		pb2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("Hello World! pb2 ");
			}
		});

		pb3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("Hello World! pb3");
			}
		});

		pb4.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("Hello World! pb4");
			}
		});
		pb3.setBounds(0, 0, 100, 200);
		pb.setEnabled(false);
		f.getContentPane().add(pb);
		f.getContentPane().add(pb2);
		f.getContentPane().add(pb3);
		f.getContentPane().add(pb4);
		f.getContentPane().add(new JButton("TEST"));
		f.setSize(200, 200);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}