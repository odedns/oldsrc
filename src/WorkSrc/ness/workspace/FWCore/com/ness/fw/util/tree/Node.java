package com.ness.fw.util.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

/**
 * @author gtabakman
 *
 * An in-memory map based tree implementation.
 */
public class Node
{

	/**
	 * States that a search is not recursive and scans
	 * the current node only.
	 */
	public static final int NO_RECURSION = 0;

	/**
	 * States the maximum recursion depth that will
	 * be used in recursive calls.
	 */
	public static final int MAX_RECURSION = Integer.MAX_VALUE;

	/**
	 * States that when finding nodes in the various finders, 
	 * all of the matching nodes will be automaticaly selected.
	 */
	public static final boolean SELECT = true;

	/**
	 * States that when finding nodes in the various finders, 
	 * all of the matching nodes will not have their selected property
	 * altered.
	 */
	public static final boolean NO_SELECT = false;

	/**
	 * States that a node will be sorted by its key.
	 */
	public static final int SORT_BY_KEY = 0;

	/**
	 * States that a node will be sorted by its description.
	 */
	public static final int SORT_BY_DESCRIPTION = 1;

	/**
	 * States that a node will be sorted by its order helper property.
	 */
	public static final int SORT_BY_ORDER = 2;

	/**
	 * States that a sort operation will be done in ascending order.
	 */
	public static final int SORT_ASCENDING = 1;

	/**
	 * States that a sort operation will be done in descending order.
	 */
	public static final int SORT_DESCENDING = -1;

	/**
	 * The unique identifier that will identify the node.
	 */
	private Object key;

	/**
	 * The description of the node.
	 */
	private String description;

	/**
	 * The parent node of the node.
	 */
	private Node parent;

	/**
	 * A collection of children associated to the node. 
	 */
	private TreeMap children;

	/**
	 * A collection of key-value pairs representing extra data items
	 * associated to the node. 
	 */
	private HashMap extraData;

	/**
	 * An extra object that can be associated to the node.
	 * This object can be used to hold any object.
	 */
	private Object extraObject;

	/**
	 * A mark that states wheather the node is selected or not.
	 * this mark can be set manualy or as a result of finder calls.
	 */
	private boolean selected;

	/**
	 * A mark that states wheather the node is collapsed or exoanded.
	 * This attribute is used in the visual representation of the tree.
	 */
	private boolean expanded;

	/**
	 * States the current sort order.
	 * This attribute is used when sorting with the SORT_BY_ORDER
	 * sort type.
	 */
	private int order = 0;

	/**
	 * States the current sort order in which sort operations will
	 * be handled.
	 * Order type can be either SORT_ASCENDING or SORT_DESCENDING
	 */
	private int orderType = SORT_ASCENDING;

	/**
	 * Create a new node using a KeyGenerator to generate the key.
	 * @param keyGen the KeyGenerator that will be used.
	 * @param description - the description of the node.
	 */
	public Node(KeyGenerator keyGen, String description)
	{
		this.key = keyGen.nextKey();
		this.description = description;
	}

	/**
	 * Create a new node of which the key is supplied and managed elsewhere.
	 * @param key the key for this node.
	 * @param description the description of the node.
	 */
	public Node(Object key, String description)
	{
		this.key = key;
		this.description = description;
	}

	/**
	 * Returns true if the node has children.
	 * @return true if the node has children, false otherwise.
	 */
	public boolean hasChildren()
	{
		return children != null && children.size() > 0;
	}

	/**
	 * Add a node to this node.
	 * @param child the node that should be added to this node.
	 */
	public void addChild(Node child)
	{
		if (children == null)
		{
			children = new TreeMap();
		}
		child.setParent(this);
		children.put(child.getKey(), child);
	}

	/**
	 * Add this node to a specified parent node.
	 * @param parent the parent node to which this node will be added.
	 */
	public void addTo(Node parent)
	{
		if(parent!=null)
		{
			parent.addChild(this);
		}
	}

	/**
	 * Add a group of nodes to this node.
	 * @param children a List of nodes that should be added to this node. 
	 */
	public void addChildren(List children)
	{
		if(children!=null)
		{
			for (Iterator iter = children.iterator(); iter.hasNext();)
			{
				addChild((Node) iter.next());
			}			
		}
	}

