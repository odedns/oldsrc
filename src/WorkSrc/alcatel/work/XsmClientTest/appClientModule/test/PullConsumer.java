/* Created on 23/01/2007 */
package test;

import java.util.Properties;

import hoshen.common.utils.PropertyReader;
import hoshen.common.utils.StringUtils;
import hoshen.common.utils.ToStringBuilder;
import hoshen.common.utils.exception.HoshenException;
import hoshen.xsm.lightsoft.LightsoftManager;
import hoshen.xsm.lightsoft.corba.CosNotification.StructuredEvent;
import hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.*;
import hoshen.xsm.lightsoft.corba.CosNotifyComm.StructuredPullConsumer;
import hoshen.xsm.lightsoft.corba.CosNotifyComm.StructuredPushConsumer;
import hoshen.xsm.lightsoft.corba.common.Common_IHolder;
import hoshen.xsm.lightsoft.corba.emsSession.EmsSession_I;
import hoshen.xsm.lightsoft.corba.emsSession.EmsSession_IHolder;
import hoshen.xsm.lightsoft.corba.emsSessionFactory.EmsSessionFactory_I;
import hoshen.xsm.lightsoft.corba.emsSessionFactory.EmsSessionFactory_IHelper;
import hoshen.xsm.lightsoft.corba.nmsSession.NmsSession_I;
import hoshen.xsm.lightsoft.corba.nmsSession._NmsSession_IStub;

import org.omg.CORBA.BooleanHolder;
import org.omg.CORBA.IntHolder;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

/**
 * 
 * @author Odedn
 */
public class PullConsumer {

	

	public static void main(String[] args)
	{
		
		LightsoftManager manager = null;
		try {
	
			/*
			 * register for notifications.
			 */
			manager = LightsoftManager.getInstance("hoshen4","hoshen4");
			EmsSession_I  emsSession = manager.getEmsSession();
			ORB orb = manager.getORB();
			
			/*
			 * register for notifications.
			 */
			EventChannelHolder evChannelholder = new EventChannelHolder();
			emsSession.getEventChannel(evChannelholder);
			EventChannel channel = evChannelholder.value;
			ConsumerAdmin admin = channel.default_consumer_admin();
			
			StructuredProxyPullSupplier proxyPullSupplier = 
			StructuredProxyPullSupplierHelper.narrow(	admin.obtain_notification_pull_supplier(ClientType.STRUCTURED_EVENT,new IntHolder()));
			StructuredPullConsumer pullConsumer = new EmsPullConsumerPOA()._this(orb);
			
			proxyPullSupplier.connect_structured_pull_consumer(pullConsumer);			
			System.out.println("connected pullSupplier");
			while(true) {
				BooleanHolder bh = new BooleanHolder();
				StructuredEvent event = proxyPullSupplier.try_pull_structured_event(bh);		
				if(bh.value) {
					System.out.println("got event: " + ToStringBuilder.toString(event));
					LSDebug.printEvent(event);
				} 
				System.out.println("going to sleep..");
				Thread.sleep(10000);
			} // while
			/*
			System.out.println("cleaning up");
			proxyPullSupplier.disconnect_structured_pull_supplier();
			channel.destroy();
			emsSession.endSession();
			*/
			} catch(Exception e) {
				e.printStackTrace();
				manager.close();
			}
	}
}
