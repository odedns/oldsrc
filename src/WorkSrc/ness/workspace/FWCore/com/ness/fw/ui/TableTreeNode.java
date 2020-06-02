package com.ness.fw.ui;

public class TableTreeNode extends DefaultTreeNode
{
	private Row row;
	
	/**
	 * Empty constructor
	 */
	public TableTreeNode()
	{	
		this(new Row(DEFAULT_ROOT_ID));
	}
	
	/**
	 * Constructs TableTreeNode with (@link Row) object
	 * @param row the {@link Row} of this node
	 */
	public TableTreeNode(Row row)
	{
		this(row,false);
	}
	
	/**
	 * Constructs TableTreeNode with (@link Row) object and isOpen indication for the root
	 * @param row the {@link Row} of this node
	 * @param isOpen indicates if this row is open
	 */
	public TableTreeNode(Row row, boolean isOpen)
	{
		this.open = isOpen;
		this.row = row;
	}
	
	/**
	 * constructor for TableTreeNode
	 * @param row the {@link Row} of this node
	 * @param isOpen if true - the row of this node is open which means that its children will
	 * be visible
	 * @param closeImage name of an image for closed row - a row that has children
	 * which are not visible
	 * @param openImage name of an image for this row in open state - a row that has children
	 * which are visible
	 * @param emptyImage name of an image for empty row -  a row that has no children
	 */
	public TableTreeNode(Row row, boolean isOpen, String emptyImage, String openImage, String closeImage)
	{
		this.id = row.getId();
		this.open = isOpen;
		this.row = row;
		if (!openImage.equals(""))
			this.openImage = openImage;
		if (!closeImage.equals(""))
			this.closeImage = closeImage;
		if (!emptyImage.equals(""))
			this.emptyImage = emptyImage;
	}
	
	/**
	 * @see com.ness.fw.ui.AbstractTreeNode#getChild(int)
	 */
	public AbstractTreeNode getChild(int index)
	{
		return (TableTreeNode) nodes.get(index);
	}
	
	/**
	 * @see com.ness.fw.ui.AbstractTreeNode#setParent(AbstractTreeNode)
	 */
	public void setParent(AbstractTreeNode parent)
	{
		this.parent = (TableTreeNode) parent;
	}
	
	/**
	 * @see com.ness.fw.ui.AbstractTreeNode#getParent()
	 */
	public AbstractTreeNode getParent()
	{
		return (TableTreeNode) parent;
	}
	
	/**
	 * Sets new row for this node
	 * @param row the new row of this node
	 */
	public void setRow(Row row)
	{
		this.row = row;
	}
	
	/**
	 * Returns the row of this node
	 * @return row of this node
	 */
	public Row getRow()
	{
		return row;
	}
}
