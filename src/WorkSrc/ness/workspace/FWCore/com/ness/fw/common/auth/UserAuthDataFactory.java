/*
 * Created on 19/05/2004
 * Author: yifat har-nof
 * @version $Id: UserAuthDataFactory.java,v 1.6 2005/05/08 13:45:27 yifat Exp $
 */
package com.ness.fw.common.auth;


import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.ness.fw.common.externalization.XMLUtil;
import com.ness.fw.common.externalization.XMLUtilException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.resources.ResourceUtils;

/**
 * Provide authorization managers.
 */
public class UserAuthDataFactory
{
	public static final String LOGGER_CONTEXT = "USER AUTH DATA FACTORY";
	
	private static final String USER_DATA_XML_FILE = "/config/UserLoginTempData.xml";
	
	private static final String TEMP_USER_ID = "123"; 
	private static final String LOGIN_DATA_TAG = "loginData"; 
	private static final String USER_ID_TAG = "userID";
	private static final String AUTH_USER_ID_TAG = "authorizationUserID";
	private static final String USER_TYPE_TAG = "userType";
	private static final String AUTH_TYPE_TAG = "authenticationType";


	/**
	 * Creates UserAuthData with the given parameters. 
	 * @param userID The user id of the current user (from login).
	 * @param authorizationUserID The authorization user id of the current user. 
	 * @param userType The user type which the current user loged on with. 
	 * could be: customer, company employee, agent.
	 * @param authenticationType The authentication type. could be: login, card etc. 
	 * @return UserAuthData
	 */
	public static UserAuthData getUserAuthData (String userID, String authorizationUserID, int userType, int authenticationType, String sessionID)
	{
		return new UserAuthData(userID, authorizationUserID, userType, authenticationType, sessionID);
	}

	/**
	 * Creates UserAuthData with the given userId (used for userId & authorizationUserID).
	 * The userType is set to USER_TYPE_NONE.
	 * The authenticationType is set to AUTHENTICATION_TYPE_NONE.
	 * @param userID The user id of the current user (from login).
	 * @return UserAuthData
	 */
	public static UserAuthData getUserAuthData (String userID)
	{
		return new UserAuthData(userID, userID, UserAuthData.USER_TYPE_NONE, UserAuthData.AUTHENTICATION_TYPE_NONE, null);
	}

	/**
	 * Creates UserAuthData.
	 * @param emptyUser If set to true, creates the object for an empty user id. 
	 * If set to false, creates the object with the paramaters declared in the xml file.
	 * @return
	 */
	public static UserAuthData getEmptyUserAuthData ()
	{
		return new UserAuthData(UserAuthData.EMPTY_USER_ID, UserAuthData.EMPTY_USER_ID, UserAuthData.USER_TYPE_NONE, UserAuthData.AUTHENTICATION_TYPE_NONE, null);
	}

	/**
	 * Creates UserAuthData with the paramaters declared in the xml file.
	 * @return UserAuthData
	 */
	public static UserAuthData getUserAuthDataFromXML (String sessionId)
	{
		String userID = TEMP_USER_ID;
		String authorizationUserID = TEMP_USER_ID;
		int userType = UserAuthData.USER_TYPE_AGENT;
		int authenticationType = UserAuthData.AUTHENTICATION_TYPE_LOGIN;		
		
		try
		{
			String path = ResourceUtils.getResourceAbsolutePath(USER_DATA_XML_FILE);
			Document document =  XMLUtil.readXML(path, false);
			Element rootElement = document.getDocumentElement();
			NodeList nodeList = XMLUtil.getElementsByTagName(rootElement, LOGIN_DATA_TAG);
			if(nodeList.getLength() > 0)
			{
				Element element = (Element) nodeList.item(0);
				userID = XMLUtil.getAttribute(element, USER_ID_TAG);
				authorizationUserID = XMLUtil.getAttribute(element, AUTH_USER_ID_TAG);
				userType = Integer.parseInt(XMLUtil.getAttribute(element, USER_TYPE_TAG));
				authenticationType = Integer.parseInt(XMLUtil.getAttribute(element, AUTH_TYPE_TAG));
			}
		}
		catch (IOException e)
		{
			Logger.debug(LOGGER_CONTEXT, "The file /config/UserLoginTempData.xml is missing.", e);
		}
		catch (XMLUtilException e)
		{
			Logger.debug(LOGGER_CONTEXT, "The file /config/UserLoginTempData.xml is missing.", e);
		}
		
		Logger.debug(LOGGER_CONTEXT, "user login data: userID=" + userID + " authorizationUserID=" + authorizationUserID + " userType=" + userType + " authenticationType=" + authenticationType);
		
		return new UserAuthData(userID, authorizationUserID, userType, authenticationType, sessionId);
	}

}
