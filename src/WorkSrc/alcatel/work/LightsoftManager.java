/*
 * Created on 28/09/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package hoshen.xsm.lightsoft;

import hoshen.common.utils.PropertyReader;
import hoshen.common.utils.ToStringBuilder;
import hoshen.common.utils.exception.HoshenException;
import hoshen.xsm.lightsoft.corba.CosEventComm.Disconnected;
import hoshen.xsm.lightsoft.corba.CosNotification.StructuredEvent;
import hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.*;
import hoshen.xsm.lightsoft.corba.CosNotifyComm.StructuredPullConsumer;
import hoshen.xsm.lightsoft.corba.common.Common_IHolder;
import hoshen.xsm.lightsoft.corba.emsMgr.EMSMgr_I;
import hoshen.xsm.lightsoft.corba.emsMgr.EMSMgr_IHelper;
import hoshen.xsm.lightsoft.corba.emsSession.EmsSession_I;
import hoshen.xsm.lightsoft.corba.emsSession.EmsSession_IHolder;
import hoshen.xsm.lightsoft.corba.emsSessionFactory.EmsSessionFactory_I;
import hoshen.xsm.lightsoft.corba.emsSessionFactory.EmsSessionFactory_IHelper;
import hoshen.xsm.lightsoft.corba.equipment.*;
import hoshen.xsm.lightsoft.corba.globaldefs.NameAndStringValue_T;
import hoshen.xsm.lightsoft.corba.globaldefs.ProcessingFailureException;
import hoshen.xsm.lightsoft.corba.maintenanceOps.*;
import hoshen.xsm.lightsoft.corba.managedElement.*;
import hoshen.xsm.lightsoft.corba.managedElementManager.ManagedElementMgr_I;
import hoshen.xsm.lightsoft.corba.managedElementManager.ManagedElementMgr_IHelper;
import hoshen.xsm.lightsoft.corba.multiLayerSubnetwork.*;
import hoshen.xsm.lightsoft.corba.nmsSession.NmsSession_I;
import hoshen.xsm.lightsoft.corba.nmsSession.NmsSession_IPOATie;
import hoshen.xsm.lightsoft.corba.nmsSession._NmsSession_IStub;
import hoshen.xsm.lightsoft.corba.notifications.EventIterator_IHolder;
import hoshen.xsm.lightsoft.corba.notifications.EventList_THolder;
import hoshen.xsm.lightsoft.corba.notifications.PerceivedSeverity_T;
import hoshen.xsm.lightsoft.corba.subnetworkConnection.*;
import hoshen.xsm.lightsoft.corba.terminationPoint.TerminationPointIterator_IHolder;
import hoshen.xsm.lightsoft.corba.terminationPoint.TerminationPointList_THolder;
import hoshen.xsm.lightsoft.corba.terminationPoint.TerminationPoint_T;
import hoshen.xsm.lightsoft.corba.topologicalLink.TopologicalLinkIterator_IHolder;
import hoshen.xsm.lightsoft.corba.topologicalLink.TopologicalLinkList_THolder;
import hoshen.xsm.lightsoft.corba.topologicalLink.TopologicalLink_T;

import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.omg.CORBA.BooleanHolder;
import org.omg.CORBA.IntHolder;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAPackage.WrongPolicy;


/**
 * @author Oded Nissan
 *
 * This class contains utility methods for accessing the ECI Lightsoft
 * system.
 */
public class LightsoftManager {

	private static final Logger log = Logger.getLogger(LightsoftManager.class);
	private static final String SLASH_SEP = "/";
	private static final char DOT_CHAR = '.';
	public static final short LR_STS3c_and_AU4_VC4 = 15;
	/** comment for <code>MANAGED_ELEMENT</code> */
	public static final String MANAGED_ELEMENT = "ManagedElement";
	public static final String SNC = "SubnetworkConnection";
	public static final String MULTILAYER_SUBNETWORK = "MultiLayerSubnetwork";
	public static final String EQUIPMENT_INVENTORY = "EquipmentInventory";
	public static final String MAINTENANCE = "Maintenance";
	public static final String PTP = "PTP"; 
	public static final String CTP = "CTP";
	public  static final String EMS = "EMS";
	private static final String LIGHTSOFT_PASSWORD = "lightsoft.password";
	private static final String LIGHTSOFT_USER = "lightsoft.user";
	private static final String LIGHTSOFT_PROPERTY_FILE = "hoshen/xsm/lightsoft/lightsoft.properties";
	private static final String NAME_SERVICE = "NameService";
	private static final String ROOT_POA = "RootPOA";
	public static final int MOM_OPERATE = 0;
	public static final int MOM_RELEASE = 1;
	public static final short RATE_ALL = -1;
	private final int CHUNK_SIZE = 100;
	private EmsSession_I m_emsSession = null;
	private EMSMgr_I m_emsMgr = null;;
	private ManagedElementMgr_I m_elemMgr = null;
	private EquipmentInventoryMgr_I m_equipMgr = null;
	private MultiLayerSubnetworkMgr_I m_subnetMgr = null;
	private MaintenanceMgr_I m_maintenanceMgr = null;
	private NameAndStringValue_T m_subnetName[] = null;
	private NameAndStringValue_T m_emsName = null;
	private ORB m_orb = null;
	private POA m_poa = null;
	private NmsSession_I m_nmsSession = null;
	/**
	 * the singleton instance.
	 */
	private static LightsoftManager m_instance = null;
	private StructuredProxyPullSupplier m_proxyPullSupplier = null; 
	
	
	/**
	 * Singleton constructor.
	 * @return LightsoftManager singleton instance.
	 */
	public static synchronized LightsoftManager getInstance() throws HoshenException
	{
		if(null == m_instance){
			m_instance = new LightsoftManager(null,null);
		}
		return(m_instance);
		
	}
	/**
	 * Singleton constructor.
	 * @param user the user name to use.
	 * @param Password the password.
	 * @return LightsoftManager singleton instance.
	 */
	public static synchronized LightsoftManager getInstance(String user, String password) throws HoshenException
	{
		if(null == m_instance){
			m_instance = new LightsoftManager(user,password);
		}
		return(m_instance);
		
	}
	
