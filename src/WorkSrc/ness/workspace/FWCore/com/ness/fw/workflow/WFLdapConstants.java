/*
 * Created on: 16/12/2004
 *
 * Author: Amit Mendelson
 * @version $Id: WFLdapConstants.java,v 1.6 2005/04/20 14:35:30 amit Exp $
 */
package com.ness.fw.workflow;

/**
 * This class holds all the constants used by WorkFlow package for working with LDAP.
 */
public class WFLdapConstants
{

	/**
	 * ROLE_ORGANIZATION_DELIMITER
	 * Separator between role and organization for virtual user name.
	 */
	public static final String ROLE_ORGANIZATION_DELIMITER = "_";


	/**
	 * OBJECT_CLASS
	 */
	public static final String OBJECT_CLASS = "objectClass";
	
	/**
	 * INET_ORG_PERSON
	 */
	public static final String INET_ORG_PERSON = "inetOrgPerson";
	
	/**
	 * AUTO_INITIATE
	 */
	public static final String AUTO_INITIATE = "autoInitiate";
	
	/**
	 * TRUE
	 */
	public static final String TRUE = "true";
	
	/**
	 * ACCESS_LIST
	 */
	public static final String ACCESS_LIST = "accessList";
	
	/**
	 * CN_ALL_PERSON_IN_COMPANY
	 */
	public static final String CN_ALL_PERSON_IN_COMPANY =
		"cn=allperson, ou=People, o=company";

	/**
	 * BUSINESS_CATEGORY
	 */
	public static final String BUSINESS_CATEGORY = "BusinessCategory";

	/**
	 * CN_PREFIX
	 */
	public static final String CN_EQUALS_PREFIX = "cn=";
	
	/**
	 * PEOPLE_IN_COMPANY_SUFFIX
	 */
	public static final String PEOPLE_IN_COMPANY_SUFFIX = ", ou=People, o=company";

	/**
	 * PEOPLE_IN_COMPANY
	 */
	public static final String PEOPLE_IN_COMPANY = "ou=People, o=company";


	/**
	 * ORGANIZATIONAL_PERSON
	 */
	public static final String ORGANIZATIONAL_PERSON = "organizationalPerson";
	
	/**
	 * PERSON
	 */
	public static final String PERSON = "person";
	
	/**
	 * TOP
	 */
	public static final String TOP = "top";

	/**
	 * CN
	 */
	public static final String CN = "cn";

	/**
	 * DESCRIPTION
	 */
	public static final String DESCRIPTION = "description";
	
	/**
	 * OU
	 */
	public static final String OU = "ou";

	/**
	 * OU_EQUALS_PREFIX
	 */
	public static final String OU_EQUALS_PREFIX = "ou=";

	/**
	 * ORGANIZATIONS_IN_COMPANY_SUFFIX
	 */
	public static final String ORGANIZATIONS_IN_COMPANY_SUFFIX =
		",ou=Organizations,o=company";

	/**
	 * ORGANIZATIONS_IN_COMPANY
	 */
	public static final String ORGANIZATIONS_IN_COMPANY =
		"ou=Organizations,o=company";

	/**
	 * MEMBER
	 */
	public static final String MEMBER = "member";

	/**
	 * MEMBER_EQUALS_PREFIX
	 */
	public static final String MEMBER_EQUALS_PREFIX = "member=";


	/**
	 * ROLES_IN_COMPANY_SUFFIX
	 */
	public static final String ROLES_IN_COMPANY_SUFFIX =
		", ou=Roles, o=company";

	/**
	 * ROLES_IN_COMPANY
	 */
	public static final String ROLES_IN_COMPANY =
		"ou=Roles, o=company";

	/**
	 * GROUP_OF_NAMES
	 */
	public static final String GROUP_OF_NAMES = "groupOfNames";

	/**
	 * OWNER
	 */
	public static final String OWNER = "owner";
	
	/**
	 * ORGANIZATIONAL_UNIT
	 */	
	public static final String ORGANIZATIONAL_UNIT = "organizationalUnit";
	
	/**
	 * MANAGER
	 */
	public static final String MANAGER = "manager";

	/**
	 * MANAGER_UPPERCASED
	 */
	public static final String MANAGER_UPPERCASED = "MANAGER";

	/**
	 * DEFAULT_MANAGER
	 */
	public static final String DEFAULT_MANAGER = "defaultManager";
	
	/**
	 * OU_INFIX
	 */
	public static final String OU_INFIX = ", ou=";

	/**
	 * CN_EQUALS_STAR
	 */
	public static final String CN_EQUALS_STAR = "cn=*";
	
	/**
	 * OU_EQUALS_STAR
	 */
	public static final String OU_EQUALS_STAR = "ou=*";
	
	/**
	 * ACCESS_LIST_EQUALS_CN
	 */
	public static final String ACCESS_LIST_EQUALS_CN = "accessList=cn=";
	
	/**
	 * OU_EQUALS_IN_PARENTHESIS
	 */
	public static final String OU_EQUALS_IN_PARENTHESIS = "(ou=";
	/**
	 * COMMA_DELIMITER
	 */
	public static final String COMMA_DELIMITER = ",";
	
	/**
	 * EQUALS_SIGN
	 */
	public static final String EQUALS_SIGN = "=";
	
	/**
	 * GIVEN_NAME
	 * An attribute in LDAP.
	 */
	public static final String GIVEN_NAME = "givenname";
	
	/**
	 * SN
	 * LDAP attribute that represents the family Name.
	 */
	public static final String SN = "sn";
	
	/**
	 * MEMBER_EQUALS_CN
	 * 
	 */
	public static final String MEMBER_EQUALS_CN = "member=cn=";
	
	/**
	 * AUTHORIZATION_LEVEL
	 */
	public static final String AUTHORIZATION_LEVEL = "authorizationLevel";

	/**
	 * AUTHORIZING_USER
	 */
	public static final String AUTHORIZING_USER = "מאשר דרגה";
	
//	/**
//	 * AUTH_PROCESS_CATEGORY_ALL
//	 * Is used as the user category
//	 */
//	public static final String AUTH_PROCESS_CATEGORY_ALL = "organizationalStatus";

	/**
	 * USER_CLASS
	 * Is used to give the manager authorization for all users.
	 */
	public static final String USER_CLASS = "userClass";

}
