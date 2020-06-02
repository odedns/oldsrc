package hoshen.xsm.lightsoft.corba.CosNotifyComm;


/**
* hoshen/xsm/lightsoft/corba/CosNotifyComm/NotifyPublishOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyComm.idl
* 13:11:11 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * An interface used by event publishers.
    */
public interface NotifyPublishOperations 
{

  /**
       * Indicates that a supplier is changing the names of the types of
       * events it is publishing.
       * @parm <code>added</code> - The event types added.
       * @parm <code>removed</code> - The event types removed.
       * @raises InvalidEventType If any of the event type names in either 
       * the of the input sequences are invalid.
       */
  void offer_change (hoshen.xsm.lightsoft.corba.CosNotification.EventType[] added, hoshen.xsm.lightsoft.corba.CosNotification.EventType[] removed) throws hoshen.xsm.lightsoft.corba.CosNotifyComm.InvalidEventType;
} // interface NotifyPublishOperations