	/**
	 * private constructor.
	 * prevent instantiation.
	 * @throws HoshenException in case of error.
	 */
	private LightsoftManager(String user, String password) throws HoshenException
	{
		initSession( user, password);
	}
		
	/**
	 * Establish Ems session.
	 * @return the EmsSession object.
	 */
	public EmsSession_I getEmsSession()
	{
		return(m_emsSession);
	}
	
	
	/**
	 * init the session.
	 * @throws HoshenException in case of error.
	 * @throws HoshenException
	 */
	private void initSessionOld() throws HoshenException 
	{
		try {
			Properties p = PropertyReader.read(LIGHTSOFT_PROPERTY_FILE);
			m_orb = ORB.init( ( String[] ) null, p );
			org.omg.CORBA.Object ns = m_orb.resolve_initial_references( NAME_SERVICE );
			NamingContextExt initCtx = NamingContextExtHelper.narrow( ns );
			log.debug( "NameService=" + m_orb.object_to_string( ns ) );
			
			/*
			 * get the name component path from a property file.
			 */
			String nc = p.getProperty("nameComponent");
			if(nc == null || nc.length() == 0) {
				throw new HoshenException("nameComponent property in lightsoft.properties is empty");
			}
			NameComponent path[] = getNameComponent(nc);
			org.omg.CORBA.Object obj = initCtx.resolve( path );		
			EmsSessionFactory_I emsSessionFactory = EmsSessionFactory_IHelper.narrow( obj );
			NmsSession_I nmsSession = new _NmsSession_IStub();
		   
			EmsSession_IHolder emsSessionHolder = new EmsSession_IHolder();
			
			String user = p.getProperty(LIGHTSOFT_USER);
			if(user == null || "".equals(user)) {
				throw new HoshenException("Error: no lightsoft user name in properties");
			}
			String password = p.getProperty(LIGHTSOFT_PASSWORD);
			emsSessionFactory.getEmsSession(user, password,nmsSession,emsSessionHolder );
			log.debug("user= " + user + "\temsSession=" + emsSessionHolder.toString());
			m_emsSession = emsSessionHolder.value;
			m_subnetName = getSubnetwork();
			m_emsName = m_subnetName[0];
		} catch(Exception e) {
			throw new HoshenException("Error in getEmsSession ", e);
		}
		
	}	
	
