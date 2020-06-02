package onjlib.ejb.mysession;
import com.ibm.ejs.container.*;
import javax.ejb.*;
import java.rmi.RemoteException;
/**
 * EJSLocalStatelessMysessionHome_1266f99a
 * @generated
 */
public class EJSLocalStatelessMysessionHome_1266f99a
	extends EJSLocalWrapper
	implements onjlib.ejb.mysession.MysessionLocalHome {
	/**
	 * EJSLocalStatelessMysessionHome_1266f99a
	 * @generated
	 */
	public EJSLocalStatelessMysessionHome_1266f99a() {
		super();
	}
	/**
	 * create
	 * @generated
	 */
	public onjlib.ejb.mysession.MysessionLocal create()
		throws javax.ejb.CreateException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		onjlib.ejb.mysession.MysessionLocal _EJS_result = null;
		try {
			onjlib
				.ejb
				.mysession
				.EJSStatelessMysessionHomeBean_1266f99a _EJS_beanRef =
				(
					onjlib
						.ejb
						.mysession
						.EJSStatelessMysessionHomeBean_1266f99a) container
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
			onjlib
				.ejb
				.mysession
				.EJSStatelessMysessionHomeBean_1266f99a _EJS_beanRef =
				(
					onjlib
						.ejb
						.mysession
						.EJSStatelessMysessionHomeBean_1266f99a) container
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
