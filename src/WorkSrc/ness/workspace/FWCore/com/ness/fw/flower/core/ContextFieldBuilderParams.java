/*
 * Created on: 18/11/2004
 * Author: yifat har-nof
 * @version $Id: ContextFieldBuilderParams.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import com.ness.fw.common.resources.LocalizedResources;
import com.ness.fw.util.MessagesContainer;

/**
 * 
 */
public class ContextFieldBuilderParams
{

	private MessagesContainer messagesContainer;
	
	private LocalizedResources localizable;

	private boolean checkAuthorization = false;
	

	/**
	 * 
	 */
	public ContextFieldBuilderParams(MessagesContainer messagesContainer, LocalizedResources localizable, boolean checkAuthorization)
	{
		this.checkAuthorization = checkAuthorization;
		this.messagesContainer = messagesContainer;
		this.localizable = localizable;
	}

	/**
	 * @return
	 */
	public boolean isCheckAuthorization()
	{
		return checkAuthorization;
	}

	/**
	 * @return
	 */
	public LocalizedResources getLocalizable()
	{
		return localizable;
	}

	/**
	 * @return
	 */
	public MessagesContainer getMessagesContainer()
	{
		return messagesContainer;
	}

}