	/**
	 * init the session.
	 * @throws HoshenException in case of error.
	 * @throws HoshenException
	 */
	private void initSession(String user, String password) throws HoshenException 
	{
		try {
			Properties p = PropertyReader.read(LIGHTSOFT_PROPERTY_FILE);
			m_orb = ORB.init( ( String[] ) null, p );
			org.omg.CORBA.Object ns = m_orb.resolve_initial_references( NAME_SERVICE );
			NamingContextExt initCtx = NamingContextExtHelper.narrow( ns );
			log.debug( "NameService=" + m_orb.object_to_string( ns ) );
			org.omg.CORBA.Object root_poa = m_orb.resolve_initial_references(ROOT_POA);
			m_poa = POAHelper.narrow(root_poa);
					
			/*
			 * get the name component path from a property file.
			 */
			String nc = p.getProperty("nameComponent");
			if(nc == null || nc.length() == 0) {
				throw new HoshenException("nameComponent property in lightsoft.properties is empty");
			}
			NameComponent path[] = getNameComponent(nc);
			org.omg.CORBA.Object obj = initCtx.resolve( path );		
			EmsSessionFactory_I emsSessionFactory = EmsSessionFactory_IHelper.narrow( obj );
			LightsoftNmsSession nmsSessionImpl = new LightsoftNmsSession();
			NmsSession_IPOATie tie =new  NmsSession_IPOATie(nmsSessionImpl,m_poa);
			m_poa.activate_object(tie);
			m_nmsSession = tie._this();
			EmsSession_IHolder emsSessionHolder = new EmsSession_IHolder();
			if(user == null) {
				user = p.getProperty(LIGHTSOFT_USER);
				if(user == null || "".equals(user)) {
					throw new HoshenException("Error: no lightsoft user name in properties");
				}			
				password = p.getProperty(LIGHTSOFT_PASSWORD);
			}
			log.debug("user: " + user);
			emsSessionFactory.getEmsSession(user, password,m_nmsSession,emsSessionHolder );					
			log.debug("established emsSession=" + emsSessionHolder.toString());
			m_emsSession = emsSessionHolder.value;
			m_subnetName = getSubnetwork();
			m_emsName = m_subnetName[0];
		} catch(Exception e) {
			e.printStackTrace();
			String err = "";
			if(e instanceof ProcessingFailureException) {
				err = ((ProcessingFailureException)e).errorReason;
			}
			throw new HoshenException("Error in getEmsSession: " + err, e);
		}
		
	}	
	

	
	/**
	 * get the ORB object.
	 * @return ORB the orb object used.
	 */
	public ORB getORB()
	{
		return(m_orb);
	}
	/**
	 * Get the ems manager object.
	 * @return Ems manager object.
	 */
	public EMSMgr_I getEmsManager() throws ProcessingFailureException
	{	
		if(m_emsMgr != null ) {
			return(m_emsMgr);
		}		
		Common_IHolder holder = getManager(EMS);
		m_emsMgr = EMSMgr_IHelper.narrow(holder.value);
		return(m_emsMgr);
	}
	
	
	
	/**
	 * get a manager by name
	 * @param name the name of the manager to get
	 * @return the holder object.
	 * @throws HoshenException
	 */
	private Common_IHolder getManager(String name) throws ProcessingFailureException
	{		
		Common_IHolder holder = new Common_IHolder();
		m_emsSession.getManager(name,holder);
		return(holder);		
	}
	/**
	 * Get the Managed element manager.
	 * @return the Managed Element manager object.
	 */
	public ManagedElementMgr_I getManagedElementManager() throws ProcessingFailureException
	{
		if(m_elemMgr != null) {
			return(m_elemMgr);
		}
		
		Common_IHolder holder = getManager(MANAGED_ELEMENT);
		m_elemMgr = ManagedElementMgr_IHelper.narrow(holder.value);
		return(m_elemMgr);
		
	}
	
	/**
	 * Get the Maintenance Manager object.
	 * @return Maintenance Manager object.
	 * @throws HoshenException in case of error.
	 */
	public MaintenanceMgr_I getMaintenanceManager() throws ProcessingFailureException
	{
		if(m_maintenanceMgr != null) {
			return(m_maintenanceMgr);
		}		
		Common_IHolder holder = getManager(MAINTENANCE);
		m_maintenanceMgr = MaintenanceMgr_IHelper.narrow(holder.value);
		return(m_maintenanceMgr);		
	}
	
	/**
	 * Get the equipment manager.
	 * @return the EquipmentInventoryMgr_I object.
	 */
	public EquipmentInventoryMgr_I getEquipmentManager() throws ProcessingFailureException
	{
	
		if(m_equipMgr != null) {
			return(m_equipMgr);
		}		
		Common_IHolder holder = getManager(EQUIPMENT_INVENTORY);
		m_equipMgr = EquipmentInventoryMgr_IHelper.narrow(holder.value);
		return(m_equipMgr);			
	}
	
	/**
	 * Get the multilayer subnetwork manager.
	 * @return MultiLayerSubnetworkMgr_I object.
	 */
	public MultiLayerSubnetworkMgr_I getMultiLayerSubnetMgr() throws ProcessingFailureException
	{
		if(m_subnetMgr != null) {
			return(m_subnetMgr);
		}		
		Common_IHolder holder = getManager(MULTILAYER_SUBNETWORK);
		m_subnetMgr = MultiLayerSubnetworkMgr_IHelper.narrow(holder.value);
		return(m_subnetMgr);		
	}
	
