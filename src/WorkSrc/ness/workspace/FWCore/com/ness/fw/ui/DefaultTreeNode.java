package com.ness.fw.ui;

public class DefaultTreeNode extends AbstractTreeNode
{
	/**
	 * Indicates if this node is in "open" state
	 */
	protected boolean open = false;

	/**
	 * Indicates if this node is in "visible" state
	 */
	protected boolean visible = true;

	/**
	 * Indicates if this node is in "selected" state
	 */
	protected boolean selected = false;

	/**
	 * Indicates if this node is allowed to be selected
	 */
	protected boolean selectable = true;

	/**
	 * indicates if this node is allowed to be linked
	 */
	protected boolean linkable = true;	
	
	/**
	 * the path to this node in the tree
	 */
	protected String path;
	
	/**
	 * string representing the children of this node
	 */
	protected String childrenPath = ""; 
	
	/**
	 * image for open state
	 */
	protected String openImage;

	/**
	 * image for close state
	 */
	protected String closeImage;

	/**
	 * image for empty state
	 */
	protected String emptyImage;
	
	
	/**
	 * @see com.ness.fw.ui.AbstractTreeNode#getChild(int)
	 */
	public AbstractTreeNode getChild(int index)
	{
		return (DefaultTreeNode)nodes.get(index);
	}
	
	
	/**
	 * @see com.ness.fw.ui.AbstractTreeNode#getParent()
	 */
	public AbstractTreeNode getParent()
	{	
		return (DefaultTreeNode)parent;
	}
	
	/**
	 * @see com.ness.fw.ui.AbstractTreeNode#setParent(AbstractTreeNode)
	 */
	public void setParent(AbstractTreeNode parent)
	{
		this.parent = (DefaultTreeNode)parent;
	}
		
	/**
	 * checks if this node is open
	 * @return true if this node is open 
	 */
	public boolean isOpen()
	{
		return open;
	}
	
	/**
	 * checks if this node is visible
	 * @return true if this node is visible 
	 */
	public boolean isVisible()
	{
		return visible;
	}
	
	/**
	 * checks if this node is selected
	 * @return true if this node is selected 
	 */
	public boolean isSelected()
	{
		return selected;
	}
	
	/**
	 * sets the visible property of this node
	 * @param isVisible the new value of property visible
	 */
	public void setVisible(boolean isVisible)
	{
		this.visible = isVisible;
	}
	
	/**
	 * sets the open property of this node
	 * @param isOpen the new value of property open
	 */	
	protected void setOpen(boolean isOpen)
	{
		this.open = isOpen;
	}
	
	/**
	 * sets the selected property of this node
	 * @param isSelected the new value of property selected
	 */
	protected void setSelected(boolean isSelected)
	{
		this.selected = isSelected;
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
	 * Returns the path.
	 * @return String
	 */
	public String getPath()
	{
		return path;
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
	 * Sets the path.
	 * @param path The path to set
	 */
	public void setPath(String path)
	{
		this.path = path;
	}
	
	/**
	 * Returns the childrenPath.
	 * @return String
	 */
	public String getChildrenPath()
	{
		return childrenPath;
	}

	/**
	 * Sets the childrenPath.
	 * @param childrenPath The childrenPath to set
	 */
	public void setChildrenPath(String childrenPath)
	{
		this.childrenPath = childrenPath;
	}

	/**
	 * @return
	 */
	public boolean isSelectable()
	{
		return selectable;
	}

	/**
	 * @param b
	 */
	public void setSelectable(boolean b)
	{
		selectable = b;
	}

	/**
	 * @return
	 */
	public boolean isLinkable() 
	{
		return linkable;
	}

	/**
	 * @param b
	 */
	public void setLinkable(boolean b) 
	{
		linkable = b;
	}

}
