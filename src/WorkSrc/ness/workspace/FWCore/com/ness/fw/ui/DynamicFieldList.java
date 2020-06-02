package com.ness.fw.ui;

import java.util.ArrayList;
import java.util.HashMap;

import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.ui.data.LabelTagData;
import com.ness.fw.ui.data.TagData;

public class DynamicFieldList 
{
	private HashMap dynamicFields;
	private ArrayList orderedDynamicFieldIds;
	private int labelCounter = 0;
	
	public DynamicFieldList()
	{
		dynamicFields = new HashMap();
		orderedDynamicFieldIds = new ArrayList();
	}
	
	public void addDynamicLabel(LabelTagData labelTagData) throws UIException
	{
		if (labelTagData.getValue() == null)
		{
			throw new UIException("When adding a label which does not have a field in the context, to the dynamic field iterator,it must have a value ");		
		}
		addDynamicField(String.valueOf(labelCounter++),null,labelTagData);
	}
	
	public void addDynamicField(String id,TagData tagData)
	{
		addDynamicField(id,"",tagData);
	}
	
	public void addDynamicField(String id,String label,TagData tagData)
	{
		addDynamicField(id,label,null,tagData);
	}

	public void addDynamicField(String id,String label,String layoutId,TagData tagData)
	{
		if (!dynamicFields.containsKey(id))
		{
			dynamicFields.put(id,new DynamicField(id,label,layoutId,tagData));
			orderedDynamicFieldIds.add(id);
		}
	}	
		
	public void removeDynamicField(String id)
	{
		Object removedId = dynamicFields.remove(id);
		if (removedId != null)
		{
			orderedDynamicFieldIds.remove(id);
		}
	}
	
	public void removeAllFields()
	{
		dynamicFields.clear();
		orderedDynamicFieldIds.clear();
	}
	
	public int getFieldCount()
	{
		return orderedDynamicFieldIds.size();
	}
	
	public String getFieldLabel(String id) throws UIException
	{
		return getDynamicField(id).label;
	}
		
	public TagData getFieldTagData(String id) throws UIException
	{
		return getDynamicField(id).tagData;
	}
		
	public String getFieldLabel(int fieldIndex) throws UIException
	{
		return getFieldLabel(getFieldId(fieldIndex));
	}
	
	public TagData getFieldTagData(int fieldIndex) throws UIException
	{
		return getFieldTagData(getFieldId(fieldIndex));
	}	
		
	public void setFieldLabel(int fieldIndex,String label) throws UIException
	{
		setFieldLabel(getFieldId(fieldIndex),label);
	}

	public void setFieldLabel(String id,String label) throws UIException
	{
		DynamicField dynamicField = getDynamicField(id);
		dynamicField.label = label;	
	}

	public void setFieldTagData(int fieldIndex,TagData tagData) throws UIException
	{
		setFieldTagData(getFieldId(fieldIndex),tagData);
	}
	
	public void setFieldTagData(String id,TagData tagData) throws UIException
	{
		DynamicField dynamicField = getDynamicField(id)	;
		dynamicField.tagData = tagData;	
	}
	
	public String getFieldId(int fieldIndex) throws UIException
	{
		String id = (String)orderedDynamicFieldIds.get(fieldIndex);
		if (id != null)
		{
			return id;
		}
		else
		{
			throw new UIException("fieldIndex " + fieldIndex + " does not exist in the list");
		}
	}
	
	private DynamicField getDynamicField(String id) throws UIException
	{
		DynamicField dynamicField = (DynamicField)dynamicFields.get(id);
		if (dynamicField != null)
		{
			return dynamicField;
		}
		else
		{
			throw new UIException("field with id " + id + " does not exist in the list");
		}
	}
	class DynamicField
	{
		private String id;
		private String label;
		private TagData tagData;
		private String layoutId;
		
		private DynamicField(String id,String label,String layoutId,TagData tagData)
		{
			this.id = id;
			this.label = label;
			this.tagData = tagData;
			this.layoutId = layoutId;
		}
	}
}
