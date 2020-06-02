package mataf.types;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import mataf.data.VisualDataField;

import com.ibm.dse.base.DataField;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.desktop.Desktop;
import com.ibm.dse.gui.CoordinatedEventListener;
import com.ibm.dse.gui.CoordinatedEventMulticaster;
import com.ibm.dse.gui.DSECoordinatedPanel;
import com.ibm.dse.gui.DSECoordinationEvent;
import com.ibm.dse.gui.DataChangedListener;
import com.ibm.dse.gui.DataExchangerWithList;
import com.ibm.dse.gui.NavigationParameters;
import com.ibm.dse.gui.Outsider;
import com.ibm.dse.gui.Settings;
import com.ibm.dse.gui.SpPanel;

/** 
 * Set up a Text Pane with a filled background, 
 * automatic scrolling and useful appending method. 
 * 
 * This class is an Outsider and a DataExchangerForList.
 * 
 * @author Nati Dykstein
 */

public class MatafMessagesPane implements Outsider, DataExchangerWithList
{
 	public  static String CLEAR_MESSAGES 		= "-1";
    public  static String END_OF_MESSAGES 	= "-2";
     
    private static int	MAXIMUM_VISIBLE_MESSAGES = 6;
    
    public static final Color BG_COLOR = new Color(248,248,248); 
 
    private JTextPane   	textPane;    
    private JScrollPane 	scrollPane;
    private boolean	 	rightToLeftOrientation;
	private Font			font;


    private int			messagesNumber;
    
    private transient CoordinatedEventListener aCoordinatedEventListener;

	private String		 	dataNameForList;
	
	private SpPanel		spPanel;
	
    
    public MatafMessagesPane()
    {
           this(null);//new Dimension(300,80));
    }
    
////////////////////////////////////////////////////////////////////////////////
    
    public MatafMessagesPane(Dimension dim)
    {
        textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        textPane.setBackground(BG_COLOR);
        
        
        // Create the scroll pane.
        scrollPane = new JScrollPane(textPane);
        scrollPane.setOpaque(false);

        // Fix the size of the scroll pane.        
        //scrollPane.setMinimumSize(dim);
        //scrollPane.setPreferredSize(dim);
        //scrollPane.setMaximumSize(dim);        
    }
    
////////////////////////////////////////////////////////////////////////////////
    
    public JTextPane getLogPane()
    {             
        return textPane;
    }
    
////////////////////////////////////////////////////////////////////////////////
    
    public JScrollPane getLogScrollPane()
    {        
        return scrollPane;
    }

////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Adds text to the pane.
     */
    public void log(String msg)
    {
        log(msg,Color.black);
    }
    
////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Adds colored text to the pane.
     */
    public void log(String msg,Color color)
    {
    	// Trim the message.
    	msg = msg.trim();
    			
		Document doc = textPane.getStyledDocument();
        SimpleAttributeSet set = new SimpleAttributeSet();        
		
        // Add newline mark.
		msg+=System.getProperty("line.separator");
		
        // Make text bold & colorful.
		StyleConstants.setBold(set, font.isBold());
        StyleConstants.setForeground(set, color);
		StyleConstants.setFontSize(set, font.getSize());
		StyleConstants.setFontFamily(set, font.getFamily());

		// Append text to the text pane.
		try
		{
            doc.insertString(doc.getLength(), msg, set);
		}
		catch(BadLocationException e)
        {
            System.out.println(e.getMessage());
		}
        catch(Error e) // <-- There's an erratic writelock error.
        {
            System.out.println(e.getMessage());
        }
        
        // Scroll Down Automatically
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        try
        {            
            verticalBar.setValue(verticalBar.getMaximum());
        }
        catch(Exception e)
        {
            System.out.println("Exception while automatically scrolling down : ");
            e.printStackTrace();
        }
    }
    
    
///////////////////////////////////////////////////////////////////////////////////////    

	/**
	 * Adds a message to the message window.
	 */
	public void addMessage(String message)
	{
		addMessage(message, Color.black);
	}
	
///////////////////////////////////////////////////////////////////////////////////////

