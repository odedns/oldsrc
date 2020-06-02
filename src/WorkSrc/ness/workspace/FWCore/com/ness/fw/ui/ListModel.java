package com.ness.fw.ui;

import java.util.*;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.ui.events.CustomEvent;
import com.ness.fw.ui.events.Event;

public class ListModel extends AbstractModel
{	
	/**
	 * constant for single model type in which only one key may be selected
	 */
	public static final String TYPE_SINGLE = UIConstants.LIST_TYPE_SINGLE;

	/**
	 * constant for multiple model type in which only few keys may be selected
	 */
	public static final String TYPE_MULTIPLE = UIConstants.LIST_TYPE_MULTIPLE;

	/**
	 * Constant used for the state attribute,which affects the css class name<br> 
	 * of the component.This state is the default state of a component.
	 */	
	public final static String LIST_ENABLED_STATE = UIConstants.COMPONENT_ENABLED_STATE;

	/**
	 * Constant used for the state attribute,which affects the css class name<br> 
	 * of the component.This state indicates that a field is disabled.
	 */	
	public final static String LIST_DISABLED_STATE = UIConstants.COMPONENT_DISABLED_STATE;	

	/**
	 * Constant used for the state attribute.This state indicates that a list is invisible.
	 */	
	public final static String LIST_HIDDEN_STATE = UIConstants.COMPONENT_HIDDEN_STATE;	

	/**
	 * Constant used for the inputType attribute,which affects the css class name<br> 
	 * of the component.This inputType indicates that a field is normal.
	 */	
	public final static String LIST_NORMAL_INPUT_TYPE = UIConstants.COMPONENT_NORMAL_INPUT_TYPE;

	/**
	 * Constant used for the inputType attribute,which affects the css class name<br> 
	 * of the component.This inputType indicates that a field is mandatory.
	 */	
	public final static String LIST_MANDATORY_INPUT_TYPE = UIConstants.COMPONENT_MANDATORY_INPUT_TYPE;
	
	private final static String LIST_BOOLEAN_KEY_TRUE = "listTrue";
	private final static String LIST_BOOLEAN_KEY_FALSE = "listFalse";
	private final static String LIST_BOOLEAN_KEY = "booleanKey";

	protected static final String LIST_MODEL_SELECTED_KEYS_PROPERTY = "keys";
	protected static final String LIST_MODEL_SELECTED_KEY_PROPERTY = "key";
	protected static final String LIST_MODEL_SEARCH_STRING_PROPERTY = "str";
	protected static final String LIST_MODEL_EDITABLE_VALUE_PROPERTY = "editableValue";
	protected static final String LIST_MODEL_REORDERED_KEYS_PROPERTY = "reorderedKeys";
	
	protected static final String LIST_MODEL_CLICK_EVENT_TYPE = "click";
	protected static final String LIST_MODEL_CHANGE_EVENT_TYPE = "change";
	protected static final String LIST_MODEL_SEARCH_EVENT_TYPE = "search";
					
	private HashMap options;
	private ArrayList orderedKeys;
	protected String inputType;
	protected String selectionType = UIConstants.LIST_TYPE_SINGLE; 
	protected String srcListTitle;
	protected String trgListTitle;
	
	protected CustomEvent listOnChangeEvent;
	protected CustomEvent listClickEvent;
	protected Event listEvent;
	
	/**
	 * constructor of ListModel
	 */
	public ListModel()
	{
		options = new HashMap();
		orderedKeys = new ArrayList();
		listOnChangeEvent = new CustomEvent();
		listClickEvent = new CustomEvent();		
		listEvent = new Event();
	}
		
	/**
	 * Handles list model events
	 */	
	protected void handleEvent(boolean checkAuthorization) throws AuthorizationException, UIException
	{
		String eventType = getEventTypeProperty();
		if (eventType != null)
		{
			if (eventType.equals(LIST_MODEL_CHANGE_EVENT_TYPE))
			{
				if (checkEventLegal(listOnChangeEvent,checkAuthorization))
				{
					handleKeySelectionEvent();	
				}
			}
			else if (eventType.equals(LIST_MODEL_CLICK_EVENT_TYPE))
			{
				if (checkEventLegal(listClickEvent,checkAuthorization))
				{
					handleKeySelectionEvent();	
				}
			}
			else if (eventType.equals(LIST_MODEL_SEARCH_EVENT_TYPE))
			{
				if (checkEventLegal(listEvent,checkAuthorization))
				{
					setSearchString(getSearchString());
				}	
			}
			else
			{
				if (checkEventLegal(listEvent,checkAuthorization))
				{
					handleKeySelectionEvent();
				}			
			}
		}
		else
		{
			if (checkEventLegal(listEvent,checkAuthorization))
			{
				handleKeySelectionEvent();
			}
		}
	}	
	
