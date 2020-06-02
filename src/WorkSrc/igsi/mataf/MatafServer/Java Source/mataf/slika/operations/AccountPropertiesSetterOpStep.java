package mataf.slika.operations;

import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.Hashtable;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.Vector;
import com.ibm.dse.services.jdbc.JDBCTable;

import mataf.data.VisualDataField;
import mataf.general.operations.MatafOperationStep;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class AccountPropertiesSetterOpStep extends MatafOperationStep {

	/**
	 * Constructor for AccountPropertiesSetterOpStep.
	 */
	public AccountPropertiesSetterOpStep() {
		super();
	}

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		String kodIfyun = ((String)getValueAt("AccountBalanceHostReplyData.GKSG_IFYUN")).trim();
		if(kodIfyun.length()>0) {
			String sqlCondition = getSqlCondition(kodIfyun);
			JDBCTable service = (JDBCTable) getService("GLST_IFYUN");
			Vector records = service.retrieveRecordsMatching(sqlCondition);
			for(int counter=0 ; counter<records.size() ; counter++ ) {
				setAccountProperty((Hashtable) records.elementAt(counter));
			}
		}
		
		return RC_OK;
	}
	
	private void setAccountProperty(Hashtable propertyMap) throws DSEException {
		String kodIfyun = (String) propertyMap.get("GL_IFYUN");
		String teurIfyun = (String) propertyMap.get("GL_TEUR_IFYUN");
		boolean isNegetiveData = false;
		int red, blue, green;
		
		// get properties from another table 4 this kodifyun
		JDBCTable service2 = (JDBCTable) getService("GLST_IFYUN_2");
		Vector vector2 = service2.retrieveRecordsMatching("GL_IFYUN='"+kodIfyun+"'");
		if(vector2.size()>0) {
			Hashtable properties2 = (Hashtable) vector2.elementAt(0);
			isNegetiveData = Boolean.valueOf((String) properties2.get("isNegativeData")).booleanValue();
			red = Integer.parseInt((String) properties2.get("red"));
			green = Integer.parseInt((String) properties2.get("green"));
			blue = Integer.parseInt((String) properties2.get("blue"));
				
			// isNegetiveData is 4 future reference
			VisualDataField vField2set = getField2setData(isNegetiveData);
					
			if(vField2set == null) // no more space 2 set account properties
				return;
			
			vField2set.setValue(kodIfyun+"    "+teurIfyun);
			vField2set.setForeground(new Color(red, green, blue));
		}
	}
	
	private VisualDataField getField2setData(boolean isNegetiveData) throws DSEObjectNotFoundException {		
		KeyedCollection feedbacksKcoll = (KeyedCollection) getElementAt("AccountBalance.Feedbacks") ;
		VisualDataField vFiled = null;
		for( int counter=0 ; counter<feedbacksKcoll.size() ; counter++ ) {
			vFiled = (VisualDataField) feedbacksKcoll.getElementAt(counter);
			if(((String)vFiled.getValue()).length() == 0) 
				return vFiled;
		}
		
		return null;
	}
	
	private String getSqlCondition(String kodIfyun) {
		StringBuffer sqlCondition = new StringBuffer("GL_IFYUN IN('");
		for( int counter=0 ; counter<kodIfyun.length() ; counter++ ) 
		{
			sqlCondition.append(kodIfyun.charAt(counter));
		
			if(counter<(kodIfyun.length()+2))
				sqlCondition.append("','");				
		}
		sqlCondition.append("')");		
		return sqlCondition.toString();
	}

}
