/*
 * Created on 19/09/2004
 * Author: Amit Mendelson
 * @version $Id: LdapUtil.java,v 1.2 2005/04/19 15:07:42 amit Exp $
 */
package com.ness.fw.util;

import javax.naming.*;
import javax.naming.directory.*;

import java.util.Hashtable;
import java.util.Set;
import java.util.HashSet;

/**
 * This class defines the methods used for LDAP service connection,
 * and the relevant LDAP connection variables.
 */
public class LdapUtil
{

	/**
	 * LDAP_INITIAL_CONTEXT_FACTORY
	 */
	public static final String LDAP_INITIAL_CONTEXT_FACTORY = 
		"com.sun.jndi.ldap.LdapCtxFactory";

	/**
	 * PROVIDER_URL_KEY - URL of the LDAP provider.
	 */
	public static final String PROVIDER_URL_KEY = "Ldap.ProviderURL";

	/**
	 * SECURITY_AUTHENTICATION_KEY
	 */
	public static final String SECURITY_AUTHENTICATION_KEY = 
		"Ldap.SecurityAuthentication";

	/**
	 * SECURITY_PRINCIPAL_KEY
	 */
	public static final String SECURITY_PRINCIPAL_KEY = "Ldap.SecurityPrincipal";

	/**
	 * SECURITY_CREDENTIALS_KEY
	 */
	public static final String SECURITY_CREDENTIALS_KEY = "Ldap.SecurityCredentials";
	
	/**
	 * SEARCH_CONTROLS_SUBTREE_SCOPE
	 * Is used to search for a string in the sub-tree scope.
	 */
	public static final int SEARCH_CONTROLS_SUBTREE_SCOPE = 10;
	
	/**
	 * SEARCH_CONTROLS_OBJECT_SCOPE
	 * Is used to search for a string in the current object scope.
	 */
	public static final int SEARCH_CONTROLS_OBJECT_SCOPE = 20;

	/**
	 * SEARCH_CONTROLS_OBJECT_SCOPE
	 * Is used to search for a string in the level below scope.
	 */
	public static final int SEARCH_CONTROLS_ONE_LEVEL_SCOPE = 30;

	/**
	 * This method opens a connection to the LDAP service whose parameters
	 * are received.
	 * @param providerUrl
	 * @param securityAuthentication
	 * @param securityPrincipal specify the username
	 * @param securityCredentials specify the password
	 * @return DirContext - open connection to the LDAP service.
	 * @throws javax.naming.NamingException
	 */
	public static DirContext ldapConnect(String providerUrl, 
		String securityAuthentication, String securityPrincipal,
		String securityCredentials) throws LdapException
	{
		
		try {
		Hashtable ldapEnvironment = new Hashtable();
			
		//fill the HashTable with javax.naming.Context elements.
		ldapEnvironment.put(Context.INITIAL_CONTEXT_FACTORY, 
			LDAP_INITIAL_CONTEXT_FACTORY);
		ldapEnvironment.put(Context.PROVIDER_URL, providerUrl);
		ldapEnvironment.put(Context.SECURITY_AUTHENTICATION, 
			securityAuthentication);
		ldapEnvironment.put(Context.SECURITY_PRINCIPAL, securityPrincipal);
		ldapEnvironment.put(Context.SECURITY_CREDENTIALS, securityCredentials); 

		return new InitialDirContext(ldapEnvironment);
		} catch (NamingException ne)
		{
			throw new LdapException(ne);	
		}
	}	

	/**
	 * Perform a search for the given searchString in the given area in the given
	 * DirContext. The search string is assumed to be built in advance.
	 * @param area E.g. "accessList=cn=Amit, ou=people, o=company"
	 * @param searchString String to be searched
	 * @param context
	 * @param searchOption
	 * @return NamingEnumeration with all results
	 * @throws LdapException
	 */
	public static NamingEnumeration search(String area, String searchString, 
		DirContext context, int searchOption) throws LdapException
	{

		try {
			//Determines the search scope.
			SearchControls controls = new SearchControls();

			//Set the search controls.
			switch(searchOption)
			{
				case SEARCH_CONTROLS_SUBTREE_SCOPE:
					controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
					break;
				case SEARCH_CONTROLS_OBJECT_SCOPE:
					controls.setSearchScope(SearchControls.OBJECT_SCOPE);
					break;
				case SEARCH_CONTROLS_ONE_LEVEL_SCOPE:
					controls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
					break;
				default:
					controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			}

			return context.search(area, searchString, controls);		
		} catch(NamingException nex)
		{
			throw new LdapException(nex);
		}
	}

	/**
	 * Perform a search for the given searchString in the given area in the given
	 * DirContext. The search string is assumed to be built in advance.
	 * @param area E.g. "accessList=cn=Amit, ou=people, o=company"
	 * @param matchingAttributes attributes to be searched
	 * @param context
	 * @return NamingEnumeration with all results
	 * @throws LdapException
	 */
	public static NamingEnumeration search(String name, Attributes matchingAttributes, 
		DirContext context) throws LdapException
	{

		try {

			return context.search(name, matchingAttributes);		
		} catch(NamingException nex)
		{
			throw new LdapException(nex);
		}
	}
	
	/**
	 * Return the requested attributes
	 * @param name Name of the schema to be searched (can be either role, organiztation
	 * or user).
	 * @param attrIds Names of the attributes to be searched.
	 * @param context 
	 * @return Attributes all the relevant attributes
	 * @throws LdapException
	 */
	public static Attributes getAttributes(String name, String[] attrIds, 
		DirContext context) throws LdapException
	{
		try {

			return context.getAttributes(name, attrIds);		
		} catch(NamingException nex)
		{
			throw new LdapException(nex);
		}
		
	}

