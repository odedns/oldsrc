package com.ness.fw.ui.taglib;

import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.ui.UIConstants;
import com.ness.fw.ui.data.TagData;

public class UITagFactory 
{
	private final static String TAG_TYPE_LIST = UIConstants.TAG_TYPE_LIST;
	
	private final static String TAG_TYPE_LABEL = UIConstants.TAG_TYPE_LABEL;

	private final static String TAG_TYPE_TEXT_FIELD = UIConstants.TAG_TYPE_TEXT_FIELD;
	
	private final static String TAG_TYPE_TEXT_AREA = UIConstants.TAG_TYPE_TEXT_AREA;
	
	private final static String TAG_TYPE_SELECTION = UIConstants.TAG_TYPE_SELECTION;

	public static UITag createUITag(String id,TagData tagData) throws UIException
	{
		UITag uiTag;
		if (tagData.getTagType().equals(TAG_TYPE_LIST))
		{
			uiTag = new FWListTag();
			uiTag.setId(id);
			uiTag.setTagData(tagData);
		}
		else if (tagData.getTagType().equals(TAG_TYPE_LABEL))
		{
			uiTag = new FWLabelTag();
			uiTag.setId(id);
			uiTag.setTagData(tagData);
		}
		else if (tagData.getTagType().equals(TAG_TYPE_TEXT_FIELD))
		{
			uiTag = new TextFieldTag();
			uiTag.setId(id);
			uiTag.setTagData(tagData);
		}
		else if (tagData.getTagType().equals(TAG_TYPE_TEXT_AREA))
		{
			uiTag = new TextAreaTag();
			uiTag.setId(id);
			uiTag.setTagData(tagData);
		}	
		else  if (tagData.getTagType().equals(TAG_TYPE_SELECTION))
		{
			uiTag = new FWSelectionTag();
			uiTag.setId(id);
			uiTag.setTagData(tagData);
		}
		else
		{
			throw new UIException("cannot create tag of type " + tagData.getTagType());
		}
		return uiTag;
	}
}
