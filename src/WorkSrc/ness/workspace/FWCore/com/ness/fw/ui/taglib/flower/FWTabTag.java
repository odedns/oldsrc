package com.ness.fw.ui.taglib.flower;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ness.fw.ui.events.Event;
import com.ness.fw.ui.taglib.UITag;
import com.ness.fw.ui.TabModel;
import com.ness.fw.ui.UIConstants;
import com.ness.fw.shared.ui.FlowerUIUtil;

import com.ness.fw.common.exceptions.ResourceException;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.resources.LocalizedResources;

import com.ness.fw.flower.core.*;
import com.ness.fw.flower.servlet.ControllerServlet;
import com.ness.fw.flower.util.ApplicationUtil;
import com.ness.fw.flower.util.PageElementAuthLevel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class FWTabTag extends UITag
{
	protected final static String FW_TAB_SWITCH_EVENT = "switchTab";
	protected final static String JS_SEND_EVENT_TAB = "sendEventTab";
	private final static String TAB_ORIENTATION_HORIZONTAL = UIConstants.TAB_ORIENTATION_HORIZONTAL;
	private final static String TAB_ORIENTATION_VERTICAL = UIConstants.TAB_ORIENTATION_VERTICAL;
	private final static String TAB_TYPE_TABS = UIConstants.TAB_TYPE_TABS;
	private final static String TAB_TYPE_MENU = UIConstants.TAB_TYPE_MENU;
	private final static String CSS_DIRECTION_SUFFIX = "_ltr";
	
	private TabModel tabModel = null;

	protected String hTabBodyClassName;
	protected String vTabBodyClassName;
	protected String mTabBodyClassName;
	
	protected String enableHTabClassName;
	protected String disableHTabClassName;
	protected String selectedHTabClassName;
	protected String gapHTabClassName;
	protected String hTabFillClassName;
	
	protected String enableHMenuTabClassName;
	protected String disableHMenuTabClassName;
	protected String selectedHMenuTabClassName;
	protected String gapHMenuTabClassName;

	protected String enableVMenuTabClassName;
	protected String disableVMenuTabClassName;
	protected String selectedVMenuTabClassName;
	protected String gapVMenuTabClassName;

	protected String enableVTabClassName; 
	protected String disableVTabClassName;
	protected String selectedVTabClassName;
	protected String gapVTabClassName ;
	protected String vTabFillClassName;
			
	private String vTabClassNameSuffix = "";
	
	protected String orientation = TAB_ORIENTATION_HORIZONTAL;
	protected String type = TAB_TYPE_TABS;
	private int dirtyAction = UIConstants.TAB_DIRTY_ACTION_NONE;
	
	private String includePage = "";
	
	protected void init() throws UIException
	{
		printStartOutput = false;
		initCss();
		if (id != null)
		{
			tabModel = (TabModel)FlowerUIUtil.getObjectFromContext(getHttpRequest(),id);
			if (tabModel == null)
			{
				throw new UIException("TabModel is null in tab tag with id " + id);
			}
		}
		initState();
		initDirtyAction();
	}
	
	private void initDirtyAction()
	{
		if (tabModel != null && tabModel.getDirtyAction() != UIConstants.TAB_DIRTY_ACTION_NONE)
		{
			dirtyAction = tabModel.getDirtyAction();
		}
		if (dirtyAction == UIConstants.TAB_DIRTY_ACTION_NONE)
		{
			if (type.equals(TAB_TYPE_TABS))
			{
				dirtyAction = UIConstants.TAB_DIRTY_ACTION_IGNORE;
			}
			else
			{
				dirtyAction = UIConstants.TAB_DIRTY_ACTION_WARNING;
			}
		}
	}

	protected void initCss()
	{
		hTabBodyClassName = initUIProperty(hTabBodyClassName,"ui.tabs.body.horizontal");
		vTabBodyClassName = initUIProperty(vTabBodyClassName,"ui.tabs.body.vertical");
		mTabBodyClassName = initUIProperty(mTabBodyClassName,"ui.tabs.body.menu");
		
		enableHTabClassName = initUIProperty(enableHTabClassName,"ui.tabs.horizontal.enabled");
		disableHTabClassName = initUIProperty(disableHTabClassName,"ui.tabs.horizontal.disabled");
		selectedHTabClassName = initUIProperty(selectedHTabClassName,"ui.tabs.horizontal.selected");
		
		enableHMenuTabClassName = initUIProperty(enableHMenuTabClassName,"ui.tabs.horizontal.menu.enabled");
		disableHMenuTabClassName = initUIProperty(disableHMenuTabClassName,"ui.tabs.horizontal.menu.disabled");
		selectedHMenuTabClassName = initUIProperty(selectedHMenuTabClassName,"ui.tabs.horizontal.menu.selected");

		enableVTabClassName = initUIProperty(enableVTabClassName,"ui.tabs.vertical.enabled");
		disableVTabClassName = initUIProperty(disableVTabClassName,"ui.tabs.vertical.disabled");
		selectedVTabClassName = initUIProperty(selectedVTabClassName,"ui.tabs.vertical.selected");
		
		enableVMenuTabClassName = initUIProperty(enableVMenuTabClassName,"ui.tabs.vertical.menu.enabled");
		disableVMenuTabClassName = initUIProperty(disableVMenuTabClassName,"ui.tabs.vertical.menu.disabled");
		selectedVMenuTabClassName = initUIProperty(selectedVMenuTabClassName,"ui.tabs.vertical.menu.selected");

		gapHTabClassName = initUIProperty(gapHTabClassName,"ui.tabs.horizontal.gap");
		hTabFillClassName = initUIProperty(hTabFillClassName,"ui.tabs.horizontal.fill");
		gapHMenuTabClassName = initUIProperty(gapHMenuTabClassName,"ui.tabs.horizontal.menu.gap");
		
		gapVTabClassName = initUIProperty(gapVTabClassName,"ui.tabs.vertical.gap");
		vTabFillClassName = initUIProperty(vTabFillClassName,"ui.tabs.vertical.fill");
		gapVMenuTabClassName = initUIProperty(gapVMenuTabClassName,"ui.tabs.vertical.menu.gap");
	
		//Change the classes that take the locale's direction into consideration
		String localeSuffix = getLocaleCssSuffix();
		selectedVTabClassName += localeSuffix;
		gapVTabClassName += localeSuffix;
		vTabFillClassName += localeSuffix;
		vTabBodyClassName += localeSuffix;
		enableVTabClassName += localeSuffix;
	}
	
	protected void renderStartTag() throws UIException
	{
		try 
		{
			renderTabsTable();
		}
		catch (ResourceException re)
		{
			throw new UIException(re);
		}
		catch (ContextException ce)
		{
			throw new UIException(ce);
		}
		catch (ServletException se)
		{
			throw new UIException(se);
		}		
		catch (IOException ie)
		{
			throw new UIException(ie);
		}		
	}
		
	private void renderTabsTable() throws ContextException, ResourceException, UIException, ServletException, IOException
	{
		startTag(TABLE);
		addAttribute(CELLPADDING,"0");
		addAttribute(CELLSPACING,"0");
		addAttribute(WIDTH,"100%");
		addAttribute(HEIGHT,"100%");		
		endTag();
		appendln();
		renderTabs();		
		endTag(TABLE);
		appendln();
	}
	
	private void renderTabs() throws ContextException, ResourceException, UIException, ServletException, IOException
	{
		ResultEvent resultEvent = FlowerUIUtil.getResultEvent(getHttpRequest()); 
		Flow tabFlow = null;
		FlowDefinition tabFlowDefinition = null;
		
		if (resultEvent != null)
		{
			tabFlow = resultEvent.getFlow();
			if (!tabFlow.isCurrentStateVisible())
			{
				throw new UIException("current state " + tabFlow.getCurrentState().getName() + " of tab flow " + tabFlow.getName() + " is not visible");
			}
			if (!isTabVisible(tabFlow.getCurrentState().getName()))
			{
				throw new UIException("current state " + tabFlow.getCurrentState().getName() + " of tab flow " + tabFlow.getName() + " is hidden by the tab model");
			}
			if (isTabDisabled(tabFlow.getCurrentState().getName()))
			{
				throw new UIException("current state " + tabFlow.getCurrentState().getName() + " of tab flow " + tabFlow.getName() + " is disabled by the tab model");
			}
			if (tabModel != null)
			{
				tabModel.setLastVisitedTab(tabFlow.getCurrentState().getName());
			}
			tabFlowDefinition = tabFlow.getFlowDefinition();			
		}
		
		if (isHorizontal())
		{
			if (isMenu())
			{
				renderHorizontalMenuTabs(tabFlow,tabFlowDefinition);	
			}
			else
			{	
				renderHorizontalTabs(tabFlow,tabFlowDefinition);
			}
		}
		else
		{
			if (isMenu())
			{
				renderVerticalMenuTabs(tabFlow,tabFlowDefinition);
			}
			else
			{
				renderVerticalTabs(tabFlow,tabFlowDefinition);
			}			
		}
	}
	
	private List getStates(Flow tabFlow) throws ServletException, IOException, ContextException, ResourceException
	{
		List list = new ArrayList();
		FlowStatesList flowStatesList = tabFlow.getVisibleFlowStatesList();
		int flowStateNumber = flowStatesList.getFlowStatesCount();
		HttpServletRequest request = getHttpRequest();
		int authLevel;
		for (int i = 0; i < flowStateNumber; i++)
		{
			FlowState flowState = flowStatesList.getFlowState(i);
				
			if (flowState.getType() == FlowState.STATE_TYPE_COMPLEX && 
				(flowState.isStateContainsPage() || flowState.isStateContainsSubFlow()))
			{
				authLevel = FlowerUIUtil.getStateAuthLevel(flowState.getAuthId(),request);
				if (authLevel != FlowerUIUtil.AUTH_LEVEL_NONE && isTabVisible(flowState.getName()))
				{
					list.add(flowState);
				}
			}
		}
		return list;
	}
	
	private boolean isTabVisible(String tabState)
	{
		return tabModel == null || !tabModel.isTabHidden(tabState);
	}
	
	private boolean isTabDisabled(String tabState)
	{
		return tabModel != null && tabModel.isTabDisabled(tabState);
	}
	
	/*********************render tabs - horizontal***************************/
	private void renderHorizontalTabs(Flow tabFlow,FlowDefinition tabFlowDefinition) throws ContextException, ResourceException, UIException, ServletException, IOException
	{
		startTag(ROW,true);
		appendln();
		startTag(CELL);
		addAttribute(HEIGHT,"1");
		endTag();
		appendln();
		
		startTag(TABLE);
		addAttribute(WIDTH,"100%");
		addAttribute(CELLPADDING,"0");
		addAttribute(CELLSPACING,"0");
		endTag();
		appendln();
		startTag(ROW,true);
		appendln();
		List list = getStates(tabFlow);
		int flowStateNumber = list.size();
		for (int i = 0; i < list.size(); i++)
		{
			FlowState flowState = (FlowState)list.get(i);	
			renderHorizontalTab(tabFlow,flowState);	
			renderHorizontalTabGap(i == flowStateNumber - 1);
		}
		endTag(ROW);
		appendln();
		endTag(TABLE);
		appendln();
		endTag(CELL);
		appendln();
		endTag(ROW);
		appendln();
		renderHorizontalTabInclude(tabFlow);
	}
		
	/*render one tab*/
	private void renderHorizontalTab(Flow tabFlow,FlowState flowState) throws ContextException, ResourceException, UIException
	{
		startTag(CELL);
		addAttribute(CLASS,getHorizontalTabClassByState(tabFlow,flowState));
		renderJsCallTabEvent(tabFlow,flowState);
		addAttribute(NOWRAP,NOWRAP);
		endTag();
		append(getTabData(tabFlow,flowState));
		endTag(CELL);
		appendln();
	}
	
	/*render gap*/
	private void renderHorizontalTabGap(boolean isLastTab)
	{
		if (isLastTab)
		{
			startTag(CELL);
			addAttribute(WIDTH,"100%");
			addAttribute(CLASS,hTabFillClassName);
			endTag();
			append(SPACE);
			endTag(CELL);
			appendln();
		}
		else
		{
			startTag(CELL);
			addAttribute(CLASS,gapHTabClassName);
			endTag();
			append(SPACE);
			endTag(CELL);
			appendln();
		}
	}
	
	/*horizontal tab include*/
	private void renderHorizontalTabInclude(Flow tabFlow) throws UIException, ServletException, IOException
	{
		startTag(ROW,true);
		appendln();
		startTag(CELL,true);
		appendln();
		startTag(DIV);
		addAttribute(CLASS,hTabBodyClassName);
		endTag();
		appendln();
		includeTabPage(tabFlow);
		appendToEnd();
		endTag(DIV);
		appendln();
		endTag(CELL);
		appendln();
		endTag(ROW);
		appendln();		
	}
	
	/*get horizontal tab class*/
	private String getHorizontalTabClassByState(Flow tabFlow,FlowState flowState)
	{
		if (tabFlow == null)
		{
			return enableHTabClassName;
		}
		else
		{
			if (flowState.getName().equals(tabFlow.getCurrentState().getName()))
			{
				return selectedHTabClassName;
			}
			else if (flowState.isReachableByFlowOnly() || isTabDisabled(flowState.getName()))
			{
				return disableHTabClassName;
			}
			else
			{
				return enableHTabClassName;
			}			
		}			
	}
		
	/*************************render tabs - horizontal menu*********************/
	private void renderHorizontalMenuTabs(Flow tabFlow,FlowDefinition tabFlowDefinition) throws ContextException, ResourceException, UIException, ServletException, IOException
	{
		startTag(ROW,true);
		appendln();
		startTag(CELL);
		addAttribute(HEIGHT,"1");
		endTag();
		appendln();

		startTag(TABLE);
		addAttribute(WIDTH,"100%");
		addAttribute(CELLPADDING,"0");
		addAttribute(CELLSPACING,"0");		
		endTag();
		appendln();
		startTag(ROW,true);
		appendln();
		
		if (tabFlow == null)
		{
			for (int i = 0; i < 4; i++)
			{
				renderHorizontalMenuTab(null,null);
				renderHorizontalMenuTabGap(i == 3);
			}
		}
		else
		{
			List list = getStates(tabFlow);
			int flowStateNumber = list.size();
			for (int i = 0; i < list.size(); i++)
			{
				FlowState flowState = (FlowState)list.get(i);	
				renderHorizontalMenuTab(tabFlow,flowState);	
				renderHorizontalMenuTabGap(i == flowStateNumber - 1);	
			}
		}		
		endTag(ROW);		
		endTag(TABLE);

		endTag(CELL);		
		endTag(ROW);
		renderHorizontalMenuTabInclude(tabFlow);
	}
	
	/*render one tab*/
	private void renderHorizontalMenuTab(Flow tabFlow,FlowState flowState) throws ContextException, ResourceException, UIException
	{
		startTag(CELL);
		addAttribute(NOWRAP,NOWRAP);
		endTag();
		startTag(DIV);
		addAttribute(CLASS,getHorizontalMenuTabClassByState(tabFlow,flowState));
		renderJsCallTabEvent(tabFlow,flowState);
		endTag();	
		append(getTabData(tabFlow,flowState));
		endTag(DIV);
		endTag(CELL);
		appendln();
	}
	
	/*render gap*/
	private void renderHorizontalMenuTabGap(boolean isLastTab)
	{
		if (isLastTab)
		{
			startTag(CELL);
			addAttribute(WIDTH,"100%");
			endTag();
			append(SPACE);
			endTag(CELL);
		}
		else
		{
			startTag(CELL);
			addAttribute(CLASS,gapHMenuTabClassName);
			endTag();
			append(SPACE);
			endTag(CELL);			
		}
	}
	
	/*horizontal menu  tab include*/
	private void renderHorizontalMenuTabInclude(Flow tabFlow) throws UIException, ServletException, IOException
	{
		startTag(ROW,true);
		appendln();
		startTag(CELL);
		addAttribute(CLASS,gapVMenuTabClassName);
		endTag();
		append(SPACE);
		endTag(CELL);
		appendln();
		endTag(ROW);
		appendln();
		startTag(ROW,true);
		startTag(CELL);
		addAttribute(HEIGHT,"100%");
		endTag();
		appendln();
		startTag(DIV);
		addAttribute(CLASS,mTabBodyClassName);
		endTag();
		appendln();
		includeTabPage(tabFlow);
		appendToEnd();
		endTag(DIV);
		appendln();
		endTag(CELL);
		appendln();
		endTag(ROW);
	}
	
	/*get horizontal menu tab class*/
	private String getHorizontalMenuTabClassByState(Flow tabFlow,FlowState flowState)
	{	
		if (tabFlow == null)
		{
			return enableHMenuTabClassName;
		}
		else
		{
			if (flowState.getName().equals(tabFlow.getCurrentState().getName()))
			{
				return selectedHMenuTabClassName;
			}
			else if (flowState.isReachableByFlowOnly() || isTabDisabled(flowState.getName()))
			{
				return disableHMenuTabClassName;
			}
			else
			{
				return enableHMenuTabClassName;
			}			
		}				
	}
	
	/******************************render tabs - orientation***********************8*/
	private void renderVerticalTabs(Flow tabFlow,FlowDefinition tabFlowDefinition) throws ContextException, ResourceException, UIException, ServletException, IOException
	{
		startTag(ROW,true);
		appendln();
		startTag(CELL);
		addAttribute(WIDTH,"1");
		endTag();
		appendln();
		
		startTag(TABLE);
		addAttribute(HEIGHT,"100%");
		addAttribute(CELLSPACING,"0");
		addAttribute(CELLPADDING,"0");
		endTag();
		if (tabFlow == null)
		{
			for (int i = 0; i < 4; i++)
			{
				renderVerticalTab(null,null);
				renderVerticalTabGap(i == 3);
			}
		}
		else
		{
			List list = getStates(tabFlow);
			int flowStateNumber = list.size();
			for (int i = 0; i < list.size(); i++)
			{
				FlowState flowState = (FlowState)list.get(i);	
				renderVerticalTab(tabFlow,flowState);	
				renderVerticalTabGap(i == flowStateNumber - 1);							
			}
		}				
		endTag(TABLE);
		
		endTag(CELL);	
		appendln();
		renderVerticalTabInclude(tabFlow);
		endTag(ROW);
	}
	
	/*render one tab*/
	private void renderVerticalTab(Flow tabFlow,FlowState flowState) throws ContextException, ResourceException, UIException
	{
		startTag(ROW,true);
		appendln();
		startTag(CELL);
		addAttribute(NOWRAP,NOWRAP);
		addAttribute(CLASS,getVerticalTabClassByState(tabFlow,flowState));
		renderJsCallTabEvent(tabFlow,flowState);
		endTag();
		append(getTabData(tabFlow,flowState));
		endTag(CELL);
		appendln();
		endTag(ROW);
		appendln();
	}	
	
	/*render gap*/
	private void renderVerticalTabGap(boolean isLastTab)
	{
		startTag(ROW,true);
		if (isLastTab)
		{
			startTag(CELL);
			addAttribute(HEIGHT,"100%");
			addAttribute(CLASS,vTabFillClassName + vTabClassNameSuffix);
			endTag();
			append(SPACE);
			endTag(CELL);
		}
		else
		{
			startTag(CELL);
			addAttribute(CLASS,gapVTabClassName + vTabClassNameSuffix);
			endTag();
			append(SPACE);
			endTag(CELL);
			appendln();
		}
		endTag(ROW);
	}	
	
	/*orientation tab include*/
	private void renderVerticalTabInclude(Flow tabFlow) throws UIException, ServletException, IOException
	{
		startTag(CELL);
		addAttribute(WIDTH,"100%");
		endTag();
		appendln();
		startTag(DIV);
		addAttribute(CLASS,vTabBodyClassName + vTabClassNameSuffix);
		endTag();
		appendln();
		includeTabPage(tabFlow);
		appendToEnd();
		appendln();
		endTag(DIV);
		appendln();
		endTag(CELL);
		appendln();
	}
	
	/*get orientation tab class*/
	private String getVerticalTabClassByState(Flow tabFlow,FlowState flowState)
	{
		if (tabFlow == null)
		{
			return enableVTabClassName;
		}
		else
		{
			if (flowState.getName().equals(tabFlow.getCurrentState().getName()))
			{
				return selectedVTabClassName + vTabClassNameSuffix;
			}
			else if (flowState.isReachableByFlowOnly() || isTabDisabled(flowState.getName()))
			{
				return disableVTabClassName;
			}
			else
			{
				return enableVTabClassName;
			}			
		}			
	}	
	
	/********************render tabs - orientation menu**********************/
	private void renderVerticalMenuTabs(Flow tabFlow,FlowDefinition tabFlowDefinition) throws ContextException, ResourceException, UIException, ServletException, IOException
	{
		startTag(CELL);
		addAttribute(WIDTH,"1");
		endTag();
		appendln();
		startTag(TABLE);
		addAttribute(HEIGHT,"100%");
		addAttribute(CELLSPACING,"0");
		addAttribute(CELLPADDING,"0");		
		endTag();
		if (tabFlow == null)
		{
			for (int i = 0; i < 4; i++)
			{
				renderVerticalMenuTab(null,null);
				renderVerticalMenuTabGap(i == 3);
			}
		}
		else
		{
			List list = getStates(tabFlow);
			int flowStateNumber = list.size();
			for (int i = 0; i < list.size(); i++)
			{
				FlowState flowState = (FlowState)list.get(i);	
				renderVerticalMenuTab(tabFlow,flowState);	
				renderVerticalMenuTabGap(i == flowStateNumber - 1);	
			}
		}						
		endTag(TABLE);				
		endTag(CELL);	
		appendln();
		renderVerticalMenuTabInclude(tabFlow);
		endTag(ROW);			
	}
	
	/*render one tab*/
	private void renderVerticalMenuTab(Flow tabFlow,FlowState flowState) throws ContextException, ResourceException, UIException
	{
		startTag(ROW,true);
		appendln();
		startTag(CELL);
		addAttribute(NOWRAP,NOWRAP);
		addAttribute(CLASS,getVerticalMenuTabClassByState(tabFlow,flowState));
		renderJsCallTabEvent(tabFlow,flowState);
		endTag();
		append(getTabData(tabFlow,flowState));
		endTag(CELL);
		appendln();
		endTag(ROW);
		appendln();
		
	}	
	
	/*render gap*/
	private void renderVerticalMenuTabGap(boolean isLastTab)
	{
		startTag(ROW,true);
		if (isLastTab)
		{
			startTag(CELL);
			addAttribute(HEIGHT,"100%");
			endTag();
			append(SPACE);
			endTag(CELL);
		}
		else
		{
			startTag(CELL);
			addAttribute(CLASS,gapVMenuTabClassName);
			endTag();
			append(SPACE);
			endTag(CELL);
			appendln();
		}
		endTag(ROW);
	}	
	
	/*orientation tab include*/
	private void renderVerticalMenuTabInclude(Flow tabFlow) throws UIException, ServletException, IOException
	{
		startTag(CELL);
		addAttribute(CLASS,gapHMenuTabClassName);
		endTag();
		append(SPACE);
		endTag(CELL);
		appendln();
		startTag(CELL);
		addAttribute(WIDTH,"100%");
		endTag();
		appendln();
		startTag(DIV);
		addAttribute(CLASS,mTabBodyClassName);
		endTag();
		appendln();
		includeTabPage(tabFlow);
		appendToEnd();
		endTag(DIV);
		appendln();
		endTag(CELL);
		appendln();		
	}
	
	/*get menu orientation tab class*/
	private String getVerticalMenuTabClassByState(Flow tabFlow,FlowState flowState)
	{
		if (tabFlow == null)
		{
			return enableVMenuTabClassName;
		}
		else
		{
			if (flowState.getName().equals(tabFlow.getCurrentState().getName()))
			{
				return selectedVMenuTabClassName;
			}
			else if (flowState.isReachableByFlowOnly() || isTabDisabled(flowState.getName()))
			{
				return disableVMenuTabClassName;
			}
			else
			{
				return enableVMenuTabClassName;
			}			
		}
	}	
	
	/********************common methods to all tabs type*******************/		
	/*get tab data*/
	private String getTabData(Flow tabFlow,FlowState flowState) throws ResourceException, ContextException
	{
		LocalizedResources localizable = ApplicationUtil.getLocalizedResources(tabFlow.getCurrentStateContext());
		return FlowerUIUtil.replaceDynamicParameters(localizable.getString(flowState.getName()),tabFlow.getCurrentStateContext());
	}
	
	/*set tab include page*/
	private void includeTabPage(Flow tabFlow) throws ServletException,IOException,UIException
	{
		PageElementAuthLevel pageElement = tabFlow.getCurrentStatePageElement();			
		if (pageElement == null || !pageElement.isContainsPage()) 
		{
			pageElement = getSubFlowsTabPage(tabFlow, FlowerUIUtil.getResultEvent(getHttpRequest())); 
		} 
		if (pageElement != null && pageElement.isContainsPage())
		{
			int authLevel = FlowerUIUtil.getAuthLevelInformation(getHttpRequest(),pageElement,"TabTag " + tabFlow.getCurrentState().getName());
			if (authLevel != FlowerUIUtil.AUTH_LEVEL_NONE)
			{
				String pageStr = ControllerServlet.getJspRootPath() + pageElement.getPage();
				printStartOutput();
				Logger.info("tab tag","sessionId[" + getHttpSession().getId() + "] include page=" + pageStr);
				pageContext.include(pageStr);									
			}
			FlowerUIUtil.removeAuthLevel(pageElement,getHttpRequest(),"TabTag " + tabFlow.getCurrentState().getName());
		}
	}
	
	private PageElementAuthLevel getSubFlowsTabPage(Flow flow,ResultEvent resultEvent)
	{
		Iterator subflowIdsIterator = flow.getHierarchySubFlowIdsIterator();
		PageElementAuthLevel pageElementAuthLevel = null;
		Flow subFlow;				
		while (subflowIdsIterator.hasNext())
		{
			subFlow = flow.getSubFlowById((String)subflowIdsIterator.next());
			ApplicationUtil.writeFlowInResultEvent(subFlow, resultEvent, true);
			if (subFlow.isContainsPage())
			{
				pageElementAuthLevel = subFlow.getPageElement();
			}
			else
			{
				pageElementAuthLevel = getSubFlowsTabPage(subFlow,resultEvent);
			}	
			if (pageElementAuthLevel != null)						
			{
				break;
			}
		}	
		return pageElementAuthLevel;	
	}
	
	
		
	/*renders tab event*/	
	protected void renderJsCallTabEvent(Flow tabFlow,FlowState flowState) throws UIException
	{
		if (tabFlow == null)
		{
			return;
		}
		else
		{
			if (!flowState.getName().equals(tabFlow.getCurrentState().getName()) && !flowState.isReachableByFlowOnly()  && !isTabDisabled(flowState.getName()))
			{
				addAuthorizedEvent(FW_TAB_SWITCH_EVENT,Event.EVENT_TARGET_TYPE_DEFAULT,false);
				boolean checkDirty = (dirtyAction == UIConstants.TAB_DIRTY_ACTION_WARNING 
									|| dirtyAction == UIConstants.TAB_DIRTY_ACTION_ERROR);
				boolean checkWarning = false;
				boolean allowEventOnDirty = dirtyAction != UIConstants.TAB_DIRTY_ACTION_ERROR;
				ArrayList jsParams = new ArrayList();
				jsParams.add(FW_TAB_SWITCH_EVENT);
				jsParams.add(tabFlow.getFlowPathString());
				jsParams.add(tabFlow.getCurrentState().getName());
				jsParams.add(flowState.getName());
				jsParams.add(String.valueOf(checkDirty));				
				jsParams.add(String.valueOf(checkWarning));
				jsParams.add("");
				jsParams.add(String.valueOf(allowEventOnDirty));
				
				addAttribute(ONCLICK,getFunctionCall(JS_SEND_EVENT_TAB,jsParams));
			}
		}
	}
	
	protected void resetTagState()
	{
		hTabBodyClassName = null;
		vTabBodyClassName = null;
		mTabBodyClassName = null;	
		enableHTabClassName = null;
		disableHTabClassName = null;
		selectedHTabClassName = null;
		gapHTabClassName = null;
		hTabFillClassName = null;	
		enableHMenuTabClassName = null;
		disableHMenuTabClassName = null;
		selectedHMenuTabClassName = null;
		gapHMenuTabClassName = null;
		enableVMenuTabClassName = null;
		disableVMenuTabClassName = null;
		selectedVMenuTabClassName = null;
		gapVMenuTabClassName = null;
		enableVTabClassName = null; 
		disableVTabClassName = null;
		selectedVTabClassName = null;
		gapVTabClassName  = null;
		vTabFillClassName = null;
		vTabClassNameSuffix = "";	
		orientation = TAB_ORIENTATION_HORIZONTAL;
		type = TAB_TYPE_TABS;
		dirtyAction = UIConstants.TAB_DIRTY_ACTION_NONE;	
		includePage = "";	
		tabModel = null;	
		super.resetTagState();	
	}

	protected void renderEndTag() throws UIException
	{
	}
	
	/**
	 * @return
	 */
	protected boolean isHorizontal()
	{
		return orientation.equals(TAB_ORIENTATION_HORIZONTAL);
	}

	/**
	 * @return
	 */
	protected boolean isMenu()
	{
		return type.equals(TAB_TYPE_MENU);
	}
	/**
	 * @return
	 */
	public String getOrientation()
	{
		return orientation;
	}

	/**
	 * @return
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @param string
	 */
	public void setOrientation(String string)
	{
		orientation = string;
	}

	/**
	 * @param string
	 */
	public void setType(String string)
	{
		type = string;
	}

	/**
	 * @return
	 */
	public String getDisableHMenuTabClassName()
	{
		return disableHMenuTabClassName;
	}

	/**
	 * @return
	 */
	public String getDisableHTabClassName()
	{
		return disableHTabClassName;
	}

	/**
	 * @return
	 */
	public String getDisableVTabClassName()
	{
		return disableVTabClassName;
	}

	/**
	 * @return
	 */
	public String getEnableHMenuTabClassName()
	{
		return enableHMenuTabClassName;
	}

	/**
	 * @return
	 */
	public String getEnableHTabClassName()
	{
		return enableHTabClassName;
	}

	/**
	 * @return
	 */
	public String getEnableVTabClassName()
	{
		return enableVTabClassName;
	}

	/**
	 * @return
	 */
	public String getGapHMenuTabClassName()
	{
		return gapHMenuTabClassName;
	}

	/**
	 * @return
	 */
	public String getGapHTabClassName()
	{
		return gapHTabClassName;
	}

	/**
	 * @return
	 */
	public String getGapVMenuTabClassName()
	{
		return gapVMenuTabClassName;
	}

	/**
	 * @return
	 */
	public String getGapVTabClassName()
	{
		return gapVTabClassName;
	}

	/**
	 * @return
	 */
	public String getHTabBodyClassName()
	{
		return hTabBodyClassName;
	}

	/**
	 * @return
	 */
	public String getHTabFillClassName()
	{
		return hTabFillClassName;
	}

	/**
	 * @return
	 */
	public String getMTabBodyClassName()
	{
		return mTabBodyClassName;
	}

	/**
	 * @return
	 */
	public String getSelectedHMenuTabClassName()
	{
		return selectedHMenuTabClassName;
	}

	/**
	 * @return
	 */
	public String getSelectedHTabClassName()
	{
		return selectedHTabClassName;
	}

	/**
	 * @return
	 */
	public String getSelectedVTabClassName()
	{
		return selectedVTabClassName;
	}

	/**
	 * @return
	 */
	public String getVTabBodyClassName()
	{
		return vTabBodyClassName;
	}

	/**
	 * @return
	 */
	public String getVTabFillClassName()
	{
		return vTabFillClassName;
	}

	/**
	 * @param string
	 */
	public void setDisableHMenuTabClassName(String string)
	{
		disableHMenuTabClassName = string;
	}

	/**
	 * @param string
	 */
	public void setDisableHTabClassName(String string)
	{
		disableHTabClassName = string;
	}

	/**
	 * @param string
	 */
	public void setDisableVTabClassName(String string)
	{
		disableVTabClassName = string;
	}

	/**
	 * @param string
	 */
	public void setEnableHMenuTabClassName(String string)
	{
		enableHMenuTabClassName = string;
	}

	/**
	 * @param string
	 */
	public void setEnableHTabClassName(String string)
	{
		enableHTabClassName = string;
	}

	/**
	 * @param string
	 */
	public void setEnableVTabClassName(String string)
	{
		enableVTabClassName = string;
	}

	/**
	 * @param string
	 */
	public void setGapHMenuTabClassName(String string)
	{
		gapHMenuTabClassName = string;
	}

	/**
	 * @param string
	 */
	public void setGapHTabClassName(String string)
	{
		gapHTabClassName = string;
	}

	/**
	 * @param string
	 */
	public void setGapVMenuTabClassName(String string)
	{
		gapVMenuTabClassName = string;
	}

	/**
	 * @param string
	 */
	public void setGapVTabClassName(String string)
	{
		gapVTabClassName = string;
	}

	/**
	 * @param string
	 */
	public void setHTabBodyClassName(String string)
	{
		hTabBodyClassName = string;
	}

	/**
	 * @param string
	 */
	public void setHTabFillClassName(String string)
	{
		hTabFillClassName = string;
	}

	/**
	 * @param string
	 */
	public void setMTabBodyClassName(String string)
	{
		mTabBodyClassName = string;
	}

	/**
	 * @param string
	 */
	public void setSelectedHMenuTabClassName(String string)
	{
		selectedHMenuTabClassName = string;
	}

	/**
	 * @param string
	 */
	public void setSelectedHTabClassName(String string)
	{
		selectedHTabClassName = string;
	}

	/**
	 * @param string
	 */
	public void setSelectedVTabClassName(String string)
	{
		selectedVTabClassName = string;
	}

	/**
	 * @param string
	 */
	public void setVTabBodyClassName(String string)
	{
		vTabBodyClassName = string;
	}

	/**
	 * @param string
	 */
	public void setVTabFillClassName(String string)
	{
		vTabFillClassName = string;
	}

	/**
	 * @return
	 */
	public String getDisableVMenuTabClassName() {
		return disableVMenuTabClassName;
	}

	/**
	 * @return
	 */
	public String getEnableVMenuTabClassName() {
		return enableVMenuTabClassName;
	}

	/**
	 * @return
	 */
	public String getSelectedVMenuTabClassName() {
		return selectedVMenuTabClassName;
	}

	/**
	 * @param string
	 */
	public void setDisableVMenuTabClassName(String string) {
		disableVMenuTabClassName = string;
	}

	/**
	 * @param string
	 */
	public void setEnableVMenuTabClassName(String string) {
		enableVMenuTabClassName = string;
	}

	/**
	 * @param string
	 */
	public void setSelectedVMenuTabClassName(String string) {
		selectedVMenuTabClassName = string;
	}	
	/**
	 * @param i
	 */
	public void setDirtyAction(int dirtyAction) 
	{
		this.dirtyAction = dirtyAction;
	}

}
