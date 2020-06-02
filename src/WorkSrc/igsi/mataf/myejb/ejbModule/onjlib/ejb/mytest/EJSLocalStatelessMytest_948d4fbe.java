package onjlib.ejb.mytest;
import com.ibm.ejs.container.*;
import javax.ejb.*;
import java.rmi.RemoteException;
/**
 * EJSLocalStatelessMytest_948d4fbe
 * @generated
 */
public class EJSLocalStatelessMytest_948d4fbe
	extends EJSLocalWrapper
	implements onjlib.ejb.mytest.MytestLocal {
	/**
	 * EJSLocalStatelessMytest_948d4fbe
	 * @generated
	 */
	public EJSLocalStatelessMytest_948d4fbe() {
		super();
	}
	/**
	 * getData
	 * @generated
	 */
	public java.lang.String getData() {
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
	 * setData
	 * @generated
	 */
	public void setData(java.lang.String data) {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);

		try {
			onjlib.ejb.mytest.MytestBean beanRef =
				(onjlib.ejb.mytest.MytestBean) container.preInvoke(
					this,
					1,
					_EJS_s);
			beanRef.setData(data);
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
		return;
	}
}
