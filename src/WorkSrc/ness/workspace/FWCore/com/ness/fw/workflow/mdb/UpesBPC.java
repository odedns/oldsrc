/*
 * Created on 07/02/2005
 */
package com.ness.fw.workflow.mdb;

import java.util.ArrayList;
import java.util.HashMap;

import com.ness.fw.bl.BusinessProcessContainer;
import com.ness.fw.common.auth.UserAuthData;
import com.ness.fw.util.tree.Node;

/**
 * Container for the UPES data.
 * @author p0006404
 * @version $Id: UpesBPC.java,v 1.4 2005/03/14 15:59:25 amit Exp $
 */
public class UpesBPC extends BusinessProcessContainer {
	
	/**
	 * Replaces the XML-formatted input data in the message.
	 */
	private Node node;

	/**
	 * The relevant actImplCorrelId.
	 */
	private String actImplCorrelId;
	
	/**
	 * Name of the process which activated the UPES.
	 */
	private String processName;
	
	/**
	 * The process template name.
	 */
	private String processTemplateName;
		
	/**
	 * Constructor.
	 * @param node
	 * @param userAuthData
	 * @param processName
	 * @param processTemplateName
	 */
	public UpesBPC(Node node, UserAuthData userAuthData, String actImplCorrelId,
		String processName, String processTemplateName)
	{
		super (userAuthData);
		this.node = node;
		this.actImplCorrelId = actImplCorrelId;
		this.processName = processName;
		this.processTemplateName = processTemplateName;
	}
	
	/**
	 * Returns the encapsulated node.
	 * @return Node
	 */
	public Node getNode()
	{
		return node;
	}

	/**
	 * Returns the actImplCorrelId.
	 * @return actImplCorrelId.
	 */
	public String getActImplCorrelId()
	{
		return actImplCorrelId;
	}

	/**
	 * Returns the processName.
	 * @return processName.
	 */
	public String getProcessName()
	{
		return processName;
	}
	
	/**
	 * Returns the processTemplateName.
	 * @return processTemplateName.
	 */
	public String getProcessTemplateName()
	{
		return processTemplateName;
	}

	/**
	 * Retrieve an element from Node and return its value as String.
	 * In case the element does not exist in the Node, null is returned.
	 * @param elementName Name of the retrieved element.
	 * @return String the retrieved element' value.
	 */
	public String getInputElement(String elementName)
	{
		int maxRecursionDepth = 10;
		
		ArrayList nodesList = 
			node.findDescendantsByExtraDataKey(elementName, maxRecursionDepth, false);

		//The element must be unique in the Node for the search to work!
		if(nodesList.size()==0)
		{
			return null;
		}
		Node selectedNode = (Node)nodesList.get(0);
		HashMap map = selectedNode.getExtraData();
		Object element = map.get(elementName);
		if(element!=null)
		{
			return element.toString();
		} else {
			return null;
		}
		
	}

}
