package hoshen.xsm.lightsoft.corba.CosNotifyComm;


/**
* hoshen/xsm/lightsoft/corba/CosNotifyComm/_StructuredPullSupplierStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyComm.idl
* 13:11:11 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * An interface for structured pull suppliers.
    */
public class _StructuredPullSupplierStub extends org.omg.CORBA_2_3.portable.ObjectImpl implements hoshen.xsm.lightsoft.corba.CosNotifyComm.StructuredPullSupplier
{
  // Constructors
  // NOTE:  If the default constructor is used, the
  //        object is useless until _set_delegate (...)
  //        is called.
  public _StructuredPullSupplierStub ()
  {
    super ();
  }

  public _StructuredPullSupplierStub (org.omg.CORBA.portable.Delegate delegate)
  {
    super ();
    _set_delegate (delegate);
  }


  /**
       * Pull a structured event from this supplier
       * @returns The structured event.
       * @raises Disconnected If this supplier is disconnected.
       */
  public hoshen.xsm.lightsoft.corba.CosNotification.StructuredEvent pull_structured_event () throws hoshen.xsm.lightsoft.corba.CosEventComm.Disconnected
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("pull_structured_event",true);
          _in = _invoke (_out);
          hoshen.xsm.lightsoft.corba.CosNotification.StructuredEvent __result = hoshen.xsm.lightsoft.corba.CosNotification.StructuredEventHelper.read (_in);
          return __result;
        } catch (org.omg.CORBA.portable.ApplicationException _ex) {
          _in = _ex.getInputStream ();
          String _id = _ex.getId ();
           if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosEventComm.DisconnectedHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosEventComm.DisconnectedHelper.read( _in );
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
          _servant_preinvoke( "pull_structured_event",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          hoshen.xsm.lightsoft.corba.CosNotification.StructuredEvent __result = ((hoshen.xsm.lightsoft.corba.CosNotifyComm.StructuredPullSupplierOperations)_so.servant).pull_structured_event(  );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // pull_structured_event


  /**
       * Try to pull a structured event from this supplier.
       * @parm <code>has_event</code> - boolean indicating if supplier has
       * an event.
       * @returns The structured event if has_event is true.
       * @raises Disconnected If this supplier is disconnected.
       */
  public hoshen.xsm.lightsoft.corba.CosNotification.StructuredEvent try_pull_structured_event (org.omg.CORBA.BooleanHolder has_event) throws hoshen.xsm.lightsoft.corba.CosEventComm.Disconnected
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("try_pull_structured_event",true);
          _in = _invoke (_out);
          hoshen.xsm.lightsoft.corba.CosNotification.StructuredEvent __result = hoshen.xsm.lightsoft.corba.CosNotification.StructuredEventHelper.read (_in);
          has_event.value = _in.read_boolean ();
          return __result;
        } catch (org.omg.CORBA.portable.ApplicationException _ex) {
          _in = _ex.getInputStream ();
          String _id = _ex.getId ();
           if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosEventComm.DisconnectedHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosEventComm.DisconnectedHelper.read( _in );
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
          _servant_preinvoke( "try_pull_structured_event",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
         org.omg.CORBA.BooleanHolder _has_event = new org.omg.CORBA.BooleanHolder();
          hoshen.xsm.lightsoft.corba.CosNotification.StructuredEvent __result = ((hoshen.xsm.lightsoft.corba.CosNotifyComm.StructuredPullSupplierOperations)_so.servant).try_pull_structured_event( _has_event );
         has_event.value = _has_event.value;
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // try_pull_structured_event


  /**
       * Disconnect this pull supplier.
       */
  public void disconnect_structured_pull_supplier ()
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("disconnect_structured_pull_supplier",true);
          _in = _invoke (_out);
          return;
        } catch (org.omg.CORBA.portable.ApplicationException _ex) {
          _in = _ex.getInputStream ();
          String _id = _ex.getId ();
          throw new org.omg.CORBA.UNKNOWN( "Unexpected User Exception: " + _id );
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
          _servant_preinvoke( "disconnect_structured_pull_supplier",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          ((hoshen.xsm.lightsoft.corba.CosNotifyComm.StructuredPullSupplierOperations)_so.servant).disconnect_structured_pull_supplier(  );
          return;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // disconnect_structured_pull_supplier


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
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("subscription_change",true);
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
          _servant_preinvoke( "subscription_change",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          ((hoshen.xsm.lightsoft.corba.CosNotifyComm.NotifySubscribeOperations)_so.servant).subscription_change( added,removed );
          return;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // subscription_change

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:omg.org/CosNotifyComm/StructuredPullSupplier:1.0", 
    "IDL:omg.org/CosNotifyComm/NotifySubscribe:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  final public static java.lang.Class _opsClass =
    hoshen.xsm.lightsoft.corba.CosNotifyComm.StructuredPullSupplierOperations.class;

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
} // class _StructuredPullSupplierStub