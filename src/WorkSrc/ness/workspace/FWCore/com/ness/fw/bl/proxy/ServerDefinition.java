/*
 * Created on: 30/01/2005
 * @author: baruch hizkya
 * @version $Id: ServerDefinition.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */

package com.ness.fw.bl.proxy;

import java.util.Properties;

import javax.naming.Context;

/**
 * A class that represent server definition whice will use the {@link BPOProxy}
 * @author bhizkya
 *
 */
public class ServerDefinition
{
	private String serverName;
	private String url;
	private int implementationType;
	private String jndiName;
	private boolean isDefault;
	private String refName;

	/**
	 * default contructor
	 *
	 */
	public ServerDefinition()
	{
	}
	
	/**
	 * constructor 
	 * @param serverName the logic server name
	 * @param url the url of the server (only for ejb/ws)
	 * @param type the server type (local/ejb/ws)
	 * @param jndiName the jndi name of the entity bean
	 * @param isDefault mention if this server is default
	 * @param refName the reference jndi name of the entity bean
	 */
	public ServerDefinition(String serverName, String url,int type,String jndiName,boolean isDefault, String refName)
	{
		this.serverName = serverName;
		this.url = url;
		this.implementationType = type;
		this.jndiName = jndiName;
		this.isDefault = isDefault;	
		this.refName = refName;	
	}

	/**
	 * @return if the server is the default one
	 */
	public boolean getIsDefault()
	{
		return isDefault;
	}

	/**
	 * @return the jndi name of the bean
	 */
	public String getJndiName()
	{
		return jndiName;
	}

	/**
	 * @return the reference name of the bean
	 */
	public String getRefName()
	{
		return refName;
	}

	/**
	 * @return the implementation type of the system (local,ejb,ws)
	 */
	public int getImplementationType()
	{
		return implementationType;
	}

	/**
	 * @return the url string representing the remove computer that has the bean
	 */
	public String getUrl()
	{
		return url;
	}

	/**
	 * @param isDefault
	 */
	public void setIsDefault(boolean isDefault)
	{
		this.isDefault = isDefault;
	}

	/**
	 * @param jndiName
	 */
	public void setJndiName(String jndiName)
	{
		this.jndiName = jndiName;
	}

	/**
	 * @param refName
	 */
	public void setRefName(String refName)
	{
		this.refName = refName;
	}

	/**
	 * @param type
	 */
	public void setType(int type)
	{
		this.implementationType = type;
	}

	/**
	 * @param url
	 */
	public void setUrl(String url)
	{
		this.url = url;
	}
	
	/**
	 * Return Properties object for init the InitialContext
	 * @return Properties
	 */
	public Properties getEJBProperties()
	{
		Properties props = new Properties();
		props.put(Context.PROVIDER_URL, url);	
		return props;		
	}

	/**
	 * @return String. the logic server name
	 */
	public String getServerName()
	{
		return serverName;
	}

	/**
	 * @param serverName
	 */
	public void setServerName(String serverName)
	{
		this.serverName = serverName;
	}

}
