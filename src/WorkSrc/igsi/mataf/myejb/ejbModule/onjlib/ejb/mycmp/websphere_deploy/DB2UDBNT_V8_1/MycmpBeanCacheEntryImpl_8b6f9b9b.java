package onjlib.ejb.mycmp.websphere_deploy.DB2UDBNT_V8_1;
import com.ibm.ejs.persistence.*;
import javax.ejb.EntityBean;
import java.sql.*;
import java.text.*;
import com.ibm.vap.converters.*;
import com.ibm.vap.converters.streams.*;
/**
 * MycmpBeanCacheEntryImpl_8b6f9b9b
 * @generated
 */
public class MycmpBeanCacheEntryImpl_8b6f9b9b
	extends com.ibm.ws.ejbpersistence.cache.DataCacheEntry
	implements onjlib.ejb.mycmp.websphere_deploy.MycmpBeanCacheEntry_8b6f9b9b {
	/**
	 * @generated
	 */
	private int ID_Data;
	/**
	 * @generated
	 */
	private boolean ID_IsNull;
	/**
	 * @generated
	 */
	private String NAME_Data;
	/**
	 * @generated
	 */
	private String DESCRIPTION_Data;
	/**
	 * getId
	 * @generated
	 */
	public java.lang.Integer getId() {
		if (ID_IsNull)
			return null;
		else
			return new Integer(ID_Data);
	}
	/**
	 * setDataForID
	 * @generated
	 */
	public void setDataForID(int data, boolean isNull) {
		this.ID_Data = data;
		this.ID_IsNull = isNull;
	}
	/**
	 * getName
	 * @generated
	 */
	public java.lang.String getName() {
		return NAME_Data;
	}
	/**
	 * setDataForNAME
	 * @generated
	 */
	public void setDataForNAME(String data) {
		this.NAME_Data = data;
	}
	/**
	 * getDescription
	 * @generated
	 */
	public java.lang.String getDescription() {
		return DESCRIPTION_Data;
	}
	/**
	 * setDataForDESCRIPTION
	 * @generated
	 */
	public void setDataForDESCRIPTION(String data) {
		this.DESCRIPTION_Data = data;
	}
}
