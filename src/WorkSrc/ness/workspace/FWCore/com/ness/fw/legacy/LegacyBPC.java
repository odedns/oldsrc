/*
 * Created on: 13/10/2004
 * Author: yifat har-nof
 * @version $Id: LegacyBPC.java,v 1.2 2005/03/22 13:23:10 yifat Exp $
 */
package com.ness.fw.legacy;

import com.ness.fw.bl.BasicContainer;
import com.ness.fw.bl.BusinessProcessContainer;
import com.ness.fw.common.auth.UserAuthData;

/**
 * 	The container that contains the arguments for the SP call.
 */
public class LegacyBPC extends BusinessProcessContainer
{
	/**
	 * The name of the legacy command to execute.
	 */
	private String legacyCommandName;

	/**
	 * The input container to pass the legacy call. 
	 */
	private BasicContainer inputContainer;

	/**
	 * Creates new LegacyBPC object.
	 * @param userAuthData Contains the basic authorization data of the current user.
	 * @param legacyCommandName The name of the legacy command to execute.
	 * @param bpc The input container to pass the legacy call. 
	 */
	public LegacyBPC(UserAuthData userAuthData, String legacyCommandName, BasicContainer inputContainer)
	{
		super(userAuthData);
		
		this.legacyCommandName = legacyCommandName;
		this.inputContainer = inputContainer;
	}

	/**
	 * Creates new LegacyBPC object.
	 * @param legacyCommandName The name of the legacy command to execute.
	 * @param bpc The input container to pass the legacy call. 
	 */
	public LegacyBPC(String legacyCommandName, BasicContainer inputContainer)
	{
		super();
		
		this.legacyCommandName = legacyCommandName;
		this.inputContainer = inputContainer;
	}

	/**
	 * Returns the name of the legacy command to execute.
	 * @return String
	 */
	public String getLegacyCommandName()
	{
		return legacyCommandName;
	}

	/**
	 * Sets the name of the legacy command to execute.
	 * @param legacyCommandName
	 */
	public void setLegacyCommandName(String legacyCommandName)
	{
		this.legacyCommandName = legacyCommandName;
	}

	/**
	 * Returns the input container to pass the legacy call. 
	 * @return BasicContainer
	 */
	public BasicContainer getInputContainer()
	{
		return inputContainer;
	}

}