	/**
	 * Handles selected keys event.
	 * If the action is authorized the list of the selected keys is set if
	 * the type of the list model is multiple or one selected key is set if
	 * the type of the list model is single.
	 * If the action is unauthorized the last list the selected keys is set if
	 * the type of the list model is multiple or the last selected key if
	 * the type of the list model is single.
	 */
	protected void handleKeySelectionEvent() throws UIException
	{
		if (getProperty(LIST_MODEL_REORDERED_KEYS_PROPERTY) != null)
		{
			orderedKeys = getListProperty(LIST_MODEL_REORDERED_KEYS_PROPERTY);
		}
		if (isMultipleSelectionType())
		{
			setSelectedKeys(getSelectedKeys());
		}
		else
		{
			setSelectedKey(getSelectedKey());
		}		
	}
	
	/**
	 * Returns list of last selected keys in the model.It is used 
	 * only in case of an unauthorized action whicj tries to
	 * set new selected keys. 
	 * @return list of last selected keys
	 */
	protected ArrayList getLastSelectedKeys()
	{
		ArrayList keys = new ArrayList();
		for (int index = 0;index < orderedKeys.size();index++)
		{
			String key = (String)orderedKeys.get(index); 
			if (((Option)options.get(key)).selected)
			{
				keys.add(key);
			}
		}
		return keys;
	}
	
	/**
	 * 
	 * @return last key selected
	 */
	protected String getLastSelectedKey()
	{
		for (int index = 0;index < orderedKeys.size();index++)
		{
			String key = (String)orderedKeys.get(index); 
			if (((Option)options.get(key)).selected)
			{
				return key;
			}
		}
		return null;
	}
	
	/**
	 * Marks keys as selected in multiple selected keys model and adds keys to the 
	 * list of selected keys
	 * @param keys the list of keys to select
	 */
	public void setSelectedKeys(ArrayList selectedKeys)
	{
		HashMap selectedKeysMap = new HashMap();
		if (isMultipleSelectionType() && selectedKeys != null )
		{
			for (int index = 0;index < selectedKeys.size();index++)
			{
				if (options.containsKey(selectedKeys.get(index)))
				{
					selectedKeysMap.put(selectedKeys.get(index),"");
				}
				else
				{
					selectedKeys.remove(selectedKeys.get(index));				
				}
			}
			selectKeys(selectedKeysMap);
			setProperty(LIST_MODEL_SELECTED_KEYS_PROPERTY,selectedKeys);
		}
	}
	
	/**
	 * Adds key to selected keys list
	 * @param key the key to add
	 */ 
	protected void addKeyToSelected(String key) //throws UIException
	{
		if (isMultipleSelectionType())
		{
			ArrayList selectedKeys = getSelectedKeys();
			if (selectedKeys == null)
			{
				selectedKeys = new ArrayList();
			}	
			selectedKeys.add(key);
			setProperty(LIST_MODEL_SELECTED_KEYS_PROPERTY,selectedKeys);
		}
	}
	
	/**
	 * Removes key from selected keys list
	 * @param key the key to remove
	 */ 	
	protected void removeKeyFromSelected(String key) throws UIException
	{
		if (isMultipleSelectionType())
		{
			ArrayList selectedKeys = getSelectedKeys();
			if (selectedKeys != null)
			{
				selectedKeys.remove(key);
				setProperty(LIST_MODEL_SELECTED_KEYS_PROPERTY,selectedKeys);
			}		
		}
		else
		{
			removeProperty(LIST_MODEL_SELECTED_KEY_PROPERTY);
		}
	}
		
