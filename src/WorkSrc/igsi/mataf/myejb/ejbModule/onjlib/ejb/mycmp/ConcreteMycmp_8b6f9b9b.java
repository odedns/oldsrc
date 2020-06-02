package onjlib.ejb.mycmp;
import com.ibm.ws.ejbpersistence.beanextensions.*;
import com.ibm.websphere.cpmi.*;
import javax.resource.cci.*;
/**
 * Bean implementation class for Enterprise Bean: Mycmp
 * @generated
 */
public class ConcreteMycmp_8b6f9b9b
	extends onjlib.ejb.mycmp.MycmpBean
	implements javax.ejb.EntityBean, ConcreteBean {
	/**
	 * @generated
	 */
	private ConcreteBeanInstanceExtension instanceExtension;
	/**
	 * Implementation field for persistent attribute: id
	 * @generated
	 */
	public java.lang.Integer id;
	/**
	 * Implementation field for persistent attribute: name
	 * @generated
	 */
	public java.lang.String name;
	/**
	 * Implementation field for persistent attribute: description
	 * @generated
	 */
	public java.lang.String description;
	/**
	 * setEntityContext
	 * @generated
	 */
	public void setEntityContext(javax.ejb.EntityContext ctx) {
		super.setEntityContext(ctx);
		instanceExtension.setEntityContext(ctx);
	}
	/**
	 * unsetEntityContext
	 * @generated
	 */
	public void unsetEntityContext() {
		super.unsetEntityContext();
		instanceExtension.unsetEntityContext();
	}
	/**
	 * ejbActivate
	 * @generated
	 */
	public void ejbActivate() {
		super.ejbActivate();
		instanceExtension.ejbActivate();
	}
	/**
	 * ejbLoad
	 * @generated
	 */
	public void ejbLoad() {
		instanceExtension.ejbLoad();
	}
	/**
	 * ejbPassivate
	 * @generated
	 */
	public void ejbPassivate() {
		super.ejbPassivate();
		instanceExtension.ejbPassivate();
	}
	/**
	 * ejbRemove
	 * @generated
	 */
	public void ejbRemove() throws javax.ejb.RemoveException {
		super.ejbRemove();
		instanceExtension.ejbRemove();
	}
	/**
	 * ejbStore
	 * @generated
	 */
	public void ejbStore() {
		super.ejbStore();
		instanceExtension.ejbStore();
	}
	/**
	 * _WSCB_getInstanceInfo
	 * @generated
	 */
	public PMConcreteBeanInstanceInfo _WSCB_getInstanceInfo() {
		return instanceExtension;
	}
	/**
	 * ConcreteMycmp_8b6f9b9b
	 * @generated
	 */
	public ConcreteMycmp_8b6f9b9b() {
		super();
		instanceExtension =
			ConcreteBeanInstanceExtensionFactory.getInstance(this);
	}
	/**
	 * getInjector
	 * @generated
	 */
	private onjlib
		.ejb
		.mycmp
		.websphere_deploy
		.MycmpBeanInjector_8b6f9b9b getInjector() {
		return (
			onjlib
				.ejb
				.mycmp
				.websphere_deploy
				.MycmpBeanInjector_8b6f9b9b) instanceExtension
			.getInjector();
	}
	/**
	 * hydrate
	 * @generated
	 */
	public void hydrate(Object inRecord) {
		onjlib.ejb.mycmp.websphere_deploy.MycmpBeanCacheEntry_8b6f9b9b record =
			(onjlib
				.ejb
				.mycmp
				.websphere_deploy
				.MycmpBeanCacheEntry_8b6f9b9b) inRecord;
		;
		id = record.getId();
		name = record.getName();
		description = record.getDescription();
		super.ejbLoad();
	}
	/**
	 * resetCMP
	 * @generated
	 */
	public void resetCMP() {
		id = null;
		name = null;
		description = null;
	}
	/**
	 * resetCMR
	 * @generated
	 */
	public void resetCMR() {
	}
	/**
	 * ejbFindByPrimaryKey
	 * @generated
	 */
	public java.lang.Integer ejbFindByPrimaryKey(java.lang.Integer primaryKey)
		throws javax.ejb.FinderException {
		return (java.lang.Integer) instanceExtension.ejbFindByPrimaryKey(
			primaryKey);
	}
	/**
	 * ejbFindByPrimaryKey
	 * @generated
	 */
	public Object ejbFindByPrimaryKey(java.lang.Object pk)
		throws javax.ejb.FinderException {
		return ejbFindByPrimaryKey((java.lang.Integer) pk);
	}
	/**
	 * ejbFindByPrimaryKeyForCMR_Local
	 * @generated
	 */
	public java.lang.Integer ejbFindByPrimaryKeyForCMR_Local(
		java.lang.Integer pk)
		throws javax.ejb.FinderException {
		return (java.lang.Integer) instanceExtension.ejbFindByPrimaryKey(pk);
	}
	/**
	 * ejbCreate
	 * @generated
	 */
	public java.lang.Integer ejbCreate(java.lang.Integer id)
		throws javax.ejb.CreateException {
		super.ejbCreate(id);
		return (java.lang.Integer) instanceExtension.ejbCreate();
	}
	/**
	 * ejbPostCreate
	 * @generated
	 */
	public void ejbPostCreate(java.lang.Integer id)
		throws javax.ejb.CreateException {
		super.ejbPostCreate(id);
		instanceExtension.ejbPostCreate();
	}
	/**
	 * createPrimaryKey
	 * @generated
	 */
	public Object createPrimaryKey() {
		return id;
	}
	/**
	 * getNumberOfFields
	 * @generated
	 */
	public int getNumberOfFields() {
		return 3;
	}
	/**
	 * Get accessor for persistent attribute: id
	 * @generated
	 */
	public java.lang.Integer getId() {
		return id;
	}
	/**
	 * Set accessor for persistent attribute: id
	 * @generated
	 */
	public void setId(java.lang.Integer newId) {
		instanceExtension.markDirty(0, id, newId);
		id = newId;
	}
	/**
	 * Get accessor for persistent attribute: name
	 * @generated
	 */
	public java.lang.String getName() {
		return name;
	}
	/**
	 * Set accessor for persistent attribute: name
	 * @generated
	 */
	public void setName(java.lang.String newName) {
		instanceExtension.markDirty(1, name, newName);
		name = newName;
	}
	/**
	 * Get accessor for persistent attribute: description
	 * @generated
	 */
	public java.lang.String getDescription() {
		return description;
	}
	/**
	 * Set accessor for persistent attribute: description
	 * @generated
	 */
	public void setDescription(java.lang.String newDescription) {
		instanceExtension.markDirty(2, description, newDescription);
		description = newDescription;
	}
}
