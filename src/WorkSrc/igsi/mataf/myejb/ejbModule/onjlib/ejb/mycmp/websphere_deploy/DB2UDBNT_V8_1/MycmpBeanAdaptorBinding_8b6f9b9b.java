package onjlib.ejb.mycmp.websphere_deploy.DB2UDBNT_V8_1;
/**
 * MycmpBeanAdaptorBinding_8b6f9b9b
 * @generated
 */
public class MycmpBeanAdaptorBinding_8b6f9b9b
	implements com.ibm.ws.ejbpersistence.beanextensions.BeanAdaptorBinding {
	/**
	 * getExtractor
	 * @generated
	 */
	public com.ibm.ws.ejbpersistence.dataaccess.EJBExtractor getExtractor() {
		int beanChunkLength = 3;
		// extractor for onjlib.ejb.mycmp.websphere_deploy.DB2UDBNT_V8_1.MycmpBeanExtractor_8b6f9b9b
		com.ibm.ws.ejbpersistence.dataaccess.AbstractEJBExtractor extractor =
			new onjlib
				.ejb
				.mycmp
				.websphere_deploy
				.DB2UDBNT_V8_1
				.MycmpBeanExtractor_8b6f9b9b();
		extractor.setChunkLength(beanChunkLength);
		extractor.setPrimaryKeyColumns(new int[] { 1 });
		extractor.setDataColumns(new int[] { 1, 2, 3 });
		return extractor;
	}
	/**
	 * getInjector
	 * @generated
	 */
	public com.ibm.ws.ejbpersistence.beanextensions.EJBInjector getInjector() {
		return new onjlib
			.ejb
			.mycmp
			.websphere_deploy
			.DB2UDBNT_V8_1
			.MycmpBeanInjectorImpl_8b6f9b9b();
	}
	/**
	 * getAdapter
	 * @generated
	 */
	public com.ibm.websphere.ejbpersistence.EJBToRAAdapter getAdapter() {
		return com.ibm.ws.rsadapter.cci.WSRelationalRAAdapter.createAdapter();
	}
	/**
	 * getMetadata
	 * @generated
	 */
	public Object[] getMetadata() {

		java.lang.String[] primarykey, subhomes, composedObjs, composedObjImpls;
		com.ibm.ObjectQuery.metadata.OSQLExternalCatalogEntry[] cat;
		com.ibm.ObjectQuery.metadata.OSQLExternalColumnDef[] fields;
		cat = new com.ibm.ObjectQuery.metadata.OSQLExternalCatalogEntry[4];
		//-------------------------------------------------------------------------
		cat[0] =
			new com.ibm.ObjectQuery.metadata.OSQLExternalCatalogRDBAlias(
				"Mycmp",
				"Mycmp1_Alias",
				"DB2NT",
				"MYCMP.MYCMP",
				"Mycmp_Mycmp1_Table");

		//-------------------------------------------------------------------------
		fields = new com.ibm.ObjectQuery.metadata.OSQLExternalColumnDef[3];

		fields[0] =
			new com.ibm.ObjectQuery.metadata.OSQLExternalColumnDef(
				"ID",
				new String(),
				com.ibm.ObjectQuery.engine.OSQLSymbols._INTEGER,
				0,
				com.ibm.ObjectQuery.engine.OSQLConstants.NO_TYPE,
				true,
				0,
				-1,
				0);
		fields[1] =
			new com.ibm.ObjectQuery.metadata.OSQLExternalColumnDef(
				"NAME",
				new String(),
				com.ibm.ObjectQuery.engine.OSQLSymbols._CHARACTER,
				250,
				com.ibm.ObjectQuery.engine.OSQLConstants.NO_TYPE,
				false,
				0,
				-1,
				0);
		fields[2] =
			new com.ibm.ObjectQuery.metadata.OSQLExternalColumnDef(
				"DESCRIPTION",
				new String(),
				com.ibm.ObjectQuery.engine.OSQLSymbols._CHARACTER,
				250,
				com.ibm.ObjectQuery.engine.OSQLConstants.NO_TYPE,
				false,
				0,
				-1,
				0);
		primarykey = new String[1];
		primarykey[0] = "ID";
		cat[1] =
			new com.ibm.ObjectQuery.metadata.OSQLExternalCatalogType(
				"Mycmp",
				"Mycmp1_Table",
				null,
				fields,
				primarykey);

		//-------------------------------------------------------------------------
		fields = new com.ibm.ObjectQuery.metadata.OSQLExternalColumnDef[3];

		fields[0] =
			new com.ibm.ObjectQuery.metadata.OSQLExternalColumnDef(
				"id",
				new String(),
				com.ibm.ObjectQuery.engine.OSQLSymbols._INTEGER,
				0,
				com.ibm.ObjectQuery.engine.OSQLConstants.NO_TYPE,
				false,
				0,
				-1,
				0);
		fields[1] =
			new com.ibm.ObjectQuery.metadata.OSQLExternalColumnDef(
				"name",
				new String(),
				com.ibm.ObjectQuery.engine.OSQLSymbols._CHARACTER,
				0,
				com.ibm.ObjectQuery.engine.OSQLConstants.NO_TYPE,
				false,
				0,
				-1,
				0);
		fields[2] =
			new com.ibm.ObjectQuery.metadata.OSQLExternalColumnDef(
				"description",
				new String(),
				com.ibm.ObjectQuery.engine.OSQLSymbols._CHARACTER,
				0,
				com.ibm.ObjectQuery.engine.OSQLConstants.NO_TYPE,
				false,
				0,
				-1,
				0);
		primarykey = new String[1];
		primarykey[0] = "id";
		cat[2] =
			new com.ibm.ObjectQuery.metadata.OSQLExternalCatalogType(
				"Mycmp",
				"Mycmp_BO",
				"onjlib.ejb.mycmp.MycmpBean",
				fields,
				primarykey);

		//-------------------------------------------------------------------------
		composedObjs = null;
		composedObjImpls = null;
		subhomes = null;
		cat[3] =
			new com.ibm.ObjectQuery.metadata.OSQLExternalCatalogView(
				"Mycmp",
				"Mycmp_Mycmp_BO",
				"Mycmp_Mycmp1_Alias",
				composedObjs,
				composedObjImpls,
				"select t1.ID,t1.NAME,t1.DESCRIPTION from _this t1",
				null,
				subhomes,
				0,
				null);

		return cat;
	}
	/**
	 * createDataAccessSpecs
	 * @generated
	 */
	public java.util.Collection createDataAccessSpecs()
		throws javax.resource.ResourceException {
		com.ibm.ws.ejbpersistence.beanextensions.EJBDataAccessSpec daSpec;
		com.ibm.ws.rsadapter.cci.WSInteractionSpecImpl iSpec;
		java.util.Collection result = new java.util.ArrayList(6);

		daSpec =
			com
				.ibm
				.ws
				.ejbpersistence
				.beanextensions
				.DataAccessSpecFactory
				.getDataAccessSpec();
		iSpec = new com.ibm.ws.rsadapter.cci.WSInteractionSpecImpl();
		iSpec.setFunctionSetName(
			"onjlib.ejb.mycmp.websphere_deploy.DB2UDBNT_V8_1.MycmpBeanFunctionSet_8b6f9b9b");
		iSpec.setFunctionName("Create");
		daSpec.setInteractionSpec(iSpec);
		daSpec.setSpecName("Create");
		daSpec.setInputRecordName("Create");
		daSpec.setOptimistic(false);
		daSpec.setType(
			com
				.ibm
				.ws
				.ejbpersistence
				.beanextensions
				.EJBDataAccessSpec
				.CREATE_BEAN);
		daSpec.setQueryScope(new String[] { "Mycmp" });
		result.add(daSpec);

		daSpec =
			com
				.ibm
				.ws
				.ejbpersistence
				.beanextensions
				.DataAccessSpecFactory
				.getDataAccessSpec();
		iSpec = new com.ibm.ws.rsadapter.cci.WSInteractionSpecImpl();
		iSpec.setFunctionSetName(
			"onjlib.ejb.mycmp.websphere_deploy.DB2UDBNT_V8_1.MycmpBeanFunctionSet_8b6f9b9b");
		iSpec.setFunctionName("Remove");
		daSpec.setInteractionSpec(iSpec);
		daSpec.setSpecName("Remove");
		daSpec.setInputRecordName("Remove");
		daSpec.setOptimistic(false);
		daSpec.setType(
			com
				.ibm
				.ws
				.ejbpersistence
				.beanextensions
				.EJBDataAccessSpec
				.REMOVE_BEAN);
		daSpec.setQueryScope(new String[] { "Mycmp" });
		result.add(daSpec);

		daSpec =
			com
				.ibm
				.ws
				.ejbpersistence
				.beanextensions
				.DataAccessSpecFactory
				.getDataAccessSpec();
		iSpec = new com.ibm.ws.rsadapter.cci.WSInteractionSpecImpl();
		iSpec.setFunctionSetName(
			"onjlib.ejb.mycmp.websphere_deploy.DB2UDBNT_V8_1.MycmpBeanFunctionSet_8b6f9b9b");
		iSpec.setFunctionName("Store");
		daSpec.setInteractionSpec(iSpec);
		daSpec.setSpecName("Store");
		daSpec.setInputRecordName("Store");
		daSpec.setOptimistic(false);
		daSpec.setType(
			com
				.ibm
				.ws
				.ejbpersistence
				.beanextensions
				.EJBDataAccessSpec
				.STORE_BEAN);
		daSpec.setQueryScope(new String[] { "Mycmp" });
		result.add(daSpec);

		daSpec =
			com
				.ibm
				.ws
				.ejbpersistence
				.beanextensions
				.DataAccessSpecFactory
				.getDataAccessSpec();
		iSpec = new com.ibm.ws.rsadapter.cci.WSInteractionSpecImpl();
		iSpec.setFunctionSetName(
			"onjlib.ejb.mycmp.websphere_deploy.DB2UDBNT_V8_1.MycmpBeanFunctionSet_8b6f9b9b");
		iSpec.setFunctionName("StoreUsingOCC");
		daSpec.setInteractionSpec(iSpec);
		daSpec.setSpecName("Store");
		daSpec.setInputRecordName("Store");
		daSpec.setOptimistic(true);
		daSpec.setType(
			com
				.ibm
				.ws
				.ejbpersistence
				.beanextensions
				.EJBDataAccessSpec
				.STORE_BEAN);
		daSpec.setQueryScope(new String[] { "Mycmp" });
		result.add(daSpec);

		daSpec =
			com
				.ibm
				.ws
				.ejbpersistence
				.beanextensions
				.DataAccessSpecFactory
				.getDataAccessSpec();
		iSpec = new com.ibm.ws.rsadapter.cci.WSInteractionSpecImpl();
		iSpec.setFunctionSetName(
			"onjlib.ejb.mycmp.websphere_deploy.DB2UDBNT_V8_1.MycmpBeanFunctionSet_8b6f9b9b");
		iSpec.setFunctionName("FindByPrimaryKey");
		daSpec.setInteractionSpec(iSpec);
		daSpec.setSpecName("FindByPrimaryKey");
		daSpec.setInputRecordName("FindByPrimaryKey");
		daSpec.setOptimistic(false);
		daSpec.setType(
			com.ibm.ws.ejbpersistence.beanextensions.EJBDataAccessSpec.FIND_PK);
		daSpec.setQueryScope(new String[] { "Mycmp" });
		daSpec.setReadAccess(true);
		daSpec.setAllowDuplicates(false);
		daSpec.setContainsDuplicates(false);
		daSpec.setSingleResult(true);
		daSpec.setExtractor(
			new com.ibm.ws.ejbpersistence.dataaccess.WholeRowExtractor(
				getExtractor()));
		result.add(daSpec);

		daSpec =
			com
				.ibm
				.ws
				.ejbpersistence
				.beanextensions
				.DataAccessSpecFactory
				.getDataAccessSpec();
		iSpec = new com.ibm.ws.rsadapter.cci.WSInteractionSpecImpl();
		iSpec.setFunctionSetName(
			"onjlib.ejb.mycmp.websphere_deploy.DB2UDBNT_V8_1.MycmpBeanFunctionSet_8b6f9b9b");
		iSpec.setFunctionName("FindByPrimaryKeyForUpdate");
		daSpec.setInteractionSpec(iSpec);
		daSpec.setSpecName("FindByPrimaryKey");
		daSpec.setInputRecordName("FindByPrimaryKey");
		daSpec.setOptimistic(false);
		daSpec.setType(
			com.ibm.ws.ejbpersistence.beanextensions.EJBDataAccessSpec.FIND_PK);
		daSpec.setQueryScope(new String[] { "Mycmp" });
		daSpec.setReadAccess(false);
		daSpec.setAllowDuplicates(false);
		daSpec.setContainsDuplicates(false);
		daSpec.setSingleResult(true);
		daSpec.setExtractor(
			new com.ibm.ws.ejbpersistence.dataaccess.WholeRowExtractor(
				getExtractor()));
		result.add(daSpec);

		return result;

	}
}
