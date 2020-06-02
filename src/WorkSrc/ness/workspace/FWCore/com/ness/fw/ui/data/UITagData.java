package com.ness.fw.ui.data;

public abstract class UITagData extends TagData 
{
	private final static String ATTRIBUTE_STATE = "state";
	private final static String ATTRIBUTE_DIRTABLE = "dirtable";
	private final static String ATTRIBUTE_AUTHID = "authId";
	private final static String ATTRIBUTE_CSS_PRE = "cssPre";
	
	public void setState(String state)
	{
		setAttribute(ATTRIBUTE_STATE,state);
	}
	
	public String getState()
	{
		return getStringAttribute(ATTRIBUTE_STATE);
	}
		
	public void setDirtable(boolean dirtable)
	{
		setBooleanAttribute(ATTRIBUTE_DIRTABLE,dirtable);
	}
	
	public Boolean isDirtable()
	{
		return getBooleanAttribute(ATTRIBUTE_DIRTABLE);
	}
	
	public void setAuthId(String authId)
	{
		setAttribute(ATTRIBUTE_AUTHID,authId);
	}
	
	public String getAuthId()
	{
		return getStringAttribute(ATTRIBUTE_AUTHID);
	}
	
	public void setCssPre(String cssPre)
	{
		setAttribute(ATTRIBUTE_CSS_PRE,cssPre);
	}
	
	public String getCssPre()
	{
		return getStringAttribute(ATTRIBUTE_CSS_PRE);
	}
}
