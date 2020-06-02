/*
 * Created on: 11/01/2005
 * @author: baruch hizkya
 * @version $Id: EJBLocatorFactory.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */

package com.ness.fw.bl.proxy;

/**
 * A factory for lookping ejb
 */
public class EJBLocatorFactory
{
	public static EJBLocator findEJB()
	{
		return new Websphere50EJBLocator();
	}
}
