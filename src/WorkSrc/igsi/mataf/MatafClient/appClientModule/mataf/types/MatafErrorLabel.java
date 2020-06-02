package mataf.types;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import mataf.borders.CustomizableLineBorder;
import mataf.utils.FontFactory;

import com.ibm.dse.gui.SpErrorLabel;

/**
 * Class holds a queue of error messages and displays each one for at least
 * a DELAY amount of time.
 * The class implements the Producer-Consumer design pattern.
 */
public class MatafErrorLabel extends SpErrorLabel 
									implements Runnable
{
	private static final Color 	BORDER_COLOR = new Color(94,127,172);
	
	private static final Font	FONT = FontFactory.createFont("Arial", Font.BOLD,14);
	
	/** 2 Seconds delay betweem 2 messages.*/
	private static final int DELAY = 2000;
	
	private static final Border ERROR_BORDER = 
									new CustomizableLineBorder(Color.red,1,0,0,0);
	private static final Border BORDER = 
										new CustomizableLineBorder(BORDER_COLOR,1,0,0,0);
	
	private static final ImageIcon errorIcon;

    private Thread		 reader;
    private Vector		 messagesQueue;
    
    static
    {
    	errorIcon =	new ImageIcon(ClassLoader.getSystemResource("images/Desktop/ikon_error.gif"));
    }
    
    public MatafErrorLabel()
    {
    	super();
    	
    	setText(" ");
    	
    	initComponent();
    	
    	// Init the message queue.
    	messagesQueue = new Vector();
    	
    	// Init and start the reader thread.
    	reader = new Thread(this,"ErrorLabelQueue-Thread");
    	reader.start();
    }
    
    private void initComponent()
    {
		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		
		setHorizontalTextPosition(SwingConstants.LEFT);
		setOpaque(false);
		
		Border empty = BorderFactory.createEmptyBorder(2, 2, 2, 2);
		Border line = new CustomizableLineBorder(MatafEmbeddedPanel.BORDER_COLOR, 1, 0, 0, 0);
		setBorder(BorderFactory.createCompoundBorder(line, empty));

		setFont(FONT);
		
		setPreferredSize(new Dimension(100,18));
					
		setIconTextGap(8);
    }
    
    public void queueErrorMessage(String errorMsg)
    {
    	queueErrorMessage(errorMsg, Color.red);
    }
    
    /**
     * Method adds a new message to the error messages queue and
     * notifies the reader thread.
     */
    public void queueErrorMessage(String errorMsg,Color errorMsgColor)
    {
    	messagesQueue.add(new Message(errorMsg, errorMsgColor));
    	synchronized(this)
    	{
    		notify();
    	}
    }
    
    /**
     * Reader thread displays error messge from the queue.
     * Keeps an interval of time between each message.
     */
    public void run()
    {
    	while(true)
    	{
    		try
    		{
	    		// Check if there are messages waiting.
   		 		while(messagesQueue.isEmpty())
   		 		{
   		 			// Wait for new messages.
   		 			synchronized(this)
   		 			{
   		 				wait();
   		 			}
   		 		} 
    				
				// Display current message.
    			Message msg = (Message)messagesQueue.remove(0);
	    		setText(msg.getMessage());
    			setForeground(msg.getColor());
    		
    			// If message is not empty, wait the specified amount of time.
    			if(msg.getMessage()!=null && !msg.getMessage().equals(""))
    				Thread.sleep(DELAY);
    		}
    		catch(InterruptedException e)
    		{e.printStackTrace();}
    			
   	 	}
    }
    
    /**
	 * DEBUG : Error while sending an empty array to setListText
	 */
	public void setListText(String[] listText) 
	{
		if(listText==null)
			listText = new String[]{"נשלחה שגיאה ריקה !"};
		
		super.setListText(listText);
	}
	
	public void showErrorIcon()
	{
		setIcon(errorIcon);
		setBorder(ERROR_BORDER);
		repaint();
	}
	
	public void hideErrorIcon()
	{
		setIcon(null);
		setBorder(BORDER);
	}

	/**
	 * Inner class for encapsulating the message's required information.
	 */
	private class Message
	{
		private String message;
		private Color  color;
		
		Message()
		{
			this("Empty",Color.black);
		}
		
		Message(String message)
		{
			this(message, Color.black);
		}
		
		Message(String message, Color color)
		{
			this.message = message;
			this.color = color;
		}
		/**
		 * Returns the color.
		 * @return Color
		 */
		Color getColor() {
			return color;
		}

		/**
		 * Returns the message.
		 * @return String
		 */
		String getMessage() {
			return message;
		}

		/**
		 * Sets the color.
		 * @param color The color to set
		 */
		void setColor(Color color) {
			this.color = color;
		}

		/**
		 * Sets the message.
		 * @param message The message to set
		 */
		void setMessage(String message) {
			this.message = message;
		}

	}
}
