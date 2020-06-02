/*
 * Created on: 25/11/2004
 * Author: baruch hizkya
 * @version $Id: XMLEntityResolver.java,v 1.1 2005/02/21 15:07:14 baruch Exp $
 */
package com.ness.fw.common.externalization;

import java.io.InputStream;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/**
 * An {@link EntityResolver} specifically designed to return
 * <code>flower's dtd files</code> which are embedded within the fw jar file. 
 */
public class XMLEntityResolver implements EntityResolver
{

	/**
	 * Resolve the entity according to it's name
	 */
	public InputSource resolveEntity(String publicId, String systemId)
	{
		// acorrding to the specific systemId find the relvantive
		// dtd file
		
		if (systemId.endsWith("flower.dtd"))
		{
			return getInputSource("/flower.dtd");
		}
		else if (systemId.endsWith("bpo.dtd"))
		{
			return getInputSource("/bpo.dtd");
		}
		else if (systemId.endsWith("menu.dtd"))
		{
			return getInputSource("/menu.dtd");
		}
		else if (systemId.endsWith("controller_servlet.dtd"))
		{
			return getInputSource("/controller_servlet.dtd");
		}
		else if (systemId.endsWith("legacy.dtd"))
		{
			return getInputSource("/legacy.dtd");
		}
		else if (systemId.endsWith("language.dtd"))
		{
			return getInputSource("/language.dtd");
		}
		else if (systemId.endsWith("system.dtd"))
		{
			return getInputSource("/system.dtd");
		}
		else if (systemId.endsWith("server.dtd"))
		{
			return getInputSource("/server.dtd");
		}

		// no match, return null, which is the default behaviour
		else
		{
			return null;
		}
	}
	
	/**
	 * return an InputSource, that, a dtd file according to it's name
	 * @param dtd
	 * @return
	 */
	private InputSource getInputSource(String dtd)
	{
		Class clazz = getClass();
		InputStream in = clazz.getResourceAsStream(dtd);
		if (in == null)
		{
			return null;
		}
		else
		{
			return new InputSource(in);
		}

	}
}
