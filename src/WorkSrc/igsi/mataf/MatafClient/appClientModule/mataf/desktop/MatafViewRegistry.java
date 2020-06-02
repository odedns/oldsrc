/*
 * Created on 20/04/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mataf.desktop;
import mataf.desktop.views.MatafClientView;

import com.ibm.dse.gui.CoordinatedPanel;
/**
 * @author eyalbz
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MatafViewRegistry {

	private CoordinatedPanel view = null;
	private String parentView = null;
	private String previousView = null;
	private String nextView = null;
	private int navigationType = 0;
	private String currentState = null;
	private MatafClientView owner = null;
	private boolean unchain = true;
	private boolean subview = false;

	public MatafViewRegistry() {
		super();
	}

	/**
	 * Gets the current state of the ViewRegistry's view.
	 * @return java.lang.String
	 */
	public String getCurrentState() {
		return currentState;
	}
	/**
	 * Gets the navigation type of the ViewRegistry's view.
	 * @return int
	 */
	public int getNavigationType() {
		return navigationType;
	}
	/**
	 * Gets the nextView of the ViewRegistry's view.
	 * @return java.lang.String
	 */
	public String getNextView() {
		return nextView;
	}
	/**
	 * Gets the Container of the ViewRegistry's view.
	 * @return java.awt.Container
	 */
	public MatafClientView getOwner() {
		return owner;
	}
	/**
	 * Gets the parent view of the ViewRegistry's view.
	 * @return String
	 */
	public String getParentView() {
		return parentView;
	}
	/**
	 * Gets the previous view of the ViewRegistry's view.
	 * @return java.lang.String
	 */
	public String getPreviousView() {
		return previousView;
	}
	/**
	 * Gets the boolean that describes whether the ViewRegistry's view is a subview.
	 * @return boolean
	 */
	public boolean getSubview() {
		return subview;
	}
	/**
	 * Gets the boolean that describes whether the ViewRegistry's view is unchained from the context.
	 * @return boolean
	 */
	public boolean getUnchain() {
		return unchain;
	}
	/**
	 * Gets the JPanel of the ViewRegistry's view.
	 * @return CoordinatedPanel
	 */
	public CoordinatedPanel getView() {
		return view;
	}
	
	/**
	 * Sets the current state of the ViewRegistry's view.
	 * @param o java.lang.Object
	 */
	public void setCurrentState(Object o) {
		currentState = (String) o;
	}
	/**
	 * Sets the navigation type related to the ViewRegistry's view.
	 * @param o java.lang.Object
	 */
	public void setNavigationType(int i) {
		navigationType = i;
	}
	/**
	 * Sets the next view associated with the ViewRegistry's view.
	 * @param newValue boolean
	 */
	public void setNextView(Object o) {
		nextView = (String) o;
	}
	/**
	 * Sets the owner of the ViewRegistry's view.
	 * @param o java.lang.Object
	 */
	public void setOwner(Object o) {
		owner = (MatafClientView) o;
	}
	/**
	 * Sets the parent view associated with the ViewRegistry's view.
	 * @param newValue Object
	 */
	public void setParentView(Object newValue) {
		parentView = (String) newValue;
	}
	/**
	 * Sets the previous view associated with the ViewRegistry's view.
	 * @param Object
	 */
	public void setPreviousView(Object o) {
		previousView = (String) o;
	}
	/**
	 * Sets whether the ViewRegistry's view is a subview.
	 * @param newSubview boolean
	 */
	public void setSubview(boolean newSubview) {
		subview = newSubview;
	}
	/**
	 * Sets whether the ViewRegistry's view is unchained from the context.
	 * @param boolean
	 */
	public void setUnchain(boolean b) {
		unchain = b;
	}
	/**
	 * Sets the view that the ViewRegistry defines.
	 * @param o java.lang.Object
	 */
	public void setView(Object o) {
		view = (CoordinatedPanel) o;
	}
}