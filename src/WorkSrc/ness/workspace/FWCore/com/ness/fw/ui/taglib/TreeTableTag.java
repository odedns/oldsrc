package com.ness.fw.ui.taglib;

import java.util.ArrayList;

import com.ness.fw.shared.ui.FlowerUIUtil;
import com.ness.fw.ui.*;
import com.ness.fw.ui.events.Event;
import com.ness.fw.common.exceptions.UIException;

public class TreeTableTag extends AbstractTableTag
{
	protected String openImage;
	protected String closeImage;
	protected String emptyImage;
	protected String[] levelOpenImages;
	protected String[] levelCloseImages;
	protected String[] levelEmptyImages;	
	protected String[] levelClassNames;
	protected String[] levelStyles;
	protected String rowClassName;
	private String imagesDir;
	private int treeSelectionType = UIConstants.TREE_TABLE_SELECTION_TYPE_NONE;
	
	/*constants for js functions*/
	protected static String JS_CHECK_ALL_ROWS = "checkAllTreeRows";
	protected static String JS_OPEN_TREE = "openTree";
	protected static String JS_SAVE_TREE_STATE = "saveTreeState";
	protected static String JS_ROW_SELECT_SONS_EVENT  = "rowSelectRowSonsEvent";
	/*constants for js variables*/
	protected static String JS_VAR_OPEN_NODE_SIGN = "openNodeSign";
	protected static String JS_VAR_CLOSE_NODE_SIGN = "closeNodeSign";
		
	/*constants for tree attributes inside the html*/
	protected static String HTML_TREE_ATTRIBUTE_ROW_ID = "rid";
	protected static String HTML_TREE_ATTRIBUTE_HASSONS = "hasSons";
	protected static String HTML_TREE_ATTRIBUTE_SHOWSONS = "showSons";
	protected static String HTML_TREE_ATTRIBUTE_LEVEL = "level";
	protected static String HTML_TREE_ATTRIBUTE_PARENT_OPEN = "pOpen";
	protected static String HTML_TREE_ATTRIBUTE_OPEN = "open";
	protected static String HTML_TREE_ATTRIBUTE_CLOSE_IMAGE = "cImg";
	protected static String HTML_TREE_ATTRIBUTE_OPEN_IMAGE = "oImg";
			
	/*constants for variables prefixes*/
	protected static String PREFIX_OPEN_NODES_STR = "TO";
			
	/*set model*/
	public void setModel(TreeTableModel model)
	{
		this.model = model;
	}
	
	protected void initModel() throws UIException
	{
		if (model == null)
		{
			model = (TreeTableModel)FlowerUIUtil.getObjectFromContext(getHttpRequest(),id);
		}
		if (model == null)
		{
			throw new UIException("no tree table model exists in context's field " + id);
		}	
		
		//initialize the state of this table tag
		initState();
		
		//initialize tag properties
		initTagProperties();		
		
		lastColumnRendered = null;
		numberOfRenderedColumns = model.getColumnsCount();
	}
		
	protected void renderStartTag() throws UIException
	{
		initCss();
		renderStart();
		renderWrapperTable();
		renderHiddenField();	
		model.setAuthLevel(getAuthLevel());	
	}
	
	protected void resetTagState()
	{
		openImage = null;
		closeImage = null;
		emptyImage = null;
		levelOpenImages = null;
		levelCloseImages = null;
		levelEmptyImages = null;	
		levelClassNames = null;
		levelStyles = null;
		rowClassName = null;
		imagesDir = null;
		treeSelectionType = UIConstants.TREE_TABLE_SELECTION_TYPE_NONE;	
		super.resetTagState();		
	}
	
	protected void renderEndTag()
	{
		
	}
	
	protected void initCss()
	{
		super.initCss();
		openImage = initUIProperty(openImage,"ui.treeTable.image.open");
		closeImage = initUIProperty(closeImage,"ui.treeTable.image.close");
		emptyImage = initUIProperty(emptyImage,"ui.treeTable.image.empty");
		rowClassName = initUIProperty(rowClassName,"ui.table.row");
		imagesDir = getLocalizedImagesDir();
	}
	
	protected void renderStart()
	{
		startTag(SCRIPT);
		endTag();
		append("openNodeSign['" + id + "']=" + getSingleQuot(openImage));
		append(";");
		append("closeNodeSign['" + id + "']=" + getSingleQuot(closeImage));
		endTag(SCRIPT);
	}
	
