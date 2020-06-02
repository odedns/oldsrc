/*
 * Created on: 24/11/2004
 * Author: Amit Mendelson
 * @version $Id: WFLdapManager.java,v 1.17 2005/05/04 13:53:42 amit Exp $
 */
package com.ness.fw.workflow;

import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.resources.SystemResources;

import com.ness.fw.util.LdapUtil;
import com.ness.fw.util.LdapException;
import javax.naming.directory.*;
import javax.naming.*;

import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

/**
 * This class supports workflow LDAP operations.
 * Note that the operations do not support LDAP hierarchy except in organizations:
 * Each person is below "ou=people, o=company", and each role is below
 * "ou=roles, o=company". The organization can be under a parent organization,
 * like "ou=team2, ou=team1, ou=organizations, o= company".
 */
public class WFLdapManager
{
	public static final String LOGGER_CONTEXT = "WFLdapManager";
	
	/**
	 * Instance of DirContext.
	 */
	private DirContext ldapContext = null;
	


	/**
	 * 
	 */
	public WFLdapManager() throws LdapException
	{
		Logger.debug(LOGGER_CONTEXT, "started WFLdapManager initialization");

		SystemResources resources = SystemResources.getInstance();
		String providerURL = 
			resources.getProperty(LdapUtil.PROVIDER_URL_KEY);
		String securityAuthentication =
			resources.getProperty(LdapUtil.SECURITY_AUTHENTICATION_KEY);

		String securityPrincipal =
			resources.getProperty(LdapUtil.SECURITY_PRINCIPAL_KEY);

		String securityCredentials =
			resources.getProperty(LdapUtil.SECURITY_CREDENTIALS_KEY);
		
		ldapContext = 
			LdapUtil.ldapConnect(providerURL, securityAuthentication, 
			securityPrincipal, securityCredentials);

		Logger.debug(
			LOGGER_CONTEXT,
			"completed method init of WFLdapManager");

	}

	/**
	 * Assistance method for unifying the creation of virtual user names.
	 * @param role
	 * @param organization
	 * @return String the virtual user name (ID) in LDAP.
	 */
	public String createVirtualUserName(String role, String organization)
	{
		Logger.debug(
			LOGGER_CONTEXT,
			"called createVirtualUserName, role: "+role + ", organization: "+organization);
		
		return role+WFLdapConstants.ROLE_ORGANIZATION_DELIMITER+organization;
	}

	/**
	 * Assistance method.
	 * @param userId
	 * @return boolean - true if the userId already exists, false otherwise.
	 * @throws LdapException
	 */
	public boolean doesUserExist(String userId) throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method doesUserExist, userId: "+userId);

