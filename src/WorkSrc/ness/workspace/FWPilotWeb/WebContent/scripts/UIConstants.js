/***********form constants**************/
var FORM_NAME = "mainForm";
var FORM_FIELD_EVENT_NAME = "eventData.eventName";
var FORM_FIELD_FLOW_STATE = "eventData.flowState";
var FORM_FIELD_FLOW_ID = "eventData.flowId";
var FORM_FIELD_EXTRA_PARAMS = "eventData.eventExtraParams";
var FORM_FIELD_EVENT_CHECK_WARNINGS = "checkWarnings";
var FORM_FIELD_IS_POPUP_WINDOW = "isPopupWindow";

/***********model constants***************/
var MODEL_PARAM_SEPERATOR = "|";
var MODEL_KEY_VALUE_SEPERATOR = "=";
var MODEL_MULTI_VALUES_SEPERATOR = ",";
var MODEL_MULTI_VALUES_START = "{";
var MODEL_MULTI_VALUES_END = "}";
var MODEL_EVENT_DATA_CONSTANT = "";
var MODEL_EVENT_TYPE_PROPERTY = "eventType";

/***********list model constants***************/
var LIST_MODEL_SELECTED_KEYS_PROPERTY = "keys"; 
var LIST_MODEL_SELECTED_KEY_PROPERTY = "key"; 
var LIST_MODEL_SEARCH_STRING_PROPERTY = "str";
var LIST_MODEL_EDITABLE_VALUE_PROPERTY = "editableValue";

/***********toolbar model constants***************/
var TOOLBAR_MODEL_CLICKED_BUTTON_PROPERTY = "tbName";
var TOOLBAR_MODEL_CLICKED_BUTTON_SET_PROPERTY = "tbsName";

/***********table model constants***************/
var TABLE_MODEL_SORT_DIR_PROPERTY = "sortDir";
var TABLE_MODEL_SORT_COLUMN_PROPERTY = "sortCol";
var TABLE_MODEL_PAGING_TYPE_PROPERTY = "pagingType";
var TABLE_MODEL_PAGE_NUMBER_PROPERTY = "page";
var TABLE_MODEL_PAGE_START_RANGE_PROPERTY = "range";
var TABLE_MODEL_SELECTED_ROWS_PROPERTY = "rows";
var TABLE_MODEL_UNSELECTED_ROWS_PROPERTY = "uRows";
var TABLE_MODEL_SELECTED_ROW_PROPERTY = "row";
var TABLE_MODEL_SELECTED_MENU_ITEM_PROPERTY = "menuIndex";
var TABLE_MODEL_SELECTED_MENU_ROW_PROPERTY = "menuRow";
var TABLE_MODEL_SELECTED_LINK_ROW_PROPERTY = "linkRow";
var TABLE_MODEL_SELECTED_CELL_PROPERTY = "cell";	
var TABLE_MODEL_MENU_ACTION_PROPERTY = "menuAction";
var TABLE_MODEL_ORDER_PROPERTY = "colOrder";

var TABLE_MODEL_ADVANCED_SORT_DIR_PROPERTY = "sortDirs";
var TABLE_MODEL_ADVANCED_SORT_COLUMN_PROPERTY = "sortCols";

/***********tree table model constants***************/
var TREE_TABLE_MODEL_OPENED_ROWS_PROPERTY = "open";
var TREE_TABLE_MODEL_CLOSED_ROWS_PROPERTY = "close";

/***********tree model constants***************/
var TREE_MODEL_OPENED_NODES_PROPERTY = "open";
var TREE_MODEL_CLOSED_NODES_PROPERTY = "close";
var TREE_MODEL_SELECTED_NODES_PROPERTY = "selectedNodes";
var TREE_MODEL_SELECTED_NODE_PROPERTY = "selectedNode";
var TREE_MODEL_OPEN_NODE_PROPERTY = "exNode";

/***********collapsible model constants***************/
var COLLAPSIBLE_MODEL_OPEN_SECTIONS = "sections";	
var COLLAPSIBLE_MODEL_CLOSE = 0;
var COLLAPSIBLE_MODEL_OPEN = 1;

/***********messages model constants***************/
var MESSAGES_MODEL_STATE = "messages";
var MESSAGES_MODEL_CLOSE = 0;
var MESSAGES_MODEL_OPEN = 1;
var WARNING_EVENT_SUFFIX = ".Accept";
var WARNING_CLEARED = "0";

/***********dirty model constants***************/
var DIRTY_MODEL_ISDIRTY_PROPERTY = "isDirty";
var DIRTY_MODEL_DIRTY = "0";
var DIRTY_MODEL_CLEAN = "1";
var DIRTY_MODEL_ID = "dirtyFlagModel";
var DIRTY_MODEL_PARAM_SEPERATOR = "+";

