/* Created on 20/03/2006 */
package components;

import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentTag;

/**
 * 
 * @author Odedn
 */
public class DisplayTag extends UIComponentTag
{	
	private String value;
	
	/* (non-Javadoc)
	 * @see javax.faces.webapp.UIComponentTag#getComponentType()
	 */
	public String getComponentType()
	{
		// TODO Auto-generated method stub
		return "components.Display";
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
	 * @see javax.faces.webapp.UIComponentTag#setProperties(javax.faces.component.UIComponent)
	 */
	protected void setProperties(UIComponent component)
	{
		// TODO Auto-generated method stub
		super.setProperties(component);
		System.out.println("Display setProperties() value= " + value);
		TagUtils.setValueBinding(component,"value",value);
	}
	/**
	 * Getter for value. <br>
	 * 
	 * @return value
	 */
	public String getValue()
	{
		return value;
	}
	/**
	 * Setter for value. <br>
	 * 
	 * @param value the value to set
	 */
	public void setValue(String value)
	{
		this.value = value;
	}
}
