package com.ness.fw.ui.taglib;

import com.ness.fw.shared.ui.FlowerUIUtil;
import com.ness.fw.ui.*;
import com.ness.fw.ui.events.Event;
import com.ness.fw.common.exceptions.UIException;

import java.util.Iterator;

public class TreeTag extends AbstractModelTag
{
	protected final static String JS_OPEN_TREE = "openTree";
	protected final static String JS_SELECT_TREE_NODE = "selectTreeNode";
	protected final static String JS_TREE_NODE_SUBMIT_EVENT_FUNCTION = "treeNodeSubmitEvent";
	protected final static String JS_TREE_NODE_EVENT_FUNCTION = "treeNodeEvent";
	protected final static String JS_TREE_OPEN_NODE_SUBMIT_EVENT_FUNCTION = "nodeOpenSubmitEvent";

	protected static String TREE_NODE_EVENT = "TreeEvent";
	protected static String TREE_EXPAND_NODE_EVENT = "TreeExpandEvent";
	
	protected final static String HTML_ROW_ATTRIBUTE_EXTRA_DATA = "ed";
	protected final static String HTML_TREE_NODE_PREFIX = "nodeDiv";
	protected final static String HTML_TREE_ATTRIBUTE_ROW_ID = "rid";
	protected final static String HTML_TREE_ATTRIBUTE_HASSONS = "hasSons";
	protected final static String HTML_TREE_ATTRIBUTE_SHOWSONS = "showSons";
	protected final static String HTML_TREE_ATTRIBUTE_LEVEL = "level";
	protected final static String HTML_TREE_ATTRIBUTE_PARENT_OPEN = "pOpen";
	protected final static String HTML_TREE_ATTRIBUTE_OPEN = "open";
	protected final static String HTML_TREE_ATTRIBUTE_COMPONENT_NAME = "treeComponent";
	protected final static String HTML_TREE_ATTRIBUTE_TABLE_SUFFIX = "Tree";

	private final static int NUMBER_OF_SPACES_IN_LEVEL = 6;	
	private final static String LEVEL_SPACE = SPACE;
	
	protected TreeModel treeModel;
	protected String[] nodeClassNames;
	protected String nodeClassName;
	protected String selectedNodeClassName;
	protected String selectedLinkedNodeClassName;
	protected String linkedNodeClassName;
	protected String treeClassName;
	protected String openImage;
	protected String closeImage;
	protected String emptyImage;
	protected String[] levelOpenImages;
	protected String[] levelCloseImages;
	protected String[] levelEmptyImages;
	protected boolean submitOnNodeSelection = true;
	protected int nodeOpenType = UIConstants.TREE_OPEN_NODE_TYPE_DEFAULT;
	protected int[] levelOpenTypes;
	protected String width;
	private String imagesDir;
		
	public void setTreeModel(TreeModel treeModel)
	{
		this.treeModel = treeModel;
	}
	
	protected void initModel() throws UIException
	{
		if (treeModel == null)
		{
			treeModel = (TreeModel)FlowerUIUtil.getObjectFromContext(getHttpRequest(),id);
		}
		if (treeModel == null)
		{
			throw new UIException("no tree model exists in context's field " + id);
		}
		
		//initialize the state of this tree tag
		initState();		
	}
		
	protected void setModelState()
	{
		if (treeModel.getState() != null)
		{
			if (isStricterState(treeModel.getState(),state))
			{
				state = treeModel.getState();
			}			
		}			
	}		
	
	protected void renderStartTag() throws UIException 
	{
		treeModel.setId(id);
		initCss();
		renderStartScript();
		renderDiv();
		renderTable();
		renderTree();
		renderHiddenField();
		treeModel.setAuthLevel(getAuthLevel());
	}
	
