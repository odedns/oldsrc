/*
 * Created on 06/04/2005
 *
 * Author Amit Mendelson
 * @version $Id: UserLdapDetails.java,v 1.2 2005/04/19 16:14:01 amit Exp $
 */
package com.ness.fw.workflow;

/**
 * Assistance class used to retrieve user details from Ldap.
 * 
 */
public class UserLdapDetails
{
	//User Id
	private String userId;
	
	//User private name. For a virtual user, this field will contain the organization.
	private String userPrivateName;
	
	//User family name. For a virtual user, this field will contain the role.
	private String userFamilyName;

	/**
	 * Constructor.
	 * @param userId
	 * @param userPrivateName
	 * @param userFamilyName
	 */	
	public UserLdapDetails(String userId, String userPrivateName, String userFamilyName)
	{
		this.userId = userId;
		this.userPrivateName = userPrivateName;
		this.userFamilyName = userFamilyName;
	}
	
	/**
	 * 
	 * @param userId
	 */
	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	/**
	 * @return
	 */
	public String getUserFamilyName()
	{
		return userFamilyName;
	}

	/**
	 * @return
	 */
	public String getUserId()
	{
		return userId;
	}

	/**
	 * @return
	 */
	public String getUserPrivateName()
	{
		return userPrivateName;
	}

	/**
	 * @param string
	 */
	public void setUserFamilyName(String string)
	{
		userFamilyName = string;
	}

	/**
	 * @param string
	 */
	public void setUserPrivateName(String string)
	{
		userPrivateName = string;
	}

}
