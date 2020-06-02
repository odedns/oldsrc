package mataf.conversions;

import com.ibm.dse.dw.model.*;
import com.ibm.dse.dw.cm.*;
import mataf.utils.*;
import java.util.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FldscrHandler extends WBBasicHandler {

	String structFieldFrom = null;
	String structFieldTo = null;
	String flagField = null;
	boolean isMashovFld = false;
	String displayOnly;
	String mashovLen;
		
	/**
	 * Constructor for FldscrHandler.
	 * @param eInfo
	 */
	public FldscrHandler() 
	{
		super();
	}


	/**
	 * convert the values for MatafTable Check 
	 * domain.
	 */
	private String cvtTable(String val)
	{
		String s = null;
		if(null == val) {
			return(s);
		}
		
		if(val.equals("î")) {
			s = "value in table";
		} else {
			if(val.equals("à")) {
				s = "value not in table";
			}
		}
		return(s);
	}
	
	/**
	 * convert to sifrat bikoret domain.
	 */
	private String cvtBikoret(String val)
	{
		String s = null;
		if(null == val) {			
			return(s);
		}			
		if(val.equals("10")) {
			s = "modulo 10";	
		} else {
			if(val.equals("11")) {
				s = "modulo 11";	
			}
			
		}
		return(s);		
		
	}
	
	/**
	 * convert to external device domain.
	 */
	private String cvtExternalDevice(String val)
	{
		String s = null;
		if(null == val || val.length() == 0) {
			return(s);
		}			
		char c = val.charAt(0);
		switch (c) {
			case '0':
				s = null;
				break;
			case '1':
				s = "MSR";
				break;
			case '2':
				s = "optical reader";
				break;
			case '3':
				s = "check reader";
				break;
				
		} // switch 			
		return(s);		
	}
	
	
	private String cvtFilter(String val)
	{
		String s = null;
		if(null == val || val.length() == 0) {
			return(s);
		}			
		char c = val.charAt(0);
		switch (c) {
			case ' ':
				s = null;
				break;
			case '>':			
				s = "GT";
				break;			
			case '=':
				s = "EQ";
				break;		
			case '<':
				s = "LT";
				break;				
			case 243:
				s = "LE";
				break;
			default:
				s = null;
				break;

		} // switch 			
		return(s);			
		
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
			if(name.equals("engName")) {
				value = (String) fields[i].getValue();
				fields[i].setIgnore(true);
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

		if(name.equals("isMashovField")) {		
			String s = ValueConversions.cvtYn(value);
			Boolean b = new Boolean(s);
			isMashovFld = b.booleanValue();						
			return;
		}

		if(name.equals("mashovFieldLen")) {		
			if(isMashovFld) {
				mashovLen = value;	
			}
			return;
		}

		if(name.equals("displayOnly")) {		
			if(isMashovFld) {
				displayOnly = value;
				try {
					createResposeField(m_id, mashovLen, displayOnly);
				} catch(Exception e) {
					e.printStackTrace();	
				}
			}
			return;
		}

		if(name.equals("intableCheck")) {		
			value = ValueConversions.cvtAllowDeny(value);
			ins.setValue(ent.getAttribute(name),value);
			return;
		}

		if(name.equals("checksum")) {		
			value = cvtBikoret(value);
//			System.out.println("FldscrHandler name=" + name + " value=" + value);
			ins.setValue(ent.getAttribute(name),value);
			return;
		}
		
		if(name.equals("externDevice")) {		
			value = cvtExternalDevice(value);
//			System.out.println("FldscrHandler name=" + name + " value=" + value);
			ins.setValue(ent.getAttribute(name),value);
			return;
		}
		if(name.equals("intableCheck")) {		
			value = cvtTable(value);
//			System.out.println("FldscrHandler name=" + name + " value=" + value);
			ins.setValue(ent.getAttribute(name),value);
			return;
		}
		
		if(name.equals("select1Filter") || name.equals("select2Filter") ||
			name.equals("intableFilter")) {		
			value = cvtFilter(value);
//			System.out.println("FldscrHandler name=" + name + " value=" + value);
			ins.setValue(ent.getAttribute(name),value);
			return;
		}
				
		if(name.equals("rangeFieldTo")) {
			structFieldTo	= value.trim();
			return;
		}
		if(name.equals("rangeFieldStructTo")) {
			if(value.trim().length() > 0) {				
				structFieldTo= value + "." + structFieldTo;
			}
			return;
		}
		if(name.equals("rangeStructTo")) {
			if(value.trim().length() > 0) {				
				structFieldTo= value + "." + structFieldTo;
			}
			value = structFieldTo;
			name = "rangeFieldFrom";
//			System.out.println("FldscrHandler name=" + name + " value=" + value);
			ins.setValue(ent.getAttribute(name),value);
			structFieldTo = null;
			return;

		}

		if(name.equals("rangeFieldFrom")) {
			structFieldFrom	= value.trim();
			return;
		}
		
		if(name.equals("rangeFieldStructFrom")) {
			if(value.trim().length() > 0) {				
				structFieldFrom= value + "." + structFieldFrom;
			}
			return;
		}
		if(name.equals("rangeStructFrom")) {
			if(value.trim().length() > 0) {				
				structFieldFrom= value + "." + structFieldFrom;
			}
			name = "rangeFieldTo";
			value = structFieldFrom;
//			System.out.println("FldscrHandler name=" + name + " value=" + value);
			ins.setValue(ent.getAttribute(name),value);
			structFieldFrom = null;
			return;

		}
		
		/**
		 * handle flag field.
		 */
		if(name.equals("flagField")) {
			flagField = value.trim();
			return;
		}
		
		if(name.equals("flagStructFldName")) {
			if(value.trim().length() > 0) {				
				flagField = value + "." + flagField;
			}
			return;
		}
		if(name.equals("flagStructName")) {
			if(value.trim().length() > 0) {				
				flagField = value + "." + flagField;
			}
			value = flagField;
			name = "flagField";
		}
		
//		System.out.println("FldscrHandler name=" + name + " value=" + value);
		ins.setValue(ent.getAttribute(name),value);
		flagField = null;
	}
	

	private void createResposeField(String fldName, String len, String displayOnly)
		throws Exception
	{
		System.out.println("adding response field: name: " + fldName);
		Workspace wks = WBConnectionManager.getWorkspace();
		Entity ent = wks.getEntity("Mataf response field");		
		Instance myIns = wks.getModelFactoryImpl().createInstance();
		DefaultGroupPath grp = new DefaultGroupPath("MatafDev.Tests");
		myIns.setEntity(ent);
		myIns.setName("_" + fldName);
		myIns.setOwner(wks.getUser("odedn"));
		myIns.setUsersGroup(wks.getUsersGroup("default"));		
		myIns.setParent(wks.getGroup(new DefaultGroupPath("MatafDev.Data.Fields.Other")));
		myIns.setValue(ent.getAttribute("id"),"_" + fldName);
		myIns.setValue(ent.getAttribute("length"), len);
		myIns.setValue(ent.getAttribute("displayOnly"), displayOnly);
		myIns.setValue(ent.getAttribute("refField"), fldName);
		myIns.save();		

	}	

}
