/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ExtendedTransitionSupplierFactory.java,v 1.1 2005/02/21 15:07:09 baruch Exp $
 */
package com.ness.fw.flower.factory;

import java.util.ArrayList;
import java.util.Iterator;

import com.ness.fw.flower.core.*;

/**
 * Used to supply instances of <code>ExtendedTransitionSupplier</code> using data contained in context
 */
public abstract class ExtendedTransitionSupplierFactory
{
	private static ExtendedTransitionSupplierFactory supplierFactory;

	/**
	 * 
	 * initialize ExtendedTransitionSupplier.
	 * 
	 * <b>
	 * Must be called in the scope of lock in the ControllerServlet.
	 * <b>
	 * 
	 * @param initString
	 * @throws ExtendedTransitionSupplierFactoryException
	 */
//	public static void initialize(String initString) throws ExtendedTransitionSupplierFactoryException
//	{
//		supplierFactory = new ExtendedTransitionSupplierFactoryXML(initString);
//	}

	/**
	 * 
	 * initialize ExtendedTransitionSupplier.
	 * 
	 * <b>
	 * Must be called in the scope of lock in the ControllerServlet.
	 * <b>
	 * 
	 * @param rootsPath
	 * @throws ExtendedTransitionSupplierFactoryException
	 */
	public static void initialize(ArrayList rootsPath) throws ExtendedTransitionSupplierFactoryException
	{
		supplierFactory = new ExtendedTransitionSupplierFactoryXML(rootsPath);
	}




	public static ExtendedTransitionSupplierFactory getInstance()
	{
		return supplierFactory;
	}

	/**
    * Used to supply instances of <code>ExtendedTransitionSupplier</code> using data contained in context
	*/
	public abstract ExtendedTransitionSupplier getTransitionSupplier(String supplierName);

	public abstract ExtendedTransitionSupplier getGlobalTransitionSupplier();	
	
	/**
	 * Returns the ids of all the supplier groups.
	 * @return Iterator
	 */
	public abstract Iterator getSupplierGroups ();
	
}