	/**
	 * Add a group of nodes to this node.
	 * @param children a TreeMap of nodes that should be added to this node. 
	 */
	public void addChildren(TreeMap childrenToAdd)
	{
		if (childrenToAdd != null)
		{
			for (Iterator iter = childrenToAdd.values().iterator(); iter.hasNext();)
			{
				addChild((Node) iter.next());
			}
		}
	}

	/**
	 * Remove a node from this node.
	 * @param child the node that should be removed from this node. 
	 * @return the removed node which is now an orphan.
	 */
	public Node removeChild(Node child)
	{
		if(child!=null)
		{
			Node childToRemove = (Node) children.remove(child.getKey());
			if (childToRemove != null)
			{
				childToRemove.setParent(null);
			}
			return childToRemove;			
		}
		else
		{
			return null;
		}
	}

	/**
	 * Remove a node from this node by key recursively.
	 * The removal is done by recursively searching for the requested key
	 * starting from this node and ending after reaching the maximum recursion
	 * level specified. if the key is found it is then removed and orphaned. 
	 * @param descendantKey the key of the node that should be removed recursively.
	 * @param maxRecursionDepth the maximum recursion level that should be scanned.
	 * @return the removed node which is now an orphan.
	 * @throws TreeException if attempting to remove a root node.
	 */
	public Node removeDescendantByKey(Object descendantKey, int maxRecursionDepth) throws TreeException
	{
		Node descendantToRemove = findDescendantByKey(descendantKey, maxRecursionDepth, NO_SELECT);
		if (descendantToRemove != null)
		{
			if (descendantToRemove.getParent() == null)
			{
				throw new TreeException("Cannot remove a root node");
			}
			descendantToRemove.getParent().removeChild(descendantToRemove);
		}
		return descendantToRemove;
	}

	/**
	 * Remove this node from its parent.
	 */
	public void remove()
	{
		if (parent != null)
		{
			parent.removeChild(this);
		}
	}

	/**
	 * Move this node from its parent to another node.
	 * @param newParent the node which this node will move to.
	 */
	public void moveTo(Node newParent)
	{
		if (parent != null)
		{
			parent.removeChild(this);
		}
		newParent.addChild(this);
	}

	/**
	 * Merge this node into another node.
	 * The merge operation applies only to the children and not to
	 * the node itself, which is no more used. 
	 * The additional information (extraData, extraObject) is lost.
	 * @param targetNode the node which this node will be merged into.
	 */
	public void mergeTo(Node targetNode)
	{
		if(targetNode!=null)
		{
			targetNode.addChildren(getChildren());
			if (parent != null)
			{
				parent.removeChild(this);
			}
			
		}
	}

	/**
	 * Copy the node to another node recursively.
	 * @param targetNode the target node which this node will be copied to.
	 * @param maxRecursionDepth the maximum nodes depth to copy. 
	 * @param keyGen the KeyGenerator used to generate keys to the new nodes.
	 */
	public void copyTo(Node targetNode, int maxRecursionDepth, KeyGenerator keyGen)
	{
		targetNode.addChild(deepClone(maxRecursionDepth, keyGen));
	}

	/**
	 * Deep clone a node with all of the attributes recursively. 
	 * this method copies everything an not just use references.
	 * @param maxRecursionDepth the maximum nodes depth to copy. 
	 * @param keyGen the KeyGenerator used to generate keys to the new nodes.
	 * @return the new cloned node.
	 */
	private Node deepClone(int maxRecursionDepth, KeyGenerator keyGen)
	{
		Node node = new Node(keyGen, description);
		node.extraObject = extraObject;
		node.addExtraData(extraData);
		if (hasChildren() && maxRecursionDepth-- > 0)
		{
			for (Iterator iter = children.values().iterator(); iter.hasNext();)
			{
				node.addChild(((Node) iter.next()).deepClone(maxRecursionDepth, keyGen));
			}
		}
		return node;
	}

	/**
	 * Add a group of extra data objects to this node.
	 * @param extraDataToAdd the group of extra data to be added to this node.
	 */
	public void addExtraData(HashMap extraDataToAdd)
	{
		if (extraData == null)
		{
			extraData = new HashMap();
		}

		if (extraDataToAdd != null)
		{
			extraData.putAll(extraDataToAdd);
		}
	}

