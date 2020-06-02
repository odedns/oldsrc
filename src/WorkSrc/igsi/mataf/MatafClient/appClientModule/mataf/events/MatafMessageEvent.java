package mataf.events;

import com.ibm.dse.gui.ErrorMessageEvent;

/**
 *
 * This class extends the composer's ErrorMessageEvent to ecapsulate
 * additional properties.
 * 
 * @author Nati Dykstein. Creation Date : (13/04/2004 15:22:54).  
 */
public class MatafMessageEvent extends ErrorMessageEvent
{
	public static final String ERROR_MESSAGE 		= "Error";
	public static final String INFORMATION_MESSAGE 	= "Information";
	public static final String LOADING_MESSAGE 		= "Loading";
	
	private String messageType = ERROR_MESSAGE;
	
	/**
	 * @param source
	 */
	public MatafMessageEvent(Object source)
	{
		super(source);
	}

	/**
	 * @param source
	 * @param message
	 */
	public MatafMessageEvent(Object source, String[] messages)
	{
		super(source, messages);
	}

	/**
	 * @param source
	 * @param message
	 */
	public MatafMessageEvent(Object source, String message)
	{
		super(source, message);
	}
	
	/**
	 * A new constructor that allows to init the message type.
	 * 
	 * @param source
	 * @param messages
	 * @param messageType
	 */
	public MatafMessageEvent(Object source, String[] messages, String messageType)
	{
		super(source, messages);
		setMessageType(messageType);
	}

	/**
	 * @return
	 */
	public String getMessageType()
	{
		return messageType;
	}

	/**
	 * @param string
	 */
	public void setMessageType(String string)
	{
		messageType = string;
	}

}
