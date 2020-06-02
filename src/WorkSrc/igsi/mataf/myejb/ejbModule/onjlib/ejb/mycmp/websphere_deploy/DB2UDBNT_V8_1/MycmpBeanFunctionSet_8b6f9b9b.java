package onjlib.ejb.mycmp.websphere_deploy.DB2UDBNT_V8_1;
import com.ibm.ws.rsadapter.cci.WSRdbResultSetImpl;
import com.ibm.ws.rsadapter.cci.WSRdbConnectionImpl;
import com.ibm.websphere.rsadapter.WSInteractionSpec;
import com.ibm.ws.rsadapter.exceptions.DataStoreAdapterException;
import javax.resource.cci.Record;
import javax.resource.cci.IndexedRecord;
import java.sql.*;
import java.text.*;
import com.ibm.vap.converters.*;
import com.ibm.vap.converters.streams.*;
/**
 * MycmpBeanFunctionSet_8b6f9b9b
 * @generated
 */
public class MycmpBeanFunctionSet_8b6f9b9b
	extends com.ibm.ws.rsadapter.cci.WSResourceAdapterBase
	implements com.ibm.websphere.rsadapter.DataAccessFunctionSet {
	/**
	 * @generated
	 */
	private java.util.HashMap functionHash;
	/**
	 * Create
	 * @generated
	 */
	public void Create(
		IndexedRecord inputRecord,
		Object connection,
		WSInteractionSpec interactionSpec)
		throws DataStoreAdapterException {
		PreparedStatement pstmt = null;
		try {
			pstmt =
				prepareStatement(
					connection,
					"INSERT INTO MYCMP.MYCMP (ID, NAME, DESCRIPTION) VALUES (?, ?, ?)");

			// For column ID
			{
				Integer tempInteger;

				tempInteger = (Integer) inputRecord.get(0);
				if (tempInteger == null)
					pstmt.setNull(1, java.sql.Types.INTEGER);
				else
					pstmt.setInt(1, tempInteger.intValue());
			}
			// For column NAME
			{
				String tempString;

				tempString = (String) inputRecord.get(1);
				if (tempString == null)
					pstmt.setNull(2, java.sql.Types.VARCHAR);
				else
					pstmt.setString(2, tempString);
			}
			// For column DESCRIPTION
			{
				String tempString;

				tempString = (String) inputRecord.get(2);
				if (tempString == null)
					pstmt.setNull(3, java.sql.Types.VARCHAR);
				else
					pstmt.setString(3, tempString);
			}
			if (pstmt.executeUpdate() == 0)
				throw new DataStoreAdapterException(
					"DSA_ERROR",
					new javax.ejb.NoSuchEntityException(),
					this.getClass());

		} catch (SQLException e) {
			throw new DataStoreAdapterException(
				"DSA_ERROR",
				e,
				this.getClass());
		} finally {
			try {
				if (pstmt != null) {
					returnPreparedStatement(connection, pstmt);
				}
			} catch (SQLException ignored) {
			}
		}
	}
	/**
	 * Remove
	 * @generated
	 */
	public void Remove(
		IndexedRecord inputRecord,
		Object connection,
		WSInteractionSpec interactionSpec)
		throws DataStoreAdapterException {
		PreparedStatement pstmt = null;
		try {
			pstmt =
				prepareStatement(
					connection,
					"DELETE FROM MYCMP.MYCMP  WHERE ID = ?");

			// For column ID
			{
				Integer tempInteger;

				tempInteger = (Integer) inputRecord.get(0);
				if (tempInteger == null)
					pstmt.setNull(1, java.sql.Types.INTEGER);
				else
					pstmt.setInt(1, tempInteger.intValue());
			}
			if (pstmt.executeUpdate() == 0)
				throw new DataStoreAdapterException(
					"DSA_ERROR",
					new javax.ejb.NoSuchEntityException(),
					this.getClass());

		} catch (SQLException e) {
			throw new DataStoreAdapterException(
				"DSA_ERROR",
				e,
				this.getClass());
		} finally {
			try {
				if (pstmt != null) {
					returnPreparedStatement(connection, pstmt);
				}
			} catch (SQLException ignored) {
			}
		}
	}
	/**
	 * Store
	 * @generated
	 */
	public void Store(
		IndexedRecord inputRecord,
		Object connection,
		WSInteractionSpec interactionSpec)
		throws DataStoreAdapterException {
		PreparedStatement pstmt = null;
		try {
			pstmt =
				prepareStatement(
					connection,
					"UPDATE MYCMP.MYCMP  SET NAME = ?, DESCRIPTION = ? WHERE ID = ?");

			// For column ID
			{
				Integer tempInteger;

				tempInteger = (Integer) inputRecord.get(0);
				if (tempInteger == null)
					pstmt.setNull(3, java.sql.Types.INTEGER);
				else
					pstmt.setInt(3, tempInteger.intValue());
			}
			// For column NAME
			{
				String tempString;

				tempString = (String) inputRecord.get(1);
				if (tempString == null)
					pstmt.setNull(1, java.sql.Types.VARCHAR);
				else
					pstmt.setString(1, tempString);
			}
			// For column DESCRIPTION
			{
				String tempString;

				tempString = (String) inputRecord.get(2);
				if (tempString == null)
					pstmt.setNull(2, java.sql.Types.VARCHAR);
				else
					pstmt.setString(2, tempString);
			}
			if (pstmt.executeUpdate() == 0)
				throw new DataStoreAdapterException(
					"DSA_ERROR",
					new javax.ejb.NoSuchEntityException(),
					this.getClass());

		} catch (SQLException e) {
			throw new DataStoreAdapterException(
				"DSA_ERROR",
				e,
				this.getClass());
		} finally {
			try {
				if (pstmt != null) {
					returnPreparedStatement(connection, pstmt);
				}
			} catch (SQLException ignored) {
			}
		}
	}
	/**
	 * StoreUsingOCC
	 * @generated
	 */
	public void StoreUsingOCC(
		IndexedRecord inputRecord,
		Object connection,
		WSInteractionSpec interactionSpec)
		throws DataStoreAdapterException {
		PreparedStatement pstmt = null;
		try {
			pstmt =
				prepareStatement(
					connection,
					"UPDATE MYCMP.MYCMP  SET NAME = ?, DESCRIPTION = ? WHERE ID = ? AND NAME = ? AND DESCRIPTION = ?");

			// For column ID
			{
				Integer tempInteger;

				tempInteger = (Integer) inputRecord.get(0);
				if (tempInteger == null)
					pstmt.setNull(3, java.sql.Types.INTEGER);
				else
					pstmt.setInt(3, tempInteger.intValue());
			}
			// For column NAME
			{
				String tempString;

				tempString = (String) inputRecord.get(1);
				if (tempString == null)
					pstmt.setNull(1, java.sql.Types.VARCHAR);
				else
					pstmt.setString(1, tempString);
			}
			// For column DESCRIPTION
			{
				String tempString;

				tempString = (String) inputRecord.get(2);
				if (tempString == null)
					pstmt.setNull(2, java.sql.Types.VARCHAR);
				else
					pstmt.setString(2, tempString);
			}
			IndexedRecord oldRecord = interactionSpec.getOldRecord();
			// For column NAME
			{
				String tempString;

				tempString = (String) oldRecord.get(1);
				if (tempString == null)
					pstmt.setNull(4, java.sql.Types.VARCHAR);
				else
					pstmt.setString(4, tempString);
			}
			// For column DESCRIPTION
			{
				String tempString;

				tempString = (String) oldRecord.get(2);
				if (tempString == null)
					pstmt.setNull(5, java.sql.Types.VARCHAR);
				else
					pstmt.setString(5, tempString);
			}
			if (pstmt.executeUpdate() == 0)
				throw new DataStoreAdapterException(
					"DSA_ERROR",
					new javax.ejb.NoSuchEntityException(),
					this.getClass());

		} catch (SQLException e) {
			throw new DataStoreAdapterException(
				"DSA_ERROR",
				e,
				this.getClass());
		} finally {
			try {
				if (pstmt != null) {
					returnPreparedStatement(connection, pstmt);
				}
			} catch (SQLException ignored) {
			}
		}
	}
	/**
	 * FindByPrimaryKey
	 * @generated
	 */
	public javax.resource.cci.Record FindByPrimaryKey(
		IndexedRecord inputRecord,
		Object connection,
		WSInteractionSpec interactionSpec)
		throws DataStoreAdapterException {
		PreparedStatement pstmt = null;
		ResultSet result = null;
		try {
			pstmt =
				prepareStatement(
					connection,
					"SELECT T1.ID, T1.NAME, T1.DESCRIPTION FROM MYCMP.MYCMP  T1 WHERE T1.ID = ?");

			// For column ID
			{
				Integer tempInteger;

				tempInteger = (Integer) inputRecord.get(0);
				if (tempInteger == null)
					pstmt.setNull(1, java.sql.Types.INTEGER);
				else
					pstmt.setInt(1, tempInteger.intValue());
			}
			result = pstmt.executeQuery();

		} catch (SQLException e) {
			throw new DataStoreAdapterException(
				"DSA_ERROR",
				e,
				this.getClass());
		}
		return createCCIRecord(connection, result);
	}
	/**
	 * FindByPrimaryKeyForUpdate
	 * @generated
	 */
	public javax.resource.cci.Record FindByPrimaryKeyForUpdate(
		IndexedRecord inputRecord,
		Object connection,
		WSInteractionSpec interactionSpec)
		throws DataStoreAdapterException {
		PreparedStatement pstmt = null;
		ResultSet result = null;
		try {
			pstmt =
				prepareStatement(
					connection,
					"SELECT T1.ID, T1.NAME, T1.DESCRIPTION FROM MYCMP.MYCMP  T1 WHERE T1.ID = ? FOR UPDATE  OF NAME");

			// For column ID
			{
				Integer tempInteger;

				tempInteger = (Integer) inputRecord.get(0);
				if (tempInteger == null)
					pstmt.setNull(1, java.sql.Types.INTEGER);
				else
					pstmt.setInt(1, tempInteger.intValue());
			}
			result = pstmt.executeQuery();

		} catch (SQLException e) {
			throw new DataStoreAdapterException(
				"DSA_ERROR",
				e,
				this.getClass());
		}
		return createCCIRecord(connection, result);
	}
	/**
	 * MycmpBeanFunctionSet_8b6f9b9b
	 * @generated
	 */
	public MycmpBeanFunctionSet_8b6f9b9b() {
		functionHash = new java.util.HashMap(6);

		functionHash.put("Create", new Integer(0));
		functionHash.put("Remove", new Integer(1));
		functionHash.put("Store", new Integer(2));
		functionHash.put("StoreUsingOCC", new Integer(3));
		functionHash.put("FindByPrimaryKey", new Integer(4));
		functionHash.put("FindByPrimaryKeyForUpdate", new Integer(5));
	}
	/**
	 * execute
	 * @generated
	 */
	public Record execute(
		WSInteractionSpec interactionSpec,
		IndexedRecord inputRecord,
		Object connection)
		throws javax.resource.ResourceException {
		String functionName = interactionSpec.getFunctionName();
		Record outputRecord = null;
		int functionIndex =
			((Integer) functionHash.get(functionName)).intValue();

		switch (functionIndex) {
			case 0 :
				Create(inputRecord, connection, interactionSpec);
				break;
			case 1 :
				Remove(inputRecord, connection, interactionSpec);
				break;
			case 2 :
				Store(inputRecord, connection, interactionSpec);
				break;
			case 3 :
				StoreUsingOCC(inputRecord, connection, interactionSpec);
				break;
			case 4 :
				outputRecord =
					FindByPrimaryKey(inputRecord, connection, interactionSpec);
				break;
			case 5 :
				outputRecord =
					FindByPrimaryKeyForUpdate(
						inputRecord,
						connection,
						interactionSpec);
				break;
		}
		return outputRecord;
	}
}
