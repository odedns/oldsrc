package com.ness.fw.ui.taglib;

import com.ness.fw.ui.CollapsibleModel;
import com.ness.fw.ui.UIConstants;
import com.ness.fw.common.exceptions.UIException;

/**
 * @author srancus
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CollapsibleSectionTag extends UITag
{
	private final static String HTML_SECTION_PREFIX = "sec:";
	
	private final static int DEFAULT_STATE = UIConstants.COLLAPSE_CLOSE;
	private final static String JS_COLLAPSE = "openCollapse";
	private final static String JS_COLLAPSE_EVENT = "collapseEvent";
	
	private String sectionTitle = "";
	private String sectionHeight;
	private String sectionOpenImage = "collapsibleSectionIcon.png";
	private String sectionCloseImage = "collapsibleSectionIcon2.png";
	private int sectionState = -1;
	private String sectionName;
	private int sectionIndex;

	private String sectionTitleClassName;
	private String sectionIconClassName;
	private String sectionContentClassName;
	private String sectionTopRowClassName;
	private String sectionBottomRowClassName;
	private String sectionGapClassName;
	
	private int sectionGap = UIConstants.GAP_TYPE_NONE;
	
	private CollapsibleModel collapsibleModel = null;

	protected void init() throws UIException
	{
		if (!(getParent() instanceof CollapsibleTag))
		{
			throw new UIException("collapsibleSection must be inside collapsible");
		}
		startTagReturnValue = EVAL_BODY_INCLUDE;
		unauthorizedMessage = "";
		sectionIndex = ((CollapsibleTag)getParent()).getCurrentRenderedSection();
		sectionName = getSectionName();
		collapsibleModel = ((CollapsibleTag)getParent()).getCollapsibleModel();
	}
	
	private String getSectionName()
	{
		CollapsibleTag parent = (CollapsibleTag)getParent();
		return parent.getName() + HTML_SECTION_PREFIX + sectionIndex;
	}
	
	private String getParentId()
	{
		return ((CollapsibleTag)getParent()).getId();
	}

	private String getParentName()
	{
		return ((CollapsibleTag)getParent()).getName();
	}
	
	private String getParentTitleClassName()
	{
		return ((CollapsibleTag)getParent()).getCollapsibleTitleClassName();
	}

	private String getParentIconClassName()
	{
		return ((CollapsibleTag)getParent()).getCollapsibleIconClassName();
	}
	
	private String getParentContentClassName()
	{
		return ((CollapsibleTag)getParent()).getCollapsibleContentClassName();
	}
	
	private String getParentTopRowClassName()
	{
		return ((CollapsibleTag)getParent()).getCollapsibleTopRowClassName();	
	}

	private String getParentBottomRowClassName()
	{
		return ((CollapsibleTag)getParent()).getCollapsibleBottomRowClassName();	
	}
	
	private String getParentOpenImage()
	{
		return ((CollapsibleTag)getParent()).getCollapsibleOpenImage();
	}
	
	private String getParentCloseImage()
	{
		return ((CollapsibleTag)getParent()).getCollapsibleCloseImage();
	}
	
	/* (non-Javadoc)
	 * @see com.ness.fw.ui.taglib.UITag#renderStartTag()
	 */
	protected void renderStartTag() throws UIException
	{
		initCss();
		if (sectionGap == UIConstants.GAP_TYPE_BEFORE || sectionGap == UIConstants.GAP_TYPE_BOTH)
		{
			renderCollapsibleGap();
		}
		renderCollapsibleTitle();
		renderCollapsibleStart();
	}

	protected void initCss() throws UIException
	{
		sectionGapClassName = initUIProperty(sectionGapClassName,"ui.collapsible.gap");
	}
	
	private void renderCollapsibleGap()
	{
		startTag(ROW);
		endTag();
		startTag(CELL);
		addAttribute(CLASS,sectionGapClassName);
		addAttribute(COLSPAN,"2");
		endTag();
		endTag(CELL);
		endTag(ROW);
		appendln();
	}

	private void renderCollapsibleTitle()
	{
		String openImage = getSectionOpenImage();
		String closeImage = getSectionCloseImage();
		startTag(ROW);
		//addAttribute(CLASS,getSectionTopRowClassName());
		endTag();
		appendln();
		
		startTag(CELL);
		addAttribute(CLASS,"collapsibleSectionHead");
		endTag();
		startTag(TABLE);
		addAttribute(CELLPADDING,"0");
		addAttribute(CELLSPACING,"0");
		addAttribute(WIDTH,"100%");
		endTag();
		startTag(ROW,true);
		
		startTag(CELL);
		addAttribute(CLASS,getSectionTitleClassName());
		addAttribute(WIDTH,"100%");
		endTag();
		append(getLocalizedText(getSectionTitle()));
		endTag(CELL);
		appendln();
		startTag(CELL);
		renderCollapseEvent(openImage,closeImage);
		if (openImage == null || closeImage == null)
		{
			addAttribute(CLASS,getSectionIconClassName());
		}
		addAttribute(STYLE,CURSOR_HAND + (openImage == null || closeImage == null ? ";" + WEBDINGS : ""));
		endTag();
		renderCollapsibleImage(openImage,closeImage);
		endTag(CELL);
		appendln();
		
		endTag(ROW);
		endTag(TABLE);
		endTag(CELL);
		
		endTag(ROW);
		appendln();		
	}
	
	private void renderCollapseEvent(String openImage,String closeImage)
	{
		addAttribute(ONCLICK);
		append(QUOT);
		renderFunctionCall(JS_COLLAPSE,getSingleQuot(sectionName) + COMMA +
										getSingleQuot(openImage != null ? openImage : "5") + COMMA +
										getSingleQuot(closeImage != null ? closeImage : "6") + COMMA +
										THIS,false);
		append(";");
		String parentId = getParentId();
		if (parentId != null)
		{
			renderFunctionCall(JS_COLLAPSE_EVENT,parentId + COMMA + getParentName(),true);
		}
		append(QUOT);
	}
	
	private void renderCollapsibleStart()
	{
		String height = getSectionHeight();
		startTag(ROW);
		addAttribute(CLASS,getSectionBottomRowClassName());
		addAttribute(ID,sectionName);	
		if (height != null)
		{
			addAttribute(HEIGHT,height);
		}
		if (getSectionState() == UIConstants.COLLAPSE_CLOSE)
		{
			addAttribute(STYLE,DISPLAY_NONE);
		}
		endTag();
		appendln();
		startTag(CELL);
		addAttribute(CLASS,getSectionContentClassName());
		addAttribute(VALIGN,TOP);
		addAttribute(COLSPAN,"2");
		endTag();
		appendln();
	}
	
	private void renderCollapsibleImage(String openImage,String closeImage)
	{
		if (openImage != null && closeImage != null)
		{
			startTag(IMAGE);
			addAttribute(SRC,getLocalizedImagesDir() + (getSectionState() == UIConstants.COLLAPSE_CLOSE ? closeImage : openImage));
			endTag();
		}
		else
		{
			append("6");
		}
	}
	
	private void renderCollapsibleEnd()
	{
		endTag(CELL);
		appendln();
		endTag(ROW);
		appendln();			
	}

	protected void resetTagState()
	{
		sectionTitle = "";
		sectionHeight = null;
		sectionOpenImage = null;
		sectionCloseImage = null;
		sectionState = -1;
		sectionName = null;
		sectionIndex = 0;

		sectionTitleClassName = null;
		sectionIconClassName = null;
		sectionContentClassName = null;
		sectionTopRowClassName = null;
		sectionBottomRowClassName = null;
		sectionGapClassName = null;
	
		sectionGap = UIConstants.GAP_TYPE_NONE;
	
		collapsibleModel = null;
		
		super.resetTagState();
	}

	/* (non-Javadoc)
	 * @see com.ness.fw.ui.taglib.UITag#renderEndTag()
	 */
	protected void renderEndTag() throws UIException
	{
		appendToEnd();
		renderCollapsibleEnd();
		if (sectionGap == UIConstants.GAP_TYPE_AFTER || sectionGap == UIConstants.GAP_TYPE_BOTH)
		{
			renderCollapsibleGap();
		}		
	}

	/**
	 * @return
	 */
	public String getSectionCloseImage()
	{
		String closeImage = sectionCloseImage;
		if (closeImage == null)
		{
			if (collapsibleModel != null)
			{
				closeImage = collapsibleModel.getSectionCloseImage(sectionIndex);
			}
			if (closeImage == null)
			{
				closeImage = getParentCloseImage();
			}
		}
		return closeImage;
	}

	/**
	 * @return
	 */
	public String getSectionOpenImage()
	{
		String openImage = sectionOpenImage;
		if (openImage == null)
		{
			if (collapsibleModel != null)
			{
				openImage = collapsibleModel.getSectionOpenImage(sectionIndex);
			}
			if (openImage == null)
			{
				openImage = getParentOpenImage();
			}
		}
		return openImage;
	}

	/**
	 * @return
	 */
	public String getSectionTitleClassName()
	{
		String titleClassName = sectionTitleClassName;
		if (sectionTitleClassName == null)
		{
			if (collapsibleModel != null)
			{
				titleClassName = collapsibleModel.getSectionTitleClassName(sectionIndex);
			}
			if (titleClassName == null)
			{
				titleClassName = getParentTitleClassName();
			}
		}
		return titleClassName;
	}

	/**
	 * @return
	 */
	public String getSectionTitle()
	{
		if (sectionTitle.equals("") && collapsibleModel != null)
		{
			return (collapsibleModel.getSectionTitle(sectionIndex));
		}
		return sectionTitle;
	}

	/**
	 * @param string
	 */
	public void setSectionCloseImage(String string)
	{
		sectionCloseImage = string;
	}

	/**
	 * @param string
	 */
	public void setSectionOpenImage(String string)
	{
		sectionOpenImage = string;
	}

	/**
	 * @param string
	 */
	public void setSectionTitle(String string)
	{
		sectionTitle = string;
	}

	/**
	 * @param string
	 */
	public void setSectionTitleClassName(String string)
	{
		sectionTitleClassName = string;
	}

	/**
	 * @return
	 */
	public int getSectionState()
	{
		int state = sectionState;
		if (collapsibleModel != null)
		{
			state = collapsibleModel.getSectionState(sectionIndex);
		}
		if (state == -1)
		{
			state = DEFAULT_STATE;
		}
		return state;
	}

	/**
	 * @param i
	 */
	public void setSectionState(int i)
	{
		sectionState = i;
	}

	/**
	 * @return
	 */
	public String getSectionHeight()
	{
		if (sectionHeight == null)
		{
			if (collapsibleModel != null)
			{
				return collapsibleModel.getSectionHeight(sectionIndex);
			}
		}
		return sectionHeight;
	}

	/**
	 * @param string
	 */
	public void setSectionHeight(String string)
	{
		sectionHeight = string;
	}

	/**
	 * @return
	 */
	public String getSectionIconClassName() 
	{
		return (sectionIconClassName != null ? sectionIconClassName : getParentIconClassName());
	}

	/**
	 * @param string
	 */
	public void setSectionIconClassName(String string) 
	{
		sectionIconClassName = string;
	}

	/**
	 * @return
	 */
	public String getSectionContentClassName() 
	{
		return (sectionContentClassName != null ? sectionContentClassName : getParentContentClassName());
	}

	/**
	 * @param string
	 */
	public void setSectionContentClassName(String string) 
	{
		sectionContentClassName = string;
	}

	/**
	 * @return
	 */
	public String getSectionBottomRowClassName() 
	{
		return (sectionBottomRowClassName != null ? sectionBottomRowClassName : getParentBottomRowClassName());
	}

	/**
	 * @return
	 */
	public String getSectionTopRowClassName() 
	{
		return (sectionTopRowClassName != null ? sectionTopRowClassName : getParentTopRowClassName());
	}

	/**
	 * @param string
	 */
	public void setSectionBottomRowClassName(String string) 
	{
		sectionBottomRowClassName = string;
	}

	/**
	 * @param string
	 */
	public void setSectionTopRowClassName(String string) 
	{
		sectionTopRowClassName = string;
	}

	/**
	 * @return
	 */
	public int getSectionGap() 
	{
		return sectionGap;
	}

	/**
	 * @param i
	 */
	public void setSectionGap(int i) 
	{
		sectionGap = i;
	}

	/**
	 * @return
	 */
	public String getSectionGapClassName() 
	{
		return sectionGapClassName;
	}

	/**
	 * @param string
	 */
	public void setSectionGapClassName(String string) 
	{
		sectionGapClassName = string;
	}

}
