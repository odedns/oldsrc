/*
 * Created on 14/02/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package components;

import java.io.IOException;

import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;

/**
 * @author odedn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ImageButton extends UICommand {

	private static final String SUFFIX = "_imageButton";
	private String name=null;
	
	/**
	 * contructor.
	 */
	public ImageButton() {
		super();
		// TODO Auto-generated constructor stub
		setRendererType(null);
	
	}
	

	
	/**
	 * @see javax.faces.component.UICommand 
	 */
	public void encodeEnd(FacesContext context) throws IOException {
		System.out.println("imageButton encode begin called");		
		ResponseWriter writer = context.getResponseWriter();
		String clientId = getClientId(context);
		name = clientId + SUFFIX;		
		writer.startElement("INPUT", this);
		writer.writeAttribute("name",name,"clientId");
		writer.writeAttribute("type","image","image");
		String src = (String) getAttributes().get("src");
		writer.writeAttribute("src",src,"src");
		// MethodBinding mb = getAction();		
		writer.endElement("INPUT");
	}

	/**
	 * decode the input params.
	 * queue the action event.
	 * @param context the FacesContext
	 */
	public void decode(FacesContext context) {
		// super.decode(context);
		System.out.println("ImageButton decode");
		String val = (String) context.getExternalContext().getRequestParameterMap().get(name + ".x");
		/*
		 * we queue the event only if button was pressed.
		 */
		if(val != null) {
			System.out.println("ImageButton decode name = " + name + "\tval = " + val);
			this.queueEvent(new ActionEvent(this));
		}
		
		
	}
	

}
