/*
 * Created on 21/02/2006
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
public class UIMenuTag extends UIComponentTag {

	private String value;
	private String label;
	private String styleClass;
	private String name;
	/**
	 * 
	 */
	public UIMenuTag() {
		super();
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see javax.faces.webapp.UIComponentTag#getComponentType()
	 */
	public String getComponentType() {
		// TODO Auto-generated method stub		
		return "components.UIMenu";
	}

	/* (non-Javadoc)
	 * @see javax.faces.webapp.UIComponentTag#getRendererType()
	 */
	public String getRendererType() {
		// TODO Auto-generated method stub
		return("components.UIMenu");
	}
	
	public void setProperties(UIComponent component) {
		System.out.println("setProperties called");
		// always call the superclass method
		super.setProperties(component);
		System.out.println("label=" + label + "\tvalue=" + value);		
		TagUtils.setStringProperty(component,"name", name);
		TagUtils.setStringProperty(component,"styleClass", styleClass);
		TagUtils.setStringProperty(component,"label", label);
		TagUtils.setValueBinding(component,"value", value);
	      
	}

	/**
	 * @return Returns the label.
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label The label to set.
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the styleClass.
	 */
	public String getStyleClass() {
		return styleClass;
	}
	/**
	 * @param styleClass The styleClass to set.
	 */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	/**
	 * @return Returns the value.
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value The value to set.
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
