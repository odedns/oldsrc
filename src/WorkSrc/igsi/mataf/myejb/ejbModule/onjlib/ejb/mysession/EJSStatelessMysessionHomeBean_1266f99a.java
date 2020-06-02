package onjlib.ejb.mysession;
import com.ibm.ejs.container.*;
import com.ibm.ejs.persistence.*;
import com.ibm.ejs.EJSException;
import javax.ejb.*;
import java.rmi.RemoteException;
/**
 * EJSStatelessMysessionHomeBean_1266f99a
 * @generated
 */
public class EJSStatelessMysessionHomeBean_1266f99a extends EJSHome {
	/**
	 * EJSStatelessMysessionHomeBean_1266f99a
	 * @generated
	 */
	public EJSStatelessMysessionHomeBean_1266f99a()
		throws java.rmi.RemoteException {
		super();
	}
	/**
	 * create
	 * @generated
	 */
	public onjlib.ejb.mysession.Mysession create()
		throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		onjlib.ejb.mysession.Mysession result = null;
		boolean createFailed = false;
		try {
			result =
				(onjlib.ejb.mysession.Mysession) super.createWrapper(
					new BeanId(this, null));
		} catch (javax.ejb.CreateException ex) {
			createFailed = true;
			throw ex;
		} catch (java.rmi.RemoteException ex) {
			createFailed = true;
			throw ex;
		} catch (Throwable ex) {
			createFailed = true;
			throw new CreateFailureException(ex);
		} finally {
			if (createFailed) {
				super.createFailure(beanO);
			}
		}
		return result;
	}
	/**
	 * create_Local
	 * @generated
	 */
	public onjlib.ejb.mysession.MysessionLocal create_Local()
		throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		onjlib.ejb.mysession.MysessionLocal result = null;
		boolean createFailed = false;
		boolean preCreateFlag = false;
		try {
			result =
				(
					onjlib
						.ejb
						.mysession
						.MysessionLocal) super
						.createWrapper_Local(
					null);
		} catch (javax.ejb.CreateException ex) {
			createFailed = true;
			throw ex;
		} catch (java.rmi.RemoteException ex) {
			createFailed = true;
			throw ex;
		} catch (Throwable ex) {
			createFailed = true;
			throw new CreateFailureException(ex);
		} finally {
			if (createFailed) {
				super.createFailure(beanO);
			}
		}
		return result;
	}
}
