package onjlib.ejb.mycmp;
import com.ibm.ejs.container.*;
import com.ibm.ejs.persistence.*;
import com.ibm.ejs.EJSException;
import javax.ejb.*;
import java.rmi.RemoteException;
/**
 * EJSCMPMycmpHomeBean_8b6f9b9b
 * @generated
 */
public class EJSCMPMycmpHomeBean_8b6f9b9b extends EJSHome {
	/**
	 * EJSCMPMycmpHomeBean_8b6f9b9b
	 * @generated
	 */
	public EJSCMPMycmpHomeBean_8b6f9b9b() throws java.rmi.RemoteException {
		super();
	}
	/**
	 * findByPrimaryKey
	 * @generated
	 */
	public onjlib.ejb.mycmp.Mycmp findByPrimaryKey(
		java.lang.Integer primaryKey)
		throws javax.ejb.FinderException, java.rmi.RemoteException {
		return (onjlib.ejb.mycmp.Mycmp) super.activateBean(primaryKey);
	}
	/**
	 * create
	 * @generated
	 */
	public onjlib.ejb.mycmp.Mycmp create(java.lang.Integer id)
		throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		onjlib.ejb.mycmp.Mycmp result = null;
		boolean createFailed = false;
		boolean preCreateFlag = false;
		try {
			beanO = super.createBeanO();
			onjlib.ejb.mycmp.MycmpBean bean =
				(onjlib.ejb.mycmp.MycmpBean) beanO.getEnterpriseBean();
			preCreateFlag = super.preEjbCreate(beanO);
			bean.ejbCreate(id);
			Object ejsKey = keyFromBean(bean);
			result =
				(onjlib.ejb.mycmp.Mycmp) super.postCreate(beanO, ejsKey, true);
			bean.ejbPostCreate(id);
			super.afterPostCreate(beanO, ejsKey);
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
			if (preCreateFlag && !createFailed)
				super.afterPostCreateCompletion(beanO);
			if (createFailed) {
				super.createFailure(beanO);
			}
		}
		return result;
	}
	/**
	 * findByPrimaryKey_Local
	 * @generated
	 */
	public onjlib.ejb.mycmp.MycmpLocal findByPrimaryKey_Local(
		java.lang.Integer primaryKey)
		throws javax.ejb.FinderException, java.rmi.RemoteException {
		return (onjlib.ejb.mycmp.MycmpLocal) super.activateBean_Local(
			primaryKey);
	}
	/**
	 * create_Local
	 * @generated
	 */
	public onjlib.ejb.mycmp.MycmpLocal create_Local(java.lang.Integer id)
		throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		onjlib.ejb.mycmp.MycmpLocal result = null;
		boolean createFailed = false;
		boolean preCreateFlag = false;
		try {
			beanO = super.createBeanO();
			onjlib.ejb.mycmp.MycmpBean bean =
				(onjlib.ejb.mycmp.MycmpBean) beanO.getEnterpriseBean();
			preCreateFlag = super.preEjbCreate(beanO);
			bean.ejbCreate(id);
			Object ejsKey = keyFromBean(bean);
			result =
				(onjlib.ejb.mycmp.MycmpLocal) super.postCreate_Local(
					beanO,
					ejsKey,
					true);
			bean.ejbPostCreate(id);
			super.afterPostCreate(beanO, ejsKey);
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
			if (preCreateFlag && !createFailed)
				super.afterPostCreateCompletion(beanO);
			if (createFailed) {
				super.createFailure(beanO);
			}
		}
		return result;
	}
	/**
	 * keyFromBean
	 * @generated
	 */
	public Object keyFromBean(javax.ejb.EntityBean generalEJB) {
		onjlib.ejb.mycmp.ConcreteMycmp_8b6f9b9b tmpEJB =
			(onjlib.ejb.mycmp.ConcreteMycmp_8b6f9b9b) generalEJB;
		return tmpEJB.getId();
	}
	/**
	 * keyFromFields
	 * @generated
	 */
	public java.lang.Integer keyFromFields(java.lang.Integer f0) {
		return f0;
	}
}
