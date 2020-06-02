/*
 * Created on: 02/11/2004
 * Author: baruch hizkya
 */
package com.ness.fw.job.util;

import java.util.*;
import org.w3c.dom.*;
import com.ness.fw.common.externalization.*;

/**
 * Used to keep DOM's by names of its nodes at first level. (used for fast access to all DOM's that contain specific tag. for example tag service)
 */
public class DOMRepository
{
	/**
	 * The logger context
	 */
	private String LOGGER_CONTEXT = "DOMRepository";

	/**
	 * map of DOM's
	 */
	private HashMap doms;

	/**
	 * Used to initialize the DOM Repository from files under the given root directory.
	 * @param confFilesRoot The root directory to load the files.
	 * @throws ExternalizationException
	 */
	public void initialize(String xml) throws XMLUtilException 
	{
		//creating doms
		doms = new HashMap();
		//creating document
		Document doc = XMLUtil.readXMLFromString(xml, false,true);
		//retrieve root element
		Element rootElement = doc.getDocumentElement();
		//retrieve list of child nodes
		NodeList nodes = rootElement.getChildNodes();
		//run over list of child nodes
        for (int j = 0; j < nodes.getLength(); j++)
        {
            //retrieve node name
            String nodeName = nodes.item(j).getNodeName();
            //retrieve list of nodes with the name from the map
            DOMList domList = (DOMList) doms.get(nodeName);
            if (domList == null)
            {
                //create list for the name
                domList = new DOMList();
                //add the list to the map
                doms.put(nodeName, domList);
            }
            //add document to the list
            domList.addDocument(doc);
        }
	}

	/**
	 * Used to retrieve list of DOM's that contain tag wit specified name
	 *
	 * @param tagName name of tag to be contained in the DOM
	 */
	public DOMList getDOMList(String tagName)
	{
		return (DOMList) doms.get(tagName);
	}
}