	protected void resetTagState()
	{
		treeModel = null;
		nodeClassNames = null;
		nodeClassName = null;
		selectedNodeClassName = null;
		selectedLinkedNodeClassName = null;
		linkedNodeClassName = null;
		treeClassName = null;
		openImage = null;
		closeImage = null;
		emptyImage = null;
		levelOpenImages = null;
		levelCloseImages = null;
		levelEmptyImages = null;
		submitOnNodeSelection = true;
		nodeOpenType = UIConstants.TREE_OPEN_NODE_TYPE_DEFAULT;
		levelOpenTypes = null;
		width = null;
		imagesDir = null;	
		dirtable = false;	
		super.resetTagState();		
	}
	
	protected void renderEndTag()
	{
		appendToEnd();
		endTag(TABLE);
		endTag(DIV);
	}
			
	protected void initCss()
	{
		treeClassName = initUIProperty(treeClassName,"ui.tree.mainDiv");
		nodeClassName = initUIProperty(nodeClassName,"ui.tree.node");
		linkedNodeClassName = initUIProperty(linkedNodeClassName,"ui.tree.node.linked");
		selectedNodeClassName = initUIProperty(selectedNodeClassName,"ui.tree.node.selected");
		selectedLinkedNodeClassName = initUIProperty(selectedLinkedNodeClassName,"ui.tree.node.selectedLinkedNode");
		openImage = initUIProperty(openImage,"ui.tree.image.open");
		closeImage = initUIProperty(closeImage,"ui.tree.image.close");
		emptyImage = initUIProperty(emptyImage,"ui.tree.image.empty");	
		imagesDir = getLocalizedImagesDir();		
	}
	
	protected void renderDiv()
	{
		startTag(DIV);
		addAttribute(ID,HTML_TREE_ATTRIBUTE_COMPONENT_NAME);
		addAttribute("model",id);
		addAttribute(CLASS,treeClassName);
		if (width != null)
		{
			addAttribute(STYLE,WIDTH + ":" + width);
		}
		if (isHidden())
		{
			addAttribute(STYLE,DISPLAY_NONE);
		}		
		endTag();
	}
	protected void renderTable()
	{
		startTag(TABLE);
		addAttribute(ID,treeModel.getId() + HTML_TREE_ATTRIBUTE_TABLE_SUFFIX);
		endTag();
	}
	
	protected void renderStartScript()
	{
		startTag(SCRIPT);
		endTag();
		append("openNodeSign['" + treeModel.getId() + "']=" + getSingleQuot(openImage));
		append(";");
		append("closeNodeSign['" + treeModel.getId() + "']=" + getSingleQuot(closeImage));
		endTag(SCRIPT);
	}
	
	/*render rows*/
	protected void renderTree() throws UIException
	{
		renderNode((TreeNode) treeModel.getRoot(), 0);
	}
	
	private void renderNode(TreeNode treeNode, int level) throws UIException 
	{
		if (treeNode.getPath() == null)
		{
			treeNode.setPath(treeModel.getId());
		}
		
		if (!(!treeModel.isShowRoot() && level == 0))
		{
			renderNodeRow(treeNode,level);
		}		

		for (int index = 0; index < treeNode.getChildrenNumber(); index++)
		{
			TreeNode son = (TreeNode) treeNode.getChild(index);
			son.setPath(getNodePath(treeNode, index));
			
			/*multiple roots*/
			if (treeModel.getRootLevel() == TreeModel.MULTIPLE_ROOT_TREE_LEVEL && level == 0)
			{
				son.setVisible(true);
			}
			else
			{
				son.setVisible(treeNode.isOpen() && treeNode.isVisible());
			}
			
			//son.setVisible(treeNode.isOpen() && treeNode.isVisible());
			renderNode(son, level + 1);
		}
	}
	
