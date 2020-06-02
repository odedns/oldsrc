package onjlib.ejb.mytest;
import com.ibm.ejs.container.*;
import com.ibm.ejs.persistence.*;
import com.ibm.ejs.EJSException;
import javax.ejb.*;
import java.rmi.RemoteException;
/**
 * EJSStatelessMytestHomeBean_948d4fbe
 * @generated
 */
public class EJSStatelessMytestHomeBean_948d4fbe extends EJSHome {
	/**
	 * EJSStatelessMytestHomeBean_948d4fbe
	 * @generated
	 */
	public EJSStatelessMytestHomeBean_948d4fbe()
		throws java.rmi.RemoteException {
		super();
	}
	/**
	 * create
	 * @generated
	 */
	public onjlib.ejb.mytest.Mytest create()
		throws java.rmi.RemoteException, javax.ejb.CreateException {
		BeanO beanO = null;
		onjlib.ejb.mytest.Mytest result = null;
		boolean createFailed = false;
		try {
			result =
				(onjlib.ejb.mytest.Mytest) super.createWrapper(
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
	public onjlib.ejb.mytest.MytestLocal create_Local()
		throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		onjlib.ejb.mytest.MytestLocal result = null;
		boolean createFailed = false;
		boolean preCreateFlag = false;
		try {
			result =
				(onjlib.ejb.mytest.MytestLocal) super.createWrapper_Local(null);
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
