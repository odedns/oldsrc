package onjlib.ejb.mycmp.websphere_deploy.DB2UDBNT_V8_1;
import com.ibm.ejs.persistence.*;
import javax.ejb.EntityBean;
import java.sql.*;
import java.text.*;
import com.ibm.vap.converters.*;
import com.ibm.vap.converters.streams.*;
/**
 * MycmpBeanInjectorImpl_8b6f9b9b
 * @generated
 */
public class MycmpBeanInjectorImpl_8b6f9b9b
	implements onjlib.ejb.mycmp.websphere_deploy.MycmpBeanInjector_8b6f9b9b {
	/**
	 * ejbCreate
	 * @generated
	 */
	public void ejbCreate(
		com.ibm.ws.ejbpersistence.beanextensions.ConcreteBean cb,
		javax.resource.cci.IndexedRecord record) {
		onjlib.ejb.mycmp.ConcreteMycmp_8b6f9b9b concreteBean =
			(onjlib.ejb.mycmp.ConcreteMycmp_8b6f9b9b) cb;
		record.set(0, concreteBean.getId());
		record.set(1, concreteBean.getName());
		record.set(2, concreteBean.getDescription());
	}
	/**
	 * ejbStore
	 * @generated
	 */
	public void ejbStore(
		com.ibm.ws.ejbpersistence.beanextensions.ConcreteBean cb,
		javax.resource.cci.IndexedRecord record) {
		onjlib.ejb.mycmp.ConcreteMycmp_8b6f9b9b concreteBean =
			(onjlib.ejb.mycmp.ConcreteMycmp_8b6f9b9b) cb;
		record.set(0, concreteBean.getId());
		record.set(1, concreteBean.getName());
		record.set(2, concreteBean.getDescription());
	}
	/**
	 * ejbRemove
	 * @generated
	 */
	public void ejbRemove(
		com.ibm.ws.ejbpersistence.beanextensions.ConcreteBean cb,
		javax.resource.cci.IndexedRecord record) {
		onjlib.ejb.mycmp.ConcreteMycmp_8b6f9b9b concreteBean =
			(onjlib.ejb.mycmp.ConcreteMycmp_8b6f9b9b) cb;
		record.set(0, concreteBean.getId());
	}
	/**
	 * ejbFindByPrimaryKey
	 * @generated
	 */
	public void ejbFindByPrimaryKey(
		Object pkeyObject,
		javax.resource.cci.IndexedRecord record) {
		java.lang.Integer pkey = (java.lang.Integer) pkeyObject;
		record.set(0, pkey);
	}
}