	protected void renderNodeRow(TreeNode treeNode,int level) throws UIException
	{
		boolean isOpen = treeNode.isOpen() && treeNode.hasChildren();
		String open = String.valueOf(isOpen);
		String pOpen = treeNode.getParent() == null ? "" : String.valueOf( ((TreeNode)treeNode.getParent()).isOpen());
		startTag(ROW);
		
		/*multiple roots*/
		if (!treeNode.isVisible() && level > treeModel.getRootLevel())
		{
			addAttribute(STYLE,DISPLAY_NONE);
		}
		
		addAttribute(VALIGN,TOP);
		addAttribute(ID, "row" + treeNode.getPath());
		addAttribute(HTML_TREE_ATTRIBUTE_ROW_ID,treeNode.getId());
		addAttribute(HTML_TREE_ATTRIBUTE_LEVEL,String.valueOf(level));
		addAttribute(HTML_TREE_ATTRIBUTE_HASSONS,String.valueOf(treeNode.hasChildren()));
		addAttribute(HTML_TREE_ATTRIBUTE_SHOWSONS,String.valueOf(treeNode.hasChildren() && treeNode.isOpen()));
		addAttribute(HTML_TREE_ATTRIBUTE_OPEN,open);
		addAttribute(HTML_TREE_ATTRIBUTE_PARENT_OPEN,pOpen);
		endTag();
		appendln();
		startTag(CELL);
		String currentNodeClassName = nodeClassName;
		if (nodeClassNames != null && level < nodeClassNames.length && nodeClassNames[level] != null)
		{
			currentNodeClassName = nodeClassNames[level];
		}
		endTag();
		
		//inner table
		startTag(TABLE);
		addAttribute(CELLPADDING,"0");
		addAttribute(CELLSPACING,"0");
		endTag();
		startTag(ROW,true);
		startTag(CELL);
		addAttribute(VALIGN,TOP);
		endTag();
		
		append(getSpaces(level));
		
		//renderNodeSign
		startTag(SPAN);
		addAttribute(ID,treeNode.getPath());
		addAttribute("path",treeNode.getPath());
		addAttribute(STYLE,CURSOR_HAND);
		
		//addAttribute(ONCLICK,getFunctionCall(JS_OPEN_TREE,THIS + COMMA + false,false));
		addAttribute(ONCLICK);
		append(QUOT);
		renderOpenNode(treeNode,level,treeNode.isOpen() && treeNode.hasChildren());
		append(QUOT);
		
		String oImage = getNodeOpenImage(treeNode,level);
		String cImage = getNodeCloseImage(treeNode,level);
		String eImage = getNodeEmptyImage(treeNode,level);
		if (treeNode.hasChildren())
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
		renderNodeImage(treeNode,oImage,cImage,eImage);
		endTag(SPAN);
		endTag(CELL);
		
		boolean isNodeLinked = treeNode.isLinkable() && treeNode.isSelectable() && treeNode.getTreeNodeClickEvent() != null;
		
		startTag(CELL);
		endTag();
		renderNodeText(treeNode,currentNodeClassName,level,isNodeLinked);
		
		renderNodeTextSelectionScript(treeNode,isNodeLinked);
		endTag(CELL);
		endTag(ROW);
		endTag(TABLE);

		endTag(CELL);
		appendln();
		endTag(ROW);
		appendln();		
	}
	
	protected void renderNodeText(TreeNode treeNode,String className,int level,boolean isNodeLinked) throws UIException
	{
		if (isNodeLinked)
		{
			renderNodeTextLink(treeNode,level);
		}
		else
		{
			renderNodeTextNormal(treeNode,level);
		}
	}
	
	protected void renderNodeTextLink(TreeNode treeNode,int level) throws UIException
	{
		startTag(LINK);
		addAttribute(ID,HTML_TREE_NODE_PREFIX + id + treeNode.getId());
		addAttribute(CLASS,treeNode.getLinkedNodeClassName() == null ? linkedNodeClassName : treeNode.getLinkedNodeClassName());
		if (isEventRendered(treeNode.getTreeNodeClickEvent()))
		{
			checkDirtyFlag(treeNode.getTreeNodeClickEvent());
			addAttribute(HREF);
			append(QUOT);
			append(JS_FUNCTION_CALL);
			renderJsCallLinkedTreeNodeSubmitEvent(treeNode);
			append(QUOT);
		}
		renderNodeExtraData(treeNode);
		addAttribute("level",String.valueOf(level));
		endTag();
		//startTag(NOBR,true);
		append(treeModel.getNodeFormattedData(treeNode,getLocalizable()));
		//endTag(NOBR);
		endTag(LINK);		
	}
	
