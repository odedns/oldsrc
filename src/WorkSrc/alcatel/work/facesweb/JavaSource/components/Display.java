/* Created on 20/03/2006 */
package components;

import java.io.IOException;
import java.util.List;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import beans.JsfUtil;


/**
 * 
 * @author Odedn
 */
public class Display extends UIComponentBase
{

	private boolean displayState = true;
	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#getFamily()
	 */
	public String getFamily()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#decode(javax.faces.context.FacesContext)
	 */
	public void decode(FacesContext arg0)
	{
		// TODO Auto-generated method stub
		super.decode(arg0);
	}
	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext ctx) throws IOException
	{
		// TODO Auto-generated method stub
		super.encodeBegin(ctx);
		System.out.println(">Display encodeBegin");
		System.out.println("displayState = " + displayState);
		ValueBinding vb = getValueBinding("value");
		Boolean b = (Boolean) vb.getValue(ctx);
		System.out.println("b = " + b);
		setDisplayState(b.booleanValue()); 
		
		
	}
	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext ctx) throws IOException
	{
		// TODO Auto-generated method stub
		super.encodeChildren(ctx);
		System.out.println(">Display encodeChildren");
		System.out.println("displayState = " + displayState);
		List l = this.getChildren();
		Iterator iter = l.iterator();
		while(iter.hasNext()) {
			UIComponent c = (UIComponent) iter.next();
			if(!displayState) {
				c.setRendered(false);				
			} else {
				JsfUtil.encodeComponent(ctx,c);
			}
		}	

	}
	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext arg0) throws IOException
	{
		// TODO Auto-generated method stub
		super.encodeEnd(arg0);
		System.out.println(">Display encodeEnd");
	}
	/* (non-Javadoc)
	 * @see javax.faces.component.StateHolder#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state)
	{
		// TODO Auto-generated method stub		
		Object values[] = (Object[]) state;
		super.restoreState(ctx,values[0]);
		Boolean b = (Boolean) values[1];
		displayState = b.booleanValue();

	}
	/* (non-Javadoc)
	 * @see javax.faces.component.StateHolder#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx)
	{
		// TODO Auto-generated method stub		
		Object values[] = new Object[2];
		values[0] = super.saveState(ctx);
		values[1] = new Boolean(displayState);
		return(values);
	}
	
	/**
	 * Getter for displayState. <br>
	 * 
	 * @return displayState
	 */
	public boolean isDisplayState()
	{
		return displayState;
	}
	/**
	 * Setter for displayState. <br>
	 * 
	 * @param displayState the displayState to set
	 */
	public void setDisplayState(boolean displayState)
	{
		this.displayState = displayState;
	}
	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#getRendersChildren()
	 */
	public boolean getRendersChildren()
	{
		// TODO Auto-generated method stub
		return true;
	}
}
