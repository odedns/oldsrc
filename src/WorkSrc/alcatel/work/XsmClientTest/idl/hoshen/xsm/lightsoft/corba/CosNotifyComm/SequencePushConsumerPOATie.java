package hoshen.xsm.lightsoft.corba.CosNotifyComm;


/**
* hoshen/xsm/lightsoft/corba/CosNotifyComm/SequencePushConsumerPOATie.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyComm.idl
* 13:11:11 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * An interface for sequence push consumers.
    */
public class SequencePushConsumerPOATie extends SequencePushConsumerPOA
{

  private hoshen.xsm.lightsoft.corba.CosNotifyComm.SequencePushConsumerOperations _delegate;
  private org.omg.PortableServer.POA _poa;
  // Constructors
  public SequencePushConsumerPOATie (hoshen.xsm.lightsoft.corba.CosNotifyComm.SequencePushConsumerOperations delegate) {
     this._delegate = delegate;
  }

  public SequencePushConsumerPOATie (hoshen.xsm.lightsoft.corba.CosNotifyComm.SequencePushConsumerOperations delegate,
     org.omg.PortableServer.POA poa) {
     this._delegate = delegate;
     this._poa = poa;
  }

  public hoshen.xsm.lightsoft.corba.CosNotifyComm.SequencePushConsumerOperations _delegate() {
     return this._delegate;
  }

  public void _delegate(
     hoshen.xsm.lightsoft.corba.CosNotifyComm.SequencePushConsumerOperations delegate) {
     this._delegate = delegate;
  }

  public org.omg.PortableServer.POA _default_POA() {
     if(_poa != null) {
        return _poa;
     } else {
        return super._default_POA();
     }
  }


  /**
       * Push a sequence of events onto this consumer.
       * @parm <code>notifications</code> - The event sequence.
       * @raises Disconnected If this consumer is disconnected.
       */
  public void push_structured_events (hoshen.xsm.lightsoft.corba.CosNotification.StructuredEvent[] notifications) throws hoshen.xsm.lightsoft.corba.CosEventComm.Disconnected
  {
    _delegate.push_structured_events(notifications);
    return;
  } // push_structured_events


  /**
       * Disconnect this push consumer.
       */
  public void disconnect_sequence_push_consumer ()
  {
    _delegate.disconnect_sequence_push_consumer();
    return;
  } // disconnect_sequence_push_consumer


  /**
       * Indicates that a supplier is changing the names of the types of
       * events it is publishing.
       * @parm <code>added</code> - The event types added.
       * @parm <code>removed</code> - The event types removed.
       * @raises InvalidEventType If any of the event type names in either 
       * the of the input sequences are invalid.
       */
  public void offer_change (hoshen.xsm.lightsoft.corba.CosNotification.EventType[] added, hoshen.xsm.lightsoft.corba.CosNotification.EventType[] removed) throws hoshen.xsm.lightsoft.corba.CosNotifyComm.InvalidEventType
  {
    _delegate.offer_change(added, removed);
    return;
  } // offer_change


} // class SequencePushConsumerPOATie