	/**
	 * Marks key as selected in single selected keys model and sets the key as
	 * the selected key of the model.Also used for marking a key to selected in 
	 * multiple type model.
	 * @param selectedKey the key to select,if null or empty string, the property is removed
	 * and no key is marked in this model.
	 * @return true if the key was set succesfully,return false if key does not exist
	 * in the list.
	 */	
	public boolean setSelectedKey(String selectedKey) //throws UIException
	{
		if (selectedKey == null || selectedKey.equals(""))
		{
			unSelectAllKeys();
			return true;
		}
		else
		{
			if (options.containsKey(selectedKey))
			{
				if (isMultipleSelectionType())
				{
					addKeyToSelected(selectedKey);
					selectKey(selectedKey);
				}
				else
				{
					HashMap selectedKeys = new HashMap();
					selectedKeys.put(selectedKey,"");
					setProperty(LIST_MODEL_SELECTED_KEY_PROPERTY,selectedKey);
					selectKeys(selectedKeys);		
				}
				return true;
			}
			return false;
		}
	}
	
	/**
	 * Marks key as selected in single selected keys model and sets the key as
	 * the selected key of the model.Also used for marking a key to selected in 
	 * multiple type model.
	 * @param selectedKey the key to select of type Integer,if null the property is removed
	 * and no key is marked in this model.
	 * @return true if the key was set succesfully,return false if key does not exist
	 * in the list.
	 */	
	public boolean setSelectedKey(Integer selectedKey) throws UIException
	{
		if (selectedKey == null)
		{
			unSelectAllKeys();
			return true; 
		}
		else
		{
			return setSelectedKey(String.valueOf(selectedKey));
		}
	}
	
	/**
	 * Marks key as selected in single selected keys model and sets the key as
	 * the selected key of the model.Also used for marking a key to selected in 
	 * multiple type model.
	 * @param selectedKey the key to select of type int.
	 * @return true if the key was set succesfully,return false if key does not exist
	 * in the list.
	 */	
	public boolean setSelectedKey(int selectedKey)throws UIException 
	{
		return setSelectedKey(String.valueOf(selectedKey));
	}
	
	/**
	 * Marks key as selected in single selected keys model and sets the key as<br>
	 * the selected key of the model.The key is retrieved by the order it was added to the list.<br>
	 * Also used for marking a key as selected in multiple type model.
	 * @param key the key to select of type int.
	 * @return true if the key was set succesfully,return false if key does not exist
	 * in the list.
	 */	
	public boolean setSelectedKeyByKeyOrder(int keyOrder)throws UIException 
	{
		if (keyOrder < 0 || keyOrder > orderedKeys.size() - 1)
		{
			throw new UIException("The index of the key in the list " + keyOrder + " is not legal");
		}
		return setSelectedKey((String)orderedKeys.get(keyOrder));
	}
	
	
	/**
	 * Sets search string,relvant only in type singleExpanded of the ListTag
	 * @param searchString
	 */
	public void setSearchString(String searchString)
	{
		setProperty(LIST_MODEL_SEARCH_STRING_PROPERTY,searchString);
	}
	
	/**
	 * Returns list of selected keys in multiple selected keys model
	 * @return list of selected keys
	 */
	public ArrayList getSelectedKeys()
	{
		if (selectionType.equals(TYPE_SINGLE))
		{
			//throw new UIException("cannot use getSelectedKeys when type of the model is single");
		}
		if (getProperty(LIST_MODEL_SELECTED_KEYS_PROPERTY) != null)
		{
			return (ArrayList)((ArrayList)getProperty(LIST_MODEL_SELECTED_KEYS_PROPERTY)).clone();
		}
		else
		{
			return new ArrayList();
		}
	}
	
	/**
	 * Returns selected key in single selected key model
	 * @return the selected key of this model
	 */
	public String getSelectedKey()
	{
		if (selectionType.equals(TYPE_MULTIPLE))
		{
			//throw new UIException("cannot use getSelectedKey when type of the model is multiple");
		}
		return (String)getProperty(LIST_MODEL_SELECTED_KEY_PROPERTY);
	}
	
	/**
	 * Returns search string,relevant only in multipleExpanded type of the ListTag
	 * @return
	 */
	public String getSearchString()
	{
		return (String)modelProperties.get(LIST_MODEL_SEARCH_STRING_PROPERTY);
	}
	
	/**
	 * Marks boolean key as selected or unselected.
	 * @param selectBooleanValue if true the boolean key is marked as selected 
	 * @throws UIException no boolean values exists in the model.
	 */
	public void markBooleanValue(boolean selectBooleanValue) throws UIException
	{
		markBooleanValue(LIST_BOOLEAN_KEY,selectBooleanValue);
	}
	
