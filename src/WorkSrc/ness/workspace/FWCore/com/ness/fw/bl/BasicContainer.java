/*
 * Author: yifat har-nof
 * @version $Id: BasicContainer.java,v 1.3 2005/04/14 14:01:45 yifat Exp $
 */
package com.ness.fw.bl;

import java.io.Serializable;

import com.ness.fw.common.auth.UserAuthData;
import com.ness.fw.util.Message;
import com.ness.fw.util.MessagesContainer;

/**
 * Basic Container for the business layer objects.
 */
public abstract class BasicContainer implements Serializable, Cloneable
{
	/**
	 * Contains the basic authorization data of the current user.
	 */
	private UserAuthData userAuthData;

	/**
	 * The {@link MessageContainer} that containes the application messages 
	 */
	private MessagesContainer messagesContainer;
	

	/**
	 * Creates new BasicContainer Object
	 */
	public BasicContainer()
	{
		super();
	}

	/**
	 * Creates new BasicContainer Object
	 * @param userAuthData Contains the basic authorization data of the current user.
	 */
	public BasicContainer(UserAuthData userAuthData)
	{
		super();
		this.userAuthData = userAuthData;
	}

	/**
	 * Creates new BasicContainer Object
	 * @param basicContainer The <code>BasicContainer</code> to pass the basic parameters.
	 */
	public BasicContainer(BasicContainer basicContainer)
	{
		super();
		this.userAuthData = basicContainer.getUserAuthData();
	}


	/**
	 * Returns the current user id.
	 * @return String userId
	 */
	public String getUserId()
	{
		if(userAuthData != null)
		{
			return userAuthData.getUserID();
		}
		return null;
	}

	/**
	 * Sets the UserAuthData contains the basic authorization data of the current user.
	 * @param userAuthData
	 */
	public void setUserAuthData(UserAuthData userAuthData)
	{
		this.userAuthData = userAuthData;
	}

	/**
	 * Returns the UserAuthData contains the basic authorization data of the current user.
	 * @return UserAuthData
	 */
	public UserAuthData getUserAuthData()
	{
		return userAuthData;
	}

	/**
	 * add a new message to the message container. 
	 * @param message The message to add.
	 */
	public void addMessage(Message message)
	{
		if (messagesContainer == null)
			messagesContainer = new MessagesContainer();

		messagesContainer.addMessage(message);
	}

	/**
	 * add all the messages from the container to the current one. 
	 */
	public void addMessages(MessagesContainer container)
	{
		if(container != null)
		{
			if (messagesContainer == null)
				messagesContainer = new MessagesContainer();

			messagesContainer.merge(container);			
		}
	}


	/**
	 * Returns the {@link MessageContainer} that containes the application messages
	 * @return MessagesContainer
	 */
	public MessagesContainer getMessagesContainer()
	{		
		return messagesContainer;
	}
	
	/**
	 * Returns the messages count.
	 * @return int
	 */
	public int getMessagesCount()
	{
		if(messagesContainer != null)
			return messagesContainer.getMessagesCount();
		return 0;
	}
	

}
