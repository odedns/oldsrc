package com.ibm;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import com.ibm.bpe.api.PIID;
/**
 * Bean implementation class for Enterprise Bean: AuditData
 */
public class AuditDataBean implements javax.ejb.SessionBean {
	private DataSource theDataSource = null;
	private javax.ejb.SessionContext mySessionCtx;
	/**
	 * getSessionContext
	 */
	public javax.ejb.SessionContext getSessionContext() {
		return mySessionCtx;
	}
	/**
	 * setSessionContext
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) {
		mySessionCtx = ctx;
	}
	/**
	 * ejbCreate
	 */
	public void ejbCreate() throws javax.ejb.CreateException {
	}
	/**
	 * ejbActivate
	 */
	public void ejbActivate() {
	}
	/**
	 * ejbPassivate
	 */
	public void ejbPassivate() {
	}
	/**
	 * ejbRemove
	 */
	public void ejbRemove() {
	}
	private DataSource getDataSource() {
		if (theDataSource == null) {
			try {
				InitialContext ctx = new InitialContext();
				// Perform a naming service lookup to get the DataSource object.
				theDataSource = (DataSource) ctx.lookup("jdbc/BPEDB");
			} catch (Exception e) {
				System.out.println("Naming service exception: " + e.getMessage());
				e.printStackTrace();
				return null;
			}
		}
		return theDataSource;
	}
	public AuditDataResult queryActivityAudit(PIID thePIID) throws Exception {
		DataSource theDS = this.getDataSource();
		Connection conn = null;
		PreparedStatement ps = null;
		String theSQL = "select "+AuditDataResult.getSelect();
		theSQL += " from audit_log_t where TOP_LEVEL_PIID=? ORDER BY event_time";
 		try {
			conn = theDS.getConnection();
			ps = conn.prepareStatement(theSQL);
			ps.setObject(1, thePIID.toByteArray());
			ResultSet theResult = ps.executeQuery();
			return new AuditDataResult(theResult);
		} catch (SQLException sq) {
			System.out.println("SQL Exception during get connection or process SQL: " + sq.getMessage());
			throw new Exception(sq.getMessage());
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception e) {
					System.out.println("Close Statement Exception: " + e.getMessage());
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					System.out.println("Close connection exception: " + e.getMessage());
				}
			}
		}
	}
}
