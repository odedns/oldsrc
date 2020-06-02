package onjlib.ejb.mytest;
import com.ibm.ejs.container.*;
import javax.ejb.*;
import java.rmi.RemoteException;
/**
 * EJSLocalStatelessMytestHome_948d4fbe
 * @generated
 */
public class EJSLocalStatelessMytestHome_948d4fbe
	extends EJSLocalWrapper
	implements onjlib.ejb.mytest.MytestLocalHome {
	/**
	 * EJSLocalStatelessMytestHome_948d4fbe
	 * @generated
	 */
	public EJSLocalStatelessMytestHome_948d4fbe() {
		super();
	}
	/**
	 * create
	 * @generated
	 */
	public onjlib.ejb.mytest.MytestLocal create()
		throws javax.ejb.CreateException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		onjlib.ejb.mytest.MytestLocal _EJS_result = null;
		try {
			onjlib.ejb.mytest.EJSStatelessMytestHomeBean_948d4fbe _EJS_beanRef =
				(
					onjlib
						.ejb
						.mytest
						.EJSStatelessMytestHomeBean_948d4fbe) container
						.preInvoke(
					this,
					0,
					_EJS_s);
			_EJS_result = _EJS_beanRef.create_Local();
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
	 * remove
	 * @generated
	 */
	public void remove(java.lang.Object arg0)
		throws javax.ejb.RemoveException, javax.ejb.EJBException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);

		try {
			onjlib.ejb.mytest.EJSStatelessMytestHomeBean_948d4fbe _EJS_beanRef =
				(
					onjlib
						.ejb
						.mytest
						.EJSStatelessMytestHomeBean_948d4fbe) container
						.preInvoke(
					this,
					1,
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
