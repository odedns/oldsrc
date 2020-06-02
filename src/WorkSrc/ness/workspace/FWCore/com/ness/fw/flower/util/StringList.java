/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: StringList.java,v 1.1 2005/02/21 15:07:19 baruch Exp $
 */
package com.ness.fw.flower.util;

import java.util.*;

/**
 * Represent a List of string values.
 */
public class StringList
{
	private ArrayList list;

	public StringList()
	{
		list = new ArrayList();
	}

	public StringList(String s, String delimiter)
	{
		this();
		for (StringTokenizer stringTokenizer = new StringTokenizer(s, delimiter); stringTokenizer.hasMoreTokens();)
		{
			addString(stringTokenizer.nextToken().trim());
		}
	}

	public String getString(int index)
	{
		return (String) list.get(index);
	}

	public void addString(String s)
	{
		list.add(s);
	}

	public int getStringsCount()
	{
		return list.size();
	}

	public boolean contains(String name)
	{
		for (int i = 0; i < list.size(); i++)
		{
			if (list.get(i).equals(name))
			{
				return true;
			}
		}

		return false;
	}
}
