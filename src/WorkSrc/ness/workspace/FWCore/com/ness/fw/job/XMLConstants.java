/*
 * Created on: 02/11/2004
 * Author: baruch hizkya
 */
package com.ness.fw.job;

/**
 * The constants for XML parsing of batch definition files.
 */
public class XMLConstants
{
	/*
	 * batch element definitions
	 */

	// container tag name
	public static final String CONTAINER_TAG_NAME = "containerParams";

	// param tag name
	public static final String XML_TAG_NAME = "?xml";
	public static final String XML_ATTR_VERSION = "version";
	public static final String XML_ATTR_ENCODING = "encoding";
	public static final String XML_VERSION = "1.0";
	public static final String XML_ENCODING = "UTF-8";


	// root tag name
	public static final String ROOT_TAG_NAME = "root";

	// entry tag name
	public static final String ENTRY_TAG_NAME = "entry";

	// param tag name
	public static final String PARAM_TAG_NAME = "param";
	// attributes
	public static final String PARAM_ATTR_KEY = "key";
	public static final String PARAM_ATTR_VALUE = "value";
	public static final String PARAM_ATTR_TYPE = "type";
	public static final String PARAM_ATTR_SINGLE_TYPE = "single";
	public static final String PARAM_ATTR_ARRAY_TYPE = "array";
	public static final String PARAM_ATTR_MAP_TYPE = "map";
}
