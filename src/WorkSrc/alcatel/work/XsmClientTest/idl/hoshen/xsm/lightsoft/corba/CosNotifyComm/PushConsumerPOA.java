package hoshen.xsm.lightsoft.corba.CosNotifyComm;


/**
* hoshen/xsm/lightsoft/corba/CosNotifyComm/PushConsumerPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyComm.idl
* 13:11:11 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * An interface for push consumers.
    */
public abstract class PushConsumerPOA extends org.omg.PortableServer.Servant
                implements hoshen.xsm.lightsoft.corba.CosNotifyComm.PushConsumerOperations, org.omg.CORBA.portable.InvokeHandler
{

  public hoshen.xsm.lightsoft.corba.CosNotifyComm.PushConsumer _this() {
     return hoshen.xsm.lightsoft.corba.CosNotifyComm.PushConsumerHelper.narrow(
        super._this_object());
  }

  public hoshen.xsm.lightsoft.corba.CosNotifyComm.PushConsumer _this(org.omg.CORBA.ORB orb) {
     return hoshen.xsm.lightsoft.corba.CosNotifyComm.PushConsumerHelper.narrow(
        super._this_object(orb));
  }

  public String[] _all_interfaces(
     org.omg.PortableServer.POA poa,
     byte[] objectId) {
         return (String[])__ids.clone();
  }

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:omg.org/CosNotifyComm/PushConsumer:1.0", 
    "IDL:omg.org/CosNotifyComm/NotifyPublish:1.0", 
    "IDL:omg.org/CosEventComm/PushConsumer:1.0"};

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("offer_change", new java.lang.Integer (0));
    _methods.put ("push", new java.lang.Integer (1));
    _methods.put ("disconnect_push_consumer", new java.lang.Integer (2));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {

  /**
       * Indicates that a supplier is changing the names of the types of
       * events it is publishing.
       * @parm <code>added</code> - The event types added.
       * @parm <code>removed</code> - The event types removed.
       * @raises InvalidEventType If any of the event type names in either 
       * the of the input sequences are invalid.
       */
       case 0:  // CosNotifyComm/NotifyPublish/offer_change
       {
         try {
           hoshen.xsm.lightsoft.corba.CosNotification.EventType added[] = hoshen.xsm.lightsoft.corba.CosNotification.EventTypeSeqHelper.read (in);
           hoshen.xsm.lightsoft.corba.CosNotification.EventType removed[] = hoshen.xsm.lightsoft.corba.CosNotification.EventTypeSeqHelper.read (in);
           this.offer_change (added, removed);
           out = $rh.createReply();
         } catch (hoshen.xsm.lightsoft.corba.CosNotifyComm.InvalidEventType __ex) {
           out = $rh.createExceptionReply ();
           hoshen.xsm.lightsoft.corba.CosNotifyComm.InvalidEventTypeHelper.write (out, __ex);
         }
         break;
       }


  /**
       * Push an event onto this consumer.
       * @parm <code>data</code> - The event data.
       * @raises Disconnected If this consumer is disconnected.
       */
       case 1:  // CosEventComm/PushConsumer/push
       {
         try {
           org.omg.CORBA.Any data = in.read_any ();
           this.push (data);
           out = $rh.createReply();
         } catch (hoshen.xsm.lightsoft.corba.CosEventComm.Disconnected __ex) {
           out = $rh.createExceptionReply ();
           hoshen.xsm.lightsoft.corba.CosEventComm.DisconnectedHelper.write (out, __ex);
         }
         break;
       }


  /**
       * Disconnect a push consumer.
       */
       case 2:  // CosEventComm/PushConsumer/disconnect_push_consumer
       {
         this.disconnect_push_consumer ();
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke


} // class _PushConsumerPOA