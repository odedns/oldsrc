package hoshen.xsm.lightsoft.corba.CosNotifyComm;


/**
* hoshen/xsm/lightsoft/corba/CosNotifyComm/NotifySubscribePOATie.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyComm.idl
* 13:11:11 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * An interface used by event subscribers.
    */
public class NotifySubscribePOATie extends NotifySubscribePOA
{

  private hoshen.xsm.lightsoft.corba.CosNotifyComm.NotifySubscribeOperations _delegate;
  private org.omg.PortableServer.POA _poa;
  // Constructors
  public NotifySubscribePOATie (hoshen.xsm.lightsoft.corba.CosNotifyComm.NotifySubscribeOperations delegate) {
     this._delegate = delegate;
  }

  public NotifySubscribePOATie (hoshen.xsm.lightsoft.corba.CosNotifyComm.NotifySubscribeOperations delegate,
     org.omg.PortableServer.POA poa) {
     this._delegate = delegate;
     this._poa = poa;
  }

  public hoshen.xsm.lightsoft.corba.CosNotifyComm.NotifySubscribeOperations _delegate() {
     return this._delegate;
  }

  public void _delegate(
     hoshen.xsm.lightsoft.corba.CosNotifyComm.NotifySubscribeOperations delegate) {
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
       * Indicates that a consumer is changing the names of the types of
       * events it is subscribed to.
       * @parm <code>added</code> - The event types added.
       * @parm <code>removed</code> - The event types removed.
       * @raises InvalidEventType If any of the event type names in either 
       * the of the input sequences are invalid.
       */
  public void subscription_change (hoshen.xsm.lightsoft.corba.CosNotification.EventType[] added, hoshen.xsm.lightsoft.corba.CosNotification.EventType[] removed) throws hoshen.xsm.lightsoft.corba.CosNotifyComm.InvalidEventType
  {
    _delegate.subscription_change(added, removed);
    return;
  } // subscription_change


} // class NotifySubscribePOATie
