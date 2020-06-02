/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: FormatterImpl.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.FatalException;

import java.util.*;

/**
 * Implementation of simple formatter (Configurable using XML).
 */
public class FormatterImpl extends Formatter
{
	/**
	 * List of formatter entries
	 */
	private ArrayList formatterEntries;

	public FormatterImpl()
	{
		formatterEntries = new ArrayList();
	}

	/**
	 * Used to perform formatting
	 *
	 * @param from context to copy parameters from
	 * @param to context to copy parameters to
	 * @throws ContextException thrown if one of parameters does not allowed for context
	 */
	public void format(Context from, Context to) throws FormatterException, ContextException, FatalException, AuthorizationException
	{
		Object valueTo;
		//run over list of formatter entries
        for (int i = 0; i < formatterEntries.size(); i++)
        {
	        FormatterEntry formatterEntry = (FormatterEntry) formatterEntries.get(i);

			if(formatterEntry.fromValue != null)
			{
				valueTo = formatterEntry.fromValue;
			}
			else
			{
				valueTo = from.getField(formatterEntry.fromName); 
			}
            to.setField(formatterEntry.toName, valueTo);
        }
	}

	/**
	 * The method has no sence because no parameters can be passed to simple formatter
	 *
	 */
	public void initialize(ParameterList params)
	{
		//not implemented
		throw new RuntimeException("Method not implemented");
	}

	/**
	 * Used by framework while building formatter
	 *
	 * @param fromName The name of field in the context from which data is copied
	 * @param fromValue The constant value to set in the context 
	 * @param toName The name of field in the context to which data is copied
	 */
	public void addFormatterEntry(String fromName, Object fromValue, String toName)
	{
        FormatterEntry fe = new FormatterEntry();
		fe.fromName = fromName;
		fe.fromValue = fromValue;
		fe.toName = toName;

		formatterEntries.add(fe);
	}

	/**
	 * Used internally to keey type - name pairs
	 */
	private class FormatterEntry
	{
		String fromName;
		Object fromValue;
		String toName;
	}
}
