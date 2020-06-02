/*
 * Author: yifat har-nof, Shay Rancus, Baruch Hizkya
 * @version $Id: SystemUtil.java,v 1.1 2005/02/21 15:07:14 baruch Exp $
 */
package com.ness.fw.shared.common;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.ness.fw.bl.proxy.BPOCommandException;

/**
 * A utility class with helper methods for handling Throwables and global fields 
 * such as global message container, global dirty flag etc.  
 */
public class SystemUtil
{
	/**
	 * Convert the throwable message & stack trace into a formatted String.
	 * @param throwable 
	 * @return String 
	 */
	public static String convertThrowable2String(Throwable throwable)
	{
		StringBuffer sb = new StringBuffer(1024);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		throwable.printStackTrace(pw);
		sb.append(sw.toString());
		return sb.toString();
	}

	public static void copyContainerData(Object originalContainer, Object changedContainer) throws BPOCommandException 
	{
		Object params[] = new Object[] { null };
		try
		{
			Class mClass = changedContainer.getClass();
			// Get all bean properties
			BeanInfo bi = Introspector.getBeanInfo(mClass);
			// Get PropertyDescriptors describing the editable
			// properties supported by this bean
			PropertyDescriptor[] descriptors = bi.getPropertyDescriptors();
			for (int i = 0; i < descriptors.length; ++i)
			{
				PropertyDescriptor descriptor = descriptors[i];
				Class fieldClass = descriptor.getPropertyType();
				if (fieldClass != null)
				{				
					// get the writeMethod for this field
					Method writeMethod = descriptor.getWriteMethod();					
					if (writeMethod != null)
					{
						// Getting the returnValue by calling the getter method
						// from the changed bpc
						Method readMethod = descriptor.getReadMethod();
						Object obj = readMethod.invoke(changedContainer, null);
						params[0] = obj;
						// Setting the value to the original bpc
						writeMethod.invoke(originalContainer, params);
					}
				}
			}
		}
		catch (IntrospectionException e)
		{
			throw new BPOCommandException("problem in introspection class " + changedContainer.getClass().getName() , e);
		}
		catch (IllegalArgumentException e)
		{
			throw new BPOCommandException("problem in introspection class " + changedContainer.getClass().getName() , e);
		}
		catch (IllegalAccessException e)
		{
			throw new BPOCommandException("problem in introspection class " + changedContainer.getClass().getName() , e);
		}
		catch (InvocationTargetException e)
		{
			throw new BPOCommandException("problem in introspection class " + changedContainer.getClass().getName() , e);
		}
	}

}
