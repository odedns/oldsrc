/*
 * Created on: 22/12/2004
 * @author: baruch hizkya
 * @version $Id: LanguagesManager.java,v 1.3 2005/05/08 12:51:00 yifat Exp $
 */

package com.ness.fw.flower.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import org.w3c.dom.*;
import com.ness.fw.common.externalization.XMLUtil;
import com.ness.fw.common.externalization.XMLUtilException;
import com.ness.fw.common.logger.Logger;

/**
 * 
 * @author bhizkya
 *
 * This class handle the languages in the system, and support convienient methods
 * to get a set of properties og languages according to locale
 */
public class LanguagesManager
{

	/**
	 * XML Tags
	 */
	private static final String LANGUAGE_SET_TAG_NAME		  = "languageSet";
	private static final String LANGUAGE_DEFINITION_TAG_NAME  = "definition";
	private static final String LANGUAGE_COPY_DEF_TAG_NAME    = "copyDefFrom";

	/**
	 * XML Attributes
	 */
	private static final String LANGUAGE_ATTR   			  = "language";
	private static final String COUNTRY_ATTR    			  = "country";
	private static final String DIRECTION_ATTR    			  = "direction";
	private static final String FROM_LANGUAGE_ATTR   		  = "fromLanguage";
	private static final String FROM_COUNTRY_ATTR    		  = "fromCountry";
	private static final String ENCODING_ATTR   			  = "encoding";
	private static final String CHARSET_ATTR    			  = "charset";
	private static final String RESOURCES_DIRECTORY_ATTR      = "resourcesDirectoryName";
	private static final String IMAGES_DIRECTORY_ATTR         = "imagesDirectoryName";
	private static final String FLAG_IMAGE_ATTR 			  = "flagImage";
	private static final String DEFAULT_LANGUAGE_ATTR 		  = "defaultLanguage";
	private static final String VALUE_ATTR 					  = "value";
	
	/**
	 * The logger context
	 */
	private static final String LOGGER_CONTEXT 				  = "LANGUAGES";

		
	/**
	 * A container to hold all the languages 
	 */
	private static HashMap languages = new HashMap();

	private static String defaultSetLanguage = null;
	private static String defaultSetCountry = null;


	/**
	 * Initialize the supported languages of the system
	 * @param langauageConf
	 * @throws XMLUtilException
	 * @throws LanguageException
	 */
	public static void initizlize(String langauageConf) throws XMLUtilException, LanguageException
	{
		initLanguages(langauageConf);
	}

	/**
	 * Get a language set according to specific locale. The method always 
	 * return a languageSet - if no appropiate found, return according to
	 * the default one
	 * @param locale
	 * @return
	 */
	public static LanguageSet getLanguageSet(Locale locale)
	{
		String language = locale.getLanguage();
		String country = locale.getCountry();
		LanguageSet languageSet = (LanguageSet)languages.get(language + (!country.equals("") ? "_" + country : ""));
		// no suitable languageSet according the locale was found, return the
		// languageSet that coressponding to the default language
		if (languageSet == null)
		{
			languageSet = (LanguageSet)languages.get(getLocaleStrByDefaultLanguageSet());
		}
		return languageSet;		
	}
	

	/**
	 * Get the default langaugeSet according to the default language
	 * @return
	 */
	public static LanguageSet getDefaultLanguageSet()
	{
		return getLanguageSet(getLocaleByDefaultLanguageSet());
	}
	
	/**
	 * Get all supporet locales, whice represented by string containing 
	 * language + country
	 * @return
	 */
	public static Iterator getSupportedLocales()
	{
		return languages.keySet().iterator();
	}


	/**
	 * Get the supported locale by the system. if the locale not supported 
	 * by the system declaration supported locales, default one will be
	 * returned, according to the default language
	 * @param locale the desired locale
	 * @return
	 */
	public static Locale getSupportedLocale(Locale locale)
	{
		Locale supportedLocale = null;
		String localeStr = getLocaleStr(locale.getLanguage(), locale.getCountry());
		if (languages.containsKey(localeStr))
		{
			// get the locale from the language set, because it may be a 
			// reference to other set.
			LanguageSet languageSet = (LanguageSet)languages.get(localeStr);
			supportedLocale = languageSet.getLocale();
		}
		// not found locale, get the default one
		else
		{
			supportedLocale = getLocaleByDefaultLanguageSet();
		}
		return supportedLocale;
	}


	/**
	 * GEt the default locale
	 * @return
	 */
	public static Locale getLocaleByDefaultLanguageSet()
	{
		return new Locale(defaultSetLanguage,defaultSetCountry);
	}

	/**
	 * Get formatted locale
	 * @param language
	 * @param country
	 * @return
	 */
	private static String getLocaleStr(String language, String country)
	{
		String localeStr = language + (!country.equals("") ? "_" + country : "");
		return localeStr; 
	}


	/**
	 * Get formatted default locale
	 * @return
	 */
	private static String getLocaleStrByDefaultLanguageSet()
	{
		return getLocaleStr(defaultSetLanguage, defaultSetCountry); 
	}

	/**
	 * Init all the languages in the system
	 * @param langauageConf
	 * @throws XMLUtilException
	 * @throws LanguageException
	 */
	private static void initLanguages(String langauageConf) throws XMLUtilException, LanguageException
	{
		synchronized (languages)
		{
			Logger.debug(LOGGER_CONTEXT, "loading languages");
			
			languages.clear();
						
			Document doc = XMLUtil.readXML(langauageConf, false);
			
			//retrieve root element
			Element documentElement = doc.getDocumentElement();	
			loadLangauges(documentElement);
			
			// check if the default language has a definition
			checkLanguagesValidity(documentElement);
		}			
	}

