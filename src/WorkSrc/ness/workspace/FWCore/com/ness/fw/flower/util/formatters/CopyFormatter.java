/*
 * Created on: 5/11/2003
 * Author: yifat har-nof
 * @version $Id: CopyFormatter.java,v 1.1 2005/02/21 15:07:19 baruch Exp $
 */
package com.ness.fw.flower.util.formatters;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.flower.core.*;

import java.util.*;

/**
 * The formatter copy fields data from one context to another
 * All the fields must be exist in the destination context
 * with the same name and type.
 */
public class CopyFormatter extends Formatter
{
	
	/* (non-Javadoc)
	 * @see com.ness.fw.flower.core.Formatter#format(com.ness.fw.flower.core.Context, com.ness.fw.flower.core.Context)
	 */
	public void format(Context from, Context to) throws FormatterException, ContextException, FatalException, AuthorizationException
	{
		Iterator it = from.getFieldNamesIterator();
		while (it.hasNext())
		{
			String fieldName = (String) it.next();
			to.setField(fieldName, from.getField(fieldName));
		}
	}

	/* (non-Javadoc)
	 * @see com.ness.fw.flower.core.Formatter#initialize(com.ness.fw.flower.core.ParameterList)
	 */
	public void initialize(ParameterList parametersList) throws FormatterException
	{
	}

}
