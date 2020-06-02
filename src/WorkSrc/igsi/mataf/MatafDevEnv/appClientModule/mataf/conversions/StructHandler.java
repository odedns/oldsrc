package mataf.conversions;

import com.ibm.dse.dw.model.Entity;
import com.ibm.dse.dw.model.Instance;
import java.util.HashMap;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class StructHandler extends WBBasicHandler {

	HashMap m_convTable;
	HashMap m_recTypeTable;
	/**
	 * Constructor for StructHandler.
	 */
	public StructHandler() {
		super();
		initConvTable();
		initRecTypeTbl();
	}

	/**
	 * initialize conversion to Mataf Conversion
	 * domain type.
	 */
	private void initConvTable()
	{
		m_convTable = new HashMap();
		m_convTable.put("B","bulletin");
		m_convTable.put("O","old code");
		m_convTable.put("M","old code + bulletin");
		m_convTable.put("C","conv1");
		m_convTable.put("D","conv2");
		m_convTable.put("A","conv3");
		m_convTable.put("E","conv1 + conv2");				
	}
	
	
	/**
	 * Convert to Mataf record type domain.
	 */
	private void initRecTypeTbl()
	{
		m_recTypeTable = new HashMap();
		m_recTypeTable.put("0","general");
		m_recTypeTable.put("1","screen");
		m_recTypeTable.put("2","communication");
		m_recTypeTable.put("3","disk");
		m_recTypeTable.put("4","printer");
		m_recTypeTable.put("5","internal");
		m_recTypeTable.put("6","device");
		m_recTypeTable.put("7","table");
		m_recTypeTable.put("8","counter");
		m_recTypeTable.put("9","UREC");		
	}
	
	/**
	 * @see mataf.conversions.WBBasicHandler#handleAttributes(Entity, Instance, String, String)
	 */
	public void handleAttributes(Entity ent,Instance ins,String name,String value) 
	{
		String s = null;
		if(name.equals("conversion")) {
			s = (String)m_convTable.get(value);
			if(null == s) {
				s = "none";
			} 				
			value = s;
		}
		
		if(name.equals("type")) {
			s = (String)m_recTypeTable.get(value);
			if(null != s) {
				value = s;
			} 				
		}				
		System.out.println("StrucHandler name=" + name + " value=" + value);
		ins.setValue(ent.getAttribute(name),value);
	}

}