	/**
	 * Add an extra data object to this node.
	 * @param extraDataObjectKey the key for the extra data object to be added.
	 * @param extraDataObject the extra data object to be added.
	 */
	public void addExtraDataObject(String extraDataObjectKey, Object extraDataObject)
	{
		if (extraData == null)
		{
			extraData = new HashMap();
		}
		extraData.put(extraDataObjectKey, extraDataObject);
	}

	/**
	 * Remove an Extra data object from this node.
	 * @param extraDataObjectKey the key of the extra data to be removed. 
	 */
	public void removeExtraDataObject(String extraDataObjectKey)
	{
		extraData.remove(extraDataObjectKey);
	}

	/**
	 * Returns true if the node has no children.
	 * @return true if the node has no children, false otherwise.
	 */
	public boolean isLeaf()
	{
		return (children == null || children.isEmpty()) ? true : false;
	}

	/**
	 * Find a node by key recursively.
	 * The search is done recursively starting from this node and ending after 
	 * reaching the maximum recursion level specified.
	 * @param key the key of the node to search for recursively.
	 * @param maxRecursionDepth the maximum recursion level that should be scanned.
	 * @param select wheather the found node should be selected or left unchanged.
	 * @return the found node or null if no matching node exists.
	 */
	public Node findDescendantByKey(Object key, int maxRecursionDepth, boolean select)
	{
		Node found = (this.key.equals(key)) ? this : null;
		if (found == null && hasChildren() && maxRecursionDepth-- > 0)
		{
			Iterator iter = children.values().iterator();
			while (found == null && iter.hasNext())
			{
				found = ((Node) iter.next()).findDescendantByKey(key, maxRecursionDepth, select);
			}
		}
		if (found != null && select)
		{
			found.select();
		}
		return found;
	}

	/**
	 * Find the node descendants whose description matches a 
	 * regular expression pattern recursively.
	 * @param regExPattern the pattern to look for inside the description.
	 * @param regExFlags the regEx pattern flags tu use.
	 * @param maxRecursionDepth the maximum recursion level that should be scanned.
	 * @param select wheather the found node should be selected or left unchanged.
	 * @return an ArrayList of all the nodes which matches the search criteria.
	 */
	public ArrayList findDescendantsByDescription(String regExPattern, int regExFlags, int maxRecursionDepth, boolean select)
	{
		ArrayList foundNodes = new ArrayList();
		Pattern pattern = Pattern.compile(regExPattern, regExFlags);
		findDescendantsByDescription(pattern, maxRecursionDepth, foundNodes, select);
		return foundNodes;
	}

	/**
	 * Helper method of findDescendantsByDescription().
	 * This method performs the recursive calls for the public method.
	 * @param pattern the pattern to look for inside the description.
	 * @param maxRecursionDepth the maximum recursion level that should be scanned.
	 * @param foundNodes an ArrayList of all the nodes which matches the search criteria.
	 * @param select wheather the found node should be selected or left unchanged.
	 */
	private void findDescendantsByDescription(Pattern pattern, int maxRecursionDepth, ArrayList foundNodes, boolean select)
	{
		if (description != null && pattern.matcher(description).matches())
		{
			if (select)
			{
				select();
			}
			foundNodes.add(this);
		}
		if (hasChildren() && maxRecursionDepth-- > 0)
		{
			for (Iterator iter = children.values().iterator(); iter.hasNext();)
			{
				((Node) iter.next()).findDescendantsByDescription(pattern, maxRecursionDepth, foundNodes, select);
			}
		}
	}

	/**
	 * Find the node descendants having a specified extraData item key recursively.
	 * @param key the key to search for in the extraData HashMap.
	 * @param maxRecursionDepth the maximum recursion level that should be scanned.
	 * @param select wheather the found node should be selected or left unchanged.
	 * @return an ArrayList of all the nodes having a matching extraData item key.
	 */
	public ArrayList findDescendantsByExtraDataKey(String key, int maxRecursionDepth, boolean select)
	{
		ArrayList foundNodes = new ArrayList();
		findDescendantsByExtraDataKey(key, maxRecursionDepth, foundNodes, select);
		return foundNodes;
	}