	/**
	 * Indicates if the boolean value of this model is selected.
	 * @return true if the boolean value of this model is selected,false if it is not<br>
	 * selected or if a boolean value does not exist in this model.
	 */
	public boolean isBooleanChecked()
	{
		return isValueSelected(LIST_BOOLEAN_KEY);
	}
	
	/**
	 * Marks boolean key as selected or unselected.
	 * @param key the boolean key to select or unselect
	 * @param selectBooleanValue if true the boolean key is marked as selected
	 * @throws UIException if the key does not exist in the model
	 */
	public void markBooleanValue(String key,boolean selectBooleanValue) throws UIException
	{
		ArrayList selectedKeys = new ArrayList();
		Option option = (Option)options.get(key);
		if (option != null)
		{
			if (selectBooleanValue)
			{
				selectedKeys.add(key);
			}
			setSelectedKeys(selectedKeys);
		}	
		else
		{
			throw new UIException("the boolean value with key " + key + "does not exist in the model");	
		}
	}
	
	/**
	 * Adds boolean value to the list.Using this method means that the list holds<br>
	 * only one boolean value in it,so all the other values in the list are removed<br>
	 * The key for the value added is generated automatically by the ListModel.<br>
	 * Usually ,using this method will be follwed by using the method {@link hasSelectedKeys}
	 * @param isSelected boolean value - if true the key of the model is selected.
	 * @param value the value of this key
	 */
	public void addBooleanValue(String value,boolean isSelected)
	{
		addBooleanValue(LIST_BOOLEAN_KEY,value,isSelected);
	}

	/**
	 * Adds boolean value to the list.Using this method means that the list holds<br>
	 * only one boolean value in it,so all the other values in the list are removed.<br>
	 * Usually ,using this method will be follwed by using the method {@link hasSelectedKeys}
	 * @param key the key of the boolean value	
	 * @param isSelected boolean value - if true the key of the model is selected.
	 * @param value the value of this key
	 */
	public void addBooleanValue(String key,String value,boolean isSelected)
	{
		selectionType = TYPE_MULTIPLE;
		removeAllValues();
		addValue(key,value,isSelected);
	}
	
	/**
	 * Adds new value to the list.The new value is not marked as selected
	 * @param key the key of the value
	 * @param value the value
	 */
	public void addValue(String key,String value) //throws UIException
	{
		addValue(key,value,false);
	}
	
	/**
	 * Adds new value to the list
	 * @param key the key of the value
	 * @param value the value
	 * @param isSelected if true the new value is marked as selected
	 */
	public void addValue(String key,String value,boolean isSelected) //throws UIException
	{
		Object prevKey = options.put(key,new Option(value,isSelected));
		if (prevKey == null)
		{
			orderedKeys.add(key);
		}
		if (isSelected)
		{
			addKeyToSelected(key);
			if (!isMultipleSelectionType())
			{
				setSelectedKey(key);
			}
		}
	}
	
	/**
	 * Removes value from the list by its key
	 * @param key the key of the value to remove
	 */
	public void removeValue(String key) throws UIException
	{
		Object value = options.remove(key);
		if (value != null)
		{
			orderedKeys.remove(key);
			Option option = (Option)value;
			if (option.selected)
			{
				removeKeyFromSelected(key);
			}
		}
	}
	
	/**
	 * Empties all the keys and values of this model.
	 * Removes the selectedKey or selectedKeys properties,according to the model's type.
	 */
	public void removeAllValues()
	{
		options.clear();
		orderedKeys.clear();
		if (isMultipleSelectionType())
		{
			modelProperties.remove(LIST_MODEL_SELECTED_KEYS_PROPERTY);
		}
		else
		{
			modelProperties.remove(LIST_MODEL_SELECTED_KEY_PROPERTY);
		}
	}
	
	/**
	 * Returns the value by its key or null if key is null or found.
	 * @param key the key 
	 * @return the value or null if key is null or not found
	 */
	public String getValue(String key)
	{
		if (key == null || options.get(key) == null)
		{
			return null;
		}
		else
		{
			return ((Option)options.get(key)).value;
		}
	}
	
	/**
	 * Check if value is marked as selected
	 * @param key the key of the value
	 * @return true of the value is marked as selected
	 */
	public boolean isValueSelected(String key)
	{
		if (options.get(key) == null)
		{
			return false;
		}
		else
		{
			return ((Option)options.get(key)).selected;
		}
	}