    /**
	 * Adds a message with the specified color to the pane.
	 * Adding messages has additional logic associated with it.
	 * It encapsulates the ability to maintain a maximum number of
	 * messages to display(exceeding it will result in opening a pop-up window),
	 * and also the ability to clear the pane.
	 */
	public void addMessage(String message, Color messageColor)
	{
		// Solve almost non-visible white text coming from the RT.
		if(messageColor.equals(Color.white))
			messageColor = Color.black;
		// Clears the message area.
		if(message.equals(CLEAR_MESSAGES))
		{
			getLogPane().setText("");
			messagesNumber = 0;
		}
		else
			// End of current group of messages.(Flag recieved from the RT)
			if(message.equals(END_OF_MESSAGES))
			{
				// Check if we have to show a popup.
				if(messagesNumber>MAXIMUM_VISIBLE_MESSAGES)
					showMessagesInPopup();
			}		
			else // Append a new message.
			{
				log(message, messageColor);
				messagesNumber++;
				
				if(messagesNumber>MAXIMUM_VISIBLE_MESSAGES)
					showMessagesInPopup();
			}
	}
	
	/**
	 * Shows the messages in the messages area in a pop-up window.
	 */
	private void showMessagesInPopup()
	{
		String text = getLogPane().getText();
		MatafOptionPane.showMessageDialog(Desktop.getFrame(),
										text,
										"חלון הודעות",
										JOptionPane.INFORMATION_MESSAGE);
	}
    
///////////////////////////////////////////////////////////////////////////////    

    /**
     * Special Implementaion of the component orientation property for
     * the EditorPane.
     */
    public void setComponentOrientation(ComponentOrientation orientation)
    {    	
    	getLogScrollPane().setComponentOrientation(orientation);
    	rightToLeftOrientation = 
    		(orientation.equals(ComponentOrientation.RIGHT_TO_LEFT)) ? true : false;
    	if(rightToLeftOrientation)
    	{
    		SimpleAttributeSet alignSet = new SimpleAttributeSet();
        	StyleConstants.setAlignment(alignSet, StyleConstants.ALIGN_RIGHT);
         	textPane.setParagraphAttributes(alignSet, false);
    	}
    }
    
///////////////////////////////////////////////////////////////////////////////

	public void setFont(Font font)
	{
		this.font = font;
	}
    
    public Font getFont()
    {
    	return font;
    }
    
///////////////////////////////////////////////////////////////////////////////    
///////////////////////////////////////////////////////////////////////////////    
///////////////////////////////////////////////////////////////////////////////        
    
    
	/**
	 * @see com.ibm.dse.gui.PanelActions#getSpPanel()
	 */
	public SpPanel getSpPanel() 
	{
		return spPanel;		
	}

