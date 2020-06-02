	/*
	 * Created on 19/07/2005
	 *
	 * TODO To change the template for this generated file go to
	 * Window - Preferences - Java - Code Style - Code Templates
	 */
package test;

	import java.util.Properties;

import org.omg.CORBA.BooleanHolder;
import org.omg.CORBA.IntHolder;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;


import hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ClientType;
import hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ConsumerAdmin;
import hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.EventChannel;
import hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.EventChannelHolder;
import hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.StructuredProxyPullSupplier;
import hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.StructuredProxyPullSupplierHelper;
import hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin._EventChannelStub;
import hoshen.xsm.lightsoft.corba.CosNotifyComm.StructuredPullConsumer;
import hoshen.xsm.lightsoft.corba.common.Common_IHolder;
import hoshen.xsm.lightsoft.corba.emsMgr.EMSMgr_I;
import hoshen.xsm.lightsoft.corba.emsMgr.EMSMgr_IHelper;
import hoshen.xsm.lightsoft.corba.emsSession.EmsSession_I;
import hoshen.xsm.lightsoft.corba.emsSession.EmsSession_IHolder;
import hoshen.xsm.lightsoft.corba.emsSessionFactory.EmsSessionFactory_I;
import hoshen.xsm.lightsoft.corba.emsSessionFactory.EmsSessionFactory_IHelper;
import hoshen.xsm.lightsoft.corba.globaldefs.NameAndStringValue_T;
import hoshen.xsm.lightsoft.corba.globaldefs.NamingAttributesIterator_IHolder;
import hoshen.xsm.lightsoft.corba.globaldefs.NamingAttributesList_THolder;
import hoshen.xsm.lightsoft.corba.globaldefs.ProcessingFailureException;
import hoshen.xsm.lightsoft.corba.nmsSession.NmsSession_I;
import hoshen.xsm.lightsoft.corba.nmsSession._NmsSession_IStub;
import hoshen.xsm.lightsoft.corba.notifications.EventIterator_IHolder;
import hoshen.xsm.lightsoft.corba.notifications.EventList_THolder;
import hoshen.xsm.lightsoft.corba.notifications.PerceivedSeverity_T;

	/**
	 * @author alex
	 *
	 * To change the template for this generated type comment go to
	 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
	 */
	public class EmsTest {

		public static void main(String[] args) {
			
			Properties p = new Properties();
			p.put( "org.omg.CORBA.ORBClass", "com.ibm.CORBA.iiop.ORB" );
			p.put( "com.ibm.CORBA.Debug", "1" );
			p.put( "com.ibm.CORBA.CommTrace", "1" );
			p.put( "com.ibm.CORBA.ORBInitRef.NameService", "corbaloc:iiop:N755:2899/NameService" );
			p.put( "com.ibm.CORBA.ORBInitRef.NameService", "corbaloc:iiop:N755:2899/NameServiceCellPersistentRoot" );		
//			p.put( "com.ibm.CORBA.ORBInitRef.NameService", "corbaloc:iiop:ECI:3075/NameService" );
//			p.put( "com.ibm.CORBA.ORBInitRef.NameServiceServerRoot", "corbaloc:iiop:ECI:3075/NameServiceServerRoot" );

			try {
				ORB orb = ORB.init( ( String[] ) null, p );
				org.omg.CORBA.Object ns = orb.resolve_initial_references( "NameService" );
				NamingContextExt initCtx = NamingContextExtHelper.narrow( ns );
				System.out.println( "NameService=" + orb.object_to_string( ns ) );
			
				NameComponent[] path = new NameComponent[] {
					new NameComponent( "TMF_MTNM", "Class" ),
					new NameComponent( "ECI", "Vendor" ),
					new NameComponent( "ECI:LightSoft_1", "EmsInstance" ),
					new NameComponent( "2_0", "Version" ),				
					new NameComponent( "ECI:LightSoft_1", "EmsSessionFactory_I" )
				};
				org.omg.CORBA.Object obj = initCtx.resolve( path );
				
				EmsSessionFactory_I emsSessionFactory = EmsSessionFactory_IHelper.narrow( obj );
				String version = emsSessionFactory.getVersion();				
				System.out.println("got EmsSessionFactory object version =" + version);
				System.out.println("got EmsSessionFactory object");
				NmsSession_I nmsSession = new _NmsSession_IStub();
				EmsSession_IHolder emsSessionHolder = new EmsSession_IHolder();
				emsSessionFactory.getEmsSession("hoshen", "hoshen",nmsSession,emsSessionHolder );
				
				System.out.println("emsSession=" + emsSessionHolder.toString());
				EmsSession_I emsSession = emsSessionHolder.value;
				Common_IHolder holder = new Common_IHolder();
				
				
				emsSession.getManager("EMS",holder);
				
				EMSMgr_I emsMgr = EMSMgr_IHelper.narrow(holder.value); 
				
				System.out.println("emsMgr=" + emsMgr.toString());
				
				NamingAttributesList_THolder nholder = new NamingAttributesList_THolder();
				NamingAttributesIterator_IHolder iterHolder = new NamingAttributesIterator_IHolder();
				// all entries will be returned in the nholder.
				emsMgr.getAllTopLevelSubnetworkNames(100,nholder,iterHolder );
				System.out.println("nholder length = " + nholder.value.length);				
				NameAndStringValue_T nsv1[][] = nholder.value;
				for(int j=0; j < nholder.value.length; ++j) {
									
					NameAndStringValue_T nsv[] = nsv1[j];
					for(int i=0; i < nsv.length; ++i) {
						System.out.println(nsv[i].name + "=" + nsv[i].value);
					}
				}
				PerceivedSeverity_T percievedSeverity[] = new PerceivedSeverity_T[0];
				EventList_THolder eventList = new EventList_THolder();
				EventIterator_IHolder eventIterator = new EventIterator_IHolder();
				emsMgr.getAllEMSSystemActiveAlarms(percievedSeverity,10,eventList,eventIterator);
				System.out.println("eventList length = " + eventList.value.length);
				
				/*
				 * register for notifications.
				 */
				EventChannel channel = new _EventChannelStub();
				EventChannelHolder evChannelholder = new EventChannelHolder();
				emsSession.getEventChannel(evChannelholder);
				channel = evChannelholder.value;
				ConsumerAdmin admin = channel.default_consumer_admin();
				StructuredProxyPullSupplier proxyPullSupplier = 
					StructuredProxyPullSupplierHelper.narrow(
							admin.obtain_notification_pull_supplier(ClientType.STRUCTURED_EVENT,new IntHolder()));
				StructuredPullConsumer pullConsumer = new EmsPullConsumerPOA()._this(orb);
				
				proxyPullSupplier.connect_structured_pull_consumer(pullConsumer);
				proxyPullSupplier.try_pull_structured_event(new BooleanHolder(true));

				/*
				 * cleanup
				 */
				proxyPullSupplier.disconnect_structured_pull_supplier();
				channel.destroy();
				emsSession.endSession();
				
	   		} catch (Exception e) {
				e.printStackTrace();
				ProcessingFailureException pe = (ProcessingFailureException) e;
			
				System.out.println("reason= " + pe.errorReason);
			}
			System.out.println( "done!" );
		}
	}


	

