/*
 * Created on: 22/12/2004
 * @author: baruch hizkya
 * @version $Id: LanguageSet.java,v 1.1 2005/02/21 15:07:10 baruch Exp $
 */

package com.ness.fw.flower.common;

import java.util.Locale;

/**
 * 
 * @author bhizkya
 *
 * This class represent a set of properties of language according to locale.
 * for instance, The Hebrew language represented by the locale: he_IL
 */
public class LanguageSet
{

	private String language;
	private String country;
	private String encoding;
	private String charset;
	private String imagesDirectoryName;
	private String flagImage;
	private String direction;
	private Locale locale;


	/**
	 * Constructor
	 * @param language
	 * @param country
	 */
	public LanguageSet(String language,String country)
	{
		this.language = language;
		this.country  = country;
		locale = new Locale(language,country);
	}


	/**
	 * Constructor
	 * @param language
	 * @param country
	 * @param encoding
	 * @param charset
	 */
	public LanguageSet(String language,String country, String encoding, String charset)
	{
		this(language,country);
		this.charset = charset;
		this.encoding = encoding;
	}


	/**
	 * @return the charset
	 */
	public String getCharset()
	{
		return charset;
	}

	/**
	 * @return the country
	 */
	public String getCountry()
	{
		return country;
	}

	/**
	 * @return the encoding
	 */
	public String getEncoding()
	{
		return encoding;
	}

	/**
	 * @return flagImage
	 */
	public String getFlagImage()
	{
		return flagImage;
	}

	/**
	 * @return language
	 */
	public String getLanguage()
	{
		return language;
	}

	/**
	 * @param string
	 */
	public void setCharset(String charset)
	{
		this.charset = charset;
	}

	/**
	 * @param string
	 */
	public void setCountry(String country)
	{
		this.country = country;
	}

	/**
	 * @param string
	 */
	public void setEncoding(String encoding)
	{
		this.encoding = encoding;
	}

	/**
	 * @param string
	 */
	public void setFlagImage(String flagImage)
	{
		this.flagImage = flagImage;
	}

	/**
	 * @param string
	 */
	public void setLanguage(String language)
	{
		this.language = language;
	}

	/**
	 * @return
	 */
	public String getImagesDirectoryName()
	{
		return imagesDirectoryName;
	}

	/**
	 * @param string
	 */
	public void setImagesDirectoryName(String imagesDirectoryName)
	{
		this.imagesDirectoryName = imagesDirectoryName;
	}
	
	/**
	 * @return the locale
	 */
	public Locale getLocale()
	{
		return locale;
	}
	/**
	 * @return direction
	 */
	public String getDirection()
	{
		return direction;
	}

	/**
	 * @param direction
	 */
	public void setDirection(String direction)
	{
		this.direction = direction;
	}

}