	/**
	 * Helper method of findDescendantsByExtraDataKey().
	 * This method performs the recursive calls for the public method.
	 * @param key the key to search for in the extraData HashMap.
	 * @param maxRecursionDepth the maximum recursion level that should be scanned.
	 * @param foundNodes an ArrayList of all the nodes having a matching extraData item key.
	 * @param select wheather the found node should be selected or left unchanged.
	 */
	private void findDescendantsByExtraDataKey(String key, int maxRecursionDepth, ArrayList foundNodes, boolean select)
	{
		if (extraData != null && extraData.get(key) != null)
		{
			if (select)
			{
				select();
			}
			foundNodes.add(this);
		}
		if (hasChildren() && maxRecursionDepth-- > 0)
		{
			for (Iterator iter = children.values().iterator(); iter.hasNext();)
			{
				((Node) iter.next()).findDescendantsByExtraDataKey(key, maxRecursionDepth, foundNodes, select);
			}
		}
	}

	/**
	 * Find the node descendants having an extraData item description that
	 * matches a regular expression pattern recursively.
	 * @param regExPattern the pattern to look for inside the description.
	 * @param regExFlags the regEx pattern flags tu use.
	 * @param maxRecursionDepth the maximum recursion level that should be scanned.
	 * @param select wheather the found node should be selected or left unchanged.
	 * @return an ArrayList of all the nodes having an extraData item description that
	 * matches a regular expression pattern.
	 */
	public ArrayList findDescendantsByExtraDataDescription(String regExPattern, int regExFlags, int maxRecursionDepth, boolean select)
	{
		ArrayList foundNodes = new ArrayList();
		Pattern pattern = Pattern.compile(regExPattern, regExFlags);
		findDescendantsByExtraDataDescription(pattern, maxRecursionDepth, foundNodes, select);
		return foundNodes;
	}

	/**
	 * Helper method of findDescendantsByExtraDataDescription().
	 * This method performs the recursive calls for the public method.
	 * @param pattern the pattern to look for inside the description.
	 * @param maxRecursionDepth the maximum recursion level that should be scanned.
	 * @param foundNodes an ArrayList of all the nodes having an extraData item description that
	 * matches a regular expression pattern.
	 * @param select wheather the found node should be selected or left unchanged.
	 */
	private void findDescendantsByExtraDataDescription(Pattern pattern, int maxRecursionDepth, ArrayList foundNodes, boolean select)
	{
		Iterator iter;
		if (extraData != null)
		{
			boolean found = false;
			iter = extraData.values().iterator();
			while (!found && iter.hasNext())
			{
				Object obj = iter.next();
				if (obj instanceof String && pattern.matcher((String) obj).matches())
				{
					if (select)
					{
						select();
					}
					foundNodes.add(this);
					found = true;
				}
			}
		}
		if (hasChildren() && maxRecursionDepth-- > 0)
		{
			for (iter = children.values().iterator(); iter.hasNext();)
			{
				((Node) iter.next()).findDescendantsByExtraDataDescription(pattern, maxRecursionDepth, foundNodes, select);
			}
		}
	}

	/**
	 * Find all the leaves (nodes with no children) recursively.
	 * @param maxRecursionDepth the maximum recursion level that should be scanned.
	 * @param select wheather the found node should be selected or left unchanged.
	 * @return an ArrayList of all the leaves found.
	 */
	public ArrayList findLeaves(int maxRecursionDepth, boolean select)
	{
		ArrayList foundNodes = new ArrayList();
		findLeaves(maxRecursionDepth, foundNodes, select);
		return foundNodes;
	}

	/**
	 * Helper method of findLeaves().
	 * This method performs the recursive calls for the public method.
	 * @param maxRecursionDepth the maximum recursion level that should be scanned.
	 * @param foundNodes an ArrayList of all the leaves found.
	 * @param select wheather the found node should be selected or left unchanged.
	 */
	private void findLeaves(int maxRecursionDepth, ArrayList foundNodes, boolean select)
	{
		if (isLeaf())
		{
			if (select)
			{
				select();
			}
			foundNodes.add(this);
		}
		if (hasChildren() && maxRecursionDepth-- > 0)
		{
			for (Iterator iter = children.values().iterator(); iter.hasNext();)
			{
				((Node) iter.next()).findLeaves(maxRecursionDepth, foundNodes, select);
			}
		}
	}