	/*render rows*/
	protected void renderRows() throws UIException
	{
		TreeTableModel treeTableModel = (TreeTableModel) model;
		renderNode(treeTableModel.getRoot(), 0);
	}
	
	private void renderNode(TableTreeNode tableTreeNode, int level) throws UIException
	{
		TreeTableModel treeTableModel = (TreeTableModel) model;
		if (level == 0)
		{
			tableTreeNode.setPath(id);
		}
		if (!(!treeTableModel.isShowRoot() && level == 0))
		{
			renderRow(tableTreeNode,level);
		}
		for (int index = 0; index < tableTreeNode.getChildrenNumber(); index++)
		{
			TableTreeNode son = (TableTreeNode)tableTreeNode.getChild(index);
			son.setPath(getNodePath(tableTreeNode, index));
			if (treeTableModel.getRootLevel() == TableTreeModel.MULTIPLE_ROOT_TREE_LEVEL && level == 0)
			{
				son.setVisible(true);
			}
			else
			{
				son.setVisible(tableTreeNode.isOpen() && tableTreeNode.isVisible());
			}
			renderNode(son, level + 1);
		}
	}
	
	private void renderRow(TableTreeNode tableTreeNode,int level) throws UIException
	{
		TreeTableModel treeTableModel = (TreeTableModel) model;
		Row row = tableTreeNode.getRow();
		if (row.isSelected() && !row.isSelectable())
		{
			throw new UIException("row " + row.getId() + " in model " + id + " is not selectable");
		}		
		String rowClass = levelClassNames != null && levelClassNames[level] != null ? levelClassNames[level] : "level" + (treeTableModel.isShowRoot() ? level + 1 : level) + "Class";
		String rowCss = levelStyles != null && levelStyles[level] != null ? levelStyles[level] : rowStyle;
		boolean isCellRowSelected = !model.isMultiple() && row.isSelected();
		String open = String.valueOf(tableTreeNode.isOpen() && tableTreeNode.hasChildren());
		String pOpen = tableTreeNode.getParent() == null ? "" : String.valueOf(( (TableTreeNode) (tableTreeNode.getParent()) ).isOpen());
		startTag(ROW);
		
		if (!tableTreeNode.isVisible() && level > treeTableModel.getRootLevel())
		{
			addAttribute(STYLE,DISPLAY_NONE);
		}
		if (isCellRowSelected)
		{
			renderStyles(cssPre + rowClass + "Tr" + " " + selectedRowClassName, selectedRowStyle);
		}
		else
		{
			renderStyles(cssPre + rowClass + "Tr",rowCss);
		}
		addAttribute(ID,PREFIX_ROW_ID + tableTreeNode.getPath());
		addAttribute(HTML_TREE_ATTRIBUTE_ROW_ID,row.getId());
		addAttribute(VALIGN, TOP);
		addAttribute(HTML_TREE_ATTRIBUTE_LEVEL, String.valueOf(level));
		addAttribute(HTML_TREE_ATTRIBUTE_HASSONS, String.valueOf(tableTreeNode.hasChildren()));
		addAttribute(HTML_TREE_ATTRIBUTE_SHOWSONS,String.valueOf(tableTreeNode.hasChildren() && tableTreeNode.isOpen()));
		addAttribute(HTML_TREE_ATTRIBUTE_OPEN,open);
		addAttribute(HTML_TREE_ATTRIBUTE_PARENT_OPEN,pOpen);
		
		//render single selection
		renderSingleSelection(row,tableTreeNode.getPath(),treeTableModel.getRootLevel() == level,rowClass,rowCss);
		
		//render extra data
		renderRowExtraData(row);
		endTag();
		appendln();
		
		//render type selection
		renderMultipleSelection(row,rowClass);
		
		//render menu
		renderMenu(row,treeTableModel.getRootLevel() == level);
		
		//render cells
		//add one cell in case of an empty row
		if (row.getCellsCount() == 0)
		{
			row.addCell(new Cell("",""));	
		}
		
		ArrayList columnsDisplayOrder = model.getColumnsDisplayOrder();
		boolean isFirstCell = true;
		for (int cellIndex = 0; cellIndex < columnsDisplayOrder.size(); cellIndex++)
		{
			int currentCellIndex = Integer.parseInt((String)columnsDisplayOrder.get(cellIndex));
			Column column = model.getColumn(currentCellIndex);
			if (column.isDisplayable())
			{
				renderCell(row,currentCellIndex,rowClass,cellIndex == row.getCellsCount() - 1,isCellRowSelected,level,tableTreeNode,isFirstCell,column.isBreakable());
				isFirstCell = false;
			}
		}		
		for (int cellIndex = row.getCellsCount(); cellIndex < model.getColumnsCount(); cellIndex++)
		{
			renderEmptyCell();
		}
		endTag(ROW);
		appendln();
	}
	
