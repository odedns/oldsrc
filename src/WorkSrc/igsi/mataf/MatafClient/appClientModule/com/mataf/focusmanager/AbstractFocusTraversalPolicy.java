package com.mataf.focusmanager;
import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.Hashtable;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Vector;
/**
 * @author Nati Dikshtein.
 * @revised by Eyal Ben Zeev.
 *
 * This class is an abstract FocusTraversalPolicy that uses an orderd List 
 * of focusable components.
 * It builds two hashtables from the ordered list : <p>
 * First hashtable is used to determine the next focusable component, and
 * the second is used to determine the previous focusable component.
 */
public abstract class AbstractFocusTraversalPolicy extends FocusTraversalPolicy
{
	/** Holds "component,next component" pairs.*/
	private Hashtable prevs = new Hashtable();
	/** Holds "component,previous component" pairs.*/
	private Hashtable nexts = new Hashtable();
	/** The ordered list of the focusable components.*/
	private List focusOrderList;
	/** Determines if first component should be last component's next
	 *  focusable component.(and the other way around) */
	private boolean cycledFocus;
	private Component defaultComponent;
	
	/** 
	 * Construct an empty FocusTraversalPolicy.
	 */
	
	public AbstractFocusTraversalPolicy()
	{
		focusOrderList = new Vector();
	}
	
	/**
	 * Construct a FocusTraversalPolicy from an ordered List.
	 */
	public AbstractFocusTraversalPolicy(List focusOrderList)
	{
		this(focusOrderList, false);
	}
	
	/**
	 * Construct a optionally cycled FocusTraversalPolicy from 
	 * an ordered List.
	 */
	public AbstractFocusTraversalPolicy(
		List focusOrderList,
		boolean cycledFocus)
	{
		this.focusOrderList = focusOrderList;
		this.cycledFocus = cycledFocus;
		buildHashtables();
	}
	
	/**
	 * Set a new focusOrderList from a give list of components.
	 */
	public void setFocusOrderList(List focusOrderList)
	{
		this.focusOrderList = focusOrderList;
		buildHashtables();
	}
	
	
	/** 
	 * Constructs 2 Hashtables from the orderd List.
	 * One for determining the next focusable component for each component,
	 * and one for determining the previous focusable component for each
	 * component.
	 */
	private void buildHashtables()
	{		
		// Build the hashtables.
		int size = focusOrderList.size();
		for (int i = 0; i < size - 1; i++)
		{
			Component c1 = (Component) focusOrderList.get(i);
			Component c2 = (Component) focusOrderList.get(i + 1);
			prevs.put(c2, c1);
			nexts.put(c1, c2);
		}
		// If cycledFocus is true, connect first and last components.
		if (cycledFocus)
		{
			Component first = (Component) focusOrderList.get(0);
			Component last = (Component) focusOrderList.get(size - 1);
			prevs.put(first, last);
			nexts.put(last, first);
		}
		// Set default component to first component by default.
		defaultComponent = (Component) focusOrderList.get(0);
	}
	/** 
	 * Adds a component to the FocusTraversalPolicy List after the specified
	 * component.
	 */
	public void addComponentBefore(
		Component aComponent,
		Component prevComponent)
	{
		int i = getComponentIndex(aComponent);
		focusOrderList.add(i, prevComponent);
		buildHashtables();
	}
	/** 
	 * Adds a component to the FocusTraversalPolicy List before the specified
	 * component.
	 */
	public void addComponentAfter(
		Component aComponent,
		Component nextComponent)
	{
		int i = getComponentIndex(aComponent);
		// aComponent is the first Component.
		if (i == focusOrderList.size() - 1)
			focusOrderList.add(aComponent);
		else
			focusOrderList.add(i + 1, nextComponent);
		buildHashtables();
	}
	
	/** 
	 * Sets the next component for a component.
	 * This method should be invoked only for components that 
	 * are NOT part of the focusOrderList.
	 * @see addComponentAfter
	 */
	public void putInNextsHashtable(Component c, Component next)
	{
		nexts.put(c, next);
	}
	
	/** 
	 * Sets the previuos component for a component.
	 * This method should be invoked only for components that 
	 * are NOT part of the focusOrderList.
	 * @see addComponentBefore
	 */
	public void putInPrevsHashtable(Component c, Component prev)
	{
		prevs.put(c, prev);
	}	
	
	/**
	 * Sets the default component for this TraversalPolicy.
	 * Throws an exception if component is not part of the
	 * TraversalPolicy.
	 */
	public void setDefaultComponent(Component aComponent)
	{
		if(nexts.get(aComponent)==null)
			throw new NoSuchElementException("Component is NOT in part of the FocusTraversalPolicy !");
	
		defaultComponent = aComponent;
	}
	
	
	/** 
	 * Utility method which throws an exception if the component is not
	 * part of the focus traversal policy.
	 * Otherwise the method returns its index in the ordered list.
	 */
	private int getComponentIndex(Component aComponent)
	{
		int i = focusOrderList.indexOf(aComponent);
		if (i < 0)
			throw new NoSuchElementException("Component is NOT in part of the FocusTraversalPolicy List !");
		return i;
	}
	
	public boolean isCycledFocus()
	{
		return cycledFocus;
	}
	
	public void setCycledFocus(boolean isCycledFocus)
	{
		cycledFocus = isCycledFocus;
	}
	
	/**
	 * @see java.awt.FocusTraversalPolicy#getComponentAfter(Container, Component)
	 */
	public Component getComponentAfter(
		Container focusCycleRoot,
		Component aComponent)
	{
		return (Component) nexts.get(aComponent);
	}
	
	/**
	 * @see java.awt.FocusTraversalPolicy#getComponentBefore(Container, Component)
	 */
	public Component getComponentBefore(
		Container focusCycleRoot,
		Component aComponent)
	{
		return (Component) prevs.get(aComponent);
	}
	
	/**
	 * @see java.awt.FocusTraversalPolicy#getFirstComponent(Container)
	 */
	public Component getFirstComponent(Container focusCycleRoot)
	{
		return (Component) focusOrderList.get(0);
	}
	
	/**
	 * @see java.awt.FocusTraversalPolicy#getLastComponent(Container)
	 */
	public Component getLastComponent(Container focusCycleRoot)
	{
		return (Component) focusOrderList.get(focusOrderList.size() - 1);
	}
	
	/**
	 * @see java.awt.FocusTraversalPolicy#getDefaultComponent(Container)
	 */
	public Component getDefaultComponent(Container focusCycleRoot)
	{
		return defaultComponent;
	}
}