	protected void renderNodeTextNormal(TreeNode treeNode,int level) throws UIException
	{
		startTag(SPAN);
		addAttribute(ID,HTML_TREE_NODE_PREFIX + id + treeNode.getId());
		if (nodeClassNames != null && level < nodeClassNames.length && nodeClassNames[level] != null)
		{
			addAttribute(CLASS,nodeClassNames[level]);
		}
		else
		{
			addAttribute(CLASS,nodeClassName);	
		}
		if (treeNode.isSelectable())
		{
			addAttribute(ONCLICK);
			append(QUOT);
			if (submitOnNodeSelection && isEventRendered(treeModel.getTreeNodeDefaultClickEvent()))
			{
				checkDirtyFlag(treeModel.getTreeNodeDefaultClickEvent());
				renderJsCallTreeNodeSubmitEvent(treeNode);
			}
			else
			{
				renderJsCallTreeNodeEvent(treeNode);
			}
			append(QUOT);
		}
		renderNodeExtraData(treeNode);
		addAttribute("level",String.valueOf(level));
		endTag();
		//startTag(NOBR,true);
		append(treeModel.getNodeFormattedData(treeNode,getLocalizable()));
		//endTag(NOBR);
		endTag(SPAN);		
	}
	
	protected void renderNodeTextSelectionScript(TreeNode treeNode,boolean isNodeLinked)
	{
		if (treeNode.isSelected())
		{
			startTag(SCRIPT,true);
			append(getFunctionCall(JS_SELECT_TREE_NODE,
									id + COMMA + 
									HTML_TREE_NODE_PREFIX + id + treeNode.getId() + COMMA + 
									(isNodeLinked ? selectedLinkedNodeClassName : selectedNodeClassName),
									true));
			endTag(SCRIPT);
		}		
	}
		
	protected void renderNodeExtraData(TreeNode treeNode)
	{
		StringBuffer extraData = new StringBuffer(50);
		Iterator extraDataKeyIterator = treeNode.getExtraDataKeysIterator();			
		while (extraDataKeyIterator.hasNext())
		{
			String key = (String)extraDataKeyIterator.next();
			String value = getFormattedHtmlValue(treeNode.getExtraData(key).toString());
			extraData.append(key + "-" + value + SEPERATOR);
		}
		if (extraData.length() > 0)
		{
			extraData.deleteCharAt(extraData.length() - 1);
		}		
		addAttribute(HTML_ROW_ATTRIBUTE_EXTRA_DATA, extraData.toString());
	}	
	
	private String getSpaces(int level)
	{
		StringBuffer sb = new StringBuffer(50);
		for (int i = 0; i < level; i++)
		{
			for (int j = 0;j < NUMBER_OF_SPACES_IN_LEVEL;j++)
			{
				sb.append(LEVEL_SPACE);
			}
		}
		return sb.toString();
	}
	
	private String getNodePath(TreeNode parent, int nodeOrder)
	{
		return parent.getPath() + "-" + nodeOrder;
	}

	protected void renderNodeImage(TreeNode treeNode,String openImageName,String closeImageName,String emptyImageName)
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
	
	private String getNodeOpenImage(TreeNode treeNode,int level)
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
	
	private String getNodeCloseImage(TreeNode treeNode,int level)
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
	
	private String getNodeEmptyImage(TreeNode treeNode,int level)
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
	
	private int getNodeOpenType(TreeNode treeNode,int level)
	{
		int openType = treeNode.getNodeOpenType(); 
		if (openType == 0)	
		{
			if (levelOpenTypes != null && levelOpenTypes.length > level && levelOpenTypes[level] != 0)
			{
				openType = levelOpenTypes[level];
			}
			else
			{
				openType = nodeOpenType;
			}			
		}
		return openType;
	}
	