		NamingEnumeration enumeration = 
			LdapUtil.search(WFLdapConstants.PEOPLE_IN_COMPANY,
			WFLdapConstants.CN_EQUALS_PREFIX+userId,ldapContext,
			LdapUtil.SEARCH_CONTROLS_SUBTREE_SCOPE);
		Set s =  LdapUtil.generateResultSet(enumeration);
		if(s==null)
		{
			return false;
		}
		if(s.contains(userId.toUpperCase())) {
			return true;			
		} else {
			return false;		
		}
	}

	/**
	 * Assistance method.
	 * @param role
	 * @return boolean - true if the role already exists, false otherwise.
	 * @throws LdapException
	 */
	public boolean doesRoleExist(String role) throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method doesRoleExist, role: "+role);

		NamingEnumeration enumeration = 
			LdapUtil.search(WFLdapConstants.ROLES_IN_COMPANY,
			WFLdapConstants.CN_EQUALS_PREFIX+role,ldapContext,
			LdapUtil.SEARCH_CONTROLS_SUBTREE_SCOPE);
		Set s =  LdapUtil.generateResultSet(enumeration);
		if(s==null)
		{
			return false;
		}
		if(s.contains(role.toUpperCase())) {
			return true;			
		} else {
			return false;		
		}
	}

	/**
	 * Assistance method.
	 * @param organization
	 * @return boolean - true if the organization already exists, false otherwise.
	 * @throws LdapException
	 */
	public boolean doesOrganizationExist(String organization) throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method doesOrganizationExist, organization: "+organization);

		NamingEnumeration enumeration = 
			LdapUtil.search(WFLdapConstants.ORGANIZATIONS_IN_COMPANY,
			WFLdapConstants.OU_EQUALS_PREFIX+organization+"*",ldapContext,
			LdapUtil.SEARCH_CONTROLS_SUBTREE_SCOPE);
		Set s =  LdapUtil.generateResultSet(enumeration);
		
		if(s==null)
		{
			return false;
		}

		if(s.contains(organization.toUpperCase())) {
			return true;			
		} else {
			return false;		
		}
	}

	/**
	 * 
	 * @param team
	 * @param rank
	 * @return
	 * @throws LdapException
	 */
	public boolean doesUserExistWithGivenTeamAndRank(String team, Integer rank)
		throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method doesUserExistWithGivenTeamAndRank, team: "+
			team + ", rank: "+rank);

		Set set = searchUsersWithGivenOrganizationName(team);
		Iterator iterator = set.iterator();
		String teamName = findHierarchyToRoot(team)+
			WFLdapConstants.ORGANIZATIONS_IN_COMPANY_SUFFIX;
		while(iterator.hasNext())
		{
			//for each context check it
			Attributes attributes = new BasicAttributes();
			attributes.put(WFLdapConstants.AUTHORIZATION_LEVEL,rank);
			NamingEnumeration enumeration = LdapUtil.search(teamName, attributes, ldapContext);
			if(enumeration!=null)
			{
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Look-up for users with the specific attribute value.
	 * @param attributeName
	 * @param attributeValue
	 * @return set of the applicable users.
	 * @throws LdapException
	 */
	public Set searchUsersWithGivenOrganizationName(String organization)
		throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method searchUsersWithGivenOrganizationName, organization: "+organization);

		String attributeValue = 
			WFLdapConstants.OU_EQUALS_PREFIX+findHierarchyToRoot(organization)+
			WFLdapConstants.ORGANIZATIONS_IN_COMPANY_SUFFIX;
		NamingEnumeration enumeration = LdapUtil.search(
			WFLdapConstants.PEOPLE_IN_COMPANY,
			WFLdapConstants.OU_EQUALS_PREFIX+attributeValue,
			ldapContext, LdapUtil.SEARCH_CONTROLS_SUBTREE_SCOPE);
		return LdapUtil.generateResultSet(enumeration);
	}

	/**
	 * Look-up for users with the specific attribute value.
	 * @param attributeName
	 * @param attributeValue
	 * @return set of the applicable users.
	 * @throws LdapException
	 */
	public Set searchUsersWithGivenAttributeValue(String attributeName, 
		String attributeValue)
		throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method searchUsersWithGivenAttributeValue, attributeName: "+
			attributeName + ", attributeValue: "+attributeValue);

		NamingEnumeration enumeration = LdapUtil.search(
			WFLdapConstants.PEOPLE_IN_COMPANY,
			attributeName+"="+attributeValue,
			ldapContext, LdapUtil.SEARCH_CONTROLS_SUBTREE_SCOPE);
		return LdapUtil.generateResultSet(enumeration);
	}
	
	/**
	 * Look-up for users with the specific attribute.
	 * @param attributeName
	 * @return set of the applicable users.
	 * @throws LdapException
	 */
	public Set searchUsersWithGivenAttribute(String attributeName)
		throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method searchUsersWithGivenAttribute, attributeName: "+
			attributeName);

		NamingEnumeration enumeration = LdapUtil.search(
			WFLdapConstants.PEOPLE_IN_COMPANY,
			attributeName+"=*",
			ldapContext, LdapUtil.SEARCH_CONTROLS_SUBTREE_SCOPE);
		return LdapUtil.generateResultSet(enumeration);
	}	
	
	/**
	 * Add user
	 * @param userId
	 * @param firstName
	 * @param familyName
	 * @throws LdapException
	 */
	public void addUser(String userId, String firstName, String familyName)
		throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method addUser, userId: "+
			userId + ", firstName: "+ firstName + ", familyName: "+familyName);

		Attributes attrs = new BasicAttributes();

		attrs.put(WFLdapConstants.OBJECT_CLASS, WFLdapConstants.INET_ORG_PERSON);
		attrs.put(WFLdapConstants.AUTO_INITIATE, WFLdapConstants.TRUE);
		attrs.put(WFLdapConstants.GIVEN_NAME,firstName);
		attrs.put(WFLdapConstants.SN, familyName);
		//added 3.4.05
		//attrs.put(WFLdapConstants.AUTH_PROCESS_CATEGORY_ALL, WFLdapConstants.TRUE);
		
		//Access the super-virtual user. Note that the name "allperson" is hard-coded.
		attrs.put(WFLdapConstants.ACCESS_LIST, WFLdapConstants.CN_ALL_PERSON_IN_COMPANY);

		LdapUtil.createSubContext(WFLdapConstants.CN_EQUALS_PREFIX+userId+WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX, attrs, ldapContext);
		//Cannot put the attributes in the Attributes above, as the attribute is changed
		//to the last value put.
		addUserAttribute(userId, WFLdapConstants.OBJECT_CLASS, WFLdapConstants.ORGANIZATIONAL_PERSON);
		addUserAttribute(userId, WFLdapConstants.OBJECT_CLASS, WFLdapConstants.PERSON);
		addUserAttribute(userId, WFLdapConstants.OBJECT_CLASS, WFLdapConstants.TOP);
	}
	
	/**
	 * For the given user, add authorizations as written in the access list.
	 * @param name Name of the user who receives the authorizations.
	 * @param authorizations Access List - List of people this user is authorized for.
	 * @throws LdapException.
	 */
	public void addUserAuthorizations(String name, String[] authorizations)
		throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method addUserAuthorizations, name: "+
			name + ", authorizations: "+ authorizations);

		Attribute attr;
		for(int i=0;i<authorizations.length;i++)
		{
			attr = new BasicAttribute(WFLdapConstants.ACCESS_LIST,WFLdapConstants.CN_EQUALS_PREFIX+authorizations[i]+WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX);
			LdapUtil.addAttribute(WFLdapConstants.CN_EQUALS_PREFIX+name+WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX, ldapContext, attr);
		}
	}

	/**
	 * For the given user, remove authorizations as written in the access list.
	 * @param name Name of the user who loses the authorizations.
	 * @param authorizations List of people this user is authorized for.
	 * @throws LdapException.
	 */
	public void removeUserAuthorizations(String name, String[] authorizations)
		throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method removeUserAuthorizations, name: "+
			name + ", authorizations: "+ authorizations);

		Attribute attr;
		for(int i=0;i<authorizations.length;i++)
		{
			attr = new BasicAttribute(WFLdapConstants.ACCESS_LIST,
				WFLdapConstants.CN_EQUALS_PREFIX+authorizations[i]+
				WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX);
			LdapUtil.removeAttribute(WFLdapConstants.CN_EQUALS_PREFIX+name+
				WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX, ldapContext, attr);
		}
	}

	/**
	 * For the given user, add authorizations as written in the access list
	 * and remove the obsolete authorizations.
	 * @param name Name of the user who receives the authorizations.
	 * @param oldAuthorization Authorization to be removed.
	 * @param newAuthorization Authorization to be added.
	 * @throws LdapException.
	 */
	public void updateUserAuthorizations(String name, String oldAuthorization,
		String newAuthorization) throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method updateUserAuthorizations, name: "+
			name + ", oldAuthorization: "+ oldAuthorization +
			", newAuthorization: " + newAuthorization);

		Attribute attr;

		attr = new BasicAttribute(WFLdapConstants.ACCESS_LIST,
			WFLdapConstants.CN_EQUALS_PREFIX+oldAuthorization+WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX);
		Logger.debug(LOGGER_CONTEXT, "attribute to be removed: "+
			WFLdapConstants.CN_EQUALS_PREFIX+oldAuthorization+WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX);

		LdapUtil.removeAttribute(
			WFLdapConstants.CN_EQUALS_PREFIX+name+WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX, ldapContext, attr);
		Logger.debug(LOGGER_CONTEXT, "removed user authorization: "+ oldAuthorization);
		attr = new BasicAttribute(WFLdapConstants.ACCESS_LIST,
			WFLdapConstants.CN_EQUALS_PREFIX+newAuthorization+WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX);
		Logger.debug(LOGGER_CONTEXT, "attribute to be added: "+
			WFLdapConstants.CN_EQUALS_PREFIX+newAuthorization+WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX);

		LdapUtil.addAttribute(
			WFLdapConstants.CN_EQUALS_PREFIX+name+WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX, ldapContext, attr);
		Logger.debug(LOGGER_CONTEXT, "added user authorization: "+ newAuthorization);
	}

	/**
	 * Assistance method.
	 * @param user1
	 * @param user2
	 * @return true in case user1 is authorized for user2.
	 * @throws LdapException
	 */
	public boolean isUser1AuthorizedForUser2(String user1, String user2)
		throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method isUser1AuthorizedForUser2, user1: "+
			user1 + ", user2: " + user2);
		
		NamingEnumeration enumeration = 
			LdapUtil.search(WFLdapConstants.PEOPLE_IN_COMPANY,
				WFLdapConstants.ACCESS_LIST_EQUALS_CN + user2+"*",	
				ldapContext, LdapUtil.SEARCH_CONTROLS_SUBTREE_SCOPE);
		Set s =  LdapUtil.generateResultSet(enumeration);

		return s.contains(user1.toUpperCase());
	}

	/**
	 * Return set of users authorized for this user.
	 * @param user
	 * @return Set all the users authorized for this one (like
	 * if the checked user is a virtual user).
	 * @throws LdapException
	 */
	public Set usersAuthorizedForThisUser(String user)
		throws LdapException
	{
		
		Logger.debug(
			LOGGER_CONTEXT,
			"started method usersAuthorizedForThisUser, user: "+
			user);

		NamingEnumeration enumeration = 
			LdapUtil.search(WFLdapConstants.PEOPLE_IN_COMPANY,
				WFLdapConstants.ACCESS_LIST_EQUALS_CN + user+"*",	
				ldapContext, LdapUtil.SEARCH_CONTROLS_SUBTREE_SCOPE);
		return LdapUtil.generateResultSet(enumeration);
	}

	/**
	 * update an existing user (When attempting to call this method on an
	 * un-existing user, a LdapException will be thrown).
	 * @param userId
	 * @param firstName
	 * @param familyName
	 * @throws LdapException
	 */
	public void modifyUser(String userId, String firstName, String familyName)
		throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method modifyUser, userId: "+
			userId + ", firstName: "+firstName + ", familyName: "+familyName);

		Attributes attrs = new BasicAttributes();

		attrs.put(WFLdapConstants.GIVEN_NAME,firstName);
		attrs.put(WFLdapConstants.SN, familyName);

		LdapUtil.modifyAttributes(WFLdapConstants.CN_EQUALS_PREFIX+userId+
			WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX, ldapContext, attrs);
	}

	/**
	 * Update the user attributes.
	 * @param name
	 * @param attributeName
	 * @param attributeValue
	 * @throws LdapException
	 */
	private void modifyUserAttribute(String name, String attributeName, 
		String attributeValue) throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method modifyUserAttribute, name: "+
			name + ", attributeName: "+attributeName + ", attributeValue: "+attributeValue);

		Attribute attr = new BasicAttribute(attributeName, attributeValue);
		LdapUtil.modifyAttribute(WFLdapConstants.CN_EQUALS_PREFIX+name+
			WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX, ldapContext, attr);
	}

	/**
	 * Adding an authorizing user with default attributes.
	 * @param name Name of the added user.
	 * @param authorizationLevel authorization level of the added user.
	 * @param organization organization of the added user.
	 * @throws LdapException.
	 */
	public void addAuthorizingUser(String name, Integer authorizationLevel, String organization)
		throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method modifyUserAttribute, name: "+
			name + ", authorizationLevel: "+authorizationLevel + ", organization: "+organization);

		Attributes attrs = new BasicAttributes();
		
		attrs.put(WFLdapConstants.OBJECT_CLASS, WFLdapConstants.INET_ORG_PERSON);
		attrs.put(WFLdapConstants.AUTO_INITIATE, WFLdapConstants.TRUE);
		attrs.put(WFLdapConstants.CN, name);
		attrs.put(WFLdapConstants.OU,
			findHierarchyToRoot(organization)+ 
				WFLdapConstants.ORGANIZATIONS_IN_COMPANY_SUFFIX);
		attrs.put(WFLdapConstants.AUTHORIZATION_LEVEL, authorizationLevel);

		LdapUtil.createSubContext(WFLdapConstants.CN_EQUALS_PREFIX+name+WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX, attrs, ldapContext);

		addUserAttribute(name, WFLdapConstants.OBJECT_CLASS, WFLdapConstants.ORGANIZATIONAL_PERSON);
		addUserAttribute(name, WFLdapConstants.OBJECT_CLASS, WFLdapConstants.PERSON);
		addUserAttribute(name, WFLdapConstants.OBJECT_CLASS, WFLdapConstants.TOP);
	}

	/**
	 * Adding a virtual user with default attributes.
	 * @param role Role of the added user. The role parameter must be in English for WorkFlow purposes.
	 * @param organization Organizational unit of the added user.
	 * @param roleDescription Description of the role (can be in locale language)
	 * @param organizationDescription Description of the organization (can be in 
	 * local language).
	 * The organization  parameter must be in English for WorkFlow purposes.
	 * If the combination of role and organization already exists, an exception is thrown.
	 * @throws LdapException.
	 */
	public void addVirtualUser(String role, String organization, 
		String roleDescription, String organizationDescription) throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method addVirtualUser, role: "+
			role + ", organization: "+organization);

		String virtualUserName = createVirtualUserName(role,organization);
		Attributes attrs = new BasicAttributes();
		
		attrs.put(WFLdapConstants.OBJECT_CLASS, WFLdapConstants.INET_ORG_PERSON);
		attrs.put(WFLdapConstants.AUTO_INITIATE, WFLdapConstants.TRUE);
		
		/*
		 * Organization of a virtual user serves as its given name.
		 * Role of a virtual user serves as its family Name.
		 * (Decision with Yariv and Yifat, 12/4/05).
		 */
		attrs.put(WFLdapConstants.GIVEN_NAME,organizationDescription);
		attrs.put(WFLdapConstants.SN, roleDescription);

		attrs.put(WFLdapConstants.CN, virtualUserName);
		attrs.put(WFLdapConstants.OU,
			findHierarchyToRoot(organization)+ 
				WFLdapConstants.ORGANIZATIONS_IN_COMPANY_SUFFIX);
		//added 3.4.05
		//attrs.put(WFLdapConstants.AUTH_PROCESS_CATEGORY_ALL, WFLdapConstants.TRUE);

		//Add the virtual user to the appropriate role.
		LdapUtil.createSubContext(WFLdapConstants.CN_EQUALS_PREFIX+virtualUserName+WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX, attrs, ldapContext);

		Attribute roleAttribute = 
			new BasicAttribute(WFLdapConstants.MEMBER, 
				WFLdapConstants.CN_EQUALS_PREFIX+virtualUserName+ WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX);
		LdapUtil.addAttribute(WFLdapConstants.CN_EQUALS_PREFIX+role+WFLdapConstants.ROLES_IN_COMPANY_SUFFIX, ldapContext, roleAttribute);

		addUserAttribute(virtualUserName, WFLdapConstants.OBJECT_CLASS, WFLdapConstants.ORGANIZATIONAL_PERSON);
		addUserAttribute(virtualUserName, WFLdapConstants.OBJECT_CLASS, WFLdapConstants.PERSON);
		addUserAttribute(virtualUserName, WFLdapConstants.OBJECT_CLASS, WFLdapConstants.TOP);
	}

	/**
	 * Remove the given virtual user.
	 * @param role Role of the removed user.
	 * @param organization Organizational unit of the removed user.
	 * @throws LdapException.
	 */
	public void removeVirtualUser(String role, String organization) throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method removeVirtualUser, role: "+
			role + ", organization: "+organization);

		//TODO: here should use findVirtualUserId() ?
		String virtualUserName = createVirtualUserName(role,organization);

		//Remove the virtual user from the appropriate role.
		LdapUtil.unBind(WFLdapConstants.CN_EQUALS_PREFIX+virtualUserName+WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX, ldapContext);

		Attribute roleAttribute = 
			new BasicAttribute(WFLdapConstants.MEMBER, WFLdapConstants.CN_EQUALS_PREFIX+virtualUserName+ WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX);
		LdapUtil.removeAttribute(WFLdapConstants.CN_EQUALS_PREFIX+role+WFLdapConstants.ROLES_IN_COMPANY_SUFFIX, ldapContext, roleAttribute);
	}

	
	/**
	 * Update the user attributes.
	 * @param name
	 * @param attributeName
	 * @param attributeValue
	 * @throws LdapException
	 */
	private void addUserAttribute(String name, String attributeName, 
		String attributeValue) throws LdapException
	{
		Attribute attr = new BasicAttribute(attributeName, attributeValue);
		LdapUtil.addAttribute(WFLdapConstants.CN_EQUALS_PREFIX+name+
			WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX, ldapContext, attr);
	}
	
	/**
	 * Remove the given user from Ldap.
	 * @param name Name of the user to be removed.
	 * @throws LdapException
	 */
	public void removeUser(String name) throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method removeUser, name: "+ name);

		LdapUtil.unBind(WFLdapConstants.CN_EQUALS_PREFIX+name+
			WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX, ldapContext);
	}
	
	/**
	 * Add a new role.
	 * @param name Must be in English (for WorkFlow purposes).
	 * @param description in the locale languange
	 * @throws LdapException
	 */
	public void addRole(String name, String description) throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method addRole, name: "+ name);

		Attributes attrs = new BasicAttributes();
		attrs.put(WFLdapConstants.OBJECT_CLASS, WFLdapConstants.GROUP_OF_NAMES);

		LdapUtil.createSubContext(WFLdapConstants.CN_EQUALS_PREFIX+name+
			WFLdapConstants.ROLES_IN_COMPANY_SUFFIX, attrs, ldapContext);
		addRoleAttribute(name, WFLdapConstants.OBJECT_CLASS, WFLdapConstants.TOP);
		//new addition
		addRoleAttribute(name, WFLdapConstants.DESCRIPTION, description);

	}

	/**
	 * add attributes to the role.
	 * @param name RoleName
	 * @param attributeName
	 * @param attributeValue
	 * @throws LdapException
	 */
	private void addRoleAttribute(String name, String attributeName, 
		String attributeValue) throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method addRoleAttribute, name: "+ name +
			", attributeName: " + attributeName + "attributeValueL "+attributeValue);

		Attribute attr = new BasicAttribute(attributeName, attributeValue);

		LdapUtil.addAttribute(WFLdapConstants.CN_EQUALS_PREFIX+name+WFLdapConstants.ROLES_IN_COMPANY_SUFFIX, ldapContext, attr);
		
	}

	/**
	 * remove the given attributes from the role.
	 * @param name
	 * @param attributeName
	 * @param attributeValue
	 * @throws LdapException
	 */
	private void removeRoleAttribute(String name, String attributeName, 
		String attributeValue) throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method removeRoleAttribute, name: "+ name +
			", attributeName: " + attributeName + "attributeValue "+attributeValue);

		Attribute attr = new BasicAttribute(attributeName, attributeValue);

		LdapUtil.removeAttribute(WFLdapConstants.CN_EQUALS_PREFIX+name+WFLdapConstants.ROLES_IN_COMPANY_SUFFIX, ldapContext, attr);
		
	}

	/**
	 * Update the role attributes (change existing attributes with the new value.
	 * If such attribute didn't exist before, LdapException will be thrown.
	 * If the attribute existed, with different value, the previous value is discarded.
	 * @param name
	 * @param attributeName
	 * @param attributeValue
	 * @throws LdapException
	 */
	private void modifyRoleAttribute(String name, String attributeName, 
		String attributeValue) throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method modifyRoleAttribute, name: "+
			name + ", attributeName: "+attributeName + ", attributeValue: "+attributeValue);

		Attribute attr = new BasicAttribute(attributeName, attributeValue);
		LdapUtil.modifyAttribute(WFLdapConstants.CN_EQUALS_PREFIX+name+
			WFLdapConstants.ROLES_IN_COMPANY_SUFFIX, ldapContext, attr);
	}

	/**
	 * Is used to change the role name with preserving its attributes.
	 * @param oldRoleName
	 * @param newRoleName Must be in English (For WorkFlow purposes).
	 * @param roleDescription Can be in the local language. the former description
	 * exists, it is replaced with the new description.
	 * @throws LdapException
	 */
	public void updateRoleName(String oldRoleName, String newRoleName, String roleDescription) 
		throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method updateRoleName, role: "+ oldRoleName +
			", newName: " + newRoleName);

		LdapUtil.reBind(
			WFLdapConstants.CN_EQUALS_PREFIX+oldRoleName+WFLdapConstants.ROLES_IN_COMPANY_SUFFIX,
			WFLdapConstants.CN_EQUALS_PREFIX+newRoleName+WFLdapConstants.ROLES_IN_COMPANY_SUFFIX,
			ldapContext);
		
		//update the description attribute
		modifyRoleAttribute(newRoleName, WFLdapConstants.DESCRIPTION, roleDescription);

		Logger.debug(
			LOGGER_CONTEXT,
			"updateRoleName, added new description attribute");

		Attribute removedAttribute = 
			new BasicAttribute(WFLdapConstants.CN,oldRoleName);

		LdapUtil.removeAttribute(
			WFLdapConstants.CN_EQUALS_PREFIX+newRoleName+
				WFLdapConstants.ROLES_IN_COMPANY_SUFFIX, ldapContext, removedAttribute);
		removeRole(oldRoleName);

	}
	
	/**
	 * Set the coordinator of this role.
	 * @param roleName
	 * @param name
	 * @throws LdapException
	 */
	public void setCoordinatorOfRole(String roleName, String name) throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method setCoordinatorOfRole, name: "+ name +
			", roleName: " + roleName);

		String currentCoordinator = getCoordinatorOfRole(roleName);
		Attribute attr = 
			new BasicAttribute(WFLdapConstants.OWNER,WFLdapConstants.CN_EQUALS_PREFIX+currentCoordinator+WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX);

		if(currentCoordinator!=null)
		{
			//Remove the coordinator
			Logger.debug(
				LOGGER_CONTEXT,
				"setCoordinatorOfRole, removing currentCoordinator: " + currentCoordinator);

			LdapUtil.removeAttribute(WFLdapConstants.CN_EQUALS_PREFIX+roleName+WFLdapConstants.ROLES_IN_COMPANY_SUFFIX,
				ldapContext, attr);
		}
		attr = 
			new BasicAttribute(WFLdapConstants.OWNER,WFLdapConstants.CN_EQUALS_PREFIX+name+WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX);
		LdapUtil.addAttribute(WFLdapConstants.CN_EQUALS_PREFIX+roleName+WFLdapConstants.ROLES_IN_COMPANY_SUFFIX, ldapContext, attr);

	}
	
	/**
	 * @param roleName
	 * @return String
	 * @throws LdapException
	 */
	public String getCoordinatorOfRole(String roleName) throws LdapException
	{	

		Logger.debug(
			LOGGER_CONTEXT,
			"started method getCoordinatorOfRole, roleName: "+ roleName);

		String owner = WFLdapConstants.OWNER;	
		String[] attrIds = new String[1];
		attrIds[0] = owner;
		Attributes attributes = LdapUtil.getAttributes(WFLdapConstants.CN_EQUALS_PREFIX+
			roleName+WFLdapConstants.ROLES_IN_COMPANY_SUFFIX, attrIds, ldapContext);
		Attribute ownerAttribute = attributes.get(owner);
		if(ownerAttribute==null)
		{
			return null;
		}
		String rawOwner = ownerAttribute.toString(); //Only one owner for a given role.

		return getNameFromAttribute(rawOwner);
		
	}
	
	/**
	 * Remove the selected role.
	 * @param name
	 * @throws LdapException
	 */
	public void removeRole(String name) throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method removeRole, roleName: "+ name);

		LdapUtil.unBind(WFLdapConstants.CN_EQUALS_PREFIX+name+WFLdapConstants.ROLES_IN_COMPANY_SUFFIX, ldapContext);

	}

	/**
	 * Addition of an organizational team, can be a deep one.
	 * @param name Name of the new organizational unit (should be unique, English only).
	 * @param description
	 * @param parentName Name of the organizational unit parent.
	 * @param manager Name of the new unit manager.
	 * @throws LdapException
	 */
	public void addOrganizationalTeam(String name, String description, 
		String parentName, String manager) throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method addOrganizationalTeam, name: "+ name +
			", parentName: "+parentName + ", manager: "+manager);

		Attributes attrs = new BasicAttributes();
		attrs.put(WFLdapConstants.OBJECT_CLASS, WFLdapConstants.ORGANIZATIONAL_UNIT);
		attrs.put(WFLdapConstants.OU,name);
		//If no manager was supported, use default manager.
		if(manager==null)
		{
			attrs.put(WFLdapConstants.MANAGER, 
				WFLdapConstants.CN_EQUALS_PREFIX+WFLdapConstants.DEFAULT_MANAGER+
				WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX);
		} else 
		{
			attrs.put(WFLdapConstants.MANAGER, 
				WFLdapConstants.CN_EQUALS_PREFIX+manager+
				WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX);
		}
		attrs.put(WFLdapConstants.DESCRIPTION, description);
		
		String searchedStr = WFLdapConstants.OU_EQUALS_PREFIX+name;
		if(parentName!=null)
		{
			searchedStr+=","+findHierarchyToRoot(parentName);
		}

		LdapUtil.createSubContext(searchedStr+WFLdapConstants.ORGANIZATIONS_IN_COMPANY_SUFFIX, attrs, ldapContext);
		addOrganizationalTeamAttribute(searchedStr, WFLdapConstants.OBJECT_CLASS, WFLdapConstants.TOP);
		
	}

	/**
	 * Add new organizational team attributes.
	 * @param name Name of the organizational team to be updated.
	 * @param attributeName
	 * @param attributeValue
	 * @throws LdapException
	 */	
	private void addOrganizationalTeamAttribute(String name, 
		String attributeName, String attributeValue) throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method addOrganizationalTeamAttribute, name: "+ name +
			", attributeName: "+attributeName + ", attributeValue: "+attributeValue);

		Attribute attr = new BasicAttribute(attributeName, attributeValue);

		LdapUtil.addAttribute(name+WFLdapConstants.ORGANIZATIONS_IN_COMPANY_SUFFIX,
			ldapContext, attr);
		
	}
	
	/**
	 * remove organizational team under the root.
	 * @param name Name of the organizational team to be removed.
	 * @throws LdapException
	 */
	public void removeOrganizationalTeam(String name) throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method removeOrganizationalTeam, name: "+ name);

		//Search sub-organizations and remove them before attempting to remove this organization.
		String searchedStr = findHierarchyToRoot(name);
		NamingEnumeration enumeration = LdapUtil.list(searchedStr+WFLdapConstants.ORGANIZATIONS_IN_COMPANY_SUFFIX,ldapContext);
		String subOrganization = null;
		while(enumeration.hasMoreElements())
		{
			//Try to remove the son element recursively
			subOrganization = formatSubOrganization(enumeration.nextElement().toString());
			removeUsersConnectedToOrganization(subOrganization);
			removeOrganizationalTeam(subOrganization);
		}

		removeUsersConnectedToOrganization(name);
		LdapUtil.unBind(searchedStr+WFLdapConstants.ORGANIZATIONS_IN_COMPANY_SUFFIX, ldapContext);
	}

	/**
	 * Assistance method
	 * @param rawOrganization
	 * @return
	 */
	private String formatSubOrganization(String rawOrganization)
	{
		int indexOfEqualsSign = rawOrganization.indexOf("=");
		String temp = rawOrganization.substring(indexOfEqualsSign+1);
		int indexOfColon = temp.indexOf(":");
		return temp.substring(0,indexOfColon);
	}

	/**
	 * Assistance method used to remove all virtual users connected to 
	 * the role, before deletion of it.
	 * @param roleName
	 * @throws LdapException
	 */
	public void removeUsersConnectedToOrganization(String organizationName) throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method removeUsersConnectedToOrganization, organizationName: "+ 
				organizationName);

		//1. find all users with this organization.
		Set members = membersOfOrganization(organizationName);

		//2. delete only users that have only this role.
		//Ensure that there are such users...
		if(members!=null)
		{
			Iterator iterator = members.iterator();
			while(iterator.hasNext())
			{
				String checkedUser = iterator.next().toString().toLowerCase();
				if(numberOfRoles(checkedUser)<=1)
				{
					removeUser(checkedUser);
				} 
				//3. delete authorization of users authorized for deleted users.
				removeAuthorizationsForDeletedUser(checkedUser);
			}					
		}
	}

	/**
	 * Assistance method.
	 * Return the organizational team for a given user.
	 * @param userName
	 * @return String the organizational team (can be null).
	 * @throws LdapException
	 */
	public String getOrganizationalTeam(String userName) throws LdapException
	{	

		Logger.debug(
			LOGGER_CONTEXT,
			"started method getOrganizationalTeam, userName: "+ userName);

		//String owner = WFLdapConstants.OWNER;	
		String[] attrIds = new String[1];
		attrIds[0] = WFLdapConstants.OU;
		Attributes attributes = LdapUtil.getAttributes(WFLdapConstants.CN_EQUALS_PREFIX+
			userName+WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX, attrIds, ldapContext);
		Attribute teamAttribute = attributes.get(WFLdapConstants.OU);
		if(teamAttribute==null)
		{
			return null;
		}
		String rawTeam = teamAttribute.toString(); //Only one owner for a given role.
		return getNameFromAttribute(rawTeam);
		
	}

	/**
	 * Retrieve all the user names in the system.
	 * @return Set set of all userNames
	 * @throws LdapException
	 */
	public Set getUserNames() throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,	"started method getUserNames");
		NamingEnumeration enumeration = 
			LdapUtil.search(WFLdapConstants.PEOPLE_IN_COMPANY, 
			WFLdapConstants.CN_EQUALS_STAR, ldapContext, 
			LdapUtil.SEARCH_CONTROLS_SUBTREE_SCOPE);
		return LdapUtil.generateResultSet(enumeration);
	}

	/**
	 * Retrieve all the roles in the system.
	 * @return Set set of all roles
	 * @throws LdapException
	 */	
	public Set getRoles() throws LdapException
	{
		Logger.debug(
			LOGGER_CONTEXT,	"started method getRoles");

		NamingEnumeration enumeration = 
			LdapUtil.search(WFLdapConstants.ROLES_IN_COMPANY, 
			WFLdapConstants.CN_EQUALS_STAR, ldapContext, 
			LdapUtil.SEARCH_CONTROLS_SUBTREE_SCOPE);
		return LdapUtil.generateResultSet(enumeration);
	}
	
	/**
	 * Retrieve all the organizational teams in the system.
	 * @return Set set of all organizationalTeams
	 * @throws LdapException
	 */
	public Set getOrganizationalTeams() throws LdapException
	{
		Logger.debug(
			LOGGER_CONTEXT,	"started method getOrganizationalTeams");

		NamingEnumeration enumeration = 
			LdapUtil.search(WFLdapConstants.ORGANIZATIONS_IN_COMPANY, 
			WFLdapConstants.OU_EQUALS_STAR, ldapContext, 
			LdapUtil.SEARCH_CONTROLS_SUBTREE_SCOPE);
		return LdapUtil.generateResultSet(enumeration);
	}

	/**
	 * Retrieve all the organizational teams under this one.
	 * @return Set set of all organizationalTeams
	 * @throws LdapException
	 */
	public Set getSubOrganizationalTeams(String organizationName) throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method getSubOrganizationalTeams, organizationName: "+ organizationName);

		NamingEnumeration enumeration = 
			LdapUtil.search(findHierarchyToRoot(organizationName)+
			WFLdapConstants.ORGANIZATIONS_IN_COMPANY_SUFFIX, 
			WFLdapConstants.OU_EQUALS_STAR, ldapContext, 
			LdapUtil.SEARCH_CONTROLS_SUBTREE_SCOPE);
		Set set = LdapUtil.generateResultSet(enumeration);
		//TODO: Was added due to a bug in Ldap, which returns 
		// a redundant organizational team
		if(set.contains(WFLdapConstants.MANAGER_UPPERCASED))
		{
			set.remove(WFLdapConstants.MANAGER_UPPERCASED);
		}
		return set;
	}
	
	/**
	 * This method is used during retrieveUsersForWorkItemRouting(), and
	 * returns the virtual users who are members of the given role.
	 * @param roleName name of the checked role.
	 * @return set set of the users who are members of this role.
	 * @throws LdapException.
	 */
	public Set membersOfRole(String roleName) throws LdapException
	{
		try {

			Logger.debug(
				LOGGER_CONTEXT,
				"started method membersOfRole, roleName: "+ roleName);

			StringBuffer temp = new StringBuffer(128);
			temp.append(WFLdapConstants.CN_EQUALS_PREFIX);
			temp.append(roleName);
			temp.append(WFLdapConstants.ROLES_IN_COMPANY_SUFFIX);
			String[] attrIds = new String[1];
			attrIds[0] = WFLdapConstants.MEMBER;
			Attributes attributes =
				LdapUtil.getAttributes(temp.toString(), attrIds, ldapContext);
			Attribute attribute = attributes.get(WFLdapConstants.MEMBER);
			if(attribute==null)
			{
				return null;
			} else
			{
				NamingEnumeration enumeration = attribute.getAll();
				return LdapUtil.generateResultSet(enumeration);
			}

		} catch(NamingException nex)
		{
			throw new LdapException(nex);
		}

	}

	/**
	 * Assistance method that retrieves the number of roles for the given user.
	 * @param userId
	 * @return int number of roles for this user.
	 * @throws LdapException
	 */
	public int numberOfRoles(String userId) throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method numbersOfRoles, userId: "+ userId);

		String[] attrIds = new String[1];
		attrIds[0] = WFLdapConstants.MEMBER;
		NamingEnumeration enumeration = 
			LdapUtil.search(WFLdapConstants.ROLES_IN_COMPANY,
				WFLdapConstants.MEMBER_EQUALS_CN+userId+WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX,
				ldapContext, LdapUtil.SEARCH_CONTROLS_SUBTREE_SCOPE);
		Set set = LdapUtil.generateResultSet(enumeration);
		return set.size();
			
	}

	/**
	 * Returns array of User IDs of all users in the given organizational
	 * team, that can perform this role.
	 * @param organizationalTeam
	 * @param role
	 * @return Set Contains user IDs.
	 * @throws LdapException
	 */
	public Set retrieveUsersInOrganizationalTeam(
		String organizationalTeam,
		String role)
		throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method retrieveUsersInOrganizationalTeam, organizationalTeam: "+ 
			organizationalTeam + ", role: " + role);

		//Initial load factor for the HashSet.
		int userSetsInitialSize =
			Integer.parseInt(
				SystemResources.getInstance().getProperty(WFConstants.USERS_SET_INITIAL_SIZE_KEY));
		Logger.debug(
			LOGGER_CONTEXT,
			"retrieveUsersInOrganizationalTeam, initial set size: "
			+ userSetsInitialSize);

		HashSet resultSet = new HashSet(userSetsInitialSize);

		/*
		 * Look for all people authorized for this virtual user.
		 * As a person can't be authorized for a virtual user in a
		 * different team, No need for special checking if the person
		 * is in the correct organizational team.
		 */
		 //TODO: ensure that the above comment is correct!
		StringBuffer tempAccessList = new StringBuffer(128);
		tempAccessList.append(WFLdapConstants.ACCESS_LIST_EQUALS_CN);
		//TODO: here should use findVirtualUserId()?
		tempAccessList.append(findVirtualUserId(role,organizationalTeam));
		tempAccessList.append(WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX);
		NamingEnumeration enumeration = 
			LdapUtil.search(WFLdapConstants.PEOPLE_IN_COMPANY,
			tempAccessList.toString(), ldapContext,
			LdapUtil.SEARCH_CONTROLS_SUBTREE_SCOPE);

		while(enumeration.hasMoreElements())
		{
					
			/*
			 * Conversion to upper case is required to prevent 
			 * an Exception when passing the requested user name
			 * to WorkFlow.
			 */
			String formattedName = 
				formatString2(LdapUtil.formatString(enumeration.nextElement().toString().toUpperCase()));
			resultSet.add(formattedName);
		}
		
		return resultSet;

	}

	/**
	 * This method is used to prepare list of users required for 
	 * "choosing working queue of another user".
	 * Note that it is different from the method retrieveUsersInOrganizationalTeam(),
	 * which demands the users to be allowed for the supplied role.
	 * @return list of users for "choosing working queue of another user".
	 * The return type is a Set, in order to prevent multiple entries easily.
	 * @throws NullParametersException
	 * @throws WorkFlowException
	 */
	public Set usersInSubOrganizationForViewAnother(String organization)
		throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method usersInSubOrganizationForViewAnother, organization: "+ 
				organization);

		//Initial load factor for the HashSet.
		int userSetsInitialSize =
			Integer.parseInt(
				SystemResources.getInstance().getProperty(WFConstants.USERS_SET_INITIAL_SIZE_KEY));
		Logger.debug(
			LOGGER_CONTEXT,
			"usersInSubOrganizationForViewAnother, initial set size: "
			+ userSetsInitialSize);

		HashSet result = new HashSet(userSetsInitialSize);

		SearchControls controls = new SearchControls();
		controls.setSearchScope(SearchControls.SUBTREE_SCOPE);

		/*
		 * Recursive search on the subtree of the organizations section.
		 * Note - for this to work, no organization should have
		 * more than one parent organization!
		 */
		NamingEnumeration enumeration = 
			LdapUtil.search(WFLdapConstants.ORGANIZATIONS_IN_COMPANY,
			WFLdapConstants.OU_EQUALS_PREFIX+organization, ldapContext, 
			LdapUtil.SEARCH_CONTROLS_SUBTREE_SCOPE);

		/*
		 * Next loop is in order to add all the virtual users
		 * this user is permitted for, under the managed organization.
		 */
		while(enumeration.hasMoreElements())
		{
					
			String formattedName = 
				LdapUtil.formatString(enumeration.nextElement().toString());
					
			//Used for filtering on the organizational unit of the people.
			String sr = formattedName+WFLdapConstants.ORGANIZATIONS_IN_COMPANY_SUFFIX;
			NamingEnumeration enumeration2 = 
				LdapUtil.search(WFLdapConstants.PEOPLE_IN_COMPANY,
					WFLdapConstants.OU_EQUALS_IN_PARENTHESIS+sr+")", ldapContext, 
					LdapUtil.SEARCH_CONTROLS_SUBTREE_SCOPE);
					
			while(enumeration2.hasMoreElements())
			{
				
			/*
			 * Conversion to upper case is required to prevent 
			 * an Exception when passing the requested user name
			 * to WorkFlow.
			 */
				String formattedName2 = 
					LdapUtil.formatString(enumeration2.nextElement().toString().toUpperCase());
				result.add(formatString2(formattedName2));
			}
		}

		Logger.debug(
			LOGGER_CONTEXT,
			"finished method usersInSubOrganizationForViewAnother, result: "+ 
				result);

			
		return result;

	}

	/**
	 * Assistance method of prepareListOfUsers().
	 * This method retrieves the user name from the LDAP String,
	 * which has the structure: cn=<name>: <attributes list>
	 * The name is limited between the first char and the first colon in the String.
	 * 
	 * @param stringToFormat
	 * @return String the formatted user name.
	 */
	private String formatString2(String stringToFormat)
	{

		int indexOfEqualitySign = stringToFormat.indexOf(WFLdapConstants.EQUALS_SIGN);
		if(indexOfEqualitySign==-1)
		{
			return null;
		}

		return stringToFormat.substring(indexOfEqualitySign+1).toUpperCase();
	}
	
	/**
	 * Assistance method
	 * meant to be used in strings like
	 * "owner: cn=leonid,ou=people,o=company"
	 * @param attribute The raw String.
	 * @return The processed String.
	 */
	private String getNameFromAttribute(String attribute)
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method getNameFromAttribute, attribute: "+ attribute);

		int indexOfEqualsSign = attribute.indexOf(WFLdapConstants.EQUALS_SIGN);
		int indexOfCommaSign = attribute.indexOf(",");
		return attribute.substring(indexOfEqualsSign+1,indexOfCommaSign);
	}

	/**
	 * find Hierarchy to root
	 * @param organization
	 * @return String the hierarchy in Ldap format.
	 * @throws LdapException
	 */
	public String findHierarchyToRoot(String organization) throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method findHierarchyToRoot, organization: "+ 
				organization);

		if(!doesOrganizationExist(organization))
		{
			throw new LdapException(WFExceptionMessages.ORGANIZATION_DOES_NOT_EXIST);
		}
		NamingEnumeration enumeration = 
			LdapUtil.search(WFLdapConstants.ORGANIZATIONS_IN_COMPANY,
				WFLdapConstants.OU_EQUALS_PREFIX+organization,
				ldapContext,LdapUtil.SEARCH_CONTROLS_SUBTREE_SCOPE);
		String rawHierarchy = enumeration.nextElement().toString();
		int colonIndex = rawHierarchy.indexOf(":");
		if(colonIndex == -1)
		{
			return null;
		} else
		{
			return rawHierarchy.substring(0,colonIndex);
		}
	}


	/**
	 * Set the organizational team manager.
	 * @param team the organizational team.
	 * @param manager Name of the new unit manager.
	 * @throws LdapException
	 */
	public void setOrganizationalTeamManager(String team, String manager) 
		throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method setOrganizationalTeamManager, team: "+ 
				team + ", manager: " + manager);

		Attribute attr = 
			new BasicAttribute(WFLdapConstants.MANAGER, 
				WFLdapConstants.CN_EQUALS_PREFIX+manager+WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX);
		
		String searchedStr = findHierarchyToRoot(team);
		searchedStr+=WFLdapConstants.ORGANIZATIONS_IN_COMPANY_SUFFIX;
		LdapUtil.modifyAttribute(searchedStr, ldapContext, attr);
	}

	/**
	 * Set the organizational team manager.
	 * @param manager Name of the new unit manager.
	 * @throws LdapException
	 */
	public Set managedOrganizations(String manager) 
		throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method managedOrganizations, manager: " + manager);

		NamingEnumeration enumeration = LdapUtil.search(
			WFLdapConstants.ORGANIZATIONS_IN_COMPANY,
				WFLdapConstants.MANAGER+"="+WFLdapConstants.CN_EQUALS_PREFIX+
				manager+WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX,
				ldapContext, LdapUtil.SEARCH_CONTROLS_SUBTREE_SCOPE);
		return LdapUtil.generateResultSet(enumeration);
	}

	/**
	 * Assistance method used to remove all virtual users connected to 
	 * the role, before deletion of it.
	 * @param roleName
	 * @throws LdapException
	 */
	public void removeUsersConnectedToRole(String roleName) throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method removeUsersConnectedToRole, roleName: " + roleName);


		//1. find all users with this role.
		Set members = membersOfRole(roleName);
		//2. delete only users that have only this role.
		//Ensure that there are such users...
		if(members!=null)
		{
			Iterator iterator = members.iterator();
			while(iterator.hasNext())
			{
				String checkedUser = iterator.next().toString().toLowerCase();
				if(numberOfRoles(checkedUser)<=1)
				{
					removeUser(checkedUser);
				} 
				//3. delete authorization of users authorized for deleted users.
				removeAuthorizationsForDeletedUser(checkedUser);
			}					
		}
	}

	/**
	 * @param userName
	 * @return authorizedUsers The users whose authorization was cancelled.
	 * @throws LdapException
	 */
	public Set removeAuthorizationsForDeletedUser(String userName)
		throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method removeAuthorizationsForDeletedUser, userName: " + userName);

		NamingEnumeration enumeration = 
			LdapUtil.search(WFLdapConstants.PEOPLE_IN_COMPANY,
				WFLdapConstants.ACCESS_LIST_EQUALS_CN + userName+"*",	
				ldapContext, LdapUtil.SEARCH_CONTROLS_SUBTREE_SCOPE);
		Set authorizedUsers =  LdapUtil.generateResultSet(enumeration);
		Iterator iterator = authorizedUsers.iterator();

		//This set contains the users authorized for the deleted user.
		String[] authorization = new String[1];
		authorization[0] = userName;
		while(iterator.hasNext())
		{
			String xxx = iterator.next().toString().toLowerCase();
			removeUserAuthorizations(xxx,authorization);
		}
		return authorizedUsers;
	}
	
	/**
	 * Find the members of this organization.
	 * @param organizationName name of the checked organization.
	 * @return set set of the users who are members of this organization.
	 * @throws LdapException.
	 */
	private Set membersOfOrganization(String organizationName) throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method membersOfOrganization, organizationName: " + organizationName);

		StringBuffer temp = new StringBuffer(128);
		temp.append(findHierarchyToRoot(organizationName));
		temp.append(WFLdapConstants.ORGANIZATIONS_IN_COMPANY_SUFFIX);
		NamingEnumeration enumeration = 
			LdapUtil.search(
				WFLdapConstants.PEOPLE_IN_COMPANY,
				WFLdapConstants.OU_EQUALS_PREFIX+temp.toString(),
				ldapContext,LdapUtil.SEARCH_CONTROLS_SUBTREE_SCOPE);

			return LdapUtil.generateResultSet(enumeration);

	}

	/**
	 * 
	 * @param oldName
	 * @param newName Must be in English for WorkFlow purposes.
	 * @throws LdapException
	 */
	public void updateUserName(String oldName, String newName) throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method updateUserName, oldName: " + oldName + ", newName: " + newName);

		LdapUtil.reBind(
			WFLdapConstants.CN_EQUALS_PREFIX+oldName+WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX,
			WFLdapConstants.CN_EQUALS_PREFIX+newName+WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX,
			ldapContext);

		Attribute removedAttribute = 
			new BasicAttribute(WFLdapConstants.CN,oldName);

		LdapUtil.removeAttribute(
			WFLdapConstants.CN_EQUALS_PREFIX+newName+
				WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX, ldapContext, removedAttribute);
		removeUser(oldName);

	}
	
	/**
	 * Update the organizational team.
	 * Four cases:
	 * 0. Nothing is changed (name and newName are identical
	 * 	  and so are parentName and current parent name).
	 * 1. just renaming the organizational team.
	 * 2. just moving the organizational team.
	 * 3. both renaming and moving the organizational team.
	 * @param name
	 * @param description can be in locale language
	 * @param newName must be in English
	 * @param parentName
	 * @throws LdapException
	 */
	public void updateOrganizationalTeam(String name, String description, 
		String newName, String parentName) throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method updateOrganizationalTeam, name: " + name + 
			", description: " + description + ", newName: " + newName + 
			", parentName: " + parentName);

		 String currentParent = getParent(name);
		Logger.debug(
			LOGGER_CONTEXT,
			"updateOrganizationalTeam, currentParent: " + currentParent);

		 /*
		  * Assistance variable, to avoid NullPointerException when
		  * working with currentParent and parentName (null values
		  * are perfectly legal in this case!).
		  * If both variables are null, of course they are equal.
		  * Otherwise, can perform comparison (only after one of the fields is
		  * known for sure to be not null).
		  */
		 boolean isParentEqualToCurrentParent = false;
		 if((currentParent==null)&&(parentName==null))
		 {
		 	isParentEqualToCurrentParent = true;
		 } else if((currentParent==null)&&(parentName!=null))
		 {
		 	isParentEqualToCurrentParent = false;
		 	//Now can work with currentParent without fear from exception
		 } else if(currentParent.equals(parentName))
		 {
		 	isParentEqualToCurrentParent = true;
		 }
		 /*
		  * The inner condition ((parentName==null)&&(currentParent==null)) was
		  * inserted in order to prevent NullPointerException if either the current
		  * parent is null or the new parent is null (root level).
		  */
		 if((name.equals(newName))&& (isParentEqualToCurrentParent))
		 {
		 	//do nothing;
		 } else if((!(name.equals(newName)))&&(isParentEqualToCurrentParent))
		 {
		 	//Rename the organization.
		 	String nameInHierarchy = 
		 		findHierarchyToRoot(name)+WFLdapConstants.ORGANIZATIONS_IN_COMPANY_SUFFIX;
		 	String newNameInHierarchy = nameInHierarchy.replaceFirst(name, newName);
		 	LdapUtil.reBind(nameInHierarchy, newNameInHierarchy, ldapContext);
		 	//Copy all the hierarchy
		 	recursiveUpdateOrganization(name, newName, nameInHierarchy);

			//eliminate the redundant attribute from the renamed organization
			Attribute removedAttribute = 
				new BasicAttribute(WFLdapConstants.OU,name);

			LdapUtil.removeAttribute(
				newNameInHierarchy, ldapContext, removedAttribute);

			Attribute attr = new BasicAttribute(WFLdapConstants.DESCRIPTION, description);
			LdapUtil.modifyAttribute(newNameInHierarchy, ldapContext, attr);

			removeOrganizationalTeam(name);
		 } else if((name.equals(newName))&&(!(isParentEqualToCurrentParent)))
		 {
		 	//Moving: just replace the newName in hierarchy.
			String nameInHierarchy = 
				findHierarchyToRoot(name)+WFLdapConstants.ORGANIZATIONS_IN_COMPANY_SUFFIX;
			//Note: calculating again the new name in order to allow copying from a
			//sub-child to one of its parents.	
			String newNameInHierarchy = 
				WFLdapConstants.OU_EQUALS_PREFIX+name+","+
				findHierarchyToRoot(parentName)+WFLdapConstants.ORGANIZATIONS_IN_COMPANY_SUFFIX;

			LdapUtil.reBind(nameInHierarchy, newNameInHierarchy, ldapContext);
			//Copy all the hierarchy
			recursiveUpdateOrganizationLocation(name, name, nameInHierarchy);

			//eliminate the redundant attribute from the renamed organization
			Attribute removedAttribute = 
				new BasicAttribute(WFLdapConstants.OU,name);

			LdapUtil.removeAttribute(
				newNameInHierarchy, ldapContext, removedAttribute);
			removeOrganizationalTeam(name);

		 } else
		 {
		 	//both renaming the organization and moving it.

			String nameInHierarchy = 
				findHierarchyToRoot(name)+WFLdapConstants.ORGANIZATIONS_IN_COMPANY_SUFFIX;
			//Note: calculating again the new name in order to allow copying from a
			//sub-child to one of its parents.	
			String newNameInHierarchy = 
				WFLdapConstants.OU_EQUALS_PREFIX+newName+","+
				findHierarchyToRoot(parentName)+WFLdapConstants.ORGANIZATIONS_IN_COMPANY_SUFFIX;

			LdapUtil.reBind(nameInHierarchy, newNameInHierarchy, ldapContext);
			//Copy all the hierarchy
			recursiveUpdateOrganizationLocation(name, newName, nameInHierarchy);

			//eliminate the redundant attribute from the renamed organization
			Attribute removedAttribute = 
				new BasicAttribute(WFLdapConstants.OU,name);

			LdapUtil.removeAttribute(
				newNameInHierarchy, ldapContext, removedAttribute);

			Attribute attr = new BasicAttribute(WFLdapConstants.DESCRIPTION, description);
			LdapUtil.modifyAttribute(newNameInHierarchy, ldapContext, attr);

			removeOrganizationalTeam(name);
		 }
	}
	
	/**
	 * 
	 * @param name
	 * @param newName
	 * @param nameInHierarchy
	 * @throws LdapException
	 */
	private void recursiveUpdateOrganization(String name, String newName,
		String nameInHierarchy) throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method recursiveUpdateOrganization, name: " + name + 
			", newName: " + newName + ", nameInHierarchy: " + nameInHierarchy);

		//Search sub-organizations and remove them before attempting to remove this organization.
		NamingEnumeration enumeration = LdapUtil.list(nameInHierarchy,ldapContext);
		String subOrganization = null;
		while(enumeration.hasMoreElements())
		{
			//Try to remove/copy the son element recursively
			subOrganization = formatSubOrganization(enumeration.nextElement().toString());
			Logger.debug(
				LOGGER_CONTEXT,
				"recursiveUpdateOrganization, current checked subOrganization: " +  
					subOrganization);

			String subOrganizationOldHierarchy = 
				WFLdapConstants.OU_EQUALS_PREFIX+subOrganization+","+nameInHierarchy;
			String subOrganizationNewHierarchy = 
				subOrganizationOldHierarchy.replaceFirst(name,newName);
			LdapUtil.reBind(subOrganizationOldHierarchy,subOrganizationNewHierarchy,ldapContext);
			Logger.debug(
				LOGGER_CONTEXT,
				"recursiveUpdateOrganization, rebinded the subOrganization, new hierarchy: "
				+ subOrganizationNewHierarchy);

			recursiveUpdateOrganization(name, newName, 
				WFLdapConstants.OU_EQUALS_PREFIX+subOrganization+","+
				nameInHierarchy);
			updateUsersConnectedToOrganization(subOrganization,
				name, newName);
			Logger.debug(
				LOGGER_CONTEXT,
				"recursiveUpdateOrganization, called updateUsersConnectedToOrganization");

			removeOrganizationalTeam(subOrganization);
		}

	}

	/**
	 * 
	 * @param name
	 * @param newName
	 * @param nameInHierarchy
	 * @throws LdapException
	 */
	private void recursiveUpdateOrganizationLocation(String name, String parentName,
		String nameInHierarchy) throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method recursiveUpdateOrganizationLocation, name: " + name + 
			", parentName: " + parentName + ", nameInHierarchy: " + nameInHierarchy);


		//Search sub-organizations and remove them before attempting to remove this organization.
		NamingEnumeration enumeration = LdapUtil.list(nameInHierarchy,ldapContext);
		String subOrganization = null;
		while(enumeration.hasMoreElements())
		{
			//Try to remove/copy the son element recursively
			subOrganization = formatSubOrganization(enumeration.nextElement().toString());
			String subOrganizationOldHierarchy = 
				WFLdapConstants.OU_EQUALS_PREFIX+subOrganization+","+nameInHierarchy;
			String subOrganizationNewHierarchy = 
				WFLdapConstants.OU_EQUALS_PREFIX+name+","+
				findHierarchyToRoot(parentName)+WFLdapConstants.ORGANIZATIONS_IN_COMPANY_SUFFIX;

			LdapUtil.reBind(subOrganizationOldHierarchy,subOrganizationNewHierarchy,ldapContext);

			recursiveUpdateOrganizationLocation(name, parentName, 
				WFLdapConstants.OU_EQUALS_PREFIX+subOrganization+","+
				nameInHierarchy);
			updateUsersConnectedToOrganizationLocation(subOrganization,
				name, parentName);
			removeOrganizationalTeam(subOrganization);
		}
	}

		/**
		 * Assistance method
		 * @param name
		 * @param newName
		 * @param parentName
		 * @param nameInHierarchy
		 * @throws LdapException
		 */
		private void recursiveUpdateOrganizationNameAndLocation(String name, String newName,
			String parentName, String nameInHierarchy) throws LdapException
		{

			Logger.debug(
				LOGGER_CONTEXT,
				"started method recursiveUpdateOrganizationLocation, name: " + name + 
				", newName: " + newName + ", parentName: " + parentName + 
				", nameInHierarchy: " + nameInHierarchy);


			//Search sub-organizations and remove them before attempting to remove this organization.
			NamingEnumeration enumeration = LdapUtil.list(nameInHierarchy,ldapContext);
			String subOrganization = null;
			while(enumeration.hasMoreElements())
			{
				//Try to remove/copy the son element recursively
				subOrganization = formatSubOrganization(enumeration.nextElement().toString());
				String subOrganizationOldHierarchy = 
					WFLdapConstants.OU_EQUALS_PREFIX+subOrganization+","+nameInHierarchy;
				String subOrganizationNewHierarchy = 
					WFLdapConstants.OU_EQUALS_PREFIX+newName+","+
					findHierarchyToRoot(parentName)+WFLdapConstants.ORGANIZATIONS_IN_COMPANY_SUFFIX;

				LdapUtil.reBind(subOrganizationOldHierarchy,subOrganizationNewHierarchy,ldapContext);

				recursiveUpdateOrganizationNameAndLocation(name, newName, parentName, 
					WFLdapConstants.OU_EQUALS_PREFIX+subOrganization+","+
					nameInHierarchy);
				updateUsersConnectedToOrganizationLocationAndName(subOrganization,
					name, newName, parentName);
				removeOrganizationalTeam(subOrganization);
			}

	}

	/**
	 * Assistance method used to update all virtual users connected to 
	 * the organization, before deletion of it.
	 * @param organizationName
	 * @param oldName
	 * @param newName
	 * @throws LdapException
	 */
	private void updateUsersConnectedToOrganization(String organizationName,
		String oldName, String newName) 
		throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method updateUsersConnectedToOrganization, organizationName: " + 
			organizationName +	", oldName: " + oldName + ", newName: " + newName);


		//1. find all users with this organization.
		Set members = membersOfOrganization(organizationName);

		//2. delete only users that have only this organization.
		//Ensure that there are such users...
		if(members!=null)
		{
			Iterator iterator = members.iterator();
			while(iterator.hasNext())
			{
				String checkedUser = iterator.next().toString().toLowerCase();
				
				String hierarchy = findHierarchyToRoot(organizationName);
				/*
				 * Update the ou attribute of the user.
				 * Note that if the user is virtual and is connected with the changed
				 * name, have to change the user itself!
				 */
				modifyUserAttribute(checkedUser,WFLdapConstants.OU,
					WFLdapConstants.OU_EQUALS_PREFIX+
					hierarchy.replaceFirst(oldName, newName)+
					WFLdapConstants.ORGANIZATIONS_IN_COMPANY_SUFFIX);

			}					
		}
	}

	/**
	 * Assistance method used to update all virtual users connected to 
	 * the organization, before deletion of it.
	 * @param organizationName
	 * @param name
	 * @param newParentName
	 * @throws LdapException
	 */
	private void updateUsersConnectedToOrganizationLocation(String organizationName,
		String name, String newParentName) 
		throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method updateUsersConnectedToOrganizationLocation, organizationName: " + 
			organizationName +	", name: " + name + ", newParentName: " + newParentName);


		//1. find all users with this organization.
		Set members = membersOfOrganization(organizationName);

		//2. delete only users that have only this organization.
		//Ensure that there are such users...
		if(members!=null)
		{
			Iterator iterator = members.iterator();
			while(iterator.hasNext())
			{
				String checkedUser = iterator.next().toString().toLowerCase();
				
				String hierarchy = findHierarchyToRoot(newParentName);
				modifyUserAttribute(checkedUser,WFLdapConstants.OU,
					WFLdapConstants.OU_EQUALS_PREFIX+name+","+
					WFLdapConstants.OU_EQUALS_PREFIX+hierarchy+
					WFLdapConstants.ORGANIZATIONS_IN_COMPANY_SUFFIX);

			}					
		}
	}

	/**
	 * Assistance method used to update all virtual users connected to 
	 * the organization, before deletion of it.
	 * @param organizationName
	 * @param oldName
	 * @param newName
	 * @param newParentName
	 * @throws LdapException
	 */
	private void updateUsersConnectedToOrganizationLocationAndName(
		String organizationName,
		String oldName, String newName, String newParentName) 
		throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method updateUsersConnectedToOrganizationLocationAndName, organizationName: " + 
			organizationName +	", oldName: " + oldName + ", newName: " + newName +
			", newParentName: " + newParentName);

		//1. find all users with this organization.
		Set members = membersOfOrganization(organizationName);

		//2. delete only users that have only this organization.
		//Ensure that there are such users...
		if(members!=null)
		{
			Iterator iterator = members.iterator();
			while(iterator.hasNext())
			{
				String checkedUser = iterator.next().toString().toLowerCase();
				
				String hierarchy = findHierarchyToRoot(newParentName);
				modifyUserAttribute(checkedUser,WFLdapConstants.OU,
					WFLdapConstants.OU_EQUALS_PREFIX+newName+","+
					WFLdapConstants.OU_EQUALS_PREFIX+hierarchy+
					WFLdapConstants.ORGANIZATIONS_IN_COMPANY_SUFFIX);

			}					
		}
	}
	
	/**
	 * Retrieve the parent organization.
	 * @param organization
	 * @return
	 * @throws LdapException
	 */
	private String getParent(String organization) throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method getParent, organization: " + organization);

		String hierarchy = findHierarchyToRoot(organization);
		int indexOfFirstComma = hierarchy.indexOf(",");
		String parentHierarchy = hierarchy.substring(indexOfFirstComma+1);
		if(parentHierarchy.equals(WFLdapConstants.ORGANIZATIONS_IN_COMPANY))
		{
			//The organization is in the root level
			return organization;
		} else
		{
			//Take the parent organization
			int indexOfEqualSign = parentHierarchy.indexOf("=");
			int indexOfSecondComma = parentHierarchy.indexOf(",");
			if(indexOfSecondComma==-1)
			{
				return parentHierarchy.substring(indexOfEqualSign+1);
			} else
			{
				return parentHierarchy.substring(indexOfEqualSign+1,indexOfSecondComma);
			}
		}
	}
	
	/**
	 * 
	 * @param userId
	 * @return set Roles of the user.
	 * @throws LdapException
	 */
	public Set rolesOfUser(String userId) throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method rolesOfUser, userId: " + userId);

		NamingEnumeration enumeration = 
			LdapUtil.search(WFLdapConstants.ROLES_IN_COMPANY,
			WFLdapConstants.MEMBER_EQUALS_PREFIX+
			WFLdapConstants.CN_EQUALS_PREFIX+
			userId+WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX,
			ldapContext,
			LdapUtil.SEARCH_CONTROLS_SUBTREE_SCOPE);
		return LdapUtil.generateResultSet(enumeration);
	}

	/**
	 * @param userName
	 * @return String authorizationLevelOfUser
	 * @throws LdapException
	 */
	public Integer getAuthorizationLevelOfUser(String userName) throws LdapException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method getAuthorizationLevelOfUser, userName: " + userName);

		try {
			
			String[] attrIds = new String[1];
			attrIds[0] = WFLdapConstants.AUTHORIZATION_LEVEL;
			String fullUserName = 
				WFLdapConstants.CN_EQUALS_PREFIX+userName+WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX;
			Attributes attributes = LdapUtil.getAttributes(fullUserName,attrIds,ldapContext);
			Attribute attribute = attributes.get(WFLdapConstants.AUTHORIZATION_LEVEL);
			
			//TODO: throw exception?
			if(attribute==null)
			{
				return new Integer(0);
			}
			//The user has only one authorization level attribute !!
			Integer authLevel = new Integer(attribute.get(0).toString());
			return authLevel;
		} catch(NamingException nex)
		{
			throw new LdapException(nex);
		}
	}
	
	 /**
	  * Authorizations of this user.
	  * @param userName name of the checked user.
	  * @return set set of all the authorizations of this user.
	  * @throws LdapException.
	  */
	 public Set accessListOfUser(String userName) throws LdapException
	 {
		 try {
	
			Logger.debug(
				LOGGER_CONTEXT,
				"accessList of user started, user name: " + userName);
	
			 StringBuffer temp = new StringBuffer(128);
			 temp.append(WFLdapConstants.CN_EQUALS_PREFIX);
			 temp.append(userName);
			 temp.append(WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX);
			 String[] attrIds = new String[1];
			 attrIds[0] = WFLdapConstants.ACCESS_LIST;
			Logger.debug(
				LOGGER_CONTEXT,
				"accessList of user, attributes area: " + temp.toString());
	
			 Attributes attributes =
				 LdapUtil.getAttributes(temp.toString(), attrIds, ldapContext);
			 Attribute attribute = attributes.get(WFLdapConstants.ACCESS_LIST);
			 if(attribute==null)
			 {
				 return null;
			 } else
			 {
				 NamingEnumeration enumeration = attribute.getAll();
				 return LdapUtil.generateResultSet(enumeration);
			 }
	
		 } catch(NamingException nex)
		 {
			 throw new LdapException(nex);
		 }
	
	 }

	/**
	 * @param userId
	 * @return UserLdapDetails wrapper object returning the user details from Ldap.
	 * @throws LdapException
	 */
	public UserLdapDetails getUserDetails(String userId) throws LdapException
	{	

		Logger.debug(
			LOGGER_CONTEXT,
			"started method getUserDetails, userId: "+ userId);

		try
		{
			//String owner = WFLdapConstants.OWNER;	
			String[] attrIds = new String[2];
			attrIds[0] = WFLdapConstants.GIVEN_NAME;
			attrIds[1] = WFLdapConstants.SN;
			Attributes attributes = LdapUtil.getAttributes(WFLdapConstants.CN_EQUALS_PREFIX+
				userId+WFLdapConstants.PEOPLE_IN_COMPANY_SUFFIX, attrIds, ldapContext);
			Attribute givenNameAttribute = attributes.get(WFLdapConstants.GIVEN_NAME);
			Attribute familyNameAttribute = attributes.get(WFLdapConstants.SN);
			
			Object tempPrivateName = null;
			Object tempFamilyName = null;
			String privateName = null;
			String familyName = null;
			if(givenNameAttribute!=null)
			{
				tempPrivateName = givenNameAttribute.get(0);
				if(tempPrivateName!=null)
				{
					privateName = tempPrivateName.toString();
				}
			}
		
			if(familyNameAttribute!=null)
			{
				tempFamilyName = familyNameAttribute.get(0);
				if(tempFamilyName!=null)
				{
					familyName = tempFamilyName.toString();
				}
			}
			
			UserLdapDetails userDetails = 
				new UserLdapDetails(userId, privateName, familyName);
	
			Logger.debug(
				LOGGER_CONTEXT,
				"finished method getUserDetails, privateName: "
				+ privateName + ", familyName: " + familyName);
			return userDetails;
		}
		catch (NamingException e)
		{
			throw new LdapException(e);
		}
		
	}

	/**
	 * @param organizationName
	 * @return String name of the organization manager.
	 * @throws LdapException
	 */
	public String getOrganizationManager(String organizationName) throws LdapException
	{	

		Logger.debug(
			LOGGER_CONTEXT,
			"started method getOrganizationManager, organizationName: "+ organizationName);

		try
		{
			String[] attrIds = new String[1];
			attrIds[0] = WFLdapConstants.MANAGER;

			/*
			 * Calling findHierarchyToRoot in order to get the full organization
			 * hierarchy - Required for retrieval of the correct attributes.
			 */
			Attributes attributes = 
				LdapUtil.getAttributes(findHierarchyToRoot(organizationName) + 
				WFLdapConstants.ORGANIZATIONS_IN_COMPANY_SUFFIX, 
				attrIds, ldapContext);
			Attribute managerAttribute = attributes.get(WFLdapConstants.MANAGER);
			
			Object managerName = null;
			String manager = null;
			if(managerAttribute!=null)
			{
				managerName = managerAttribute.get(0);

				if(managerName!=null)
				{
					manager = getNameFromAttribute(managerName.toString()).toUpperCase();
				}
			}
		
			Logger.debug(
				LOGGER_CONTEXT,
				"finished method getOrganizationManager, manager: " + manager);
			return manager;
		}
		catch (NamingException e)
		{
			throw new LdapException(e);
		}
		
	}
	
	/**
	 * Get the virtual user Id, according to the given role and organization.
	 * @param role
	 * @param organization
	 * @return
	 * @throws LdapException
	 */
	public String findVirtualUserId(String role, String organization) throws LdapException
	{
		return createVirtualUserName(role, organization);

		/*
		Logger.debug(
			LOGGER_CONTEXT,
			"started findVirtualUserId, role: " + role + ", organization: " + organization);

		Set set = this.membersOfRole(role);
		String tempOrganizationalTeam = null;
		String result = null;
		String currentUser = null;
		if(set!=null)
		{
			Iterator iterator = set.iterator();
			while(iterator.hasNext())
			{
				currentUser = iterator.next().toString();
				//TODO: check for the user if it has also the organizational team
				tempOrganizationalTeam = this.getOrganizationalTeam(currentUser);
				if(organization.equals(tempOrganizationalTeam))
				{
					result = currentUser;
					break;
				}
			}
			
		}
		
		// Note: if the original set is null, returned result is null.
		return result;
		*/
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	protected void finalize() throws Throwable
	{
		super.finalize();
		if (ldapContext != null)
		{
			ldapContext.close();
		}
	}

}
