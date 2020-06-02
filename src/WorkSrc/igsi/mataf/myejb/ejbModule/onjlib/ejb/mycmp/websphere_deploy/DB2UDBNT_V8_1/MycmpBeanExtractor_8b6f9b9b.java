package onjlib.ejb.mycmp.websphere_deploy.DB2UDBNT_V8_1;
import com.ibm.ejs.persistence.*;
import javax.ejb.EntityBean;
import java.sql.*;
import java.text.*;
import com.ibm.vap.converters.*;
import com.ibm.vap.converters.streams.*;
/**
 * MycmpBeanExtractor_8b6f9b9b
 * @generated
 */
public class MycmpBeanExtractor_8b6f9b9b
	extends com.ibm.ws.ejbpersistence.dataaccess.AbstractEJBExtractor {
	/**
	 * MycmpBeanExtractor_8b6f9b9b
	 * @generated
	 */
	public MycmpBeanExtractor_8b6f9b9b() {
		int[] pkCols = { 1 };
		setPrimaryKeyColumns(pkCols);

		int[] dataCols = { 1, 2, 3 };
		setDataColumns(dataCols);
	}
	/**
	 * extractData
	 * @generated
	 */
	public com.ibm.ws.ejbpersistence.cache.DataCacheEntry extractData(
		com.ibm.ws.ejbpersistence.dataaccess.RawBeanData dataRow)
		throws
			com.ibm.ws.ejbpersistence.utilpm.ErrorProcessingResultCollectionRow,
			com.ibm.ws.ejbpersistence.utilpm.PersistenceManagerInternalError {
		int[] dataColumns = getDataColumns();

		onjlib
			.ejb
			.mycmp
			.websphere_deploy
			.DB2UDBNT_V8_1
			.MycmpBeanCacheEntryImpl_8b6f9b9b entry =
			new onjlib
				.ejb
				.mycmp
				.websphere_deploy
				.DB2UDBNT_V8_1
				.MycmpBeanCacheEntryImpl_8b6f9b9b();

		entry.setDataForID(dataRow.getInt(dataColumns[0]), dataRow.wasNull());
		entry.setDataForNAME(dataRow.getString(dataColumns[1]));
		entry.setDataForDESCRIPTION(dataRow.getString(dataColumns[2]));

		return entry;
	}
	/**
	 * extractPrimaryKey
	 * @generated
	 */
	public Object extractPrimaryKey(
		com.ibm.ws.ejbpersistence.dataaccess.RawBeanData dataRow)
		throws
			com.ibm.ws.ejbpersistence.utilpm.ErrorProcessingResultCollectionRow,
			com.ibm.ws.ejbpersistence.utilpm.PersistenceManagerInternalError {
		int[] primaryKeyColumns = getPrimaryKeyColumns();

		java.lang.Integer key;
		key = new Integer(dataRow.getInt(primaryKeyColumns[0]));

		return key;
	}
	/**
	 * getHomeName
	 * @generated
	 */
	public String getHomeName() {
		return "Mycmp";
	}
	/**
	 * getChunkLength
	 * @generated
	 */
	public int getChunkLength() {
		return 3;
	}
}
