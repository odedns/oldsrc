package com.ibm;
import com.ibm.ejs.container.*;
import javax.ejb.*;
import java.rmi.RemoteException;
/**
 * EJSRemoteStatelessAuditDataHome_04ee2aa2
 * @generated
 */
public class EJSRemoteStatelessAuditDataHome_04ee2aa2
	extends EJSWrapper
	implements com.ibm.AuditDataHome {
	/**
	 * EJSRemoteStatelessAuditDataHome_04ee2aa2
	 * @generated
	 */
	public EJSRemoteStatelessAuditDataHome_04ee2aa2()
		throws java.rmi.RemoteException {
		super();
	}
	/**
	 * create
	 * @generated
	 */
	public com.ibm.AuditData create()
		throws javax.ejb.CreateException, java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		com.ibm.AuditData _EJS_result = null;
		try {
			com.ibm.EJSStatelessAuditDataHomeBean_04ee2aa2 _EJS_beanRef =
				(
					com
						.ibm
						.EJSStatelessAuditDataHomeBean_04ee2aa2) container
						.preInvoke(
					this,
					0,
					_EJS_s);
			_EJS_result = _EJS_beanRef.create();
		} catch (javax.ejb.CreateException ex) {
			_EJS_s.setCheckedException(ex);
			throw ex;
		} catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
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
	/**
	 * getEJBMetaData
	 * @generated
	 */
	public javax.ejb.EJBMetaData getEJBMetaData()
		throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		javax.ejb.EJBMetaData _EJS_result = null;
		try {
			com.ibm.EJSStatelessAuditDataHomeBean_04ee2aa2 _EJS_beanRef =
				(
					com
						.ibm
						.EJSStatelessAuditDataHomeBean_04ee2aa2) container
						.preInvoke(
					this,
					1,
					_EJS_s);
			_EJS_result = _EJS_beanRef.getEJBMetaData();
		} catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
		} catch (Throwable ex) {
			_EJS_s.setUncheckedException(ex);
			throw new RemoteException(
				"bean method raised unchecked exception",
				ex);
		} finally {
			container.postInvoke(this, 1, _EJS_s);
			container.putEJSDeployedSupport(_EJS_s);
		}
		return _EJS_result;
	}
	/**
	 * getHomeHandle
	 * @generated
	 */
	public javax.ejb.HomeHandle getHomeHandle()
		throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		javax.ejb.HomeHandle _EJS_result = null;
		try {
			com.ibm.EJSStatelessAuditDataHomeBean_04ee2aa2 _EJS_beanRef =
				(
					com
						.ibm
						.EJSStatelessAuditDataHomeBean_04ee2aa2) container
						.preInvoke(
					this,
					2,
					_EJS_s);
			_EJS_result = _EJS_beanRef.getHomeHandle();
		} catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
		} catch (Throwable ex) {
			_EJS_s.setUncheckedException(ex);
			throw new RemoteException(
				"bean method raised unchecked exception",
				ex);
		} finally {
			container.postInvoke(this, 2, _EJS_s);
			container.putEJSDeployedSupport(_EJS_s);
		}
		return _EJS_result;
	}
	/**
	 * remove
	 * @generated
	 */
	public void remove(java.lang.Object arg0)
		throws java.rmi.RemoteException, javax.ejb.RemoveException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);

		try {
			com.ibm.EJSStatelessAuditDataHomeBean_04ee2aa2 _EJS_beanRef =
				(
					com
						.ibm
						.EJSStatelessAuditDataHomeBean_04ee2aa2) container
						.preInvoke(
					this,
					3,
					_EJS_s);
			_EJS_beanRef.remove(arg0);
		} catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
		} catch (javax.ejb.RemoveException ex) {
			_EJS_s.setCheckedException(ex);
			throw ex;
		} catch (Throwable ex) {
			_EJS_s.setUncheckedException(ex);
			throw new RemoteException(
				"bean method raised unchecked exception",
				ex);
		} finally {
			container.postInvoke(this, 3, _EJS_s);
			container.putEJSDeployedSupport(_EJS_s);
		}
		return;
	}
	/**
	 * remove
	 * @generated
	 */
	public void remove(javax.ejb.Handle arg0)
		throws java.rmi.RemoteException, javax.ejb.RemoveException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);

		try {
			com.ibm.EJSStatelessAuditDataHomeBean_04ee2aa2 _EJS_beanRef =
				(
					com
						.ibm
						.EJSStatelessAuditDataHomeBean_04ee2aa2) container
						.preInvoke(
					this,
					4,
					_EJS_s);
			_EJS_beanRef.remove(arg0);
		} catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
		} catch (javax.ejb.RemoveException ex) {
			_EJS_s.setCheckedException(ex);
			throw ex;
		} catch (Throwable ex) {
			_EJS_s.setUncheckedException(ex);
			throw new RemoteException(
				"bean method raised unchecked exception",
				ex);
		} finally {
			container.postInvoke(this, 4, _EJS_s);
			container.putEJSDeployedSupport(_EJS_s);
		}
		return;
	}
}
