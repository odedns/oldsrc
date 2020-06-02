/**
 * Created on 15/03/2005
 * @author P0006439
 * @version $id$
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package beans;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.http.HttpSession;

import jmonitor.WasadminBean;

import org.apache.log4j.Logger;


/**
 * @author Oded
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class JsfUtil
{
	private final static Logger logger = Logger.getLogger(JsfUtil.class);
	/**
	 * 
	 */
	private JsfUtil()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public static ValueBinding getValueBinding(String valueRef)
	{
			ValueBinding vb = null;
			FacesContext context = FacesContext.getCurrentInstance();
			vb = context.getApplication().createValueBinding(valueRef);
			return vb;
	}
	
	/**
	 * get the managed bean by name.
	 * @param valueRef the name of the bean.
	 * @return Object the managed bean.
	 */
	public static Object getManagedBean(String valueRef)
	{
		ValueBinding binding = getValueBinding(valueRef);
		Object o = binding.getValue(FacesContext.getCurrentInstance());
		return(o);	
	
	}
	
	/**
	 * set the value of the managed bean binding.
	 * @param o the object representing the managed bean.
	 * @param valueRef the name of the bean. #{bean-name}
	 */
	public static void setManagedBean(String valueRef,Object o)
	{
		ValueBinding vb = getValueBinding(valueRef);
		vb.setValue(FacesContext.getCurrentInstance(),o);
		
	}
	/**
	 * find a component in the view tree.
	 * @param form
	 * @param name
	 * @return a UIComponent
	 */
	public static UIComponent findComponent(String form, String name)
	{
		FacesContext context = FacesContext.getCurrentInstance();
		UIViewRoot root = context.getViewRoot();
		
		UIComponent f = root.findComponent(form);
		if(null == form) {
			return(null);
		}
		UIComponent comp = f.findComponent(name);
		return(comp);
		
	}
	/**
	 * get the parent form for the component.
	 * @param comp the ui componenet
	 * @return the UIForm that is the parent of this
	 * component.
	 */
	public static UIForm getParentForm(UIComponent comp)
	{
		UIComponent parent = comp.getParent();
		while(parent != null) {
			if(parent instanceof UIForm) {
				break;
			}
			parent = parent.getParent();
		}
		return((UIForm) parent);
	}
	
	/**
	 * activate a value binding method.
	 * @param value a String representing the value binding method.
	 * @return Object the object returned from the method.
	 */
	public static Object activateBindingMethod(String value)
	{
		ValueBinding vb = FacesContext.getCurrentInstance().getApplication().createValueBinding(value);
		Object o = vb.getValue(FacesContext.getCurrentInstance());
		return(o);
	}
	
	/**
	 * encode a component by calling the encode methods
	 * on the component.
	 * @param ctx the FacesContext
	 * @param comp the Component to encode.
	 * @throws IOException in case of error.
	 */
	public static void encodeComponent(FacesContext ctx,UIComponent comp)
		throws IOException
	{
		comp.encodeBegin(ctx);
		comp.encodeChildren(ctx);
		comp.encodeEnd(ctx);
	}
	
	/**
	 * Get message from the resource bundle.
	 * @param bundleName the resource bundle name.
	 * @param key the message key.
	 * @param params message params.
	 * @param locale the Locale to use.
	 * @return String the formatted message.
	 */
	public static String getMessageFromBundle(String bundleName, String key, Object params[], Locale locale)
	{
		logger.debug("getMessageFromBundle: bundleName=" + bundleName + "key=" + key);		
		String msg = null;
		String txt = null;
		
		ResourceBundle rb = ResourceBundle.getBundle(bundleName, locale);
		if(rb == null) {
			logger.error("Cannot find resourceBundle: " + bundleName);
			return(null);
		}
		try {
			msg = rb.getString(key);			
		} catch(MissingResourceException me){
			msg = "Cannot find message key: " + key;
		}
		if(params != null) {
			MessageFormat mf = new MessageFormat(txt);
			msg = mf.format(params,new StringBuffer(),null).toString();
		}
		
		return(msg);
	}
	
	/**
	 * add a message to the message list.
	 * @param msg the message to add.
	 */
	public static void addMessage(String msg)
	{
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(msg,msg));
	}
	
	/**
	 * get the session bean hash map.
	 * @return Map 
	 */
	public static Map getSessionBean()
	{
		Map m = (Map) getManagedBean("#{sessionBean}");
		return(m);
	}
	
	/**
	 * get the HttpSession object
	 * @return HttpSession
	 */
	public static HttpSession getSession()
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		return(session);
	}
	
	/**
	 * create an HttpSession
	 * @return HttpSession
	 */
	public static HttpSession createSession()
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return(session);
	}
	
	/**
	 * invalidate the httpSession 
	 * for this application.
	 */
	public static void invalidateSession()
	{
		HttpSession session = getSession();
		session.invalidate();
	}
}