	protected void renderMultipleSelectionEvent(Row row)
	{
		if (treeSelectionType == UIConstants.TREE_TABLE_SELECTION_TYPE_NONE)
		{
			super.renderMultipleSelectionEvent(row);
		}
		else
		{
			addAttribute(ONCLICK,getFunctionCall(JS_ROW_SELECT_SONS_EVENT,getSingleQuot(id) + COMMA + THIS + COMMA + treeSelectionType,false));
		}
	}
	
	protected void renderColumn(Column column,int columnIndex) throws UIException
	{
		startTag(CELL);
		renderCellWidth(columnIndex);
		renderStyles(getColumnHeaderClassName(column),getColumnHeaderStyle(column));		renderTooltip(column.getTooltip());
		endTag();
		if (!column.isBreakable()) startTag(NOBR,true);
		append(column.getHeader());
		if (!column.isBreakable()) endTag(NOBR);
		endTag(CELL);
	}
	
	protected void renderCell(Row row,int cellIndex,String className,boolean isLastCell,boolean isCellRowSelected,int level,TableTreeNode tableTreeNode,boolean isFirstCell,boolean isColumnBreakable) throws UIException
	{
		String cellClassName = renderCellStart(row,cellIndex,className,isLastCell,isCellRowSelected);
		
		//If the cell is the first one of the table tree,its rendering should
		//be different.
		if (isFirstCell)
		{
			String cssSuffix = isLastCell ? "" : CSS_SUFFIX_FIRST;
			if (isCellRowSelected)
			{
				cellClassName = cellClassName.substring(0,cellClassName.indexOf(BLANK)) + cssSuffix + BLANK + selectedRowClassName;
			}
			else
			{
				cellClassName += cssSuffix;
			}
			startTag(TABLE);
			addAttribute(CELLPADDING,"0");
			addAttribute(CELLSPACING,"0");
			endTagLn();
			startTagLn(ROW,true);
			startTagLn(CELL,true);
			renderNodeSigh(tableTreeNode,level,isColumnBreakable);
			endTagLn(CELL);
			startTag(CELL);
			addAttribute(CLASS,cellClassName);
			addAttribute(STYLE,getStyleAttribute(STYLE_VERTICAL_ALIGN,HTML_VALIGN_MIDDLE));
			endTag();
			renderFirstCellEnd(row,cellIndex,isCellRowSelected);
			endTag(CELL);
			endTag(ROW);
			endTag(TABLE);
			endTag(CELL);
			appendln();
		}
		else
		{
			renderCellEnd(row,cellIndex,isCellRowSelected,!isFirstCell);
		}
	}
	
	/**
	 * Renders the image in the first cell of the row
	 * @param tableTreeNode
	 * @param level
	 */
	private void renderNodeSigh(TableTreeNode tableTreeNode,int level,boolean isColumnBreakable)
	{
		append(getSpaces(((TreeTableModel)model).isShowRoot() ? level : level - 1));
		startTag(SPAN);
		addAttribute(ID,tableTreeNode.getPath());
		addAttribute("path",tableTreeNode.getPath());
		addAttribute(STYLE,CURSOR_HAND);
		addAttribute(ONCLICK);
		append(QUOT);
		append(CANCEL_BUBBLE);
		append(";");
		renderJsCallOpenTree(tableTreeNode.getPath());
		append(QUOT);
			
		String oImage = getNodeOpenImage(tableTreeNode,level);
		String cImage = getNodeCloseImage(tableTreeNode,level);
		String eImage = getNodeEmptyImage(tableTreeNode,level);
			
		if (tableTreeNode.hasChildren())
		{
			if (!cImage.equals(closeImage))
			{
				addAttribute("cImg",cImage);
			}
			if (!oImage.equals(openImage))
			{
				addAttribute("oImg",oImage);
			}				
		}
		endTag();
		renderNodeImage(tableTreeNode,oImage,cImage,eImage);
		endTagLn(SPAN);	
	}
	
