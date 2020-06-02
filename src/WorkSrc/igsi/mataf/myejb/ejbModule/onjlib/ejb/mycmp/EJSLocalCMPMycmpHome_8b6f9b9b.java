package onjlib.ejb.mycmp;
import com.ibm.ejs.container.*;
import javax.ejb.*;
import java.rmi.RemoteException;
/**
 * EJSLocalCMPMycmpHome_8b6f9b9b
 * @generated
 */
public class EJSLocalCMPMycmpHome_8b6f9b9b
	extends EJSLocalWrapper
	implements
		onjlib.ejb.mycmp.MycmpLocalHome,
		onjlib.ejb.mycmp.websphere_deploy.MycmpBeanInternalLocalHome_8b6f9b9b {
	/**
	 * EJSLocalCMPMycmpHome_8b6f9b9b
	 * @generated
	 */
	public EJSLocalCMPMycmpHome_8b6f9b9b() {
		super();
	}
	/**
	 * create
	 * @generated
	 */
	public onjlib.ejb.mycmp.MycmpLocal create(java.lang.Integer id)
		throws javax.ejb.CreateException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		onjlib.ejb.mycmp.MycmpLocal _EJS_result = null;
		try {
			onjlib.ejb.mycmp.EJSCMPMycmpHomeBean_8b6f9b9b _EJS_beanRef =
				(
					onjlib
						.ejb
						.mycmp
						.EJSCMPMycmpHomeBean_8b6f9b9b) container
						.preInvoke(
					this,
					0,
					_EJS_s);
			_EJS_result = _EJS_beanRef.create_Local(id);
		} catch (javax.ejb.CreateException ex) {
			_EJS_s.setCheckedException(ex);
			throw ex;
		} catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedLocalException(ex);
		} catch (Throwable ex) {
			_EJS_s.setUncheckedLocalException(ex);
		} finally {
			try {
				container.postInvoke(this, 0, _EJS_s);
			} catch (RemoteException ex) {
				_EJS_s.setUncheckedLocalException(ex);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * findByPrimaryKey
	 * @generated
	 */
	public onjlib.ejb.mycmp.MycmpLocal findByPrimaryKey(
		java.lang.Integer primaryKey)
		throws javax.ejb.FinderException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		onjlib.ejb.mycmp.MycmpLocal _EJS_result = null;
		try {
			onjlib.ejb.mycmp.EJSCMPMycmpHomeBean_8b6f9b9b _EJS_beanRef =
				(
					onjlib
						.ejb
						.mycmp
						.EJSCMPMycmpHomeBean_8b6f9b9b) container
						.preInvoke(
					this,
					1,
					_EJS_s);
			_EJS_result = _EJS_beanRef.findByPrimaryKey_Local(primaryKey);
		} catch (javax.ejb.FinderException ex) {
			_EJS_s.setCheckedException(ex);
			throw ex;
		} catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedLocalException(ex);
		} catch (Throwable ex) {
			_EJS_s.setUncheckedLocalException(ex);
		} finally {
			try {
				container.postInvoke(this, 1, _EJS_s);
			} catch (RemoteException ex) {
				_EJS_s.setUncheckedLocalException(ex);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * remove
	 * @generated
	 */
	public void remove(java.lang.Object arg0)
		throws javax.ejb.RemoveException, javax.ejb.EJBException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);

		try {
			onjlib.ejb.mycmp.EJSCMPMycmpHomeBean_8b6f9b9b _EJS_beanRef =
				(
					onjlib
						.ejb
						.mycmp
						.EJSCMPMycmpHomeBean_8b6f9b9b) container
						.preInvoke(
					this,
					2,
					_EJS_s);
			_EJS_beanRef.remove(arg0);
		} catch (javax.ejb.RemoveException ex) {
			_EJS_s.setCheckedException(ex);
			throw ex;
		} catch (javax.ejb.EJBException ex) {
			_EJS_s.setUncheckedLocalException(ex);
		} catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedLocalException(ex);
		} catch (Throwable ex) {
			_EJS_s.setUncheckedLocalException(ex);
		} finally {
			try {
				container.postInvoke(this, 2, _EJS_s);
			} catch (RemoteException ex) {
				_EJS_s.setUncheckedLocalException(ex);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return;
	}
}