	/**
	 * Checks if the model is empty.
	 * @return ture if there are no keys in this model.
	 */
	public boolean isEmpty()
	{
		return options.isEmpty();
	}
	
	/**
	 * Checks if there are any selected values in this list,no matter if the
	 * list's type is single or multiple
	 * @return true if at least one value of the list is selected
	 */
	public boolean hasSelectedKeys()
	{
		if (selectionType.equals(TYPE_SINGLE))
		{
			return getProperty(LIST_MODEL_SELECTED_KEY_PROPERTY) != null;
		}
		else
		{
			ArrayList selectedKeys = (ArrayList)getProperty(LIST_MODEL_SELECTED_KEYS_PROPERTY);
			return selectedKeys != null && selectedKeys.size() != 0;
		}
	}
	
	/**
	 * Returns the order of the currently selected key in the model.
	 * @return the order of the currently selected key in the model or <br>
	 * -1 if no key is selected
	 */
	public int getSelectedKeyOrder()
	{
		if (getProperty(LIST_MODEL_SELECTED_KEY_PROPERTY) != null)
		{
			return (orderedKeys.indexOf(getStringProperty(LIST_MODEL_SELECTED_KEY_PROPERTY)));
		}
		else
		{
			return -1;
		}
	}
	
	/**
	 * Returns all the keys of this list model,in the order thay were added.
	 * @return ArrayList of keys
	 */
	public ArrayList getKeys()
	{
		return (ArrayList)orderedKeys.clone();
	}
	
	/**
	 * Marks all keys in the list as unselected,and removes selectedKeys property from 
	 * model with type multiple or remove selectedKey property from 
	 * model with type single.
	 */
	public void unSelectAllKeys()
	{
		Iterator keysIter = options.keySet().iterator();
		while (keysIter.hasNext())
		{
			unSelectKey((String)keysIter.next());
		}		
		if (isMultipleSelectionType())
		{
			modelProperties.remove(LIST_MODEL_SELECTED_KEYS_PROPERTY);
		}
		else
		{
			modelProperties.remove(LIST_MODEL_SELECTED_KEY_PROPERTY);
		}		
	}
	
	/**
	 * Marks all keys in the list as selected,relevant only in type multiple.
	 */
	public void selectAllKeys()
	{
		if (isMultipleSelectionType())
		{
			ArrayList selectedKeys = new ArrayList();
			Iterator keysIter = options.keySet().iterator();
			while (keysIter.hasNext())
			{
				String key = (String)keysIter.next();
				selectedKeys.add(key);
				selectKey(key);
			}		
			modelProperties.put(LIST_MODEL_SELECTED_KEYS_PROPERTY,selectedKeys);
		}
	}
	
	/**
	 * Marks keys as selected
	 * @param selectedKeys map of selected keys
	 */
	protected void selectKeys(HashMap selectedKeys)
	{
		Iterator keysIter = options.keySet().iterator();
		while (keysIter.hasNext())
		{
			String key = (String)keysIter.next();
			if (selectedKeys.get(key) != null)
			{
				selectKey(key);
			}
			else
			{
				unSelectKey(key);
			}
		}
	}
	
	/**
	 * Marks one key in the model as selected
	 * @param key the key to mark as selected
	 */
	protected void selectKey(String key)
	{
		((Option)options.get(key)).selected = true;
	}
	
	/**
	 * Marks one key in the model as unselected
	 * @param key the key to mark as unselected
	 */	
	protected void unSelectKey(String key)
	{
		((Option)options.get(key)).selected = false;
	}
		
	/**
	 * Returns true if few values may be selected in the model
	 * @return true if few values may be selected in the model
	 */	
	public boolean isMultipleSelectionType()
	{
		return selectionType.equals(TYPE_MULTIPLE);
	}
	
	/**
	 * Returns true if one value may be selected in the model
	 * @return true if one value may be selected in the model
	 */	
	public boolean isSingleSelectionType()
	{
		return selectionType.equals(TYPE_SINGLE);
	}
		
	/**
	 * Returns the inputType of this TextModel
	 * @return
	 */
	public String getInputType()
	{
		return inputType;
	}
	
	/**
	 * Sets the inputType of this TextModel
	 * @param inputType
	 */	
	public void setInputType(String inputType)
	{
		this.inputType = inputType;
	}
	
