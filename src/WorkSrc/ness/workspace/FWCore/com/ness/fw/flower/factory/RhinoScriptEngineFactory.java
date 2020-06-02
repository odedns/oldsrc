/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: RhinoScriptEngineFactory.java,v 1.1 2005/02/21 15:07:09 baruch Exp $
 */
package com.ness.fw.flower.factory;

import org.mozilla.javascript.*;


public abstract class RhinoScriptEngineFactory
{
	public static Context scriptableContext = Context.enter();

	public static Context getScriptableContext()
	{
		return scriptableContext;
	}
}
