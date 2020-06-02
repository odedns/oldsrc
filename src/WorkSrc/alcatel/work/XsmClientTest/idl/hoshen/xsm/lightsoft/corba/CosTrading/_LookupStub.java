package hoshen.xsm.lightsoft.corba.CosTrading;


/**
* hoshen/xsm/lightsoft/corba/CosTrading/_LookupStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from lightsoft/CosTrading.idl
* 13:11:18 GMT+02:00 ��� ����� 28 ���� 2007
*/


/** This interface is used to query the trader and retrieve
 * offers which match specified conditions and constraints.
 */
public class _LookupStub extends org.omg.CORBA_2_3.portable.ObjectImpl implements hoshen.xsm.lightsoft.corba.CosTrading.Lookup
{
  // Constructors
  // NOTE:  If the default constructor is used, the
  //        object is useless until _set_delegate (...)
  //        is called.
  public _LookupStub ()
  {
    super ();
  }

  public _LookupStub (org.omg.CORBA.portable.Delegate delegate)
  {
    super ();
    _set_delegate (delegate);
  }


  /* doc: 25 */
  public void query (String type, String constr, String pref, hoshen.xsm.lightsoft.corba.CosTrading.Policy[] policies, hoshen.xsm.lightsoft.corba.CosTrading.LookupPackage.SpecifiedProps desired_props, int how_many, hoshen.xsm.lightsoft.corba.CosTrading.OfferSeqHolder offers, hoshen.xsm.lightsoft.corba.CosTrading.OfferIteratorHolder offer_iter, hoshen.xsm.lightsoft.corba.CosTrading.PolicyNameSeqHolder limits_applied) throws hoshen.xsm.lightsoft.corba.CosTrading.IllegalServiceType, hoshen.xsm.lightsoft.corba.CosTrading.UnknownServiceType, hoshen.xsm.lightsoft.corba.CosTrading.IllegalConstraint, hoshen.xsm.lightsoft.corba.CosTrading.LookupPackage.IllegalPreference, hoshen.xsm.lightsoft.corba.CosTrading.LookupPackage.IllegalPolicyName, hoshen.xsm.lightsoft.corba.CosTrading.LookupPackage.PolicyTypeMismatch, hoshen.xsm.lightsoft.corba.CosTrading.LookupPackage.InvalidPolicyValue, hoshen.xsm.lightsoft.corba.CosTrading.IllegalPropertyName, hoshen.xsm.lightsoft.corba.CosTrading.DuplicatePropertyName, hoshen.xsm.lightsoft.corba.CosTrading.DuplicatePolicyName
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("query",true);
          _out.write_string (type);
          _out.write_string (constr);
          _out.write_string (pref);
          hoshen.xsm.lightsoft.corba.CosTrading.PolicySeqHelper.write (_out, policies);
          hoshen.xsm.lightsoft.corba.CosTrading.LookupPackage.SpecifiedPropsHelper.write (_out, desired_props);
          _out.write_ulong (how_many);
          _in = _invoke (_out);
          offers.value = hoshen.xsm.lightsoft.corba.CosTrading.OfferSeqHelper.read (_in);
          offer_iter.value = hoshen.xsm.lightsoft.corba.CosTrading.OfferIteratorHelper.read (_in);
          limits_applied.value = hoshen.xsm.lightsoft.corba.CosTrading.PolicyNameSeqHelper.read (_in);
          return;
        } catch (org.omg.CORBA.portable.ApplicationException _ex) {
          _in = _ex.getInputStream ();
          String _id = _ex.getId ();
           if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosTrading.IllegalServiceTypeHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosTrading.IllegalServiceTypeHelper.read( _in );
           else if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosTrading.UnknownServiceTypeHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosTrading.UnknownServiceTypeHelper.read( _in );
           else if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosTrading.IllegalConstraintHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosTrading.IllegalConstraintHelper.read( _in );
           else if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosTrading.LookupPackage.IllegalPreferenceHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosTrading.LookupPackage.IllegalPreferenceHelper.read( _in );
           else if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosTrading.LookupPackage.IllegalPolicyNameHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosTrading.LookupPackage.IllegalPolicyNameHelper.read( _in );
           else if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosTrading.LookupPackage.PolicyTypeMismatchHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosTrading.LookupPackage.PolicyTypeMismatchHelper.read( _in );
           else if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosTrading.LookupPackage.InvalidPolicyValueHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosTrading.LookupPackage.InvalidPolicyValueHelper.read( _in );
           else if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosTrading.IllegalPropertyNameHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosTrading.IllegalPropertyNameHelper.read( _in );
           else if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosTrading.DuplicatePropertyNameHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosTrading.DuplicatePropertyNameHelper.read( _in );
           else if ( _id.equals ( hoshen.xsm.lightsoft.corba.CosTrading.DuplicatePolicyNameHelper.id() ) )
            throw hoshen.xsm.lightsoft.corba.CosTrading.DuplicatePolicyNameHelper.read( _in );
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
          _servant_preinvoke( "query",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
         hoshen.xsm.lightsoft.corba.CosTrading.OfferSeqHolder _offers = new hoshen.xsm.lightsoft.corba.CosTrading.OfferSeqHolder();
         hoshen.xsm.lightsoft.corba.CosTrading.OfferIteratorHolder _offer_iter = new hoshen.xsm.lightsoft.corba.CosTrading.OfferIteratorHolder();
         hoshen.xsm.lightsoft.corba.CosTrading.PolicyNameSeqHolder _limits_applied = new hoshen.xsm.lightsoft.corba.CosTrading.PolicyNameSeqHolder();
          ((hoshen.xsm.lightsoft.corba.CosTrading.LookupOperations)_so.servant).query( type,constr,pref,policies,desired_props,how_many,_offers,_offer_iter,_limits_applied );
         offers.value = _offers.value;
         offer_iter.value = _offer_iter.value;
         limits_applied.value = _limits_applied.value;
          return;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // query

  public hoshen.xsm.lightsoft.corba.CosTrading.Lookup lookup_if ()
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("_get_lookup_if",true);
          _in = _invoke (_out);
          hoshen.xsm.lightsoft.corba.CosTrading.Lookup __result = hoshen.xsm.lightsoft.corba.CosTrading.LookupHelper.read (_in);
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
          _servant_preinvoke( "_get_lookup_if",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          hoshen.xsm.lightsoft.corba.CosTrading.Lookup __result = ((hoshen.xsm.lightsoft.corba.CosTrading.TraderComponentsOperations)_so.servant).lookup_if(  );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // lookup_if

  public hoshen.xsm.lightsoft.corba.CosTrading.Register register_if ()
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("_get_register_if",true);
          _in = _invoke (_out);
          hoshen.xsm.lightsoft.corba.CosTrading.Register __result = hoshen.xsm.lightsoft.corba.CosTrading.RegisterHelper.read (_in);
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
          _servant_preinvoke( "_get_register_if",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          hoshen.xsm.lightsoft.corba.CosTrading.Register __result = ((hoshen.xsm.lightsoft.corba.CosTrading.TraderComponentsOperations)_so.servant).register_if(  );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // register_if

  public hoshen.xsm.lightsoft.corba.CosTrading.Link link_if ()
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("_get_link_if",true);
          _in = _invoke (_out);
          hoshen.xsm.lightsoft.corba.CosTrading.Link __result = hoshen.xsm.lightsoft.corba.CosTrading.LinkHelper.read (_in);
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
          _servant_preinvoke( "_get_link_if",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          hoshen.xsm.lightsoft.corba.CosTrading.Link __result = ((hoshen.xsm.lightsoft.corba.CosTrading.TraderComponentsOperations)_so.servant).link_if(  );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // link_if

  public hoshen.xsm.lightsoft.corba.CosTrading.Proxy proxy_if ()
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("_get_proxy_if",true);
          _in = _invoke (_out);
          hoshen.xsm.lightsoft.corba.CosTrading.Proxy __result = hoshen.xsm.lightsoft.corba.CosTrading.ProxyHelper.read (_in);
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
          _servant_preinvoke( "_get_proxy_if",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          hoshen.xsm.lightsoft.corba.CosTrading.Proxy __result = ((hoshen.xsm.lightsoft.corba.CosTrading.TraderComponentsOperations)_so.servant).proxy_if(  );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // proxy_if

  public hoshen.xsm.lightsoft.corba.CosTrading.Admin admin_if ()
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("_get_admin_if",true);
          _in = _invoke (_out);
          hoshen.xsm.lightsoft.corba.CosTrading.Admin __result = hoshen.xsm.lightsoft.corba.CosTrading.AdminHelper.read (_in);
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
          _servant_preinvoke( "_get_admin_if",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          hoshen.xsm.lightsoft.corba.CosTrading.Admin __result = ((hoshen.xsm.lightsoft.corba.CosTrading.TraderComponentsOperations)_so.servant).admin_if(  );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // admin_if

  public boolean supports_modifiable_properties ()
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("_get_supports_modifiable_properties",true);
          _in = _invoke (_out);
          boolean __result = _in.read_boolean ();
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
          _servant_preinvoke( "_get_supports_modifiable_properties",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          boolean __result = ((hoshen.xsm.lightsoft.corba.CosTrading.SupportAttributesOperations)_so.servant).supports_modifiable_properties(  );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // supports_modifiable_properties

  public boolean supports_dynamic_properties ()
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("_get_supports_dynamic_properties",true);
          _in = _invoke (_out);
          boolean __result = _in.read_boolean ();
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
          _servant_preinvoke( "_get_supports_dynamic_properties",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          boolean __result = ((hoshen.xsm.lightsoft.corba.CosTrading.SupportAttributesOperations)_so.servant).supports_dynamic_properties(  );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // supports_dynamic_properties

  public boolean supports_proxy_offers ()
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("_get_supports_proxy_offers",true);
          _in = _invoke (_out);
          boolean __result = _in.read_boolean ();
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
          _servant_preinvoke( "_get_supports_proxy_offers",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          boolean __result = ((hoshen.xsm.lightsoft.corba.CosTrading.SupportAttributesOperations)_so.servant).supports_proxy_offers(  );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // supports_proxy_offers

  public org.omg.CORBA.Object type_repos ()
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("_get_type_repos",true);
          _in = _invoke (_out);
          org.omg.CORBA.Object __result = org.omg.CORBA.ObjectHelper.read (_in);
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
          _servant_preinvoke( "_get_type_repos",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          org.omg.CORBA.Object __result = ((hoshen.xsm.lightsoft.corba.CosTrading.SupportAttributesOperations)_so.servant).type_repos(  );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // type_repos

  public int def_search_card ()
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("_get_def_search_card",true);
          _in = _invoke (_out);
          int __result = _in.read_ulong ();
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
          _servant_preinvoke( "_get_def_search_card",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          int __result = ((hoshen.xsm.lightsoft.corba.CosTrading.ImportAttributesOperations)_so.servant).def_search_card(  );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // def_search_card

  public int max_search_card ()
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("_get_max_search_card",true);
          _in = _invoke (_out);
          int __result = _in.read_ulong ();
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
          _servant_preinvoke( "_get_max_search_card",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          int __result = ((hoshen.xsm.lightsoft.corba.CosTrading.ImportAttributesOperations)_so.servant).max_search_card(  );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // max_search_card

  public int def_match_card ()
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("_get_def_match_card",true);
          _in = _invoke (_out);
          int __result = _in.read_ulong ();
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
          _servant_preinvoke( "_get_def_match_card",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          int __result = ((hoshen.xsm.lightsoft.corba.CosTrading.ImportAttributesOperations)_so.servant).def_match_card(  );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // def_match_card

  public int max_match_card ()
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("_get_max_match_card",true);
          _in = _invoke (_out);
          int __result = _in.read_ulong ();
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
          _servant_preinvoke( "_get_max_match_card",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          int __result = ((hoshen.xsm.lightsoft.corba.CosTrading.ImportAttributesOperations)_so.servant).max_match_card(  );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // max_match_card

  public int def_return_card ()
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("_get_def_return_card",true);
          _in = _invoke (_out);
          int __result = _in.read_ulong ();
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
          _servant_preinvoke( "_get_def_return_card",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          int __result = ((hoshen.xsm.lightsoft.corba.CosTrading.ImportAttributesOperations)_so.servant).def_return_card(  );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // def_return_card

  public int max_return_card ()
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("_get_max_return_card",true);
          _in = _invoke (_out);
          int __result = _in.read_ulong ();
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
          _servant_preinvoke( "_get_max_return_card",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          int __result = ((hoshen.xsm.lightsoft.corba.CosTrading.ImportAttributesOperations)_so.servant).max_return_card(  );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // max_return_card

  public int max_list ()
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("_get_max_list",true);
          _in = _invoke (_out);
          int __result = _in.read_ulong ();
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
          _servant_preinvoke( "_get_max_list",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          int __result = ((hoshen.xsm.lightsoft.corba.CosTrading.ImportAttributesOperations)_so.servant).max_list(  );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // max_list

  public int def_hop_count ()
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("_get_def_hop_count",true);
          _in = _invoke (_out);
          int __result = _in.read_ulong ();
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
          _servant_preinvoke( "_get_def_hop_count",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          int __result = ((hoshen.xsm.lightsoft.corba.CosTrading.ImportAttributesOperations)_so.servant).def_hop_count(  );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // def_hop_count

  public int max_hop_count ()
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("_get_max_hop_count",true);
          _in = _invoke (_out);
          int __result = _in.read_ulong ();
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
          _servant_preinvoke( "_get_max_hop_count",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          int __result = ((hoshen.xsm.lightsoft.corba.CosTrading.ImportAttributesOperations)_so.servant).max_hop_count(  );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // max_hop_count

  public hoshen.xsm.lightsoft.corba.CosTrading.FollowOption def_follow_policy ()
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("_get_def_follow_policy",true);
          _in = _invoke (_out);
          hoshen.xsm.lightsoft.corba.CosTrading.FollowOption __result = hoshen.xsm.lightsoft.corba.CosTrading.FollowOptionHelper.read (_in);
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
          _servant_preinvoke( "_get_def_follow_policy",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          hoshen.xsm.lightsoft.corba.CosTrading.FollowOption __result = ((hoshen.xsm.lightsoft.corba.CosTrading.ImportAttributesOperations)_so.servant).def_follow_policy(  );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // def_follow_policy

  public hoshen.xsm.lightsoft.corba.CosTrading.FollowOption max_follow_policy ()
  {
    while(true) {
      if ( !this._is_local() ) {
        org.omg.CORBA.portable.InputStream _in = null;
        try {
          org.omg.CORBA.portable.OutputStream _out = _request ("_get_max_follow_policy",true);
          _in = _invoke (_out);
          hoshen.xsm.lightsoft.corba.CosTrading.FollowOption __result = hoshen.xsm.lightsoft.corba.CosTrading.FollowOptionHelper.read (_in);
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
          _servant_preinvoke( "_get_max_follow_policy",_opsClass );
        if ( _so == null ) { 
          continue;
        }
        try {
          hoshen.xsm.lightsoft.corba.CosTrading.FollowOption __result = ((hoshen.xsm.lightsoft.corba.CosTrading.ImportAttributesOperations)_so.servant).max_follow_policy(  );
          return __result;
        } finally { _servant_postinvoke( _so ); }
      }
    }
  } // max_follow_policy

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:omg.org/CosTrading/Lookup:1.0", 
    "IDL:omg.org/CosTrading/TraderComponents:1.0", 
    "IDL:omg.org/CosTrading/SupportAttributes:1.0", 
    "IDL:omg.org/CosTrading/ImportAttributes:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  final public static java.lang.Class _opsClass =
    hoshen.xsm.lightsoft.corba.CosTrading.LookupOperations.class;

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
} // class _LookupStub
