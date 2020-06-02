package mataf.conversions;
import mataf.utils.*;
import com.ibm.dse.dw.model.*;


/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class StructDetailsHandler extends WBBasicHandler {
	
	boolean mashovFld;
	/**
	 * Constructor for StructDetailsHandler.
	 */
	public StructDetailsHandler() {
		super();
	}

	/**
	 * return the Id for the new instance
	 * by searching the field array for a 
	 * specific field.
	 * In most cases: engName.
	 */
	protected String getId(BtField fields[])
	{
		String name=null;
		String value=null;
		for(int i=0; i < fields.length; ++i) {
			name = fields[i].getName();
			if(name.equals("refId")) {
				value = (String) fields[i].getValue();
				// fields[i].setIgnore(true);
				break;
			}		
		}
		return(value);
	}	
	/**
	 * @see mataf.conversions.WBBasicHandler#handleAttributes(Entity, Instance, String, String)
	 */
	public void handleAttributes(Entity ent,Instance ins,String name,String value) 
	{
		System.out.println("StrucHandler name=" + name + " value=" + value);
		if(name.equals("mashovFld")) {			
			Boolean b = new Boolean(value);
			mashovFld = b.booleanValue();	
		}
		if(name.equals("refId")) {			
			/**
			 * if we have a mashov field then
			 * create a refId to the mashov field.
			 */
			if(mashovFld) {
				value = "_" + value;	
			}
			ins.setValue(ent.getAttribute(name),value);			
			return;
		}						
	}
	
	/**
	 * after addInstance.
	 * This call back is called after addInstance.
	 * If we want to dot something after the instance has been
	 * added - this is the place.
	 */
	protected void afterAddInstance(Instance ins, BtField fields[])
	{
		String name=null;
		String value = null;
		String len = null;
		for(int i=0; i < fields.length; ++i ) {
			name = fields[i].getName();
			value = (String) fields[i].getValue();				
			if(name.equals("refId")) {
				continue;	
			}
			if(name.equals("lenAfterDot")) {
				if(value.length() > 0) {
					len = value;	
				}
				continue;
			}
			/* handle length */
			if(name.equals("lenBeforeDot")) {				
				name = "length";
				if(len != null) {
					len = value + "." + len;
					value = len;
				}
			}	
			if(name.equals("type")) {	
				String type = ValueConversions.getFieldType(value.trim());
				if(null == type) {
					type = value;	
				}
				value = type;		
			}	

			try {
				createParam(ins,name,value);
			} catch(Exception e) {
				e.printStackTrace();					
			}
			
		}  //for		
	}
	
	/**
	 * create a parameter instance.
	 */
	private void createParam(Instance parent, String name, String value) throws Exception
	{
		System.out.println("adding reference param name: " + name + " value:" + value);
		System.out.println("parent = " + parent.getPath().toString());
		Workspace wks = WBConnectionManager.getWorkspace();
		Entity ent = wks.getEntity("Parameter");		
		Instance myIns = wks.getModelFactoryImpl().createInstance();
		myIns.setEntity(ent);
		myIns.setName(name);
		myIns.setOwner(wks.getUser("odedn"));
		myIns.setUsersGroup(wks.getUsersGroup("default"));		
		myIns.setParent(parent);
		myIns.setValue(ent.getAttribute("id"),name);
		myIns.setValue(ent.getAttribute("value"), value);
		myIns.save();		
	}

}