	/**
	 * Ping the NMS
	 * 
	 */
	public void ping() 
	{
		m_emsSession.ping();
	}
	
	
	/**
	 * Get all the subnetworks.
	 * @return a list containing managedElement objects.
	 */
	public NameAndStringValue_T[] getSubnetwork() throws ProcessingFailureException
	{
	
		NameAndStringValue_T v[] = null;
		SubnetworkIterator_IHolder iterHolder = new SubnetworkIterator_IHolder();
		SubnetworkList_THolder listHolder = new SubnetworkList_THolder();
		getEmsManager().getAllTopLevelSubnetworks(0,listHolder,iterHolder);			
		iterHolder.value.next_n(CHUNK_SIZE,listHolder);
		MultiLayerSubnetwork_T elements[] = listHolder.value;
		v = elements[0].name;													
		return(v);
	}
	/**
	 * Get all the subnetworks.
	 * @return a list containing managedElement objects.
	 */
	public List getAllSubnetworks() throws ProcessingFailureException
	{
		List<MultiLayerSubnetwork_T> l = new LinkedList<MultiLayerSubnetwork_T>();
		boolean hasMore = true;
		SubnetworkIterator_IHolder iterHolder = new SubnetworkIterator_IHolder();
		SubnetworkList_THolder listHolder = new SubnetworkList_THolder();
		getEmsManager().getAllTopLevelSubnetworks(0,listHolder,iterHolder);			
		while(hasMore) {
			hasMore = iterHolder.value.next_n(CHUNK_SIZE,listHolder);
			MultiLayerSubnetwork_T elements[] = listHolder.value;
			CollectionUtils.addAll(l,elements);
								
		}			
		return(l);
	}
	
	/**
	 * Get all the managed elements.
	 * @return a list containing managedElement objects.
	 */
	public List getAllManagedElements() throws ProcessingFailureException
	{
		List<ManagedElement_T> l = new LinkedList<ManagedElement_T>();
		boolean hasMore = true;
		ManagedElementIterator_IHolder iterHolder = new ManagedElementIterator_IHolder();
		ManagedElementList_THolder listHolder = new ManagedElementList_THolder();
		getManagedElementManager().getAllManagedElements(0,listHolder,iterHolder);			
		while(hasMore) {
			hasMore = iterHolder.value.next_n(CHUNK_SIZE,listHolder);
			ManagedElement_T elements[] = listHolder.value;
			CollectionUtils.addAll(l,elements);
								
		}			
		return(l);
	}
	
	
	/**
	 * get a managed element by name.
	 * @param name the name of the managed element.
	 * @return ManagedElement object.
	 */
	public ManagedElement_T getManagedElement(String name) throws ProcessingFailureException
	{
		ManagedElement_THolder holder = new ManagedElement_THolder();
		NameAndStringValue_T value = new NameAndStringValue_T(MANAGED_ELEMENT, name);
		NameAndStringValue_T v[] = new NameAndStringValue_T[1];
		v[0] = value;
		getManagedElementManager().getManagedElement(v,holder);						
		return(holder != null  ? holder.value: null);
	}
	
	/**
	 * Get all PTPs for a managed element.
	 * @param name the ,amaged element name.
	 * @return List the list of PTPs
	 * @throws HoshenException in case of error.
	 */
	public List getAllPTPs(String name) throws ProcessingFailureException
	{
		boolean hasMore = true;
		NameAndStringValue_T value = new NameAndStringValue_T("ManagedElement", name);
		NameAndStringValue_T v[] = new NameAndStringValue_T[1];
		v[0] = value;		
		short tpLayerRate[] = new short[0];
		short connLayerRate[] = new short[0];
		TerminationPointList_THolder tpList = new TerminationPointList_THolder();
		TerminationPointIterator_IHolder iterHolder = new TerminationPointIterator_IHolder();
		List<TerminationPoint_T> list = new LinkedList<TerminationPoint_T>();
		getManagedElementManager().getAllPTPs(v,tpLayerRate,connLayerRate,CHUNK_SIZE,tpList,iterHolder);
		if(tpList.value != null) {
			CollectionUtils.addAll(list,tpList.value);			
		} // if
		while(hasMore && iterHolder.value != null) {
			hasMore = iterHolder.value.next_n(CHUNK_SIZE,tpList);
			if(tpList.value != null) {
				CollectionUtils.addAll(list,tpList.value);					
			} // if
				
		} // while
		return(list);
	}
	
	/**
	 * Get all CTPs for a managed element. and a PTP.
	 * @param elemName the ,managed element name.
	 * @param type PTP or CTP.
	 * @param tpName the tpName of the PTP to search.
	 * @return List the list of CTPs
	 * @throws HoshenException in case of error.
	 */
	public List getAllCTPs(String elemName,String type, String tpName) throws ProcessingFailureException
	{
		boolean hasMore = true;
		NameAndStringValue_T v[] = new NameAndStringValue_T[2];
		v[0] = new NameAndStringValue_T(MANAGED_ELEMENT,elemName);
		v[1] = new NameAndStringValue_T(type, tpName);
		short tpLayerRate[] = new short[0];
		TerminationPointList_THolder tpList = new TerminationPointList_THolder();
		TerminationPointIterator_IHolder iterHolder = new TerminationPointIterator_IHolder();
		List<TerminationPoint_T> list = new LinkedList<TerminationPoint_T>();
		getManagedElementManager().getContainedPotentialTPs(v,tpLayerRate,CHUNK_SIZE,tpList,iterHolder);
		if(tpList.value != null) {
			CollectionUtils.addAll(list,tpList.value);			
		} // if
		while(hasMore && iterHolder.value != null) {
			hasMore = iterHolder.value.next_n(CHUNK_SIZE,tpList);
			if(tpList.value != null) {
				CollectionUtils.addAll(list,tpList.value);					
			} // if
				
		} // while
		return(list);
	}
	
