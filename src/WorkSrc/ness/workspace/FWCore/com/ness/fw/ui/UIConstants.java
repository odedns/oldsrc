package com.ness.fw.ui;

import com.ness.fw.shared.common.SystemConstants;

public class UIConstants
{
	/**
	 * Used in {@link com.ness.fw.ui.taglib.flower.FWTabTag} for the
	 * orientation attribute.When the orientation attribute is set to 
	 * this value,horizontal tab will be rendered by the tag.
	 */
	public final static String TAB_ORIENTATION_HORIZONTAL = "horizontal";

	/**
	 * Used in {@link com.ness.fw.ui.taglib.flower.FWTabTag} for the
	 * orientation attribute.When the orientation attribute is set to 
	 * this value,vertical tab will be rendered by the tag.
	 */
	public final static String TAB_ORIENTATION_VERTICAL = "vertical";

	/**
	 * Used in {@link com.ness.fw.ui.taglib.flower.FWTabTag} for the<br>
	 * dirtyAction attribute.When the dirtyAction attribute is set to<br>
	 * this value,the event sent when clicking on a tab does not check<br>
	 * the dirty flag.
	 */
	public final static int TAB_DIRTY_ACTION_NONE = 0;

	/**
	 * Used in {@link com.ness.fw.ui.taglib.flower.FWTabTag} for the<br>
	 * dirtyAction attribute.When the dirtyAction attribute is set to<br>
	 * this value,the event sent when clicking on a tab does not check<br>
	 * the dirty flag.
	 */
	public final static int TAB_DIRTY_ACTION_IGNORE = 1;

	/**
	 * Used in {@link com.ness.fw.ui.taglib.flower.FWTabTag} for the<br>
	 * dirtyAction attribute.When the dirtyAction attribute is set to<br>
	 * this value,the event sent when clicking on a tab checks for<br>
	 * dirty flag and show a warning if it exists.
	 */
	public final static int TAB_DIRTY_ACTION_WARNING = 2;

	/**
	 * Used in {@link com.ness.fw.ui.taglib.flower.FWTabTag} for the<br>
	 * dirtyAction attribute.When the dirtyAction attribute is set to<br>
	 * this value,the event sent when clicking on a tab checks for<br>
	 * dirty flag and show an error if it exists.
	 */
	public final static int TAB_DIRTY_ACTION_ERROR = 3;

	/**
	 * Used in {@link com.ness.fw.ui.taglib.flower.FWTabTag} for the type
	 * attribute.Represents tab of "tabs" type.
	 */
	public final static String TAB_TYPE_TABS = "tabs";

	/**
	 * Used in {@link com.ness.fw.ui.taglib.flower.FWTabTag} for the type
	 * attribute.Represents tab of "menu" type.
	 */
	public final static String TAB_TYPE_MENU = "menu";

	/**
	 * Used in {@link com.ness.fw.ui.taglib.FWTableTag} for 
	 * the pagingControlType attribute.When the pagingControlType attribute
	 * is set to this value,the paging toolbar will not be rendered. 
	 */
	public static final int PAGING_NONE = 0;

	/**
	 * Used in {@link com.ness.fw.ui.taglib.FWTableTag} for 
	 * the pagingControlType attribute.When the pagingControlType attribute
	 * is set to this value,"first" anf "last" buttons will ne rendered. 
	 */
	public static final int PAGING_FIRSTLAST = 1;
	
	/**
	 * Used in {@link com.ness.fw.ui.taglib.FWTableTag} for 
	 * the pagingControlType attribute.When the pagingControlType attribute
	 * is set to this value,"next" anf "previous" buttons will ne rendered. 
	 */	
	public static final int PAGING_PREVNEXT = 2;
	
	/**
	 * Used in {@link com.ness.fw.ui.taglib.FWTableTag} for 
	 * the pagingControlType attribute.When the pagingControlType attribute
	 * is set to this value,links to the pages will rendered by the tag. 
	 */	
	public static final int PAGING_SPECIFIC_LINK = 4;
	