	protected void renderFirstCellEnd(Row row,int cellIndex,boolean isCellRowSelected) throws UIException
	{
		Column column = model.getColumn(cellIndex);		
		Cell cell = row.getCell(cellIndex);
		Event cellEvent = getCellClickEvent(cell,cellIndex);
		String formattedData = model.getCellFormattedData(cell,getLocalizable());		
		renderCellData(row,column,cell,cellEvent,formattedData,isCellRowSelected);
	}	
	
	protected void renderSingleSelection(Row row,String path, boolean isFirstRow, String className, String style) throws UIException
	{
		if (model.isSelectionAllowed() && !model.isMultiple() && row.isSelectable())
		{
			renderSelectionScript(row, className, style);
			if (model.getSelectedRowId() != null && model.getSelectedRowId().equals(row.getId()))
			{
				isRowSelected = true;
				appendToEnd();
				startTag(SCRIPT);
				endTag();
				renderFunctionCall
				(
					JS_SELECT_ROW,
					id + COMMA + 
					PREFIX_ROW_ID + path + COMMA + 
					rowClassName + COMMA + 
					rowStyle
				);
				endTag(SCRIPT);
				appendToStart();
			}
		}
	}	
	protected void renderAdditionalFeatures()
	{
		//renderEndScript();
	}

	/*tree specific rendering*/
	private String getSpaces(int level)
	{
		StringBuffer sb = new StringBuffer(50);
		sb.append(getStartTag(SPAN));
		sb.append(getAttribute(ONCLICK, CANCEL_BUBBLE));
		sb.append(getEndTag());
		for (int i = 0; i < level; i++)
		{
			sb.append("&nbsp;&nbsp;&nbsp;");
		}
		sb.append(getEndTag(SPAN));
		return sb.toString();
	}
	
	private String getNodePath(TableTreeNode parent, int nodeOrder)
	{
		return parent.getPath() + "-" + nodeOrder;
	}

	protected void renderNodeImage(TableTreeNode treeNode,String openImageName,String closeImageName,String emptyImageName)
	{
		String imageName = null;
		if (!treeNode.hasChildren())
		{
			imageName = emptyImageName;
		}
		else
		{
			if (treeNode.isOpen())
			{
				imageName = openImageName;
			}
			else
			{
				imageName = closeImageName;
			}
		}
		startTag(IMAGE);
		addAttribute(SRC,imagesDir + imageName);
		endTag();	
	}
	
	private String getNodeOpenImage(TableTreeNode treeNode,int level)
	{
		if (treeNode.getOpenImage() != null && !treeNode.getOpenImage().equals(""))
		{
			return treeNode.getOpenImage();
		}
		else if (levelOpenImages != null && level < levelOpenImages.length && levelOpenImages[level] != null)
		{
			return levelOpenImages[level];
		}
		else
		{
			return openImage;
		}
	}
	
	private String getNodeCloseImage(TableTreeNode treeNode,int level)
	{
		if (treeNode.getCloseImage() != null && !treeNode.getCloseImage().equals(""))
		{
			return treeNode.getCloseImage();
		}
		else if (levelCloseImages != null && level < levelCloseImages.length && levelCloseImages[level] != null)
		{
			return levelCloseImages[level];
		}		
		else
		{
			return closeImage;
		}
	}
	
	private String getNodeEmptyImage(TableTreeNode treeNode,int level)
	{
		if (treeNode.getEmptyImage() != null && !treeNode.getEmptyImage().equals(""))
		{
			return treeNode.getEmptyImage();
		}
		else if (levelEmptyImages != null && level < levelEmptyImages.length && levelEmptyImages[level] != null)
		{
			return levelEmptyImages[level];
		}				
		else
		{
			return emptyImage;
		}
	}	
	
	/******************/


	
	private String getNodeSign(TableTreeNode tableTreeNode)
	{
		if (!tableTreeNode.hasChildren())
		{
			return getEmptyNodeRender(tableTreeNode);
		}
		else
		{
			if (tableTreeNode.isOpen())
			{
				return getOpenNodeRender(tableTreeNode);
			}
			else
			{
				return getClosedNodeRender(tableTreeNode);
			}
		}
	}
	