	/**
	 * A helper method to create languages set 
	 * @param documentElement
	 * @throws XMLUtilException
	 * @throws LanguageException
	 */
	private static void loadLangauges(Element documentElement) throws XMLUtilException, LanguageException
	{
		
		NodeList nodes = XMLUtil.getElementsByTagName(documentElement, LANGUAGE_SET_TAG_NAME);

		String language;
		String country;
		Element languageElement = null;
	
		// for each element set, create an element set and process it's content
		for (int i = 0; i < nodes.getLength(); i++)
		{
			languageElement = (Element) nodes.item(i);
			language = languageElement.getAttribute(LANGUAGE_ATTR);
			country = languageElement.getAttribute(COUNTRY_ATTR);

			LanguageSet languageSet = new LanguageSet(language,country);
			processSet(languageElement, languageSet);
			Logger.info(LOGGER_CONTEXT,"langauge set " + language + "_" + country + " reloaded");
		}
	}
	
	
	/**
	 * A helper method to process the content of ElementSet
	 * @param languageElement
	 * @param languageSet
	 * @throws XMLUtilException
	 * @throws LanguageException
	 */
	private static void processSet(Element languageElement, LanguageSet languageSet) throws XMLUtilException, LanguageException
	{
		String encoding;
		String charset;
		String direction;
		String imagesDirectoryName;
		String flagImage;		

		String fromLanguage;
		String fromCountry;
		boolean defintionDeclared = false;


		// getting the definition
		NodeList nodes = XMLUtil.getElementsByTagName(languageElement, LANGUAGE_DEFINITION_TAG_NAME);		

		if (nodes.getLength() > 1)
		{
			throw new LanguageException("Can't declared more than one " +  LANGUAGE_DEFINITION_TAG_NAME  + " tag");
		}

		for (int i = 0; i < nodes.getLength(); i++)
		{
			defintionDeclared = true;
			Element definitionElement = (Element)nodes.item(i);	
			encoding = definitionElement.getAttribute(ENCODING_ATTR);
			charset = definitionElement.getAttribute(CHARSET_ATTR);
			direction = definitionElement.getAttribute(DIRECTION_ATTR);
			imagesDirectoryName = definitionElement.getAttribute(IMAGES_DIRECTORY_ATTR);
			flagImage = definitionElement.getAttribute(FLAG_IMAGE_ATTR);

			languageSet.setEncoding(encoding);
			languageSet.setCharset(charset);
			languageSet.setDirection(direction);
			languageSet.setFlagImage(flagImage);
			languageSet.setImagesDirectoryName(imagesDirectoryName);

			languages.put(languageSet.getLanguage() + "_" + languageSet.getCountry(), languageSet);

		}
		
		// getting the copyDefFrom
		nodes = XMLUtil.getElementsByTagName(languageElement, LANGUAGE_COPY_DEF_TAG_NAME);		

		// both definition and copyDefFrom shouldn't be declared
		if (defintionDeclared && nodes.getLength() > 0)
		{
			throw new LanguageException("Can't declare " + LANGUAGE_DEFINITION_TAG_NAME + " and " + LANGUAGE_COPY_DEF_TAG_NAME + " .only one of them should be declared");
		}
	
		if (nodes.getLength() > 1)
		{
			throw new LanguageException("Can't declared more than one copyDefFr tag");
		}

		for (int i = 0; i < nodes.getLength(); i++)
		{
			Element definitionElement = (Element)nodes.item(i);	
			fromLanguage = definitionElement.getAttribute(FROM_LANGUAGE_ATTR);
			fromCountry = definitionElement.getAttribute(FROM_COUNTRY_ATTR);
			// check if language set exists
			if (!languages.containsKey(fromLanguage + "_" + fromCountry))
			{
				throw new LanguageException("elment set with language " + fromLanguage + " and country " + fromCountry + " wasn't declread or wasn't order properly");
			}
			// add an entry that referenced to exists language set
			String key = languageSet.getLanguage();
			if (languageSet.getCountry() != null && !languageSet.getCountry().equals(""))
			{
				key += "_" + languageSet.getCountry();
			}
			languages.put(key, languages.get(fromLanguage + "_" + fromCountry));
		}
	}
	

	/**
	 * check if one of the langauges set suitable to the defaultLanguage
	 * @param documentElement
	 * @throws LanguageException
	 */
	private static void checkLanguagesValidity(Element documentElement) throws LanguageException
	{
		// getting the default language
		NodeList nodes = XMLUtil.getElementsByTagName(documentElement, DEFAULT_LANGUAGE_ATTR);
		defaultSetLanguage = ((Element)nodes.item(0)).getAttribute(LANGUAGE_ATTR);
		defaultSetCountry = ((Element)nodes.item(0)).getAttribute(COUNTRY_ATTR);
		if (defaultSetCountry.equals(""))
		{
			defaultSetCountry = null;
		}

	
		// check if one of the langauges set suitable to the defaultLanguage
		if (!languages.containsKey(defaultSetLanguage + (defaultSetCountry != null ? "_" + defaultSetCountry : "")))
		{
			throw new LanguageException("no language set was defined for default language " + defaultSetLanguage);
		}

		Logger.info(LOGGER_CONTEXT,"default langauge set is " + defaultSetLanguage);

	}
}