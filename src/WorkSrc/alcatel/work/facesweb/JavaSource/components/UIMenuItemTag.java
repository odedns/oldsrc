/* Created on 07/03/2006 */
package components;

import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;

/**
 * 
 * @author Odedn
 */
public class UIMenuItemTag extends UIComponentTag
{

	private String label;
	private String action;
	
	/* (non-Javadoc)
	 * @see javax.faces.webapp.UIComponentTag#getComponentType()
	 */
	public String getComponentType()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.faces.webapp.UIComponentTag#getRendererType()
	 */
	public String getRendererType()
	{
		// TODO Auto-generated method stub
		return null;
	}

	
	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public int doStartTag() throws JspException
	{
		// TODO Auto-generated method stub
		System.out.println("in doStartTag");
		System.out.println("id= " + getId() + "\tlabel=" + label);
		
		UIComponentTag parent = getParentUIComponentTag(pageContext);
		if(parent == null || ! (parent instanceof UIMenuTag)) {
			throw new JspException("Parent tag is no a UIMenuTag");
		}
		UIMenuTag menuTag = (UIMenuTag) parent;
		UIMenu menu = (UIMenu) menuTag.getComponentInstance();
		MenuItem item = new MenuItem(getId(),label,action);
		menu.addMenuItem(item);
		return (getDoStartValue());
	}
	/**
	 * Getter for action. <br>
	 * 
	 * @return action
	 */
	public String getAction()
	{
		return action;
	}
	/**
	 * Setter for action. <br>
	 * 
	 * @param action the action to set
	 */
	public void setAction(String action)
	{
		this.action = action;
	}
	/**
	 * Getter for label. <br>
	 * 
	 * @return label
	 */
	public String getLabel()
	{
		return label;
	}
	/**
	 * Setter for label. <br>
	 * 
	 * @param label the label to set
	 */
	public void setLabel(String label)
	{
		this.label = label;
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	public int doEndTag() throws JspException
	{
		// TODO Auto-generated method stub
		
		return (getDoEndValue());
	}
	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.Tag#release()
	 */
	public void release()
	{
		// TODO Auto-generated method stub
		super.release();
		label = null;
		action = null;
	}
}
