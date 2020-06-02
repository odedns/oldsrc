package com.ness.fw.ui.data;

import java.util.HashMap;

public abstract class TagData 
{
	private HashMap tagAttributes = new HashMap();
	
	/**
	 * Returns the type of the tag associated with this TagData.It may one of the<br>
	 * contants from UIConstants:
	 * TAG_TYPE_LIST
	 * TAG_TYPE_SELECTION
	 * TAG_TYPE_TEXT_AREA
	 * TAG_TYPE_TEXT_FIELD 
	 * @return
	 */
	public abstract String getTagType(); 
	
	/**
	 * Sets tag's atrribute
	 * @param attributeName the name of the tag's atrribute
	 * @param attributeValue Object,the value of the tag's atrribute
	 */
	protected void setAttribute(String attributeName,Object attributeValue)
	{
		tagAttributes.put(attributeName,attributeValue);
	}
	
	/**
	 * Sets boolean tag's atrribute
	 * @param attributeName the name of the tag's atrribute
	 * @param attributeValue boolean,the value of the tag's atrribute
	 */
	protected void setBooleanAttribute(String attributeName,boolean attributeValue)
	{
		setAttribute(attributeName,new Boolean(attributeValue));
	}
	
	/**
	 * Sets integer tag's atrribute
	 * @param attributeName the name of the tag's atrribute
	 * @param attributeValue int,the value of the tag's atrribute
	 */
	protected void setIntAttribute(String attributeName,int attributeValue)
	{
		setAttribute(attributeName,new Integer(attributeValue));
	}
	
	
	/**
	 * Returns tag's attribute
	 * @param attributeName the name of the tag's attribute
	 * @return Object,the value of the attribute
	 */
	protected Object getAttribute(String attributeName)
	{
		return tagAttributes.get(attributeName);
	}
	
	/**
	 * Returns tag's attribute
	 * @param attributeName the name of the tag's attribute
	 * @return String,the value of the attribute 
	 */
	protected String getStringAttribute(String attributeName)
	{
		return (String)tagAttributes.get(attributeName);
	}
	
	/**
	 * Returns tag's boolean attribute
	 * @param attributeName the name of the tag's attribute
	 * @return Boolean,the value of the attribute 
	 */
	protected Boolean getBooleanAttribute(String attributeName)
	{
		return (Boolean)tagAttributes.get(attributeName);
	}
	
	/**
	 * Returns tag's integer attribute
	 * @param attributeName the name of the tag's attribute
	 * @return Integer,the value of the attribute 
	 */
	protected Integer getIntegerAttribute(String attributeName)
	{
		return (Integer)tagAttributes.get(attributeName);
	}
	
}
