/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: DOMList.java,v 1.1 2005/02/21 15:07:14 baruch Exp $
 */
package com.ness.fw.common.externalization;

import org.w3c.dom.*;

import java.util.*;


/**
 * Implementation of list of XML documents based on <code>ArrayList</code> implementation
 */
public class DOMList
{
	private ArrayList list;

	public DOMList()
	{
		list = new ArrayList();
	}

	public void addDocument(Document doc)
	{
		for (int i = 0; i < list.size(); i++)
		{
			Document document = (Document) list.get(i);
			if (document.equals(doc))
			{
				return;
			}
		}

		list.add(doc);
	}

	public Document getDocument(int index)
	{
		return (Document) list.get(index);
	}

	public int getDocumentCount()
	{
		return list.size();
	}
}
