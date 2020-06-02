/*
 * Created on: 24/06/2003
 * Author: yifat har-nof
 * @version $Id: ExtendedTransitionSupplierList.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import java.util.*;

/**
 * Implementation of list of ExtendedTransitionSupplier's based on ArrayList
 */
public class ExtendedTransitionSupplierList
{
	private ArrayList list;

	public ExtendedTransitionSupplierList()
	{
		list = new ArrayList();
	}

	public ExtendedTransitionSupplier getExtendedTransitionSupplier(int index)
	{
		return (ExtendedTransitionSupplier) list.get(index);
	}

	public void addExtendedTransitionSupplier(ExtendedTransitionSupplier extendedTransitionSupplier)
	{
		list.add(extendedTransitionSupplier);
	}

	public int getExtendedTransitionSuppliersCount()
	{
		return list.size();
	}
}