	/**
	 * Used in {@link com.ness.fw.ui.taglib.FWTableTag} for 
	 * the pagingControlType attribute.When the pagingControlType attribute
	 * is set to this value,combo with the pages numbers will rendered by the tag. 
	 */	
	public static final int PAGING_SPECIFIC_COMBO = 8;
	
	/**
	 * Used in {@link com.ness.fw.ui.TableModel} for
	 * paging which is managed outside the model.
	 */
	public static final String TABLE_PAGING_TYPE_NORMAL = "normal";
	
	/**
	 * Used in {@link com.ness.fw.ui.TableModel} for
	 * paging which is managed by the model.
	 */
	public static final String TABLE_PAGING_TYPE_AUTO = "auto";

	/**
	 * Used in {@link com.ness.fw.ui.TableModel} for
	 * sort which is managed outside the model.
	 */
	public static final String TABLE_SORT_TYPE_NORMAL = "normal";
	
	/**
	 * Used in {@link com.ness.fw.ui.TableModel} for
	 * sort which is managed by the model.
	 */
	public static final String TABLE_SORT_TYPE_AUTO = "auto";

	/**
	 * Used in {@link com.ness.fw.ui.TableModel} for
	 * the selectionType attribute.When the selectionType is set to this 
	 * value no selection of rows in the table is allowed. 
	 */
	public static final int TABLE_SELECTION_NONE = 0;

	/**
	 * Used in {@link com.ness.fw.ui.TableModel} for
	 * the selectionType attribute.When the selectionType is set to this 
	 * value only selection of one row in the table is allowed. 
	 */
	public static final int TABLE_SELECTION_SINGLE = 1;

	/**
	 * Used in {@link com.ness.fw.ui.TableModel} for
	 * the selectionType attribute.When the selectionType is set to this 
	 * value selection of few rows in the table is allowed. 
	 */
	public static final int TABLE_SELECTION_MULTIPLE = 2;

	/**
	 * Used in {@link com.ness.fw.ui.TableModel} for
	 * the selectionType attribute.When the selectionType is set to this 
	 * value selection of all the rows in the table in the same time is allowed. 
	 */
	public static final int TABLE_SELECTION_ALL = 4;
		
	/**
	 * Used in {@link com.ness.fw.ui.taglib.FWTreeTableTag} for the 
	 * treeSelectionType attribute.When the treeSelectionType is set to
	 * this value,there is no affect on a node's children nodes, when 
	 * the node is selected.
	 */
	public static final int TREE_TABLE_SELECTION_TYPE_NONE = 0;

	/**
	 * Used in {@link com.ness.fw.ui.taglib.FWTreeTableTag} for the 
	 * treeSelectionType attribute.When the treeSelectionType is set to
	 * this value,a node selection in the tree selects the nodes's 
	 * children,but a node deselection does not affect its children.
	 */
	public static final int TREE_TABLE_SELECTION_TYPE_CHECK_ONLY = 1;

	/**
	 * Used in {@link com.ness.fw.ui.taglib.FWTreeTableTag} for the 
	 * treeSelectionType attribute.When the treeSelectionType is set to
	 * this value,a node selection in the tree selects the nodes's 
	 * children and a node deselection deselects its children.
	 */
	public static final int TREE_TABLE_SELECTION_TYPE_ALL = 2;
	
	/**
	 * Used in {@link com.ness.fw.ui.TableModel} for column that is not sorted
	 */
	public static final int TABLE_COLUMN_SORT_NONE = 0;

	/**
	 * Used in {@link com.ness.fw.ui.TableModel} for setting
	 * ascending sort direction of a column.
	 */
	public static final int TABLE_COLUMN_SORT_ASC = 1;
	
	/**
	 * Used in {@link com.ness.fw.ui.TableModel} for setting
	 * descending sort direction of a column.
	 */
	public static final int TABLE_COLUMN_SORT_DESC = -1;
	
