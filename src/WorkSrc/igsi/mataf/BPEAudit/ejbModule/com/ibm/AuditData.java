package com.ibm;
import com.ibm.bpe.api.PIID;
import java.sql.ResultSet;
/**
 * Remote interface for Enterprise Bean: AuditData
 */
public interface AuditData extends javax.ejb.EJBObject {
	public AuditDataResult queryActivityAudit(PIID thePIID) throws Exception, java.rmi.RemoteException;
}
