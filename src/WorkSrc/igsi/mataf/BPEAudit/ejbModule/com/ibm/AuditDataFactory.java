package com.ibm;
import javax.ejb.*;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import com.ibm.etools.ejb.client.runtime.*;
/**
 * AuditDataFactory
 * @generated
 */
public class AuditDataFactory extends AbstractEJBFactory {
	/**
	 * AuditDataFactory
	 * @generated
	 */
	public AuditDataFactory() {
		super();
	}
	/**
	 * _acquireAuditDataHome
	 * @generated
	 */
	protected com.ibm.AuditDataHome _acquireAuditDataHome() throws java.rmi.RemoteException {
		return (com.ibm.AuditDataHome) _acquireEJBHome();
	}
	/**
	 * acquireAuditDataHome
	 * @generated
	 */
	public com.ibm.AuditDataHome acquireAuditDataHome() throws javax.naming.NamingException {
		return (com.ibm.AuditDataHome) acquireEJBHome();
	}
	/**
	 * getDefaultJNDIName
	 * @generated
	 */
	public String getDefaultJNDIName() {
		return "ejb/com/ibm/AuditDataHome";
	}
	/**
	 * getHomeInterface
	 * @generated
	 */
	protected Class getHomeInterface() {
		return com.ibm.AuditDataHome.class;
	}
	/**
	 * resetAuditDataHome
	 * @generated
	 */
	public void resetAuditDataHome() {
		resetEJBHome();
	}
	/**
	 * setAuditDataHome
	 * @generated
	 */
	public void setAuditDataHome(com.ibm.AuditDataHome home) {
		setEJBHome(home);
	}
	/**
	 * create
	 * @generated
	 */
	public com.ibm.AuditData create() throws CreateException, RemoteException {
		return _acquireAuditDataHome().create();
	}
}
