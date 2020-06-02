/* Created on 23/01/2007 */
package test;

import hoshen.xsm.lightsoft.LightsoftManager;
import hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.*;
import hoshen.xsm.lightsoft.corba.CosNotifyComm.StructuredPushConsumer;
import hoshen.xsm.lightsoft.corba.CosNotifyComm.StructuredPushConsumerPOATie;
import hoshen.xsm.lightsoft.corba.common.Common_IHolder;
import hoshen.xsm.lightsoft.corba.emsSession.EmsSession_I;

import org.omg.CORBA.IntHolder;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;




/**
 * 
 * @author Odedn
 */
public class PushConsumer {

	public static void main(String[] args)
	{
		
		
		LightsoftManager manager = null;
		try {
			/*
			 * register for notifications.
			 */
		
			
			manager = LightsoftManager.getInstance("hoshen2","hoshen2");
			EmsSession_I  emsSession = manager.getEmsSession();
			ORB orb = manager.getORB();
			EventChannel channel = new _EventChannelStub();
			EventChannelHolder evChannelholder = new EventChannelHolder();
			
			POA rootPOA = (POA) orb.resolve_initial_references("RootPOA");
			emsSession.getEventChannel(evChannelholder);
			channel = evChannelholder.value;
			ConsumerAdmin admin = channel.default_consumer_admin();
			//ConsumerAdmin admin = channel.get_consumeradmin(0);
			
			StructuredProxyPushSupplier proxyPushSupplier = 
				StructuredProxyPushSupplierHelper.narrow(
						admin.obtain_notification_push_supplier(ClientType.STRUCTURED_EVENT,new IntHolder()));
			
			EMSPushConsumerPOA emsPushConsumer = new EMSPushConsumerPOA();
			//StructuredPus
			StructuredPushConsumerPOATie pushConsumerTie = new StructuredPushConsumerPOATie(emsPushConsumer,rootPOA);
			rootPOA.activate_object(pushConsumerTie);
			StructuredPushConsumer pushConsumer = pushConsumerTie._this();
			proxyPushSupplier.connect_structured_push_consumer(pushConsumer);		
			System.out.println("Connected pushSupplier...running ORB");
			orb.run();
						
			/*
			 * cleanup
			 */
			
			System.out.println("disconnecting");
			proxyPushSupplier.disconnect_structured_push_supplier();
			channel.destroy();
			emsSession.endSession();
			
		} catch(Exception e) {
			e.printStackTrace();
			manager.close();
			
		}
	}
}
