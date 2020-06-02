/**
 * Created on 15/03/2005
 * @author P0006439
 * @version $id$
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package beans;

import java.util.*;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import jsfdemo.MovieBean;

import components.MenuItem;


public class SharedBean
{
	String user;
	String password;
	Float balance;
	List items;
	/**
	 * 
	 */
	public SharedBean()
	{
		super();
		// TODO Auto-generated constructor stub
		items = new ArrayList();
		items.add(new MenuItem("Item1", "Item1", "#{sharedBean.item1Action}"));
		items.add(new MenuItem("Item2", "Item2", "#{sharedBean.item2Action}"));
		items.add(new MenuItem("Item3", "Item3", "#{sharedBean.item3Action}"));
		
		System.out.println("SharedBean : created items");
	}

	/**
	 * @return
	 */
	public Float getBalance()
	{
		return balance;
	}

	/**
	 * @return
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @return
	 */
	public String getUser()
	{
		return user;
	}

	/**
	 * @param float1
	 */
	public void setBalance(Float float1)
	{
		balance = float1;
	}

	/**
	 * @param string
	 */
	public void setPassword(String string)
	{
		password = string;
	}

	/**
	 * @param string
	 */
	public void setUser(String string)
	{
		user = string;
	}
	
	public String login()
	{
		setBalance(new Float(99.99));
		return("success");	
	}
	
	public String catalog()
	{
		System.out.println("sharedBean.catalog()");
		
		MovieBean mbean = (MovieBean) JsfUtil.getManagedBean("#{movieBean}");
		if(mbean == null) {
			System.err.println("could not initialize MovieBean");
		}
		mbean.setStore("BlockBuster");		
		return("catalog");	
	}
	
	public String testAction()
	{
		System.out.println("***** Test action invoked *****");
		return(null);
	}
	

	public String item1Action()
	{
		System.out.println("***** Item1 action invoked ******");
		return(null);
	}
	
	public String item2Action()
	{
		System.out.println("***** Item2 action invoked *****");
		return(null);
	}
	public String item3Action()
	{
		System.out.println("***** Item3 action invoked *****");
		return(null);
	}
	public List getItems()
	{
		System.out.println("***** getItems called ******");
		return(items);
	}
	
	
	public Boolean getDisplayState()
	{
		System.out.println("***** getDisplayState ******");
		return(Boolean.TRUE);
	}

}
