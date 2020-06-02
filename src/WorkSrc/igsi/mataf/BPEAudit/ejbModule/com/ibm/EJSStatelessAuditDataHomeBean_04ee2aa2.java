package com.ibm;
import com.ibm.ejs.container.*;
import com.ibm.ejs.persistence.*;
import com.ibm.ejs.EJSException;
import javax.ejb.*;
import java.rmi.RemoteException;
/**
 * EJSStatelessAuditDataHomeBean_04ee2aa2
 * @generated
 */
public class EJSStatelessAuditDataHomeBean_04ee2aa2 extends EJSHome {
	/**
	 * EJSStatelessAuditDataHomeBean_04ee2aa2
	 * @generated
	 */
	public EJSStatelessAuditDataHomeBean_04ee2aa2()
		throws java.rmi.RemoteException {
		super();
	}
	/**
	 * create
	 * @generated
	 */
	public com.ibm.AuditData create()
		throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ibm.AuditData result = null;
		boolean createFailed = false;
		try {
			result =
				(com.ibm.AuditData) super.createWrapper(new BeanId(this, null));
		} catch (javax.ejb.CreateException ex) {
			createFailed = true;
			throw ex;
		} catch (java.rmi.RemoteException ex) {
			createFailed = true;
			throw ex;
		} catch (Throwable ex) {
			createFailed = true;
			throw new CreateFailureException(ex);
		} finally {
			if (createFailed) {
				super.createFailure(beanO);
			}
		}
		return result;
	}
}
