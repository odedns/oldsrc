package com.ness.fw.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.ness.fw.common.exceptions.UIException;

public class CollapsibleModel extends AbstractModel
{	
	private HashMap collapsibleSections;
	
	/**
	 * Constant for "close" state of a section in the model.
	 */
	public final static int COLLAPSE_CLOSE = UIConstants.COLLAPSE_CLOSE;

	/**
	 * Constant for "open" state of a section in the model.
	 */
	public final static int COLLAPSE_OPEN = UIConstants.COLLAPSE_OPEN;
	
	private final static String COLLAPSIBLE_MODEL_OPEN_SECTIONS = "sections";
		
	/**
	 * Empty constructor for CollapsibleModel
	 */
	public CollapsibleModel()
	{
		collapsibleSections = new HashMap();
	}
	
	/**
	 * Opens and close sections in the model according to the information sent to 
	 * the server,
	 */
	protected void handleEvent(boolean checkAuthorization) throws UIException
	{
		ArrayList openSections = (ArrayList)getProperty(COLLAPSIBLE_MODEL_OPEN_SECTIONS);
		if (openSections != null)
		{
			HashMap openSectionsMap = new HashMap();
			for (int index = 0;index < openSections.size();index++)
			{
				openSectionsMap.put(openSections.get(index),"");
			}
			setOpenSections(openSectionsMap);
		}
	}
	
	/**
	 * Sets sections' state to "open", and other sections' state to "close".
	 * @param openSections map of open sections.The state of these sections 
	 * is set to "open",the state of all the other sections in the model is set
	 * to "close".
	 */
	protected void setOpenSections(HashMap openSections)
	{
		Iterator iter = collapsibleSections.keySet().iterator();
		while (iter.hasNext())
		{
			String sectionKey = (String)iter.next();
			if (openSections.containsKey(sectionKey))
			{
				setSectionState(Integer.parseInt(sectionKey),COLLAPSE_OPEN);	
			}
			else
			{
				setSectionState(Integer.parseInt(sectionKey),COLLAPSE_CLOSE);
			}
		}
	}
	
	/**
	 * Initializes sections in the model and sets their state to COLLAPSE_CLOSE.
	 * A section is initialized only if it does not already exist.
	 * @param sectionNumber the number of sections to initialize.Section 
	 * 0 to sectionsNumber-1 will be initialized.
	 */
	public void initializeSections(int sectionsNumber)
	{
		for (int sectionIndex = 0;sectionIndex < sectionsNumber;sectionIndex++)
		{
			CollapsibleSection section = (CollapsibleSection)collapsibleSections.get(String.valueOf(sectionIndex));
			if (section == null)
			{
				collapsibleSections.put(String.valueOf(sectionIndex),new CollapsibleSection(COLLAPSE_CLOSE,"",null,null,null,null));
			}
		}
	}
	
	/**
	 * Sets the title of a section,by the section's index.If the section does not
	 * exist in the model,a new entry for the section  is created.
	 * @param sectionIndex the index of the section in the model.
	 * @param title the new title of the section.
	 */
	public void setSectionTitle(int sectionIndex,String title)
	{
		CollapsibleSection section = (CollapsibleSection)collapsibleSections.get(String.valueOf(sectionIndex));
		if (section == null)
		{
			collapsibleSections.put(String.valueOf(sectionIndex),new CollapsibleSection(-1,title,null,null,null,null));
		}
		else
		{
			section.sectionTitle = title;
		}
	}
	
	/**
	 * Returns the title of a section,by the sections's index. 
	 * @param sectionIndex the index of the section in the model.
	 * @return The title of the section or null if the section does not exist or
	 * its title was not set.
	 */
	public String getSectionTitle(int sectionIndex)
	{
		CollapsibleSection section = (CollapsibleSection)collapsibleSections.get(String.valueOf(sectionIndex));
		if (section == null || section.sectionTitle == null)
		{
			return "";
		}
		else
		{
			return section.sectionTitle;
		}
	}
	
	/**
	 * Sets the height of a section,by the section's index.If the section does not
	 * exist in the model,a new entry for the section is created.
	 * @param sectionIndex the index of the section in the model.
	 * @param height the new height of the section.
	 */
	public void setSectionHeight(int sectionIndex,String height)
	{
		CollapsibleSection section = (CollapsibleSection)collapsibleSections.get(String.valueOf(sectionIndex));
		if (section == null)
		{
			collapsibleSections.put(String.valueOf(sectionIndex),new CollapsibleSection(-1,"",height,null,null,null));
		}
		else
		{
			section.sectionHeight = height;
		}
		
	}

	/**
	 * Returns the height of a section,by the sections's index. 
	 * @param sectionIndex the index of the section in the model.
	 * @return The height of the section or null if the section does not exist or
	 * its height was not set.
	 */
	public String getSectionHeight(int sectionIndex)
	{
		CollapsibleSection section = (CollapsibleSection)collapsibleSections.get(String.valueOf(sectionIndex));
		if (section == null || section.sectionHeight == null)
		{
			return null;
		}
		else
		{
			return section.sectionHeight;
		}
	}
	
