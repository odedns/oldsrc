package com.ibm;
import com.ibm.ejs.container.*;
import javax.ejb.*;
import java.rmi.RemoteException;
/**
 * EJSRemoteStatelessAuditData_04ee2aa2
 * @generated
 */
public class EJSRemoteStatelessAuditData_04ee2aa2
	extends EJSWrapper
	implements AuditData {
	/**
	 * EJSRemoteStatelessAuditData_04ee2aa2
	 * @generated
	 */
	public EJSRemoteStatelessAuditData_04ee2aa2()
		throws java.rmi.RemoteException {
		super();
	}
	/**
	 * queryActivityAudit
	 * @generated
	 */
	public com.ibm.AuditDataResult queryActivityAudit(
		com.ibm.bpe.api.PIID thePIID)
		throws java.lang.Exception, java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		com.ibm.AuditDataResult _EJS_result = null;
		try {
			com.ibm.AuditDataBean beanRef =
				(com.ibm.AuditDataBean) container.preInvoke(this, 0, _EJS_s);
			_EJS_result = beanRef.queryActivityAudit(thePIID);
		} catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
		} catch (java.lang.RuntimeException ex) {
			_EJS_s.setUncheckedException(ex);
		} catch (java.lang.Exception ex) {
			_EJS_s.setCheckedException(ex);
			throw ex;
		} catch (Throwable ex) {
			_EJS_s.setUncheckedException(ex);
			throw new RemoteException(
				"bean method raised unchecked exception",
				ex);
		} finally {
			container.postInvoke(this, 0, _EJS_s);
			container.putEJSDeployedSupport(_EJS_s);
		}
		return _EJS_result;
	}
}