	/**
	 * Get al PTPs and CTPs for a managedElement.
	 * First call getAllPTP and then for each PTP check for CTPs.
	 * @param elemName the managed element name.
	 * @return a list of all TPs.
	 * @throws HoshenException in case of error.
	 */
	public List getAllTps(String elemName) throws ProcessingFailureException
	{
		List<TerminationPoint_T> tpList = getAllPTPs(elemName);
		List<TerminationPoint_T> ctpList = new LinkedList<TerminationPoint_T>();
		
		Iterator iter = tpList.iterator();
		while(iter.hasNext() ) {
			TerminationPoint_T tp = (TerminationPoint_T) iter.next();
			String name = LSConverter.extractValue(tp.name,PTP);
			List<TerminationPoint_T> l = getAllCTPs(elemName,PTP,name);
			//ctpList.addAll(l);
			CollectionUtils.addAll(ctpList,l.iterator());
		}
		//tpList.addAll(ctpList);
		CollectionUtils.addAll(tpList, ctpList.iterator());
		return(tpList);
	}
	
	/**
	 * Get all equipment per managed element.
	 * @param elemName the element name to get the equipment for.
	 * @return a List of EquipmentOrHolder_T 
	 * @throws HoshenException
	 */
	public List getAllEquipment(String elemName) throws ProcessingFailureException
	{
		log.info("getAllEquiptmen: " + elemName);
		List<Equipment_T> l = new LinkedList<Equipment_T>();
		boolean hasMore = true;
		NameAndStringValue_T value = new NameAndStringValue_T("ManagedElement", elemName);
		NameAndStringValue_T v[] = new NameAndStringValue_T[2];
		v[0] = m_emsName;
		v[1] = value;
		EquipmentOrHolderList_THolder listHolder = new EquipmentOrHolderList_THolder();
		EquipmentOrHolderIterator_IHolder iterHolder =  new EquipmentOrHolderIterator_IHolder();
		getEquipmentManager().getAllEquipment(v,CHUNK_SIZE,listHolder,iterHolder);
		if(listHolder.value != null) {
			EquipmentOrHolder_T  equip[] = listHolder.value;
			for(int i=0; i < equip.length; ++i) {
				if(equip[i].discriminator() != EquipmentTypeQualifier_T.EQT_HOLDER) {					
					l.add(equip[i].equip());
				}
			}
				
		}
		while(hasMore && iterHolder.value != null) {
			hasMore = iterHolder.value.next_n(CHUNK_SIZE,listHolder);				
			EquipmentOrHolder_T  equip[] = listHolder.value;
			for(int i=0; i < equip.length; ++i) {
				if(equip[i].discriminator() != EquipmentTypeQualifier_T.EQT_HOLDER) {					
					l.add(equip[i].equip());
				}
			}
				
		}					
		return(l);
	}
	
	/**
	 * Get all supported PTPs for a specific Equipment.
	 * @param v a Name_And_String value of the Equipment object.
	 * @return List of TerminationPoint_T object.
	 * @throws HoshenException in case of error.
	 */
	public List getSupportedPTPs(NameAndStringValue_T v[]) throws ProcessingFailureException
	{
		log.debug("getSupportedPTP : " + LSConverter.nsvToString(v));
		TerminationPointList_THolder tpList = new TerminationPointList_THolder();
		TerminationPointIterator_IHolder iterHolder = new TerminationPointIterator_IHolder();
		List<TerminationPoint_T> list = new LinkedList<TerminationPoint_T>();
		boolean hasMore = true;
		getEquipmentManager().getAllSupportedPTPs(v, CHUNK_SIZE, tpList, iterHolder);
		if(tpList.value != null) {
			CollectionUtils.addAll(list,tpList.value);			
		} // if
		while(hasMore && iterHolder.value != null) {
			hasMore = iterHolder.value.next_n(CHUNK_SIZE,tpList);
			if(tpList.value != null) {
				CollectionUtils.addAll(list,tpList.value);					
			} // if
				
		} // while
		
		return(list);
		
	}

