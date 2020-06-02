package hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin;


/**
* hoshen/xsm/lightsoft/corba/CosNotifyChannelAdmin/_ProxyPushConsumerStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosNotifyChannelAdmin.idl
* 13:11:03 GMT+02:00 ��� ����� 28 ���� 2007
*/


/**
    * An interface for proxy push consumers.
    */
public class _ProxyPushConsumerStub extends org.omg.CORBA_2_3.portable.ObjectImpl implements hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ProxyPushConsumer
{
  // Constructors
  // NOTE:  If the default constructor is used, the
  //        object is useless until _set_delegate (...)
  //        is called.
  public _ProxyPushConsumerStub ()
  {
    super ();
  }

  public _ProxyPushConsumerStub (org.omg.CORBA.portable.Delegate delegate)
  {
    super ();
    _set_delegate (delegate);
  }


  /**
       * Connect an any type push supplier to this proxy.
       * @parm <code>push_supplier</code> - The <code>PushSupplier</code>
       * object reference.
       * @raises AlreadyConnected If this supplier is already connected.
       */
  public void connect_any_push_supplier (hoshen.xsm.lightsoft.corba.CosEventComm.PushSupplier push_supplier) throws hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.AlreadyConnected
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("connect_any_push_supplier",true);
          hoshen.xsm.lightsoft.corba.CosEventComm.PushSupplierHelper.write (_out, push_supplier);
          _in = _invoke (_out);
          return;
        } catch (org.omg.CORBA.portable.ApplicationException _ex) {
          _in = _ex.getInputStream ();
          String _id = _ex.getId ();
           if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.AlreadyConnectedHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosEventChannelAdmin.AlreadyConnectedHelper.read( _in );
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
          _servant_preinvoke( "connect_any_push_supplier",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          ((hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ProxyPushConsumerOperations)_so.servant).connect_any_push_supplier( push_supplier );
          return;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // connect_any_push_supplier


  /**
       * The type of this proxy.
       */
  public hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ProxyType MyType ()
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("_get_MyType",true);
          _in = _invoke (_out);
          hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ProxyType __result = hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ProxyTypeHelper.read (_in);
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
          _servant_preinvoke( "_get_MyType",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ProxyType __result = ((hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ProxyConsumerOperations)_so.servant).MyType(  );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // MyType


  /**
       * The administration object that created this proxy.
       */
  public hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.SupplierAdmin MyAdmin ()
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("_get_MyAdmin",true);
          _in = _invoke (_out);
          hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.SupplierAdmin __result = hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.SupplierAdminHelper.read (_in);
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
          _servant_preinvoke( "_get_MyAdmin",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.SupplierAdmin __result = ((hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ProxyConsumerOperations)_so.servant).MyAdmin(  );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // MyAdmin


  /**
       * Get a list of event type names which represent the event types that
       * consumers connected to this channel are interested in receiving.
       * @parm <code>mode</code> - The mode of type retrieval.
       * @returns A sequence of event type names.
       */
  public hoshen.xsm.lightsoft.corba.CosNotification.EventType[] obtain_subscription_types (hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ObtainInfoMode mode)
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("obtain_subscription_types",true);
          hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ObtainInfoModeHelper.write (_out, mode);
          _in = _invoke (_out);
          hoshen.xsm.lightsoft.corba.CosNotification.EventType __result[] = hoshen.xsm.lightsoft.corba.CosNotification.EventTypeSeqHelper.read (_in);
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
          _servant_preinvoke( "obtain_subscription_types",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          hoshen.xsm.lightsoft.corba.CosNotification.EventType __result[] = ((hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ProxyConsumerOperations)_so.servant).obtain_subscription_types( mode );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // obtain_subscription_types


  /**
       * Validate whether or not this proxy can honour the specified 
       * quality of service requirements.
       * @parm <code>required_qos</code> - Quality of service name-value
       * pairs which a client is interested in validating.
       * @parm <code>available_qos</code> - Quality of service settings that
       * this proxy can support in addition to the ones in the input sequence.
       * @raises UnsupportedQoS If any of the quality of service settings
       * in the input sequence could not be honoured.
       */
  public void validate_event_qos (hoshen.xsm.lightsoft.corba.CosNotification.Property[] required_qos, hoshen.xsm.lightsoft.corba.CosNotification.NamedPropertyRangeSeqHolder available_qos) throws hoshen.xsm.lightsoft.corba.CosNotification.UnsupportedQoS
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("validate_event_qos",true);
          hoshen.xsm.lightsoft.corba.CosNotification.PropertySeqHelper.write (_out, required_qos);
          _in = _invoke (_out);
          available_qos.value = hoshen.xsm.lightsoft.corba.CosNotification.NamedPropertyRangeSeqHelper.read (_in);
          return;
        } catch (org.omg.CORBA.portable.ApplicationException _ex) {
          _in = _ex.getInputStream ();
          String _id = _ex.getId ();
           if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNotification.UnsupportedQoSHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNotification.UnsupportedQoSHelper.read( _in );
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
          _servant_preinvoke( "validate_event_qos",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
         hoshen.xsm.lightsoft.corba.CosNotification.NamedPropertyRangeSeqHolder _available_qos = new hoshen.xsm.lightsoft.corba.CosNotification.NamedPropertyRangeSeqHolder();
          ((hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ProxyConsumerOperations)_so.servant).validate_event_qos( required_qos,_available_qos );
         available_qos.value = _available_qos.value;
          return;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // validate_event_qos


  /**
       * Returns the current quality of service settings for this object.
       * @returns A sequence of name-value pairs defining the quality of 
       * service settings.
       */
  public hoshen.xsm.lightsoft.corba.CosNotification.Property[] get_qos ()
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("get_qos",true);
          _in = _invoke (_out);
          hoshen.xsm.lightsoft.corba.CosNotification.Property __result[] = hoshen.xsm.lightsoft.corba.CosNotification.PropertySeqHelper.read (_in);
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
          _servant_preinvoke( "get_qos",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          hoshen.xsm.lightsoft.corba.CosNotification.Property __result[] = ((hoshen.xsm.lightsoft.corba.CosNotification.QoSAdminOperations)_so.servant).get_qos(  );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // get_qos


  /**
       * Sets the quality of service settings for this object.
       * @parm <code>qos</code> - A sequence of name-value pairs defining
       * the desired quality of service settings.
       * @raises UnsupportedQoS If the requested settings are not supported.
       */
  public void set_qos (hoshen.xsm.lightsoft.corba.CosNotification.Property[] qos) throws hoshen.xsm.lightsoft.corba.CosNotification.UnsupportedQoS
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("set_qos",true);
          hoshen.xsm.lightsoft.corba.CosNotification.PropertySeqHelper.write (_out, qos);
          _in = _invoke (_out);
          return;
        } catch (org.omg.CORBA.portable.ApplicationException _ex) {
          _in = _ex.getInputStream ();
          String _id = _ex.getId ();
           if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNotification.UnsupportedQoSHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNotification.UnsupportedQoSHelper.read( _in );
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
          _servant_preinvoke( "set_qos",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          ((hoshen.xsm.lightsoft.corba.CosNotification.QoSAdminOperations)_so.servant).set_qos( qos );
          return;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // set_qos


  /**
       * Validates a set of quality of service requirements.
       * @parm <code>qos</code> -  A sequence of name-value pairs defining
       * quality of service settings that are to be validated.
       * @parm <code>available_qos</code> - A sequence of all additional
       * quality of service setting supported by this object.
       * @raises UnsupportedQoS If any of the setting in the input argument
       * are not supported.
       */
  public void validate_qos (hoshen.xsm.lightsoft.corba.CosNotification.Property[] required_qos, hoshen.xsm.lightsoft.corba.CosNotification.NamedPropertyRangeSeqHolder available_qos) throws hoshen.xsm.lightsoft.corba.CosNotification.UnsupportedQoS
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("validate_qos",true);
          hoshen.xsm.lightsoft.corba.CosNotification.PropertySeqHelper.write (_out, required_qos);
          _in = _invoke (_out);
          available_qos.value = hoshen.xsm.lightsoft.corba.CosNotification.NamedPropertyRangeSeqHelper.read (_in);
          return;
        } catch (org.omg.CORBA.portable.ApplicationException _ex) {
          _in = _ex.getInputStream ();
          String _id = _ex.getId ();
           if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNotification.UnsupportedQoSHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNotification.UnsupportedQoSHelper.read( _in );
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
          _servant_preinvoke( "validate_qos",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
         hoshen.xsm.lightsoft.corba.CosNotification.NamedPropertyRangeSeqHolder _available_qos = new hoshen.xsm.lightsoft.corba.CosNotification.NamedPropertyRangeSeqHolder();
          ((hoshen.xsm.lightsoft.corba.CosNotification.QoSAdminOperations)_so.servant).validate_qos( required_qos,_available_qos );
         available_qos.value = _available_qos.value;
          return;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // validate_qos


  /**
       * Add a filter to this object's list of filters. All these filters
       * are tried upon reception of an event.
       * @parm <code>new_filter</code> - The filter to add.
       * @returns An unique ID that identifies the added filter.
       */
  public int add_filter (hoshen.xsm.lightsoft.corba.CosNotifyFilter.Filter new_filter)
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("add_filter",true);
          hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterHelper.write (_out, new_filter);
          _in = _invoke (_out);
          int __result = _in.read_long ();
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
          _servant_preinvoke( "add_filter",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          int __result = ((hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterAdminOperations)_so.servant).add_filter( new_filter );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // add_filter


  /**
       * Remove a filter from this object's list of filters.
       * @parm <code>filter</code> - The filter ID.
       * @raises FilterNotFound If the ID does not correspond to any
       * filter that has been added to this object.
       */
  public void remove_filter (int filter) throws hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterNotFound
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("remove_filter",true);
          _out.write_long (filter);
          _in = _invoke (_out);
          return;
        } catch (org.omg.CORBA.portable.ApplicationException _ex) {
          _in = _ex.getInputStream ();
          String _id = _ex.getId ();
           if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterNotFoundHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterNotFoundHelper.read( _in );
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
          _servant_preinvoke( "remove_filter",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          ((hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterAdminOperations)_so.servant).remove_filter( filter );
          return;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // remove_filter


  /**
       * Get a filter from the filter ID.
       * @parm <code>filter</code> - The filter ID.
       * @raises FilterNotFound If the ID does not correspond to any
       * filter that has been added to this object.
       */
  public hoshen.xsm.lightsoft.corba.CosNotifyFilter.Filter get_filter (int filter) throws hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterNotFound
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("get_filter",true);
          _out.write_long (filter);
          _in = _invoke (_out);
          hoshen.xsm.lightsoft.corba.CosNotifyFilter.Filter __result = hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterHelper.read (_in);
          return __result;
        } catch (org.omg.CORBA.portable.ApplicationException _ex) {
          _in = _ex.getInputStream ();
          String _id = _ex.getId ();
           if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterNotFoundHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterNotFoundHelper.read( _in );
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
          _servant_preinvoke( "get_filter",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          hoshen.xsm.lightsoft.corba.CosNotifyFilter.Filter __result = ((hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterAdminOperations)_so.servant).get_filter( filter );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // get_filter


  /**
       * Get all filters added to this administration object.
       * @returns A sequence of filter IDs.
       */
  public int[] get_all_filters ()
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("get_all_filters",true);
          _in = _invoke (_out);
          int __result[] = hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterIDSeqHelper.read (_in);
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
          _servant_preinvoke( "get_all_filters",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          int __result[] = ((hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterAdminOperations)_so.servant).get_all_filters(  );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // get_all_filters


  /**
       * Remove all filters added to this administration object.
       */
  public void remove_all_filters ()
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("remove_all_filters",true);
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
          _servant_preinvoke( "remove_all_filters",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          ((hoshen.xsm.lightsoft.corba.CosNotifyFilter.FilterAdminOperations)_so.servant).remove_all_filters(  );
          return;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // remove_all_filters


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


  /**
       * Push an event onto this consumer.
       * @parm <code>data</code> - The event data.
       * @raises Disconnected If this consumer is disconnected.
       */
  public void push (org.omg.CORBA.Any data) throws hoshen.xsm.lightsoft.corba.CosEventComm.Disconnected
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("push",true);
          _out.write_any (data);
          _in = _invoke (_out);
          return;
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
          _servant_preinvoke( "push",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          ((hoshen.xsm.lightsoft.corba.CosEventComm.PushConsumerOperations)_so.servant).push( data );
          return;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // push


  /**
       * Disconnect a push consumer.
       */
  public void disconnect_push_consumer ()
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("disconnect_push_consumer",true);
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
          _servant_preinvoke( "disconnect_push_consumer",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          ((hoshen.xsm.lightsoft.corba.CosEventComm.PushConsumerOperations)_so.servant).disconnect_push_consumer(  );
          return;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // disconnect_push_consumer

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:omg.org/CosNotifyChannelAdmin/ProxyPushConsumer:1.0", 
    "IDL:omg.org/CosNotifyChannelAdmin/ProxyConsumer:1.0", 
    "IDL:omg.org/CosNotification/QoSAdmin:1.0", 
    "IDL:omg.org/CosNotifyFilter/FilterAdmin:1.0", 
    "IDL:omg.org/CosNotifyComm/PushConsumer:1.0", 
    "IDL:omg.org/CosNotifyComm/NotifyPublish:1.0", 
    "IDL:omg.org/CosEventComm/PushConsumer:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  final public static java.lang.Class _opsClass =
    hoshen.xsm.lightsoft.corba.CosNotifyChannelAdmin.ProxyPushConsumerOperations.class;

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
} // class _ProxyPushConsumerStub