	private Event getNodeEvent(TreeNode treeNode)
	{
		return (treeNode.getTreeNodeClickEvent() != null ? treeNode.getTreeNodeClickEvent() : treeModel.getTreeNodeDefaultClickEvent());
	}
	
	/********methods for initializing the hidden field of the tag**********/
	protected void renderHiddenField()
	{
		renderHidden(treeModel.getId() + AbstractModel.MODEL_EVENT_DATA_CONSTANT,"");
	}	
	
	/*js functions calls*/
	protected void renderOpenNode(TreeNode treeNode,int level,boolean isOpen) throws UIException
	{
		int openType = getNodeOpenType(treeNode,level);
		if (openType == UIConstants.TREE_OPEN_NODE_TYPE_DEFAULT)
		{
			renderOpenNodeEvent();
		}
		else if (openType == UIConstants.TREE_OPEN_NODE_TYPE_SUBMIT_FIRST_TIME)
		{
			if (treeNode.hasChildren())
			{
				renderOpenNodeEvent();
			}
			else
			{
				renderOpenNodeSubmitEvent();
			}
		}
		else if (openType == UIConstants.TREE_OPEN_NODE_TYPE_SUBMIT_ALWAYS)
		{
			renderOpenNodeSubmitEvent();
		}		
	}
	
	protected void renderOpenNodeEvent()
	{
		renderFunctionCall(JS_OPEN_TREE,THIS + COMMA + false,false);
	}
	
	protected void renderOpenNodeSubmitEvent() throws UIException
	{
		renderFunctionCall
		(
			JS_TREE_OPEN_NODE_SUBMIT_EVENT_FUNCTION,
			THIS + COMMA + 
			getSingleQuot(id) + COMMA + 
			getSingleQuot(TREE_EXPAND_NODE_EVENT) + COMMA + 
			"'x'" + COMMA + 
			"'x'" + COMMA + 
			true,
			false
		);
	}
	
	protected void renderJsCallTreeNodeEvent(TreeNode treeNode)
	{		
		renderFunctionCall
		(
			JS_TREE_NODE_EVENT_FUNCTION,
			treeModel.getId() + COMMA + 
			treeNode.getId() + COMMA + 
			selectedNodeClassName + COMMA + 
			dirtable
		);
	}
	
	protected void renderJsCallLinkedTreeNodeSubmitEvent(TreeNode treeNode) throws UIException
	{
		String eventName = treeNode.getTreeNodeClickEvent().getEventName() != null ? treeNode.getTreeNodeClickEvent().getEventName() : TREE_NODE_EVENT;
		renderFunctionCall
		(
			JS_TREE_NODE_SUBMIT_EVENT_FUNCTION,
			treeModel.getId() + COMMA + 
			treeNode.getId() + COMMA + 
			eventName + COMMA +  
			treeNode.getTreeNodeClickEvent().getEventTargetType() + COMMA + 
			treeNode.getTreeNodeClickEvent().getEventTargetName() + COMMA + 
			treeNode.getTreeNodeClickEvent().isCheckDirty() + COMMA + 
			dirtable + COMMA + 
			treeNode.getTreeNodeClickEvent().isCheckWarnings()
		);		
	}
	
	protected void renderJsCallTreeNodeSubmitEvent(TreeNode treeNode) throws UIException
	{			
		renderFunctionCall
		(
			JS_TREE_NODE_SUBMIT_EVENT_FUNCTION,
			treeModel.getId() + COMMA + 
			treeNode.getId() + COMMA + 
			TREE_NODE_EVENT + 
			treeModel.getTreeNodeDefaultClickEvent().getEventTargetType() + COMMA + 
			treeModel.getTreeNodeDefaultClickEvent().getEventTargetName() + COMMA + 
			treeModel.getTreeNodeDefaultClickEvent().isCheckDirty() + COMMA +
			dirtable + COMMA + 
			treeNode.getTreeNodeClickEvent().isCheckWarnings()
		);
	}
	
