/*
 * Created on 20/12/2004
*/
package com.ness.fw.util;

/**
 * @author Oren Lavie
 * @version $Id: 
*/
public class GeneralUtilities
{

	/**
	 * check if the two objects are equal with respect of being both null.
	 * @param object1
	 * @param object2
	 * @return
	 */
	public static boolean isEquals(Object object1, Object object2)
	{
		if (object1 == null && object2 == null)
		{
			return true;
		}
		else if (object1 == null || object2 == null)
		{
			return false;
		}
		else
		{
			return object1.equals(object2);
		}
	}

	/**
	 * return the className without its Path
	 * @param object
	 * @return
	 */
	public static String getClassName(Object object)
	{
		if (object == null)
		{
			return null;
		}
		String fullClassName = object.getClass().getName();
		int startOfName = fullClassName.lastIndexOf('.');
		return fullClassName.substring(startOfName + 1, fullClassName.length());
	}
}