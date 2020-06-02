/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: GuardFactory.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import com.ness.fw.flower.factory.*;

/**
 * Used to create instances of guard using boolean expression
 */
public abstract class GuardFactory
{

	public static Guard createGuard(String condition) throws GuardException
	{
		return new GuardIntrospection(condition);
	}
}
