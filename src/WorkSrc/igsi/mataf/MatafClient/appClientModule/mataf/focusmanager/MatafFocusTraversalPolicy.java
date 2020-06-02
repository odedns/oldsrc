package mataf.focusmanager;
import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.Window;
import java.util.Hashtable;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Vector;

import javax.swing.LayoutFocusTraversalPolicy;

/** 
 * This class is an abstract FocusTraversalPolicy that uses an ordered List 
 * of focusable components.
 * It builds two hashtables from the ordered list : <p>
 * First hashtable is used to determine the next focusable component, and
 * the second is used to determine the previous focusable component.
 *
 * Special condition for accept() should be specified here.
 * 
 * PENDING : Iterate through components in getComponentAfter() and getComponentBefore()
 * 				to find a component that 'passes' the accept() conditions.
 *
 * @author Nati Dikshtein. 
 */
public abstract class MatafFocusTraversalPolicy extends LayoutFocusTraversalPolicy
{
	/** Holds "component,next component" pairs.*/
	private Hashtable prevs = new Hashtable();
	/** Holds "component,previous component" pairs.*/
	private Hashtable nexts = new Hashtable();
	/** The ordered list of the focusable components.*/
	private List focusOrderList = new Vector();
	/** Determines if first component should be last component's next
	 *  focusable component.(and the other way around) */
	private boolean cycledFocus = false;
	
	private Component defaultComponent;
	
	public MatafFocusTraversalPolicy()
	{
		focusOrderList = new Vector();
	}
	
	/**
	 * Construct a FocusTraversalPolicy from an ordered List.
	 */
	public MatafFocusTraversalPolicy(List focusOrderList)
	{
		this(focusOrderList, true);
	}
	
	/**
	 * Construct a optionally cycled FocusTraversalPolicy from 
	 * an ordered List.
	 */
	public MatafFocusTraversalPolicy(List focusOrderList, boolean cycledFocus)
	{
		this.focusOrderList = focusOrderList;
		this.cycledFocus = cycledFocus;
		buildHashtables();
	}
	
	public void setNextAndPrevForComponent(Component c,Component next, Component prev)
	{
		nexts.put(c,next);
		prevs.put(c,prev);
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
		if(defaultComponent==null)
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
	 * A utility method that returns true if 
	 * Component c1 is after Component c2 in the focus
	 * traversal policy.
	 * 
	 * @param focusPolicy - The focus traversal policy in which c1 and c2 participate.
	 * @param c1 - First component.
	 * @param c2 - Second Component.
	 * @return
	 */
	public static boolean isComponentAfter(FocusTraversalPolicy focusPolicy, 
												Component c1, Component c2)
	{
		Container cycleRoot = c2.getFocusCycleRootAncestor();
		
		// Our stopping sign.
		Component firstComponent = focusPolicy.getFirstComponent(cycleRoot);
		
		// We start from c2 and going forward.
		Component nextComponent = focusPolicy.getComponentAfter(cycleRoot, c2);
		
		while(nextComponent!=firstComponent)
		{
			// We found that c1 IS after c2.
			if(nextComponent==c1)
				return true;
				
			// Promote our nextComponent through the focus cycle policy.
			nextComponent = focusPolicy.getComponentAfter(cycleRoot, nextComponent);
			
			// The focus cycle ended. 
			if(nextComponent==null)
				break;
		}
		
		// c1 could not be found AFTER c2 in the focus cycle policy.
		return false;
	}
	
		
	
	/**
	 * Sets the default component for this TraversalPolicy.
	 * Throws an exception if component is not part of the
	 * TraversalPolicy.
	 */
	public void setDefaultComponent(Component aComponent)
	{
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
	 * Looking for the next component to focus according to the following :
	 * 1.Check if next component exists in our hashtable.
	 * 2.If not, get the next component according to the default implementation.
	 * 
	 * Note : Component must 'pass' the accept() conditions or else the focus
	 *        won't be transfered to it.
	 * 
	 * @see javax.swing.LayoutFocusTraversalPolicy#getComponentAfter(Container, Component)
	 */
	public Component getComponentAfter(Container focusCycleRoot, Component aComponent) 
	{
		while(true)
		{
			Component c = (Component)nexts.get(aComponent);
			if(c==null)
				return super.getComponentAfter(focusCycleRoot, aComponent);
			if(accept(c))
				return c;
			aComponent = c;
		}
	}

	/**
	 * 
	 * Looking for the previous component to focus according to the following :
	 * 1.Check if next component exists in our hashtable.
	 * 2.If not, get the previous component according to the default implementation.
	 * 
	 * Note : Component must 'pass' the accept() conditions or else the focus
	 *        won't be transfered to it.
	 * @see javax.swing.LayoutFocusTraversalPolicy#getComponentBefore(Container, Component)
	 */
	public Component getComponentBefore(Container focusCycleRoot, Component aComponent) 
	{
		while(true)
		{
			Component c = (Component)prevs.get(aComponent);
			if(c==null)
				return super.getComponentBefore(focusCycleRoot, aComponent);
			if(accept(c))
				return c;
			aComponent = c;
		}
	}
	
	
	/**
	 * @see java.awt.FocusTraversalPolicy#getFirstComponent(Container)
	 */
	public Component getFirstComponent(Container focusCycleRoot)
	{
		return super.getFirstComponent(focusCycleRoot);
	}
	
	/**
	 * @see java.awt.FocusTraversalPolicy#getLastComponent(Container)
	 */
	public Component getLastComponent(Container focusCycleRoot)
	{
		return super.getLastComponent(focusCycleRoot);
	}
	
	/**
	 * @see java.awt.FocusTraversalPolicy#getDefaultComponent(Container)
	 */
	public Component getDefaultComponent(Container focusCycleRoot)
	{
		return defaultComponent;
	}
	
	/**
	 * @see java.awt.FocusTraversalPolicy#getInitialComponent(Window)
	 */
	public Component getInitialComponent(Window window)
	{
		return (Component) focusOrderList.get(0);
	}
	
	/**
	 * Specify special conditions here.
	 * 
	 * @see javax.swing.LayoutFocusTraversalPolicy#accept(Component)
	 */
	protected boolean accept(Component aComponent) 
	{
		return super.accept(aComponent);
	}

}