	/**
	 * Used in {@link com.ness.fw.ui.Column} for column that is not allowed to be sorted
	 */
	public static final int COLUMN_SORT_NONE = TABLE_COLUMN_SORT_NONE;

	/**
	 * Used in {@link com.ness.fw.ui.Column} for column that is allowed to be sorted
	 * only in ascending direction.
	 */
	public static final int COLUMN_SORT_ASC = TABLE_COLUMN_SORT_ASC;
	
	/**
	 * Used in {@link com.ness.fw.ui.Column} for for column that is allowed to be sorted
	 * only in descending direction.
	 */
	public static final int COLUMN_SORT_DESC = TABLE_COLUMN_SORT_DESC;

	/**
	 * Used in {@link com.ness.fw.ui.Column} for for column that is allowed to be sorted normally
	 * in both directions
	 */
	public static final int COLUMN_SORT_NORMAL = 2;
	
	
	/**
	 * Used in {@link com.ness.fw.ui.taglib.AbstractInputTag} for
	 * the state attribute,which affects on the the css class name 
	 * of the component.This state is the default state of a component.
	 */	
	public final static String COMPONENT_ENABLED_STATE = "enabled";

	/**
	 * Used in {@link com.ness.fw.ui.taglib.AbstractInputTag} for
	 * the state attribute,which affects on the the css class name 
	 * of the component.This state indicates that a field is disabled.
	 */	
	public final static String COMPONENT_DISABLED_STATE = "disabled";

	/**
	 * Used in {@link com.ness.fw.ui.taglib.AbstractInputTag} for
	 * the state attribute.This state indicates that a field is invisible.
	 */	
	public final static String COMPONENT_HIDDEN_STATE = "hidden";	
	
	/**
	 * Used in {@link com.ness.fw.ui.taglib.AbstractInputTag} for
	 * the state attribute,which affects on the the css class name 
	 * of the component.This state indicates that a field is mandatory.
	 */	
	public final static String COMPONENT_NORMAL_INPUT_TYPE = "normal";	

	/**
	 * Used in {@link com.ness.fw.ui.taglib.AbstractInputTag} for
	 * the state attribute,which affects on the the css class name 
	 * of the component.This state indicates that a field is mandatory.
	 */	
	public final static String COMPONENT_MANDATORY_INPUT_TYPE = "mandatory";
	
	/**
	 * Used in {@link com.ness.fw.ui.taglib.FWListTag} for the type
	 * attribute.When the type is set to this value a simple combo is 
	 * rendered by the tag.
	 */
	public final static String LIST_TYPE_SINGLE = "single";

	/**
	 * Used in {@link com.ness.fw.ui.taglib.FWListTag} for the type
	 * attribute.When the type is set to this value,the selected value  
	 * of the list model is rendered as a label by the tag.
	 */
	public final static String LIST_TYPE_LABEL = "label";

	/**
	 * Used in {@link com.ness.fw.ui.taglib.FWListTag} for the type
	 * attribute.When the type is set to this value a simple list is 
	 * rendered by the tag.
	 */
	public final static String LIST_TYPE_MULTIPLE = "multiple";

	/**
	 * Used in {@link com.ness.fw.ui.taglib.FWListTag} for the type
	 * attribute.When the type is set to this value a list and an
	 * expansion button are rendered by the tag.
	 */
	public final static String LIST_TYPE_MULTIPLE_EXPANDED = "multipleExpanded";

	/**
	 * Used in {@link com.ness.fw.ui.taglib.FWListTag} for the type
	 * attribute.When the type is set to this value a textbox and an
	 * expansion button are rendered by the tag.
	 */
	public final static String LIST_TYPE_SINGLE_EXPANDED = "singleExpanded";	

	/**
	 * Used in {@link com.ness.fw.ui.taglib.FWListTag} for the type
	 * attribute.When the type is set to this value, two lists are 
	 * are rendered by the tag.
	 */
	public final static String LIST_TYPE_LIST_TO_LIST = "listToList";

