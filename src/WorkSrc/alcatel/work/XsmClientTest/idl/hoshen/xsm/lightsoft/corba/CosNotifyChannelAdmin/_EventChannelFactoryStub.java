package hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin;


/**
* hoshen/xsm/lightsoft/corba/CosNotifyChannelAdmin/_EventChannelFactoryStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyChannelAdmin.idl
* 13:11:07 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * An interface for the event channel factory.
    */
public class _EventChannelFactoryStub extends org.omg.CORBA_2_3.portable.ObjectImpl implements hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.EventChannelFactory
{
  // Constructors
  // NOTE:  If the default constructor is used, the
  //        object is useless until _set_delegate (...)
  //        is called.
  public _EventChannelFactoryStub ()
  {
    super ();
  }

  public _EventChannelFactoryStub (org.omg.CORBA.portable.Delegate delegate)
  {
    super ();
    _set_delegate (delegate);
  }


  /**
       * Create an event channel with specified quality of service.
       * @parm <code>initial_qos</code> - A list of name-value pair that
       * specify the desired quality of service settings for this event
       * channel.
       * @parm <code>initial_admin</code> - A list of name-value pair that
       * specify the desired administrative settings for this event channel.
       * @returns A new event channel object reference.
       * @raises UnsupportedQoS If any of the settings in the
       * <code>initial_qos</code> sequence could not be supported.
       * @raises UnsupportedAdmin If any of the settings in the
       * <code>initial_admin</code> sequence could not be supported.
       */
  public hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.EventChannel create_channel (hoshen.xsm.lightsoft.corba.CosNotification.Property[] initial_qos, hoshen.xsm.lightsoft.corba.CosNotification.Property[] initial_admin, org.omg.CORBA.IntHolder id) throws hoshen.xsm.lightsoft.corba.CosNotification.UnsupportedQoS, hoshen.xsm.lightsoft.corba.CosNotification.UnsupportedAdmin
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("create_channel",true);
          hoshen.xsm.lightsoft.corba.CosNotification.PropertySeqHelper.write (_out, initial_qos);
          hoshen.xsm.lightsoft.corba.CosNotification.PropertySeqHelper.write (_out, initial_admin);
          _in = _invoke (_out);
          hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.EventChannel __result = hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.EventChannelHelper.read (_in);
          id.value = _in.read_long ();
          return __result;
        } catch (org.omg.CORBA.portable.ApplicationException _ex) {
          _in = _ex.getInputStream ();
          String _id = _ex.getId ();
           if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNotification.UnsupportedQoSHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNotification.UnsupportedQoSHelper.read( _in );
           else if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNotification.UnsupportedAdminHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNotification.UnsupportedAdminHelper.read( _in );
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
          _servant_preinvoke( "create_channel",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
         org.omg.CORBA.IntHolder _id = new org.omg.CORBA.IntHolder();
          hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.EventChannel __result = ((hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.EventChannelFactoryOperations)_so.servant).create_channel( initial_qos,initial_admin,_id );
         id.value = _id.value;
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // create_channel


  /**
       * Get all event channels created by this factory.
       * @returns A sequence of channel object unique identifiers.
       */
  public int[] get_all_channels ()
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("get_all_channels",true);
          _in = _invoke (_out);
          int __result[] = hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ChannelIDSeqHelper.read (_in);
          return __result;
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
          _servant_preinvoke( "get_all_channels",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          int __result[] = ((hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.EventChannelFactoryOperations)_so.servant).get_all_channels(  );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // get_all_channels


  /**
       * Get an event channel object from its ID.
       * @parm <code>id</code> - A unique identifier for the channel object.
       * @returns An <code>EventChannel</code> object reference.
       * @raises ChannelNotFound If no channel object with that ID
       * could be found.
       */
  public hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.EventChannel get_event_channel (int id) throws hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ChannelNotFound
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("get_event_channel",true);
          _out.write_long (id);
          _in = _invoke (_out);
          hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.EventChannel __result = hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.EventChannelHelper.read (_in);
          return __result;
        } catch (org.omg.CORBA.portable.ApplicationException _ex) {
          _in = _ex.getInputStream ();
          String _id = _ex.getId ();
           if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ChannelNotFoundHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ChannelNotFoundHelper.read( _in );
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
          _servant_preinvoke( "get_event_channel",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.EventChannel __result = ((hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.EventChannelFactoryOperations)_so.servant).get_event_channel( id );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // get_event_channel

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:omg.org/CosNotifyChannelAdmin/EventChannelFactory:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  final public static java.lang.Class _opsClass =
    hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.EventChannelFactoryOperations.class;

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
} // class _EventChannelFactoryStub