	/**
	 * Returns the type of selection in this ListModel which may be one of 2 constants in {@link UIConstants}<br>
	 * LIST_TYPE_MULTIPLE - few values mey be selected in this model
	 * LIST_TYPE_SINGLE - one value may be selected in this model
	 * @return the type of selection in this ListModel
	 */
	public String getSelectionType()
	{
		return selectionType;
	}

	/**
	 * Sets the type of selection in this ListModel which may be one of 2 constants in {@link UIConstants}<br>
	 * LIST_TYPE_MULTIPLE - few values mey be selected in this model
	 * LIST_TYPE_SINGLE - one value may be selected in this model
	 * @param selectionType the type of selection in this ListModel
	 */
	public void setSelectionType(String selectionType)
	{
		this.selectionType = selectionType;
	}

	/**
	 * Returns the title for the list of values in the model
	 * @return the title for the list of values in the model
	 */
	public String getSrcListTitle()
	{
		return srcListTitle;
	}

	/**
	 * Returns the title for the list of selected values in the model
	 * @return the title for the list of selected values in the model
	 */
	public String getTrgListTitle()
	{
		return trgListTitle;
	}

	/**
	 * Sets the title for the list of values in the model
	 * @param srcListTitle the title for the list of values in the model
	 */
	public void setSrcListTitle(String srcListTitle)
	{
		this.srcListTitle = srcListTitle;
	}

	/**
	 * Sets the title for the list of selected values in the model
	 * @param trgListTitle the title for the list of selected values in the model
	 */
	public void setTrgListTitle(String trgListTitle)
	{
		this.trgListTitle = trgListTitle;
	}

	/**
	 * Returns the {@link com.ness.fw.ui.events.CustomEvent} object which holds theinformation about 
	 * change event on a list(when an item in the list is selected). 
	 * @return {@link com.ness.fw.ui.events.CustomEvent} object
	 */
	public CustomEvent getListOnChangeEvent() 
	{
		return listOnChangeEvent;
	}

	/**
	 * Sets the {@link com.ness.fw.ui.events.CustomEvent} object which holds the information about 
	 * change event on a list(when an item in the list is selected). 
	 * @param event the {@link com.ness.fw.ui.events.CustomEvent} object
	 */
	public void setListOnChangeEvent(CustomEvent event) 
	{
		listOnChangeEvent = event;
	}

	/**
	 * Returns the {@link com.ness.fw.ui.events.CustomEvent} object which holds the information about 
	 * click event on a list(when an item in the list is clicked). 
	 * @return {@link com.ness.fw.ui.events.CustomEvent} object
	 */
	public CustomEvent getListClickEvent() 
	{
		return listClickEvent;
	}

	/**
	 * Sets the {@link com.ness.fw.ui.events.CustomEvent} object which holds the information about 
	 * click event on a list(when an item in the list is clicked). 
	 * @param event the {@link com.ness.fw.ui.events.CustomEvent} object
	 */
	public void setListClickEvent(CustomEvent event) 
	{
		listClickEvent = event;
	}

	/**
	 * Returns the {@link com.ness.fw.ui.events.Event} object which holds the information about 
	 * general events on a lists. 
	 * @return {@link com.ness.fw.ui.events.Event} object
	 */
	public Event getListEvent() 
	{
		return listEvent;
	}

	/**
	 * Sets the {@link com.ness.fw.ui.events.Event} object which holds the information about 
	 * general events on a lists. 
	 * @param event the {@link com.ness.fw.ui.events.Event} object
	 */
	public void setListEvent(Event event) 
	{
		listEvent = event;
	}

	/**
	 * Returns a value that may be added to the list model.This value does not<br>
	 * have a key in the model.
	 * @return the value of the list model,or null if no value was set.
	 */
	public String getEditableValue() 
	{
		return (String)getStringProperty(LIST_MODEL_EDITABLE_VALUE_PROPERTY);
	}

	/**
	 * Sets a value to the list model.This value does not have a key in the model.
	 * @param value the value of the list model
	 */
	public void setEditableValue(String editableValue) 
	{
		setProperty(LIST_MODEL_EDITABLE_VALUE_PROPERTY,editableValue);
		if (getSelectedKey() != null)
		{
			unSelectKey(getSelectedKey());
			removeProperty(LIST_MODEL_SELECTED_KEY_PROPERTY);
		}
	}

	class Option
	{
		private String value;
		private boolean selected;
		Option(String value,boolean isSelected)
		{
			this.value = value;
			this.selected = isSelected;
		}
	}
}