	/**
	 * Used in {@link com.ness.fw.ui.taglib.FWListTag} for the searchIn
	 * attribute.When the searchIn is set to this value,no search is available
	 * inside lists.
	 */
	public final static int LIST_SEARCH_IN_NONE = 0;

	/**
	 * Used in {@link com.ness.fw.ui.taglib.FWListTag} for the searchIn
	 * attribute.When the type is set to this value,search is available
	 * only inside the source list,when type is set to LIST_TYPE_LIST_TO_LIST.
	 */
	public final static int LIST_SEARCH_IN_SRC = 1;

	/**
	 * Used in {@link com.ness.fw.ui.taglib.FWListTag} for the searchIn
	 * attribute.When the searchIn is set to this value,search is available
	 * only inside the target list,when type is set to LIST_TYPE_LIST_TO_LIST
	 * or to LIST_TYPE_MULTIPLE_EXPANDED.
	 */
	public final static int LIST_SEARCH_IN_TARGET = 2;

	/**
	 * Used in {@link com.ness.fw.ui.taglib.FWListTag} for the searchIn
	 * attribute.When the searchIn is set to this value,search is available
	 * in both lists,when type is set to LIST_TYPE_LIST_TO_LIST.
	 */
	public final static int LIST_SEARCH_IN_ALL = 4;
	
	/**
	 * 
	 */	
	public final static int LIST_SEARCH_TYPE_NONE = 1;
	
	/**
	 * 
	 */
	public final static int LIST_SEARCH_TYPE_START = 2;
	
	/**
	 * 
	 */
	public final static int LIST_SEARCH_TYPE_CONTAINS = 4;
	
	/**
	 * 
	 */
	public final static int LIST_SEARCH_TYPE_END = 8;
	
	/**
	 * 
	 */
	public final static int LIST_SEARCH_TYPE_ALL = 16;
			
	/**
	 * 
	 */
	public final static int LIST_SEARCH_ACTION_SEARCH = 2;
	
	/**
	 * 
	 */
	public final static int LIST_SEARCH_ACTION_FILTER = 4;
	
	/**
	 * 
	 */
	public final static int LIST_SEARCH_ACTION_ALL = 8;
	
	/**
	 * Used in {@link com.ness.fw.ui.taglib.FWListTag} for the refreshType
	 * attribute,relevant only when type is set to LIST_TYPE_MULTIPLE_EXPANDED
	 * or LIST_TYPE_SINLE_EXPANDED.When the refreshType is set to this value,
	 * a click on the expansion button will open a static html page,without
	 * sending an event to the server.
	 */
	public final static int LIST_REFRESH_TYPE_NEVER = 0;

	/**
	 * Used in {@link com.ness.fw.ui.taglib.FWListTag} for the refreshType
	 * attribute,relevant only when type is set to LIST_TYPE_MULTIPLE_EXPANDED
	 * or LIST_TYPE_SINLE_EXPANDED.When the refreshType is set to this value,
	 * a click on the expansion button will send an event to the server.
	 */
	public final static int LIST_REFRESH_TYPE_ALWAYS = 1;

	/**
	 * Used in {@link com.ness.fw.ui.taglib.FWListTag} for the refreshType
	 * attribute,relevant only when type is set to LIST_TYPE_MULTIPLE_EXPANDED
	 * or LIST_TYPE_SINLE_EXPANDED.When the refreshType is set to this value,
	 * a first click on the expansion button will send an event to the server.
	 * The next clicks will open a static html page,without sending an event to the server
	 */
	public final static int LIST_REFRESH_TYPE_ONCE = 2;		
	
	/**
	 * Used in {@link com.ness.fw.ui.taglib.FWSelectionTag} for the type
	 * attribute,When the type is set to this value,the component will
	 * be rendered as checkboxes with multiple selection.
	 */	
	public final static String SELECTION_MULTIPLE = "multiple";