	/**
	 * Return the number of descendants of the current node recursively
	 * including the node itself.
	 * @param maxRecursionDepth the maximum recursion level that should be scanned.
	 * @param selectedOnly true - count only the selected nodes.
	 * false - all of the nodes.
	 * @return the number of descendants counted. 
	 */
	public int countDescendants(int maxRecursionDepth, boolean selectedOnly)
	{
		int descendantsCount = 0;
		if (!selectedOnly || selected)
		{
			++descendantsCount;
		}
		if (hasChildren() && maxRecursionDepth-- > 0)
		{
			for (Iterator iter = children.values().iterator(); iter.hasNext();)
			{
				descendantsCount += ((Node) iter.next()).countDescendants( maxRecursionDepth, selectedOnly);
			}
		}
		return descendantsCount;
	}

	/**
	 * Return the number of descendant leaves of the current node recursively
	 * including the node itself.
	 * @param maxRecursionDepth the maximum recursion level that should be scanned.
	 * @param selectedOnly true - count only the selected nodes.
	 * false - all of the nodes.
	 * @return the number of descendant leaves counted. 
	 */
	public int countLeaves(int maxRecursionDepth, boolean selectedOnly)
	{
		int leavesCount = 0;
		if (isLeaf())
		{
			if ((!selectedOnly) || (selected))
			{
				++leavesCount;
			}
		}
		if (hasChildren() && maxRecursionDepth-- > 0)
		{
			for (Iterator iter = children.values().iterator(); iter.hasNext();)
			{
				leavesCount	+= ((Node) iter.next()).countLeaves(maxRecursionDepth, selectedOnly);
			}
		}
		return leavesCount;
	}

	/**
	 * Find selected nodes recursively.
	 * @param maxRecursionDepth the maximum recursion level that should be scanned.
	 * @return an ArrayList of all the selected nodes found.
	 */
	public ArrayList findSelectedNodes(int maxRecursionDepth)
	{
		ArrayList foundNodes = new ArrayList();
		findSelectedNodes(maxRecursionDepth, foundNodes);
		return foundNodes;
	}

	/**
	 * Helper method of findSelectedNodes().
	 * This method performs the recursive calls for the public method.
	 * @param maxRecursionDepth the maximum recursion level that should be scanned.
	 * @param foundNodes an ArrayList of all the selected nodes found.
	 */
	private void findSelectedNodes(int maxRecursionDepth, ArrayList foundNodes)
	{
		if (selected)
		{
			foundNodes.add(this);
		}
		if (hasChildren() && maxRecursionDepth-- > 0)
		{
			for (Iterator iter = children.values().iterator(); iter.hasNext();)
			{
				((Node) iter.next()).findSelectedNodes(maxRecursionDepth, foundNodes);
			}
		}
	}

	/**
	 * Clear the selection status recursively.
	 * @param maxRecursionDepth the maximum recursion level that should be scanned.
	 */
	public void clearSelection(int maxRecursionDepth){
		unSelect();
		if (hasChildren() && maxRecursionDepth-- > 0)
		{
			for (Iterator iter = children.values().iterator(); iter.hasNext();)
			{
				((Node) iter.next()).clearSelection(maxRecursionDepth);
			}
		}
	}

	/**
	 * Sort the children of this node.
	 * Sorting is performed only on the children of the current node.
	 * the setOrderType() method should be called prior to this method
	 * in order to state the sort order type (ascending or descending).
	 * @param sortType determines the sorting criteria
	 * @return an ArrayList containing the sorted children. 
	 */
	public ArrayList sortChildren(int sortType)
	{
		if (hasChildren())
		{
			Comparator comparator = null;
			switch (sortType)
			{
				case SORT_BY_KEY :
					comparator = new KeyComparator();
					break;
				case SORT_BY_DESCRIPTION :
					comparator = new DescriptionComparator();
					break;
				case SORT_BY_ORDER :
					comparator = new OrderComparator();
					break;
				default:
					comparator = new KeyComparator();
					break;
			}
			return sortChildren(comparator);
		}
		return null;
	}

	/**
	 * Sort the children of this node with a specified Comparator.
	 * Sorting is performed only on the children of the current node.
	 * the setOrderType() method should be called prior to this method
	 * in order to state the sort order type (ascending or descending).
	 * @param comparator the Comparator to use for the sort.
	 * @return an ArrayList containing the sorted children. 
	 */
	public ArrayList sortChildren(Comparator comparator)
	{
		if (comparator != null && hasChildren())
		{
			ArrayList sortedList = new ArrayList(children.values());
			Collections.sort(sortedList, comparator);
			return sortedList;
		}
		return null;
	}