	/**
	 * Get all active maintenance operations on a managed element
	 * @param type could be ManagedElement PTP or CTP.
	 * @param tpName the name of managed element. 
	 * @return List of MaintenaceOp object.
	 * @throws HoshenException in case of error.
	 */
	public List getAllActiveMaintenanceOp(String type,String tpName ) throws ProcessingFailureException
	{
		List<CurrentMaintenanceOperation_T> l = new LinkedList<CurrentMaintenanceOperation_T>();
		boolean hasMore = true;
		CurrentMaintenanceOperation_T currOps[] = null;
		NameAndStringValue_T value = new NameAndStringValue_T(type, tpName);
		NameAndStringValue_T v[] = new NameAndStringValue_T[1];
		v[0] = value;
		CurrentMaintenanceOperationList_THolder listHolder = new CurrentMaintenanceOperationList_THolder();
		CurrentMaintenanceOperationIterator_IHolder iterHolder = new CurrentMaintenanceOperationIterator_IHolder();
		getMaintenanceManager().getActiveMaintenanceOperations(v,CHUNK_SIZE,listHolder,iterHolder);
		if(listHolder.value != null) {
			currOps = listHolder.value;
			CollectionUtils.addAll(l,currOps);
		}
		while(hasMore && iterHolder.value != null) {
			hasMore = iterHolder.value.next_n(CHUNK_SIZE,listHolder);
			currOps = listHolder.value;
			log.debug("length = " + currOps.length);
			CollectionUtils.addAll(l,currOps);
				
		}
	
		return(l);
	}
	
	/**
	 * Get all SNC
	 * @return List of SubnetworkConnection object.
	 * @throws HoshenException in case of error.
	 */
	public List getAllSNC(short rate) throws ProcessingFailureException
	{
		List<SubnetworkConnection_T> l = new LinkedList<SubnetworkConnection_T>();
		boolean hasMore = true;
		SubnetworkConnectionList_THolder listHolder = new SubnetworkConnectionList_THolder();
		SNCIterator_IHolder iterHolder = new SNCIterator_IHolder();
		short connRate[] = null;
		if(rate > RATE_ALL) {
			connRate = new short[1];
			connRate[0] = rate;
		} else {
			connRate = new short[0];
		}
	
		SubnetworkConnection_T sncs[] = null;
		getMultiLayerSubnetMgr().getAllSubnetworkConnections(m_subnetName,connRate,CHUNK_SIZE,listHolder,iterHolder);
		if(listHolder.value != null) {
			sncs = listHolder.value;
			CollectionUtils.addAll(l,sncs);
		}
		while(hasMore && iterHolder.value != null) {
			hasMore = iterHolder.value.next_n(CHUNK_SIZE,listHolder);
			sncs = listHolder.value;
			log.debug("length = " + sncs.length);
			CollectionUtils.addAll(l,sncs);
				
		}	
		return(l);
	}
	
	/**
	 * Get all SNC with TP.
	 * @param rate the connection rate.
	 * @return List of SubnetworkConnection object.
	 * @throws HoshenException in case of error.
	 */
	public List getAllSNCWithTp(short rate) throws ProcessingFailureException
	{
		List<SubnetworkConnection_T> l = new LinkedList<SubnetworkConnection_T>();
		boolean hasMore = true;
		
		SubnetworkConnectionList_THolder listHolder = new SubnetworkConnectionList_THolder();
		SNCIterator_IHolder iterHolder = new SNCIterator_IHolder();
		short connRate[] = new short[1];
		connRate[0] = rate;
		getMultiLayerSubnetMgr().getAllSubnetworkConnectionsWithTP(m_subnetName,connRate,0,listHolder,iterHolder);
		while(hasMore && iterHolder.value != null) {
			hasMore = iterHolder.value.next_n(CHUNK_SIZE,listHolder);
			SubnetworkConnection_T sncs[] = listHolder.value;
			log.debug("length = " + sncs.length);
			CollectionUtils.addAll(l,sncs);
			
		}
		return(l);
	}
	
	
	/**
	 * Get all topological links
	 * @return a List of TopologicalLink object.
	 * @throws HoshenException in case of error.
	 */
	public List getAllTopologicalLinks() throws ProcessingFailureException
	{
		List<TopologicalLink_T> l = new LinkedList<TopologicalLink_T>();
		boolean hasMore = true;
		TopologicalLinkIterator_IHolder iterHolder = new TopologicalLinkIterator_IHolder();
		TopologicalLinkList_THolder listHolder = new TopologicalLinkList_THolder(); 
		getMultiLayerSubnetMgr().getAllTopologicalLinks(m_subnetName,0,listHolder,iterHolder);
		while(hasMore && iterHolder.value != null) {
			hasMore = iterHolder.value.next_n(CHUNK_SIZE,listHolder);
			TopologicalLink_T tlinkt[] = listHolder.value;
			CollectionUtils.addAll(l,tlinkt);
				
		}
	
		return(l);
	}	
	
