/*
 * Created on: 08/03/2005
 * Author:  user name
 * @version $Id: HelpManager.java,v 1.3 2005/04/04 15:52:50 shay Exp $
 */
package com.ness.fw.ui.help;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.ness.fw.common.exceptions.ResourceException;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.common.externalization.XMLUtil;
import com.ness.fw.common.externalization.XMLUtilException;
import com.ness.fw.common.lock.MultipleReadersSingleWriterLock;
import com.ness.fw.common.lock.SynchronizationLockException;
import com.ness.fw.common.lock.SynchronizationLockTimeOutException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.resources.ResourceUtils;
import com.ness.fw.common.resources.SystemResources;
import com.ness.fw.flower.common.LanguagesManager;

/**
 * @author srancus
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HelpManager 
{
	private static final String XML_PROCESSING_INSTRUCTION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
	private static final String ATTR_ID = "id";
	private static final String ATTR_CAPTION = "caption";
	private static final String TAG_NAME_HELP_DIRECTORY = "helpDirectory";
	private static final String TAG_NAME_HELP_ITEM = "helpItem";
	private static final String TAG_NAME_LINKED_HELP_ITEM = "linkedHelpItem";
	private static final String TAG_NAME_ROOT_HELP_ITEMS = "helpItems";
	
	private static final String TAG_START = "<";
	private static final String TAG_START_SLASH = "</";
	private static final String TAG_END = ">\n";
	private static final String TAG_END_SLASH = "/>\n";
	private static final String TAB = "	";
	private static final String BLANK = " ";
	
	private final static String PROPERTY_NAME_SYSTEM_FILE_SEPERATOR = "file.separator";
	private static final String PROPERTY_KEY_HELP_MAIN_DIR = "ui.help.mainDir";
	private static final String PROPERTY_KEY_HELP_FILE_SUFFIX = "ui.help.fileSuffix";
	private static final String PROPERTY_KEY_HELP_TEMPLATE_FILE_NAME = "ui.help.templateFileName";
	private static final String PROPERTY_KEY_HELP_TITLES_FILE_NAME = "ui.help.titlesFileName";
	private static final String PROPERTY_KEY_HELP_TITLES_FILE_SUFFIX = "ui.help.titlesFileSuffix";
	private static final String PROPERTY_KEY_HELP_XML_FILE_NAME = "ui.help.xmlFileName";
	private static final String PROPERTY_KEY_HELP_XML_FILE_SUFFIX = "ui.help.xmlFileNameSuffix";
	
	protected static final String HELP_SAVE_ACTION_TYPE_ALL = "all";
	protected static final String HELP_SAVE_ACTION_TYPE_NEW_FILE = "new";
	
	private static final String HELP_TREE_VIRTUAL_ROOT_KEY = "vroot";
	private static final String HELP_TREE_VIRTUAL_ROOT_DESC = "vroot";
	
	private static HelpManager helpManager = null;
	
	private HelpDirectory helpTree; 
	
	private Properties helpItemTitles;
	
	private Locale locale;
	
	private boolean multipleRoots = false;
	
	/**
	 * The lock object that manage the concurrency synchronization.
	 */
	private static MultipleReadersSingleWriterLock globalLockObject = new MultipleReadersSingleWriterLock();


	/**
	 * Constructs a new HelpManager
	 *
	 */
	private HelpManager()
	{
	}
	
	/**
	 * Returns the HelpManager instance
	 * @return
	 */
	public static HelpManager getInstance()
	{
		if (helpManager == null)
		{
			helpManager = new HelpManager();
		}
		return helpManager;
	}
	
	/**
	 * Loads the help directories tree.
	 */
	private void loadHelpTree() throws UIException, SynchronizationLockException,SynchronizationLockTimeOutException
	{
		globalLockObject.getWriteLock();
		try 
		{
			//Read xml file with the definitions of the help directories tree
			Document document = XMLUtil.readXML(ResourceUtils.getResource(getXmlHelpFilePath()).getFile(),false);
					
			//Load the properties file with the help items titles
			helpItemTitles = ResourceUtils.load(getTitlesFilePath(false));
			
			//Get the root element of the xml file
			Element documentRootElement = (Element)(document.getFirstChild());
			
			//Get the direct children of the root
			NodeList nodeList = XMLUtil.getElementsByTagName(documentRootElement,TAG_NAME_HELP_DIRECTORY);
			
			//One root for the help tree
			if (nodeList.getLength() == 1)
			{
				Element helpDirectoryRoot = (Element)nodeList.item(0);
				helpTree = createNewHelpDirectory(helpDirectoryRoot);
				parseHelpDirectory(helpTree,helpDirectoryRoot);
			}
			
			//Multiple roots for the help tree
			else
			{
				//build virtual root
				buildVirtualRoot();
				parseHelpDirectory(helpTree,documentRootElement);
			}
		}
		catch (ResourceException re)
		{
			Logger.fatal("error in reading from " + getXmlHelpFilePath(),re);
			throw new UIException(re); 
		}
		
		catch (XMLUtilException xe) 
		{
			Logger.fatal("error in parsing  " + getXmlHelpFilePath(),xe);
			throw new UIException(xe);
		}
		
		catch (Throwable t)
		{
			Logger.fatal("error in loading help tree",t);
			throw new UIException(t);
		}
		finally
		{
			//Release the writer locking. other readers could start. 
			globalLockObject.releaseWriteLock();
		}
	}
	
	/* Saves the help tree.
	 * If saveActionType is HELP_SAVE_ACTION_TYPE_NEW_FILE creates new file<br>
	 * "helpItemId.html",writes into it default content and
	 */
	public String save(String saveActionType,String helpItemId,String helpItemContent) throws UIException,SynchronizationLockException,SynchronizationLockTimeOutException
	{
		globalLockObject.getWriteLock();
		try
		{
//			for (int j = 0;j < 7;j++)
//				for (int i = 0;i < 1000000000;i++);
			if (saveActionType.equals(HELP_SAVE_ACTION_TYPE_ALL))
			{
				//Write into the xml file	
				String xml = getTreeXml();
				FileOutputStream fos = new FileOutputStream(ResourceUtils.getFileFromResource(getXmlHelpFilePath()));
				fos.write(xml.getBytes());
		
				//Write into the titles properties file
				fos = new FileOutputStream(ResourceUtils.getFileFromResource(getTitlesFilePath(true)));
				helpItemTitles.store(fos,"");
				fos.close();
			
				//Writes into the html file
				fos = new FileOutputStream(new File(ResourceUtils.getFileFromResource(getHelpLocaleDirectory()),helpItemId + getHelpFileSuffix()));
				fos.write(helpItemContent.getBytes());
				fos.close();
			}
			else if (saveActionType.equals(HELP_SAVE_ACTION_TYPE_NEW_FILE))
			{
				//Creates new file : "helpItemId.html"
				File newFile = new File(ResourceUtils.getFileFromResource(getHelpLocaleDirectory()),helpItemId + getHelpFileSuffix());
				newFile.createNewFile();
			
				//Reads the default content from the file "helpTemplate.html"
				String template = getFileContent(getHelpLocaleDirectory(),getTemplateFileName());
				
				//Writes into the html file
				FileOutputStream fos = new FileOutputStream(new File(ResourceUtils.getFileFromResource(getHelpLocaleDirectory()),helpItemId + getHelpFileSuffix()));
				fos.write(template.getBytes());
				fos.close();
				helpItemContent = template;					
			}
			return helpItemContent;
		}
		catch (Throwable ex)		
		{
			Logger.fatal("HelpServlet","Failed to save help item " + helpItemId);	
			throw new UIException(ex);
		}
		finally
		{
			globalLockObject.releaseWriteLock();
		}
	}
	
	private String getTreeXml()
	{
		int tabLevel = 1;
		String xml = XML_PROCESSING_INSTRUCTION;
		xml += TAG_START + TAG_NAME_ROOT_HELP_ITEMS + TAG_END;
		xml += treeNodeToXml(helpTree,tabLevel);
		xml += TAG_START_SLASH + TAG_NAME_ROOT_HELP_ITEMS + TAG_END;
		return xml;		
	}
	
	/**
	 * Returns help tree node as xml
	 * @param helpTreeNode
	 * @param tabLevel
	 * @return
	 */
	private String treeNodeToXml(HelpTreeNode helpTreeNode,int tabLevel)
	{
		String xml = "";
		if (helpTreeNode.getType() == HelpConstants.HELP_ITEM_TYPE_DIRECTORY)
		{
			xml += buildDirectoryXmlStart((HelpDirectory)helpTreeNode,tabLevel);
			Iterator children = helpTreeNode.getChildDirectories();
			if (children != null)
			{
				while (children.hasNext())
				{
					xml += treeNodeToXml((HelpTreeNode)children.next(),tabLevel + 1);
				}
			}
			return xml + buildDirectoryXmlEnd(tabLevel);
		}
		else
		{
			HelpItem helpItem = (HelpItem)helpTreeNode;
			if (helpItem.getDescription() != null)
			{
				helpItemTitles.setProperty(helpItem.getId(),helpItem.getDescription());
			}
			return buildHelpItemXml(helpItem,tabLevel + 1);
		}
	}
	
	private String buildDirectoryXmlStart(HelpDirectory helpDirectory,int tabLevel)
	{
		return  buildSpace(tabLevel) + 
				TAG_START + TAG_NAME_HELP_DIRECTORY +
				buildAttribute(ATTR_ID,helpDirectory.getId()) + 
				buildAttribute(ATTR_CAPTION,helpDirectory.getDescription()) + 
				TAG_END;
	}
	
	private String buildDirectoryXmlEnd(int tabLevel)
	{
		return buildSpace(tabLevel) + TAG_START_SLASH + TAG_NAME_HELP_DIRECTORY + TAG_END;
	}	
	
	private String buildHelpItemXml(HelpItem helpItem,int tabLevel)
	{
		boolean hasLinkedItems = helpItem.getLinkedItemsNumber() > 0;
		String xml = 
				buildSpace(tabLevel) + 
				TAG_START + TAG_NAME_HELP_ITEM +
				buildAttribute(ATTR_ID,helpItem.getId()) + 
				(hasLinkedItems ? TAG_END : TAG_END_SLASH);
		for (int index = 0;index < helpItem.getLinkedItemsNumber();index++)
		{
			xml += buildLinkedItemXml(helpItem.getLinkedItemd(index),tabLevel + 1);
		}
		if (hasLinkedItems)
		{
			xml += buildSpace(tabLevel) + TAG_START_SLASH + TAG_NAME_HELP_ITEM + TAG_END;
		}
		return xml;	
	}
	
	private String buildLinkedItemXml(String itemId,int tabLevel)
	{
		return
			buildSpace(tabLevel) + 
			TAG_START + TAG_NAME_LINKED_HELP_ITEM + 
			buildAttribute(ATTR_ID,itemId) + TAG_END_SLASH;
	}
	
	private String buildAttribute(String name,String value)
	{
		return BLANK + name + "=" + "\"" + value + "\"";
	}
	
	private String buildSpace(int level)
	{
		String space = "";
		for (int index = 0;index < level;index++)
		{
			space += "	";
		}
		return space;
	}
	
	/**
	 * Creates a HelpDirectory object from an Element and adds it to the tree,
	 * as the child of another HelpDirectory object.
	 * @param helpDirectory
	 * @param helpDirectoryElement
	 */
	private void parseHelpDirectory(HelpDirectory helpDirectory,Element helpDirectoryElement)
	{
		NodeList nodeList = helpDirectoryElement.getChildNodes();
		for (int index = 0;index < nodeList.getLength();index++)
		{
			Element childElement = (Element)nodeList.item(index);
			if (childElement.getTagName().equals(TAG_NAME_HELP_DIRECTORY))
			{
				HelpDirectory childHelpDirectory = createNewHelpDirectory(childElement);
				childHelpDirectory.setOrder(index);
				helpDirectory.addChild(childHelpDirectory);
				childHelpDirectory.setParent(helpDirectory);
				parseHelpDirectory(childHelpDirectory,childElement);	
			}
			else if (childElement.getTagName().equals(TAG_NAME_HELP_ITEM))
			{
				HelpItem helpItem = createNewHelpItem(childElement);
				helpItem.setOrder(index);
				helpDirectory.addChild(helpItem);
				helpItem.setParent(helpDirectory);
			}
		}
	}
		
	/**
	 * Builds virtual root,in case of ,multiple roots.
	 */
	private void buildVirtualRoot()
	{
		multipleRoots = true;
		helpTree = new HelpDirectory(HELP_TREE_VIRTUAL_ROOT_KEY,HELP_TREE_VIRTUAL_ROOT_DESC);
	}
	
	/**
	 * Creates and returns new help directory by the xml Element
	 * @param helpDirectoryElement
	 * @return
	 */
	private HelpDirectory createNewHelpDirectory(Element helpDirectoryElement)
	{
		return new HelpDirectory(XMLUtil.getAttribute(helpDirectoryElement,ATTR_ID),XMLUtil.getAttribute(helpDirectoryElement,ATTR_CAPTION));
	}
	
	/**
	 * Creates and returns new help item by the xml Element
	 * @param helpItemElement
	 * @return
	 */
	private HelpItem createNewHelpItem(Element helpItemElement)
	{
		String helpItemId = XMLUtil.getAttribute(helpItemElement,ATTR_ID);
		HelpItem helpItem = new HelpItem(helpItemId,helpItemTitles.getProperty(helpItemId)); 
		NodeList linkedHelpItems = XMLUtil.getElementsByTagName(helpItemElement,TAG_NAME_LINKED_HELP_ITEM);
		for (int index = 0;index < linkedHelpItems.getLength();index++)
		{
			Element linkedHelpItem = (Element)linkedHelpItems.item(index);
			helpItem.addLinkedItem(XMLUtil.getAttribute(linkedHelpItem,ATTR_ID));
		}
		return helpItem;
	}
	
	/**
	 * Returns the help directories tree
	 * If helpTree is null,the tree is loaded. 
	 * @return
	 * @throws UIException
	 */
	public HelpDirectory getHelpTree(Locale locale) throws UIException,SynchronizationLockException,SynchronizationLockTimeOutException
	{
		if (helpTree == null)
		{
			this.locale = locale;
			loadHelpTree();
		}
		return helpTree;
	}
	
	/**
	 * Indicates if the help tree has multiple roots
	 * @return
	 */
	public boolean isMultipleRoots()
	{
		return multipleRoots;
	}
	
	/**
	 * 
	 * @param directoryId
	 * @param helpId
	 * @return
	 */
	public HelpItem getHelpItem(String helpId)
	{
		HelpItem helpItem = null;
		HelpTreeNode helpTreeNode = (HelpTreeNode)helpTree.findDescendantByKey(helpId,HelpItem.MAX_RECURSION,false);
		if (helpTreeNode != null && helpTreeNode.getType() == HelpConstants.HELP_ITEM_TYPE_FILE)
		{
			helpItem = (HelpItem)helpTreeNode;
		}
		return helpItem;
	}

	/**
	 * 
	 * @param helpItemId
	 * @return
	 */
	public String getHelpItemTitle(String helpItemId)
	{
		if (helpItemTitles == null)
		{
			return helpItemId;
		}
		return helpItemTitles.getProperty(helpItemId);
	}
	
	/**
	 * Returns the content of help item by its id.If the help item's file<br>
	 * does not exits the method returns null.
	 * @param helpItemId
	 * @return
	 * @throws UIException
	 */
	public String getHelpItemContent(String helpItemId) throws UIException,SynchronizationLockException,SynchronizationLockTimeOutException
	{
		globalLockObject.getReadLock();
		try
		{
			return getFileContent(getHelpLocaleDirectory(),helpItemId + getHelpFileSuffix());
		}
		catch (Throwable ex)
		{
			Logger.fatal("HelpServlet","Failed to tetrieve content of help item " + helpItemId + " " + ex.getMessage());	
			throw new UIException(ex.getMessage(),ex);
		}
		finally
		{
			globalLockObject.releaseReadLock();
		}
	}
	
	/**
	 * Returns file's content by the file directory anf file name
	 * @param fileDir
	 * @param fileName
	 * @return the content of the file or null if file does not exist
	 * @throws UIException
	 */
	public String getFileContent(String fileDir,String fileName) throws UIException
	{
		try
		{
			File file = new File(ResourceUtils.getFileFromResource(fileDir),fileName);
			if (file.exists())
			{
				StringBuffer fileContent = new StringBuffer("");
				byte b[] = new byte[1024];
				FileInputStream fis = new FileInputStream(file);
				int length;
				while ((length = fis.read(b)) != -1)
				{
					fileContent.append(new String(b,0,length));
				}
				fis.close();
				return fileContent.toString();
			}
			else
			{
				return null;
			}
		}
		catch (FileNotFoundException fe)
		{			
			throw new UIException("error in reading file " + fileDir + fileName,fe);
		}		
		catch (IOException io)
		{
			throw new UIException("error in reading " + fileDir + fileName,io);

		}	
		catch (ResourceException re)
		{
			throw new UIException("error in reading " + fileDir + fileName,re);
		}			
	}
		
	//e.g: /help/heb/
	private String getHelpLocaleDirectory()
	{
		return getHelpDirectory() + getLocaleDirectory();
	}
	
	//e.g: /help/ 
	private String getHelpDirectory()
	{
		return System.getProperty(PROPERTY_NAME_SYSTEM_FILE_SEPERATOR) 
				+ getSystemProperty(PROPERTY_KEY_HELP_MAIN_DIR) 
				+ System.getProperty(PROPERTY_NAME_SYSTEM_FILE_SEPERATOR);
	}
		
	//e.g: heb/ 	
	private String getLocaleDirectory()
	{
		return locale != null ? 
				LanguagesManager.getLanguageSet(locale).getImagesDirectoryName()
				+ System.getProperty(PROPERTY_NAME_SYSTEM_FILE_SEPERATOR) : "";
	}
	
	//e.g: /help/help.xml 
	private String getXmlHelpFilePath()
	{
		return getHelpDirectory() + getXmlFileName();
	}
	
	//e.g: /help/heb/helpTitles.properties 
	private String getTitlesFilePath(boolean addSuffix)
	{
		return getHelpLocaleDirectory() + getTitlesFileName(addSuffix);
	}
	
	//e.g: /help/help.xml
	private String getXmlFileName()
	{
		return getSystemProperty(PROPERTY_KEY_HELP_XML_FILE_NAME) + getSystemProperty(PROPERTY_KEY_HELP_XML_FILE_SUFFIX);
	}
	
	//e.g: helpTitles.properties
	private String getTitlesFileName(boolean addSuffix)
	{
		return getSystemProperty(PROPERTY_KEY_HELP_TITLES_FILE_NAME)
		+ (addSuffix ? getSystemProperty(PROPERTY_KEY_HELP_TITLES_FILE_SUFFIX) : "");
	}
	
	//e.g. helpTemplate.html
	private String getTemplateFileName()
	{
		return getSystemProperty(PROPERTY_KEY_HELP_TEMPLATE_FILE_NAME) + getHelpFileSuffix();
	}
	
	//e.g. .html
	private String getHelpFileSuffix()
	{
		return getSystemProperty(PROPERTY_KEY_HELP_FILE_SUFFIX);
	}
		
	private static String getSystemProperty(String key)
	{
		String systemProperty = SystemResources.getInstance().getProperty(key);
		if (systemProperty == null)
		{
			return key;
		}
		else
		{	
			return systemProperty;
		}
	}	
}