	/**
	 * Used in {@link com.ness.fw.ui.taglib.FWSelectionTag} for the type
	 * attribute,When the type is set to this value,the component will
	 * be rendered as radio buttons with single selection.
	 */	
	public final static String SELECTION_SINGLE = "single";

	/**
	 * Used in {@link com.ness.fw.ui.taglib.FWSelectionTag} for the orientation
	 * attribute.When the type is set to this value,the component will
	 * be rendered verically.
	 */	
	public final static String SELECTION_VERTICAL = "vertical";

	/**
	 * Used in {@link com.ness.fw.ui.taglib.FWSelectionTag} for the orientation
	 * attribute.When the type is set to this value,the component will
	 * be rendered horizontally.
	 */	
	public final static String SELECTION_HORIZONTAL = "horizontal";
	
	/**
	 * Used in {@link com.ness.fw.ui.taglib.FWTextTag} for the textType
	 * attribute.When the textType is set to this value,a normal text field will
	 * be rendered.
	 */	
	public final static String TEXT_FIELD_TYPE_DEFAULT = "default";

	/**
	 * Used in {@link com.ness.fw.ui.taglib.FWTextTag} for the textType
	 * attribute.When the textType is set to this value,the text field will
	 * contain a date value.The tag will render an expansion button which
	 * opens a calendar for selecting a date.
	 */	
	public final static String TEXT_FIELD_TYPE_DATE = "date";

	/**
	 * Used in {@link com.ness.fw.ui.taglib.FWTextTag} for the textType
	 * attribute.When the textType is set to this value,the text field will
	 * contain a date value in mm/yy format.The tag will render an expansion button which
	 * opens a calendar for selecting a date.
	 */
	public final static String TEXT_FIELD_TYPE_MONTH_DATE = "monthDate";

	/**
	 * Used in {@link com.ness.fw.ui.taglib.FWTextTag} for the textType
	 * attribute.When the textType is set to this value,the text field will
	 * contain a numeric value
	 */	
	public final static String TEXT_FIELD_TYPE_NUMERIC = "numeric";

	/**
	 * Used in {@link com.ness.fw.ui.taglib.FWTextTag} for the textType
	 * attribute.When the textType is set to this value,the text field will
	 * contain an integer value
	 */	
	public final static String TEXT_FIELD_TYPE_INTEGER = "int";

	/**
	 * Used in {@link com.ness.fw.ui.taglib.FWTextTag} for the textType
	 * attribute.When the textType is set to this value,the text field will
	 * contain a password
	 */	
	public final static String TEXT_FIELD_TYPE_PASSWORD = "password";
		
	/**
	 * Used in {@link com.ness.fw.ui.taglib.FormParametersTag} for the state
	 * When this value is used,form parameters's opening is enabled.
	 */
	public final static String FORM_PARAMETERS_STATE_ENABLED = "enabled";

	/**
	 * Used in {@link com.ness.fw.ui.taglib.FormParametersTag} for the state
	 * When this value is used,form parameters's opening is disabled.
	 */
	public final static String FORM_PARAMETERS_STATE_DISABLED = "disabled";
	
	/**
	 * Used in {@link com.ness.fw.ui.taglib.CollapsibleSectionTag} for the sectionGap
	 * attribute.When sectionGap is set to this value,no gap is rendered between
	 * section, when a section of a {@link com.ness.fw.ui.taglib.CollapsibleTag}
	 * is rendered.
	 */
	public final static int GAP_TYPE_NONE = 0;	

	/**
	 * Used in {@link com.ness.fw.ui.taglib.CollapsibleSectionTag} for the sectionGap
	 * attribute.When sectionGap is set to this value,a gap is rendered before
	 * the section,when a section of a {@link com.ness.fw.ui.taglib.CollapsibleTag}
	 * is rendered.
	 */
	public final static int GAP_TYPE_BEFORE = 1;
	
