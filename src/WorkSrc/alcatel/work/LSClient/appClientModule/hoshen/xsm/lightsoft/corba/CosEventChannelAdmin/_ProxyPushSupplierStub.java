package hoshen.xsm.lightsoft.corba.CosEventChannelAdmin;


/**
* hoshen/xsm/lightsoft/corba/CosEventChannelAdmin/_ProxyPushSupplierStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosEventChannelAdmin.idl
* 11:11:10 IST ��� ����� 6 ������ 2005
*/


/**
    * Interface for a proxy push supplier.
    */
public class _ProxyPushSupplierStub extends org.omg.CORBA_2_3.portable.ObjectImpl implements hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.ProxyPushSupplier
{
  // Constructors
  // NOTE:  If the default constructor is used, the
  //        object is useless until _set_delegate (...)
  //        is called.
  public _ProxyPushSupplierStub ()
  {
    super ();
  }

  public _ProxyPushSupplierStub (org.omg.CORBA.portable.Delegate delegate)
  {
    super ();
    _set_delegate (delegate);
  }


  /**
       * Connect a push consumer to this proxy.
       * @parm <code>push_consumer</code> - The <code>PushConsumer</code>
       * object reference.
       * @raises AlreadyConnected If this consumer is already connected.
       * @raises TypeError Illegal consumer type.
       */
  public void connect_push_consumer (hoshen.xsm.lightsoft.corba.CosEventComm.PushConsumer push_consumer) throws hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.AlreadyConnected, hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.TypeError
  {
    if ( !this._is_local() ) {
      org.omg.CORBA.portable.InputStream _in = null;
      try {
         org.omg.CORBA.portable.OutputStream _out = _request ("connect_push_consumer",true);
         hoshen.xsm.lightsoft.corba.CosEventComm.PushConsumerHelper.write (_out, push_consumer);
         _in = _invoke (_out);
         return;
      } catch (org.omg.CORBA.portable.ApplicationException _ex) {
         _in = _ex.getInputStream ();
         String _id = _ex.getId ();
         if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.AlreadyConnectedHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.AlreadyConnectedHelper.read( _in );
         else if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.TypeErrorHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.TypeErrorHelper.read( _in );
         else throw new org.omg.CORBA.UNKNOWN( "Unexpected User Exception: " + _id );
      } catch (org.omg.CORBA.portable.RemarshalException _rm) {
        connect_push_consumer( push_consumer ); return;
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
        _servant_preinvoke( "connect_push_consumer",_opsClass );
      if ( _so == null ) { connect_push_consumer( push_consumer ); return; }
      try {
         ((hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.ProxyPushSupplierOperations)_so.servant).connect_push_consumer( push_consumer );
         return;
      } finally { _servant_postinvoke( _so ); }
    }
  } // connect_push_consumer


  /**
       * Disconnect a push supplier.
       */
  public void disconnect_push_supplier ()
  {
    if ( !this._is_local() ) {
      org.omg.CORBA.portable.InputStream _in = null;
      try {
         org.omg.CORBA.portable.OutputStream _out = _request ("disconnect_push_supplier",true);
         _in = _invoke (_out);
         return;
      } catch (org.omg.CORBA.portable.ApplicationException _ex) {
         _in = _ex.getInputStream ();
         String _id = _ex.getId ();
        throw new org.omg.CORBA.UNKNOWN( "Unexpected User Exception: " + _id );
      } catch (org.omg.CORBA.portable.RemarshalException _rm) {
        disconnect_push_supplier(  ); return;
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
        _servant_preinvoke( "disconnect_push_supplier",_opsClass );
      if ( _so == null ) { disconnect_push_supplier(  ); return; }
      try {
         ((hoshen.xsm.lightsoft.corba.CosEventComm.PushSupplierOperations)_so.servant).disconnect_push_supplier(  );
         return;
      } finally { _servant_postinvoke( _so ); }
    }
  } // disconnect_push_supplier

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:omg.org/CosEventChannelAdmin/ProxyPushSupplier:1.0", 
    "IDL:omg.org/CosEventComm/PushSupplier:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  final public static java.lang.Class _opsClass =
    hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.ProxyPushSupplierOperations.class;

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
} // class _ProxyPushSupplierStub
