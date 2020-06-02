/*
 * Created on 14/02/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package components;

import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentTag;

/**
 * @author odedn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ImageButtonTag extends UIComponentTag {

	private String align;
	private String src;
	private String action;
	private String actionListener;
	/**
	 * 
	 */
	public ImageButtonTag() {
		super();
		// TODO Auto-generated constructor stub		
	}

	/* (non-Javadoc)
	 * @see javax.faces.webapp.UIComponentTag#getComponentType()
	 */
	public String getComponentType() {
		// TODO Auto-generated method stub
		return "components.ImageButton";
	}

	/* (non-Javadoc)
	 * @see javax.faces.webapp.UIComponentTag#getRendererType()
	 */
	public String getRendererType() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return Returns the align.
	 */
	public String getAlign() {
		return align;
	}
	/**
	 * @param align The align to set.
	 */
	public void setAlign(String align) {
		this.align = align;
	}
	/**
	 * @return Returns the src.
	 */
	public String getSrc() {
		return src;
	}
	/**
	 * @param src The src to set.
	 */
	public void setSrc(String src) {
		this.src = src;
	}
	
	public void release()
	{
		super.release();
	}
	/**
	 * @return Returns the action.
	 */
	public String getAction() {
		return action;
	}
	/**
	 * @param action The action to set.
	 */
	public void setAction(String action) {
		this.action = action;
	}
	/**
	 * @return Returns the actionListener.
	 */
	public String getActionListener() {
		return actionListener;
	}
	/**
	 * @param actionListener The actionListener to set.
	 */
	public void setActionListener(String actionListener) {
		this.actionListener = actionListener;
	}
	
	public void setProperties(UIComponent component) {
		// always call the superclass method
		super.setProperties(component);
	      
		TagUtils.setStringProperty(component,"src", src);
		TagUtils.setStringProperty(component,"align", align);
		TagUtils.setActionProperty(component,action);
		TagUtils.setActionListenerProperty(component,actionListener);
		
	}

	 
	
	 
}
