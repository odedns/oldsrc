/*
 * Created on 21/02/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package components;

import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;

import beans.JsfUtil;

import java.io.IOException;
import java.util.*;

/**
 * @author odedn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UIMenu extends UICommand {
	List items;
	MenuItem m_selectedItem;
	/**
	 * 
	 */
	public UIMenu() {
		super();
		// TODO Auto-generated constructor stub
		setRendererType("components.UIMenu");
	}

	
	public void setMenuItems(List items)
	{
		this.items = items;
	}
	
	/**
	 * check if the item exists int the item list.
	 * @param item the item to check
	 * @return boolean
	 */
	public MenuItem findItemByName(String name)
	{
		MenuItem item = null;
		Iterator iter = items.iterator();
		while(iter.hasNext()) {
			MenuItem it = (MenuItem) iter.next();
			if(it.getName().equals(name)) {
				item = it;
				break;
			}
			
		}
		return(item);
	}
	/**
	 * add a menu item to the list of items
	 * @param item the item to add.
	 */
	public void addMenuItem(MenuItem item)
	{
		if(items == null) {
			items = new ArrayList();
		}
		if(null == findItemByName(item.getName())) {
			items.add(item);
			System.out.println("adding : " + item.getName() + "size=" + items.size());
		}
	}
	/**
	 * set the selected menu item.
	 * @param item
	 */
	public void setSelectedItem(MenuItem item)
	{
		m_selectedItem = item;
	}
	
	/**
	 * get the selected menu Item.
	 * @return MenuItem 
	 */
	public MenuItem getSelectedItem()
	{
		return(m_selectedItem);
	}

	
	
	public List getMenutItemList(FacesContext ctx)
	{
		List l = null;
		ValueBinding vb = getValueBinding("value");
		if(null != vb) {
			l = (List) vb.getValue(ctx);
			setMenuItems(l);
		} else {
			l = items;
		}
		return(l);
	}

	
		/* (non-Javadoc)
	 * @see javax.faces.component.StateHolder#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state)
	{
		// TODO Auto-generated method stub		
		Object values[] = (Object[]) state;
		super.restoreState(ctx,values[0]);
		items = (List) values[1];
	}
	/* (non-Javadoc)
	 * @see javax.faces.component.StateHolder#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx)
	{
		// TODO Auto-generated method stub
		Object values[] = new Object[2];
		values[0] = super.saveState(ctx);
		values[1] = items;
		return(values);
	}
}
