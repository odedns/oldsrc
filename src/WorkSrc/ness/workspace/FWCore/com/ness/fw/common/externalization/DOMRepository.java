/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: DOMRepository.java,v 1.1 2005/02/21 15:07:14 baruch Exp $
 */
package com.ness.fw.common.externalization;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.resources.ResourceUtils;

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
	 * Used to retrieve list of XML files
	 * @param dir root directory to search files from
	 * @param filesList A list that holds the files, should add the new files to the list. 
	 */
	private void getFiles(File dir, ArrayList filesList) 
	{
		// if the file is directory, call the function recursivaly inorder to get
		// the subdirecroty files
		if (dir.isDirectory()) 
		{
			String[] children = dir.list(
			new FilenameFilter()
			{
				// anonymos class to filter only the xml files.
				public boolean accept(File dir, String name)
				{
					return new File(dir,name).isDirectory() || name.endsWith(".xml");
				}
			});
			for (int i=0; i<children.length; i++) 
			{
				// calling the function recursivaly for directiroes
				getFiles(new File(dir, children[i]), filesList);
			}
		}
		else
		{
			filesList.add(dir);
		}		
	}


	/**
	 * Used to initialize the DOM Repository from files under the given root directory.
	 * @param confFilesRoot The root directory to load the files. each rootPath should be an absolute path and not a relative one
	 * @throws ExternalizationException
	 */
	public void initialize(ArrayList confFilesRoots) throws ExternalizationException
	{
		//retrieve xml files list
		ArrayList directoryFiles = new ArrayList();
		ArrayList allFiles = new ArrayList();
		
		for (int i=0; i<confFilesRoots.size(); i++)
		{
			//creating file object
			String confFilesRoot = (String)confFilesRoots.get(i);

			// finding a resource through by using the class loader search resource.
			// it should get an absolute path and not a relative one

			//URL url = this.getClass().getClassLoader().getResource(confFilesRoot);
			URL url = null;
			try
			{
				url = ResourceUtils.getResource(confFilesRoot, false);
			}
			catch (IOException e)
			{
				Logger.debug(LOGGER_CONTEXT, "Initializing DOM Repository from [" + confFilesRoot + "] failed. Coudln't locate the path");
			}

			if (url != null)
			{
				String path = url.getPath();			
				Logger.debug(LOGGER_CONTEXT, "Initializing DOM Repository from [" + path + "]");
	
				File dir = new File(path);
				if (!dir.exists() || !dir.isDirectory())
				{
					Logger.fatal(LOGGER_CONTEXT, "The directory [" + path + "] not exists or not a directory. Initializing terminated.");
					throw new ExternalizationException("The directory [" + path + "] not exists or not a directory");
				}
		
				getFiles(dir, directoryFiles);
				allFiles.addAll(directoryFiles);	
				Logger.debug(LOGGER_CONTEXT, "Loading [" + directoryFiles.size() + "] files");
				directoryFiles.clear();
			}
		}

		//creating doms
		doms = new HashMap();
		for (int i = 0; i < allFiles.size(); i++)
		{
			String file = ((File)allFiles.get(i)).getPath();

			Logger.debug(LOGGER_CONTEXT, "Loading file [" + file + "]");
			try
			{
				//creating document
				Document doc = XMLUtil.readXML(file, true);
				//retrieve root element
				Element rootElement = doc.getDocumentElement();
				//retrieve list of child nodes
				NodeList nodes = rootElement.getChildNodes();
				//run over list of child nodes
				for (int j = 0; j < nodes.getLength(); j++)
				{
					//retrieve node name
					String nodeName = nodes.item(j).getNodeName();
//					Logger.debug(lc, "\tElement [" + nodeName + "] found");
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

			} catch (XMLUtilException e)
			{
				Logger.error(LOGGER_CONTEXT, "Loading file [" + file + "] failed. See Exception. Continue with other files.");
				Logger.error(LOGGER_CONTEXT, e);
			}
		}
	}

	/**
	 * Used to initialize the DOM Repository from files under the given root directory.
	 * @param confFilesRoot The root directory to load the files.
	 * @throws ExternalizationException
	 */
	public void initialize(String confFilesRoot) throws ExternalizationException
	{
		ArrayList roots = new ArrayList();
		roots.add(confFilesRoot);
		initialize(roots);
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
