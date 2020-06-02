/* Created on 29/03/2006 */
package components;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;
import javax.faces.render.Renderer;

import beans.JsfUtil;

/**
 * 
 * @author odedn
 */
public class UIMenuRenderer extends Renderer {

	private String m_hiddenName = null;
	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#decode(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void decode(FacesContext ctx, UIComponent comp)
	{
		// TODO Auto-generated method stub		
		
		System.out.println("UIMenu decode");
		String val = (String) ctx.getExternalContext().getRequestParameterMap().get(m_hiddenName);
		System.out.println(m_hiddenName + "= " + val);
		MenuItem item = ((UIMenu)comp).findItemByName(val);
		if(null != item) {			
			((UIMenu)comp).setSelectedItem(item);
			System.out.println("selected items: name= " + item.getName() + "\taction =" + item.getAction());
			TagUtils.setActionProperty(comp,item.getAction());
			comp.queueEvent(new ActionEvent(comp));
		}
	}
	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#encodeEnd(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	public void encodeEnd(FacesContext ctx, UIComponent comp)
			throws IOException
	{
		m_hiddenName = comp.getClientId(ctx) + "_op";		
		// TODO Auto-generated method stub
		System.out.println("encodeEnd called");		
		String formName = JsfUtil.getParentForm(comp).getId();
		ResponseWriter writer = ctx.getResponseWriter();
		writer.startElement("input",comp);
		writer.writeAttribute("type","hidden", null);
		writer.writeAttribute("name",m_hiddenName,null);
		writer.endElement("input");
		writer.startElement("ul",comp);
		writer.writeAttribute("id","topmenu",null);
		writer.startElement("li", comp);
		writer.writeAttribute("class","sub",null);
		writer.startElement("a",comp);
		writer.writeAttribute("href","#",null);
		writer.write((String) comp.getAttributes().get("label"));
		writer.endElement("a");
		// writer.endElement("li");
		
			List l = ((UIMenu)comp).getMenutItemList(ctx);
			if(l != null) {
				writer.startElement("ul",comp);
				Iterator iter = l.iterator();
				while(iter.hasNext()) {
					MenuItem item = (MenuItem) iter.next();					
					writer.startElement("li", comp);
					
					writer.writeAttribute("onclick","document.forms['" +formName + "']['"+ 
							m_hiddenName + "'].value='" + item.getName() + "';document.forms['" + formName + "']" + ".submit();",null);
					writer.startElement("a",comp);
					writer.writeAttribute("href","#",null);
					writer.write(item.getLabel());
					writer.endElement("a");
					writer.endElement("li");
				} //while
				writer.endElement("ul");
			} // if
			writer.endElement("li");
		
		writer.endElement("ul");
		
	
	}
}