	/**
	 * @see com.ibm.dse.gui.Outsider#setSpPanel(SpPanel)
	 */
	public void setSpPanel(SpPanel spPanel) 
	{
		this.spPanel = spPanel;
		spPanel.registerOutsider(this);
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#addActionListener(ActionListener)
	 */
	public void addActionListener(ActionListener ae) {
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#addCoordinatedEventListener(CoordinatedEventListener)
	 */
	public void addCoordinatedEventListener(CoordinatedEventListener newListener) 
	{
		aCoordinatedEventListener = CoordinatedEventMulticaster.add(aCoordinatedEventListener, newListener);
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#addDataChangedListener(DataChangedListener)
	 * @deprecated
	 */
	public void addDataChangedListener(DataChangedListener adl) {
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#getAlternativeDataName()
	 */
	public String getAlternativeDataName() {
		return "";
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#getDataDirection()
	 */
	public String getDataDirection() {
		return Settings.OUTPUT_DIRECTION;
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#getDataName()
	 */
	public String getDataName() 
	{
		return "";
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#getDataValue()
	 */
	public Object getDataValue() {
		return null;
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#getErrorMessage()
	 */
	public String getErrorMessage() {
		return null;
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#getNavigationParameters()
	 */
	public NavigationParameters getNavigationParameters() {
		return null;
	}

	/**
	 * @see com.ibm.dse.gui.PanelActions#getType()
	 */
	public String getType() {
		return null;
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#hasAlternativeDataName()
	 */
	public boolean hasAlternativeDataName() {
		return false;
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#isInError()
	 */
	public boolean isInError() {
		return false;
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#isKeyedValue()
	 */
	public boolean isKeyedValue() {
		return false;
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#isRequired()
	 */
	public boolean isRequired() {
		return false;
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#removeActionListener(ActionListener)
	 */
	public void removeActionListener(ActionListener ae) {
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#removeCoordinatedEventListener(CoordinatedEventListener)
	 */
	public void removeCoordinatedEventListener(CoordinatedEventListener newListener) {
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#removeDataChangedListener(DataChangedListener)
	 */
	public void removeDataChangedListener(DataChangedListener dcl) {
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#setAlternativeDataName(String)
	 */
	public void setAlternativeDataName(String o) {
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#setDataDirection(String)
	 */
	public void setDataDirection(String o) {
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#setDataName(String)
	 */
	public void setDataName(String o) {
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#setDataValue(Object)
	 */
	public void setDataValue(Object o) {
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#setHelpID(String)
	 */
	public void setHelpID(String helpID) {
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#setNavigationParameters(NavigationParameters)
	 */
	public void setNavigationParameters(NavigationParameters navigationParameters) {
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#setRequired(boolean)
	 */
	public void setRequired(boolean req) {
	}

	/**
	 * @see com.ibm.dse.gui.DataExchanger#setType(String)
	 */
	public void setType(String type) {
	}

	/**
	 * @see com.ibm.dse.gui.PanelActions#getDataToClear()
	 */
	public String getDataToClear() {
		return null;
	}

	/**
	 * @see com.ibm.dse.gui.PanelActions#getDSECoordinatedPanel()
	 */
	public DSECoordinatedPanel getDSECoordinatedPanel() {
		return null;
	}

	/**
	 * @see com.ibm.dse.gui.DataExchangerWithList#getDataNameForList()
	 */
	public String getDataNameForList() 
	{
		return dataNameForList;
	}

	/**
	 * @see com.ibm.dse.gui.DataExchangerWithList#setDataNameForList(String)
	 */
	public void setDataNameForList(String dataNameForList) 
	{
		this.dataNameForList = dataNameForList;
	}

	/**
	 * @see com.ibm.dse.gui.DataExchangerWithList#setDataValueForList(Object)
	 */
	public void setDataValueForList(Object dataValueForList) 
	{
		Vector v = null;
		try
		{
			v = (com.ibm.dse.base.Vector)((IndexedCollection)dataValueForList).getValue();
		}
		catch(Exception e)
		{e.printStackTrace();}
		
		// Clear previous messages.
		addMessage(CLEAR_MESSAGES);
		
		while(v.size()>0)
		{
			KeyedCollection k = (KeyedCollection)v.remove(0);

			DataField df = (DataField)k.tryGetElementAt("BusinessMessage");
			if(df instanceof VisualDataField)
			{
				VisualDataField vf = (VisualDataField)df;			
				addMessage((String)vf.getValue(),vf.getForeground());
			}
			else // In case the business message is NOT a VisualDataField just show the message in red.(QuickPatch)
				addMessage((String)df.getValue(),Color.red);
		}
	}
	
	public void fireCoordinationEvent(DSECoordinationEvent event)
	{
		if (aCoordinatedEventListener == null)
			return;
		
		aCoordinatedEventListener.handleDSECoordinationEvent(event);
	}
	
	public static void main(String[] args)
    {
    	JFrame f = new JFrame("Testing MessagesPane");
    	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	MatafMessagesPane mmp = new MatafMessagesPane();
    	JScrollPane scroller = mmp.getLogScrollPane();
    	f.getContentPane().add(scroller);
    	f.setSize(320,200);    	
    	f.setVisible(true);
    	
    	for(int i=0;i<30;i++)
    	{
    		Color c = new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256));
    		mmp.log("בדיקה חלון הודעות",c);
    	}
    }

}
        
