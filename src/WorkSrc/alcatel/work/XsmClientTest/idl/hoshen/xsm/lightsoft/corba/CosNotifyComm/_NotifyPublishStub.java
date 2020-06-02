package hoshen.xsm.lightsoft.corba.CosNotifyComm;


/**
* hoshen/xsm/lightsoft/corba/CosNotifyComm/_NotifyPublishStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyComm.idl
* 13:11:11 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * An interface used by event publishers.
    */
public class _NotifyPublishStub extends org.omg.CORBA_2_3.portable.ObjectImpl implements hoshen.xsm.lightsoft.corba.CosNotifyComm.NotifyPublish
{
  // Constructors
  // NOTE:  If the default constructor is used, the
  //        object is useless until _set_delegate (...)
  //        is called.
  public _NotifyPublishStub ()
  {
    super ();
  }

  public _NotifyPublishStub (org.omg.CORBA.portable.Delegate delegate)
  {
    super ();
    _set_delegate (delegate);
  }


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
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("offer_change",true);
          hoshen.xsm.lightsoft.corba.CosNotification.EventTypeSeqHelper.write (_out, added);
          hoshen.xsm.lightsoft.corba.CosNotification.EventTypeSeqHelper.write (_out, removed);
          _in = _invoke (_out);
          return;
        } catch (org.omg.CORBA.portable.ApplicationException _ex) {
          _in = _ex.getInputStream ();
          String _id = _ex.getId ();
           if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNotifyComm.InvalidEventTypeHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNotifyComm.InvalidEventTypeHelper.read( _in );
           else throw new org.omg.CORBA.UNKNOWN( "Unexpected User Exception: " + _id );
        } catch (org.omg.CORBA.portable.RemarshalException _rm) {
          continue;
        } catch (org.omg.CORBA.portable.UnknownException _ue) {
          Throwable _oe = _ue.originalEx;
          if (_oe instanceof Error)
              throw (Error)_oe;
          else if (_oe instanceof RuntimeException)
              throw (RuntimeException)_oe;
          else
              throw _ue;
        } finally { _releaseReply (_in); }
      }
      else {
        org.omg.CORBA.portable.ServantObject _so =
          _servant_preinvoke( "offer_change",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          ((hoshen.xsm.lightsoft.corba.CosNotifyComm.NotifyPublishOperations)_so.servant).offer_change( added,removed );
          return;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // offer_change

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:omg.org/CosNotifyComm/NotifyPublish:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  final public static java.lang.Class _opsClass =
    hoshen.xsm.lightsoft.corba.CosNotifyComm.NotifyPublishOperations.class;

  private void readObject (java.io.ObjectInputStream s)
  {
     try 
     {
       String str = s.readUTF ();
       org.omg.CORBA.Object obj = org.omg.CORBA.ORB.init ((String[])null, null).string_to_object (str);
       org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
       _set_delegate (delegate);
     } catch (java.io.IOException e) {}
  }

  private void writeObject (java.io.ObjectOutputStream s)
  {
     try 
     {
       String str = org.omg.CORBA.ORB.init ((String[])null, null).object_to_string (this);
       s.writeUTF (str);
     } catch (java.io.IOException e) {}
  }
} // class _NotifyPublishStub