	private String getOpenNodeRender(TableTreeNode treeTableNode)
	{
		if (treeTableNode.getOpenImage() != null && !treeTableNode.getOpenImage().equals(""))
		{
			return (getStartTag(IMAGE) + getAttribute(SRC, imagesDir + treeTableNode.getOpenImage()) + getEndTag());
		}
		else
		{
			return (getStartTag(IMAGE) + getAttribute(SRC, imagesDir + openImage) + getEndTag());
		}
	}
	
	private String getClosedNodeRender(TableTreeNode treeTableNode)
	{
		if (treeTableNode.getCloseImage() != null && !treeTableNode.getCloseImage().equals(""))
		{
			return (getStartTag(IMAGE) + getAttribute(SRC, imagesDir + treeTableNode.getCloseImage()) + getEndTag());
		}
		else
		{
			return (getStartTag(IMAGE) + getAttribute(SRC,imagesDir + closeImage) + getEndTag());
		}
	}
	
	private String getEmptyNodeRender(TableTreeNode treeTableNode)
	{
		if (treeTableNode.getEmptyImage() != null && !treeTableNode.getEmptyImage().equals(""))
		{
			return (getStartTag(IMAGE) + getAttribute(SRC, imagesDir + treeTableNode.getEmptyImage()) + getEndTag());
		}
		else
		{
			return (getStartTag(IMAGE) + getAttribute(SRC, imagesDir + emptyImage) + getEndTag());
		}
	}
	
	/***************js functions calls***************/
	protected void renderJsCallOpenTree(String path)
	{
		renderFunctionCall(JS_OPEN_TREE,"this,true",false);
	}
		
	protected void renderJsCallOpenMenu(String rowId,String menuStr,String menuClassName)
	{
		renderFunctionCall(JS_OPEN_MENU,id + COMMA + rowId + COMMA + menuStr + COMMA + menuClassName + COMMA + true);
	}	
	/***************setters and getters****************/
	/**
	 * Returns the closeImage.
	 * @return String
	 */
	public String getCloseImage()
	{
		return closeImage;
	}
	/**
	 * Returns the openImage.
	 * @return String
	 */
	public String getOpenImage()
	{
		return openImage;
	}
	/**
	 * Sets the closeImage.
	 * @param closeImage The closeImage to set
	 */
	public void setCloseImage(String closeImage)
	{
		this.closeImage = closeImage;
	}
	/**
	 * Sets the openImage.
	 * @param openImage The openImage to set
	 */
	public void setOpenImage(String openImage)
	{
		this.openImage = openImage;
	}
	/**
	 * Returns the emptyImage.
	 * @return String
	 */
	public String getEmptyImage()
	{
		return emptyImage;
	}
	/**
	 * Sets the emptyImage.
	 * @param emptyImage The emptyImage to set
	 */
	public void setEmptyImage(String emptyImage)
	{
		this.emptyImage = emptyImage;
	}
	/**
	 * Returns the levelClassNames.
	 * @return String[]
	 */
	public String[] getLevelClassNames()
	{
		return levelClassNames;
	}
	/**
	 * Returns the levelStyles.
	 * @return String[]
	 */
	public String[] getLevelStyles()
	{
		return levelStyles;
	}
	/**
	 * Sets the levelClassNames.
	 * @param levelClassNames The levelClassNames to set
	 */
	public void setLevelClassNames(String[] levelClassNames)
	{
		this.levelClassNames = levelClassNames;
	}
	/**
	 * Sets the levelStyles.
	 * @param levelStyles The levelStyles to set
	 */
	public void setLevelStyles(String[] levelStyles)
	{
		this.levelStyles = levelStyles;
	}

	/**
	 * @return
	 */
	public int getTreeSelectionType() {
		return treeSelectionType;
	}

	/**
	 * @param i
	 */
	public void setTreeSelectionType(int i) {
		treeSelectionType = i;
	}

	/**
	 * @return
	 */
	public String getRowClassName() 
	{
		return rowClassName;
	}

	/**
	 * @param string
	 */
	public void setRowClassName(String string) 
	{
		rowClassName = string;
	}

	/**
	 * @param strings
	 */
	public void setLevelCloseImages(String[] strings) {
		levelCloseImages = strings;
	}

	/**
	 * @param strings
	 */
	public void setLevelEmptyImages(String[] strings) {
		levelEmptyImages = strings;
	}

	/**
	 * @param strings
	 */
	public void setLevelOpenImages(String[] strings) {
		levelOpenImages = strings;
	}

}