/***********help model constants***************/
var HELP_MODEL_CONTENT_PROPERTY = "content";
var HELP_MODEL_LINKED_ITEMS_PROPERTY = "linkedItems";
var HELP_MODEL_SELECTED_ITEM_PROPERTY = "selectedItem";
var HELP_MODEL_EXPANDED_DIRECTORIES_PROPERTY = "expandedDirs";
var HELP_MODEL_EXPANDED_DIRECTORY_PROPERTY = "expandedDir"
		
/***********events constants***************/
var TABLE_MODEL_SORT_EVENT_TYPE = "sort";
var TABLE_MODEL_ADVANCED_SORT_EVENT_TYPE = "aSort";
var TABLE_MODEL_PAGING_EVENT_TYPE = "paging";
var TABLE_MODEL_LINK_EVENT_TYPE = "link";
var TABLE_MODEL_ROW_EVENT_TYPE = "singleRow";
var TABLE_MODEL_ROWS_EVENT_TYPE = "multipleRow";
var TABLE_MODEL_MENU_EVENT_TYPE = "menu";
var TABLE_MODEL_COLUMN_ORDER_EVENT_TYPE = "order";

var TREE_TABLE_MODEL_OPEN_EVENT_TYPE = "ot";

var TREE_MODEL_OPEN_EVENT_TYPE = "ot";
var TREE_MODEL_NODES_EVENT_TYPE = "nodes";
var TREE_MODEL_NODE_EVENT_TYPE = "node";
var TREE_MODEL_OPEN_NODE_EVENT_TYPE = "expand";

var LIST_MODEL_CLICK_EVENT_TYPE = "click";
var LIST_MODEL_CHANGE_EVENT_TYPE = "change";
var LIST_MODEL_SEARCH_EVENT_TYPE = "search";

var BUTTON_MODEL_CLICK_EVENT_TYPE = "bClick";

var FORM_PARAMETERS_MODEL_CLICK_EVENT_TYPE = "fmClick";

var TOOLBAR_MODEL_CLICK_EVENT_TYPE = "tbClick";

var HELP_MODEL_EXPAND_NODE_EVENT_TYPE = "expand";
var HELP_MODEL_SHOW_EVENT_TYPE = "show";
var HELP_MODEL_OPEN_EVENT_TYPE = "open";
var HELP_MODEL_NEW_EVENT_TYPE = "new";
var HELP_MODEL_SAVE_EVENT_TYPE = "save";
var HELP_MODEL_ADD_LINKS_EVENT_TYPE = "addLinks";
var HELP_MODEL_REMOVE_LINKS_EVENT_TYPE = "remove";
var HELP_MODEL_PREVIEW_EVENT_TYPE = "preview";
var HELP_MODEL_CANCEL_PREVIEW_EVENT_TYPE = "cancelPreview";

/**********list tag constants*************/
var LIST_TYPE_SINGLE = "single";
var LIST_TYPE_MULTIPLE = "multiple";
var LIST_TYPE_MULTIPLE_EXPANDED = "multipleExpanded";
var LIST_TYPE_SINGLE_EXPANDED = "singleExpanded";	
var LIST_TYPE_LIST_TO_LIST = "listToList";
var LIST_TABLE_ID = "listComponent";

var JS_L2L_OBJECT_TYPE_SRC = "1";
var JS_L2L_OBJECT_TYPE_TRG = "2";
var JS_L2L_OBJECT_TYPE_ALL = "4";

var LIST_SEARCH_IN_NONE = 0;	
var LIST_SEARCH_IN_SOURCE = 1;
var LIST_SEARCH_IN_TARGET = 2;
var LIST_SEARCH_IN_ALL = 4;

var LIST_SEARCH_TYPE_NONE = 1;
var LIST_SEARCH_TYPE_START = 2;
var LIST_SEARCH_TYPE_CONTAINS = 4;
var LIST_SEARCH_TYPE_END = 8;
var LIST_SEARCH_TYPE_ALL = 16;
	
var LIST_SEARCH_ACTION_NONE = 1;	
var LIST_SEARCH_ACTION_SEARCH = 2;	
var LIST_SEARCH_ACTION_FILTER = 4;	
var LIST_SEARCH_ACTION_ALL = 8;

var LIST_REFRESH_TYPE_NEVER = 0;
var LIST_REFRESH_TYPE_ALWAYS = 1;
var LIST_REFRESH_TYPE_ONCE = 2;		

var LIST_MULTIPLE_EXPANDER_DIALOG_FILENAME = "html/UIListToList.html";
var LIST_SINGLE_EXPANDER_DIALOG_FILENAME = "html/UIListToText.html";

var LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_DIR = "dir";
var LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_OBJECT_TYPE = "objectType";
var LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_SEARCH_TYPE = "searchType";
var LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_SEARCH_ACTION = "searchAction";
var LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_SEARCH_IN = "searchIn";
var LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_IS_ACTION_SELECT = "isActionSelect";
var LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_IS_TYPE_SELECT = "isTypeSelect";
var LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_IS_ORDER_BUTTONS = "isOrderButtons";
var LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_TITLE = "title";
var LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_LABELS = "labels";
var LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_MESSAGES = "messages";
var LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_SRC_ARR = "srcArr";
var LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_TRG_ARR = "trgArr";

/**********textArea tag constants*************/
var TEXT_AREA_DIALOG_WIDTH = "750px";
var TEXT_AREA_DIALOG_HEIGHT = "320px";
var TEXT_AREA_DIALOG_SCROLL = "no";
var TEXTAREA_EXPANDER_DIALOG_FILENAME = "html/UITextAreaExpander.html";
var TEXTAREA_EXPANDER_PARAM_TEXTAREA = "textArea";
var TEXTAREA_EXPANDER_PARAM_DIR = "dir";
var TEXTAREA_EXPANDER_PARAM_TITLE = "title";
var TEXTAREA_EXPANDER_PARAM_APPROVE_BUTTON_TITLE = "approveButtonTitle";
var TEXTAREA_EXPANDER_PARAM_CANCEL_BUTTON_TITLE = "cancelButtonTitle";

/**********table tag constants*************/
var TABLE_DIV_ID = "BodyTable";
var TABLE_COMPONENT_NAME = "tableComponent";
var TABLE_CHECKBOX_SUFFIX = "Checkbox";
var TABLE_SORT_COLUMNS_SUFFIX = "Columns";
var TABLE_ORDER_COLUMNS_SUFFIX = "ColumnsOrder";
var TABLE_CHECK_ALL_CHECKBOX_PREFIX = "SelectAll";

/**********tree tag constants*************/
var TREE_COMPONENT_NAME = "treeComponent";
var TREE_TABLE_SUFFIX = "Tree";
var TREE_ROW_PREFIX = "row";

/**********tree table tag constants*************/
var TREE_TABLE_SELECTION_TYPE_NONE = 0;
var TREE_TABLE_SELECTION_TYPE_CHECK_ONLY = 1;
var TREE_TABLE_SELECTION_TYPE_ALL = 2;

/**********form parameter tag constants*************/
FORM_PARAMETERS_ID = "formParameters";

/**********collapsible tag constants*************/
COLLAPSIBLE_SECTION_PREFIX = "sec:";

/**********menu tag constants*************/
var MENU_DIV_LEVEL = "menuDivLevel";
var MENU_IFRAME = "menuIframe";

/**********constants for sorting*************/
var SORT_ASC = 1;
var SORT_NONE = 0;
var SORT_DESC = -1;

/**********constants for paging*************/
var PAGING_NEXT = 0;
var PAGING_PREV = 1;
var PAGING_TO = 2;

/***********system constants**************/
SYSTEM_DIR_RTL = "rtl";
SYSTEM_DIR_LTR = "ltr";

/***********shortcut keys constants**************/
var SHORTCUT_COMPONENT_PREFIX = "scId=";
var SHORTCUT_SHIFT_PRESSED = 1;
var SHORTCUT_CTRL_PRESSED = 2;
var SHORTCUT_ALT_PRESSED = 4;

/**********constants for target types when sending an event*********/
var EVENT_TARGET_TYPE_DIALOG = "dialog";
var EVENT_TARGET_TYPE_POPUP = "popup";
var EVENT_TARGET_TYPE_NEW_WINDOW = "newWindow";
var EVENT_TARGET_TYPE_NORMAL = "normal";
var EVENT_TARGET_TYPE_CLOSE_DIALOG = "closeDialog";
var EVENT_TARGET_TYPE_CLOSE_POPUP = "closePopup";
var POPUP_WINDOW_INDICATION = "1";
var NORMAL_WINDOW_INIDCATION = "0";

/**********constants for opening modal dialogs***********/
var DIALOG_ARGUEMENT_PARENT_TARGET = "target";
var DIALOG_ARGUEMENT_PARENT_DOCUMENT = "document";
var DIALOG_ARGUEMENT_PARENT_WINDOW = "window";
var DIALOG_ARGUEMENT_SOURCE_FORM = "sourceForm";
var DIALOG_PARAM_WIDTH = "dialogWidth:";
var DIALOG_PARAM_HEIGHT = "dialogHeight:";
var DIALOG_PARAM_TOP = "dialogTop:";
var DIALOG_PARAM_LEFT = "dialogLeft:";
var DIALOG_PARAM_SCROLL = "scroll:";
var DIALOG_DEFAULT_PARAMS = "";
var DIALOG_FILENAME = "html/UIModalDialog.html";

