package onjlib.ejb.mytest;
import com.ibm.ejs.container.*;
import javax.ejb.*;
import java.rmi.RemoteException;
/**
 * EJSRemoteStatelessMytest_948d4fbe
 * @generated
 */
public class EJSRemoteStatelessMytest_948d4fbe
	extends EJSWrapper
	implements Mytest {
	/**
	 * EJSRemoteStatelessMytest_948d4fbe
	 * @generated
	 */
	public EJSRemoteStatelessMytest_948d4fbe()
		throws java.rmi.RemoteException {
		super();
	}
	/**
	 * getData
	 * @generated
	 */
	public java.lang.String getData() throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		java.lang.String _EJS_result = null;
		try {
			onjlib.ejb.mytest.MytestBean beanRef =
				(onjlib.ejb.mytest.MytestBean) container.preInvoke(
					this,
					0,
					_EJS_s);
			_EJS_result = beanRef.getData();
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
	 * setData
	 * @generated
	 */
	public void setData(java.lang.String data)
		throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);

		try {
			onjlib.ejb.mytest.MytestBean beanRef =
				(onjlib.ejb.mytest.MytestBean) container.preInvoke(
					this,
					1,
					_EJS_s);
			beanRef.setData(data);
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
		return;
	}
}
