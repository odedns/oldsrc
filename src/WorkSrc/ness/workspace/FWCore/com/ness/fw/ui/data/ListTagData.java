package com.ness.fw.ui.data;

import com.ness.fw.ui.UIConstants;

public class ListTagData extends InputTagData 
{
	public void setType(String type)
	{
		setAttribute("type",type);
	}
	
	public String getType()
	{
		return getStringAttribute("type");
	}
	
	public void setSize(int size)
	{
		setIntAttribute("size",size);
	}
	
	public Integer getSize()
	{
		return getIntegerAttribute("size");
	}
	
	public void setSearchType(int searchType)
	{
		setIntAttribute("searchType",searchType);
	}
	
	public Integer getSearchType()
	{
		return getIntegerAttribute("searchType");
	}
	
	public void setRefreshType(int refreshType)
	{
		setIntAttribute("refreshType",refreshType);
	}
	
	public Integer getRefreshType()
	{
		return getIntegerAttribute("refreshType");
	}	
	
	public void setControlButtons(boolean controlButtons)
	{
		setBooleanAttribute("controlButtons",controlButtons);
	}
	
	public Boolean isControlButtons()
	{
		return getBooleanAttribute("controlButtons");
	}
	
	public void setChangeEventName(String changeEventName)
	{
		setAttribute("changeEventName",changeEventName);
	}

	public String getChangeEventName()
	{
		return getStringAttribute("changeEventName");
	}
	
	public void setExpandEventName(String expandEventName)
	{
		setAttribute("expandEventName",expandEventName);
	}

	public String getExpandEventName()
	{
		return getStringAttribute("expandEventName");
	}
	
	public void setMandatoryPrompt(String mandatoryPrompt)
	{
		setAttribute("mandatoryPrompt",mandatoryPrompt);
	}
	
	public String getMandatoryPrompt()
	{
		return getStringAttribute("mandatoryPrompt");
	}
	
	public void setAddMandatoryPrompt(boolean addMandatoryPrompt)
	{
		setBooleanAttribute("addMandatoryPrompt",addMandatoryPrompt);
	}
	
	public Boolean isAddMandatoryPrompt()
	{
		return getBooleanAttribute("addMandatoryPrompt");
	}
	
	public void setOptionalPrompt(String optionalPrompt)
	{
		setAttribute("optionalPrompt",optionalPrompt);
	}
	
	public String getOptionalPrompt()
	{
		return getStringAttribute("optionalPrompt");
	}
	
	public void setAddOptionalPrompt(boolean addOptionalPrompt)
	{
		setBooleanAttribute("addOptionalPrompt",addOptionalPrompt);
	}
	
	public Boolean isAddOptionalPrompt()
	{
		return getBooleanAttribute("addOptionalPrompt");
	}	

	public void setOnchange(String onchange)
	{
		setAttribute("onchange",onchange);
	}
	
	public String getOnchange()
	{
		return getStringAttribute("onchange");
	}	
	
	public void setSrcListTitle(String srcListTitle)
	{
		setAttribute("srcListTitle",srcListTitle);
	}
	
	public String getSrcListTitle()
	{
		return getStringAttribute("srcListTitle");
	}
	
	public void setSrcListTitleClassName(String srcListTitleClassName)
	{
		setAttribute("srcListTitleClassName",srcListTitleClassName);
	}
	
	public String getSrcListTitleClassName()
	{
		return getStringAttribute("srcListTitleClassName");
	}	
	
	public void setTrgListTitle(String trgListTitle)
	{
		setAttribute("trgListTitle",trgListTitle);
	}
	
	public String getTrgListTitle()
	{
		return getStringAttribute("trgListTitle");
	}
	
	public void setTrgListTitleClassName(String trgListTitleClassName)
	{
		setAttribute("trgListTitleClassName",trgListTitleClassName);
	}
	
	public String getTrgListTitleClassName()
	{
		return getStringAttribute("trgListTitleClassName");
	}
	
	public void setExpanderDialogParams(String expanderDialogParams)
	{
		setAttribute("expanderDialogParams",expanderDialogParams);	
	}
	
	public String getExpanderDialogParams()
	{
		return getStringAttribute("expanderDialogParams");	
	}	

	public void setExpanderImage(String expanderImage)
	{
		setAttribute("expanderImage",expanderImage);	
	}
	
	public String getExpanderImage()
	{
		return getStringAttribute("expanderImage");	
	}	

	public void setExpanderTitle(String expanderTitle)
	{
		setAttribute("expanderTitle",expanderTitle);	
	}
	
	public String getExpanderTitle()
	{
		return getStringAttribute("expanderTitle");	
	}
	
	public void setExpanderButtonTitle(String expanderButtonTitle)
	{
		setAttribute("expanderButtonTitle",expanderButtonTitle);	
	}
	
	public String getExpanderButtonTitle()
	{
		return getStringAttribute("expanderButtonTitle");	
	}	
	
	public void setOpenOnKeyPress(boolean openOnKeyPress)
	{
		setBooleanAttribute("openOnKeyPress",openOnKeyPress);	
	}
	
	public Boolean isOpenOnKeyPress()
	{
		return getBooleanAttribute("openOnKeyPress");
	}
	
	public void setEditable(boolean editable)
	{
		setBooleanAttribute("editable",editable);
	}
	
	public Boolean isEditable()
	{
		return getBooleanAttribute("editable");
	}

	public String getTagType() 
	{
		return UIConstants.TAG_TYPE_LIST;
	}	
}
