package com.ness.fw.ui;

import com.ness.fw.common.exceptions.UIException;

public class MessagesModel extends AbstractModel {

	/**
	 * Constant for "close" messagesState of a messages area.
	 */
	public final static int MESSAGES_CLOSE = 0;

	/**
	 * Constant for "open" messagesState of a messages area.
	 */
	public final static int MESSAGES_OPEN = 1;
	
	private final static String MESSAGES_MODEL_STATE = "messages";
	
	private final static int DEFAULT_STATE = MESSAGES_CLOSE;
	
	private int messagesState = DEFAULT_STATE;
	private int normalHeight = -1;
	private int expandHeight = -1;
	
	/**
	 * Handles events that affect the MessagesModel
	 */
	protected void handleEvent(boolean checkAuthorization) throws UIException 
	{
		if (getProperty(MESSAGES_MODEL_STATE) != null)
		{
			messagesState = Integer.parseInt((String)getProperty(MESSAGES_MODEL_STATE));
		}
	}
	
	/**
	 * Returns the height of the messages area when it is expanded(opened).
	 * @return The height of the messages area when it is expanded(opened).
	 */
	public int getExpandHeight()
 	{
		return expandHeight;
	}

	/**
	 * Returns the height of the messages area when it is normal(opened).
	 * @return The height of the messages area when it is normal(opened).
	 */
	public int getNormalHeight() 
	{
		return normalHeight;
	}

	/**
	 * Sets the height of the messages area when it is expanded(opened).
	 * @param expandHeight the height of the messages area when it is expanded(opened).
	 */
	public void setExpandHeight(int expandHeight)
	{
		this.expandHeight = expandHeight;
	}

	/**
	 * Sets the height of the messages area when it is normal(closed).
	 * @param normaHeight the height of the messages area when it is normal(closed).
	 */
	public void setNormalHeight(int normaHeight) {
		this.normalHeight = normaHeight;
	}

	/**
	 * Returns the messagesState of the messages model - MESSAGES_CLOSE when it is 
	 * normal or MESSAGES_OPEN when it is expanded.
	 * @return the messagesState of the messages model(MESSAGES_CLOSE or MESSAGES_OPEN)
	 */
	public int getMessagesState() 
	{
		return messagesState;
	}

	/**
	 * Sets the messagesState of the messages model - MESSAGES_CLOSE or MESSAGES_OPEN.
	 * @param messagesState the new messagesState of the messages model.
	 */
	public void setMessagesState(int state) 
	{
		this.messagesState = state;
	}

}