/**********constants for opening popup windows***********/
var POPUP_DEFAULT_PARAMS = "status=no";
var POPUP_TARGET_SELF = "_self";
var POPUP_TARGET_NEW = "_new";

/**********constants for opening help screens***********/
var HELP_DIRECTORY = "help/";
var HELP_CONTAINER_DIV_ID = "helpLinksDiv";
var HELP_CONTAINER_DIALOG_FILENAME = "html/UIHelpLinks.html";
var HELP_PARAM_NAME_HELP_INFO = "helpInfo";
var HELP_FORM_NAME = "helpForm";
var HELP_FORM_ACTION = "hs";
var HELP_POPUP_DEFAULT_PARAMS = "status=no";
var HELP_DIALOG_DEFAULT_PARAMS = "";
var HELP_WINDOW_NAME = "helpWin";

/**********constants for rich text editor**********/
var EDITOR_SELECT_COLOR_FILENAME = "html/UIEditorSelectColor.html";
var EDITOR_CREATE_TABLE_FILENAME = "html/UIEditorCreateTable.html";
var EDITOR_INITIAL_CONTENT = "<body></body>";

/**********authorization related constants********/
var TEXT_FIELD_NOT_AVAILABLE = "notAvailable";

/**********general messages indexes**********/
var GENERAL_MESSAGE_CONFIRM_DIRTY = 0;
var GENERAL_MESSAGE_CONFIRM_WARNINGS = 1;
var GENERAL_MESSAGE_ERROR_DIRTY = 2;
var GENERAL_MESSAGE_CONFIRM_DIRTY_AREAS = 3;
var GENERAL_MESSAGE_DIRTY_AREA_NOT_FOUND1 = 4;
var GENERAL_MESSAGE_DIRTY_AREA_NOT_FOUND2 = 5;
var GENERAL_MESSAGE_POPUP_NOT_LEGAL = 6;

/**********components id suffixes***********/
//list
var LIST_SUFFIX = "TrgList";

//selection
var SELECTION_SUFFIX = "Selection";

//button
var Button_SUFFIX = "Button";

//complex component suffix - like the <table> in l2l,the table in text
//component with date type,etc.
var COMPLEX_COMPONENT_SUFFIX = "Wrapper";

/**********popup windows constants***********/
var POPUP_COLUMN_ORDER_WIDTH = 500;
var POPUP_COLUMN_ORDER_HEIGHT = 300;
var POPUP_SORT_WIDTH = 228;
var POPUP_SORT_ORDER_HEIGHT = 342;
var POPUP_CALENDAR_WIDTH = 245;
var POPUP_CALENDAR_ORDER_HEIGHT = 240;
var POPUP_MONTH_CALENDAR_WIDTH = 200;
var POPUP_MONTH_CALENDAR_ORDER_HEIGHT = 175;
var POPUP_HELP_LINKS_WIDTH = 200;
var POPUP_HELP_LINKS_HEIGHT = 175;

/*************style sheets constants***********************/
CSS_SUFFIX_DISABLED = "Disabled";
CSS_SUFFIX_SELECTED = "Selected";
CSS_SUFFIX_OVER = "Over";
CSS_SUFFIX_LTR = "_ltr";

/***********general constants**************/
var SEPERATOR = "|";
var SEPERATOR_MINUS = "-";
var ERROR_DIV_ID = "errorPageDiv";
var ERROR_AREA_START_SIGN = "<ERRORPAGESTART>";
var ERROR_AREA_END_SIGN = "<ERRORPAGEEND>";

/***********html related constants**************/
var HTML_FILE_SUFFIX = ".html";

var HTML_TAG_INPUT = "INPUT";
var HTML_TAG_IMAGE = "IMG";
var HTML_TAG_BUTTON = "BUTTON";
var HTML_TAG_TEXTAREA = "TEXTAREA";

var HTML_VALUE_BUTTON = "button";
var HTML_VALUE_PASSWORD = "password";
var HTML_VALUE_TEXT = "text";
var HTML_VALUE_CHECKBOX = "checkbox";
var HTML_VALUE_RADIO = "radio";
var HTML_VALUE_VISIBLE = "visible";
var HTML_VALUE_HIDDEN = "hidden";
var HTML_VALUE_NONE = "none";
var HTML_VALUE_POST = "post";
var HTML_VALUE_AFTER_BEGIN = "AfterBegin";

var HTML_EVENT_ONCLICK = "onclick";
var HTML_EVENT_ONCHANGE = "onchange";
var HTML_EVENT_KEYPRESS = "keypress";

var PIXEL = "px";
