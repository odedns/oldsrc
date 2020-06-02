package hoshen.xsm.lightsoft.corba.CosNotifyComm;


/**
* hoshen/xsm/lightsoft/corba/CosNotifyComm/SequencePullConsumerPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyComm.idl
* 13:11:11 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * An interface for sequences pull consumers.
    */
public abstract class SequencePullConsumerPOA extends org.omg.PortableServer.Servant
                implements hoshen.xsm.lightsoft.corba.CosNotifyComm.SequencePullConsumerOperations, org.omg.CORBA.portable.InvokeHandler
{

  public hoshen.xsm.lightsoft.corba.CosNotifyComm.SequencePullConsumer _this() {
     return hoshen.xsm.lightsoft.corba.CosNotifyComm.SequencePullConsumerHelper.narrow(
        super._this_object());
  }

  public hoshen.xsm.lightsoft.corba.CosNotifyComm.SequencePullConsumer _this(org.omg.CORBA.ORB orb) {
     return hoshen.xsm.lightsoft.corba.CosNotifyComm.SequencePullConsumerHelper.narrow(
        super._this_object(orb));
  }

  public String[] _all_interfaces(
     org.omg.PortableServer.POA poa,
     byte[] objectId) {
         return (String[])__ids.clone();
  }

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:omg.org/CosNotifyComm/SequencePullConsumer:1.0", 
    "IDL:omg.org/CosNotifyComm/NotifyPublish:1.0"};

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("disconnect_sequence_pull_consumer", new java.lang.Integer (0));
    _methods.put ("offer_change", new java.lang.Integer (1));
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
       * Disconnect this pull consumer.
       */
       case 0:  // CosNotifyComm/SequencePullConsumer/disconnect_sequence_pull_consumer
       {
         this.disconnect_sequence_pull_consumer ();
         out = $rh.createReply();
         break;
       }


  /**
       * Indicates that a supplier is changing the names of the types of
       * events it is publishing.
       * @parm <code>added</code> - The event types added.
       * @parm <code>removed</code> - The event types removed.
       * @raises InvalidEventType If any of the event type names in either 
       * the of the input sequences are invalid.
       */
       case 1:  // CosNotifyComm/NotifyPublish/offer_change
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

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke


} // class _SequencePullConsumerPOA