	/**
	 * Used in {@link com.ness.fw.ui.taglib.CollapsibleSectionTag} for the sectionGap
	 * attribute.When sectionGap is set to this value,a gap is rendered after
	 * the section,when a section of a {@link com.ness.fw.ui.taglib.CollapsibleTag}
	 * is rendered.
	 */
	public final static int GAP_TYPE_AFTER = 2;

	/**
	 * Used in {@link com.ness.fw.ui.taglib.CollapsibleSectionTag} for the sectionGap
	 * attribute.When sectionGap is set to this value,a gap is rendered before
	 * and after the section,when a section of a {@link com.ness.fw.ui.taglib.CollapsibleTag}
	 * is rendered.
	 */
	public final static int GAP_TYPE_BOTH = 4;
	
	/**
	 * Used in {@link com.ness.fw.ui.taglib.PanelTag} for the scrollType
	 * attribute.When scrollType is set to this value,the panel is
	 * rendered without scroll. 
	 */
	public final static int PANEL_SCROLL_NONE = 0;

	/**
	 * Used in {@link com.ness.fw.ui.taglib.PanelTag} for the scrollType
	 * attribute.When scrollType is set to this value,the panel is
	 * rendered with horizontal scroll. 
	 */
	public final static int PANEL_SCROLL_X_AUTO = 1;

	/**
	 * Used in {@link com.ness.fw.ui.taglib.PanelTag} for the scrollType
	 * attribute.When scrollType is set to this value,the panel is
	 * rendered with vertical scroll. 
	 */
	public final static int PANEL_SCROLL_Y_AUTO = 2;

	/**
	 * Used in {@link com.ness.fw.ui.taglib.PanelTag} for the scrollType
	 * attribute.When scrollType is set to this value,the panel is
	 * rendered with vertical and horizontal scroll. 
	 */
	public final static int PANEL_SCROLL_AUTO = 4;
	
	/**
	 * Used in {@link com.ness.fw.ui.taglib.TreeTag} for the nodeOpenType
	 * attribute.When nodeOpenType is set to this value,the expanding of 
	 * a node in the tree will not send an event to the server.
	 */
	public final static int TREE_OPEN_NODE_TYPE_DEFAULT = 1;

	/**
	 * Used in {@link com.ness.fw.ui.taglib.TreeTag} for the nodeOpenType
	 * attribute.When nodeOpenType is set to this value,the expanding of 
	 * a node in the tree will send an event to the server as long as the
	 * the node does not have child nodes under it.When the node's childs
	 * node are fetched in the server,the expanding of the node will not
	 * send an event to the server.
	 */
	public final static int TREE_OPEN_NODE_TYPE_SUBMIT_FIRST_TIME = 2;

	/**
	 * Used in {@link com.ness.fw.ui.taglib.TreeTag} for the nodeOpenType
	 * attribute.When nodeOpenType is set to this value,the expanding of 
	 * a node in the tree will always send an event to the server.
	 */
	public final static int TREE_OPEN_NODE_TYPE_SUBMIT_ALWAYS = 3;
	
	/**
	 * Used in {@link com.ness.fw.ui.CollapsibleModel}
	 * Constant for "close" state of a section in the model.
	 */
	public final static int COLLAPSE_CLOSE = 0;

	/**
	 * Used in {@link com.ness.fw.ui.CollapsibleModel}
	 * Constant for "open" state of a section in the model.
	 */
	public final static int COLLAPSE_OPEN = 1;

	/**
	 * Used in {@link com.ness.fw.ui.AbstractTableModel} ,in {@link com.ness.fw.ui.TreeModel},<br>
	 * in {@link com.ness.fw.ui.Cell} and in {@link com.ness.fw.ui.TreeNode} for the <br>
	 * setDataType method.When this value is used the data which is set by the setData method<br>
	 * must be of type java.lang.String
	 */
	public final static String DATA_TYPE_STRING = "string";
	