	/**
	 * Get all active alarms for a managed element.
	 * @param elemName the name of the element.
	 * @return a List of Alarm object.
	 * @throws HoshenException in case of error.
	 */
	public List getAllActiveAlarms(String elemName) throws ProcessingFailureException
	{
		List<StructuredEvent> l = new LinkedList<StructuredEvent>();
		boolean hasMore = true;
		NameAndStringValue_T value = new NameAndStringValue_T(MANAGED_ELEMENT, elemName);
		NameAndStringValue_T v[] = new NameAndStringValue_T[1];
		v[0] = value;
		String excludeList[] = new String[0];
		PerceivedSeverity_T pv[] = new PerceivedSeverity_T[0];
		EventList_THolder eventHolder = new EventList_THolder();
		EventIterator_IHolder iterHolder = new EventIterator_IHolder();
		getManagedElementManager().getAllActiveAlarms(v,excludeList,pv,CHUNK_SIZE,eventHolder,iterHolder);
		if(eventHolder != null) {
			StructuredEvent events[] = eventHolder.value;
			CollectionUtils.addAll(l,events);
		}			
		while(hasMore && iterHolder.value != null) {
			hasMore = iterHolder.value.next_n(CHUNK_SIZE,eventHolder);
			StructuredEvent stEvents[] = eventHolder.value;
			log.debug("length = " + stEvents.length);
			CollectionUtils.addAll(l,stEvents);
				
		}
		return(l);
	}
	
	/**
	 * Get all EMS and ME active alarms.
	 * @return a List of Alarm object.
	 * @throws HoshenException in case of error.
	 */
	public List getAllEMSandMEActiveAlarms() throws ProcessingFailureException
	{
		List<StructuredEvent> l = new LinkedList<StructuredEvent>();
		boolean hasMore = true;		
		String excludeList[] = new String[0];
		PerceivedSeverity_T pv[] = new PerceivedSeverity_T[0];
		
		EventList_THolder eventHolder = new EventList_THolder();
		EventIterator_IHolder iterHolder = new EventIterator_IHolder();
		getEmsManager().getAllEMSAndMEActiveAlarms(excludeList,pv,CHUNK_SIZE,eventHolder,iterHolder);
		if(eventHolder != null) {
			StructuredEvent events[] = eventHolder.value;
			CollectionUtils.addAll(l,events);
		}
		while(hasMore && iterHolder.value != null ) {
			hasMore = iterHolder.value.next_n(CHUNK_SIZE,eventHolder);
			StructuredEvent stEvents[] = eventHolder.value;
			log.debug("length = " + stEvents.length);
			CollectionUtils.addAll(l,stEvents);
				
		}
		return(l);
	}
	
	/**
	 * performMaintenanceOp on a device
	 * @param type CTP or PTP.
	 * @param tpName the name of the  CTP or PTP
	 * @param mopName the MaintenanceOperation name.
	 * @param layerRate short the layer rate.
	 * @throws HoshenException in case of error.
	 */
	public void performMaintenanceOpTp(String type, String meName, String tpName, String mopName,short layerRate, int mode) throws ProcessingFailureException
	{
		log.debug("type=" + type + "\tme=" + meName + "\tpNamep="+ tpName +"\tmopName=" + mopName + "\tlayerRate=" + layerRate + "\tmode=" + mode);
		NameAndStringValue_T value = new NameAndStringValue_T(type, tpName);
		NameAndStringValue_T v[] = new NameAndStringValue_T[3];
		v[0]  = m_emsName;
		v[1]  = new NameAndStringValue_T(MANAGED_ELEMENT,meName);
		v[2] = value;
		CurrentMaintenanceOperation_T mop = new CurrentMaintenanceOperation_T();
		mop.layerRate = layerRate;
		mop.tpName = v;
		mop.maintenanceOperation = mopName;
		mop.additionalInfo = new NameAndStringValue_T[0];
		MaintenanceOperationMode_T mopMode = MaintenanceOperationMode_T.from_int(mode);
		getMaintenanceManager().performMaintenanceOperation(mop,mopMode);
	}
	
	/**
	 * performMaintenanceOp on a device
	 * @param meName the name of the ManagedElement.
	 * @param mopName the MaintenanceOperation name.
	 * @param layerRate short the layer rate.
	 * @throws HoshenException in case of error.
	 */
	public void performMaintenanceOpMe( String meName, String mopName,short layerRate, int mode) throws ProcessingFailureException
	{
		NameAndStringValue_T v[] = new NameAndStringValue_T[2];
		v[0]  = m_emsName;
		v[1]  = new NameAndStringValue_T(MANAGED_ELEMENT,meName);
		
		CurrentMaintenanceOperation_T mop = new CurrentMaintenanceOperation_T();
		mop.layerRate = layerRate;
		mop.tpName = v;
		mop.maintenanceOperation = mopName;
		mop.additionalInfo = new NameAndStringValue_T[0];
		MaintenanceOperationMode_T mopMode = MaintenanceOperationMode_T.from_int(mode);
		getMaintenanceManager().performMaintenanceOperation(mop,mopMode);
	}
	
	
	/**
	 * Get a route.
	 * @param sncName the name of the SNC
	 * @param includeHigherOrderCCs
	 * @return List of CrossConnect_T objects.
	 * @throws HoshenException in case of error.
	 */
	public List getRoute(String sncName,boolean includeHigherOrderCCs) throws ProcessingFailureException
	{
		NameAndStringValue_T v[] = new NameAndStringValue_T[3];
		LinkedList l = null;
		v[0]  = m_subnetName[0];
		v[1] = m_subnetName[1];
		v[2]  = new NameAndStringValue_T(SNC,sncName);
		Route_THolder holder = new Route_THolder();
		getMultiLayerSubnetMgr().getRoute(v,includeHigherOrderCCs,holder);
		if(holder !=  null) {
			l = new LinkedList();
			CollectionUtils.addAll(l,holder.value);
		}
		
		return(l);
	}
	