	/**
	 * Returns the closeImage.
	 * @return String
	 */
	public String getCloseImage()
	{
		return closeImage;
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
	 * Sets the emptyImage.
	 * @param emptyImage The emptyImage to set
	 */
	public void setEmptyImage(String emptyImage)
	{
		this.emptyImage = emptyImage;
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
	 * Returns the nodeClassName.
	 * @return String
	 */
	public String getNodeClassName()
	{
		return nodeClassName;
	}
	
	/**
	 * Sets the nodeClassName.
	 * @param nodeClassName The nodeClassName to set
	 */
	public void setNodeClassName(String nodeClassName)
	{
		this.nodeClassName = nodeClassName;
	}
	
	/**
	 * Returns the treeClassName.
	 * @return String
	 */
	public String getTreeClassName()
	{
		return treeClassName;
	}
	
	/**
	 * Sets the treeClassName.
	 * @param treeClassName The treeClassName to set
	 */
	public void setTreeClassName(String treeClassName)
	{
		this.treeClassName = treeClassName;
	}
	
	/**
	 * Returns the selectedNodeClassName.
	 * @return String
	 */
	public String getSelectedNodeClassName()
	{
		return selectedNodeClassName;
	}
	
	/**
	 * Sets the selectedNodeClassName.
	 * @param selectedNodeClassName The selectedNodeClassName to set
	 */
	public void setSelectedNodeClassName(String selectedNodeClassName)
	{
		this.selectedNodeClassName = selectedNodeClassName;
	}

	/**
	 * @return
	 */
	public String[] getNodeClassNames()
	{
		return nodeClassNames;
	}

	/**
	 * @param strings
	 */
	public void setNodeClassNames(String[] strings)
	{
		nodeClassNames = strings;
	}

	/**
	 * @return
	 */
	public boolean isSubmitOnNodeSelection()
	{
		return submitOnNodeSelection;
	}

	/**
	 * @param b
	 */
	public void setSubmitOnNodeSelection(boolean b)
	{
		submitOnNodeSelection = b;
	}

	/**
	 * @return
	 */
	public String getWidth()
	{
		return width;
	}

	/**
	 * @param string
	 */
	public void setWidth(String string)
	{
		width = string;
	}

	/**
	 * @return
	 */
	public String getLinkedNodeClassName()
	{
		return linkedNodeClassName;
	}

	/**
	 * @return
	 */
	public String getSelectedLinkedNodeClassName()
	{
		return selectedLinkedNodeClassName;
	}

	/**
	 * @param string
	 */
	public void setLinkedNodeClassName(String string)
	{
		linkedNodeClassName = string;
	}

	/**
	 * @param string
	 */
	public void setSelectedLinkedNodeClassName(String string)
	{
		selectedLinkedNodeClassName = string;
	}

	/**
	 * @param strings
	 */
	public void setLevelOpenImages(String[] strings)
	{
		levelOpenImages = strings;
	}

	/**
	 * @param strings
	 */
	public void setLevelCloseImages(String[] strings)
	{
		levelCloseImages = strings;
	}

	/**
	 * @param strings
	 */
	public void setLevelEmptyImages(String[] strings)
	{
		levelEmptyImages = strings;
	}

	/**
	 * @return
	 */
	public int getNodeOpenType() 
	{
		return nodeOpenType;
	}

	/**
	 * @param nodeOpenType
	 */
	public void setNodeOpenType(int nodeOpenType) 
	{
		this.nodeOpenType = nodeOpenType;
	}

	/**
	 * @return
	 */
	public int[] getLevelOpenTypes() 
	{
		return levelOpenTypes;
	}

	/**
	 * @param is
	 */
	public void setLevelOpenTypes(int[] is) 
	{
		levelOpenTypes = is;
	}

}