	/**
	 * Used in {@link com.ness.fw.ui.AbstractTableModel} ,in {@link com.ness.fw.ui.TreeModel},<br>
	 * in {@link com.ness.fw.ui.Cell} and in {@link com.ness.fw.ui.TreeNode} for the <br>
	 * setDataType method.When this value is used the data which is set by the setData method<br>
	 * must be of type java.lang.Number
	 */
	public final static String DATA_TYPE_NUMBER = "number";
	
	/**
	 * Used in {@link com.ness.fw.ui.AbstractTableModel} ,in {@link com.ness.fw.ui.TreeModel},<br>
	 * in {@link com.ness.fw.ui.Cell} and in {@link com.ness.fw.ui.TreeNode} for the <br>
	 * setDataType method.When this value is used the data which is set by the setData method<br>
	 * must be of type java.util.Date
	 */
	public final static String DATA_TYPE_DATE = "date";
	
	/**
	 * Used in {@link com.ness.fw.ui.AbstractTableModel} ,in {@link com.ness.fw.ui.TreeModel},<br>
	 * in {@link com.ness.fw.ui.Cell} and in {@link com.ness.fw.ui.TreeNode} for the <br>
	 * setDataType method.When this value is used the data which is set by the setData method<br>
	 * must be of type java.lang.Boolean
	 */
	public final static String DATA_TYPE_BOOLEAN = "boolean";
		
	public final static String FIELD_TYPE_PAGE_SCOPE= "pageScope";
	
	public final static String FIELD_TYPE_CONTEXT= "contextScope";
	
	public final static String CAPTION_FIELD_TYPE_PROPERTIES = "properties";
	
	public final static String CAPTION_FIELD_TYPE_PAGE_SCOPE = "pageScope";
		
	public final static String TAG_TYPE_LIST = "list";

	public final static String TAG_TYPE_LABEL = "label";
	
	public final static String TAG_TYPE_TEXT_FIELD = "textField";
	
	public final static String TAG_TYPE_TEXT_AREA = "textArea";
	
	public final static String TAG_TYPE_SELECTION = "selection";
	
	public static final String MODEL_PARAM_SEPERATOR = "|";

	public static final String DIRTY_MODEL_PARAM_SEPERATOR = "+";
	
	public final static String FORM_DEFAULT_ID = "mainForm";
	
	/**
	 * This constant is related to level of authorization.<br>
	 * It indicates that this event may only cause to read actions<br>
	 * which means that a less strict level of authorization should be used.	 
	 */
	public final static int EVENT_TYPE_READONLY = SystemConstants.EVENT_TYPE_READONLY;

	/**
	 * This constant is related to level of authorization.<br>
	 * It indicates that this event may also cause to write actions<br>
	 * which means that a more strict level of authorization should be used.
	 */
	public final static int EVENT_TYPE_READWRITE = SystemConstants.EVENT_TYPE_READWRITE;

	/**
	 * This constant is used for the eventType property when sending an event to a normal window.
	 */
	public final static String EVENT_TARGET_TYPE_NORMAL = SystemConstants.EVENT_TARGET_TYPE_NORMAL;

	/**
	 * This constant is used for the eventType property when sending an event to a popup window.
	 */
	public final static String EVENT_TARGET_TYPE_POPUP = SystemConstants.EVENT_TARGET_TYPE_POPUP;

	/**
	 * This constant is used for the eventType property when sending an event to a modal window.
	 */
	public final static String EVENT_TARGET_TYPE_DIALOG = SystemConstants.EVENT_TARGET_TYPE_DIALOG;		

	/**
	 * This constant is used for the eventType property when sending an event and closing a modal window.
	 */
	public final static String EVENT_TARGET_TYPE_CLOSE_DIALOG = SystemConstants.EVENT_TARGET_TYPE_CLOSE_DIALOG;

	/**
	 * This constant is used for the eventType property when sending an event and closing a popup window.
	 */
	public final static String EVENT_TARGET_TYPE_CLOSE_POPUP = SystemConstants.EVENT_TARGET_TYPE_CLOSE_POPUP;	
}