	/**
	 * Select a node by key.
	 * @param key the key of the node to search for recursively.
	 * @param maxRecursionDepth the maximum recursion level that should be scanned.
	 * @return the node found or null if no matching node exists.
	 */
	public Node selectByKey(Object key, int maxRecursionDepth)
	{
		return findDescendantByKey(key, maxRecursionDepth, SELECT);
	}

	/**
	 * Returns the path from this node to the root recursively.
	 * @return an ArrayList of the nodes between this node and its root node.
	 */
	public ArrayList getPath()
	{
		ArrayList path = new ArrayList();
		getPath(path);
		return path;
	}

	/**
	 * Helper method of getPath().
	 * This method performs the recursive calls for the public method.
	 * @param path an ArrayList of the nodes between this node and its root node.
	 */
	private void getPath(ArrayList path)
	{
		if (parent != null)
		{
			parent.getPath(path);
		}
		path.add(this);
	}

	/**
	 * Get the root node of this node recursively.
	 * @return the root node of this node.
	 */
	private Node getRoot()
	{
		return (parent == null) ? this : parent.getRoot(); 
	}

	/**
	 * Returns the key of this node
	 * @return Object the key of this node
	 */
	public Object getKey()
	{
		return key;
	}

	/**
	 * Sets the key of this node.
	 * @param key the key of this node.
	 */
	public void setKey(Object key)
	{
		this.key = key;
	}

	/**
	 * Returns the order of this node.
	 * @return the order of this node.
	 */
	public int getOrder()
	{
		return order;
	}

	/**
	 * Sets the order of this node.
	 * @param order the order of this node.
	 */
	public void setOrder(int order)
	{
		this.order = order;
	}

	/**
	 * Returns the order type of this node.
	 * @return the order type of this node.
	 */
	public int getOrderType()
	{
		return orderType;
	}

	/**
	 * Sets the order type of this node.
	 * @param orderType the order type of this node.
	 */
	public void setOrderType(int orderType)
	{
		this.orderType = orderType;
	}

	/**
	 * Returns the description of this node.
	 * @return the description of this node.
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sets the description of this node.
	 * @param description the description of this node.
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * Returns the parent of this node.
	 * @return the parent of this node.
	 */
	public Node getParent()
	{
		return parent;
	}

	/**
	 * Sets the parent of this node.
	 * @param parent the parent of this node.
	 */
	public void setParent(Node parent)
	{
		this.parent = parent;
	}

	/**
	 * Returns the children of this node.
	 * @return the children of this node.
	 */
	public TreeMap getChildren()
	{
		return children;
	}

	/**
	 * Sets the children of this node.
	 * @param children the children of this node.
	 */
	public void setChildren(TreeMap children)
	{
		this.children = children;
	}

	/**
	 * Returns the extra data of this node.
	 * @return the extra data of this node.
	 */
	public HashMap getExtraData()
	{
		return extraData;
	}

	/**
	 * Sets the extra data of this node.
	 * @param extraData the extra data of this node.
	 */
	public void setExtraData(HashMap extraData)
	{
		this.extraData = extraData;
	}

	/**
	 * Returns the extra object of this node.
	 * @return the extra object of this node.
	 */
	public Object getExtraObject()
	{
		return extraObject;
	}

	/**
	 * Sets the extra object of this node.
	 * @param extraObject the extra object of this node.
	 */
	public void setExtraObject(Object extraObject)
	{
		this.extraObject = extraObject;
	}

	/**
	 * Returns wheather the node is selected or not.
	 * @return true - the node is selected, false - the node is not selected.
	 */
	public boolean isSelected()
	{
		return selected;
	}

