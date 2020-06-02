/*
 * Created on: 15/12/2003
 * Author: yifat har-nof
 * @version $Id: MessageParameter.java,v 1.1 2005/02/21 15:07:15 baruch Exp $
 */
package com.ness.fw.util;

import java.io.Serializable;

/**
 * Represent a parameter for a message in the MessagesContainer. 
 */
public class MessageParameter implements Serializable, Cloneable
{
	public static final int TYPE_LOCALIZED          =   1;
	public static final int TYPE_NON_LOCALIZED      =   2;

	private String value;
	private int type;

	public MessageParameter(String value, int type)
	{
		this.value = value;
		this.type = type;
	}

	public String getValue()
	{
		return value;
	}

	public int getType()
	{
		return type;
	}

	public boolean isLocalized()
	{
		return type == TYPE_LOCALIZED;
	}
}