	/**
	 * Establish a pull consomer.
	 * @throws HoshenException in case of error.
	 */
	public void openPullConsumer() throws HoshenException
	{
		try {
			/*
			 * register for notifications.
			 */
			
			EmsSession_I  emsSession = m_instance.getEmsSession();
			ORB orb = m_instance.getORB();
			EventChannelHolder evChannelholder = new EventChannelHolder();
			emsSession.getEventChannel(evChannelholder);
			EventChannel channel = evChannelholder.value;
			ConsumerAdmin admin = channel.default_consumer_admin();
			
			m_proxyPullSupplier = StructuredProxyPullSupplierHelper.narrow(	admin.obtain_notification_pull_supplier(ClientType.STRUCTURED_EVENT,new IntHolder()));
			StructuredPullConsumer pullConsumer = new EmsPullConsumerPOA()._this(orb);			
			m_proxyPullSupplier.connect_structured_pull_consumer(pullConsumer);
			log.info("connected pullSupplier");
		} catch(Exception  pe){
			throw new HoshenException("Error in openPullConsumer ",pe);
		}
		
	}

	/**
	 * pull a list of events.
	 * @return List of StructureEvent
	 * @throws HoshenException in case of error.
	 */
	public List tryPullEvents() throws HoshenException
	{
		List <StructuredEvent> list = null;
		StructuredEvent event = tryPullEvent();
		while(null != event) {
			list.add(event);
			event = tryPullEvent();
		}
		return(list);
	}
	
	
	/**
	 * Try to pull events.
	 * @return StructureEvent object.
	 * @throws HoshenException in case of error.
	 * @throws Disconnected 
	 */
	public StructuredEvent  tryPullEvent() throws HoshenException
	{
		
		BooleanHolder bh = new BooleanHolder();
		StructuredEvent event;
		try
		{
			event = m_proxyPullSupplier.try_pull_structured_event(bh);
		} catch (Disconnected e)
		{
			// TODO Auto-generated catch block
			throw new HoshenException("Error in tryPullEvent: " + e);
		}		
		if(bh.value) {
			log.debug("got event");
		} else {
			event = null;
		}
		
		return(event);
	}
	
	/**
	 * close the session
	 * release all resources.
	 */
	public void close()
	{
		log.debug(">close");
		try {
			byte b[] = m_poa.reference_to_id(m_nmsSession);
			m_poa.deactivate_object(b);
			log.debug("deactivating nmsSession object");
		} catch(Exception e){
			log.error("Error deactivating object: " + e);
		}
		m_poa.destroy(false,false);
		
		if(m_elemMgr != null) {
			m_elemMgr._release();
		}
		if(m_emsMgr != null) {
			m_emsMgr._release();
		}
		if(m_maintenanceMgr != null) {
			m_maintenanceMgr._release();
		}
		if(m_subnetMgr != null) {
			m_subnetMgr._release();
		}
		if(m_equipMgr != null) {
			m_equipMgr._release();
		}
		
		/*
		 * end session.
		 */
		if(m_emsSession != null) {
			m_emsSession.endSession();
			log.info("emsSession endSession");
		}
		if(m_emsSession != null) {
			m_emsSession._release();
		}
		
		m_instance = null;
		log.debug("<close");
	}
	
	/**
	 * break up a string into an array of name components.
	 * @param nc the String to convert
	 * @return NameComponent[] an array of NameComponent.
	 */
	private static NameComponent[] getNameComponent(String nc)
	{
		StringTokenizer st = new StringTokenizer(nc,SLASH_SEP);
		int cnt = st.countTokens();
		NameComponent path[] =  new NameComponent[cnt];
		int i = 0;
		while(st.hasMoreTokens()) {
			String t = st.nextToken();
			int inx = t.indexOf(DOT_CHAR);
			String key = t.substring(0,inx);
			String value = t.substring(inx+1,t.length());
			path[i++] =  new NameComponent(key,value);
		}
		
		return(path);
	}
	
		
	/**
	 * Getter for m_subnetName. <br>
	 * 
	 * @return m_subnetName
	 */
	public NameAndStringValue_T[] getSubnetName()
	{
		return m_subnetName;
	}
}