	/**
	 * Sets the node selected status.
	 * @param selected true - the node is selected, false - the node is not selected.
	 */
	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}

	/**
	 * Selects the current Node (the select field is set to true).
	 */
	public void select()
	{
		selected = true;
	}

	/**
	 * Unselects the current Node (the select field is reset to false).
	 */
	public void unSelect()
	{
		selected = false;
	}

	/**
	 * Returns wheather the node is expanded or not.
	 * @return true - the node is expanded, false - the node is not expanded.
	 */
	public boolean isExpanded()
	{
		return expanded;
	}

	/**
	 * Sets the node expanded status.
	 * @param expanded true - the node is expanded, false - the node is not expanded.
	 */
	public void setExpanded(boolean expanded)
	{
		this.expanded = expanded;
	}

	/**
	 * Expands the current Node (the expanded field is set to true).
	 */
	public void expand()
	{
		expanded = true;
	}

	/**
	 * Collapses the current Node (the expanded field is reset to false).
	 */
	public void collapse()
	{
		expanded = false;
	}

	/**
	 * Expand the node recursively
	 * @param maxRecursionDepth the maximum recursion level that should be expanded.
	 */
	public void expand(int maxRecursionDepth)
	{
		expand();
		if (hasChildren() && maxRecursionDepth-- > 0)
		{
			for (Iterator iter = children.values().iterator(); iter.hasNext();)
			{
				((Node) iter.next()).expand(maxRecursionDepth);
			}
		}
	}

	/**
	 * Colapse the node recursively
	 * @param maxRecursionDepth the maximum recursion level that should be colapsed.
	 */
	public void collapse(int maxRecursionDepth)
	{
		collapse();
		if (hasChildren() && maxRecursionDepth-- > 0)
		{
			for (Iterator iter = children.values().iterator(); iter.hasNext();)
			{
				((Node) iter.next()).collapse(maxRecursionDepth);
			}
		}
	}

	/**
	 * Replaces the default toString() method.
	 * @return String
	 */
	public String toString()
	{
		return printNode("", false);
	}

	/**
	 * Helper method of toString()
	 * @param tabs a String that should precede the current node String 
	 * representation. usefull for indentation.
	 * @param recursive should the method print the descendants recursively
	 * @return the String representation of the node.
	 */
	private String printNode(String tabs, boolean recursive)
	{
		StringBuffer sb = new StringBuffer(256);
		sb.append(tabs + "{[key=" + key + "],[description=" + description + "]");
		if (parent != null)
		{
			sb.append(", [parentKey=" + parent.getKey() + "]");
		}
		sb.append(",[selected=" + selected + "]");
		sb.append(",[expanded=" + expanded + "]");
		if (extraData != null)
		{
			sb.append(",[extraData=");
			for (Iterator iter = extraData.entrySet().iterator(); iter.hasNext();)
			{
				Map.Entry me = (Map.Entry) iter.next();
				sb.append("(" + me.getKey() + "=" + me.getValue() + ")");
			}
			sb.append("]");
		}
		if (recursive && hasChildren())
		{
			sb.append("\n");
			for (Iterator iter = children.entrySet().iterator(); iter.hasNext();)
			{
				Node n = (Node) ((Map.Entry) iter.next()).getValue();
				sb.append(n.printNode((tabs + "\t"), recursive));
			}
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Comparator that compare nodes by their key.
	 */
	private class KeyComparator implements Comparator
	{
		public int compare(Object obj1, Object obj2)
		{
			Object var1 = ((Node) obj1).getKey();
			Object var2 = ((Node) obj2).getKey();
			int equality = 0;
			if (var1 == null)
			{
				if (var2 == null)
				{
					equality = 0;
				}
				else
				{
					equality = -1;
				}
			}
			else if (var2 == null)
			{
				equality = 1;
			}
			else
			{
				equality = ((Comparable) var1).compareTo(var2);
			}
			return equality * orderType;
		}
	};

	/**
	 * Comparator that compare nodes by their description.
	 */
	private class DescriptionComparator implements Comparator
	{
		public int compare(Object obj1, Object obj2)
		{
			int equality = 0;
			String var1 = ((Node) obj1).getDescription();
			String var2 = ((Node) obj2).getDescription();
			if (var1 == null)
			{
				if (var2 == null)
				{
					equality = 0;
				}
				else
				{
					equality = -1;
				}
			}
			else if (var2 == null)
			{
				equality = 1;
			}
			else
			{
				equality = ((Comparable) var1).compareTo(var2);
			}
			return equality * orderType;
		}
	};

	/**
	 * Comparator that compare nodes by their sort order.
	 */
	private class OrderComparator implements Comparator
	{
		public int compare(Object obj1, Object obj2)
		{
			int equality = 0;
			int var1 = ((Node) obj1).getOrder();
			int var2 = ((Node) obj2).getOrder();
			if (var1 == var2)
			{
				equality = 0;
			}
			else if (var1 > var2)
			{
				equality = 1;
			}
			else
			{
				equality = -1;
			}
			return equality * orderType;
		}
	};

}