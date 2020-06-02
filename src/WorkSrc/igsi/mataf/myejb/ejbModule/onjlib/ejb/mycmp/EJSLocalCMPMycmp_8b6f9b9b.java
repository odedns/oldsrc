package onjlib.ejb.mycmp;
import com.ibm.ejs.container.*;
import javax.ejb.*;
import java.rmi.RemoteException;
/**
 * EJSLocalCMPMycmp_8b6f9b9b
 * @generated
 */
public class EJSLocalCMPMycmp_8b6f9b9b
	extends EJSLocalWrapper
	implements onjlib.ejb.mycmp.MycmpLocal {
	/**
	 * EJSLocalCMPMycmp_8b6f9b9b
	 * @generated
	 */
	public EJSLocalCMPMycmp_8b6f9b9b() {
		super();
	}
	/**
	 * getDescription
	 * @generated
	 */
	public java.lang.String getDescription() {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		java.lang.String _EJS_result = null;
		try {
			onjlib.ejb.mycmp.MycmpBean beanRef =
				(onjlib.ejb.mycmp.MycmpBean) container.preInvoke(
					this,
					0,
					_EJS_s);
			_EJS_result = beanRef.getDescription();
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
	 * getName
	 * @generated
	 */
	public java.lang.String getName() {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		java.lang.String _EJS_result = null;
		try {
			onjlib.ejb.mycmp.MycmpBean beanRef =
				(onjlib.ejb.mycmp.MycmpBean) container.preInvoke(
					this,
					1,
					_EJS_s);
			_EJS_result = beanRef.getName();
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
	 * setDescription
	 * @generated
	 */
	public void setDescription(java.lang.String newDescription) {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);

		try {
			onjlib.ejb.mycmp.MycmpBean beanRef =
				(onjlib.ejb.mycmp.MycmpBean) container.preInvoke(
					this,
					2,
					_EJS_s);
			beanRef.setDescription(newDescription);
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
	/**
	 * setName
	 * @generated
	 */
	public void setName(java.lang.String newName) {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);

		try {
			onjlib.ejb.mycmp.MycmpBean beanRef =
				(onjlib.ejb.mycmp.MycmpBean) container.preInvoke(
					this,
					3,
					_EJS_s);
			beanRef.setName(newName);
		} catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedLocalException(ex);
		} catch (Throwable ex) {
			_EJS_s.setUncheckedLocalException(ex);
		} finally {
			try {
				container.postInvoke(this, 3, _EJS_s);
			} catch (RemoteException ex) {
				_EJS_s.setUncheckedLocalException(ex);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return;
	}
}
