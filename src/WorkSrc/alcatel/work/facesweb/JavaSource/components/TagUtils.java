/*
 * Created on 16/02/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package components;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;

/**
 * @author odedn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TagUtils {

	/**
	 * 
	 */
	private TagUtils() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static void setStringProperty(UIComponent comp, String name, String value)
	{
		if(value != null) {
			comp.getAttributes().put(name,value);
		}
	}
	
	/**
	 * set the action property.
	 * @param comp the UIComponent.
	 * @param action the action name.
	 */
	public static void setActionProperty(UIComponent comp, String action)
	{
		if(action != null) {
			MethodBinding mb = FacesContext.getCurrentInstance().getApplication().createMethodBinding(action,null);
			((UICommand)comp).setAction(mb);
		}
	}

	/**
	 * set the action property.
	 * @param comp the UIComponent.
	 * @param actionListener the action name.
	 */
	public static void setActionListenerProperty(UIComponent comp, String actionListener)
	{
		if(actionListener != null) {
			Class args[] = {ActionEvent.class};
			MethodBinding mb = FacesContext.getCurrentInstance().getApplication().createMethodBinding(actionListener,args);		
			((UICommand)comp).setActionListener(mb);
		}
	}
	
	/**
	 * Set a value binding value.
	 * @param comp the UIComponent
	 * @param property the string to be bound
	 */
	public static void setValueBinding(UIComponent comp, String property, String obj)
	{
		System.out.println("trying to bind : " + obj);
		if(obj != null) {
			ValueBinding vb = FacesContext.getCurrentInstance().getApplication().createValueBinding(obj);
			comp.setValueBinding(property,vb);
			System.out.println("bound: " + property + "=" + obj);
		}
	}
}
