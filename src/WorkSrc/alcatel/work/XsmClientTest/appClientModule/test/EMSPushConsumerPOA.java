/* Created on 23/01/2007 */
package test;

import org.apache.log4j.Logger;


import hoshen.xsm.lightsoft.corba.CosEventComm.Disconnected;
import hoshen.xsm.lightsoft.corba.CosNotification.*;
import hoshen.xsm.lightsoft.corba.CosNotifyComm.InvalidEventType;
import hoshen.xsm.lightsoft.corba.CosNotifyComm.StructuredPushConsumerPOA;

/**
 * 
 * @author Odedn
 */
public class EMSPushConsumerPOA extends StructuredPushConsumerPOA {
	private final static Logger log = Logger.getLogger(EMSPushConsumerPOA.class);

	/* (non-Javadoc)
	 * @see hoshen.xsm.lightsoft.corba.CosNotifyComm.StructuredPushConsumerOperations#push_structured_event(hoshen.xsm.lightsoft.corba.CosNotification.StructuredEvent)
	 */
	public void push_structured_event(StructuredEvent notification)
			throws Disconnected
	{
		// TODO Auto-generated method stub
		System.out.println("push_structured_event()");
		printEvent(notification);
	}

	/* (non-Javadoc)
	 * @see hoshen.xsm.lightsoft.corba.CosNotifyComm.StructuredPushConsumerOperations#disconnect_structured_push_consumer()
	 */
	public void disconnect_structured_push_consumer()
	{
		// TODO Auto-generated method stub
		System.out.println("in disconnect_structured_pull_cinsumer");

	}

	/* (non-Javadoc)
	 * @see hoshen.xsm.lightsoft.corba.CosNotifyComm.NotifyPublishOperations#offer_change(hoshen.xsm.lightsoft.corba.CosNotification.EventType[], hoshen.xsm.lightsoft.corba.CosNotification.EventType[])
	 */
	public void offer_change(EventType[] added, EventType[] removed)
			throws InvalidEventType
	{
		// TODO Auto-generated method stub
		System.out.println("in offer_change");

	}
	public static void printEvent(StructuredEvent event)
	{
		org.omg.CORBA.Object o  = null;
		log.debug("StructuredEvent:");
		log.debug("===========================");
		EventHeader header = event.header;
		FixedEventHeader fixedHeader = header.fixed_header;
		log.debug("Event name = " +fixedHeader.event_name);
		log.debug("event type.domain name = " + fixedHeader.event_type.domain_name);
		log.debug("eventType. type name  = " + fixedHeader.event_type.type_name);
	}

}
