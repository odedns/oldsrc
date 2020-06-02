package onjlib.ejb.mycmp;
import com.ibm.ejs.container.*;
import javax.ejb.*;
import java.rmi.RemoteException;
/**
 * EJSRemoteCMPMycmp_8b6f9b9b
 * @generated
 */
public class EJSRemoteCMPMycmp_8b6f9b9b extends EJSWrapper implements Mycmp {
	/**
	 * EJSRemoteCMPMycmp_8b6f9b9b
	 * @generated
	 */
	public EJSRemoteCMPMycmp_8b6f9b9b() throws java.rmi.RemoteException {
		super();
	}
	/**
	 * getDescription
	 * @generated
	 */
	public java.lang.String getDescription() throws java.rmi.RemoteException {
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
	 * getName
	 * @generated
	 */
	public java.lang.String getName() throws java.rmi.RemoteException {
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
	 * setDescription
	 * @generated
	 */
	public void setDescription(java.lang.String newDescription)
		throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);

		try {
			onjlib.ejb.mycmp.MycmpBean beanRef =
				(onjlib.ejb.mycmp.MycmpBean) container.preInvoke(
					this,
					2,
					_EJS_s);
			beanRef.setDescription(newDescription);
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
		return;
	}
	/**
	 * setName
	 * @generated
	 */
	public void setName(java.lang.String newName)
		throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);

		try {
			onjlib.ejb.mycmp.MycmpBean beanRef =
				(onjlib.ejb.mycmp.MycmpBean) container.preInvoke(
					this,
					3,
					_EJS_s);
			beanRef.setName(newName);
		} catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
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
}
