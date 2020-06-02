/*
 * Created on 20/09/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package test;

import hoshen.xsm.lightsoft.corba.CosNotification.EventType;
import hoshen.xsm.lightsoft.corba.CosNotifyComm.InvalidEventType;
import hoshen.xsm.lightsoft.corba.CosNotifyComm.StructuredPullConsumerPOA;

/**
 * @author odedn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EmsPullConsumerPOA extends StructuredPullConsumerPOA {

	/* (non-Javadoc)
	 * @see xsm.lightsoft.corba.CosNotifyComm.StructuredPullConsumerOperations#disconnect_structured_pull_consumer()
	 */
	public void disconnect_structured_pull_consumer() {
		// TODO Auto-generated method stub
		System.out.println("in disconnect_structured_pull_cinsumer");
	}

	/* (non-Javadoc)
	 * @see xsm.lightsoft.corba.CosNotifyComm.NotifyPublishOperations#offer_change(xsm.lightsoft.corba.CosNotification.EventType[], xsm.lightsoft.corba.CosNotification.EventType[])
	 */
	public void offer_change(EventType[] added, EventType[] removed)
			throws InvalidEventType {
		// TODO Auto-generated method stub
		System.out.println("in offer_change");

	}

}
