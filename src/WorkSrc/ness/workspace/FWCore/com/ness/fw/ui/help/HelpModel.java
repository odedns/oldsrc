/*
 * Created on: 10/03/2005
 * Author:  user name
 * @version $Id: HelpModel.java,v 1.6 2005/04/04 15:52:50 shay Exp $
 */
package com.ness.fw.ui.help;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.common.lock.SynchronizationLockException;
import com.ness.fw.common.lock.SynchronizationLockTimeOutException;
import com.ness.fw.ui.AbstractModel;

/**
 * @author srancus
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HelpModel extends AbstractModel
{
	protected static final String HELP_MODEL_CONTENT_PROPERTY = "content";
	protected static final String HELP_MODEL_LINKED_ITEMS_PROPERTY = "linkedItems";
	protected static final String HELP_MODEL_SELECTED_ITEM_PROPERTY = "selectedItem";
	protected static final String HELP_MODEL_EXPANDED_DIRECTORIES_PROPERTY = "expandedDirs";
	protected static final String HELP_MODEL_EXPANDED_DIRECTORY_PROPERTY = "expandedDir";
	
		
	protected static final String HELP_MODEL_EXPAND_NODE_EVENT_TYPE = "expand";
	protected static final String HELP_MODEL_SHOW_EVENT_TYPE = "show";
	protected static final String HELP_MODEL_OPEN_EVENT_TYPE = "open";
	protected static final String HELP_MODEL_NEW_EVENT_TYPE = "new";
	protected static final String HELP_MODEL_SAVE_EVENT_TYPE = "save";
	protected static final String HELP_MODEL_PREVIEW_EVENT_TYPE = "preview";
	protected static final String HELP_MODEL_CANCEL_PREVIEW_EVENT_TYPE = "cancelPreview";
	protected static final String HELP_MODEL_ADD_LINKS_EVENT_TYPE = "addLinks";
	protected static final String HELP_MODEL_REMOVE_LINKS_EVENT_TYPE = "remove";
	
	/**
	 * The directories and help items tree
	 */
	private HelpDirectory helpTree;
		
	/**
	 * The expanded directories in the tree
	 */
	private HashMap expandedDirectories = new HashMap();
	
	/**
	 * The id of the currenly displayed help item
	 */
	private String displayedHelpItemId;
	
	/**
	 * The HelpItem the currenly displayed help item
	 */
	private HelpItem displayedHelpItem;
	
	/**
	 * The title of the currenly displayed help item
	 */
	private String displayedHelpItemTitle;
	
	/**
	 * The content of the help item
	 */
	private String displayedHelpItemContent;
	
	/**
	 * The list of the currenly displayed help item'm linked items
	 */
	private ArrayList displayedHelpItemLinks;

	/**
	 * The list of the titles currenly displayed help item'm linked items
	 */
	private ArrayList displayedHelpItemLinksTitles;
	
	/**
	 * Indicates if the mode is edit mode(admin)
	 */
	private boolean editMode = false;
	
	/**
	 * Indicates if the mode is preview mode(admin)
	 */
	private boolean previewMode = false;
	
	/**
	 * Indicates if the currently displayed help item exists as a file
	 */
	private boolean fileExist = true;
	
	/**
	 * Indicates if the currently displayed help item is defined in the system.
	 */	
	private boolean helpItemExist = true;
	
	/**
	 * Indicates if the title of the currently displayed help item was saved<br>
	 * into a file
	 */
	private boolean titlesFileChanged = false;
	
	/**
	 * Indicates if there was a legal save action
	 */
	private boolean legalSaveAction = true;
	
	/**
	 * Indicates if there was a legal read action
	 */
	private boolean legalReadAction = true;
	
	/**
	 * 
	 */
	private Locale locale = null;
	
	/**
	 * Sets the help directories tree
	 * @param helpTree
	 */
	public void setHelpTree(HelpDirectory helpTree)
	{
		this.helpTree = helpTree;
	}
	
	/**
	 * Returns the help directories tree
	 * @return
	 */
	public HelpDirectory getHelpTree()
	{
		return helpTree;
	}
	
	public void setDisplayedHelpItem(String displayedHelpItemId)
	{
		this.displayedHelpItemId = displayedHelpItemId;
	}
		
	public void setDisplayedHelpItemTitle(String displayedHelpItemTitle)
	{
		this.displayedHelpItemTitle = displayedHelpItemTitle;
	}
	
	public String getDisplayedHelpItemTitle()
	{
		return displayedHelpItemTitle;
	}
	
	public String getDisplayedHelpItemId()
	{
		return displayedHelpItemId;
	}	
	
	public void setDisplayedHelpItemContent(String displayedHelpItemContent)
	{
		this.displayedHelpItemContent = displayedHelpItemContent;
	}
	
	public String getDisplayedHelpItemContent()
	{
		return displayedHelpItemContent;
	}	
		
	public ArrayList getDisplayedHelpItemLinks()
	{
		return displayedHelpItemLinks;
	}
	
	public ArrayList getDisplayedHelpItemLinksTitles()
	{
		return displayedHelpItemLinksTitles;
	}		
	
	public boolean isDirectoryExpanded(String helpDirectoryId)
	{
		return (expandedDirectories != null && expandedDirectories.get(helpDirectoryId) != null);
	}

	public void setEditMode(boolean editMode)
	{
		this.editMode = editMode;
	}
	
	public boolean isEditMode()
	{
		return editMode;
	}
	
	public boolean isPreviewMode()
	{
		return previewMode;
	}
	
	public boolean isFileExist()
	{
		return fileExist;
	}
	
	public boolean isHelpItemExist()
	{
		return helpItemExist;
	}
	
	public boolean isTitleFileChanged()
	{
		return titlesFileChanged;
	}
	
	public boolean isLegalSaveAction()
	{
		return legalSaveAction;
	}

	public boolean isLegalReadAction()
	{
		return legalReadAction;
	}
		
	public boolean isHelpTreeMultipleRoots()
	{
		return getHelpManager().isMultipleRoots();
	}
	
	public String getInitialModelInfo()
	{
		String initialInfo = null;
		if (displayedHelpItemId != null)
		{
			initialInfo = getHelpItemInitialInfo();
			if (legalReadAction && legalSaveAction)
			{
				if (previewMode)
				{
					initialInfo += getLinkedHelpItemsInitialInfo();
				}		
			}
			else
			{
				initialInfo += MODEL_EVENT_TYPE_PROPERTY + MODEL_KEY_VALUE_SEPERATOR + HELP_MODEL_OPEN_EVENT_TYPE + MODEL_PARAM_SEPERATOR;
			}
		}
		
		return initialInfo;
	}
	
	private String getHelpItemInitialInfo()
	{
		return HELP_MODEL_SELECTED_ITEM_PROPERTY + MODEL_KEY_VALUE_SEPERATOR + displayedHelpItemId + MODEL_PARAM_SEPERATOR;		
	}
	
	private String getLinkedHelpItemsInitialInfo()
	{
		StringBuffer sb = new StringBuffer();
		if (displayedHelpItemLinks.size() > 0)
		{
			sb.append(HELP_MODEL_LINKED_ITEMS_PROPERTY);
			sb.append(MODEL_KEY_VALUE_SEPERATOR);
			sb.append(MODEL_MULTI_VALUES_START);
		}
		for (int index = 0;index < displayedHelpItemLinks.size();index++)
		{
			sb.append((String)displayedHelpItemLinks.get(index));
			if (index != displayedHelpItemLinks.size() - 1)
			{
				sb.append(MODEL_MULTI_VALUES_SEPERATOR);
			}
		}
		if (displayedHelpItemLinks.size() > 0)
		{
			sb.append(MODEL_MULTI_VALUES_END);
			sb.append(MODEL_PARAM_SEPERATOR);
		}		
		return sb.toString();
	}
	
	public void setLocale(Locale locale)
	{
		this.locale = locale;
	}

	/**
	 * Returns the HelpManager instance
	 * @return
	 */
	private HelpManager getHelpManager()
	{
		return HelpManager.getInstance();
	}

	/**
	 * 
	 */
	protected void handleEvent(boolean checkAuthorization) throws UIException, AuthorizationException 
	{
		//Getting the event type
		String eventType = getEventTypeProperty();

		//Getting help tree from the HelpManager
		loadHelpTree();
		
		//Show help item
		if (eventType.equals(HELP_MODEL_SHOW_EVENT_TYPE))
		{
			//If a new help item was selected,reset the former help item's title,
			//content and linked items
			displayedHelpItemTitle = null;
			displayedHelpItemContent = null;
			removeProperty(HELP_MODEL_LINKED_ITEMS_PROPERTY);
			handleHelpItemGeneralEvent();	
			handleExpandedDirectories();					
		}	
		
		//Open help item
		if (eventType.equals(HELP_MODEL_OPEN_EVENT_TYPE))
		{
			handleHelpItemGeneralEvent();	
			handleExpandedDirectories();								
		}
		
		//Save event
		else if (eventType.equals(HELP_MODEL_SAVE_EVENT_TYPE))	
		{
			handleHelpItemGeneralEvent();						
			handleSave();
			handleExpandedDirectories();
		}
		
		//New help item event
		else if (eventType.equals(HELP_MODEL_NEW_EVENT_TYPE))
		{
			handleNewHelpItem();
			handleExpandedDirectories();
		}
		
		//Preview 
		else if (eventType.equals(HELP_MODEL_PREVIEW_EVENT_TYPE))
		{
			handleHelpItemGeneralEvent();
			handleExpandedDirectories();
			previewMode = true;
		}
		
		//Cancel Preview 
		else if (eventType.equals(HELP_MODEL_CANCEL_PREVIEW_EVENT_TYPE))
		{
			handleHelpItemGeneralEvent();
			handleExpandedDirectories();
			previewMode = false;
		}		
		
		//Expand node
		else if (eventType.equals(HELP_MODEL_EXPAND_NODE_EVENT_TYPE))
		{
			handleHelpItemGeneralEvent();
			handleExpandedDirectory();
		}
	}
	
	private void loadHelpTree() throws UIException
	{
		try
		{
			setHelpTree(getHelpManager().getHelpTree(locale));
		}
		catch (SynchronizationLockTimeOutException slte)
		{
			legalReadAction = false;
			return;
		}
		catch (SynchronizationLockException sle)
		{
			throw new UIException(sle);
		}		
	}
	
	private void handleHelpItemGeneralEvent() throws UIException
	{
		handleDisplayedHelpItem();
		if (helpItemExist)
		{
			handleHelpItemTitle();
			handleLinkedHelpItems();
			handleHelpItemContent();		
		}
	}
	
	private void handleSave() throws UIException
	{
		//Sets the new title of the help item 
		displayedHelpItem.setDescription(displayedHelpItemTitle);
		
		//Sets the new list of linked help items
		displayedHelpItem.setLinkedItems(displayedHelpItemLinks);
		
		//saves the tree,the title of the currently displayed help item
		//and the content of the currently displayed help item
		try 
		{
			getHelpManager().save(HelpManager.HELP_SAVE_ACTION_TYPE_ALL,displayedHelpItemId,displayedHelpItemContent);
			titlesFileChanged = true;
		}
		catch (SynchronizationLockTimeOutException slte)
		{
			legalSaveAction = false;
		}
		catch (SynchronizationLockException sle) 
		{
			throw new UIException(sle);
		}
	}
	
	private void handleNewHelpItem() throws UIException
	{
		handleDisplayedHelpItem();
		handleHelpItemTitle();
		handleLinkedHelpItems();		
		try
		{
			displayedHelpItemContent = getHelpManager().save(HelpManager.HELP_SAVE_ACTION_TYPE_NEW_FILE,displayedHelpItemId,null);
		}
		catch (SynchronizationLockTimeOutException slte)
		{
			legalSaveAction = false;		
		}
		catch (SynchronizationLockException sle) 
		{
			throw new UIException(sle);
		}		
	}
	
	/**
	 * Setting the currently displayed help item
	 */
	private void handleDisplayedHelpItem() throws UIException
	{
		//Setting the displayed help item
		setDisplayedHelpItem(getStringProperty(HELP_MODEL_SELECTED_ITEM_PROPERTY));
		
		//Getting the help item object from the help tree
		displayedHelpItem = getHelpManager().getHelpItem(displayedHelpItemId);
		
		//if the current help item does not exist in the system,throw UIException
		if (displayedHelpItem == null)
		{
			helpItemExist = false;
			//throw new UIException("help item " + displayedHelpItemId + " does not exist");		
		}
	}
	
	/**
	 * Setting the title of the currently displayed help item
	 */
	private void handleHelpItemTitle()
	{
		//If a new title was not set,use the title of the displayed help item		
		if (displayedHelpItemTitle == null)
		{
			  displayedHelpItemTitle = displayedHelpItem.getDescription();
		}		
	}
	
	/**
	 * Setting the list of linked help items of the currently displayed help item
	 */
	private void handleLinkedHelpItems()
	{
		//If new links were not set use the links of the displayed help item		
		if (getProperty(HELP_MODEL_LINKED_ITEMS_PROPERTY) != null)
		{
			displayedHelpItemLinks = getListProperty(HELP_MODEL_LINKED_ITEMS_PROPERTY);	
		}
		else
		{
			displayedHelpItemLinks = displayedHelpItem.getLinkedItems();
		}	
		displayedHelpItemLinksTitles = new ArrayList();
		if (displayedHelpItemLinks != null)
		{
			for (int index = 0;index < displayedHelpItemLinks.size();index++)
			{
				String linkedHelpItem = (String)displayedHelpItemLinks.get(index);
				displayedHelpItemLinksTitles.add(getHelpManager().getHelpItemTitle(linkedHelpItem));
			}
		}
	}
	
	/**
	 * Setting the list of expanded directories
	 */
	private void handleExpandedDirectories()
	{
		ArrayList directoriesIds = getListProperty(HELP_MODEL_EXPANDED_DIRECTORIES_PROPERTY);
		if (directoriesIds != null)
		{
			for (int index = 0;index < directoriesIds.size();index++)
			{
				expandedDirectories.put(directoriesIds.get(index),"");
			}
		}
		handelHelpItemExpandedDirectories();
	}
	
	/**
	 * Setting the list of expanded directories of the currently displayed help items 
	 */
	private void handelHelpItemExpandedDirectories()
	{
		if (displayedHelpItem != null)
		{
			ArrayList path = displayedHelpItem.getPath();
			for (int index = 0;index < path.size();index++)
			{
				expandedDirectories.put(((HelpTreeNode)path.get(index)).getId(),"");
			}
		}
	}
	
	private void handleExpandedDirectory() 
	{
		String expandedDir = getStringProperty(HELP_MODEL_EXPANDED_DIRECTORY_PROPERTY);
		if (expandedDir != null)
		{
			HelpTreeNode helpTreeNode = (HelpTreeNode)helpTree.findDescendantByKey(expandedDir,HelpTreeNode.MAX_RECURSION,false);			
			ArrayList path = helpTreeNode.getPath();
			for (int index = 0;index < path.size();index++)
			{
				expandedDirectories.put(((HelpTreeNode)path.get(index)).getId(),"");
			}	
			expandedDirectories.put(expandedDir,"");		
		}
	}
	
	/**
	 * Setting the content of the help item(html file)
	 * @throws UIException
	 */
	private void handleHelpItemContent() throws UIException
	{
		if (displayedHelpItemContent == null)
		{
			try 
			{
				displayedHelpItemContent = getHelpManager().getHelpItemContent(displayedHelpItemId);
			} 
			catch (SynchronizationLockTimeOutException slte)
			{
				legalReadAction = false;			
			}
			catch (SynchronizationLockException sle) 
			{
				throw new UIException(sle);
			}
			//File was not found
			if (displayedHelpItemContent == null)
			{
				fileExist = false;
			}
		}
	}
}