	/**
	 * Addition of a single attribute to the given DirContext.
	 * If the attribute already exists - ?
	 * @param name
	 * @param context
	 * @param attr
	 * @throws LdapException
	 */
	public static void addAttribute(String name, DirContext context, Attribute attr) 
		throws LdapException
	{
		try {
			
			Attributes attrs = new BasicAttributes();
			attrs.put(attr);
			context.modifyAttributes(name, DirContext.ADD_ATTRIBUTE, attrs);	
		} catch(NamingException nex) 
		{
			throw new LdapException(nex);
		}	
	}
	
	/**
	 * Modification of a single attribute to the given DirContext.
	 * If the attribute does not exist - ?
	 * @param name
	 * @param context
	 * @param attr
	 * @throws LdapException
	 */
	public static void modifyAttribute(String name, DirContext context, Attribute attr) 
		throws LdapException
	{
		try {
			Attributes attrs = new BasicAttributes();
			attrs.put(attr);
			context.modifyAttributes(name, DirContext.REPLACE_ATTRIBUTE, attrs);	
		} catch(NamingException nex) 
		{
			throw new LdapException(nex);
		}	
	}

	/**
	 * Modification of some attributes to the given DirContext.
	 * @param name
	 * @param context
	 * @param attrs
	 * @throws LdapException
	 */
	public static void modifyAttributes(String name, DirContext context, Attributes attrs) 
		throws LdapException
	{
		try {
			context.modifyAttributes(name, DirContext.REPLACE_ATTRIBUTE, attrs);	
		} catch(NamingException nex) 
		{
			throw new LdapException(nex);
		}	
	}
	
	/**
	 * Removal of a single attribute from the given DirContext.
	 * If the attribute does not exist, throw an LDAPException
	 * @param name
	 * @param context
	 * @param attr
	 * @throws LdapException
	 */
	public static void removeAttribute(String name, DirContext context, Attribute attr) 
	throws LdapException
	{
		try {
			Attributes attrs = new BasicAttributes();
			attrs.put(attr);
			context.modifyAttributes(name, DirContext.REMOVE_ATTRIBUTE, attrs);
		} catch(NamingException nex)
		{
			throw new LdapException(nex);
		}	
	}

	/**
	 * Creation of a new sub-context
	 * @param name Name of the new sub-context (should contain the exact path)
	 * @param attrs
	 * @param context
	 * @throws LdapException
	 */
	public static DirContext createSubContext(String name, Attributes attrs, 
		DirContext context) throws LdapException
	{
		try {
			return context.createSubcontext(name, attrs);
		} catch(NamingException ne)
		{
			throw new LdapException(ne);
		}
	}
	
	/**
	 * Unbinding of the named object from the given context.
	 * @param name
	 * @param context
	 * @throws NamingException
	 */
	public static void unBind(String name, DirContext context) throws LdapException
	{
		try {
			context.unbind(name);

		} catch (NamingException nex)
		{
			throw new LdapException(nex);
		}
	}

	/**
	 * Rebinding of the named object from the given context.
	 * @param name the full name in schema
	 * @param newName
	 * @param context
	 * @throws NamingException
	 */
	public static void reBind(String name, String newName, 
		DirContext context) throws LdapException
	{
		try {
			DirContext renamedContext = context.getSchema(name);
			//save the attributes of the original schema.
			Attributes attrs = context.getAttributes(name);
			context.rebind(newName, renamedContext, attrs);

		} catch (NamingException nex)
		{
			throw new LdapException(nex);
		}
	}
	
	/**
	 * Assistance method - Is used by WFLdapManager.
	 * @param enumeration The unprocessed query results.
	 * @return Set The processed set generated from the NamingEnumeration
	 * @throws LdapException.
	 */
	public static Set generateResultSet(NamingEnumeration enumeration) 
		throws LdapException
	{

		try {
			Set result = new HashSet();
			int indexOfEqualSign = -1;
			int indexOfCommaSign = -1;
			String addedData = null;
			while(enumeration.hasMoreElements())
			{

				String enumerationNext = enumeration.next().toString();
				indexOfEqualSign = enumerationNext.indexOf("=");
				indexOfCommaSign = enumerationNext.indexOf(",");

				/*
				 * Conversion to upper case is required, otherwise
				 * WorkFlow will throw an exception.
				 */
				if((indexOfEqualSign==-1)&&(indexOfCommaSign==-1))
				{
					addedData = enumerationNext.toUpperCase();
				} else
				{
					addedData =
						enumerationNext
							.substring(indexOfEqualSign + 1, indexOfCommaSign)
								.toUpperCase();					
				}
				result.add(formatString(addedData));
			}
			return result;

		} catch(NamingException nex)
		{
			throw new LdapException(nex);
		}
		
	}

	/**
	 * Assistance method - truncate the naming enumeration results.
	 * Is used by WFLdapManager.
	 * @param name
	 * @return String the formatted String.
	 */
	public static String formatString(String name)
	{
		String result = name;
		if (name.indexOf(':')>-1)
		{
			result = name.substring(0, name.indexOf(':'));
		}
		return result;
	}
	
	/**
	 * Returns enumeration of the names bound in the named context.
	 * @param name Name of the context (can be sub-context).
	 * @param context
	 * @return NamingEnumeration
	 * @throws LdapException
	 */
	public static NamingEnumeration list(String name, DirContext context)
		throws LdapException
	{
		try
		{
			return context.list(name);
		}
		catch (NamingException e)
		{
			throw new LdapException(e);
		}
	}

}
