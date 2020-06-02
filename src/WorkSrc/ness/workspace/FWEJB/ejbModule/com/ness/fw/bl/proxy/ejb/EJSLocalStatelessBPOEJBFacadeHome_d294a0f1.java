package com.ness.fw.bl.proxy.ejb;

import com.ibm.ejs.container.*;
import java.rmi.RemoteException;

/**
 * EJSLocalStatelessBPOEJBFacadeHome_d294a0f1
 */
public class EJSLocalStatelessBPOEJBFacadeHome_d294a0f1 extends EJSLocalWrapper implements com.ness.fw.bl.proxy.ejb.BPOEJBFacadeLocalHome {
	/**
	 * EJSLocalStatelessBPOEJBFacadeHome_d294a0f1
	 */
	public EJSLocalStatelessBPOEJBFacadeHome_d294a0f1() {
		super();	}
	/**
	 * create
	 */
	public com.ness.fw.bl.proxy.ejb.BPOEJBFacadeLocal create() throws javax.ejb.CreateException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		com.ness.fw.bl.proxy.ejb.BPOEJBFacadeLocal _EJS_result = null;
		try {
			com.ness.fw.bl.proxy.ejb.EJSStatelessBPOEJBFacadeHomeBean_d294a0f1 _EJS_beanRef = (com.ness.fw.bl.proxy.ejb.EJSStatelessBPOEJBFacadeHomeBean_d294a0f1)container.preInvoke(this, 0, _EJS_s);
			_EJS_result = _EJS_beanRef.create_Local();
		}
		catch (javax.ejb.CreateException ex) {
			_EJS_s.setCheckedException(ex);
			throw ex;
		}
		catch (java.rmi.RemoteException ex) {
		 	_EJS_s.setUncheckedLocalException(ex);
		}
		catch (Throwable ex) {
			_EJS_s.setUncheckedLocalException(ex);
		}

		finally {
			try {
				container.postInvoke(this, 0, _EJS_s);
			} catch ( RemoteException ex ) {
				_EJS_s.setUncheckedLocalException(ex);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * remove
	 */
	public void remove(java.lang.Object arg0) throws javax.ejb.RemoveException, javax.ejb.EJBException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		
		try {
			com.ness.fw.bl.proxy.ejb.EJSStatelessBPOEJBFacadeHomeBean_d294a0f1 _EJS_beanRef = (com.ness.fw.bl.proxy.ejb.EJSStatelessBPOEJBFacadeHomeBean_d294a0f1)container.preInvoke(this, 1, _EJS_s);
			_EJS_beanRef.remove(arg0);
		}
		catch (javax.ejb.RemoveException ex) {
			_EJS_s.setCheckedException(ex);
			throw ex;
		}
		catch (javax.ejb.EJBException ex) {
		 	_EJS_s.setUncheckedLocalException(ex);
		}
		catch (java.rmi.RemoteException ex) {
		 	_EJS_s.setUncheckedLocalException(ex);
		}
		catch (Throwable ex) {
			_EJS_s.setUncheckedLocalException(ex);
		}

		finally {
			try {
				container.postInvoke(this, 1, _EJS_s);
			} catch ( RemoteException ex ) {
				_EJS_s.setUncheckedLocalException(ex);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return ;
	}
}