	/**
	 * Sets the class name for the title of a section,by the section's index.If the section does not
	 * exist in the model,a new entry for the section is created.
	 * @param sectionIndex the index of the section in the model.
	 * @param titleClassName the new class name for the title of the section.
	 */
	public void setSectionTitleClassName(int sectionIndex,String titleClassName)
	{
		CollapsibleSection section = (CollapsibleSection)collapsibleSections.get(String.valueOf(sectionIndex));
		if (section == null)
		{
			collapsibleSections.put(String.valueOf(sectionIndex),new CollapsibleSection(-1,"",null,titleClassName,null,null));
		}
		else
		{
			section.sectionTitleClassName = titleClassName;
		}
	}
	
	/**
	 * Returns the class name for the title of a section,by the sections's index. 
	 * @param sectionIndex the index of the section in the model.
	 * @return The class name for the title of the section or null if the section does not exist or
	 * its title class name was not set.
	 */	
	public String getSectionTitleClassName(int sectionIndex)
	{
		CollapsibleSection section = (CollapsibleSection)collapsibleSections.get(String.valueOf(sectionIndex));
		if (section == null || section.sectionTitleClassName == null)
		{
			return null;
		}
		else
		{
			return section.sectionTitleClassName;
		}
	}

	/**
	 * Sets the state of a section,by the section's index.If the section does not
	 * exist in the model,a new entry for the section is created.The values
	 * of a state may be the constants COLLAPSE_CLOSE and COLLAPSE_OPEN.
	 * @param sectionIndex the index of the section in the model.
	 * @param state the new state of the section.
	 */
	public void setSectionState(int sectionIndex,int state)
	{
		CollapsibleSection section = (CollapsibleSection)collapsibleSections.get(String.valueOf(sectionIndex));
		if (section == null)
		{
			collapsibleSections.put(String.valueOf(sectionIndex),new CollapsibleSection(state,"",null,null,null,null));
		}
		else
		{
			section.state = state;
		}		
	}
	
	/**
	 * Returns the state of a section,by the sections's index. 
	 * @param sectionIndex the index of the section in the model.
	 * @return The state of the section or -1 if the section does not exist or
	 * its state was not set.
	 */
	public int getSectionState(int sectionIndex)
	{
		CollapsibleSection section = (CollapsibleSection)collapsibleSections.get(String.valueOf(sectionIndex));
		if (section == null || section.state == -1)
		{
			return -1;
		}
		else
		{
			return section.state;
		}		
	}
	
	
	/**
	 * Sets the "open" image of a section,by the section's index.If the section does not
	 * exist in the model,a new entry for the section  is created.
	 * @param sectionIndex the index of the section in the model.
	 * @param openImage the new openImage of the section.
	 */
	public void setSectionOpenImage(int sectionIndex,String openImage)
	{
		CollapsibleSection section = (CollapsibleSection)collapsibleSections.get(String.valueOf(sectionIndex));
		if (section == null)
		{
			collapsibleSections.put(String.valueOf(sectionIndex),new CollapsibleSection(-1,"",null,null,openImage,null));
		}
		else
		{
			section.sectionOpenImage = openImage;
		}			
	}

	/**
	 * Returns the open image of a section,by the sections's index. 
	 * @param sectionIndex the index of the section in the model.
	 * @return The open image of the section or null if the section does not exist or
	 * its open image was not set.
	 */
	public String getSectionOpenImage(int sectionIndex)
	{
		CollapsibleSection section = (CollapsibleSection)collapsibleSections.get(String.valueOf(sectionIndex));
		if (section == null || section.sectionOpenImage == null)
		{
			return null;
		}
		else
		{
			return section.sectionOpenImage;
		}
	}
	
	/**
	 * Sets the "close" image of a section,by the section's index.If the section does not
	 * exist in the model,a new entry for the section  is created.
	 * @param sectionIndex the index of the section in the model.
	 * @param closeImage the new closeImage of the section.
	 */
	public void setSectionCloseImage(int sectionIndex,String closeImage)
	{
		CollapsibleSection section = (CollapsibleSection)collapsibleSections.get(String.valueOf(sectionIndex));
		if (section == null)
		{
			collapsibleSections.put(String.valueOf(sectionIndex),new CollapsibleSection(-1,"",null,null,null,closeImage));
		}
		else
		{
			section.sectionCloseImage = closeImage;
		}					
	}
	
	/**
	 * Returns the close image of a section,by the sections's index. 
	 * @param sectionIndex the index of the section in the model.
	 * @return The close image of the section or null if the section does not exist or
	 * its close image was not set.
	 */	
	public String getSectionCloseImage(int sectionIndex)
	{
		CollapsibleSection section = (CollapsibleSection)collapsibleSections.get(String.valueOf(sectionIndex));
		if (section == null || section.sectionCloseImage == null)
		{
			return null;
		}
		else
		{
			return section.sectionCloseImage;
		}
	}
	
	
	class CollapsibleSection
	{
		private int state;
		private String sectionTitle = "";
		private String sectionHeight;
		private String sectionTitleClassName;
		private String sectionOpenImage;
		private String sectionCloseImage;
		
		CollapsibleSection(int state,String sectionTitle,String sectionHeight,String sectionTitleClassName,String sectionOpenImage,String sectionCloseImage)
		{
			this.state = state;
			this.sectionTitle = sectionTitle;
			this.sectionHeight = sectionHeight;
			this.sectionTitleClassName = sectionTitleClassName;
			this.sectionOpenImage = sectionOpenImage;
			this.sectionCloseImage = sectionCloseImage;
		}
	}
}
